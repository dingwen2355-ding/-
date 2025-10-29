<template>
  <el-dialog
    title="新增事件进展"
    :visible.sync="dialogVisible"
    width="720px"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose">
    <el-form :inline="true" ref='form' :rules="rules" :model="form" class="EventsProgressAdd" label-width="100px">
      <el-form-item label="进展时间" prop="progressTime">
        <el-date-picker
          v-model="form.progressTime"
          type="datetime"
          value-format='yyyy-MM-dd HH:mm:ss'
          placeholder="选择进展时间"
          clearable>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="进展类型" prop="progressType" v-show="false">
        <el-select v-model="form.progressType" placeholder="进展类型" clearable>
          <el-option v-for="(v,i) in typeList" :key="i" :label="v" :value="v"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="事件等级" prop="eventLevel">
        <el-select v-model="form.eventLevel" placeholder="事件等级" clearable>
          <el-option v-for="(v,i) in eventLevelDic" :key="i" :label="v" :value="v"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="伤亡情况" prop="details" class="whole-line">
        死亡：<el-input clearable v-model="form.casualtyDeath"  style="width: 100px"></el-input>人

        受伤：<el-input clearable v-model="form.casualtyInjured"  style="width: 100px"></el-input>人

        涉险：<el-input clearable v-model="form.casualtyDanger"  style="width: 100px"></el-input>人
      </el-form-item>
      <el-form-item label="进展描述" prop="details" class="whole-line">
        <el-input clearable v-model="form.details" type="textarea" :rows="2" placeholder="请输入进展描述"></el-input>
      </el-form-item>
      <el-form-item label="上报人" prop="reportPeople">
        <el-input clearable v-model="form.reportPeople" placeholder="请输入上报人"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="reportPeoplePhone">
        <el-input clearable v-model="form.reportPeoplePhone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="附件" prop="attachment" class="whole-line">
        <el-upload ref="upload" action="/" :multiple="false" :file-list="fileList" :show-file-list="true" :on-change="handleChange" :on-remove="handleRemove" :auto-upload="false" >
          <el-button slot="trigger" size="small" type="primary">上传附件</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
// 新增事件进展
import axios from "axios";

export default {
  name: "EventsProgressAdd",
  components: {},
  data() {
    return {
      form: {
        eventId: '',
        details: '',
        reportPeople: '',
        progressTime: '',
        progressType: '',
        reportPeoplePhone: '',
        eventLevel: '',
        casualtyDeath: '',
        casualtyInjured: '',
        casualtyDanger: ''
      },
      rules: {
        details: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        reportPeople: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        progressTime: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        progressType: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        reportPeoplePhone: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        eventLevel: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ]
      },
      fileList: [],
      file: null,
      typeList: ['续报', '终报'],
      eventLevelDic: []
    }
  },
  props: ['dialogVisible', 'rowInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    rowInfo(v) {
      if (v) this.form.eventId = v.id
    }
  },
  mounted() {
    if (this.rowInfo) this.form.eventId = this.rowInfo.id
    this.queryEventLevel()
  },
  methods: {
    queryEventLevel() {
      let url = '/gtw/EIMS-management/event/queryEventLevel'
      axios.get(url).then(res => {
        this.eventLevelDic = res.data.data
      }).catch(() => {
      })
    },
    handleChange(file, fileList) {
      this.file = file
      this.fileList = fileList
    },
    handleRemove(file, fileList) {
      this.file = file
      this.fileList = fileList
    },
    handleClose(done) {
      this.reset()
      this.$emit("update:dialogVisible", false)
    },
    reset() {
      this.$refs.form.resetFields()
      this.form = {
        eventId: '',
        details: '',
        reportPeople: '',
        progressTime: '',
        progressType: '',
        reportPeoplePhone: ''
      }
      this.$refs.upload.clearFiles()
      this.file = null
      this.fileList = []
    },
    handleOk() {
      this.form.progressType = '续报'
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          formData.append('data', JSON.stringify(this.form))
          if (this.file && this.fileList.length) {
            this.file = this.fileList[0]
            // this.$refs.upload.clearFiles()
            const types = this.file.name.split('.')[1]
            const fileType = ['xlsx', 'xlc', 'xlm', 'xls', 'xlt', 'xlw', 'csv', 'doc', 'docx'].some(item => item === types)
            if (!fileType) {
              this.$message('格式错误！请重新选择')
              return
            }
            formData.append('file', this.file.raw)
          }
          let url = '/gtw/EIMS-management/event/saveEventProgress'
          axios.post(url, formData).then(r => {
            if (r.data.code === 200 || r.data.code === 201) {
              this.$message.success('操作成功')
              this.$emit('success')
              this.handleClose()
            } else {
              this.$message.error(r.data.message)
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style lang="scss">
.EventsProgressAdd.el-form--inline {
  .el-select {
    display: block;
  }
  .el-form-item__content {
    width: 225px;
  }

  .el-date-editor {
    width: 225px;
  }

  .whole-line {
    width: 100%;

    .el-form-item__content {
      width: 564px;
    }
  }

}
</style>
