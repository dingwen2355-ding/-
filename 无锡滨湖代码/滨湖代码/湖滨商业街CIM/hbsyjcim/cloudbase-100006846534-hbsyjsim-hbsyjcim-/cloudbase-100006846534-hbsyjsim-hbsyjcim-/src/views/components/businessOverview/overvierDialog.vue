<template>
  <div v-if="visible" class="activity-dialog">
    <i class="close" @click="visibleDialog"></i>
    <div class="tab-box">
      <p class="title-01" :class="{ 'active': showTab == 1 }" @click="showTab = 1">基本信息</p>
      <p class="title-01" :class="{ 'active': showTab == 2 }" @click="showTab = 2">股东信息</p>
      <p class="title-01" :class="{ 'active': showTab == 3 }" @click="showTab = 3">税务信息</p>
      <p class="title-01" :class="{ 'active': showTab == 4 }" @click="showTab = 4">参保信息</p>
      <p class="title-01" :class="{ 'active': showTab == 5 }" @click="showTab = 5">生产经营信息</p>
    </div>

    <div class="box-1" v-show="showTab == 1">
      <div class="left-content">
        <div class="head-box">
          <!-- <img class="img-1" src="@/assets/business-overview/img_cleaner_sample.png" alt=""> -->
          <div class="head-detail">
            <div class="name-box">
              <div class="name" :class="{ 'animate': showData.companyName && showData.companyName.length > 18 }"
                id="name">{{
                  showData.companyName ? showData.companyName : '-'
                }}
              </div>
            </div>
            <div class="type-box">
              <div class="type">{{ showData.industry ? showData.industry : '-' }}</div>
              <div class="flag" v-show="showData.tagList.length > 0" v-for="(item, i) in showData.tagList ">{{ item }}
              </div>
            </div>
            <div class="address-box">
              <img class="img-2" src="@/assets/business-overview/icon_adds.png" alt="">
              <div class="address">{{ showData.address ? showData.address : '-' }}</div>
            </div>
          </div>
        </div>
        <div class="title-02">资产信息</div>
        <dl>
          <dd>
            <div>
              <label>信用代码：</label>
              <p>{{ showData.code ? showData.code : '-' }}</p>
            </div>
          </dd>
          <dd>
            <div>
              <label>资产类型：</label>
              <p>{{ showData.assetType ? showData.assetType : '-' }}</p>
            </div>
            <div>
              <label class="label-1">起租时间：</label>
              <p>{{ showData.rentStartTime ? showData.rentStartTime : '-' }}</p>
            </div>
          </dd>
          <dd>
            <div>
              <label>到期时间：</label>
              <p>{{ showData.rentEndTime ? showData.rentEndTime : '-' }}</p>
            </div>
            <div>
              <label class="label-2">剩余租期：</label>
              <p>{{ showData.rentTime ? showData.rentTime : '-' }}</p>
            </div>
          </dd>
          <dd>
            <div>
              <label>合同面积：</label>
              <p>{{ showData.area ? showData.area : '-' }}</p>
            </div>
          </dd>
        </dl>
        <div class="title-02">营业信息</div>
        <dl>
          <dd>
            <div>
              <label>法人姓名：</label>
              <p class="legalPerson">{{ showData.legalPerson ? showData.legalPerson : '-' }}</p>
            </div>
            <div>
              <label class="label-3">联系电话：</label>
              <p>{{ showData.phone ? showData.phone : '-' }}</p>
            </div>
          </dd>
          <dd>
            <div>
              <label>开业时间：</label>
              <p>{{ showData.createdate ? showData.createdate : '-' }}</p>
            </div>
            <div>
              <label class="label-4">在职人数：</label>
              <p>{{ showData.peopleNum ? showData.peopleNum : '-' }}</p>
            </div>
          </dd>
        </dl>
        <div class="title-02">服务信息</div>
        <dl>
          <dd>
            <div>
              <label>服务时间：</label>
              <p class="serve-p">{{ showData.serviceTime ? showData.serviceTime : '-' }}</p>
            </div>
          </dd>
          <dd>
            <div>
              <label>服务电话：</label>
              <p class="serve-p">{{ showData.servicePhone ? showData.servicePhone : '-' }}</p>
            </div>
          </dd>
        </dl>
        <!-- <div class="work-time"></div> -->
      </div>
      <div class="right-content ">
        <div class="title-03">营业范围</div>
        <!-- <div class=""> -->
        <div class="text scroll-container">
          {{ showData.businessScope ? showData.businessScope : '暂无数据' }}
        </div>
        <!-- </div> -->
        <div class="title-02">能耗概览</div>
        <div class="nenghao">
          <div class="nh-item">
            <div class="nh-type">当月用水量</div>
            <div class="nh-value">{{ showData.useWater ? showData.useWater : '-' }}<span>m³</span></div>
          </div>
          <div class="nh-item">
            <div class="nh-type">当月用电量</div>
            <div class="nh-value">{{ showData.useElec ? showData.useElec : '-' }} <span>kwh</span></div>
          </div>
          <div class="nh-item">
            <div class="nh-type">煤气用量</div>
            <div class="nh-value">{{ showData.useGas ? showData.useGas : '-' }} <span>m³</span></div>
          </div>
        </div>

        <div class="title-02">营业执照</div>
        <!-- <img class="img-3" v-show="showData.lincense" :src="showData.lincense" alt=""> -->
        <div class="img-3" @click="showImgBig = true">
          <pdf ref="pdf" :id="'pdf1'" v-show="showData.lincense" :src="showData.lincense">
          </pdf>
        </div>
      </div>
    </div>
    <div class="box-2" v-show="showTab == 2">
      <div class="box-2-head">
        <div class="name">{{ showData.companyName ? showData.companyName : '-' }}</div>
        <div class="flag-box">
          <div class="flag1" v-show="gdInfo.flag1">{{ gdInfo.flag1 }}</div>
          <div class="flag2" v-show="gdInfo.flag2">{{ gdInfo.flag2 }}</div>
        </div>
      </div>
      <div class="title-02">基本信息</div>
      <div class="box-2-center">
        <dl class="box-2-list">
          <dd>
            <div>
              <label>信用代码：</label>
              <p>{{ gdInfo.baseInfo.code ? gdInfo.baseInfo.code : '-' }}</p>
            </div>
          </dd>
          <dd>
            <div>
              <label>所属业态：</label>
              <p>{{ gdInfo.baseInfo.hangye ? gdInfo.baseInfo.hangye : '-' }}</p>
            </div>
          </dd>
          <dd>
            <div>
              <label>注册资本：</label>
              <p>{{ gdInfo.baseInfo.ziben ? gdInfo.baseInfo.ziben : '-' }}万人民币</p>
            </div>

          </dd>
          <dd>
            <div>
              <label>成立时间：</label>
              <p>{{ gdInfo.baseInfo.time ? gdInfo.baseInfo.time : '-' }}</p>
            </div>
          </dd>
          <dd>
            <div>
              <label>法定代表人：</label>
              <p class="daibiaoren">{{ gdInfo.baseInfo.daibiaoren ? gdInfo.baseInfo.daibiaoren : '-' }}</p>
            </div>

          </dd>
          <dd>
            <div>
              <label class="box-2-address">地址：</label>
              <p class="box-2-address-val">{{ gdInfo.baseInfo.address ? gdInfo.baseInfo.address : '-' }}</p>
            </div>
          </dd>
        </dl>
      </div>
      <div class="title-02">股东合伙人</div>
      <ul class="list">
        <li>
          <div>
            <span>序号</span>
          </div>
          <div>
            <span>股东姓名</span>
          </div>
          <div>
            <span>持股比例</span>
          </div>
          <div>
            <span>认缴出资额</span>
          </div>
          <div>
            <span>认缴日期</span>
          </div>
          <div>
            <span>实缴认缴出资额</span>
          </div>
          <div>
            <span>实缴出资日期</span>
          </div>
        </li>
      </ul>
      <ul class="content scroll-container">
        <div class="tip" v-show="gdInfo.list == 0 || gdInfo.list.length == 0">暂无数据</div>
        <li v-show="gdInfo.list.length > 0" v-for="(item, i) in gdInfo.list" :key="i">
          <div>
            <span>{{ i + 1 }}</span>
          </div>
          <div>
            <span>{{ item.name ? item.name : '-' }}</span>
          </div>
          <div>
            <span>{{ item.persent ? item.persent + '%' : '-' }}</span>
          </div>
          <div>
            <span>{{ changeStar(item.money) }}</span>
          </div>
          <div>
            <span>{{ item.paydate ? item.paydate : '-' }}</span>
          </div>
          <div>
            <span>{{ changeStar(item.realitymoney) }}</span>
          </div>
          <div>
            <span>{{ item.realitypaydate ? item.realitypaydate : '-' }}</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="box-3" v-show="showTab == 3">
      <div class="title-02">税务信息</div>
      <dl class="box-3-list">
        <dd>
          <div>
            <label>缴纳名称：</label>
            <p>{{ swInfo.name ? swInfo.name : '-' }}</p>
          </div>
        </dd>
        <dd>
          <div>
            <label>状态：</label>
            <p>{{ swInfo.status ? swInfo.status : '-' }}</p>
          </div>
        </dd>
        <dd>
          <div>
            <label>纳税识别号：</label>
            <p class="shuiwuId">{{ swInfo.id ? swInfo.id : '-'
            }}</p>
          </div>

        </dd>
        <dd>
          <div>
            <label>登记机关：</label>
            <p>{{ swInfo.organId ? swInfo.organId : '-' }}</p>
          </div>
        </dd>
        <dd>
          <div>
            <label>登记日期：</label>
            <p>{{ swInfo.startDate ? swInfo.startDate : '-' }}</p>
          </div>

        </dd>
        <dd>
          <div>
            <label>税务注销日期：</label>
            <p>{{ swInfo.endDate ? swInfo.endDate : '-' }}</p>
          </div>
        </dd>
      </dl>
      <div class="title-02">税务缴纳记录</div>
      <ul class="list">
        <li>
          <div>
            <span>序号</span>
          </div>
          <div>
            <span>缴纳年月</span>
          </div>
          <div>
            <span>缴纳依据总额</span>
          </div>
          <div>
            <span>实缴总额</span>
          </div>
        </li>
      </ul>
      <ul class="content scroll-container">
        <div class="tip" v-show="!swInfo.list1 || swInfo.list1.length == 0">暂无数据</div>
        <li v-show="swInfo.list1.length > 0" v-for="(item, i) in swInfo.list1" :key="i">
          <div>
            <span>{{ i + 1 }}</span>
          </div>
          <div>
            <span>{{ item.date ? item.date : '-' }}</span>
          </div>
          <div>
            <span>{{ changeStar(item.persent) }}</span>
          </div>
          <div>
            <span>{{ changeStar(item.realAmount) }}</span>
          </div>
        </li>
      </ul>
      <div class="title-02">税务申缴记录</div>
      <ul class="list">
        <li>
          <div>
            <span>序号</span>
          </div>
          <div>
            <span>年份</span>
          </div>
          <div>
            <span>申缴次数</span>
          </div>
          <div>
            <span>申报总额(元)</span>
          </div>
        </li>
      </ul>
      <ul class="content2 scroll-container">
        <div class="tip" v-show="!swInfo.list2 || swInfo.list2.length == 0">暂无数据</div>
        <li v-show="swInfo.list2.length > 0" v-for="(item, i) in swInfo.list2" :key="i">
          <div>
            <span>{{ i + 1 }}</span>
          </div>
          <div>
            <span>{{ item.date ? item.data : '-' }}</span>
          </div>
          <div>
            <span>{{ item.num ? item.num : '-' }}</span>
          </div>
          <div>
            <span>{{ changeStar(item.total) }}</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="box-4" v-show="showTab == 4">
      <div class="title-02">公积金信息</div>
      <dl class="box-4-list">
        <dd>
          <div>
            <label>机构全称：</label>
            <p class="cbName">{{ cbInfo.name ? cbInfo.name : '-' }}</p>
          </div>
        </dd>
        <dd>
          <div>
            <label>统一社会信用代码：</label>
            <p>{{ cbInfo.code ? cbInfo.code : '-' }}</p>
          </div>
        </dd>
        <dd class="row-2">
          <div>
            <label>单位账号：</label>
            <p>{{ cbInfo.account ? cbInfo.account : '-' }}</p>
          </div>
        </dd>
        <dd class="row-2">
          <div>
            <label>缴存比例：</label>
            <p>{{ cbInfo.per ? cbInfo.per : '-' }}</p>
          </div>
        </dd>
        <dd class="row-2">
          <div>
            <label>缴存人数：</label>
            <p>{{ cbInfo.peopleNum ? cbInfo.peopleNum : '-' }}</p>
          </div>
        </dd>
      </dl>
      <ul class="list">
        <li>
          <div>
            <span>序号</span>
          </div>
          <div>
            <span>年份</span>
          </div>
          <div>
            <span>发生笔数</span>
          </div>
          <div>
            <span>当年缴纳总额</span>
          </div>
        </li>
      </ul>
      <ul class="content scroll-container">
        <div class="tip" v-show="cbInfo.list1.length == 0">暂无数据</div>
        <li v-show="cbInfo.list1.length > 0" v-for="(item, i) in cbInfo.list1" :key="i">
          <div>
            <span>{{ i + 1 }}</span>
          </div>
          <div>
            <span>{{ item.date ? item.date : '-' }}</span>
          </div>
          <div>
            <span>{{ item.num ? item.num : '-' }}</span>
          </div>
          <div>
            <span>{{ changeStar(item.total) }}</span>
          </div>
        </li>
      </ul>
      <div class="title-02">社会保险参保信息</div>
      <ul class="list">
        <li>
          <div>
            <span>序号</span>
          </div>
          <div>
            <span>年份</span>
          </div>
          <div>
            <span>发生笔数</span>
          </div>
          <div>
            <span>实缴总额</span>
          </div>
        </li>
      </ul>
      <ul class="content2 scroll-container">
        <div class="tip" v-show="cbInfo.list2.length == 0">暂无数据</div>
        <li v-show="cbInfo.list2.length > 0" v-for="(item, i) in cbInfo.list2" :key="i">
          <div>
            <span>{{ i + 1 }}</span>
          </div>
          <div>
            <span>{{ item.date ? item.date : '-' }}</span>
          </div>
          <div>
            <span>{{ item.num ? item.num : '-' }}</span>
          </div>
          <div>
            <span>{{ changeStar(item.total) }}</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="box-5" v-show="showTab == 5">
      <div class="title-02">企业年报申报信息</div>
      <div class="list-box">
        <ul class="list">
          <li>
            <div>
              <span>序号</span>
            </div>
            <div>
              <span>年度</span>
            </div>
            <div>
              <span>从业人数</span>
            </div>
            <div>
              <span>年产值(万元)</span>
            </div>
            <div>
              <span>年缴纳总额(万元)</span>
            </div>
            <div>
              <span>申报日期</span>
            </div>
          </li>
        </ul>
        <ul class="content scroll-container">
          <div class="tip" v-show="!scInfo.list1 || scInfo.list1.length == 0">暂无数据</div>
          <li v-show="scInfo.list1.length > 0" v-for="(item, i) in scInfo.list1" :key="i">
            <div>
              <span>{{ i + 1 }}</span>
            </div>
            <div>
              <span>{{ item.date ? item.date : '-' }}</span>
            </div>
            <div>
              <span>{{ item.peopleNum ? item.peopleNum : '-' }}</span>
            </div>
            <div>
              <span>{{ changeStar(item.output) }}</span>
            </div>
            <div>
              <span>{{ changeStar(item.total) }}</span>
            </div>
            <div>
              <span>{{ item.startDate ? item.startDate : '-' }}</span>
            </div>
          </li>
        </ul>
      </div>

      <div class="title-02">用水总量</div>
      <div class="list-2-box">
        <ul class="list2">
          <li>
            <div>
              <span>序号</span>
            </div>
            <div>
              <span>年度</span>
            </div>
            <div>
              <span>用水量(吨)</span>
            </div>
            <div>
              <span>更新时间</span>
            </div>
          </li>
        </ul>
        <ul class="content2 scroll-container">
          <div class="tip" v-show="!scInfo.list2 || scInfo.list2.length == 0">暂无数据</div>
          <li v-show="scInfo.list2.length > 0" v-for="(item, i) in scInfo.list2" :key="i">
            <div>
              <span>{{ i + 1 }}</span>
            </div>
            <div>
              <span>{{ item.date ? item.date : '-' }}</span>
            </div>
            <div>
              <span>{{ item.num ? item.num : '-' }}</span>
            </div>
            <div>
              <span>{{ item.time ? item.time : '-' }}</span>
            </div>

          </li>
        </ul>
      </div>

    </div>
    <div class="img-3-big" v-show="showImgBig" @click="showImgBig = false">
      <pdf ref="pdf" :id="'pdf1'" v-show="showData.lincense" :src="showData.lincense">
      </pdf>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'
