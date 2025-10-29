<template>
  <div class="StorageInventoryAdd" v-if="dialogVisible">
    <el-dialog
      title="入库"
      :visible.sync="dialogVisible"
      width="1060px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <el-form
        :inline="true"
        ref="form"
        :rules="rules"
        :model="form"
        class="storageInventory-addForm"
        label-width="100px"
      >
        <!-- <el-form-item label="仓库名称" prop="storageName">
        <el-autocomplete
          class="inline-input"
          v-model="form.storageName"
          :fetch-suggestions="stockSearch"
          placeholder="请输入仓库名称"
          @select="checkStock"
          clearable
        ></el-autocomplete>
        </el-form-item>-->
        <el-form-item label="物资名称" prop="goodName">
          <el-autocomplete
            class="inline-input"
            v-model="form.goodName"
            :fetch-suggestions="goodSearch"
            placeholder="请输入物资名称"
            @select="checkGood"
            clearable
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="物资型号" prop="goodInfo">
          <el-input clearable v-model="form.goodInfo" placeholder="请输入物资型号"></el-input>
        </el-form-item>
        <el-form-item label="物资品牌" prop="brand">
          <el-input clearable v-model="form.brand" placeholder="请输入物资品牌"></el-input>
        </el-form-item>
        <el-form-item label="入库数量" prop="operationNum">
          <el-input clearable v-model="form.operationNum" placeholder="请输入出库数量"></el-input>
        </el-form-item>
        <el-form-item label="经办人" prop="manager">
          <el-input clearable v-model="form.manager" placeholder="请输入经办人"></el-input>
        </el-form-item>
        <el-form-item label="操作时间" prop="recipientTime">
          <el-date-picker
            v-model="form.recipientTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择操作时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="编号" prop="identifier">
          <el-input clearable v-model="form.identifier" placeholder="请输入编号"></el-input>
        </el-form-item>
        <el-form-item label="物资来源" prop="source">
          <el-input clearable v-model="form.source" placeholder="请输入物资来源"></el-input>
        </el-form-item>
        <!-- <el-form-item label="用途" prop="use" class="whole-line">
        <el-input clearable v-model="form.use" type="textarea" :rows="4" placeholder="请输入用途"></el-input>
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'StorageInventoryAdd',
  props: ['dialogVisible', 'name', 'id'],
  data() {
    return {
      textVisible: false,
      restaurants: '',
      stockList: '',
      goodList: '',
      form: {
        recipientTime: '',
        operationNum: '',
        manager: '',
        brand: '',
        goodInfo: '',
        storageName: '',
        stockId: '',
        goodName: '',
        goodId: '',
        source: '',
        identifier: ''
      },
      clearForm: {},
      rules: {
        operationNum: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        recipientUnit: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        manager: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        goodInfo: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        storageName: [{ required: true, message: '请输入后提交', trigger: ['blur', 'change'] }],
        goodName: [{ required: true, message: '请输入后提交', trigger: ['blur', 'change'] }],
        brand: [{ required: true, message: '请输入后提交', trigger: 'blur' }]
      }
    }
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.form.storageName = this.name
      this.form.stockId = this.id
      this.clearForm = this.form
      this.form.recipientTime = this.getNowDate()
      if (v === true) {
        this.getStockNameList()
        this.getGoodNameList()
      }
    }
  },
  methods: {
    handleClose(done) {
      this.reset()
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.$refs.form.resetFields()
      this.form = this.clearForm
    },
    getStockNameList() {
      let url = `/community/menu/getStuff?userLevel=${localStorage.level}`
      axios.get(url).then((r) => {
        console.log(r.data.data)
        let arr = []
        r.data.data.forEach((item) => {
          let obj = {}
          obj['value'] = item.stockName
          obj['id'] = item.stockId
          arr.push(obj)
        })
        this.stockList = arr
      })
    },
    getGoodNameList() {
      let url = '/binhuapis/resourceList/queryAllResource'
      axios.get(url).then((r) => {
        console.log(r.data.data)
        let arr = []
        r.data.data.forEach((item) => {
          let obj = {}
          obj['value'] = item.stuffName
          obj['id'] = item.id
          arr.push(obj)
        })
        this.goodList = arr
      })
    },
    stockSearch(queryString, cb) {
      let restaurants = this.stockList
      let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    goodSearch(queryString, cb) {
      let restaurants = this.goodList
      let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter(queryString) {
      return (restaurant) => {
        return restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1
      }
    },
    checkStock(v) {
      this.form.stockId = v.id
    },
    checkGood(v) {
      this.form.goodId = v.id
    },
    searchStockId() {
      this.stockList.forEach((item) => {
        if (item.name === this.form.storageName) {
          this.form.stockId = item.id
          return true
        }
      })
      if (this.form.stockId === '') {
        return false
      }
    },
    searchGoodId() {
      this.stockList.forEach((item) => {
        if (item.name === this.form.storageName) {
          this.form.stockId = item.id
          return true
        }
      })
      if (this.form.stockId === '') {
        return false
      }
    },
    handleOk() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.stockId === '') {
            let key = this.searchStockId()
            if (!key) {
              this.$message.error('不存在该仓库名，请重新选择')
              return
            }
          }
          if (this.form.goodId === '') {
            let key = this.searchGoodId()
            if (!key) {
              this.$message.error('不存在该物资名，请重新选择')
              return
            }
          }
          let url = `/community/menu/stuffInsert?stockId=${this.form.stockId}&userLevel=${localStorage.level}&stuffId=${this.form.goodId}&brand=${this.form.brand}&model=${this.form.goodInfo}&stuffNumber=${this.form.operationNum}&manager=${this.form.manager}&recipientTime=${this.form.recipientTime}&source=${this.form.source}&identifier=${this.form.identifier}`
          axios.get(url).then((r) => {
            console.log(r)
            if (r.data.code === 200) {
              this.$message.success('入库成功')
              this.$emit('success')
              this.handleClose()
            } else {
              this.$message.error(`入库失败,${r.data.message},请重试`)
            }
          })
        } else {
          this.$message.error('失败')
        }
      })
    },
    getNowDate() {
      let now = new Date()
      let year = now.getFullYear()
      let mounth = now.getMonth() + 1 < 10 ? `0${now.getMonth() + 1}` : now.getMonth() + 1
      let day = now.getDate() < 10 ? `0${now.getDate()}` : now.getDate()

      return `${year}-${mounth}-${day}`
    }
  }
}
</script>

<style lang='scss'>
.StorageInventoryAdd {
  @import '../../common/style/base';

  .el-dialog__body {
    height: 250px;
  }
  .storageInventory-addForm.el-form--inline {
    .el-select {
      display: block;
    }
    .el-form-item__content {
      width: 200px;
    }

    .el-date-editor {
      width: 200px;
    }

    .whole-line {
      width: 100%;

      .el-form-item__content {
        width: 840px;
      }
    }
  }
}
</style>
