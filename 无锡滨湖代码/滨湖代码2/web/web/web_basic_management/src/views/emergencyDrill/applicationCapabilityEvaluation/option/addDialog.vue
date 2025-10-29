<template>
  <div class="addDialog">
    <el-dialog
      :title="isCode == 0 ? '修改' : isCode === 1 ? '查看' : '新增'"
      :visible.sync="dialogVisible"
      width="1100px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form
          :inline="true"
          ref="formLabel"
          style="margin-top: -20px;"
          :rules="rules"
          :model="form"
          label-width="80px"
          :disabled="isCode == 1"
        >
          <el-form-item label="评估名称" prop="name">
            <el-input clearable v-model="form.name" placeholder="请输入评估名称" style="width: 960px"></el-input>
          </el-form-item>
          <el-form-item label="关联事件" prop="eventTitle">
            <el-select
              filterable
              v-model="form.eventTitle"
              placeholder="请选择关联事件"
              style="width: 960px"
              clearable
              :disabled="isCode !== 2"
            >
              <el-option v-for="item in typeList" :value="item.eventTitle" :label="item.eventTitle" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-table :data="tableData" class="elTable" height="380px">
          <el-table-column prop="projectName" label="评估项目"></el-table-column>
          <el-table-column label="评估内容">
            <template slot-scope="scope">
              <div v-html="scope.row.content"></div>
            </template>
          </el-table-column>
          <el-table-column label="评分标准">
            <template slot-scope="scope">
              <div v-html="scope.row.standard"></div>
            </template>
          </el-table-column>
          <el-table-column prop="num" label="得分" width="180">
            <div class="item" slot-scope="scope">
              <el-input
                v-if="scope.row.projectName === '应急准备能力'"
                :disabled="isCode == 1"
                type="number"
                min="0"
                class="item__input"
                v-model="form.prepareScore"
                placeholder="请输入得分"
              ></el-input>
              <el-input
                v-if="scope.row.projectName === '预测预警能力'"
                :disabled="isCode == 1"
                type="number"
                min="0"
                class="item__input"
                v-model="form.warningScore"
                placeholder="请输入得分"
              ></el-input>
              <el-input
                v-if="scope.row.projectName === '事件接报能力'"
                :disabled="isCode == 1"
                type="number"
                min="0"
                class="item__input"
                v-model="form.criminalScore"
                placeholder="请输入得分"
              ></el-input>
              <el-input
                v-if="scope.row.projectName === '事件响应能力'"
                :disabled="isCode == 1"
                type="number"
                min="0"
                class="item__input"
                v-model="form.responseScore"
                placeholder="请输入得分"
              ></el-input>
              <el-input
                v-if="scope.row.projectName === '应急指挥能力'"
                :disabled="isCode == 1"
                type="number"
                min="0"
                class="item__input"
                v-model="form.commandScore"
                placeholder="请输入得分"
              ></el-input>
              <el-input
                v-if="scope.row.projectName === '善后处置能力'"
                :disabled="isCode == 1"
                type="number"
                min="0"
                class="item__input"
                v-model="form.afterScore"
                placeholder="请输入得分"
              ></el-input>
            </div>
          </el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import moment from 'moment'
