<template>
  <section class="StorageOutInManage">
    <el-row class="header">
      <el-form :inline="true" :model="formInline">
        <!-- <el-form-item label="文件名称">
          <el-input v-model="formInline.fileName" placeholder="请输入文件名称"></el-input>
        </el-form-item> -->
        <el-form-item label="仓库名称">
          <el-input v-model="formInline.stockName" placeholder="请输入仓库名称"></el-input>
        </el-form-item>
        <el-form-item label="操作人员">
          <el-input v-model="formInline.userName" placeholder="请输入操作人员"></el-input>
        </el-form-item>
        <el-form-item label="操作类型">
          <el-select @change="changeType" v-model="formInline.operationType" placeholder="操作类型">
            <el-option label="全部" value=""></el-option>
            <el-option label="出库" value="1"></el-option>
            <el-option label="入库" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传时间">
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
          <a
            class="downloadBtn"
            href="../../../../../static/物资出入库信息登记模板.xlsx"
            download="物资出入库信息登记模板"
          >
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
              :data="listData[props.row.id] ? listData[props.row.id] : []"
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="50"> </el-table-column>

              <el-table-column prop="stuff_name" label="物资名称" width="160"> </el-table-column>

              <el-table-column prop="model" label="物资型号" width="160"> </el-table-column>

              <el-table-column prop="stuff_unit" label="计量单位" width="160"> </el-table-column>

              <el-table-column prop="STUFF_NUMBER" label="物资数量" width="160"> </el-table-column>

              <el-table-column prop="brand" label="物资品牌" width="160"> </el-table-column>

              <el-table-column prop="operation_type" label="操作类型" width="160">
                <template slot-scope="scope">
                  {{
                    scope.row.operation_type === '1'
                      ? '出库'
                      : scope.row.operation_type === '2'
                      ? '入库'
                      : scope.row.operation_type === '3'
                      ? '删除'
                      : ''
                  }}
                </template>
              </el-table-column>

              <el-table-column prop="report_time" label="操作时间" width="160"> </el-table-column>

              <el-table-column prop="fileName" label="备注"> </el-table-column>
            </el-table>
          </template>
        </el-table-column>

        <el-table-column prop="file_name" type="index" label="序号"> </el-table-column>
        <el-table-column prop="stockName" label="仓库名称"> </el-table-column>
        <el-table-column prop="userName" label="操作人员"> </el-table-column>
        <el-table-column prop="operation_time" label="最近操作时间"> </el-table-column>
        <el-table-column prop="operationCount" label="操作天数">
          <template slot-scope="scope">
            <el-popover placement="left" title="操作日历" width="700" trigger="click">
              <el-calendar>
                <template slot="dateCell" slot-scope="{ date, data }">
                  <p>
                    {{ data.day.split('-').slice(2).join('') }}
                    <span style="color: red; font-size: 25px">{{
                      scope.row.operationDates.split(',').indexOf(data.day) > -1 ? '●' : ''
                    }}</span>
                  </p>
                </template>
              </el-calendar>
              <span slot="reference" class="operaDay">{{ scope.row.count }}</span>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="operationCount" label="操作次数"> </el-table-column>
        <el-table-column prop="operation_type" label="操作" width="120">
          <template slot-scope="scope">
            <el-button v-if="userLevel !== '300'" @click="handleDel(scope.row)" type="text" size="medium"
              >删除</el-button
            >
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
      expandList: [],
      formInline: {
        fileName: '',
        stockName: '',
        userName: '',
        operationType: '',
        startTime: '',
        endTime: '',
        page: 1,
        pageSize: 50
      },
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
    this.getTotal()
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
      return row.id
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
    datetimerangeChange(val) {
      // console.log(val)
      if (val) {
        this.formInline.startTime = val[0]
        this.formInline.endTime = val[1]
      } else {
        this.formInline.startTime = ''
        this.formInline.endTime = ''
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
          let arr = []
          this.tableData.forEach((item) => {
            if (item.operationCount !== 0) {
              arr.push(item)
            }
          })
          this.tableData = arr
          this.getListData(this.tableData[0]).then((r) => {
            this.expandList = [this.tableData[0].id]
          })
          this.updateTime = this.getNowDate()
        })
        .catch(() => {
          this.loading = false
        })
    },
    getTotal() {
      let url =
        '/gtw/EIMS-management/warehouse/stuffRecordManagement?userId=' + this.userId + '&userLevel=' + this.userLevel
      for (let key in this.formInline) {
        if (this.formInline[key] && key !== 'page' && key !== 'pageSize') url += '&' + key + '=' + this.formInline[key]
      }
      axios
        .get(url)
        .then((res) => {
          let data = res.data.data || {}
          let arr = []
          data.list.forEach((item) => {
            if (item.operationCount !== 0) {
              arr.push(item)
            }
          })
          this.total = arr.length
        })
        .catch(() => {
          this.loading = false
        })
    },
    getOperaDay() {
      let url = '/gtw/EIMS-management/warehouse/queryOperationDate'
      axios.get(url).then((r) => {
        let data = r.data.data
        let obj = {}
        data.forEach((item) => {
          if (item.warehouse && item.id) {
            if (obj[item.warehouse]) {
              obj[item.warehouse][item.id] = item
            } else {
              obj[item.warehouse] = {}
            }
          }
        })
        this.operaDayList = obj
        // console.log(this.operaDayList)
      })
    },
    async getListData(row) {
      this.loadingInner = true
      if (this.listData[row.warehouse]) {
        this.loadingInner = false
        return true
      } else {
        let url = `/gtw/EIMS-management/warehouse/stuffRecordDetailByStockId?stockId=${row.warehouse}&userName=${row.userName}&userId=${row.user_id}&operationType=${this.formInline.operationType}`
        axios
          .get(url)
          .then((r) => {
            this.listData[row.id] = r.data.data
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
@import '../../../../common/style/base';
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
