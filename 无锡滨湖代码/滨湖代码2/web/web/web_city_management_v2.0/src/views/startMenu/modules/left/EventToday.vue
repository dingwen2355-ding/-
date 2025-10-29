<template>
  <div class="eventToday_title">
    <SmallTitle :titleList="['当日事件']" size="big" />
    <div class="content">
      <div class="top">
        <div class="list">
          <div class="item" style="margin-left: 80rem;width: 200rem !important;">全部</div>
          <div
            style="color: #00dbe6;margin-left: 40rem;font-size: 72rem !important;width: 200rem !important; text-align: center"
          >{{ allData }}</div>
        </div>
        <div class="list">
          <div class="item">
            <span class="iconfont">&#xeaa1;</span>
            <span class="name" style="width:300rem">12345</span>
            <span class="number" style="color: #00dbe6">{{ oneTwoEvent }}</span>
          </div>

          <div class="item">
            <span class="iconfont">&#xea8c;</span>
            <span class="name" style="width:300rem">主动发现</span>
            <span class="number" style="color: #00dbe6; ">{{ proactivelyDiscovering }}</span>
          </div>
        </div>
        <div class="list">
          <div class="item">
            <span class="iconfont">&#xea89;</span>
            <span class="name" style="width:300rem">城管</span>
            <span class="number" style="color: #00dbe6">{{ cgEvent }}</span>
          </div>
          <div class="item">
            <span class="iconfont">&#xea9e;</span>
            <span class="name" style="width:300rem">智能发现</span>
            <span class="number" style="color: #00dbe6">{{ intelligentDiscovery }}</span>
          </div>
        </div>
        <div class="list">
          <div class="item">
            <span class="iconfont">&#xea8a;</span>
            <span class="name" style="width:390rem">文明城市创建</span>
            <span class="number" style="color: #00dbe6;">{{ wmcscj }}</span>
          </div>
          <div class="item">
            <span class="iconfont">&#xeaa0;</span>
            <span class="name" style="width:390rem">被动发现</span>
            <span class="number" style="color: #00dbe6">{{ passiveDiscovery }}</span>
          </div>
        </div>
      </div>
      <div class="bottom">
        <!-- <el-table
          :data="tableData"
          class="large-screen1"
          height="465rem"
          @scroll.native="handleScroll"
        >
          <el-table-column label="序号" type="index" min-width="5%" align="center"></el-table-column>
          <el-table-column
            prop="flowTitle"
            label="事件标题"
            show-overflow-tooltip
            align="center"
            min-width="16%"
          ></el-table-column>
          <el-table-column prop="flowSource" show-overflow-tooltip label="事件来源" min-width="14%"></el-table-column>
          <el-table-column prop="class1" show-overflow-tooltip label="事件类别" min-width="14%"></el-table-column>
          <el-table-column label="处置状态" min-width="15%">
            <template slot-scope="scope">
              <span style="color: #FFA21F">{{ flowStatusChange(scope.row.flowStatus) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="address"
            label="事发地点"
            show-overflow-tooltip
            align="center"
            min-width="15%"
          ></el-table-column>
          <el-table-column prop="createDate" label="发生时间" align="center" min-width="28%"></el-table-column>
        </el-table>
        -->
        <!-- 表格 -->
        <div class="custom-table" ref="customTable" @scroll="handleScroll">
          <div class="table-header">
            <div class="table-row">
              <div class="table-cell num">序号</div>
              <div class="table-cell">事件标题</div>
              <div class="table-cell">事件来源</div>
              <div class="table-cell">事件类别</div>
              <div class="table-cell">处置状态</div>
              <div class="table-cell">事发地点</div>
              <div class="table-cell time">发生时间</div>
            </div>
          </div>
          <div class="table-body">
            <div v-for="(item, index) in tableData" :key="index" class="table-row">
              <div class="table-cell num">{{ index + 1 }}</div>
              <div class="table-cell title" :title="item.flowTitle">{{ item.flowTitle }}</div>
              <div class="table-cell">{{ item.flowSource }}</div>
              <div class="table-cell">{{ item.class1 }}</div>
              <div class="table-cell">
                <span style="color: #FFA21F">{{ flowStatusChange(item.flowStatus) }}</span>
              </div>
              <div class="table-cell address" :title="item.address">{{ item.address }}</div>
              <div class="table-cell time">{{ item.createDate }}</div>
            </div>
            <div v-if="loading" class="loading">加载中...</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SmallTitle from "@/components/SmallTitle.vue";
import axios from '@/utils/request';
import moment from "moment";
import qs from 'qs'

export default {
  name: "EventToday",
  components: { SmallTitle },
  data() {
    return {
      allData: '0/0',                // 全部
      wgEvent: '0/0',                // 网格事件
      cgEvent: '0/0',                // 城管事件
      lxshp: '0/0',                  // 灵锡随手拍
      leaderOrder: '0/0',            // 领导交办
      signWarning: '0/0',            // 体征预警
      civilizationCity: '0/0',       // 文明城市
      economicBrain: '0/0',          // 经济云大脑
      discoverActive: '0/0',         // 主动发现
      oneTwoEvent: '0/0',            // 12345
      wmcscj: '0/0',                 // 文明城市创建
      proactivelyDiscovering: '0/0', // 主动发现
      intelligentDiscovery: '0/0',   // 智能发现
      passiveDiscovery: '0/0',       // 被动发现
      tableData: [],
      dataList: {},
      eventList: [
        {
          name: '12345',
          flowSource: '12345平台',
        },
        {
          name: '主动发现',
          flowSource: '主动发现',
        },
        {
          name: '城管',
          flowSource: '城管事件',
        },
        {
          name: '智能发现',
          flowSource: '告警事件',
        },
        {
          name: '文明城市创建',
          flowSource: '文明城市',
        },
        {
          name: '被动发现',
          flowSource: '基层吹哨',
        }
      ],
      flowStatusList: [
        { "name": "延期申请", "code": 142 },
        { "name": "待签收", "code": 106 },
        { "name": "新发起", "code": 1189 },
        { "name": "待街道核查", "code": 132 },
        { "name": "延期核查", "code": 130 },
        { "name": "待评价", "code": 129 },
        { "name": "待城运核查", "code": 128 },
        { "name": "待街区核查", "code": 126 },
        { "name": "社区吹哨", "code": 125 },
        { "name": "街道吹哨", "code": 124 },
        { "name": "应哨处置中", "code": 123 },
        { "name": "区待应哨", "code": 121 },
        { "name": "重新处置", "code": 120 },
        { "name": "已应哨", "code": 118 },
        { "name": "等待应哨", "code": 117 },
        { "name": "已转派", "code": 116 },
        { "name": "退单", "code": 115 },
        { "name": "已分派", "code": 114 },
        { "name": "待接单", "code": 113 },
        { "name": "审批中", "code": 119 },
        { "name": "已结案", "code": 107 },
        { "name": "待核查", "code": 105 },
        { "name": "处置中", "code": 104 },
        { "name": "待开单", "code": 103 },
        { "name": "待审核", "code": 102 },
        { "name": "待分派", "code": 100 },
        { "name": "等待市级反馈", "code": 131 },
        { "name": "等待市级退单反馈", "code": 145 },
        { "name": "市级退单通过", "code": 156 },
        { "name": "退回分派", "code": 158 },
        { "name": "同意退单", "code": 159 },
        { "name": "申请未通过", "code": 141 },
        { "name": "拒绝退单", "code": 160 },
        { "name": "市级退单不通过", "code": 157 },
        { "name": "已签收", "code": 108 },
        { "name": "结案申请", "code": 143 }
      ],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      loading: false, // 是否正在加载数据
    }
  },
  mounted() {
    // let startTime = new Date().getFullYear() + '-' + `${new Date().getMonth() + 1}` + '-' + '01' + ' ' + '00:00:00'
    // let endTime = moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
    let startTime = moment().format('YYYY-MM-DD 00:00:00')
    let endTime = moment().format('YYYY-MM-DD HH:mm:ss')
    console.log(startTime, endTime);
    // this.eventList.forEach(item => {
    //   this.getTableData(startTime, endTime, item.flowSource)
    // })
    this.getTableData(startTime, endTime)
    this.getSourceDivision(startTime, endTime)
  },
  methods: {
    flowStatusChange(flowStatus) {
      let status = this.flowStatusList.find(item => item.code + '' === flowStatus)
      return status ? status.name : ''
    },
    // let url = `/community/dapeng/event/syncEventList?startTime=${startTime}&pageNum=1&pageSize=100&endTime=${endTime}`
    // axios.get(url).then(res => {
    //   if (res.data.code === 200) {
    //     this.tableData = res.data.data.list
    //   }
    // })
    getTableData(startTime, endTime) {
      // let url = '/apis/eventJoint/queryEventList??beginTime=' + startTime + '&endTIme=' + endTime
      let url = `/apis/eventJoint/queryEventList?beginTime=${startTime}&endTime=${endTime}&pageNum=${this.currentPage}&pageSize=${this.pageSize}`;
      this.loading = true;
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.tableData = [...this.tableData, ...res.data.data.list];
          this.currentPage++;
        }
        this.loading = false;
      }).catch(error => {
        console.error('Error fetching data:', error);
        this.loading = false; // 出错时也需要恢复 loading 状态
      })
    },
    handleScroll(event) {
      const target = event.target;
      if ((target.scrollHeight - target.scrollTop - 1) <= target.clientHeight) {
        // 判断滚动条是否滚动到底部
        if (!this.loading) {
          // 如果当前不是在加载数据，则加载更多数据
          this.getTableData(moment().format('YYYY-MM-DD 00:00:00'), moment().format('YYYY-MM-DD HH:mm:ss'));
        }
      }
    },
    getSourceDivision(startTime, endTime) {
      // let url = `/community/dapeng/event/getSyncEventCountBySource?startTime=${startTime}&endTime=${endTime}`
      // axios.get(url).then(res => {
      //   if (res.data.code === 200) {
      //     let data = res.data.data
      //     data.forEach(item => {
      //       switch (item.source) {
      //         case '全部':
      //           this.allData = `${item.isDelNum}/${item.total}`
      //           break
      //         case '网格事件':
      //           this.wgEvent = `${item.isDelNum}/${item.total}`
      //           break
      //         case '城管事件':
      //           this.cgEvent = `${item.isDelNum}/${item.total}`
      //           break
      //         case '灵锡随手拍':
      //           this.lxshp = `${item.isDelNum}/${item.total}`
      //           break
      //         case '领导交办':
      //           this.leaderOrder = `${item.isDelNum}/${item.total}`
      //           break
      //         case '体征预警':
      //           this.signWarning = `${item.isDelNum}/${item.total}`
      //           break
      //         case '文明城市':
      //           this.civilizationCity = `${item.isDelNum}/${item.total}`
      //           break
      //         case '经济云大脑':
      //           this.economicBrain = `${item.isDelNum}/${item.total}`
      //           break
      //         case '主动发现':
      //           this.discoverActive = `${item.isDelNum}/${item.total}`
      //           break
      //       }
      //     })
      //   }
      // })
      let url = '/apis/eventJoint/queryEventStatisticsMap?beginTime=' + startTime + '&endTIme=' + endTime
      // let params = {
      //   beginTime: startTime,
      //   endTIme: endTime
      // }
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          let data = res.data.data
          let endCountAll = 0
          let totalCountAll = 0
          data.forEach(item => {
            // 判断item.flowSource在eventList中是否存在
            if (this.eventList.find(e => e.flowSource === item.flowSource)) {
              console.log();
              endCountAll += item.endCount * 1
              totalCountAll += item.totalCount * 1
            }
            switch (item.flowSource) {
              case '12345平台':
                this.oneTwoEvent = `${item.endCount}/${item.totalCount}`
                break
              case '城管事件':
                this.cgEvent = `${item.endCount}/${item.totalCount}`
                break
              case '主动发现':
                this.proactivelyDiscovering = `${item.endCount}/${item.totalCount}`
                break
              case '告警事件':
                this.intelligentDiscovery = `${item.endCount}/${item.totalCount}`
                break
              case '文明城市':
                this.wmcscj = `${item.endCount}/${item.totalCount}`
                break
              case '基层吹哨':
                this.passiveDiscovery = `${item.endCount}/${item.totalCount}`
                break
            }
          })
          this.allData = `${endCountAll}/${totalCountAll}`
        }
      })
    }
  }
}
</script>

