<template>
  <div class="right">
    <div class="c-title">报警记录</div>
    <ul class="warning-list-box scroll-container">
      <li class="r-item" v-for="(item, index) in alarmList" :key="index">
        <div>
          <div class="title">
            <span class="tip">普通</span>
            <!-- <span class="tip normal">普通</span> -->

            {{ item.alter_context }}
          </div>
          <span class="time">2023/06/29 10:44:52</span>
        </div>
        <div>
          <span class="adress">{{ item.location_desc }}</span>
          <span class="status success">已处理</span>
        </div>
      </li>
      <p style="margin: 20px; font-size: 16px" v-if="alarmList.length < 1">
        暂无数据
      </p>
    </ul>
    <el-pagination
      class="page-box"
      layout="total,prev, pager, next"
      @current-change="changePage"
      :total="total"
      :page-size="10"
      :page-count="1"
    >
    </el-pagination>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      alarmList: [],
      diviceCode: "",
      total: 0,
    };
  },
  methods: {
    // 改变页码
    changePage(val) {
      console.log(val);
    },
    async getData(code) {
      this.alarmList = [];
      this.diviceCode = code;
      await request
        .post("api/map/getCoverWarnningGpsDetail", {
          deviceCode: code,
        })
        .then(({ code, data }) => {
          if (code !== 200) return false; //
          this.alarmList = data;
          this.total = data.length;
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.warning-list-box {
  height: 430px;
}

.r-item {
  border-bottom: 1px dashed #bcd1e9;
  width: 356px;
  margin-top: 15px;

  & > div {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
  }

  .title {
    color: #00d5ff;
    font-size: 18px;
  }

  .tip {
    width: 32px;
    height: 23px;
    border: 1px solid #ffa076;
    background: #ffa07633;
    font-size: 12px;
    color: #ffa076;
    margin-right: 8px;
    padding: 0 2px;
  }

  .normal {
    border: 1px solid #00d5ff;
    background: #00d5ff33;
    color: #00d5ff;
  }

  .time {
    font-size: 15px;
    color: #bcd1e9;
  }

  .adress {
    width: 280px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    font-size: 16px;
    color: #ffffff;
  }

  .status {
    color: #ffffff;
    font-size: 12px;
    position: relative;
  }

  .success::before {
    background: #1bc078;
    width: 6px;
    height: 6px;
    border-radius: 50%;
    content: "";
    position: absolute;
    left: -10px;
    top: 6px;
  }

  .error::before {
    background: #ff506d;
    width: 6px;
    height: 6px;
    border-radius: 50%;
    content: "";
    position: absolute;
    left: -10px;
    top: 6px;
  }
}
.c-title {
  width: 364px;
  height: 28px;
  font-size: 16px;
  color: #00d5ff;
  line-height: 28px;
  padding-left: 10px;
  background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 100%);
}
.page-box {
  margin-top: 5px;
  width: 100%;
  display: flex;
  justify-content: flex-end;
  position: relative;

  ::v-deep .el-pagination__total {
    position: absolute;
    left: 0;
    color: #fff;
  }

  ::v-deep .btn-prev {
    background: #606465;
    color: #fff;
    width: 28px;
    min-width: 28px;
    border-radius: 50%;
  }

  ::v-deep .btn-next {
    background: #606465;
    color: #fff;
    width: 28px;
    min-width: 28px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  ::v-deep .el-pager li {
    background: transparent;
    // border-radius: 50%;
    color: #fff;
  }

  ::v-deep .el-pager {
    .number {
      width: 28px;
      min-width: 28px;
      height: 28px;
      border-radius: 50%;
    }

    .active {
      background: #409eff;
    }
  }
}
</style>
