<template>
  <div class="application-model">
    <div class="tree-top tree-warp">
      <!--搜索-->
      <div class="tree-search">
        <img :src="require('@/assets/images/cloud/page/tree-1.png')" alt="" srcset="" />
        <el-input type="text" placeholder="搜索应用模型" v-model="filterText"></el-input>
      </div>
      <el-scrollbar style="height: calc(100% - 90px)" :class="{ switch: $store.getters.sceneInfoData.needSW }">
        <!--添加3dt-->
        <div v-if="operate === 'init' && amList.length === 0" class="add3dt" @click="handleClickAdd3dt">
          <div>
            <i class="el-icon-plus"></i>
          </div>
          <p>在场景中添加3dt数据<br />查看您的方案</p>
        </div>
        <!--3dt列表-->
        <div v-if="operate === 'init' && amList.length > 0" class="list_3dt">
          <div class="add" @click="handleClickAdd3dt">
            <i class="el-icon-plus"></i> 添加3dt
          </div>
          <div class="item_3dt" v-for="(row, index) in amList" :key="index" v-show="filterKeyword(row.name)">
            <el-row>
              <el-col :span="22">
                {{ row.name }}
                <span v-if="row.state === 'uploading'" class="upload">
                  <template v-if="row.skd_state === 'pause'">停止中 <i class="el-icon-video-pause"></i></template>
                  <template v-else>上传中 <i class="el-icon-loading"></i></template>
                </span>
              </el-col>
              <el-col :span="2">
                <el-dropdown trigger="click">
                  <i class="el-icon-more"></i>
                  <el-dropdown-menu v-if="row.state === 'uploading'" slot="dropdown" class="el_dropdown">
                    <el-dropdown-item v-if="row.skd_state === 'pause'"
                      @click.native="pauseEvent(row, 'start')">上传</el-dropdown-item>
                    <el-dropdown-item v-else @click.native="pauseEvent(row, 'stop')">暂停</el-dropdown-item>
                    <el-dropdown-item @click.native="delete2Event(row)">删除</el-dropdown-item>
                  </el-dropdown-menu>
                  <el-dropdown-menu v-else slot="dropdown" class="el_dropdown">
                    <el-dropdown-item @click.native="applicationEvent(row)">应用场景</el-dropdown-item>
                    <el-dropdown-item @click.native="copyEvent(row)">复制到待更新模型</el-dropdown-item>
                    <el-dropdown-item @click.native="deleteEvent(row)">删除</el-dropdown-item>
                    <el-dropdown-item v-if="!!row.fid" @click.native="cancelEvent(row)">取消应用</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-col>
            </el-row>
            <!--<div v-if="row.state === 'uploading'" class="schedule">
              <el-progress
                color="#019bff"
                :percentage="
                  parseFloat(((row.end * 100) / row.fileSize).toFixed(2))
                "
              ></el-progress>
              <span>{{ row | getSpeed }}</span>
            </div>-->
          </div>
        </div>
        <!--应用场景-->
        <div v-if="operate === 'application'" class="application_scenario">
          <el-form label-position="top" label-width="80px" :model="asModel" :rules="asRules" ref="asForm">
            <!--参数设置-->
            <el-form-item>
              <el-collapse v-model="defaultCollapse">
                <el-collapse-item title="Transform" name="1">
                  <p class="data-title">
                    位置<i class="el-icon-location-information" :class="locateActive ? 'locateActive' : ''"
                      @click="handleClickLoc"></i>
                  </p>
                  <el-row :gutter="20">
                    <el-col :span="8">
                      <el-form-item prop="location_x">
                        <el-input v-model.number="asModel.location_x"></el-input><span>X</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="location_y">
                        <el-input v-model.number="asModel.location_y"></el-input><span>Y</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="location_z">
                        <el-input v-model.number="asModel.location_z"></el-input><span>Z</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <p class="data-title">旋转</p>
                  <el-row :gutter="20">
                    <el-col :span="8">
                      <el-form-item prop="rotation_x">
                        <el-input v-model.number="asModel.rotation_x"></el-input><span>X</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="rotation_y">
                        <el-input v-model.number="asModel.rotation_y"></el-input><span>Y</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="rotation_z">
                        <el-input v-model.number="asModel.rotation_z"></el-input><span>Z</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <p class="data-title">缩放</p>
                  <el-row :gutter="20">
                    <el-col :span="8">
                      <el-form-item prop="scale_x">
                        <el-input v-model.number="asModel.scale_x"></el-input><span>X</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="scale_y">
                        <el-input v-model.number="asModel.scale_y"></el-input><span>Y</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="scale_z">
                        <el-input v-model.number="asModel.scale_z"></el-input><span>Z</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-collapse-item>
              </el-collapse>
            </el-form-item>
            <!--方案备注-->
            <el-form-item prop="remark">
              <el-collapse>
                <el-collapse-item title="方案备注" name="3">
                  <el-input type="textarea" :rows="3" v-model="asModel.remark"></el-input>
                </el-collapse-item>
              </el-collapse>
            </el-form-item>
            <!--效果-->
            <el-form-item prop="effect">
              <el-collapse>
                <el-collapse-item title="效果" name="4">
                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-select v-model="asModel.effect" placeholder="请选择" @change="effectTypeChange">
                        <el-option v-for="item in effectList" :key="item.styleid" :label="item.name"
                          :value="item.styleid">
                        </el-option>
                      </el-select>
                    </el-col>
                    <el-col :span="12">
                      <el-color-picker v-model="asModel.color" show-alpha @change="effectColorChange"></el-color-picker>
                    </el-col>
                  </el-row>
                </el-collapse-item>
              </el-collapse>
            </el-form-item>
            <el-form-item class="form-footer">
              <el-button @click="handleClickCancelApp">关闭</el-button>
              <el-button type="primary" @click="handleClickSubmitApp">确定应用</el-button>
            </el-form-item>
          </el-form>
        </div>
        <!--上传3dt-->
        <div v-if="operate === 'upload3dt'" class="upload_3dt">
          <el-form label-position="top" label-width="80px" :model="dtModel" :rules="dtRules" ref="dtForm">
            <el-form-item prop="dataFile">
              <el-upload ref="upload-3DT" class="upload-demo" action="#" accept=".3dt,.zip,.tar,.7z"
                :on-change="handleChange" :on-remove="handleRemove" multiple :file-list="updataFileList"
                :http-request="defaultUp">
                <div class="upload" v-if="updataFileShow">
                  <i class="el-icon-plus upload-icon"></i>
                  <p>支持3dt、zip、tar、7z</p>
                  <h4>从电脑中选择数据</h4>
                </div>
              </el-upload>
            </el-form-item>
            <el-form-item label="3dt名称" prop="name" v-if="updataFileList.length <= 1">
              <el-input v-model="dtModel.name"></el-input>
            </el-form-item>
            <el-form-item label="字符集类型" prop="character">
              <el-select v-model="dtModel.character" placeholder="请选择" popper-class="el_sel">
                <el-option v-for="item in optionsChar" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <!--参数设置-->
            <el-form-item>
              <el-collapse>
                <el-collapse-item title="Transform" name="1">
                  <p class="data-title">
                    位置<i class="el-icon-location-information" :class="locateActive ? 'locateActive' : ''"
                      @click="handleClickLoc"></i>
                  </p>
                  <el-row :gutter="20">
                    <el-col :span="8">
                      <el-form-item prop="location_x">
                        <el-input v-model.number="dtModel.location_x"></el-input><span>X</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="location_y">
                        <el-input v-model.number="dtModel.location_y"></el-input><span>Y</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="location_z">
                        <el-input v-model.number="dtModel.location_z"></el-input><span>Z</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <p class="data-title">旋转</p>
                  <el-row :gutter="20">
                    <el-col :span="8">
                      <el-form-item prop="rotation_x">
                        <el-input v-model.number="dtModel.rotation_x"></el-input><span>X</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="rotation_y">
                        <el-input v-model.number="dtModel.rotation_z"></el-input><span>Y</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="rotation_z">
                        <el-input v-model.number="dtModel.rotation_y"></el-input><span>Z</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <p class="data-title">缩放</p>
                  <el-row :gutter="20">
                    <el-col :span="8">
                      <el-form-item prop="scale_x">
                        <el-input v-model.number="dtModel.scale_x"></el-input><span>X</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="scale_y">
                        <el-input v-model.number="dtModel.scale_y"></el-input><span>Y</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item prop="scale_z">
                        <el-input v-model.number="dtModel.scale_z"></el-input><span>Z</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-collapse-item>
              </el-collapse>
            </el-form-item>
            <el-form-item class="form-footer">
              <el-button @click="handleClickCancel">取消</el-button>
              <el-button type="primary" @click="handleClickUpload">上传</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-scrollbar>
    </div>
  </div>
