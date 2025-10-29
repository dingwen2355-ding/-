<template>
  <div class="Emergencyment">
    <div class="table-dec-warp">
      <div class="add-div">
        <div class="border-btn" @click="showAddRiskDialog('1')">
          <i class="el-icon-plus"></i> 新增
        </div>
        <!-- <div style="display: flex; margin-left: 50px" v-if="!precinctId">
          <el-select clearable v-model="value" placeholder="请选择区域">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div> -->
        <div style="display: flex; margin-left: 50px">
         <el-select clearable v-model="search1" placeholder="请选择预案类型">
            <el-option
              v-for="item in options1"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId"
            >
            </el-option>
          </el-select>
        </div>
        <div style="display:flex;margin-left: 50px;">
            <el-input style="width: 500px"
            clearable
              placeholder="请输入预案名称"
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
        <el-table-column prop="planName" label="预案名称"></el-table-column>
        <el-table-column prop="planFile" label="预案pdf"></el-table-column>
        <el-table-column prop="typeName" label="预案类型"></el-table-column>
        <el-table-column prop="precinctName" label="所属区域"></el-table-column>
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
      :dialog-title="showIndex === '1' ? '导入预案': '编辑预案'"
      :dialog-visible="dialogVisible"
      @closeDialog="closeDialog"
    >
      <template>
        <div class="AddRiskDialog" v-if="showIndex === '1'">
          <div class="form-container">
            <el-form
              ref="form"
              :model="form"
              label-width="260px"
            >
            <!-- <el-form-item label="下载模板">
            <el-link @click="modeldown">通讯录录入模板.xlsx</el-link>
            </el-form-item> -->
            <el-form-item label="预案名称">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="预案类型" >
              <el-select clearable v-model="form.search" placeholder="请选择预案类型">
                <el-option
                  v-for="item in options1"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeId"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所属区域" v-if="!precinctId">
              <el-select clearable v-model="form.value" placeholder="请选择区域">
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
              <el-form-item label="上传pdf/图片" prop="fileurl">
                <el-upload
                  action="/"
                  :class="{hide:hideUpload}"
                  list-type="picture-card"
                  :http-request="uploading"
                  :on-change="onChange"
                  :before-upload="beforeUpload"
                  :limit="1"
                  :file-list="newList"
                  ref="upload"
                >
                  <i class="el-icon-plus"></i>
                  <div slot="file" slot-scope="{ file }">
                    <img
                      class="el-upload-list__item-thumbnail"
                      style="width: 75%; height: 65%; margin-top: 5px;margin-left: 12%;"
                      :src="file.url"
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
                <el-button type="primary" @click="onSubmit('1')">确定</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div class="AddRiskDialog" v-else>
        <div class="form-container">
            <el-form
              ref="form"
              :model="form"
              label-width="260px"
            >
            <!-- <el-form-item label="下载模板">
            <el-link @click="modeldown">通讯录录入模板.xlsx</el-link>
            </el-form-item> -->
            <el-form-item label="预案名称">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="预案类型">
              <el-select clearable v-model="form.search" placeholder="请选择预案类型">
                <el-option
                  v-for="item in options1"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeId"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所属区域" v-if="!precinctId">
              <el-select clearable v-model="form.value" placeholder="请选择区域">
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
              <el-form-item label="上传pdf/图片" prop="fileurl">
                <el-upload
                  action="https://jsonplaceholder.typicode.com/posts/"
                  list-type="picture-card"
                  :http-request="uploading"
                  :before-upload="beforeUpload"
                  :class="{hide:hideUpload}"
                  :on-change="onChange"
                  :limit="limitCount"
                  :file-list="newList"
                  :on-exceed="handleExceed"
                  ref="upload"
                >
                  <i class="el-icon-plus"></i>
                  <div slot="file" slot-scope="{ file }">
                    <img
                      class="el-upload-list__item-thumbnail"
                      style="width: 75%; height: 65%; margin-top: 5px;margin-left: 12%;"
                      :src="file.url | picFilter"
                    />
                    <div class="nameSlot" :title="file.name">{{ file.name }}</div>
                    <span class="el-upload-list__item-actions">
                        <span class="el-upload-list__item-preview"
                          @click="handlePictureCardPreview(file)">
                         <i class="el-icon-zoom-in"></i>
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
                <el-button type="primary" @click="onSubmit('2')">确定</el-button>
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
  name: 'Emergencyment',
  components: { KDialog },
  data() {
    return {
      tableData: [],
      currentPage: 1,
      activeIndex: '1',
      pageSize: 6,
      showIndex: '',
      total: 0,
      dialogVisible: false,
      precinctId: this.$store.getters.precinctId,
      form: {
        emergencyPlanId: '',
        filename: '',
        fileurl: '',
        name: '',
        id: '',
        phone: '',
        emergyPerson: '',
        search: '',
        value: ''
      },
      emergencyPlanOptions: [],
      options: [{
        id: '510100',
        name: '无锡市'
      }],
      options1: [],
      planIdList: [],
      newList: [],
      hideUpload: false,
      value: '',
      limitCount: 1,
      search: '',
      search1: '',
      multipleSelection: []
    }
  },
  filters: {
    picFilter(url) {
      if (
        url.includes('.png') ||
        url.includes('.PNG') ||
        url.includes('jpg')
      ) {
        return url
      } else if (url.includes('.pdf') || url.includes('.PDF')) {
        return require('../../assets/global/pdf.png')
      }
    }
  },
  watch: {
    value: {
      handler(v) {
        this.getEmergencyPlanOptions()
      }
    },
    search1: {
      handler(v) {
        this.getEmergencyPlanOptions()
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
    // let obj = {
    //   id: '510100',
    //   name: '达州市'
    // }
    // this.options = JSON.parse(JSON.stringify(sysConfig.area))
    // this.options.unshift(obj)
    this.getEmergencyPlanOptions()
    this.getEmergencyPlanOptions1()
  },
  methods: {
    handlePictureCardPreview(file) {
      window.open(file.url)
    },
    getRowKeys(row) {
      return row.id
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    getEmergencyPlanOptions1() {
      let url = '/apis/emergencyPlan/queryPlanTypeList'
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.options1 = data || []
      })
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
    leftSearch() {
      this.getEmergencyPlanOptions()
    },
    onChange(file, fileList) {
      this.hideUpload = true
    },
    handleExceed(files, fileList) {
      this.$message.warning('上传文件数不能超过1！')
    },
    handleChange(file, fileList) {
      console.log(file, fileList)
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
        let url = '/apis/emergencyPlan/deletePlanByIds?ids=' + row.id
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
    // modeldown() {
    //   let a = document.createElement('a')
    //   a.href = '/static/通讯录录入模板.xlsx'
    //   a.download = '通讯录录入模板.xlsx'
    //   a.style.display = 'none'
    //   document.body.appendChild(a)
    //   a.click()
    //   a.remove()
    // },
    beforeUpload(file) {
      console.log(file)
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      const extension = testmsg === 'pdf' || testmsg === 'png' || testmsg === 'jpeg' || testmsg === 'jpg'
      const isLt2M = file.size / 1024 / 1024 < 20
      if (!extension) {
        this.$message({
          message: '上传文件只能是pdf/png/jpg格式!',
          type: 'warning'
        })
      }
      if (!isLt2M) {
        this.$message.warning('上传文件大小不能超过20MB!')
      }
      return extension && isLt2M
    },
    handleRemove(file, fileList) {
      console.log(file, this.newList)
      console.log(file)
      let index = this.newList.indexOf(file)
      this.newList.splice(index, 1)
      this.form.fileurl = ''
      this.hideUpload = false
      this.$message.success('删除成功！')
    },
    handleDownload(file) {
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
      let url = '/apis/emergencyPlan/queryPlanList'
      if (this.search) {
        url += url.includes('?') ? '&name=' + this.search : '?name=' + this.search
      }
      if (this.value) {
        url += url.includes('?') ? '&precinctId=' + this.value : '?precinctId=' + this.value
      }
      if (this.search1) {
        url += url.includes('?') ? '&typeId=' + this.search1 : '?typeId=' + this.search1
      }
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.tableData = data || []
        // data.forEach(item => {
        //   this.options.forEach(r => {
        //     if (item.precinctId === r.id) {
        //       item.precinctName = r.name
        //     }
        //   })
        // })
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
      if (this.showIndex === '1') {
        this.form.emergencyPlanId = ''
        this.form.name = ''
        this.form.id = ''
        this.form.phone = ''
        this.newList = []
        this.form.search = ''
        this.form.value = ''
      } else {
        this.form.name = row.planName
        this.form.id = row.id
        this.options1.forEach(item => {
          if (item.typeName === row.typeName) {
            this.form.search = item.typeId
          }
        })
        this.form.value = row.precinctId
        this.newList = row.planFile.split(',').map((item) => {
          return {
            name: item.split('/').pop(),
            percentage: 0,
            raw: File,
            size: 6194,
            status: 'ready',
            uid: row.planName + new Date().getTime(),
            url: item
          }
        })
        this.hideUpload = true
      }
      this.dialogVisible = true
    },
    onSubmit(form) {
      if (form === '1') {
        if (!this.form.name) {
          this.$message.warning('请输入预案名称！')
          return
        }
        if (!this.form.search) {
          this.$message.warning('请选择预案类型！')
          return
        }
        if (this.newList.length === 0) {
          this.$message.warning('请上传pdf/图片！')
          return
        }
      } else {
        if (!this.form.name) {
          this.$message.warning('请输入预案名称！')
          return
        }
        if (!this.form.search) {
          this.$message.warning('请选择预案类型！')
          return
        }
      }
      let url = form === '1' ? ('/apis/emergencyPlan/addPlan?planName=' + this.form.name) : ('/apis/emergencyPlan/updatePlan?planId=' + this.form.id + '&planName=' +
      this.form.name)
      if (this.form.search) {
        url += '&planTypeId=' + this.form.search
      }
      if (!this.precinctId) {
        if (this.form.value) {
          url += '&precinctId=' + this.form.value
        }
      }
      let formData = new FormData()
      formData.append('file', this.newList[0].raw)
      formData.append('group', 'system')
      Axios.post(url, formData).then((res) => {
        if (this.showIndex && this.showIndex === '2') {
          this.$message.success('编辑成功！')
        } else {
          this.$message.success('新增成功！')
        }
        this.getEmergencyPlanOptions()
      })
      this.dialogVisible = false
    },
    closeDialog: function (v) {
      this.dialogVisible = false
    }
  }
}
</script>

<style lang="scss">
.Emergencyment {
  position: relative;
  width: 100%;
  padding: 40px 62px 0px;
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
 .el-form {
  .el-form-item {
    margin-bottom: 44px;
  }
  .el-form-item__label {
    font-size: 40px;
    line-height: 80px;
    padding: 0 16px 0 0;
  }
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
      .el-upload-list--picture-card .el-upload-list__item-actions .el-upload-list__item-preview {
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
