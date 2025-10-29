<template>
  <div v-if="visible" class="left-bg">
    <i class="close" @click="closedWin"></i>
    <div class="top-tab">
      <div :class="activeTab === 1 ? 'active-tab' : ''" @click="activeTab = 1">
        停车概况
      </div>
      <div :class="activeTab === 2 ? 'active-tab' : ''" @click="activeTab = 2">
        实时出入数据
      </div>
    </div>

    <div v-if="activeTab === 1">
      <div class="parking-overview-box">
        <div>
          停车场数量
          <div>
            <span class="number">{{ topParkInfo.parknum }}</span> 家
          </div>
        </div>
        <div>
          停车位总数
          <div>
            <span class="number">{{ topParkInfo.parktotal }}</span> 个
          </div>
        </div>
        <div>
          空闲车位
          <div>
            <span class="number">{{ topParkInfo.freespacecount }}</span> 个
          </div>
        </div>
      </div>

      <IocTitle
        title="外地客流概况"
        style="margin: 15px 0 8px 0"
        :closeVisible="false"
      />
      <div class="overview-of-passenger-flow">
        <div>
          <div class="title">外地客流</div>
          <div class="number">{{ klxfInfo.wdkl }} <span>人次</span></div>
        </div>
        <div>
          <div class="title">消费金额</div>
          <div class="number">{{ klxfInfo.xfje }} <span>元</span></div>
        </div>
      </div>

      <div class="top10">客源来源地TOP10</div>

      <div class="top-box">
        <div class="t-item" v-for="(item, index) in top10" :key="index">
          <div class="t-top">
            <div class="t-left">
              TOP{{ index + 1 }} <span class="t-adress">{{ item.name }}</span>
            </div>
            <div class="t-right">
              {{ item.num }}辆
              <span class="t-scale">{{ item.scale }}%</span>
            </div>
          </div>
          <el-progress
            :percentage="item.scale"
            :show-text="false"
          ></el-progress>
        </div>
      </div>

      <IocTitle
        title="停车场信息"
        style="margin: 12px 0"
        :closeVisible="false"
      />

      <div class="park-info-box">
        <div v-for="(item, index) in parkInfoList" :key="index">
          <div class="p-top">
            <div class="p-title">
              {{ item.areaname
              }}<span class="p-precent">({{ item.percent }}%)</span>
            </div>
            <div class="p-info">{{ item.allspcace }}<span>车位</span></div>
          </div>
          <el-progress
            :percentage="item.percent"
            :show-text="false"
            :stroke-width="14"
            text-color="#FFFFFF"
          ></el-progress>
        </div>
      </div>
    </div>

    <div class="car-in-out-list" v-else>
      <div
        class="item"
        :class="item.xw == 'rc' ? 'type-in' : 'type-out'"
        v-for="(item, index) in realTimeList"
        :key="index"
      >
        <div
          class="type type-title-out"
          :class="item.xw == 'rc' ? 'type-title-in' : 'type-title-out'"
        >
          <div>{{ item.xw == "rc" ? "入场" : "出场" }}</div>
        </div>

        <div class="infos">
          <div>
            {{ item.plate }}
            <span>{{ item.cartype == "bd" ? "本地" : "外地" }}</span>
          </div>
          <div>
            {{ item.time }} 车辆已{{ item.xw == "rc" ? "入场" : "出场" }}
          </div>
          <div class="adress"><i></i>{{ item.areaname }}</div>
        </div>
        <div class="right-car">
          <!-- <img src="../../../assets/park-supervision/Rectangle.png" alt="" /> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import IocTitle from "@/components/title";
import { concurRequest } from "@/utils/util";
import request from "@/utils/request";
export default {
  name: "parkingSupervisionRight",
  components: { IocTitle },
  data() {
    return {
      visible: true,
      activeTab: 1,
      realTimeList: [],
      top10: [],
      parkInfoList: [],
      topParkInfo: {
        parknum: 0,
        freespacecount: 0,
        parktotal: 0,
      },
      klxfInfo: {
        wdkl: 0,
        xfje: 0,
      },
    };
  },
  watch: {
    activeTab(val) {
      if (val === 2) {
        request.post("api/binhu/getParkReal").then(({ code, data }) => {
          if (code !== 200) return;
          this.realTimeList = data;
        });
      }
    },
  },
  mounted() {
    this.getServerData();
  },
  methods: {
    closedWin() {
      this.visible = false;
    },
    async getServerData() {
      let proxyName = "api";
      let urls = [
        `${proxyName}/binhu/getParkInfo`, // 停车概况
        `${proxyName}/binhu/getWdInfo`, // 客流概况
        `${proxyName}/binhu/getParkPercent`, // 停车场信息
        `${proxyName}/binhu/getComefrom`, // 客源来源
      ];
      await concurRequest(urls, 5)
        .then((res) => {
          let resultMap = res.map((item) => item.code === 200 && item.data);
          // 停车概况
          resultMap[0] &&
            (this.topParkInfo = resultMap[0].length ? resultMap[0][0] : {});
          // 客流概况
          resultMap[1] && (this.klxfInfo = resultMap[1][0]);
          // 停车场信息
          resultMap[2] && (this.parkInfoList = resultMap[2]);
          // 客流来源
          if (resultMap[3]) {
            let allPeople = resultMap[3].reduce((pre, cur) => pre + cur.num, 0);
            resultMap[3].forEach((el) => {
              let scale = (el.num / allPeople) * 100;
              el.scale = scale.toFixed(2) * 1;
            });
            this.top10 = resultMap[3];
          }
        })
        .catch((err) => {
          console.log(err, "=====");
        });
    },
  },
};
</script>
<style lang="scss" scoped>
.close {
  display: inline-block;
  width: 22px;
  height: 22px;
  background-image: url("../../../assets/icon_btn_close.png");
  background-size: 100% 100%;
  position: absolute;
  right: 16px;
  top: 24px;
  cursor: pointer;
}
.top-tab {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  font-size: 18px;
  color: #fff;
  margin-bottom: 15px;
  div {
    padding: 6px 10px;
    margin-right: 10px;
  }
  .active-tab {
    color: #00d5ff;
    font-weight: 600;
    border-bottom: 2px solid #00d5ff;
  }
}