</template>
    
<script>
import { generalPostEvent } from "@/utils/fetch";
import { mapGetters } from "vuex";
export default {
  name: "ApplicationModel",
  data() {
    return {
      topic: "60dfc29a-13cc-44be-a982-3dc5a4868d9d",
      filterText: "",
      operate: "", // 操作
      list3dt: [],
      uploadList: [], // 正在上传
      locateActive: false, //是否开始定位
      asModel: {
        remark: "",
        effect: "0",
        location_x: 0,
        location_y: 0,
        location_z: 0,
        rotation_x: 0,
        rotation_y: 0,
        rotation_z: 0,
        scale_x: 1,
        scale_y: 1,
        scale_z: 1,
        color: "",
      }, // 应用场景
      asRules: {
        location_x: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        location_y: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        location_z: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        rotation_x: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        rotation_y: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        rotation_z: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        scale_x: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        scale_y: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        scale_z: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
      },
      effectList: [
        { id: "default", name: "默认", styleid: "0" },
        { id: "xRay", name: "X光", styleid: "1" },
        { id: "pure", name: "纯色", styleid: "2" },
        { id: "Crystal", name: "水晶体", styleid: "3" },
        { id: "dark", name: "暗黑", styleid: "4" },
        { id: "science", name: "科幻", styleid: "5" },
        { id: "spreadOf", name: "扩散", styleid: "6" },
      ],
      defaultCollapse: ["1"],
      dtModel: {
        filename: "",
        filesize: "",
        dataFile: null,
        name: "",
        character: "UTF-8",
        location_x: 0,
        location_y: 0,
        location_z: 0,
        rotation_x: 0,
        rotation_y: 0,
        rotation_z: 0,
        scale_x: 1,
        scale_y: 1,
        scale_z: 1,
        type: 21,
      }, // 上传3dt
      dtRules: {
        name: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "长度在 1 到 50 个字符",
            trigger: "blur",
          },
        ],
        character: [
          { required: true, message: "请选择字符集类型", trigger: "change" },
        ],
        dataFile: [{ required: true, message: "请添加附件", trigger: "blur" }],
        location_x: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        location_y: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        location_z: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        rotation_x: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        rotation_y: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        rotation_z: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        scale_x: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        scale_y: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
        scale_z: [
          { required: true, message: "不能为空", trigger: "blur" },
          { type: "number", message: "必须为数字值" },
        ],
      },
      optionsChar: [
        {
          value: "UTF-8",
          label: "UTF-8",
        },
        {
          value: "GB2312",
          label: "GB2312",
        },
      ],
      updataFileList: [],
      updataFileShow: true,
      currentApp: null, // 当前应用场景
      lmbc_coordinate: null, // Event: LeftMouseButtonClick
    };
  },
  computed: {
    ...mapGetters(["uploadtoken", "eventSealAPI"]),
    amList() {
      return [...this.list3dt, ...this.uploadList];
    },
  },
  watch: {
		eventSealAPI: {
			indeterminate: true,
			deep: true,
			handler(val) {
        debugger
				if (val.eventtype === "LeftMouseButtonClick") {
					this.onEventMeasurement(val);
				}
			},
		},
    "$parent.modelName"(val, old) {
      if (val === "application") {
        this.filterText = "";
        this.operate = "init";
      }
    },
  },
  created() {
    this.get3dtList();
  },
  methods: {
    // 3dt列表
    get3dtList() {
      generalPostEvent(`${cim_server}service/3dt/list`, {
        topic: `${this.topic}`,
      }).then((res) => {
        this.list3dt = res.message.list;
        this.operate = "init";
      });
    },
    filterKeyword(val) {
      return val.indexOf(this.filterText) != -1;
    },
    // 暂停/上传
    pauseEvent(data, str) {
      let _list = this.uploadList.filter((item) => item.random === data.random);
      if (str === "stop") {
        let index = this.getIndex(data, "upload");
        this.GLOBAL.stop(index).then((res) => {
          this.$set(_list[0], "skd_state", "pause");
        });
      } else {
        let index = this.getIndex(data, "pause");
        this.GLOBAL.start(index).then((res) => {
          this.$set(_list[0], "skd_state", "upload");
        });
      }
    },
    // 删除正在上传的3dt
    delete2Event(data) {
      if (data.skd_state === "pause") {
        let index = this.getIndex(data, "pause");
        this.GLOBAL.deleteStopFile(index).then(() => {
          this.delUploadList(data.fid);
        });
      } else {
        let index = this.getIndex(data, "upload");
        this.GLOBAL.deleteRunFile(index).then(() => {
          this.delUploadList(data.fid);
        });
      }
    },
    getIndex(data, str) {
      return this.uploadList
        .filter((item) => item.skd_state === str)
        .map((item) => item.random)
        .indexOf(data.random);
    },
    // 应用场景
    applicationEvent(data) {
      // set data
      this.currentApp = data;
      let options = JSON.parse(data.options);
      this.asModel.location_x = options.location_x;
      this.asModel.location_y = options.location_y;
      this.asModel.location_z = options.location_z;
      this.asModel.rotation_x = options.rotation_x;
      this.asModel.rotation_y = options.rotation_y;
      this.asModel.rotation_z = options.rotation_z;
      this.asModel.scale_x = options.scale_x;
      this.asModel.scale_y = options.scale_y;
      this.asModel.scale_z = options.scale_z;
      this.asModel.effect = options.showStyle || "0";
      this.asModel.remark = this.currentApp.remark || "";
      this.operate = "application";
    },
    // 取消应用
    cancelEvent(data) {
      if (data.fid) {
        window.sealAPI._tileLayer.delete(data.fid);
        data.fid = null;
        this.$forceUpdate();
      }
      this.operate = "init";
    },
    // 3dt复制到待更新模型
    copyEvent(data) {
      let res = JSON.parse(data.options);
      if (!this.asModel.color) {
        res.color = "255,255,255,1";
        res.displayStyle = "默认";
      } else {
        if (!res.showStyle) {
          res.displayStyle = "默认";
        } else {
          if (res.showStyle == 1) {
            res.displayStyle = "X光";
          }
          if (res.showStyle == 2) {
            res.displayStyle = "纯色";
          }
          if (res.showStyle == 3) {
            res.displayStyle = "水晶体";
          }
          if (res.showStyle == 4) {
            res.displayStyle = "暗黑";
          }
          if (res.showStyle == 5) {
            res.displayStyle = "科幻";
          }
          if (res.showStyle == 6) {
            res.displayStyle = "扩散";
          }
          let color = this.asModel.color.slice(5);
          color = color.substr(0, color.length - 1);
          res.color = color;
        }
      }
      delete res.showStyle;
      res = JSON.stringify(res)
      console.log(res);
      generalPostEvent(`${cim_main}/service/model/copy`, {
        id: data.id,
        name: data.name,
        remark: data.remark,
        options: res,
        topic: "3b0127cd-1b94-4c29-8f5f-737aa672b658",
      }).then((res) => {
        this.$message({
          message: "已复制",
          type: "success",
        });
      });
    },
    // 删除3dt
    deleteEvent(data) {
      const h = this.$createElement;
      this.$msgbox({
        title: "删除3dt",
        message: h("div", null, [
          h("p", { style: "color: #65708A;" }, "您确定删除这个3dt吗？"),
          h(
            "p",
            { style: "color: #FD3B31; margin-top: 10px;" },
            "1、删除后，参与场景组装的内容将会缺相关部分"
          ),
          h(
            "p",
            { style: "color: #FD3B31;" },
            "2、删除后所有内容将无法找回，请谨慎操作"
          ),
        ]),
        showCancelButton: true,
        confirmButtonText: "确认",
        cancelButtonText: "取消",
      }).then((action) => {
        // 取消应用
        if (data.fid) {
          window.sealAPI._tileLayer.delete(data.fid);
        }
        generalPostEvent(`${cim_server}service/3dt/delete`, {
          id: `${data.id}`,
        }).then((res) => {
          this.$message.success("删除成功！");
          this.list3dt.splice(
            this.list3dt.findIndex((item) => item.id === data.id),
            1
          );
        });
      });
    },
    // 添加3dt数据
    handleClickAdd3dt() {
      this.operate = "upload3dt";
    },
    // 取消应用
    handleClickCancelApp() {
      this.locateActive = false;
      this.list3dt.filter((item) => {
        if (!!this.currentApp.fid && item.id === this.currentApp.id) {
          item.fid = this.currentApp.fid;
          this.$forceUpdate();
        }
      });
      this.currentApp = null;
      this.$refs.asForm.resetFields();
      this.operate = "init";
    },
    // 确定应用
    handleClickSubmitApp() {
      console.log(this.asModel);
      this.locateActive = false;
      this.update3dt();
    },
    effectColorChange() {
      this.update3dt();
    },
    effectTypeChange() {
      this.update3dt();
    },
    //更新3dt位置及效果
    update3dt() {
      let params = {
        fileName:
          this.currentApp.file.replace(/\//g, "\\") + this.currentApp.indexfile,
        location: [
          this.asModel.location_x,
          this.asModel.location_y,
          this.asModel.location_z,
        ],
        rotation: [
          this.asModel.rotation_x,
          this.asModel.rotation_y,
          this.asModel.rotation_z,
        ],
        scale: [
          this.asModel.scale_x,
          this.asModel.scale_y,
          this.asModel.scale_z,
        ],
      };
      if (!!this.currentApp.fid) {
        window.sealAPI._tileLayer.delete(this.currentApp.fid);
        params.id = this.currentApp.fid;
        /* // 更新位置
        window.sealAPI._tileLayer.setLocation(this.currentApp.fid, params.location);
        window.sealAPI._tileLayer.setRotation(this.currentApp.fid, params.rotation);
        window.sealAPI._tileLayer.setScale(this.currentApp.fid, params.scale);
        window.sealAPI._tileLayer.focus(this.currentApp.fid); */
      } else {
        // 3dt添加到场景
        let ran = Math.random().toString();
        this.currentApp.fid = ran;
        params.id = ran;
      }
      window.sealAPI._tileLayer.add(
        new TileLayerData(
          params.id,
          params.fileName,
          params.location,
          params.rotation,
          params.scale
        )
      );
      if (this.asModel.color && this.asModel.effect) {
        let color = this.asModel.color.slice(5);
        color = color.substr(0, color.length - 1);
        let colorArr = color.split(",");
        let style = [
          parseInt(colorArr[0]) / 255,
          parseInt(colorArr[1]) / 255,
          parseInt(colorArr[2]) / 255,
          parseFloat(colorArr[3]),
        ];
        window.sealAPI._tileLayer.setStyle_biz(params.id, parseInt(this.asModel.effect), style);
      }
      // window.sealAPI._tileLayer.focus(params.id);
      // update data
      let options = JSON.parse(this.currentApp.options);
      options.location_x = this.asModel.location_x;
      options.location_y = this.asModel.location_y;
      options.location_z = this.asModel.location_z;
      options.rotation_x = this.asModel.rotation_x;
      options.rotation_y = this.asModel.rotation_z;
      options.rotation_z = this.asModel.rotation_y;
      options.scale_x = this.asModel.scale_x;
      options.scale_y = this.asModel.scale_y;
      options.scale_z = this.asModel.scale_z;
      options.showStyle = this.asModel.effect;
      this.currentApp.options = JSON.stringify(options);
      this.currentApp.remark = this.asModel.remark;
    },
    // 选择3dt
    handleChange(file, fileList) {
      this.updataFileList = fileList;
      this.dtModel.filename = file.name;
      this.dtModel.filesize = (file.size / (1024 * 1024)).toFixed(2) + "MB";
      this.dtModel.dataFile = file;
      if (this.updataFileList.length > 1) {
        this.dtModel.name = "";
      } else {
        this.dtModel.name = file.name.split(".")[0];
      }
      if (fileList.length > 0) {
        this.updataFileShow = false;
      } else {
        this.updataFileShow = true;
      }
    },
    // 文件移除
    handleRemove(file, fileList) {
      if (fileList.length > 0) {
        this.updataFileShow = false;
      } else {
        this.updataFileShow = true;
      }
      if (fileList.length == 0) {
        this.dtModel.name = "";
      }
    },
    defaultUp() { },
    // 取消上传
    handleClickCancel() {
      // reset data
      this.$refs.dtForm.resetFields();
      this.dtModel.filename = "";
      this.dtModel.filesize = "";
      this.dtModel.dataFile = null;
      this.updataFileList = [];
      this.updataFileShow = true;
      this.operate = "init";
    },
    // 上传
    handleClickUpload() {
      this.$refs.dtForm.validate((valid) => {
        if (valid) {
          if (this.updataFileList.length > 1) {
            this.updataFileList.map((item) => {
              let parms = {
                from: "list",
                fileName: item.raw.name,
                name: item.raw.name.split(".")[0],
                raw: item.raw,
              };
              this.uploadFile(parms);
            });
          } else {
            let parms = {
              name: this.dtModel.name,
              raw: this.dtModel.dataFile.raw,
            };
            this.uploadFile(parms);
          }
          this.handleClickCancel();
        } else {
          return false;
        }
      });
    },
    // 正在上传
    addUploadFile(parms, random) {
      let item = {
        state: "uploading",
        skd_state: "upload",
        name: parms.name,
        random: random,
      };
      this.uploadList.push(item);
    },
    // 上传3DT
    uploadFile(parms) {
      let self = this;
      let attribute = {
        name: parms.name,
        topic: this.topic,
        character: this.dtModel.character,
        remark: "",
        options: {
          location_x: this.dtModel.location_x,
          location_y: this.dtModel.location_y,
          location_z: this.dtModel.location_z,
          rotation_x: this.dtModel.rotation_x,
          rotation_y: this.dtModel.rotation_y,
          rotation_z: this.dtModel.rotation_z,
          scale_x: this.dtModel.scale_x,
          scale_y: this.dtModel.scale_y,
          scale_z: this.dtModel.scale_z,
        },
      };
      if (parms.from === "list") {
        attribute.options.fileName = parms.fileName;
      }
      let random = Math.random();
      self.addUploadFile(attribute, random);
      this.GLOBAL.addFile(
        parms.raw,
        this.dtModel.type,
        JSON.stringify(attribute),
        this.uploadtoken
      ).then((res) => {
        if (!!res && res === "pause") {
          return false;
        }
        setTimeout(() => {
          // 3DT上传成功
          self.delUploadList(random);
          self.get3dtList();
        }, 1000);
      });
    },
    // 上传中->删除
    delUploadList(random) {
      this.uploadList.splice(
        this.uploadList.findIndex((item) => item.random === random),
        1
      );
    },
    // 点击鼠标左键: 坐标
    onEventMeasurement(data) {
      this.lmbc_coordinate = data.MouseClickPoint;
      let self = this;
      if (this.locateActive) {
        if (self.operate === "upload3dt") {
          self.dtModel.location_x = self.lmbc_coordinate[0];
          self.dtModel.location_y = self.lmbc_coordinate[1];
          self.dtModel.location_z = self.lmbc_coordinate[2];
        } else if (self.operate === "application") {
          self.asModel.location_x = self.lmbc_coordinate[0];
          self.asModel.location_y = self.lmbc_coordinate[1];
          self.asModel.location_z = self.lmbc_coordinate[2];
        }
        this.update3dt();
      }
    },
    // 位置
    handleClickLoc() {
      this.locateActive = true;
    },
  },
  beforeDestroy() {
    this.locateActive = false;
    // 取消应用
    this.list3dt.filter((item) => {
      this.cancelEvent(item);
      if (item.fid) {
        window.sealAPI._tileLayer.delete(item.fid);
      }
    });
  },
};
</script>

