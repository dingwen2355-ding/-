<template>
  <div class="centerTools">
    <!--    <top />
    <bottom />-->
    <!-- <div class="titlepic"></div> -->
    <!-- <div class="mappic"></div> -->
    <div class="carpic">
      <div class="zu1" v-if="!showDan" @click="changeCarBoard(1)"></div>
      <div class="zu1 dan1" v-else @click="changeCarBoard(1)"><span style="color: #ffd56f">单兵</span></div>
      <div class="zu3" @click="changeCarBoard(3)">
        <div>视频会议</div>
      </div>
      <!-- v-if="!showtalk"  -->
      <!-- <div class="zu3 dan2" v-else @click="changeCarBoard(3)"><span style="color: #ffd56f">通讯</span></div> -->
      <div :class="['zu',showTrue ? 'zu4' : 'zu2']" @click="changeCarBoard(2)">
        <span style="position: absolute; bottom: -40rem; left: -10rem; display: inline-block; width: 200rem"
        >无人机</span
        >
      </div>
      <!-- v-if="!showTrue"  -->
      <!-- <div v-else class="zu4" @click="show"><span style="color: #ffd56f">单兵</span></div> -->
    </div>
    <!--    <div class="friendly-link">
      <ul>
        <li v-for="(item,index) in friendLinkList" :key="index">
          <span>{{ index + 1 }}</span>
          <a :href="item.url" target="_blank">
            {{item.name}}
          </a>
        </li>
      </ul>
    </div>-->
    <div class="hiddentu" v-if="showTrue">
      <div
        v-for="sub in wrjSubjects"
        :class="['zu', sub.className, sub.id !== activeWrjSub ? 'wrj_zu' : 'wrj_zu_pre']"
        :key="sub.id"
        @click="checkChildStatus(sub)"
      >
        <span>{{ sub.title }}</span>
      </div>
      <!-- <div class="zu zu1" v-if="!showCar1" @click="car1"><span>出租车</span></div> -->
      <!-- <div class="zu zu1 car1" v-else @click="car1"><span style="color: #ffd56f">出租车</span></div> -->
      <!-- <div class="zu zu2" v-if="!showCar2" @click="car2"><span>救护车</span></div>
      <div class="zu zu2 car2" v-else @click="car2"><span style="color: #ffd56f">救护车</span></div>
      <div class="zu zu3" v-if="!showCar3" @click="car3"><span>消防车</span></div>
      <div class="zu zu3 car3" v-else @click="car3"><span style="color: #ffd56f">消防车</span></div>
      <div class="zu zu4" v-if="!showCar4" @click="car4"><span>公交车</span></div>
      <div class="zu zu4 car4" v-else @click="car4"><span style="color: #ffd56f">公交车</span></div> -->
    </div>
    <div class="hiddentu" v-if="showDan">
      <div
        v-for="sub in yddbSubjects"
        :class="['zu', sub.className, sub.id !== activeWrjSub ? 'wrj_zu' : 'wrj_zu_pre']"
        :key="sub.id"
        @click="checkChildStatus(sub)"
      >
        <span>{{ sub.title }}</span>
      </div>
    </div>
    <!-- <div class="hiddentu hiddentu1" v-if="showtalk">
      <div class="zu zu5" v-if="!showCar5" @click="car5"></div>
      <div class="zu zu5 car5" v-else @click="car5"></div>
      <div class="zu zu6" v-if="!showCar6" @click="car6"><span style="margin-left: -48rem">卫星电话</span></div>
      <div class="zu zu6 car6" v-else @click="car6">
        <span style="color: #ffd56f; margin-left: -48rem">卫星电话</span>
      </div>
    </div> -->
    <el-dialog
      :visible.sync="dialogVisible"
      width="50%"
      custom-class="meetFrameDialog"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <p slot="title" style="color: #fff; font-size: 60rem">华为视频会议</p>
      <iframe src="https://2.20.233.33" style="width: 100%; height: 4000rem"></iframe>
    </el-dialog>
  </div>
