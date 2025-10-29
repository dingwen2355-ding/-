<template>
  <el-dialog
    title="删除"
    :visible.sync="dialogVisible"
    width="30%"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose"
  >
    <div class="delete-class">
      <img src="@/assets/common/warning.png" alt="" />
      <div>
        <div v-if="storageInfo && storageInfo.stuffNumber > 0">该仓库存有物资,</div>
        <div>是否删除该物资仓库?</div>
        <div>删除仓库，会将仓库关联信息一并删除</div>
      </div>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import axios from 'axios'

export default {
  props: ['dialogVisible', 'storageInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    }
  },
  data() {
    return {}
  },
  methods: {
    handleClose(done) {
      this.$emit('update:dialogVisible', false)
    },
    reset() {},
    handleOk() {
      let url = '/gtw/EIMS-management/stockInfoData/deleteStockInfo?id=' + this.storageInfo.id
      axios.post(url).then((r) => {
        if (r.data.code === 200) {
          this.$message.success('删除成功')
          this.$emit('success')
          this.handleClose()
        } else {
          this.$message.error(r.data.message)
        }
      })
    }
  }
}
</script>
<style lang="scss">
.delete-class {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
