<template>
  <section class="enterprise blank">
    <el-row style="height:100%;">
      <!-- <el-col :span="5" class="znv-tree-container">
        <el-tree class="znv-tree" :data="data"  @node-click="handleNodeClick">
           <span class="custom-tree-node" slot-scope="{ node, data }">
             <i class="el-icon-map-location"></i>
            <span>{{ node.label }}</span>
          </span>
        </el-tree>
      </el-col>-->
      <el-col :span="24" class="right-area">
        <el-row class="header">
          <el-col :span="24">
            <el-form
              :inline="true"
              ref="form"
              :model="form"
              v-if="isDetail"
              class="znv-el-form"
              label-width="150px"
            >
              <el-form-item label="企业名称">
                <el-input v-model="form.stockName"></el-input>
              </el-form-item>
              <el-form-item label="预案名称">
                <el-input v-model="form.planName"></el-input>
              </el-form-item>
              <el-form-item label="预案类别">
                <el-select v-model="form.planTypeName" placeholder="请选择" clearable>
                  <el-option
                    v-for="item in planType"
                    :key="item.label"
                    :label="item.label"
                    :value="item.label"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="预案级别">
                <el-select v-model="form.planLevelName" placeholder="请选择" clearable>
                  <el-option
                    v-for="item in planLevel"
                    :key="item.label"
                    :label="item.label"
                    :value="item.label"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="适用领域">
                <el-select v-model="form.planAreaName" placeholder="请选择" clearable>
                  <el-option
                    v-for="item in planArea"
                    :key="item.label"
                    :label="item.label"
                    :value="item.label"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="是否备案">
                <el-select v-model="form.record" placeholder="请选择" clearable>
                  <el-option
                    v-for="item in recordList"
                    :key="item.label"
                    :label="item.label"
                    :value="item.label"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label=" ">
                <el-button plain @click="reset('form')">重置</el-button>
                <el-button type="primary" @click="onSubmit">查询</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row class="znv-table-area">
          <header>
            <span class="title">查询表格</span>
            <span>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
              <el-button type="success" icon="el-icon-download" @click="exportData">导出</el-button>
            </span>
          </header>
          <el-table :data="tableData" height="600" v-loading="loading">
            <el-table-column type="selection" width="55"></el-table-column>
            <!-- <el-table-column type="index" label="序号" width="50"></el-table-column> -->
            <el-table-column prop="enterprise_name" label="企业名称"></el-table-column>
            <el-table-column prop="administrative_division" label="行政区划"></el-table-column>
            <el-table-column prop="name" label="预案名称"></el-table-column>
            <el-table-column prop="type" label="预案类别"></el-table-column>
            <el-table-column prop="level" label="预案级别"></el-table-column>
            <el-table-column prop="applicable_field" label="适用领域"></el-table-column>
            <el-table-column prop="preparation_unit" label="编制单位(部门)"></el-table-column>
            <el-table-column prop="is_record" label="是否备案"></el-table-column>
            <el-table-column prop="release_time" label="发布日期"></el-table-column>
            <el-table-column prop="preparation_record_time" label="预备案时间"></el-table-column>
            <el-table-column prop="preparation_record_department" label="预备案部门"></el-table-column>
            <el-table-column prop="preparation_record_number" label="预备案编号"></el-table-column>
            <el-table-column fixed="right" label="操作" width="100px">
              <template slot-scope="scope">
                <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
                <el-button @click="handleDelete(scope.row)" type="text" size="medium">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!--        <div class="data-title">共<span class="blue">{{ total }}</span>条数据 | 最近更新时间：{{ updateTime }}</div>-->
          <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page.sync="currentPage"
            :page-size.sync="pageSize"
            :page-sizes="[10, 20, 50, 2]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          ></el-pagination>
        </el-row>
      </el-col>
    </el-row>
    <EnterprisePlanAdd
      :dialogVisible.sync="showAdd"
      :infomation="eventInfo"
      :planType="planType"
      :planLevel="planLevel"
      :planArea="planArea"
      :recordList="recordList"
      @success="addCom"
    />
    <EnterprisePlanEdit
      :dialogVisible.sync="showEdit"
      :infomation="eventInfo"
      :planType="planType"
      :planLevel="planLevel"
      :planArea="planArea"
      :recordList="recordList"
      @success="editCom"
    />
  </section>
