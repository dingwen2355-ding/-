<template>
  <el-dialog
    class="AddEditingDialog"
    :title="title"
    :visible.sync="addEditDrawerShow"
    width="1000px"
    :modal-append-to-body="false"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <div class="content-tip">
      <span>专家信息</span>
    </div>

    <el-form
      label-width="130px"
      :model="formLabelAlign"
      inline
      :rules="rules"
      ref="formLabelAlign"
      v-loading="loading"
    >
      <el-form-item label="专家姓名" prop="name">
        <el-input
          v-model="formLabelAlign.name"
          placeholder="请输入专家姓名"
          clearable
          :disabled="disabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="idNumber">
        <el-input
          v-model="formLabelAlign.idNumber"
          placeholder="请输入身份证号"
          clearable
          :disabled="disabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="formLabelAlign.gender" placeholder="请选择性别" :disabled="disabled">
          <el-option
            v-for="item in options1"
            :key="item.gender"
            :label="item.label"
            :value="item.gender"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="出生日期" prop="expertsTime">
        <el-date-picker
          v-model="formLabelAlign.expertsTime"
          type="date"
          placeholder="请选择出生日期"
          value-format="yyyy-MM-dd"
          :disabled="disabled"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="常驻地" prop="address">
        <el-input
          v-model="formLabelAlign.address"
          placeholder="请输入常驻地"
          clearable
          :disabled="disabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input
          v-model="formLabelAlign.phone"
          placeholder="请输入联系方式"
          clearable
          :disabled="disabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="工作单位" prop="companyName">
        <el-input
          v-model="formLabelAlign.companyName"
          placeholder="请输入工作单位"
          clearable
          :disabled="disabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="专家类别" prop="expertOfType">
        <el-select v-model="formLabelAlign.expertOfType" placeholder="请选择专家类别" :disabled="disabled">
          <el-option
            v-for="item in expertOfType"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="擅长行业" prop="expertsType">
        <el-select v-model="formLabelAlign.expertsType" placeholder="请选择擅长行业" :disabled="disabled">
          <el-option
            v-for="item in options2"
            :key="item.expertsType"
            :label="item.label"
            :value="item.expertsType"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专家职称" prop="expertsTitle">
        <el-select v-model="formLabelAlign.expertsTitle" placeholder="请选择专家职称" :disabled="disabled">
          <el-option
            v-for="item in options3"
            :key="item.expertsTitle"
            :label="item.label"
            :value="item.expertsTitle"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="行业描述" class="whole-line" prop="experience">
        <el-input
          v-model="formLabelAlign.experience"
          type="textarea"
          :rows="5"
          placeholder="请输入行业描述"
          clearable
          :disabled="disabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="任务类别描述" class="whole-line" prop="experienceType">
        <el-input
          v-model="formLabelAlign.experienceType"
          type="textarea"
          :rows="5"
          placeholder="请输入任务类别描述"
          clearable
          :disabled="disabled"
        ></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer" v-if="title != '详情'">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="submitBtn('formLabelAlign')">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from '@/utils/request'

