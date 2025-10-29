<template>
  <div>
    <el-row>
      <el-col :span="24">
        <basic-container class="topuser">
          <div class="userinfo">
            <i class="el-icon-s-custom"></i>
            <div>
              <div class="name">{{ userData.name }}</div>
              {{ userData.dept }}/{{ userData.position }}
            </div>
          </div>
          <div class="date">
            <span>{{ timeArr[1] }}</span>
            <span style="margin-left: 10px">{{ timeArr[0] }}</span>
          </div>
        </basic-container>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16">
        <basic-container class="module">
          <div class="module-title">数据简报</div>
          <div class="dataCount">
            <div v-for="item in countData" :key="item.name" class="each-data">
              <div class="left">
                {{ item.name }}
                <div class="data">{{ item.number }}</div>
                <div class="percent" :class="item.percent < 0 ? 'active' : ''">
                  {{ item.percent }}%
                </div>
              </div>
              <div class="right">
                <img :src="item.src" alt="" />
              </div>
            </div>
          </div>
        </basic-container>
        <basic-container class="module">
          <div class="module-title">活跃趋势</div>
          <e-chart
            :options="chartOption"
            style="width: 100%; height: 300px; padding-bottom: 15px"
          ></e-chart>
        </basic-container>
      </el-col>
      <el-col :span="8">
        <basic-container class="module">
          <div class="module-title">应用排名</div>
          <el-table :data="tableData" style="width: 100%" height="400">
            <el-table-column label="排名" width="50">
              <template slot-scope="{ $index }">
                <span class="ranking" :class="$index < 3 ? 'active' : ''">
                  {{ $index + 1 }}
                </span>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="应用名称"
              width="200"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column prop="number" label="访问次数" width="100">
            </el-table-column>
            <el-table-column label="占比">
              <template slot-scope="{ row, $index }">
                <el-progress
                  v-if="$index == 0"
                  :percentage="row.percent"
                  color="#f96163"
                ></el-progress>
                <el-progress
                  v-else-if="$index == 1"
                  :percentage="row.percent"
                  color="#fda853"
                ></el-progress>
                <el-progress
                  v-else-if="$index == 2"
                  :percentage="row.percent"
                  color="#51cecf"
                ></el-progress>
                <el-progress v-else :percentage="row.percent"></el-progress>
              </template>
            </el-table-column>
          </el-table>
        </basic-container>
        <basic-container class="module">
          <div class="module-title">快捷菜单</div>
          <ul class="fastmenu">
            <router-link tag="li" to="/organization/userManage">
              <i class="el-icon-user"></i>
              <div>用户列表</div>
            </router-link>
            <router-link tag="li" to="/application/management">
              <i class="el-icon-copy-document"></i>
              <div>应用列表</div>
            </router-link>
            <router-link tag="li" to="/message/send">
              <i class="el-icon-position"></i>
              <div>短信发送</div>
            </router-link>
          </ul>
        </basic-container>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getBulletin,
  getAppRecordRank,
  getYearActive,
  getUserInfo,
} from "@/api/home";

