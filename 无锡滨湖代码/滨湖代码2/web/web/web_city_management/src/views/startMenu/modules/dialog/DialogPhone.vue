<template>
  <el-dialog
    title="拨打电话"
    :visible.sync="dialogVisible"
    :modal="false"
    width="490rem"
    class="phone-dialog"
    :before-close="handleClose"
    :append-to-body="true"
    v-show="isMax"
  >
    <i class="el-icon-minus" @click="minimize"></i>
    <div class="head">
      <img v-if="infomation.url" :src="infomation.url" alt="" />
      <img v-else src="@/assets/leftOne/defaultAvatar.png" alt="" />
    </div>
    <div class="name">{{ name }}</div>
    <div class="list">
      <div class="item" v-for="(v, i) in list" :key="i">
        <div class="left">{{ v.key }}:</div>
        <div class="right" :title="v.value">{{ v.value }}</div>
      </div>
    </div>
    <div class="law-footer">
      <div class="foot" v-if="!showPhone" @click="handleOk(infomation.phone)" style="cursor: pointer">
        <img src="@/assets/rightTwo/联动指挥弹窗_slices/电话.png" alt="" />
        <span style="color: #00ffff">拨打</span>
      </div>
      <div class="foot" v-else @click="handleNo(infomation.phone)" style="cursor: pointer">
        <img src="@/assets/leftOne/hungup.png" alt="" />
        <span style="color: #f3740c">挂断</span>
      </div>
    </div>
    <!-- <div class="others">
      <div class="other-head">
        <span class="head-item" v-show="show" @click="show = !show">
          <span>展开</span>
          <i class="el-icon-arrow-down"></i>
        </span>
        <span class="head-item" v-show="!show" @click="show = !show">
          <span>收起 </span>
          <i class="el-icon-arrow-up" @click="minimize"></i>
        </span>
      </div>
      <div class="other-list" v-show="show">
        <div class="other-item" v-show="infomation.phone">
          <div>{{ infomation.phone }}</div>
          <div>
            <img src="@/assets/leftOne/call.png" alt="" @click="callup(infomation.phone)" style="cursor: pointer" />
          </div>
        </div>
      </div>
    </div> -->
  </el-dialog>
</template>

<script>
// import Axios from '@/utils/request'
// import { demo, unCall } from '../../../../utils/phone'

