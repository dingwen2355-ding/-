<template>
  <div class="Right" :class="{ active: active }">
    <div class="leftpic"></div>
    <div class="right1"></div>
    <div class="y4 y5" v-if="y5visible">
      <img ref="box" src="../../../assets/dialog/动态.png" alt="" />
      <div class="cheng" @click="chengy5">返回</div>
    </div>
    <div class="zhiling" v-if="!y6visible" @click="ling">指令</div>
    <div class="y4 y6" v-if="y6visible">
      <div class="zhiling1" @click="ling1">领导指示</div>
      <img ref="box" src="../../../assets/dialog/指令.png" alt="" />
    </div>
    <div class="add" v-if="!y5visible" style="top: 43%; right: 39.9%" @click="showy5">更多>></div>
    <div class="add" v-if="y6visible" @click="show1">更多>></div>
    <div class="add1" v-if="y6visible" @click="show"></div>
    <div class="video" @click="video"></div>
    <div class="video1" @click="video1"></div>
    <!-- <Right1 />
    <Right2 />
    <Right3 /> -->
    <div class="y4" v-if="videodialog">
      <img ref="box" src="../../../assets/dialog/视频融合.png" alt="" />
      <!-- <iframe id="iframe" :src="link" frameborder="no" scrolling="no" allowtransparency="true"></iframe> -->
      <div class="cheng1" @click="videocheng">x</div>
    </div>
    <img
      style="z-index: 3"
      v-if="showgetVisible"
      ref="box"
      src="../../../assets/dialog/融合视频.png"
      alt=""
      @click="showvideo"
    />
    <img
      style="z-index: 3"
      v-if="dialogVisible"
      ref="box"
      src="../../../assets/dialog/指示.png"
      alt=""
      @click="showImg"
    />
    <div class="y1" v-if="dialogVisible1">
      <img ref="box" src="../../../assets/dialog/列表1.png" alt="" @click="showImg1" />
      <div class="new" @click="show3">新增</div>
      <div class="cheng" @click="cheng">x</div>
    </div>
    <div class="y2" v-if="dialogVisible2">
      <img ref="box" src="../../../assets/dialog/详情1.png" alt="" />
      <div class="cheng1" @click="cheng1">x</div>
    </div>
    <!-- <el-dialog :visible.sync="dialogVisible" :modal="false" width="1268rem" height="651rem">
    <div class=""></div>
    </el-dialog> -->
  </div>
</template>

<script>
// import Right1 from './right/Right1.vue'
// import Right2 from './right/Right2.vue'
// import Right3 from './right/Right3.vue'

export default {
  components: {
    // Right1,
    // Right2,
    // Right3
    // Right2To1,
    // Right2To2,
    // Right2To3,
    // Right2To4
  },
  props: ['active'],
  data() {
    return {
      dialogVisible: false,
      dialogVisible1: false,
      dialogVisible2: false,
      showgetVisible: false,
      videodialog: false,
      link: 'http://10.1.50.149:8080/VideoConferencing',
      y5visible: false,
      y6visible: false
    }
  },
  methods: {
    video() {
      // let url = sysConfig.urllink
      // let strWindowFeatures = 'width=1100,height=620'
      // window.open(url, (new Date().getTime()), strWindowFeatures)
      this.videodialog = true
    },
    ling() {
      this.y6visible = true
    },
    ling1() {
      this.y6visible = false
    },
    showy5() {
      this.y5visible = true
    },
    showy6() {
      this.y6visible = false
    },
    chengy5() {
      this.y5visible = false
    },
    videocheng() {
      this.videodialog = false
    },
    video1() {
      this.showgetVisible = true
      this.dialogVisible = false
      this.dialogVisible1 = false
      this.dialogVisible2 = false
    },
    showvideo() {
      this.showgetVisible = false
    },
    show() {
      this.dialogVisible = true
      this.dialogVisible1 = false
      this.dialogVisible2 = false
      this.showgetVisible = false
    },
    show3() {
      this.dialogVisible = true
    },
    show1() {
      this.dialogVisible1 = true
      this.dialogVisible = false
      this.showgetVisible = false
    },
    cheng() {
      this.dialogVisible1 = false
    },
    cheng1() {
      this.dialogVisible2 = false
    },
    showImg() {
      this.dialogVisible = false
    },
    showImg1() {
      this.dialogVisible2 = true
    }
  }
}
</script>

