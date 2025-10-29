<template>
  <div>
    <!-- loading -->
    <!-- <loading-tmpl v-if="!showPage" ref="loading" class="loadingdiv"></loading-tmpl> -->
    <!-- player -->
    <div :id="divId" class="player"></div>
    <!-- 功能模板入口 -->
    <FunctionTmpl v-if="showPage && bizTMPL == 'default'" ref="view" :class="{ showTool: !showTool }" />
    <!-- 业务模板 -->
    <component v-if="showPage && !!bizTMPL && bizTMPL != 'default' && component" :is="component" :ref="bizFolder('ref')"
      :class="{ showTool: !showTool }" />
    <!-- 切换业务模板 -->
    <div v-if="showPage && !!scenesInfo && scenesInfo.needSW && bizTMPL !== 'default'
      " :class="['switch_business_tmpl', { showTool: !showTool }]"
      @click="handleSwitchBusinessTmpl(bizTMPL, 'switch')">
      <img src="@/assets/images/cloud/img/cutover.png" />
    </div>

    <!-- scene service download：实例申请 -->
    <InstanceAppTmpl v-if="showInstance" @handleClickButton="handleClickButton" />
  </div>
</template>

<script>
import SealAirCityAPI from "./SealAirCityAPI";
import StyleStrategy from "./showStyleList";
import api from "./manager/api/index";
import { get, generalPostEvent } from "@/utils/fetch";
import { TileLayerStyle } from "@/utils/Enum";
import { initPoint, markerWatch } from './Marker.js'

