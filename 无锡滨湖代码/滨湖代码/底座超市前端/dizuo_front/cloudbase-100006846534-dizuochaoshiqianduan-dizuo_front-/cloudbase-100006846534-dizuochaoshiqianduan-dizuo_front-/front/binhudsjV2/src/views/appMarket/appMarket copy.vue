<template>
  <!-- 应用市场 -->
  <div>
    <!-- 顶部大图 -->
    <div class="home-banner">
      <div class="search-wrap">
        <div class="search-title">应用市场</div>
        <div class="appmarket-banner-detail">
          <div class="banner-detail">
            应用超市汇聚了滨湖区各级政府部门优秀应用成果、服务商产品和数字政府建设公共基础能力，
            用户通过应用超市，进行应用产品搜索、详情查阅、应用推荐等操作。
          </div>
        </div>
        <el-input placeholder="请输入你要查找的内容" v-model="sBannerKeyWords" class="search-input">
          <el-button type="primary" slot="append" icon="el-icon-search" @click="fClickSearch()">搜索</el-button>
        </el-input>
        <!-- <div class="search-keyword">关键字：人口 建筑 企业</div> -->
      </div>
    </div>

    <!-- 全部应用 -->
    <div class="box _flex" style="background: #FFFFFF;">
      <com-block-t title="全部应用" position="center" more="allapp" @fClickMore="fClickMore" class="box-block">
        <div class="allapp-tab">
          <div class="allapp-tab-item" :class="sTabType === 'ysjapp' ? '_act' : ''" @click="fChangeAppTab('ysjapp')">已上架应用
          </div>
          <div class="allapp-tab-item" :class="sTabType === 'wsjapp' ? '_act' : ''" @click="fChangeAppTab('wsjapp')">待上架应用
          </div>
        </div>
        <!--  -->
        <div class="sjapp-con">
          <template v-if="aSjApp.length > 0">
            <div class="sjapp-con-item" v-for="(item, idx) in aSjApp" :key="'aSjApp' + idx"
              @click="sTabType === 'ysjapp' ? fGoToDetails(item) : fClick()">
              <img src="../../assets/image/comments/list_icon.png" class="con-item-image" />
              {{ item.title || item.name || item.appName }}
            </div>
          </template>
          <template v-else>
            <el-empty description="暂无数据"></el-empty>
          </template>
        </div>
      </com-block-t>
    </div>

    <!-- 应用分类 -->
    <div class="box _flex" id="vital1">
      <com-block-t title="应用分类" position="center" more="appclass" @fClickMore="fClickMore" class="box-block">
        <div class="yytj-top">
          <div class="yytj-top-item" v-for="(item, idx) in aYytjData" :key="'aYytjData' + idx"
            @click="fClickAppType(item)" :class="sAllAppType === item.name ? '_act' : ''">
            {{ item.name }}
          </div>
        </div>
        <div v-if="aCjyyData" class="yytj-item-wrap">
          <div class="yytj-item block _pad" v-for="(item, idx) in aCjyyData" :key="'aCjyyData' + idx">
            <!-- <div></div> -->
            <img class="yytj-item-image" src="../../assets/image/comments/list_icon.png" alt="">
            <div class="yytj-item-con">
              <div class="dyytj-item-title">{{ item.title }}</div>
              <div class="dyytj-item-detail text-ellipsis-2">{{ item.description }}</div>
              <div class="dyytj-item-box">
                <div class="item-box-unit">所属部门:{{ item.deptName }}</div>
                <div class="appmarket-more" @click.stop="fGoToDetails(item)">查看详情 ></div>
              </div>
            </div>
          </div>
        </div>
        <div v-else>
          <el-empty description="暂无数据"></el-empty>
        </div>
      </com-block-t>
    </div>

    <!-- 最新应用 -->
    <div class="box _flex ">
      <com-block-t title="最新应用" position="center" class="box-block ">
        <div class="zxtj-item-wrap">
          <div class="zxtj-item" v-for="(item, idx) in aZxtjData" :key="'aZxtjData' + idx">
            <!-- <img :src="item.logo.indexOf(',') >= 0 ? item.logo.split(',')[0] : item.logo"
                 alt="" @click.stop="fClickApp(item)"> -->
            <template v-if="item.appScreenshot && JSON.parse(item.appScreenshot).length>0 >= 0">
                <img :src="JSON.parse(item.appScreenshot)[0]" class="zxtj-item-image" alt="">
            </template>
            <div class="zxtj-item-con">
              <div class="dyytj-item-title">{{ item.title }}</div>
              <div class="dyytj-item-detail  text-ellipsis-2" :title="item.description || '--'">
                {{ item.description || '--' }}
              </div>
              <div class="dyytj-item-box">
                <!-- <div class="item-box-unit">应用名称：{{ item.title }}</div> -->
                <div class="appmarket-more" @click.stop="fGoToDetails(item)">查看详情 ></div>
              </div>
            </div>
          </div>
        </div>

      </com-block-t>
    </div>
  </div>