</template>
<script>
import axios from '@/utils/request'
import moment from 'moment'
import EnterprisePlanEdit from './EnterprisePlanEdit'
import EnterprisePlanAdd from './EnterprisePlanAdd'
export default {
  components: {
    EnterprisePlanEdit,
    EnterprisePlanAdd
  },
  data() {
    return {
      isDetail: true,
      unfold: false,
      showView: false,
      showAdd: false,
      showEdit: false,
      eventInfo: '',
      showDelete: false,
      loading: false,
      storageInfo: {},
      currentPage: 0,
      total: 0,
      pageSize: 10,
      updateTime: moment().format('YYYY-MM-DD HH:mm:ss'),
      planType: [{
        value: '1',
        label: '综合预案'
      }, {
        value: '2',
        label: '专项预案'
      }, {
        value: '3',
        label: '现场预案'
      }, {
        value: '4',
        label: '卡式预案'
      }],
      planTypeName: null,
      planLevel: [{
        value: '选项1',
        label: '政府预案级别'
      }, {
        value: '选项2',
        label: '企业预案级别'
      }],
      planLevelName: null,
      planArea: [{
        value: '选项1',
        label: '自然灾害'
      }, {
        value: '选项2',
        label: '事故灾害'
      }, {
        value: '选项3',
        label: '公共卫生事故'
      }, {
        value: '选项4',
        label: '社会安全事故'
      }],
      planAreaName: null,
      recordList: [{
        value: 0,
        label: '是'
      }, {
        value: 1,
        label: '否'
      }],
      form: {
        planName: null,
        stockName: null,
        region: '',
        planLevelName: null,
        planTypeName: null,
        planAreaName: null,
        department: '',
        bianzhi: '',
        showDate: '',
        showId: '',
        showPeople: '',
        showDepart: '',
        record: '',
        recordTime: '',
        recordDepart: '',
        recordId: '',
        other: '',
        doc: ''
      },
      tableData: [],
      data: [],
      selectData: []
    }
  },
  watch: {
    tableData() {
      if (this.tableData.length === 0 && this.total !== 0) {
        this.currentPage -= 1
        this.getData()
      }
    }
  },
  methods: {
    queryTree() {
      let url = "/binhuapis/warehouse/warehouseDetail";
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.data = res.data.data || [];
        }
      });
    },
    getData() {
      this.loading = true
      let url = '/binhuapis/selectEPlanByDesignated'
      axios.get(url, {
        params: {
          enterpriseName: this.form.stockName,
          applicableField: this.form.planAreaName,
          name: this.form.planName,
          type: this.form.planTypeName,
          level: this.form.planLevelName,
          pageStart: this.currentPage,
          pageLen: this.pageSize,
          isRecord: this.form.record
        }
      }).then(r => {
        console.log(r)
        this.tableData = r.data.data.data
        this.loading = false
        this.total = r.data.data.count
      })
    },
    handleNodeClick(data) {
      console.log(data);
    },
    onSubmit() {
      this.getData()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.onSubmit()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.onSubmit()
    },
    handleView(row) {
      this.storageInfo = row
      this.showView = true
    },
    handleAdd() {
      this.showAdd = true
    },
    handleEdit(row) {
      this.eventInfo = row
      this.showEdit = true
    },
    handleDelete(row) {
      this.$confirm('是否确定删除该文件?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(row['id'])
        let url = '/binhuapis/deleteEPlan' + '?id=' + row['id']
        axios.get(url).then(r => {
          console.log(r)
          this.$message.success('删除成功')
          this.getData()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    addCom() {
      this.getData()
    },
    editCom() {
      this.getData()
    },
    reset(formName) {
      for (let key in this.form) {
        this.form[key] = null
      }
      // this.$refs.formDetail.resetFields()
      this.$refs[formName].resetFields()
    },
    handleSuccess() {
      this.currentPage = 1
      this.query()
    },
    handleSelectionChange(e) {
      this.selectData = e
    },
    exportData() {
      this.$message('还没开放')
    }
  },
  mounted() {
    this.queryTree()
    this.getData()
  }
}
</script>
<style lang="scss">
@import '../../../common/style/base';

.enterprise {
  .right-area {
    overflow: auto;
    height: 100%;
  }

  .check {
    color: $--color-primary;
    //border-bottom: 1px solid $--color-primary;
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
      width: 220px;

      .el-select {
        width: 100%;
      }
    }
  }
}
</style>