export default {
  name: 'AddEditingDialog',
  props: ['title', 'rowData', 'showDrawer', 'options2List', 'options3List'],
  data() {
    return {
      loading: false,
      addEditDrawerShow: false,
      formLabelAlign: {
        name: '',
        gender: '',
        expertsTime: '',
        expertsType: '',
        expertsTitle: '',
        companyName: '',
        id: '',
        idNumber: '',
        address: '',
        phone: '',
        experience: '',
        experienceType: '',
        expertOfType: ''
      },
      expertOfType: [
        {
          value: '行业专家',
          label: '行业专家'
        },
        {
          value: '认证专家',
          label: '认证专家'
        }
      ],
      options1: [
        {
          gender: '0',
          label: '男'
        },
        {
          gender: '1',
          label: '女'
        }
      ],
      options2: [],
      options3: [],
      rules: {
        name: [{ required: true, message: '请输入专家姓名', trigger: 'blur' }],
        gender: [{ required: true, message: '请选择专家性别', trigger: 'blur' }],
        // expertsTime: [
        //   { required: true, message: '请选择出生日期', trigger: 'change' }
        // ],
        expertsType: [{ required: true, message: '请选择擅长行业', trigger: 'blur' }],
        expertsTitle: [{ required: true, message: '请选择专家职称', trigger: 'blur' }],
        companyName: [{ required: true, message: '请输入工作单位', trigger: 'blur' }],
        expertOfType: [{ required: true, message: '请选择专家类别', trigger: 'change' }]
      },
      disabled: false
    }
  },
  watch: {
    title(v) {
      if (v != '详情') {
        this.disabled = false
      } else {
        this.disabled = true
      }
    },

    showDrawer(v) {
      if (v.addEditDrawerShow) {
        this.options2 = this.options2List.options2
        this.options3 = this.options3List.options3
        this.addEditDrawerShow = true
      } else {
        this.addEditDrawerShow = false
      }
    },

    rowData(v) {
      if (v) {
        this.formLabelAlign.name = v.name
        this.formLabelAlign.gender = v.sex
        this.formLabelAlign.expertsTime = v.birthday
        this.formLabelAlign.expertsType = v.expertType
        this.formLabelAlign.expertsTitle = v.title
        this.formLabelAlign.companyName = v.company
        this.formLabelAlign.id = v.id
        this.formLabelAlign.idNumber = v.idNumber
        this.formLabelAlign.address = v.address
        this.formLabelAlign.phone = v.phone
        this.formLabelAlign.experience = v.experience
        this.formLabelAlign.expertOfType = v.expertCategory
        this.formLabelAlign.experienceType = v.experienceType
      } else {
        this.formLabelAlign = {
          name: '',
          gender: '',
          expertsTime: '',
          expertsType: '',
          expertsTitle: '',
          companyName: '',
          idNumber: '',
          address: '',
          phone: '',
          experience: '',
          experienceType: '',
          expertOfType: ''
        }
      }
    }
  },
  mounted() { },
  methods: {
    // 关闭弹窗前操作
    handleClose(done) {
      this.$refs['formLabelAlign'].resetFields()
      this.resetBtn()
      this.addEditDrawerShow = false
    },

    // 重置
    resetBtn() {
      this.loading = false
      this.$refs['formLabelAlign'].resetFields()
      if (!this.rowData) {
        this.formLabelAlign = {
          name: '',
          gender: '',
          expertsTime: '',
          expertsType: '',
          expertsTitle: '',
          companyName: '',
          id: '',
          idNumber: '',
          address: '',
          phone: '',
          experience: '',
          experienceType: '',
          expertOfType: ''
        }
      } else {
        this.formLabelAlign.name = this.rowData.name
        this.formLabelAlign.gender = this.rowData.sex
        this.formLabelAlign.expertsTime = this.rowData.birthday
        this.formLabelAlign.expertsType = this.rowData.expertType
        this.formLabelAlign.expertsTitle = this.rowData.title
        this.formLabelAlign.companyName = this.rowData.company
        this.formLabelAlign.id = this.rowData.id
        this.formLabelAlign.idNumber = this.rowData.idNumber
        this.formLabelAlign.address = this.rowData.address
        this.formLabelAlign.phone = this.rowData.phone
        this.formLabelAlign.experience = this.rowData.experience
        this.formLabelAlign.experienceType = this.rowData.experienceType
      }
    },

    // 提交
    submitBtn(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          if (this.title === '修改') {
            let url = '/binhuapis/basicExpert/updateBasicExpertInfo'
            let param = {
              id: this.formLabelAlign.id,
              name: this.formLabelAlign.name,
              sex: Number(this.formLabelAlign.gender),
              birthday: this.formLabelAlign.expertsTime,
              type: this.formLabelAlign.expertsType,
              title: this.formLabelAlign.expertsTitle,
              company: this.formLabelAlign.companyName,
              idNumber: this.formLabelAlign.idNumber,
              address: this.formLabelAlign.address,
              phone: this.formLabelAlign.phone,
              experience: this.formLabelAlign.experience,
              experienceType: this.formLabelAlign.experienceType,
              expertCategory: this.formLabelAlign.expertOfType
            }
            axios.post(url, param).then((res) => {
              console.log(res, '编辑-提交')
              this.loading = false
              if (res.data.code === 200) {
                this.$message.success('提交成功！')
                this.addEditDrawerShow = false
                this.$root.eventBus.$emit('upDataTable', true)
              } else {
                this.$message.warning(res.data.message)
              }
            })
          } else if (this.title === '新增') {
            let url = '/binhuapis/basicExpert/insertBasicExpertInfo'
            let param = {
              name: this.formLabelAlign.name,
              sex: Number(this.formLabelAlign.gender),
              birthday: this.formLabelAlign.expertsTime,
              type: this.formLabelAlign.expertsType,
              title: this.formLabelAlign.expertsTitle,
              company: this.formLabelAlign.companyName,
              idNumber: this.formLabelAlign.idNumber,
              address: this.formLabelAlign.address,
              phone: this.formLabelAlign.phone,
              experience: this.formLabelAlign.experience,
              experienceType: this.formLabelAlign.experienceType,
              expertCategory: this.formLabelAlign.expertOfType
            }
            axios.post(url, param).then((res) => {
              console.log(res, '新增-提交')
              this.loading = false
              if (res.data.code === 200) {
                this.$message.success('提交成功！')
                this.addEditDrawerShow = false
                this.resetBtn()
                this.$root.eventBus.$emit('upDataTable', true)
              } else {
                this.$message.warning(res.data.message)
              }
            })
          }
        } else {
          this.$message.warning('请将信息填写完整！')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
.AddEditingDialog {
  .content-tip {
    height: 40px;
    width: 100%;
    border-bottom: 1px solid #eff2f5;
    display: flex;
    align-items: flex-end;
    margin-bottom: 20px;

    span {
      display: inline-block;
      font-size: 18px;
      font-family: Microsoft YaHei;
      font-weight: bold;
      color: #4e5a6e;
      border-bottom: 2px solid #0093ff;
      padding-bottom: 6px;
    }
  }
  .el-date-editor.el-input,
  .el-date-editor.el-input__inner {
    width: 200px;
  }
  .whole-line {
    width: 100%;

    .el-form-item__content {
      width: 800px;

      .el-date-editor {
        margin-bottom: 10px;
        &:last-of-type {
          margin-bottom: 0px;
        }
      }
    }
  }
}
</style>
