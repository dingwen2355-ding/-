<template>
  <div id="layout" ref="layout">
    <div id="player"></div>
    <IocHeader ref="headerRef" />
    <router-view
      name="left"
      class="left"
      :key="$route.fullPath + 'left'"
    ></router-view>
    <router-view
      name="right"
      class="right"
      :key="$route.fullPath + 'right'"
    ></router-view>
    <IocFooter />
    <!-- <ActivityDialog :visible.sync="activityVisible" :title="title" /> -->
    <ActivityDialog />
    <OvervierDialog
      :visible.sync="overvierDialogVisible"
      :code="businessCode"
    ></OvervierDialog>
    <ParkDialog :visible.sync="parkDialogVisible" ref="parkDialogRef" />
    <VideoPop></VideoPop>
    <DetailsPop></DetailsPop>
    <DetailsPop_City></DetailsPop_City>
    <BusinessDistrictComponentsDialg
      :visible.sync="bdcVisible"
      :bdcData="bdcData"
      ref="bdcRef"
      @handleClose="bdcVisible = false"
    />
  </div>
</template>

<script>
import IocHeader from "./components/header";
import IocFooter from "./components/footer";
import OvervierDialog from "./components/businessOverview/overvierDialog.vue"; //商户总览弹窗
import ParkDialog from "./components/parkingSupervision/parkDialog.vue"; // 停车监督弹窗

import ActivityDialog from "./components/partyBuilding/activityDialog"; // 智慧党建弹窗
import VideoPop from "./SecurityoniMtoring/VideoPop"; //安防监控弹窗
import DetailsPop from "./BusinessCircleEvent/DetailsPop"; //商圈事件弹窗 井盖
import DetailsPop_City from "./BusinessCircleEvent/DetailsPop_City"; //商圈事件弹窗 城市

import BusinessDistrictComponentsDialg from "./components/businessDistrictComponents/dialog/index.vue"; // 商圈部件
import eventBus from "@/utils/eventBus";

export default {
  name: "IocLayout",
  components: {
    IocHeader,
    IocFooter,
    ActivityDialog,
    OvervierDialog,
    ParkDialog,
    VideoPop,
    DetailsPop,
    DetailsPop_City,
    BusinessDistrictComponentsDialg,
  },
  data() {
    return {
      // 三维
      player: null,
      // 活动弹框
      // activityVisible: false,
      // 商户总览弹窗
      overvierDialogVisible: false,
      // 商户名
      businessName: null,
      businessCode: null,
      // 居委会名称
      // title: null,
      // 停车监督数据
      parkDialogVisible: false,
      // 商圈部件弹框
      bdcVisible: false,
      bdcData: {},
    };
  },
  watch: {
    async $route(to, from) {
      if (to.path === from.path) return;
      // 关闭弹框
      this.parkDialogVisible = false;
      this.overvierDialogVisible = false;
      eventBus.$emit("handleBdcDialog", false);
    },
  },
  created() {
    // 监督商圈部件切换部件时关闭部件弹框
    eventBus.$on("handleBdcDialog", (flag, data) => {
      this.bdcVisible = flag;
      if (data) this.bdcData = data;
    });
    // this.$nexTick(() => {
    //   __g?.reset();
    // });
  },
  mounted() {
    // 初始化三维地图
    this.player = new acapi.AirCityPlayer(
      HostConfig.PlayerMapping || HostConfig.Player,
      {
        domId: "player",
        apiOptions: {
          onEvent: this._onEvent,
          onReady: async () => {
            console.log("--工程加载完成");
            await __g.reset();
            await __g.camera.set([
              525791.608633, 3490318.575625, 121.722373, -44.999851, -81.767303,
              0,
            ]);
            await __g.misc.setMainUIVisibility(false);
            await this.$refs.headerRef.setDay();
          },
        },
      }
    ).getAPI();

    // 绑定商户总览右侧列表触发弹窗事件
    this.$eventBus.$on("showOverDialog", (code) => {
      this.businessCode = code;
      this.overvierDialogVisible = true;
    });
    this.$eventBus.$on("closeOverDialog", () => {
      this.overvierDialogVisible = false;
    });
  },
  methods: {
    _onEvent(e) {
      console.log(e);
      if (e.Type === "marker") {
        let cover = e.Id.includes("bdc-t0"); // 井盖
        let dustbin = e.Id.includes("bdc-t1"); // 垃圾桶
        let publicToliets = e.Id.includes("bdc-t4"); // 公厕

        if (e.Id.includes("park")) {
          // 停车监督
          this.parkDialogVisible = true;
          this.$refs.parkDialogRef.getParkData(e.UserData);
        } else if (e.Id.includes("bdc-t")) {
          if (cover || dustbin || publicToliets) {
            this.bdcVisible = true;
            this.bdcData = e;
          }
        } else if (e.Id.includes("business")) {
          // 商户总览弹窗
          this.businessCode = JSON.parse(e.UserData).code;
          this.overvierDialogVisible = true;
          // this.businessName = JSON.parse(e.UserData).name;
        } else if (e.Id.includes("fireFighting") || e.Id.includes("refuge")) {
          // 应急消防
          return;
        } else if (e.Id.includes("video")) {
          //安防监控弹窗
          let idArr = e.Id.split("_");
          let id = idArr[1];
          const model = this.findComponentDownward(this, "VideoPop");
          model.getVideoUrl(id);
          model.name = JSON.parse(e.UserData).name;
          model.videoShow = true;
        } else if (e.Id.includes("warnning")) {
          //商圈事件 井盖弹窗
          const model = this.findComponentDownward(this, "DetailsPop");
          let item = JSON.parse(e.UserData).item;
          model.Data = item;
          model.detailsShow = true;
        } else if (e.Id.includes("circle")) {
          //商圈事件 城市弹窗
          const model_City = this.findComponentDownward(
            this,
            "DetailsPop_City"
          );
          let item = JSON.parse(e.UserData).item;
          model_City.Data = item;
          model_City.detailsShow = true;
        } else {
          // 智慧党建弹窗
          // this.activityVisible = true;
          // this.title = JSON.parse(e.UserData).name;
          const model = this.findComponentDownward(this, "ActivityDialog");
          model.activityDialogShow = true;
          model.name = JSON.parse(e.UserData).name;
          model.getPartyOrgDetail(model.name);
        }
      }
    },
  },
  destroyed() {
    // 三维地图销毁
    this.player.destroy();
  },
};
</script>

<style>
#layout {
  width: 100%;
  height: 100%;
  color: #fff;
  /* background-color: rgba(0, 0, 0, 0.685); */
  position: relative;
}

#player {
  width: 100%;
  height: 100%;
  position: absolute;
  /* top: 0%; */
  z-index: 0;
}

.left {
  position: absolute;
  top: 9%;
  left: 0;
  padding: 16px;
  box-sizing: border-box;
}

.right {
  position: absolute;
  top: 9%;
  right: 0;
  padding: 16px;
  box-sizing: border-box;
}
</style>
