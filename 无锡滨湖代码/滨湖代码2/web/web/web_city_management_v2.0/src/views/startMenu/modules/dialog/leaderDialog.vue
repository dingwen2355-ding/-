<template>
  <el-dialog
    :title="
      showLeader && showLeader.length > 0 && showLeader[0].index === 0 ? '事件相关区属单位详情' : '事件相关属地单位详情'
    "
    :visible.sync="dialogVisible"
    :modal="false"
    width="900rem"
    height="570rem"
    class="leaderDialog default-dialog-class large-screen"
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
      <el-table :data="showTable" class="large-screen">
        <el-table-column type="index" label="序号" width="180" align="center"></el-table-column>
        <el-table-column prop="name" label="单位名称" align="center"></el-table-column>
        <el-table-column prop="data[0].leader" label="姓名" width="550" align="center"></el-table-column>
        <!--        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row.data[0])" type="text" size="small" icon="el-icon-phone" title="拨打电话"></el-button>
            <el-button @click="handleClick1(scope.row.data[0])" type="text" size="small" icon="el-icon-message" title="发送短信"></el-button>
          </template>
        </el-table-column>-->
      </el-table>
      <div class="footer">
        <el-pagination
          class="large-screen"
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
    <DialogPhone :dialogPhoneVisible.sync="dialogPhoneVisible" :infomation="dialogPhoneData" />
    <DialogMessage :dialogMessageVisible.sync="messageVisible" :infomation="dialogMessageData" />
  </el-dialog>
</template>

<script>
import DialogPhone from './DialogPhone.vue'
import DialogMessage from './DialogMessage.vue'
export default {
  props: ['dialogVisibleFlag', 'showLeader'],
  components: {
    DialogPhone,
    DialogMessage
  },
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.currentPage = 1
        this.pageSize = 10
      }
    }
  },
  computed: {
    showTable() {
      // console.log('val', this.showLeader)
      return this.showLeader.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
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
      messageVisible: false,
      dialogPhoneVisible: false,
      dialogPhoneData: {},
      dialogMessageData: {}
    }
  },
  methods: {
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleClick(el) {
      this.dialogPhoneData = el
      this.dialogPhoneVisible = true
      console.log(el)
    },
    handleClick1(el) {
      this.dialogMessageData = el
      this.messageVisible = true
      console.log(el)
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.leaderDialog {
  ::v-deep .el-dialog {
    width: 1575rem !important;
    height: 3204rem !important;
  }
  .footer {
    height: 272rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
