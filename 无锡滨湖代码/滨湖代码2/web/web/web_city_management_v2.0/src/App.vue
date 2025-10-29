<template>
  <el-scrollbar style="height:100%">
    <div
      id="app"
      v-loading="loading"
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <router-view />
    </div>
  </el-scrollbar>
</template>
<script>
export default {
  name: 'App',
  components: {},
  data() {
    return {
      precinctName: sysConfig.defaultPrecinct.precinctName,
      precinctId: sysConfig.defaultPrecinct.precinctId,
      screenWidth: '',
      screenHeight: '',
      loading: false
    }
  },
  mounted() {
    const _this = this
    this.intervalRefreshData()
    _this.loading = true
    setTimeout(() => {
      _this.loading = false
    }, 3000)
  },
  methods: {
    intervalRefreshData() {
      this.interval = setInterval(() => {
        this.$store.dispatch('view/setIntervalRefresh', new Date().getTime())
      }, 1000 * 60)
    }
  }
}
</script>
<style lang="scss">
#app {
  .el-loading-spinner i {
    font-size: 140rem;
  }
}
</style>
