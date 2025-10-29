<template>
  <div v-if="visible" class="left-bg">
    <div class="TOP">
      <IocTitle title="工单概览" @change="closedWin()" />

      <div class="Flex">
        <div class="way_img">
          <div class="Title" style="color: #38deff">民意速办</div>
        </div>

        <div class="AI_img">
          <div class="Title" style="color: #bcd1e9">AI监测</div>
        </div>
      </div>

      <div class="Flex">
        <div class="block_img">
          <div class="position">
            <div class="title">本月总数</div>
            <div class="number" style="color: #00d5ff">
              {{ MonthData }}<span>宗</span>
            </div>
          </div>
        </div>

        <div class="block_img">
          <div class="position">
            <div class="title">今日事件</div>
            <div class="number" style="color: #00d5ff">
              {{ EventData }}<span>宗</span>
            </div>
          </div>
        </div>

        <div class="block_img">
          <div class="position">
            <div class="title">今日办结</div>
            <div class="number" style="color: #1bc078">
              {{ CompletionData }}<span>宗</span>
            </div>
          </div>
        </div>
      </div>

      <div class="sel">
        <el-date-picker @change="handleDateChange" class="sel" style="width: 100%" v-model="isDataShow" type="month"
          placeholder="请选择年月" format="yyyy 年 MM 月"></el-date-picker>
      </div>

      <div class="block">
        <div class="Title">
          <h3>事件列表</h3>
        </div>

        <div ref="customList" class="block_list" v-infinite-scroll="loadMore" infinite-scroll-distance="20">
          <div v-if="GetEventList.length != 0">
            <div class="content" v-for="(item, index) in GetEventList" :key="index">
              <div class="Flex">
                <div class="serial">{{ index + 1 }}</div>

                <div class="information">
                  <div class="tooltip">
                    <div class="title">{{ item.content }}</div>
                    <div class="none-text">{{ item.content }}</div>
                  </div>

                  <div class="Flex">
                    <div class="block_">
                      <img class="img" src="@/assets/BusinessCircleEvent/img_tablelist_people_icon@2x.png" alt="名字" />
                      <div class="tooltip">
                        <div class="text">{{ item.sbr || '-' }}</div>
                        <div class="none-text">{{ item.sbr || '-' }}</div>
                      </div>
                    </div>

                    <div class="block_">
                      <img class="img" src="@/assets/BusinessCircleEvent/img_tablelist_time_icon@2x.png" alt="时间" />
                      <span class="time">{{ item.time }}</span>
                    </div>
                  </div>
                </div>

                <div class="state" style="color: #ffa076" v-if="item.status == 1">
                  待办
                </div>
                <div class="state" style="color: #1bc078" v-else-if="item.status == 0 || item.status == ''">
                  完成
                </div>
                <div class="state" v-else>-</div>
              </div>
            </div>
          </div>

          <div class="none" v-else>暂无数据</div>
        </div>
      </div>
    </div>

    <div class="Block_">
      <!-- 事件趋势 -->
      <div class="Block_IMG" v-show="this.category == 0">
        <div class="Flex">
          <div class="Title" @click="Event(0)">事件趋势</div>
          <div class="Title" style="color: #fff" @click="Event(1)">
            事件分类
          </div>
        </div>

        <div class="Echarts">
          <BusinessCircleEventLineChart></BusinessCircleEventLineChart>
        </div>
      </div>

      <!-- 事件分类 -->
      <div class="Block_IMG_" v-show="this.category == 1">
        <div class="Flex">
          <div class="Title" style="color: #fff" @click="Event(0)">
            事件趋势
          </div>
          <div class="Title" @click="Event(1)">事件分类</div>
        </div>

        <div class="Echarts">
          <BusinessCircleEventPie></BusinessCircleEventPie>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import IocTitle from "@/components/title/index";
import BusinessCircleEventLineChart from "./LineChart.vue";
import BusinessCircleEventPie from "./Pie.vue";

import { GetEventnum, GetEventList } from "@/utils/api/BusinessCircleEvent";

import { FenceTool } from "@/utils/tool/FenceTool.js";
import { MarkerTool } from "@/utils/tool/MarkerTool";

