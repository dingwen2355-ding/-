<template>
  <div :class="['cloud-tree', { switch: needSW }]">
    <div class="title">
      <el-cascader v-model="modelCascader" :options="optionsCascader" :props="{ expandTrigger: 'hover' }"
        @change="handleChangeModel" popper-class="cas_tree"></el-cascader>
    </div>
    <!--模型树、图层树-->
    <div v-show="modelName === 'modelData' || modelName === 'cimLevel' || modelName === 'region'"
      class="tree-top tree-warp">
      <!--搜索-->
      <div class="tree-search">
        <img :src="require('@/assets/images/cloud/page/tree-1.png')" alt="" srcset="" />
        <el-input type="text" placeholder="搜索图层" v-model="filterText"></el-input>
      </div>
      <el-scrollbar style="height: calc(100% - 50px)">
        <!--模型树-->
        <div v-show="modelName === 'modelData' || modelName === 'cimLevel' || modelName === 'region'">
          <el-tree ref="modeltree" :data="modelTreeData" show-checkbox node-key="id" @check="handleChecked"
            @node-click="handleNodeClick" :default-checked-keys="checkTreeIds" :props="defaultProps" default-expand-all
            :check-on-click-node="false" :expand-on-click-node="true" :filter-node-method="filterNode">
          </el-tree>
        </div>
      </el-scrollbar>
    </div>
    <!--属性信息-->
    <div v-if="(modelName === 'modelData' || modelName === 'cimLevel' || modelName === 'region') && showAttr" class="tree-footer tree-warp">
      <div class="tree-footer-warp">
        <div class="title">属性信息</div>
        <el-scrollbar style="height: calc(100% - 30px)">
          <el-collapse v-model="activeNames">
            <el-collapse-item title="Transform" name="1">
              <div class="collapse-list">
                <div class="list-title">位置</div>
                <div class="list-warp">
                  <ul>
                    <el-input-number v-model="option.translation[0]" controls-position="right"
                      @change="changeTranslation"></el-input-number>
                    <span>X</span>
                  </ul>
                  <ul>
                    <el-input-number v-model="option.translation[1]" controls-position="right"
                      @change="changeTranslation"></el-input-number>
                    <span>Y</span>
                  </ul>
                  <ul>
                    <el-input-number v-model="option.translation[2]" controls-position="right"
                      @change="changeTranslation"></el-input-number>
                    <span>Z</span>
                  </ul>
                </div>
              </div>
              <div class="collapse-list">
                <div class="list-title">旋转</div>
                <div class="list-warp">
                  <ul>
                    <el-input-number v-model="option.rotation[0]" controls-position="right" @change="changeRotation"
                      :min="0" :max="360"></el-input-number>
                    <span>X</span>
                  </ul>
                  <ul>
                    <el-input-number v-model="option.rotation[1]" controls-position="right" @change="changeRotation"
                      :min="0" :max="360"></el-input-number>
                    <span>Y</span>
                  </ul>
                  <ul>
                    <el-input-number v-model="option.rotation[2]" controls-position="right" @change="changeRotation"
                      :min="0" :max="360"></el-input-number>
                    <span>Z</span>
                  </ul>
                </div>
              </div>
              <div class="collapse-list">
                <div class="list-title">缩放</div>
                <div class="list-warp">
                  <ul>
                    <el-input-number v-model="option.scale[0]" controls-position="right" @change="changeScale" :min="0.01"
                      :max="100"></el-input-number>
                    <span>X</span>
                  </ul>
                  <ul>
                    <el-input-number v-model="option.scale[1]" controls-position="right" @change="changeScale" :min="0.01"
                      :max="100"></el-input-number>
                    <span>Y</span>
                  </ul>
                  <ul>
                    <el-input-number v-model="option.scale[2]" controls-position="right" @change="changeScale" :min="0.01"
                      :max="100"></el-input-number>
                    <span>Z</span>
                  </ul>
                </div>
              </div>
            </el-collapse-item>
            <el-collapse-item title="样式" name="2">
              <el-row :gutter="20">
                <el-col :span="12">
                  <el-select v-model="option.effect" placeholder="请选择" @change="changeStyle" popper-class="sel_tree">
                    <el-option v-for="item in effectList" :key="item.styleid" :label="item.name" :value="item.styleid">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="12">
                  <el-color-picker v-model="option.color" show-alpha @change="changeStyle"></el-color-picker>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-if="option.effect == '0'">
                <el-col :span="12">
                  <span>饱和度</span>
                </el-col>
                <el-col :span="12">
                  <el-input-number v-model="option.saturation" controls-position="right" @change="changeStyle" :min="0"
                    :max="2"></el-input-number>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-if="option.effect == '0'">
                <el-col :span="12">
                  <span>亮度</span>
                </el-col>
                <el-col :span="12">
                  <el-input-number v-model="option.brightness" controls-position="right" @change="changeStyle" :min="0.1"
                    :max="10"></el-input-number>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-if="option.effect == '0'">
                <el-col :span="12">
                  <span>对比度</span>
                </el-col>
                <el-col :span="12">
                  <el-input-number v-model="option.contrast" controls-position="right" @change="changeStyle" :min="0.2"
                    :max="5"></el-input-number>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-if="option.effect == '0'">
                <el-col :span="12">
                  <span>对比度基准</span>
                </el-col>
                <el-col :span="12">
                  <el-input-number v-model="option.contrastBase" controls-position="right" @change="changeStyle"
                    :min="0.036" :max="0.9"></el-input-number>
                </el-col>
              </el-row>
            </el-collapse-item>
          </el-collapse>
        </el-scrollbar>
      </div>
    </div>
    <!-- 空间数据 -->
    <SpatialData v-if="modelName == 'spatialdata'" ref="SpatialData" :baseData="baseData" />
    <!-- 应用模型
    <ApplicationModelTmpl v-if="modelName == 'application'" ref="application_tmpl" />-->
  </div>
