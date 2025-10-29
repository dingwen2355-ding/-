<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  name: 'App',
  data() {
    return {
      timer: null
    }
  },
  methods: {
  },
  created() {},
  mounted() {
  },
  destroyed() {}
}
</script>
<style lang="scss">
#app {
  font-family: 'PingFangSC-Regular';
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #131824;
  text-align: left;
  height: 100%;
  font-size: 25rem;
}
</style>
