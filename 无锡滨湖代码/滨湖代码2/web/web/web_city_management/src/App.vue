<template>
<el-scrollbar style="height:100%">
  <div id="app">
    <router-view/>
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
      screenHeight: ''
    }
  },
  mounted() {
    this.intervalRefreshData()
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
  }
</style>
