<template>
  <el-drawer
    :title="title"
    :visible.sync="addEditDrawerShow"
    direction="rtl"
    size="70%"
    class="AddEditDrawer"
    :before-close="handleClose"
    modal-append-to-body
    :modal="false"
  >
    <div class="drawer-content">
      <div class="content-tip">
        <span>专家信息</span>
      </div>

      <el-form
        label-position="top"
        label-width="80px"
        :model="formLabelAlign"
        inline
        :rules="rules"
        ref="formLabelAlign"
        v-loading="loading"
      >
        <el-form-item label="专家姓名" class="item-half" prop="name">
          <el-input v-model="formLabelAlign.name" placeholder="请输入专家姓名" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" class="item-half" prop="idNumber">
          <el-input v-model="formLabelAlign.idNumber" placeholder="请输入身份证号" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="性别" class="item-half" prop="gender">
          <el-select v-model="formLabelAlign.gender" placeholder="请选择性别" :disabled="disabled">
            <el-option v-for="item in options1" :key="item.gender" :label="item.label" :value="item.gender"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" class="item-half" prop="expertsTime">
          <el-date-picker
            v-model="formLabelAlign.expertsTime"
            type="date"
            placeholder="请选择出生日期"
            value-format="yyyy-MM-dd"
            :disabled="disabled"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="常驻地" class="item-half" prop="address">
          <el-input v-model="formLabelAlign.address" placeholder="请输入常驻地" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" class="item-half" prop="phone">
          <el-input v-model="formLabelAlign.phone" placeholder="请输入联系方式" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="工作单位" class="item-half" prop="companyName">
          <el-input v-model="formLabelAlign.companyName" placeholder="请输入工作单位" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="擅长行业" class="item-half" prop="expertsType">
          <el-select v-model="formLabelAlign.expertsType" placeholder="请选择擅长行业" :disabled="disabled">
            <el-option v-for="item in options2" :key="item.expertsType" :label="item.label" :value="item.expertsType"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专家职称" class="item-half" prop="expertsTitle">
          <el-select v-model="formLabelAlign.expertsTitle" placeholder="请选择专家职称" :disabled="disabled">
            <el-option v-for="item in options3" :key="item.expertsTitle" :label="item.label" :value="item.expertsTitle"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="行业描述" class="item-all" prop="experience">
          <el-input
            v-model="formLabelAlign.experience"
            type="textarea"
            :rows="5"
            placeholder="请输入行业描述"
            clearable
            :disabled="disabled"
          ></el-input>
        </el-form-item>
        <el-form-item label="任务类别描述" class="item-all" prop="experienceType">
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
    </div>
    <div class="drawer-bottome" v-show="title != '详情'">
      <el-button class="reset" @click="resetBtn()">重置</el-button>
      <el-button class="submit" @click="submitBtn('formLabelAlign')">提交</el-button>
    </div>
  </el-drawer>
</template>

<script>
import axios from '@/utils/request'

export default {
  name: 'AddEditDrawer',
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
        experienceType: ''
      },
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
        name: [
          { required: true, message: '请输入专家姓名', trigger: 'change' }
        ],
        gender: [
          { required: true, message: '请选择专家性别', trigger: 'change' }
        ],
        // expertsTime: [
        //   { required: true, message: '请选择出生日期', trigger: 'change' }
        // ],
        expertsType: [
          { required: true, message: '请选择擅长行业', trigger: 'change' }
        ],
        expertsTitle: [
          { required: true, message: '请选择专家职称', trigger: 'change' }
        ],
        companyName: [
          { required: true, message: '请输入工作单位', trigger: 'change' }
        ]
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
          experienceType: ''
        }
      }
    }
  },
  mounted() {

  },
  methods: {

    // 关闭弹窗前操作
    handleClose(done) {
      this.$refs['formLabelAlign'].resetFields()
      this.resetBtn()
      done()
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
          experienceType: ''
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
              experienceType: this.formLabelAlign.experienceType
            }
            axios.post(url, param).then(res => {
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
              experienceType: this.formLabelAlign.experienceType
            }
            axios.post(url, param).then(res => {
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
.AddEditDrawer {
  .el-drawer__header {
    width: 100%;
    height: 64px;
    background: #eff2f5;
    font-size: 24px;
    font-family: Microsoft YaHei;
    font-weight: bold;
    color: #4e5a6e;
    padding-top: 0px;
    margin-bottom: 10px;
    padding-left: 30px;

    .el-drawer__close-btn {
      font-size: 30px;
    }
  }

  .el-drawer__body {
    padding-bottom: 0;
    position: relative;

    .drawer-content {
      width: 100%;
      height: calc(100% - 64px);
      padding: 0px 30px;
      box-sizing: border-box;

      .content-tip {
        height: 40px;
        width: 100%;
        border-bottom: 1px solid #eff2f5;
        display: flex;
        align-items: flex-end;

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

      .el-form {
        margin-top: 20px;

        .el-form-item {
          margin-right: 30px !important;
        }

        .el-form-item__content {
          .el-input__inner {
            height: 36px !important;
            line-height: 36px;
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #202d40;

            .el-range-input {
              font-size: 14px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #202d40;
            }
          }
          .el-select {
            width: 100%;
          }
          .el-textarea {
            width: 100%;

            textarea {
              resize: none;
              font-size: 16px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #202d40;
            }
          }
          .el-date-editor {
            width: 30%;
            margin-right: 30px;
            .el-range__icon,
            .el-range-separator,
            .el-range__close-icon {
              line-height: 28px;
            }

            .el-range-separator {
              width: 10%;
            }
          }
        }

        .el-form-item__label {
          font-size: 16px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #606d80;
          padding: 0px;
        }

        .item-half {
          width: 30%;

          .el-date-editor {
            width: 100%;
          }
        }
        .item-all {
          width: 100%;
        }
      }
    }

    .drawer-bottome {
      width: 100%;
      height: 64px;
      background: #ffffff;
      box-shadow: 0px 0px 12px 0px rgba(0, 0, 0, 0.15);
      position: absolute;
      bottom: 0px;
      left: 0px;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      padding: 0px 30px;
      box-sizing: border-box;

      .reset {
        width: 90px;
        height: 40px;
        border: 1px solid #dbe0e7;
        border-radius: 2px;
        font-size: 18px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #666666;
        padding: 0;
      }

      .submit {
        width: 90px;
        height: 40px;
        background: linear-gradient(90deg, #0093ff 0%, #056cf2 100%);
        border-radius: 2px;
        font-size: 18px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #ffffff;
        padding: 0;
        border: 0 none;
      }
    }
  }
}
</style>
