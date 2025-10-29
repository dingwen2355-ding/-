<template>
  <j-modal
    :title="'工单'+title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    :okText="type"
    @cancel="handleCancel"
    cancelText="关闭"
    :ok-button-props="{ props: { disabled: disableSubmit } }"
  >
    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" v-bind="layout" :rules="validatorRules">
        <a-form-model ref="form" :model="model" v-bind="layout" :rules="validatorRules">
        <a-form-model-item v-if="type!='处理'" :label="type+'人员'" required prop="users">
          <g-select-user-by-dep v-model="model.users" :multi="false"></g-select-user-by-dep>
        </a-form-model-item>
  
        <a-form-model-item label="备注" prop="msg">
          <a-input v-model="model.msg" placeholder="请输入备注内容" type="textarea" />
        </a-form-model-item>
      </a-form-model>
      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>
import { postAction } from '@/api/manage'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import GSelectUserByDep from '@/components/jeecgbiz/GSelectUserByDep'
export default {
  name: 'RepairOrderModal',
  components: { JDictSelectTag ,GSelectUserByDep},
  data() {
    let checkStatus = (rule, value, callback) => {
      // clearTimeout(checkPending);
      if (!value) {
        return callback(new Error('请选择处理状态'))
      } else {
        callback()
      }
      // checkPending = setTimeout(() => {
      //   if (!Number.isInteger(value)) {
      //     callback(new Error('Please input digits'));
      //   } else {
      //     if (value < 18) {
      //       callback(new Error('Age must be greater than 18'));
      //     } else {
      //       callback();
      //     }
      //   }
      // }, 1000);
    }
    return {
      title: '操作',
      visible: false,
      model: {},
      type:'',
      layout: {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
      },
      confirmLoading: false,
      validatorRules: {
        status: [{ trigger: 'change', validator: checkStatus }],
      },
      url: '/alarm/handleAlarm',
      disableSubmit: false,
    }
  },
  created() {},
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.$refs['form'].clearValidate()
      this.visible = false
      this.$emit('close')
    },
    handleOk() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          that.confirmLoading = true
          const kv={处理:99,上报:1,移交:2/* ,下发:4 */}

          postAction(this.url,{alarmId:this.model.id,toWho:this.model.users,msg:this.model.msg,state:kv[this.type]})
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.error(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.close()
            })
        } else {
          return false
        }
      })
    },
    handleCancel() {
      this.close()
    },
  },
}
</script>

<style lang="less" scoped></style>

