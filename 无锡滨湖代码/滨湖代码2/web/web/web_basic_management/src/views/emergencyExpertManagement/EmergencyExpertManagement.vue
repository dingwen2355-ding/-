<template>
  <div class="EmergencyExpertManagement">
    <div class="headerTop">
      <div class="item1">
        <label>专家姓名:</label>
        <el-input v-model="serchData.name" placeholder="请输入专家姓名" clearable></el-input>
      </div>
      <div class="item1">
        <label>专家性别:</label>
        <el-select clearable v-model="serchData.gender" placeholder="请选择专家性别">
          <el-option
            v-for="item in options1"
            :key="item.gender"
            :label="item.label"
            :value="item.gender"
          ></el-option>
        </el-select>
      </div>
      <div class="item1">
        <label>专家类别:</label>
        <el-select clearable v-model="serchData.expertType" placeholder="请选择专家类别">
          <el-option
            v-for="item in expertType"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <div class="item1">
        <label>擅长行业:</label>
        <el-select clearable v-model="serchData.expertsType" placeholder="请选择擅长行业">
          <el-option
            v-for="item in options2"
            :key="item.expertsType"
            :label="item.label"
            :value="item.expertsType"
          ></el-option>
        </el-select>
      </div>
      <div class="item1">
        <label>专家职称:</label>
        <el-select clearable v-model="serchData.expertsTitle" placeholder="请选择专家职称">
          <el-option
            v-for="item in options3"
            :key="item.expertsTitle"
            :label="item.label"
            :value="item.expertsTitle"
          ></el-option>
        </el-select>
      </div>
      <div class="item1">
        <label>企业名称:</label>
        <el-input v-model="serchData.companyName" placeholder="请输入企业名称" clearable></el-input>
      </div>
      <div class="item2">
        <el-button class="resetBtn" size="small" @click="resetBtn()">重置</el-button>
        <el-button class="searchBtn" size="small" @click="querySerch()">查询</el-button>
        <el-button class="btn2" type="primary" size="small" @click="addSingle()">新增</el-button>
        <el-button class="btn1" type="danger" size="small" @click="delMore('', '2')">删除</el-button>
        <el-upload
          class="btn2"
          ref="upload"
          action="/"
          size="small"
          :file-list="fileList"
          :show-file-list="false"
          :on-change="handleChange"
          :auto-upload="false"
          style="display: inline-block; margin-left: 10px"
        >
          <el-button type="primary" size="small" icon="el-icon-upload2">导入</el-button>
        </el-upload>
        <a
          download="应急专家管理模板"
          style="font-size: 14px"
          href="template/应急专家管理模板.xlsx"
          class="sel_btn"
        >
          点击下载导入模板
          <i class="el-icon-download"></i>
        </a>
        <el-button
          class="btn3"
          type="success"
          size="small"
          icon="el-icon-download"
          style="margin-left: 10px"
          @click="exportData()"
        >导出</el-button>
      </div>
    </div>
    <div class="contentBody">
      <div class="content-top">
        <div class="top-title">查询表格</div>
        <!--        <div class="btn-list">-->
        <!--          <el-button class="btn1" type="primary" icon="el-icon-delete" @click="delMore('', '2')">删除</el-button>-->
        <!--          <el-button class="btn2" type="primary" icon="el-icon-plus" @click="addSingle()">新增</el-button>-->
        <!--          <el-upload-->
        <!--            class="btn2"-->
        <!--            ref="upload"-->
        <!--            action="/"-->
        <!--            :file-list="fileList"-->
        <!--            :show-file-list="false"-->
        <!--            :on-change="handleChange"-->
        <!--            :auto-upload="false"-->
        <!--            style="display: inline-block"-->
        <!--          >-->
        <!--            <el-button type="primary" icon="iconfont icon-daoru">导入</el-button>-->
        <!--          </el-upload>-->
        <!--          <a download="应急专家管理模板" href="template/应急专家管理模板.xlsx" class="sel_btn">-->
        <!--            点击下载导入模板-->
        <!--            <i class="el-icon-download"></i>-->
        <!--          </a>-->
        <!--          <el-button class="btn3" type="primary" icon="iconfont icon-daochu" @click="exportData()">导出</el-button>-->
        <!--        </div>-->
      </div>

      <el-table
        :data="tableData"
        style="width: 100%"
        height="616"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="name" label="专家姓名" width="100"></el-table-column>
        <el-table-column prop="sex" label="性别" width="70">
          <template slot-scope="scope">
            <div>{{ scope.row.sex === '0' ? '男' : '女' }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="birthday" label="出生日期" width="120"></el-table-column>
        <el-table-column prop="expertCategory" label="专家类型" width="180"></el-table-column>
        <el-table-column prop="expertTypeName" label="擅长行业" width="180"></el-table-column>
        <el-table-column prop="title" label="专家职称" width="200"></el-table-column>
        <el-table-column prop="company" label="专家工作单位"></el-table-column>
        <el-table-column prop="phone" label="联系方式" width="150"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <div class="operation">
              <span class="operationText" @click="detailsBtn(scope.row)">详情</span>
              <i></i>
              <span class="operationText" @click="disposalBtn(scope.row)">修改</span>
              <i></i>
              <span class="operationText" @click="delMore(scope.row, '1')">删除</span>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-body">
        <el-pagination
          background
          :current-page="currentPage"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-size="pageSize"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>

    <!-- 弹窗--居右 -->
    <!-- <AddEditingDrawer :title="title" :rowData='rowData' :showDrawer='showDrawer' :options2List='options2List'
    :options3List='options3List' />-->

    <!-- 弹窗--居中 -->
    <AddEditingDialog
      :title="title"
      :rowData="rowData"
      :showDrawer="showDrawer"
      :options2List="options2List"
      :options3List="options3List"
    />
  </div>
</template>

<script>
import axios from '@/utils/request'
import xlsx from 'xlsx'
import AddEditingDrawer from './AddEditingDrawer.vue'
import AddEditingDialog from './AddEditingDialog.vue'

export default {
  // eslint-disable-next-line vue/no-unused-components
  components: { AddEditingDrawer, AddEditingDialog },
  name: 'EmergencyExpertManagement',
  data() {
    return {
      title: '新增',
      rowData: '',
      showDrawer: {
        addEditDrawerShow: false,
        EnterprisesShow: false
      },
      serchData: {
        name: '',
        gender: '',
        expertsType: '',
        expertsTitle: '',
        companyName: '',
        expertType: ''
      },
      options1: [
        {
          gender: '',
          label: '全部'
        },
        {
          gender: '0',
          label: '男'
        },
        {
          gender: '1',
          label: '女'
        }
      ],
      expertType: [
        {
          value: '',
          label: '全部'
        },
        {
          value: '行业专家',
          label: '行业专家'
        },
        {
          value: '认证专家',
          label: '认证专家'
        }
      ],
      options2: [
        { expertsType: '1', label: '防灾减灾救灾' },
        { expertsType: '2', label: '地震地质' },
        { expertsType: '3', label: '消防' },
        { expertsType: '4', label: '防汛抗旱' },
        { expertsType: '5', label: '非煤矿山' },
        { expertsType: '6', label: '化工' },
        { expertsType: '7', label: '工商贸' },
        { expertsType: '8', label: '城市安全' },
        { expertsType: '9', label: '安全生产' },
        { expertsType: '10', label: '应急救援' }
      ],
      options3: [],
      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      checkedArry: [], // 选中的选项
      fileList: [],
      file: null,
      options2List: {
        options2: [
          { expertsType: '1', label: '防灾减灾救灾' },
          { expertsType: '2', label: '地震地质' },
          { expertsType: '3', label: '消防' },
          { expertsType: '4', label: '防汛抗旱' },
          { expertsType: '5', label: '非煤矿山' },
          { expertsType: '6', label: '化工' },
          { expertsType: '7', label: '工商贸' },
          { expertsType: '8', label: '城市安全' },
          { expertsType: '9', label: '安全生产' },
          { expertsType: '10', label: '应急救援' }
        ]
      },
      options3List: {
        options3: []
      }
    }
  },
  mounted() {
    this.getBasicExpertTitle()
    // this.getBasicExpertType()
    this.querySerch()

    this.$root.eventBus.$on('upDataTable', (data) => {
      this.querySerch()
    })
  },
  methods: {
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },

    // 擅长行业列表
    // getBasicExpertType() {
    //   this.options2 = []
    //   this.options2List.options2 = []
    //   let url = '/binhuapis/basicExpert/getBasicExpertType'
    //   axios.post(url).then((res) => {
    //     console.log(res, '擅长行业列表')
    //     let allData = res.data
    //     this.options2 = [
    //       {
    //         expertsType: '',
    //         label: '全部'
    //       }
    //     ]
    //     allData.forEach((item) => {
    //       item.expertsType = item.id
    //       item.label = item.name
    //       this.options2.push(item)
    //       this.options2List.options2.push(item)
    //     })
    //   })
    // },

    // 专家职称列表
    getBasicExpertTitle() {
      this.options3 = []
      this.options3List.options3 = []
      let url = '/binhuapis/basicExpert/getBasicExpertTitle'
      axios.post(url).then((res) => {
        console.log(res, '专家职称列表')
        let allData = res.data.data
        this.options3 = [
          {
            expertsTitle: '',
            label: '全部'
          }
        ]
        allData.forEach((item) => {
          item.expertsTitle = item.name
          item.label = item.name
          this.options3.push(item)
          this.options3List.options3.push(item)
        })
      })
    },

    // 点击重置
    resetBtn() {
      this.serchData = {
        name: '',
        gender: '',
        expertsType: '',
        expertsTitle: '',
        companyName: ''
      }
    },

    // 查询
    querySerch() {
      let url = '/binhuapis/basicExpert/getBasicExpertList?pageStart=' + this.currentPage + '&pageLen=' + this.pageSize + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      if (this.serchData.name) {
        url = url + '&name=' + this.serchData.name
      }
      if (this.serchData.gender) {
        url = url + '&sex=' + Number(this.serchData.gender)
      }
      if (this.serchData.expertsType) {
        url = url + '&type=' + this.serchData.expertsType
      }
      if (this.serchData.expertsTitle) {
        url = url + '&title=' + this.serchData.expertsTitle
      }
      if (this.serchData.companyName) {
        url = url + '&company=' + this.serchData.companyName
      }
      if (this.serchData.expertType) {
        url = url + '&expertCategory=' + this.serchData.expertType
      }
      this.loading = true
      this.tableData = []
      axios.get(url).then((res) => {
        this.loading = false
        console.log(res, '应急专家管理查询')
        if (res.data.code === 200) {
          this.total = res.data.data.count
          this.tableData = res.data.data.data
        }
      })
    },

    // 删除
    delMore(row, val) {
      if (val === '1') {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let url = '/binhuapis/basicExpert/deleteBasicExpertInfo?ids=' + row.id
          axios.post(url).then((res) => {
            console.log(res, '删除')
            if (res.data.code === 200) {
              this.$message.success('删除成功！')
              this.querySerch()
            } else {
              this.$message.warning('删除失败，请重试！')
            }
          })
        })
      } else if (val === '2') {
        if (this.checkedArry.length > 0) {
          this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let arry = []
            this.checkedArry.forEach((item) => {
              arry.push(item.id)
            })
            let url = '/binhuapis/basicExpert/deleteBasicExpertInfo?ids=' + arry.toString()
            axios.post(url).then((res) => {
              console.log(res, '删除')
              if (res.data.code === 200) {
                this.$message.success('删除成功！')
                this.querySerch()
              } else {
                this.$message.warning('删除失败，请重试！')
              }
            })
          })
        } else {
          this.$message.warning('请至少勾选一个选项！')
        }
      }
    },

    // 新增
    addSingle() {
      this.title = '新增'
      this.rowData = ''
      this.showDrawer = {
        addEditDrawerShow: true,
        DisposalDrawerShow: false
      }
    },

    // 导入
    handleChange(file, fileList) {
      this.file = file
      this.fileList = fileList
      this.importExcel()
    },
    importExcel() {
      let url = '/binhuapis/basicExpert/importExcel'
      this.file = this.fileList[0]
      // this.$refs.upload.clearFiles()
      const types = this.file.name.split('.')[1]
      const fileType = ['xlsx', 'xlc', 'xlm', 'xls', 'xlt', 'xlw', 'csv'].some((item) => item === types)
      if (!fileType) {
        this.$message.warning('格式错误！请重新选择')
        return
      }
      let formData = new FormData()
      formData.append('file', this.file.raw)
      formData.append('type', 4)
      axios
        .post(url, formData)
        .then((res) => {
          if (res.data.code === 201 || res.data.code === 200) {
            this.$message.success('导入成功')
            this.pageStart = 1
            this.querySerch() // 调用查询接口
            this.$refs.upload.clearFiles()
          } else {
            this.$message({
              type: 'error',
              message: res.data.message
            })
            this.$refs.upload.clearFiles()
          }
        })
        .catch((err) => {
          console.log(err)
          this.$message.error('导入失败')
        })
    },

    // 点击详情
    detailsBtn(row) {
      this.title = '详情'
      this.rowData = row
      this.showDrawer = {
        addEditDrawerShow: true,
        DisposalDrawerShow: false
      }
    },

    // 点击修改
    disposalBtn(row) {
      this.title = '修改'
      this.rowData = row
      this.showDrawer = {
        addEditDrawerShow: true,
        DisposalDrawerShow: false
      }
    },

    // 表格中选中的数据
    handleSelectionChange(val) {
      this.checkedArry = val
      console.log(val, '表格中选中的数据')
    },

    // 导出
    exportData() {
      if (this.checkedArry.length > 0) {
        let tempTable = []
        this.checkedArry.forEach((item, index) => {
          tempTable.push({
            序号: index + 1,
            专家姓名: item.name,
            性别: item.sex === '0' ? '男' : '女',
            出生日期: item.birthday,
            擅长行业: item.expertTypeName,
            专家职称: item.title,
            专家工作单位: item.company
          })
        })
        let ws = xlsx.utils.json_to_sheet(tempTable)
        let wb = xlsx.utils.book_new()
        // 设置列宽
        ws['!cols'] = [{ wch: 8 }, { wch: 24 }, { wch: 20 }, { wch: 30 }, { wch: 26 }, { wch: 24 }, { wch: 40 }]
        // 设置行高
        ws['!rows'] = [{ hpx: 25 }]
        this.checkedArry.forEach(() => {
          ws['!rows'].push({ hpx: 25 })
        })
        xlsx.utils.book_append_sheet(wb, ws, 'Sheet1')
        xlsx.writeFile(wb, '应急专家管理.xlsx')
      } else {
        let url = '/binhuapis/basicExpert/getBasicExpertList?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
        if (this.serchData.name) {
          url = url + '&name=' + this.serchData.name
        }
        if (this.serchData.gender) {
          url = url + '&sex=' + Number(this.serchData.gender)
        }
        if (this.serchData.expertsType) {
          url = url + '&type=' + this.serchData.expertsType
        }
        if (this.serchData.expertsTitle) {
          url = url + '&title=' + this.serchData.expertsTitle
        }
        if (this.serchData.companyName) {
          url = url + '&company=' + this.serchData.companyName
        }
        axios.get(url).then((res) => {
          this.loading = false
          if (res.data.code === 200) {
            let allData = res.data.data.data
            let tempTable = []
            allData.forEach((item, index) => {
              tempTable.push({
                序号: index + 1,
                专家姓名: item.name,
                性别: item.sex === '0' ? '男' : '女',
                出生日期: item.birthday,
                擅长行业: item.expertTypeName,
                专家职称: item.title,
                专家工作单位: item.company
              })
            })
            let ws = xlsx.utils.json_to_sheet(tempTable)
            let wb = xlsx.utils.book_new()
            // 设置列宽
            ws['!cols'] = [{ wch: 8 }, { wch: 24 }, { wch: 20 }, { wch: 30 }, { wch: 26 }, { wch: 24 }, { wch: 40 }]
            // 设置行高
            ws['!rows'] = [{ hpx: 25 }]
            allData.forEach(() => {
              ws['!rows'].push({ hpx: 25 })
            })
            xlsx.utils.book_append_sheet(wb, ws, 'Sheet1')
            xlsx.writeFile(wb, '应急专家管理.xlsx')
          }
        })
      }
    },

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.querySerch()
    },

    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
      this.querySerch()
    }
  }
}
</script>

