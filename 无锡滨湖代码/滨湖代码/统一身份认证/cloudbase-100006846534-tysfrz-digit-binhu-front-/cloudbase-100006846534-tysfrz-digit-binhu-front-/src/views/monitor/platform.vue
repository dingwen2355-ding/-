<template>
  <div>
    <basic-container class="top">
      <el-card class="box-card">
        <div class="left">
          <img src="img/bg/zhexiantu.png" alt="" />
        </div>
        <div class="right">
          <div>
            <span style="font-size: 26px">{{ apiData.dayApiAmount }}</span>
          </div>
          <div style="margin-top: 2px; font-size: 12px">今日接口调用次数</div>
        </div>
      </el-card>
      <el-card class="box-card">
        <div class="left"><img src="img/bg/zhexiantu.png" alt="" /></div>
        <div class="right">
          <div>
            <span style="font-size: 26px">{{ apiData.apiAbnormaAmount }}</span>
          </div>
          <div style="margin-top: 2px; font-size: 12px">接口陷阱命中次数</div>
        </div>
      </el-card>
    </basic-container>

    <basic-container>
      <div class="title-text">接口调用频率</div>
      <e-chart
        :options="chartOption"
        style="width: 100%; height: 300px"
      ></e-chart>
    </basic-container>
  </div>
</template>

<script>
import {
  getCallapi
} from "@/api/monitor/platform";

export default {
  name: "wel",
  data() {
    return {
      userData: {},
      apiData: {},
      chartOption : {},
      countData: [],
      timer: null,
      timeArr: [],
      tableData: [],
      jvmList:[],
      apiAbnorAmount:0,
      srcList: [
        "img/bg/yonghu.png",
        "img/bg/login@.png",
        "img/bg/duanxin.png",
        "img/bg/order-request.png",
        "img/bg/zhexiantu.png",
        "img/bg/zhexiantu.png",
        "img/bg/zhexiantu.png",
        "img/bg/zhexiantu.png",
      ],
    };
  },
  mounted() {
    this.init();
    let that = this;
    this.timer = setInterval(function () {
      that.init();
    }, 30000);
  },
  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    getChart(data) {
      this.chartOption = {
        tooltip: {
          trigger: "axis",
        },
        legend: {
          data: ["接口调用频率"]
        },

        grid: {
          left: "1%",
          bottom: "3%",
          containLabel: true,
        },

        xAxis: [
          {
            type: "category",
            data: data.hours,
            name: "时间（小时）",
            axisPointer: {
              type: "shadow",
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "调用次数",
            minInterval: 1,
            axisLabel: {
              formatter: "{value}",
            },
          },
        ],
        series: [
          {
            name: "接口调用频率",
            type: "line",
            smooth: true,
            itemStyle: {
              color: "#97db9c",
            },
            data: data.hourApiAmount,
          }
        ],
      };
    },
    // 时间格式化
    checkTime(i) {
      if (i < 10) {
        i = "0" + i;
      }
      return i;
    },
    // 初始化时间
    init() {
      getCallapi().then((res) => {
        this.apiData = res.data.data;
        this.getChart(this.apiData);
      });

    },
  },
};
</script>

<style lang="scss" scoped>
.top {
  padding-bottom: 0;
  /deep/.el-card__body {
    display: flex;
    align-items: center;
    .box-card {
      width: 300px;
      margin-right: 15px;
    }
  }
}
.box-card /deep/.el-card__body {
  padding: 10px 20px !important;
  .left {
    margin-right: 20px;
    padding-top: 4px;
  }
}
.bottom {
  display: flex;
  height: 430px;
  width: 100%;
  justify-content: space-between;
}
* {
  box-sizing: border-box;
}

.title-text {
  font-weight: 600;
}
</style>