</template>

<script>
import ComBlockT from '@/components/page/comBlockT.vue'; // 块容器
import { URLS_USERINFO, URLS_SZDZ, URLS_APPMARKET } from '@/api/url.js';
import fGetAppListAsync from '@/api/setAppList.js'

export default {
  name: '',
  components: {
    ComBlockT,
    Image
  },
  props: [''],
  data() {
    return {
      sBannerKeyWords: '',
      sTabType: 'ysjapp',// 全部应用的类型
      // 上架应用
      aSjApp: [],
      sAllAppType: '数字政务',
      // 应用分类的类别
      aYytjData: [
        { name: '数字政务', icon: 'szzw', image: require('../../assets/image/home/appmarket/szzw_img.png') },
        { name: '数字经济', icon: 'szjji', image: require('../../assets/image/home/appmarket/szjj_img.png') },
        { name: '数字生活', icon: 'szsh', image: require('../../assets/image/home/appmarket/szsh_img.png') },
        { name: '数字生态', icon: 'szst', image: require('../../assets/image/home/appmarket/szst_img.png') },
        { name: '数字治理', icon: 'szzl', image: require('../../assets/image/home/appmarket/szzl_img.png') },
        { name: '数字基建', icon: 'szjj', image: require('../../assets/image/home/appmarket/szjj1_img.png') },
      ],
      aCjyyData: [],// 应用分类显示的数据
      aAllAppData: [],// 所有的应用含分类
      aZxtjData: [], // 最新推荐的数据
      aRecommendedApp: [],// 推荐应用
    }
  },
  computed: {},
  watch: {
    $route: {
      handler(newVal) {
        // console.log(newVal)
        if (newVal.query.id && newVal.query.id == 'vital1') {
          setTimeout(() => {
            this.$nextTick(() => {
              this.sAllAppType = newVal.query.type
              this.fSetAllAppData();
              this.fGoAnchor(newVal.query.id);
            })
          }, 100);
        }
      },
      deep: true,
      immediate: true,
    },
  },
  created() {

  },
  mounted() {
    // console.log(this.$route.query)
    this.fRequestAllpp(); // 请求所有应用
    // this.fChangeAppTab();
  },
  beforeDestroy() {

  },
  methods: {

    // 申请上架的列表
    fGetSqsjList(replayStatus) {
      let params = null
      let obj = null;
      if (replayStatus != null && replayStatus != undefined) {
        obj = JSON.stringify({ "appType": "", "status": 1, "params": { "startDate": "", "endDate": "" } })
      } else {
        params = { 'own': 1 }
        obj = JSON.stringify({ params: params })
      }
      $.ajax({
        url: `https://2.21.138.89:11511/cmApp/replayListAll?pageNum=${1}&pageSize=${99}`,
        method: 'post',
        headers: {
          'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
          'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
          // "Content-Type": "application/json"
        },
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: obj
      }).then(res => {
        // debugger
        console.log(res)
        // this.aSqData = res.data.records;
        // this.aSqTotal = res.data.total;

        this.aSjApp = res.data.records;
      })
    },
    // 根据申请人查询应用申请
    fRequestApplicationApplyForList() {
      this.$fRequest({
        url: URLS_USERINFO.u_applicationApplyForList,
        data: {
          applicant: JSON.parse(window.localStorage.getItem('userInfo')).account,
          // keyword: this.sMyAppKeyWord,
          current: 1,// 当前页
          size: 100,// 每页数量
        }
      }).then(res => {
        // console.log(res, '根据申请人查询应用申请')
        res.data.records.forEach(item => {
          item.title = item.name
        })
        this.aSjApp = res.data.records.filter(item => item.reviewStatus == 0).slice(0, 10);
      })
    },
    fGoAnchor(selector) {/*参数selector是id选择器（#anchor14）*/
      document.body.scrollTop = document.getElementById(selector).scrollIntoView()
    },
    async fRequestAllpp() {
      const res = await fGetAppListAsync();

      this.aAllAppData = res.filter(item => item.sTypeName == '应用');
      this.aZxtjData = this.fMsgSort(this.aAllAppData).splice(0, 3);

      let aData = res.filter(item => item.sTypeName == '组件');
      let aAllData = res
      this.$store.commit('fSetAllAppDataRes', res.filter(item => item.sTypeName == '应用'));
      this.$store.commit('fSetComUtilsData', [...res.filter(item => item.sTypeName == '组件'), ...res.filter(item => item.sTypeName == '工具')]);

      // console.log('>>>>>>>>>>>>',aData)

      this.aAllAppData.length > 15 ? this.aSjApp = this.aAllAppData.slice(0, 10) : this.aSjApp = this.aAllAppData;

      this.$store.commit('fSetAllAppData', aData);// 请求全部应用
      this.$store.commit('fSetAllApp', aData);// 请求全部应用

      // this.fRequestAppList(aAllData.length);// 请求所有应用 最新推荐
      this.fSetAllAppData();// 设置显示的数据
      this.fGetRecommendedApp(); // 获取推荐的应用
    },
    // 请求所有应用
    // fRequestAllpp() {
    //     this.$fRequest({
    //         url: URLS_USERINFO.u_applicationList,
    //     }).then(res => {
    //         // console.log(111, res)
    //         this.aAllAppData = res.data[2].children;
    //         let aData = []
    //         let aAllData = []
    //         this.$store.commit('fSetAllAppDataRes', res.data[2].children);
    //         this.$store.commit('fSetComUtilsData', [res.data[0], res.data[1]]);
    //         res.data.forEach(item => {
    //             aAllData.push(...this.fSetStoreAllApp(item))
    //         })

    //         let aComData = res.data.filter(item => item.title.indexOf('应用') >= 0)[0];// 获取所有组件
    //         aComData.children.forEach(item => {
    //             if (item.children && item.children.length > 0) {
    //                 aData.push(...item.children)
    //             }
    //         })

    //         aData.length > 15 ? this.aSjApp = aData.slice(0, 10) : this.aSjApp = aData;
    //         this.$store.commit('fSetAllAppData', aData);// 请求全部应用
    //         this.$store.commit('fSetAllApp', aData);// 请求全部应用
    //         this.fRequestAppList(aAllData.length);// 请求所有应用 最新推荐
    //         this.fSetAllAppData();// 设置显示的数据
    //         this.fGetRecommendedApp(); // 获取推荐的应用
    //     })
    // },
    // 请求所有应用 最新推荐
    fRequestAppList(len) {
      this.$fRequest({
        url: URLS_APPMARKET.u_applicationByClassificationAndDept,
        data: {
          applicationClassificationId: '',// 应用分类id
          deptId: '',// 应用单位id
          keywords: '',// 关键字
          current: 1,// 当前页
          size: len + 50,// 每页数量
        }
      }).then(res => {
        this.aZxtjData = [];
        this.aZxtjData = this.fMsgSort(res.data.records).splice(0, 3);
      })
    },
    // 获取推荐的应用
    fGetRecommendedApp() {
      // console.log(this.aAllAppData)
      let aData = []
      this.aAllAppData.forEach(item => {
        if (item.children && item.children.length > 0) {
          aData.push(...item.children)
        }
      })
      this.$store.commit('fSetAllAppData', aData)
      // this.aBannerData[0].value = aData.length;
      this.aRecommendedApp = aData.filter(item => item.isRecommend == '1')
    },
    // 设置所有组件应用工会的集合
    fSetStoreAllApp(data) {
      let aData = []
      data.children.forEach(item => {
        if (item.children && item.children.length > 0) {
          aData.push(...item.children)
        }
      })
      return aData;
    },
    // 选择app的类型
    fClickAppType(item) {
      this.sAllAppType = item.name;
      this.sImage = item.image
      this.fSetAllAppData();
    },
    // 设置显示的数据
    fSetAllAppData() {
      this.aCjyyData = this.aAllAppData.filter(item => item.sType == this.sAllAppType)

      if (this.aCjyyData && this.aCjyyData.length > 6) {
        this.aCjyyData = this.aCjyyData.slice(0, 6)
      }
      // console.log(this.aCjyyData, 111)
    },
    //传入一个需要排序的数组
    fMsgSort(obj) {
      obj.sort((a, b) => {
        let t1 = new Date(Date.parse(a.createTime.replace(/-/g, "/")))
        let t2 = new Date(Date.parse(b.createTime.replace(/-/g, "/")))
        return t2.getTime() - t1.getTime()
      })
      return obj
    },

    // 搜索框的点击按钮
    fClickSearch() {
      // console.log(this.sBannerKeyWords.trim());
      this.$router.push({
        path: "/appMarket/allapp",
        query: { type: 'gjNum', keywords: this.sBannerKeyWords.trim() }
      })
    },
    // 点击更多
    fClickMore(type) {
      // console.log(type, '更多')
      this.$router.push({
        path: "/appMarket/allapp",
        query: { type: 'gjNum' }
      })
    },
    // 上架应用的切换
    fChangeAppTab(type) {
      // debugger
      switch (type) {
        case 'ysjapp':
          this.$nextTick(() => {
            this.fRequestAllpp(); // 请求所有应用
          })

          break;
        case 'wsjapp':
          // this.aSjApp = [];
          this.fGetSqsjList(1)

          // this.fRequestApplicationApplyForList(); // 根据申请人查询应用申请
          break;
      }
      this.sTabType = type;
    },
    fClick() {

    },

    // 去详情页面
    fGoToDetails(item) {
      let oObj = this.$fSetData(item, ['appSecret'])
      this.$router.push({
        path: "/appMarket/appDetails",
        query: {data:JSON.stringify(oObj)}
      })
    },
  }
}
</script>
<style lang='less' scoped>
.home-banner {
  position: relative;
  width: 100%;
  height: 617px;
  // margin-bottom: 100px;
  background: url('../../assets/image/home/yyscbg.png') no-repeat center / 100% 617px;
}

