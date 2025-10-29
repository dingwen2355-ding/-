<template>
  <el-dialog
    :title="isCode === 1 ? '编辑' : isCode === 2 ? '详情' : '新增'"
    :visible.sync="dialogVisible"
    width="940px"
    max-height="240px"
    :modal-append-to-body="true"
    :append-to-body="true"
    :before-close="handleClose"
    class="MaterialAgreementReserveAdd"
  >
    <el-form
      :inline="true"
      ref="form"
      :rules="rules"
      :model="form"
      class="MaterialAgreementReserveAdd"
      label-width="120px"
    >
      <el-form-item label="所属区县" prop="streetName">
        <el-select
          v-model="form.streetName"
          filterable
          placeholder="请选择所属区县"
          clearable
          :disabled="isCode === 2"
          @change="changestockName"
        >
          <el-option v-for="item in areaList" :value="item.regionName" :label="item.regionName" :key="item.regionId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="仓库名称" prop="stockName">
        <el-select
          v-model="form.stockName"
          filterable
          placeholder="请选择仓库名称"
          clearable
          :disabled="isCode === 2"
          @visible-change="changestockNameIshow"
        >
          <el-option v-for="item in stockNameList" :value="item.stockName" :label="item.stockName" :key="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="协议名称" prop="agreementName">
        <el-input
          clearable
          v-model="form.agreementName"
          placeholder="请输入协议名称"
          :disabled="isCode === 2"
        ></el-input>
      </el-form-item>
      <el-form-item label="协议时效" prop="datetimerange">
        <el-date-picker
          v-model="form.datetimerange"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :disabled="isCode === 2"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="协议简介">
        <el-input
          type="textarea"
          :rows="2"
          v-model="form.content"
          placeholder="请输入协议简介"
          :disabled="isCode === 2"
        ></el-input>
      </el-form-item>
      <el-form-item label="相关附件" prop="imgList" class="whole-line">
        <el-upload
          list-type="picture-card"
          ref="uploadMaterialAgreement"
          action="/"
          :disabled="isCode === 2"
          v-model="form.fileList"
          :multiple="false"
          :file-list="form.fileList"
          :show-file-list="true"
          :on-change="handleChange"
          :auto-upload="false"
          accept=".xlsx,.xlc,.xlm,.xls,.xlt,.xlw,.csv,.doc,.docx"
        >
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{ file }">
            <img class="el-upload-list__item-thumbnail" :src="file.url | picFilter" alt="" />
            <div class="file-name">{{ file.name }}</div>
            <span class="el-upload-list__item-actions">
              <span class="el-upload-list__item-delete" @click="handleDownload(file)">
                <i class="el-icon-download"></i>
              </span>
              <span v-if="isCode !== 2" class="el-upload-list__item-delete" @click="handleRemove(file)">
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
      </el-form-item>
    </el-form>
    <div class="addDutyInfo">
      <div class="addDutyInfoButton">
        <el-button type="primary" size="medium" :disabled="isCode === 2" @click="addDutyInfoBtn()"> 新增</el-button>
      </div>
      <el-table
        :data="form.resourceAgreementStuffs"
        border
        style="width: 100%; margin-top: 15px"
        class="tableInfo"
        max-height="220px"
      >
        <el-table-column sortable type="index" label="序号" width="70"></el-table-column>
        <el-table-column prop="stuffName" show-overflow-tooltip label="物资名称"></el-table-column>
        <el-table-column prop="stuffUnit" show-overflow-tooltip label="计量单位"></el-table-column>
        <el-table-column prop="stuffModel" show-overflow-tooltip label="物资型号"></el-table-column>
        <el-table-column prop="stuffBrand" show-overflow-tooltip label="物资品牌"></el-table-column>
        <el-table-column prop="stuffQuantity" show-overflow-tooltip label="储备数量"></el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="text" :disabled="isCode === 2" size="medium" @click="delDutyInfo(scope.row, scope.$index)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      :title="'新增物资'"
      :visible.sync="addDutyInfoDialog"
      width="700px"
      :modal-append-to-body="true"
      append-to-body
      :before-close="handleClose1"
      :close-on-click-modal="false"
    >
      <el-form
        label-width="100px"
        :model="formLabelAlign"
        :rules="formLabelRules"
        ref="formLabelAlign"
        v-loading="loading"
      >
        <el-form-item label="物资名称" prop="stuffName">
          <!-- <el-input v-model="formLabelAlign.stuffName" placeholder="请输入物资名称" clearable></el-input> -->
          <el-select
            v-model="formLabelAlign.stuffName"
            filterable
            placeholder="请选择物资"
            clearable
            @change="changestuffName"
          >
            <el-option v-for="item in stuffNameList" :value="item.id" :label="item.stuffName" :key="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="计量单位" prop="stuffUnit">
          <el-input v-model="formLabelAlign.stuffUnit" placeholder="请输入计量单位" clearable disabled></el-input>
        </el-form-item>
        <el-form-item label="物资型号" prop="stuffModel">
          <el-input v-model="formLabelAlign.stuffModel" placeholder="请输入物资型号" clearable></el-input>
        </el-form-item>
        <el-form-item label="物资品牌" prop="stuffBrand">
          <el-input v-model="formLabelAlign.stuffBrand" placeholder="请输入物资品牌" clearable></el-input>
        </el-form-item>
        <el-form-item label="储备数量" prop="stuffQuantity">
          <el-input
            oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
            v-model="formLabelAlign.stuffQuantity"
            placeholder="请输入储备数量"
            clearable
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose1()">取 消</el-button>
        <el-button type="primary" @click="submitBtn('formLabelAlign')">确 定</el-button>
      </div>
    </el-dialog>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose()" v-if="isCode !== 2">取 消</el-button>
      <el-button type="primary" @click="handleOk" v-if="isCode !== 2">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'
