<template>
  <div>
    <div id="jsoneditor" style="width: 100%; height: 400px; margin-bottom: 10px"></div>
  </div>
</template>

<script>
// #引入 jsoneditor
import jsoneditor from 'jsoneditor'
import 'jsoneditor/dist/jsoneditor.css'

export default {
  name: 'JsonEditor',
  /* eslint-disable vue/require-prop-types */
  props: {
    mode: {
      // 选中的模式
      type: String,
      default: 'code'
    },
    modes: {
      // 可选模式
      type: Array,
      default: function () {
        return ['code', 'view', 'tree', 'form', 'text', 'preview']
      }
    },
    jsonOb: {
      // json数据内容
      type: Object
    },
    expandedOnStart: {
      // json数据是否默认展开
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      jsonEditor: null,
      error: false,
      json: this.jsonOb,
      internalChange: false,
      expandedModes: ['tree', 'view', 'form']
    }
  },
  watch: {
    jsonOb: {
      immediate: true,
      async handler(val) {
        if (!this.internalChange) {
          await this.setEditor(val)

          this.error = false
          this.expandAll()
        }
      },
      deep: true
    }
  },
  mounted() {
    this.initJsonEditor()
  },
  methods: {
    // 初始化jsonEditor
    initJsonEditor() {
      let self = this
      var container = document.getElementById('jsoneditor')
      var options = {
        mode: this.mode,
        modes: this.modes, // allowed modes
        onChange() {
          try {
            let json = self.jsonEditor.get()
            self.json = json
            self.error = false
            self.$emit('json-change', json) //  json编辑器内容改变时触发
            self.internalChange = true
            self.$nextTick(function () {
              self.internalChange = false
            })
          } catch (e) {
            self.error = true
            self.$emit('has-error', e) // 发生错误时触发
          }
        },
        onModeChange() {
          self.expandAll()
        }
      }
      // eslint-disable-next-line new-cap
      this.jsonEditor = new jsoneditor(container, options, this.json)
    },
    expandAll() {
      if (this.expandedOnStart && this.expandedModes.includes(this.jsonEditor.getMode())) {
        this.jsonEditor.expandAll()
      }
    },
    async setEditor(value) {
      if (this.jsonEditor) this.jsonEditor.set(value)
    }
  }
}
</script>

<style lang="scss" >
// .jsoneditor-menu {
//   background: #57c5f7;
// }
</style>