export default {
  name: "wel",
  data() {
    return {
      userData: {},
      countData: [],
      timer: null,
      timeArr: [],
      tableData: [],
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
    getUserInfo().then((res) => {
      this.userData = res.data.data;
    });
    getBulletin().then((res) => {
      this.countData = res.data.data;
      this.countData.forEach((item, index) => {
        item.src = this.srcList[index];
      });
    });
    getAppRecordRank().then((res) => {
      this.tableData = res.data.data;
    });
    getYearActive().then((res) => {
      this.getChart(res.data.data);
    });
    this.init();
    let that = this;
    this.timer = setInterval(function () {
      that.init();
    }, 1000);
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
          data: ["用户新增数量", "用户登录次数"],
          bottom: 0,
        },
        xAxis: [
          {
            type: "category",
            data: data.time,
            axisPointer: {
              type: "shadow",
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "",
            // min: 0,
            // max: 250,
            // interval: 200,
            axisLabel: {
              formatter: "{value}",
            },
          },
        ],
        series: [
          {
            name: "用户新增数量",
            type: "line",
            smooth: true,
            itemStyle: {
              color: "#70cbf4",
            },
            data: data.createUserNum,
          },
          {
            name: "用户登录次数",
            type: "line",
            smooth: true,
            itemStyle: {
              color: "#97db9c",
            },
            data: data.loginNum,
          },
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
      this.timeArr = [];
      var today = new Date(); //定义日期对象
      var yyyy = today.getFullYear(); //通过日期对象的getFullYear()方法返回年
      var MM = today.getMonth() + 1; //通过日期对象的getMonth()方法返回年
      var dd = today.getDate(); //通过日期对象的getDate()方法返回年
      var hh = today.getHours(); //通过日期对象的getHours方法返回小时
      var mm = today.getMinutes(); //通过日期对象的getMinutes方法返回分钟
      var ss = today.getSeconds(); //通过日期对象的getSeconds方法返回秒
      // 如果分钟或小时的值小于10，则在其值前加0，比如如果时间是下午3点20分9秒的话，则显示15：20：09
      MM = this.checkTime(MM);
      dd = this.checkTime(dd);
      mm = this.checkTime(mm);
      ss = this.checkTime(ss);
      this.timeArr.push(hh + ":" + mm + ":" + ss);
      this.timeArr.push(yyyy + "-" + MM + "-" + dd);
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/ .topuser {
  .el-card__body {
    padding: 20px 50px;
    position: relative;
  }
}
.userinfo {
  display: flex;
  align-items: center;
  i {
    font-size: 30px;
    margin-right: 20px;
  }
  div {
    font-size: 14px;
    color: #999999;
  }
  div.name {
    font-size: 18px;
    color: #666;
    font-weight: 700;
  }
}
/deep/.el-progress {
  width: 80%;
}
/deep/.el-progress-bar {
  margin-right: 0;
  padding-right: 0;
}
.date {
  font-size: 15px;
  position: absolute;
  top: 20px;
  right: 50px;
}
/deep/ .module {
  .el-card__body {
    padding: 0;
  }
  .module-title {
    background-color: rgba(249, 249, 249, 1);
    border-bottom: 1px solid rgba(233, 233, 233, 1);
    font-size: 16px;
    font-weight: 700;
    color: #333;
    padding: 15px;
  }
  .dataCount {
    display: flex;
    flex-wrap: wrap;
    height: 210px;
    padding: 20px 20px 10px 20px;
    justify-content: space-between;
    .each-data {
      border-radius: 2px;
      width: 20%;
      margin-bottom: 20px;
      font-weight: 400;
      font-size: 14px;
      color: #999;
      display: flex;
      box-shadow: -2px 0px 3px rgba(249, 249, 249, 0.5),
        0px -2px 5px rgba(226, 226, 226, 0.5),
        5px 5px 5px rgba(226, 226, 226, 0.5),
        0px -2px 5px rgba(226, 226, 226, 0.5);
      align-items: center;
      padding: 10px 15px;

      .left {
        width: 65%;
      }
      .right {
        width: 35%;
      }
      .data {
        font-weight: 500;
        color: black;
        font-size: 26px;
        margin: 3px 0;
      }
      .percent {
        font-size: 12px;
        color: #19be6b;
      }
      .percent.active {
        color: #f56c6c;
      }
    }
    li:nth-child(4n) {
      border-right: 0;
    }
  }
  .ranking {
    display: inline-block;
    color: #fff;
    background: #ccc;
    font-weight: 700;
    height: 26px;
    width: 26px;
    text-align: center;
    line-height: 26px;
    border-radius: 26px;
  }
  .ranking.active {
    background: #0e53d7;
  }
  .fastmenu {
    display: flex;
    li {
      list-style: none;
      width: 33%;
      text-align: center;
      font-size: 13px;
      color: #333;
      padding: 16px 0;
      cursor: pointer;
      i {
        font-size: 50px;
        color: #999;
        margin-bottom: 10px;
      }
    }
  }
}
</style>