.search-wrap {
  display: flex;
  align-items: center;
  // justify-content: center;
  flex-direction: column;
  padding-top: 102px;
  position: absolute;
  top: 0%;
  left: 0%;
  height: 100%;
  width: 100%;
  z-index: 999;
  font-weight: bold;
}

.search-title {
  color: #FFFFFF;
  font-size: 40px;
}

.search-keyword {
  color: #FFFFFF;
  font-size: 18px;
}

.search-input {
  // display: flex;
  width: 50%;
  height: 60px;
  font-size: 30px;
  margin: 30px 0 10px;
}

/deep/ .el-input__inner {
  height: 60px;
}

/deep/ .el-button {
  height: 60px;
  background-color: #26acf7 !important;
  color: #FFFFFF !important;
}

.banner-image {
  width: 100%;
  height: 617px;
  background: no-repeat center / 100% 617px;
}

.appmarket-banner {
  width: 100%;
  height: 617px;
  padding: 109px 0 0 100px;
  margin-bottom: 50px;
  margin-top: -65px;
  background: url('../../assets/image/appmarket/banner.png') no-repeat center 100%;
}

.appmarket-banner-title {
  margin-bottom: 28px;
  color: #000000;
  font-weight: 500;
  font-size: 60px;
}

.appmarket-banner-detail {
  font-size: 18px;
  color: #000000;
}

