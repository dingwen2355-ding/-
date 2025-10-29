<template>
  <el-dialog
    class="detailsDialog"
    :title="formInfo ? '编辑' : '新增'"
    :visible.sync="dialogVisible"
    width="940px"
    :modal-append-to-body="true"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <el-form :inline="true" ref="formref" :rules="rules" :model="form" label-width="120px">
      <el-form-item label="所属区县" prop="regionName">
        <el-select
          v-model="form.regionName"
          @change="regionNamechange"
          filterable
          placeholder="请选择所属区县"
          clearable
        >
          <el-option
            v-for="item in areaList"
            :value="item.regionId"
            :label="item.regionName"
            :key="item.regionId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="仓库名称" prop="stockName">
        <el-tooltip effect="dark" :disabled="disabledTooltip" content="请先输入区县名" placement="top">
          <el-select v-model="form.stockName" :disabled="disabled" filterable placeholder="请输入仓库名称" clearable>
            <el-option v-for="item in stockList" :value="item.id" :label="item.stockName" :key="item.id"></el-option>
          </el-select>
        </el-tooltip>
      </el-form-item>
    </el-form>
    <div class="addDutyInfo">
      <el-button type="primary" size="medium" plain @click="addDutyInfoBtn()">新增</el-button>
      <el-table :data="form.saferInfos" border style="width: 100%; margin-top: 15px" max-height="400px">
        <el-table-column sortable type="index" label="序号" width="70"></el-table-column>
        <el-table-column prop="stuffName" show-overflow-tooltip label="物资名称"></el-table-column>
        <el-table-column prop="stuffUnit" show-overflow-tooltip label="计量单位"></el-table-column>
        <el-table-column prop="stuffModel" show-overflow-tooltip label="物资型号"></el-table-column>
        <el-table-column prop="stuffBrand" show-overflow-tooltip label="物资品牌"></el-table-column>
        <el-table-column prop="stuffLimit" show-overflow-tooltip label="储备限额"></el-table-column>
        <el-table-column prop="stuffFloat" show-overflow-tooltip label="浮动值"></el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="text" size="medium" @click="delDutyInfo(scope.row, scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
      :title="'新增'"
      :visible.sync="addDutyInfoDialog"
      width="800px"
      :modal-append-to-body="true"
      append-to-body
      :before-close="handleClose1"
      :close-on-click-modal="false"
    >
      <el-form
        label-width="120px"
        :model="formLabelAlign"
        :rules="formLabelRules"
        ref="formLabelAlign"
        v-loading="loading"
        :inline="true"
      >
        <el-form-item label="物资名称" prop="stuffName">
          <el-select
            v-model="formLabelAlign.stuffName"
            filterable
            placeholder="请输入物资名称"
            clearable
            @change="materialNameChange"
          >
            <el-option
              v-for="item in materialNameList"
              :value="item.stuffName"
              :label="item.stuffName"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="物资型号" prop="stuffModel">
          <el-input v-model="formLabelAlign.stuffModel" placeholder="请输入物资型号" clearable></el-input>
        </el-form-item>
        <el-form-item label="物资品牌" prop="stuffBrand">
          <el-input v-model="formLabelAlign.stuffBrand" placeholder="请输入物资品牌" clearable></el-input>
        </el-form-item>
        <el-form-item label="计量单位" prop="stuffUnit">
          <el-input
            v-model="formLabelAlign.stuffUnit"
            placeholder="请输入计量单位"
            :disabled="true"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="储备限额" prop="stuffLimit">
          <el-input
            oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
            v-model="formLabelAlign.stuffLimit"
            placeholder="请输入储备限额（数字）"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="浮动值" prop="stuffFloat">
          <el-input
            oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
            v-model="formLabelAlign.stuffFloat"
            placeholder="请输入浮动值（数字）"
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
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'

