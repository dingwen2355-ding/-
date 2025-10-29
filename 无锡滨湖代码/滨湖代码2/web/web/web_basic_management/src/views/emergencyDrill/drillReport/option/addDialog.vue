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
          :rules="rules"
          :model="form"
          label-width="110px"
          :disabled="isCode == 1"
        >
          <el-form-item label="演练名称" prop="drillName">
            <el-input clearable v-model="form.drillName" style="width: 900px" placeholder="请输入演练名称"></el-input>
          </el-form-item>

          <el-form-item label="地点" prop="address">
            <el-input clearable v-model="form.address" placeholder="请输入地点"></el-input>
          </el-form-item>
          <el-form-item label="时间" style="margin-bottom: 0" prop="drillTime">
            <el-date-picker
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="pickerOptionsStart"
              v-model="form.drillTime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="所属分类" prop="category">
            <el-select v-model="form.category" placeholder="请选择所属分类" clearable>
              <el-option v-for="item in categoryList" :value="item.dictName" :label="item.dictName" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="参演人数" prop="actNum">
            <el-input clearable type="number" min="0" v-model="form.actNum" placeholder="请输入参演人数"></el-input>
          </el-form-item>
          <!--          <el-form-item label="主办单位" prop="organizer">
            <el-input clearable v-model="form.organizer" placeholder="请输入单位"></el-input>
          </el-form-item>-->
          <el-form-item label="主办单位" prop="organizerId">
            <el-select filterable v-model="form.organizerId" placeholder="请选择主办单位" clearable>
              <el-option
                v-for="item in unitList"
                :value="item.precinctId"
                :label="item.precinctName"
                :key="item.precinctId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="演练形式" prop="drillForm">
            <el-input clearable v-model="form.drillForm" placeholder="请输入演练形式"></el-input>
          </el-form-item>
          <el-form-item label="演练类型" prop="drillTypeName">
            <el-select filterable v-model="form.drillTypeName" placeholder="请选择演练类型" clearable>
              <el-option v-for="item in kindList" :value="item.dictName" :label="item.dictName" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="出席领导" prop="leader">
            <el-input clearable v-model="form.leader" placeholder="请输入出席领导"></el-input>
          </el-form-item>
          <el-form-item label="主要参演单位" prop="actUnit">
            <el-input clearable v-model="form.actUnit" placeholder="请输入主要参演单位"></el-input>
          </el-form-item>
          <el-form-item label="出动队伍" prop="team">
            <el-input clearable v-model="form.team" placeholder="请输入出动队伍"></el-input>
          </el-form-item>
          <el-form-item label="主要演练科目" prop="drillSubject">
            <el-input clearable v-model="form.drillSubject" placeholder="请输入主要演练科目"></el-input>
          </el-form-item>
          <el-form-item label="出动装备" prop="equip">
            <el-input clearable v-model="form.equip" placeholder="请输入出动装备"></el-input>
          </el-form-item>
          <el-form-item label="参考依据" prop="basis">
            <el-input clearable v-model="form.basis" placeholder="请输入参考依据"></el-input>
          </el-form-item>
          <el-form-item label="存在问题" prop="problem">
            <el-input clearable v-model="form.problem" placeholder="请输入存在问题"></el-input>
          </el-form-item>
          <el-form-item label="演练成果" prop="results">
            <el-input clearable v-model="form.results" placeholder="请输入演练成果"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input
              clearable
              v-model="form.remark"
              type="textarea"
              :rows="6"
              style="width: 900px"
              placeholder="请输入备注"
            ></el-input>
          </el-form-item>
        </el-form>
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
export default {
  name: 'addDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.getSiteList()
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        console.log('新增编辑查看this.form', this.form)
      }
    }
  },
  data() {
    return {
      rules: {
        drillName: [{ required: true, message: '请输入所属分类', trigger: 'blur' }],
        actNum: [{ required: true, message: '请输入参演人数', trigger: 'blur' }],
        category: [{ required: true, message: '请输入所属分类', trigger: 'blur' }],
        organizer: [{ required: true, message: '请输入单位', trigger: 'blur' }],
        drillTime: [{ required: true, message: '选择时间', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地点', trigger: 'blur' }],
        drillForm: [{ required: true, message: '请输入演练形式', trigger: 'blur' }],
        drillTypeName: [{ required: true, message: '请选择演练类型', trigger: 'blur' }],
        leader: [{ required: true, message: '请输入出席领导', trigger: 'blur' }],
        actUnit: [{ required: true, message: '请输入主要参演单位', trigger: 'blur' }],
        team: [{ required: true, message: '请输入出动队伍', trigger: 'blur' }],
        drillSubject: [{ required: true, message: '请输入主要演练科目', trigger: 'blur' }],
        results: [{ required: true, message: '请输入演练成果', trigger: 'blur' }],
        equip: [{ required: true, message: '请输入出动装备', trigger: 'blur' }],
        basis: [{ required: true, message: '请输入参考依据', trigger: 'blur' }],
        problem: [{ required: true, message: '请输入存在问题', trigger: 'blur' }]
      },
      loading: false,
      kindList: [],
      typeList: [{ id: '影像资料', name: '影像资料' }],
      siteList: [],
      unitList: [],
      form: {
        drillName: '',
        drillTime: '',
        category: '',
        drillForm: '',
        drillTypeName: '',
        drillTypeId: '',
        leader: '',
        organizer: '',
        address: '',
        remark: '',
        actUnit: '',
        actNum: '',
        team: '',
        drillSubject: '',
        basis: '',
        problem: '',
        results: '',
        equip: '',
        organizerId: ''
      },
      timeout: null
    }
  },
  mounted() {
    //   this.getSiteList()
    this.getQueryUnit()
    this.getCategoryList()
  },
  methods: {
    getCategoryList() {
      let url = '/binhuapis/dict/dictTree?parentId=1'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.categoryList = data
        }
      })
    },
    getSiteList() {
      let url = '/binhuapis/dict/dictTree?parentId=7'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.kindList = data
        }
      })
    },
    getQueryUnit() {
      let url = '/binhuapis/drillReport/query-unit'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.unitList = data
        }
      })
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
      this.kindList.forEach(item => {
        if (this.form.drillTypeName === item.dictName) {
          this.form.drillTypeId = item.id
        }
      })
      let drillReport = {
        drillName: this.form.drillName,
        drillTime: this.form.drillTime,
        category: this.form.category,
        drillForm: this.form.drillForm,
        drillTypeName: this.form.drillTypeName,
        drillTypeId: this.form.drillTypeId,
        leader: this.form.leader,
        organizer: this.form.organizer,
        address: this.form.address,
        remark: this.form.remark,
        actUnit: this.form.actUnit,
        actNum: this.form.actNum,
        team: this.form.team,
        drillSubject: this.form.drillSubject,
        basis: this.form.basis,
        problem: this.form.problem,
        results: this.form.results,
        equip: this.form.equip,
        organizerId: this.form.organizerId
      }
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        drillReport.id = this.formInfo.id
        url = '/binhuapis/drillReport/edit'
        // console.log('参数: ', newForm, 'url: ', url)
      } else {
        url = '/binhuapis/drillReport/add'
        // console.log('参数: ', newForm, 'url: ', url)
      }
      request.post(url, drillReport).then((r) => {
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
// .addDialog{}
</style>
