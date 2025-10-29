<template>
  <el-dialog
    title="物资库存"
    :visible.sync="dialogVisible"
    width="1200px"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose"
  >
    <div class="storageInventory-detail">
      <el-row class="header">
        <el-form :inline="true" :model="formInline">
          <el-form-item label="仓库名称">
            <el-input clearable v-model="formInline.stockName" placeholder="请输入仓库名称"></el-input>
          </el-form-item>
          <el-form-item label="物资名称">
            <el-input clearable v-model="formInline.stuffName" placeholder="请输入物资名称"></el-input>
          </el-form-item>
          <el-form-item label="救援类型">
            <el-select clearable v-model="formInline.helpTypeId" placeholder="救援类型">
              <el-option label="全部" value=""></el-option>
              <el-option v-for="item in helpTypeList" :value="item.id" :label="item.name" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-table v-loading="loading" :data="tableData" style="width: 100%">
        <el-table-column prop="stockName" label="所属仓库" width="300"> </el-table-column>
        <!-- <el-table-column
          prop="stockUnit"
          label="主管单位"
          width="200">
        </el-table-column> -->
        <el-table-column prop="stuff_name" label="物资名称" width="120"> </el-table-column>
        <el-table-column prop="model" label="物资型号" width="200"> </el-table-column>
        <el-table-column prop="STUFF_NUMBER" label="库存数量" width="120"> </el-table-column>
        <!-- <el-table-column
          prop="totalPirce"
          label="总价值"
          width="120">
        </el-table-column> -->
        <el-table-column prop="brand" label="物资品牌" width="120"> </el-table-column>
        <el-table-column prop="helpName" label="救援类型" width="180"> </el-table-column>
        <!-- <el-table-column
          prop="stuff_create_date"
          label="生产日期"
          width="120">
        </el-table-column> -->
        <!-- <el-table-column
          prop="life"
          label="使用年限"
          width="120">
        </el-table-column> -->
        <el-table-column prop="MANAGER" label="负责人" width="120"> </el-table-column>
        <el-table-column prop="MANAGER_PHONE" label="联系方式" width="120"> </el-table-column>
      </el-table>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="formInline.page"
        :page-size="formInline.pageSize"
        layout="total, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'
export default {
  name: 'StorageInventoryDetail',
  components: {},
  data() {
    return {
      loading: false,
      userId: localStorage.getItem('userId'),
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: configs.wareHouseLevel,
      total: 0,
      formInline: {
        stockName: '',
        stuffName: '',
        helpTypeId: '',
        page: 1,
        pageSize: 10
      },
      tableData: [],
      updateTime: '',
      helpTypeList: []
    }
  },
  props: ['dialogVisible', 'storageInventoryInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.formInline.stockName = this.storageInventoryInfo.stockName
        this.getTableData()
      }
    }
  },
  mounted() {
    this.getHelpTypeList()
  },
  methods: {
    handleClose(done) {
      this.$emit('update:dialogVisible', false)
    },
    onSubmit() {
      this.formInline.page = 1
      this.getTableData()
    },
    handleCurrentChange(val) {
      this.getTableData()
    },
    getTableData() {
      this.tableData = []
      this.loading = true
      let level = this.userLevel
      let url = '/gtw/EIMS-management/warehouse/stuffInventory?userId=' + this.userId + '&userLevel=' + level
      console.log(url)
      for (let key in this.formInline) {
        if (this.formInline[key]) url += '&' + key + '=' + this.formInline[key]
      }
      axios
        .get(url)
        .then((res) => {
          this.loading = false
          let data = res.data.data || {}
          this.total = data.total
          this.tableData = data.list || []
        })
        .catch(() => {
          this.loading = false
        })
    },
    getHelpTypeList() {
      let url = '/gtw/EIMS-management/teamInfo/emergencyPowerDictList'
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.helpTypeList = data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.storageInventory-detail {
  @import '../../../../style/base';

  .check {
    color: $--color-primary;
    border-bottom: 1px solid $--color-primary;
    padding: 5px 0;
    font-size: 20px;
    cursor: pointer;
  }

  .data-title {
    float: left;
    margin-top: 20px;
  }

  .blue {
    color: $--color-primary;
  }

  .header {
    .el-input__inner {
      width: 225px;
    }
  }
}
</style>
