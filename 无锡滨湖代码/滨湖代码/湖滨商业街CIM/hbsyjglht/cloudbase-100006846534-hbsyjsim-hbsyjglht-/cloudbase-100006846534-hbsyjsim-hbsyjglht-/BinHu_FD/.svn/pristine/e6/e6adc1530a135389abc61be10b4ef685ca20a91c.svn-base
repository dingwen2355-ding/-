<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 85%;overflow-y: hidden">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="党组织名称">
          <a-input placeholder="请输入党组织名称" v-decorator.trim="[ 'dzzmc', validatorRules.dzzmc]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="姓名">
          <a-input placeholder="请输入姓名" v-decorator.trim="[ 'xm', validatorRules.xm]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="性别">
          <a-select v-decorator.trim="[ 'xb', validatorRules.xb]">
            <a-select-option value="">请选择</a-select-option>
            <a-select-option value="男">男</a-select-option>
            <a-select-option value="女">女</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="手机号码">
          <a-input placeholder="请输入手机号码" :disabled="sjhmDisabled" v-decorator.trim="[ 'sjhm', validatorRules.sjhm]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
          <a-select v-decorator.trim="[ 'bz', validatorRules.bz]" @change="judge($event)">
            <a-select-option value="">请选择</a-select-option>
            <a-select-option value="党员">党员</a-select-option>
            <a-select-option value="发展对象">发展对象</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="地址">
          <a-input placeholder="请输入地址" v-decorator.trim="[ 'dz', validatorRules.dz]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="出生时间">
          <a-month-picker placeholder="请选择出生时间" v-decorator.trim="[ 'cssj', validatorRules.cssj]" />
        </a-form-item>

        <a-form-item
          v-if="isPartyMember"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="入党时间">
          <a-month-picker placeholder="请选择入党时间" v-decorator.trim="[ 'rdsj', validatorRules.rdsj]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="经度">
          <a-input placeholder="请输入经度" v-decorator.trim="[ 'lng', validatorRules.lng]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="纬度">
          <a-input placeholder="请输入纬度" v-decorator.trim="[ 'lat', validatorRules.lat]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { addRole, duplicateCheck, editRole } from '@/api/api'
import moment from 'moment'
import 'moment/locale/zh-cn'
import { postAction } from '@api/manage'

moment.locale('zh-cn')
export default {
  name: 'PartyMemberModal',
  data() {
    return {
      title: '操作',
      visible: false,
      isPartyMember: false,
      sjhmDisabled: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,
      form: this.$form.createForm(this),
      validatorRules: {
        dzzmc: {
          rules: [
            {
              required: true,
              message: '请输入党组织名称!'
            }
          ]
        },
        xm: {
          rules: [
            {
              required: true,
              message: '请输入角色名称!'
            },
            {
              min: 2,
              max: 30,
              message: '长度在 2 到 30 个字符',
              trigger: 'blur'
            }
          ]
        },
        xb: {
          rules: [
            {
              required: true,
              message: '请选择性别!'
            }
          ]
        },
        sjhm: {
          rules: [
            {
              required: true,
              message: '请输入手机号码!'
            },
            {
              pattern: /^1[3456789]\d{9}$/,
              message: '手机号码格式不正确!'
            }
          ]
        },
        bz: {
          rules: [
            {
              required: true,
              message: '请选择备注!'
            }
          ]
        },
        dz: {
          rules: [
            {
              required: true,
              message: '请输入地址!'
            }
          ]
        },
        cssj: {
          rules: [
            {
              required: true,
              message: '请选择出生时间!'
            }
          ]
        },
        rdsj: {
          rules: [
            {
              required: true,
              message: '请选择入党时间!'
            }
          ]
        },
        lng: {
          rules: [
            {
              required: true,
              message: '请输入经度!'
            }
          ]
        },
        lat: {
          rules: [
            {
              required: true,
              message: '请输入纬度!'
            }
          ]
        }
      }
    }
  },
  created() {
  },
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true

      //编辑页面禁止修改角色编码
      this.sjhmDisabled = !!this.model.sjhm;
      this.isPartyMember = this.model.bz === '党员';
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'dzzmc', 'xm', 'xb', 'sjhm', 'bz', 'dz', 'cssj', 'rdsj', 'lng', 'lat'))
      })

    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let formData = Object.assign(this.model, values)
          const rawCssj = formData.cssj
          const rawRdsj = formData.rdsj

          //如果是发展对象，入党时间可以为空
          if (this.isPartyMember) {
            formData.rdsj = moment(rawRdsj).format('YYYY.MM')
          } else {
            formData.rdsj = ''
          }
          // 使用日期处理库（如 moment.js）来格式化日期为 YYYY.MM 格式
          formData.cssj = moment(rawCssj).format('YYYY.MM')
          let obj
          console.log(formData)
          if (this.title === '新增') {
            obj = postAction('/party/add', formData)
          } else if (this.title === '编辑'){
            obj = postAction('/party/edit', formData)
          }
          obj.then((res) => {
            if (res.success) {
              that.$message.success(res.message)
              that.$emit('ok')
            } else {
              that.$message.warning(res.message)
            }
          }).finally(() => {
            that.confirmLoading = false
            that.close()
          })
        }
      })
    },
    handleCancel() {
      this.close()
    },
    validateRoleCode(rule, value, callback) {
      if (/[\u4E00-\u9FA5]/g.test(value)) {
        callback('角色编码不可输入汉字!')
      } else {
        var params = {
          tableName: 'sys_role',
          fieldName: 'role_code',
          fieldVal: value,
          dataId: this.model.id
        }
        duplicateCheck(params).then((res) => {
          if (res.success) {
            callback()
          } else {
            callback(res.message)
          }
        })
      }
    },
    judge(event) {
      const selectedValue = event // 通过事件对象获取选中的值
      console.log(selectedValue)
      this.isPartyMember = selectedValue === '党员'
    }

  }
}
</script>

<style scoped>

</style>