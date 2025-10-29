<template>
  <div class="Right2To2 module">
    <div class="content">
      <div class="leader">
        <img src="../../../../assets/global/title.png" alt />
        <ul>
          <li
            v-for="(item, index) in title"
            :key="index"
            :class="{ active: index === activeIndex }"
            @click="showVisible=true"
          >{{ item }}</li>
          <!--handleClick(index)-->
        </ul>
        <div style="margin-bottom: 65rem">
          <span class="more" @click="openA">更多>></span>
          <!--more-->
          <!-- <img class="add1" style="width: 98rem; height: 88rem" src="../../../../assets/global/新增.png" alt="" /> -->
        </div>
      </div>
      <div class="content_s">
        <ul>
          <li v-for="item in showTable" :key="item.title">
            <img :style="{ background: item.url }" src="../../../../assets/global/闪电.png" alt />
            <div class="weather_c">
              <p :style="{ color: item.url }">{{ item.num }}</p>
              <p>{{ item.title }}</p>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <el-dialog
      title="气象云图"
      :visible.sync="showVisible"
      :modal="false"
      class="videoDialogDIY default-dialog-class large-screen"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="dd-title-back">
        <div class="dd-title-back-s"></div>
      </div>
      <div class="dd-back-area top-right-area"></div>
      <div class="dd-back-angle top-left-angle"></div>
      <div class="dd-back-angle top-right-angle"></div>
      <div class="dd-back-angle bottom-left-angle"></div>
      <div class="dd-back-angle bottom-right-angle"></div>
      <div class="d-maintainer">
        <iframe class="d-iframe" src="http://www.weather.com.cn/radar/index.shtml"></iframe>
        <!-- <span class="close-tip" @click="handleClose"></span> -->
      </div>
    </el-dialog>

    <el-dialog
      :title="'重大事件气象预警'"
      :visible.sync="showVisible2"
      :modal="false"
      width="4267rem"
      height="3344rem"
      class="videoDialogRWXP default-dialog-class large-screen"
      :append-to-body="true"
      :before-close="handleClose2"
    >
      <div class="dd-title-back">
        <div class="dd-title-back-s"></div>
      </div>
      <div class="dd-back-area top-right-area"></div>
      <div class="dd-back-angle top-left-angle"></div>
      <div class="dd-back-angle top-right-angle"></div>
      <div class="dd-back-angle bottom-left-angle"></div>
      <div class="dd-back-angle bottom-right-angle"></div>
      <div>
        <div class="content_s">
          <ul>
            <li v-for="item in showTable" :key="item.title">
              <img :style="{ background: item.url }" src="../../../../assets/global/闪电.png" alt />
              <div class="weather_c">
                <p :style="{ color: item.url }">{{ item.num }}</p>
                <p>{{ item.title }}</p>
              </div>
            </li>
          </ul>
        </div>
        <el-table :data="tableData" class="large-screen" height="2500">
          <el-table-column label="序号" width="300" type="index"></el-table-column>
          <el-table-column prop="releaseTime" label="上报时间"></el-table-column>
          <el-table-column prop="warningTitle" label="预警标题" ></el-table-column>
          <el-table-column  label="预警等级" >
            <template slot-scope="scope">
              <span :style="cerrorColor(scope.row.warningLevel)">{{ scope.row.warningLevel }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="warningContent" label="预警内容"></el-table-column>
        </el-table>
        <div class="footer">
          <el-pagination
            class="large-screen"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="total"
            :pager-count="5"
          ></el-pagination>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/request'
import moment from 'moment'
export default {
  name: 'LeftDuty',
  data() {
    return {
      showVisible2: false,
      showVisible: false,
      searchInput: '',
      title: ['气象预警', '气象云图'],
      activeIndex: 0,
      showMore: false,
      showTable: [
        {
          url: '#EE2929',
          title: '红色预警',
          num: 0
        },
        {
          url: '#FF6600',
          title: '橙色预警',
          num: 0
        },
        {
          url: '#DFBD00',
          title: '黄色预警',
          num: 2
        },
        {
          url: '#00A8FF',
          title: '蓝色预警',
          num: 5
        }
      ],
      tableData: [
        /*{ time: moment().format('YYYY-MM-DD HH:mm:ss'), title: '大风蓝色', level: '蓝色预警', message: '受冷空气影响，目前我市已出现阵风7级的偏北风，预计今天下午到傍晚，我市 所有区县将出现平均风力4-5级阵风，7-8级偏北风大风' },
        { time: moment().format('YYYY-MM-DD HH:mm:ss'), title: '大风蓝色', level: '蓝色预警', message: '受冷空气影响，目前我市已出现阵风7级的偏北风，预计今天下午到傍晚，我市 所有区县将出现平均风力4-5级阵风，7-8级偏北风大风' },
        { time: moment().format('YYYY-MM-DD HH:mm:ss'), title: '道路结冰黄色', level: '黄色预警', message: '受冷空气影响，目前我市已出现阵风7级的偏北风，预计今天下午到傍晚，我市 所有区县将出现平均风力4-5级阵风，7-8级偏北风大风' },
        { time: moment().format('YYYY-MM-DD HH:mm:ss'), title: '大风蓝色', level: '蓝色预警', message: '受冷空气影响，目前我市已出现阵风7级的偏北风，预计今天下午到傍晚，我市 所有区县将出现平均风力4-5级阵风，7-8级偏北风大风' },
        { time: moment().format('YYYY-MM-DD HH:mm:ss'), title: '大风蓝色', level: '蓝色预警', message: '受冷空气影响，目前我市已出现阵风7级的偏北风，预计今天下午到傍晚，我市 所有区县将出现平均风力4-5级阵风，7-8级偏北风大风' },
        { time: moment().format('YYYY-MM-DD HH:mm:ss'), title: '道路结冰黄色', level: '黄色预警', message: '受冷空气影响，目前我市已出现阵风7级的偏北风，预计今天下午到傍晚，我市 所有区县将出现平均风力4-5级阵风，7-8级偏北风大风' },
        { time: moment().format('YYYY-MM-DD HH:mm:ss'), title: '大风蓝色', level: '蓝色预警', message: '受冷空气影响，目前我市已出现阵风7级的偏北风，预计今天下午到傍晚，我市 所有区县将出现平均风力4-5级阵风，7-8级偏北风大风' },*/
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
    }
  },
  computed: {
    cerrorColor() {
      return (v) => {
        let color = ''
        switch (v) {
          case '蓝色预警': // 报废
            color = { color: '#00A8FF' }
            break
          case '黄色预警': //  已反馈
            color = { color: '#DFBD00' }
            break
          case '橙色预警': // 待派遣
            color = { color: '#FF6600' }
            break
          case '红色预警': // 待接收
            color = { color: '#EE2929' }
            break
          default:
            color = { color: '#000000' }
            break
        }
        return color
      }
    }
  },
  mounted() {
    this.getTableData()
  },
  methods: {
    getTableData(){
      let startDate = moment().format('YYYY-MM-DD 00:00:00')
      let endDate = moment().format('YYYY-MM-DD HH:mm:ss')
      let url = `/community/commandWarning/getWarning?startDate=${startDate}&endDate=${endDate}`
      axios.get(url).then(res=>{
        if(res.data.code == 200){
            this.tableData = res.data.data
        }
      }).catch(err=>{

      })
    },
    openA() {
      this.showVisible2 = true
    },
    handleClose2() {
      this.showVisible2 = false
    },
    handleClose() {
      this.showVisible = false
    },
    more() {
      this.showMore = !this.showMore
      this.$root.Bus.$emit('showMore', this.showMore)
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      // this.getEventList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      // this.getEventList()
    },
  }
}
</script>
<style lang="scss" scoped>
.videoDialogRWXP {
  ::v-deep .el-dialog {
    width: 4267rem !important;
    height: 3344rem !important;
  }
  .content_s {
    margin-top: 85rem;
    margin-bottom: 74rem;
    ul {
      width: 98%;
      display: flex;
      justify-content: space-evenly;
      li {
        display: flex;
        margin-right: 206rem;
        img {
          width: 160rem;
          height: 160rem;
          border-radius: 8rem;
          margin: 0 35rem 0 0;
        }
      }
    }
    .weather_c {
      p:first-of-type {
        font-size: 72rem;
        font-family: DIN;
        font-weight: bold;
      }
      p {
        font-size: 48rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #ffffff;
      }
    }
  }
  .historyList {
    margin-bottom: 64rem;
  }
  .footer {
    height: 272rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
.videoDialogDIY {
  z-index: 10000 !important;
  ::v-deep .el-dialog {
    position: relative;
    // .el-dialog__header {
    //   // display: none;
    // }
    .el-dialog__body {
      display: flex;
      align-items: center;
      justify-content: center;
      .d-maintainer {
        width: 100%;
        height: 100%;
        // background-image: url('../../../../assets/global/弹框_bg.png');
        // background-size: 100% 100%;
        position: relative;
        display: flex;
        justify-content: space-around;
        .d-iframe {
          width: 100%;
          height: 100%;
          // margin-top: 100rem;
          // margin-left: 25rem;
        }
        .close-tip {
          display: inline-block;
          width: 80rem;
          height: 50rem;
          position: absolute;
          right: 15rem;
          top: 20rem;
        }
      }
    }
  }
}
</style>
<style lang="scss">
.Right2To2 {
  width: 100%;
  height: 34rem;
  position: absolute;
  top: 1250rem;
  z-index: 1999;
  .content {
    margin: 54rem 0 10rem;
    position: relative;
    .leader {
      width: 100%;
      height: 72rem;
      display: flex;
      img {
        width: 105rem;
      }
      ul {
        display: flex;
        width: 590rem;
        li {
          font-size: 60rem;
          font-family: Microsoft YaHei;
          font-weight: 600;
          color: #ffffff;
          line-height: 70rem;
          background: linear-gradient(0deg, #13a1f1 0%, #ffffff 100%);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          margin: 0 25rem;
          cursor: pointer;
        }
      }
      span {
        font-size: 16rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #ffd56f;
        margin-top: 6rem;
        cursor: pointer;
      }
      .more {
        font-size: 48rem;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #ffd56f;
        position: absolute;
        right: 8%;
        top: -6rem;
      }
    }
    .content_s {
      margin-top: 85rem;
      ul {
        width: 98%;
        display: flex;
        justify-content: space-between;
        li {
          display: flex;
          img {
            width: 160rem;
            height: 160rem;
            border-radius: 8rem;
            margin: 0 35rem 0 0;
          }
        }
      }
      .weather_c {
        p:first-of-type {
          font-size: 72rem;
          font-family: DIN;
          font-weight: bold;
        }
        p {
          font-size: 48rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #ffffff;
        }
      }
    }
    .monitor-search-input {
      height: 40rem;
      width: 100%;
      padding: 0 18rem 0 20rem;
      margin-top: 13rem;
      margin-bottom: 10rem;
      display: flex;
      .el-input {
        background: rgba(0, 148, 255, 0);
        border: 1rem solid rgba(0, 0, 0, 0.4);
      }
      .search_button {
        width: 80rem;
        height: 40rem;
        border: 1rem solid #0094ff;
        margin-left: 20rem;
        border-radius: 2rem;
        line-height: 40rem;
        text-align: center;
        font-size: 16rem;
        font-family: Microsoft YaHei;
        font-weight: 500;
        color: #93d0ff;
        cursor: pointer;
      }
    }
    .info_title {
      width: 100%;
      height: 16rem;
      line-height: 25rem;
      font-size: 16rem;
      font-family: Microsoft YaHei;
      font-weight: bold;
      color: #00ffff;
      padding-left: 18rem;
      span {
        color: #ffde00;
        font-size: 24rem;
        vertical-align: bottom;
      }
    }
    .lookmore {
      font-size: 16rem;
      font-family: SourceHanSansCN;
      font-weight: 400;
      color: #ffd56f;
      position: absolute;
      right: 8rem;
      bottom: 23rem;
    }
    .info_tab {
      width: 100%;
      padding: 0 1rem 0 3rem;
      height: 285rem;
      overflow: auto;
      position: relative;
      .listItem {
        display: flex;
        // height: 57rem;
        padding-left: 12rem;
        cursor: pointer;
        span {
          display: inline-block;
          text-align: left;
          font-size: 16rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #9ab1cb;
          // line-height: 48rem;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }
      .listItem:nth-of-type(3) .title {
        margin-left: 86rem !important;
      }
      .no_data {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }
}
</style>
