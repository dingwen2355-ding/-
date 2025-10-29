<template>
  <div v-if="visible" class="right-bg">

    <IocTitle title="中心可视化概览" @change="closedWin()" />

    <ul>
      <li :class="{ active: active === 1 }">

        <!-- 党建数据 -->
        <div class="box-01" @click="clickLi(1)">
          <p>党建数据</p>
          <p>Party building data</p>
          <span>
            {{ active === 1 ? "收起" : "展开" }}
            <svg xmlns="http://www.w3.org/2000/svg" width="8" height="6" viewBox="0 0 8 6" fill="none">
              <path v-if="active === 1" fill-rule="evenodd" clip-rule="evenodd" d="M0 5.61987L4 0.379873L8 5.61987H0Z"
                fill="white" />
              <path v-else fill-rule="evenodd" clip-rule="evenodd" d="M0 0.379883L4 5.61988L8 0.379883H0Z" fill="white" />
            </svg>
          </span>
        </div>

        <div class="box-02">
          <div>
            <p>党员年龄构成</p>
            <partyBuildingPie></partyBuildingPie>
          </div>

          <div>
            <p>党员性别比例</p>
            <partyBuildingPieTwo></partyBuildingPieTwo>
          </div>

          <div class="flex">
            <div class="box-02_">
              <div class="label">党员商户</div>
              <div class="value">{{ PartyMerchantCount }}<span class="unit">&nbsp;&nbsp;家</span></div>
            </div>
  
            <div class="box-02_">
              <div class="label">在职人数</div>
              <div class="value">{{ TotalCount }}<span class="unit">&nbsp;&nbsp;人</span></div>
            </div>
          </div>
          
        </div>
      </li>

      <!-- 资源可见 -->
      <li :class="{ active: active === 2 }">
        <div class="box-01" @click="clickLi(2)">
          <p>资源可见</p>
          <p>Resource Visualization</p>
          <span>
            {{ active === 2 ? "收起" : "展开" }}
            <svg xmlns="http://www.w3.org/2000/svg" width="8" height="6" viewBox="0 0 8 6" fill="none">
              <path v-if="active === 2" fill-rule="evenodd" clip-rule="evenodd" d="M0 5.61987L4 0.379873L8 5.61987H0Z"
                fill="white" />
              <path v-else fill-rule="evenodd" clip-rule="evenodd" d="M0 0.379883L4 5.61988L8 0.379883H0Z" fill="white" />
            </svg>
          </span>
        </div>

        <div class="box-02">
          <div class="box-02-2">
            <i></i>
            <label>商铺总数</label>
            <span class="value">{{ Count }}</span>
            <span class="unit">家</span>
          </div>
          
          <div class="box-02-2">
            <i></i>
            <label>个体户数</label>
            <span class="value">{{ Individuality }}</span>
            <span class="unit">家</span>
          </div>

          <div class="box-02-2">
            <i></i>
            <label>企业数量</label>
            <span class="value">{{ Enterprise }}</span>
            <span class="unit">家</span>
          </div>

          <div class="box-02-2">
            <i></i>
            <label>今日客流</label>
            <span class="value">{{ People }}</span>
            <span class="unit">人</span>
          </div>

        </div>
      </li>

      <!-- 活动可见 -->
      <li :class="{ active: active === 3 }">
        <div class="box-01" @click="clickLi(3)">
          <p>活动可见</p>
          <p>Activity Visualization</p>
          <span>
            {{ active === 3 ? "收起" : "展开" }}
            <svg xmlns="http://www.w3.org/2000/svg" width="8" height="6" viewBox="0 0 8 6" fill="none">
              <path v-if="active === 3" fill-rule="evenodd" clip-rule="evenodd" d="M0 5.61987L4 0.379873L8 5.61987H0Z"
                fill="white" />
              <path v-else fill-rule="evenodd" clip-rule="evenodd" d="M0 0.379883L4 5.61988L8 0.379883H0Z" fill="white" />
            </svg>
          </span>
        </div>

        <div class="box-02">
          <!-- <div class="box-02-2">
            <i></i>
            <label>新闻</label>
            <span class="value">203</span>
            <span class="unit">条</span>
          </div> -->

          <div class="box-02-2">
            <!-- <i></i> -->
            <label>活动</label>
            <span class="value">15</span>
            <span class="unit">场</span>
          </div>

          <div class="box-02-3">
            <p><span>活动图片</span><span>15张</span></p>
            <dl class="scroll-container">
              <dd v-for="i in 15" :key="i">
                <img @click="showImg(i)" :src="require('@/assets/party-building/img/' + i + '.jpg')" />
              </dd>
            </dl>
          </div>

        </div>
      </li>
    </ul>

    <ImgPop></ImgPop>

  </div>
