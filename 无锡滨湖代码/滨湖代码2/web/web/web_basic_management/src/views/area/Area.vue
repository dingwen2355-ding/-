<!-- Created by Y on 2018-06-27 -->
<template>
  <div class="area" id="area">
    <AreaTree titleText="组织管理" :optionsDisable="false" ref="areatree" @node-click="handleNodeClick"></AreaTree>
    <section class="right-area">
      <!-- <header class="r-title">
        <el-input placeholder="搜索" size="medium" v-model="srhData"></el-input>
        <div class="srhIcon" @click="searchConditions">
          <label class="el-icon-search"></label>
        </div>
        <el-row style="margin-top: 5px;margin-bottom: 5px">
          <el-button icon="el-icon-circle-plus-outline"  size="medium" type="text"
                     @click="showAdd">新增</el-button>
          <el-button icon="el-icon-delete"  size="medium" type="text"
                     @click="delRows">删除</el-button>
        </el-row>
      </header>-->
      <div class="search-container">
        <el-input placeholder="搜索" size="medium" v-model="srhData" style="width: 280px; margin-right: 10px"></el-input>
        <el-button type="primary" @click="searchConditions">查询</el-button>
        <!-- <el-button type="primary" @click="showAdd" style="float: right">新增</el-button>
        <el-button type="primary" @click.prevent="delRows" style="float: right">删除</el-button>-->
      </div>
      <el-table
        :listLoading="tableLoading"
        :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        style="width: 100%"
        highlight-current-row
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="regionName" label="区名称" width="180"></el-table-column>
        <el-table-column prop="streetName" label="街道名称" width="180"></el-table-column>
        <el-table-column prop="precinctName" label="组织名称"></el-table-column>
        <el-table-column prop="precinctId" label="组织编码"></el-table-column>
        <!-- <el-table-column prop="buildingNo" label="楼号"></el-table-column> -->
        <!-- <el-table-column prop="floorNo" label="楼层"></el-table-column>
        <el-table-column prop="houseNo" label="户号"></el-table-column>-->
        <!-- <el-table-column label="操作" fixed="right" width="120">
          <template slot-scope="scope">
            <el-button type="text" size="medium" @click.stop="showEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="medium" @click.stop="delRow(scope.row)">删除</el-button>
          </template>
        </el-table-column>-->
      </el-table>
      <!-- 分页器 -->
      <div class="toolbar">
        <div class="total-container">
          <span>
            显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
            <span
              class="total-number"
            >{{ total }}</span> 条记录
          </span>
        </div>
        <div class="block">
          <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next, sizes, jumper"
            :total="total"
          ></el-pagination>
        </div>
      </div>
    </section>
    <!--新增-->
    <el-dialog
      :visible.sync="isAddVisible"
      title="新增 "
      width="750px"
      @close="resetForm"
      :modal="false"
      :modal-append-to-body="false"
    >
      <el-form :model="infoForm" ref="addForm" :rules="areaRules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="选择类型">
          <el-select disabled placeholder="行政区划" size="medium" v-model="infoForm.precinctKind">
            <el-option
              v-for="item in precinctKinds"
              :key="item.precinctKind"
              :label="item.name"
              :value="item.precinctKind"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区域名称" prop="precinctName">
          <el-input auto-complete="off" placeholder="请输入区域名称" size="medium" v-model="infoForm.precinctName"></el-input>
        </el-form-item>
        <el-row>
          <el-form-item
            label="道路名称"
            v-if="currentRow.precinctKind === 101 || currentRow.precinctKind >= 3"
            prop="streetName"
          >
            <el-input auto-complete="off" placeholder="请输入道路名称，如：XX路" size="medium" v-model="infoForm.streetName"></el-input>
          </el-form-item>
          <el-form-item
            label="街道号"
            v-if="currentRow.precinctKind === 101 || currentRow.precinctKind >= 3"
            prop="streetNo"
          >
            <el-input placeholder="请输入道路号，如：XX号" size="medium" v-model="infoForm.streetNo"></el-input>
          </el-form-item>
        </el-row>
        <el-form-item label="楼栋号" v-if="currentRow.precinctKind >= 3 && currentRow.precinctKind < 99" prop="buildingNo">
          <el-input auto-complete="off" placeholder="请输入楼（栋）号" size="medium" v-model="infoForm.buildingNo"></el-input>
        </el-form-item>
        <el-form-item label="单元号" v-if="currentRow.precinctKind >= 4 && currentRow.precinctKind < 99" prop="unitNo">
          <el-input auto-complete="off" placeholder="请输入单元号" size="medium" v-model="infoForm.unitNo"></el-input>
        </el-form-item>
        <el-form-item label="楼层" v-if="currentRow.precinctKind >= 5 && currentRow.precinctKind < 99" prop="floorNo">
          <el-input auto-complete="off" placeholder="请输入楼层" size="medium" v-model="infoForm.floorNo"></el-input>
        </el-form-item>
        <el-form-item label="户号（室）" v-if="currentRow.precinctKind >= 6 && currentRow.precinctKind < 99" prop="houseNo">
          <el-input auto-complete="off" placeholder="请输入户号" size="medium" v-model="infoForm.houseNo"></el-input>
        </el-form-item>
      </el-form>
      <span class="dialog-footer" slot="footer">
        <el-button @click="submitAdd('addForm')" type="primary">确定</el-button>
        <el-button @click="isAddVisible = false" plain>取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Axios from '@/utils/request.js'
