<template>
  <div class="autoEditor">
    <Toolbar style="border-bottom: 1px solid #ccc" v-show="!disabled" ref="toolbar" :editor="editor" :defaultConfig="toolbarConfig" :mode="mode" />
    <Editor ref="editor"
      style="height: 500px; overflow-y: hidden;"
      v-model="editorValue"
      :defaultConfig="editorConfig"
      :mode="mode"
      @onCreated="onCreated"
    />
    <!--@customPaste="customParse"-->
  </div>
</template>

<script>
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
export default {
  name: 'AutoEditor',
  components: { Editor, Toolbar },
  model: {
    prop: 'editorValue',
    event: 'modelChange'
  },
  props: {
    editorValue: {
      type: String,
      default: ""
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      editor: null,
      toolbarConfig: {
        excludeKeys: [
          'insertImage',
          'group-video',
          'insertLink'
        ]
      },
      editorConfig: {
        placeholder: '请输入内容...',
        MENU_CONF: {
          uploadImage: {
            fieldName: 'your-fileName',
            base64LimitSize: 10 * 1024 * 1024 // 10M 以下插入 base64
          }
        }
      },
      mode: 'default' // or 'simple'
    }
  },
  watch: {
    editorValue(nVal, oVal) {
      this.$emit("modelChange", nVal);
    },
    disabled: {
      handler(nVal, oVal) {
        if (nVal) {
          this.editor.disable()
        } else {
          this.editor.enable()
        }
      },
      immediate: true
    }
  },
  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  },
  methods: {
    customParse(editor, event) {
      // event 是 ClipboardEvent 类型，可以拿到粘贴的数据
      // 可参考 https://developer.mozilla.org/zh-CN/docs/Web/API/ClipboardEvent
      // const html = event.clipboardData.getData('text/html') // 获取粘贴的 html
      // const text = event.clipboardData.getData('text/plain') // 获取粘贴的纯文本
      // const rtf = event.clipboardData.getData('text/rtf') // 获取 rtf 数据（如从 word wsp 复制粘贴）
      // 同步
      // editor.insertText(html)

      // 阻止默认的粘贴行为
      event.preventDefault()
      return false
    },
    onCreated(editor) {
      this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
      if (this.disabled) {
        this.editor.disable()
      } else {
        this.editor.enable()
      }
    }
  }
}
</script>

<style type="scss">
  .w-e-full-screen-container{
    z-index: 100;
  }
</style>
