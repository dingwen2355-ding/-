<template>
  <div class="leftarea">
    <div class="header">
      <header class="title">
        <span>{{ titleText }}</span>
        <i class="el-icon-refresh sync-area" @click="syncAllBtn"></i>
      </header>
      <el-input prefix-icon="el-icon-search" v-model="filterText" placeholder="输入关键字过滤" style></el-input>
    </div>
    <div class="tree">
      <el-tree
        v-loading="treeLoading"
        :data="streetData"
        :props="drops"
        accordion
        node-key="id"
        :default-expanded-keys="checkedList"
        :filter-node-method="filterNode"
        ref="areaTree"
        highlight-current
        @node-click="handleNodeClick"
      >
        <div class="custom-tree-node" slot-scope="{ node }">
          <span>{{ node.label }}</span>
        </div>
      </el-tree>
    </div>
    <!--新增-->
    <el-dialog :modal-append-to-body="false" :visible.sync="isCityAddVisible" title="新增" width="750px">
      <el-row style="margin: 20px 0px; text-align: center">
        <el-radio-group v-model="addType">
          <el-radio :label="0">单个新增</el-radio>
          <el-radio :label="1">批量新增</el-radio>
        </el-radio-group>
      </el-row>
      <el-form
        v-if="!addType"
        :model="cityInfoForm"
        ref="addForm"
        :rules="areaRules"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="选择类型">
          <el-select disabled placeholder="行政区划" size="medium" v-model="cityInfoForm.precinctKind">
            <el-option
              v-for="item in precinctKinds"
              :key="item.precinctKind"
              :label="item.name"
              :value="item.precinctKind"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区域名称" prop="precinctName">
          <el-input auto-complete="off" placeholder="请输入区域名称" size="medium" v-model="cityInfoForm.precinctName"></el-input>
        </el-form-item>
        <el-row>
          <el-form-item label="道路名称" v-if="currentNode.kind === 101 || currentNode.kind === 3" prop="streetName">
            <el-input auto-complete="off" placeholder="请输入道路名称，如：XX路" size="medium" v-model="cityInfoForm.streetName"></el-input>
          </el-form-item>
          <el-form-item label="道路号" v-if="currentNode.kind === 101 || currentNode.kind === 3" prop="streetNo">
            <el-input auto-complete="off" placeholder="请输入道路号，如：XX号" size="medium" v-model="cityInfoForm.streetNo"></el-input>
          </el-form-item>
        </el-row>
        <el-form-item label="楼栋号" prop="buildingNo" v-if="currentNode.kind === 3">
          <el-input auto-complete="off" placeholder="请输入楼（栋）号" size="medium" v-model="cityInfoForm.buildingNo"></el-input>
        </el-form-item>
      </el-form>
      <el-upload
        v-if="addType"
        class="upload-demo upload"
        drag
        action="/auth/v2/precinct/upload"
        ref="upload"
        :limit="1"
        :file-list="fileList"
        :on-preview="handlePreview"
        :on-remove="handleRemove1"
        :on-success="uploadSuccess"
        :auto-upload="false"
        :before-upload="beforeUpload"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div slot="tip" class="el-upload__tip">
          只能上传excel文件，且不超过5MB
          <span style="color: darkred">且上传的文件内容必须符合模板规范</span>
          <a class="sel_btn" download="区域模板" href="../../static/template/区域模板.xlsx">
            点击下载模板
            <i class="el-icon-download"></i>
          </a>
        </div>
      </el-upload>
      <span class="dialog-footer" slot="footer">
        <el-button @click="isCityAddVisible = false" size="medium">取消</el-button>
        <el-button @click="submitAdd('addForm')" size="medium" type="primary">确定</el-button>
      </span>
    </el-dialog>

    <!--编辑-->
    <el-dialog :visible.sync="isCityEditVisible" title="编辑 " width="750px" :modal-append-to-body="false">
      <el-form :model="cityInfoForm" ref="editForm" :rules="areaRules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="选择类型">
          <el-select disabled placeholder="行政区划" size="medium" v-model="cityInfoForm.precinctKind">
            <el-option
              v-for="item in precinctKinds"
              :key="item.precinctKind"
              :label="item.name"
              :value="item.precinctKind"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区域ID" prop="precinctId">
          <el-input disabled auto-complete="off" placeholder="请输入ID号" size="medium" v-model="cityInfoForm.precinctId"></el-input>
        </el-form-item>
        <el-form-item label="区域名称" prop="precinctName">
          <el-input auto-complete="off" placeholder="请输入区域名称" size="medium" v-model="cityInfoForm.precinctName"></el-input>
        </el-form-item>
        <el-row>
          <el-form-item label="道路名称" v-if="(kind >= 3 && kind < 99) || kind === 101" prop="streetName">
            <el-input auto-complete="off" placeholder="请输入道路名称，如：XX路" size="medium" v-model="cityInfoForm.streetName"></el-input>
          </el-form-item>
          <el-form-item label="街道号" v-if="(kind >= 3 && kind < 99) || kind === 101" prop="streetNo">
            <el-input auto-complete="off" placeholder="请输入道路号，如：XX号" size="medium" v-model="cityInfoForm.streetNo"></el-input>
          </el-form-item>
        </el-row>
        <el-form-item label="楼栋号" prop="buildingNo" v-if="kind >= 4 && kind < 99">
          <el-input auto-complete="off" placeholder="请输入楼（栋）号" size="medium" v-model="cityInfoForm.buildingNo"></el-input>
        </el-form-item>
        <el-form-item label="单元号" prop="unitNo" v-if="kind >= 5 && kind < 99">
          <el-input auto-complete="off" placeholder="请输入单元号" size="medium" v-model="cityInfoForm.unitNo"></el-input>
        </el-form-item>
        <el-form-item label="楼层" prop="floorNo" v-if="kind >= 6 && kind < 99">
          <el-input auto-complete="off" placeholder="请输入楼层" size="medium" v-model="cityInfoForm.floorNo"></el-input>
        </el-form-item>
        <el-form-item label="户号（室）" prop="houseNo" v-if="kind >= 7 && kind < 99">
          <el-input auto-complete="off" placeholder="请输入户号" size="medium" v-model="cityInfoForm.houseNo"></el-input>
        </el-form-item>
      </el-form>
      <span class="dialog-footer" slot="footer">
        <el-button @click="isCityEditVisible = false">取消</el-button>
        <el-button @click="submitEdit('editForm')" type="primary">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Axios from '@/utils/request.js'
