<template>
  <div class="EventsModule">
    <header class="tab-container">
      <!-- <div class="tab-item" :class="{'active': currentTab === '0'}"
        @click="currentTab = '0',getPublicOpinionDataByType('0')">事件动态
      </div> -->
      <div class="tab-item" :class="{'active': currentTab === '1'}"
        @click="currentTab = '1',getPublicOpinionDataByType('1')">到场力量
      </div>
    </header>
    <section class="tab-content" v-if="currentTab === '0'">
      <div class="table-dec-warp">
        <div class="ctrl-list">
          <div class="ctrl-btn" @click="batchDelete"><i class="el-icon-delete-solid"></i>批量删除</div>
        </div>
        <div class="add-div">
          <div class="border-btn" @click="addListItem"><i class="el-icon-plus"></i> 新增</div>
        </div>
      </div>
      <el-table height="1340" ref="ledMultipleTable" :data="showData" :row-class-name="tableRowClassName"
        style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="100">
        </el-table-column>
        <el-table-column type="index" label="序号" width="160">
        </el-table-column>
        <el-table-column prop="eventStatus" label="事件状态" width="320">
          <template slot-scope="scope">
            <el-select v-model="scope.row.eventStatus" placeholder="请选择" @change="changeeventStatus">
              <el-option v-for="item in eventStatusOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="unitName" label="上报单位" width="420">
          <template slot-scope="scope">
            <k-edit-text v-model="scope.row.unitName"></k-edit-text>
          </template>
        </el-table-column>
        <el-table-column prop="recordTime" label="上报时间" width="520">
          <template slot-scope="scope">
            <el-date-picker v-model="scope.row.recordTime" :format="'yyyy-MM-dd HH:mm:ss'" type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column prop="dealContent" label="进展描述">
          <template slot-scope="scope">
            <k-edit-text v-model="scope.row.dealContent"></k-edit-text>
          </template>
        </el-table-column>
        <el-table-column prop="pictureUrl" label="照片" width="400">
          <template slot-scope="scope">
            <img :src="scope.row.pictureUrl" alt="">
          </template>
        </el-table-column>
        <el-table-column prop="videoUrl" label="视频" width="400">
          <template slot-scope="scope">
            <video :src="scope.row.videoUrl"></video>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="200">
          <template slot-scope="scope">
            <el-select v-model="scope.row.status" placeholder="请选择" @change="changeStatus">
              <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <div class="link-btn">
              <i class="el-icon-delete-solid" style="color:red;" @click="deleteClick(scope.$index,scope.row)"></i>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <div class="div-item l">
          <el-radio-group>
            <el-checkbox :true-label="'0'" v-model="ledCheckbox">全选</el-checkbox>
            <el-checkbox :true-label="'1'" v-model="ledCheckbox">反选</el-checkbox>
          </el-radio-group>
        </div>
        <div class="div-item r">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page.sync="currentPage" :page-size="pageSize" layout="total, prev, pager, next, jumper"
            :total="tableData.length">
          </el-pagination>
        </div>
      </div>
      <div class="submit-btn-warp">
        <div class="link-btn btn-item" @click="cancelSubmit">取消</div>
        <div class="blue-btn btn-item" @click="submitResult">提交</div>
      </div>
    </section>
    <section class="tab-content" v-if="currentTab === '1'">
      <PresentPower />
    </section>
  </div>
</template>

<script>
import KEditText from '../../components/KEditText'
import PresentPower from './PresentPower'
import { getEventInfoList, getSubmitEventInfoList } from '@/api/events-modules.js'

