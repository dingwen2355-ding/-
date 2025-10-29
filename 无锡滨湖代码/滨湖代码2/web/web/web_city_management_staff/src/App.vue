<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
import axios from '@/utils/request'
export default {
  name: 'App',
  data () {
    return {
      precinctName: sysConfig.defaultPrecinct.precinctName,
      precinctId: sysConfig.defaultPrecinct.precinctId,
      screenWidth: '',
      screenHeight: ''
    }
  },
  mounted () {
    this.resize()
    window.onresize = function () {
      this.resize()
    }.bind(this)
    this.intervalRefreshData()
    if (sessionStorage.precinct && sessionStorage.precinct !== this.$route.query.precinctId) {
      if (sessionStorage.userId && sessionStorage.accessToken) {
        let url = '/apis/sso/checkToken?accessToken=' + sessionStorage.accessToken + '&userId=' + sessionStorage.userId
        if (this.$route.query.precinctId) {
          url += '&precinctId=' + this.$route.query.precinctId
        }
        let obj = {
          userId: sessionStorage.userId,
          accessToken: sessionStorage.accessToken
        }
        sessionStorage.precinct = ''
        sessionStorage.accessToken = ''
        sessionStorage.userId = ''
        sessionStorage.name = ''
        sessionStorage.pass = ''
        sessionStorage.trueName = ''
        axios.post(url).then((r) => {
          let data = r.data.data
          window.name = '大屏跳转'
          if (data.code === 200 && data.data === true) {
            sessionStorage.isLogin = 1
            sessionStorage.precinct = this.$route.query.precinctId
            sessionStorage.accessToken = obj.accessToken
            sessionStorage.userId = obj.userId
            sessionStorage.trueName = sessionStorage.userId
            if (this.$route.query.precinctId) {
              if (this.$route.query.eventId) {
                this.$router.push('/ChengDuCityManagementStaff?precinctId=' + this.$route.query.precinctId + '&eventId=' + this.$route.query.eventId)
              } else {
                this.$router.push('/ChengDuCityManagementStaff?precinctId=' + this.$route.query.precinctId)
              }
            } else {
              if (this.$route.query.eventId) {
                this.$router.push('/ChengDuCityManagementStaff?eventId=' + this.$route.query.eventId)
              } else {
                this.$router.push('/ChengDuCityManagementStaff')
              }
            }
          } else {
            if (this.$route.query.precinctId) {
              if (this.$route.query.eventId) {
                this.$router.push('/Login?precinctId=' + this.$route.query.precinctId + '&eventId=' + this.$route.query.eventId)
              } else {
                this.$router.push('/Login?precinctId=' + this.$route.query.precinctId)
              }
            } else {
              sessionStorage.precinctId = ''
              if (this.$route.query.eventId) {
                this.$router.push('/Login?eventId=' + this.$route.query.eventId)
              } else {
                this.$router.push('/Login')
              }
            }
          }
        })
      }
      if (sessionStorage.name && sessionStorage.pass) {
        let url = '/apis/sso/login?authType=password&loginId=' + sessionStorage.name + '&auth=' + sessionStorage.pass
        if (this.$route.query.precinctId) {
          url += '&precinctId=' + this.$route.query.precinctId
        }
        let obj = {
          name: sessionStorage.name,
          pass: sessionStorage.pass
        }
        axios.post(url).then((r) => {
          window.name = '大屏跳转'
          if (r.data.data && r.data.data.code === 200) {
            sessionStorage.isLogin = 1
            // let data = r.data.data.data.precinctId
            let data = obj
            sessionStorage.precinct = this.$route.query.precinctId
            sessionStorage.name = data.name
            sessionStorage.pass = data.pass
            if (r.data.data.data) {
              sessionStorage.trueName = r.data.data.data.username
            }
            if (this.$route.query.precinctId) {
              if (this.$route.query.eventId) {
                this.$router.push('/ChengDuCityManagementStaff?precinctId=' + this.$route.query.precinctId + '&eventId=' + this.$route.query.eventId)
              } else {
                this.$router.push('/ChengDuCityManagementStaff?precinctId=' + this.$route.query.precinctId)
              }
            } else {
              if (this.$route.query.eventId) {
                this.$router.push('/ChengDuCityManagementStaff?eventId=' + this.$route.query.eventId)
              } else {
                this.$router.push('/ChengDuCityManagementStaff')
              }
            }
          } else {
            sessionStorage.precinct = ''
            sessionStorage.name = ''
            sessionStorage.pass = ''
            if (this.$route.query.precinctId) {
              if (this.$route.query.eventId) {
                this.$router.push('/Login?precinctId=' + this.$route.query.precinctId + '&eventId=' + this.$route.query.eventId)
              } else {
                this.$router.push('/Login?precinctId=' + this.$route.query.precinctId)
              }
            } else {
              sessionStorage.precinctId = ''
              if (this.$route.query.eventId) {
                this.$router.push('/Login?eventId=' + this.$route.query.eventId)
              } else {
                this.$router.push('/Login')
              }
            }
          }
        })
      }
    }
  },
  methods: {
    resize () {
      // 系统整体缩放
      let cliWidth = document.documentElement.clientWidth || document.body.clientWidth
      let cliHeight = document.documentElement.clientHeight || document.body.clientHeight
      // let contW = cliWidth > 1920 ? 3840 : 1920
      // let contH = cliHeight > 1080 ? 2160 : 1080
      let contW = 3840
      let contH = 2160
      this.$store.dispatch('app/setRatio', contW)
      let w = cliWidth / contW
      let h = cliHeight / contH
      this.$store.dispatch('view/setResize', [w, h])
      $('#app')
        .css('transform', 'scale(' + w + ',' + h + ')')
        .css('transformOrigin', 'top left')
        .width(contW)
        .height(contH)
    },
    intervalRefreshData () {
      this.interval = setInterval(() => {
        this.$store.dispatch('view/setIntervalRefresh', new Date().getTime())
      }, 1000 * 60)
    },
    setPrecinctName(streetId, streetName, gridId, regionName) {
      localStorage.setItem('precinctName', streetName)
      localStorage.setItem('precinctId', streetId)
      localStorage.setItem('gridId', gridId)
      localStorage.setItem('regionName', regionName)
      this.$store.dispatch('app/setPrecinctName', streetName)
      this.$store.dispatch('app/setPrecinctId', streetId)
      this.$store.dispatch('app/setGridId', gridId)
      this.$store.dispatch('app/setRegionName', regionName)
    }
  }
}
</script>
<style lang="scss">
#app {
  font-family: "Microsoft YaHei", "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #5b5b5b;
  text-align: left;
  // overflow-x: hidden;
  // overflow-y: auto;
  &#app::-webkit-scrollbar {
    display: none;
  }
}
</style>
