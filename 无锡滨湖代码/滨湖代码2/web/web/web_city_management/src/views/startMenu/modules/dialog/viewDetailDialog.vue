<template>
  <el-dialog
    title="指令列表"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1431rem"
    height="600rem"
    class="lineDialog"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div>
      <div class="historyList">
        <div class="add" @click="addView">新增</div>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item>
            <el-date-picker
              v-model="formInline.time"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item class="formItem" style="text-align: center">
            <div
              class="formDiv"
              v-for="(item, i) in timer"
              :class="{ active: i === activeIndex }"
              :key="item"
              @click="choose(item, i)"
            >
              {{ item }}
            </div>
          </el-form-item>
          <el-form-item>
            <el-button class="chaxun" type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table :data="tableData">
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="departments" label="下发单位"></el-table-column>
        <el-table-column prop="createDate" label="时间"></el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="prev, pager, next"
          :total="total"
          :pager-count="5"
        ></el-pagination>
      </div>
    </div>
    <showViewDialog :dialogVisibleFlag.sync="showViewVisible" :depart="depart" @goToview="goToview" />
    <videoMeetingDialog :dialogVisibleFlag.sync="videoMeetingVisible" :videoMeetingData="videoMeetingData" />
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
import moment from 'moment'
import showViewDialog from './showViewDialog.vue'
import videoMeetingDialog from './videoMeetingDialog.vue'
export default {
  props: ['dialogVisibleFlag', 'depart'],
  components: { showViewDialog, videoMeetingDialog },
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.formInline = {
          time: '',
          timeChoose: []
        }
        this.activeIndex = -1
        this.currentPage = 1
        this.pageSize = 10
        this.getEventList()
      }
    },
    'formInline.time'(v) {
      if (v) {
        this.activeIndex = -1
        this.formInline.timeChoose = []
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      showViewVisible: false,
      videoMeetingVisible: false,
      videoMeetingData: {},
      currentTime: '',
      tableData: [],
      newZ: '',
      newTime: '',
      activeIndex: -1,
      type: 1,
      timer: ['近一天', '近一周', '近一月'],
      alarmType: 'xf',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      precinct: this.$route.query.precinctId,
      alarmList: sysConfig.area,
      formInline: {
        time: '',
        timeChoose: []
      }
    }
  },
  methods: {
    addView() {
      this.showViewVisible = true
    },
    choose(e, index) {
      if (this.activeIndex !== index) {
        this.activeIndex = index
      } else {
        this.activeIndex = -1
        this.formInline.timeChoose = []
      }
      this.formInline.time = ''
      if (e === '近一天') {
        this.formInline.timeChoose = []
        this.formInline.timeChoose = [
          moment(new Date()).subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss'),
          moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        ]
      } else if (e === '近一周') {
        this.formInline.timeChoose = []
        this.formInline.timeChoose = [
          moment(new Date()).subtract(7, 'days').format('YYYY-MM-DD HH:mm:ss'),
          moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        ]
      } else {
        this.formInline.timeChoose = []
        this.formInline.timeChoose = [
          moment(new Date()).subtract(1, 'months').format('YYYY-MM-DD HH:mm:ss'),
          moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        ]
      }
      console.log(this.formInline.timeChoose)
    },
    onSubmit() {
      this.currentPage = 1
      this.getEventList()
    },
    goToview(e) {
      this.currentPage = 1
      this.getEventList()
    },
    reset() {
      this.currentPage = 1
      this.formInline = {
        time: ''
      }
      // let time = new Date(new Date().toLocaleDateString())
      // let timeUnder = new Date(time.getTime() + 24 * 60 * 60 * 1000)
      this.getEventList()
    },
    getEventList() {
      let url = '/apis/eventJoint/queryTitle?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.formInline.time) {
        url +=
          '&startDate=' +
          moment(this.formInline.time[0]).format('YYYY-MM-DD HH:mm:ss') +
          '&endDate=' +
          moment(this.formInline.time[1]).format('YYYY-MM-DD HH:mm:ss')
      }
      if (this.formInline.timeChoose.length > 0) {
        url += '&startDate=' + this.formInline.timeChoose[0] + '&endDate=' + this.formInline.timeChoose[1]
      }
      if (this.$route.query.precinctId) {
        url += '&precinctId=' + this.$route.query.precinctId
      }
      Axios.get(url).then((res) => {
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.getEventList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getEventList()
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    },
    handleClick(e) {
      e.url = 'http://10.1.50.123/zhrc_portal/eventlog/?riskId=' + e.eventIdNew + '&type=duty&height=800'
      this.videoMeetingVisible = true
      this.videoMeetingData = e
    }
  }
}
</script>

