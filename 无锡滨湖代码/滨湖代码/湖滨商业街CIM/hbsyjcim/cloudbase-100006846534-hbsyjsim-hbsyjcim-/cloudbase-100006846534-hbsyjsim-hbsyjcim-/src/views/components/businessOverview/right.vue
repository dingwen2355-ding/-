<template>
  <div v-if="visible" class="right-bg">
    <IocTitle title="客户主体变化情况" @change="closedWin()" />
    <div class="box1">
      <div class="box1-head">
        <div class="box1-head-item" :class="{ 'active': active == 1 }" @click="clickItem(1)">按年度</div>
        <div class="box1-head-item" :class="{ 'active': active == 2 }" @click="clickItem(2)">按季度</div>
        <div class="box1-head-item" :class="{ 'active': active == 3 }" @click="clickItem(3)">按月</div>
      </div>
      <div class="box1-center">
        <div class="center-left">
          <div class="left-title title">新增总量</div>
          <div class="left-value value">{{ showBusinessChange.newAdd }} <span class="unit">家</span></div>
        </div>
        <div class="center-right">
          <div class="right-title title">{{ active == 1 ? '平均每年新增' : active == 2 ? '平均每季度新增' : '平均每月新增' }}</div>
          <div class="right-value value">{{ showBusinessChange.averAdd }} <span class="unit">家</span></div>
        </div>
      </div>
      <!-- <div class="axisUnit">单位 &nbsp;家</div>
          <div id="axisChart"></div> -->
      <div class="charts-box">
        <div id="pieChart2"></div>
        <div class="charts-box-right">
          <div class="charts-box-right-item">
            <div class="right-item-top">
              <div class="icon"></div>
              <div class="charts-box-right-item-top">
                已租商铺
              </div>
              <div class="per">
                {{ shop.rentedPer }}
              </div>
            </div>
            <div class="charts-box-right-item-down">{{ shop.rented.toString().replace(/\B(?=(\d{3})+(?!\d))/g,
              ',')
            }}
              <span>间</span>
            </div>

          </div>
          <div class="charts-box-right-item">
            <div class="right-item-top">
              <div class="icon"></div>
              <div class="charts-box-right-item-top">
                空置商铺
              </div>
              <div class="per">
                {{ shop.unRentPer }}
              </div>
            </div>
            <div class="charts-box-right-item-down">{{ shop.unRent.toString().replace(/\B(?=(\d{3})+(?!\d))/g,
              ',') }}
              <span>间</span>
            </div>

          </div>
        </div>
      </div>

    </div>
    <IocTitle title="商户列表" :closeVisible="false" />
    <div class="box2">
      <div class="query-box">
        <el-input placeholder="请输入商铺名称" v-model="key" class="query-inp" @input="filterBusiness">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
        <el-select v-model="type" placeholder="请选择客户类型" class="query-sel" :popper-append-to-body="false"
          @change="filterBusiness">
          <el-option v-for="(item, i) in businessType" :key="i" :label="item" :value="item">
          </el-option>
        </el-select>
      </div>
      <ul class="head">
        <li>
          <div>
            <span>序号</span>
          </div>
          <div>
            <span>商户名称</span>
          </div>
          <div>
            <span>商户类别</span>
          </div>
        </li>
      </ul>
      <ul class="content scroll-container">
        <div class="tip" v-show="pageList.length == 0">暂无数据</div>
        <li class="content-li" v-for="(item, i) in pageList" :key="i" @click="clickBusiness(item.id)">
          <div>
            <span>{{ (page - 1) * 7 + i + 1 }}</span>
          </div>
          <div>
            <span>{{ item.name }}</span>
          </div>
          <div>
            <span>{{ item.type ? item.type :
              '-' }}</span>
          </div>
        </li>
      </ul>
      <el-pagination class="page-box" layout="total,prev, pager, next" @current-change="changePage"
        :total="businessList.length" :page-size="7" :pager-count="5">
      </el-pagination>

    </div>
  </div>
</template>