import pdf from 'vue-pdf'
export default {
  name: 'overvierDialog',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    code: {
      type: String,
      default: ""
    },
  },
  data() {
    return {
      showImgBig: false,//显示营业执照 放大版
      list: [],//商户基本数据
      showData: {
        // companyName: "测试",
        // type: "零售",
        // flag1: "拥军商户"
      },//商户基本信息
      showTab: 1,//弹窗标签页
      gdInfo: {
        flag1: "",
        flag2: "",
        baseInfo: {
          code: "",//信用代码
          hangye: "",//行业
          ziben: "",//注册资本
          time: "",//成立时间
          daibiaoren: "",//法定代表人
          address: "",
        },
        list: [
          // {
          //   name: '张建高',
          //   per: '70%',
          //   amount: 70.00,
          //   date: '2017-11-09',
          //   realAmount: .00,
          //   realDate: ''
          // },
        ]
      },//股东信息
      swInfo: {
        // name: "无锡芯研微电子有限公司",
        // status: "正常",
        // id: "430511197809010365",
        // organId: '18414564083',
        // startDate: "2020-05-14",
        // endDate: "暂无数据",
        // list1: [
        //   {
        //     date: "2023年06月",
        //     persent: 100.00,
        //     realAmount: 100.00
        //   },
        //   {
        //     date: "2023年05月",
        //     persent: 100.00,
        //     realAmount: 100.00
        //   },
        //   {
        //     date: "2023年04月",
        //     persent: 100.00,
        //     realAmount: 100.00
        //   },
        //   {
        //     date: "2023年03月",
        //     persent: 100.00,
        //     realAmount: 100.00
        //   },
        //   {
        //     date: "2023年01月",
        //     persent: 100.00,
        //     realAmount: 100.00
        //   },
        // ],
        // list2: [
        //   {
        //     date: "2023年",
        //     num: 5,
        //     total: 100.00
        //   },
        //   {
        //     date: "2022年",
        //     num: 12,
        //     total: 100.00
        //   },
        //   {
        //     date: "2021年",
        //     num: 12,
        //     total: 100.00
        //   },
        //   {
        //     date: "2020年",
        //     num: 12,
        //     total: 100.00
        //   },
        //   {
        //     date: "2019年",
        //     num: 12,
        //     total: 100.00
        //   },
        // ]
      },//税务信息
      cbInfo: {
        name: "",
        code: "",
        account: "",
        per: "",
        peopleNum: "",
        list1: [
          // {
          //   date: "2023年",
          //   num: 7,
          //   total: 100.00
          // },

        ],
        list2: [
          // {
          //   date: "2023",
          //   num: 5,
          //   total: 100.00
          // },
        ]
      },//参保信息
      scInfo: {
        // list1: [
        //   {
        //     date: "2022年",
        //     peopleNum: "9(女性7)",
        //     output: 100.00,
        //     total: 100.00,
        //     startDate: "2023-05-30"
        //   },
        //   {
        //     date: "2021年",
        //     peopleNum: "9(女性7)",
        //     output: 100.00,
        //     total: 100.00,
        //     startDate: "2022-05-31"
        //   },
        //   {
        //     date: "2020年",
        //     peopleNum: "6(女性6)",
        //     output: 100.00,
        //     total: 100.00,
        //     startDate: "2021-05-14"
        //   },
        //   {
        //     date: "2019年",
        //     peopleNum: "7(女性3)",
        //     output: 100.00,
        //     total: 100.00,
        //     startDate: "2020-03-19"
        //   },
        //   {
        //     date: "2018年",
        //     peopleNum: "5(女性3)",
        //     output: 100.00,
        //     total: 100.00,
        //     startDate: "2019-04-13"
        //   },
        //   {
        //     date: "2017年",
        //     peopleNum: "2(女性0)",
        //     output: 100.00,
        //     total: 100.00,
        //     startDate: "2018-05-24"
        //   },
        // ],
        // list2: [
        //   // {
        //   //   date: "ss",
        //   //   num: 2,
        //   //   time: "ss"
        //   // }
        // ]
      },//生产信息
    }
  },
  components: {
    pdf,
  },
  computed: {
    codeData() {
      return this.code
    }
  },
  watch: {
    async codeData(id) {
      this.showTab = 1
      // this.showData = this.list.filter(item => item.code == val)[0]
      if (id) {
        await this.getBaseInfo(id)
        await this.getGdInfo(id)
        await this.getSwInfo(id)
        await this.getCbInfo(id)
        await this.getScInfo(id)
      } else {
        console.log("商户id为空，关闭弹窗");
        this.$eventBus.$emit('closeOverDialog');
      }

    },
  },
  mounted() {
    this.showTab = 1
  },
  methods: {
    //字符串改成星号-
    changeStar(val) {
      if (val) {
        let str = val.toString()
        return str.replace(/./g, "****").slice(0, 4)
      }
      return "****"
    },
    // 获取基本信息数据
    async getBaseInfo(id) {
      await request({
        url: "/api/binhu/getCompanyBaseInfo",
        method: "post",
        data: {
          qyid: id,
        }
      }).then(res => {
        // console.log(res.data.lincense);
        if (!res.data || res.code != 200) {
          console.log("商户弹窗-基本信息无数据或请求失败");
          this.$eventBus.$emit('closeOverDialog');
          return
        }
        this.showData = {
          qyid: res.data.qyid ? res.data.qyid : '-',
          companyName: res.data.companyName ? res.data.companyName : '-',
          industry: res.data.industry ? res.data.industry : '-',
          tagList: res.data.tagList ? res.data.tagList : '-',
          address: res.data.address ? res.data.address : '-',
          code: res.data.code ? res.data.code : '-',
          assetType: res.data.rentType ? res.data.rentType : '-',//资产类型
          rentStartTime: res.data.rentStartTime ? res.data.rentStartTime : '-',
          rentEndTime: res.data.rentEndTime ? res.data.rentEndTime : '-',
          rentTime: res.data.residueTime ? res.data.residueTime + '月' : '-',//剩余租期
          area: res.data.area ? res.data.area + 'm²' : '-',
          legalPerson: res.data.legalPerson ? res.data.legalPerson : '-',
          phone: res.data.phone ? res.data.phone : '-',
          createdate: res.data.createdate.slice(0, -8) ? res.data.createdate.slice(0, -8) : '-',
          peopleNum: res.data.employeeCount,//在职人数
          serviceTime: null,//服务时间
          servicePhone: null,//服务电话
          introduction: res.data.introduction ? res.data.introduction : '-',
          businessScope: res.data.businessScope ? res.data.businessScope : '-',
          useWater: res.data.useWater ? res.data.useWater : '-',
          useElec: res.data.useElec ? res.data.useElec : '-',
          useGas: res.data.useGas ? res.data.useGas : '-',
          // "data:image/png;base64," + 
          // lincense: res.data.lincense,
          lincense: this.toUrl(res.data.lincense) ? this.toUrl(res.data.lincense) : '-',
          status: res.data.status ? res.data.status : '-'
        }
      })

    },
    // 获取股东信息
    async getGdInfo(id) {
      let baseInfo
      await request({
        url: "/api/binhu/getShareholder",
        method: "post",
        data: {
          qyid: id
        }
      }).then(res => {
        if (!res.data || res.code != 200) {
          console.log("商户弹窗-股东信息无数据或请求失败");
          return
        }
        // console.log(res.data.baseInfo);
        baseInfo = {
          code: res.data.baseInfo.code ? res.data.baseInfo.code : '-',
          hangye: res.data.baseInfo.industry ? res.data.baseInfo.industry : '-',
          ziben: res.data.baseInfo.capital ? res.data.baseInfo.capital : '-',
          time: res.data.baseInfo.createdate.slice(0, -8) ? res.data.baseInfo.createdate.slice(0, -8) : '-',
          daibiaoren: res.data.baseInfo.legalPerson ? res.data.baseInfo.legalPerson : '-',
          address: this.showData.address ? this.showData.address : '-'
        }
        this.gdInfo = {
          flag1: null,
          flag2: this.showData.status ? this.showData.status : '-',
          baseInfo: baseInfo ? baseInfo : '-',
          list: res.data.shareholder ? res.data.shareholder : '-'
        }
      })
    },
    // 获取税务信息
    async getSwInfo(id) {
      await request({
        url: "/api/binhu/getTaxLog",
        method: "post",
        data: {
          qyid: id,
          pageNo: 1,
          pageSize: 10
        }
      }).then(res => {
        if (!res.data || res.code != 200) {
          console.log("商户弹窗-税务信息无数据或请求失败");
          this.swInfo = {
            name: '-',
            status: '-',
            id: '-',
            organId: '-',
            startDate: '-',//登记日期
            endDate: '-',//税务注销日期
            list1: [],
            list2: []
          }
          return
        }
        this.swInfo = {
          name: res.data.baseInfo.companyName,
          status: res.data.baseInfo.status,
          id: res.data.baseInfo.code,
          organId: res.data.baseInfo.regOrg,
          startDate: null,//登记日期
          endDate: null,//税务注销日期
          list1: res.data.taxLog.map((d) => {
            let data = {
              date: d.taxDate.slice(0, -8),
              realAmount: d.money,
              persent: d.persent
            }
            return data
          }),
          list2: []
        }
      })
    },
    // 获取参保信息
    async getCbInfo(id) {
      let data1
      let data2
      let CBbaseInfo
      await request({
        url: "/api/binhu/getGjjList",
        method: "post",
        data: {
          qyid: id,
          pageNo: 1,
          pageSize: 10
        }
      }).then(res => {
        if (!res.data || res.code != 200) {
          console.log("商户弹窗-参保信息无数据或请求失败");
          return
        }
        data1 = res.data
      })
      await request({
        url: "/api/binhu/getSbList",
        method: "post",
        data: {
          qyid: id,
          pageNo: 1,
          pageSize: 10
        }
      }).then(res => {
        if (!res.data || res.code != 200) {
          console.log("商户弹窗-参保信息无数据或请求失败");
          return
        }
        data2 = res.data
      })
      await request({
        url: "/api/binhu/getGjjBaseInfo",
        method: "post",
        data: {
          qyid: id,
        }
      }).then(res => {
        if (!res.data || res.code != 200) {
          console.log("商户弹窗-参保信息无数据或请求失败");
          CBbaseInfo = {
            name: null,
            code: null,
            account: null,
            per: null,
            peopleNum: null
          }
          return
        }
        CBbaseInfo = res.data
      })

      this.cbInfo = {
        name: CBbaseInfo.name ? CBbaseInfo.name : '-',
        code: CBbaseInfo.code ? CBbaseInfo.code : '-',
        acount: CBbaseInfo.account ? CBbaseInfo.account : '-',
        per: CBbaseInfo.persent ? CBbaseInfo.persent : '-',
        peopleNum: CBbaseInfo.personcount ? CBbaseInfo.personcount : '-',
        list1: data1.map(d => {
          let data = {
            date: d.year,
            num: d.count,
            total: d.total
          }
          return data
        }),
        list2: data2.map(d => {
          let data = {
            date: d.year,
            num: d.count,
            total: d.total
          }
          return data
        })
      }
    },
    // 获取生产经营信息
    async getScInfo(id) {
      let data1
      let data2
      // 企业年报信息
      await request({
        url: "/api/binhu/getYearReport",
        method: "post",
        data: {
          qyid: id,
          pageNo: 1,
          pageSize: 5
        }
      }).then(res => {
        if (!res.data || res.code != 200) {
          console.log("商户弹窗-生产经营-企业年报无数据或请求失败");
          data1 = []
          return
        }
        data1 = res.data
      })
      // 用水总量
      await request({
        url: "/api/binhu/getUseWaterLog",
        method: 'post',
        data: {
          qyid: id,
          pageNo: 1,
          pageSize: 10
        }
      }).then(res => {
        if (!res.data || res.code != 200) {
          console.log("商户弹窗-生产经营-用水总量无数据或请求失败");
          data2 = []
          return
        }
        data2 = res.data
      })

      this.scInfo = {
        list1: data1.map((d) => {
          let d1 = {
            date: d.year + '年',
            peopleNum: d.employeeCount,
            output: d.money,
            total: d.taxMoney,
            startDate: d.applyDate.slice(0, -8)
          }
          return d1
        }),
        list2: data2.map((d) => {
          let d2 = {
            date: d.yearMonth,
            num: d.waterCount,
            time: d.datatime
          }
          return d2
        })
      }

    },
    toUrl(base64) {
      const blob = this.base64ToBlob(`data:application/pdf;base64,${base64}`);
      const file = this.blobToFile(blob, 'judianPig');
      let fileURL = URL.createObjectURL(file)
      return fileURL
    },

    base64ToBlob(base64Data) {
      let arr = base64Data.split(','),
        fileType = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        l = bstr.length,
        u8Arr = new Uint8Array(l);

      while (l--) {
        u8Arr[l] = bstr.charCodeAt(l);
      }
      return new Blob([u8Arr], {
        type: fileType
      });
    },
    blobToFile(newBlob, fileName) {
      newBlob.lastModifiedDate = new Date();
      newBlob.name = fileName;
      return newBlob;
    },
    visibleDialog() {
      this.showImgBig = false
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.img-3-big {
  position: absolute;
  top: 57%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 807px;
  height: 580px;
  overflow: auto;

  ::v-deep span {
    .annotationLayer {
      cursor: zoom-out;
    }
  }
}

.zz-img {
  position: absolute;
  top: 5%;
  left: 50%;
  transform: translateX(-50%);
  // background-image: url("@/assets/party-building/img_window_800_bg.png");
  background-size: 100% 100%;
  width: 800px;
  height: 630px;
  overflow: auto;
}

.tip {
  width: 753px;
  text-align: center;
  margin-top: 15px;
  font-size: 18px;
}

.active {
  // width: 100px;
  height: 30px;
  background-image: url('@/assets/business-overview/img_tab_bg_left_act.png');
  background-size: 100% 100%;
  color: #00D5FF;
}

.animate {
  /* 创建滚动动画，5秒循环滚动 */
  animation: scroll 5s linear infinite;
}

@keyframes scroll {
  0% {
    transform: translateX(0%);
    /* 初始位置在容器右侧 */
  }

  100% {
    transform: translateX(-50%);
    /* 最终位置在容器左侧 */
  }
}

.activity-dialog {
  // width: 840px;
  // height: 620px;
  width: 800px;
  height: 630px;
  position: fixed;
  top: 15vh;
  left: 50%;
  transform: translateX(-50%);
  background-image: url("@/assets/party-building/img_window_800_bg.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;

  .tab-box {
    display: flex;
    align-items: center;
    // justify-content: center;
    background-image: url('@/assets/business-overview/img_lvhua_tab_bg.png');
    background-size: 100% 100%;
    width: 500px;
    height: 30px;
    position: relative;
    left: 50%;
    transform: translate(-50%, 0);
    margin-top: 10px;
  }

  .title-01 {
    // margin-left: 58px;
    // margin-top: 17px;
    box-sizing: border-box;
    height: 30px;
    width: 100px;
    // text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    // padding: 3px;
    border: 1px solid rgba(0, 213, 255, .4);
    font-size: 13px;
  }

  .title-02 {
    // margin-top: 18px;
    margin-top: 10px;
    margin-bottom: 7.5008px;
    padding-left: 12px;
    height: 23px;
    line-height: 23px;
    font-weight: 700;
    color: #00d5ff;
    font-size: 15px;
    border-left: 4px solid #00d5ff;
  }

  dd {
    // width: 400px;
    width: 380px;
    height: 31px;
    opacity: 1;
    border-bottom: 1px solid #00d5ff4d;
    margin-bottom: 10.5008px;
    display: flex;
    // justify-content: space-between;

    div {
      display: flex;

      label {
        height: 18px;
        opacity: 1;
        color: #bcd1e9;
        font-size: 15px;
        font-weight: 400;
        font-family: "Inter";
        text-align: left;
      }

      p {
        // width: 125px;
        width: 115px;
        height: 23px;
        opacity: 1;
        color: #ffffff;
        font-size: 16px;
        font-weight: 400;
        font-family: "Source Sans 3";
        text-align: left;
      }

      .legalPerson {
        overflow: auto;
      }
    }
  }



  .box-1 {
    margin-top: 10px;
    height: 92%;
    display: flex;
    justify-content: flex-start;

    .left-content {
      margin-left: 24px;
      margin-top: 24px;
      padding: 0px;

      .head-box {
        display: flex;

        .img-1 {
          width: 112px;
          height: 112px;
          margin-right: 16px;
        }

        .head-detail {
          .name-box {
            // width: 259px;
            width: 365px;
            /* 设置容器的宽度 */
            white-space: nowrap;
            /* 防止文字换行 */
            overflow: hidden;
            /* 隐藏溢出的文字 */
          }

          .name {
            // width: 259px;
            width: 365px;
            // height: 48px;
            // height: 28px;
            opacity: 1;
            color: #ffffff;
            font-family: "Source Sans 3";
            font-size: 20px;
            font-style: normal;
            font-weight: 700;
            line-height: normal;
            text-align: left;
            margin-bottom: 10px;
          }



          .type-box {
            display: flex;

            .type {
              box-sizing: border-box;
              padding: 3px;
              height: 22px;
              opacity: 1;
              border: 1px solid #00d5ff;
              background: #00d5ff1a;
              color: #00d5ff;
              font-size: 12px;
              font-weight: 400;
              font-family: "Source Sans 3";
              text-align: center;
              line-height: 18px;
              margin-right: 10px;
              display: flex;
              justify-content: center;
              align-items: center;
            }

            .flag {
              box-sizing: border-box;
              padding: 2px;
              margin-right: 8px;
              // width: 64px;
              height: 22px;
              opacity: 1;
              border: 1px solid #2ce493;
              background: #2ce4931a;
              color: #2ce493;
              font-size: 12px;
              font-weight: 400;
              font-family: "Source Sans 3";
              text-align: center;
              // line-height: 18px;
              display: flex;
              justify-content: center;
              align-self: center;
            }
          }

          .address-box {
            margin-top: 10px;
            margin-bottom: 10px;
            display: flex;

            .img-2 {
              width: 15px;
              height: 15px;
              margin-right: 12px;
            }

            .address {
              // width: 219px;
              // height: 42px;
              opacity: 1;
              color: #ffffff;
              font-size: 15px;
              font-weight: 400;
              font-family: "Source Sans 3";
              text-align: left;
              height: 20px;
              overflow-y: auto;
            }
          }
        }
      }





      .serve-p {
        width: 200px;
      }





      // .work-time {
      //     width: 203px;
      //     height: 23px;
      //     opacity: 1;
      //     color: #ffffff;
      //     font-size: 16px;
      //     font-weight: 400;
      //     font-family: "Source Sans 3";
      // }
    }

    .right-content {
      margin-left: 12px;
      margin-top: 24px;
      width: 50%;

      .nenghao {
        display: flex;
        justify-content: space-between;
        width: 356px;


        .nh-item {
          // background-color: #bcd1e9;
          background-image: url("@/assets/business-overview/img_slider_databox_2col_1.png");
          width: 114.5px;
          height: 68px;
          background-size: 100% 100%;
          box-sizing: border-box;
          padding-left: 8px;
          padding-top: 11px;

          .nh-type {
            color: #e2efff;
            font-family: "Inter";
            font-size: 16px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
          }

          .nh-value {
            color: #00d5ff;
            font-family: "Source Sans 3";
            font-size: 18px;
            font-style: normal;
            font-weight: 700;
            line-height: 27px;

            span {
              color: #a0bde0;
              font-family: "Inter";
              font-size: 15px;
              font-style: normal;
              font-weight: 400;
              line-height: normal;

            }
          }
        }
      }

      .img-3 {
        width: 160px;
        height: 110px;
        overflow: auto;

        ::v-deep span {
          .annotationLayer {
            cursor: zoom-in;
          }
        }
      }


      // .text-box {
      //     width: 100%;
      //     height: 100%;
      //     overflow-y: auto;
      // }

      .text {
        position: relative;
        padding-right: 3px;
        // width: 400px;
        width: 360px;
        font-weight: 00;
        font-size: 15px;
        letter-spacing: 0.1em;
        margin-top: 15px;
        margin-bottom: 25px;
        // height: 270px;
        height: 194px;
        // overflow-y: auto;
        text-align: justify;
      }

      .title-03 {
        width: 100%;
        height: 28px;
        line-height: 28px;
        color: #00d5ff;
        font-size: 16px;
        font-weight: 700;
        padding-left: 8px;
        opacity: 1;
        background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 100%);
      }

      .title-02 {
        margin-top: 23px;
        margin-bottom: 7.5008px;
        padding-left: 12px;
        height: 23px;
        line-height: 23px;
        font-weight: 700;
        color: #00d5ff;
        font-size: 16px;
        border-left: 4px solid #00d5ff;
      }
    }
  }

  .box-2 {
    // margin-top: 10px;
    height: 92%;
    padding-left: 24px;
    padding-top: 30px;
    padding-right: 24px;

    .box-2-head {
      display: flex;
      align-items: center;
      height: 30px;
      margin-bottom: 20px;

      .name {
        color: #ffffff;
        font-family: "Source Sans 3";
        font-size: 20px;
        font-style: normal;
        font-weight: 700;
        line-height: normal;
      }

      .flag-box {
        display: flex;
        align-items: center;
        margin-left: 20px;

        .flag1 {
          box-sizing: border-box;
          padding: 2px 8px;
          height: 22px;
          opacity: 1;
          border: 1px solid #00d5ff;
          background: #00d5ff1a;
          color: #00d5ff;
          font-family: "Source Sans 3";
          font-size: 12px;
          font-style: normal;
          font-weight: 400;
          line-height: 18px;
          text-align: center;
          margin-right: 10px;
          display: flex;
          justify-content: center;
          align-items: center;
        }

        .flag2 {
          box-sizing: border-box;
          padding: 2px 8px;
          // width: 147px;
          height: 22px;
          opacity: 1;
          border: 1px solid #2ce493;
          background: #2ce4931a;
          color: #2ce493;
          font-family: "Source Sans 3";
          font-size: 12px;
          font-style: normal;
          font-weight: 400;
          text-align: center;
          display: flex;
          justify-content: center;
          align-self: center;
        }
      }
    }

    .box-2-center {
      .box-2-list {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;

        dd {
          width: 355px;

          .daibiaoren {
            width: 254px;
            overflow: auto;
          }

          .box-2-address {
            width: 75px;
          }

          .box-2-address-val {
            width: 260px;
            overflow: hidden
          }
        }
      }
    }

    ul {
      li {
        height: 42px;
        display: grid;
        // grid-template-columns: 56px 100px 100px 150px 100px 150px auto;
        // grid-template-columns: 48px 80px 80px 120px 148px 128px 148px;
        grid-template-columns: 48px 240px auto auto auto auto auto;
        align-items: center;
        font-size: 14px;
        font-weight: 400;
        background-color: rgba(31, 168, 239, 0.2);

        &:nth-child(even) {
          background-color: rgba(31, 168, 239, 0.1);
        }

        div {
          &:nth-child(1) {
            text-align: center;
          }

          &:nth-child(2) {
            padding-left: 8px;
          }
        }
      }
    }

    ul.list {
      margin-top: 8px;

      li {
        color: rgb(0, 213, 255);
        font-size: 14px;
        font-weight: 400;
        background-color: rgba(29, 80, 120, 0.8);
      }
    }

    ul.content {
      height: 210px;
    }


  }

  .box-3 {
    margin-top: 10px;
    height: 92%;
    padding-left: 24px;
    padding-top: 10px;
    padding-right: 24px;


    .box-3-list {
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;

      dd {
        width: 350px;
      }

      label {
        width: 110px;
      }

      p {
        width: 270px;
        overflow: hidden
      }

      // .shuiwuId {
      //   overflow-x: auto
      // }
    }

    ul {
      li {
        height: 42px;
        display: grid;
        // grid-template-columns: 56px 232px 232px 232px;
        grid-template-columns: 48px 234.67px 234.67px 234.67px;
        align-items: center;
        font-size: 14px;
        font-weight: 400;
        background-color: rgba(31, 168, 239, 0.2);

        &:nth-child(even) {
          background-color: rgba(31, 168, 239, 0.1);
        }

        div {
          &:nth-child(1) {
            text-align: center;
          }

          &:nth-child(2) {
            padding-left: 8px;
          }
        }
      }
    }

    ul.list {
      margin-top: 8px;

      li {
        color: rgb(0, 213, 255);
        font-size: 14px;
        font-weight: 400;
        background-color: rgba(29, 80, 120, 0.8);
      }
    }

    ul.content {
      height: 125px;
    }

    ul.content2 {
      height: 84px;
    }
  }

  .box-4 {
    margin-top: 10px;
    height: 92%;
    padding-left: 24px;
    padding-top: 10px;
    padding-right: 24px;

    .box-4-list {
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;

      dd {
        width: 350px;
      }

      .cbName {
        width: 210px;
        overflow: hidden;
      }

      dd.row-2 {
        width: 230px;
      }
    }

    ul {
      li {
        height: 42px;
        display: grid;
        // grid-template-columns: 56px 232px 232px 232px;
        grid-template-columns: 48px 234.67px 234.67px 234.67px;
        align-items: center;
        font-size: 14px;
        font-weight: 400;
        background-color: rgba(31, 168, 239, 0.2);

        &:nth-child(even) {
          background-color: rgba(31, 168, 239, 0.1);
        }

        div {
          &:nth-child(1) {
            text-align: center;
          }

          &:nth-child(2) {
            padding-left: 8px;
          }
        }
      }
    }

    ul.list {
      margin-top: 8px;

      li {
        color: rgb(0, 213, 255);
        font-size: 14px;
        font-weight: 400;
        background-color: rgba(29, 80, 120, 0.8);
      }
    }

    ul.content {
      position: relative;
      height: 125px;
    }

    ul.content2 {
      height: 160px;
    }
  }

  .box-5 {
    margin-top: 10px;
    height: 92%;
    padding-left: 24px;
    padding-top: 10px;
    padding-right: 24px;

    .list-box {
      ul {
        li {
          height: 42px;
          display: grid;
          // grid-template-columns: 56px 139px 139px 139px 139px auto;
          grid-template-columns: 48px 120px 100px 161.33px 161.33px 161.33px;
          align-items: center;
          font-size: 14px;
          font-weight: 400;
          background-color: rgba(31, 168, 239, 0.2);

          &:nth-child(even) {
            background-color: rgba(31, 168, 239, 0.1);
          }

          div {
            &:nth-child(1) {
              text-align: center;
            }

            &:nth-child(2) {
              padding-left: 8px;
            }
          }
        }
      }

      ul.list {
        margin-top: 8px;

        li {
          color: rgb(0, 213, 255);
          font-size: 14px;
          font-weight: 400;
          background-color: rgba(29, 80, 120, 0.8);
        }
      }

      ul.content {
        position: relative;
        height: 210px;
      }
    }

    .list-2-box {
      ul {
        li {
          height: 42px;
          display: grid;
          // grid-template-columns: 56px 232px 232px auto;
          grid-template-columns: 48px 234.67px 234.67px 234.67px;
          align-items: center;
          font-size: 14px;
          font-weight: 400;
          background-color: rgba(31, 168, 239, 0.2);

          &:nth-child(even) {
            background-color: rgba(31, 168, 239, 0.1);
          }

          div {
            &:nth-child(1) {
              text-align: center;
            }

            &:nth-child(2) {
              padding-left: 8px;
            }
          }
        }
      }

      ul.list2 {
        margin-top: 8px;

        li {
          color: rgb(0, 213, 255);
          font-size: 14px;
          font-weight: 400;
          background-color: rgba(29, 80, 120, 0.8);
        }
      }


      ul.content2 {
        height: 160px;
      }
    }

  }
}

.close {
  display: inline-block;
  width: 22px;
  height: 22px;
  background-image: url("@/assets/icon_btn_close.png");
  background-size: 100% 100%;
  position: absolute;
  right: 8px;
  top: 8px;
  cursor: pointer;
  z-index: 20;
}
</style>