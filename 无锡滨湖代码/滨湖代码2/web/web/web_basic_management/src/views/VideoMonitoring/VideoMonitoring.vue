<template>
  <div class="VideoMonitoring" style="overflow: hidden">
    <div class="left">
      <div style="display: flex; margin-top: 5px">
        <el-input placeholder="请输入搜索内容" v-model="mainForm.search"></el-input>
        <el-button icon="el-icon-search" @click="mainSearch" style="margin-left: 5px"></el-button>
      </div>
      <div class="mainContent">
        <el-tree
          ref="tree"
          node-key="id"
          :highlight-current="true"
          :props="treeProps"
          :load="loadNode"
          lazy
          style="width: 100%"
          @node-click="checkChange"
          :filter-node-method="filterNodeMethod"
        ></el-tree>
      </div>
    </div>
    <div class="right">
      <div class="headerTop">
        <el-form ref="form" :inline="true" :model="searchform" :disabled="rightButton">
          <el-form-item label="所在辖区:">
            <el-select
              filterable
              v-model="searchform.region"
              size="small"
              placeholder="请选择所在辖区"
              clearable
            >
              <el-option
                v-for="item in areaList"
                :value="item.regionId"
                :label="item.regionName"
                :key="item.regionId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="摄像头名称:">
            <el-input clearable v-model="searchform.search" size="small" placeholder="请输入摄像头名称"></el-input>
          </el-form-item>
          <el-form-item label="摄像头状态:">
            <el-select
              filterable
              v-model="searchform.cameraStatus"
              size="small"
              placeholder="请选择摄像头状态"
              clearable
            >
              <el-option
                v-for="item in cameraStatusList"
                :value="item.id"
                :label="item.name"
                :key="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="所属单位:">
            <el-input clearable v-model="searchform.belongUnit" size="small" placeholder="请输入所属单位"></el-input>
          </el-form-item>
          <el-form-item label="经纬度状态:">
            <el-select
              filterable
              v-model="searchform.longLatStatus"
              size="small"
              placeholder="请选择经纬度状态"
              clearable
            >
              <el-option label="全部" value="0"></el-option>
              <el-option
                v-for="item in longLatStatusList"
                :value="item.id"
                :label="item.name"
                :key="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="querySerch()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="contentBody">
        <div class="content-top">
          <div class="top-title">查询表格</div>
        </div>
        <div style="overflow: scroll">
          <el-table :data="tableData" style="width: 100%" height="640" border v-loading="loading">
            <el-table-column type="index" label="序号" width="50" :index="table_index"></el-table-column>
            <el-table-column prop="precinctName" label="辖区" width="80"></el-table-column>
            <el-table-column prop="deviceId" label="摄像头ID" show-overflow-tooltip></el-table-column>
            <el-table-column prop="deviceName" label="摄像头名称" width="275">
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.fullName"
                  placement="bottom"
                >
                  <span style="cursor: pointer">{{ scope.row.deviceName }}</span>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column prop="gpsx" label="经度" width="100"></el-table-column>
            <el-table-column prop="gpsy" label="纬度" width="100"></el-table-column>
            <el-table-column prop="onofflineState" label="摄像头状态" width="95">
              <template slot-scope="scope">{{ scope.row.onofflineState === 0 ? '在线' : '离线' }}</template>
            </el-table-column>
            <el-table-column prop="regionName" label="所属单位" show-overflow-tooltip></el-table-column>
            <el-table-column label="操作" width="120">
              <template slot-scope="scope">
                <el-button @click="preview(scope.row)" type="text" size="medium">预览</el-button>
                <el-button @click="edit(scope.row)" type="text" size="medium">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <div class="pagination-body">
          <el-pagination
            background
            :current-page="currentPage"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :page-size="pageSize"
            :page-sizes="[15, 50, 100, 500, 1000]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          ></el-pagination>
        </div>
      </div>
    </div>

    <edit-info-dialog
      :dialogVisible.sync="editInfoShow"
      :formInfo="editFormInfo"
      @editSuccess="editSuccess"
    ></edit-info-dialog>

    <VideoDialog :markerInfo="markerInfo" />
  </div>
</template>

<script>
import axios from '@/utils/request'

import Video from '../../components/znvVideo/Video.vue'
import VideoDialog from './VideoDialog.vue'
import editInfoDialog from './editInfoDialog.vue'
import { getHlsUrl, getFlvUrl } from '@/api/info.js'

