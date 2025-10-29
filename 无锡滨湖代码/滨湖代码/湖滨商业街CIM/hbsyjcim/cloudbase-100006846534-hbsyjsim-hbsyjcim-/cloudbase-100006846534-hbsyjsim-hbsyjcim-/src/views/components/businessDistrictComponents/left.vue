<template>
  <div v-if="visible" class="left-bg">
    <IocTitle title="设备部件概览" @change="closedWin()" />

    <ul class="components-box">
      <li
        class="com-item"
        v-for="(item, index) in componentsList"
        :key="index"
        @click="handleClickItem(index, item.type, item.queryKey)"
        :class="activeIndex === index ? 'activeBg' : 'normalBg'"
      >
        <div class="imgbox">
          <img
            :src="activeIndex === index ? item.activeIcon : item.normalIcon"
            class="img-icon"
          />
        </div>
        <div class="infobox">
          <div class="value-line">
            <span>{{ item.value }}</span
            ><span>{{ item.unit }}</span>
          </div>
          <div class="infoname">{{ item.name }}</div>
        </div>
      </li>
    </ul>
    <IocTitle title="设备状态统计" :closeVisible="false" />
    <DeviceTypeChart :deviceData="allDevice" />
  </div>
</template>

<script>
import IocTitle from "@/components/title";
import DeviceTypeChart from "./leftDeviceChart.vue";
import request from "@/utils/request";
import * as CoordTransform from "@/utils/coordtransform";
import eventBus from "@/utils/eventBus";
import { concurRequest } from "@/utils/util";