.parking-overview-box {
  display: flex;
  margin-top: 10px;
  justify-content: space-between;

  & > div {
    background-image: url("@/assets/park-supervision/img_park_data_blue_bg.png");
    background-repeat: no-repeat;
    width: 137px;
    height: 64px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding-left: 12px;
    font-size: 16px;
    color: #bcd1e9;

    & > div {
      font-size: 14px;
    }

    span {
      font-size: 18px;
      color: #00d5ff;
      font-weight: 400;
    }
  }
}

.overview-of-passenger-flow {
  display: flex;
  justify-content: space-between;
  & > div {
    width: 209px;
    height: 70px;
    flex-shrink: 0;
    background-repeat: no-repeat;
    display: flex;
    flex-direction: column;
    justify-content: center;
    & > div {
      margin-left: 25px;
    }
  }
  & > div:nth-child(1) {
    background-image: url("@/assets/park-supervision/img_people_data_bg.png");
  }
  & > div:nth-child(2) {
    background-image: url("@/assets/park-supervision/img_money_data_bg.png");
  }
  .title {
    font-size: 16px;
    color: #fff;
    margin-bottom: 8px;
  }
  .number {
    color: #00d5ff;
    font-size: 18px;
    font-weight: 500;
  }
  span {
    color: #a0bde0;
    font-size: 15px;
    margin-left: 8px;
  }
}

.top10 {
  width: 428px;
  height: 28px;
  flex-shrink: 0;
  background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 101.22%);
  padding-left: 10px;
  line-height: 28px;
  font-size: 18px;
  margin: 15px 0;
}

.top-box {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 428px;
  overflow-y: scroll;
  height: 200px;
  .t-item {
    display: flex;
    flex-direction: column;
    height: 38px;
    margin: 8px 0;
    .t-top {
      display: flex;
      justify-content: space-between;
      margin-bottom: 8px;
      .t-left {
        color: #00d5ff;
        font-size: 16px;
        .t-adress {
          color: #fff;
          margin-left: 10px;
        }
      }
      .t-right {
        font-size: 16px;
        color: #fff;
        .t-scale {
          color: #a0bde0;
          font-size: 14px;
          margin-left: 30px;
        }
      }
    }
  }
  ::v-deep .el-progress-bar__inner {
    background: linear-gradient(90deg, #5071ff 0%, #00d5ff 100%);
  }
  ::v-deep .el-progress-bar__outer {
    border-radius: 0 !important;
    width: 420px !important;
    background-color: #ffffff33 !important;
  }
}

.park-info-box {
  display: flex;
  flex-direction: column;
  overflow-y: scroll;
  height: 250px;
  & > div {
    margin-bottom: 10px;
    width: 408px;
    height: 48px;
    flex-shrink: 0;
    padding: 6px 8px;
    background: linear-gradient(90deg, #3d94ff4d 0.6%, #00d5ff26 76.59%);
  }
  .p-top {
    display: flex;
    justify-content: space-between;
    margin-bottom: 6px;
    .p-title {
      color: #fff;
      font-size: 18px;
      .p-precent {
        font-size: 14px;
        margin-left: 10px;
      }
    }
    .p-info {
      display: flex;
      color: #00d5ff;
      font-size: 18px;
      span {
        color: #a0bde0;
        font-size: 14px;
        margin-left: 10px;
      }
    }
  }

  ::v-deep .el-progress-bar__inner {
    background: #38deff;
  }
  ::v-deep .el-progress-bar__outer {
    border-radius: 0 !important;
    background-color: rgba(0, 213, 255, 0.4) !important;
  }
  ::v-deep .el-progress-bar__innerText {
    font-size: 12px !important;
  }
}

.car-in-out-list {
  overflow-y: scroll;
  max-height: calc(845px - 120px);
  .item {
    display: flex;
    align-items: center;
    background-repeat: no-repeat;
    height: 99px;
    font-size: 16px;
    margin-bottom: 15px;

    .type {
      display: flex;
      width: 48px;
      align-items: center;
      justify-content: center;
      color: #ffb24e;
      & > div {
        width: 16px;
        font-size: 18px;
        font-weight: 600;
      }
    }
    .type-title-in {
      color: #00d5ff;
    }
    .infos {
      flex: 1;
      display: flex;
      flex-direction: column;
      padding: 10px 15px;
      justify-content: space-between;
      color: #bcd1e9;
      div:nth-child(1) {
        font-size: 18px;
        color: #fff;
        font-weight: 700;
      }
      div:nth-child(2) {
        margin: 6px 0;
      }
      .adress {
        color: #bcd1e9;
        display: flex;
        align-items: center;
        i {
          background-image: url("@/assets/park-supervision/icon_adds.png");
          background-size: 100% 100%;
          width: 12px;
          height: 12px;
          margin-right: 5px;
        }
      }
    }
    .right-car {
      width: 99px;
      height: 99px;
      align-self: flex-end;
      display: flex;
    }
  }
  .type-in {
    background-image: url("@/assets/park-supervision/img_parkcar_out_data_bg.png");
  }
  .type-out {
    background-image: url("@/assets/park-supervision/img_parkcar_in_data_bg.png");
  }
}
</style>
