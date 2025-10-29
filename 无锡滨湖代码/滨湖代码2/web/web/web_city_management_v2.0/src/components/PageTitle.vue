<template>
  <div class="page-title">
    <div class="page-back">
      <div class="left">
        <!-- <span class="circle1">5</span> -->
        <div class="weather-box">
          <div v-for="item in weather" :key="item.name" class="item">
            <img :src="item.icon" alt />
            <div class="info">
              <span class="name">{{ item.name }}</span>
              <span class="value">
                {{ item.value }}
                <span style="font-size: 48rem">{{ item.num }}</span>
              </span>
            </div>
          </div>
        </div>
        <div class="left-page">
          <!--        <img-->
          <!--          src="../assets/pageTitle/事件灾害.png"-->
          <!--          style="margin-right: 20rem;width: 250rem;margin-top: 35rem;float: left"-->
          <!--          alt-->
          <!--        />-->
          <!--          <div v-for="item in weather" :key="item.name" class="item">
            <img :src="item.icon" alt/>
            <div class="info">
              <span class="name">{{ item.name }}</span>
              <span class="value">
                        {{ item.value }}
                        <span style="font-size: 48rem">{{ item.num }}</span>
                      </span>
            </div>
          </div>-->
          <!--        <div class="warning-panel" v-for="(item, index) in tipsArr" :key="index">-->
          <!--          <div class="panel-row">-->
          <!--            <span v-if="item.icon" class="a-icon" :class="item.icon"></span>-->
          <!--            {{ item.title }}-->
          <!--          </div>-->
          <!--          <div class="panel-line1" v-if="item.tips[0].title === 'AQI'">-->
          <!--            <div class="title1">{{ item.tips[0].title }}</div>-->
          <!--            <div class="title2" :style="{ color: item.tips[0].color }">{{ item.tips[0].num }}</div>-->
          <!--            <div class="title3" :style="{ background: item.tips[0].color }">{{ item.tips[0].price }}</div>-->
          <!--          </div>-->
          <!--          <div class="panel-line" :class="func(item.tips[0].color)" v-else>-->
          <!--            <div class="cell-left">{{ item.tips[0].title }}</div>-->
          <!--            <div class="cell-right">{{ item.tips[0].num }}</div>-->
          <!--          </div>-->
          <!--          <div class="panel-line" :class="func(item.tips[1].color)">-->
          <!--            <div class="cell-left">{{ item.tips[1].title }}</div>-->
          <!--            <div class="cell-right">{{ item.tips[1].num }}</div>-->
          <!--          </div>-->
          <!--        </div>-->
        </div>
        <!-- <div class="button1" style="cursor: pointer" @click="openDialog">切换事件</div> -->
        <!-- <i class="el-icon-caret-bottom"></i> -->
      </div>
      <!-- <div class="y2" v-if="eventVisible">
        <img ref="box" src="../assets/dialog/重大事件.png" alt="" />
        <div class="cheng1" @click="showvideo">x</div>
      </div>-->
      <div class="center">
        <!--        <span class="title">城市运行管理平台</span>-->
        <span class="title">滨湖智脑</span>
      </div>
      <div class="center-menu-bar">
        <div class="left-bar bar-list">
          <div class="bar-menu" @click="menuActive('防汛防台')">防汛防台</div>
          <div class="bar-menu" @click="menuActive('滨湖城管')">滨湖城管</div>
        </div>
        <div class="right-bar bar-list">
          <div class="bar-menu" @click="menuActive('自然规划局')">自然规划局</div>
          <div class="bar-menu" @click="menuActive('农业大数据')">农业大数据</div>

          <el-dropdown style="cursor: pointer">
            <div class="bar-more">...</div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="menuActive('应急综合')">应急综合</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
      <!--首页菜单中间栏  start      -->
      <!--      <ul class="center-menus">-->
      <!--        <li class="center-menu" v-for="(item, index) in menus" :key="index">-->
      <!--          <span style="cursor: pointer" @click="tiaozhuan(item)">{{ item.name }}</span>-->
      <!--          <ul class="center-menu-lis" v-for="(itm, ind) in item.menus" :key="ind">-->
      <!--            <li class="center-menu-li" @click="tiaozhuan(itm)">-->
      <!--              &lt;!&ndash; <a :href="itm.url" @click="chengguanLogin" target="_blank">-->
      <!--                {{ itm.name }}-->
      <!--              </a> &ndash;&gt;-->
      <!--              {{ itm.name }}-->
      <!--            </li>-->
      <!--          </ul>-->
      <!--        </li>-->
      <!--      </ul>-->
      <!--首页菜单中间栏  end      -->
      <div class="right">
        <!-- <div
          class="button"
          style="margin-right: 90rem; cursor: pointer; width: 200rem"
          @click="back"
        >日常监测</div>-->

        <div class="time-box">
          <img src="../assets/pageTitle/时间@2x.png" alt />
          <div class="info">
            <div
              class="time"
            >{{ nowTimeArr[0] }}:{{ nowTimeArr[1] }}:{{ nowTimeArr[2] }} {{ nowDay }} {{ nowWeek }}</div>
          </div>
        </div>
        <!--        <div class="weather-box">
          <img :src="img1" alt=""/>
          <div class="info">
            <span class="temperature">{{ temNum }}℃</span>
            <span class="weather" :title="weatherText">{{ weatherText }}</span>
          </div>
        </div>
        <img
          src="../assets/pageTitle/天气@2x.png"
          style="position: absolute; right: -0.9%; top: 25rem; cursor: pointer"
          @click="showVisible = true"
          alt
        />-->
        <i class="el-icon-back" @click="back"></i>
        <i class="el-icon-switch-button" @click="loginOut"></i>
      </div>
      <div v-if="showWeatherVisible" class="showWeatherVisible">
        <div class="cheng1" v-if="showWeatherVisible" @click="cheng1">x</div>
        <iframe id="iframe" :src="link" frameborder="no" scrolling="no" allowtransparency="true"></iframe>
      </div>
      <el-dialog
        title="气象预报"
        :visible.sync="showVisible"
        :modal="false"
        class="videoDialogDIY default-dialog-class large-screen"
        :append-to-body="true"
        :before-close="handleClose"
      >
        <div class="dd-title-back">
          <div class="dd-title-back-s"></div>
        </div>
        <div class="dd-back-area top-right-area"></div>
        <div class="dd-back-angle top-left-angle"></div>
        <div class="dd-back-angle top-right-angle"></div>
        <div class="dd-back-angle bottom-left-angle"></div>
        <div class="dd-back-angle bottom-right-angle"></div>
        <div class="d-maintainer">
          <iframe class="d-iframe" src="http://www.weather.com.cn/weather1d/101190206.shtml#input"></iframe>
          <!-- <span class="close-tip" @click="showVisible=false"></span> -->
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import Axios from '@/utils/request'
// import axios from '@/utils/request'
import md5 from 'js-md5'

