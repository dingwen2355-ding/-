<template>
  <div class="EventProfile module">
    <SmallTitle :titleList="['值班信息']" />
    <div class="content">
      <div class="information_1">
        <div class="title">
          <div class="image" style="width:108rem;height:76rem">
            <img style="width: 100%; height: 100%" src="../../../../assets/SmallTitle/二级标题@2x.png" />
          </div>
          <span class="content_title">中心值班信息</span>
        </div>
        <div style="display: flex; overflow-x: scroll; width: 1300rem;">
          <div v-for="(item,index) in dutyCenterData" :key="index">
            <div style="display: flex">
              <div style="width: 240rem;height: 265rem;margin-top: 30rem; border-radius: 5rem;">
                <img style="width: 100%; height: 100%" :src="item.picture" />
              </div>
              <div
                style="margin-left: 32rem;margin-right: 64rem;margin-top: 33rem; width: 390rem; height: 350rem; font-size: 56rem;font-family: Source Han Sans CN;font-weight: 400;color: #FFFFFF;line-height: 75rem;"
              >
                <div>{{ item.occupation }}</div>
                <div style="margin-top: 20rem">{{ item.roleName }}</div>
                <div style="margin-top: 20rem">{{ item.personName }}</div>
                <div style="margin-top: 20rem">{{ item.phone }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--      <div class="information_2">-->
      <!--        <div class="title">-->
      <!--          <div class="image" style="width:108rem;height:76rem">-->
      <!--            <img style="width: 100%; height: 100%" src="../../../../assets/SmallTitle/二级标题@2x.png">-->
      <!--          </div>-->
      <!--          <span class="content_title">委办值班信息</span>-->
      <!--        </div>-->
      <!--        <div>-->
      <!--          <el-table :data="dutyNotCenterData" class="large-screen" height="535rem">-->
      <!--            <el-table-column label="地区" prop="area" min-width="19" show-overflow-tooltip></el-table-column>-->
      <!--            <el-table-column label="单位" prop="dept" min-width="19%" show-overflow-tooltip></el-table-column>-->
      <!--            <el-table-column label="姓名" prop="personName" min-width="17%" show-overflow-tooltip></el-table-column>-->
      <!--            <el-table-column label="职位" prop="occupation" min-width="15%" show-overflow-tooltip></el-table-column>-->
      <!--            <el-table-column label="联系方式" prop="phone" min-width="30%" show-overflow-tooltip></el-table-column>-->
      <!--          </el-table>-->
      <!--        </div>-->
      <!--      </div>-->
      <div class="information_3">
        <div class="title">
          <div class="image" style="width:108rem;height:76rem">
            <img style="width: 100%; height: 100%" src="../../../../assets/SmallTitle/二级标题@2x.png" />
          </div>
          <span class="content_title">待办事项信息</span>
        </div>
        <div>
          <el-table :data="ToDoTableData" class="large-screen" height="681rem">
            <el-table-column
              label="事件描述"
              prop="event_title"
              show-overflow-tooltip
              width="auto"
              min-width="63%"
            ></el-table-column>
            <el-table-column label="操作" width="auto" min-width="37%">
              <template slot-scope="scope">
                <span class="iconfont">&#xea88;</span>
                <span
                  style="cursor: pointer;font-size: 56rem;font-family: Microsoft YaHei;font-weight: 400;color: #14A1FF;"
                  @click="toEventHandelPage(scope.row)"
                >一键指挥</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import axios from '@/utils/request';
import Axios from '@/utils/request'

export default {
  name: 'DutyInformation',
  components: { SmallTitle },
  props: ['info'],
  data() {
    return {
      dutyCenterData: [],
      dutyNotCenterData: [],
      commissionTableData: [],
      ToDoTableData: [],
      value: '',
      precinct: this.$route.query.eventId,
      type: '',
      list: [],
      lineDialogVisible: false,
      showvideoVisible: false
    }
  },
  watch: {
    '$store.getters.eventInfo': {
      handler(v) {
        this.type = v ? v.eventType : ''
      },
      deep: true
    },
    info: {
      handler(v) {
        this.list = v.eventTags ? JSON.parse(v.eventTags) : []
      },
      deep: true
    }
  },
  mounted() {
    this.query()
    this.queryEventList()
  },
  methods: {
    handleClose() {
    },
    openDialog() {
      this.lineDialogVisible = true
    },
    query() {
      const month = new Date().getMonth() + 1
      let startDate
      let endDate
      if (month > 9) {
        startDate = new Date().getFullYear() + '-' + month + '-' + new Date().getDate() + ' ' + '00:00:00'
        endDate = new Date().getFullYear() + '-' + month + '-' + new Date().getDate() + ' ' + '23:59:59'
      } else {
        startDate = new Date().getFullYear() + '-' + '0' + month + '-' + new Date().getDate() + ' ' + '00:00:00'
        endDate = new Date().getFullYear() + '-' + '0' + month + '-' + new Date().getDate() + ' ' + '23:59:59'
      }
      let url = '/community/queryDutyInfo?startDate=' + startDate + '&endDate=' + endDate
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.dutyCenterData = res.data.data.data
          let data = []
          let dataNotCenter = []
          res.data.data.data.forEach(item => {
            if (item.dept === '城运中心') {
              data.push(item)
            } else {
              item.area = '滨湖区'
              dataNotCenter.push(item)
            }
          })
          this.dutyCenterData = data
          this.dutyNotCenterData = dataNotCenter
        } else {
          this.$message.error('值班信息查询失败！')
        }
      })
    },
    queryEventList() {
      let url = '/apis/Command/eventInfoList'
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.ToDoTableData = res.data.data
        } else {
          this.$message.error('事件查询失败！')
        }
      })
    },
    toEventHandelPage(row) {
      let url = '/community/dapeng/event/edit'
      Axios
        .post(url, {
          id: row.eventId,
          status: '42'
        })
        .then((res) => {
          if (res.data.code === 200) {
            this.$router.push({
              path: '/',
              query: {
                eventId: row.eventId
              }
            })
            this.$router.go(0) // 页面刷新
          } else {
            this.$message.error(res.data.message)
          }
        })
    }
  }
}
</script>
<style lang="scss">
.videoDialogSJGL {
  z-index: 10000 !important;

  ::v-deep .el-dialog {
    .el-dialog__header {
      display: none;
    }
  }
}
</style>
<style lang="scss" scoped>
.EventProfile {
  width: 1672rem;
  position: absolute;
  top: 2100rem;
  left: 3048rem;

  .content {
    display: flex;
    flex-wrap: wrap;
    width: 1672rem;
    height: 1404rem;
    background: rgba(3, 16, 50, 0.5);
    border: 2rem solid #002e7b;
    border-radius: 2rem;
    padding: 5rem;

    .information_1 {
      margin-left: 76rem;
      width: 100%;
      background: transparent;
      margin-top: 25rem;
      height: 460rem;

      .title {
        margin-bottom: 45rem;
        display: inline-flex;

        .image {
          margin-top: 6rem;
        }

        .content_title {
          margin-left: 28rem;
          width: 450rem;
          height: 18rem;
          font-size: 64rem;
          font-family: SourceHanSansCN-Regular;
          font-weight: 400;
          color: #fff;
          line-height: 80rem;
        }
      }
    }

    //.information_2 {
    //  margin-left: 50rem;
    //  width: 55%;
    //  background: transparent;
    //
    //  .title {
    //    margin-bottom: 45rem;
    //    display: inline-flex;
    //
    //    .image {
    //      margin-top: 2rem;
    //    }
    //
    //    .content_title {
    //      margin-left: 28rem;
    //      width: 1420rem;
    //      height: 18rem;
    //      font-size: 64rem;
    //      font-family: SourceHanSansCN-Regular;
    //      font-weight: 400;
    //      color: #00F6FF;
    //    }
    //  }
    //}

    .information_3 {
      //margin-left: 50rem;
      width: 101%;
      margin-top: 90rem;
      background: transparent;

      .title {
        margin-bottom: 45rem;
        display: inline-flex;

        .image {
          margin-top: 6rem;
        }

        .content_title {
          margin-left: 28rem;
          width: 450rem;
          height: 18rem;
          font-size: 64rem;
          font-family: SourceHanSansCN-Regular;
          font-weight: 400;
          color: #fff;
        }
      }
    }
  }
}
</style>
<style lang="scss">
.iconfont {
  font-family: 'iconfont' !important;
  font-size: 70rem;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-image: -webkit-linear-gradient(228deg, #9bc3e1 0%, #0f87d4 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.el-table tr {
  background-color: #031033 !important;
}

.el-table thead {
  color: #fff !important;
}

.large-screen.el-table .el-table__body-wrapper .el-table__body tr:nth-of-type(2n-1) td {
  background: #002d7a;
}

.large-screen.el-table .el-table__body-wrapper .el-table__body tr:nth-of-type(2n) td {
  background: #032532;
}

.el-table th.el-table__cell > .cell {
  display: inline-block;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  position: relative;
  vertical-align: middle;
  //padding-left: 10rem;
  padding-right: 10rem;
  font-size: 56rem;
  font-family: SourceHanSansCN-Regular;
  font-weight: 400;
  color: #ffffff;
}

.large-screen.el-table .cell {
  font-size: 56rem;
  font-family: SourceHanSansCN-Regular;
  font-weight: 400;
  color: #ffffff;
}
.large-screen.el-table .el-table__body-wrapper .el-table__body .el-table__row {
  height: 118rem !important;
}
.large-screen.el-table .el-table__header-wrapper .el-table__header {
  height: 118rem !important;
}
//.el-table .el-table__cell {
//  padding: 25rem 0 25rem 36rem;
//}
.el-table .cell {
  line-height: 80rem !important;
}
.el-table .el-table__cell {
  padding: 5rem 0 5rem 36rem !important;
}
</style>