<style lang="scss">
.lineDialog {
  .el-dialog__body {
    padding: 0 20rem 30rem;
    height: 600rem;
    overflow: hidden;
    .add {
      width: 65rem;
      border: 1rem solid #42678d;
      height: 38rem;
      line-height: 35rem;
      margin-top: 1rem;
      margin-left: 10rem;
      cursor: pointer;
      position: absolute;
      top: 69rem;
      right: 25rem;
      color: #409eff;
      text-align: center;
    }
  }
  .box-2 {
    ul {
      display: flex;
      float: right;
      li {
        width: 252rem;
        span {
          font-size: 16rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          &:nth-of-type(1) {
            color: #035561;
          }
        }
      }
    }

    .time-box {
      display: flex;
      padding: 25rem 0rem 0rem 55rem;
      .time-item {
        width: 76rem;
        // height: 28rem;
        background: rgba(61, 248, 248, 0.2);
        border: 2rem solid #035561;
        font-size: 16rem;
        line-height: 28rem;
        text-align: center;
        cursor: pointer;
      }
      .active {
        border: 2rem solid #00ffff;
        // background: linear-gradient(180deg, #28d9ff 0%, rgba(40, 217, 255, 0.2) 100%);
        background-image: radial-gradient(rgba(8, 36, 36, 0.2) 40%, rgba(89, 230, 240, 0.39));
        color: #00ffff;
      }
    }
  }
  #echart {
    width: 100%;
    height: 400rem;
  }
  .el-table th {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00ffff;
    background: #1c2331;
    height: 50rem;
  }
  .el-table td {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #ffffff;
    height: 67rem;
    background: #171d2b;
  }
  .el-table th.is-leaf,
  .el-table th.el-table__cell.is-leaf {
    border: 0 !important;
  }
  .el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #171d2b !important;
  }
  .el-table,
  .el-table__expanded-cell {
    background-color: #1c2331;
  }
  .el-table th.is-leaf {
    border-top: 1rem solid #28feff;
    border-bottom: 1rem solid #28feff;
  }
  .el-table td {
    border-bottom: 1rem solid #28feff;
  }
  .el-table::before {
    height: 0;
  }
  .el-table__body-wrapper {
    height: 410rem !important;
    overflow: auto;
  }
  .historyList .el-input__inner {
    font-size: 14rem;
    color: #fff;
  }
  .historyList .el-select .el-input__inner {
    width: 140rem;
    color: #fff;
  }
  .formItem {
    .el-form-item__content {
      display: flex;
    }
    .formDiv {
      width: 85rem;
      border: 1rem solid #42678d;
      height: 38rem;
      line-height: 35rem;
      margin-top: 1rem;
      margin-left: 10rem;
      cursor: pointer;
    }
    .active {
      background: #409eff;
      color: #fff;
    }
  }
  .el-form--inline .el-form-item__label {
    font-size: 16rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00efff;
  }
  // .el-form--inline .el-form-item:nth-of-type(2) .el-form-item__content {
  //   width: 180rem;
  // }
  // .el-form--inline .el-form-item:nth-of-type(3) .el-form-item__content {
  //   width: 360rem;
  // }
  .el-range-editor .el-range-input {
    background: #0c223e;
    color: #fff;
    margin-left: 8rem;
  }
  .el-date-editor .el-range-separator {
    color: #fff;
  }
  .footer {
    margin-top: 27rem;
    text-align: center;
    .el-pagination__jump {
      color: #ffffff;
    }
  }
}
</style>