<script>
import IocTitle from '@/components/title'
import request from '@/utils/request'
export default {
  name: 'PartyBuildingRight',
  components: {
    IocTitle
  },
  data() {
    return {
      visible: true,
      page: 1,
      initList: [],//原始商户列表数据
      businessList: [],//商户列表
      pageList: [],//商户列表分页展示的数据
      key: "",//搜索框的值
      type: "",//选择器的值
      active: 1,//当前选中商户主体变化情况
      businessChangeData: {
        newAdd: {
          year: '-',
          season: '-',
          month: '-',
        },//年、季度、月新增数据
        averAdd: {
          year: '-',
          season: '-',
          month: '-',
        },//年、季度、月平均新增数据
      },//商户主体变化年、月、日总数据
      showBusinessChange: {},//显示的商户主体变化数据
      shop: {
        rented: 0,//已租
        rentedPer: '0%',
        unRent: 0,//未租
        unRentPer: '0%',
      },//饼图商铺数据
    }
  },
  computed: {
    // 从原始商户列表数据中获取类型
    businessType() {
      const categorized = [];
      this.initList.forEach((business) => {
        const type = business.type;
        if (!categorized.includes(type) && type) {
          categorized.push(type);
        }
      });
      categorized.push("全部")

      return categorized;
    },
  },
  mounted() {
    // 商户数据
    this.getData()
    // 初始化饼图
    this.getPieChart()

    // 默认商户主体变化情况显示按年度
    this.showBusinessChange = {
      newAdd: this.businessChangeData.newAdd.year,
      averAdd: this.businessChangeData.averAdd.year,
      // cycleAdd: this.businessChangeData.cycleAdd.year
    }

  },
  methods: {
    // 获取商户数据
    async getData() {
      await request({
        url: "/api/map/getPoint",
        method: "post",
        data: {
          type: "shop"
        }
      }).then(res => {
        if (res.data.length == 0) {
          console.log("商户列表无数据");
          return
        }
        let formatData = []
        let businessList = res.data.sort((a, b) => { return a.id - b.id })
        for (let i = 0; i < businessList.length; i++) {
          const e = businessList[i];
          // if (!e.lng || !e.lat || !e.id || !e.name || !e.shlx) {
          //   // console.log("有数据为空");
          //   continue
          // }
          let d = {
            id: e.id,
            name: e.name,
            type: e.shlx
          }
          formatData.push(d)
        }

        this.initList = formatData
      })
      // 设置默认显示第一页商户列表
      this.businessList = [...this.initList]
      this.pageList = this.businessList.slice(0, 7)
    },
    // 点击切换商户主体变化情况
    clickItem(val) {
      this.active = val;
      if (val == 1) {
        this.showBusinessChange = {
          newAdd: this.businessChangeData.newAdd.year,
          averAdd: this.businessChangeData.averAdd.year,
          // cycleAdd: this.businessChangeData.cycleAdd.year
        }
      } else if (val == 2) {
        this.showBusinessChange = {
          newAdd: this.businessChangeData.newAdd.season,
          averAdd: this.businessChangeData.averAdd.season,
          // cycleAdd: this.businessChangeData.cycleAdd.season
        }
      } else if (val == 3) {
        this.showBusinessChange = {
          newAdd: this.businessChangeData.newAdd.month,
          averAdd: this.businessChangeData.averAdd.month,
          // cycleAdd: this.businessChangeData.cycleAdd.month,
        }
      }
    },
    // 商铺空置情况饼图
    async getPieChart() {
      const chartBox = await this.$echarts.init(document.getElementById('pieChart2'))
      const option = {
        title: {
          text: this.shop.unRentPer,
          subtext: '商铺空置',
          left: 'center',
          top: '30%',
          textStyle: {
            fontSize: 24,
            fontWeight: 700,
            color: '#00d5ff',
          },
          subtextStyle: {
            fontSize: 15,
            color: '#e2efff'
          }
        },
        tooltip: {
          //提示框
          show: true,
          backgroundColor: "rgba(0, 0, 0,.8)",

          textStyle: {
            color: "#fff",
          },
          position: 'right',
        },
        series: [
          {
            name: '商铺数',
            type: 'pie',
            radius: ['70%', '100%'],
            color: ['#5071ff', '#00d5ff'],
            avoidLabelOverlap: false,
            itemStyle: {
            },
            label: {
              normal: {
                show: false
              },

            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: [this.shop.rented, this.shop.unRent],
            emphasis: {
              scale: false, // 禁用点击时的放大效果
            },
          },

        ]
      }
      await chartBox.setOption(option)
    },
    // 搜索商户
    filterBusiness() {
      // console.log(this.key);
      // console.log(this.type);
      this.businessList = this.initList.filter((business) => {
        return business.name.includes(this.key) && business.type == this.type
      })
      if (this.type == "全部" || !this.type) {
        this.businessList = this.initList.filter((business) => {
          return business.name.includes(this.key)
        })
      }
      // console.log(this.businessList);
      this.pageList = this.businessList.slice(0, 7)
    },
    // 改变页码
    changePage(val) {
      this.page = val
      this.pageList = []
      this.pageList = this.businessList.slice((val - 1) * 7, val * 7)

    },
    // 点击右下角商铺
    clickBusiness(code) {
      if (!code) {
        return
      }
      this.$eventBus.$emit('showOverDialog', code);
    },
    closedWin() {
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>
.tip {
  width: 100%;
  text-align: center;
  margin-top: 15px;
  font-size: 18px;
}

.right-bg {
  .box1 {
    .box1-head {
      width: 424px;
      height: 28px;
      opacity: 1;
      background-image: url('@/assets/business-overview/img_lvhua_tab_bg.png');
      margin-top: 17px;
      margin-bottom: 16px;
      display: flex;
      align-items: center;

      .box1-head-item {
        width: 142.67px;
        height: 28px;
        opacity: 1;
        color: #bcd1e9;
        font-size: 16px;
        font-weight: 400;
        font-family: "Inter";
        padding-top: 6px;
        padding-bottom: 4px;
        display: flex;
        justify-content: center;
        align-items: center;
      }

      .active {
        // color: #38deff;
        // background: #338fff05;
        // box-shadow: inset 0 0 10px 8px #338fff;
        width: 142.67px;
        height: 20px;
        background-image: url('@/assets/business-overview/img_tab_bg_left_act.png');
        background-size: 100% 100%;
        color: #00D5FF;

      }
    }

    .box1-center {
      display: flex;
      justify-content: space-between;

      .center-left {
        padding-left: 60px;
        padding-top: 12.5px;
        width: 211px;
        height: 65px;
        box-sizing: border-box;
        background-image: url('@/assets/business-overview/img_ditan_loudong_bg1.png');
      }

      .center-right {
        padding-left: 60px;
        padding-top: 10px;
        width: 211px;
        height: 65px;
        box-sizing: border-box;
        background-image: url('@/assets/business-overview/img_ditan_loudong_bg1(1).png');
      }

      .title {
        font-size: 16px;
        font-weight: 400;
        font-family: "Inter";
        text-align: left;
      }

      .value {
        display: flex;
        align-items: center;
        width: 29px;
        height: 25px;
        opacity: 1;
        color: #00d5ff;
        font-size: 18px;
        font-weight: 600;
        font-family: "PingFang SC";
        text-align: left;

        .unit {
          padding-left: 8px;
          opacity: 1;
          color: #bcd1e9;
          font-size: 15px;
          font-weight: 400;
          font-family: "Inter";
          text-align: center;
        }
      }
    }

    .axisUnit {
      margin-top: 10px;
      position: relative;
      padding-left: 20px;
      color: #a0bde0;

    }

    .axisUnit::before {
      content: '●';
      /* 添加一个蓝色圆点 */
      color: #00d5ff;
      /* 设置圆点的颜色 */
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
    }

    // #axisChart {
    //     width: 428px;
    //     height: 150px;
    //     margin-bottom: 10px;
    // }
    .charts-box {
      display: flex;
      align-items: center;

      #pieChart2 {
        width: 150px;
        height: 150px;
        margin-top: 17px;
        margin-left: 29px;
        margin-bottom: 24px;
      }

      .charts-box-right {
        margin-left: 60px;
        height: 152px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;

        .charts-box-right-item {

          width: 188px;
          height: 70px;
          flex-shrink: 0;
          background: linear-gradient(0deg, #3d94ff1a 0%, #00d5ff1a 100%);
          stroke-width: 1px;
          stroke: #00d5ffb3;
          display: flex;
          flex-direction: column;

          .right-item-top {
            display: flex;
            align-items: center;
            margin-left: 10px;
            margin-top: 10px;

            .icon {
              width: 5px;
              height: 5px;
              border-radius: 6px;
              background: #00D5FF;
              margin-right: 8px;
            }


            .charts-box-right-item-top {
              width: 120px;
              color: #ffffff;
              font-family: "Inter";
              font-size: 16px;
              font-style: normal;
              font-weight: 400;
              line-height: normal;
            }

            .per {
              width: 100%;
              text-align: right;
              margin-right: 5px;
              color: #00d5ff;
              font-size: 15px;
            }
          }


          .charts-box-right-item-down {
            margin-top: 8px;
            margin-left: 26px;
            color: #00d5ff;
            font-family: "PingFang SC";
            font-size: 20px;
            font-style: normal;
            font-weight: 600;
            line-height: normal;

            span {
              color: #a0bde0;
              font-family: "Inter";
              font-size: 15px;
              font-style: normal;
              font-weight: 400;
              line-height: normal;
            }
          }

        }
      }
    }




  }

  .box2 {
    .query-box {
      display: flex;
      margin-top: 16px;
      margin-bottom: 10px;

      ::v-deep .el-input__inner {
        background: transparent;
        border-radius: 2px;
        opacity: 1;
        border: 1px solid #2e70bb;
        width: 210px;
        height: 36px;
        color: #fff;
      }

      .query-sel {
        ::v-deep .el-select-dropdown {
          background-color: #484d4f;
          border: none;

          .el-select-dropdown__item {
            color: #fff;
          }
        }
      }
    }

    .page-box {
      margin-top: 5px;
      width: 100%;
      display: flex;
      justify-content: flex-end;
      position: relative;

      ::v-deep .el-pagination__total {
        position: absolute;
        left: 0;
        color: #fff;
      }

      ::v-deep .btn-prev {
        background: #606465;
        color: #fff;
        width: 28px;
        min-width: 28px;
        border-radius: 50%;
      }

      ::v-deep .btn-next {
        background: #606465;
        color: #fff;
        width: 28px;
        min-width: 28px;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
      }

      ::v-deep .el-pager li {
        background: transparent;
        // border-radius: 50%;
        color: #fff;
      }

      ::v-deep .el-pager {
        .number {
          width: 28px;
          min-width: 28px;
          height: 28px;
          border-radius: 50%;
        }

        .active {
          background: #409eff;
        }
      }

    }

    ul {
      li {
        height: 42px;
        display: grid;
        grid-template-columns: 56px 270px auto;
        align-items: center;
        font-size: 14px;
        font-weight: 400;
        background-color: rgba(31, 168, 239, 0.2);

        &:nth-child(even) {
          background-color: rgba(31, 168, 239, 0.1);
        }

        div {
          &:nth-child(1) {
            text-align: center;
          }

          &:nth-child(2) {
            padding-left: 8px;
          }
        }
      }
    }

    ul.head {
      margin-top: 8px;

      li {
        color: rgb(0, 213, 255);
        font-size: 16px;
        font-weight: 400;
        background-color: rgba(29, 80, 120, 0.8);
        // display: flex;

        // div:nth-child(2) {
        //     flex: 1;
        // }
      }
    }

    ul.content {
      height: 294px;
    }
  }
}
</style>