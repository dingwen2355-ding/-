<template>
  <div class="classic-case-query">
    <van-search
      v-model="searchKey"
      input-align="center"
      shape="round"
      show-action
      placeholder="请输入关键词搜索"
      @search="handleSearch"
      @clear="handleSearch"
    >
      <template #action>
        <van-button type="info" @click="openMap">空间查询</van-button>

<!--        <van-icon name="guide-o" />-->
      </template>
    </van-search>
    <van-tabs v-model="activeTab">
      <van-tab name="标准规范" key="标准规范">
        <template #title><i class="iconfont icon-biaoqianxuanxiang_zhishiku"></i> 标准规范</template>
        <van-pull-refresh
          v-model="specification_refreshing"
          @refresh="queryData('specification', true)"
          class="guide-list-container"
        >
          <van-list
            v-model="specification_loading"
            :finished="specification_finished"
            finished-text="没有更多了"
            :error.sync="specification_error"
            error-text="请求失败，点击重新加载"
            @load="queryData('specification')"
          >
            <div v-for="item in specification_list" :key="item.id" class="guide-item" @click="toDetail('specification', item)">
              <div class="guide-item-content">
                <div class="guide-item-title">{{ item.fileName }}</div>
                <div class="guide-item-info">
                  <span class="guide-item-type" v-if="item.managementType">{{ item.managementType }}</span>
                  <span class="right-info">{{ item.effectiveDate }}</span>
                </div>
              </div>
            </div>
          </van-list>
        </van-pull-refresh>
      </van-tab>
      <van-tab name="法律法规" key="法律法规">
        <template #title><i class="iconfont icon-bottom_zixun"></i> 法律法规</template>
        <van-pull-refresh v-model="law_refreshing" @refresh="queryData('law', true)" class="guide-list-container">
          <van-list
            v-model="law_loading"
            :finished="law_finished"
            finished-text="没有更多了"
            :error.sync="law_error"
            error-text="请求失败，点击重新加载"
            @load="queryData('law')"
          >
            <div v-for="item in law_list" :key="item.id" class="guide-item" @click="toDetail('law', item)">
              <div class="guide-item-content">
                <div class="guide-item-title">{{ item.lawName }}</div>
                <div class="guide-item-info">
                  <span class="guide-item-type" v-if="item.lawClass">{{ item.lawClass }}</span>
                  <span class="guide-item-type" v-if="item.accidentType">{{ item.accidentType }}</span>
                </div>
              </div>
            </div>
          </van-list>
        </van-pull-refresh>
      </van-tab>
      <van-tab name="类似事件" key="类似事件">
        <template #title><i class="iconfont icon-bottom_shijian"></i> 类似事件</template>
        <van-pull-refresh v-model="event_refreshing" @refresh="queryData('event', true)" class="guide-list-container">
          <van-list
            v-model="event_loading"
            :finished="event_finished"
            finished-text="没有更多了"
            :error.sync="event_error"
            error-text="请求失败，点击重新加载"
            @load="queryData('event')"
          >
            <div v-for="item in event_list" :key="item.id" class="guide-item" @click="toDetail('event', item)">
              <div class="guide-item-content">
                <div class="guide-item-title">{{ item.accidentTitle }}</div>
                <div class="guide-item-info">
                  <span class="guide-item-type" v-if="item.accidentCasesType">{{ item.accidentCasesType }}</span>
                  <span class="guide-item-type" v-if="item.accidentType">{{ item.accidentType }}</span>
                  <span class="right-info">{{ item.occurrenceDate }}</span>
                </div>
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
  name: 'classicCaseQuery',
  data() {
    return {
      searchKey: '',
      activeTab: '标准规范',
      pageSize: 10,
      // 标准规范的变量
      specification_loading: false,
      specification_error: false,
      specification_refreshing: false,
      specification_finished: false,
      specification_pageNum: 0,
      specification_list: [],
      // 法律法规的变量
      law_loading: false,
      law_error: false,
      law_refreshing: false,
      law_finished: false,
      law_pageNum: 0,
      law_list: [],
      // 类似事件的变量
      event_loading: false,
      event_error: false,
      event_refreshing: false,
      event_finished: false,
      event_pageNum: 0,
      event_list: []
    }
  },
  methods: {
    openMap() {
      this.$router.push('/mapView')
    },
    handleSearch() {
      this.queryData('specification', true)
      this.queryData('law', true)
      this.queryData('event', true)
    },
    queryData(tabName, isRefresh) {
      const attrPrefix = tabName + '_'
      if (isRefresh) {
        this[attrPrefix + 'pageNum'] = 1
        this[attrPrefix + 'list'] = []
      } else {
        this[attrPrefix + 'pageNum']++
      }
      let url
      if (tabName === 'law') {
        url = '/binhuapis/Law/queryLawList'
      } else if (tabName === 'event') {
        url = '/binhuapis/HistoricalEvent/queryHistoricalEventList'
      } else {
        url = '/binhuapis/Specification/querySpecificationList'
      }
      const params = {
        pageNum: this[attrPrefix + 'pageNum'],
        pageSize: this.pageSize
      }
      if (this.searchKey) {
        if (tabName === 'event') {
          params.accidentTitle = this.searchKey
        } else if (tabName === 'law') {
          params.lawName = this.searchKey
        } else {
          params.fileName = this.searchKey
        }
      }
      this[attrPrefix + 'loading'] = true
      axios({
        method: 'get',
        url,
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
                data.list.filter((item) => this[attrPrefix + 'list'].findIndex((guide) => guide.id === item.id) === -1)
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
    // 查询标准规范数据
    toDetail(tabName, guide) {
      let routeName
      if (tabName === 'event') {
        routeName = 'historyEventDetail'
      } else if (tabName === 'law') {
        routeName = 'lawDetail'
      } else {
        routeName = 'specificationDetail'
      }
      this.$router.push({
        name: routeName,
        params: {
          data: guide
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.guide-list-container {
  .guide-item {
    width: calc(100% - 56rem);
    min-height: 145rem;
    padding: 30rem 0 20rem 0;
    margin: 0 auto;
    border-bottom: 1rem solid #ececec;
    position: relative;
    cursor: pointer;
  }
  .guide-item-title {
    color: #010101;
    font-size: 28rem;
    line-height: 40rem;
  }
  .guide-item-info {
    margin-top: 28rem;
    width: 100%;
    height: 40rem;
    color: #919191;
    font-size: 25rem;
    line-height: 40rem;
    .guide-item-type {
      display: inline-block;
      vertical-align: top;
      height: 40rem;
      padding: 0 15rem;
      color: #427de5;
      font-size: 21rem;
      line-height: 38rem;
      background: rgba(66, 125, 229, 0.1);
      border: 1rem solid #427de5;
      border-radius: 7rem;
      + .guide-item-type {
        margin-left: 10rem;
      }
    }
    .right-info {
      float: right;
    }
  }
}
</style>
