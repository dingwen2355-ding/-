<template>
  <div class="show-flex-r" style="height: 100%">
    <div v-loading="tableListLoding" class="show-flex-c" style="
          width: 400px;
          margin-right: 10px;
          height: 100%;
          border-right: 10px solid #eceef5;
        ">
      <!-- 组织搜索 -->
      <div class="flex-1" style="background: #ffffff; overflow: auto; padding: 10px">
        <div class="show-flex-r margin-bottom-10 margin-top-5" style="padding: 0 10px">
          <el-input v-model="filterText" size="mini" clearable placeholder="请输入组织名称或ID" />
        </div>
        <el-tree ref="treeId" :props="props" :data="deviceTree" show-checkbox check-strictly node-key="id"
          :filter-node-method="filterNode" default-expand-all :default-checked-keys="currentKey"
          @check="changeCheckType" />
      </div>
    </div>
    <div class="flex-1 w-0 h-100" style="overflow: auto">
      <baseKanbanTd title="设备管理">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
          <div slot="customQuery" class="listSearch padding-left-10">
            <div class="search-line">
              <normalSearch ref="channelId" labelName="通道ID" keyName="channelId" placName="请输入通道ID" />
              <normalSearch ref="name" labelName="通道名称" keyName="name" placName="请输入通道名称" />
              <searchRegionDept ref="regionCode" labelName="所属街镇" keyName="regionCode"/>
              <!-- <normalSelect 
                        ref="ai"
                        labelName="接入状态"
                        keyName="ai"
                        :options="[{label: '接入',value:1},{label: '不接入', value: 0}]"
                    /> -->
            </div>
          </div>
          <div slot="table">
            <div class="show-flex-r j-e-2 margin-bottom-10">
              <t-button class="margin-right-20" @click="toSynchOrg()">同步组织</t-button>
              <t-button class="margin-right-20" @click="toSynchChannel()">同步通道</t-button>
            </div>
            <el-table v-loading="tableListLoding" stripe :data="tableData">
              <el-table-column label="所属组织" prop="orgName" min-width="250" />
              <el-table-column label="组织所在社区" prop="regionAllName" min-width="200" />
              <el-table-column label="通道编号" prop="channelId" min-width="220" />
              <el-table-column label="通道名称" prop="name" min-width="200" />
              <!-- <el-table-column label="经度" prop="gpsX" width="150" /> -->
              <!-- <el-table-column label="纬度" prop="gpsY" width="150" /> -->
              <el-table-column label="操作" width="180" fixed="right">
                <template slot-scope="scope">
                  <div class="show-flex-r a-c">
                    <div class="zr-link-p unline m-5" @click="row = scope.row; showVideo = true">查看监控</div>
                    <div class="zr-link-h unline m-5" v-if="scope.row.checkChannel == null" @click="row = scope.row; showPz = true">配置</div>
                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </baseSearchForm>
      </baseKanbanTd>
    </div>
    <video-dialog v-if="showVideo" :show="showVideo" :row="row" @cancel="showVideo = false"></video-dialog>
    <!-- 配置 -->
    <pz
        v-if="showPz"
        :show="showPz"
        :detail="row"
        @cancel="() => { showPz = false }"
        @finish="refushPage"
        @sure="savePointsDevice"
    />
  </div>
</template>
<script>
import { reportMixins } from "@/utils/mixins";
import { pageDevice, synchDevice } from "@/api/data/device";
import {
  pageDeviceChannel,
  deleteDevice,
  synchChannel
} from "@/api/data/deviceChannel";
import { saveAI } from '@/api/data/checkPointsDevice'
import videoDialog from "@/components/util/videoDialog.vue"
import pz from './pz.vue'

export default {
  mixins: [reportMixins],
  components: { videoDialog, pz },
  data() {
    return {
      showVideo: false,
      showPz: false,
      filterText: "",
      props: {
        label: "name",
        children: "children"
      },
      includeChildren: false,
      deviceTree: [],
      currentKey: [],
      row: {}
    };
  },
  watch: {
    filterText(val) {
      this.$refs.treeId.filter(val);
    }
  },
  mounted() {
    this.getAllType();
  },
  methods: {
    getAllType() {
      let _this = this;
      pageDevice({}).then(data => {
        _this.deviceTree = data.data.records || [];
      });
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    changeCheckType(node, list) {
      let arr = [];
      if (list.checkedKeys.length > 0) {
        arr = [node.id];
      }
      this.$nextTick(() => {
        this.currentKey = arr;
        this.$refs.treeId.setCheckedKeys(arr);
        this.startSearch();
      });
    },
    getList(searchData) {
      if (this.currentKey.length > 0) {
        searchData["treeId"] = this.currentKey[0];
      }
      this.getTableData(pageDeviceChannel, searchData);
    },
    delItem(item) {
      let _this = this;
      new Promise((res, rej) => {
        _this.useDia = this.$dialog({
          header: "系统提示",
          body: `是否确认删除名称为"${item.name}"的数据项？`,
          style: "color: rgba(0, 0, 0, 0.6)",
          onConfirm: ({ e }) => {
            res();
          }
        });
      })
        .then(d => {
          _this.useDia.hide();
          return deleteDevice(item.id);
        })
        .then(d => {
          _this.$successMessage("操作成功!");
          _this.refushPage();
        });
    },
    savePointsDevice(result) {
      let _this = this
      saveAI({
        "channelId": result.channelId,
        "regionCode": result.regionCode,
        "pointsId": result.pointsId,
        "alarmTypeRemark": result.alarmTypeRemark
      }).then(d => {
        _this.showPz = false
        _this.$successMessage("操作成功!")
        _this.refushPage()
      })
    },
    jbItem(item) {
      let _this = this;
      new Promise((res, rej) => {
        _this.useDia = this.$dialog({
          header: "系统提示",
          body: `是否确认解绑名称为"${item.name}"的数据项？`,
          style: "color: rgba(0, 0, 0, 0.6)",
          onConfirm: ({ e }) => {
            res();
          }
        });
      })
        .then(d => {
        //   _this.useDia.hide();
        //   return jbDevice(item.id);
        })
        .then(d => {
          _this.$successMessage("操作成功!");
          _this.refushPage();
        });
    },
    toSynchChannel() {
      synchChannel({}).then(data => {
        this.startSearch();
      });
    },
    toSynchOrg() {
      synchDevice({}).then(data => {
        this.startSearch();
        this.getAllType();
      });
    }
  }
};
</script>
  