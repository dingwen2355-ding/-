<!-- 活动图片弹窗 -->
<template>
  <div class="ImgPop" v-show="ImgShow == true">
    <div class="ImgPop_img">
      <div class="flex">
        <div class="title">活动信息</div>

        <div class="close" @click="Close()">
          <img
            class="close_img"
            src="@/assets/party-building/img_close_btn@2x.png"
            alt="close"
          />
        </div>
      </div>

      <div class="Img">
        <div class="Title">{{ Title }}</div>
        <img class="img_" :src="Img" alt="活动图片" />
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "ImgPop",

  components: {},

  data() {
    return {
      ImgShow: false,
      name: "",
      Img: "",
      Title: "", //图片对应的文本
      ImgNumber: 0,
    };
  },

  methods: {
    Close() {
      console.log("关闭视频弹窗1");
      this.ImgShow = false;
    },
  },

  created() {},

  beforeMount() {},

  mounted() {},
};
</script>

<style lang="scss" scoped>
.ImgPop {
  position: fixed;
  top: 15vh;
  left: 50%;
  transform: translateX(-50%);

  .ImgPop_img {
    background-image: url("@/assets/party-building/img_window_800_bg@2x.png");
    background-size: 100% 100%;
    width: 800px;
    height: 580px;

    .flex {
      display: flex;
      justify-content: space-between;
    }
    .title {
      font-size: 15px;
      transform: translate(58px, 17px);
    }

    .close {
      transform: translate(0, 13px);
      margin-right: 10px;
      cursor: pointer;
      width: 22px;
      height: 22px;
      .close_img {
        width: 100%;
        height: 100%;
      }
    }

    .Img {
      width: 752px;
      height: 483px;
      border-radius: 8px;
      margin: 0 auto;
      margin-top: 6%;
      overflow-y: auto;

    .Title {
        color: #00d5ff;
        text-align: center;
        font-size: 25px;
        font-weight: 700;
        margin-bottom: 30px;
      }

      .img_{
        width: 600px;
        margin: 0 9.5%;
      }
    }

  }
}
</style>