<template>
  <div v-if="visible" class="activity-dialog">
    <i class="close" @click="visibleDialog"></i>
    <p class="title-01">{{ title }}</p>
    <div class="content">
      <div class="content-left">
        <p class="title-02">中心介绍</p>
        <p class="desc scroll-container">
          2018年6月30日，滨湖区蠡湖街道举办纪念中国共产党成立97周年表彰大会暨湖滨商业街区党群服务中心揭牌成立仪式，来自街道机关、社区、直属单位等近80名党员代表参加活动。
          湖滨商业街区党群服务中心分为六大功能区域，包含读书角、谈心室、“一站式”服务点、党史展示墙、新党员宣誓墙等内容，已成为蠡湖科技创业服务中心党总支、湖滨商业街管理中心党总支和蠡湖物业管理中心党总支共同的活动阵地。
        </p>
        <p class="title-03">中心详情</p>
        <dl>
          <dd v-if="title === '湖滨假日商业旅游发展有限公司支部委员会'">
            <div>
              <label>所属支部：</label>
              <span>湖滨假日商业旅游发展有限公司支部委员会（13人）</span>
            </div>
            <div>
              <label>地址：</label>
              <span>滨湖区湖滨街15号</span>
            </div>
          </dd>
          <dd v-else-if="title === '湖滨假日物业服务有限公司支部委员会'">
            <div>
              <label>所属支部：</label>
              <span>湖滨假日物业服务有限公司支部委员会（5人）</span>
            </div>
            <div>
              <label>地址：</label>
              <span>滨湖区湖滨街15号</span>
            </div>
          </dd>
          <dd v-else-if="title === '蠡湖集贸市场支部委员会'">
            <div>
              <label>所属支部：</label>
              <span>蠡湖集贸市场支部委员会（9人）</span>
            </div>
            <div>
              <label>地址：</label>
              <span>滨湖区中南西路211号</span>
            </div>
          </dd>
          <dd v-else>
            <div>
              <label>所属支部：</label>
              <span>湖滨商业街餐饮行业支部委员会（31人）</span>
            </div>
            <div>
              <label>地址：</label>
              <span>滨湖区中南西路211号</span>
            </div>
          </dd>
        </dl>
        <p class="title-03">服务时间</p>
        <p class="time">
          <label>周一到周五：</label>
          <span>09:00-12:00,13:00-17:00</span>
        </p>
      </div>
      <div class="content-right">
        <p class="title-02">人员信息</p>
        <dl class="head">
          <dd>
            <div>序号</div>
            <div>姓名</div>
            <div>手机号</div>
            <div>性别</div>
            <div>所属支部</div>
          </dd>
        </dl>
        <dl class="dl-content scroll-container">
          <dd v-for="(item, i) in committeeList" :key="i">
            <div>{{ i + 1 }}</div>
            <div>{{ item.name }}</div>
            <div>{{ item.phone || "-" }}</div>
            <div>{{ item.sex }}</div>
            <div :title="item.committee">{{ item.committee }}</div>
          </dd>
        </dl>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ActivityDialog',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: '湖滨商业街党群服务中心'
    }
  },
  data() {
    return {
      list: [
        {
          "name": "沈佳敏",
          "sex": "女",
          "phone": "15052130716",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "欧敏志",
          "sex": "女",
          "phone": "13771100288",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "殷  健",
          "sex": "男",
          "phone": "13961817979",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "周海娟",
          "sex": "女",
          "phone": "15190299046",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "胡  赟",
          "sex": "女",
          "phone": "13921150910",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "季烨秋",
          "sex": "女",
          "phone": "13861693601",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "吴  培",
          "sex": "女",
          "phone": "13665166991",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "任品梅",
          "sex": "女",
          "phone": "15861679030",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "陆  佳",
          "sex": "女",
          "phone": "13812057533",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "张椿洋",
          "sex": "男",
          "phone": "15861555789",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "顾乃斌",
          "sex": "男",
          "phone": "13585046831",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "徐鸿文",
          "sex": "男",
          "phone": "13952460737",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "张  君",
          "sex": "男",
          "phone": "13861815990",
          "committee": "湖滨假日商业旅游发展有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "季辰洁",
          "sex": "女",
          "phone": "13404261722",
          "committee": "湖滨假日物业服务有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "鲍立群",
          "sex": "男",
          "phone": "13961890987",
          "committee": "湖滨假日物业服务有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "张鲁根",
          "sex": "男",
          "phone": "15951511960",
          "committee": "湖滨假日物业服务有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "王丽霞",
          "sex": "女",
          "phone": "13815105098",
          "committee": "湖滨假日物业服务有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "朱迎春",
          "sex": "男",
          "phone": "15995223071",
          "committee": "湖滨假日物业服务有限公司支部委员会",
          "address": "滨湖区湖滨街15号"
        },
        {
          "name": "夏  光",
          "sex": "男",
          "phone": "13961878330",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "陆振华",
          "sex": "男",
          "phone": "13814241332",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "徐  斌",
          "sex": "男",
          "phone": "19952780185",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "何  超",
          "sex": "男",
          "phone": "13701519120",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "李建亚",
          "sex": "男",
          "phone": "13961729157",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "陶建伟",
          "sex": "男",
          "phone": "15052255881",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "张卫宝",
          "sex": "男",
          "phone": "13861793775",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "陈玲娇",
          "sex": "女",
          "phone": "19805122280",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "程轶萍",
          "sex": "女",
          "phone": "13616170321",
          "committee": "蠡湖集贸市场支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "虞学鹏",
          "sex": "男",
          "phone": "13771024424",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "葛刚强",
          "sex": "男",
          "phone": "18651585080",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "朱昌年",
          "sex": "男",
          "phone": "13182798271",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "徐瑾宇",
          "sex": "男",
          "phone": "13961883921",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "钱  丹",
          "sex": "女",
          "phone": "15061897015",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "李运华",
          "sex": "男",
          "phone": "18552186547",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "孙威威",
          "sex": "男",
          "phone": "18751559166",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "丁   峰",
          "sex": "男",
          "phone": "13812515670",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "宋雅萍",
          "sex": "女",
          "phone": "13585080068",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "支   鹰",
          "sex": "女",
          "phone": "13961700886",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "陈志芳",
          "sex": "女",
          "phone": "13812001970",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "梁志杰",
          "sex": "男",
          "phone": "13861739210",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "钱  静",
          "sex": "女",
          "phone": "15261662017",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "罗新宇",
          "sex": "男",
          "phone": "13218058000",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "卢英杰",
          "sex": "男",
          "phone": "13806191745",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "蒋源发",
          "sex": "男",
          "phone": "13601517951",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "徐   峰",
          "sex": "男",
          "phone": "13812286862",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "杨   斌",
          "sex": "男",
          "phone": "13812066888",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "高伟伟",
          "sex": "男",
          "phone": "18851585598",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "张红亮",
          "sex": "男",
          "phone": "13376230555",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "贲银红",
          "sex": "女",
          "phone": "18762818183",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "朱昭荣",
          "sex": "女",
          "phone": "13812526741",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "胡雯君",
          "sex": "女",
          "phone": "18795600360",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "王   丽",
          "sex": "女",
          "phone": "18961843987",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "刘雄兵",
          "sex": "男",
          "phone": "13812537755",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "孙建中",
          "sex": "男",
          "phone": "15370213779",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "刘同琳",
          "sex": "男",
          "phone": "13167066371",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "王   平",
          "sex": "男",
          "phone": "13338117417",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "储梦仪",
          "sex": "女",
          "phone": "15995296999",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "蒋燕妮",
          "sex": "女",
          "phone": "",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        },
        {
          "name": "丁  庆",
          "sex": "男",
          "phone": "",
          "committee": "湖滨商业街餐饮行业支部委员会",
          "address": "滨湖区中南西路211号"
        }
      ],
      committeeList: []
    }
  },
  computed: {
    titleChange() {
      return this.title
    }
  },
  watch: {
    titleChange(value) {
      this.committeeList = this.list.filter(v => v.committee === value)

    }
  },
  mounted() {

    this.committeeList = this.list.filter(v => v.committee === this.title)
    console.log(this.committeeList)


  },
  methods: {
    visibleDialog() {
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.activity-dialog {
  width: 800px;
  height: 580px;
  position: fixed;
  top: 15vh;
  left: 50%;
  transform: translateX(-50%);
  background-image: url("@/assets/party-building/img_window_800_bg.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;

  .title-01 {
    margin-left: 58px;
    margin-top: 17px;
    font-size: 18px;
  }

  .title-02 {
    width: 48%;
    height: 28px;
    line-height: 28px;
    color: #00d5ff;
    font-size: 16px;
    font-weight: 700;
    padding-left: 8px;
    opacity: 1;
    background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 100%);
  }

  .title-03 {
    margin-top: 16px;
    padding-left: 12px;
    height: 23px;
    line-height: 23px;
    font-weight: 700;
    color: #00d5ff;
    font-size: 16px;
    border-left: 4px solid #00d5ff;
  }

  .content {
    width: 94%;
    margin: 30px auto 0;
    display: flex;
    gap: 24px;

    &-left {
      width: 50%;
      font-size: 14px;
      dl {
        dd {
          padding: 8px 0;
          border-bottom: 1px solid;
          border-image: linear-gradient(
              to right,
              #00d5ff00,
              #00d5ff 30%,
              #00d5ff00
            )
            1;
        }
      }
    }

    &-right {
      width: 50%;

      dl {
        dd {
          height: 42px;
          display: grid;
          grid-template-columns: 48px 70px 100px 48px auto;
          align-items: center;
          font-size: 14px;
          font-weight: 400;
          background-color: rgba(31, 168, 239, 0.2);

          &:nth-child(even) {
            background-color: rgba(31, 168, 239, 0.1);
          }

          div {
            &:nth-child(1),
            &:nth-child(4) {
              text-align: center;
              
            }

            &:nth-child(2) {
              padding-left: 8px;
            }
            &:nth-child(5) {
              white-space: nowrap;
              text-overflow: ellipsis;
              word-break: break-all;
              overflow: hidden;
            }
          }
        }
      }

      dl.head {
        margin-top: 8px;

        dd {
          color: rgb(0, 213, 255);
          font-size: 16px;
          font-weight: 400;
          background-color: rgba(29, 80, 120, 0.8);
        }
      }

      dl.dl-content {
        height: 414px;
      }
    }

    .desc {
      height: 144px;
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