<template>
  <div v-if="visible" class="right-bg">
    <div class="TOP">
      <IocTitle title="AI事件" @change="closedWin()" />

      <div class="Flex">
        <div class="ai">
          <div class="position">
            <div class="title">当月AI事件总数</div>
            <div class="number">{{ Count }}</div>
          </div>
        </div>

        <div class="ai">
          <div class="position">
            <div class="title">今日AI事件数</div>
            <div class="number">{{ Today }}</div>
          </div>
        </div>
      </div>

      <div class="block">
        <div class="background"><h3>AI事件类型结构</h3></div>
        <div class="Echarts">
          <SecurityoniMtoringPie></SecurityoniMtoringPie>
        </div>
      </div>

      <div class="block">
        <div class="background"><h3>AI事件类型趋势</h3></div>
        <div class="Echarts">
          <SecurityoniMtoringLineChart></SecurityoniMtoringLineChart>
        </div>
      </div>

      <div class="block">
        <div class="background"><h3>AI事件列表</h3></div>

        <div class="Echarts">
          <div class="content-right">
            <dl class="head">
              <dd>
                <div>序号</div>
                <div>事件名称</div>
                <div>发生时间</div>
                <div>状态</div>
              </dd>
            </dl>
            
            <dl class="dl-content">
              <dd v-for="(item, index) in List" :key="index">
                <div>{{ index + 1 }}</div>
                <div>{{ item.name || "-" }}</div>
                <div>{{ item.time }}</div>
                <div>{{ item.state }}</div>
              </dd>
            </dl>

            <!-- <dl class="dl-content">
              <dd v-for="(item, index) in 10" :key="index">
                <div>{{ index + 1 }}</div>
                <div>垃圾散落</div>
                <div>2023/06/27 17:00:22</div>
                <div>关闭</div>
              </dd>
            </dl> -->

            <!-- <div class="none">暂无数据</div> -->

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import IocTitle from "@/components/title/index";
import SecurityoniMtoringPie from "./Pie.vue";
import SecurityoniMtoringLineChart from "./LineChart.vue";

// import { GetAinum } from "@/utils/api/SecurityoniMtoring";

export default {
  name: "SecurityoniMtoringRight",
  components: {
    IocTitle,
    SecurityoniMtoringPie,
    SecurityoniMtoringLineChart,
  },

  data() {
    return {
      visible: true,
      Count: 502,
      Today: 86,

      List: [
        {
          name: "路面积水",
          time: "2024/7/11 12:13:25",
          state: "已处置",
        },
        {
          name: "路面积水",
          time: "2024/7/11 12:09:29",
          state: "已处置",
        },
        {
          name: "暴露垃圾",
          time: "2024/7/11 12:09:23",
          state: "已处置",
        },
        {
          name: "堆物",
          time: "2024/7/11 12:07:29",
          state: "已处置",
        },
        {
          name: "路面积水",
          time: "2024/7/11 11:57:55",
          state: "已处置",
        },
        {
          name: "机动车违停",
          time: "2024/7/10 18:09:55",
          state: "已处置",
        },
        {
          name: "机动车违停",
          time: "2024/7/10 12:46:07",
          state: "已处置",
        },
        {
          name: "悬挂横幅",
          time: "2024/3/31 9:04:01",
          state: "已处置",
        },
        {
          name: "非机动车逆行",
          time: "2024/3/31 16:59:38",
          state: "已处置",
        },
        {
          name: "机动车逆行",
          time: "2024/3/31 16:59:31",
          state: "已处置",
        },
      ],
    };
  },

  methods: {
    closedWin() {
      this.visible = false;
    },

    getAinum() {
      let that = this;
      for (let index = 1; index < 3; index++) {
        GetAinum({
          thingtype: index, //1当月AI事件总数 2今日ai事件数
        }).then((res) => {
          console.log("-AI事件-请求到接口了------");
          console.log(res);
          if (res.code == 200) {
            if (index === 1) {
              that.Count = res.data;
            } else if (index === 2) {
              that.Today = res.data;
            }
          }
        });
      }
    },

  },

  created() {
    // this.getAinum();
  },
};
</script>

<style lang="scss" scoped>
.TOP {
  margin-top: 16px;
  .Flex {
    display: flex;
    .ai {
      margin-top: 8px;
      margin-right: 8px;
      background-image: url("@/assets/SecurityoniMtoring/img_park_data_bg2@2x.png");
      background-size: 100% 100%;
      width: 210px;
      height: 65px;
    }
    .ai:last-child {
      margin-right: 0;
    }

    .position {
      margin: 8px 0 0 12px;
      .title {
        color: #ffffff;
        font-size: 15px;
        font-weight: 400;
      }

      .number {
        font-size: 18px;
        font-weight: 500;
        color: #00d5ff;
      }
    }
  }

  .background {
    width: 428px;
    height: 28px;
    line-height: 28px;
    background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 101.22%);
    margin-top: 23px;

    h3 {
      color: #ffffff;
      font-size: 18px;
      font-weight: 400;
      margin-left: 17px;
    }
  }

  .Echarts {
    width: 428px;
  }

  .content-right {
    width: 100%;
    dl {
      dd {
        height: 42px;
        display: grid;
        grid-template-columns: 60px 130px 150px 60px 0;
        align-items: center;
        font-size: 14px;
        font-weight: 400;
        background-color: rgba(31, 168, 239, 0.1);
        &:nth-child(even) {
          background-color: rgba(31, 168, 239, 0.2);
        }
        div {
          &:nth-child(1),
          &:nth-child(4) {
            text-align: center;
          }
          &:nth-child(2) {
            padding-left: 0;
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
      height: 140px;
      overflow-y: auto;
    }

    .none {
        font-size: 15px;
        text-align: center;
        margin: 15% 0;
    }
  }
}
</style>
