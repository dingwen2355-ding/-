<template>
  <div v-if="visible" class="left-bg">
    <IocTitle title="商户概览" @change="closedWin()" />
    <div class="box1">
      <div class="box1-up">
        <!-- <img :src="screenWidth <= 1920 ? require('@/assets/business-overview/Frame.png') : require('@/assets/business-overview/Frame@2x.png')"
                  alt=""> -->
        <img src="@/assets/business-overview/Frame.png" alt="">
        <div class="box1-up-text"><span class="box1-up-text-icon"></span> &nbsp;&nbsp;{{ businessOverview.total }}
          <span class="box1-up-unit">家</span>
        </div>
      </div>
      <div class="box1-down">
        <div class="box1-down-left">
          <!-- <img :src="screenWidth <= 1920 ? require('@/assets/business-overview/img_home_shop_bg.png') : require('@/assets/business-overview/img_home_shop_bg@2x.png')"
                      alt=""> -->
          <img src="@/assets/business-overview/img_home_shop_bg.png" alt="">
          <div class="box1-down-left-text">
            <div class="text-title">企业</div>
            <div class="text-content">
              {{ businessOverview.enterpriseNumber }} <span class="box1-up-unit">家</span>
            </div>
          </div>
        </div>
        <div class="box1-down-right">
          <!-- <img :src="screenWidth <= 1920 ? require('@/assets/business-overview/img_home_shop_bg(1).png') : require('@/assets/business-overview/img_home_shop_bg@2x(1).png')"
                      alt=""> -->
          <img src="@/assets/business-overview/img_home_shop_bg(1).png" alt="">
          <div class="box1-down-right-text">
            <div class="text-title">个体户</div>
            <div class="text-content">
              {{ businessOverview.individualNumber }} <span class="box1-up-unit">家</span>
            </div>
          </div>
        </div>
      </div>

    </div>
    <IocTitle title="业态分布" :closeVisible="false" />
    <div class="pieUnit">单位 家</div>
    <div class="box2">
      <div id="pieChart"></div>
      <dl>
        <dd v-for="(item, index) in businessDistribution" :key="index">
          <i></i>
          <span class="dis-name">{{ item.name }}</span>
          <span class="dis-value">{{ item.value }}</span>
          <span>{{ item.proportion }}</span>
        </dd>
      </dl>
    </div>
    <!-- <IocTitle title="商户人气·TOP 10" :closeVisible="false" /> -->
    <div class="top-box">
      <div class="top-box-item" :class="{ 'active': active == 1 }"
        @click="showTopData = popularityBusinessList; active = 1">商户人气TOP10</div>
      <div class="top-box-item" :class="{ 'active': active == 2 }" @click="showTopData = saleBusinessList; active = 2">
        商户营业额TOP10</div>
    </div>
    <div class="box3">
      <ul class="head">
        <li>
          <div>
            <span>排名</span>
          </div>
          <div>
            <span>商户名称</span>
          </div>
        </li>
      </ul>
      <ul class="content scroll-container">
        <div class="tip" v-show="showTopData.length == 0">暂无数据</div>
        <li v-for="(item, i) in showTopData" :key="i">
          <div>
            <span v-show="i >= 3">{{ i + 1 }}</span>
            <img v-show="i == 0" src="@/assets/business-overview/img_rang_icon_no1.png" alt="">
            <img v-show="i == 1" src="@/assets/business-overview/img_rang_icon_no2.png" alt="">
            <img v-show="i == 2" src="@/assets/business-overview/img_rang_icon_no3.png" alt="">
          </div>
          <div>
            <span>{{ item.name }}</span>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import IocTitle from '@/components/title'