export default {
  name: 'PageTitle',
  // components: { importPosition },
  props: ['title'],
  data() {
    return {
      showVisible: false,
      tipsArr: [
        // {
        //   title: '灾害天气',
        //   icon: 'icon-zrzh',
        //   tips: [
        //     {
        //       title: '暴雨预警',
        //       num: '1',
        //       color: '红色'
        //     },
        //     {
        //       title: '防汛响应',
        //       num: '0',
        //       color: '红色'
        //     }
        //   ]
        // },
        // {
        //   title: '自然灾害',
        //   icon: 'icon-rqaq',
        //   tips: [
        //     {
        //       title: '地震预警',
        //       num: '0',
        //       color: '红色'
        //     },
        //     {
        //       title: '山洪预警',
        //       num: '0'
        //     }
        //   ]
        // },
        // {
        //   title: '燃气安全',
        //   icon: 'icon-csjt',
        //   tips: [
        //     {
        //       title: '今日报警',
        //       num: '0',
        //       color: '红色'
        //     },
        //     {
        //       title: '重大风险（km）',
        //       num: '0'
        //     }
        //   ]
        // },
        // {
        //   title: '城市交通',
        //   icon: 'icon-shaq',
        //   tips: [
        //     {
        //       title: '拥堵指数',
        //       num: '0'
        //     },
        //     {
        //       title: '大客流预警',
        //       num: '0',
        //       color: '红色'
        //     }
        //   ]
        // },
        // {
        //   title: '社会安全',
        //   icon: 'icon-shaq',
        //   tips: [
        //     {
        //       title: '群访集访',
        //       num: '0'
        //     },
        //     {
        //       title: '刑事案件',
        //       num: '0',
        //       color: '红色'
        //     }
        //   ]
        // }
      ],
      nowTimeArr: ['00', '00', '00'],
      nowDay: '2018-01-01',
      nowWeek: '星期一',
      timeInterval: null,
      timeInterval2: null,
      allWeek: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
      isActive: false,
      weather: [
        {
          icon: require('../assets/pageTitle/降雨@2x.png'),
          name: '降雨量(小时)',
          value: '无降雨量',
          num: '' // mm
        },
        {
          icon: require('../assets/pageTitle/风向@2x.png'),
          name: '风向',
          value: '北'
        },
        {
          icon: require('../assets/pageTitle/风速new@2x.png'),
          name: '风速',
          value: '3',
          num: '级'
        },
        {
          icon: require('../assets/pageTitle/气压@2x.png'),
          name: '气压',
          value: '750',
          num: 'hPa'
        },
        {
          icon: require('../assets/pageTitle/湿度new@2x.png'),
          name: '湿度',
          value: '73',
          num: '%'
        }
      ],
      lineDialogVisible: false,
      precinctName: '',
      area: sysConfig.area,
      temNum: '27',
      weatherText: '多云',
      windDirect: '',
      img1: '',
      link: '',
      showWeatherVisible: false,
      weatherTime: 60 * 1000 * 30,
      timer: null,
      menus: sysConfig.topMenus
    }
  },
  methods: {
    createURL(urlModel) {
      const time = new Date().valueOf()
      const nonce = 'MI1udsfYgUk'
      let code = md5(nonce + time).toUpperCase()
      const account = 'cy000001'
      const serverName = 'bhcg'
      urlModel = urlModel.replace('${serverName}', serverName)
      urlModel = urlModel.replace('${code}', code)
      urlModel = urlModel.replace('${time}', time)
      urlModel = urlModel.replace('${account}', account)
      window.open(urlModel, '_target')
    },
    tiaozhuan(item) {
      if (item.name !== '更多') {
        if (['滨湖城管', '自然规划局'].includes(item.name)) {
          this.createURL(item.url)
          return
        }

        window.open(item.url, '_target')
      }
    },
    handleClose() {
      this.showVisible = false
    },
    func(val) {
      if (val === '黄色') {
        return 'classYellow'
      }
      if (val === '橙色') {
        return 'classOrange'
      }
      if (val === '红色') {
        return 'classRed'
      }
    },
    weatherOk() {
      this.link = 'http://10.165.1.4:81/dtqxweb/zncj/index.html'
      this.showWeatherVisible = true
    },
    cheng1() {
      this.showWeatherVisible = false
    },
    initWeather() {
      // flood  apis
      Axios.get('/apis/gaoDeApi/queryWeather').then((r) => {
        let data = r.data.data
        if (data.precip) {
          this.weather[0].value = data.text
          this.weather[0].num = 'mm'
        } else {
          this.weather[0].value = '无降雨量'
          this.weather[0].num = ''
        }
        // this.weather[0].value = data.text
        this.weather[1].value = data.windDir || '北'
        this.weather[2].value = data.windspeed || '3'
        this.weather[3].value = data.pressure || '750'
        this.weather[4].value = data.humidity || '73'
        // this.weatherText = data.situation.replace(/\s*!/g, '')
        // this.temNum = data.temperature
        // this.img1 = data.situationimg
      })
    },
    initTime() {
      this.nowDay = moment().format('YYYY/MM/DD')
      this.nowWeek = this.allWeek[moment().day()]
      this.$nextTick(() => {
        this.timeInterval = setInterval(() => {
          let timeStr = moment().format('HH:mm:ss')
          this.nowTimeArr = timeStr.split(':')
        }, 1000)
        this.timeInterval2 = setInterval(() => {
          this.nowDay = moment().format('YYYY/MM/DD')
          this.nowWeek = this.allWeek[moment().day()]
        }, 1000 * 60 * 15)
      })
    },
    warningInfo() {
      Axios.get('/apis/warningInfo/getInfo').then((r) => {
        if (r.data && r.data.code === 200) {
          let data = r.data.data
          this.tipsArr[0].tips[0].num = data.AQI || 0
          this.tipsArr[0].tips[0].price = data['空气质量'] || ''
          if (data['质量颜色'] === '黄') {
            this.tipsArr[0].tips[0].color = 'yellow'
          } else if (data['质量颜色'] === '绿') {
            this.tipsArr[0].tips[0].color = 'lime'
          } else if (data['质量颜色'] === '褐') {
            this.tipsArr[0].tips[0].color = 'saddlebrown'
          } else if (data['质量颜色'] === '红') {
            this.tipsArr[0].tips[0].color = 'red'
          } else if (data['质量颜色'] === '橙') {
            this.tipsArr[0].tips[0].color = 'orange'
          } else if (data['质量颜色'] === '紫') {
            this.tipsArr[0].tips[0].color = 'purple'
          } else {
            this.tipsArr[0].tips[0].color = ''
          }
          this.tipsArr[0].tips[1].num = data['空气质量预警'] || 0
          this.tipsArr[0].tips[1].color = data['空气质量预警等级颜色'] || ''
          this.tipsArr[1].tips[0].num = data['暴雨预警'] || 0
          this.tipsArr[1].tips[0].color = data['暴雨预警等级颜色'] || ''
          this.tipsArr[1].tips[1].num = data['防汛预警'] || 0
          this.tipsArr[1].tips[1].color = data['防汛响应预警等级颜色'] || ''
          this.tipsArr[2].tips[0].num = data['地震预警'] || 0
          this.tipsArr[2].tips[0].color = data['地震预警等级颜色'] || ''
          this.tipsArr[2].tips[1].num = data['山洪预警'] || 0
          this.tipsArr[2].tips[1].color = data['山洪灾害预警等级颜色'] || ''
          this.tipsArr[3].tips[0].num = data['今日预警'] || 0
          this.tipsArr[3].tips[1].num = data['重大风险'] || 0
          this.tipsArr[4].tips[0].num = data['严重拥堵路段告警'] || 0
          this.tipsArr[4].tips[1].num = data['大客流预警'] || 0
          this.tipsArr[5].tips[0].num = data['群访集访'] || 0
          this.tipsArr[5].tips[1].num = data['食品药品安全'] || 0
        }
      })
    },
    back() {
      /* this.$root.Bus.$emit('eventVisible', true) */
      window.location.href = sysConfig.backUrl
    },
    loginOut() {
      localStorage.removeItem('cauthorization')
      localStorage.removeItem('userInfo')
      if (window.opener === null) {
        window.open("about:blank", "_self")
        window.location.href = "about:blank"
        window.close()
      } else {
        window.open('', '_self')
        window.close()
      }
      // window.opener = null
      // window.open('', '_self')
      // window.close()
      /* localStorage.removeItem('cauthorization')
      localStorage.removeItem('userInfo')
      window.open('http://2.22.51.26:30002/logout?logoutUrl=http://2.21.137.156:8080', '_self')
      window.close()
      window.open(
        'http://2.22.51.26:30002/oauth/authorize?client_id=CdZLrF07&client_secret=1914d35bc31ae09ae924a330a05e726888602004&redirect_uri=http://2.21.137.156:8080&response_type=code&scope=all'
      ) */
    },
    menuActive(name) {
      let filterO = this.menus.filter(t => t.name === name)
      console.log(filterO, this.menus);
      if (filterO.length > 0) {
        this.tiaozhuan(filterO[0])
      }
    },
    openDialog() {
      this.lineDialogVisible = true
    }
    // showvideo() {
    //   this.eventVisible = false
    // }
  },
  mounted() {
    this.initWeather()
    // this.queryWeather()
    this.initTime()
    // this.warningInfo()
    // this.timer = setInterval(() => {
    //   this.warningInfo()
    // }, 1000 * 60)
    if (this.$route.query.precinctId) {
      this.area.forEach((item) => {
        if (this.$route.query.precinctId === item.id) {
          this.precinctName = item.name
        }
      })
    } else {
      this.precinctName = sysConfig.defaultPrecinct.precinctName
    }
  },
  beforeDestroy() {
    this.timer && clearInterval(this.timer)
  }
}
</script>
<style lang="scss" scoped>
.videoDialogDIY {
  z-index: 10000 !important;

  ::v-deep .el-dialog {
    position: relative;
    // .el-dialog__header {
    //   display: none;
    // }
    .el-dialog__body {
      display: flex;
      align-items: center;
      justify-content: center;

      .d-maintainer {
        width: 100%;
        height: 100%;
        // background-image: url('../assets/global/弹框_bg.png');
        // background-size: 100% 100%;
        position: relative;
        display: flex;
        justify-content: space-around;

        .d-iframe {
          width: 100%;
          height: 100%;
        }

        .close-tip {
          display: inline-block;
          width: 80rem;
          height: 50rem;
          position: absolute;
          right: 15rem;
          top: 20rem;
        }
      }
    }
  }
}
</style>
<style scoped lang="scss">
.page-title {
  position: relative;
  z-index: 100;
  height: 400rem;
  background: linear-gradient(to bottom, RGBA(0, 7, 44, 1), RGBA(0, 7, 44, 0.9) 75%, RGBA(1, 31, 101, 0));

  .page-back {
    width: 100%;
    height: 100%;
    background: url('../assets/pageTitle/TOP.png');
    background-size: 100% 100%;
    background-position: center center;

    .showWeatherVisible {
      position: relative;
      width: 50%;
      left: 25%;
      height: 1020rem;
      top: 15%;
      background: #051421;

      .cheng1 {
        position: absolute;
        top: 2%;
        right: 2%;
        font-size: 50rem;
        color: #bea760;
        cursor: pointer;
      }

      #iframe {
        width: 100%;
        height: 100%;
      }
    }

    .weather-box {
      position: absolute;
      left: 0rem;
      top: -140rem;
      display: flex;
      align-items: center;
      .item {
        display: flex;
        margin-right: 80rem;
        align-items: center;

        img {
          width: 130rem;
        }

        .info {
          display: flex;
          flex-direction: column;
          margin-left: 9rem;
          width: 280rem;
          .name {
            font-size: 48rem;
            font-family: PingFang SC;
            font-weight: 400;
            color: #bee5ff;
          }

          .value {
            font-size: 48rem;
            font-family: PingFang SC;
            font-weight: 400;
            color: #ffffff;
            margin-top: 3rem;
          }
        }
      }
    }
    .left {
      position: absolute;
      left: 40rem;
      top: 175rem;
      display: flex;

      .circle1 {
        width: 20rem;
        height: 20rem;
        background: #f44336;
        border-radius: 50%;
        font-size: 16rem;
        text-align: center;
        color: #fff;
        position: absolute;
        line-height: 18rem;
        top: 40rem;
        font-family: 'DIN';
        right: -75rem;
        font-weight: 600;
        z-index: 2;
      }

      .left-page {
        width: 4600rem;
        height: 100rem;
        margin-left: 90rem;
        // background: url('../assets/global/top-left.png') no-repeat;
        position: absolute;
        //top: 20rem;
        //控制天气
        // left: 30rem;
        // display: flex;
        top: 85rem;
        display: flex;
        align-items: center;
        // background: url('../assets/global/气象大脑.png') no-repeat;
        // background-size: contain;
        //justify-content: space-between;
        .title {
          font-size: 16rem;
          height: 30rem;

          span:first-of-type {
            width: 100rem;
            display: inline-block;
            background: red;
          }
        }

        .item {
          display: flex;
          margin-right: 210rem;
          align-items: center;

          img {
            width: 130rem;
          }

          .info {
            display: flex;
            flex-direction: column;
            margin-left: 9rem;

            .name {
              font-size: 48rem;
              font-family: PingFang SC;
              font-weight: 400;
              color: #bee5ff;
            }

            .value {
              font-size: 48rem;
              font-family: PingFang SC;
              font-weight: 400;
              color: #ffffff;
              margin-top: 3rem;
            }
          }
        }

        .warning-panel {
          display: flex;
          flex-direction: column;
          margin-top: 15rem;
          float: left;
          margin-right: 18rem;

          .panel-row {
            display: flex;
            align-items: center;
            font-size: 56rem;

            .a-icon {
              width: 63rem;
              height: 45rem;
              margin-right: 15rem;
              background-size: contain;
            }

            .a-icon.icon-zhtq {
              display: inline-block;
              background-image: url('../assets/pageTitle/灾害天气@2x.png');
              background-repeat: no-repeat;
              background-position: 0rem 3rem;
            }

            .a-icon.icon-zrzh {
              display: inline-block;
              background-image: url('../assets/pageTitle/自然灾害@2x.png');
              background-repeat: no-repeat;
            }

            .a-icon.icon-rqaq {
              display: inline-block;
              background-image: url('../assets/pageTitle/燃气安全@2x.png');
              background-repeat: no-repeat;
            }

            .a-icon.icon-csjt {
              display: inline-block;
              background-image: url('../assets/pageTitle/城市交通@2x.png');
              background-repeat: no-repeat;
            }

            .a-icon.icon-shaq {
              display: inline-block;
              background-image: url('../assets/pageTitle/社会安全@2x.png');
              background-repeat: no-repeat;
            }
          }

          .panel-line1 {
            display: flex;
            flex-wrap: nowrap;
            width: 160rem;
            height: 20rem;
            margin-top: 5rem;
            font-size: 11rem;

            .title1 {
              font-size: 16rem;
              font-family: DIN;
              font-weight: 600;
              color: #ffffff;
              line-height: 24rem;
            }

            .title2 {
              font-size: 24rem;
              font-family: DIN;
              font-weight: bold;
              color: #82ff5d;
              line-height: 20rem;
              margin-left: 18rem;
            }

            .title3 {
              width: 15rem;
              height: 15rem;
              background: #82ff5d;
              font-size: 12rem;
              font-family: PingFang SC;
              font-weight: 500;
              color: #ffffff;
              margin-left: 18rem;
              margin-top: 4rem;
            }
          }

          .panel-line {
            display: flex;
            flex-wrap: nowrap;
            width: 555rem;
            height: 75rem;
            margin-top: 5rem;
            font-size: 48rem;
            background: url('../assets/global/常规预警条.png') no-repeat;
            background-size: cover;
            margin-right: 160rem;

            .title1 {
              font-size: 16rem;
              font-family: DIN;
              font-weight: 600;
              color: #ffffff;
              line-height: 24rem;
            }

            .title2 {
              font-size: 24rem;
              font-family: DIN;
              font-weight: bold;
              color: #82ff5d;
              line-height: 20rem;
              margin-left: 18rem;
            }

            .title3 {
              width: 15rem;
              height: 15rem;
              background: #82ff5d;
              font-size: 12rem;
              font-family: PingFang SC;
              font-weight: 500;
              color: #ffffff;
              margin-left: 18rem;
              margin-top: 4rem;
            }

            .cell-left {
              width: 400rem;
              height: 100%;
              // background-color: rgba(34, 65, 98, 1);
              // border: 1rem solid rgba(66,59,77,0.9);
              display: flex;
              align-items: center;
              justify-content: center;
            }

            .cell-left.yb {
              // background-color: rgba(53, 131, 209, 1);
              // border: 1rem solid rgba(34,67,100,0.9);
            }

            .cell-right {
              margin-left: 64rem;
              width: 40rem;
              height: 100%;
              // background-color: rgba(34, 65, 98, 1);
              // border: 1rem solid rgba(66,59,77,0.9);
              display: flex;
              align-items: center;
              justify-content: center;
            }

            .cell-right.yb {
              // background-color: rgba(53, 131, 209, 1);
              // border: 1rem solid rgba(34,67,100,0.9);
            }
          }

          .panel-line.classYellow {
            background: url('../assets/global/黄.png') no-repeat;
            background-size: cover;
          }

          .panel-line.classOrange {
            background: url('../assets/global/橙.png') no-repeat;
            background-size: cover;
          }

          .panel-line.classRed {
            background: url('../assets/global/红.png') no-repeat;
            background-size: cover;
          }
        }

        .warning-panel:first-child {
          margin-left: 93rem;
        }
      }
    }

    .button1 {
      width: 153rem;
      height: 38rem;
      background: url(../assets/pageTitle/切换事件@2x.png) center no-repeat;
      color: #fff;
      font-size: 22rem;
      font-weight: 500;
      line-height: 38rem;
      text-align: center;
      position: absolute;
      left: 1174rem;
      top: 27rem;
    }

    .button {
      width: 153rem;
      height: 38rem;
      background: url(../assets/pageTitle/切换事件@2x.png) center no-repeat;
      color: #fff;
      font-size: 22rem;
      font-weight: 500;
      line-height: 38rem;
      text-align: center;
      position: absolute;
      right: 1095rem;
    }

    .center {
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      top: 15rem;
      text-align: center;

      .title {
        width: 595rem;
        height: 35rem;
        line-height: 360rem;
        vertical-align: center;
        font-size: 224rem;
        font-family: 'YouShe';
        // font-family: bblk;
        // font-weight: 900;
        color: #5b8cff;
        font-weight: 400;
        // text-shadow: 0rem 2rem 2rem rgba(0, 0, 0, 0.2);
        background: linear-gradient(to bottom, #fafcff 40%, #84c2ff);
        text-shadow: 0px 0px 96px rgba(12, 56, 97, 0.92);
        -webkit-background-clip: text;
        background-clip: text;
        -webkit-text-fill-color: transparent;
      }
    }

    .center-menus {
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      padding-left: 280rem;
      padding-right: 280rem;
      top: 440rem;
      text-align: center;
      background: url('../assets/center/链接_bg@2x.png');
      background-repeat: no-repeat;
      background-size: 100% 100%;
      width: 1816rem;
      height: 232rem;
      display: flex;
      align-items: center;
      justify-content: space-between;

      span {
        font-size: 72rem;
        font-family: Microsoft YaHei;
        font-weight: bold;
        font-style: italic;
        color: rgba(255, 255, 255, 1);
        text-shadow: 0rem 10rem 10rem #006cbb;

        &:hover {
          color: rgba(115, 251, 253, 1);
        }
      }

      .center-menu {
        height: 100%;
        width: 300rem;
        display: flex;
        align-items: center;
        justify-content: center;
        position: relative;

        .center-menu-lis {
          display: none;
          position: absolute;
          left: calc(50% - 150rem);
          top: 200rem;
          height: auto;
          width: auto;

          .center-menu-li {
            width: auto;
            min-width: 1100rem;
            margin-bottom: 20rem;
            height: 136rem;
            background: #172942;
            border: 4rem solid #165d95;
            border-radius: 8rem;
            padding-left: 70rem;
            padding-right: 70rem;
            line-height: 116rem;
            vertical-align: middle;
            font-size: 72rem;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #ffffff;
            cursor: pointer;
          }
        }

        &:hover {
          .center-menu-lis {
            display: inline-block;
          }
        }
      }
    }

    .center-menu-bar {
      position: absolute;
      top: 240rem;
      width: 100%;
      height: auto;
      display: flex;
      flex-wrap: nowrap;
      .left-bar {
        width: 50%;
        height: 132rem;
        display: flex;
        flex-direction: row-reverse;
        padding-right: 1690rem;
      }
      .right-bar {
        width: 50%;
        height: 132rem;
        display: flex;
        flex-direction: row;
        padding-left: 1690rem;
      }
      .bar-list {
        .bar-menu {
          cursor: pointer;
          width: 536rem;
          height: 132rem;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 72rem;
          font-family: Microsoft YaHei;
          font-weight: bold;
          color: #ffffff;
        }
        .bar-more {
          cursor: pointer;
          width: 160rem;
          height: 132rem;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 72rem;
          font-family: Microsoft YaHei;
          font-weight: bold;
          color: #ffffff;
        }
      }
      .bar-list.left-bar {
        .bar-menu {
          background: url('../assets/global/顶部标签-1@2x.png') no-repeat;
          background-size: 100% 100%;
        }
        .bar-menu:hover {
          background: url('../assets/global/12345.png') no-repeat;
          background-size: 100% 100%;
        }
      }
      .bar-list.right-bar {
        .bar-menu {
          background: url('../assets/global/顶部标签-2@2x.png') no-repeat;
          background-size: 100% 100%;
        }
        .bar-menu:hover {
          background: url('../assets/global/5323.png') no-repeat;
          background-size: 100% 100%;
        }
        .bar-more {
          background: url('../assets/global/顶部标签-3@2x.png') no-repeat;
          background-size: 100% 100%;
        }
      }
    }

    .right {
      position: absolute;
      right: 110rem;
      top: 260rem;
      display: flex;
      align-items: center;
      // background: url('../assets/global/气象大脑.png') no-repeat;
      height: 100rem;
      // background-size: contain;
      .item {
        display: flex;
        margin-right: 210rem;
        align-items: center;

        img {
          width: 130rem;
        }

        .info {
          display: flex;
          flex-direction: column;
          margin-left: 9rem;

          .name {
            font-size: 48rem;
            font-family: PingFang SC;
            font-weight: 400;
            color: #bee5ff;
          }

          .value {
            font-size: 48rem;
            font-family: PingFang SC;
            font-weight: 400;
            color: #ffffff;
            margin-top: 3rem;
          }
        }
      }

      img {
        width: 130rem;
      }

      .el-icon-back {
        font-size: 80rem;
        cursor: pointer;
        position: absolute;
        right: 100rem;
        top: -200rem;
      }

      .el-icon-switch-button {
        font-size: 80rem;
        cursor: pointer;
        position: absolute;
        right: -30rem;
        top: -200rem;
      }

      .weather-box {
        margin-top: 30rem;
        display: flex;
        align-items: center;
        margin-right: 190rem;

        .info {
          display: flex;
          flex-direction: column;
          margin-left: 9rem;
        }

        .weather {
          font-size: 48rem;
          font-family: PingFang SC;
          font-weight: 400;
          color: #bee5ff;
          margin-left: 100rem;
          display: inline-block;
          width: 100rem;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        .temperature {
          font-size: 72rem;
          font-family: DIN;
          font-weight: bold;
          color: #ffffff;
          margin-left: 100rem;
        }
      }

      .winder {
        font-size: 48rem;
        font-family: PingFang SC;
        font-weight: 600;
        color: #ffffff;
        background: linear-gradient(0deg, #f4eded 0%, #4d6fb3 110.04296875%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        position: absolute;
        top: 65rem;
        right: 0.7%;
      }

      .time-box {
        margin-top: 30rem;
        display: flex;
        align-items: center;

        .info {
          display: flex;
          margin-left: 9rem;
          align-items: center;
        }

        .day {
          font-size: 48rem;
          font-family: PingFang SC;
          font-weight: 400;
          color: #ffffff;
          margin-left: 10rem;
        }

        .time {
          font-size: 72rem;
          font-family: Microsoft YaHei;
          font-weight: bold;
          color: #ffffff;
          margin-left: 10rem;
        }
      }
    }

    .y2 {
      .cheng1 {
        position: absolute;
        top: 211.6%;
        left: 60%;
        font-size: 26rem;
        color: #bea760;
        cursor: pointer;
      }

      img {
        position: absolute;
        cursor: pointer;
        top: 210%;
        left: 35%;
      }
    }
  }
}
.el-dropdown-menu {
  background-color: #002c8d;
  border: 0;
  .el-dropdown-menu__item {
    color: #ffffff;
  }
  .el-dropdown-menu__item:focus,
  .el-dropdown-menu__item:not(.is-disabled):hover {
    background-color: #0047b6;
    color: #ffffff;
  }
}
</style>
