<template>
  <div class="videoMeeting">
    <el-menu
      :default-active="activeIndexs"
      class="el-menu-demo"
      style="margin: -70px 0 50px 0;"
      mode="horizontal"
      @select="handleSelect1"
      text-color="#000"
      active-text-color="#019bfd"
    >
      <el-menu-item index="1">区域分组</el-menu-item>
      <el-menu-item index="2">预案模板</el-menu-item>
    </el-menu>
    <div v-show="activeShow === '1'">
      <div class="table-dec-warp">
        <div class="add-div">
          <div class="border-btn" @click="showAddRiskDialog('1')">
            <i class="el-icon-plus"></i> 新增
          </div>
          <div style="display: flex; margin-left: 50px">
            <el-input
              style="width: 500px"
              clearable
              placeholder="请输入分组名"
              v-model="search"
            >
            </el-input>
            <el-button icon="el-icon-search" @click="leftSearch"></el-button>
          </div>
        </div>
        <div class="ctrl-list">
          <div class="ctrl-btn" @click="batchDelete">
            <i class="el-icon-delete-solid"></i>批量删除
          </div>
        </div>
      </div>
      <div class="table-container">
        <el-table
          ref="multipleTable"
          height="1180"
          :data="showData"
          :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange"
          :row-key="getRowKeys"
          style="width: 100%"
        >
          <el-table-column
            type="selection"
            :reserve-selection="true"
            width="100"
          >
          </el-table-column>
          <el-table-column prop="uriObj" label="已绑定会场"></el-table-column>
          <el-table-column
            prop="name"
            label="分组名"
             width="600"
          ></el-table-column>
          <el-table-column prop="type" label="类型" width="600"></el-table-column>
          <el-table-column label="操作" width="360">
            <template slot-scope="scope">
              <el-button
                @click="showAddRiskDialog('2', scope.row)"
                type="text"
                size="small"
                >编辑</el-button
              >
              <el-button @click="delClick(scope.row)" type="text" size="small"
                >删除</el-button
              >
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
        :dialog-title="showIndex === '1' ? '新增分组' : '编辑分组'"
        :dialog-visible="dialogVisible"
        @closeDialog="closeDialog"
      >
        <template>
          <div class="AddRiskDialog" v-if="showIndex === '1'">
            <div class="form-container">
              <el-form ref="form" :model="form" label-width="270px">
                <el-form-item label="分组名">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="会场">
                  <!-- <el-select
                    v-model="form.uri"
                    multiple
                    placeholder="请选择会场"
                    filterable
                  >
                    <el-option
                      v-for="item in superNameOption"
                      :key="item.uri"
                      :label="item.name"
                      :value="item.uri"
                    ></el-option>
                  </el-select> -->
                  <el-input v-model="form.uri" @focus="handleSelect"></el-input>
                </el-form-item>
                <el-form-item label="上级分组">
                  <el-select
                    v-model="form.emergencyPlanId"
                    placeholder="请选择上级"
                    clearable
                    filterable
                  >
                    <el-option
                      v-for="(item, index) in emergencyPlanOptions"
                      :key="index"
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
                <el-form-item label="分组名">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="会场">
                  <!-- <el-select
                    v-model="form.uri"
                    multiple
                    placeholder="请选择会场"
                    filterable
                  >
                    <el-option
                      v-for="item in superNameOption"
                      :key="item.uri"
                      :label="item.name"
                      :value="item.uri"
                    ></el-option>
                  </el-select> -->
                  <el-input v-model="form.uri" @focus="handleSelect"></el-input>
                </el-form-item>
                <el-form-item label="上级分组">
                  <el-select
                    v-model="form.emergencyPlanId"
                    placeholder="请选择上级"
                    filterable
                  >
                    <el-option
                      v-for="(item, index) in emergencyPlanOptions"
                      :key="index"
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
    <div v-show="activeShow === '2'">
      <linkMeeting />
    </div>
    <div class="addDialog" v-show="addDialogRight">
      <div class="add-dialog-content" style="width: 1200px">
        <div class="add-dialog-title">
          <div class="title-content">
            <i class="iconfont iconzhgl_title"></i>
            <span>新增</span>
            <i class="iconfont iconzhgl_title"></i>
          </div>
          <div class="close-icon" @click="addDialogRight = false">
            <i class="el-icon-close"></i>
          </div>
        </div>
        <div class="transfer-content">
        <div style="display:flex">
        <div class="leftForm">
          <div class="topTitle">可选列表</div>
          <div style="display:flex;margin-top:5px">
            <el-input
              placeholder="请输入搜索内容"
              v-model="allForm.search">
            </el-input>
            <el-button style="font-size: 30px;" icon="el-icon-search" @click="allSearch"></el-button>
          </div>
          <div class="mainContent">
            <el-checkbox-group v-model="allForm.chooseList">
              <el-checkbox class="leftItem" v-for="(item,index) in superNameOption" :key="index" :label="item.uri">
                {{item.name}}
              </el-checkbox>
            </el-checkbox-group>
          </div>
        </div>
      </div>
          <div class="btn">
            <div class="item item_btn1" @click="submitOut">取消</div>
            <div class="item item_btn2" @click="submitRight">提交</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import KDialog from '../../components/KDialog'
