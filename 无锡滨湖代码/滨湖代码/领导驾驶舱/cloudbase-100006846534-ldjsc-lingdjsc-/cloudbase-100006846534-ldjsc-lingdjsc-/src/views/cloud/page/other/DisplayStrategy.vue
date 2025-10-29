<!--
 * @FileDescription: 场景预览 - 显示策略
 * @Author: yuanhaijun
 * @Date: 2023.04.21
 * @LastEditors: yuanhaijun
 * @LastEditTime: 2023.04.21
 -->
<template>
  <div v-show="false" class="cloud-func">
    <div class="func-title">
      显示效果
      <span @click="handleClose">
        <i class="el-icon-close"></i>
      </span>
    </div>
    <div class="func-warp">
      <ul>
        <li v-for="(item, index) in displayStyle" :class="item.label == activeName ? 'active' : ''" :key="index"
          @click="changeStyle(item.label, item.index)">
          <p>{{ item.name }}</p>
        </li>
      </ul>
    </div>
    <div class="query-line"></div>
    <div class="query-warp">
      <el-radio v-model="radio" label="shell" @click.native.prevent="rclick('shell')">全部显示</el-radio>
      <el-radio v-model="radio" label="high" @click.native.prevent="rclick('high')">优先显示高级别</el-radio>
    </div>
  </div>
</template>

<script>
import StyleStrategy from "../../showStyleList";
import { mapGetters } from 'vuex';
import { get } from "@/utils/fetch";
import { ServerType, TileLayerStyle } from "@/utils/Enum"

