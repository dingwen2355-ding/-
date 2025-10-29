<template>
  <div>
    <editorJSON
      ref="editorJson"
      :jsonOb="jsonValue"
      :mode="mode"
      :expandedOnStart="false"
      :modes="modes"
      :disabled="isEdit"
      @json-change="onJsonChange"
      @has-error="onError"
    ></editorJSON>
    <el-button type="primary" @click="checkJson">提交</el-button>
  </div>
</template>
<script>
import editorJSON from '../../../components/jsonEditor/editorJSON.vue'

export default {
  components: { editorJSON },
  data() {
    return {
      hasJsonFlag: true, // json是否验证通过
      // json数据
      jsonValue: {
        'eims.database.host.ip': '172.16.7.11',
        'eims.database.host.port': '8036',
        'eims.database.name': 'eims',
        'eims.database.username': 'root',
        'eims.database.password': 'zxm10',
        'eims.spring.redis.password': 'zxm10',
        'eims.spring.redis.host': '172.16.7.11',
        'eims.spring.redis.port': '6379',
        'file.path': '/home/znv/EIMS/files',
        'url.path': 'http://172.16.7.11:8097/files'
      },
      isEdit: true,
      // 编辑/新增
      mode: 'code',
      modes: ['code', 'view', 'tree', 'form', 'text']
      //   // 详情
      //   mode: 'preview',
      //   modes: ['view', 'preview']
    }
  },
  mounted() {},
  methods: {
    onJsonChange(value) {
      // console.log('更改value:', value)
      this.resultInfo = value
      this.hasJsonFlag = true
    },
    onError(value) {
      // console.log("json错误了value:", value);
      this.hasJsonFlag = false
    },
    // 检查json
    checkJson() {
      if (this.hasJsonFlag == false) {
        // console.log("json验证失败")
        this.$message.error('json验证失败')
        // alert('json验证失败')
        return false
      } else {
        // console.log("json验证成功")
        this.$message.success('json验证成功')
        // alert('json验证成功')
        return true
      }
    }
    // submit() {
    // //   this.jsonValue = this.$refs.editorJson.getValue()
    // this.checkJson
    //   console.log('jsonValue', this.jsonValue)
    // }
  }
}
</script>
<style lang="scss" scoped>
</style>