export default {
  name: "businessDistrictComLeft",
  components: { IocTitle, DeviceTypeChart },
  data() {
    return {
      visible: true,
      activeIndex: 0,
      componentsList: [
        {
          name: "智能垃圾桶",
          normalIcon: require("../../../assets/businessDistrictComponents/icon_bujian_tursh.png"),
          activeIcon: require("../../../assets/businessDistrictComponents/icon_bujian_tursh_act.png"),
          unit: "个",
          value: "-",
          type: "t0",
          queryKey: "垃圾桶",
        },
        {
          name: "智能井盖",
          normalIcon: require("../../../assets/businessDistrictComponents/icon_bujian_cover.png"),
          activeIcon: require("../../../assets/businessDistrictComponents/icon_bujian_cover_act.png"),
          image: "",
          unit: "个",
          value: 59,
          type: "t1",
        },
        {
          name: "公共厕所",
          normalIcon: require("../../../assets/businessDistrictComponents/icon_bujian_tolit.png"),
          activeIcon: require("../../../assets/businessDistrictComponents/icon_bujian_tolit_act.png"),
          image: "",
          unit: "间",
          value: 2,
          type: "t4",
        },
        {
          name: "智能路灯",
          normalIcon: require("../../../assets/businessDistrictComponents/icon_bujian_light.png"),
          activeIcon: require("../../../assets/businessDistrictComponents/icon_bujian_light_act.png"),
          image: "",
          unit: "盏",
          value: 143,
          type: "t3",
        },
        {
          name: "户外广告",
          normalIcon: require("../../../assets/businessDistrictComponents/icon_bujian_billboard.png"),
          activeIcon: require("../../../assets/businessDistrictComponents/icon_bujian_billboard_act.png"),
          image: "",
          unit: "处",
          value: 17,
          type: "t5",
        },
        {
          name: "雕塑",
          normalIcon: require("../../../assets/businessDistrictComponents/icon_bujian_diaosu.png"),
          activeIcon: require("../../../assets/businessDistrictComponents/icon_bujian_diaosu_act.png"),
          image: "",
          unit: "座",
          value: 3,
          type: "t6",
        },
        {
          name: "街头座椅",
          normalIcon: require("../../../assets/businessDistrictComponents/icon_bujian_chair.png"),
          activeIcon: require("../../../assets/businessDistrictComponents/icon_bujian_chair_act.png"),
          image: "",
          unit: "个",
          value: 11,
          type: "t7",
        },
        {
          name: "喷泉",
          normalIcon: require("../../../assets/businessDistrictComponents/icon_bujian_penquan.png"),
          activeIcon: require("../../../assets/businessDistrictComponents/icon_bujian_penquan_act.png"),
          image: "",
          unit: "座",
          value: 12,
          type: "t8",
        },
      ],
      allDevice: {},
    };
  },
  mounted() {
    this.getPageData();
    // request.post("api/map/getPoint", { type: "csbj" });
  },
  methods: {
    async getPageData() {
      let proxyName = "api";
      let urls = [
        `${proxyName}/binhu/getComponentTypeStatistic`, // 设备部件
        `${proxyName}/binhu/getComponentStatusStatistic`, // 状态统计
      ];
      await concurRequest(urls, 5).then((res) => {
        let resultMap = res.map((item) => item.code === 200 && item.data);
        if (resultMap[0]) {
          let _componentsList = this.componentsList;
          resultMap[0].forEach((el) => {
            _componentsList.forEach((item) => {
              if (item.name.includes(el.name)) {
                item.queryKey = el.name;
                item.value = el.count;
              }
            });
          });
          this.componentsList = _componentsList;
          this.handleClickItem(
            0,
            this.componentsList[0].type,
            this.componentsList[0].queryKey
          );
        }
        // 设备状态统计
        resultMap[1] && (this.allDevice = resultMap[1]);
      });
    },
    closedWin() {
      this.visible = false;
    },
    handleClickItem(index, type, name) {
      this.activeIndex = index;
      this.setMarker(type, name);
      eventBus.$emit("handleBdcDialog", false);
    },
    // 坐标转换
    getCoord(name, lng, lat) {
      // const a = CoordTransform.BD09ToGCJ02(lng, lat);
      // const point = CoordTransform.GCJ02ToWGS84(a[0], a[1]);
      let points = [];
      // 除了i井盖厕所的其他部件  gc > 84
      if (!name.includes("路灯")) {
        let point = CoordTransform.GCJ02ToWGS84(lng, lat);
        points = CoordTransform.TransformByEPSG(4326, 4549, point[0], point[1]);
      } else {
        points = CoordTransform.TransformByEPSG(4326, 4549, lng, lat);
      }
      return [...points, 50];
    },
    async setMarker(type, name) {
      // 清除marker
      await __g?.marker.clear();

      let parkMaker = [];
      // 获取定位数据
      await request
        .post("api/map/getPoint", {
          name: name,
          type: "csbj",
        })
        .then(async ({ code, data }) => {
          if (code !== 200 || !data || data.length < 1) return;
          // let img = `../../../assets/businessDistrictComponents/${type}.png`;
          for (let i = 0; i < data.length; i++) {
            let e = data[i];
            if (isNaN(e.lng * 1) || isNaN(e.lat * 1)) continue;
            let marker = {
              id: "bdc-" + type + i,
              userData: JSON.stringify(e),
              coordinate: this.getCoord(e.name, e.lng * 1, e.lat * 1),
              imagePath: HostConfig.Path + type + ".png",
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
          // 添加点位
          await __g?.marker.add(parkMaker);
          let focus = [parkMaker[0].id];
          if (parkMaker.length > 3) {
            focus = [parkMaker[0].id, parkMaker[1].id, parkMaker[2].id];
          }
          // 聚焦点位
          await __g?.marker.focus(focus, 500);
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.components-box {
  display: flex;
  flex-wrap: wrap;
  margin: 4px 0 28px 0;

  .com-item {
    width: 210px;
    height: 96px;
    display: flex;
    margin: 4px 0;
    cursor: pointer;

    .imgbox {
      width: 96px;
      height: 96px;
      display: flex;
      align-items: center;
      justify-content: center;

      img {
        width: 38px;
        height: 38px;
      }
    }

    .infobox {
      display: flex;
      flex-direction: column;
      justify-content: center;

      .value-line {
        margin-bottom: 8px;

        span:nth-child(1) {
          font-size: 18px;
          color: #00d5ff;
          font-weight: 400;
          margin-right: 8px;
        }

        span:nth-child(2) {
          font-size: 14px;
          color: #bcd1e9;
          font-weight: 400;
        }
      }

      .infoname {
        color: #ffffff;
        font-size: 15px;
      }
    }
  }

  li:nth-child(odd) {
    margin-right: 4px;
  }

  li:nth-child(even) {
    margin-left: 4px;
  }

  .normalBg {
    background-image: url("@/assets/businessDistrictComponents/img_park_data_blue_bg.png");
  }

  .activeBg {
    background-image: url("@/assets/businessDistrictComponents/img_park_data_blue_bg_active.png");
  }
}

#deviceTypePieChart {
  width: 428px;
  height: 290px;
}
</style>