import * as CoordTransform from '@/utils/coordtransform'
import request from '@/utils/request'
export default {
  name: "BusinessOverviewLeft",
  components: {
    IocTitle
  },
  data() {
    return {
      visible: true,
      businessOverview: {
        // total: 0,//总数
        // individualNumber: 0,//个体户商户数量
        // enterpriseNumber: 0,//企业商户数量
      },//商户概览数据
      businessDistribution: [
        // {
        //   name: "零售",
        //   value: 207,
        //   proportion: "28.47%"
        // },
        // {
        //   name: "科技服务",
        //   value: 128,
        //   proportion: "17.60%"
        // },
        // {
        //   name: "餐饮",
        //   value: 159,
        //   proportion: "21.87%"
        // },
        // {
        //   name: "休闲娱乐",
        //   value: 56,
        //   proportion: "7.70%"
        // },
        // {
        //   name: "其他",
        //   value: 177,
        //   proportion: "24.34%"
        // },
      ],//业态分布数据
      popularityBusinessList: [
        // "滨湖区沈府醉江南食府",
        // "无锡湖滨印象餐饮有限公司",
        // "无锡艾尼克斯餐饮管理有限公司",
        // "滨湖区手抄板凳饭店",
        // "无锡签挂缸烤餐饮管理有限公司",
        // "滨湖区活泼鲜跳火锅店",
        // "滨湖区阿琪火锅店",
        // "滨湖区你蚝我蚝餐饮店",
        // "无锡市猴哥烤王胖子龙虾餐饮有限公司",
        // "滨湖区品众餐饮店"
      ],//商户人气TOP10
      saleBusinessList: [
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },
        // {
        //   id: "",
        //   name: "滨湖区手抄板凳饭店"
        // },

      ],//商户营业额TOP10
      showTopData: [],
      active: 1,//tab选中
    }
  },
  beforeMount() {
    __g.marker.clear()
    __g.infoTree.hide('7ED18D9A439D78D5B62CF3AA7205B2A1');
  },
  mounted() {
    //商户概览和业态分布
    this.getbusinessDistribution()
    // 商户人气top10
    this.getpopularityBusinessList()

    // 地图打点
    this.getData().then(e => {
      let data = e
      let markerArr = []
      for (let i = 0; i < data.length; i++) {
        const e = data[i];
        if (!e.longitude || !e.latitude || !e.id || !e.name || !e.type) {
          // console.log("商户点位有数据为空");
          continue
        }
        let img;
        if (e.type == '零售批发') {
          img = require('@/assets/business-overview/icon_map_point_1.png')
        }
        else if (e.type == '餐饮住宿') {
          img = require('@/assets/business-overview/icon_map_point_2.png')
        }
        else if (e.type == '休闲娱乐') {
          img = require('@/assets/business-overview/icon_map_point_3.png')
        }
        else if (e.type == '科技服务') {
          img = require('@/assets/business-overview/icon_map_point_4.png')
        }
        else if (e.type == '其他类型') {
          img = require('@/assets/business-overview/icon_map_point_5.png')
        } else {
          img = require('@/assets/business-overview/icon_map_point_1.png')
        }
        let marker
        marker = {
          id: 'business' + i,
          userData: JSON.stringify({ code: e.id }),
          // coordinate: this.getCoord(e.longitude, e.latitude, e.zbx),
          coordinate: [...this.getCoord(e.longitude, e.latitude, e.zbx), 10],
          imagePath: img,
          // text: `${e.name}(${e.type})`,
          fontSize: 20,
          displayMode: 2,
          range: [1, 80000],
          imageSize: [48, 48],
          anchors: [-24, 48]
        }
        if (e.name == '无锡市九连环科技有限公司') {
          marker = {
            id: 'businessFocus',
            userData: JSON.stringify({ code: e.id }),
            // coordinate: this.getCoord(e.longitude, e.latitude, e.zbx),
            coordinate: [...this.getCoord(e.longitude, e.latitude, e.zbx), 10],
            imagePath: img,
            // text: `${e.name}(${e.type})`,
            fontSize: 20,
            displayMode: 2,
            range: [1, 80000],
            imageSize: [48, 48],
            anchors: [-24, 48]
          }
        }

        markerArr.push(marker)
      }
      // 添加点位
      __g.marker.add(markerArr)
      // 聚焦点位
      // __g.marker.focus(['businessFocus'], 300)
      __g.camera.set([525790.870859, 3489796.7275, 865.093125, -44.999958, -81.767433, 0]); //商业街的位置
    })

  },
  methods: {
    // 饼图
    async getPieChart() {
      const chartBox = await this.$echarts.init(document.getElementById('pieChart'))
      const option = {
        title: {
          text: this.businessOverview.total,
          subtext: '商户总数',
          left: 'center',
          top: '30%',
          textStyle: {
            fontSize: 24,
            fontWeight: 700,
            color: '#00d5ff'
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
            name: '商户类型',
            type: 'pie',
            radius: ['80%', '100%'],
            color: ['#3E3EDA', '#3D94FF', '#FFB24E', '#1BC078', '#1BDDD5'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              // borderColor: '#484d4f',
              borderWidth: 2,
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
            data: [...this.businessDistribution.map(item => { return { value: item.value, name: item.name } }),],
            emphasis: {
              scale: false, // 禁用点击时的放大效果
            },
          },
          { //虚线圆
            type: 'gauge',
            zlevel: 2,
            splitNumber: 30,
            radius: '100%',
            center: ['50%', '50%'],
            startAngle: 90,
            endAngle: -269.9999,
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            splitLine: {
              show: true,
              length: 2,
              lineStyle: {
                width: 3,
                color: '#00D5FF',
              },
            },
            pointer: {
              show: 0,
            },
            detail: {
              show: 0,
            },
          },
          { //背景圆
            type: "pie",
            center: ["50%", "50%"],
            radius: ["0%", "70%"],
            label: {
              show: false,
            },

            labelLine: {
              show: false,
            },

            itemStyle: {
              color: "rgba(0, 213, 255, 0.1)",
            },

            silent: true,
            data: [
              {
                value: 100,
                name: "",
              },
            ],
          },
        ]
      }
      await chartBox.setOption(option)
    },
    // 隐藏当前窗口
    closedWin() {
      this.visible = false
    },
    // 坐标转换
    getCoord(lng, lat, zbx) {
      let points;
      if (zbx == "baidu") {
        const a = CoordTransform.BD09ToGCJ02(lng, lat)
        const point = CoordTransform.GCJ02ToWGS84(a[0], a[1])
        points = CoordTransform.TransformByEPSG(4326, 4549, point[0], point[1])
      }
      else {
        lng = parseFloat(lng)
        lat = parseFloat(lat)
        points = CoordTransform.TransformByEPSG(4326, 4549, lng, lat)
      }

      return points
    },
    // 获取地图打点数据
    async getData() {
      let formatData = []
      await request({
        url: "/api/map/getPoint",
        method: "post",
        data: {
          type: "shop"
        }
      }).then(res => {
        console.log("商户点位---", res);
        if (res.data.length == 0) {
          console.log("商户点位无数据");
          return
        }
        let point = res.data.sort((a, b) => { return a.id - b.id })
        for (let i = 0; i < point.length; i++) {
          const e = point[i];
          let data = {
            name: e.name,
            type: e.shlx,
            longitude: e.lng,
            latitude: e.lat,
            id: e.id,
            zbx: e.zbx
          }
          formatData.push(data)
        }
      })
      return formatData
    },

    // 商户概览和业态分布数据
    async getbusinessDistribution() {
      let countInfo
      await request({
        url: "/api/binhu/getResourceVisualization",
        method: "post"
      }).then(res => {
        countInfo = res
      })

      await request({
        url: "/api/binhu/getOverview",
        method: "post"
      }).then(res => {
        let data1 = res.data.merchantCount
        let data2 = res.data.overview
        if (!data1.data[0] || !data2.data[1]) {
          console.log("商户概览和业态分布无数据");
          return
        }
        // this.businessOverview = {
        //   total: data1.total,
        //   enterpriseNumber: data1.data.filter(e => { return e.name == '工商企业' })[0].count,
        //   individualNumber: data1.data.filter(e => { return e.name == '工商个体户' })[0].count
        // }
        this.businessOverview = {
          total: countInfo.data[0].count + countInfo.data[1].count,
          enterpriseNumber: countInfo.data.filter(e => { return e.name == '工商企业' })[0].count,
          individualNumber: countInfo.data.filter(e => { return e.name == '工商个体户' })[0].count
        }
        data2.data.forEach(e => {
          let p = {
            name: e.name,
            value: e.count,
            proportion: (e.persent * 100).toFixed(2) + '%'
          }
          this.businessDistribution.push(p)
        });
        // 渲染饼图
        this.getPieChart()
      })
    },
    // 商户人气top10数据
    async getpopularityBusinessList() {
      await request({
        url: "/api/binhu/getTop10List",
        method: "post"
      }).then(res => {
        if (res.data.length == 0) {
          console.log("商户人气top10无数据");
          return
        }
        this.popularityBusinessList = res.data
        // 默认展示商户人气TOP10
        this.showTopData = this.popularityBusinessList
      })
    },

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

.left-bg {
  .box1 {
    margin-top: 16px;

    .box1-up-unit {
      padding-left: 8px;
      opacity: 1;
      color: #bcd1e9;
      font-size: 15px;
      font-weight: 400;
      font-family: "Inter";
      text-align: center;
    }

    .box1-up {
      position: relative;

      .box1-up-text {
        position: absolute;
        top: 26%;
        left: 21%;
        // width: 58px;
        height: 34px;
        opacity: 1;
        color: #00d5ff;
        font-size: 24px;
        font-weight: 600;
        font-family: "PingFang SC";
        text-align: center;
        display: flex;
        align-items: center;

        .box1-up-text-icon {
          // padding-right: 12px;
          width: 10px;
          height: 10px;
          flex-shrink: 0;
          border-radius: 10px;
          background: #38deff33;
        }
      }
    }

    .box1-down {
      margin-top: 16px;
      display: flex;
      height: 66px;
      margin-bottom: 24px;

      .text-title {
        color: #ffffff;
        // text-align: center;
        font-family: "Inter";
        font-size: 16px;
        font-style: normal;
        font-weight: 400;
        line-height: normal;
      }

      .text-content {
        color: #00D5FF;
        color: #00d5ff;
        font-family: "PingFang SC";
        font-size: 18px;
        font-style: normal;
        font-weight: 600;
        line-height: normal;
      }

      .box1-down-left {
        margin-right: 8px;
        position: relative;

        .box1-down-left-text {
          position: relative;
          top: -85%;
          left: 35%;
        }
      }

      .box1-down-right {
        position: relative;

        .box1-down-right-text {
          position: relative;
          top: -85%;
          left: 35%;
        }
      }
    }
  }

  .box2 {
    margin-bottom: 17px;
    display: flex;
    align-items: center;

    dl {
      margin-left: 30px;

      dd {
        display: flex;
        align-items: center;
        margin-bottom: 10px;
        color: #fff;

        i {
          width: 8px;
          height: 8px;
          display: inline-block;
          border-radius: 50%;
          margin: 0 8px;
          background-color: #3E3EDA;
        }

        .dis-name {
          width: 65px;
        }

        span {
          margin-left: 10px;
          font-size: 13px;
        }

        .dis-value {
          width: 37.113px;
        }

        &:nth-child(2) {
          i {
            background-color: #3D94FF;
          }
        }

        &:nth-child(3) {
          i {
            background-color: #FFB24E;
          }
        }

        &:nth-child(4) {
          i {
            background-color: #1BC078;
          }
        }

        &:nth-child(5) {
          i {
            background-color: #1BDDD5;
          }
        }
      }
    }
  }

  .top-box {
    width: 425px;
    display: flex;
    background-image: url('@/assets/business-overview/img_lvhua_tab_bg.png');



    .top-box-item {
      padding: 5px;
      width: 214px;
      text-align: center;
      font-size: 16px;
      color: #bcd1e9;
    }

    .active {
      width: 214px;
      // height: 20px;
      background-image: url('@/assets/business-overview/img_tab_bg_left_act.png');
      background-size: 100% 100%;
      color: #00D5FF;
    }
  }

  .box3 {
    margin-top: 10px;

    ul {
      li {
        height: 42px;
        display: grid;
        grid-template-columns: 56px auto;
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
      }
    }

    ul.content {
      height: 250px;
    }
  }

  .pieUnit {
    margin-top: 10px;
    margin-bottom: 10px;
    position: relative;
    padding-left: 20px;
    /* 调整文字和圆点之间的距离 */
    color: #00d5ff;
  }

  .pieUnit::before {
    content: '●';
    /* 添加一个蓝色圆点 */
    color: #00d5ff;
    /* 设置圆点的颜色 */
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
  }

  #pieChart {
    margin: 10px;
    width: 148px;
    height: 148px;
    flex-shrink: 0;
  }
}
</style>