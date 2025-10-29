<template>
  <section class="StorageOutInManage">
    <el-row class="header">
      <el-form :inline="true" :model="formInline">
        <el-form-item label="所属辖区">
          <el-select clearable v-model="formInline.region" placeholder="所属辖区">
            <el-option v-for="(v, i) in regionList" :key="i" :label="v.name" :value="v.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库名称">
          <el-input v-model="formInline.stockName" placeholder="请输入仓库名称"></el-input>
        </el-form-item>
        <el-form-item label="仓库类型">
          <el-select clearable v-model="formInline.stockType" placeholder="请输入仓库类型">
            <el-option v-for="(v, i) in stockTypeList" :key="i" :label="v.name" :value="v.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="完成状态">
          <el-select clearable="" v-model="formInline.status" placeholder="操作类型">
            <el-option label="已完成" value="已完成"></el-option>
            <el-option label="未完成" value="未完成"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="核对时间">
          <el-date-picker
            style="width: 433px"
            v-model="datetimerange"
            type="datetimerange"
            value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            @change="datetimerangeChange"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-upload
            ref="upload"
            style="display: inline-block"
            class="upload-demo"
            action="/"
            :file-list="fileList"
            :show-file-list="false"
            :on-change="handleChange"
            :auto-upload="false"
          >
            <el-button type="primary" plain>Excel上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item style="float: right">
          <a class="downloadBtn" href="/template/物资出入库信息登记模板.xlsx" download="物资出入库信息登记模板">
            <!--  href="../../../../../static/物资出入库信息登记模板.xlsx" -->
            <span>Excel模版下载</span>
          </a>
        </el-form-item>
      </el-form>
    </el-row>
    <div class="scrollArea">
      <el-table
        v-loading="loading"
        :data="tableData"
        :expand-row-keys="expandList"
        :row-key="getRowKey"
        @expand-change="getListData"
        style="width: 100%"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-table
              border
              v-loading="loadingInner"
              :data="
                listData[props.row.stockId + props.row.checkTime || '']
                  ? listData[props.row.stockId + props.row.checkTime || '']
                  : []
              "
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="50"> </el-table-column>

              <el-table-column prop="stuffName" label="物资名称" width="220"> </el-table-column>

              <el-table-column prop="model" label="物资型号" width="220"> </el-table-column>

              <el-table-column prop="brand" label="物资品牌" width="220"> </el-table-column>

              <el-table-column prop="stuffUnit" label="计量单位" width="220"> </el-table-column>

              <el-table-column prop="stuffNumber" label="物资数量" width="220"> </el-table-column>

              <el-table-column prop="checkTime" label="核对时间" width="220">
                <template slot-scope="scope">
                  {{ scope.row.checkTime ? scope.row.checkTime : '暂无' }}
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>

        <el-table-column prop="file_name" type="index" label="序号"> </el-table-column>
        <el-table-column prop="region" label="所属辖区"> </el-table-column>
        <el-table-column prop="stockName" label="仓库名称"> </el-table-column>
        <el-table-column prop="stockType" label="仓库类型">
          <template slot-scope="scope">
            {{ stockTypeObj[scope.row.stockType] }}
          </template>
        </el-table-column>
        <el-table-column prop="operationCount" label="核对时间">
          <template slot-scope="scope">
            {{ scope.row.checkTime ? scope.row.checkTime : '暂无' }}
          </template>
        </el-table-column>
        <el-table-column prop="schedule" label="完成进度"> </el-table-column>
        <el-table-column prop="status" label="完成状态">
          <template slot-scope="scope">
            <span :class="scope.row.status === '未完成' ? 'colorRed' : 'colorGreen'">{{ scope.row.status }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="data-title">
      共<span class="blue">{{ total }}</span
      >条数据 | 最近更新时间：{{ updateTime }}
    </div>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="formInline.page"
      :page-sizes="[50, 100, 500, 1000]"
      :page-size="formInline.pageSize"
      layout="sizes,prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </section>
</template>

<script>
// 物资出入库管理
import axios from 'axios'
export default {
  name: 'StorageOutInManage',
  components: {},
  data() {
    return {
      loading: false,
      loadingInner: false,
      total: 0,
      userId: localStorage.getItem('userId'),
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: configs.wareHouseLevel,
      regionList: [],
      expandList: [],
      formInline: {
        region: '',
        stockType: '',
        status: '',
        stockName: '',
        userName: '',
        operationType: '',
        checkStartTime: '',
        checkEndTime: '',
        page: 1,
        pageSize: 50
      },
      stockTypeList: [
        { id: 1, name: '物资库' },
        { id: 2, name: '装备库' },
        { id: 4, name: '救援队伍' },
        { id: 5, name: '社会力量' },
        { id: 6, name: '监管企业' }
      ],
      tableData: [],
      listData: {},
      updateTime: '',
      datetimerange: [],
      fileList: [],
      file: null,
      dateTest: ['2022-04-08', '2022-04-07', '2022-04-02', '2022-03-28', '2022-03-26'],
      operaDayList: []
    }
  },
  mounted() {
    this.getTableData()
    this.queryRegion()
  },
  computed: {
    stockTypeObj() {
      let obj = {}
      this.stockTypeList.forEach((item) => {
        obj[item.id] = item.name
      })
      return obj
    }
  },
  watch: {
    expandList(v) {
      // this.expandList.forEach(item => {
      //   this.getListData(item)
      // })
      // console.log(v)
    }
  },
  methods: {
    onSubmit() {
      this.formInline.page = 1
      this.getTableData()
    },
    getRowKey(row) {
      return row.stockId + row.checkTime || ''
    },
    handleDel(row) {
      if (localStorage.level === '300') {
        this.$message('仓库管理员不能进行删除操作!')
      } else {
        this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let url = `/gtw/EIMS-management/warehouse/deleteOperationRecordById?stockId=${row.warehouse}&userId=${row.user_id}`
            axios.get(url).then((r) => {
              if (r.data.code === 200) {
                this.$message.success('删除成功')
                this.getTableData()
              } else {
                this.$message(r.data.message)
              }
            })
          })
          .catch(() => {
            this.$message('已取消')
          })
      }
    },
    handleSizeChange(v) {
      this.formInline.pageSize = v
      this.formInline.page = 1
      this.getTableData()
    },
    handleCurrentChange(val) {
      this.getTableData()
    },
    queryRegion() {
      let url = `/gtw/EIMS-management/stockInfoData/getRegion?userLevel=${localStorage.level}&userId=${localStorage.userId}`
      axios.get(url).then((r) => {
        this.regionList = r.data.data.data
      })
    },
    datetimerangeChange(val) {
      // console.log(val)
      if (val) {
        this.formInline.checkStartTime = val[0]
        this.formInline.checkEndTime = val[1]
      } else {
        this.formInline.checkStartTime = ''
        this.formInline.checkEndTime = ''
      }
    },
    handleChange(file, fileList) {
      this.file = file
      this.fileList = fileList
      this.importExcel()
    },
    importExcel() {
      // console.log(localStorage.userId)
      if (!localStorage.userId) return
      let url = `/gtw/EIMS-management/warehouse/stuffExcelAnalysis?userLevel=${localStorage.level}`
      this.file = this.fileList[this.fileList.length - 1]
      // this.$refs.upload.clearFiles()
      const types = this.file.name.split('.')[1]
      const fileType = ['xlsx', 'xlc', 'xlm', 'xls', 'xlt', 'xlw', 'csv'].some((item) => item === types)
      if (!fileType) {
        this.$message('格式错误！请重新选择')
        return
      }
      let formData = new FormData()
      formData.append('file', this.file.raw)
      // formData.append('userId', localStorage.userId)
      axios
        .post(url, formData)
        .then((res) => {
          if (res.data.code === 201 || res.data.code === 200) {
            this.$message({
              type: 'success',
              message: 'Excel上传成功'
            })
            this.$refs.upload.clearFiles()
            this.getTableData()
          } else {
            this.$message({
              type: 'error',
              message: res.data.data || 'Excel上传失败'
            })
            this.$refs.upload.clearFiles()
          }
        })
        .catch((err) => {
          // console.log(err)
          this.$message({
            type: 'error',
            message: err.message || 'Excel上传失败'
          })
          this.$refs.upload.clearFiles()
        })
    },
    changeType() {
      this.listData = {}
      this.getTableData()
    },
    getTableData() {
      this.tableData = []
      this.loading = true
      let url =
        '/gtw/EIMS-management/warehouse/stuffRecordManagement?userId=' + this.userId + '&userLevel=' + this.userLevel
      for (let key in this.formInline) {
        if (this.formInline[key]) url += '&' + key + '=' + this.formInline[key]
      }
      axios
        .get(url)
        .then((res) => {
          this.loading = false
          let data = res.data.data || {}
          this.tableData = data.list || []
          this.getListData(this.tableData[0]).then((r) => {
            this.expandList = [this.tableData[0].stockId + this.tableData[0].checkTime]
          })
          this.total = data.total
          this.updateTime = this.getNowDate()
        })
        .catch(() => {
          this.loading = false
        })
    },
    async getListData(row) {
      this.loadingInner = true
      if (this.listData[row.stockId + row.checkTime || '']) {
        this.loadingInner = false
        return true
      } else {
        let url = `/gtw/EIMS-management/warehouse/stuffRecordDetailByStockId?stockId=${row.stockId}`
        if (row.checkTime) {
          url += `&checkTime=${row.checkTime}`
        }
        axios
          .get(url)
          .then((r) => {
            this.listData[row.stockId + row.checkTime || ''] = r.data.data
            // console.log(r.data.data)
            this.loadingInner = false
            return true
          })
          .catch(() => {
            return false
          })
      }
    },
    showDay(row) {
      // console.log(row)
    },
    getNowDate() {
      let now = new Date()
      let year = now.getFullYear()
      let mounth = now.getMonth() + 1
      let day = now.getDate()
      let hour = now.getHours() < 10 ? `0${now.getHours()}` : now.getHours()
      let min = now.getMinutes() < 10 ? `0${now.getMinutes()}` : now.getMinutes()
      let second = now.getSeconds() < 10 ? `0${now.getSeconds()}` : now.getSeconds()

      return `${year}-${mounth}-${day} ${hour}:${min}:${second}`
    }
  }
}
</script>

