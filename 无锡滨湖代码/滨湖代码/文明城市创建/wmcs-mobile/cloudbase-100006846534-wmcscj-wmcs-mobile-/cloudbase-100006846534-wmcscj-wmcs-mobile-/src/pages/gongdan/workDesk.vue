<template>
  <view class="workDesk">
    <view class="noticeBoxMain">
      <img src="static/images/workDesk/header.png" />
      <view class="noticeBoxInfos" v-if="userInfo != null">
        <view class="title">
          {{ userInfo.name }}
          <text>欢迎回来！</text>
        </view>
        <view class="brief">单位：{{ userInfo.deptName }}</view>
        <view class="brief">角色：{{ userInfo.roleNames }}</view>
        <view class="brief" v-if="userInfo.user">联系方式：{{ userInfo.user.phonenumber }}</view>
        <!-- <div class="icons" v-if="jiashicang">
          <uni-icons type="tune" size="36" color="#4770bf" @click="logout()"></uni-icons>
        </div> -->
      </view>
    </view>
    <div class="menu" v-for="(item, index) in list" :key="index">
      <uni-section :title="item.name" type="line" padding>
        <uni-grid :column="4" :show-border="false" :square="false" @change="change">
          <uni-grid-item v-for="(item2, j) in item.children" :index="index + '-' + j" :key="j">
            <view class="grid-item-box">
              <img class="image" :src="item2.icon" width="48" height="48" />
              <span class="text">{{ item2.name }}</span>
            </view>
          </uni-grid-item>
        </uni-grid>
      </uni-section>
    </div>
    <p>{{ code }}</p>
    <!-- <uni-fab ref="fab" :popMenu="false" :pattern="pattern" horizontal="right" vertical="bottom" direction="horizontal"
      @fabClick="fabClick1" /> -->
    <!-- <uni-fab ref="fab2" :popMenu="false" :pattern="pattern2" horizontal="left" vertical="bottom" direction="horizontal"
      @fabClick="fabClick2" /> -->
  </view>
</template>