export default {
  name: 'EventsModule',
  components: {
    KEditText,
    PresentPower
  },
  data () {
    return {
      currentTab: '1',
      eventStatusOptions: [
        {
          value: '1',
          label: '事件上报'
        },
        {
          value: '2',
          label: '事件续报'
        },
        {
          value: '3',
          label: '事件终报'
        }
      ],
      statusOptions: [
        {
          value: '0',
          label: '显示'
        },
        {
          value: '1',
          label: '隐藏'
        }
      ],
      tableData: [
        {
          'eventId': '101',
          'dealContent': '今天上午8时许，普陀区长征镇产业园区发生火灾。经了解，系上址园区内3号、5号仓库内堆放的泡沫板、木板等物起火，据现场工作人员反映人员均已撤出。现消防正在现场组织灭火救援工作。',
          'eventTitle': '普陀区火灾',
          'recordTime': '2020-05-27 09:18:56',
          'videoUrl': null,
          'unitName': null,
          'eventStatus': '1',
          'pictureUrl': null,
          'unitId': null,
          'id': 6,
          'status': '0'
        }
      ],
      showData: [],
      eventId: '',
      checkbox: [],
      currentPage: 1,
      pageSize: 5,
      multipleSelection: [],
      ledCheckbox: ''
    }
  },
  watch: {
    '$store.getters.eventId': {
      handler (v) {
        this.$nextTick(() => {
          this.eventId = v.id
          this.getTableData(v.id)
        })
      },
      immediate: true
    },
    'ledCheckbox': {
      handler (v) {
        this.$nextTick(() => {
          if (v === '0') {
            this.$refs.ledMultipleTable.clearSelection()
            this.$refs.ledMultipleTable.toggleAllSelection()
          } else if (v === '1') {
            this.tableData.forEach(row => {
              this.$refs.ledMultipleTable.toggleRowSelection(row)
            })
          } else {
            this.$refs.ledMultipleTable.clearSelection()
          }
        })
      },
      immediate: true
    }
  },
  mounted () {
    // this.$root.eventBus.$on('closeAddEventDialog')
    this.$root.eventBus.$on('addNewData', data => {
      // this.tableData.push(data)
      this.getTableData(this.eventId)
    })
  },
  methods: {
    async getTableData (v) {
      this.tableData = []
      try {
        let tableDataRes = await getEventInfoList(v)
        this.tableData = tableDataRes.data.data ? [...tableDataRes.data.data.eventDealFlow] : []
        this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
      } catch (error) {
        console.log(error)
      }
    },
    changeStatus (val) {

    },
    changeeventStatus (val) {

    },
    addListItem () {
      // console.log('object')
      this.$root.eventBus.$emit('showAddEventDialog', this.eventId)
    },
    handleSizeChange (val) {
      this.pageSize = val
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
      console.log(`当前页: ${val}`)
    },
    deleteClick (index, row) {
      this.tableData.splice(index, 1)
      this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    },
    async submitResult () {
      let obj = {
        eventId: this.eventId,
        lists: this.tableData ? JSON.stringify(this.tableData) : '[]'
      }
      try {
        await getSubmitEventInfoList(obj)
        this.$message({
          message: '提交成功！',
          type: 'success'
        })
        this.$root.eventBus.$emit('refreshIframe')
      } catch (error) {
        console.log(error)
      }
    },
    cancelSubmit () {
      this.getTableData(this.eventId)
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    batchDelete () {
      let selectList = this.multipleSelection.map(item => {
        return item.id
      })
      let dataArr = []
      this.tableData.map((item, index) => {
        if (selectList.indexOf(item.id) === -1) {
          dataArr.push(item)
        }
      })
      this.tableData = dataArr
    },
    tableRowClassName ({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'even-row'
      } else if (rowIndex % 2 === 1) {
        return 'odd-row'
      }
      return ''
    },
    getPublicOpinionDataByType (type) {

    }
  },
  destroyed() {
    this.$root.eventBus.$off('addNewData')
  }
}
</script>

<style lang="scss" scoped>
.EventsModule {
  width: 100%;
  position: relative;
  /*padding: 76px 62px 80px;*/

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
  }

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
        border: 2px solid #ff5858;
        border-radius: 8px;
        margin: 0 20px;
        line-height: 76px;
        cursor: pointer;
        color: #ff5858;

        i {
          font-size: 40px;
        }
      }
    }

    .add-div {
      position: relative;
      margin-right: 800px;

      .el-select {
        width: 738px;
      }
    }
  }

  .tab-container {
    position: relative;
    /*height: 1680px;*/
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
</style>