<style lang="scss">
@import '../../../../../style/base';
.StorageOutInManage {
  .check {
    color: $--color-primary;
    border-bottom: 1px solid $--color-primary;
    padding: 5px 0;
    font-size: 20px;
    cursor: pointer;
  }
  .el-table__expanded-cell {
    padding: 0px 50px 20px 70px;
  }
  .data-title {
    float: left;
    margin-top: 20px;
  }
  .colorRed {
    color: rgb(230, 98, 75);
  }
  .colorGreen {
    color: rgb(89, 197, 112);
  }

  .scrollArea {
    width: 100%;
    height: 80%;
    margin-bottom: 15px;
    overflow: scroll;
  }

  .blue {
    color: $--color-primary;
  }

  .header {
    .el-input__inner {
      width: 225px;
    }
  }
  .operaDay {
    cursor: pointer;
  }
  .operaDay:hover {
    color: #0093ff;
  }
  .downloadBtn {
    display: inline-block;
    color: #0093ff;
    background: #e6f4ff;
    border: 1px solid #99d4ff;
    padding: 0px 20px;
    font-size: 14px;
    border-radius: 4px;
    -webkit-appearance: none;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    outline: none;
    margin: 0;
    -webkit-transition: 0.1s;
    transition: 0.1s;
    font-weight: 500;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    text-decoration: none;

    &:hover {
      color: #fff;
      background-color: #0d9bf2;
      border-color: #0d9bf2;
    }
  }
}
</style>
