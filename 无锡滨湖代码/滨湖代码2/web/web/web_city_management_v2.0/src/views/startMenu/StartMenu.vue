<template>
  <div class="Command" id="Command">
    <PageTitle title="智慧蓉城运行中心" />
    <Left :active="left_btnVisible" />
    <Center />
    <Right :active="right_btnVisible" />
  </div>
</template>
<script>
import PageTitle from '@/components/PageTitle'
import Left from './modules/Left'
import Center from './modules/Center'
import Right from './modules/Right'
import Axios from '@/utils/request'
export default {
  name: 'App',
  components: {
    PageTitle,
    Left,
    Center,
    Right
  },
  data() {
    return {
      left_btnVisible: false,
      right_btnVisible: false,
      refreshInterval: null
    }
  },
  watch: {
    '$store.getters.eventId': {
      handler() {
        // this.getEventInfo()
      },
      immediate: true
    }
  },
  created() {
    // 刷新定时任务 10秒 事件动态、指挥决策相关数据
    this.startRefreshInterval()
    // 解决浏览器切换可能导致的定时器加速的问题
    document.onvisibilitychange = () => {
      if (document.visibilityState === 'visible') {
        this.startRefreshInterval()
      } else {
        clearInterval(this.refreshInterval)
      }
    }
  },
  mounted() {
    this.getEventInfo()
    setTimeout(() => {
      this.left_btnVisible = true
      this.right_btnVisible = true
      // this.$store.dispatch('view/setLeftBtnVisible', true)
      // this.$store.dispatch('view/setRightBtnVisible', true)
    }, 1000)
    /*this.resize()
    window.onresize = function () {
      this.resize()
    }.bind(this)*/
    this.$root.Bus.$on('overDueDialog', (data) => {
      this.overByond = data.isOverdue
      this.newObj = data.obj
    })
    this.$root.Bus.$on('eventVisible', (data) => {
      if (data) {
        this.left_btnVisible = false
        this.right_btnVisible = false
      }
    })
    // window.addEventListener('message', (e) => {
    //   console.log('data from boss' + e.data)
    //   if (e.data === 'close') {
    //     this.left_btnVisible = false
    //     this.right_btnVisible = false
    //     // window.name = '日常监测联调'
    //   }
    // }, false)
  },
  methods: {
    startRefreshInterval() {
      if (this.refreshInterval) {
        clearInterval(this.refreshInterval)
        this.refreshInterval = null
      }
      this.refreshInterval = setInterval(() => {
        // console.log('globalTimeIntervalRefreshEvent', new Date())
        this.$root.Bus.$emit('globalTimeIntervalRefreshEvent')
      }, 1000 * 10)
    },
    getEventInfo() {
      let url = '/apis/eventDeal/eventInfo'
      Axios.get(url).then((r) => {
        let data = r.data.data[0]
        let url = '/apis/manage/queryEventInfo'
        Axios.get(url).then((res) => {
          if (res.data.code === 200) {
            this.info = res.data.data.length > 0 ? res.data.data[0] : {}
            this.info.urgency = this.info.urgency || '重大'
            if (data) {
              data.obj = this.info
            }
            // this.$store.dispatch('view/setEventPlanId', this.info.planId)
            this.$store.dispatch('view/setEventInfo', data)
          }
        })
      })
    },
    resize() {
      // 系统整体缩放
      let cliWidth = document.documentElement.clientWidth || document.body.clientWidth
      let cliHeight = document.documentElement.clientHeight || document.body.clientHeight
      let contW = 15360
      let contH = 3780
      this.$store.dispatch('app/setRatio', contW)
      let w = cliWidth / contW
      let h = cliHeight / contH
      this.$store.dispatch('view/setResize', [w, h])
      this.$nextTick(() => {
        $('#Command')
          // .css('transform', 'scale(' + w + ',' + h + ')')
          // .css('transformOrigin', 'top left')
          .width(contW)
          .height(contH)
      })
      if (sessionStorage.getItem('checkTrue')) {
        sessionStorage.removeItem('checkTrue')
      }
    }
  },
  destroyed() {
    clearInterval(this.refreshInterval)
    this.refreshInterval = null
  }
}
</script>
<style lang="scss">
.Command {
  // background-color: #000;
  height: 100%;
  font-size: 48rem;
  position: relative;
  background: #000;
}
#Command {
  font-family: 'Microsoft YaHei', 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #fff;
  text-align: left;
  position: relative;
  top: 50%;
  width: 15360rem;
  height: 3780rem;
}
</style>
