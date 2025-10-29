<template>
  <div class="remark-response-dialog" v-if="dialogVisible">
    <el-dialog
      :title="isCode == 1 ? '查看回复反馈信息' : '查看回复信息'"
      :visible.sync="dialogVisible"
      width="900px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button type="primary" icon="el-icon-plus" @click="addResponseItem" style="margin-bottom: 10px"
            >添加回复</el-button
          >
        </div>
        <div style="height: calc(100% - 50px); width: 100%; overflow-x: hidden">
          <div
            style="width: 100%; margin-top: 10px; margin-left: 10px"
            v-for="item in responseRemarkData"
            :key="item.id"
          >
            <el-form :inline="true" :rules="rules" :model="item" ref="formLabel" label-width="110px">
              <el-form-item label="回复人: ">{{ item.recipientName }} </el-form-item>
              <el-form-item label="回复时间: ">{{ item.createTime }} </el-form-item>
              <el-form-item label="回复内容" prop="responseContent" class="whole-line">
                <el-input
                  disabled
                  type="textarea"
                  :rows="6"
                  clearable
                  v-model="item.respContent"
                  placeholder="请输入回复内容"
                ></el-input>
              </el-form-item>
            </el-form>
            <div style="float: right; margin-right: 20px">
              <el-button type="primary" plain @click="responseRemark(item.id, item)">查看回复</el-button>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
    <add-response-remark-dialog
      :dialogVisible.sync="showResponseAdd"
      :planId="belongPlanId"
      :remarkId="remarkId"
      :responseId="responsePeopleId"
      :flag="respFlag"
      :isCode="isAddResponseCode"
      @addSuccess="addSuccess"
    ></add-response-remark-dialog>
    <response-remark-dialog
      :dialogVisible.sync="showResponse"
      :id="belongPlanId"
      :formInfo="responseFormInfo"
      :isCode="isResponseCode"
    ></response-remark-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
import responseRemarkDialog from './responseRemarkDialog.vue'
import addResponseRemarkDialog from './addRemark/addResponseRemarkDialog.vue'
// import znvTime from '../../../../utils/znvTime'
export default {
  components: { responseRemarkDialog, addResponseRemarkDialog },
  name: 'responseRemarkDialog',
  props: ['dialogVisible', 'id', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.belongPlanId = this.id
      // console.log('this.belongPlanId', this.belongPlanId)
      // console.log('this.formInfo', this.formInfo)
      this.queryResponseRemarkList(this.formInfo.id)
    }
  },
  data() {
    return {
      rules: {
        responseContent: [{ required: true, message: '请输入回复内容', trigger: 'blur' }]
      },
      belongPlanId: '',
      responseRemarkData: [],
      // form: {
      //    recipientName: '',
      //     createTime: '',
      //     respContent: ''
      // },
      showResponseAdd: false, // 添加回复
      isAddResponseCode: '',
      remarkId: '', // 评论ID
      responsePeopleId: '', // 被回复人id
      respFlag: '', // 标志 若为1 则respType= '1'; 若为2 respType= '2'
      showResponse: false, // 查看回复
      responseFormInfo: {},
      isResponseCode: '2'
    }
  },
  mounted() {},
  methods: {
    queryResponseRemarkList(id) {
      let params = {
        id: id,
        respType: ''
      }
      if (this.formInfo.respType !== undefined) {
        params.respType = '3'
      } else {
        params.respType = '2'
      }
      // console.log('queryResponseRemarkList参数', params)
      let url = '/gtw/EIMS-management/get-plan-comment_resp'
      Axios.post(url, params).then((res) => {
        this.responseRemarkData = res.data.data.list
      })
    },
    handleClose() {
      this.$emit('update:dialogVisible', false)
    },
    // 添加回复
    addResponseItem() {
      this.showResponseAdd = true
      if (this.formInfo.respType !== undefined) {
        this.isAddResponseCode = '2'
        this.remarkId = this.formInfo.id
        this.responsePeopleId = this.formInfo.operatorId
        this.respFlag = 2
      } else {
        this.isAddResponseCode = '1'
        this.remarkId = this.formInfo.id
        this.responsePeopleId = this.formInfo.operatorId
        this.respFlag = 1
      }
    },
    addSuccess(id) {
      this.queryResponseRemarkList(id)
    },
    // 回复
    responseRemark(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      this.showResponse = true
      this.responseFormInfo = item
    }
  }
}
</script>
<style lang="scss">
.remark-response-dialog {
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
