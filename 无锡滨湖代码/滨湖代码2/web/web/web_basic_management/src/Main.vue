<template>
  <div id="mainPage">
    <header class="header">
      <TopNav :user-name="userName" />
    </header>
    <aside class="left" :class="{ leftRetract: isCollapse }">
      <LeftNav @changeNav="changeNav" />
    </aside>
    <InformationDialog v-if="$store.getters.informationVisible" />
    <section class="content" :class="{ contentRetract: isCollapse }">
      <router-view />
    </section>
  </div>
</template>

<script>
import LeftNav from './components/LeftNav'
import TopNav from './components/TopNav'
import InformationDialog from './views/information/informationDialog'
export default {
  name: 'Main',
  components: { LeftNav, TopNav, InformationDialog },
  data() {
    return {
      isCollapse: false,
      screenWidth: '',
      screenHeight: '',
      userName: '',
      emitEventName: '',
      forbid: false,
      mapShow: false,
      oriGpsInfo: '',
      areaSupport: false,
      MapPns: []
    }
  },
  watch: {
  },
  mounted() {
    this.userName = sessionStorage.getItem('user')
    let self = this
    /* //  十分钟查询Token是否过期
    this.reLoadTimer = setInterval(function () {
      // window.location.reload()
      let param = {
        Authorizatuin: sessionStorage.getItem('token')
      }
      axios.get('/auth/verifyAuthority/checkToken?' + qs.stringify(param)).then((res) => {
        if (res.data.result === 1) {
          MessageBox.confirm('用户信息已过期，请重新登录!', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            center: true
          })
            .then(() => {
              sessionStorage.clear()
              window.location.href = '/'
              // Notification({
              //   title: '成功',
              //   message: '已经退回登录页',
              //   type: 'success'
              // })
            })
            .catch(() => {
              window.location.reload()
              Notification.info({
                title: '消息',
                message: '已取消'
              })
            })
        }
      })
    }, 10 * 60 * 1000) */
    /* $(document).on('click', function () {
      self.reLoadTimer && clearInterval(self.reLoadTimer)
      self.reLoadTimerOut && clearTimeout(self.reLoadTimerOut)
      self.reLoadTimerOut = setTimeout(function () {
        self.reLoadTimer = setInterval(function () {
          // window.location.reload()
          let param = {
            Authorizatuin: sessionStorage.getItem('token')
          }
          axios.get('/auth/verifyAuthority/checkToken?' + qs.stringify(param)).then((res) => {
            if (res.data.result === 1) {
              MessageBox.confirm('用户信息已过期，请重新登录!', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
              })
                .then(() => {
                  sessionStorage.clear()
                  window.location.href = '/'
                  Notification({
                    title: '成功',
                    message: '已经退回登录页',
                    type: 'success'
                  })
                })
                .catch(() => {
                  window.location.reload()
                  Notification.info({
                    title: '消息',
                    message: '已取消'
                  })
                })
            }
          })
        }, 10 * 60 * 1000)
      }, 60 * 100)
    }) */
  },
  methods: {
    changeNav: function (val) {
      this.isCollapse = val
    }
  },
  beforeDestroy() {
    clearInterval(this.reLoadTimer)
    this.reLoadTimer = null
    clearInterval(this.reLoadTimerOut)
    this.reLoadTimerOut = null
  }
}
</script>

<style lang="scss" scoped>
#mainPage {
  width: 100%;
  height: 100%;
  overflow: hidden;
  background-color: #eeeeee;
  .header {
    width: 100%;
    position: absolute;
    height: 76px;
    color: white;
    background: #ffffff;
    //border-bottom: 1px solid #EEEEEE;
  }

  .left {
    position: absolute;
    top: 77px;
    left: 0;
    height: calc(100% - 64px);
    width: 240px;
  }

  .content {
    position: absolute;
    right: 0;
    bottom: 0;
    width: calc(100% - 240px);
    height: calc(100% - 77px);
    background: #f8f9ff;
    overflow: auto;
    padding: 20px;
  }

  .leftRetract {
    width: 0.12rem !important;
  }

  .contentRetract {
    width: calc(100% - 0.12rem) !important;
  }
}
</style>
