<template>
  <section class="storage">
    <el-row class="header">
      <el-col :span="22">
        <el-form :inline="true" :model="form" v-if="!isDetail">
          <el-form-item label="模糊查询">
            <el-input clearable v-model="form.vague" placeholder="请输入关键字"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('vague', '1')">查询</el-button>
            <span class="check" @click="isDetail = !isDetail">高级检索</span>
          </el-form-item>
        </el-form>
        <el-form :inline="true" ref="formDetail" :model="formDetail" v-if="isDetail">
          <el-form-item label="所属辖区">
            <el-select clearable v-model="formDetail.region" placeholder="所属辖区">
              <el-option v-for="(v, i) in regionList" :key="i" :label="v.name" :value="v.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="管理类型">
            <el-select clearable v-model="formDetail.beManagedType" placeholder="管理类型">
              <el-option v-for="(v, i) in manageTypeList" :key="i" :label="v.name" :value="v.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="管理级别">
            <el-select clearable v-model="formDetail.storageLevel" placeholder="请输入管理级别">
              <el-option v-for="(v, i) in manageLevelList" :key="i" :label="v.name" :value="v.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="存储类型">
            <el-select clearable v-model="formDetail.storageType" placeholder="请输入存储类型">
              <el-option v-for="(v, i) in storageTypeList" :key="i" :label="v.name" :value="v.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="救援类型">
            <el-select clearable v-model="formDetail.helpType" multiple collapse-tags placeholder="请输入救援类型">
              <el-option v-for="(v, i) in saveTypeList" :key="i" :label="v.name" :value="v.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="管理单位">
            <el-select filterable v-model="formDetail.belongUnit" placeholder="请选择管理单位" clearable>
              <el-option v-for="item in unitList" :value="item.id" :label="item.name" :key="item.id" />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="所属行业">
            <el-select clearable v-model="formDetail.industry" placeholder="请输入所属行业">
              <el-option v-for="(v, i) in industryList" :key="i" :label="v.name" :value="v.name"></el-option>
            </el-select>
          </el-form-item> -->
          <el-form-item label="仓库类型">
            <el-select clearable v-model="formDetail.stockType" placeholder="请输入仓库类型">
              <el-option v-for="(v, i) in stockTypeList" :key="i" :label="v.name" :value="v.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="仓库名称">
            <el-input clearable v-model="formDetail.stockName" placeholder="请输入仓库名称"></el-input>
          </el-form-item>
          <el-form-item style="float: right">
            <el-button type="primary" plain @click="reset('formDetail')">重置</el-button>
            <el-button type="primary" @click="onSubmit('detail', '1')">查询</el-button>
            <span class="check" @click="isDetail = !isDetail">模糊检索</span>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="2" style="text-align: right" v-if="isCheck !== 1">
        <el-button type="primary" plain @click="handleAdd" v-if="userLevel !== wareHouseLevel">新增</el-button>
      </el-col>
    </el-row>

    <el-table
      :data="tableData"
      ref="multipleTable"
      :height="isDetail ? 660 : 760"
      v-loading="loading"
      type="selection"
      :row-key="getRowKey"
      @selection-change="handleSelectionChange"
    >
      <!--      <el-table-column prop="region" label="所在辖区"></el-table-column>-->
      <el-table-column v-if="isCheck === 1" :reserve-selection="true" type="selection" width="55"> </el-table-column>
      <el-table-column prop="stockName" label="仓库名称"></el-table-column>
      <el-table-column prop="region" label="所属辖区"></el-table-column>
      <el-table-column prop="stockType" label="仓库类型"></el-table-column>
      <el-table-column prop="address" label="仓库地址"></el-table-column>
      <el-table-column prop="stuffCount" label="物资种类">
        <template slot-scope="scope">
          <el-button @click="toPath(scope.row)" type="text">{{ scope.row.stuffCount }}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="stuffNumber" label="物资总量">
        <template slot-scope="scope">
          <el-button @click="toPath(scope.row)" type="text">{{ scope.row.stuffNumber }}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="helpType" label="救援类别"></el-table-column>
      <el-table-column prop="storageType" label="存储类别"></el-table-column>
      <el-table-column prop="storageLevel" label="管理级别"></el-table-column>
      <el-table-column prop="belongUnit" label="管理单位"></el-table-column>
      <el-table-column prop="manager" label="主要责任人"></el-table-column>
      <el-table-column prop="managerPhone" label="值班电话"></el-table-column>
      <el-table-column prop="stockOrder" label="排序号"></el-table-column>
      <el-table-column label="操作" width="200px" v-if="isCheck !== 1">
        <template slot-scope="scope">
          <el-button @click="handleView(scope.row)" type="text" size="medium">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
          <el-button
            :disabled="scope.row.stockType !== '物资库' && scope.row.stockType !== '装备库'"
            @click="handleDelete(scope.row)"
            type="text"
            size="medium"
            v-if="userLevel !== wareHouseLevel"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="data-title">
      共<span class="blue">{{ total }}</span
      >条数据 | 最近更新时间：{{ updateTime }}
    </div>
    <el-pagination
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
      :current-page.sync="currentPage"
      :page-size="pageSize"
      :page-sizes="[50, 100, 500, 1000]"
      layout="sizes,prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
    <div v-if="isCheck === 1" class="bottomBtn" style="text-align: center">
      <el-button type="primary" @click="checkFinish">确定</el-button>
      <el-button @click="checkClose">取消</el-button>
      <el-button @click="checkOut">清空</el-button>
    </div>
    <StorageManageAdd
      :dialogVisible.sync="showAdd"
      :manageTypeList="manageTypeList"
      :industryList="industryList"
      :saveTypeList="saveTypeList"
      :unitList="unitList"
      :regionList="regionList"
      :manageLevelList="manageLevelList"
      :storageTypeList="storageTypeList"
      @success="handleSuccess"
    />
    <StorageManageEdit
      :dialogVisible.sync="showEdit"
      :storageInfo="storageInfo"
      :manageTypeList="manageTypeList"
      :industryList="industryList"
      :saveTypeList="saveTypeList"
      :unitList="unitList"
      :regionList="regionList"
      :manageLevelList="manageLevelList"
      :storageTypeList="storageTypeList"
      @success="handleSuccess"
    />
    <StorageManageDelete :dialogVisible.sync="showDelete" :storageInfo="storageInfo" @success="handleSuccess" />
    <StorageManageView :dialogVisible.sync="showView" :storageInfo="storageInfo" />
    <StorageInventoryDetail :dialogVisible.sync="showInventory" :storageInventoryInfo="storageInfo" />
  </section>