.banner-detail {
  width: 70%;
  margin: 0 auto 0;
  color: #FFFFFF;
  font-size: 20px;
  font-weight: 500;
  text-indent: 2rem;
}

.appmarket-banner-value {
  // display: flex;
  width: 620px;
}

.banner-value-item {
  display: flex;
  align-items: end;
  margin-right: 80px;
  line-height: 50px;
  font-size: 50px;
  font-weight: 600;
  color: #266FE8;

  & div {
    overflow: hidden !important;
  }
}

.value-item-unit {
  margin: 0 14px 0 7px;
  line-height: 22px;
  color: #000000;
  font-size: 22px;
}

.value-item-name {
  line-height: 35px;
  color: #000000;
  font-size: 35px;
}

.box-block {
  width: 100%;
  margin-bottom: 50px;
}

.allapp-tab {
  display: flex;
  width: 544px;
  height: 58px;
  margin: 0 auto 50px;
  border-radius: 29px;
  overflow: hidden;
  background-color: #ecf0f9;
  color: #5C5E66;
  font-size: 18px;
  text-align: center;
  line-height: 58px;
}

.allapp-tab-item {
  width: 50%;
  border-radius: 29px;
  cursor: pointer;
  user-select: none;

  &:hover {
    opacity: .75;
  }

  &._act {
    color: #FFFFFF;
    background: linear-gradient(140deg, #5CA4FF 0%, #2F6BFF 100%, #2F6BFF 100%);
  }
}

.sjapp-con {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  // justify-content: space-around;
}

.sjapp-con-item {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-around;
  width: 19%;
  height: 300px;
  margin-bottom: 20px;
  margin-right: 15px;
  font-size: 18px;
  color: #1A1B1F;
  cursor: pointer;
  user-select: none;
}

.con-item-image {
  width: 150px;
  height: 150px;
}

.yytj-top {
  display: flex;
  align-items: center;
  // justify-content: center;
  width: 816px;
  height: 58px;
  margin: 0 auto 34px;
  background-color: #FFFFFF;
  border-radius: 29px;
  overflow: hidden;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.15);
}

