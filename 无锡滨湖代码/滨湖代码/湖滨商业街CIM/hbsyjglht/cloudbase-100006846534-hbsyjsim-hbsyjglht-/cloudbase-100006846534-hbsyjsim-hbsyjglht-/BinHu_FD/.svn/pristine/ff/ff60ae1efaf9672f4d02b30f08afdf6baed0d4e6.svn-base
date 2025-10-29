<template>
  <div class="rank">
    <div class="title-container">
      <div class="title">人脸识别</div>
      <a-button icon="right" type="link" @click="onJumpToAccessList">查看进出记录</a-button>
    </div>
    <div class="statistic">
      <span>今日进出人次：<span class="number">{{totalToday}}</span></span>
      <span>昨日进出人次：<span class="number">{{totalYesterday}}</span></span>
    </div>
    <div v-if="list.length === 0" style="margin-top: 60px;">
      <a-empty />
    </div>
    <ul v-else class="list" :style="{ height: height ? `${height}px` : 'auto', overflow: 'auto' }">
      <li :key="index" v-for="(item, index) in list">
        <div class="record">
          <a-avatar
            :src="item.photo"
            shape="square"
            :size="64"
          />
          <div class="info">
            <!-- <a-space size="middle">
              <span>27层办公人员</span>
              <span>99%</span>
            </a-space> -->
            <a-space size="middle" style="margin: 4px 0">
              <span>{{item.indexName}}</span>
              <span v-if="item.temp">{{item.temp}}°C</span>
            </a-space>
            <span>{{item.happenTime}}</span>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'AccessList',
  props: {
    title: {
      type: String,
      default: '',
    },
    list: {
      type: Array,
      default: null,
    },
    height: {
      type: Number,
      default: null,
    },
    totalToday: {
      type: Number,
      default: null,
    },
    totalYesterday: {
      type: Number,
      default: null,
    },
  },
  methods: {
    onJumpToAccessList() {
      this.$router.push({
        path: '/security/alarm/access/list',
      })
    },
  },
}
</script>

<style lang="less" scoped>
.rank {
  // padding: 0 32px 32px 72px;

  .title-container {
    display: flex;
    align-items: center;
    justify-content: space-between;

    /deep/ .ant-btn {
      padding: 0 !important;
    }
  }

  .title {
    font-weight: bold;
  }

  .statistic {
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: rgba(0, 0, 0, 0.45);
    margin-top: 10px;

    .number {
      color: #333;
      font-weight: bold;
    }
  }

  .list {
    // margin: 10px 0 0;
    padding: 0;
    list-style: none;

    li {
      margin-top: 16px;

      .record {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;

        .info {
          display: flex;
          flex-direction: column;
        }
      }
    }
  }
}
</style>

