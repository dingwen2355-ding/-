<template>
  <!-- <video
    class="wrapper-video"
    controls
    autoplay
    loop
    muted
    :src="src"
  ></video> -->
  <video
    id="mmiid"
    class="video-js vjs-big-play-centered vjs-fluid"
    controls
    preload="auto"
    width="100%"
    height="100%"
  ></video>
</template>
<script>
import Videojs from 'video.js'
export default {
  props:{
    src:{
      type:String,
      default:null,
    }
  },
  data() {
    return {
    };
  },
  mounted() {
    debugger
    //this.playVideo()
    this.videoPlayer = Videojs(
      document.querySelector('#mmiid'), // 挂载容器
      // {
      //   autoplay: 'muted',//自动播放
      //   controls: true,//用户可以与之交互的控件
      //   loop:true,//视频一结束就重新开始
      //   muted:false,//默认情况下将使所有音频静音
      //   aspectRatio:"16:9",//显示比率
      //   fullscreen:{options: {navigationUI: 'hide'}},
      //   techOrder: ["html5", "flvjs"],// 兼容顺序
      //   html5:{hls: {withCredentials: true}},
      //   sources: [{ src: this.src, type: "application/x-mpegURL" }], // 视频地址 类型
      // }
      {
        autoplay: true, // 设置自动播放
        controls: true, // 显示播放的控件
        // sources: [
        //   // 注意，如果是以option方式设置的src,是不能实现 换台的 (即使监听了nowPlayVideoUrl也没实现)
        //   {
        //     src: nowPlayVideoUrl,
        //     type: "application/x-mpegURL" // 告诉videojs,这是一个hls流
        //   }
        // ]
        sources: [{ src: this.src, type: "application/x-mpegURL" }], // 视频地址 类型
      }
    )
  },
  methods: {
    // playVideo(){
    //   let _this = this
    //   axios.get(monitor.keyAreas,{
    //     params:{
    //       indexCode:this.code,
    //       streamType:1,
    //     }
    //   }).then(res=>{
    //     if(res.data&&res.data.data&&res.data.data.videoUrl&&res.data.data.videoUrl.fmp4){
    //       _this.videoSrc = res.data.data.videoUrl.fmp4
    //     }
    //   }).catch(err=>{
    //     this.$message({message: "无法获取直播流!视频播放失败",type: "warning"});
    //     console.log("重点区域监控请求错误",err)
    //   })
    // },
  }
};
</script>
<style scoped>
</style>