export default {
  components: { Video, VideoDialog, editInfoDialog },
  name: 'VideoMonitoring',
  data() {
    return {
      mainForm: {
        search: ''
      },
      treeProps: {
        id: 'id',
        label: 'name',
        children: 'children'
      },
      rightButton: true,
      searchform: {
        region: '',
        search: '',
        belongUnit: '',
        cameraStatus: '',
        longLatStatus: '0'
      },
      areaList: [],
      cameraStatusList: [
        { id: '0', name: '在线' },
        { id: '1', name: '离线' }
      ],
      longLatStatusList: [
        { id: '1', name: '有经纬度' },
        { id: '2', name: '无经纬度' }
      ],
      loading: false,
      tableData: [],
      checkedArry: [], // 选中的选项
      currentPage: 1, // 当前页数
      pageSize: 15, // 每页条数
      total: 0, // 总条数
      editInfoShow: false,
      editFormInfo: {},
      dialogTableVisible: false,
      cameraIndexCodes: '',
      markerInfo: {
        deviceName: '',
        deviceId: '',
        dialogVisible: false,
        url: ''
      }
    }
  },
  mounted() {
    this.querySerch()
    this.getAreaList()

    this.$root.eventBus.$on('editSuccess', (data) => {
      this.queryTableData()
    })

    this.$root.eventBus.$on('upDataVideoDialog', (data) => {
      this.markerInfo = {
        deviceName: '',
        deviceId: '',
        dialogVisible: false,
        url: ''
      }
    })
  },
  watch: {
    'mainForm.search'(val) {
      this.$refs.tree.filter(val)
    }
  },
  methods: {
    mainSearch() {
      this.$refs.tree.filter(this.mainForm.search)
    },
    filterNodeMethod(value, data, node) {
      return node.data.name.indexOf(value) > -1
    },
    loadNode(node, resl) {
      let UpRegionId = ''
      if (node.level !== 0) {
        UpRegionId = node.data.id
      }
      let url = `/binhuapis/queryRegionListByUpId?UpRegionId=${UpRegionId}`
      axios.get(url).then((r) => {
        let data = r.data.data.map((t) => {
          t.children = []
          t.label = t.name
          t.id = t.indexCode
          return t
        })
        resl(data)
      })
    },
    checkChange(node, isNodeCheck, isChildCheck) {
      console.log('checkChange', node, isNodeCheck)
      if (isNodeCheck) {
        this.checkId = node.id
        this.rightButton = false
        this.queryTableData()
      }
    },
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    // 所属辖区
    getAreaList() { // ?userLevel=${localStorage.level}&userId=${localStorage.userId}
      let url = '/binhuapis/companyDict/queryRegion'
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    // 查询
    querySerch() {
      this.currentPage = 1
      this.queryTableData()
      // this.loading = true
      // if (!this.checkId) {
      //   this.loading = false
      //   return
      // }
      // this.rightButton = false
      // let url = `/binhuapis/queryRegionDownDevices?regionId=${this.checkId}&pageNum=${this.currentPage}&pageSize=${this.pageSize}`
      // if (this.searchform.region) {
      //   // 辖区
      //   url = url + '&precinctId=' + this.searchform.region
      // }
      // if (this.searchform.search) {
      //   // 摄像头名称
      //   url = url + '&deviceName=' + this.searchform.search
      // }
      // if (this.searchform.cameraStatus) {
      //   // 摄像头状态
      //   url = url + '&onofflineState=' + this.searchform.cameraStatus
      // }
      // if (this.searchform.belongUnit) {
      //   // 所属单位
      //   url = url + '&regionName=' + this.searchform.belongUnit
      // }
      // if (this.searchform.longLatStatus) {
      //   // 经纬度状态
      //   url = url + '&gpsState=' + this.searchform.longLatStatus
      // }
      // console.log('查询的url: ', url)
      // axios.get(url).then((r) => {
      //   console.log(r, '查询')
      //   this.loading = false
      //   this.tableData = r.data.data.list
      //   this.total = Number(r.data.data.count)
      // })
    },
    queryTableData() {
      this.loading = true
      if (!this.checkId) {
        this.loading = false
        return
      }
      this.rightButton = false
      let url = `/binhuapis/queryRegionDownDevices?regionId=${this.checkId}&pageNum=${this.currentPage}&pageSize=${this.pageSize}`
      if (this.searchform.region) {
        // 辖区
        url = url + '&precinctId=' + this.searchform.region
      }
      if (this.searchform.search) {
        // 摄像头名称
        url = url + '&deviceName=' + this.searchform.search
      }
      if (this.searchform.cameraStatus) {
        // 摄像头状态
        url = url + '&onofflineState=' + this.searchform.cameraStatus
      }
      if (this.searchform.belongUnit) {
        // 所属单位
        url = url + '&regionName=' + this.searchform.belongUnit
      }
      if (this.searchform.longLatStatus) {
        // 经纬度状态
        url = url + '&gpsState=' + this.searchform.longLatStatus
      }
      console.log('查询的url: ', url)
      axios.get(url).then((r) => {
        console.log(r, '查询')
        this.loading = false
        this.tableData = r.data.data.list
        this.total = Number(r.data.data.count)
      })
    },

    // 预览
    preview(row) {
      getFlvUrl({
        deviceId: row.deviceId,
        type: 'HLS'
      }).then(res => {
        if (res.src) {
          this.dialogTableVisible = true
          this.markerInfo = {
            deviceName: row.deviceName,
            deviceId: row.deviceId,
            dialogVisible: true,
            url: res.src
          }
        } else {
          this.$message({
            message: '无对应视频，请确认！',
            type: 'warning'
          })
        }
      })
      // this.cameraIndexCodes = row.deviceId
    },
    // 编辑
    edit(row) {
      console.log('编辑当前行数据：', row)
      this.editInfoShow = true
      if (row.precinctId) {
        row.precinctId = Number(row.precinctId)
      }
      this.editFormInfo = row
    },
    editSuccess() {
      this.currentPage = 1
      this.queryTableData()
    },

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.queryTableData()
    },

    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
      this.queryTableData()
    }
  }
}
</script>

