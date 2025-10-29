<template>
  <div class="RecordDialog">
    <el-dialog
      title="新增预案信息"
      :visible.sync="dialogVisible"
      :modal-append-to-body="false"
      :model="false"
      class="recordDia"
      width="1000px"
      height="340px"
      :before-close="handleClose"
    >
      <el-form
        :inline="true"
        ref="form"
        :rules="rules"
        :model="form"
        class="drawer-form"
        label-width="130px"
      >
        <el-form-item label="预案名称" prop="planName">
          <el-input clearable v-model="form.planName" placeholder="预案名称"></el-input>
        </el-form-item>
        <el-form-item label="预案类别" prop="planTypeName">
          <el-select v-model="form.planTypeName" placeholder="预案类别">
            <el-option v-for="(v,i) in planType" :key="i" :label="v.label" :value="v.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预案级别" prop="planLevelName">
          <el-select v-model="form.planLevelName" placeholder="预案级别">
            <el-option v-for="(v,i) in planLevel" :key="i" :label="v.label" :value="v.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="适用领域" prop="planAreaName">
          <el-select v-model="form.planAreaName" placeholder="适用领域">
            <el-option v-for="(v,i) in planArea" :key="i" :label="v.label" :value="v.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预案事故类别" prop="alarmTypeName">
          <el-select v-model="form.alarmTypeName" placeholder="预案事故类别">
            <el-option v-for="(v,i) in alarmType" :key="i" :label="v.label" :value="v.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="编制单位(部门)" prop="department">
          <el-input clearable v-model="form.department" placeholder="编制单位(部门)"></el-input>
        </el-form-item>
        <el-form-item label="编制人" prop="bianzhi">
          <el-input clearable v-model="form.bianzhi" placeholder="编制人"></el-input>
        </el-form-item>
        <el-form-item label="发布日期" prop="showDate">
          <el-date-picker
            v-model="form.showDate"
            format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择日期时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="发布文号" prop="showId">
          <el-input clearable v-model="form.showId" placeholder="发布文号"></el-input>
        </el-form-item>
        <el-form-item label="签发人" prop="showPeople">
          <el-input clearable v-model="form.showPeople" placeholder="签发人"></el-input>
        </el-form-item>
        <el-form-item label="发布单位" prop="showDepart">
          <el-input clearable v-model="form.showDepart" placeholder="发布单位"></el-input>
        </el-form-item>
        <el-form-item label="是否备案" prop="record">
          <el-input clearable v-model="form.record" placeholder="是否备案"></el-input>
        </el-form-item>
        <el-form-item label="预备案时间" prop="recordTime">
          <el-date-picker
            v-model="form.recordTime"
            format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择日期时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="预备案部门" prop="recordDepart">
          <el-input clearable v-model="form.recordDepart" placeholder="预备案部门"></el-input>
        </el-form-item>
        <el-form-item label="预备案编号" prop="recordId">
          <el-input clearable v-model="form.recordId" placeholder="预备案编号"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="other">
          <el-input clearable v-model="form.other" placeholder="备注" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">返 回</el-button>
        <el-button type="primary" @click="handleOk('form')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'GovernmentPlanAdd',
  props: ['dialogVisible', 'infomation', 'planType', 'planLevel', 'planArea', 'alarmType'],
  data() {
    return {
      form: {
        planName: '',
        planLevelName: '',
        planTypeName: '',
        planAreaName: '',
        alarmTypeName: '',
        department: '',
        bianzhi: '',
        showDate: new Date(),
        showId: '',
        showPeople: '',
        showDepart: '',
        record: '',
        recordTime: new Date(),
        recordDepart: '',
        recordId: '',
        other: '',
        doc: ''
      },
      rules: {
        planName: [
          { required: true, message: '请输入预案名称', trigger: 'blur' }
        ],
        planLevelName: [
          { required: true, message: '请选择预案级别', trigger: 'change' }
        ],
        planTypeName: [
          { required: true, message: '请选择预案类别', trigger: 'change' }
        ],
        planAreaName: [
          { required: true, message: '请选择预案适用领域', trigger: 'change' }
        ],
        alarmTypeName: [
          { required: true, message: '请选择预案事故类别', trigger: 'change' }
        ],
        department: [
          { required: true, message: '请选择编制单位', trigger: 'blur' }
        ],
        bianzhi: [
          { required: true, message: '请填写编制人', trigger: 'blur' }
        ],
        showDate: [
          { required: true, message: '请选择发布日期', trigger: 'change' }
        ],
        showId: [
          { required: true, message: '请填写发布文号', trigger: 'blur' }
        ],
        showPeople: [
          { required: true, message: '请填写签发人', trigger: 'blur' }
        ],
        showDepart: [
          { required: true, message: '请填写发布单位', trigger: 'blur' }
        ],
        record: [
          { required: true, message: '是否备案', trigger: 'blur' }
        ],
        recordTime: [
          { required: true, message: '请选择预备案时间', trigger: 'change' }
        ],
        recordDepart: [
          { required: true, message: '请填写预备案部门', trigger: 'blur' }
        ],
        recordId: [
          { required: true, message: '请填写预备案编号', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.init()
    }
  },
  mounted() { },
  methods: {
    init() {
      this.getData()
    },
    handleOk(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.putData()
        } else {
          console.log('error submit!!')
          return false;
        }
      })
    },
    handleClose(done) {
      this.reset()
      this.$emit("update:dialogVisible", false)
    },
    reset() {
      this.$refs.form.resetFields()
    },
    putData() {
      let data = {
        name: this.form.planName,
        level: this.form.planLevelName,
        type: this.form.planTypeName,
        applicableField: this.form.planAreaName,
        preparationUnit: this.form.department,
        preparationPerson: this.form.bianzhi,
        releaseTime: this.timeChange(this.form.showDate),
        releaseNumber: this.form.showId,
        signer: this.form.showPeople,
        releaseDepartment: this.form.showDepart,
        isRecord: this.form.record,
        preparationRecordTime: this.timeChange(this.form.recordTime),
        preparationRecordDepartment: this.form.recordDepart,
        preparationRecordNumber: this.form.recordId,
        remark: this.form.other,
        appendix: this.form.doc,
        id: this.form.id,
        planAccidentType: this.form.alarmTypeName
      }
      let url = '/binhuapis/insertGPlan'
      axios.post(url, data).then(r => {
        this.$emit('success')
        this.$message(r.data['remark'])
        this.reset()
      })
    },
    timeChange(t) {
      let d = new Date(t)
      let theMonth = d.getMonth() + 1
      let theDate = d.getDate()
      let theHours = d.getHours()
      let theMinutes = d.getMinutes()
      let theSeconds = d.getSeconds()
      if (theMonth < 10) {
        theMonth = '0' + theMonth
      }
      if (theDate < 10) {
        theDate = '0' + theDate
      }
      if (theHours < 10) {
        theHours = '0' + theHours
      }
      if (theMinutes < 10) {
        theMinutes = '0' + theMinutes
      }
      if (theSeconds < 10) {
        theSeconds = '0' + theSeconds
      }
      let date = d.getFullYear() + '-' + theMonth + '-' + theDate + ' '
      let time = theHours + ':' + theMinutes + ':' + theSeconds
      return date + time
    }
  }
}
</script>

<style>
.midText {
  text-align: right;
}
</style>