<style lang="scss">
.EmergencyExpertManagement {
  background: transparent !important;
  box-sizing: border-box;
  padding: 0 !important;

  .headerTop {
    width: 100%;
    height: 95px;
    //background: #ffffff;
    //padding: 0 30px;
    display: flex;
    align-items: center;
    flex-wrap: wrap;

    .item1 {
      margin-right: 10px;
      margin-bottom: 20px;
    }
    .item2 {
      margin-bottom: 20px;
    }

    label {
      margin-right: 10px;
      vertical-align: middle;
      line-height: 40px;
      padding: 0 12px 0 0;
      box-sizing: border-box;
      font-size: 14px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #606d80;
    }

    .el-input {
      width: 217px;
      margin-left: -10px;

      .el-input__inner {
        height: 32px;
        line-height: 32px;
      }

      .el-input__suffix {
        top: 0px;

        .el-input__icon {
          line-height: 32px;
        }
      }
    }

    .el-input__inner {
      height: 32px;
      line-height: 32px;

      .el-range__icon,
      .el-range-separator,
      .el-range__close-icon {
        line-height: 28px;
      }

      .el-range-separator {
        width: 10%;
      }
    }

    .searchBtn {
      width: 69px;
      //background: linear-gradient(90deg, #0093ff 0%, #056cf2 100%);
      background-color: #0052d9;
      border-radius: 2px;
      border: 0 none;
      font-size: 14px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #ffffff;
      margin-left: 10px;
      padding: 0;
      height: 32px;
    }

    .resetBtn {
      width: 69px;
      height: 32px;
      border: 1px solid #dbe0e7;
      border-radius: 2px;
      font-size: 14px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #666666;
      padding: 0;
    }
  }

  .contentBody {
    margin-top: 20px;
    width: 100%;
    height: calc(100% - 152px);
    background: #ffffff;
    padding: 0px 20px 10px 20px;
    box-sizing: border-box;

    .content-top {
      width: 100%;
      height: 70px;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .top-title {
        font-size: 18px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #4e5a6e;
      }

      .el-button {
        width: 88px;
        height: 32px;
        line-height: 0px;
        border-radius: 2px;
        font-size: 18px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #ffffff;
        padding: 0;
        border: 0 none;

        span {
          margin-left: 5px;
        }

        .el-icon-delete,
        .el-icon-plus,
        .icon-daoru,
        .icon-daochu {
          font-size: 20px;
        }
      }

      .btn1 {
        background: #ffffff;
        color: #666666;
        border: 1px solid #dbe0e7;
      }

      .btn2 {
        background: linear-gradient(90deg, #0093ff 0%, #056cf2 100%);
      }

      .btn3 {
        background: linear-gradient(90deg, #52c41a 0%, #88d544 100%);
      }
    }

    .operationText {
      cursor: pointer;
    }
    .operationText:hover {
      color: #056cf2;
    }

    .el-table {
      th.el-table__cell {
        background: #eff2f5;
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #606d80;
        text-align: center;
        height: 56px;
      }

      td.el-table__cell {
        text-align: center;
        height: 56px;
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #202d40;
        border-bottom: 1px solid #d3dae8;

        .colorStyle {
          color: rgba(225, 143, 0, 0.85);
        }

        .operation {
          display: flex;
          width: 100%;
          align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #0093ff;
          justify-content: center;

          span {
            cursor: pointer;
          }
          i {
            width: 1px;
            height: 14px;
            background: #d3dae8;
            margin: 0 20px;
          }
        }
      }
    }
    .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
      background-color: #e4f4ff;
    }

    .pagination-body {
      margin-top: 30px;
    }
  }
}
</style>