export default {
  props: ['dialogPhoneVisible', 'infomation'],
  watch: {
    dialogPhoneVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.init()
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      show: true,
      isMax: true,
      showLook: true,
      name: '',
      showPhone: false,
      list: [
        // {
        //   key: '姓名',
        //   value: '王诗琪'
        // },
        {
          key: '电话',
          value: '暂无'
        },
        {
          key: '所属单位',
          value: '暂无'
        },
        {
          key: '职务',
          value: '暂无'
        }
      ],
      otherList: [],
      eventCallback: {},
      url1: ''
    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogPhoneVisible', false)
      if (this.showPhone) {
        this.handleNo(this.infomation.phone)
      }
      let model = {
        Action: 'LogOut',
        GongHao: this.url1.gonghao,
        FenJi: this.url1.fenji,
        PlatFormCode: this.url1.id,
        Params: this.infomation.phone
      }
      window.VoiceInterfaceObject.LogOut(model)
      this.url1 = ''
    },
    handleOk(e) {
      // demo(e)
      this.showPhone = true
      let model = {
        Action: 'DialOut',
        GongHao: this.url1.gonghao,
        FenJi: this.url1.fenji,
        PlatFormCode: this.url1.id,
        Params: e
      }
      window.VoiceInterfaceObject.DialOut(e, model)
    },
    handleNo(e) {
      // unCall(e)
      this.showPhone = false
      let model = {
        Action: 'HangUp',
        GongHao: this.url1.gonghao,
        FenJi: this.url1.fenji,
        PlatFormCode: this.url1.id,
        Params: e
      }
      window.VoiceInterfaceObject.HangUp(model)
    },
    init() {
      this.name = this.infomation.leader
      this.list[0].value = this.infomation.phone ? (this.infomation.phone.substring(3, 0)) + '****' + (this.infomation.phone.substring(7)) : ''
      this.list[1].value = this.infomation.unit
      this.list[2].value = this.infomation.unitName || this.infomation.duty
      this.login()
    },
    login() {
      this.url1 = ''
      sessionStorage.getLogin = ''
      let precinctId = this.$route.query.precinctId
      if (precinctId === '510113') {
        // 青白江区
        this.url1 = sysConfig.qingbaijiangUrl
      } else if (precinctId === '510106') {
        // 金牛市
        this.url1 = sysConfig.jinniuUrl
      } else if (precinctId === '510104') {
        // 锦江区
        this.url1 = sysConfig.jinjiangUrl
      } else if (precinctId === '510121') {
        // 金堂县
        this.url1 = sysConfig.jintangUrl
      } else if (precinctId === '510129') {
        // 大邑县
        this.url1 = sysConfig.dayiUrl
      } else if (precinctId === '510181') {
        // 都江堰市
        this.url1 = sysConfig.dujiangyanUrl
      } else if (precinctId === '510183') {
        // 邛崃市
        this.url1 = sysConfig.qinlaiUrl
      } else if (precinctId === '510105') {
        // 青羊区
        this.url1 = sysConfig.qinyangUrl
      } else if (precinctId === '510185') {
        // 简阳市
        this.url1 = sysConfig.jianyangUrl
      } else if (precinctId === '510108') {
        // 成华区
        this.url1 = sysConfig.chenghuaUrl
      } else if (precinctId === '510131') {
        // 蒲江县
        this.url1 = sysConfig.pujiangUrl
      } else if (precinctId === '510107') {
        // 武侯区
        this.url1 = sysConfig.wuhouUrl
      } else if (precinctId === '510182') {
        // 彭州市
        this.url1 = sysConfig.pengzhouUrl
      } else if (precinctId === '510116') {
        // 双流区
        this.url1 = sysConfig.shuangliuUrl
      } else {
        this.url1 = sysConfig.phoneUrl
      }
      let model = {
        Action: 'keeplive',
        GongHao: this.url1.gonghao,
        FenJi: this.url1.fenji,
        PlatFormCode: this.url1.id,
        Params: ''
      }
      let models = {
        Action: 'Login',
        GongHao: this.url1.gonghao,
        FenJi: this.url1.fenji,
        PlatFormCode: this.url1.id,
        Params: ''
      }
      var wsParams = {
        url: this.url1.url,
        IsReConnect: true, // 是否重连
        reConnectTime: 40, // 重连间隔
        IsKeepAlive: true, // 是否发送心跳
        KeepAliveTime: 30, // 20秒发送1次
        KeepAliveData: JSON.stringify(model), // 心跳
        onOpen: function (e) {
          // 登录语音系统
          window.VoiceInterfaceObject.Login(models)
        },
        onMessage: function (e) {
          // eslint-disable-next-line no-new-func
          var func = new Function('VoiceInterfaceObject.' + e.data)
          func()
        },
        onClose: function (e) {
          console.log('通讯链路断开等待重连')
        }
      }
      // 初始化WebSocket
      window.WebSocktInterface(wsParams)
      let modelt = {
        Action: 'SetGroupRole',
        GongHao: this.url1.gonghao,
        FenJi: this.url1.fenji,
        PlatFormCode: this.url1.id,
        Params: this.url1.zuoxi
      }
      window.VoiceInterfaceObject.SetGroupRole(modelt)
    },
    callup() {},
    minimize() {
      this.$root.Bus.$emit('minimize')
      this.isMax = false
    }
  },
  mounted() {
    this.$root.Bus.$on('maximize', () => {
      this.isMax = true
    })
  }
}
</script>

<style lang='scss'>
.phone-dialog {
  .el-icon-minus {
    position: absolute;
    top: 20rem;
    right: 45rem;
    color: #3df8f8;
    cursor: pointer;
  }

  .el-dialog__body {
    height: 500rem !important;
  }

  .head {
    width: 100%;
    height: 170rem;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: 130rem;
      height: 130rem;
    }
  }

  .name {
    font-size: 20rem;
    margin-bottom: 10rem;
    text-align: center;
    font-size: 20rem;
  }

  .list {
    .item {
      margin-top: 10rem;
      font-size: 20rem;
      display: flex;
      line-height: 40rem;
      padding: 0 10rem;
      border-bottom: 1rem solid #066469;
      justify-content: space-between;

      .right {
        color: #00ffff;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }

    .item:nth-of-type(2) .left {
      width: 85rem;
    }
  }

  .law-footer {
    display: flex;
    justify-content: space-around;
    margin-top: 20rem;
    padding: 0 120rem;

    .foot {
      font-size: 24rem;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      img {
        width: 50rem;
        margin-bottom: 10rem;
      }
    }
  }

  .others {
    font-size: 16rem;
    color: #00ffff;
    width: 100%;

    .other-head {
      width: 100%;
      margin-top: 10rem;
      border-bottom: 1rem solid #28feff;

      .head-item {
        display: flex;
        justify-content: flex-end;
        cursor: pointer;

        i {
          margin-left: 10rem;
          font-size: 20rem;
        }
      }
    }

    .other-list {
      max-height: 180rem;
      overflow: auto;
    }

    .other-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 36rem;
      padding: 0 10rem;
      border-bottom: 1rem solid #28feff;

      img {
        width: 16rem;
      }

      &:nth-child(even) {
        background: #183d4d;
      }
    }
  }
}
</style>