export default {
  name: "BusinessCircleEventLeft",

  components: {
    IocTitle,
    BusinessCircleEventLineChart,
    BusinessCircleEventPie,
  },

  data() {
    return {
      visible: true,
      category: 0,

      MonthData: 0,
      EventData: 0,
      CompletionData: 0,

      GetEventList: [],
      PageNo: 1,
      isDataUpdate: false, //滚动条，加载数据是否正在执行
      isDataShow: "",
    };
  },

  methods: {
    // 隐藏当前窗口
    closedWin() {
      this.visible = false;
    },

    // 事件切换
    Event(item) {
      console.log("事件切换", item);
      this.category = item;
    },

    getEventnum() {
      let that = this;
      for (let index = 1; index < 4; index++) {
        GetEventnum({
          type: index, //1本月总数 2今日事件 3今日办结
        }).then((res) => {
          console.log("-工单概览-请求到接口了------");
          console.log(res);
          if (res.code == 200) {
            if (index === 1) {
              that.MonthData = res.data;
            } else if (index === 2) {
              that.EventData = res.data;
            } else if (index === 3) {
              that.CompletionData = res.data;
            }
          }
        });
      }
    },

    //当前时间年月的数据
    getNewDateList() {
      const currentDate = new Date();

      const year = currentDate.getFullYear();
      const month = (currentDate.getMonth() + 1).toString().padStart(2, "0");

      const formattedDate = `${year}-${month}`;
      this.isDataShow = formattedDate;
      this.initPage(formattedDate);
      this.getEventList();
      this.sonEx(formattedDate);
    },

    //选择日期-年月的数据
    handleDateChange(date) {
      if (date instanceof Date) {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, "0");
        const formattedDate = `${year}-${month}`;

        this.initPage(formattedDate);
        this.getEventList();
        this.sonEx(formattedDate);
      }
    },

    //初始化 分页数据
    initPage(formattedDate) {
      this.PageNo = 1;
      this.isDataShow = formattedDate;
      this.GetEventList = [];
    },

    //显示两个图表数据
    sonEx(formattedDate) {
      var pieModel = this.findComponentDownward(this, "BusinessCircleEventPie");
      pieModel.getEventGroup(formattedDate);
      var ChartModel = this.findComponentDownward(this, "BusinessCircleEventLineChart");
      ChartModel.getEventTrends(formattedDate);
    },

    getEventList() {
      GetEventList({
        pageNo: this.PageNo,
        pageSize: 10,
        time: this.isDataShow,
      }).then((res) => {
        console.log("-事件列表-请求到接口了------");
        console.log(res);
        if (res.code == 200) {
          this.GetEventList = [...this.GetEventList, ...res.data];
          this.isDataUpdate = false;
        }
      });
    },

    scrollToTop() {
      this.$refs.customList.scrollTop = 0;
    },

    loadMore() {
      if (this.isDataUpdate == false) {
        this.isDataUpdate = true;
        let that = this;
        this.PageNo = this.PageNo + 1;
        this.$nextTick(() => {
          that.getEventList();
        });
      }
    }
  },

  created() {
    this.getEventnum();
  },

  beforeMount() {
    const markerTool = new MarkerTool();
    markerTool.Clear();
  },

  mounted() {
    this.getNewDateList();

    const markerTool = new MarkerTool();
    markerTool.ShowCityEventGps();
    markerTool.ShowCoverWarnningGps();

    const fence = new FenceTool();
    fence.Show();


  },

  destroyed() { // 销毁
    FenceTool.Clear();
  },
};
</script>

