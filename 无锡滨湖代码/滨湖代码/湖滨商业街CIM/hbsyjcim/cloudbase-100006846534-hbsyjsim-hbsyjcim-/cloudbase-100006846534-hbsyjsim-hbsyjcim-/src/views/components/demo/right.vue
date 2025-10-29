<template>
  <div v-if="visible" class="right-bg">
    <IocTitle title="中心可视化概览" @change="closedWin()" />
  </div>
</template>

<script>
import IocTitle from '@/components/title'

export default {
  name: 'PartyBuildingRight',
  components: {
    IocTitle
  },
  data(){
    return {
      visible: true
    }
  },
  methods: {
    closedWin() {
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>

</style>