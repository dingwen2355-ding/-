<template>
  <div class="MonitorPublicModule">
    <div class="tab-container">
      <div class="tab-item" :class="{'active': currentTab === '0'}"
        @click="currentTab = '0',getPublicOpinionDataByType('0')">网上舆情</div>
      <div class="tab-item" :class="{'active': currentTab === '1'}"
        @click="currentTab = '1',getPublicOpinionDataByType('1')">社会舆情</div>
    </div>
    <div class="tab-content" v-if="currentTab === '0'">
      <div class="table-dec-warp">
        <div class="ctrl-list">
          <div class="ctrl-btn" @click="batchShow"><i class="iconfont iconzhgl_xianshi"></i> 批量显示</div>
          <div class="ctrl-btn" @click="batchHide"><i class="iconfont iconzhgl_yincang"></i> 批量隐藏</div>
        </div>
        <div class="event-public">
          <label>事件关联舆情：</label>
          <el-select v-model="eventMonitorPublic" placeholder="请选择事件关联舆情" @change="changeEvent" filterable :filter-method="filterMethod">
            <el-option v-for="item in eventMonitorPublicList20" :key="item.opinionId" :label="item.title"
              :value="item.opinionId">
            </el-option>
          </el-select>
        </div>
      </div>
      <div class="table-container">
        <el-table ref="monitorRef" height="1340" :data="showwebTableData" :row-class-name="tableRowClassName"
          style="width: 100%" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="100">
          </el-table-column>
          <el-table-column type="index" label="序号" width="160">
          </el-table-column>
          <!-- <el-table-column label="舆情类型" width="320">
            <template slot-scope="scope">
              <div class="arrow-btn" @click="showWebArrowDialog(scope.row)">{{scope.row.type}} <i
                  class="el-icon-arrow-down"></i></div>
              <div class="arrow-dialog" v-if="scope.row.showArrowDialog">
                <div class="arrow-list">
                  <div class="arrow-item" @click="scope.row.type = '境内媒体', scope.row.showArrowDialog = false">境内媒体</div>
                  <div class="arrow-item" @click="scope.row.type = '外媒', scope.row.showArrowDialog = false">外媒</div>
                  <div class="arrow-item" @click="scope.row.type = '自媒体', scope.row.showArrowDialog = false">自媒体</div>
                </div>
              </div>
            </template>
          </el-table-column> -->
          <el-table-column prop="mediaName" label="媒体名称" width="320">
          </el-table-column>
          <el-table-column prop="reportTitle" label="报道标题">
            <!-- <template slot-scope="scope">
              <k-edit-text v-model="scope.row.reportTitle"></k-edit-text>
            </template> -->
          </el-table-column>
          <el-table-column prop="reportUrl" label="报道链接">
            <!-- <template slot-scope="scope">
              <k-edit-text v-model="scope.row.reportUrl"></k-edit-text>
            </template> -->
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <div class="link-btn" :class="{'red':scope.row.status != 0}" @click="webTableHandleClick(scope.row)">
                <i class="iconfont iconzhgl_xianshi" v-if="scope.row.status == 0"></i>
                <i class="iconfont iconzhgl_yincang" v-else></i>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination-container">
        <div class="div-item l">
          <el-radio-group>
            <el-checkbox :true-label="'0'" v-model="webcheckbox">全选</el-checkbox>
            <el-checkbox :true-label="'1'" v-model="webcheckbox">反选</el-checkbox>
          </el-radio-group>
        </div>
        <div class="div-item r">
          <el-pagination @size-change="handleSizeChange1" @current-change="handleCurrentChange1"
            :current-page="currentPage1" :page-size="pagesize1" layout="total, prev, pager, next, jumper"
            :total="webTableData.length">
          </el-pagination>
        </div>
      </div>
      <div class="submit-btn-warp">
        <div class="link-btn btn-item">取消</div>
        <div class="blue-btn btn-item" @click="submitWebTableData">提交</div>
      </div>
    </div>
    <div class="tab-content" v-if="currentTab === '1'">
      <div class="table-dec-warp">
        <div class="ctrl-list">
          <div class="ctrl-btn"><i class="iconfont iconzhgl_xuanding"></i> 批量选定</div>
        </div>
        <div class="event-public">
          <label>事件关联舆情：</label>
          <el-select v-model="eventMonitorPublic" placeholder="请选择事件关联舆情" filterable>
            <el-option v-for="item in eventMonitorPublicList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </div>
      </div>
      <div class="table-container">
        <el-table height="1340" :data="societyTableData" :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange" style="width: 100%">
          <el-table-column type="selection" width="100">
          </el-table-column>
          <el-table-column type="index" label="序号" width="160">
          </el-table-column>
          <!-- <el-table-column prop="type" label="舆情类型" width="320">
          </el-table-column> -->
          <el-table-column prop="mediaName" label="媒体名称" width="320">
          </el-table-column>
          <el-table-column prop="reportTitle" label="报道标题">
          </el-table-column>
          <el-table-column prop="reportUrl" label="报道链接">
          </el-table-column>
          <el-table-column label="操作" width="520">
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination-container">
        <div class="div-item l">
          <el-checkbox-group v-model="societyCheckbox" :max="1">
            <el-checkbox label="全选"></el-checkbox>
            <el-checkbox label="反选"></el-checkbox>
          </el-checkbox-group>
        </div>
        <div class="div-item r">
          <el-pagination @size-change="handleSizeChange2" @current-change="handleCurrentChange2"
            :current-page="currentPage2" :page-size="pagesize2" layout="total, prev, pager, next, jumper"
            :total="societyTableData.length">
          </el-pagination>
        </div>
      </div>
      <div class="submit-btn-warp">
        <div class="link-btn btn-item">取消</div>
        <div class="blue-btn btn-item">提交</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'MonitorPublicModule',
  data () {
    return {
      eventId: '',
      currentTab: '0', // 0-网上舆情 1-社会舆情
      eventMonitorPublic: '',
      eventMonitorPublicList: [],
      eventMonitorPublicList20: [],
      webTableData: [
        {
          type: '境内媒体',
          mediaName: '人民日报',
          reportTitle: '卫健委上报：13名伤者被送往普陀区中心医院进行救治，1人经抢救无效死亡，12名伤者目前生命体征平稳。',
          reportUrl: 'http://192.168.21.168:8083/#/ShCityManagement?eventId=101',
          isShow: true,
          showArrowDialog: false
        },
        {
          type: '新京报',
          mediaName: '人民日报',
          reportTitle: '卫健委上报：13名伤者被送往普陀区中心医院进行救治，1人经抢救无效死亡，12名伤者目前生命体征平稳。',
          reportUrl: 'http://192.168.21.168:8083/#/ShCityManagement?eventId=101',
          isShow: false,
          showArrowDialog: false
        },
        {
          type: '境外媒体',
          mediaName: 'BBC',
          reportTitle: '卫健委上报：13名伤者被送往普陀区中心医院进行救治，1人经抢救无效死亡，12名伤者目前生命体征平稳。',
          reportUrl: 'http://192.168.21.168:8083/#/ShCityManagement?eventId=101',
          isShow: false,
          showArrowDialog: false
        },
        {
          type: '新浪微博',
          mediaName: '胡锡进',
          reportTitle: '卫健委上报：13名伤者被送往普陀区中心医院进行救治，1人经抢救无效死亡，12名伤者目前生命体征平稳。',
          reportUrl: 'http://192.168.21.168:8083/#/ShCityManagement?eventId=101',
          isShow: false,
          showArrowDialog: false
        },
        {
          type: '境外媒体',
          mediaName: 'BBC',
          reportTitle: '卫健委上报：13名伤者被送往普陀区中心医院进行救治，1人经抢救无效死亡，12名伤者目前生命体征平稳。',
          reportUrl: 'http://192.168.21.168:8083/#/ShCityManagement?eventId=101',
          isShow: true,
          showArrowDialog: false
        },
        {
          type: '新浪微博',
          mediaName: '胡锡进',
          reportTitle: '卫健委上报：13名伤者被送往普陀区中心医院进行救治，1人经抢救无效死亡，12名伤者目前生命体征平稳。',
          reportUrl: 'http://192.168.21.168:8083/#/ShCityManagement?eventId=101',
          isShow: true,
          showArrowDialog: false
        }
      ],
      showwebTableData: [],
      societyTableData: [],
      webcheckbox: '',
      societyCheckbox: [],
      // webCurrentPage: 1,
      // societyCurrentPage: 1,
      currentPage1: 1,
      pagesize1: 5,
      currentPage2: 1,
      pagesize2: 5,
      multipleSelectionIds: [],
      multipleSelection: [],
      opinionId: '',
      opinionReportIds: ''
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.getPublicOpinionDataByType('0')
    })
  },
  watch: {
    '$store.getters.eventId': {
      handler (v) {
        this.$nextTick(() => {
          this.getTaleData(v.id)
          this.eventId = v.id
        })
      },
      immediate: true
    },
    'webcheckbox': {
      handler (v) {
        this.$nextTick(() => {
          if (v === '0') {
            this.$refs.monitorRef.clearSelection()
            this.$refs.monitorRef.toggleAllSelection()
          } else if (v === '1') {
            this.webTableData.forEach(row => {
              if (row) {
                this.$refs.monitorRef.toggleRowSelection(row)
              }
            })
          } else {
            this.$refs.monitorRef.clearSelection()
          }
        })
      },
      immediate: true
    }
  },
  methods: {
    filterMethod(query = '') {
      let arr = this.eventMonitorPublicList.filter(item => {
        return item.title.includes(query)
      })
      if (arr.length > 20) {
        this.eventMonitorPublicList20 = arr.slice(0, 20)
      } else {
        this.eventMonitorPublicList20 = arr
      }
    },
    getTaleData (eventId) {
      this.webTableData = []
      let url = '/apis/publicOpinion/publicOpinionDataDemo?eventId=' + eventId
      axios.get(url).then(res => {
        if (res.data.data) {
          let data = res.data.data
          this.webTableData = data.webReportList ? data.webReportList : []
          this.showwebTableData = this.webTableData.slice((this.currentPage1 - 1) * this.pagesize1, this.currentPage1 * this.pagesize1)
        }
      }).catch(err => {
        console.error(err)
      })
    },

    getPublicOpinionDataByType (type) {
      this.eventMonitorPublic = ''
      this.eventMonitorPublicList = []
      let url = '/apis/publicOpinion/getPublicOpinionDataByType?type=' + type
      axios.get(url).then(res => {
        this.eventMonitorPublicList = res.data.data
        if (this.eventMonitorPublicList.length > 20) {
          this.eventMonitorPublicList20 = this.eventMonitorPublicList.slice(0, 20)
        } else {
          this.eventMonitorPublicList20 = this.eventMonitorPublicList
        }
      }).catch(err => {
        console.error(err)
      })
    },
    changeEvent (val) {
      this.opinionId = val
      this.webTableData = []
      let url = '/apis/publicOpinion/getPublicOpinionReportByOpinionId?opinionId=' + val
      axios.get(url).then(res => {
        let data = [...res.data.data]
        data.map(item => {
          item.reportTitle = item.report_title
          item.reportAbstract = item.report_abstract
          item.mediaType = item.media_type
          item.publicOpinionId = item.public_opinion_id
          item.reportUrl = item.report_url
          item.mediaName = item.media_name
        })
        this.webTableData = [...data] || []
        this.showwebTableData = this.webTableData.slice((this.currentPage1 - 1) * this.pagesize1, this.currentPage1 * this.pagesize1)
      }).catch(err => {
        console.error(err)
      })
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
      this.multipleSelectionIds = [...val].map(item => {
        return item.id
      })
    },
    batchShow () {
      this.webTableData.map((item, index) => {
        if (this.multipleSelectionIds.indexOf(item.id) !== -1) {
          item.status = 0
        }
      })
    },
    batchHide () {
      this.webTableData.map((item, i) => {
        if (this.multipleSelectionIds.indexOf(item.id) !== -1) {
          item.status = 1
        }
      })
    },
    tableRowClassName ({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'even-row'
      } else if (rowIndex % 2 === 1) {
        return 'odd-row'
      }
      return ''
    },
    handleSizeChange1 (val) {
      this.pagesize1 = val
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange1 (val) {
      this.currentPage1 = val
      this.showwebTableData = this.webTableData.slice((this.currentPage1 - 1) * this.pagesize1, this.currentPage1 * this.pagesize1)

      console.log(`当前页: ${val}`)
    },
    handleSizeChange2 (val) {
      this.pagesize2 = val
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange2 (val) {
      this.currentPage2 = val
      console.log(`当前页: ${val}`)
    },
    webTableHandleClick (row) {
      // eslint-disable-next-line eqeqeq
      row.status = row.status == 0 ? 1 : 0
    },
    showWebArrowDialog (row) {
      for (let i = 0; i < this.webTableData.length; i++) {
        if (this.webTableData[i] === row) {
          row.showArrowDialog = !row.showArrowDialog
        } else {
          this.webTableData[i].showArrowDialog = false
        }
      }
    },
    submitWebTableData () {
      if (!this.eventId) {
        this.$message('请选择事件名称')
        return
      }
      if (!this.opinionId) {
        this.$message('请选择事件关联舆情')
        return
      }
      let arr = []
      this.webTableData.map(item => {
        // eslint-disable-next-line eqeqeq
        if (item.status == 1) {
          arr.push(item.id)
        }
      })
      this.opinionReportIds = arr.join(',') || ''
      let url = '/apis/publicOpinion/updateOpinionReportStatus?eventId=' + this.eventId + '&opinionId=' + this.opinionId + '&opinionReportIds=' + this.opinionReportIds
      axios.post(url).then(res => {
        let data = res.data
        if (data.code === 200) {
          this.$message({
            message: '提交成功！',
            type: 'success'
          })
          this.$root.eventBus.$emit('refreshIframe')
        } else {
          this.$message({
            message: '提交失败！',
            type: 'error'
          })
        }
        // console.log(res)
      }).catch(err => {
        console.error(err)
        this.$message({
          message: '提交失败！',
          type: 'error'
        })
      })
    },
    selectAll () {
      this.$refs.monitorRef.toggleAllSelection()
    }
  }
}
</script>

<style lang="scss">
.MonitorPublicModule {
  position: relative;
  width: 100%;

  .tab-container {
    position: relative;
    margin-top: 76px;
    line-height: 80px;
    margin-bottom: 40px;
    margin-left: 62px;

    .tab-item {
      display: inline-block;
      width: 234px;
      height: 80px;
      border-radius: 4px;
      text-align: center;
      cursor: pointer;

      &.active {
        color: #fff;
        background: rgba(12, 153, 219, 1);
      }
    }
  }

  .tab-content {
    margin-bottom: 80px;
    margin-left: 62px;
    margin-right: 62px;

    .table-dec-warp {
      line-height: 80px;
      margin-bottom: 40px;

      .ctrl-list {
        float: right;
        position: relative;

        .ctrl-btn {
          display: inline-block;
          width: 291px;
          height: 80px;
          text-align: center;
          background: rgba(255, 255, 255, 1);
          border: 2px solid;
          border-radius: 8px;
          margin: 0 20px;
          line-height: 76px;
          cursor: pointer;

          i {
            font-size: 40px;
          }

          &:last-child {
            color: #ff5858;
            border-color: #ff5858;
          }

          &:first-child {
            color: #0c99db;
            border-color: #0c99db;
          }
        }
      }

      .event-public {
        position: relative;
        margin-right: 800px;

        .el-select {
          width: 738px;
        }
      }
    }

    .table-container {
      position: relative;
      /*height: 1680px;*/

      .arrow-btn {
        position: relative;
        cursor: pointer;
      }
      .arrow-dialog {
        position: absolute;
        border: 1px solid #e4e7ed;
        border-radius: 4px;
        background-color: #fff;
        -webkit-box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        z-index: 11;

        .arrow-item {
          cursor: pointer;
          line-height: 80px;
          padding: 0px 30px;
          &:hover {
            background-color: #f5f7fa;
          }
        }
      }

      .el-table__row:last-child {
        .arrow-dialog {
          top: -200px;
        }
      }
    }
    .pagination-container {
      height: 80px;
      line-height: 80px;
      margin-bottom: 40px;

      .l {
        float: left;
      }
      .r {
        float: right;
      }
    }

    .submit-btn-warp {
      position: relative;
      text-align: center;

      .btn-item {
        margin: 0 40px;
      }
    }
  }
}
</style>
