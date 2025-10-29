<template>
  <div class="InterfaceOverview" style="overflow: hidden">
    <!-- <iframe :src="src" style="width: 100%; height: calc(100% - 5px)"> -->
    <iframe :src="src" frameborder="0">
      <p>您的浏览器不支持 iframe 标签。</p>
    </iframe>
  </div>
</template>
<script>
export default {
  name: 'InterfaceOverview',
  data() {
    return {
      src: configs.interfaceOverview // 需要在config --> config.js中进行配置IP地址
    }
  }
}
</script>
<style lang="scss" scoped>
.InterfaceOverview {
  // .page {
  //   /* background: #fff; */
  //   padding: 10px;
  // }
  iframe {
    width: 100%;
    height: 100%;
  }
}
</style>
