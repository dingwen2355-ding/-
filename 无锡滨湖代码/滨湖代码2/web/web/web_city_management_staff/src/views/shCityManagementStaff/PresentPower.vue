<template>
  <section class="present-power">
    <div class="table-dec-warp">
      <div class="ctrl-list">
        <div class="ctrl-btn" @click="batchDelete"><i class="el-icon-delete-solid"></i>批量删除</div>
      </div>
      <div class="add-div">
        <div class="border-btn" @click="addListItem"><i class="el-icon-plus"></i> 新增</div>
      </div>
    </div>
    <el-table height="1340" ref="powerMultipleTable" :data="showData" :row-class-name="tableRowClassName" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="100"> </el-table-column>
      <el-table-column type="index" label="序号" width="160" :index="indexMethods"> </el-table-column>
      <el-table-column label="力量部门">
        <template slot-scope="scope">
          <el-select v-model="scope.row.unitName" placeholder="请选择" filterable allow-create @change="unitNameChange(scope.row)">
            <el-option v-for="item in unitOptions" :key="'power' + item.id" :label="item.unitName" :value="item.unitName"> </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="力量类型">
        <template slot-scope="scope">
          <el-select v-model="scope.row.resourceName" placeholder="请选择" filterable allow-create @change="resourceNameChange(scope.row)">
            <el-option v-for="item in resourceOptions" :key="item.resourceName" :label="item.resourceName" :value="item.resourceName"> </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="arriveTime" label="到达时间">
        <template slot-scope="scope">
          <el-date-picker v-model="scope.row.arriveTime" :format="'yyyy-MM-dd HH:mm:ss'" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"> </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column prop="count" label="到达数量">
        <template slot-scope="scope">
          <k-edit-text v-model="scope.row.count"></k-edit-text>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="数量单位">
        <template slot-scope="scope">
          <k-edit-text v-model="scope.row.type"></k-edit-text>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <div class="link-btn">
            <i class="el-icon-delete-solid" style="color: red" @click="deleteClick(scope.$index, scope.row)"></i>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <div class="div-item l">
        <el-radio-group>
          <el-checkbox :true-label="'0'" v-model="powerCheckbox">全选</el-checkbox>
          <el-checkbox :true-label="'1'" v-model="powerCheckbox">反选</el-checkbox>
        </el-radio-group>
      </div>
      <div class="div-item r">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="tableData.length"
        ></el-pagination>
      </div>
    </div>
    <div class="submit-btn-warp">
      <div class="link-btn btn-item" @click="cancelSubmit">取消</div>
      <div class="blue-btn btn-item" @click="submitResult">提交</div>
    </div>
  </section>
</template>
<script>
import axios from '@/utils/request'
import KEditText from '../../components/KEditText'