import linkMeeting from './linkMeeting'
import Axios from '@/utils/request'
export default {
  name: 'videoMeeting',
  components: { KDialog, linkMeeting },
  data() {
    return {
      tableData: [],
      currentPage: 1,
      activeIndex: '1',
      activeIndexs: '1',
      pageSize: 6,
      showIndex: '',
      total: 0,
      dialogVisible: false,
      form: {
        emergencyPlanId: '',
        filename: '',
        fileurl: '',
        name: '',
        id: '',
        uri: '',
        superName: []
      },
      allForm: {
        pageStart: 1,
        pageLen: 20,
        search: '',
        chooseList: [],
        currentPage: 1,
        count: 0
      },
      emergencyPlanOptions: [],
      emergencyPlanOptions1: [],
      planIdList: [],
      newList: [],
      hideUpload: false,
      search: '',
      activeShow: '1',
      multipleSelection: [],
      superNameOption: [],
      value: '区域分组',
      superNameOptionAll: [],
      addDialogRight: false
    }
  },
  watch: {
    tableData: {
      handler(v) {
        this.total = v.length
        this.planIdList = v.map((e) => e.emergencyPlanId)
      }
    },
    value: {
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
    this.superNameOptions()
    this.getEmergencyPlanOptions()
  },
  methods: {
    submitOut() {
      if (!this.form.uri) {
        this.allForm.chooseList = []
      }
      this.addDialogRight = false
    },
    submitRight() {
      this.form.uri = ''
      this.form.uri = this.allForm.chooseList.length !== 0 ? this.allForm.chooseList.join(',') : ''
      this.allForm.search = ''
      this.addDialogRight = false
    },
    allSearch() {
      if (this.allForm.search) {
        this.superNameOption = this.superNameOption.filter(item => item.name.includes(this.allForm.search))
      } else {
        this.superNameOption = this.superNameOptionAll
      }
    },
    handleSelect1(key, keyPath) {
      if (key === '1') {
        this.value = '区域分组'
        this.activeShow = '1'
        this.superNameOptions()
        this.getEmergencyPlanOptions()
      } else {
        this.activeShow = '2'
        this.$root.eventBus.$emit('linkmeeting', '预案模板')
      }
    },
    superNameOptions() {
      let url = '/apis/huawei/querySites?currentPage=1&numberPerPage=1000'
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.superNameOption = data.list
        this.allForm.count = data.list.length
        this.superNameOptionAll = JSON.parse(JSON.stringify(this.superNameOption))
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    getRowKeys(row) {
      return row.id
    },
    batchDelete() {
      if (this.multipleSelection.length === 0) {
        return
      }
      let selectList = this.multipleSelection.map((item) => {
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
    handleSelect() {
      this.superNameOption = this.superNameOptionAll
      this.addDialogRight = true
    },
    delClick(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/apis/huaweiGroup/deleteGroup?ids=' + row.id
          Axios.post(url).then((res) => {
            if (res.data && res.data.code === 200) {
              this.currentPage = 1
              this.getEmergencyPlanOptions()
              this.$message.success('删除成功！')
            } else {
              this.$message.error(res.data.message)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    getEmergencyPlanOptions() {
      let url = '/apis/huaweiGroup/queryGroupList?type=' + this.value
      if (this.search) {
        url += '&name=' + this.search
      }
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.tableData = data || []
        this.emergencyPlanOptions = data || []
        this.tableData.forEach(item => {
          let arr = []
          if (item.uriList && item.uriList.length > 0) {
            arr = item.uriList.map(r => r.name)
          }
          item.uriObj = arr.length > 0 ? arr.join(',') : ''
        })
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
        this.form.uri = ''
        this.allForm.chooseList = []
      } else {
        this.form.emergencyPlanId = row.parentId === '-1' ? '' : row.parentId
        this.form.name = row.name
        this.form.id = row.id
        this.form.uri = row.uris
        this.allForm.chooseList = row.uris.split(',')
      }
      this.dialogVisible = true
    },
    onSubmit(form) {
      if (!this.form.name) {
        this.$message.warning('请填写名称！')
        return
      }
      if (!this.form.uri) {
        this.$message.warning('请选择会场！')
        return
      }
      if (this.showIndex && this.showIndex === '2') {
        let sid = this.form.emergencyPlanId
        if (sid === this.form.id) {
          this.form.emergencyPlanId = '-1'
        }
      }
      let url = this.showIndex && this.showIndex === '2' ? '/apis/huaweiGroup/updateGroupInfo' : '/apis/huaweiGroup/addGroup'
      let unit = {
        uris: this.form.uri,
        name: this.form.name,
        type: this.value,
        parentId: Array.isArray(this.form.emergencyPlanId) ? this.form.emergencyPlanId.pop() : (this.form.emergencyPlanId ? this.form.emergencyPlanId : '-1')
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
.videoMeeting {
  position: relative;
  width: 100%;
  padding: 76px 62px 80px;
  .el-form-item:nth-of-type(2) {
    .el-input .el-input__inner {
      height: 80px !important;
    }
    .el-tag--small {
      height: 48px;
      line-height: 46px;
    }
    .el-tag {
      font-size: 30px;
    }
    .el-tag .el-icon-close {
      font-size: 32px;
      height: 32px;
      width: 32px;
      line-height: 32px;
    }
  }
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
    .el-table__body-wrapper {
        height: 994px !important;
    }
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
    }

    .list {
      line-height: 80px;
      font-size: 40px;
      float: left;
    }
  }
      .addDialog {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background: rgba(0, 0, 0, 0.5);
    z-index: 9999;
    .el-checkbox .el-checkbox__inner {
      border: 1px solid #DCDFE6;
    }

    .add-dialog-content {
      width: 2174px;
      height: 1965px;
      background: white;
      border-radius: 10px;
      margin: 238px auto 0;
      position: relative;

      .add-dialog-title {
        width: 100%;
        height: 203px;
        border-bottom: 4px solid #d9e1e5;
        display: inline-flex;
        justify-content: center;
        align-items: center;

        .title-content {
          width: 320px;
          height: 100%;
          font-size: 48px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: rgba(51, 51, 51, 1);
          line-height: 203px;

          i:nth-of-type(1) {
            font-size: 48px;
            margin: 0 25px 0 20px;
            background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }

          i:nth-of-type(2) {
            font-size: 48px;
            margin: 0 25px 0 20px;
            background: linear-gradient(-90deg, rgba(0, 150, 255, 1) 0%, rgba(0, 215, 254, 1) 100%);
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }
        }

        .close-icon {
          position: absolute;
          right: 73px;
          top: 78px;
          width: 48px;
          height: 48px;
          border-radius: 2px;

          i {
            font-size: 48px;
            color: rgba(182, 182, 182, 1);
          }
        }
      }
    }

    .transfer-content {
      width: 100%;
      height: 1510px;
      display: inline-flex;
      justify-content: center;
      // position: relative;
      .leftForm{
    width: 780px;
    border: 2px solid #EFF2F5;
    margin-left: 119px;
    margin-right: 119px;
    .topTitle{
      background: rgba(0, 147, 255, 0.1);
      border: 1px solid #0093FF;
      padding: 18px;
      font-size: 40px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #0093FF;
    }
    .mainContent{
        display: flex;
        flex-direction: column;
        overflow: scroll;
        height: 1242px;
        .leftItem{
          padding-left: 10px;
          width: 100%;
          height: 80px;
          margin-top: 10px;
          line-height: 42px;
        }
      }
  }
  .centerBtn{
    margin-top: 500px;
    .chooseBtn{
      display: inline-block;
      width: 90px;
      height: 85px;
      background: #0093FF;
      border-radius: 4px;
      font-size: 38px;
      padding: 20px;
      cursor: pointer;
      color: #fff;
    }
  }
  .el-transfer {
    font-size: 14px;
    margin-left: 109px;
  }
  .el-transfer-panel__item {
    height: 44px;
  }
  .el-transfer-panel__item .el-checkbox__input {
    top: 16px;
  }
  .el-transfer-panel {
    width: 380px;
    height: 903px;
    border: 1px solid #eff2f5;
    box-shadow: 0px 0px 6px #b7b9bb;
    max-height: 100%;
  }
  .el-transfer-panel .el-transfer-panel__header {
    height: 40px;
    line-height: 40px;
    background: rgba(0, 147, 255, 0.1);
    margin: 0;
    border: 1px solid #0093ff;
    padding-left: 15px;
    /* border-bottom: 1px solid #EBEEF5; */
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
  .el-transfer__buttons {
    padding: 0 80px;
  }
  .el-transfer-panel__item.el-checkbox .el-checkbox__label {
    line-height: 80px;
  }
  .el-checkbox {
    margin-right: 0px;
    font-size: 16px;
  }
  .el-transfer-panel__filter .el-input__inner {
    height: 40px;
    border-radius: 0px;
    font-size: 16px;
  }
  .el-transfer-panel__list.is-filterable {
    height: 780px;
  }
  .el-transfer-panel__item.el-checkbox:hover {
    background: #e4f4ff;
  }
      .input-with-search {
        width: 795px;
        height: 85px;
        border: 1px solid #ccc;
        position: absolute;
        top: 330px;
        left: 175px;
        background: #f5f7fa;
        z-index: 33333;

      .el-input__inner {
        border: none;
      }

      .el-input-group__append {
        padding: 0 12px;
        font-size: 20px;
        border: none;
      }
    }

      .btn {
        position: absolute;
        bottom: -35px;
        width: 100%;
        height: 280px;
        // background-color: #0ff;
        display: inline-flex;
        justify-content: center;
        border-top: 2px solid #d6d6d6;

        .item {
          width: 168px;
          height: 80px;
          margin-top: 80px;
          text-align: center;
        }

        .item_btn1 {
          font-size: 40px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: rgba(12, 153, 219, 1);
          line-height: 80px;
          cursor: pointer;
        }

        .item_btn2 {
          background: linear-gradient(176deg, rgba(0, 168, 255, 1) 0%, rgba(0, 98, 209, 1) 100%);
          border-radius: 8px;
          line-height: 80px;
          color: #fff;
          cursor: pointer;
        }
      }
    }

    .search-warp {
      margin-top: 20px;
      padding: 40px 60px;

      .el-input__inner {
        padding-left: 60px;
      }
    }

    .number-setting {
      height: 1200px;
      padding: 40px 60px;
      overflow-x: hidden;
      overflow-y: auto;

      .el-radio {
        width: 30%;
      }
    }

    .foot-btn {
      position: absolute;
      bottom: 0;
      width: 100%;
      height: 280px;
      // background-color: #0ff;
      display: inline-flex;
      justify-content: center;
      border-top: 2px solid #d6d6d6;

      .item {
        width: 168px;
        height: 80px;
        margin-top: 80px;
        text-align: center;
      }

      .item_btn1 {
        font-size: 40px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: rgba(12, 153, 219, 1);
        line-height: 80px;
        cursor: pointer;
      }

      .item_btn2 {
        background: linear-gradient(176deg, rgba(0, 168, 255, 1) 0%, rgba(0, 98, 209, 1) 100%);
        border-radius: 8px;
        line-height: 80px;
        color: #fff;
        cursor: pointer;
      }
    }
  }
}
</style>
