<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  components: {},
};
</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  list-style: none;
  cursor: default;
}

a {
  text-decoration: none;
  color: #fff;
}

html,
body {
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  overflow-y: hidden;
}

#app {
  width: 100%;
  height: 100%;
}
</style>
