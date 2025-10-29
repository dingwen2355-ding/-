
<template>
  <div id="mainPage">
    <header>
      <TopNav :user-name="userName" :user-id="userId" class="header" />
    </header>
    <!-- <div class="page-nav"></div> -->
    <aside class="menu" :class="{ leftRetract: isCollapse }">
      <LeftNav @changeNav="changeNav" />
    </aside>
    <div class="tagNab">
      <TagNav />
    </div>
    <keep-alive>
      <router-view class="page" :class="{ contentRetract: isCollapse }" />
    </keep-alive>
  </div>
</template>

<script>
import LeftNav from './components/LeftNav'
import TopNav from './components/TopNav'
import TagNav from './components/TagNav'
import $ from 'jquery'

export default {
  name: 'Main',
  components: { LeftNav, TopNav, TagNav },
  data() {
    return {
      isCollapse: false,
      screenWidth: '',
      screenHeight: '',
      userName: '',
      userId: ''
    }
  },
  mounted() {
    window.onresize = function () {
      this.screenWidth = document.documentElement.clientWidth || document.body.clientWidth
      this.screenHeight = document.documentElement.clientHeight || document.body.clientHeight
    }.bind(this)
    this.resize()
    this.userName = localStorage.getItem('user')
    this.userId = localStorage.getItem('userId')
  },
  watch: {
    screenWidth: function () {
      this.resize()
    },
    screenHeight: function () {
      this.resize()
    }
  },
  methods: {
    changeNav: function (val) {
      this.isCollapse = val
    },
    resize: function () {
      let contW = 1920
      let contH = 1080
      let cliWidth = document.documentElement.clientWidth || document.body.clientWidth
      let cliHeight = document.documentElement.clientHeight || document.body.clientHeight
      let w = cliWidth / contW
      let h = cliHeight / contH
      sessionStorage.transformW = w
      sessionStorage.transformH = h
      this.$store.dispatch('view/setResize', [w, h])
      $('#mainPage')
        .css('transform', 'scale(' + w + ',' + h + ')')
        .css('transformOrigin', 'top left')
        .css('overflow', 'hidden')
        .width(contW)
        .height(contH)
    }
  }
}
</script>

<style lang="scss" scoped>
#mainPage {
  width: 100%;
  height: 100%;
  overflow: hidden;
  background: #f1f2f5;

  .header {
    width: 100%;
    position: relative;
    color: #fff;
    height: 70px;
    background: linear-gradient(-90deg, #001d77 0%, #056cf2 100%);
  }

  .page-nav {
    width: 100%;
    height: 56px;
    background: #ffffff;
  }

  .menu {
    position: absolute;
    top: 70px;
    left: 0;
    height: calc(100% - 64px);
    width: 290px;
    background: #fff;
    box-shadow: 0px -2px 8px 0px rgba(0, 0, 0, 0.15);
  }

  .page {
    position: absolute;
    right: 20px;
    bottom: 20px;
    width: calc(100% - 330px);
    height: calc(100% - 134px);
    background: #fff;
    padding: 20px;
    overflow: auto;

    &.blank {
      background: transparent;
      padding: 0;
    }
  }

  .leftRetract {
    width: 64px !important;
  }

  .contentRetract {
    width: calc(100% - 104px) !important;
  }
}
</style>
