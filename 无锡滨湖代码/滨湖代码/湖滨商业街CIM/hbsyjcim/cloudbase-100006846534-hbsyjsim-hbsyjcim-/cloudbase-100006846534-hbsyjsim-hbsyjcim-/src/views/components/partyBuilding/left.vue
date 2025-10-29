<template>
  <div v-if="visible" class="left-bg">
    <IocTitle title="统计概况" @change="closedWin()" />

    <div class="box-01">
      <div>
        <p>湖滨商业街党群服务中心</p>
      </div>
      <div></div>
      <ul>
        <li>
          <p>党支部</p>
          <p>
            <span>{{ Dangjian }}</span
            ><span>&nbsp;&nbsp;个</span>
          </p>
        </li>
        <li>
          <p>个体户</p>
          <p>
            <span>{{ Geti }}</span
            ><span>&nbsp;&nbsp;个</span>
          </p>
        </li>
        <li>
          <p>企业</p>
          <p>
            <span>{{ Qiye }}</span
            ><span>&nbsp;&nbsp;个</span>
          </p>
        </li>
      </ul>
    </div>

    <IocTitle title="党员商铺" :closeVisible="false" />

    <div class="box-02">
      <ul class="head">
        <li>
          <div>
            <span>序号</span>
          </div>
          <div>
            <span>商铺名称</span>
          </div>
        </li>
      </ul>

      <div v-if="ShopList.length != 0">
        <ul class="content">
          <li v-for="(item, i) in ShopList" :key="i">
            <div>
              <span>{{ (PageNo - 1) * PageSize + i + 1 }}</span>
            </div>
            <div>
              <span>{{ item.name }}</span>
            </div>
          </li>
        </ul>
      </div>

      <div v-else class="none">暂无数据</div>

      <el-pagination
        class="page-box"
        layout="total,prev, pager, next"
        @current-change="changePage"
        :total="Count"
        :page-size="10"
        :pager-count="7"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import IocTitle from "@/components/title";

import {
  GetPartyCompanyTypeCountData,
  GetPartyMerchantList,
} from "@/utils/api/partyBuilding";

import { MarkerTool } from "@/utils/tool/MarkerTool";

export default {
  name: "PartyBuildingLeft",
  components: {
    IocTitle,
  },
  data() {
    return {
      visible: true,
      ShopList: [], //党员商铺列表

      Dangjian: 0,
      Geti: 0,
      Qiye: 0,

      Lisn: [],
      Count: 0,
      PageNo: 1,
      PageSize: 10,
    };
  },

  methods: {
    // 隐藏当前窗口
    closedWin() {
      this.visible = false;
    },

    // 改变页码
    changePage(val) {
      console.log(val);
      this.PageNo = val;
      this.getPartyMerchantList();
    },

    getPartyCompanyTypeCountData() {
      GetPartyCompanyTypeCountData({}).then((res) => {
        console.log("-统计概括-请求到接口了------");
        console.log(res);
        if (res.code == 200) {
          this.Dangjian = res.data[0].dangjian;
          this.Geti = res.data[0].geti;
          this.Qiye = res.data[0].qiye;
        }
      });
    },

    getPartyMerchantList() {
      let that = this;
      GetPartyMerchantList({
        pageNo: this.PageNo,
        pageSize: this.PageSize,
      }).then((res) => {
        console.log("-党员商铺-请求到接口了------");
        console.log(res);
        if (res.code == 200) {
          that.ShopList = res.data;
          that.Count = res.count;
        }
      });
    },
  },

  created() {
    this.getPartyCompanyTypeCountData();
    this.getPartyMerchantList();
  },

  beforeMount() {
    const markerTool = new MarkerTool();
    markerTool.Clear();
  },

  mounted() {
    const markerTool = new MarkerTool();

    __g.camera.set([525790.870859, 3489796.7275, 865.093125, -44.999958, -81.767433, 0]); //智慧党建的位置

    //需要特殊处理的数据
    let  specialData = [
        {
          name: "无锡市蠡园供销合作社支部委员会",
          textOffset: [-460, 0],
        },
        {
          name: "无锡市湖滨假日物业服务有限公司",
          textOffset: [-460, 0],
        },
    ];

    let isTextShow = true;   //显示名称

    markerTool.getPointShow("dzb", "党支部", "marker", specialData, isTextShow);
  },
};
</script>

<style lang="scss" scoped>
.box-01 {
  div:nth-child(1) {
    width: 428px;
    height: 100px;
    margin-top: 8px;
    background-image: url("@/assets/party-building/img_dangjian_partcenter_bg.png");
    background-size: 100% 100%;
    overflow: hidden;

    p {
      font-size: 22px;
      font-weight: 700;
      margin-left: 16px;
      margin-top: 37px;
    }
  }

  div:nth-child(2) {
    width: 296px;
    height: 32px;
    margin: 0 auto;
    background-image: url("@/assets/party-building/img_dangjian_line_3col.png");
    background-size: 100% 100%;
  }

  ul {
    display: flex;
    margin-bottom: 25px;

    li {
      flex-shrink: 0;
      width: 137px;
      height: 70px;
      background-image: url("@/assets/party-building/img_gongdan_databox_3col_bg.png");
      background-repeat: no-repeat;
      margin-right: 8px;

      p {
        width: 78%;
        margin-left: 22%;

        &:first-child {
          margin-top: 12px;
        }

        &:last-child {
          span {
            &:first-child {
              color: #00d5ff;
              font-size: 22px;
              font-weight: 400;
              line-height: 33px;
            }
          }
        }
      }
    }
  }
}

.box-02 {
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
    height: 420px;
  }

  .page-box {
    margin-top: 15px;
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

  .none {
    font-size: 14px;
    height: 420px;
    line-height: 420px;
    text-align: center;
  }
}
</style>