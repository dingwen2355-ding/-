<template>
  <div>
    <div class="show-time">
      <h1>{{ currntTime }}</h1>
    </div>
    <div class="header">
      <p>数字孪生-湖滨商业街</p>
    </div>
    <div class="header-weather">
      <div class="active-box" @click="visibel = !visibel">
        <img :src="activeWeather.url" alt="" />
        <span>{{ activeWeather.label }}</span>
        <img
          src="../../../assets/header/icon_dropmenu_down.png"
          class="drop-icon"
          alt=""
        />
      </div>
      <div class="drop-down-box" v-show="visibel">
        <div
          class="item"
          :class="activeWeather.id === item.id ? 'd-active' : ''"
          v-for="(item, index) in weatherOption"
          :key="index"
          @click="handleClickWrather(item)"
        >
          <img :src="item.url" alt="" />
          <span>{{ item.label }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCurrentTime } from "@/utils/util";
import eventBus from "@/utils/eventBus";

// eslint-disable-next-line no-unused-vars
let timer = null;
// eslint-disable-next-line no-unused-vars
let autoSwitchDay = null;
export default {
  name: "IocHeader",
  data() {
    return {
      visibel: false,
      weatherOption: [
        {
          label: "白天",
          url: require("../../../assets/header/icon_head_model_selet_sun.png"),
          id: 1,
          fn: "day",
        },
        {
          label: "夜晚",
          url: require("../../../assets/header/icon_head_model_selet_moon.png"),
          id: 2,
          fn: "night",
        },
        {
          label: "雨天",
          url: require("../../../assets/header/icon_head_model_selet_raining.png"),
          id: 3,
          fn: "crainyDay",
        },
        {
          label: "雪天",
          url: require("../../../assets/header/icon_head_model_selet_snow.png"),
          id: 4,
          fn: "snowDay",
        },
      ],
      activeWeather: {
        label: "白天",
        id: 1,
        url: require("../../../assets/header/icon_head_model_selet_sun.png"),
        fn: "day",
      },
      isRainy: false,
      currntTime: "",
    };
  },
  watch: {
    $route(to, form) {
      if (form.path === "/pipeNetwork") this.setDay();
      if (to.path == "/pipeNetwork") {
        autoSwitchDay = null;
      }
    },
  },
  destroy() {
    timer = null;
  },
  created() {
    eventBus.$on("handlePipeShow", (flag) => {
      console.log(flag, "-----");
      this.handlePipe(flag);
    });

    // 添加定时器自动设置白天黑夜
    if (this.$route.path !== "/pipeNetwork") {
      // 10分钟查询一次当前时间
      autoSwitchDay = setInterval(() => {
        this.setDay();
      }, 600000);
    }
  },
  mounted() {
    timer = setInterval(() => {
      this.currntTime = getCurrentTime();
    }, 1000);
    // this.$nextTick(() => {
    //   this.day();
    // });
    // this.setDay();
  },
  methods: {
    setDay() {
      let hour = new Date().getHours();
      let item = {};
      if (hour > 6 && hour < 18) {
        // 白天
        item = this.weatherOption[0];
      } else {
        // 黑夜
        item = this.weatherOption[1];
      }
      this.activeWeather = item;
      this[item.fn]();
    },
    // 白天
    day() {
      __g.weather.disableRainSnow();
      __g.weather.setDateTime(2020, 9, 9, 11, 8, false);
      __g.weather.setCloudThickness(8);
      __g.weather.setCloudDensity(0.65);
      // __g.infoTree.hide(["BCE83CA3479608908DE93D99C03D0C5F"]);
    },

    // 黑夜
    night() {
      __g.weather.disableRainSnow();
      __g.weather.setDateTime(2020, 9, 9, 0, 8, false);
      // __g.infoTree.show(["BCE83CA3479608908DE93D99C03D0C5F"]);
      setTimeout(() => {
        __g.weather.setDateTime(2020, 9, 9, 1, 8, false);
      }, 1000);
    },
    // 雨天
    crainyDay() {
      if (this.isRainy) {
        this.isRainy = false;
        __g.weather.disableRainSnow();
      } else {
        this.isRainy = true;
        __g.weather.setDateTime(2020, 9, 9, 11, 8, false);
        __g.weather.setCloudDensity(0.2);
        __g.weather.setCloudThickness(5);
        __g.weather.setRainParam(0.3, 0.2, 0.2);
      }
    },
    // 雪天
    snowDay() {
      // 清除雨雪
      __g.weather.disableRainSnow();
      // 设置雪天
      __g.weather.setSnowParam(0.1, 1, 1);
    },
    handleClickWrather(item) {
      this.activeWeather = item;
      this.visibel = !this.visibel;
      this[item.fn]();
    },
    handlePipe(flag) {
      let obj;
      if (flag) {
        obj = this.weatherOption[1];
      } else {
        obj = this.weatherOption[0];
      }
      this.visibel = !this.visibel;
      this.handleClickWrather(obj);
    },
  },
};
</script>

<style lang="scss" scoped>
.header {
  position: fixed;
  left: 50%;
  top: 0;
  transform: translateX(-50%);
  // width: 1136px;
  width: 11.36rem;
  // height: 66px;
  height: 0.66rem;
  line-height: 0.66rem;
  background-image: url("../../../assets/header/img_head_title_bg@2x.png");
  background-size: 100% 100%;
  background-position: top center;
  background-repeat: no-repeat;
  text-align: center;
  // font-size: 32px;
  font-size: 0.32rem;
  font-weight: 600;
}

.show-time {
  position: fixed;
  left: 30px;
  top: 24px;
  z-index: 2;
}
.header-weather {
  position: fixed;
  right: 30px;
  top: 24px;
  z-index: 2;
  .active-box {
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50px;
    background: #1a212333;
    color: #fff;
    font-size: 16px;
    height: 40px;
    width: 126px;
    img {
      width: 24px;
      height: 24px;
      margin-right: 6px;
    }
    .drop-icon {
      width: 16px;
      height: 16px;
      margin-left: 6px;
    }
  }

  .drop-down-box {
    position: relative;
    top: 4px;
    color: #fff;
    font-size: 14px;

    .item {
      width: 126px;
      height: 40px;
      background: rgba(26, 33, 35, 0.8);
      display: flex;
      align-items: center;
      justify-content: center;
      img {
        width: 16px;
        height: 16px;
        margin-right: 10px;
      }
    }
    .d-active {
      background: linear-gradient(0deg, #1a2123cc 0%, #1a2123cc 100%), #ffffff03;
      background-image: url("../../../assets/header/img_head_model_box_sun_bg.png");
      background-repeat: no-repeat;
    }
  }
}
</style>
