<template>
  <div class="infoTemplate blank">
    <div class="top-container">
      <el-form class="znv-el-form" ref="form" :inline="true" :model="form">
        <el-form-item label="标题" style="margin-bottom: 0">
          <el-input clearable v-model="form.search" placeholder="请输入关键字"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-cascader
            v-model="form.typeSelect"
            :options="typeEmergencyList"
            :props="{ checkStrictly: true }"
            clearable
          ></el-cascader>
        </el-form-item>
        <el-form-item style="margin-bottom: 0">
          <el-button type="primary" @click="onSubmit()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="emergency-container">
      <el-row class="znv-table-area">
        <header>
          <span class="title">查询表格</span>
          <span>
            <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
          </span>
        </header>
        <el-table
          :data="tableData"
          :height="720"
          v-loading="loading"
          highlight-current-row
          @current-change="handleChange"
        >
          <el-table-column prop="id" label="序号" width="180"></el-table-column>
          <el-table-column prop="templateTitle" label="标题" width="200"></el-table-column>
          <el-table-column prop="templateType" label="类型" width="200"></el-table-column>
          <el-table-column prop="templateContent" label="信息模板"></el-table-column>
          <el-table-column prop="templateProducer" label="制作人" width="180"></el-table-column>
          <el-table-column prop="produceTime" label="制作时间" width="200"></el-table-column>
          <el-table-column fixed="right" label="操作" width="160px">
            <template slot-scope="scope">
              <el-button @click="handleView(scope.row)" type="text" size="medium">详情</el-button>
              <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
              <el-button @click="handleDelete(scope.row)" type="text" size="medium">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :current-page.sync="pageStart"
          :page-size.sync="pageLen"
          layout="total, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
        <infoTemplateAdd
          :dialogVisible.sync="showAdd"
          :typeEmergencyList="typeEmergencyList"
          @success="handleSuccess"
          :id="id"
          :isCode="isCode"
        ></infoTemplateAdd>
      </el-row>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import infoTemplateAdd from './InfoTemplateAdd.vue'

export default {
  name: "InfoTemplate",
  components: {
    infoTemplateAdd: infoTemplateAdd
  },
  data() {
    return {
      areaList: [], // 所属辖区
      form: {
        search: '',
        typeSelect: []
      },
      pageStart: 1,
      pageLen: 10,
      total: 0,
      tableData: [],
      loading: false,
      showAdd: false,
      isCode: 1,
      isCodeDic: {
        isEdit: 0,
        isAdd: 2,
        isView: 1
      },
      typeEmergencyList: [],
      formInfoDefault: {
        id: '',
        shelterOrga: '',
        shelterName: '',
        shelterAddress: '',
        acerage: '',
        capacity: '',
        shelterLongitude: '',
        shelterLatitude: '',
        shelterPerson: '',
        shelterPhone: '',
        hazardAnalysis: '',
        measuresAnalysis: '',
        shelterFileKey: [],
        propertyUnit: '',
        shelterRescueType: '',
        shelterType: '',
        shelterUnit: ''
      },
      formInfo: {
        id: '',
        shelterOrga: '',
        shelterName: '',
        shelterAddress: '',
        acerage: '',
        capacity: '',
        shelterLongitude: '',
        shelterLatitude: '',
        shelterPerson: '',
        shelterPhone: '',
        hazardAnalysis: '',
        measuresAnalysis: '',
        shelterFileKey: [],
        propertyUnit: '',
        shelterRescueType: '',
        shelterType: '',
        shelterUnit: ''
      },
      id: null
    }
  },
  mounted() {
    this.getTypeList()
    this.getTemplate()
  },
  methods: {
    getTypeList() {
      let url = '/gtw/EIMS-management/event/queryEventType'
      Axios.get(url).then(r => {
        let data = r.data.data || []
        this.typeEmergencyList = this.changeTree(data)
      })
    },
    changeTree(data) {
      let arr = []
      if (typeof (data) === 'object') {
        for (let key in data) {
          let obj = {
            label: key,
            value: key,
            children: []
          }
          data[key].forEach(item => {
            let obj2 = {
              label: item,
              value: item
            }
            if (item) {
              obj.children.push(obj2)
            }
          })
          arr.push(obj)
        }
      }
      return arr
    },
    getTemplate(id) {
      let url = '/gtw/EIMS-management/commandMessage/getTemplate?page=' + this.pageStart + '&pageSize=' + this.pageLen
      if (id) {
        url = url + '&id=' + id
      }
      if (this.form.search) {
        url = url + '&title=' + this.form.search
      }
      if (this.form.typeSelect.length > 0) {
        url = url + '&type1=' + this.form.typeSelect[0]
      }
      if (this.form.typeSelect.length > 1) {
        url = url + '&type2=' + this.form.typeSelect[1]
      }
      Axios.get(url).then(res => {
        let data = res.data.data.list || []
        this.tableData = data.map(t => {
          t.templateType = t.templateType1
          if (t.templateType2) {
            t.templateType = t.templateType + '/' + t.templateType2
          }
          return t
        })
        this.total = res.data.data.total
      })
    },
    onSubmit() {
      this.pageStart = 1
      this.getTemplate()
    },
    handleCurrentChange(val) {
      this.pageStart = val
      this.getTemplate()
    },
    handleSizeChange(val) {
      this.pageLen = val
      this.getTemplate()
    },
    // 查看
    handleView(row) {
      this.id = row.id
      this.isCode = this.isCodeDic.isView
      this.showAdd = true
    },
    handleEdit(row) {
      this.id = row.id
      this.isCode = this.isCodeDic.isEdit
      this.showAdd = true
    },
    handleDelete(row) {
      this.$confirm('确认删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/commandMessage/deleteTemplate?id=' + row.id
          Axios.get(url).then(res => {
            if (res.data.code === 200) {
              this.$message.success('删除成功！')
            } else {
              this.$message.error('删除失败！')
            }
          }).catch(() => {
            this.$message.error('删除失败！')
          }).finally(() => {
            this.pageStart = 1
            this.getTemplate()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handleAdd() {
      this.id = null
      this.isCode = this.isCodeDic.isAdd
      this.showAdd = true
    },
    handleSuccess() {
      const _this = this
      if (_this.isCode === _this.isCodeDic.isAdd) {
        _this.pageStart = 1
      }
      this.getTemplate()
    }
  }
}
</script>

<style lang="scss">
.infoTemplate {
  position: relative;

  .left-container {
    position: relative;
    float: left;
    width: 270px;
    background-color: #fff;
    height: 100%;

    .search-box {
      position: relative;
      padding: 20px 22px;
    }

    .event-list-ctrl {
      position: relative;
      margin-left: 22px;
      margin-top: 20px;
      color: #0093ff;

      i {
        font-size: 20px;
        cursor: pointer;
      }

      .add-i {
        float: right;
        margin-right: 22px;
      }
    }

    .event-list-content {
      position: relative;
      margin-top: 15px;

      .event-item {
        position: relative;
        line-height: 44px;
        height: 44px;
        cursor: pointer;
        text-indent: 80px;

        &.isActive {
          color: #249bff;
          background-color: #e4f4ff;
        }

        &:hover {
          color: #249bff;
        }
      }
    }
  }

  .top-container {
    position: relative;
    float: left;
    width: 100%;
    background-color: #fff;
    height: 76px;
    margin-bottom: 20px;
  }

  .emergency-container {
    position: relative;
    float: left;
    width: 100%;
    background-color: #fff;
    height: calc(100% - 96px);
  }
}
</style>
