<template>
  <el-dialog
    :title="'事件详情'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1000rem"
    height="800rem"
    class="eventDetail"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div>
      <ul>
       <li style="display: flex">
        <div style="width: 50%"><span>{{ tableData.src || '' }}人：</span>{{ tableData.fromname || '' }}</div>
        <div><span>{{ tableData.src || '' }}时间：</span>{{ tableData.fromtime || '' }}</div>
       </li>
       <li style="display: flex">
        <div style="width: 50%"><span>{{ tableData.src || '' }}内容分类：</span>{{ tableData.dcntaname || '' }}</div>
        <div><span>{{ tableData.src || '' }}人所在区县：</span>{{ tableData.fmareaname || '' }}</div>
       </li>
       <li style="display: flex">
        <div style="width: 50%"><span>{{ tableData.src || '' }}处理状态：</span>{{ tableData.statedesc || '' }}</div>
        <div><span>{{ tableData.src || '' }}类别：</span>{{ tableData.dfmclaname || '' }}</div>
       </li>
       <li><span>{{ tableData.src || '' }}人所在地址：</span>{{ tableData.fmaddress || '--' }}</li>
       <li>
       <div style="color: #6fd1ff;margin-bottom: 15rem">{{ tableData.src || '' }}内容：</div>
       <div style="white-space: pre-wrap;height: 300rem;overflow: auto">{{ tableData.fmcontent }}</div>
       </li>
       <li class="line"></li>
       <li style="display: flex">
        <div style="width: 50%"><span>{{ tableData.src || '' }}回复人：</span>{{ tableData.yworker || '' }}</div>
        <div><span>{{ tableData.src || '' }}工单号：</span>{{ tableData.wkfmno || '' }}</div>
       </li>
       <li><span>{{ tableData.src || '' }}回复时间：</span>{{ tableData.yedittime || '' }}</li>
       <li><span>{{ tableData.src || '' }}回复内容：</span>{{ tableData.ydealdesc || '' }}</li>
      </ul>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
export default {
  props: ['dialogVisibleFlag', 'infomation'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.formInline = {
          title: '',
          precinctId: '',
          time: '',
          type: ''
        }
        this.currentPage = 1
        this.pageSize = 10
        this.getEventList(this.infomation)
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      currentTime: '',
      tableData: {},
      newZ: '',
      newTime: '',
      type: 1,
      alarmType: 'xf',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      precinct: this.$route.query.precinctId,
      alarmList: sysConfig.area,
      formInline: {
        title: '',
        precinctId: '',
        time: '',
        type: ''
      },
      unitOptions: []
    }
  },
  methods: {
    getEventList(v) {
      let url = '/apis/warningInfo/get12345EventDetail?pkId=' + v.pkid + '&source=' + v.source
      Axios.get(url).then((res) => {
        this.tableData = res.data.data
      })
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss">
.eventDetail {
  ul li {
    margin-top: 20rem;
    font-size: 16rem;
    color: #ffffff;
    font-family: PingFang SC;
    font-weight: bold;
    span {
        display: inline-block;
        width: 130rem;
        color: #6fd1ff;
    }
  }
  ul .line {
    width: 100%;
    border: 1rem dashed #42a1e4;
  }
  .el-dialog__body {
    padding: 0 20rem 30rem;
    height: 730rem;
    overflow: auto;
  }
  .box-2 {
    ul {
      display: flex;
      float: right;
      li {
        width: 252rem;
        span {
          font-size: 16rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          &:nth-of-type(1) {
            color: #035561;
          }
        }
      }
    }

    .time-box {
      display: flex;
      padding: 25rem 0rem 0rem 55rem;
      .time-item {
        width: 76rem;
        // height: 28rem;
        background: rgba(61, 248, 248, 0.2);
        border: 2rem solid #035561;
        font-size: 16rem;
        line-height: 28rem;
        text-align: center;
        cursor: pointer;
      }
      .active {
        border: 2rem solid #00ffff;
        // background: linear-gradient(180deg, #28d9ff 0%, rgba(40, 217, 255, 0.2) 100%);
        background-image: radial-gradient(rgba(8, 36, 36, 0.2) 40%, rgba(89, 230, 240, 0.39));
        color: #00ffff;
      }
    }
  }
  #echart {
    width: 100%;
    height: 400rem;
  }
  .el-table th {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00ffff;
    background: #1c2331;
    height: 50rem;
  }
  .el-table td {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #ffffff;
    height: 67rem;
    background: #171d2b;
  }
  .el-table th.is-leaf,
  .el-table th.el-table__cell.is-leaf {
    border: 0 !important;
  }
  .el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #171d2b !important;
  }
  .el-table,
  .el-table__expanded-cell {
    background-color: #1c2331;
  }
  .el-table th.is-leaf {
    border-top: 1rem solid #28feff;
    border-bottom: 1rem solid #28feff;
  }
  .el-table td {
    border-bottom: 1rem solid #28feff;
  }
  .el-table::before {
    height: 0;
  }
  .el-table__body-wrapper {
    height: 410rem !important;
    overflow: auto;
  }
  .historyList .el-input__inner {
    font-size: 14rem;
    color: #fff;
  }
  .historyList .el-select .el-input__inner {
    width: 140rem;
    color: #fff;
  }
  .el-form--inline .el-form-item__label {
    font-size: 16rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00efff;
  }
  // .el-form--inline .el-form-item:nth-of-type(2) .el-form-item__content {
  //   width: 180rem;
  // }
  // .el-form--inline .el-form-item:nth-of-type(3) .el-form-item__content {
  //   width: 360rem;
  // }
  .el-range-editor .el-range-input {
    background: #0c223e;
    color: #fff;
    margin-left: 8rem;
  }
  .el-date-editor .el-range-separator {
    color: #fff;
  }
  .footer {
    margin-top: 27rem;
    text-align: center;
    .el-pagination__jump {
      color: #ffffff;
    }
  }
}
</style>
