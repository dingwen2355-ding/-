<template>
  <div class="VideoPop" v-show="videoShow == true">
    <div class="VideoPop_img">
      <div class="flex">
        <div class="title">{{ name }}</div>

        <div class="close" @click="Close()">
          <img class="close_img" src="@/assets/icon_btn_close.png" alt="close" />
        </div>
      </div>

      <div class="Video">
        <div class="none" v-if="isVideoUrl == false">暂无视频推送</div>
        <div class="video_" v-else id="content"></div>
      </div>
    </div>
  </div>
</template>

<script>
import Videojs from "video.js";
import "video.js/dist/video-js.css";

import { GetVideoUrl } from "@/utils/api/SecurityoniMtoring";

export default {
  name: "VideoPop",

  components: {},

  data() {
    return {
      videoShow: false,
      name: "",
      isVideoUrl: true, //true有视频地址 false没有视频地址
      videoPlayer: null, //存储视频播放对象
    };
  },

  methods: {
    Close() {
      console.log("关闭视频弹窗1");
      this.videoShow = false;
      this.otherCloseVideo();
    },

    //获取视频播放地址
    getVideoUrl(cId) {
      var that = this;
      GetVideoUrl({
        channelId: cId,
      }).then((res) => {
        console.log("-安防监控-获取设备信息-视频流HLS-请求到接口了------");
        console.log(res);
        if (res.code == 200) {
          if (
            res.message == null ||
            res.message == undefined ||
            res.message == ""
          ) {
            that.isVideoUrl = false;
            that.otherCloseVideo();
          } else {
            that.VideoPay(res.message);
          }
        }
      });
    },

    VideoPay(videoUrl) {
      this.otherCloseVideo();

      const videoTag = document.createElement('video');
      videoTag.id = 'mmiid';
      videoTag.className = 'video-js vjs-big-play-centered vjs-fluid';
      videoTag.setAttribute('controls', true);
      videoTag.setAttribute('preload', 'auto');
      videoTag.setAttribute('width', '100%');
      videoTag.setAttribute('height', '100%');
      const container = document.getElementById('content');
      container.appendChild(videoTag);

      //显示视频
      var url = videoUrl;
      // hls(m3u8)格式
      var videoElement = document.querySelector("#mmiid");
      this.videoPlayer = Videojs(
        videoElement, // 挂载容器
        {
          autoplay: "muted", //自动播放
          controls: true, //用户可以与之交互的控件
          loop: true, //视频一结束就重新开始
          muted: false, //默认情况下将使所有音频静音
          aspectRatio: "16:9", //显示比率
          fullscreen: {
            options: { navigationUI: "hide" },
          },
          techOrder: ["html5"], // 兼容顺序
          html5: {
            hls: {
              withCredentials: true,
            },
          },
          sources: [{ src: url, type: "application/x-mpegURL" }], // 视频地址 类型
        }
      );

      videoElement.addEventListener('error', (event) => {
        console.error('---error--', event.message);
      });

      this.videoPlayer.play().catch((error) => {
        console.error('--play--', error);
      });
    },

    //销毁视频
    otherCloseVideo() {
      if (this.videoPlayer) {
        this.videoPlayer.pause();
        if (this.videoPlayer) {
          this.videoPlayer.dispose();
        }
        this.videoPlayer = null;
      }
    },
  },

  beforeMount() { },

  mounted() { },
};
</script>

<style lang="scss" scoped>
.VideoPop {
  position: fixed;
  top: 15vh;
  left: 50%;
  transform: translateX(-50%);

  .VideoPop_img {
    background-image: url("@/assets/SecurityoniMtoring/VideoPop.png");
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

    .Video {
      width: 752px;
      height: 483px;
      border-radius: 8px;
      margin: 0 auto;
      margin-top: 6%;

      .none {
        font-size: 20px;
        text-align: center;
        line-height: 483px;
      }

      .video_ {
        width: 100%;
        margin: 10% 0;
      }
    }
  }
}
</style>