</template>

<script>
import { breadthData } from "../../SealAirCityAPI";
import { get, generalPostEvent } from "@/utils/fetch";
/* import ApplicationModelTmpl from "./ApplicationModel.vue"; */
import SpatialData from "./SpatialData/SpatialData.vue";
import _ from "lodash";
import * as turf from "@turf/turf";
import { mapGetters } from 'vuex';
import { ServerType } from "@/utils/Enum"

export default {
  components: {
    /* ApplicationModelTmpl, */
    SpatialData
  },
  name: "Tree",
  data() {
    return {
      baseData: {},//空间数据
      optionsCascader: [
        {
          value: "",
          label: "模型数据",
          children: [
            {
              value: "cimLevel",
              label: "CIM等级"
            },
            {
              value: "modelData",
              label: "文件目录"
            },
            {
              value: "region",
              label: "行政区域"
            },
          ]
        },
        {
          value: "spatialdata",
          label: "基础数据"
        },
        /* {
          value: "application",
          label: "应用模型"
        }, */
      ],
      modelCascader: ["", "cimLevel"], // 默认显示CIM等级
      modelName: "cimLevel", // 默认显示CIM等级
      modelTreeData: [], // 图层树
      checkTreeIds: [], // 默认勾选的节点key
      defaultProps: {
        children: "childrens",
        label: "name",
      },
      activeNames: ["1"], // 属性信息-展开的面板
      option: {
        translation: [0, 0, 0],
        rotation: [0, 0, 0],
        scale: [1, 1, 1],
        effect: "0",
        color: "",
        // 饱和度
        saturation: 1.0,
        // 亮度 
        brightness: 1.0,
        // 对比度
        contrast: 1.0,
        // 对比度基准
        contrastBase: 0.18,
      },
      curCheckedData: null,
      showAttr: false, // 是否显示属性信息
      filterText: "",
      treeClickCount: 0, //记录点击的次数
      effectList: [
        { id: "default", name: "默认", styleid: "0" },
        { id: "xRay", name: "X光", styleid: "1" },
        { id: "pure", name: "纯色", styleid: "2" },
        { id: "Crystal", name: "水晶体", styleid: "3" },
        { id: "dark", name: "暗黑", styleid: "4" },
        { id: "science", name: "科幻", styleid: "5" },
        { id: "spreadOf", name: "扩散", styleid: "6" },
      ],
      tileLayer: [], //获取所有图层
    };
  },
  computed: {
    ...mapGetters(['sceneInfoData', 'treeData']),
    needSW() {
      return this.sceneInfoData.needSW;
    }
  },
  watch: {
    filterText(val) {
      let refname = this.modelName === "modelData" ? "tree" : "base-tree";
      this.$refs[refname].filter(val);
    },
  },
  created() {
    // 默认显示CIM等级
    this.modelTreeData = this.treeData.cimTree;
    this.baseData = this.treeData.basedataDirTree;
    window.tree = this;
  },
  mounted() {
    let that = this;
    this.initTreeData();
    setTimeout(() => {
      // that.addClipPart();
    }, 1000);
  },
  methods: {
    initTreeData() {
      // 默认选中的3dt
      let ids = this.sceneInfoData.media.attribute.checkedIds || [];
      this.checkTreeIds = ids.length > 0 ? ids : this.treeData.media3dt.map(o => { return o.id });
      // window.sealAPI._tileLayer.focus_biz(this.checkTreeIds[0]);
      // 3dt显隐
      window.sealAPI._tileLayer.show_biz(this.checkTreeIds);
      let hideIds = this.treeData.media3dt.filter(o => !this.checkTreeIds.includes(o.id)).map(o => { return o.id });
      window.sealAPI._tileLayer.hide_biz(hideIds);
    },
    handleChangeModel() {
      this.modelName = this.modelCascader.join("");

      let modelIds = this.getSelectedTreeIds();
      if (this.modelName === "modelData") {
        this.filterText = "";
        this.modelTreeData = this.treeData.dirTree;
        this.$refs.modeltree.setCheckedKeys(modelIds);
      }
      if (this.modelName === "cimLevel") {
        this.filterText = "";
        this.modelTreeData = this.treeData.cimTree;
        this.$refs.modeltree.setCheckedKeys(modelIds);
      }
      if (this.modelName === "region") {
        this.filterText = "";
        this.modelTreeData = this.treeData.areaTree;
        this.$refs.modeltree.setCheckedKeys(modelIds);
      }
    },
    changeStyle() {
      if (this.option.color && this.option.effect) {
        let color = this.option.color.slice(5);
        color = color.substr(0, color.length - 1);
        let colorArr = color.split(",");
        let parseColor = [parseInt(colorArr[0]) / 255, parseInt(colorArr[1]) / 255, parseInt(colorArr[2]) / 255, parseFloat(colorArr[3])];
        if (this.option.effect == "0") {
          window.sealAPI._tileLayer.setStyle_biz(this.curCheckedData.id, parseInt(this.option.effect), parseColor, this.option.saturation, this.option.brightness, this.option.contrast, this.option.contrastBase);
        } else {
          window.sealAPI._tileLayer.setStyle_biz(this.curCheckedData.id, parseInt(this.option.effect), parseColor);
        }
        this.updateModelOptions();
      }
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    debonceChangeTranslation: _.debounce(function () {
      // 设置平移
      window.sealAPI._tileLayer.setLocation(
        this.curCheckedData.id,
        this.option.translation
      );
      this.updateModelOptions();
    }, 1000),
    //平移
    changeTranslation(e) {
      if (!this.curCheckedData || !this.curCheckedData.id) return;
      this.debonceChangeTranslation();
    },
    debonceChangeRotation: _.debounce(function () {
      // 设置旋转
      window.sealAPI._tileLayer.setRotation(this.curCheckedData.id, this.option.rotation)
        .then(() => {
          //平移旋转问题，结束后需要进行平移
          this.changeTranslation();
        });
    }, 1000),
    //旋转
    changeRotation(e) {
      if (!this.curCheckedData || !this.curCheckedData.id) return;
      this.debonceChangeRotation();
    },
    debonceChangeScale: _.debounce(function () {
      // 设置缩放
      window.sealAPI._tileLayer.setScale(this.curCheckedData.id, this.option.scale)
        .then(() => {
          //平移旋转问题，结束后需要进行平移
          this.changeTranslation();
        });
    }, 1000),
    //缩放
    changeScale(e) {
      if (!this.curCheckedData || !this.curCheckedData.id) return;
      this.debonceChangeScale();
    },
    updateModelOptions() {
      let options = {};
      options.location_x = this.option.translation[0];
      options.location_y = this.option.translation[1];
      options.location_z = this.option.translation[2];
      options.rotation_x = this.option.rotation[0];
      options.rotation_y = this.option.rotation[1];
      options.rotation_z = this.option.rotation[2];
      options.scale_x = this.option.scale[0];
      options.scale_y = this.option.scale[1];
      options.scale_z = this.option.scale[2];

      options.saturation = this.option.saturation;
      options.brightness = this.option.brightness;
      options.contrast = this.option.contrast;
      options.contrastBase = this.option.contrastBase;

      // effectList
      let effectName = this.effectList.find(
        (o) => o.styleid == this.option.effect
      ).name;
      options.displayStyle = effectName;
      options.color = this.option.color.replace("rgba(", "");
      options.color = options.color.replace(")", "");

      this.curCheckedData.options = JSON.stringify(options);

      generalPostEvent(`${cim_server}service/3dt/update`, {
        id: this.curCheckedData.id,
        name: this.curCheckedData.name,
        remark: this.curCheckedData.remark,
        options: JSON.stringify(options),
        // topic: item.topic,
        pri: this.curCheckedData.pri
      }).then((res) => {

      });
    },
    //模型树选中事件处理
    handleChecked(data, params) {
      let _data = breadthData(data, "childrens");
      let modelIds = []
      _data.forEach(o => {
        if (o.type == ServerType["3DT"]) {
          modelIds.push(o.id);
        }
      });
      let isCheck = params.checkedKeys.includes(data.id);
      if (isCheck) {
        window.sealAPI._tileLayer.show_biz(modelIds);
      } else {
        window.sealAPI._tileLayer.hide_biz(modelIds);
      }
    },
    //label点击处理
    handleNodeClick(data, node) {
      let self = this;
      //记录点击次数
      this.treeClickCount++;
      //单次点击次数超过2次不作处理,直接返回
      if (this.treeClickCount >= 2) {
        return;
      }
      //计时器,计算300毫秒为单位,可自行修改
      window.setTimeout(() => {
        if (self.treeClickCount == 1) {
          //把次数归零
          self.treeClickCount = 0;
          //单击事件处理
          self.oneLableClick(data, node);
        } else if (self.treeClickCount > 1) {
          //把次数归零
          self.treeClickCount = 0;
          //双击事件
          self.twoLableClick(data, node);
        }
      }, 300);
    },
    //单击处理
    oneLableClick(data, node) {
      if (data.type !== ServerType["3DT"]) {
        this.curCheckedData = null;
        this.showAttr = false;
        return;
      }
      this.curCheckedData = data;
      this.showAttr = true;
      let option = JSON.parse(data.options);

      this.option.translation = [option.location_x, option.location_y, option.location_z];
      this.option.rotation = [option.rotation_x, option.rotation_y, option.rotation_z];
      this.option.scale = [option.scale_x, option.scale_y, option.scale_z];

      this.option.saturation = option.saturation;
      this.option.brightness = option.brightness;
      this.option.contrast = option.contrast;
      this.option.contrastBase = option.contrastBase;

      this.option.color = `rgba(${option.color})`;
      let style = this.effectList.find((o) => o.name == option.displayStyle);
      this.option.effect = style.styleid;
    },
    //双击处理
    twoLableClick(data, node) {
      if (data.type !== ServerType["3DT"]) {
        this.curCheckedData = null;
        this.showAttr = false;
        return;
      }
      window.sealAPI._tileLayer.focus_biz(data);
    },
    getSelectedTreeIds() {
      let modelTreeNodes = this.$refs.modeltree.getCheckedNodes();
      modelTreeNodes = modelTreeNodes.filter((o) => o.type == ServerType["3DT"]);
      let modeIds = modelTreeNodes.map((o) => {
        return o.id;
      });
      // let layerIds = layerTreeNodes.map((o) => {
      //   return o.id;
      // });
      // let ids = modeIds.concat(layerIds);
      return modeIds;
    },
    get3dtsCheckedList() {
      let checkedList = this.$refs.modeltree.getCheckedNodes();
      let _3dtsCheckedList = checkedList.filter(
        (item) => item.type === ServerType["3DT"]
      ); //遍历所有叶子节点
      return _3dtsCheckedList;
    },
    async addClipPart() {
      // let layers = this.tileLayer;
      let layers = this.treeData.media3dt;
      let data = [];
      let count = 0;
      let cimlevelboundary = this.sceneInfoData.media.attribute.boundary;
      if (JSON.stringify(cimlevelboundary) === '{}') {
        return false // 如果为空,返回false
      }
      let needHide3dtTileLayers = [];
      let needHideLevels = [5, 6, 7];
      // let needHideLevels = ["CIM5", "CIM6", "CIM7"];
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

      let res = await window.sealAPI._tileLayer.getAllFlattenInfo();
      let canClipLayers = (res.data || []).filter((o) => o.bFlattenSupported == 1);

      let ids = canClipLayers.map(o => o.id);

      window.sealAPI._tileLayer.clearHole(ids);
      layers.forEach((item) => {
        let cimlevel = item.cimlevel;
        let cimboundary = cimlevelboundary.find(o => o.id == cimlevel);
        if (cimboundary) {
          let boundary = cimboundary.boundary;
          let id = item.id;
          if (canClipLayers.find((o) => o.id == id)) {
            boundary.forEach(boud => {
              data.push({
                id: "tilehole" + count,
                tileLayerId: id,
                coordinates: boud,
                innerRings: [],
                isReverseCut: true,
              });
              count++;
            })
          } else {
            //不支持挖洞、压平的3dt  cim5\6\7 中直接隐藏3dt
            if (needHideLevels.indexOf(cimlevel) > -1) {
              needHide3dtTileLayers.push({
                tileLayerId: childlayer.id,
                boundary: boundary,
              });
            } else {
              //cim3 、4 隐藏tilelayer中的object
              //20230221  cim2 白膜不支持挖洞，需要隐藏actor
              if (cimlevel == 2) {
                cim2HideData.tilelayerIds.push(item.id);
                cim2HideData.boundary = boundary;
              } else if (cimlevel == 3) {
                cim3HideData.tilelayerIds.push(item.id);
                cim3HideData.boundary = boundary;
              } else if (cimlevel == 4) {
                // 暂时不能隐藏actor的tilelayer ，隐藏会有问题
                // if (canNotHideActors.indexOf(childlayer.id) < 0) {
                //   cim4HideData.tilelayerIds.push(childlayer.id);
                //   cim4HideData.boundary = boundary;
                // }
                cim4HideData.tilelayerIds.push(item.id);
                cim4HideData.boundary = boundary;
              }
            }
          }
        }
      });
      cim2HideData.boundary && this.hideObjectidsInBoundary(cim2HideData);
      cim3HideData.boundary && this.hideObjectidsInBoundary(cim3HideData);
      cim4HideData.boundary && this.hideObjectidsInBoundary(cim4HideData);

      // 获取TileLayer的详细信息
      let res1 = await window.sealAPI._tileLayer.get_biz(
        needHide3dtTileLayers.map((o) => {
          return o.tileLayerId;
        })
      );
      let hideIds = [];
      (res1.data || []).forEach((item) => {
        let tilelayer = needHide3dtTileLayers.find(
          (o) => o.tileLayerId == item.id
        );
        let point = turf.point([
          (item.bbox[0] + item.bbox[3]) / 2,
          (item.bbox[1] + item.bbox[4]) / 2,
        ]);

        tilelayer.boundary.forEach((boud) => {
          let polygon = turf.polygon([boud]);
          let isIntersects = turf.booleanIntersects(polygon, point);
          if (!isIntersects) hideIds.push(item.id);
        });
      });
      hideIds.length > 0 && window.sealAPI._tileLayer.hide(hideIds);

      // if (!this.sceneInfoData.media.attribute.hasClip) {
      data.forEach((item) => {
        window.sealAPI._tileLayer.addHole(item);
      });
      // this.sceneInfoData.media.attribute.hasClip = true; // SI：说明已经经过裁剪了，下次预览时，不用再次裁剪 隐藏范围外的模型
      window.RTCPlayer.saveProject();
      // }
    },
    hideObjectidsInBoundary(data) {
      let that = this;
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
        flag: 2,//2表示范围外，1表示范围内
      };
      get(`${cim_main}service/object/info/byscope`, params).then(res => {
        let result = res.message;
        let _data = [];
        for (const key in result) {
          if (Object.hasOwnProperty.call(result, key)) {
            const item = result[key];
            let tileLayer = _data.find((o) => o.id == key);
            if (tileLayer) tileLayer.objectIds.push(item);
            else _data.push({ id: key, objectIds: item });
          }
        }
        window.sealAPI._tileLayer.hideActors(_data);
      })
    },
  },
  beforeDestroy() {
    if (this.curCheckedData && this.curCheckedData.id) {
      //退出时，禁用X光效果
      //window.sealAPI._tileLayer.setStyle_biz(this.curCheckedData.id, 0, [1, 0, 0, 0.8]);
    }
  },
};
</script>

