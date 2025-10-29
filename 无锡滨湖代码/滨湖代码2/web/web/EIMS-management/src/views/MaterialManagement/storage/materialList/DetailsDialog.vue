<template>
  <el-dialog
    class="detailsDialog"
    :title="title"
    :visible.sync="dialogVisible"
    width="1000px"
    :modal-append-to-body="true"
    append-to-body
    @close="setDangerousDetailsDialogClose"
  >
    <el-form
      :rules="rules"
      ref="formDataref"
      :model="formData"
      label-width="150px"
      class="demo-ruleForm"
      :inline="true"
    >
      <el-form-item label="物资名称:" prop="stuffName">
        <el-input v-model="formData.stuffName" :disabled="disabled"></el-input>
      </el-form-item>
      <el-form-item label="计量单位:" prop="stuffUnit">
        <el-input v-model="formData.stuffUnit" :disabled="disabled"></el-input>
      </el-form-item>
      <el-form-item label="物资类型:" class="propCascader" prop="valueResource">
        <el-cascader
          v-model="formData.valueResource"
          :options="optionsResource"
          :props="resourceCascaderProps"
          placeholder="请选择物资类型"
          clearable
          filterable
          style="width: 285px"
          :disabled="disabled"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="可参与的救援类型:" class="propCascader" prop="valueHelp">
        <el-cascader
          v-model="formData.valueHelp"
          :options="optionsHelp"
          :props="helpCascaderProps"
          placeholder="请选择可参与的救援类型"
          clearable
          filterable
          style="width: 285px"
          :disabled="disabled"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="是否为特种机器:">
        <el-switch
          v-model="formData.isSpecial"
          active-value="Y"
          inactive-value="N"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :disabled="disabled"
        ></el-switch>
      </el-form-item>
      <el-form-item label="排序号:">
        <el-input v-model="formData.order" :disabled="disabled"></el-input>
      </el-form-item>
      <el-form-item label="简要说明:">
        <el-input
          v-model="formData.contents"
          type="textarea"
          :rows="5"
          :disabled="disabled"
          style="width: 735px"
        ></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer" v-show="title != '详情'">
      <el-button @click="resetBtn">重置</el-button>
      <el-button type="primary" @click="submitBtn" plain>提交</el-button>
    </span>
  </el-dialog>
</template>