export default {
  components: {
    LoadingTmpl: () => import("@/components/loading.tmpl"),
    InstanceAppTmpl: () => import("@/components/instance-app.tmpl"),
    FunctionTmpl: () => import("./page/FunctionTmpl"),
  },
  data() {
    var randomNum = ("000000" + Math.floor(Math.random() * 999999)).slice(-6);
    return {
      myDefer: undefined,
      divId: "player" + randomNum,
      mediaid: "",
      token: "",
      showPage: false,
      bizTMPL: null, // 业务模板：default、city、community
      component: null, // 业务模板：组件
      scenesInfo: "", // 场景信息,
      // --------------------
      showTool: true,
      activeName: "", // 显示策略
      showInstance: false, // 实例申请
    };
  },
  watch: {
    ...markerWatch
  },
  computed: {
    bizFolder() {
      return function (type = "folder") {
        switch (this.bizTMPL) {
          case "city":
          case "community":
            return this.bizTMPL;
          default:
            let _type = type === "folder" ? "page" : "view";
            return _type;
        }
      };
    },
    loader() {
      if (!this.bizTMPL) return null;
      let folder = this.bizFolder();
      if (folder) return () => import(`./${folder}/index.vue`);
    },
    isInnerMapping() {
      return !!this.$store.getters.serviceMapping.innerMapping;
    }
  },
  created() {
    // set mediaid、token
    this.mediaid = this.$route.query.mediaid;
    this.token = this.$route.query.token;
    let isShare = this.$route.query.isShare;
    if (isShare) this.showTool = false;

    if (!!this.token && this.token != this.$store.getters.token) {
      this.$store.dispatch("SET_ACCOUNT_ACTION", {
        token: this.token,
      });
    }
  },
  mounted() {
    let that = this;
    window.RTCPlayer = that;
    window.addEventListener("resize", function () {
      if (window.AirCityPlayerACP) window.AirCityPlayerACP.resize();
    });

    that.initGetiid();
  },
  methods: {
    // scene service download
    handleClickButton(val, obj) {
      if (this.showInstance) {
        this.showInstance = false;
        if (val) {
          this.initGetiid();
        }
      }
    },
    //从接口中获取实例链接配置
    async initGetiid() {
      let that = this;
      let res = undefined;
      if (typeof service_proxy_address !== "undefined") {
        // scene service download
        res = await get(`${service_proxy_address}`, false);
      } else {
        let data = {
          token: this.token,
          id: this.mediaid,
        }
        res = await generalPostEvent(`${cim_server}service/media/getconfig`, data);
      }
      if (res.message) {
        let msg = res.message.errorInfo;
        if (!!msg) {
          that.$refs["loading"].loadFailed(msg);
          // scene service download
          setTimeout(() => {
            if (typeof service_proxy_address !== "undefined" && msg === "没有找到用户绑定的实例信息") {
              let elem = document.getElementById("l_text");
              let _btn = document.createElement("button");
              _btn.setAttribute("type", "button");
              _btn.setAttribute("style", "background-color: transparent;color: #fff;font-size: 16px;border: 1px solid #fff;border-radius: 4px;padding: 8px 24px 10px 24px;margin-top: 14px;cursor: pointer;");
              _btn.innerText = "实例申请";
              _btn.addEventListener("click", function () {
                that.showInstance = true;
              })
              let _br = document.createElement("br");
              elem.appendChild(_br);
              elem.appendChild(_btn);
            }
          }, 1000);
          return;
        }
        let config = res.message;
        let _config = [];
        if (that.isInnerMapping) {
          _config = [
            `${config.proxyinurl}`,
            config.airCityCloudInstanceId,
          ];
        } else {
          _config = [
            `${config.mappingproxyurl}`,
            config.airCityCloudInstanceId,
          ];
        }

        let tree = config.tree;
        // init tree nodes
        if (Object.prototype.toString.call(tree)) {
          for (let key in tree) {
            that.initNodes(tree[key]);
          }
        }
        let modelArr = [];
        that.copyData2Childrens(tree.dirTree, true, modelArr);
        that.copyData2Childrens(tree.cimTree);
        that.copyData2Childrens(tree.areaTree);
        tree.media3dt = modelArr;

        // let baseDataArr = [];
        // that.copyData2Childrens(tree.basedataDirTree, true, baseDataArr);
        // // that.baseDataArr = baseDataArr
        that.$store.dispatch("SET_TREEDATA_ACTION", tree);
        that.initPlayer(..._config);
      } else {
        that.$refs["loading"].loadFailed();
      }
    },
    // 初始化叶子节点
    initNodes(data = []) {
      for (let i = 0; i < data.length; i++) {
        let item = data[i];
        item.label = item.name;
        if (!!item.childrens && item.childrens.length > 0) this.initNodes(item.childrens);
      }
    },
    copyData2Childrens(data, copyData, array) {
      let self = this;
      if (Array.isArray(data)) {
        data.forEach(item => {
          if (item.childrens) {
            self.copyData2Childrens(item.childrens, copyData, array)
          }
          if (item.datas) {
            if (!item.childrens) {
              item.childrens = [];
            }
            if (copyData) {
              array.push(...item.datas)
            }
            item.childrens = item.childrens.concat(item.datas)
          }
        })
      } else if (Object.prototype.toString.call(data) === '[object Object]') {
        if (data.childrens) {
          self.copyData2Childrens(data.childrens, copyData, array)
        }
        if (data.datas) {
          if (!data.childrens) {
            data.childrens = [];
          }
          if (copyData) {
            array.push(...data.datas)
          }
          data.childres = data.childrens.concat(data.datas)
        }
      }
    },
    // 初始化player
    async initPlayer(cloudUrl, iid) {
      let that = this;

      // 设置全局变量：封装后   sealAPI
      window.sealAPI = new SealAirCityAPI();
      console.log("==============================sealAPI:", window.sealAPI);

      await window.sealAPI.initPlayer(iid, that.divId, cloudUrl);

      that.$refs["loading"].loadSuccess();
      window.sealAPI
        .clear()
        .then(() => {
          window.sealAPI._misc.stopPlayVideo(1);
          console.info("清除完成");
        })
        .catch((ee) => {
          console.log(ee);
        });
      setTimeout(async () => {
        //提示连接成功还是有延时websocket没真正连接成功，暂时延时处理
        that.initData();
        window.sealAPI._tileLayer.stopHighlightActor();

        $Bus.$emit("analyse-cancel-layeredG");
        window.sealAPI._settings.setEnableCameraMovingEvent(); //开启相机移动事件
      }, 1000);
    },
    // 切换业务模板
    handleSwitchBusinessTmpl(biz, opr) {
      if (opr === "switch") {
        let defaultBiz = this.scenesInfo.media.attribute.bizTMPL;
        biz = biz === "default" ? defaultBiz : "default";
      }
      this.bizTMPL = biz;
      if (this.bizTMPL != "default") {
        // 动态添加业务模板组件
        this.loader().then(() => {
          this.component = () => this.loader();
        });
      }
    },
    async initData() {
      let that = this;
      // generalPostEvent(`${cim_main}service/scene/build/tree`, {
      //   id: this.mediaid,
      // }).then((res) => {
      //   let arr = res.message
      //     .map((item) => item.child)
      //     .flat()
      //     .filter((item) => item.id)
      //     .map((item) => item.id.replace(/-/g, ""));
      //   window.sealAPI._tileLayer.hide(arr);
      // });
      let resR = await api.getone({
        token: this.token,
        id: this.mediaid,
      })
      if (!!resR && !!resR.state && resR.state.code === 10000) {
        that.showPage = true;

        let _result = resR.body.message;
        let _attr = _result.media.attribute;
        // res.body.message.media3dt.map((i) => {
        //   i.treeid = i.id.replace(/-/g, "");
        // }); // {deving} 原有
        if (_attr) {
          _attr = JSON.parse(decodeURIComponent(_attr));
          that.handleSwitchBusinessTmpl(_attr.bizTMPL);
          window.sealAPI._tileLayer.showStyle = _attr.showStyle;

          that.changeModelStyle(_attr.showStyle, _result.media3dt);
          initPoint()
          setTimeout(() => {
            // 初始化保存的视角
            if (_attr.visualAngle) {
              let resC = _attr.visualAngle;
              window.sealAPI._camera.set(resC.x, resC.y, resC.z, resC.pitch, resC.yaw);
            } else {
              let resC = {
                "command":	6,
                "timestamp":	1691560620860,
                "callbackIndex":	59,
                "result":	0,
                "resultMessage":	"OK",
                "x":	526581.198574,
                "y":	3489183.699688,
                "z":	265.231895,
                "pitch":	-25.936386,
                "yaw":	-85.461464,
                "roll":	0.000002,
                "camera":	[526581.198574, 3489183.699688, 265.231895, -25.936386, -85.461464, 0.000002]
              }
              window.sealAPI._camera.set(resC.x, resC.y, resC.z, resC.pitch, resC.yaw);
            }
          }, 0)
        }
        _result.media.attribute = _attr || "";
        _result.needSW = _attr.bizTMPL !== "default"; // 是否需要切换模板
        that.scenesInfo = _result;
        that.$store.dispatch("SET_SCENEINFO_ACTION", that.scenesInfo);
      } else that.$message.error("访问失败");
    },
    // 显示风格
    changeModelStyle(showStyle, media3dt) {
      if (showStyle == "theDefault") {
        window.sealAPI._tileLayer.updateBegin();
        media3dt.forEach(item => {
          let options = JSON.parse(item.options);
          let effect = TileLayerStyle.find(o => o.name == options.displayStyle).styleid;
          let colorArr = options.color.split(',');
          let color = [colorArr[0] / 255, colorArr[1] / 255, colorArr[2] / 255, Number(colorArr[3])];
          if (effect == "0") {
            window.sealAPI._tileLayer.setStyle(item.id, effect, color, options.saturation, options.brightness, options.contrast, options.contrastBase)
          } else {
            window.sealAPI._tileLayer.setStyle(item.id, effect, color)
          }
        })
        window.sealAPI._tileLayer.updateEnd();
      } else {
        // 显示风格（style 样式， 0：默认；1：X光；2：纯色；3：水晶体；4：暗黑；5：科幻；6：扩散）
        let styleList = StyleStrategy.showStyleList
          .filter((item) => item.label === showStyle)
          .map((item) => item.children)
          .flat()
          .filter((item) => item);
        styleList.map((item) => {
          media3dt.map((model) => {
            if (item.cimtypes.includes(model.cimtype)) {
              window.sealAPI._tileLayer.setStyle_biz(
                model.id,
                item.style,
                item.rgba
              );
            }
          });
        });
      }
    },
    // 获取图层树
    getTree(obj) {
      api
        .gettree({
          token: this.$route.query.token,
          id: this.$route.query.mediaid,
        })
        .then((res) => {
          let arr = res.body.message.map((item) => item.child).flat();
          // console.log(arr);
          if (this.activeName === "theDefault") {
            arr.forEach((item) => {
              window.sealAPI._tileLayer.setStyle_biz(item.id, 0, [255 / 255, 255 / 255, 255 / 255, 1]);
            });
          } else {
            arr.forEach((item) => {
              window.sealAPI._tileLayer.setStyle_biz(item.id, obj.style, obj.rgba);
            });
          }
        });
    },
    // default->保存
    async saveProject(checkIds) {
      const that = this;
      // 保存当前视角
      let resV = await window.sealAPI._camera.get();
      resV = [resV].map(({ x, y, z, pitch, yaw }) => ({ x, y, z, pitch, yaw }));
      this.scenesInfo.media.attribute.visualAngle = resV[0];
      // 保存选中的模型（Tree）
      this.scenesInfo.media.attribute.checkedIds = checkIds;

      let resS = await api.getupdate({
        token: this.token,
        id: this.mediaid ? this.mediaid : "",
        topic: this.scenesInfo.media.topic ? this.scenesInfo.media.topic : "",
        name: this.scenesInfo.media.name ? this.scenesInfo.media.name : "",
        attribute: JSON.stringify(this.scenesInfo.media.attribute),
      })
      if (!!resS && !!resS.state && resS.state.code === 10000) {
        that.$message.success("保存成功！");
        setTimeout(() => {
          that.$store.dispatch("SET_SCENEINFO_ACTION", that.scenesInfo);
        }, 200);
        // SaveProject
        window.origAPI.call0(225);
      } else that.$message.error("保存失败！");
    },
  },
  destroyed() {
    console.log("destroyed ==> close");
  },
};
</script>

<style lang="scss" scoped>
.showTool {
  display: none !important;
}

.loadingdiv,
.player {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
}

.switch_business_tmpl {
  position: fixed;
  z-index: 100;
  right: 100px;
  top: 26px;
  width: 78px;
  cursor: pointer;

  img {
    max-width: 100%;
  }
}
</style>