export default {
  name: 'addDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.getSiteList()
        this.getSiteList1()
        if (this.isCode !== 2) {
          this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
          console.log('新增编辑查看this.form', this.form)
        } else {
          this.form = {
            eventTitle: '',
            name: '',
            prepareScore: 0,
            warningScore: 0,
            criminalScore: 0, // 数量
            responseScore: 0, // 单位
            afterScore: 0,
            commandScore: 0
          }
        }
      }
    }
  },
  data() {
    // const phoneCheck = (rule, value, callback) => {
    //   let isPhone = /^[1][3-9]{1}[0-9]{9}$/ // 手机号码
    //   let isMob = /^([0-9]{3,4}-)?[0-9]{7,8}$/ // 座机格式
    //   if (!(value === null || value === undefined || value === '')) {
    //     if (isMob.test(value) || isPhone.test(value)) {
    //       callback()
    //     } else {
    //       callback(new Error('非有效电话号码！'))
    //     }
    //   } else {
    //     callback()
    //   }
    // }
    return {
      rules: {
        name: [{ required: true, message: '请输入评估名称', trigger: 'blur' }],
        eventTitle: [{ required: true, message: '请选择关联事件', trigger: 'blur' }],
        category: [{ required: true, message: '请选择类别', trigger: 'blur' }],
        amount: [{ required: true, message: '请输入数量', trigger: 'blur' }],
        unit: [{ required: true, message: '请输入单位', trigger: 'blur' }],
        // person: [{ required: true, message: '请输入管理人员', trigger: 'blur' }],
        // phone: [
        //   { required: true, message: '请输入管理员手机', trigger: 'blur' },
        //   { validator: phoneCheck, trigger: 'blur' }
        // ],
        siteName: [{ required: true, message: '请选择所属场所', trigger: 'blur' }]
        // address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
        // remark: [{ required: true, message: '请输入备注', trigger: 'blur' }]
      },
      loading: false,
      kindList: [{ id: '工具', name: '工具' }],
      typeList: [],
      siteList: [],
      tableData: [],
      form: {
        eventTitle: '',
        name: '',
        prepareScore: 0,
        warningScore: 0,
        criminalScore: 0, // 数量
        responseScore: 0, // 单位
        afterScore: 0,
        commandScore: 0
      },
      editProp: ['num'],
      timeout: null
    }
  },
  // mounted() {
  //   this.getSiteList()
  // },
  methods: {
    getSiteList() {
      let url = '/binhuapis/assessProject/query-condition'
      request.get(url).then((res) => {
        this.tableData = res.data.data.list
        // this.total = res.data.data.total
      })
    },
    getSiteList1() {
      let url = '/binhuapis/dapeng/event/query-condition?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      request.get(url).then((res) => {
        this.typeList = res.data.data.list
        // this.total = res.data.data.total
      })
    },
    objPlanSearch(queryString, cb) {
      // console.log('objPlanSearch', queryString, cb)
      let restaurants = this.siteList
      // console.log('restaurants', restaurants)
      let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter(queryString) {
      // console.log('createFilter', queryString)
      return (restaurant) => {
        // return restaurant.value.toUpperCase().match(queryString.toUpperCase())
        return restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1
      }
    },
    checkObjPlan(v) {
      // console.log('checkObjPlan', v)
      this.form.siteId = v.id
      this.form.siteName = v.value
      this.form.person = v.person
      this.form.phone = v.phone
      this.form.address = v.address
    },
    afterClearable(v) {
      // console.log('afterClearable', v)
      if (!v) {
        this.form.siteName = '' // 对象名称
      }
      this.form.siteId = ''
      this.form.siteName = ''
      this.form.person = ''
      this.form.phone = ''
      this.form.address = ''
    },
    handleClose() {
      if (this.isCode !== 1) {
        this.$confirm('尚未保存，是否在离开页面前保存？', '提示', {
          distinguishCancelAndClose: true,
          confirmButtonText: '提交保存',
          cancelButtonText: '关闭窗口',
          type: 'warning'
        })
          .then(() => {
            this.handleOk()
          })
          .catch((e) => {
            if (e === 'cancel') {
              this.reset()
              this.$emit('update:dialogVisible', false)
            }
          })
      } else {
        this.$emit('update:dialogVisible', false)
      }
    },
    reset() {
      this.loading = false
      this.form = {}
      // 清空表单校验，避免再次进来会出现上次校验的记录
      this.$refs.formLabel.clearValidate()
    },
    handleOk() {
      console.log(this.form)
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          // console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      this.typeList.forEach(item => {
        if (this.form.eventTitle === item.eventTitle) {
          this.form.eventId = item.id
        }
      })
      let newForm = {
        eventTitle: this.form.eventTitle,
        eventId: this.form.eventId,
        name: this.form.name,
        prepareScore: this.form.prepareScore,
        assessTime: moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),
        warningScore: this.form.warningScore,
        criminalScore: this.form.criminalScore, // 数量
        responseScore: this.form.responseScore, // 单位
        afterScore: this.form.afterScore,
        commandScore: this.form.commandScore
      }
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        newForm.id = this.formInfo.id
        url = '/binhuapis/assess/edit'
        // console.log('参数: ', newForm, 'url: ', url)
      } else {
        url = '/binhuapis/assess/add'
        // console.log('参数: ', newForm, 'url: ', url)
      }
      request.post(url, newForm).then((r) => {
        this.loading = false
        if (r.data.code === 200) {
          if (this.formInfo && this.formInfo.id) {
            this.$message.success('修改成功！')
          } else {
            this.$message.success('新增成功！')
          }
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(r.data.message)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.addDialog {
  .elTable .el-table__body-wrapper {
    height: 300px !important;
    overflow: auto !important;
  }
}
</style>
