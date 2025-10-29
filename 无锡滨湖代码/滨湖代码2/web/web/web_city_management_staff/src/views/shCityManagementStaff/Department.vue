<template>
  <div class="Department">
    <div class="table-dec-warp">
      <div class="add-div">
        <div class="border-btn" @click="showAddRiskDialog('1')">
          <i class="el-icon-plus"></i> 新增
        </div>
        <div style="display:flex;margin-left: 50px;">
            <el-input style="width: 500px"
            clearable
              placeholder="请输入部门名称"
              v-model="search">
            </el-input>
            <el-button icon="el-icon-search" @click="leftSearch"></el-button>
          </div>
      </div>
        <div class="ctrl-list">
          <div class="ctrl-btn" @click="batchDelete"><i class="el-icon-delete-solid"></i>批量删除</div>
        </div>
    </div>
    <div class="table-container">
      <el-table
        ref="multipleTable"
        height="1340"
        :data="showData"
        :row-class-name="tableRowClassName"
         @selection-change="handleSelectionChange"
         :row-key="getRowKeys"
        style="width: 100%"
      >
      <el-table-column type="selection" :reserve-selection="true" width="100">
        </el-table-column>
        <el-table-column prop="name" label="部门名称"></el-table-column>
        <el-table-column prop="superName" label="上级部门名称"></el-table-column>
        <el-table-column prop="precinctName" label="所属区域"></el-table-column>
        <el-table-column
          prop="type"
          label="部门类型"
        ></el-table-column>
        <el-table-column label="操作" width="360">
          <template slot-scope="scope">
            <el-button  @click="showAddRiskDialog('2', scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="delClick(scope.row)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <div class="div-item r">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <k-dialog
      :dialog-title="showIndex === '1' ? '导入部门': '编辑部门'"
      :dialog-visible="dialogVisible"
      @closeDialog="closeDialog"
    >
      <template>
        <div class="AddRiskDialog" v-if="showIndex === '1'">
          <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
             <el-menu-item index="1">单个新增</el-menu-item>
             <el-menu-item index="2">批量导入</el-menu-item>
          </el-menu>
          <div class="form-container" v-if="activeIndex === '2'">
            <el-form
              ref="form"
              :model="form"
              label-width="260px"
            >
            <el-form-item label="下载模板">
            <el-link @click="modeldown">单位录入模板.xlsx</el-link>
            </el-form-item>
              <el-form-item label="上传文件" prop="fileurl">
                <el-upload
                  action="http://localhost/nginx/uploadByAction"
                  list-type="picture-card"
                  :http-request="uploading"
                  :before-upload="beforeUpload"
                  :limit="1"
                  :file-list="newList"
                  :on-exceed="handleExceed"
                  ref="upload"
                >
                  <i class="el-icon-plus"></i>
                  <div slot="file" slot-scope="{ file }">
                    <img
                      class="el-upload-list__item-thumbnail"
                      style="width: 75%; height: 65%; margin-top: 5px;margin-left: 12%;"
                      src="../../assets/global/excel.png"
                    />
                    <div class="nameSlot" :title="file.name">{{ file.name }}</div>
                    <span class="el-upload-list__item-actions">
                      <span
                        class="el-upload-list__item-delete"
                        @click="handleDownload(file)"
                      >
                        <i class="el-icon-download"></i>
                      </span>
                      <span
                        class="el-upload-list__item-delete"
                        @click="handleRemove(file)"
                      >
                        <i class="el-icon-delete"></i>
                      </span>
                    </span>
                  </div>
                </el-upload>
              </el-form-item>
              <el-form-item>
                <el-button @click="upSubmit">确定</el-button>
              </el-form-item>
            </el-form>
          </div>
          <div class="form-container" v-else>
            <el-form ref="form" :model="form" label-width="270px">
            <el-form-item label="部门名称">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="上级部门名称">
              <el-cascader placeholder="请选择上级部门" v-model="form.superName" :options="superNameOption" :props="{ value: 'id', label: 'name', checkStrictly: true }" clearable>
                <template slot-scope="{ node, data }">
                 <span>{{ data.name }}</span>
                </template>
              </el-cascader>
            </el-form-item>
              <el-form-item label="部门类型">
                <el-select
                  v-model="form.emergencyPlanId"
                  placeholder="请选择部门类型"
                  filterable
                >
                  <el-option
                    v-for="item in emergencyPlanOptions"
                    :key="item.name"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">确定</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div class="AddRiskDialog" v-else>
        <div class="form-container">
            <el-form ref="form" :model="form" label-width="270px">
            <el-form-item label="部门名称">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="上级部门名称">
                <el-cascader placeholder="请选择上级部门" v-model="form.superName" :options="superNameOption" :props="{ value: 'id', label: 'name', checkStrictly: true }" clearable>
                <template slot-scope="{ node, data }">
                 <span>{{ data.name }}</span>
                </template>
              </el-cascader>
            </el-form-item>
              <el-form-item label="部门类型">
                <el-select
                  v-model="form.emergencyPlanId"
                  placeholder="请选择部门类型"
                  filterable
                >
                  <el-option
                    v-for="item in emergencyPlanOptions"
                    :key="item.name"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">确定</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </template>
    </k-dialog>
  </div>
