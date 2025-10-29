<template>
  <div v-if="visible" class="left-bg">
    <IocTitle title="客流趋势" @change="closedWin()" />
    <div class="people-number-box">
      <div>
        <div class="title">
          <div>今日客流量</div>
          <div class="right-percent">
            <span>{{ percentage }}%</span>
            <span :class="percentage > 0 ? 'icon-up' : 'icon-down'"></span>
          </div>
        </div>
        <div class="number">{{ peopeData.todayNum }} <span>人次</span></div>
      </div>
      <div>
        <div class="title">昨日客流量</div>
        <div class="number">{{ peopeData.yesterdayNum }} <span>人次</span></div>
      </div>
    </div>

    <inOutLineChart :chartData="peopelStatisticData" />

    <IocTitle title="车流趋势" :closeVisible="false" />
    <div class="car-number">
      <div class="local">
        本地车牌 <span>{{ carData.localData }}</span> 车次
      </div>
      <div class="field">
        外地车牌 <span>{{ carData.fieldData }}</span> 车次
      </div>
    </div>

    <trafficLineChart :chartData="cardNumChartData" />
  </div>
</template>

<script>
import IocTitle from "@/components/title";
import * as CoordTransform from "@/utils/coordtransform";
import inOutLineChart from "./inOutLineChart.vue";
import trafficLineChart from "./trafficLineChart.vue";
import request from "@/utils/request";
import { concurRequest } from "@/utils/util";

export default {
  name: "parkingSupervisionLeft",
  components: { IocTitle, inOutLineChart, trafficLineChart },
  data() {
    return {
      visible: true,
      qushi: {},
      peopeData: {
        todayNum: 0,
        yesterdayNum: 0,
      },
      percentage: 0,
      peopelStatisticData: [],
      cardNumChartData: [],
      carData: {
        localData: "",
        fieldData: "",
      },
    };
  },
  mounted() {
    this.setMapMark();
    this.getStatisticsData();
  },
  methods: {
    async getStatisticsData() {
      // 客流趋势
      // request.post("api/binhu/getPersonnum").then(({ code, data }) => {
      //   if (code !== 200) return;
      //   this.squshi = data;
      // });
      let proxyName = "api";
      let urls = [
        `${proxyName}/binhu/getPersonnum`, // 客流趋势
        `${proxyName}/binhu/getPassengerLine`, // 客流趋势统计图
        `${proxyName}/binhu/getParkLine`, // 车流趋势统计图
      ];
      await concurRequest(urls, 5)
        .then((res) => {
          let resultMap = res.map((item) => item.code === 200 && item.data);
          console.log(resultMap);
          // 人
          if (resultMap[0]) {
            let data = resultMap[0];
            const today = data.filter((el) => el.type === "today");
            const yesterday = data.filter((el) => el.type === "yesterday");
            if (today.length > 0 && yesterday.length > 0) {
              this.peopeData = {
                todayNum: today[0]?.num,
                yesterdayNum: yesterday[0]?.num,
              };
            }
            this.percentage = Math.round(
              isNaN(this.peopeData.yesterdayNum / this.peopeData.todayNum)
                ? 0
                : this.peopeData.yesterdayNum / this.peopeData.todayNum
            );
            console.log(this.percentage);
          }
          // 人流统计
          resultMap[1] && (this.peopelStatisticData = resultMap[1]);
          // 车
          if (resultMap[2]) {
            this.carData = {
              localData: resultMap[2]
                .filter((item) => item.type === "bd")
                .reduce((pre, currnt) => {
                  return pre + currnt.num;
                }, 0),
              fieldData: resultMap[2]
                .filter((item) => item.type === "wd")
                .reduce((pre, currnt) => {
                  return pre + currnt.num;
                }, 0),
            };
            this.cardNumChartData = resultMap[2];
          }
        })
        .catch((err) => {
          console.log(err, "=====");
        });
    },
    setMapMark() {
      let parkMaker = [];
      __g.marker.clear();
      // 获取停车场定位i
      request
        .post("api/map/getPoint", { type: "park" })
        .then(({ code, data }) => {
          if (code !== 200) return;
          let img = require("@/assets/park-supervision/icon_map_point_08.png");
          for (let i = 0; i < data.length; i++) {
            let e = data[i];
            let marker = {
              id: "parkinglot" + e.id + i,
              userData: e.id,
              coordinate: [...this.getCoord(e.lng, e.lat), i * 10],
              imagePath: img,
              // text: `${e.name}`,
              fontSize: 20,
              displayMode: 2,
              range: [1, 80000],
              imageSize: [48, 58],
              anchors: [-24, 58],
              // textBackgroundColor: "RGB(0, 213, 255)",
              // fontColor: "RGB(255, 255, 255)",
            };
            parkMaker.push(marker);
          }
          console.log(parkMaker);
          // 添加点位
          __g.marker.add(parkMaker);
          // 聚焦点位
          __g.marker.focus([parkMaker[0].id], 500);
        });
    },
    closedWin() {
      this.visible = false;
    },
    // 坐标转换
    getCoord(lng, lat) {
      const a = CoordTransform.BD09ToGCJ02(lng, lat);
      const point = CoordTransform.GCJ02ToWGS84(a[0], a[1]);
      const points = CoordTransform.TransformByEPSG(
        4326,
        4549,
        point[0],
        point[1]
      );
      return points;
    },
    async getData() {
      /**
       * t1 井盖
       * t2 停车场
       * t3 路灯
       * t4 厕所
       * t5 户外广告
       * t6 雕塑
       * t7 座椅
       * t8 喷泉
       */
      let mapData = [];
      await request.get("./data/merchantPartsLocation.txt").then((res) => {
        let listData = res.split("\r\n");
        listData.forEach((item) => {
          if (item.includes("t2")) {
            let arr = item.split(",");
            let point = arr[3].split("/");

            // 下面两行 源数据 处理出现经纬度互换的情况
            let longitude = point[0] * 1 > 90 ? point[0] * 1 : point[1] * 1;
            let latitude = point[1] * 1 < 90 ? point[1] * 1 : point[0] * 1;

            let obj = {
              type: arr[0],
              name: arr[1],
              adress: arr[2],
              location: { longitude, latitude },
            };
            mapData.push(obj);
          }
        });
      });
      return mapData;
    },
  },
};
</script>

