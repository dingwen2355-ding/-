<template>
  <el-dialog
    :title="status === 'in' ? '入库' : '出库'"
    :visible.sync="dialogVisible"
    width="760px"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose"
  >
    <el-form :inline="true" ref="form" :rules="rules" :model="form" class="storageInventory-form" label-width="120px">
      <el-form-item :label="status === 'in' ? '入库数量' : '出库数量'" prop="operationNum">
        <el-input-number
          v-if="formInfo"
          v-model="form.operationNum"
          :precision="2"
          :controls="false"
          :placeholder="status === 'out' ? '请输入出库数量（<=' + formInfo.STUFF_NUMBER + '）' : '请填写入库数量'"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="编号" prop="identifier">
        <el-input clearable v-model="form.identifier" placeholder="请输入编号"></el-input>
      </el-form-item>
      <!-- <el-form-item label="领用单位" prop="recipientUnit">
        <el-input clearable v-model="form.recipientUnit" placeholder="请输入领用单位"></el-input>
      </el-form-item> -->
      <el-form-item label="经办人" prop="manager">
        <el-input clearable v-model="form.manager" placeholder="请输入经办人"></el-input>
      </el-form-item>
      <el-form-item label="操作时间" prop="recipientTime">
        <el-date-picker v-model="form.recipientTime" type="date" value-format="yyyy-MM-dd" placeholder="选择操作时间">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="联系方式" prop="recipientPhone">
        <el-input clearable v-model="form.recipientPhone" placeholder="请输入联系方式"></el-input>
      </el-form-item> -->
      <el-form-item v-if="status === 'in'" label="物资来源" prop="source" class="whole-line">
        <el-input clearable v-model="form.source" type="textarea" :rows="2" placeholder="请输入物资来源"></el-input>
      </el-form-item>

      <el-form-item v-else label="领取单位" prop="source" class="whole-line">
        <el-input clearable v-model="form.source" type="textarea" :rows="2" placeholder="请输入领取单位"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'
export default {
  name: 'StorageInventoryOut',
  components: {},
  data() {
    return {
      form: {
        stockId: '',
        stuffId: '',
        operationNum: '',
        // recipientUnit: '',
        manager: '',
        // recipientPhone: '',
        recipientTime: '',
        identifier: '',
        source: '',
        userId: localStorage.getItem('userId'),
        brand: '',
        model: '',
        productionDay: ''
      },
      rules: {
        operationNum: [
          { required: true, message: '请输入后提交', trigger: 'blur' },
          { type: 'number', min: 0, message: '请输入正数', trigger: 'blur' }
        ],
        recipientUnit: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        manager: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        recipientPhone: [{ required: true, message: '请输入后提交', trigger: 'blur' }]
      }
    }
  },
  props: ['dialogVisible', 'formInfo', 'status'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.form.stockId = this.formInfo.stockId
      this.form.stuffId = this.formInfo.stuffId
      this.form.model = this.formInfo.model
      this.form.brand = this.formInfo.brand
      this.form.productionDay = this.formInfo.stuff_create_date
      this.form.recipientTime = this.getNowDate()
      // console.log(this.getNowDate())
    }
  },
  mounted() {},
  methods: {
    handleClose(done) {
      this.reset()
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.$refs.form.resetFields()
    },
    handleOk() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.operationNum === 0) {
            this.$message('操作数量不能为0')
            return
          }
          let params = {}
          let text = ''
          if (this.status === 'out') {
            text = '出库'

            if (this.form.operationNum > this.formInfo.STUFF_NUMBER) {
              this.$message.error(`出库数量不能大于${this.formInfo.STUFF_NUMBER}`)
            } else if (this.form.operationNum <= 0) {
              this.$message.error(`出库数量至少大于0`)
            } else {
              for (let key in this.form) {
                if (this.form[key]) params[key] = this.form[key]
              }
              params.operationType = '1'
            }
          } else {
            text = '入库'
            for (let key in this.form) {
              if (this.form[key]) params[key] = this.form[key]
            }
            // params.operationNum = (-params.operationNum)
            params.operationType = '2'
          }
          // console.log(params)
          let url = '/gtw/EIMS-management/stockInfoData/shipment'
          axios.post(url, params).then((r) => {
            if (r.data.code === 200) {
              this.$message.success(`${text}成功`)
              this.$emit('success')
              this.handleClose()
            } else {
              this.$message.error(r.data.message)
            }
          })
        } else {
          // console.log('error submit!!')
          return false
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

<style lang="scss">
.storageInventory-form.el-form--inline {
  .el-select {
    display: block;
  }
  .el-form-item__content {
    width: 225px;
  }

  .el-date-editor {
    width: 225px;
  }

  .whole-line {
    width: 100%;

    .el-form-item__content {
      width: 585px;
    }
  }
}
</style>