export default {
  name: 'AddressBookAdd',
  components: {},
  data() {
    return {
      form: {
        regionName: [],
        stockName: [],
        saferInfos: []
      },
      rules: {
        regionName: [{ required: true, message: '请输入区县名', trigger: 'blur' }]
      },
      areaList: [],
      stockList: [],
      materialNameList: [],
      fileList: [],
      addDutyInfoDialog: false,
      loading: false,
      formLabelAlign: {
        areaName: '', // 区县
        stockName: '', // 仓库名称
        stuffName: '', // 物资名称
        stuffUnit: '', // 计算单位
        stuffModel: '', // 物资型号
        stuffBrand: '', // 物资品牌
        stuffLimit: '', // 储备限额
        stuffFloat: '' // 浮动值
      },
      formLabelRules: {
        stuffName: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        stuffLimit: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        stuffFloat: [{ required: true, message: '请输入后提交', trigger: 'blur' }]
      },
      disabled: true,
      disabledTooltip: false
    }
  },
  props: ['dialogVisible', 'formInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    formInfo: {
      handler(v) {
        this.fileList = []
        if (v) {
          this.form = JSON.parse(JSON.stringify(v))
        }
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    this.getAreaList()
  },
  methods: {
    // open() {
    //   // this.$nextTick(() => {
    //   //   this.$refs.formref.resetFields() // 将resetFields方法用nextTick包裹，否则报错
    //   // })
    // },
    // 添加弹框
    addDutyInfoBtn() {
      this.$refs.formref.validate((valid) => {
        if (valid) {
          this.addDutyInfoDialog = true
          let params = {
            regionId: this.form.regionName
            // stock: this.form.stockName.id
          }
          if (this.form.stockName) {
            params.stockId = this.form.stockName
          }
          let url = '/gtw/EIMS-management/resourceList/queryAllResource'
          axios.get(url, { params }).then((res) => {
            let data = res.data.data || []
            // console.log(data, 11)
            this.materialNameList = data
          })
        } else {
          this.$message.warning('请将信息填写完整！')
          return false
        }
      })
    },
    // 确定
    submitBtn(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          if (!this.form.saferInfos) {
            this.form.saferInfos = []
          }
          this.form.saferInfos.push({
            areaId: this.form.regionName,
            stockId: this.form.stockName,
            stuffId: this.materialNameList.filter((v) => v.stuffName === this.formLabelAlign.stuffName)[0].id,
            stuffName: this.formLabelAlign.stuffName,
            stuffUnit: this.formLabelAlign.stuffUnit,
            stuffModel: this.formLabelAlign.stuffModel,
            stuffBrand: this.formLabelAlign.stuffBrand,
            stuffLimit: this.formLabelAlign.stuffLimit,
            stuffFloat: this.formLabelAlign.stuffFloat
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
          this.form.saferInfos.splice(index, 1)
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
        stuffId: '',
        stuffName: '',
        stuffModel: '',
        stuffBrand: '',
        stuffUnit: '',
        stuffLimit: '',
        stuffFloat: ''
      }
      this.materialNameList = []
      this.$refs.formLabelAlign.resetFields()
      this.addDutyInfoDialog = false
    },
    getAreaList() {
      let url = `/gtw/EIMS-management/companyDict/queryRegion?userLevel=${localStorage.level}&userId=${localStorage.userId}`
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    // 区县框
    regionNamechange(val) {
      this.stockList = []
      this.form.stockName = ''
      if (this.form.regionName.length == 0) {
        this.$message.warning('请输入区县！')
        this.disabled = true
        this.stockList = []
        this.form.stockName = ''
        this.disabledTooltip = false
      } else {
        this.disabledTooltip = true
        this.disabled = false
        let params = {
          regionId: val,
          userLevel: JSON.parse(localStorage.getItem('userLevel'))
        }

        axios.get('/gtw/EIMS-management/stockInfo/queryAllStock?', { params }).then((res) => {
          let data = res.data.data || []
          this.stockList = data
        })
      }
    },
    // 物资框
    materialNameChange(val) {
      if (this.formLabelAlign.stuffName.length == 0) {
        this.$message.warning('请输入物资名！')
      } else {
        // this.materialNameList.filter(v => v.stuffName === val)[0].id
        this.formLabelAlign.stuffUnit = this.materialNameList.filter((v) => v.stuffName === val)[0].stuffUnit
      }
    },
    handleClose(done) {
      this.disabledTooltip = false
      this.disabled = true
      this.reset()
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.$refs.formref.resetFields()
      this.form = { regionName: [], stockName: [], saferInfos: [] }
    },
    handleOk() {
      this.$refs.formref.validate((valid) => {
        // console.log(this.form.saferInfos)
        if (valid) {
          if (this.form.saferInfos.length === 0) {
            this.$message.warning('请将信息填写完整！')
          } else {
            if (this.form.saferInfos && this.form.saferInfos.length > 0) {
              let url = '/gtw/EIMS-management/resourceAgreement/insertStuff'

              axios.post(url, [this.form.saferInfos]).then((r) => {
                if (r.data.code === 200) {
                  this.$message.success('操作成功')
                  this.$emit('success')
                  this.handleClose()
                } else {
                  this.$message.error(r.data.message)
                }
              })
            } else {
              this.$message.error('请添加物资！')
            }
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
.detailsDialog {
  .el-form-item__content {
    width: 285px;
  }
}
</style>
