<template>
  <div class="population-table">
    <el-col :span="24" class="search-container">
      <el-input placeholder="姓名(查询多个用英文逗号分割)" size="medium" v-model.trim="name" class="mrgin-r mrgin-t" clearable></el-input>
      <el-input placeholder="证件号码(查询多个用英文逗号分割)" size="medium" v-model.trim="cardNo" class="mrgin-r mrgin-t" clearable></el-input>
      <el-date-picker
        v-model="timeList"
        type="datetimerange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        class="mrgin-r mrgin-t"
        value-format="yyyy-MM-dd HH:mm:ss"
        clearable
      ></el-date-picker>
      <div class="mrgin-t">
        <el-button type="primary" @click="searchData">查询</el-button>
        <el-button type="primary" @click="templateDown">查询模板下载</el-button>
        <el-button type="primary" @click="templateSearch">导入模板查询</el-button>
        <el-button type="primary" @click="exportData" :loading="exportDataLoading" :disabled="exportDataDisabled">批量导出</el-button>
        <input
          type="file"
          name="upload"
          ref="upload"
          accept=".xls, .xlsx"
          style="display: none"
          @change="templateSearchUpload"
        />
      </div>
    </el-col>
    <!--列表-->
    <el-col :span="24" class="table-col">
      <el-table :data="showTable" highlight-current-row v-loading="listLoading" style="width: 100%" max-height="680">
        <el-table-column prop="index" label="序号" width="60px"></el-table-column>
        <el-table-column prop="real_name" label="姓名" width="100px"></el-table-column>
        <el-table-column prop="sample_time" label="采样时间" width="180px"></el-table-column>
        <el-table-column prop="check_project" label="核酸检测类型" width="120px"></el-table-column>
        <el-table-column prop="nat_result_name" label="检测结果中文" width="220px"></el-table-column>
        <el-table-column prop="card_type_name" label="证件类型名称" width="120px"></el-table-column>
        <el-table-column prop="card_no" label="证件号码" width="200px"></el-table-column>
        <el-table-column prop="sample_org_name" label="采样机构" width="180px"></el-table-column>
        <el-table-column prop="sample_code" label="样本编码" width="160px"></el-table-column>
        <el-table-column prop="sample_type_name" label="样本类型名称" width="180px"></el-table-column>
        <el-table-column prop="collect_date" label="收样时间" width="180px"></el-table-column>
        <el-table-column prop="report_date" label="检测结果报告时间" width="180px"></el-table-column>
      </el-table>
    </el-col>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <div class="total-container">
        <span>
          共
          <span class="total-number">{{ total }}</span>条记录
        </span>
      </div>
      <div class="block">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="pageSize"
          layout="prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </el-col>
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
export default {
  name: 'PopulationTable',
  components: {},
  data() {
    return {
      name: '',
      cardNo: '',
      timeList: '',
      tableData: [],
      total: 0,
      currentPage: 1,
      pageSize: 13,
      listLoading: false,
      exportDataLoading: false,
      exportDataDisabled: true
    }
  },
  computed: {
    showTable() {
      return (this.tableData || []).slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  watch: {
    total(v) {
      if (v > 0) {
        this.exportDataDisabled = false
      } else {
        this.exportDataDisabled = true
      }
    }
  },
  mounted() { },
  methods: {
    templateDown() {
      const link = document.createElement('a')
      link.href = '/static/hesuan.xls'
      link.setAttribute('download', '核酸查询模板.xls')
      document.body.appendChild(link)
      link.click()
    },
    templateSearch() {
      this.$refs.upload.click()
    },
    templateSearchUpload(event) {
      let file = event.target.files[0]
      if (!file.name.includes('.xls')) {
        this.$notify.error({
          title: '错误',
          message: '请使用模板文件上传',
          duration: 1000
        })
        this.$refs.upload.value = ''
        return
      }
      let formData = new FormData()
      formData.append('file', file)
      this.listLoading = true
      axios.post('/binhuapis/svp/importSelect', formData).then((res) => {
        this.tableData = res.data.data.map((e, index) => {
          e.index = index + 1
          return e
        })
        this.total = this.tableData.length
        this.$refs.upload.value = ''
        this.listLoading = false
      })
    },
    searchData() {
      if (!this.name && !this.cardNo) {
        this.$notify({
          title: '警告',
          type: 'warning',
          message: '必须输入查询条件',
          duration: 1000
        })
        return false
      }
      this.currentPage = 1
      let param = {
        name: this.name,
        cardNo: this.cardNo
      }
      if (this.timeList.length === 2) {
        param.startTime = this.timeList[0]
        param.endTime = this.timeList[1]
      }
      this.listLoading = true
      axios.get('/binhuapis/svp/info?' + qs.stringify(param)).then((res) => {
        this.tableData = res.data.data.map((e, index) => {
          e.index = index + 1
          return e
        })
        this.total = this.tableData.length
        this.listLoading = false
      })
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    exportData() {
      this.$notify({
        title: '警告',
        type: 'warning',
        message: '注意：批量导出仅支持导出每人近期五条核酸检测数据。',
        duration: 1000
      })
      this.exportDataLoading = true
      let param = this.tableData
        .map((e) => {
          return e.real_name + '-' + e.card_no
        })
        .join(',')
      axios
        .get('/binhuapis/svp/export?param=' + param, {
          responseType: 'blob'
        })
        .then((res) => {
          const excelurl = window.URL.createObjectURL(new Blob([res.data], { type: 'application/xlsx' }))
          const link = document.createElement('a')
          link.href = excelurl
          link.setAttribute('download', '核酸检测数据.xlsx')
          document.body.appendChild(link)
          link.click()
          this.exportDataLoading = false
        })
    }
  }
}
</script>
<style lang="scss">
.population-table {
  width: 100%;
  height: 100%;
}
</style>
