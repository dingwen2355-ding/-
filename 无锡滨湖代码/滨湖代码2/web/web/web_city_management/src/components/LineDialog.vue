<template>
  <el-dialog
    :title="'事件列表'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1931rem"
    class="Line—Dialog default-dialog-class large-screen"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div class="dd-title-back">
      <div class="dd-title-back-s"></div>
    </div>
    <div class="dd-back-area top-right-area"></div>
    <div class="dd-back-angle top-left-angle"></div>
    <div class="dd-back-angle top-right-angle"></div>
    <div class="dd-back-angle bottom-left-angle"></div>
    <div class="dd-back-angle bottom-right-angle"></div>
    <div>
      <div class="historyList">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="formInline.title" placeholder="请输入事件标题"></el-input>
          </el-form-item>
          <!-- <el-form-item v-if="!precinct">
            <el-select v-model="formInline.precinctId" placeholder="区县选择" filterable clearable>
              <el-option v-for="item in alarmList" :key="item.id" :label="item.name" :value="item.id"> </el-option>
            </el-select>
          </el-form-item> -->
          <el-form-item>
            <el-select v-model="formInline.type" placeholder="事件类型选择" filterable clearable popper-class="large-screen">
              <el-option v-for="item in unitOptions" :key="item.id" :label="item.typeName" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="formInline.time"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期" popper-class="large-screen"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button class="chaxun" type="primary" @click="onSubmit">查询</el-button>
            <el-button class="chongzhi" @click="reset">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table :data="tableData" class="large-screen">
        <el-table-column prop="event_title" label="事件标题"  style="width:1070rem"></el-table-column>
        <el-table-column prop="event_type_name" label="事件类型"  style="width:302rem"></el-table-column>
        <el-table-column prop="happen_time" label="发生时间" style="width:646rem"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="area" label="属地" style="width:346rem"></el-table-column>
        <el-table-column label="指挥" style="width:146rem">
          <template slot-scope="scope">
            <i
              class="el-icon-thumb play-icon"
              style="cursor: pointer;font-size: 60rem"
              title="指挥"
              @click="toEventHandelPage(scope.row)"
            ></i>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <el-pagination class="large-screen"
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
  props: ['dialogVisibleFlag', 'infomation'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.formInline = {
          title: '',
          precinctId: '',
          time: '',
          type: ''
        }
        this.currentPage = 1
        this.pageSize = 10
        this.getEventList()
        this.queryEventTypeList()
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
      type: 1,
      alarmType: 'xf',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      precinct: this.$route.query.precinctId,
      alarmList: sysConfig.area,
      formInline: {
        title: '',
        precinctId: '',
        time: '',
        type: ''
      },
      unitOptions: []
    }
  },
  methods: {
    queryEventTypeList() {
      let url = '/apis/manage/queryEventTypeList'
      Axios.get(url).then((r) => {
        this.unitOptions = r.data.data
      })
    },
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
      // let time = new Date(new Date().toLocaleDateString())
      // let timeUnder = new Date(time.getTime() + 24 * 60 * 60 * 1000)
      this.getEventList()
    },
    getEventList() {
      let url = '/apis/Command/eventInfoList?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.formInline.time) {
        url +=
          '&beginTime=' +
          moment(this.formInline.time[0]).format('YYYY-MM-DD HH:mm:ss') +
          '&endTime=' +
          moment(this.formInline.time[1]).format('YYYY-MM-DD HH:mm:ss')
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
      if (this.formInline.type) {
        url += '&eventType=' + this.formInline.type
      }
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
      let url = '/community/dapeng/event/edit'
      Axios
        .post(url, {
          id: row.eventId,
          status: '42'
        })
        .then((res) => {
          if (res.data.code === 200) {
            this.$router.push({
              path: '/',
              query: {
                eventId: row.eventId
              }
            })
            this.$router.go(0) // 页面刷新
          } else {
            this.$message.error(res.data.message)
          }
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.Line—Dialog {
  ::v-deep .el-dialog{
    width: 5212rem !important;
    height: 3204rem !important;
  }
  .historyList{
    margin-bottom: 64rem;
  }
  ::v-deep.el-button{
    padding: 12rem 20rem;
  }
  .footer{
    height: 272rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
