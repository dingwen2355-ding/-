<template>
  <div v-if="visible" class="parkdialog">
    <i class="close" @click="visibleDialog"></i>
    <p class="title-01">{{ parkData.parkname }}</p>
    <div class="park-info">
      <div class="all">
        <div>
          总车位数
          <div>
            <span>{{ parkData.spacecount }}</span
            >个
          </div>
        </div>
      </div>
      <div class="residue">
        <div>
          剩余车位数
          <div>
            <span>{{ parkData.freespacecount }}</span
            >个
          </div>
        </div>
      </div>
    </div>

    <div id="circularChartBox"></div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    parkId: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      parkData: {
        parkname: "-",
        spacecount: 200,
        freespacecount: 15,
        usecount: 185,
      },
      chartBox: null,
    };
  },
  watch: {
    visible(val) {
      if (!val) this.chartBox = null;
    },
  },
  methods: {
    getParkData(parkId) {
      request
        .post("api/binhu/getParkBynumber", { parknumber: parkId })
        .then(({ code, data }) => {
          if (code !== 200 || !data || data.length < 1) return;
          data[0]?.spacecount && (this.parkData = data[0]);
          this.initCircular();
        });
    },
    visibleDialog() {
      this.$emit("update:visible", false);
    },
    async initCircular() {
      // const chartBox = this.$echarts.init(
      //   document.getElementById("circularChartBox")
      // );
      if (!this.chartBox) {
        var chartBox = await this.$echarts.init(
          document.getElementById("circularChartBox")
        );
        this.chartBox = chartBox;
      }
      const parkData = this.parkData;
      const data = [
        {
          value: parkData.usecount,
          name: "已停放车位",
          itemStyle: { color: "#4EA0FF" },
        },
        {
          value: parkData.freespacecount,
          name: "剩余车位",
          itemStyle: { color: "#17FFF5" },
        },
      ];
      const option = {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          orient: "vertical", // 朝向
          icon: "circle", // 图形
          right: "right",
          top: "center",
          data: ["已停放车位", "剩余车位"],
          formatter: (name) => {
            let value;
            for (let i = 0; i < data.length; i++) {
              if (data[i].name === name) {
                value = `${data[i].value}`;
              }
            }
            const arr = [`{name|${name}}`, `{count|${value}}`];
            return arr.join("");
          },
          textStyle: {
            rich: {
              name: {
                fontSize: 15,
                width: 85,
                color: "#A0BDE0",
              },
              count: {
                fontSize: 12,
                color: "#fff",
              },
            },
          },
        },
        series: [
          {
            name: "车位占比",
            type: "pie",
            radius: ["60%", "85%"],
            center: ["30%", "50%"], // 位置
            data: data,
            itemStyle: {
              normal: {
                label: {
                  show: false,
                },
                labelLine: {
                  show: false,
                },
              },
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
          {
            // 配置中间文字样式
            z: 100,
            type: "gauge",
            radius: "-50%",
            center: ["30%", "50%"], // 需和type: 'pie'中的center一致
            pointer: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            splitLine: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            detail: {
              // 详情
              offsetCenter: [-1, 15],
              fontSize: 18,
              color: "#3D94FF",
              formatter: [`{total|${parkData.spacecount}} {unit|个}`].join(
                "\n"
              ),
              rich: {
                total: {
                  color: "#3D94FF",
                  fontSize: 18,
                },
                unit: {
                  color: "#A0BDE0",
                  fontSize: 15,
                },
              },
            },
            data: [
              {
                value: parkData.spacecount,
                name: "车位总数",
                title: {
                  show: true,
                  fontSize: 15,
                  color: "#fff",
                  offsetCenter: [0, "20%"],
                },
              },
            ],
          },
        ],
      };
      this.chartBox.setOption(option);
    },
  },
};
</script>

<style lang="scss" scoped>
.parkdialog {
  width: 360px;
  height: 337px;
  position: fixed;
  top: 20vh;
  left: 50%;
  transform: translateX(-50%);
  background-image: url("@/assets/park-supervision/img_windows_360_people_bg.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  .title-01 {
    margin-left: 45px;
    margin-top: 10px;
    font-size: 16px;
  }
  .park-info {
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-top: 40px;
    padding: 0 10px;
    & > div {
      background-repeat: no-repeat;
      width: 155px;
      height: 64px;
      font-size: 15px;
      color: #a0bde0;
      & > div {
        margin-left: 60px;
        margin-top: 12px;
        margin-bottom: 9px;
        & > div {
          margin-top: 2px;
        }
      }
    }
    & > div:nth-child(1) {
      background-image: url("@/assets/park-supervision/img_parkcar_data_bg3.png");
      span {
        font-size: 18px;
        color: #3d94ff;
        margin-right: 8px;
      }
    }
    & > div:nth-child(2) {
      background-image: url("@/assets/park-supervision/img_park_data_bg4.png");
      span {
        font-size: 18px;
        color: #1bc078;
      }
    }
  }

  #circularChartBox {
    height: 180px;
    width: 320px;
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