<script>
import axios from 'axios'
// import qs from 'qs'
export default {
  name: 'DangerousDetailsDialog',
  props: ['showDialog', 'title', 'dialogData'],
  data() {
    var ResourceCascader = (rule, value, callback) => {
      if (this.formData.valueResource.length == 0) {
        callback(new Error('请选择物资类型'))
      } else {
        callback()
      }
    }
    var HelpCascader = (rule, value, callback) => {
      if (this.formData.valueHelp[0] == '' || this.formData.valueHelp.length == 0) {
        callback(new Error('请选择可参与的救援类型'))
      } else {
        callback()
      }
    }
    return {
      titles: '详情',
      optionsHelp: [],
      optionsResource: [],
      dialogVisible: false,
      disabled: false,
      formData: {
        contents: '',
        helpType: '',
        helpTypeName: '',
        id: '',
        index: 0,
        isSpecial: 'N',
        order: '1',
        parentStuffType: 0,
        stuffName: '',
        stuffType: 0,
        stuffTypeName: '',
        stuffUnit: '',
        valueHelp: [],
        valueResource: []
      },
      rules: {
        stuffName: [{ required: true, message: '请输入物资名称', trigger: 'blur' }],
        stuffUnit: [{ required: true, message: '请输入计量单位', trigger: 'blur' }],
        valueResource: [{ validator: ResourceCascader, trigger: 'change' }],
        valueHelp: [{ validator: HelpCascader, trigger: 'change' }]
      },
      resourceCascaderProps: {
        checkStrictly: true,
        expandTrigger: 'hover',
        value: 'id',
        label: 'dictName',
        children: 'subList'
      },
      helpCascaderProps: {
        multiple: true,
        checkStrictly: true,
        expandTrigger: 'hover',
        value: 'id',
        label: 'dictName'
      }
    }
  },
  created() {
    this.getMateriaLiat()
  },
  methods: {
    // 重置
    resetBtn() {
      this.formData = {
        contents: '',
        helpType: '',
        helpTypeName: '',
        id: '',
        index: 0,
        isSpecial: 'N',
        order: '1',
        parentStuffType: 0,
        stuffName: '',
        stuffType: 0,
        stuffTypeName: '',
        stuffUnit: '',
        valueHelp: [],
        valueResource: []
      }
    },
    setDangerousDetailsDialogClose() {
      this.$refs.formDataref.resetFields()
      this.$emit('update:showDialog', false)
      this.$emit('update:dialogData', '')
      this.formData = {
        contents: '',
        helpType: '',
        helpTypeName: '',
        id: '',
        index: 0,
        isSpecial: 'N',
        order: '1',
        parentStuffType: 0,
        stuffName: '',
        stuffType: 0,
        stuffTypeName: '',
        stuffUnit: '',
        valueHelp: [],
        valueResource: []
      }
    },
    getMateriaLiat() {
      axios
        .get('/gtw/EIMS-management/resourceList/queryResourceType?')
        .then((r) => {
          // console.log(r.data.data, 11)
          this.optionsResource = this.getTreeData(r.data.data)
          // console.log(this.optionsResource, 12)

          // this.optionsResource = r.data.data
        })
        .catch((v) => {
          // console.log(v)
        })
      axios
        .get('/gtw/EIMS-management/resourceList/queryHelpType?')
        .then((r) => {
          // console.log(r.data.data)
          this.optionsHelp = r.data.data
        })
        .catch((v) => {
          // console.log(v)
        })
    },
    getTreeData(data) {
      // 循环遍历json数据
      for (var i = 0; i < data.length; i++) {
        if (data[i].subList.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].subList = undefined
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].subList)
        }
      }
      return data
    },
    submitBtn() {
      // console.log(this.title)
      if (this.title == '修改') {
        // console.log(this.formData.valueHelp, 12)
        // console.log(this.formData.valueHelp.length, 13)
        this.$refs.formDataref.validate((valid) => {
          if (!valid) {
            this.$message.warning('请将信息填写完整！')
            return false
          } else {
            // console.log(this.formData.subTypeId, 78)
            let helpvalue = ''
            if (this.formData.valueHelp.length > 0) {
              this.formData.valueHelp.map((item) => {
                helpvalue += item + ','
                return helpvalue
              })
              // 判断截取最后一个逗号
              if (helpvalue.length > 0) {
                helpvalue = helpvalue.substr(0, helpvalue.length - 1)
              }
            }
            let mainTypeId = null
            let subTypeId = null
            if (this.formData.valueResource.length === 1) {
              mainTypeId = 47
              subTypeId = this.formData.valueResource[0]
            } else {
              mainTypeId = this.formData.valueResource[0]
              subTypeId = this.formData.valueResource[1]
            }
            let url = '/gtw/EIMS-management/resourceList/updateResource?'
            let param = {
              ...{
                order: this.formData.order,
                contents: this.formData.contents,
                helpType: helpvalue,
                id: this.formData.id,
                isSpecial: this.formData.isSpecial,
                parentStuffType: mainTypeId,
                stuffName: this.formData.stuffName,
                stuffType: subTypeId,
                stuffUnit: this.formData.stuffUnit
              }
            }
            // console.log(param)
            axios.post(url, param).then((res) => {
              // console.log(res, '修改-提交')
              if (res.data.code === 200) {
                if (res.data.data == null) {
                  this.$message.success('修改成功！')
                  this.$emit('update:showDialog', false)
                  this.$emit('success')
                } else {
                  this.$message.warning(res.data.data)
                }
              } else {
                this.$message.warning(res.data.message)
              }
            })
          }
        })
      } else {
        this.$refs.formDataref.validate((valid) => {
          if (!valid) {
            this.$message.warning('请将信息填写完整！')
            return false
          } else {
            let helpvalue = ''
            if (this.formData.valueHelp.length > 0) {
              this.formData.valueHelp.map((item) => {
                helpvalue += item + ','
                return helpvalue
              })
              // 判断截取最后一个逗号
              if (helpvalue.length > 0) {
                helpvalue = helpvalue.substr(0, helpvalue.length - 1)
              }
            }
            let mainTypeId = null
            let subTypeId = null
            if (this.formData.valueResource.length === 1) {
              mainTypeId = 47
              subTypeId = this.formData.valueResource[0]
            } else {
              mainTypeId = this.formData.valueResource[0]
              subTypeId = this.formData.valueResource[1]
            }
            let url = '/gtw/EIMS-management/resourceList/insertResource?'
            let param = {
              ...{
                order: this.formData.order,
                contents: this.formData.contents,
                helpType: helpvalue,
                id: this.formData.id,
                isSpecial: this.formData.isSpecial,
                parentStuffType: mainTypeId,
                stuffName: this.formData.stuffName,
                stuffType: subTypeId,
                stuffUnit: this.formData.stuffUnit
              }
            }
            // console.log(param)
            axios.post(url, param).then((res) => {
              // console.log(res, '增加-提交')
              if (res.data.code === 200) {
                if (res.data.data == null) {
                  this.$message.success('添加成功！')
                  this.$emit('update:showDialog', false)
                  this.$emit('success')
                } else {
                  this.$message.warning(res.data.data)
                }
              } else {
                this.$message.warning(res.data.message)
              }
            })
          }
        })
      }
    }
  },
  watch: {
    title(v) {
      // console.log(v, 88)
      this.titles = v
      if (v != '详情') {
        this.disabled = false
      } else {
        this.disabled = true
      }
    },
    showDialog(v) {
      this.dialogVisible = v
    },
    dialogData(v) {
      // console.log(v, 'adhfakhdflajdshfl')
      if (v) {
        this.formData = JSON.parse(JSON.stringify(v))

        if (this.formData.parentStuffType < 100) {
          this.formData.valueResource = [this.formData.stuffType]
        } else {
          this.formData.valueResource = [this.formData.parentStuffType, this.formData.stuffType]
        }

        this.formData.valueHelp = this.formData.helpType.split(',')
        // console.log(this.formData.valueResource)
      }
    }
  }
}
</script>
<style lang="scss">
.detailsDialog {
  .el-form-item__content {
    width: 285px;
  }
  .propCascader {
    .el-form-item__label:before {
      content: '*';
      color: #ff4949;
      margin-right: 4px;
    }
  }
}
</style>
