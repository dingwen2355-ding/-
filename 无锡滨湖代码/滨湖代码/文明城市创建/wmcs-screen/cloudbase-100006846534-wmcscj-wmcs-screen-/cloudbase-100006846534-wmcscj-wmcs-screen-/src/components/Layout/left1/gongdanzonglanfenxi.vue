<script setup>
import { ref, onMounted, watch } from "vue";
import * as echarts from "echarts";
import axios from "axios";
import pinia from "@/store/store";
import { useStore } from "@/store/index";
const store = useStore(pinia);
let data = ref({
  totalCount: 0,
  todoCount: 0,
  finishCount: 0,
  extensionCount: 0
});
onMounted(() => {
  $axios_get(IP + "/screen/api/count/eventStatus").then(res => {
    data.value = res.data.data;
    loadBJL();
  });
});
function changeStatus() {
  store.$patch({
    heatmap: !store.heatmap
  });
}
function loadBJL() {
  var scale = 1;
  var echartData = [
    {
      value: data.value.finishCount,
      name: "办结"
    },
    {
      value: data.value.totalCount - data.value.finishCount,
      name: "未办结"
    }
  ];
  let option = {
    title: {
      text: "办结率",
      left: "center",
      top: "40%",
      padding: [15, 0],
      textStyle: {
        color: "#fff",
        fontSize: 18 * scale,
        align: "center"
      }
    },
    tooltip: {
      show: false
    },

    series: [
      {
        name: "办结率",
        type: "pie",
        radius: ["70%", "100%"],
        hoverAnimation: false,
        color: ["#3385FF", "#cccccc80"],
        data: echartData,
        label: {
          show: false,
          position: "left"
        },
        labelLine: {
          show: false
        }
      }
    ]
  };
  var chartDom = document.getElementById("gdzlfx-bjl");
  var myChart = echarts.init(chartDom);
  myChart.setOption(option);
}