</template>
<script>
import axios from 'axios'
import qs from 'qs'
import moment from 'moment'
import StorageManageAdd from './StorageManageAdd'
import StorageManageEdit from './StorageManageEdit'
import StorageManageDelete from './StorageManageDelete'
import StorageManageView from './StorageManageView'
import StorageInventoryDetail from './StorageInventoryDetail'

export default {
  props: ['isCheck', 'helpTypeList'],
  components: { StorageInventoryDetail, StorageManageAdd, StorageManageEdit, StorageManageDelete, StorageManageView },
  data() {
    return {
      isDetail: false,
      userLevel: localStorage.getItem('level'),
      userId: localStorage.getItem('userId'),
      wareHouseLevel: configs.wareHouseLevel,
      multipleSelection: [],
      showInventory: false,
      showView: false,
      showAdd: false,
      showEdit: false,
      showDelete: false,
      loading: false,
      storageInfo: {},
      currentPage: 0,
      total: 0,
      pageSize: 50,
      updateTime: moment().format('YYYY-MM-DD HH:mm:ss'),
      manageTypeList: [],
      industryList: [],
      saveTypeList: [],
      unitList: [],
      regionList: [],
      manageLevelList: [],
      storageTypeList: [],
      // 1:物资库  2:装备库 3:其他 4:救援队伍 5:社会力量 6 监管企业
      stockTypeList: [
        { id: 1, name: '物资库' },
        { id: 2, name: '装备库' },
        { id: 3, name: '其他' },
        { id: 4, name: '救援队伍' },
        { id: 5, name: '社会力量' },
        { id: 6, name: '监管企业' }
      ],
      form: {
        vague: ''
      },
      formDetail: {
        region: '',
        beManagedType: '',
        storageLevel: '',
        storageType: '',
        helpType: [],
        industry: '',
        stockType: '',
        stockName: '',
        belongUnit: ''
      },
      tableData: []
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
      // console.log(this.multipleSelection)
    },
    getRowKey(row) {
      return row.id
    },
    checkFinish() {
      let ids = this.multipleSelection.map((item) => {
        return item.id
      })
      this.$emit('success', ids)
      this.$emit('close')
    },
    checkOut() {
      this.$refs.multipleTable.clearSelection()
    },
    checkClose() {
      this.$emit('close')
    },
    toPath(row) {
      // let url = '/#/storageInventory?stockName=' + row.stockName
      // window.open(url, '_blank')
      this.storageInfo = row
      this.showInventory = true
    },
    onSubmit(type, key) {
      if (key === '1') {
        this.currentPage = 1
      }
      switch (type) {
        case 'vague':
          this.query(this.form)
          break
        case 'detail':
          this.query(this.formDetail)
          break
        default:
          break
      }
    },
    handleCurrentChange(val) {
      // this.query()
      if (!this.isDetail) {
        this.onSubmit('vague')
      } else {
        this.onSubmit('detail')
      }
    },
    handleSizeChange(v) {
      this.pageSize = v
      this.currentPage = 1
      this.query()
    },
    handleView(row) {
      // console.log(row)
      this.storageInfo = row
      this.showView = true
    },
    handleEdit(row) {
      this.storageInfo = row
      this.showEdit = true
    },
    handleDelete(row) {
      this.storageInfo = row
      this.showDelete = true
    },
    handleAdd() {
      this.showAdd = true
    },
    reset(formName) {
      // console.log('this.formDetail', this.formDetail)
      for (let key in this.formDetail) {
        if (key == 'helpType') {
          this.formDetail[key] = []
        } else {
          this.formDetail[key] = ''
        }
      }
      // console.log('after---this.formDetail', this.formDetail)
      // this.$refs.formDetail.resetFields()
      this.$refs[formName].resetFields()
    },
    handleSuccess() {
      this.currentPage = 1
      this.query()
    },
    query(param = {}) {
      let data = {
        ...param,
        ...{
          pageStart: this.currentPage,
          pageLen: this.pageSize
        }
      }
      this.loading = true
      axios
        .get(
          '/gtw/EIMS-management/stockInfoData/getStockInfoList?userId=' +
            this.userId +
            '&helpType=' +
            this.formDetail.helpType.join() +
            '&userLevel=' +
            this.userLevel +
            '&' +
            qs.stringify(data, { encode: true })
        )
        .then((r) => {
          this.loading = false
          // // console.log(r.data.data)
          let data = r.data.data
          this.tableData = data.data
          this.total = data.count
        })
        .catch(() => {
          this.loading = false
        })
    },
    queryManageType() {
      let url = '/gtw/EIMS-management/stockInfoData/getBeManagedType'
      axios.get(url).then((r) => {
        this.manageTypeList = r.data.data.data
      })
    },
    queryManageUnit() {
      let url = '/gtw/EIMS-management/stockInfoData/getBelongUnit'
      axios.get(url).then((r) => {
        this.unitList = r.data.data.data
      })
    },
    querySaveType() {
      let url = '/gtw/EIMS-management/stockInfoData/getHelpType'
      axios.get(url).then((r) => {
        this.saveTypeList = r.data.data.data
      })
    },
    queryIndustry() {
      let url = '/gtw/EIMS-management/stockInfoData/getIndustry'
      axios.get(url).then((r) => {
        this.industryList = r.data.data.data
      })
    },
    queryRegion() {
      let url = `/gtw/EIMS-management/stockInfoData/getRegion?userLevel=${localStorage.level}&userId=${localStorage.userId}`
      axios.get(url).then((r) => {
        this.regionList = r.data.data.data
      })
    },
    queryManageLevel() {
      let url = '/gtw/EIMS-management/stockInfoData/getStorageLevel'
      axios.get(url).then((r) => {
        this.manageLevelList = r.data.data.data
      })
    },
    queryStorageType() {
      let url = '/gtw/EIMS-management/stockInfoData/getStorageType'
      axios.get(url).then((r) => {
        this.storageTypeList = r.data.data.data
        this.storageTypeList.forEach((v) => {
          v.name = v.NAME
        })
      })
    }
  },
  watch: {
    helpTypeList(v) {
      this.formDetail.helpType = v
      this.checkOut()
      this.checkFinish()
      this.query()
      this.queryManageType()
      this.queryManageUnit()
      this.querySaveType()
      this.queryIndustry()
      this.queryRegion()
      this.queryManageLevel()
      this.queryStorageType()
    }
  },
  mounted() {
    if (localStorage.getItem('level') === '300') {
      // console.log('仓库管理员登录了')
    } else {
      // console.log(localStorage.getItem('level'))
    }
    this.formDetail.helpType = this.helpTypeList || []
    this.query()
    this.queryManageType()
    this.queryManageUnit()
    this.querySaveType()
    this.queryIndustry()
    this.queryRegion()
    this.queryManageLevel()
    this.queryStorageType()
  }
}
</script>
<style lang="scss">
@import '../../../../style/base';

.storage {
  .check {
    color: $--color-primary;
    border-bottom: 1px solid $--color-primary;
    padding: 5px 0;
    cursor: pointer;
    margin-left: 10px;
  }

  .data-title {
    float: left;
    margin-top: 20px;
  }

  .blue {
    color: $--color-primary;
  }

  .header {
    .el-form--inline .el-form-item__content {
      width: 225px;
    }
  }
}
</style>