import $ from 'jquery'
export default {
  name: 'MaterialAgreementReserveAdd',
  components: {},
  data() {
    const validateLogo = (rule, value, callback) => {
      if (this.form.fileList && this.form.fileList.length === 0) {
        callback(new Error('请上传相关附件'))
      } else {
        callback()
      }
    }

    return {
      form: {
        streetName: '',
        stockName: '',
        agreementName: '',
        content: '',
        datetimerange: [],
        fileList: []
      },

      rules: {
        streetName: [{ required: true, message: '请选择所属区县', trigger: 'change' }],
        stockName: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        agreementName: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        datetimerange: [{ required: true, message: '请选择后提交', trigger: 'blur' }],
        imgList: [{ required: true, validator: validateLogo, trigger: 'change' }]
      },
      areaList: [],

      addDutyInfoDialog: false,
      loading: false,
      formLabelAlign: {
        stuffName: '',
        stuffUnit: '',
        stuffModel: '',
        stuffBrand: '',
        stuffQuantity: '',
        stuffId: ''
      },
      formLabelRules: {
        stuffName: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        stuffQuantity: [{ required: true, message: '请输入后提交', trigger: 'blur' }]
      },
      stuffNameList: [],
      uploadFile: [],
      stockNameList: []
    }
  },
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    formInfo: {
      handler(v) {
        if (v) {
          this.form = JSON.parse(JSON.stringify(v))
          this.form.fileList = []
          if (this.form.timeLimit) {
            // this.form.datetimerange = this.form.timeLimit.split(',')
            this.$set(this.form, 'datetimerange', this.form.timeLimit.split(','))
          }
          if (v.streetName) {
            this.changestockName(v.streetName)
          }
          if (v.file) {
            v.file.split(',').forEach((r) => {
              let flieUrl = r.split('-')[r.split('-').length - 1]
              let obj = {
                url: r,
                raw: new File([r], flieUrl),
                name: flieUrl
              }
              this.form.fileList.push(obj)
              let obj1 = {
                url: r,
                name: flieUrl
              }
              this.uploadFile.push(obj1)
            })
          }
        }
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    this.getAreaList()
    this.getStuffNameList()
  },
  filters: {
    picFilter(url) {
      if (
        url.includes('.jpg') ||
        url.includes('.png') ||
        url.includes('.gif') ||
        url.includes('.bmp') ||
        url.includes('.jpeg') ||
        url.includes('.JPG') ||
        url.includes('.PNG') ||
        url.includes('.GIF') ||
        url.includes('.BMP') ||
        url.includes('.JPEG')
      ) {
        return url
      } else if (url.includes('.docx')) {
        return require('../../../assets/file/fileImg.png')
      } else if (url.includes('.pptx')) {
        return require('../../../assets/file/power.png')
      } else if (url.includes('.xlsx')) {
        return require('../../../assets/file/excel.png')
      } else if (url.includes('.pdf') || url.includes('.PDF')) {
        return require('../../../assets/file/pdf.png')
      } else {
        return require('../../../assets/file/other.png')
      }
    }
  },
  methods: {
    // 下载选中的图片
    handleDownload(file) {
      // console.log(file, '下载选中的图片')
      this.getFileAndDownload(file.name, file.url)
    },

    getFileAndDownload(fileName, url) {
      var x = new XMLHttpRequest()
      x.open('GET', url, true)
      x.responseType = 'blob'
      x.onload = function (e) {
        var blob = x.response
        if ('msSaveOrOpenBlob' in navigator) {
          // IE导出
          window.navigator.msSaveOrOpenBlob(blob, fileName)
        } else {
          var a = document.createElement('a')
          a.download = fileName
          a.href = URL.createObjectURL(blob)
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }
      x.send()
    },
    changestockNameIshow(val) {
      // console.log(val)
      if (val && !this.form.streetName) {
        this.$message.warning('请先选择区县！')
      }
    },
    changestockName(val) {
      if (val) {
        let regionId = this.areaList.filter((v) => v.regionName === val)[0].regionId
        let userLevel = JSON.parse(localStorage.getItem('userLevel'))
        let url = '/gtw/EIMS-management/stockInfo/queryAllStock?userLevel=' + userLevel + '&regionId=' + regionId
        axios.get(url).then((res) => {
          if (res.data.code === 200) {
            let data = res.data.data || []
            this.stockNameList = data
          }
        })
      } else {
        this.stockNameList = []
        this.form.stockName = ''
      }
    },
    getStuffNameList() {
      let url = '/gtw/EIMS-management/resourceList/queryAllResource'
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.stuffNameList = data
      })
    },
    changestuffName(val) {
      let arr = this.stuffNameList.filter((v) => v.id === val)
      this.formLabelAlign.stuffName = arr[0].stuffName
      this.formLabelAlign.stuffId = arr[0].id
      this.formLabelAlign.stuffUnit = arr[0].stuffUnit
    },
    // 添加应急值守信息信息
    addDutyInfoBtn() {
      this.addDutyInfoDialog = true
    },
    // 确定
    submitBtn(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          if (!this.form.resourceAgreementStuffs) {
            this.form.resourceAgreementStuffs = []
          }
          this.form.resourceAgreementStuffs.push({
            stuffName: this.formLabelAlign.stuffName,
            stuffId: this.formLabelAlign.stuffId,
            stuffUnit: this.formLabelAlign.stuffUnit,
            stuffModel: this.formLabelAlign.stuffModel,
            stuffBrand: this.formLabelAlign.stuffBrand,
            stuffQuantity: this.formLabelAlign.stuffQuantity
          })
          this.loading = false
          this.$message.success('添加成功！')
          this.handleClose1()
        } else {
          this.$message.warning('请将信息填写完整！')
          return false
        }
      })
    },

    // 删除
    delDutyInfo(row, index) {
      this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.form.resourceAgreementStuffs.splice(index, 1)
          this.currentPage = 1
          this.$message.success('删除成功！')
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 关闭添加应急值守信息信息前的操作
    handleClose1(done) {
      this.formLabelAlign = {
        stuffName: '',
        stuffUnit: '',
        stuffModel: '',
        stuffBrand: '',
        stuffQuantity: '',
        stuffId: ''
      }
      this.$refs.formLabelAlign.resetFields()
      this.addDutyInfoDialog = false
      this.loading = false
    },
    handleChange(file, fileList) {
      const isLt2M = file.size / 1024 / 1024 < 10
      let existFile = fileList.slice(0, fileList.length - 1).find((f) => f.name === file.name)
      if (!isLt2M) {
        this.$message.warning('上传文件大小不能超过 10MB!')
        fileList.pop()
        return false
      }
      if (existFile) {
        this.$message.error('当前文件已经存在!')
        fileList.pop()
        return false
      }

      let url = '/gtw/EIMS-management/resourceAgreement/uploadFile'
      let formData = new FormData()
      formData.append('uploadFile', file.raw)
      axios
        .post(url, formData)
        .then((res) => {
          if (res.data.code === 200) {
            this.uploadFile.push({
              url: res.data.data,
              name: file.name
            })
            this.form.fileList = fileList
            this.$message.success('上传成功！')
          } else {
            fileList.pop()
            this.form.fileList = fileList
            this.$message.error('上传失败！')
          }
        })
        .catch(() => {
          this.$message.error('上传失败！')
        })
    },
    handleRemove(file, fileList) {
      if (this.isCode === 0 || this.isCode === 1) {
        this.$confirm('确认删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let fileUrl = ''
            if (this.uploadFile && this.uploadFile.length > 0) {
              this.uploadFile.forEach((item) => {
                if (item.name === file.name) {
                  fileUrl = item.url
                }
              })
              let url = '/gtw/EIMS-management/resourceAgreement/deleteFile?fileUrl=' + fileUrl
              axios.post(url).then((res) => {
                if (res.data.code === 200) {
                  this.$message.success('删除成功！')
                  let arr = this.$refs.uploadMaterialAgreement.uploadFiles
                  let index = arr.indexOf(file)
                  // console.log(arr, index)
                  this.uploadFile.splice(index, 1)
                  this.form.fileList.splice(index, 1)
                  arr.splice(index, 1)
                } else {
                  this.$message.error('删除失败！')
                }
              })
            }
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
        return false
      }
    },
    getAreaList() {
      let url = `/gtw/EIMS-management/companyDict/queryRegion?userLevel=${localStorage.level}&userId=${localStorage.userId}`
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    handleClose(done) {
      this.reset()
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.$refs.form.resetFields()
      this.form = {}
      //   this.formInfo = {}
      this.stockNameList = []
      this.uploadFile = []
      this.form.fileList = []
    },
    handleOk() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.resourceAgreementStuffs && this.form.resourceAgreementStuffs.length > 0) {
            let params = {}
            params.streetName = this.form.streetName
            params.stockName = this.form.stockName
            params.agreementName = this.form.agreementName
            params.startTime = this.form.datetimerange[0]
            params.endTime = this.form.datetimerange[1]
            params.content = this.form.content
            params.resourceAgreementStuffs = this.form.resourceAgreementStuffs
            params.file = this.uploadFile
              .map((v) => {
                return v.url
              })
              .join(',')
            let url = ''
            if (this.form && this.form.id) {
              params.id = this.form.id
              url = '/gtw/EIMS-management/resourceAgreement/updateAgreement'
            } else {
              url = '/gtw/EIMS-management/resourceAgreement/insertAgreement'
            }
            axios.post(url, params).then((r) => {
              if (r.data.code === 200) {
                this.$message.success('操作成功')
                this.$emit('success')
                this.handleClose()
              } else {
                this.$message.error(r.data.message)
              }
            })
          } else {
            this.$message.warning('请添加物资！')
          }
        } else {
          // console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
.MaterialAgreementReserveAdd.el-form--inline {
  .el-select {
    display: block;
  }
  .el-form-item__content {
    width: 300px;
  }

  .el-date-editor {
    width: 300px;
  }

  .whole-line {
    width: 100%;

    .el-form-item__content {
      width: 780px;
    }
  }
}
</style>
<style lang="scss" scoped>
.MaterialAgreementReserveAdd {
  .el-dialog {
    margin-top: 10vh !important;
  }
  .el-dialog__body {
    max-height: 700px;
    overflow: auto;
  }
  .whole-line {
    .file-name {
      position: absolute;
      bottom: 20px;
      left: 5px;
      right: 2px;
    }
  }
  .addDutyInfo {
    .addDutyInfoButton {
      display: flex;
      justify-content: flex-end;
    }
  }
}
</style>