<script>
import UniGrid from "@/plugins/uni-grid/uni-grid.vue"
import UniGridItem from "@/plugins/uni-grid-item/uni-grid-item.vue"
import UniSection from "@/plugins/uni-section/uni-section.vue"
import UniFab from "@/plugins/uni-fab/uni-fab.vue"
import uniIcons from "../../plugins/uni-icons/uni-icons.vue"
export default {
  name: "workDesk",
  components: { UniGrid, UniGridItem, UniSection, UniFab, uniIcons },
  data() {
    return {
      userInfo: null,
      list: [
        // {
        //   icon: 'static/images/workDesk/xxyd.png',
        //   name: '我的上报',
        //   link: "./../gongdan/wodeshangbao",
        //   query: {}
        // },
        // {
        //   icon: 'static/images/workDesk/sjsb.png',
        //   name: '待受理',
        //   link: "./../gongdan/daifenbo",
        //   query: JSON.stringify({ flag: true })
        // },
        // {
        //   icon: 'static/images/workDesk/sqjzry.png',
        //   name: '已受理', link: "./../gongdan/daifenbo",
        //   query: JSON.stringify({ flag: false })
        // },
        // {
        //   icon: 'static/images/workDesk/sjsb.png',
        //   name: '待处置', link: "./../gongdan/daichuzhi",
        //   query: JSON.stringify({ flag: true })
        // },
        // {
        //   icon: 'static/images/workDesk/xcrw.png',
        //   name: '已处置', link: "./../gongdan/daichuzhi",
        //   query: JSON.stringify({ flag: false })
        // },
        // {
        //   icon: 'static/images/workDesk/tzgg.png',
        //   name: '待核查', link: "./../gongdan/daihecha",
        //   query: JSON.stringify({ flag: true })
        // },
        // {
        //   icon: 'static/images/workDesk/wdpm.png',
        //   name: '已办结', link: "./../gongdan/daihecha",
        //   query: JSON.stringify({ flag: false })
        // },
        // {
        //   icon: 'static/images/workDesk/dbjs.png',
        //   name: '延期事件', link: "./../gongdan/yanqishijian",
        //   query: {}
        // },
        // {
        //   icon: 'static/images/workDesk/fwdj.png',
        //   name: '转12345', link: "./../gongdan/z12345",
        //   query: {}
        // },
        // {
        //   icon: 'static/images/workDesk/kqdkdate.png',
        //   name: '我的定位', link: "./../gongdan/mygps",
        //   query: {}
        // },
        // {
        //   icon: 'static/images/workDesk/kqdkdate.png',
        //   name: '巡查点位', link: "./../gongdan/mypatrol",
        //   query: {}
        // },
        // {
        //   icon: 'static/images/workDesk/kqdkdate.png',
        //   name: '事件总览', link: "./../gongdan/zonglan",
        //   query: {}
        // },
      ],
      code: "",
      username: "",
      pattern: {
        color: '#4770bf',
        backgroundColor: '#fff',
        selectedColor: '#4770bf',
        buttonColor: '#4770bf',
        iconColor: '#fff'
      },
      pattern2: {
        color: '#4770bf',
        backgroundColor: '#fff',
        selectedColor: '#4770bf',
        buttonColor: '#4770bf',
        iconColor: '#fff',
        icon: "chatboxes-filled"
      },
      jiashicang: false
    };
  },
  onLoad(params) {
    if (window.location.href.indexOf("?code=") != -1) {
      let c = window.location.href.split("?code=")[1].split("&state=")[0]
      this.$api.Auth({ code: c }).then(res => {
        // this.code = res
        uni.setStorageSync("access_token", res.data.access_token)
        this.getRouter()

        API.getDict().then(res => {
          console.log(res);
        })
        // alert("欢迎使用！")

        // var config = {
        //   noncestr: "Wm3WZYTPz0wzccnW",
        //   "jsapi_ticket": res.data.ticket.ticket,
        //   "timestamp": Math.round(new Date().getTime() / 1000).toString(),
        //   "url": "http://2.21.137.224:8080/wechat/",
        // }
        // let string = "jsapi_ticket=" + config.jsapi_ticket + "&noncestr=" + config.noncestr + "&timestamp=" + config.timestamp + "&url=" + config.url
        // let signature = sha1(string);
        // let newConfig = {
        //   debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        //   appId: 'ww29a8dfb1be6173c1', // 必填，政务微信的cropID
        //   timestamp: config.timestamp, // 必填，生成签名的时间戳
        //   nonceStr: config.noncestr, // 必填，生成签名的随机串
        //   signature: signature,// 必填，签名，见附录1
        //   jsApiList: ["getLocation"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        // }
        // jWeixin.config(newConfig);
      })
    } else {
      this.getRouter()
      

    }

  },
  onShow() {
    this.username = uni.getStorageSync("username")
  },
  computed: {
  },
  methods: {
    getRouter() {
      this.$api.GetUserInfo().then(res => {
        this.userInfo = res.data.loginInfo
      })
      this.$api.GetDict().then(res => {
        let a = {}
        res.data.forEach(r => {
          a[r.code] = r.dict
        })
        window.localStorage.setItem("dicts", JSON.stringify(a))
      })
      this.$api.getRouters({ platform: 2 }).then(res => {
        if (res.data.menuList) {
          this.list = res.data.menuList;
          this.jiashicang = false;
          // for (let i = 0; i < this.list.length; i++) {
          //   const element = this.list[i];
          //   if (element.name == "领导驾驶舱") {
          //     this.jiashicang = true;
          //     this.list.splice(i, 1)
          //     break;
          //   }
          // }
        }
      })
    },
    logout() {
      this.jumpPage("./../screen/home")
    },
    change(e) {
      let i = e.detail.index.split("-");
      this.jumpPage(this.list[i[0]].children[i[1]].link, this.list[i[0]].children[i[1]].query)
    },
    fabClick1(e) {
      this.jumpPage("./../gongdan/shangbao")
    },
    fabClick2(e) {
      console.log(e);
    },
    jumpPage(url, item) {
      let str = JSON.stringify(item)
      uni.navigateTo({
        url: url + "?row=" + encodeURIComponent(item)
      });
      return;
    },
  }
};
</script>

<style lang="less" scoped>
.workDesk {
  width: 100%;

  .noticeBoxMain {
    width: 665upx;
    margin: 0 10upx 0px 15upx;
    box-shadow: 0 1px 8px 0 rgb(0 0 0 / 12%);
    border-radius: 8px;
    height: 360upx;
    background: url("static/images/workDesk/deskTop.png") no-repeat;
    padding: 30upx;
    background-size: contain;
    justify-content: flex-start;

    img {
      width: 120upx;
      height: 120upx;
      display: block;
      border-radius: 50%;
      border: 2px solid #fff;
    }

    .noticeBoxInfos {
      flex: 1;
      height: 118upx;
      padding: 10upx 24upx;
      display: flex;
      flex-direction: column;
      justify-content: center;

      .title {
        font-size: 38upx;
        color: #000000;
        margin-top: 40upx;
        text {
          font-size: 28upx;
          font-weight: 600;
          margin-left: 12upx;
          color: #fff;
          font-weight: 400;
        }
      }

      .brief {
        font-size: 24upx;
        font-weight: 300;
        margin-top: 10upx;
      }

      .icons {
        position: absolute;
        top: 20upx;
        right: 40upx;
      }
    }
  }

  .menu {
    background-color: #ffffff;
    width: 720upx;
    border-radius: 8px;
    margin: 20upx 15upx;
    border: 1px solid #efefef;
    overflow: hidden;
    box-shadow: 2upx 2upx 6upx 2upx rgb(0 0 0 / 10%);
  }

  .grid-item-box {
    width: 180upx;
    height: 160upx;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }
}
</style>