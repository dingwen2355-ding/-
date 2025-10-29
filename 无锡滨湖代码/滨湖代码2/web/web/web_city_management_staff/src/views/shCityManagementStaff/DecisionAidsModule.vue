<template>
  <div class="DecisionAidsModule">
    <div class="title">
      <i class="iconfont iconzhgl_title"></i><span>处置建议：</span>
    </div>
    <div class="edit_container">
      <quill-editor v-model="content" ref="myQuillEditor" :options="editorOption" @blur="onEditorBlur($event)"
        @focus="onEditorFocus($event)" @change="onEditorChange($event)">
      </quill-editor>
    </div>
    <div class="btn">
      <div class="item item_btn1" @click="calcelSubmit">取消</div>
      <div class="item item_btn2" @click="saveHtml">提交</div>
    </div>
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'
import { getDecisionSuggest, getUpdateDecisionSuggest } from '@/api/organizational-module.js'

// 标题
const titleConfig = [
  { Choice: '.ql-bold', title: '加粗' },
  { Choice: '.ql-italic', title: '斜体' },
  { Choice: '.ql-underline', title: '下划线' },
  { Choice: '.ql-header', title: '段落格式' },
  { Choice: '.ql-strike', title: '删除线' },
  { Choice: '.ql-blockquote', title: '块引用' },
  { Choice: '.ql-code', title: '插入代码' },
  { Choice: '.ql-code-block', title: '插入代码段' },
  { Choice: '.ql-font', title: '字体' },
  { Choice: '.ql-size', title: '字体大小' },
  { Choice: '.ql-list[value="ordered"]', title: '编号列表' },
  { Choice: '.ql-list[value="bullet"]', title: '项目列表' },
  { Choice: '.ql-direction', title: '文本方向' },
  { Choice: '.ql-header[value="1"]', title: 'h1' },
  { Choice: '.ql-header[value="2"]', title: 'h2' },
  { Choice: '.ql-align', title: '对齐方式' },
  { Choice: '.ql-color', title: '字体颜色' },
  { Choice: '.ql-background', title: '背景颜色' },
  { Choice: '.ql-image', title: '图像' },
  { Choice: '.ql-video', title: '视频' },
  { Choice: '.ql-link', title: '添加链接' },
  { Choice: '.ql-formula', title: '插入公式' },
  { Choice: '.ql-clean', title: '清除字体格式' },
  { Choice: '.ql-script[value="sub"]', title: '下标' },
  { Choice: '.ql-script[value="super"]', title: '上标' },
  { Choice: '.ql-indent[value="-1"]', title: '向左缩进' },
  { Choice: '.ql-indent[value="+1"]', title: '向右缩进' },
  { Choice: '.ql-header .ql-picker-label', title: '标题大小' },
  { Choice: '.ql-header .ql-picker-item[data-value="1"]', title: '标题一' },
  { Choice: '.ql-header .ql-picker-item[data-value="2"]', title: '标题二' },
  { Choice: '.ql-header .ql-picker-item[data-value="3"]', title: '标题三' },
  { Choice: '.ql-header .ql-picker-item[data-value="4"]', title: '标题四' },
  { Choice: '.ql-header .ql-picker-item[data-value="5"]', title: '标题五' },
  { Choice: '.ql-header .ql-picker-item[data-value="6"]', title: '标题六' },
  { Choice: '.ql-header .ql-picker-item:last-child', title: '标准' },
  { Choice: '.ql-size .ql-picker-item[data-value="small"]', title: '小号' },
  { Choice: '.ql-size .ql-picker-item[data-value="large"]', title: '大号' },
  { Choice: '.ql-size .ql-picker-item[data-value="huge"]', title: '超大号' },
  { Choice: '.ql-size .ql-picker-item:nth-child(2)', title: '标准' },
  { Choice: '.ql-align .ql-picker-item:first-child', title: '居左对齐' },
  { Choice: '.ql-align .ql-picker-item[data-value="center"]', title: '居中对齐' },
  { Choice: '.ql-align .ql-picker-item[data-value="right"]', title: '居右对齐' },
  { Choice: '.ql-align .ql-picker-item[data-value="justify"]', title: '两端对齐' }
]
export default {
  name: 'DecisionAidsModule',
  components: {
    quillEditor
  },
  data () {
    const toolbarOptions = [
      ['bold', 'italic', 'underline', 'strike'], // toggled buttons
      [{ 'header': 1 }, { 'header': 2 }], // custom button values
      [{ 'color': [] }, { 'background': [] }], // dropdown with defaults from theme
      [{ 'list': 'ordered' }, { 'list': 'bullet' }], // 列表
      ['clean'] // remove formatting button
    ]
    return {
      content: '',
      oldContent: '',
      editorOption: {
        modules: {
          // toolbar: '#toolbar'
          toolbar: toolbarOptions
        },
        theme: 'snow',
        placeholder: '请输入正文'
      },
      eventId: ''
    }
  },
  watch: {
    '$store.getters.eventId': {
      handler (v) {
        this.$nextTick(() => {
          this.eventId = v.id
          this.getDecisionSuggest(v.id)
        })
      },
      immediate: true
    }
  },
  computed: {
    editor () {
      return this.$refs.myQuillEditor.quill
    }
  },
  mounted () {
    document.getElementsByClassName('ql-editor')[0].dataset.placeholder = ''
    for (let item of titleConfig) {
      let tip = document.querySelector('.quill-editor ' + item.Choice)
      if (!tip) continue
      tip.setAttribute('title', item.title)
    }
  },
  methods: {
    async getDecisionSuggest (v) {
      let decisionSuggestRes = await getDecisionSuggest(v)
      let data = decisionSuggestRes.data.data[0] || {}
      this.content = data.decisionSuggest || ''
      this.oldContent = this.content
    },
    onEditorReady (editor) { // 准备编辑器
    },
    onEditorBlur () { }, // 失去焦点事件
    onEditorFocus () { }, // 获得焦点事件
    onEditorChange () { }, // 内容改变事件
    async saveHtml (event) {
      if (!this.eventId) return
      try {
        let updateDecisionSuggest = {
          eventId: this.eventId,
          decisionSuggest: this.content
        }
        console.log(this.content, event)
        await getUpdateDecisionSuggest(updateDecisionSuggest)
        this.$message({
          message: '提交成功！',
          type: 'success'
        })
        this.$root.eventBus.$emit('refreshIframe')
      } catch (error) {
        this.$message({
          message: '提交失败！',
          type: 'error'
        })
        console.error(error)
      }
    },
    calcelSubmit () {
      this.content = this.oldContent
    }
  }
}
</script>