.yytj-top-item {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 136px;
  height: 58px;
  font-size: 18px;
  // font-weight: bold;
  color: #5C5E66;
  border-radius: 29px;
  cursor: pointer;
  user-select: none;

  &:hover {
    opacity: .75;
  }

  &._act {
    color: #FFFFFF;
    background: linear-gradient(140deg, #5CA4FF 0%, #2F6BFF 100%, #2F6BFF 100%);
  }

}

.yytj-item-wrap {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
}

.yytj-item {
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 812px;
  height: 206px;
  padding: 0 20px;
  margin-bottom: 40px;

  &._pad {
    display: flex;
    justify-content: space-between;
    padding: 15px 30px 0 45px;
  }
}

.yytj-item-image {
  width: 114px;
  height: 114px;
  background: url('../../assets/image/appmarket/image.png') no-repeat center / 114px;
}

.yytj-item-con {
  width: 600px;
}

.dyytj-item-title {
  font-size: 26px;
  font-weight: 500;
  color: #000000;
}

.dyytj-item-detail {
  margin: 15px 0;
  height: 60px;
  font-size: 20px;
  font-weight: 300;
  text-indent: 2rem;
  color: #000000;
}

.dyytj-item-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
}

.item-box-unit {
  color: #000000;
}

.appmarket-title {
  margin: 20px 0 7px;
  text-align: center;
  font-size: 36px;
  font-weight: 500;
  color: rgba(0, 0, 0, 0.9);

  &._mar {
    margin-top: 45px;
  }

}

.appmarket-more {
  font-size: 14px;
  color: #266FE8;
  cursor: pointer;
  user-select: none;

  &:hover {
    opacity: .75;
  }

  &._cen {
    // margin-bottom: 50px;
    text-align: center;
  }
}

.zxtj-item-wrap {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.zxtj-item {
  width: 536px;
  height: auto;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
}

.zxtj-item-image {
  width: 100%;
  height: 350px;
  // background: #f7f7f7 url('../../assets/image/appmarket/image.png') no-repeat center / 114px;
  // background: url('../../assets/image/appmarket/listicon.png') no-repeat center;
  background-size: cover;
}

.zxtj-item-con {
  width: 100%;
  height: 200px;
  padding: 20px 20px 0;
}

/deep/ .el-empty {
  margin: 0 auto
}
</style>