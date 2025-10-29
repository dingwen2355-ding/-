<template>
  <div class="Right" :class="{ active: active }">
    <right2 />
    <!-- <right3 /> -->
  </div>
</template>

<script>
import right2 from './right/Right2.vue'
import right3 from './right/Right3.vue'
export default {
  components: { right2, right3 },
  props: ['active'],
  data() {
    return {}
  },
  methods: {}
}
</script>

<style lang="scss">
.Right {
  position: absolute;
  top: 0;
  width: 4380rem;
  height: 100%;
  right: 0;
  z-index: 1;
  background-image: linear-gradient(to left, RGBA(0, 13, 64, 1), RGBA(1, 31, 101, 1) 65%, RGBA(1, 31, 101, 0));
  //transition: 1s;
  &.active {
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
    width: 25rem;
    height: 25rem;
    position: absolute;
    line-height: 20rem;
    border: 2rem solid #4da0d1;
    right: 1.9%;
    top: -20rem;
    font-size: 29rem;
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
}
</style>