<style lang="scss" scoped>
.people-number-box {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
  & > div {
    display: flex;
    background-repeat: no-repeat;
    width: 208px;
    height: 66px;
    flex-direction: column;
    padding-left: 69px;
    justify-content: center;
  }
  .title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2px;
    font-size: 16px;
    width: 130px;

    .right-percent {
      display: flex;
      align-items: center;

      .icon-up {
        width: 12px;
        height: 12px;
        background-image: url("@/assets/park-supervision/icon_up@2x.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;
        margin-left: 2px;
      }
      .icon-down {
        width: 12px;
        height: 12px;
        background-repeat: no-repeat;
        background-image: url("@/assets/park-supervision/icon_down@2x.png");
        margin-left: 2px;
        background-size: 100% 100%;
      }
    }
  }
  .number {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 18px;
    width: 130px;
  }
  span {
    font-size: 15px;
    color: #fff;
  }
  & > div:nth-child(1) {
    background-image: url("@/assets/park-supervision/img_car_people_taday_bg.png");
    .number {
      color: #00d5ff;
    }
  }
  & > div:nth-child(2) {
    background-image: url("@/assets/park-supervision/img_car_people_yestday_bg.png");
    .number {
      color: #ffb24e;
    }
  }
}

.car-number {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  font-size: 16px;

  & > div {
    background-image: url("@/assets/park-supervision/img_slider_databox_2col.png");
    background-repeat: no-repeat;
    flex: 1;
    height: 44px;
    display: flex;
    align-items: center;
    padding-left: 25px;

    span {
      margin: 0 8px;
      font-family: "Inter";
      font-size: 18px;
      font-weight: 400;
    }
  }

  .local {
    background-position-y: -130px;
    margin-right: 5px;

    span {
      color: #00d5ff;
    }
  }

  .field {
    background-position-y: -174px;
    margin-left: 5px;

    span {
      color: #1bc078;
    }
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
</style>