<style lang="scss">
@import "./css/upload-3dt.scss";
/* el-dropdown */
</style>
<style lang="scss" scoped>
.locateActive {
  color: #029eff !important;
}

.application-model {
  height: 100%;

  .el_dropdown {
    background: rgba(46, 52, 65, 0.9);
    box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.4),
      0px 0px 0px 0px rgba(0, 0, 0, 0.5), 0px 2px 5px 0px rgba(38, 38, 44, 0.5);
    border: 0;
    border-radius: 2px;

    .el-dropdown-menu__item {
      font-size: 16px;
      font-weight: 400;
      color: #ffffff;

      &:hover {
        background: #aed6ff33;
        color: #ffffff;
      }
    }

    .popper__arrow {
      border-bottom-color: transparent !important;

      &::after {
        border-bottom-color: rgba(46, 52, 65, 0.9) !important;
      }
    }
  }

  .tree-warp {
    flex: 1;
    padding: 16px;
    box-sizing: border-box;
  }

  .tree-top {
    height: 100%;
    overflow: hidden;
    border-bottom: 1px solid rgba($color: #ffffff, $alpha: 0.2);

    /* search */
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

      /deep/ .el-input {
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

    /* 添加3dt数据 */
    .add3dt {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      white-space: nowrap;
      color: #d8d8d8;
      text-align: center;
      cursor: pointer;

      &>div:first-child {
        margin: 0px auto;
        display: flex;
        align-items: center;
        justify-content: center;
        width: 146px;
        height: 91px;
        border-radius: 7px;
        border: 1px solid #979797;
        font-size: 28px;
      }

      p {
        margin: 20px 0 0 0;
        font-size: 16px;
        font-weight: 400;
        color: #9ea1a7;
        line-height: 25px;
      }
    }

    /* 添加3dt */
    /deep/.list_3dt {
      font-size: 16px;
      font-weight: 400;
      color: #ffffff;
      height: 22px;
      line-height: 22px;

      .add {
        color: #9ea1a7;
        margin: 20px 0;
        cursor: pointer;
      }

      .item_3dt {
        &:not(:first-child) {
          margin-top: 8px;
        }

        &.checked {
          background: #aed6ff33;
        }

        background: #aed6ff33;
        border-radius: 8px;

        &>.el-row {
          line-height: 50px;
          padding: 0 8px 0 16px;
        }

        .el-dropdown {
          cursor: pointer;
          padding: 0 4px;
          font-size: 16px;
          color: #b5b4bf;
          transform: rotate(90deg);
          -ms-transform: rotate(90deg);
          -moz-transform: rotate(90deg);
          -webkit-transform: rotate(90deg);
          -o-transform: rotate(90deg);
        }

        /* 进度条
        .schedule {
          text-align: center;
          padding-bottom: 10px;
          .el-progress {
            .el-progress-bar__outer {
              background-color: #979797;
            }
            .el-progress__text {
              display: none;
            }
          }
          & > span {
            font-size: 14px;
            font-weight: 400;
            color: #9ea1a7;
          }
        } */
        .upload {
          float: right;
          font-size: 12px;
          opacity: 0.6;
        }
      }
    }

    /* 应用场景 */
    /deep/.application_scenario {
      .el-color-picker {
        width: 100%;
      }

      .el-color-picker__trigger {
        width: 100% !important;
      }
    }

    /* scrollbar */
    /deep/.el-scrollbar {
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

            &>.el-tree-node>.el-tree-node__children>.el-tree-node>.el-tree-node__children>.el-tree-node {
              &>.el-tree-node__content {
                padding-left: 72px !important;

                .el-tree-node__expand-icon {
                  display: none;
                }
              }

              &>.el-tree-node__children {
                display: none;
              }
            }
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

    /deep/ .switch {
      .el-scrollbar__wrap {
        margin-right: 0 !important;
        z-index: 999;
      }
    }

  }

}
</style>