export default {
  name: "DisplayStrategy",
  data() {
    return {
      radio: "",
      activeName: "",
      treeId: [],
      displayStyle: [
        {
          index: 1,
          name: "默认",
          label: "theDefault",
        },
        {
          index: 2,
          name: "水晶",
          label: "crystal",
        },
        {
          index: 3,
          name: "科幻",
          label: "science",
        },
        {
          index: 4,
          name: "扩散",
          label: "spreadOf",
        },
        {
          index: 5,
          name: "纯色",
          label: "pureColor",
        },
        {
          index: 6,
          name: "X光",
          label: "xRay",
        },
        {
          index: 7,
          name: "暗黑",
          label: "dark",
        },
      ],
      addedModifierIds: [],
      hideActorData: [],//已经被隐藏的actor
    };
  },
  computed: {
    ...mapGetters(['sceneInfoData', 'treeData'])
  },
  mounted() {
    let media = this.sceneInfoData.media;
    if (media && media.attribute) {
      this.radio = media.attribute.display || "";
      this.activeName = media.attribute.showStyle;
    }
    setTimeout(() => {
      this.refreshDisplayStrategy();
    }, 200);
  },
  methods: {
    rclick(type) {
      this.radio = type;
      this.refreshDisplayStrategy();

      // SI：显示策略
      this.sceneInfoData.media.attribute.display = type;
    },
    changeStyle(label, index) {
      this.activeName = label;
      this.setStyle(label, index);
    },
    // 显示风格
    setStyle(showStyle, index) {
      /* // TODO 不生效???
      this.treeId = window.Player.$refs.tree.tileLayer.map((item) => item.child).flat();
      console.log(this.treeId);
      if (showStyle == "theDefault") {
        this.treeId.forEach((item) => {
          window.sealAPI._tileLayer.setStyle_biz(item.id, 0, [255 / 255, 255 / 255, 255 / 255, 1]);
        });
      } else {
        let styleList = StyleStrategy.showStyleList
          .filter((item) => item.label === showStyle)
          .map((item) => item.children)
          .flat()
          .filter((item) => item);
        this.treeId.forEach((item) => {
          console.log(JSON.parse(item.options));
          let style = styleList.find((o) => o.typeName == JSON.parse(item.options).typeNameOf3dt);
          console.log(style);
          if (style) {
            window.sealAPI._tileLayer.setStyle_biz(item.id, style.style, style.rgba);
          }
        });
      } */
      let media3dt = this.treeData.media3dt;
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

      // SI：显示风格
      this.sceneInfoData.media.attribute.showStyle = showStyle;
    },
    refreshDisplayStrategy() {
      //将通过等级遮盖隐藏的actor先显示
      if (this.hideActorData.length > 0) {
        this.hideActorData.forEach(item => {
          window.sealAPI._tileLayer.showActors(item);
        })
      }

      let layers = this.treeData.media3dt;
      // TODO 待优化
      let checkedNodes = window.Player.$refs.tree.$refs.modeltree.getCheckedNodes(false, true);
      //所有被选中的tilelayerid
      let tilelayers = checkedNodes.filter((o) => o.type == ServerType["3DT"]);
      // 有重复数据的cim等级数据
      let cimLevelsWithRepeat = tilelayers.map((o) => o.cimlevel);
      // 去重后的cim等级
      let cimLevels = Array.from(new Set(cimLevelsWithRepeat));
      let self = this;

      let data = [];
      let count = 0;
      let clipboundary = this.sceneInfoData.media.attribute.boundary || [];
      if (JSON.stringify(clipboundary) === '{}') {
        return false // 如果为空,返回false
      }

      if (this.addedModifierIds && this.addedModifierIds.length > 0) {
        window.sealAPI._tileLayer.clearModifier(this.addedModifierIds)
      }
      // {deving}:写死数据？是否需要根据场景定制？
      // let canNotHideActors = ["a25a9f0003ba4a6a8d6aef9749238dc3", "2c2f9dd43a4e481da07b7c96682af573", "e085ec539b094051be710d34ad37c3df", "fd182d7aed43481ca38ecb2bdd82dc0c"]; //地铁，夜景,幕墙，植被
      let cim2HideData = {
        boundary: undefined,
        tilelayerIds: [],
      };
      let cim3HideData = {
        boundary: undefined,
        tilelayerIds: [],
      };
      let cim4HideData = {
        boundary: undefined,
        tilelayerIds: [],
      };
      window.sealAPI._tileLayer.getAllFlattenInfo().then((res) => {
        let canClipLayers = (res.data || []).filter((o) => o.bFlattenSupported == 1);
        self.addedModifierIds = [];
        let needShowAllActorIds = [];
        layers.forEach((item) => {
          // let pid = item.pid.substr(0, 4);
          let cimlevel = item.cimlevel;
          let index = cimLevels.indexOf(cimlevel);
          if (index > -1) {
            let upperCimLevel = cimLevels[index + 1];
            if (upperCimLevel) {
              // let upperLevel = treeMenu.children.find((o) => o.topic == upperCimLevel);
              // let cimlevel = treeMenu.children.find((o) => o.topic == pid);

              // let boundary = clipboundary[upperLevel.name];
              let boundaryItem = clipboundary.find(o => o.id == upperCimLevel);
              let height = 0;
              if (boundaryItem) {
                let boundary = boundaryItem.boundary;
                console.log('liujingjie  item.id', item.id);

                if (canClipLayers.find((o) => o.id == item.id)) {
                  /**
                  * cim5、cim6、cim7 压盖的范围不做压平，只隐藏。
                  * 被压盖的范围中，也只有建筑会被隐藏。
                  */
                  boundary.forEach(boud => {
                    height = boud[0][2];
                    boud.forEach(_boud => {
                      _boud[2] = height + 0.001 * count;
                    })
                    if (upperCimLevel < 5) {
                      self.addedModifierIds.push(item.id);
                      data.push({
                        id: "tileModifier" + count,
                        tileLayerId: item.id,
                        // coordinates: intersectPolygon.geometry.coordinates[0],
                        coordinates: boud,
                        ententBufferSize: 0,
                      });
                      count++;
                    }
                  })
                } else {

                  //不支持挖洞、压平的3dt
                  //cim3 、4 隐藏tilelayer中的object
                  if (cimlevel == 2) {
                    cim2HideData.tilelayerIds.push(item.id);
                    cim2HideData.boundary = boundary;
                  } else if (cimlevel == 3) {
                    cim3HideData.tilelayerIds.push(item.id);
                    cim3HideData.boundary = boundary;
                  } else if (cimlevel == 4) {
                    // 暂时不能隐藏actor的tilelayer ，隐藏会有问题
                    // if (canNotHideActors.indexOf(tileLayer.id) < 0) {
                    //   cim4HideData.tilelayerIds.push(tileLayer.id);
                    //   cim4HideData.boundary = boud;
                    // }
                    cim4HideData.tilelayerIds.push(item.id);
                    cim4HideData.boundary = boundary;
                  }
                }
              }
            } else {
              console.log('liujingjie  upperCimLevel', upperCimLevel);
              // if (!canClipLayers.find((o) => o.id == item.id)) {
              //   needShowAllActorIds.push(item.id)
              // }
            }
          }
        });
        this.showAllTileLayerActors(needShowAllActorIds);
        this.hideActorData = [];
        // this.showAllTileLayerActors([...needShowAllActorIds,...cim2HideData.tilelayerIds,...cim3HideData.tilelayerIds,...cim4HideData.tilelayerIds]);
        cim2HideData.boundary && this.hideObjectidsInBoundary(cim2HideData);
        cim3HideData.boundary && this.hideObjectidsInBoundary(cim3HideData);
        cim4HideData.boundary && this.hideObjectidsInBoundary(cim4HideData);

        if (self.sceneInfoData.media.attribute.display == "high") {
          data.forEach((item) => {
            window.sealAPI._tileLayer.addModifiers(item);
          });
        }
        // this.$message.success("显示策略刷新，完成率100%")
      });
    },
    /**
     * 显示tilelayer中的所有actor
     */
    showAllTileLayerActors(ids) {
      window.sealAPI._tileLayer.showAllActors(ids);
    },
    hideObjectidsInBoundary(data) {
      let multipolygon = "";
      let array = [];
      data.boundary.forEach(item => {
        let polygonarray = [];
        item.map((o) => {
          polygonarray.push(`${o[0]} ${o[1]}`);
        });
        array.push(`((${polygonarray.join(',')}))`);
      })
      multipolygon = `MULTIPOLYGON(${array.join(',')})`;

      let params = {
        scope: multipolygon,
        tilelayerids: data.tilelayerIds.join(","),
        flag: 1,//2表示范围外，1表示范围内
      };
      let self = this;
      get(`${cim_main}service/object/info/byscope`, params).then(res => {
        let result = res.message;
        let _data = [];
        for (const key in result) {
          if (Object.hasOwnProperty.call(result, key)) {
            const item = result[key];
            let tileLayer = _data.find((o) => o.id == key);
            if (tileLayer) {
              tileLayer.objectIds.push(item);
            } else {
              _data.push({
                id: key,
                objectIds: item,
              });
            }
          }
        }
        if (self.sceneInfoData.media.attribute.display == "high") {
          self.hideActorData.push(_data);
          window.sealAPI._tileLayer.hideActors(_data);
        } else {
          window.sealAPI._tileLayer.showActors(_data);
        }
      })
    },
    handleClose() {
      if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
    }
  },
  async beforeDestroy() {
    await this.handleClose();
  },
};
</script>

<style lang="scss" scoped>
@import "~@/views/cloud/page/css/func3.scss";

.func-warp {
  ul {
    display: flex;
    flex-wrap: wrap;

    li {
      width: 64px;
      height: 64px;
      background: rgba(87, 90, 101, 0.8);
      box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15), 0px 0px 0px 0px rgba(0, 0, 0, 0.5);
      border-radius: 7px;
      border: 1px solid rgba(215, 226, 255, 0.14);
      margin-right: 8px;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;

      &:nth-child(4n) {
        margin-right: 0;
      }

      &:nth-child(1),
      &:nth-child(2),
      &:nth-child(3),
      &:nth-child(4) {
        margin-bottom: 16px;
      }


      &.active {
        border: 1px solid #47e7fb !important;
        background: #535d84 !important;
      }

      p {
        font-size: 16px;
        font-weight: 400;
        color: #eeeeee;
      }
    }
  }
}

.query-line {
  margin-left: 19px;
  margin-right: 23px;
  width: 285px;
  opacity: 0.5;
  border: 1px solid #979797;
}

.query-warp {
  padding: 15px 19px;

  /deep/.el-radio {
    display: flex;
    font-size: 16px;
    color: #ccc;
    padding: 8px 0 !important;
  }
}
</style>