<style lang="scss" scoped>
.DecisionAidsModule {
  height: 1000px;
  width: 100%;
  padding-top: 50px;
  .title {
    float: left;
    // width: 400px;
    // height: 673px;
    font-size: 48px;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: rgba(12, 153, 219, 1);
    // line-height: 160px;
    i {
      font-size: 48px;
      margin: 0 25px 0 20px;
      background: linear-gradient(
        -90deg,
        rgba(0, 215, 254, 1) 0%,
        rgba(0, 150, 255, 1) 100%
      );
      // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
  }
  .save-btn {
    width: 250px;
    height: 80px;
    border: 2px solid rgba(11, 153, 219, 1);
    border-radius: 8px;
    color: #0b99db;
    background-color: #fff;
    float: right;
    margin: 102px 380px 100px 0;
    cursor: pointer;
  }
  .edit_container {
    margin-left: 10%;
    height: 800px;
    width: 80%;
    background: rgba(247, 251, 255, 1);
    border: 2px solid rgba(11, 153, 219, 1);
    border-radius: 8px;
    /deep/.ql-editor {
      background: rgba(41, 56, 82, 0.8);
      line-height: 60px;
      height: 698px;
      font-size: 40px;
      color: #fff;
    }
    /deep/.ql-snow.ql-toolbar button,
    .ql-snow .ql-toolbar button {
      background: none;
      border: none;
      cursor: pointer;
      float: left;
      height: 80px;
      /* padding: 3px 5px; */
      width: 120px;
      display: inline-flex;
      /* margin: 20px; */
      /* font-size: 40px; */
      background: #efefef;
      border: 1px solid #c8c8c8;
      justify-content: center;
      /deep/ .ql-snow .ql-stroke {
        // color: #868686;
      }
    }
    /deep/.ql-toolbar.ql-snow .ql-picker-label {
      cursor: pointer;
      float: left;
      height: 80px;
      /* padding: 3px 5px; */
      width: 120px;
      display: inline-flex;
      /* margin: 20px; */
      /* font-size: 40px; */
      background: #efefef;
      border: 1px solid #c8c8c8;
      justify-content: center;
      /deep/ svg {
        width: 120px;
      }
    }
    /deep/.ql-snow .ql-picker.ql-size {
      width: 220px;
    }
    /deep/.ql-toolbar.ql-snow .ql-formats {
      margin: 0;
    }
    /deep/ .ql-snow .ql-picker {
      color: #444;
      display: inline-block;
      float: left;
      font-size: 40px;
      font-weight: 500;
      height: 80px;
      width: 100px;
      position: relative;
      vertical-align: middle;
    }
    /deep/.ql-snow .ql-color-picker .ql-picker-item {
      border: 1px solid transparent;
      float: left;
      height: 50px;
      margin: 2px;
      padding: 0px;
      width: 50px;
    }
    /deep/.ql-snow .ql-picker.ql-expanded .ql-picker-options {
      width: 390px;
      height: 290px;
      display: block;
      margin-top: -1px;
      top: 100px;
      z-index: 1;
    }
  }
  .btn {
    width: 100%;
    height: 220px;
    // background-color: #0ff;
    display: inline-flex;
    justify-content: center;
    border-top: 2px solid #d6d6d6;

    .item {
      width: 168px;
      height: 80px;
      margin-top: 80px;
      text-align: center;
    }
    .item_btn1 {
      font-size: 40px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: rgba(12, 153, 219, 1);
      line-height: 80px;
      cursor: pointer;
    }
    .item_btn2 {
      background: linear-gradient(
        176deg,
        rgba(0, 168, 255, 1) 0%,
        rgba(0, 98, 209, 1) 100%
      );
      border-radius: 8px;
      line-height: 80px;
      color: #fff;
      cursor: pointer;
    }
  }
}
</style>
