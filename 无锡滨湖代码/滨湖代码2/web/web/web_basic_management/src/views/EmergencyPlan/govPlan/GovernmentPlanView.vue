<template>
  <div class="RecordDialog">
    <el-dialog
    title="查看预案信息"
    :visible.sync="dialogVisible"
    :modal-append-to-body="false"
    :model="false"
    class="recordDia"
    width="1000px"
    height="340px"
    :before-close="handleClose">
    <el-form :inline="true" ref='form' :model="form" class="drawer-form" label-width="130px">
      <el-form-item label="预案名称" prop="planName">
        <el-input clearable v-model="form.planName" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="预案类别" prop="planTypeName">
        <el-select v-model="form.planTypeName" :disabled="true">
          <el-option v-for="(v,i) in planType" :key="i" :label="v.label" :value="v.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="预案级别" prop="planLevelName">
        <el-select v-model="form.planLevelName" :disabled="true">
          <el-option v-for="(v,i) in planLevel" :key="i" :label="v.label" :value="v.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="适用领域" prop="planAreaName">
        <el-select v-model="form.planAreaName" :disabled="true">
          <el-option v-for="(v,i) in planArea" :key="i" :label="v.label" :value="v.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="预案事故类别" prop="alarmTypeName">
        <el-select v-model="form.alarmTypeName" :disabled="true">
          <el-option v-for="(v,i) in alarmType" :key="i" :label="v.label" :value="v.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="编制单位(部门)" prop="department">
        <el-input clearable v-model="form.department" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="编制人" prop="bianzhi">
        <el-input clearable v-model="form.bianzhi" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="发布日期" prop="showDate">
        <el-date-picker
          v-model="form.showDate"
          format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="选择日期时间"
          :disabled="true">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="发布文号" prop="showId">
        <el-input clearable v-model="form.showId" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="签发人" prop="showPeople">
        <el-input clearable v-model="form.showPeople" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="发布单位" prop="showDepart">
        <el-input clearable v-model="form.showDepart" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="是否备案" prop="record">
        <el-input clearable v-model="form.record" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="预备案时间" prop="recordTime">
        <el-date-picker
          v-model="form.recordTime"
          format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="选择日期时间"
          :disabled="true">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="预备案部门" prop="recordDepart">
        <el-input clearable v-model="form.recordDepart" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="预备案编号" prop="recordId">
        <el-input clearable v-model="form.recordId" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="other">
        <el-input clearable v-model="form.other" type="textarea" :disabled="true"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">返 回</el-button>
    <el-button type="primary" @click="handleOk">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'GovernmentPlanEdit',
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
        showDate: '',
        showId: '',
        showPeople: '',
        showDepart: '',
        record: '',
        recordTime: '',
        recordDepart: '',
        recordId: '',
        other: '',
        doc: '',
        id: ''
      }
    }
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.init()
    }
  },
  mounted() {},
  methods: {
    init() {
      this.getData()
    },
    handleOk() {
      this.handleClose()
    },
    handleClose(done) {
      this.reset()
      this.$emit("update:dialogVisible", false)
    },
    reset() {
      this.$refs.form.resetFields()
    },
    getData() {
      console.log(this.infomation)
      this.form.planName = this.infomation['name']
      this.form.planLevelName = this.infomation['level']
      this.form.planTypeName = this.infomation['type']
      this.form.planAreaName = this.infomation['applicable_field']
      this.form.department = this.infomation['preparation_unit']
      this.form.bianzhi = this.infomation['preparation_person']
      this.form.showDate = this.infomation['release_time']
      this.form.showId = this.infomation['release_number']
      this.form.showPeople = this.infomation['signer']
      this.form.showDepart = this.infomation['release_department']
      this.form.record = this.infomation['is_record']
      this.form.recordTime = this.infomation['preparation_record_time']
      this.form.recordDepart = this.infomation['preparation_record_department']
      this.form.recordId = this.infomation['preparation_record_number']
      this.form.other = this.infomation['remark']
      this.form.doc = this.infomation['appendix']
      this.form.id = this.infomation['id']
      this.form.alarmTypeName = this.infomation['plan_accident_type']
    },
    timeChange (t) {
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

.midText{
  text-align: right;
}
</style>