<style lang="scss" scoped>
.TOP {
  margin-top: 16px;

  .Flex {
    display: flex;

    .way_img {
      margin-top: 16px;
      background-image: url("@/assets/BusinessCircleEvent/img_tab_bg_left_act@2x.png");
      background-size: 100% 100%;
      width: 219px;
      height: 28px;
    }

    .AI_img {
      margin-top: 16px;
      background-image: url("@/assets/BusinessCircleEvent/img_lvhua_tab_bg@2x.png");
      background-size: 100% 100%;
      width: 219px;
      height: 28px;
    }

    .Title {
      font-size: 16px;
      line-height: 28px;
      text-align: center;
    }

    .block_img:nth-child(2) {
      background-image: url("@/assets/BusinessCircleEvent/icon_datashow_3col_c@2x.png");
    }

    .block_img:nth-child(3) {
      background-image: url("@/assets/BusinessCircleEvent/icon_data_3col_blue@2x.png");
      margin-right: 0;
    }

    .block_img {
      margin-top: 16px;
      background-image: url("@/assets/BusinessCircleEvent/icon_datashow_3col_b@2x.png");
      background-size: 100% 100%;
      width: 136px;
      height: 80px;
      margin-right: 9px;

      .position {
        margin: 18px 0 0 34px;

        .title {
          color: #ffffff;
          font-size: 15px;
          font-weight: 400;
        }

        .number {
          font-size: 18px;
          font-weight: 500;

          span {
            color: #a0bde0;
            font-size: 15px;
            margin-left: 8px;
          }
        }
      }
    }
  }

  .block {
    .Title {
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

    .block_list {
      margin-top: 8px;
      height: 275px;
      overflow-y: auto;

      .content {
        background-color: #1fa8ef33;
        height: 70px;
        position: relative;
      }

      .content:nth-child(even) {
        background-color: #1fa8ef1a;
      }

      .serial {
        color: #ffffff;
        font-size: 15px;
        font-weight: 400;
        display: flex;
        align-items: center;
        margin-left: 13px;
      }

      .information {
        margin: 8px 0 8px 20px;

        .title {
          color: #ffffff;
          font-size: 15px;
          font-weight: 400;
          margin-bottom: 16px;
          width: 250px;
          height: 20px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 1;
          -webkit-box-orient: vertical;
          white-space: normal !important;
          word-wrap: break-word;
        }

        .block_ {
          .img {
            width: 12px;
            height: 12px;
            margin-right: 8px;
          }

          .text {
            color: #bcd1e9;
            font-size: 14px;
            font-weight: 400;
            margin: -19px 0 0 20px;
            width: 110px;
            height: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
            white-space: normal !important;
            word-wrap: break-word;
          }

          .time {
            color: #bcd1e9;
            font-size: 14px;
            font-weight: 400;
          }
        }

        .tooltip {
          width: 160px;
        }

        .none-text {
          display: none;
          padding: 2px 6px;
          font-size: 14px;
          font-weight: 400;
          border-radius: 4px;
          color: #fff;
          background-color: #095261;
          border: 1px solid #00d5ff;
          position: absolute;
          bottom: 26px;
        }

        .tooltip:hover {
          .none-text {
            display: inline-block;
          }
        }
      }

      .state {
        font-size: 15px;
        font-weight: 400;
        position: absolute;
        right: 8px;
        bottom: 8px;
      }

      .none {
        font-size: 15px;
        text-align: center;
        margin: 25% 0;
      }
    }
  }
}

.Block_ {
  margin-top: 19px;

  .Block_IMG {
    background-image: url("@/assets/BusinessCircleEvent/img_tab_sele1@2x.png");
    background-size: 100% 100%;
    width: 428px;
    height: 82px;
  }

  .Block_IMG_ {
    background-image: url("@/assets/BusinessCircleEvent/img_tab_sele2@2x.png");
    background-size: 100% 100%;
    width: 428px;
    height: 82px;
  }

  .Echarts {
    height: 160px;
  }

  .Flex {
    display: flex;

    .Title {
      color: #00d5ff;
      font-size: 16px;
      font-weight: 700;
      line-height: 15px;
      margin-top: 9px;
      margin-left: 50px;
      cursor: pointer;
    }

    .Title:last-child {
      margin-left: 55px;
    }
  }
}

::v-deep .el-input__inner {
  background-color: transparent;
  border-radius: 0;
  border: 1px solid #00d5ff;
  color: #bcd1e9;
}
</style>

<style>
.sel {
  margin-top: 8px;
}

.el-popper .popper__arrow,
.el-popper .popper__arrow::after {
  border-style: none !important;
}

.el-picker-panel {
  color: #fff !important;
  border: 1px solid #00d5ff !important;
  box-shadow: 0 0 0 0 #00d5ff !important;
  background-color: #0952619b !important;
  border-radius: 0 !important;
}

.el-month-table td .cell {
  color: #fff;
}

.el-picker-panel__icon-btn {
  color: #ffffff;
}

.el-date-picker__header-label {
  color: #fff;
}

.el-month-table td.current:not(.disabled) .cell {
  color: #ffa076;
}
</style>