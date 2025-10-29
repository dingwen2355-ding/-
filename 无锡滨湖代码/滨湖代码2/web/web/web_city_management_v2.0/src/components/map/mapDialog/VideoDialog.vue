<template>
  <div class="video-box ">
    <div v-for="(item, key) in videoList" :key="key" class="box-item" :style="{top: key * 700 + 'rem'}" v-drag>
      <div v-if="JSON.stringify(item)!=='{}'">
        <div class="video-title" :title="item.name">{{ item.name }} <i class="el-icon-close" @click="removeVideo(key)"></i></div>
        <div class="video-item">
          <ZnvVideo :src="item.src" :type="item.type" :fontSize="16" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ZnvVideo from '@/components/znvVideo/Video.vue'
import { getHlsUrl, getFlvUrl } from '@/api/info.js'

export default {
  data() {
    return {
      videoList: [{}, {}, {}, {}],
      deviceModel: {
        2: '河口派出所_青祁路滨湖实幼南侧朝北人脸',
        3: '鑫园开发区派出所_环太湖公路_渔港立交东向西球',
        4: '溪南派出所_青祁桥南境西侧朝北人脸全',
        5: '雪浪派出所_缘溪道-高浪西路-南向北机动车道',
        6: '雪浪派出所_缘溪道葛球北横山小区路口',
        7: '雪浪派出所_震泽路恒华科技园路口'
      },
      videoModel: {
        2: '/files/media/2.mp4',
        3: '/files/media/3.mp4',
        4: '/files/media/4.mp4',
        5: '/files/media/5.mp4',
        6: '/files/media/6.mp4',
        7: '/files/media/7.mp4'
      }
    }
  },
  components: {
    ZnvVideo
  },
  mounted() {
    this.$root.Bus.$on('openVideoWindow', (v) => {
      if (v.deviceId) {
        this.openVideoWindow(v)
      } else {
        this.$message({
          message: '无对应视频，请确认！',
          type: 'warning'
        })
      }
    })
  },
  methods: {
    addVideo(v) {
      let flag = true
      this.videoList.forEach((t, i) => {
        if (flag && JSON.stringify(t) == '{}') {
          this.videoList[i] = v
          flag = false
        }
        return t
      })
      this.$forceUpdate()
    },
    removeVideo(index) {
      this.videoList[index] = {}
      this.$forceUpdate()
    },
    openVideoWindow(v) {
      if (v.deviceTYpeName && v.deviceTYpeName.indexOf('无人机') > -1) {
        /* this.videoList.push({
            name: v.deviceTYpeName,
            src: v.url,
            type: v.type
          }) */
        this.addVideo({
          name: v.deviceTYpeName,
          src: v.url,
          type: v.type
        })
      } else if (['2', '3', '4', '5', '6'].includes(v.deviceId)) {
        /* this.videoList.push({
            name: this.deviceModel[v.deviceId],
            src: this.videoModel[v.deviceId],
            type: 'mp4'
          }) */
        this.addVideo({
          name: this.deviceModel[v.deviceId],
          src: this.videoModel[v.deviceId],
          type: 'mp4'
        })
      } else {
        getFlvUrl({
          deviceId: v.deviceId
        }).then(res => {
          if (res.src) {
            /* this.videoList.push({
                name: v.deviceName,
                src: res.src,
                type: res.type
              }) */
            this.addVideo({
              name: v.deviceName,
              src: res.src,
              type: res.type
            })
          } else {
            this.$message({
              message: '无对应视频，请确认！',
              type: 'warning'
            })
          }
        })
      }
    },
    openVideoWindowFromUrl(v) {
      this.videoList = []
      // this.videoList.push({
      //   name: v.name,
      //   src: v.src,
      //   type: v.type
      // })
      this.addVideo({
        name: v.name,
        src: v.src,
        type: v.type
      })
    },
    closeVideo(index) {
      this.videoList.splice(index, 1)
    }
  },
  beforeDestroy() {
    this.$root.Bus.$off('openVideoWindow')
  }
}
</script>

<style lang="scss">
.video-box {
  position: absolute;
  left: 25%;
  top: 400rem;
  z-index: 4;
  .box-item {
    position: absolute;
    left: 0px;
    top: 0px;
    margin-bottom: 20rem;
    background-color: #000;
    .video-title {
      padding-left: 30rem;
      width: 1024rem;
      //width: 2712rem;
      padding-right: 50rem;
      overflow:hidden; //超出的文本隐藏
      text-overflow:ellipsis; //用省略号显示
      white-space:nowrap; //不换行
      color: #fff;
      line-height: 140rem;
      height: 140rem;
      font-size: 50rem;
      position: relative;
      .el-icon-close {
        position: absolute;
        cursor: pointer;
        top: 40rem;
        right: 10rem;
        font-size: 50rem;
      }
    }
    .video-item {
      width: 1024rem;
      height: 500rem;
      //width: 2712rem;
      //height: 1400rem;
    }
  }
}
</style>