<style lang="scss">
.eventToday_title {
  width: 2840rem;
  position: absolute;
  top: 408rem;
  left: 128rem;

  .content {
    height: 820rem;
    .top {
      display: flex;
      flex-wrap: wrap;

      .list {
        margin: 20rem 25rem 20rem 25rem;
        font-size: 56rem;

        .iconfont {
          font-family: 'iconfont' !important;
          font-size: 71rem;
          font-style: normal;
          -webkit-font-smoothing: antialiased;
          -moz-osx-font-smoothing: grayscale;
          background-image: -webkit-linear-gradient(228deg, #9bc3e1 0%, #0f87d4 100%);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }

        .item {
          width: 770rem;
          margin: 30rem 0 30rem 0;
          font-size: 64rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #ffffff;

          .name {
            display: inline-block;
          }
          .number {
            margin-left: 50rem;
            // float: right;
          }
        }
      }
    }

    .bottom {
    }
  }
}

.el-table .cell {
  line-height: 80rem;
}

.el-table__body-wrapper {
  background-color: #001a5c;
}

.el-table__empty-text {
  color: #fff;
}

.el-table {
  color: #fff;
  font-size: 56rem;
}

table th.el-table__cell.is-leaf {
  border-bottom: 0;
}

.el-table .el-table__body-wrapper .el-table__body tr:nth-of-type(2n-1) td {
  background: #002d7a;
}

.el-table .el-table__body-wrapper .el-table__body tr:nth-of-type(2n) td {
  background: #032532;
}

.el-table td.el-table__cell,
.el-table th.el-table__cell.is-leaf {
  border-bottom: 0;
}

// .el-table .el-table__cell {
//   padding: 5rem 0 5rem 36rem;
// }
.el-table__fixed:before {
  height: 0 !important;
}
.el-table th.el-table__cell > .cell {
  padding: 30rem 0 !important;
}
.el-table .el-table__cell {
  padding: 30rem 0 !important;
}
.el-table .cell {
  padding: 0 !important;
}
.el-table .cell.el-tooltip {
  min-width: 0 !important;
}
.custom-table {
  width: 100%;
  max-height: 465rem; /* 设置表格容器的最大高度，超出部分会出现滚动条 */
  overflow-y: auto; /* 设置垂直方向上有滚动条 */
  // border: 1px solid #ccc;
}

.table-header {
  position: sticky;
  top: 0;
  z-index: 1; /* 让表头在滚动时始终显示在顶部 */
  background-color: #031033;
  font-weight: bold;
  .table-row {
    display: flex;
  }
  .num {
    width: 10%;
  }
  .time {
    width: 30%;
  }
  .title {
    width: 15%;
    // 超出显示省略号
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

.table-body {
  overflow-y: auto; /* 设置表体内容有滚动条 */
  .table-row {
    display: flex;
    background: #002d7a;
    // border-bottom: 1px solid #ddd;
  }
  .table-row:nth-child(even) {
    background-color: #031033;
  }
  .num {
    width: 10%;
  }
  .time {
    width: 30%;
  }
  .title {
    width: 15%;
    // 超出显示省略号
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .address {
    // 超出显示省略号
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

.table-cell {
  // flex: 1;
  width: 15%;
  padding: 30rem;
  text-align: center;
}

// .table-cell:nth-child(even) {
//   background-color: #f9f9f9; /* 斑马纹效果，偶数行背景色 */
// }

.loading {
  text-align: center;
  padding: 10px;
  color: #888;
}
</style>