</template>

<script>
export default {
  components: {
    // top,
    // bottom
  },
  data() {
    return {
      url: '',
      showTrue: false,
      showDan: false,
      showtalk: false,
      showCar1: false,
      showCar2: false,
      showCar3: false,
      showCar4: false,
      showCar5: false,
      showCar6: false,
      dialogVisible: false,
      friendLinkList: [
        {
          name: '滨湖区智慧城市管理平台',
          url: 'http://2.20.5.20:8688'
        },
        {
          name: '滨湖区农业农村大数据平台',
          url: 'http://58.215.18.27:31010'
        },
        {
          name: '网格化系统',
          url: 'http://2.16.66.41:8888'
        }
      ],
      activeWrjSub: '',
      activeYddb: ''
      // wrjSubjects: sysConfig.localVideo.wrj
    }
  },
  watch: {
    showtalk(val) {
      this.dialogVisible = val
    }
  },
  mounted() {
    // this.$nextTick(() => {
    //   // fetch('https://2.20.233.33', { method: 'GET' }).then(res => {
    //   fetch('http://localhost:9530/', { method: 'GET' }).then(res => {
    //     if (res.ok) {
    //       console.log('飒飒法', res)
    //       return res.text()
    //     } else {
    //       throw new Error('Faild connect')
    //     }
    //   }).then(html => {
    //     this.url = 'data:text/html;charset=utf-8,' + encodeURIComponent(html)
    //     console.log('各位人纷纷为', this.url)
    //   }).catch(error => {
    //     console.log(error)
    //   })
    // })
  },
  computed: {
    yddbSubjects() {
      return sysConfig.localVideo.yddb.map((item, index) => {
        item.className = 'zu' + (index + 1)
        return item
      })
    },
    wrjSubjects() {
      return sysConfig.localVideo.wrj.map((item, index) => {
        item.className = 'zu' + (index + 1)
        return item
      })
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then((_) => {
          this.showtalk = false
          done()
        })
        .catch((_) => {
        })
    },
    changeCarBoard(type) {
      // 1 单兵 2无人机 3通讯
      if (type === 1) {
        if (this.showDan) {
          this.showDan = false
          return
        }
        this.showTrue = false
        this.showtalk = false
        this.show1()
      } else if (type === 2) {
        if (this.showTrue) {
          this.showTrue = false
          return
        }
        this.showDan = false
        this.showtalk = false
        this.show()
      } else {
        /* if (this.showtalk) {
          this.showtalk = false
          return
        }
        this.showTrue = false
        this.showDan = false
        this.show2() */
        this.showTrue = false
        this.showDan = false
        this.showtalk = false
        window.open('https://2.20.233.33', '_blank')
      }
    },
    checkChildStatus(item) {
      this.activeWrjSub = item.id
      let data = {
        deviceId: item.id,
        deviceTYpeName: item.title,
        type: item.type,
        url: item.url
      }
      this.$root.Bus.$emit('openVideoWindow', data)
    },
    show() {
      this.showTrue = !this.showTrue
      this.showtalk = false
    },
    show1() {
      this.showDan = !this.showDan
      this.$root.Bus.$emit('showDan', this.showDan)
    },
    show2() {
      this.showtalk = !this.showtalk
      this.showTrue = false
      // this.$root.Bus.$emit('showtalk', this.showtalk)
    },
    car1() {
      this.showCar1 = !this.showCar1
      this.$root.Bus.$emit('showCar', 'taxi')
    },
    car2() {
      this.showCar2 = !this.showCar2
      this.$root.Bus.$emit('showCar', 'ambulance')
    },
    car3() {
      this.showCar3 = !this.showCar3
      this.$root.Bus.$emit('showCar', 'firetruck')
    },
    car4() {
      this.showCar4 = !this.showCar4
      this.$root.Bus.$emit('showCar', 'bus')
    },
    car5() {
      this.showCar5 = !this.showCar5
    },
    car6() {
      this.showCar6 = !this.showCar6
    }
  }
}
</script>

