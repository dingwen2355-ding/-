<template>
  <cloud-tmpl :title='title' :top='top' :right='right' :width='width' :scrollHeight='height' :loading='isLoading'
    draggable @close='handleClose'>
    <!-- 头部插槽 -->
    <template v-slot:head>
      <el-button @click='handleChange' size="small">请求</el-button>
    </template>
    <!-- 正文插槽-滚动体 -->
    <template v-slot:content>
      <div class='content-box'>
        <el-input value='content'></el-input>
        <el-input value='content'></el-input>
      </div>
    </template>
    <!-- 底部插槽 -->
    <template v-slot:foot>
      <div class='content-foot'>
        <el-input value='foot'></el-input>
     </div>
    </template>
  </cloud-tmpl>
</template>
<script>
import { get, post, put, deletes } from '@/utils/fetch';
export default {
  name:'',
  // ^^^ ^^^
  data() {
    return {
      title: '',
      // ^^^ ^^^
      top: 180,
      right: 60,
      width: 300,
      height: 20,
      isLoading: false,
      // ---------------
    };
  },
  methods: {
    handleClose() {
      console.log('关闭弹窗事件 handleClose');
    },
    handleChange() {
      this.isLoading = true;
      setTimeout(() => {
        this.height = 50;
        this.isLoading = false;
      }, 2000)
    }
  },

};
</script>
<style lang='scss' scoped>
::v-deep .content-box {
  outline: solid 1px pink;
  height: 500px;
}
</style>