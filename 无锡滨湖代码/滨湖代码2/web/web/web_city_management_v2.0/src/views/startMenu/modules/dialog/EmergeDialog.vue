<template>
  <el-dialog
    :title="tableData.dispatchBasis || '成都市重污染天气应急预案（2022年版）'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1431rem"
    height="600rem"
    class="EmergeDialog"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div>
      <el-table :data="tableData.requirements" :span-method="objectSpanMethod">
        <el-table-column prop="level" label="预警等级" width="200"></el-table-column>
        <el-table-column prop="dealUnit" label="预警处置单位" width="200"></el-table-column>
        <el-table-column label="工作要求">
        <template slot-scope="scope">
          <span v-html="scope.row.requirement"></span>
        </template>
        </el-table-column>
      </el-table>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
export default {
  props: ['dialogVisibleFlag', 'infomation'],
  watch: {
    dialogVisibleFlag(v) {
      if (v) {
        this.dialogVisible = v
        this.tableData = {}
        this.getTable(this.infomation)
        console.log(this.infomation)
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
      alarmType: 'xf',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      precinct: this.$route.query.precinctId,
      alarmList: sysConfig.area,
      messageVisible: false,
      dialogPhoneVisible: false,
      dialogPhoneData: {},
      dialogMessageData: {}
    }
  },
  methods: {
    flitterData(arr) {
      let spanOneArr = []
      let concatOne = 0

      arr.forEach((item, index) => {
        if (index === 0) {
          spanOneArr.push(1)
        } else {
          if (item.level === arr[index - 1].level) {
            // 第一列根据相同name合并
            spanOneArr[concatOne] += 1
            spanOneArr.push(0)
          } else {
            spanOneArr.push(1)
            concatOne = index
          }
        }
      })
      return {
        one: spanOneArr
      }
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        const _row = this.flitterData(this.tableData.requirements).one[rowIndex]
        const _col = _row > 0 ? 1 : 0
        return {
          rowspan: _row,
          colspan: _col
        }
      }
    },
    getTable(v) {
      let url = '/apis/importPlan/query'
      if (v && v.importPlanId) {
        url += '?planId=' + v.importPlanId
      }
      Axios.get(url).then((r) => {
        this.tableData = r.data.data[0]
        console.log(this.tableData)
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
.EmergeDialog {
  .el-dialog__title {
    font-size: 16rem;
    white-space: nowrap;
    width: 365rem;
    background-size: 100% 100%;
    padding-left: 48rem;
  }
  .el-dialog__body {
    padding: 0 20rem 30rem;
    height: 740rem;
    overflow: hidden;
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
    border-bottom: 0 !important;
    border-right: 10rem solid #05192d;
    text-align: center;
  }
  .el-table td {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #9cadc7;
    height: 67rem;
    background: #0f203a;
    border-right: 10rem solid #05192d !important;
    border-top: 10rem solid #05192d !important;
    text-align: center;
  }
  .el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #0f203a;
  }
  .el-table,
  .el-table__expanded-cell {
    background-color: #1c2331;
  }
  // .el-table th.is-leaf {
  //   border-top: 1rem solid #28feff;
  //   border-bottom: 1rem solid #28feff;
  // }
  // .el-table td {
  //   border-bottom: 1rem solid #28feff;
  // }
  .el-table::before {
    height: 0;
  }
  .el-table__body-wrapper {
    height: 660rem !important;
    overflow-y: auto;
    background: #05192d;
    .el-table__row .el-table__cell:last-of-type {
        text-align: left;
    }
  }
  ::-webkit-scrollbar {
    // 滚动条整体
    display: block !important;
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
