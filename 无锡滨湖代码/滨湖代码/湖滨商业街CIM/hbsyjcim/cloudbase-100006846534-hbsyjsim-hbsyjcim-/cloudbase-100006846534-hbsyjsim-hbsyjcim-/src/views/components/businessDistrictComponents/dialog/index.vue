<template>
  <div class="components-dialog" v-if="visible">
    <i class="close" @click="visibleDialog"></i>
    <p class="title-01" v-if="deviceType === 1">智能垃圾桶</p>
    <div class="title-01 title-dustbin" v-if="deviceType === 2">
      <div :class="activeSix === 1 ? 'active-d-bg' : ''" @click="activeSix = 1">
        智慧公厕[男]
      </div>
      <div :class="activeSix === 2 ? 'active-d-bg' : ''" @click="activeSix = 2">
        智慧公厕[女]
      </div>
    </div>
    <p class="title-01" v-if="deviceType === 3">智能井盖</p>
    <div class="container">
      <!-- 垃圾桶 -->
      <Dustbin v-if="deviceType === 1" />
      <!-- 公厕 -->
      <publicToilets v-if="deviceType === 2" :activeSix="activeSix" />
      <!-- 井盖 -->
      <cover
        v-if="deviceType === 3"
        :coverData="coverData"
        :name="bdcData.device_code"
      />
      <!-- 报警记录 -->
      <alarmRecord ref="alarmRef" />
    </div>
  </div>
</template>

<script>
import Dustbin from "./dustbin.vue"; // 智能垃圾桶
import alarmRecord from "./alarmRecord.vue"; // 报警记录
import publicToilets from "./publicToilets.vue"; // 公厕

import cover from "./cover.vue"; // 井盖
export default {
  name: "componentsDialg",
  components: { Dustbin, alarmRecord, publicToilets, cover },
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    bdcData: {
      type: Object,
    },
  },
  data() {
    return {
      activeSix: 1,
    };
  },
  computed: {
    deviceType() {
      console.log(JSON.parse(this.bdcData.UserData));
      let data = JSON.parse(this.bdcData.UserData);
      let type = "";
      let ids = this.bdcData.Id;
      if (ids.includes("bdc-t0")) type = 1; // 垃圾桶
      if (ids.includes("bdc-t1") || data.name === "井盖") type = 3; // 井盖
      if (ids.includes("bdc-t4")) type = 2; // 厕所
      return type;
    },
    coverData() {
      return JSON.parse(this.bdcData.UserData);
    },
  },
  watch: {
    bdcData(val) {
      let data = JSON.parse(val.UserData);
      if (
        data.name !== "井盖" &&
        data.status !== "在线" &&
        !data.id.includes("井盖")
      )
        return;
      let str = data.bjmc?.match(/\d+/) ? data.bjmc?.match(/\d+/)[0] : "";
      let code = data.device_code ? data.device_code : str;
      this.$nextTick(() => {
        this.$refs.alarmRef.getData(code);
      });
    },
  },
  methods: {
    visibleDialog() {
      this.$emit("update:visible", false);
    },
  },
};
</script>
<style lang="scss" scoped>
.components-dialog {
  width: 800px;
  height: 580px;
  position: fixed;
  top: 12vh;
  left: 50%;
  transform: translateX(-50%);
  background-image: url("@/assets/business-overview/img_window_bg_800.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;

  .title-01 {
    margin-left: 58px;
    margin-top: 17px;
  }

  .title-dustbin {
    display: flex;
    div {
      background-image: url("@/assets/businessDistrictComponents/img_lvhua_tab_bg.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;
      width: 100px;
      height: 24px;
      line-height: 24px;
      text-align: center;
      font-size: 14px;
      color: #ffffff;
      opacity: 0.5;
    }
    .active-d-bg {
      background-image: url("@/assets/businessDistrictComponents/img_tab_bg_left_act.png");
      opacity: 1;
    }
  }

  .container {
    margin-top: 10px;
    display: flex;
    justify-content: space-between;

    & > div {
      width: 50%;
      height: 480px;
    }
  }
}
.close {
  display: inline-block;
  width: 22px;
  height: 22px;
  background-image: url("@/assets/icon_btn_close.png");
  background-size: 100% 100%;
  position: absolute;
  right: 8px;
  top: 8px;
  cursor: pointer;
  z-index: 20;
}
</style>
