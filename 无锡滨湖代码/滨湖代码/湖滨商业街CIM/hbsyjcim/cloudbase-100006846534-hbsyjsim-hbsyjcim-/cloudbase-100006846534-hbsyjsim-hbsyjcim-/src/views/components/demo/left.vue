<template>
  <div v-if="visible" class="left-bg">
    <IocTitle title="统计概况" @change="closedWin()" />
  </div>
</template>

<script>
import IocTitle from '@/components/title'
export default {
  name: 'PartyBuildingLeft',
  components: {
    IocTitle
  },
  data() {
    return {
      visible: true
    }
  },
  created() {
    
  },
  methods: {
    // 隐藏当前窗口
    closedWin() {
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>
</style>