import AreaTree from '../../components/AreaTree'
import qs from 'qs'

export default {
  name: 'Area',
  components: { AreaTree },
  data() {
    let validateNumber = (rule, value, callback) => {
      // eslint-disable-next-line
      let reg = /^[0-9]*$/g
      if (!reg.test(value)) {
        callback(new Error(''))
      } else {
        callback()
      }
    }
    let checkSpecificKey = (rule, value, callback) => {
      // eslint-disable-next-line
      let regEn = /[`~!@#$%^&*()_+<>?:"{},.\\/;'[\]]/im
      // eslint-disable-next-line
      let regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im
      if (regCn.test(value) || regEn.test(value)) {
        callback(new Error('包含特殊字符'))
      } else {
        callback()
      }
    }
    return {
      menus: configs.menus,
      menuIds: JSON.parse(sessionStorage.getItem('menuIds')),
      isAddVisible: false,
      infoForm: {
        regionName: '',
        precinctName: '',
        streetName: '',
        streetNo: '',
        buildingNo: '',
        unitNo: '',
        floorNo: '',
        houseNo: '',
        precinctId: '',
        precinctAddr: '',
        upPrecinctId: '',
        precinctKind: ''
      },
      currentRow: '',
      precinctKinds: {},
      multipleSelection: '',
      tableLoading: false,
      currentPage: 1,
      pageSize: 10,
      clearable: true,
      menuIsShow: false,
      dataIs: true,
      srhData: '',
      allData: [], // 当前节点所有数据
      storage: [], // 存储的所有数据
      isHouseAddVisible: false,
      isHouseEditVisible: false,
      areaRules: {
        regionName: [],
        precinctName: [{ required: true, message: '区域名称不能为空' }, { validator: checkSpecificKey }],
        streetName: [
          // { required: true, message: "街道名称不能为空" },
          { validator: checkSpecificKey }
        ],
        streetNo: [
          // { required: true, message: "街道号不能为空" },
          { validator: checkSpecificKey }
        ],
        buildingNo: [{ required: true, message: '街道名称不能为空' }, { validator: checkSpecificKey }],
        precinctId: [
          { required: true, message: '区域ID不能为空' },
          { validator: validateNumber, message: '区域ID必须为数字值' }
        ],
        unitNo: [{ required: true, message: '单元号不能为空' }, { validator: checkSpecificKey }],
        floorNo: [{ required: true, message: '楼层不能为空' }, { validator: checkSpecificKey }],
        houseNo: [{ required: true, message: '户号不能为空' }, { validator: checkSpecificKey }]
      },
      formData: {
        regionName: '东宏花苑',
        precinctName: '31231241212',
        streetName: 'lizhiwei',
        streetNo: '147224199507555555',
        buildingNo: '2',
        floorNo: '2',
        houseNo: '2',
        precinctId: '',
        precinctAddr: '',
        upPrecinctId: '',
        precinctKind: '',
        unitNo: ''
      },
      columnInfo: [
        {
          prop: 'regionName',
          label: '区名称',
          width: '180'
        },
        {
          prop: 'precinctName',
          label: '区域名称',
          width: '180'
        },
        {
          prop: 'streetName',
          label: '街道名称',
          width: ''
        },
        {
          prop: 'streetNo',
          label: '街道号',
          width: ''
        },
        {
          prop: 'buildingNo',
          label: '楼号',
          width: ''
        },
        {
          prop: 'floorNo',
          label: '楼层',
          width: ''
        },
        {
          prop: 'houseNo',
          label: '门牌号',
          width: ''
        }
      ],
      tableData: [],
      total: 0
    }
  },
  mounted() {
    // this.queryArea();
  },
  methods: {
    // 查询-过滤
    searchConditions() {
      this.allData = this.storage
      let search = this.srhData
      let searchData = ''
      if (search) {
        searchData = this.allData.filter(function (item) {
          return Object.keys(item).some(function (key) {
            if (
              key !== 'unitNo' &&
              key !== 'upPrecinctId' &&
              key !== 'regionXY' &&
              key !== 'precinctKind' &&
              key !== 'precinctId' &&
              key !== 'mapAddr' &&
              key !== 'gpsX' &&
              key !== 'gpsY' &&
              key !== 'precinctAddr'
            ) {
              return String(item[key]).toLowerCase().indexOf(search) > -1
            }
          })
        })
        console.log(searchData)
        this.total = searchData.length
        this.currentPage = 1 // 默认到首页
        this.allData = searchData
        this.tableData = searchData.slice(
          this.currentPage * this.pageSize - this.pageSize,
          this.currentPage * this.pageSize
        )
      } else {
        this.queryArea()
      }
    },
    showEdit(row) {
      // this.isHouseEditVisible = true;
      this.formData = row
      this.$refs.areatree.editStreet(row)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      console.log('选着了', val)
    },
    // 页面频繁执行编辑操作会出现分页错误，特注释此方法规避分页bug @current-change="currentChange"
    currentChange(row, column, event) {
      // if (row.precinctKind === 7) {
      //   // this.$message.warning('已经是最后一层节点了');
      // } else {
      this.precinctKinds = this.$refs.areatree.precinctKinds
      this.currentRow = row
      console.log(row)
      this.infoForm = {
        regionName: configs.regionName,
        precinctName: '',
        streetName: '',
        streetNo: '',
        buildingNo: '',
        precinctId: '',
        upPrecinctId: this.currentRow.precinctId
      }
      if (this.currentRow.precinctKind === 101) {
        this.infoForm.precinctKind = 3
      } else {
        this.infoForm.precinctKind = this.currentRow.precinctKind + 1
      }
      // }
    },
    nodeChange11(row) {
      console.log('row', row)
      // if (row.kind === 7) {
      //   this.$message.warning('已经是最后一层节点了');
      // } else {
      this.precinctKinds = this.$refs.areatree.precinctKinds
      this.currentRow = {
        precinctId: row.id,
        precinctKind: row.kind
      }
      this.infoForm = {
        regionName: configs.regionName,
        precinctName: '',
        streetName: '',
        streetNo: '',
        buildingNo: '',
        precinctId: '',
        upPrecinctId: this.currentRow.precinctId
      }
      if (this.currentRow.precinctKind === 101) {
        this.infoForm.precinctKind = 3
      } else {
        this.infoForm.precinctKind = this.currentRow.precinctKind + 1
      }
      // }
    },
    showAdd() {
      console.log('当前节点', this.currentRow)
      if (this.currentRow.precinctKind === 99 || this.currentRow === '' || this.currentRow.precinctKind === 7) {
        this.$notify({
          title: '警告',
          message: '当前节点无法添加',
          type: 'warning',
          duration: 1000
        })
      } else {
        this.isAddVisible = true
      }
    },
    resetForm() {
      if (this.$refs.addForm) {
        this.$refs.addForm.clearValidate()
        this.$refs.addForm.resetFields()
      }
    },
    handleNodeClick(data) {
      this.currentNode = data
      this.nodeChange11(data)
      this.queryArea()
    },
    queryArea() {
      this.currentPage = 1
      var self = this
      var url = '/auth/v2/precinct/queryPrecinct'
      if (this.currentNode) {
        console.log('节点信息', this.currentNode)
        url = url + '?upPrecinctId=' + this.currentNode.id
        // if (this.currentNode.kind > 10) {
        //   url = url + "&precinctKind=" + this.currentNode.kind;
        // }
        url = url + '&precinctKind=' + this.currentNode.kind
      }
      console.log('url地址：' + url)
      Axios.get(url)
        .then(function (response) {
          self.total = response.data.data.length
          self.tableData = response.data.data
          console.log(response.data.data)
          self.allData = self.tableData
          self.storage = self.tableData
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    submitForm(formName) {
      // this.isCityAddVisible = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    submitAdd(formName) {
      console.log(this.$store.getters.regionName)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 验证成功
          this.isAddVisible = false
          let parmas
          if (this.infoForm.precinctKind >= 99) {
            parmas = qs.stringify({
              regionName: this.$store.getters.regionName,
              precinctName: this.infoForm.precinctName,
              precinctId: this.infoForm.precinctId,
              upPrecinctId: this.infoForm.upPrecinctId,
              precinctKind: this.infoForm.precinctKind,
              streetName: this.infoForm.streetName,
              streetNo: this.infoForm.streetNo
            })
          }
          if (this.infoForm.precinctKind === 3) {
            parmas = qs.stringify({
              regionName: this.$store.getters.regionName,
              precinctName: this.infoForm.precinctName,
              streetName: this.infoForm.streetName,
              streetNo: this.infoForm.streetNo,
              precinctId: this.infoForm.precinctId,
              upPrecinctId: this.infoForm.upPrecinctId,
              precinctKind: this.infoForm.precinctKind
            })
          }
          if (this.infoForm.precinctKind === 4) {
            parmas = qs.stringify({
              regionName: this.$store.getters.regionName,
              precinctName: this.infoForm.precinctName,
              streetName: this.infoForm.streetName,
              streetNo: this.infoForm.streetNo,
              precinctId: this.infoForm.precinctId,
              buildingNo: this.infoForm.buildingNo,
              upPrecinctId: this.infoForm.upPrecinctId,
              precinctKind: this.infoForm.precinctKind
            })
          }
          if (this.infoForm.precinctKind === 5) {
            parmas = qs.stringify({
              regionName: this.$store.getters.regionName,
              precinctName: this.infoForm.precinctName,
              streetName: this.infoForm.streetName,
              streetNo: this.infoForm.streetNo,
              buildingNo: this.infoForm.buildingNo,
              unitNo: this.infoForm.unitNo,
              precinctId: this.infoForm.precinctId,
              upPrecinctId: this.infoForm.upPrecinctId,
              precinctKind: this.infoForm.precinctKind
            })
          }
          if (this.infoForm.precinctKind === 6) {
            parmas = qs.stringify({
              regionName: this.$store.getters.regionName,
              precinctName: this.infoForm.precinctName,
              streetName: this.infoForm.streetName,
              streetNo: this.infoForm.streetNo,
              buildingNo: this.infoForm.buildingNo,
              unitNo: this.infoForm.unitNo,
              floorNo: this.infoForm.floorNo,
              precinctId: this.infoForm.precinctId,
              upPrecinctId: this.infoForm.upPrecinctId,
              precinctKind: this.infoForm.precinctKind
            })
          }
          if (this.infoForm.precinctKind === 7) {
            parmas = qs.stringify({
              regionName: this.$store.getters.regionName,
              precinctName: this.infoForm.precinctName,
              streetName: this.infoForm.streetName,
              streetNo: this.infoForm.streetNo,
              buildingNo: this.infoForm.buildingNo,
              unitNo: this.infoForm.unitNo,
              floorNo: this.infoForm.floorNo,
              houseNo: this.infoForm.houseNo,
              precinctId: this.infoForm.precinctId,
              upPrecinctId: this.infoForm.upPrecinctId,
              precinctKind: this.infoForm.precinctKind
            })
          }
          Axios.post('/auth/v2/precinct/addPrecinct', parmas)
            .then((res) => {
              console.log('新增结果', res)
              if (res.data.code === 0) {
                this.$notify({
                  title: '成功',
                  message: '新增成功',
                  type: 'success',
                  duration: 1000
                })
                this.queryArea()
                this.$refs.areatree.queryStreet()
                this.resetForm()
              }
            })
            .catch((error) => {
              console.log(error)
            })
        }
      })
    },
    delRow(row) {
      this.$refs.areatree.delStreet(row)
    },
    delRows() {
      if (this.multipleSelection.length === 0) {
        this.$notify({
          title: '警告',
          message: '未选中数据',
          type: 'warning',
          duration: 1000
        })
      } else {
        var param = ''
        this.multipleSelection.forEach((e) => {
          if (param) {
            param = param + ','
          }
          param = param + e.precinctId
        })
        param = qs.stringify({
          precinctId: param
        })
        if (this.currentNode.kind === 99) {
          this.$notify({
            title: '警告',
            message: '该数据无法删除',
            type: 'warning',
            duration: 1000
          })
        } else {
          this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              Axios.post('/auth/v2/precinct/deletePrecincts', param)
                .then((res) => {
                  console.log(res.data)
                  if (res.data.code === 0) {
                    this.$notify({
                      title: '成功',
                      message: '删除成功',
                      type: 'success',
                      duration: 1000
                    })
                    // 重新渲染表哥和树
                    this.queryArea()
                    this.$refs.areatree.queryStreet()
                  }
                })
                .catch(function (error) {
                  console.log(error)
                })
            })
            .catch(() => { })
        }
      }
    },
    editPrecinct(formName) { }
  }
}
</script>
<style lang="scss">
.area {
  width: 100%;
  height: 100%;

  .right-area {
    float: left;
    width: calc(100% - 320px);
    margin-left: 20px;
    height: 100%;
    background: #f8f9ff;
    padding: 63px 20px 10px 20px;

    .r-title {
      font-size: 0px;

      .srhIcon {
        background: #6187d8;
        width: 2.5rem;
        display: inline-block;
        font-size: 0.7rem;
        line-height: 1.7rem;
        text-align: center;
        color: #fff;
        margin-right: 12px;
        cursor: pointer;

        .el-icon-search {
          cursor: pointer;
        }

        &:hover {
          background: #66b1ff;
          border-color: #66b1ff;
        }

        &:active {
          outline: 0;
          background: #6187d8;
        }
      }

      .el-input {
        width: 12.25rem;
      }
    }
  }
}
</style>