<style lang="scss" scoped>
/* select tree */
::v-deep .sel_tree {
  border: 0;
  background: rgba(46, 52, 65, 0.9);
  box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.4), 0px 0px 0px 0px rgba(0, 0, 0, 0.5), 0px 2px 5px 0px rgba(38, 38, 44, 0.5);

  .el-select-dropdown__item {
    border-color: transparent !important;
    border-radius: 2px;
    color: #ffffff;
    font-weight: 400;
    height: auto;
    line-height: 40px;

    &.selected,
    &.hover {
      background: #add6ff33;
      color: #029eff;
    }
  }

  .popper__arrow {
    border-bottom-color: transparent !important;
    border-top-color: transparent !important;

    &::after {
      border-bottom-color: rgba(46, 52, 65, 0.9) !important;
      border-top-color: rgba(46, 52, 65, 0.8) !important;
    }
  }
}
</style>
<style lang="scss" scoped>
/* tree */
.cloud-tree {
  position: absolute;
  width: 300px;
  padding: 20px 0;
  padding-bottom: 0;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  &:not(.switch) {
    top: 0;
    left: 80px;
    height: 100vh;
    background: rgba(38, 38, 41, 0.6);
    box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15), 0px 0px 0px 0px rgba(0, 0, 0, 0.5);
    border: 1px solid rgba(255, 255, 255, 0.15);
  }

  &.switch {
    top: 88px;
    left: 34px;
    height: calc(100vh - 122px);
    overflow: hidden;
    background: linear-gradient(180deg, rgba(21, 25, 37, 0.7) 0%, rgba(15, 18, 26, 0.8) 100%);
    box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15), 0px 0px 0px 0px rgba(0, 0, 0, 0.5);
    border-radius: 21px;
    border: 1px solid rgba(215, 226, 255, 0.38);

    ::v-deep .tree-footer-warp {
      .el-scrollbar {
        .el-scrollbar__wrap {
          margin-right: 0 !important;
        }
      }
    }
  }

  &>.title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 16px;
    box-sizing: border-box;
    color: white;

    ::v-deep.el-select {
      .el-input {
        .el-input__inner {
          font-size: 22px;
          font-weight: 400;
          color: #ffffff;
          line-height: 30px;
          text-shadow: 0px 2px 4px rgba(0, 0, 0, 0.3);
          border-color: transparent !important;
          background-color: transparent;
        }
      }
    }

    ::v-deep.el-cascader {
      width: 100%;

      .el-input__inner {
        font-size: 22px;
        font-weight: 400;
        color: #ffffff;
        line-height: 30px;
        text-shadow: 0px 2px 4px rgba(0, 0, 0, 0.3);
        border-color: transparent !important;
        background-color: transparent;
      }
    }
  }

  .tree-warp {
    flex: 1;
    padding: 16px;
    box-sizing: border-box;
  }

  /* tree list */
  .tree-top {
    height: 100%;
    overflow: hidden;
    border-bottom: 1px solid rgba($color: #ffffff, $alpha: 0.2);

    .tree-search {
      display: flex;
      align-items: center;
      margin-bottom: 5px;
      padding-bottom: 5px;
      border-bottom: 1px solid rgba($color: #ffffff, $alpha: 0.2);

      img {
        width: 24px;
        height: 24px;
      }

      ::v-deep.el-input {
        .el-input__inner {
          width: 100%;
          border: none;
          outline: none;
          font-size: 16px;
          color: #9ea1a7;
          padding-left: 5px;
          background: transparent;
        }

        .el-input__inner::-webkit-input-placeholder {
          font-size: 16px;
          color: #9ea1a7;
        }
      }
    }

    ::v-deep.el-scrollbar {
      box-sizing: border-box;

      .el-scrollbar__wrap {
        overflow-x: hidden;

        /* 设置滚动条的样式 */
        &::-webkit-scrollbar {
          background-color: rgba(46, 52, 65, 0.2);
        }

        /* 滚动条滑块 */
        &::-webkit-scrollbar-thumb {
          background-color: rgba(46, 52, 65, 0.4);
        }

        .el-scrollbar__view {
          .el-tree {
            background: transparent;
            color: #eeeeee;
            /* &
              > .el-tree-node
              > .el-tree-node__children
              > .el-tree-node
              > .el-tree-node__children
              > .el-tree-node {
              & > .el-tree-node__content {
                padding-left: 72px !important;
                .el-tree-node__expand-icon {
                  display: none;
                }
              }
              & > .el-tree-node__children {
                display: none;
              }
            } */
          }

          .el-tree-node:focus>.el-tree-node__content {
            background-color: rgba(0, 0, 0, 0.3);
          }

          .el-tree-node.is-current>.el-tree-node__content {
            background-color: rgba(0, 0, 0, 0.3);
          }

          /*.el-tree-node.is-current > .el-tree-node__content > .el-tree-node__label{
            color: #02b2ff;
          }*/
          .el-tree-node__content:hover {
            background: transparent !important;
          }

          .el-tree-node__expand-icon {
            color: #d8d8d8;
          }

          .el-tree-node__expand-icon.is-leaf {
            color: transparent;
          }

          .el-checkbox__inner:hover {
            border-color: transparent;
          }

          .el-checkbox__input.is-checked .el-checkbox__inner {
            /* 边框选中 */
            border-color: transparent;
            background: url("~@/assets/images/cloud/page/tree-2.png") no-repeat;
            border: none;
            background-size: 100% 100%;
          }

          .el-checkbox__input.is-indeterminate .el-checkbox__inner {
            /* 非全选半选中 */
            border-color: transparent;
            background: url("~@/assets/images/cloud/page/tree-4.png") no-repeat;
            border: none;
            background-size: 100% 100%;
          }

          .el-checkbox__inner {
            width: 24px;
            height: 24px;
            background: url("~@/assets/images/cloud/page/tree-3.png") no-repeat;
            border: none;
            background-size: 100% 100%;
          }

          .el-checkbox__inner::after {
            border-color: transparent;
          }
        }
      }
    }
  }

  /* 属性 */
  ::v-deep.tree-footer {
    height: 100%;
    overflow: hidden;

    .tree-footer-warp {
      height: 100%;
      overflow: hidden;

      .title {
        color: #ffffff;
        font-size: 20px;
        font-weight: 300;
      }

      .el-input-number {
        width: 100%;

        .el-input__inner {
          // width: 86px;
          border-radius: 4px;
          outline: none;
          height: 32px;
          border: 1px solid #646b6f;
          background: #494d52;
          padding-left: 7px;
          padding-right: 22px;
          color: rgba(255, 255, 255, 0.8);
        }

        .el-input__inner:focus {
          outline: none;
          border-radius: 4px;
          border: 1px solid;
          border-color: #02b2ff;
        }
      }

      .el-input-number__decrease,
      .el-input-number__increase {
        width: 22px;
        padding-right: 10px;
        box-sizing: border-box;
        background: transparent;
        border: none;

        i {
          font-size: 20px;
        }
      }

      .el-icon-arrow-down:before {
        content: "\e790";
      }

      .el-icon-arrow-up:before {
        content: "\e78f";
      }

      .el-scrollbar__wrap {
        overflow-x: hidden;
      }

      .el-color-picker {
        width: 100%;
      }

      .el-color-picker__trigger {
        width: 100% !important;
      }

      .el-collapse {
        border: none;

        .el-collapse-item {
          border-bottom: 1px solid rgba($color: #ffffff, $alpha: 0.2);

          .el-select {
            .el-input__inner {
              border-color: #646b6f;
              background: #494d52;
              color: rgba(255, 255, 255, 0.8);
            }
          }

          .el-collapse-item__header {
            background: transparent;
            color: #ffffff;
            border: none;
          }

          .el-collapse-item__wrap {
            background: transparent;
            border: none;

            .el-collapse-item__content {
              color: #9b9ea3;

              //   transform
              .collapse-list {
                .list-title {
                  font-size: 16px;
                  color: #9b9ea3;
                }

                .list-warp {
                  width: 100%;
                  display: flex;

                  ul {
                    flex: 1;
                    text-align: center;


                  }
                }
              }

              //   X光
              .ray {
                .el-radio__inner {
                  width: 24px;
                  height: 24px;
                  border: none;
                  background: url("~@/assets/images/cloud/page/tree-3.png");
                  background-size: cover;
                }

                .el-radio__inner::after {
                  background: transparent;
                }

                .el-radio__label {
                  color: #eeeeee;
                }

                .el-radio__input.is-checked+.el-radio__label {
                  color: #eeeeee;
                }

                .is-checked {
                  .el-radio__inner {
                    width: 24px;
                    height: 24px;
                    border: none;
                    background: url("~@/assets/images/cloud/page/tree-2.png");
                    background-size: cover;
                  }
                }
              }

              .ray-color {
                display: inline-flex;

                >span {
                  font-size: 12px;
                  height: 30px;
                  line-height: 40px;
                  padding: 10px;
                  color: #fff;
                }

                .el-input-number {
                  width: 100px;

                  .el-input__inner {
                    border-radius: 4px;
                    outline: none;
                    height: 45px;
                    color: #ffffff;
                    border: 1px solid #646b6f;
                    background: #494d52;
                    padding-left: 7px;
                    padding-right: 22px;
                  }

                  .el-input__inner:focus {
                    outline: none;
                    border-radius: 4px;
                    border: 1px solid;
                    border-color: #02b2ff;
                  }
                }

                .el-input-number.is-controls-right .el-input-number__decrease {
                  right: 1px;
                  bottom: 15px;
                  top: auto;
                  left: auto;
                }

                .el-input-number__decrease,
                .el-input-number__increase {
                  width: 22px;
                  height: 10px;
                  top: 3px;
                  padding-right: 10px;
                  box-sizing: border-box;
                  background: transparent;
                  border: none;

                  i {
                    font-size: 20px;
                  }
                }

                .el-icon-arrow-down:before {
                  content: "\e790";
                }

                .el-icon-arrow-up:before {
                  content: "\e78f";
                }
              }
            }
          }

          .el-collapse-item__arrow {
            width: 24px;
            height: 24px;
            background: url("~@/assets/images/cloud/page/tree-5.png");
            background-size: cover;
          }

          .el-collapse-item__arrow {
            transform: rotate(-90deg);
          }

          .el-collapse-item__arrow.is-active {
            transform: rotate(0deg);
          }

          .el-icon-arrow-right:before {
            content: "";
          }
        }

        .el-collapse-item:last-child {
          border: none;
        }
      }
    }
  }
}
</style>