</template>

<script>
import IocTitle from '@/components/title';
import partyBuildingPie from "./Pie.vue";
import partyBuildingPieTwo from "./PieTwo.vue";
import ImgPop from "./ImgPop.vue";

import { GetPartyMemberStatistic, GetResourceVisualization, GetPersonnum } from '@/utils/api/partyBuilding';

export default {
  name: 'PartyBuildingRight',
  components: {
    IocTitle,
    partyBuildingPie,
    partyBuildingPieTwo,
    ImgPop,
  },

  data() {
    return {
      visible: true,
      active: 1, // 列表项选中

      PartyMerchantCount: 0, // 党员
      TotalCount: 0, // 人数

      Count: 0, //商铺总数
      Individuality: 0, //个体
      Enterprise: 0, //企业
      People: 0,

      ImgText: [
        { Text: '苏书记为“学党史 强信念 跟党走”主题演讲最佳人气党员颁奖' },
        { Text: '湖滨商业街开展“学党史 强信念 跟党走”党建主题演讲比赛' },
        { Text: '湖滨商业街开展商圈党建座谈会' },
        { Text: '疫情期间爱心商家为抗疫一线工作人员送上爱心便当' },
        { Text: '商业街党总支组织开展深刻学习领会党的二十大精神' },
        { Text: '商业街党员同志化身“大白”深入一线、并肩作战、抗击疫情' },
        { Text: '湖滨商业街开展党史教育学习活动' },
        { Text: '湖滨商业街党支部联合蠡湖集贸市场党支部进行基层党员培训' },
        { Text: '参加滨湖区职工“红色湖湾马山行”徒步活动' },
        { Text: '爱心企业' },
        { Text: '爱心商户——沈府家筵董事长沈国发为街区环卫工人送温暖' },
        { Text: '同心齐向党 凝聚“新”力量主题党日活动' },
        { Text: '为湖滨商业街“红色管家”发放聘书' },
        { Text: '3.12与党建联盟单位——明日风园艺公司携手开展植树活动' },
        { Text: '苏书记带领党员同志实地查看街区提升改造项目' },
      ]
    }
  },

  methods: {
    closedWin() {
      this.visible = false
    },

    // 点击列表项
    clickLi(i) {
      this.active === i ? this.active = 0 : this.active = i
    },

    // 图片弹窗
    showImg(i) {
      const model = this.findComponentDownward(this, "ImgPop");
      model.ImgShow = true;
      let Img = require('@/assets/party-building/img/' + i + '.jpg');
      // console.log('点击了图片弹窗', Img);
      model.Img = Img;
      model.Title = this.ImgText[i-1].Text;
    },

    Requests() {
      GetPartyMemberStatistic({}).then((res) => {
        console.log("-党建数据-请求到接口了------");
        console.log(res);
        if( res.code == 200 ) {
          this.TotalCount = res.data.totalCount;
          // 年龄构成
          const modelAgeData = this.findComponentDownward(this, "partyBuildingPie");
          modelAgeData.TotalCount = res.data.totalCount;
          modelAgeData.AgeData = res.data.ageData;
          modelAgeData.Echarts();
          
          // 性别比例
          const modelSexData = this.findComponentDownward(this, "partyBuildingPieTwo");
          modelSexData.TotalCount = res.data.totalCount;
          modelSexData.SexData = res.data.sexData;
          modelSexData.Echarts();

          // 党员 人数
          this.PartyMerchantCount = res.data.partyMerchantCount;
        }
      });

      GetResourceVisualization({}).then((res) => {
        console.log("-资源可见-请求到接口了------");
        console.log(res);
        if( res.code == 200 ) {
          this.Enterprise = res.data[0].count;
          this.Individuality = res.data[1].count;
          this.Count = (this.Enterprise + this.Individuality);
        }
      });

      // 今日客流
      GetPersonnum({}).then((res) => {
        console.log("-今日客流-请求到接口了------");
        console.log(res);
        if( res.code == 200 ) {
          this.People = res.data.filter(e => { return e.type == 'today' })[0].num;
        }
      });
      
    },

  },

  created() {
    this.Requests();
  },

  mounted() {},
}
</script>