let showEventList = ref(false);
let tableData = ref([{ jjdwmc: 111 }]);
let eventPage = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  statusMark: 1
});
function openEventList(statusMark) {
  showEventList.value = true;
  eventPage.value.pageNum = 1;
  eventPage.value.statusMark = statusMark;
  getEventList();
}
function getEventList() {
  $axios_get(IP + "/screen/api/index/eventByStatus", {
      params: {
        statusMark: eventPage.value.statusMark,
        pageNum: eventPage.value.pageNum,
        pageSize: eventPage.value.pageSize
      }
    })
    .then(res => {
      tableData.value = res.data.data.records;
      eventPage.value.total = res.data.data.total;
    });
}
</script>
<template>
  <div class="panel">
    <div class="top">
      <div class="left" @click="changeStatus()">
        <img src="@/assets/img/gongdan.png" alt="" />
        <div class="text">
          <span>工单总数</span>
          <span>{{ data.totalCount }} <span>件</span></span>
        </div>
      </div>
      <div class="right" id="gdzlfx-bjl"></div>
      <div class="t">
        {{ Number((data.finishCount / data.totalCount) * 100).toFixed(0) }}%
      </div>
    </div>
    <div class="bottom">
      <div class="item" @click="openEventList(1)">
        <img src="@/assets/img/chuzhizhong.png" alt="" />
        <div class="text">
          <span>处置中</span>
          <span style="color: #00a3ff"
            >{{ data.todoCount }} <span>件</span></span
          >
        </div>
      </div>
      <div class="item">
        <img src="@/assets/img/yanqishu.png" alt="" />
        <div class="text">
          <span>延期数</span>
          <span style="color: #fbd36d"
            >{{ data.extensionCount }} <span>件</span></span
          >
        </div>
      </div>
      <div class="item" @click="openEventList(3)">
        <img src="@/assets/img/banjieshu.png" alt="" />
        <div class="text">
          <span>办结数</span>
          <span style="color: #6bea8f"
            >{{ data.finishCount }} <span>件</span></span
          >
        </div>
      </div>
    </div>
  </div>
  <div class="event-list" v-if="showEventList">
    <div class="close" @click="showEventList = false">×</div>
    <div class="title">
      <div class="titlebg">事件列表</div>
    </div>
    <div class="tabcontent" ref="tabcontent">
      <el-table :data="tableData" :height="600" style="width: 100%">
        <el-table-column
          label="周期"
          width="100"
          prop="cycle"
        ></el-table-column>
        <el-table-column
          label="类型"
          prop="typeName"
          width="120"
        ></el-table-column>
        <el-table-column label="点位" prop="pointName"></el-table-column>
        <el-table-column
          label="所属板块"
          prop="regionName"
          width="120"
        ></el-table-column>
        <el-table-column
          label="责任单位"
          prop="deptNames"
        ></el-table-column>
        <el-table-column
          label="检查时间"
          prop="checkDate"
          width="170"
        ></el-table-column>
        <el-table-column
          label="状态"
          prop="status"
          width="140"
        ></el-table-column>
      </el-table>
    </div>
    <div class="pagination">
      <el-pagination
        v-model:current-page="eventPage.pageNum"
        layout="prev, pager, next"
        :total="eventPage.total"
        @current-change="getEventList()"
      />
    </div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  .top {
    width: 100%;
    height: 50%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    .t {
      position: absolute;
      top: 90px;
      right: 78px;
      color: #fff;
      font-size: 24px;
    }

    .left {
      width: 50%;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      cursor: pointer;

      img {
        width: 96px;
        height: 111px;
        position: relative;
        top: 10px;
      }

      .text {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        color: #fff;

        span:nth-child(1) {
          margin-top: 20px;
          font-size: 20px;
        }

        span:nth-child(2) {
          margin-top: 10px;
          font-size: 24px;
          font-weight: blod;

          span {
            color: #00a3ff;
            font-size: 14px;
          }
        }
      }
    }

    .right,
    #gdzlfx-bjl {
      width: 50%;
      height: 100px;
    }
  }

  .bottom {
    width: 100%;
    height: 40%;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;

    .item {
      width: 33%;
      display: flex;
      flex-direction: row;

      img {
        width: 50px;
        height: 50px;
      }

      .text {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;

        span:nth-child(1) {
          margin-top: 5px;
          font-size: 16px;
          color: #fff;
        }

        span:nth-child(2) {
          font-size: 16px;
          font-weight: blod;

          span {
            font-size: 12px;
          }
        }
      }
    }
  }
}
.event-list {
  position: absolute;
  top: 10px;
  left: 1278px;
  width: 1240px;
  height: 750px;
  background: #053475eb;
  z-index: 9999;
  .close {
    position: absolute;
    top: 0px;
    right: 20px;
    font-size: 36px;
    color: aqua;
    cursor: pointer;
  }
  .title {
    width: 100%;
    height: 48px;
    background: url("@/assets/img/title-m.png") no-repeat;
    background-size: cover;

    .titlebg {
      margin-left: 50%;
      transform: translateX(-50%);
      width: 360px;
      height: 48px;
      line-height: 48px;
      text-align: center;
      background: url("@/assets/img/titleline.png") no-repeat;
      background-size: 360px 13px;
      background-position-y: 18px;
      font-size: 20px;
      color: #fff;
    }
  }
  .tabcontent {
    padding: 15px;
  }
  :deep(.el-table) {
    background: none;
    color: #fff;
    font-size: 23px;
  }
  :deep(.el-table tr) {
    background-color: transparent;
  }
  :deep(.el-table thead) {
    color: #6fd6ff;
  }
  :deep(.el-table th.el-table__cell) {
    background-color: transparent;
  }
  :deep(
      .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell
    ) {
    background-color: transparent;
  }
  :deep(.el-table td.el-table__cell) {
    border-bottom: none;
  }
  :deep(.el-table th.el-table__cell.is-leaf) {
    border-bottom: none;
  }
  :deep(.el-table__inner-wrapper::before) {
    background-color: transparent;
  }
  :deep(.el-pager li) {
    color: #fff;
    background-color: #ffffff00 !important;
  }
  :deep(.el-pager li.is-active) {
    color: #48bfff;
  }
  :deep(.el-pagination button) {
    color: #fff;
    background-color: #ffffff00 !important;
  }
  .pagination {
    padding: 10px 0;
    width: 100%;
    display: flex;
    justify-content: center;
  }
}
</style>