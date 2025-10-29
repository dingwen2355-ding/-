<template>
  <div class="notification-manage">
    <van-search
      v-model="searchKey"
      input-align="center"
      shape="round"
      placeholder="请输入关键词搜索"
      @search="handleSearch"
      @clear="handleSearch"
    />
    <van-tabs v-model="activeTab">
      <van-tab name="未读消息" key="未读消息">
        <template #title><i class="iconfont icon-biaoqianxuanxiang_weidu"></i> 未读消息</template>
        <van-pull-refresh v-model="unread_refreshing" @refresh="queryData('1', true)" class="notice-list-container">
          <van-list
            v-model="unread_loading"
            :finished="unread_finished"
            finished-text="没有更多了"
            :error.sync="unread_error"
            error-text="请求失败，点击重新加载"
            @load="queryData('1')"
          >
            <div
              v-for="item in unread_list"
              :key="item.id"
              :class="{
                'notice-item': true,
                'no-img': item.imgList.length === 0,
                'single-img': item.imgList.length === 1,
                'multi-img': item.imgList.length > 1
              }"
              @click="toDetail(item)"
            >
              <div class="notice-item-content">
                <div class="notice-item-title">{{ item.messageTitle }}</div>
                <div class="notice-item-info">
                  <template v-if="item.imgList.length > 1">
                    <span class="info-item">
                      <i
                        :class="{
                          'notice-level-flag': true,
                          medium: item.messageLevel === '中等',
                          emergent: item.messageLevel === '紧急'
                        }"
                      ></i>
                      {{ item.messageLevel }}
                    </span>
                    <span class="info-item">{{ item.sendTime }}</span>
                    <span class="right-info">{{ item.messageCategory }}</span>
                  </template>
                  <template v-else>
                    <div>{{ item.messageCategory }}</div>
                    <div>
                      <span class="info-item">
                        <i
                          :class="{
                            'notice-level-flag': true,
                            medium: item.messageLevel === '中等',
                            emergent: item.messageLevel === '紧急'
                          }"
                        ></i>
                        {{ item.messageLevel }}
                      </span>
                      <span class="info-item">{{ item.sendTime }}</span>
                    </div>
                  </template>
                </div>
              </div>
              <div class="notice-item-img">
                <van-image
                  v-for="(img, index) in item.imgList.slice(0, 3)"
                  :key="index"
                  :src="img"
                  fit="cover"
                  alt="资讯图片"
                  class="img"
                />
              </div>
            </div>
          </van-list>
        </van-pull-refresh>
      </van-tab>
      <van-tab name="已读消息" key="已读消息">
        <template #title><i class="iconfont icon-biaoqianxuanxiang_yidu"></i> 已读消息</template>
        <van-pull-refresh v-model="read_refreshing" @refresh="queryData('2', true)" class="notice-list-container">
          <van-list
            v-model="read_loading"
            :finished="read_finished"
            finished-text="没有更多了"
            :error.sync="read_error"
            error-text="请求失败，点击重新加载"
            @load="queryData('2')"
          >
            <div
              v-for="item in read_list"
              :key="item.id"
              :class="{
                'notice-item': true,
                'no-img': item.imgList.length === 0,
                'single-img': item.imgList.length === 1,
                'multi-img': item.imgList.length > 1
              }"
              @click="toDetail(item)"
            >
              <div class="notice-item-content">
                <div class="notice-item-title">{{ item.messageTitle }}</div>
                <div class="notice-item-info">
                  <template v-if="item.imgList.length > 1">
                    <span class="info-item">
                      <i
                        :class="{
                          'notice-level-flag': true,
                          medium: item.messageLevel === '中等',
                          emergent: item.messageLevel === '紧急'
                        }"
                      ></i>
                      {{ item.messageLevel }}
                    </span>
                    <span class="info-item">{{ item.sendTime }}</span>
                    <span class="right-info">{{ item.messageCategory }}</span>
                  </template>
                  <template v-else>
                    <div>{{ item.messageCategory }}</div>
                    <div>
                      <span class="info-item">
                        <i
                          :class="{
                            'notice-level-flag': true,
                            medium: item.messageLevel === '中等',
                            emergent: item.messageLevel === '紧急'
                          }"
                        ></i>
                        {{ item.messageLevel }}
                      </span>
                      <span class="info-item">{{ item.sendTime }}</span>
                    </div>
                  </template>
                </div>
              </div>
              <div class="notice-item-img">
                <van-image
                  v-for="(img, index) in item.imgList.slice(0, 3)"
                  :key="index"
                  :src="img"
                  fit="cover"
                  alt="资讯图片"
                  class="img"
                />
              </div>
            </div>
          </van-list>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'notificationOverview',
  data() {
    return {
      searchKey: '',
      activeTab: '未读消息',
      pageSize: 10,
      // 未读消息的变量
      unread_loading: false,
      unread_error: false,
      unread_refreshing: false,
      unread_finished: false,
      unread_noticeList: [],
      unread_pageNum: 0,
      unread_list: [],
      // 已读消息的变量
      read_loading: false,
      read_error: false,
      read_refreshing: false,
      read_finished: false,
      read_noticeList: [],
      read_pageNum: 0,
      read_list: []
    }
  },
  methods: {
    handleSearch() {
      this.queryData('1', true)
      this.queryData('2', true)
    },
    queryData(messageType, isRefresh) {
      // messageType为1是未读，为2是已读
      const attrPrefix = messageType === '1' ? 'unread_' : 'read_'
      if (isRefresh) {
        this[attrPrefix + 'pageNum'] = 1
        this[attrPrefix + 'list'] = []
      } else {
        this[attrPrefix + 'pageNum']++
      }
      const params = {
        pageNum: this[attrPrefix + 'pageNum'],
        pageSize: this.pageSize,
        receivePerson: sessionStorage.getItem('userName'),
        messageType
      }
      if (this.searchKey) {
        params.messageTitle = this.searchKey
      }
      this[attrPrefix + 'loading'] = true
      axios({
        method: 'get',
        url: '/binhuapis/queryMessage',
        params
      })
        .then((res) => {
          this[attrPrefix + 'loading'] = false
          this[attrPrefix + 'refreshing'] = false
          if (res.data.code === 200) {
            const data = res.data.data
            if (data && data.list.length > 0) {
              data.list.forEach((item) => {
                if (item.imgUrl) {
                  const imgFormat = ['jpg', 'png', 'jpeg', 'gif', 'bmp', 'JPG', 'PNG', 'JPEG', 'GIF', 'BMP']
                  item.imgList = item.imgUrl.split(',').filter((item) => imgFormat.includes(item.split('.').pop()))
                } else {
                  item.imgList = []
                }
              })
              // 防止有新增的消息让下一页中部分数据被上一页的数据挤到这一页
              this[attrPrefix + 'list'] = this[attrPrefix + 'list'].concat(
                data.list.filter(
                  (item) => this[attrPrefix + 'list'].findIndex((notice) => notice.id === item.id) === -1
                )
              )
              this[attrPrefix + 'finished'] = !data.hasNextPage
            } else {
              this[attrPrefix + 'finished'] = true
            }
          } else {
            this[attrPrefix + 'error'] = true
            this[attrPrefix + 'pageNum--']--
          }
        })
        .catch((err) => {
          console.error(err)
          this[attrPrefix + 'error'] = true
          this[attrPrefix + 'pageNum--']--
          this[attrPrefix + 'loading'] = false
          this[attrPrefix + 'refreshing'] = false
        })
    },
    toDetail(notice) {
      this.$router.push({
        name: 'notificationDetail',
        params: {
          data: notice
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.notice-list-container {
  .notice-item {
    width: calc(100% - 56rem);
    min-height: 210rem;
    padding: 30rem 0;
    margin: 0 auto;
    border-bottom: 1rem solid #ececec;
    position: relative;
    cursor: pointer;
  }
  .notice-item-title {
    color: #010101;
    font-size: 28rem;
    line-height: 40rem;
    margin-bottom: 100rem;
  }
  .notice-item-info {
    position: absolute;
    bottom: 35rem;
    width: 100%;
    color: #919191;
    font-size: 25rem;
    line-height: 40rem;
    .notice-level-flag {
      display: inline-block;
      vertical-align: top;
      width: 17rem;
      height: 17rem;
      margin-top: 12rem;
      margin-right: 15rem;
      border-radius: 50%;
      background: #abba00;
      &.medium {
        background: #ff9710;
      }
      &.emergent {
        background: #ff1010;
      }
    }
    .info-item {
      margin-right: 36rem;
    }
    .right-info {
      float: right;
    }
  }
  .notice-item-img {
    display: none;
  }
  .notice-item {
    &.single-img {
      font-size: 0;
      .notice-item-content {
        display: inline-block;
        vertical-align: top;
        width: calc(68% - 57rem);
      }
      .notice-item-img {
        display: inline-block;
        vertical-align: top;
        width: 30%;
        height: 146rem;
        margin-left: 57rem;
        .img {
          width: 100%;
          height: 100%;
        }
      }
    }
    &.multi-img {
      .notice-item-title {
        margin-bottom: 0;
      }
      .notice-item-img {
        display: flex;
        justify-content: space-between;
        margin-top: 24rem;
        margin-bottom: 60rem;
        .img {
          width: 32%;
          height: 146rem;
        }
      }
    }
  }
}
</style>