</template>

<script>
import KDialog from '../../components/KDialog'
import Axios from '@/utils/request'
export default {
  name: 'Department',
  components: { KDialog },
  data() {
    return {
      tableData: [],
      currentPage: 1,
      activeIndex: '1',
      pageSize: 10,
      showIndex: '',
      total: 0,
      dialogVisible: false,
      form: {
        emergencyPlanId: '',
        filename: '',
        fileurl: '',
        name: '',
        id: '',
        superName: []
      },
      emergencyPlanOptions: [{
        id: 1,
        name: '政府单位'
      }, {
        id: 2,
        name: '企业单位'
      }, {
        id: 3,
        name: '属地单位'
      }],
      planIdList: [],
      newList: [],
      hideUpload: false,
      search: '',
      multipleSelection: [],
      superNameOption: []
    }
  },
  watch: {
    tableData: {
      handler(v) {
        this.total = v.length
        this.planIdList = v.map((e) => e.emergencyPlanId)
      }
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
  mounted() {
    this.superNameOptions()
    this.getEmergencyPlanOptions()
  },
  methods: {
    superNameOptions() {
      let url = '/apis/communication/unitInfoTree?type=1,2,3'
      if (this.$store.getters.eventId.id) {
        url += '&eventId=' + this.$store.getters.eventId.id
      }
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.superNameOption = data || []
      })
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    getRowKeys(row) {
      return row.id
    },
    batchDelete() {
      if (this.multipleSelection.length === 0) {
        return
      }
      let selectList = this.multipleSelection.map(item => {
        return item.id
      })
      let row = {
        id: selectList.join(',')
      }
      this.delClick(row)
    },
    onChange(file, fileList) {
      this.hideUpload = true
    },
    leftSearch() {
      this.getEmergencyPlanOptions()
    },
    upSubmit() {
      if (this.newList.length > 0) {
        let url = '/apis/communication/uploadUnitExcel'
        let formData = new FormData()
        formData.append('file', this.newList[0].raw)
        formData.append('group', 'system')
        Axios.post(url, formData).then((res) => {
          if (res.data.data.length === 0 && res.data.code === 200) {
            this.$message.success('新增成功')
            this.dialogVisible = false
            this.superNameOptions()
            this.getEmergencyPlanOptions()
          } else {
            let dataList = []
            res.data.data.forEach(item => {
              for (let key in item) {
                dataList.push(key + ':' + item[key])
              }
            })
            let reg = /[,，]/g
            let showData = dataList.join(',').replace(reg, ' <br/> ')
            this.$message({
              dangerouslyUseHTMLString: true,
              message: showData,
              type: 'warning'
            })
          }
        })
      } else {
        this.dialogVisible = false
      }
    },
    handleExceed(files, fileList) {
      this.$message.warning('上传文件数不能超过1！')
    },
    handleSelect(key, keyPath) {
      this.activeIndex = key
    },
    handleClick(row) {
      console.log(row)
    },
    delClick(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/apis/communication/deleteUnitById?ids=' + row.id
        Axios.post(url).then((res) => {
          if (res.data && res.data.code === 200) {
            this.currentPage = 1
            this.getEmergencyPlanOptions()
            this.$message.success('删除成功！')
          } else {
            this.$message.error(res.data.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    modeldown() {
      let a = document.createElement('a')
      a.href = '/static/单位录入模板.xlsx'
      a.download = '单位录入模板.xlsx'
      a.style.display = 'none'
      document.body.appendChild(a)
      a.click()
      a.remove()
    },
    beforeUpload(file) {
      console.log(file)
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      const extension = testmsg === 'xlsx' || testmsg === 'xls'
      const isLt2M = file.size / 1024 / 1024 < 20
      if (!extension) {
        this.$message({
          message: '上传文件只能是xlsx/xls格式!',
          type: 'warning'
        })
      }
      if (!isLt2M) {
        this.$message.warning('上传文件大小不能超过20MB!')
      }
      return extension && isLt2M
    },
    handleRemove(file) {
      console.log(file, this.newList)
      console.log(file)
      let index = this.newList.indexOf(file)
      this.newList.splice(index, 1)
      this.form.fileurl = ''
      this.hideUpload = false
      this.$message.success('删除成功！')
    },
    handleDownload(file) {
      console.log(file)
      let url2 = file.url.replace(/\\/g, '/')
      let xhr = new XMLHttpRequest()
      xhr.open('GET', url2, true)
      xhr.responseType = 'blob'
      xhr.onload = () => {
        if (xhr.status === 200) {
          this.saveAs(xhr.response, file.name)
        }
      }
      xhr.send()
    },
    saveAs(data, name) {
      let urlObject = window.URL || window.webkitURL || window
      // eslint-disable-next-line camelcase
      let export_blob = new Blob([data])
      // eslint-disable-next-line camelcase
      let save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a')
      save_link.href = urlObject.createObjectURL(export_blob)
      save_link.download = name
      save_link.click()
    },
    uploading(e) {
      this.newList = []
      let formData = new FormData()
      formData.append('file', e.file)
      formData.append('group', 'system')
      this.$message.success('上传成功！')
      this.newList = this.$refs.upload.uploadFiles
    },
    getEmergencyPlanOptions() {
      let url = '/apis/communication/queryUnitList'
      if (this.search) {
        url += '?name=' + this.search
      }
      Axios.get(url).then((res) => {
        let data = res.data.data
        data.forEach(item => {
          item.active = false
          if (item.type === '1') {
            item.type = '政府单位'
          } else if (item.type === '2') {
            item.type = '企业单位'
          } else {
            item.type = '属地单位'
          }
        })
        this.tableData = data || []
        this.total = this.tableData.length
      })
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'even-row'
      } else if (rowIndex % 2 === 1) {
        return 'odd-row'
      }
      return ''
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.showData = this.tableData.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      )
      console.log(`当前页: ${val}`)
    },
    deleteClick(index) {
      this.tableData.splice(index, 1)
    },
    showAddRiskDialog(index, row) {
      this.showIndex = ''
      this.showIndex = index
      this.hideUpload = false
      this.newList = []
      this.form.superName = []
      if (this.showIndex === '1') {
        this.form.emergencyPlanId = ''
        this.form.name = ''
        this.form.id = ''
        this.form.superName = []
      } else {
        this.form.emergencyPlanId = row.type
        this.form.name = row.name
        this.form.id = row.id
        if (row.superId !== '-1') {
          this.form.superName = row.superId
        } else {
          this.form.superName = []
        }
      }
      this.dialogVisible = true
    },
    onSubmit(form) {
      if (!this.form.emergencyPlanId) {
        this.$message.warning('请选择部门类型！')
        return
      }
      if (!this.form.name) {
        this.$message.warning('请选择部门名称！')
        return
      }
      let url = '/apis/communication/saveUnit'
      if (this.form.emergencyPlanId === '政府单位') {
        this.form.emergencyPlanId = '1'
      } else if (this.form.emergencyPlanId === '企业单位') {
        this.form.emergencyPlanId = '2'
      } else if (this.form.emergencyPlanId === '属地单位') {
        this.form.emergencyPlanId = '3'
      }
      let unit = {
        areaId: this.$store.getters.precinctId,
        name: this.form.name,
        type: this.form.emergencyPlanId,
        superId: Array.isArray(this.form.superName) ? this.form.superName.pop() : this.form.superName
      }
      if (this.showIndex && this.showIndex === '2') {
        unit.id = this.form.id
      }
      Axios.post(url, unit).then((res) => {
        if (res.data.code === 200) {
          if (this.showIndex && this.showIndex === '2') {
            this.$message.success('编辑成功！')
          } else {
            this.$message.success('新增成功！')
          }
        } else {
          this.$message.warning(res.data.message)
        }
        this.getEmergencyPlanOptions()
        this.superNameOptions()
        this.dialogVisible = false
      })
    },
    closeDialog: function (v) {
      this.dialogVisible = false
    }
  }
}
</script>

<style lang="scss">
.Department {
  position: relative;
  width: 100%;
  padding: 40px 62px 0px;
  .el-menu {
    top: 0 !important;
  }
  .el-button {
   font-size: 40px;
   line-height: 80px;
   padding: 0 40px;
 }
 .hide .el-upload--picture-card {
  display: none;
}
.el-input .el-input__clear {
  font-size: 36px;
}

  .table-dec-warp {
    line-height: 80px;
    margin-bottom: 40px;
    display: flex;
    justify-content: space-between;

    .ctrl-list {
      float: right;
      position: relative;

      .ctrl-btn {
        display: inline-block;
        width: 291px;
        height: 80px;
        text-align: center;
        background: rgba(255, 255, 255, 1);
        border: 2px solid;
        border-radius: 8px;
        margin: 0 20px;
        line-height: 76px;
        cursor: pointer;

        i {
          font-size: 40px;
        }

        &:first-child {
          color: #ff5858;
          border-color: #ff5858;
        }
      }
    }

    .add-div {
      position: relative;
      // margin-right: 800px;
      display: flex;

      .el-select {
        width: 738px;
      }
    }
  }

  .table-container {
    position: relative;
    /*height: 1680px;*/
  }
  .pagination-container {
    height: 80px;
    line-height: 80px;
    margin-bottom: 40px;

    .l {
      float: left;
    }
    .r {
      float: right;
    }
  }

  .submit-btn-warp {
    position: relative;
    text-align: center;

    .btn-item {
      margin: 0 40px;
    }
  }
  .AddRiskDialog {
    position: relative;
    width: 100%;
    height: 100%;
    .el-menu-item {
      font-size: 40px;
    }
    .el-menu-item:first-of-type {
      margin-left: 58px;
    }
    .el-menu.el-menu--horizontal {
      margin-top: 30px;
    }
    .el-select {
      width: 100%;
    }
    .el-cascader {
      width: 100%;
    }
    .form-container {
      padding: 80px;
      .nameSlot {
        margin-top: -8px;
        font-size: 35px;
        width: 260px;
        overflow: hidden;
        margin-left: 12px;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      .el-link--inner {
        font-size: 40px;
        margin-top: 20px;
      }
      .el-upload--picture-card {
          width: 300px;
          height: 300px;
          line-height: 300px;
      }
    .el-upload-list--picture-card .el-upload-list__item {
          width: 300px;
          height: 300px;
      }
      .el-upload-list--picture-card .el-upload-list__item-actions .el-upload-list__item-delete {
          font-size: 50px;
      }
    }

    .list {
      line-height: 80px;
      font-size: 40px;
      float: left;
    }
  }
}
</style>
