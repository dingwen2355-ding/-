<template>
    <div class="addNode">
        <el-dialog
            :visible.sync="dialogVisible" @open="getEventList"
            width="700px"
            :modal-append-to-body="false"
            :append-to-body="false"
            :before-close="handleClose"
        >
            <div class="info_body" v-loading="loading">
                <el-form
                    :inline="true"
                    ref="formLabel"
                    :rules="rules"
                    :model="form"
                    label-width="80px"
                >
                  <el-form-item label="是否隐藏" prop="isHidden">
                    <el-select v-model="form.isHidden" placeholder="请选择是否隐藏">
                      <el-option
                          v-for="item in hiddenList"
                          :key="item.key"
                          :label="item.value"
                          :value="item.key">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="节点" prop="nodeId">
                    <el-select v-model="form.nodeId" placeholder="请选择节点">
                      <el-option
                          v-for="item in nodeList"
                          :key="item.id"
                          :label="item.nodeName"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-form>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleClose()">取 消</el-button>
                <el-button type="primary" @click="handleOk">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import request from '@/utils/request'
export default {
  name: 'addNode',
  props: ['dialogVisible', 'formInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        console.log('新增编辑查看this.form', this.form)
      }
    }
  },
  data() {
    return {
      rules: {
        isHidden: [{ required: true, message: '请选择是否隐藏', trigger: 'blur' }],
        nodeId: [{ required: true, message: '请选择节点', trigger: 'blur' }]
      },
      loading: false,
      form: {
        eventId: 0,
        isHidden: '0',
        nodeId: 0,
        order: 0
      },
      nodeList: [],
      hiddenList: [
        {
          key: '0',
          value: '显示'
        },
        {
          key: '1',
          value: '隐藏'
        }
      ]
    }
  },
  mounted() {
  },
  methods: {
    handleClose() {
      this.$confirm('尚未保存，是否在离开页面前保存？', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '提交保存',
        cancelButtonText: '关闭窗口',
        type: 'warning'
      })
        .then(() => {
          this.handleOk()
        })
        .catch((e) => {
          if (e === 'cancel') {
            this.reset()
            this.$emit('update:dialogVisible', false)
          }
        })
    },
    reset() {
      this.$parent.refreshData('node', this.form.eventId)
      this.loading = false
      this.form = {}
      // 清空表单校验，避免再次进来会出现上次校验的记录
      this.$refs.formLabel.clearValidate()
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          return false
        }
      })
    },
    submitAll() {
      this.addEventNodeContact()
    },
    getEventList() {
      let url = '/binhuapis/dapeng/eventNode/query-condition?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      request.get(url).then(res => {
        this.nodeList = res.data.data.list
      })
    },
    addEventNodeContact() {
      this.loading = true
      let params = this.form
      let url = '/binhuapis/dapeng/eventNodeContact/add'
      request.post(url, params).then(res => {
        this.loading = false
        if (res.data.code === 200) {
          this.$message.success('新增成功！')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(res.data.message)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.addNode{

}
</style>
