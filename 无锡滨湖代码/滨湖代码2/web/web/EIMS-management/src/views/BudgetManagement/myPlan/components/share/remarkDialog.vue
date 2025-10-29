<template>
  <div class="remark-dialog" v-if="dialogVisible">
    <el-dialog
      title="查看反馈信息"
      :visible.sync="dialogVisible"
      width="1100px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button type="primary" icon="el-icon-plus" @click="addRemarkItem" style="margin-bottom: 10px"
            >添加反馈</el-button
          >
        </div>
        <div style="height: calc(100% - 50px); width: 100%; overflow-x: hidden">
          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in remarkData" :key="item.id">
            <el-form :inline="true" :model="item" ref="formLabel" label-width="110px">
              <el-form-item label="反馈人: ">{{ item.operatorName }} </el-form-item>
              <el-form-item label="反馈时间: ">{{ item.createTime }} </el-form-item>
              <el-form-item label="反馈内容" prop="remarkContent" class="whole-line">
                <el-input
                  disabled
                  type="textarea"
                  :rows="6"
                  clearable
                  v-model="item.content"
                  placeholder="请输入反馈内容"
                ></el-input>
              </el-form-item>
              <div style="float: right; margin-right: 20px">
                <el-button type="primary" plain @click="responseRemark(item.id, item)">查看回复</el-button>
              </div>
            </el-form>
          </div>
        </div>
      </div>
    </el-dialog>
    <add-remark-dialog
      :dialogVisible.sync="showRemarkAdd"
      @addSuccess="addSuccess"
      :planId="belongPlanId"
    ></add-remark-dialog>
    <response-remark-dialog
      :dialogVisible.sync="showResponse"
      :id="belongPlanId"
      :formInfo="reaponseFromInfo"
      :isCode="isCode"
    ></response-remark-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
import responseRemarkDialog from './responseRemarkDialog.vue'
import addRemarkDialog from './addRemark/addRemarkDialog.vue'
export default {
  components: { responseRemarkDialog, addRemarkDialog },
  name: 'remarkDialog',
  props: ['dialogVisible', 'id'],
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.belongPlanId = this.id
      // console.log('this.belongPlanId', this.belongPlanId)
      this.queryRemarkList(this.belongPlanId)
    }
  },
  data() {
    return {
      belongPlanId: '', // 所属预案
      remarkData: [],
      showRemarkAdd: false,
      showResponse: false,
      reaponseFromInfo: {},
      isCode: '1'
    }
  },
  mounted() {},
  methods: {
    queryRemarkList(id) {
      let params = {
        id: id,
        respType: '1'
      }
      let url = '/gtw/EIMS-management/get-plan-comment_resp'
      Axios.post(url, params).then((res) => {
        this.remarkData = res.data.data.list
      })
    },
    handleClose() {
      this.$emit('update:dialogVisible', false)
    },
    // 添加反馈
    addRemarkItem() {
      this.showRemarkAdd = true
    },
    addSuccess(id) {
      this.queryRemarkList(id)
    },
    // 回复
    responseRemark(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      this.showResponse = true
      this.reaponseFromInfo = item
    }
  }
}
</script>
<style lang="scss">
.remark-dialog {
  // /deep/ .el-dialog__body {
  .el-dialog__body {
    height: 800px;
    overflow: auto;
  }
  .whole-line {
    width: 100%;
    .el-form-item__content {
      width: calc(100% - 191px);
    }
  }
}
</style>