<style lang="scss">
.Right {
  position: absolute;
  right: -1132rem;
  top: 0;
  width: 1132rem;
  height: 100%;
  background-image: linear-gradient(to left, rgba(22, 33, 56, 1), rgba(22, 33, 56, 1) 65%, rgba(22, 33, 56, 0));
  transition: 1s;
  &.active {
    right: 0;
    z-index: 1;
  }
  .leftpic {
    width: 97%;
    height: 82%;
    background: url('../../../assets/dialog/右.png') no-repeat;
    background-size: 100% 100%;
    position: absolute;
    top: 178rem;
  }
  .zhiling {
    position: absolute;
    font-size: 17rem;
    color: #3b90cd;
    top: 22.7%;
    left: 9.8%;
    cursor: pointer;
  }
  .zhiling1 {
    position: absolute;
    font-size: 17rem;
    color: #3b90cd;
    top: 6.4%;
    left: 3.5%;
    cursor: pointer;
    z-index: 1;
    background: #1a3255;
  }
  .video {
    width: 83rem;
    height: 28rem;
    border: 3rem solid #143d65;
    cursor: pointer;
    position: absolute;
    top: 17.3%;
    left: 52.5%;
  }
  .video1 {
    width: 83rem;
    height: 28rem;
    border: 3rem solid #143d65;
    cursor: pointer;
    position: absolute;
    top: 17.3%;
    right: 3.7%;
  }
  .y4 {
    width: 1320rem;
    height: 675rem;
    position: absolute;
    top: 16.8%;
    right: 112.5%;
    background: #172e53;
    img {
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0%;
      left: 0%;
    }
    .cheng1 {
      position: absolute;
      top: 0%;
      right: 1.5%;
      font-size: 26rem;
      color: #bea760;
      cursor: pointer;
    }
  }
  .y5 {
    width: 695rem;
    height: 235rem;
    position: absolute;
    top: 42.2%;
    right: 39.3%;
    background: #172e53;
    img {
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0%;
      left: 0%;
    }
    .cheng {
      position: absolute;
      top: 3%;
      right: 1.1%;
      font-size: 15rem;
      color: #bea760;
      cursor: pointer;
      background: #162138;
    }
  }
  .y6 {
    width: 689rem;
    height: 210rem;
    position: absolute;
    top: 21.5%;
    right: 39.2%;
    background: #172e53;
    img {
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0%;
      left: 0%;
    }
  }
  .add {
    position: absolute;
    line-height: 16rem;
    top: 23%;
    right: 42.7%;
    font-size: 14rem;
    color: #ad965e;
    cursor: pointer;
    font-weight: 600;
    background: #172e53;
  }
  .add1 {
    width: 26rem;
    height: 27rem;
    position: absolute;
    line-height: 25rem;
    border: 2rem solid #143d65;
    top: 22.4%;
    right: 39.9%;
    font-size: 18rem;
    color: #4da0d1;
    cursor: pointer;
  }
  .y1 {
    .cheng {
      position: absolute;
      top: 22.4%;
      left: -28.2%;
      font-size: 26rem;
      color: #bea760;
      cursor: pointer;
    }
    .new {
      position: absolute;
      top: 27.9%;
      left: -33.3%;
      cursor: pointer;
      width: 71rem;
      height: 33rem;
      border: 1rem solid #0770b0;
      text-align: center;
      font-size: 13rem;
      line-height: 28rem;
      background: #0d365f;
      color: #bfd4e1;
    }
  }
  .y2 {
    .cheng1 {
      position: absolute;
      top: 22.6%;
      left: -28.2%;
      font-size: 26rem;
      color: #bea760;
      cursor: pointer;
    }
  }
  img {
    position: absolute;
    top: 22%;
    left: -126%;
    cursor: pointer;
  }
}
</style>
