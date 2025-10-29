<template>
  <div class="consultation-notice-page">
    <van-swipe class="notice-swipe" :autoplay="1000" indicator-color="white">
      <van-swipe-item
        v-for="(item,key) in topMenuNotices"
        :key="'sw' + key"
        class="notice-swipe-item"
        @click.native="toDetail(item)"
      >
        <van-image :src="item.noticeImg" fit="fill" alt="资讯图片" class="notice-img" />
        <div class="notice-info">
          <div class="notice-title">{{ item.title }}</div>
          <div class="notice-time">{{ item.releaseTime }}</div>
        </div>
      </van-swipe-item>
    </van-swipe>
    <van-pull-refresh v-model="refreshing" @refresh="queryData(true)" class="notice-list-container">
      <van-list
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        :error.sync="error"
        error-text="请求失败，点击重新加载"
        @load="queryData(false)"
      >
        <div
          v-for="(item,key) in noticeList"
          :key="'not'+ key"
          :class="{ 'notice-item': true, 'has-img': !!item.noticeImg }"
          @click="toDetail(item)"
        >
          <div class="notice-item-content">
            <div class="notice-item-title">{{ item.title }}</div>
            <div class="notice-item-info">{{ item.releaseTime }}</div>
          </div>
          <div class="notice-item-img">
            <van-image :src="item.noticeImg" fit="cover" alt="资讯图片" class="img" />
          </div>
        </div>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'consultationNotice',
  data() {
    return {
      topMenuNotices: [],
      loading: false,
      error: false,
      refreshing: false,
      finished: false,
      noticeList: [],
      pageNum: 0,
      pageSize: 10
    }
  },
  methods: {
    queryData(isRefresh) {
      if (isRefresh) {
        this.pageNum = 1
        this.noticeList = []
      } else {
        this.pageNum++
      }
      this.loading = true
      axios
        .post('/binhuapis/informationManage/query', {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        })
        .then((res) => {
          this.loading = false
          this.refreshing = false
          if (res.data.code === 200) {
            const data = res.data.data
            if (data && data.list.length > 0) {
              data.list.forEach((item) => {
                item.noticeImg = ''
                const fileFormat = item.annexUrl ? item.annexUrl.slice(item.annexUrl.lastIndexOf('.') + 1) : ''
                if (
                  fileFormat &&
                  ['jpg', 'png', 'jpeg', 'gif', 'bmp', 'JPG', 'PNG', 'JPEG', 'GIF', 'BMP'].includes(fileFormat)
                ) {
                  item.noticeImg = item.annexUrl
                }
              })
              let tempNoticesTop = data.list.filter((item) => item.isTop === '1')
              let ors = tempNoticesTop[0].annexUrl.split(',')
              if (ors.length == 0) {
                this.topMenuNotices = tempNoticesTop
              } else {
                this.topMenuNotices = ors.map((t) => {
                  let o = Object.assign({}, tempNoticesTop[0])
                  o.noticeImg = t
                  return o
                })
              }
              // 防止有新增的消息让下一页中部分数据被上一页的数据挤到这一页
              this.noticeList = this.noticeList.concat(
                data.list.filter((item) => this.noticeList.findIndex((notice) => notice.id === item.id) === -1)
              )
              this.finished = !data.hasNextPage
            } else {
              this.finished = true
            }
          } else {
            this.error = true
            this.pageNum--
          }
        })
        .catch((err) => {
          console.error(err)
          this.error = true
          this.pageNum--
          this.loading = false
          this.refreshing = false
        })
    },
    toDetail(notice) {
      this.$router.push({
        name: 'consultationNoticeDetail',
        params: {
          noticeData: notice
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.notice-swipe {
  width: 100%;
  .notice-swipe-item {
    width: 100%;
    height: 563rem;
    overflow: hidden;
    /deep/ .van-image__error {
      background: #333;
    }
  }
  .notice-img {
    width: 100%;
    height: 100%;
  }
  .notice-info {
    position: absolute;
    left: 30rem;
    right: 30rem;
    bottom: 20rem;
  }
  .notice-title {
    color: #fefefe;
    font-size: 32rem;
    font-weight: 600;
    line-height: 45rem;
  }
  .notice-time {
    margin-top: 20rem;
    color: #ffffff;
    font-size: 21rem;
    line-height: 35rem;
    padding-left: 14rem;
    border-left: 6px solid #ff9739;
  }
}
.notice-list-container {
  margin-top: 20rem;
  .notice-item {
    width: calc(100% - 56rem);
    min-height: 210rem;
    padding: 30rem 0;
    margin: 0 auto;
    border-bottom: 1rem solid #ececec;
    position: relative;
    cursor: pointer;
    &.has-img {
      font-size: 0;
      .notice-item-content,
      .notice-item-img {
        display: inline-block;
        vertical-align: top;
      }
      .notice-item-content {
        width: calc(100% - 215rem);
      }
    }
  }
  .notice-item-title {
    color: #010101;
    font-size: 28rem;
    line-height: 40rem;
    margin-bottom: 30rem;
  }
  .notice-item-info {
    position: absolute;
    bottom: 35rem;
    color: #919191;
    font-size: 25rem;
    line-height: 30rem;
  }
  .notice-item-img {
    display: none;
    width: 185rem;
    height: 146rem;
    margin-left: 30rem;
    .img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
