<template>
  <div class="header">
    <p>数字孪生-湖滨商业街</p>
  </div>
</template>

<script>
export default {
  name: 'IocHeader',
  data(){
    return {

    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  position: fixed;
  left: 50%;
  top: 0;
  transform: translateX(-50%);
  width: 1136px;
  height: 66px;
  line-height: 66px;
  background-image: url('../../../assets/header/img_head_title_bg@2x.png');
  background-size: 100% 100%;
  background-position: top center;
  background-repeat: no-repeat;
  text-align: center;
  font-size: 32px;
  font-weight: 600;
  
}
</style>