<template>
  <el-dialog
    :title="type === 1 ? '类似事件' : '附近事件'"
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
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="formInline.title" placeholder="请输入事件标题"></el-input>
          </el-form-item>
          <el-form-item v-if="!precinct">
            <el-select v-model="formInline.precinctId" placeholder="区县选择" filterable clearable>
              <el-option
                v-for="item in alarmList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item>
            <el-select v-model="formInline.type" placeholder="事件类型选择" filterable clearable>
              <el-option v-for="item in unitOptions" :key="item.id" :label="item.typeName" :value="item.id"> </el-option>
            </el-select>
          </el-form-item>-->
          <el-form-item>
            <el-date-picker
              v-model="formInline.time"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button class="chaxun" type="primary" @click="onSubmit">查询</el-button>
            <el-button class="chongzhi" @click="reset">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table :data="tableData">
        <el-table-column prop="event_title" label="事件标题"></el-table-column>
        <el-table-column prop="desc" label="详情" width="200"></el-table-column>
        <el-table-column prop="happen_time" label="发生时间"></el-table-column>
        <el-table-column prop="event_type_name" label="事件类型"></el-table-column>
        <el-table-column prop="area" label="属地"></el-table-column>
        <el-table-column label="指挥" width="120">
          <template slot-scope="scope">
            <i
              class="el-icon-thumb play-icon"
              style="cursor: pointer;font-size: 24rem"
              title="指挥"
              @click="toEventHandelPage(scope.row)"
            ></i>
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
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
import moment from 'moment'
export default {
  props: ['dialogVisibleFlag', 'infomation', 'type'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.formInline = {
          title: '',
          precinctId: '',
          time: ''
          // type: ''
        }
        this.currentPage = 1
        this.pageSize = 10
        this.getEventList()
        // this.queryEventTypeList()
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      currentTime: '',
      tableData: [],
      newZ: '',
      newTime: '',
      alarmType: 'xf',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      precinct: this.$route.query.precinctId,
      alarmList: sysConfig.area,
      formInline: {
        title: '',
        precinctId: '',
        time: ''
        // type: ''
      }
      // unitOptions: []
    }
  },
  methods: {
    // queryEventTypeList() {
    //   let url = '/apis/manage/queryEventTypeList'
    //   Axios.get(url).then((r) => {
    //     this.unitOptions = r.data.data
    //   })
    // },
    onSubmit() {
      this.currentPage = 1
      this.getEventList()
    },
    reset() {
      this.currentPage = 1
      this.formInline = {
        title: '',
        precinctId: '',
        time: ''
      }
      this.getEventList()
    },
    getEventList() {
      let url = '/apis/Command/eventInfoListByType?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.formInline.time) {
        url +=
          '&beginTime=' +
          moment(this.formInline.time[0]).format('YYYY-MM-DD HH:mm:ss') +
          '&endTime=' +
          moment(this.formInline.time[1]).format('YYYY-MM-DD HH:mm:ss')
      }
      if (this.type) {
        url += '&type=' + this.type
      }
      if (this.formInline.title) {
        url += '&title=' + this.formInline.title
      }
      if (this.$route.query.precinctId) {
        url += '&precinctId=' + this.$route.query.precinctId
      }
      if (this.formInline.precinctId) {
        url += '&precinctId=' + this.formInline.precinctId
      }
      // if (this.formInline.type) {
      //   url += '&eventType=' + this.formInline.type
      // }
      Axios.get(url).then((res) => {
        this.tableData = res.data.data.list
        this.total = res.data.data.count
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
    toEventHandelPage(row) {
      if (this.$route.query.precinctId) {
        this.$router.push({
          path: '/',
          query: {
            eventId: row.event_id,
            precinctId: this.$route.query.precinctId
          }
        })
      } else {
        this.$router.push({
          path: '/',
          query: {
            eventId: row.event_id
          }
        })
      }
      this.$router.go(0) // 页面刷新
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