export default {
  name: 'PresentPower',
  data() {
    return {
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      powerCheckbox: '',
      resourceOptions: [],
      unitOptions: []
    }
  },
  components: {
    KEditText
  },
  watch: {
    '$store.getters.eventId': {
      handler(v) {
        this.$nextTick(() => {
          this.eventId = v.id
          this.query()
        })
      },
      immediate: true
    },
    powerCheckbox: {
      handler(v) {
        this.$nextTick(() => {
          if (v === '0') {
            this.$refs.powerMultipleTable.clearSelection()
            this.$refs.powerMultipleTable.toggleAllSelection()
          } else if (v === '1') {
            this.tableData.forEach((row) => {
              this.$refs.powerMultipleTable.toggleRowSelection(row)
            })
          } else {
            this.$refs.powerMultipleTable.clearSelection()
          }
        })
      },
      immediate: true
    }
  },
  computed: {
    showData: {
      get() {
        return this.tableData.slice(
          (this.currentPage - 1) * this.pageSize,
          this.currentPage * this.pageSize
        )
      },
      set(v) {
        this.stepMap = v
      }
    }
  },
  methods: {
    indexMethods(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    unitNameChange(row) {
      row.unitId = +new Date()
      this.unitOptions.forEach((item) => {
        if (item.unitName === row.unitName) {
          row.unitId = item.id
        }
      })
    },
    resourceNameChange(row) {
      row.resourceId = ''
      this.resourceOptions.forEach((item) => {
        if (item.resourceName === row.resourceName) {
          row.resourceId = item.id
        }
      })
    },
    query() {
      if (!this.$store.getters.eventId.id) return
      const url = '/apis/queryArrivedResourceList?eventId=' + this.$store.getters.eventId.id
      axios.get(url).then((r) => {
        let data = r.data.data
        this.tableData = data
        // this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
      })
    },
    batchDelete() {
      let selectList = this.multipleSelection.map((item) => {
        return item.id
      })
      let dataArr = []
      this.tableData.map((item, index) => {
        if (selectList.indexOf(item.id) === -1) {
          dataArr.push(item)
        }
      })
      this.tableData = dataArr
      if (this.tableData.length % this.pageSize === 0 && this.currentPage > Math.ceil(this.tableData.length / this.pageSize)) {
        this.currentPage -= 1
      }
      // this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    },
    addListItem() {
      this.$root.eventBus.$emit('showAddPowerDialog', this.eventId)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'even-row'
      } else if (rowIndex % 2 === 1) {
        return 'odd-row'
      }
      return ''
    },
    deleteClick(index, row) {
      this.tableData.splice(index, 1)
      if (this.tableData.length % this.pageSize === 0 && this.currentPage > Math.ceil(this.tableData.length / this.pageSize)) {
        this.currentPage -= 1
      }
      // this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    },
    cancelSubmit() {
      this.query()
    },
    async submitResult() {
      let list = [...this.tableData]
      for (let value of list) {
        if (!/(^[1-9]\d*$)/.test(value.count)) {
          this.$message.error('到达数量只能输入数字')
          return
        }
      }
      list.forEach((v) => {
        if (isNaN(v.resourceId)) {
          v.resourceName = v.resourceId
          v.resourceId = ''
        }
      })
      let obj = {
        eventId: this.eventId,
        lists: list
      }
      try {
        // await getSubmitEventInfoList(obj)
        axios.post('/apis/saveArrivedResource', obj).then((r) => {
          if (r.data.code !== 200) {
            this.$message.error(r.data.message)
          } else {
            this.$message({
              message: '提交成功！',
              type: 'success'
            })
            this.queryUnit()
            this.queryResource()
            this.$root.eventBus.$emit('refreshIframe')
          }
        })
      } catch (error) {
        console.log(error)
      }
    },
    handleSizeChange(val) {
      this.pageSize = val
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.$nextTick(() => {
        this.showData = this.tableData.slice(
          (this.currentPage - 1) * this.pageSize,
          this.currentPage * this.pageSize
        )
      })
      console.log(`当前页: ${val}`)
    },
    queryUnit() {
      const url = '/apis/queryUnitList'
      axios.get(url).then((r) => {
        this.unitOptions = r.data.data
      })
    },
    queryResource() {
      const url = '/apis/queryResourceTypeList'
      axios.get(url).then((r) => {
        r.data.data.forEach((v) => {
          v.id = v.id.toString()
        })
        this.resourceOptions = r.data.data
      })
    }
  },
  mounted() {
    this.queryUnit()
    this.queryResource()
    this.$root.eventBus.$on('addNewData', (data) => {
      // this.queryUnit()
      // this.queryResource()
      this.tableData.push(data[0])
      // this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
      console.log(this.tableData)
      // setTimeout(() => {
      //   this.query()
      // }, 1000)
    })
  }
}
</script>
<style lang="scss">
.present-power {
  .table-dec-warp {
    line-height: 80px;
    margin-bottom: 40px;

    .ctrl-list {
      float: right;
      position: relative;

      .ctrl-btn {
        display: inline-block;
        width: 291px;
        height: 80px;
        text-align: center;
        background: rgba(255, 255, 255, 1);
        border: 2px solid #ff5858;
        border-radius: 8px;
        margin: 0 20px;
        line-height: 76px;
        cursor: pointer;
        color: #ff5858;

        i {
          font-size: 40px;
        }
      }
    }

    .add-div {
      position: relative;
      margin-right: 800px;

      .el-select {
        width: 738px;
      }
    }
  }

  .submit-btn-warp {
    position: relative;
    text-align: center;

    .btn-item {
      margin: 0 40px;
    }
  }
}
</style>
