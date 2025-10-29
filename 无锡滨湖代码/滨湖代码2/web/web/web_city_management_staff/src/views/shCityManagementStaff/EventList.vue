<template>
  <div class="EventList">
    <div class="staff-title">
      <strong>溧水一网统管-融合指挥数据管理</strong>
    </div>
    <div class="event-table">
      <el-table height="1760" ref="ledMultipleTable"
        :data="tableData.slice((currentPage - 1)*pageSize,currentPage*pageSize)" :row-class-name="tableRowClassName"
        style="width: 100%">
        <el-table-column type="index" label="序号" width="160" :index="indexMethod">
        </el-table-column>
        <el-table-column prop="typeName" label="事件类型" width="320">
        </el-table-column>
        <el-table-column prop="level" label="事件等级" width="320">
        </el-table-column>
        <el-table-column prop="eventTitle" label="事件标题">
        </el-table-column>
        <el-table-column prop="happenTime" label="上报时间" width="420">
        </el-table-column>
        <el-table-column prop="address" label="地址" width="600">
        </el-table-column>
        <el-table-column prop="area" label="属地" width="400">
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <div class="event-dispose" @click="disposeClick(scope.$index,scope.row)">指挥</div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-content">
        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page.sync="currentPage" :page-size="pageSize" :page-sizes="[10, 20, 50, 80]"
          layout="sizes, prev, pager, next, total, jumper" :total="tableData.length">
        </el-pagination>
        <div class="confirm">确定</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '@/utils/request'

export default {
  name: 'EventList',
  data () {
    return {
      tableData: [],
      currentPage: 1,
      pageSize: 10
    }
  },
  watch: {
    '$store.getters.intervalRefresh': {
      handler (v) {
        this.getEventList()
      }
    }
  },
  mounted () {
    this.getEventList()
  },
  methods: {
    indexMethod (index) {
      index = (index + 1) + (this.currentPage - 1) * this.pageSize
      return index
    },
    disposeClick (index, row) {
      // console.log(index, row)
      window.open(`http://10.89.1.92:8084/#/ShCityManagement?eventId=${row.eventId}`)
    },
    getEventList () {
      let type = this.$route.query.isFalsity || ''
      let url = ''
      if (type) {
        url = '/mainApis/manage/queryEventInfo?isFalsity=' + type
      } else {
        url = '/mainApis/manage/queryEventInfo'
      }
      axios.get(url).then((res) => {
        let data = res.data.data
        this.tableData = data || []
        console.log(this.tableData)
      })
    },
    tableRowClassName ({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'even-row'
      } else if (rowIndex % 2 === 1) {
        return 'odd-row'
      }
      return ''
    },
    handleSizeChange (size) {
      this.pageSize = size
      console.log(`每页 ${size} 条`)
    },
    handleCurrentChange (currentPage) {
      this.currentPage = currentPage
      console.log(`当前页: ${currentPage}`)
    }
  }
}
</script>

<style lang="scss" scoped>
@mixin elPagination {
  /deep/ .el-input__icon {
    width: fit-content;
  }
  /deep/ .el-select {
    .el-input {
      width: 200px;
    }
  }
  /deep/ .el-pagination.is-background {
    .btn-next,
    .btn-prev,
    .el-pager li {
      background-color: transparent;
      color: #666666;
    }
  }
  /deep/ .el-dialog,
  /deep/ .el-pager {
    li {
      background: transparent;
      min-width: 60px;
    }
  }
  /deep/ .el-pagination.is-background .el-pager li:not(.disabled).active {
    background-color: #c8d9e6 !important;
    border-radius: 8px;
    color: #666666;
  }
  /deep/ .el-pagination__total,
  /deep/ .el-pagination__jump {
    color: #666666;
  }
  // 左右上下页按钮 禁用的时候隐藏
  /deep/ .el-pagination.is-background {
    .btn-prev:disabled,
    .btn-next.disabled,
    .el-pager li.disabled {
      display: none;
    }
    .btn-prev.disabled,
    .btn-next:disabled,
    .el-pager li.disabled {
      display: none;
    }
  }
}
.EventList {
  width: 100%;
  height: 100%;
  position: relative;
  .staff-title {
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
    height: 238px;
    line-height: 238px;
    z-index: 999;
    overflow: hidden;
    background-image: url("../../assets/global/title_bg@2x.png");
    background-size: 100% 100%;

    strong {
      margin-left: 80px;
      font-size: 72px;
      font-family: Microsoft YaHei, "PingFang SC", serif;
      font-weight: bold;
      color: rgba(255, 255, 255, 1);
      line-height: 72px;
      text-shadow: 0 8px 10px rgba(255, 255, 255, 0.7);

      background: linear-gradient(
        0deg,
        rgba(255, 255, 255, 1) 0.146484375%,
        rgba(90, 134, 188, 1) 99.0478515625%
      );
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
  }
  .event-table {
    width: 100%;
    padding: 0 20px;
    position: absolute;
    top: 238px;
    .event-dispose {
      color: #0c99db;
      cursor: pointer;
    }
    .pagination-content {
      height: 160px;
      width: 100%;
      position: relative;
      display: inline-flex;
      justify-content: flex-end;
      align-items: center;
      vertical-align: middle;
      @include elPagination;
      .confirm {
        width: 168px;
        height: 80px;
        background: rgba(255, 255, 255, 1);
        border: 1px solid rgba(12, 153, 219, 1);
        border-radius: 8px;
        text-align: center;
        line-height: 80px;
        color: #0c99db;
        cursor: pointer;
        margin-left: 36px;
      }
    }
  }
}
</style>