<style lang="scss" scoped>
ul {
  margin-top: 8px;

  li {
    transition: height 0.5s;
    position: relative;

    margin-bottom: 11px;
    height: 88px;
    transition-property: height;
    transition-duration: 0.8s;
    overflow: hidden;

    .box-01 {
      width: 432px;
      height: 88px;
      border-radius: 10px;
      overflow: hidden;
      cursor: pointer;
      background-image: url("@/assets/party-building/img_dangjian_right_folder.png");
      background-repeat: no-repeat;

      p {
        &:first-child {
          margin-top: 20px;
          margin-left: 88px;
          font-size: 24px;
          font-weight: 700;
        }

        &:nth-child(2) {
          margin-left: 88px;
          font-size: 14px;
          font-weight: 400;
        }
      }

      span {
        position: absolute;
        right: 16px;
        top: 33px;
        padding: 4px 8px;
        border-radius: 2px;
        background: rgba(26, 33, 35, 0.1);

        svg {
          vertical-align: middle;
        }
      }
    }

    .box-02 {
      transition-property: height;
      transition-duration: 0.8s;
      width: 430px;
      height: 0;
      background-image: url("@/assets/party-building/img_dangjian_right_databox_bg.png");
      background-repeat: no-repeat;
      background-size: 100% 100%;
      margin: 8px auto 18px;

      .flex{
        display: flex;
      }

      &_ {
        width: 194px;
        height: 66px;
        background-image: url("@/assets/party-building/img_home_party_shop_bg.png");
        background-size: 100% 100%;
        background-repeat: no-repeat;
        margin: 8px 16px 0 16px;

        .label {
        display: block;
        font-size: 16px;
        font-weight: 400;
        margin: 12px 0 0 70px;
      }

      .value {
          margin-left: 70px;
          color: #00d5ff;
          font-size: 18px;
          font-weight: 700;
        }

      .unit {
          color: #bcd1e9;
          font-size: 15px;
          font-weight: 400;
        }
      }

      &_:last-child {
        width: 194px;
        height: 66px;
        background-image: url("@/assets/party-building/img_home_onduty_bg.png");
        background-size: 100% 100%;
        background-repeat: no-repeat;
      }
      
      &-2 {
        width: 400px;
        height: 92px;
        margin: 16px auto;
        background-image: url("@/assets/party-building/img_dangjian_right_data_title_bg.png");
        background-repeat: no-repeat;
        display: flex;
        align-items: center;

        i {
          display: inline-block;
          width: 88px;
          height: 88px;
          background-image: url("@/assets/party-building/icon_dangjian_right_data_title_bg.png");
          background-repeat: no-repeat;
          margin-left: 28px;
        }

        label {
          font-size: 18px;
          font-weight: 400;
          margin: 0 22px 0 10px;
        }

        span.value {
          font-size: 35px;
          font-weight: 700;
          margin-right: 8px;
        }

        span.unit {
          font-size: 14px;
          font-style: normal;
          font-weight: 400;
        }

        &:last-child {
          i {
            background-position-y: -88px;
          }
        }
      }
    }

    &:nth-child(1) {
      .box-02 {
        padding-bottom: 8px;
        overflow: hidden;
        p {
          margin: 10px 0 8px 16px;
          border-left: 4px solid #00d5ff;
          height: 24px;
          line-height: 24px;
          color: #00d5ff;
          font-size: 18px;
          font-weight: 600;
          padding-left: 16px;
        }
      }

    }

    &:nth-child(2) {
      .box-01 {
        background-position-y: -88px;
      }

      .box-02 {
        &-2 {
          @for $n from 1 through 4 {
            &:nth-child(#{$n}) {
              i {
                background-position-y: ($n - 1) * -88 + -176px;
              }
            }
          }
        }
      }
    }

    &:nth-child(3) {
      .box-01 {
        background-position-y: -176px;
      }

      .box-02 {
        &-2 {
          label {
            margin: 0 70px;
          }

          &:nth-child(1) {
            i {
              background-position-y: -528px;
            }
          }

          &:nth-child(2) {
            i {
              background-position-y: -616px;
            }
          }
        }

        &-3 {
          width: 396px;
          margin: 0 auto;

          p {
            padding: 0 8px;
            height: 28px;
            display: flex;
            margin: 0 auto;
            line-height: 28px;
            justify-content: space-between;
            background: linear-gradient(90deg,
                rgba(0, 213, 255, 0.3) 0%,
                rgba(0, 213, 255, 0) 101.22%);

            span:nth-child(1) {
              color: #00d5ff;
              font-size: 18px;
              font-weight: 600;
            }

            span:nth-child(2) {
              color: #fff;
              font-size: 18px;
              font-weight: 600;
            }
          }

          dl.scroll-container {
            margin-top: 8px;
            // height: 188px;
            width: 100%;
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            gap: 8px;

            dd {
              width: 90px;
              height: 70px;

              img {
                width: 100%;
                height: 100%;
                cursor: pointer;
              }
            }
          }

        }
      }
    }
  }

  li.active {
    height: 570px;

    .box-01 {
      svg {
        margin-top: -4px;
      }
    }

    .box-02 {
      height: 467px;
    }
  }
}
</style>