import qs from 'qs'

export default {
  name: 'AreaTree',
  data() {
    var validateNumber = (rule, value, callback) => {
      // eslint-disable-next-line
      let reg = /^[0-9]*$/g
      if (!reg.test(value)) {
        callback(new Error(''))
      } else {
        callback()
      }
    }
    var checkSpecificKey = (rule, value, callback) => {
      // eslint-disable-next-line
      let regEn = /[`~!@#$%^&*()_+<>?:"{},.\\/;'[\]]/im
      // eslint-disable-next-line
      let regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im

      // var specialKey = "`~!#@$^&*=|/?~！#￥……&*|【】‘；：”“'。，、？‘'";
      // for (let i = 0; i < value.length; i++) {
      if (regCn.test(value) || regEn.test(value)) {
        callback(new Error('包含特殊字符'))
      } else {
        callback()
      }
      // }
    }
    return {
      checkedList: [],
      files: '',
      fileName: '',
      fileList: [],
      upload_data: {},
      addType: 0,
      kind: '',
      precinctKinds: {},
      filterText: '',
      treeLoading: false,
      streetData: [{ id: '110', name: '未知', kind: 99, children: [] }],
      drops: { label: 'name', children: 'children' },
      currentNode: '',
      cityInfoForm: {
        regionName: configs.regionName,
        precinctName: '',
        streetName: '',
        streetNo: '',
        buildingNo: '',
        precinctId: '',
        upPrecinctId: '',
        precinctKind: ''
      },
      areaRules: {
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
      isCityAddVisible: false,
      isCityEditVisible: false
    }
  },
  props: {
    titleText: {
      type: String,
      required: true
    },
    optionsDisable: {
      type: Boolean,
      required: true,
      default: false
    }
  },
  watch: {
    filterText(val) {
      this.$refs.areaTree.filter(val)
    }
  },
  mounted() {
    this.queryStreet()
    this.queryPrecinctKind()
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    addStreet() {
      if (this.currentNode) {
        this.isCityAddVisible = true
        this.cityInfoForm = {
          regionName: configs.regionName,
          precinctName: '',
          streetName: '',
          streetNo: '',
          buildingNo: '',
          precinctId: '',
          upPrecinctId: this.currentNode.id
        }
        if (this.currentNode.kind === 101) {
          this.cityInfoForm.precinctKind = 3
        } else {
          this.cityInfoForm.precinctKind = this.currentNode.kind + 1
        }
      } else {
        this.$notify({
          title: '警告',
          message: '未选中任何节点',
          type: 'warning',
          duration: 1000
        })
      }
    },
    editStreet(row) {
      if (this.currentNode) {
        this.isCityEditVisible = true
        console.log('行', row)
        if (row.precinctKind) {
          this.kind = row.precinctKind
          this.cityInfoForm = {
            regionName: configs.regionName,
            precinctName: row.precinctName,
            streetName: row.streetName,
            streetNo: row.streetNo,
            buildingNo: row.buildingNo,
            unitNo: row.unitNo,
            floorNo: row.floorNo,
            houseNo: row.houseNo,
            precinctId: row.precinctId,
            upPrecinctId: row.upPrecinctId,
            precinctKind: row.precinctKind
          }
        } else {
          this.kind = this.currentNode.kind
          this.cityInfoForm = {
            regionName: configs.regionName,
            precinctName: this.currentNode.name,
            streetName: this.currentNode.streetName,
            streetNo: this.currentNode.streetNo,
            precinctId: this.currentNode.id,
            upPrecinctId: this.currentNode.parentId,
            precinctKind: this.currentNode.kind
          }
        }
      }
      console.log(this.cityInfoForm)
    },
    queryPrecinctKind() {
      this.treeLoading = true
      Axios.get('/auth/v2/precinct/queryPrecinctKind')
        .then((res) => {
          this.precinctKinds = res.data.data
          console.log('类型', res.data.data)
        })
        .catch((error) => {
          console.log(error)
          this.treeLoading = false
        })
    },
    queryStreet() {
      this.treeLoading = true
      console.log('配置文件', configs)
      Axios.get(
        '/auth/v2/device/queryPrecinctTree?precinctId=373' +
        '&userId=' +
        sessionStorage.getItem('userId')
      )
        .then((res) => {
          this.streetData[0].name = res.data.data[0].regionName
          this.streetData[0].children = res.data.data
          this.treeLoading = false
          this.handleNodeClick(res.data.data[0])
          this.checkedList.push(res.data.data[0].id)
        })
        .catch((error) => {
          console.log(error)
          this.treeLoading = false
        })
    },
    handleNodeClick(data) {
      this.currentNode = data
      this.$emit('node-click', data)
      // this.$parent.nodeChange11(data);
      console.log('点击了', data)
    },
    syncAllBtn: function () {
      this.queryStreet()
    },
    submitAdd(formName) {
      if (this.addType) {
        this.$refs.upload.submit()
        return
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 验证成功
          this.isCityAddVisible = false
          let parmas
          if (this.cityInfoForm.precinctKind === 100) {
            parmas = qs.stringify({
              regionName: configs.regionName,
              precinctName: this.cityInfoForm.precinctName,
              precinctId: this.cityInfoForm.precinctId,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
            })
          } else if (this.cityInfoForm.precinctKind === 3) {
            parmas = qs.stringify({
              regionName: configs.regionName,
              precinctName: this.cityInfoForm.precinctName,
              streetName: this.cityInfoForm.streetName,
              streetNo: this.cityInfoForm.streetNo,
              precinctId: this.cityInfoForm.precinctId,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
            })
          } else {
            parmas = qs.stringify({
              regionName: configs.regionName,
              precinctName: this.cityInfoForm.precinctName,
              streetName: this.cityInfoForm.streetName,
              streetNo: this.cityInfoForm.streetNo,
              buildingNo: this.cityInfoForm.buildingNo,
              precinctId: this.cityInfoForm.precinctId,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
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
                this.queryStreet()
                this.cityInfoForm = {}
                this.$parent.queryArea()
              }
            })
            .catch((error) => {
              console.log(error)
              this.treeLoading = false
            })
        }
      })
    },
    submitEdit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 验证成功
          this.isCityEditVisible = false
          let parmas
          if (this.cityInfoForm.precinctKind >= 99) {
            parmas = qs.stringify({
              precinctName: this.cityInfoForm.precinctName,
              precinctId: this.cityInfoForm.precinctId,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
            })
          }
          if (this.cityInfoForm.precinctKind === 3 || this.cityInfoForm.precinctKind === 101) {
            parmas = qs.stringify({
              precinctName: this.cityInfoForm.precinctName,
              streetName: this.cityInfoForm.streetName,
              streetNo: this.cityInfoForm.streetNo,
              precinctId: this.cityInfoForm.precinctId,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
            })
          }
          if (this.cityInfoForm.precinctKind === 4) {
            parmas = qs.stringify({
              precinctName: this.cityInfoForm.precinctName,
              streetName: this.cityInfoForm.streetName,
              streetNo: this.cityInfoForm.streetNo,
              precinctId: this.cityInfoForm.precinctId,
              buildingNo: this.cityInfoForm.buildingNo,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
            })
          }
          if (this.cityInfoForm.precinctKind === 5) {
            parmas = qs.stringify({
              precinctName: this.cityInfoForm.precinctName,
              streetName: this.cityInfoForm.streetName,
              streetNo: this.cityInfoForm.streetNo,
              buildingNo: this.cityInfoForm.buildingNo,
              unitNo: this.cityInfoForm.unitNo,
              precinctId: this.cityInfoForm.precinctId,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
            })
          }
          if (this.cityInfoForm.precinctKind === 6) {
            parmas = qs.stringify({
              precinctName: this.cityInfoForm.precinctName,
              streetName: this.cityInfoForm.streetName,
              streetNo: this.cityInfoForm.streetNo,
              buildingNo: this.cityInfoForm.buildingNo,
              unitNo: this.cityInfoForm.unitNo,
              floorNo: this.cityInfoForm.floorNo,
              precinctId: this.cityInfoForm.precinctId,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
            })
          }
          if (this.cityInfoForm.precinctKind === 7) {
            parmas = qs.stringify({
              precinctName: this.cityInfoForm.precinctName,
              streetName: this.cityInfoForm.streetName,
              streetNo: this.cityInfoForm.streetNo,
              buildingNo: this.cityInfoForm.buildingNo,
              unitNo: this.cityInfoForm.unitNo,
              floorNo: this.cityInfoForm.floorNo,
              houseNo: this.cityInfoForm.houseNo,
              precinctId: this.cityInfoForm.precinctId,
              upPrecinctId: this.cityInfoForm.upPrecinctId,
              precinctKind: this.cityInfoForm.precinctKind
            })
          }
          Axios.post('/auth/v2/precinct/updatePrecinct', parmas)
            .then((res) => {
              console.log('编辑结果', res)
              if (res.data.code === 0) {
                this.$notify({
                  title: '成功',
                  message: '编辑成功',
                  type: 'success',
                  duration: 1000
                })
                this.queryStreet()
                this.$parent.queryArea()
                this.cityInfoForm = {}
              }
            })
            .catch((error) => {
              console.log(error)
              this.treeLoading = false
            })
        }
      })
    },
    delStreet(row) {
      console.log('行', row)
      let parmas
      if (row.precinctId) {
        parmas = qs.stringify({
          precinctId: row.precinctId
        })
      } else {
        parmas = qs.stringify({
          precinctId: this.currentNode.id
        })
      }
      this.$confirm('此操作将永久删除该该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          Axios.post('/auth/v2/precinct/deletePrecinct', parmas)
            .then((res) => {
              console.log('删除结果', res)
              if (res.data.code === 0) {
                this.$notify({
                  title: '成功',
                  message: '删除成功',
                  type: 'success',
                  duration: 1000
                })
                this.queryStreet()
                this.$parent.queryArea()
                this.cityInfoForm = {}
              }
            })
            .catch((error) => {
              console.log(error)
            })
        })
        .catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消删除',
            duration: 1000
          })
        })
    },

    beforeUpload(file) {
      console.log('文件:', file.name)
      this.files = file
      const extension = file.name.split('.')[1] === 'xls'
      const extension2 = file.name.split('.')[1] === 'xlsx'
      const isLt2M = file.size / 1024 / 1024 < 5
      if (!extension && !extension2) {
        this.$notify({
          title: '警告',
          message: '上传模板只能是 xls、xlsx格式!',
          type: 'warning',
          duration: 1000
        })
        return false
      }
      if (!isLt2M) {
        this.$notify({
          title: '警告',
          message: '上传模板大小不能超过 5MB!',
          type: 'warning',
          duration: 1000
        })
        return false
      }
      this.fileName = file.name
    },
    uploadSuccess(response, file, fileList) {
      console.log(response)
      this.isCityAddVisible = false
      this.$notify({
        title: '成功',
        message: '上传成功',
        type: 'success',
        duration: 1000
      })
      this.queryStreet()
      this.$parent.queryArea()
    },
    handleRemove1(file, fileList) {
      console.log('移除文件：' + file.name, fileList)
      // 执行服务器删除操作
    },
    handlePreview(file) {
      console.log('点击预览：' + file.name)
      this.files = file
    }
  }
}
</script>

<style lang="scss" scoped>
.leftarea {
  float: left;
  width: 300px;
  height: 100%;
  background: #f8f9ff;
  padding: 27px 16px;
  .header {
    .title {
      font-weight: bold;
    }
    & .el-input {
      margin-bottom: 14px;
      margin-top: 20px;
    }

    .sync-area {
      float: right;
      color: #6187d8;
      cursor: pointer;
    }
  }
  .tree {
    margin-left: 5px;
    margin-right: 5px;
    margin-top: 10px;
    height: calc(100% - 100px);
    overflow: hidden;
    &:hover {
      overflow-y: scroll;
    }
    .custom-tree-node {
      font-size: 14px;
      display: flex;
      width: 100%;
      /*justify-content: flex-end;*/
    }
    .custom-tree-node .custom-tree-node-icon {
      width: 100%;
      text-align: end;
      padding-right: 40px;
    }
    .custom-tree-node .custom-tree-node-icon i {
      margin-right: 10px;
      color: #6187d8;
    }
  }
}
</style>
