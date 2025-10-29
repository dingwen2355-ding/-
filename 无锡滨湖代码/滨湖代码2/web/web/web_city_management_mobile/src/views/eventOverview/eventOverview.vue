<template>
  <div class="event-overview-page">
    <van-search
      v-model="searchKey"
      input-align="center"
      shape="round"
      placeholder="请输入关键词搜索"
      @search="handleSearch"
      @clear="handleSearch"
    />
    <van-tabs v-model="activeTab">
      <van-tab name="当前事件" key="当前事件">
        <template #title><i class="iconfont icon-biaoqianxuanxiang_shijian"></i> 当前事件</template>
        <van-pull-refresh
          v-model="current_refreshing"
          @refresh="queryData('current', true)"
          class="notice-list-container"
        >
          <van-list
            v-model="current_loading"
            :finished="current_finished"
            finished-text="没有更多了"
            :error.sync="current_error"
            error-text="请求失败，点击重新加载"
            @load="queryData('current')"
          >
            <van-swipe-cell  v-for="item in current_list" :key="item.id">
              <div
                :class="{
                'notice-item': true,
                'no-img': !item.filePicture,
                'single-img': !!item.filePicture
              }"
                @click="toDetail(item)"
              >
                <div class="notice-item-content">
                  <div class="notice-item-title">{{ item.eventTitle }}</div>
                  <div class="notice-item-info">
                    <div>{{ item.typeName }}</div>
                    <div>
                    <span class="info-item">
                      <i :class="'notice-level-flag flag-' + item.status"></i>
                      {{ item.statusName }}
                    </span>
                      <span class="info-item">{{ item.eventTime }}</span>
                    </div>
                  </div>
                </div>
                <div class="notice-item-img" v-if="item.filePicture">
                  <van-image :src="item.filePicture" fit="cover" alt="资讯图片" class="img" />
                </div>
              </div>
              <template #right>
                <van-button square type="danger" text="删除" style="height:100%"/>
                <van-button square type="primary" text="修改" style="height:100%"/>
              </template>
            </van-swipe-cell>
          </van-list>
        </van-pull-refresh>
      </van-tab>
      <van-tab name="历史事件" key="历史事件">
        <template #title><i class="iconfont icon-biaoqianxuanxiang_lishi"></i> 历史事件</template>
        <van-pull-refresh
          v-model="history_refreshing"
          @refresh="queryData('history', true)"
          class="notice-list-container"
        >
          <van-list
            v-model="history_loading"
            :finished="history_finished"
            finished-text="没有更多了"
            :error.sync="history_error"
            error-text="请求失败，点击重新加载"
            @load="queryData('history')"
          >
            <div
              v-for="item in history_list"
              :key="item.id"
              :class="{
                'notice-item': true,
                'no-img': !item.filePicture,
                'single-img': !!item.filePicture
              }"
              @click="toDetail(item)"
            >
              <div class="notice-item-content">
                <div class="notice-item-title">{{ item.eventTitle }}</div>
                <div class="notice-item-info">
                  <div>{{ item.typeName }}</div>
                  <div>
                    <span class="info-item">
                      <i :class="'notice-level-flag flag-' + item.status"></i>
                      {{ item.statusName }}
                    </span>
                    <span class="info-item">{{ item.eventTime }}</span>
                  </div>
                </div>
              </div>
              <div class="notice-item-img" v-if="item.filePicture">
                <van-image :src="item.filePicture" fit="cover" alt="资讯图片" class="img" />
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
  name: 'eventOverview',
  data() {
    return {
      searchKey: '',
      activeTab: '当前事件',
      sourceList: [],
      statusMap: {
        current: [],
        history: []
      },
      pageSize: 10,
      // 当前事件的变量
      current_loading: false,
      current_error: false,
      current_refreshing: false,
      current_finished: false,
      current_noticeList: [],
      current_pageNum: 0,
      current_list: [],
      // 历史事件的变量
      history_loading: false,
      history_error: false,
      history_refreshing: false,
      history_finished: false,
      history_noticeList: [],
      history_pageNum: 0,
      history_list: []
    }
  },
  mounted() {
    this.getDict()
  },
  methods: {
    getDict() {
      // 查询事件状态过滤条件
      axios.get('/binhuapis/dict/dictTree?parentId=39').then((res) => {
        res.data.data.forEach((item) => {
          if (item.dictName === '已完结') {
            this.statusMap.history.push(item.id)
          } else if (item.dictName !== '待核查') {
            this.statusMap.current.push(item.id)
          }
        })
        this.handleSearch()
      })
    },
    handleSearch() {
      this.queryData('current', true)
      this.queryData('history', true)
    },
    queryData(queryType, isRefresh) {
      if (Object.keys(this.statusMap[queryType]).length === 0) {
        return
      }
      const attrPrefix = queryType + '_'
      if (isRefresh) {
        this[attrPrefix + 'pageNum'] = 1
        this[attrPrefix + 'list'] = []
      } else {
        this[attrPrefix + 'pageNum']++
      }
      const params = {
        pageNum: this[attrPrefix + 'pageNum'],
        pageSize: this.pageSize,
        userId: sessionStorage.getItem('userId'),
        statusIds: this.statusMap[queryType].join(',')
      }
      if (this.searchKey) {
        params.eventTitle = this.searchKey
      }
      this[attrPrefix + 'loading'] = true
      axios({
        method: 'get',
        url: '/binhuapis/dapeng/event/query-condition',
        params
      })
        .then((res) => {
          this[attrPrefix + 'loading'] = false
          this[attrPrefix + 'refreshing'] = false
          if (res.data.code === 200) {
            const data = res.data.data
            if (data && data.list.length > 0) {
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
    toDetail(eventData) {
      this.$router.push({
        name: 'eventDetail',
        params: {
          eventData: eventData
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
