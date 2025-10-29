<template>
  <div class="Left2To1 module">
    <SmallTitle :titleList="['指挥体系']" size="big" />
    <img class="button1" src="../../../../assets/global/设置.png" alt @click="showVisible = true" />
    <div class="content">
      <div class="card">
        <div class="cardBackground" style="display: flex; justify-content: space-evenly">
          <div style="display: flex" v-for="(item, index) in list" :key="index" v-show="index < 3">
            <img
              v-if="item.duty.indexOf('市长') !== -1 && item.duty.indexOf('副市长') === -1"
              src="../../../../assets/left/市长.png"
              alt
            />
            <img v-else src="../../../../assets/left/217时间@2x.png" alt />
            <div class="right-part">
              <span v-show="item.unit" class="line-one" :title="item.unit">{{ item.unit }}/{{ item.duty }}</span>
              <span v-show="item.duty != null" class="line-one" :title="item.duty">{{ item.leader }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="row-panel">
        <div class="info-p" @click="leader(showLeaderList)">
          <div>
            <span class="icon-a"></span>
            <span class="info">事件相关区属单位</span>
          </div>
          <span class="num">{{ reData['2'] ? Object.keys(reData['2'][0]).length : 0 }}个</span>
        </div>
        <div class="info-p" @click="leader(showLeaderList1)">
          <div>
            <span class="icon-b"></span>
            <span class="info">事件相关属地单位</span>
          </div>
          <span class="num">{{ reData['3'] ? Object.keys(reData['3'][0]).length : 0 }}个</span>
        </div>
      </div>
    </div>
    <!-- <el-dialog
      :visible.sync="showVisibleSSXGDWXQ"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showVisibleSSXGDWXQ=false"
        style="width: 4060rem;height: 3326rem;"
        src="../../../../assets/global/事件相关区属单位详情@2x.png"
        alt
      />
    </el-dialog>-->
    <!-- <el-dialog
      :visible.sync="showVisible"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img @click="showVisible=false"
           style="width: 4060rem;height: 3326rem;"
           src="../../../../assets/global/指挥组织体系编辑@2x.png"
           alt=""
      />

      <span style="position:absolute;bottom:600rem;left:200rem;display: inline-block;width: 1200rem;height: 200rem;background-color: transparent;" @click.prevent="showChild1Visible=true"></span>
      <span style="position:absolute;bottom:600rem;left:1450rem;display: inline-block;width: 1200rem;height: 200rem;background-color: transparent;" @click.prevent="showChild2Visible=true"></span>
      <span style="position:absolute;bottom:600rem;right:200rem;display: inline-block;width: 1200rem;height: 200rem;background-color: transparent;" @click.prevent="showChild3Visible=true"></span>
    </el-dialog>-->
    <personnalDialog
      :dialogVisibleFlag.sync="showVisible"
      :currentEvent="currentEvent"
      :eventType="eventType"
      :unitOptions="unitOptions"
    />
    <el-dialog
      :visible.sync="showChild1Visible"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showChild1Visible = false"
        style="width: 5590rem; height: 3218rem"
        src="../../../../assets/global/指挥长设置@2x.png"
        alt
      />
    </el-dialog>
    <el-dialog
      :visible.sync="showChild2Visible"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showChild2Visible = false"
        style="width: 5590rem; height: 3218rem"
        src="../../../../assets/global/事件相关属地单位@2x.png"
        alt
      />
    </el-dialog>
    <el-dialog
      :visible.sync="showChild3Visible"
      :modal="false"
      class="videoDialogZHTXBJ"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showChild3Visible = false"
        style="width: 5590rem; height: 3218rem"
        src="../../../../assets/global/事件相关属地单位@2x.png"
        alt
      />
    </el-dialog>
    <DialogAddressBook :dialogAddressBookVisible.sync="dialogAddressBookVisible" @openDialog="childOpenDialog" />
    <leaderDialog :dialogVisibleFlag.sync="showLeaderVisible" :showLeader="showLeader" />
    <DialogPhone :dialogPhoneVisible.sync="dialogPhoneVisible" :infomation="dialogPhoneData" />
    <DialogMessage :dialogMessageVisible.sync="messageVisible" :infomation="dialogMessageData" />
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import Axios from '@/utils/request'
import DialogAddressBook from '../dialog/DialogAddressBook.vue'
import leaderDialog from '../dialog/leaderDialog.vue'
import DialogPhone from '../dialog/DialogPhone.vue'
import DialogMessage from '../dialog/DialogMessage.vue'
import personnalDialog from '../dialog/personnalDialog.vue'
import moment from 'moment'
export default {
  name: 'Left2To1',
  components: {
    SmallTitle,
    leaderDialog,
    DialogAddressBook,
    DialogPhone,
    DialogMessage,
    personnalDialog
  },
  data() {
    return {
      showVisibleSSXGDWXQ: false,
      showChild1Visible: false,
      showChild2Visible: false,
      showChild3Visible: false,
      showVisible: false,
      importVisible: false,
      list: [],
      listArr: [],
      reData: {},
      personnalDialogVisible: false,
      currentEvent: '',
      eventType: '',
      unitOptions: [],
      showLeaderVisible: false,
      showLeader: [],
      showLeaderList: [],
      showLeaderList1: [],
      dialogAddressBookVisible: false,
      dialogPhoneVisible: false,
      precinctId: this.$route.query.precinctId,
      messageVisible: false,
      dialogPhoneData: {},
      dialogMessageData: {},
      timer: null,
      dutyList: [],
      total: 0,
      currentPage: 1,
      pageSize: 2,
      currentPageData: [],
      activeIndex: 0,
      planId: 0
    }
  },
  watch: {
    '$store.getters.eventPlanId'(v) {
      console.log('planId:', v)
      this.planId = v
      this.communicationOrderByDepartment()
    }
  },
  mounted() {
    // this.communicationOrderByDepartment()
    //this.importCity()
    // this.getEventLists()
    this.$root.Bus.$on('refreshIframe', (data) => {
      this.communicationOrderByDepartment()
      // this.importCity()
    })
  },
  methods: {
    handleClose() {},
    doShowChild(type) {
      this.showChildVisible = true
      this.showChildType = type
    },
    getCurrentPageData(v) {
      this.activeIndex = v - 1
      let begin = (v - 1) * this.pageSize
      let end = v * this.pageSize
      this.currentPageData = this.dutyList.slice(begin, end)
    },
    importCity() {
      this.timer && clearInterval(this.timer)
      let url = '/apis/warningInfo/getDutyInfo'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let list = res.data.data
          for (let i = 0; i < list.length; i++) {
            list[i].leader =
              list[i].name.replace(' ', '').length === 2
                ? list[i].name.slice(0, 1) + '\xa0\xa0\xa0' + list[i].name.slice(1)
                : list[i].name
            if (list[i].duty === '带班市领导') {
              list[i].sort = 1
              // list[i].url = 'http://10.1.50.137:7777/boss/myResource/c0ZTYzVCZnVhYU09/' + list[i].name + '.jpg'
              list[i].url = list[i].imgurl || ''
            }
            if (list[i].duty === '指挥长') {
              list[i].sort = 2
            }
            if (list[i].duty === '专职值班长') {
              list[i].sort = 3
            }
            if (list[i].duty === '值班员') {
              list[i].sort = 4
            }
          }
          // 按顺序展示
          list.sort(function (a, b) {
            return a.sort >= b.sort ? 1 : -1
          })
          // 按相同职务组成新数组
          let tempArr = []
          let Data = []
          for (let i = 0; i < list.length; i++) {
            if (tempArr.indexOf(list[i].duty) === -1) {
              Data.push({
                duty: list[i].duty,
                dataInfo: [list[i]]
              })
              tempArr.push(list[i].duty)
            } else {
              for (let j = 0; j < Data.length; j++) {
                if (Data[j].duty === list[i].duty) {
                  Data[j].dataInfo.push(list[i])
                  break
                }
              }
            }
          }
          this.listArr = Data
        }
        this.intervalFixedPointTaskFn(
          {
            timeFixedArr: ['08:55:00'],
            delayTime: 1000
          },
          this.importCity
        )
      })
    },
    intervalFixedPointTaskFn({ timeFixedArr = [], delayTime = 1000 }, tickerFn) {
      let executed = true // true代表需要定时刷新的方法是否执行了,后面纵使在设定时间范围内也不执行自动刷新
      this.timer && clearInterval(this.timer)
      this.timer = setInterval(() => {
        console.log('timer...')
        let currentTime = new Date().getTime() // 当前时间
        let currentYMD = moment(currentTime).format('YYYY-MM-DD') // 当前年月日
        let refresh = false // 判断是否需要刷新
        // 判断是否在设定的时间范围内
        let count = 0 // 记录不在设定范围内的数量
        for (let i = 0; i < timeFixedArr.length; i++) {
          let item = timeFixedArr[i] // 固定的时分秒
          let setTime = new Date(`${currentYMD} ${item}`).getTime() // 完整时间
          if (currentTime >= setTime && currentTime <= setTime + delayTime) {
            // 没有刷新过且在设定时间范围内 - 刷新
            if (!executed) {
              // 只执行一次
              refresh = true
            }
            break
          } else {
            count++
          }
        }

        if (count === timeFixedArr.length) {
          executed = false
        }

        if (refresh) {
          executed = true
          console.log(`${moment(currentTime).format('YYYY-MM-DD HH:mm:ss')} 执行定时任务....`)
          tickerFn && tickerFn()
        }
      }, 1000)
    },
    childOpenDialog(data) {
      this.openDialog(data.type, data.data)
    },
    openDialog(type, data) {
      switch (type) {
        case 'video':
          this.dialogVideoVisible = true
          this.dialogVideoData = data
          break
        case 'law-record':
          this.dialogLawRecordVisible = true
          this.dialogLawRecordData = data
          break
        case 'addressbook':
          this.dialogAddressBookVisible = true
          break
        case 'phone':
          this.dialogPhoneVisible = true
          this.dialogPhoneData = data
          break
        case 'message':
          this.messageVisible = true
          this.dialogMessageData = data
          break
        default:
          break
      }
    },
    talk(val) {
      val.unit = val.post
      this.dialogPhoneVisible = true
      this.dialogPhoneData = val
    },
    checking() {
      this.importVisible = true
    },
    leader(v, i) {
      // return false
      if (v && v.length > 0) {
        console.log('leader', v)
        this.showLeader = v
      } else {
        this.showLeader = []
      }
      this.showLeaderVisible = true
    },
    communicationOrderByDepartment() {
      let url = '/apis/Command/communicationOrderByDepartmentByPrecinctId?linkType=2&isMax=isMax&planId=' + this.planId
      // if (this.$route.query.precinctId) {
      //   url += '?precinctId=' + this.$route.query.precinctId
      // }
      let _this = this
      Axios.get(url).then((res) => {
        let data = res.data.data
        _this.reData = data
        _this.showLeaderList = []
        if (data['2']) {
          let leader = data['2'][0]
          for (let key in leader) {
            let obj = {
              index: 0,
              name: leader[key].name,
              data: leader[key].data
            }
            _this.showLeaderList.push(obj)
          }
        } else {
          // _this.showLeaderList.push({ index: 0 })
        }
        if (data['3']) {
          let leader = data['3'][0]
          for (let key in leader) {
            let obj = {
              index: 1,
              name: leader[key].name,
              data: leader[key].data
            }
            _this.showLeaderList1.push(obj)
          }
        } else {
          // _this.showLeaderList1.push({ index: 1 })
        }
        _this.list = data['1'] ? data['1'][0]['999998'].data : []
        // if (this.list.length > 0) {
        //   this.list.forEach(item => {
        //     item.unitDuty = item.unit ? (item.duty ? (item.unit + '/' + item.duty) : item.unit) : (item.duty ? item.duty : '')
        //   })
        // }
      })
    },
    openQueryUserDialog() {
      this.personnalDialogVisible = true
    },
    getEventLists() {
      let url = '/apis/eventDeal/eventInfo'
      if (this.$route.query.precinctId) {
        url += '?precinctId=' + this.$route.query.precinctId
      }
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          if (res.data.data.length > 0) {
            this.currentEvent = res.data.data[0].eventType
            let event = res.data.data[0].eventType
            this.queryUnit(event)
          } else {
            this.queryUnit()
          }
        }
      })
    },
    queryUnit(e) {
      const url = '/apis/manage/queryEventTypeList'
      Axios.get(url).then((r) => {
        this.unitOptions = r.data.data
        this.eventType = ''
        this.unitOptions.forEach((item) => {
          if (e && e === item.typeName) {
            this.eventType = item.id
          }
        })
      })
    }
  },
  beforeDestroy() {
    this.timer && clearInterval(this.timer)
  }
}
</script>
<style lang="scss" scoped>
.videoDialogZHTXBJ {
  z-index: 10000 !important;
  ::v-deep .el-dialog {
    position: relative;
    .el-dialog__header {
      display: none;
    }
  }
}
</style>
<style lang="scss" scoped>
.Left2To1 {
  width: 2268rem;
  // height: 246rem;
  position: absolute;
  top: 1850rem;
  left: 80rem;
  .button1 {
    width: 96rem;
    height: 96rem;
    position: absolute;
    right: 0rem;
    top: 3rem;
  }
  .content {
    .card {
      margin-top: 20rem;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      width: 100%;
      height: 290rem;
      background-image: url('../../../../assets/left/bg.png');
      // background: linear-gradient(0deg, rgba(21, 59, 102, 0.5), rgba(26, 34, 52, 0));
      // border: 1rem solid;
      // border-image: linear-gradient(0deg, rgba(70, 129, 249, 0.5), rgba(70, 129, 249, 0.5)) 1 1;
      // background: rgba(0, 145, 255, 0.1);
      border: 1rem solid rgba(0, 145, 255, 0.5);
      // box-shadow: 2rem 2rem 2rem 2rem rgba(0, 145, 255, 0.1);
      // border-radius: 4rem;
      .cardBackground {
        width: 100%;
        // height: 110rem;
        background-image: url('../../../../assets/left/座子.png');
        background-repeat: no-repeat;
        background-size: 98% 30%;
        background-position: 50% 90%;
      }
      img {
        width: 180rem;
        height: 114rem;
        margin-top: 70rem;
      }
      .left-part {
        width: 33%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-evenly;
        background-image: url('../../../../assets/left/值班体系@2x.png');
        background-repeat: no-repeat;
        background-position: 20rem 35rem;
        .line-one {
          font-size: 20rem;
          font-family: PingFang SC;
          font-weight: 600;
          color: #ffffff;
          white-space: nowrap;
        }
        .line-two {
          font-size: 16rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #9eacc1;
          white-space: nowrap;
        }
      }
      .center-part {
        width: 33%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-evenly;

        font-size: 14rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #5a697d;
      }
      .right-part {
        width: 100%;
        display: flex;
        flex-direction: column;
        // align-items: center;
        // justify-content: space-evenly;
        font-size: 44rem;
        font-family: PingFang SC;
        font-weight: 400;
        color: #ffffff;
        margin-top: 70rem;
        margin-left: 28rem;
        .line-one {
          color: #028df8;
        }
        .more {
          width: 160rem;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }
    }
    .cardSJ {
      margin-top: 10rem;
      // margin-top: 40rem;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      width: 100%;
      height: 100rem;

      .cardDiv:first-of-type .leaderOne {
        width: 98%;
        background-image: url('../../../../assets/left/带班领导.png');
        background-repeat: no-repeat;
        margin-left: 0;
      }
      .cardDiv:last-of-type .leaderOne .right-part .line-one {
        margin: 6rem 0;
      }
      .leaderOne {
        background-image: url('../../../../assets/left/其他三个.png');
        background-repeat: no-repeat;
        width: 94%;
        background-size: 100% 100%;
        padding: 0rem 5rem 0 10rem;
        margin-left: 7rem;
        .showFlex {
          display: flex;
          align-items: center;
          height: 25rem;
        }
        .dutyUl {
          display: flex;
          position: absolute;
          left: 40%;
          bottom: 0%;
          // justify-content: center;
          li {
            width: 10rem;
            height: 10rem;
            cursor: pointer;
            background: url('../../../../assets/left/圆形.png');
            background-repeat: no-repeat;
          }
        }
        .active {
          width: 20rem !important;
          background: url('../../../../assets/left/矩形.png') !important;
          background-repeat: no-repeat !important;
        }
        .line-two {
          color: #a3c9fc;
          font-size: 12rem;
        }
        .right-part {
          img {
            height: 56rem;
            margin-top: 5rem;
            margin-right: 10rem;
          }
          .line-one:first-of-type {
            font-size: 14rem;
            color: #fff;
            position: relative;
            // margin: 0 0 8rem 0;
            img {
              width: 15rem;
              height: 15rem;
              // margin-top: 0;
              // margin-right: 0;
              margin-left: 36rem;
              // position: absolute;
              // right: 24rem;
              cursor: pointer;
            }
          }
          .line-one:last-of-type {
            color: #a3c9fc;
            font-size: 10rem;
            flex: 1;
            justify-content: center;
            flex-direction: column;
            img {
              width: 18rem;
              height: 18rem;
              margin-top: 0;
              margin-right: 0;
              margin-left: 20rem;
              cursor: pointer;
            }
          }
        }
      }
      .leaderother {
        background-image: url('../../../../assets/left/其他三个.png');
        background-repeat: no-repeat;
        width: 95%;
        background-size: 100% 100%;
      }
    }
    .row-panel {
      display: flex;
      flex-wrap: nowrap;
      justify-content: space-between;
      .info-p {
        width: 1120rem;
        height: 125rem;
        border: 1rem solid rgba(0, 145, 255, 0.5);
        border-radius: 2rem;
        margin-top: 30rem;

        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
        justify-content: space-between;
        align-items: center;
        cursor: pointer;
        div {
          display: flex;
          align-items: center;
          margin-left: 40rem;
          .icon-a {
            width: 70rem;
            height: 60rem;
            display: inline-block;
            background-image: url('../../../../assets/left/机关单位@2x.png');
            background-repeat: no-repeat;
            background-size: contain;
          }
          .icon-b {
            width: 70rem;
            height: 60rem;
            display: inline-block;
            background-image: url('../../../../assets/left/号码归属地@2x.png');
            background-repeat: no-repeat;
            background-size: contain;
          }
          .icon-c {
            width: 22rem;
            height: 22rem;
            display: inline-block;
            background-image: url('../../../../assets/left/单位@2x.png');
            background-repeat: no-repeat;
          }
          .icon-d {
            width: 22rem;
            height: 22rem;
            display: inline-block;
            background-image: url('../../../../assets/left/救援力量@2x.png');
            background-repeat: no-repeat;
          }
          .info {
            font-size: 48rem;
            font-family: SourceHanSansCN;
            font-weight: 400;
            color: #ffffff;
            line-height: 24rem;
            margin-left: 10rem;
          }
        }
        .num {
          font-size: 56rem;
          font-family: PingFang SC;
          font-weight: 400;
          color: #ffffff;
          line-height: 24rem;
          margin-right: 20rem;
        }
      }
    }
    .onlyLine {
      position: absolute;
      top: -2rem;
      right: 55rem;
      width: 100rem;
      height: 26rem;
      font-size: 15rem;
      font-family: PingFang SC;
      text-align: center;
      line-height: 26rem;
      font-weight: 600;
      color: #ffffff;
      background: linear-gradient(0deg, RGBA(49, 173, 243, 1) 30%, #ffffff 70.042969%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      border: 1rem solid RGBA(21, 97, 155, 1);
      cursor: pointer;
    }
    .menu-1 {
      position: absolute;
      top: -3rem;
      right: 20rem;
      width: 26rem;
      height: 26rem;
      border: 1rem solid rgba(17, 155, 248, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      i {
        font-size: 18rem;
        color: rgba(17, 155, 248, 1);
      }
    }
  }
}
</style>
