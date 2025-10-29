<template>
  <div class="MaterialManagementDialog" v-if="dialogVisible">
    <el-dialog
      title="物资库存管理"
      :visible.sync="dialogVisible"
      width="1450px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <section style="height: 100%">
        <el-row>
          <el-form :inline="true" :model="formInline">
            <!-- <el-form-item label="仓库名称">
              <el-input clearable v-model="formInline.stockName" placeholder="请输入仓库名称"></el-input>
            </el-form-item>-->
            <el-form-item label="物资名称">
              <el-input clearable v-model="formInline.stuffName" placeholder="请输入物资名称"></el-input>
            </el-form-item>
            <el-form-item label="物资型号">
              <el-input clearable v-model="formInline.stuffModel" placeholder="请输入物资型号"></el-input>
            </el-form-item>
            <el-form-item label="物资品牌">
              <el-input clearable v-model="formInline.stuffBrand" placeholder="请输入物资品牌"></el-input>
            </el-form-item>
            <el-form-item label="救援类型">
              <el-select clearable v-model="formInline.helpTypeId" placeholder="救援类型">
                <el-option label="全部" value></el-option>
                <el-option
                  v-for="item in helpTypeList"
                  :value="item.id"
                  :label="item.name"
                  :key="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addNewList">入库</el-button>
            </el-form-item>
          </el-form>
        </el-row>
        <div class="scrollArea">
          <el-table v-loading="loading" :data="tableData" style="width: 100%">
            <el-table-column prop="stockName" label="所属仓库" width="200"></el-table-column>
            <!-- <el-table-column
          prop="BELONG_UNIT"
          label="主管单位"
          width="200">
            </el-table-column>-->
            <el-table-column prop="stuff_name" label="物资名称" width="120"></el-table-column>
            <el-table-column prop="model" label="物资型号" width="170"></el-table-column>
            <el-table-column prop="STUFF_NUMBER" label="库存数量" width="80"></el-table-column>
            <el-table-column prop="STUFF_UNIT" label="计量单位" width="80"></el-table-column>
            <!-- <el-table-column
          prop="totalPirce"
          label="总价值"
          width="120">
            </el-table-column>-->
            <el-table-column prop="brand" label="物资品牌" width="150"></el-table-column>
            <el-table-column prop="helpName" label="救援类型" width="220"></el-table-column>
            <!-- <el-table-column
          prop="stuff_create_date"
          label="生产日期"
          width="120">
            </el-table-column>-->
            <!-- <el-table-column
          prop="life"
          label="使用年限"
          width="120">
            </el-table-column>-->
            <el-table-column prop="MANAGER" label="负责人" width="100"></el-table-column>
            <el-table-column prop="MANAGER_PHONE" label="联系方式" width="120"></el-table-column>
            <el-table-column fixed="right" label="操作" width="139">
              <template slot-scope="scope">
                <el-button @click="handle(scope.row, 'in')" type="text" size="medium">入库</el-button>
                <el-button @click="handle(scope.row, 'out')" type="text" size="medium">出库</el-button>
                <el-button @click="handleDel(scope.row)" type="text" size="medium">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="formInline.page"
          :page-size="formInline.pageSize"
          @size-change="handleSizeChange"
          :page-sizes="[50, 100, 500, 1000]"
          layout="total,sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
        <StorageInventoryOut
          :dialogVisible.sync="showOut"
          :formInfo="formInfo"
          :status="checkTo"
          @success="handleSuccess"
        />
        <StorageInventoryAdd
          :dialogVisible.sync="addDialogVisible"
          @success="handleSuccess"
          :name="formInline.stockName"
          :id="wareHouseId"
        />
      </section>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/request'
import StorageInventoryOut from './StorageInventoryOut.vue'
import StorageInventoryAdd from './StorageInventoryAdd.vue'
export default {
  name: 'MaterialManagementDialog',
  props: ['dialogVisible', 'wareHouseName', 'wareHouseId'],
  components: { StorageInventoryOut, StorageInventoryAdd },
  data() {
    return {
      showOut: false,
      addDialogVisible: false,
      loading: false,
      userId: localStorage.getItem('userId'),
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: configs.wareHouseLevel,
      checkTo: '',
      total: 0,
      formInline: {
        stockName: '',
        stuffName: '',
        helpTypeId: '',
        stuffModel: '',
        stuffBrand: '',
        page: 1,
        pageSize: 50
      },
      tableData: [],
      updateTime: '',
      helpTypeList: [],
      formInfo: null
    }
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    wareHouseName(v) {
      console.log('wareHouseName', v)
      if (v) {
        this.formInline.stockName = v
        this.getTableData()
      }
    }
  },
  computed: {
    stuff() {
      return encodeURI(this.formInline.stuffName)
    }
  },
  mounted() {
    // console.log(this.$route.query)
    // this.$nextTick(() => {
    this.getHelpTypeList()
    // })
  },
  methods: {
    handleClose() {
      this.$emit('cancel')
      this.$emit('update:dialogVisible', false)
    },
    handle(row, status) {
      this.formInfo = row
      this.showOut = true
      this.checkTo = status
    },
    handleDel(row) {
      if (row['STUFF_NUMBER'] > 0) {
        this.$message('库存大于0时不能删除该物资数据！')
      } else {
        this.$confirm('此操作将删除并回滚该条出入库数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let url = `/community/menu/deleteStockStuff?stockId=${row.stockId}&stuffId=${row.stuffId}&brand=${row.brand}&model=${row.model}`
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
    addNewList() {
      this.addDialogVisible = true
    },
    handleSuccess() {
      this.formInline.page = 1
      this.getTableData()
    },
    onSubmit() {
      this.formInline.page = 1
      this.getTableData()
    },
    handleCurrentChange(val) {
      this.getTableData()
    },
    handleSizeChange(v) {
      this.formInline.pageSize = v
      this.formInline.page = 1
      this.getTableData()
    },
    getTableData() {
      this.tableData = []
      this.loading = true
      let url = '/binhuapis/warehouse/stuffInventory?userId=' + this.userId + '&userLevel=' + this.userLevel
      console.log('getTableData的url: ', url)
      // this.formInline.stuffName = this.stuff
      console.log('getTableData的stockName: ', this.formInline.stockName)
      for (let key in this.formInline) {
        if (this.formInline[key] && key !== 'stuffName') url += '&' + key + '=' + this.formInline[key]
      }
      url += '&stuffName=' + this.stuff
      console.log(url)
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
      let url = '/binhuapis/teamInfo/emergencyPowerDictList'
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.helpTypeList = data
      })
    }
  }
}
</script>

<style lang="scss">
.MaterialManagementDialog {
  @import '../../common/style/base';

  .el-dialog__body {
    height: 850px;
  }
  .check {
    color: $--color-primary;
    border-bottom: 1px solid $--color-primary;
    padding: 5px 0;
    font-size: 20px;
    cursor: pointer;
  }
  .addBtn {
    position: absolute;
    top: 0px;
    right: 50px;
  }

  .scrollArea {
    width: 100%;
    height: calc(100% - 100px);
    margin-bottom: 15px;
    overflow: scroll;
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
      width: 185px;
    }
  }
}
</style>