<style lang="scss">
.meetFrameDialog {
  background: #092a75;
}

.centerTools {
  position: absolute;
  width: 210rem;
  // height: 968rem;
  top: 320rem;
  right: 27.5%;
  z-index: 1;

  .titlepic {
    width: 728rem;
    height: 65rem;
    background: url('../../../../assets/dialog/口号.png') no-repeat;
    background-size: 100% 100%;
    position: absolute;
    top: -101rem;
    right: 376rem;
  }

  .mappic {
    width: 463rem;
    height: 105rem;
    background: url('../../../../assets/dialog/地图类型切换.png') no-repeat;
    background-size: 100% 100%;
    position: absolute;
    bottom: -510rem;
    right: 0rem;
  }

  .friendly-link {
    width: 850rem;
    height: auto;
    bottom: -440%;
    position: absolute;
    right: 6400rem;
    z-index: 2;

    ul {
      li {
        width: 100%;
        height: 150rem;
        margin-bottom: 20rem;
        background: #092f52;
        display: flex;
        align-items: center;
        padding-left: 10rem;

        span {
          width: 88rem;
          height: 100rem;
          line-height: 100rem;
          display: inline-block;
          border: 1rem solid RGBA(61, 91, 155, 1);
          text-align: center;
          background: #1a2f52;
          color: RGBA(0, 144, 255, 1);
          font-size: 45rem;
          margin-left: 5rem;
          margin-right: 30rem;
        }
      }
    }
  }

  .carpic {
    width: 1590rem;
    height: 600rem;
    background: url('../../../../assets/newIcon/底座.png') no-repeat;
    background-size: 100% 100%;
    position: fixed;
    top: 3172rem;
    right: 6936rem;
    z-index: 2;

    .zu1 {
      width: 135rem;
      height: 180rem;
      background: url('../../../../assets/newIcon/单兵.png') no-repeat;
      background-size: 100% 100%;
      position: absolute;
      top: 265rem;
      left: 225rem;
      cursor: pointer;
    }

    .dan1 {
      width: 135rem;
      height: 130rem;
      background: url('../../../../assets/newIcon/单兵@y.png') no-repeat;
      background-size: 100% 100%;
      position: absolute;
      top: 265rem;
      left: 225rem;
      cursor: pointer;

      span {
        display: inline-block;
        padding-top: 120rem;
        font-weight: bold;
        font-size: 48rem;
        margin-left: 16rem;
      }
    }

    .zu3 {
      width: 135rem;
      height: 135rem;
      background: url('../../../../assets/newIcon/融合通讯.png') no-repeat;
      background-size: 100% 100%;
      position: absolute;
      top: 265rem;
      right: 225rem;
      font-size: 48rem;
      cursor: pointer;

      div {
        width: 200rem;
        padding-top: 150rem;
        font-weight: bold;
        font-size: 48rem;
        margin-left: 0rem;
      }
    }

    .dan2 {
      width: 135rem;
      height: 135rem;
      background: url('../../../../assets/newIcon/融合通讯@y.png') no-repeat;
      background-size: 100% 100%;
      position: absolute;
      top: 265rem;
      right: 225rem;
      cursor: pointer;

      span {
        display: inline-block;
        padding-top: 120rem;
        font-weight: bold;
        font-size: 48rem;
        margin-left: 16rem;
      }
    }

    .zu2 {
      width: 135rem;
      height: 180rem;
      color: #fff;
      background: url('../../../../assets/newIcon/无人机.png') no-repeat;
      background-size: 70% 60%;
      background-position: center;
      position: absolute;
      top: 100rem;
      left: 725rem;
      cursor: pointer;
    }

    .zu4 {
      width: 135rem;
      height: 180rem;
      color: #ffd56f;
      background: url('../../../../assets/newIcon/无人机@y.png') no-repeat;
      background-size: 70% 60%;
      background-position: center;
      position: absolute;
      top: 100rem;
      left: 725rem;
      cursor: pointer;
    }
  }

  .hiddentu {
    width: 2060rem;
    height: 800rem;
    background: url('../../../../assets/newIcon/展开.png') no-repeat;
    background-size: 100% 100%;
    position: fixed;
    top: 2872rem;
    left: 6596rem;
    z-index: 1;

    .zu {
      min-width: 135rem;
      height: 130rem;
      cursor: pointer;

      span {
        margin-top: 120rem;
        margin-left: -3rem;
        display: inline-block;
        white-space: nowrap;
        font-size: 48rem;
      }
    }

    .wrj_zu {
      background: url('../../../../assets/newIcon/二级指标@2x.png') no-repeat;
      background-size: 100% 100%;
      background-size: contain;
      background-position: center;
    }

    .wrj_zu_pre {
      color: #ffd56f;
      background: url('../../../../assets/newIcon/二级指标_pre@2x.png') no-repeat;
      background-size: 100% 100%;
      background-size: contain;
      background-position: center;
    }

    .zu1 {
      position: absolute;
      top: 78rem;
      left: 618rem;
      animation: run 3s linear;
      font-size: 48rem;
      // background: url('../../../../assets/dialog/组7.png') no-repeat;
      // background-size: 100% 100%;
    }

    .car1 {
      width: 135rem;
      height: 130rem;
      background: url('../../../../assets/dialog/出租车选中.png') no-repeat;
      background-size: 100% 100%;
    }

    .zu2 {
      position: absolute;
      top: 78rem;
      left: 1230rem;
      animation: run 5s linear;
      font-size: 48rem;
      // background: url('../../../../assets/dialog/救护车初始.png') no-repeat;
      // background-size: 100% 100%;
    }

    .car2 {
      width: 135rem;
      height: 130rem;
      background: url('../../../../assets/dialog/救护车选中.png') no-repeat;
      background-size: 100% 100%;
    }

    .zu3 {
      position: absolute;
      top: 440rem;
      left: 125rem;
      animation: run 1s linear;
      // background: url('../../../../assets/dialog/消防车初始.png') no-repeat;
      // background-size: 100% 100%;
    }

    .car3 {
      width: 135rem;
      height: 130rem;
      background: url('../../../../assets/dialog/消防车选中.png') no-repeat;
      background-size: 100% 100%;
    }

    .zu4 {
      position: absolute;
      top: 440rem;
      right: 125rem;
      font-size: 48rem;
      animation: run 7s linear;
      background: url('../../../../assets/dialog/公交车初始.png') no-repeat;
      background-size: 100% 100%;
    }

    .car4 {
      width: 135rem;
      height: 130rem;
      background: url('../../../../assets/dialog/公交车选中.png') no-repeat;
      background-size: 100% 100%;
    }

    .zu5 {
      position: absolute;
      top: 200rem;
      left: 510rem;
      animation: run 3s linear;
      background: url('../../../../assets/global/800M.png') no-repeat;
      background-size: 100% 100%;
      transform: scale(1.5);
    }

    .car5 {
      width: 135rem;
      height: 130rem;
      background: url('../../../../assets/global/800M-选中.png') no-repeat;
      background-size: 100% 100%;
    }

    .zu6 {
      position: absolute;
      top: 165rem;
      right: 510rem;
      animation: run 3s linear;
      background: url('../../../../assets/global/卫星电话.png') no-repeat;
      background-size: 100% 100%;
    }

    .car6 {
      width: 135rem;
      height: 130rem;
      background: url('../../../../assets/global/卫星电话-选中.png') no-repeat;
      background-size: 100% 100%;
    }

    @keyframes run {
      0% {
        opacity: 0;
      }
      50% {
        opacity: 0.5;
      }
      100% {
        opacity: 1;
      }
    }
  }

  .hiddentu1 {
    background: url('../../../../assets/global/2等分.png') no-repeat;
    background-size: 100% 100%;
  }

  ::v-deep .el-button {
    color: #ffffff;
    background: linear-gradient(90deg, #00A3FF 0%, #8AD4FF 100%);
  }
}
</style>
