<template>
  <div class="Department1">
    <div class="table-dec-warp">
      <div class="add-div">
      <!-- <div style="display: flex;" v-if="!precinctId">
          <el-select clearable v-model="value" placeholder="请选择区域" style="width: 320px;">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div> -->
        <div style="display: flex;margin-left: 50px;">
          <el-select clearable v-model="kind" placeholder="请选择设备类型" style="width: 350px;">
            <el-option
              v-for="item in options1"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <div style="display:flex;margin-left: 50px;">
            <el-input style="width: 500px"
            clearable
              placeholder="请输入名称"
              v-model="search">
            </el-input>
            <el-button icon="el-icon-search" @click="leftSearch"></el-button>
        </div>
      </div>
    </div>
    <div class="table-container">
      <el-table
        ref="multipleTable"
        height="1340"
        :data="tableData"
        :row-class-name="tableRowClassName"
        style="width: 100%"
      >
        <el-table-column prop="deviceName" label="名称"></el-table-column>
        <el-table-column prop="precinctName" label="所属区域"></el-table-column>
        <el-table-column
          prop="kindName"
          label="类型"
        ></el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <div class="div-item r">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from '@/utils/request'
export default {
  name: 'Department',
  components: { },
  data() {
    return {
      tableData: [],
      currentPage: 1,
      activeIndex: '1',
      pageSize: 10,
      showIndex: '',
      total: 0,
      dialogVisible: false,
      form: {
        emergencyPlanId: '',
        filename: '',
        fileurl: '',
        name: '',
        id: ''
      },
      planIdList: [],
      newList: [],
      hideUpload: false,
      search: '',
      value: '',
      precinctId: this.$store.getters.precinctId,
      options: sysConfig.area,
      multipleSelection: [],
      options1: [{ id: 31, name: '单兵' }, { id: 33, name: '视频会商' }, { id: 666, name: '城市之眼' }],
      kind: ''
    }
  },
  watch: {
    value: {
      handler(v) {
        this.currentPage = 1
        this.getEmergencyPlanOptions()
      }
    },
    kind: {
      handler(v) {
        this.currentPage = 1
        this.getEmergencyPlanOptions()
      }
    }
  },
  computed: {},
  mounted() {
    this.getEmergencyPlanOptions()
  },
  methods: {
    getEmergencyPlanOptions() {
      let url = '/apis/queryCameraInfoByType?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.search) {
        url += '&name=' + this.search
      }
      if (this.value) {
        url += '&precinctId=' + this.value
      }
      if (this.kind) {
        url += '&deviceKind=' + this.kind
      }
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.tableData = data.list || []
        this.total = data.count
      })
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'even-row'
      } else if (rowIndex % 2 === 1) {
        return 'odd-row'
      }
      return ''
    },
    leftSearch(e) {
      this.currentPage = 1
      this.getEmergencyPlanOptions()
      console.log(this.search)
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getEmergencyPlanOptions()
      console.log(`当前页: ${val}`)
    }
  }
}
</script>

<style lang="scss">
.Department1 {
  position: relative;
  width: 100%;
  padding: 55px 62px 0px;
  .el-button {
   font-size: 40px;
   line-height: 80px;
   padding: 0 40px;
 }
 .hide .el-upload--picture-card {
  display: none;
}
.el-input .el-input__clear {
  font-size: 36px;
}

  .table-dec-warp {
    line-height: 80px;
    margin-bottom: 40px;
    display: flex;
    justify-content: space-between;

    .ctrl-list {
      float: right;
      position: relative;

      .ctrl-btn {
        display: inline-block;
        width: 291px;
        height: 80px;
        text-align: center;
        background: rgba(255, 255, 255, 1);
        border: 2px solid;
        border-radius: 8px;
        margin: 0 20px;
        line-height: 76px;
        cursor: pointer;

        i {
          font-size: 40px;
        }

        &:first-child {
          color: #ff5858;
          border-color: #ff5858;
        }
      }
    }

    .add-div {
      position: relative;
      // margin-right: 800px;
      display: flex;

      .el-select {
        width: 738px;
      }
    }
  }

  .table-container {
    position: relative;
    /*height: 1680px;*/
  }
  .pagination-container {
    height: 80px;
    line-height: 80px;
    margin-bottom: 40px;

    .l {
      float: left;
    }
    .r {
      float: right;
    }
  }

  .submit-btn-warp {
    position: relative;
    text-align: center;

    .btn-item {
      margin: 0 40px;
    }
  }
  .AddRiskDialog {
    position: relative;
    width: 100%;
    height: 100%;
    .el-menu-item {
      font-size: 40px;
    }
    .el-menu-item:first-of-type {
      margin-left: 58px;
    }
    .el-menu.el-menu--horizontal {
      margin-top: 30px;
    }
    .el-select {
      width: 100%;
    }
    .form-container {
      padding: 80px;
      .nameSlot {
        margin-top: -8px;
        font-size: 35px;
        width: 260px;
        overflow: hidden;
        margin-left: 12px;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      .el-link--inner {
        font-size: 40px;
        margin-top: 20px;
      }
      .el-upload--picture-card {
          width: 300px;
          height: 300px;
          line-height: 300px;
      }
    .el-upload-list--picture-card .el-upload-list__item {
          width: 300px;
          height: 300px;
      }
      .el-upload-list--picture-card .el-upload-list__item-actions .el-upload-list__item-delete {
          font-size: 50px;
      }
    }

    .list {
      line-height: 80px;
      font-size: 40px;
      float: left;
    }
  }
}
</style>