<style lang="scss">
@import '../../common/style/base';
.VideoMonitoring {
  display: flex;
  flex-direction: row;
  overflow: hidden;
  background: transparent !important;
  // box-sizing: border-box;
  // padding: 0 !important;
  .left {
    width: 25%;
    height: 100%;
    // margin-right: 10px;
    // margin-top: 10px;
    // margin-left: 10px;
    background: #ffffff;
    padding: 15px;
    .mainContent {
      display: flex;
      //   flex-direction: column;
      overflow: scroll;
      height: 835px;
      .leftItem {
        padding-left: 10px;
        width: 100%;
        height: 42px;
        margin-top: 10px;
        line-height: 42px;
      }
      .leftItem:hover {
        background: #e4f4ff;
      }
      .el-tree {
        .el-tree-node.is-current {
          //rgba(0,147,255,0.1)
          > .el-tree-node__content {
            background-color: rgba(0, 147, 255, 0.1);
          }
        }
      }
    }
  }
  .right {
    width: 75%;
    height: 100%;
    // margin-right: 10px;
    // margin-top: 10px;
    // margin-left: 10px;
    background: #ffffff;
    padding: 15px;
    margin-left: 20px;

    .headerTop {
      width: 100%;
      margin-top: 10px;
      background: #ffffff;
      padding-left: 30px;
      display: flex;
      align-items: center;

      .el-input {
        width: 256px;

        .el-input__inner {
          height: 36px !important;
          line-height: 36px;
        }
      }
    }

    .contentBody {
      margin-top: 10px;
      width: 100%;
      height: calc(100% - 135px);
      background: #ffffff;
      padding: 0px 20px 10px 20px;
      box-sizing: border-box;

      .content-top {
        width: 100%;
        height: 35px;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .top-title {
          font-size: 18px;
          font-family: Microsoft YaHei;
          font-weight: bold;
          color: #4e5a6e;
        }

        .el-button {
          width: 130px;
          height: 36px;
          line-height: 0px;
          background: linear-gradient(90deg, #0093ff 0%, #056cf2 100%);
          border-radius: 2px;
          font-size: 18px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #ffffff;
          padding: 0;
          border: 0 none;
        }
      }

      //   .el-table {
      //     th.el-table__cell {
      //       background: #eff2f5;
      //       font-size: 14px;
      //       font-family: Microsoft YaHei;
      //       font-weight: 400;
      //       color: #606d80;
      //       text-align: center;
      //       height: 56px;
      //     }

      //     td.el-table__cell {
      //       text-align: center;
      //       height: 56px;
      //       font-size: 14px;
      //       font-family: Microsoft YaHei;
      //       font-weight: 400;
      //       color: #202d40;
      //       border-bottom: 1px solid #d3dae8;

      //       .operation {
      //         display: flex;
      //         width: 100%;
      //         align-items: center;
      //         font-size: 14px;
      //         font-family: Microsoft YaHei;
      //         font-weight: 400;
      //         color: #0093ff;

      //         span {
      //           cursor: pointer;
      //         }
      //         i {
      //           width: 1px;
      //           height: 16px;
      //           background: #d3dae8;
      //           margin: 0 20px;
      //         }
      //       }
      //     }
      //   }
      //   .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
      //     background-color: #e4f4ff;
      //   }

      //   .pagination-body {
      //     margin-top: 30px;
      //   }
    }
  }
  .elDialog-body {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    margin: auto;
    background: rgba(255, 255, 255, 0.5);

    .video-body {
      width: 920px !important;
      height: 500px !important;
    }
  }
}
</style>
