<template>
  <el-dialog
    title="新增事件"
    :visible.sync="dialogVisible"
    width="1160px"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose">
    <el-form :inline="true" ref='form' :rules="rules" :model="form" class="EmergencyManagementAdd" label-width="120px">
      <el-form-item label="事件标题" prop="title">
        <el-input clearable v-model="form.title" placeholder="请输入事件标题"></el-input>
      </el-form-item>
<!--      <el-form-item label="一级事件类型" prop="type">
        <el-select v-model="form.type" placeholder="一级事件类型" clearable @change="firstItemChange">
          <el-option v-for="(item) in firstList" :value="item" :label="item" :key="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二级事件类型" prop="type2">
        <el-select v-model="form.type2" placeholder="二级事件类型" clearable>
          <el-option v-for="(item) in secondList" :value="item" :label="item" :key="item"></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item label="事件类别" prop="typeSelect">
        <el-cascader
          v-model="form.typeSelect"
          :options="typeEmergencyList"
          :props="{ checkStrictly: true }" clearable></el-cascader>
      </el-form-item>
      <el-form-item label="事发时间" prop="happenTime">
        <el-date-picker
          v-model="form.happenTime"
          type="datetime"
          value-format='yyyy-MM-dd HH:mm:ss'
          placeholder="选择事发时间"
          clearable>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="事件等级" prop="eventLevel">
        <el-select v-model="form.eventLevel" placeholder="事件等级" clearable>
          <el-option v-for="(v,i) in eventLevelDic" :key="i" :label="v" :value="v"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="事发地点" prop="address" class="whole-line">
        <el-input clearable v-model="form.address" placeholder="请输入事发地点"></el-input>
      </el-form-item>
      <el-form-item label="经度" prop="gpsx">
        <el-input clearable v-model="form.gpsx" placeholder="请点击此处选择经纬度位置">
          <el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="纬度" prop="gpsy">
        <el-input clearable v-model="form.gpsy" placeholder="请点击此处选择经纬度位置"
        ><el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button
        ></el-input>
      </el-form-item>
      <el-form-item label="伤亡情况" prop="casualtyDeath" class="whole-line">
        死亡：<el-input clearable v-model="form.casualtyDeath"  style="width: 100px"></el-input>人

        受伤：<el-input clearable v-model="form.casualtyInjured"  style="width: 100px"></el-input>人

        涉险：<el-input clearable v-model="form.casualtyDanger"  style="width: 100px"></el-input>人
      </el-form-item>
      <el-form-item label="事件描述" prop="details" class="whole-line">
        <el-input clearable v-model="form.details" type="textarea" :rows="2" placeholder="请输入事件描述"></el-input>
      </el-form-item>
      <el-form-item label="上报人" prop="reportPeople">
        <el-input clearable v-model="form.reportPeople" placeholder="请输入上报人"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="reportPeoplePhone">
        <el-input clearable v-model="form.reportPeoplePhone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="办理单位" prop="handleUnit">
        <el-select filterable v-model="form.handleUnit" placeholder="请选择办理单位" clearable>
          <el-option v-for="item in optionsResource" :value="item.precinctId" :label="item.precinctName" :key="item.precinctId"></el-option>
        </el-select>
        <!-- <el-cascader
          v-model="form.handleUnit" :options="optionsResource"
          :props="resourceCascaderProps" @change="handleChanges"
          placeholder="请选择办理单位" clearable filterable>
        </el-cascader> -->
      </el-form-item>
      <el-form-item label="附件" prop="attachment">
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
import axios from "axios";

export default {
  name: "EmergencyManagementAdd",
  components: {},
  data() {
    return {
      form: {
        typeSelect: [],
        type: "",
        type2: "",
        title: "",
        details: "",
        address: "",
        reportPeople: "",
        reportPeoplePhone: "",
        happenTime: "",
        eventLevel: '',
        casualtyDeath: '',
        casualtyInjured: '',
        casualtyDanger: '',
        gpsx: '',
        gpsy: '',
        handleUnit: ''
      },
      optionsResource: [],
      resourceCascaderProps: {
        checkStrictly: true,
        expandTrigger: 'hover',
        value: 'precinctId',
        label: 'precinctName'
      },
      rules: {
        title: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        typeSelect: [
          {required: true, message: '请选择后提交', trigger: 'blur'}
        ],
        happenTime: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        address: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        details: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        eventLevel: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        reportPeople: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        reportPeoplePhone: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        gpsy: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        gpsx: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        handleUnit: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ]
      },
      fileList: [],
      file: null,
      typeEmergencyList: [],
      eventLevelDic: []
    }
  },
  props: ['dialogVisible'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    }
  },
  mounted() {
    this.getTypeList()
    this.queryEventLevel()
    this.departList()
    this.$root.eventBus.$on('sendGps' + 'EssentialInformation', data => {
      console.log(data)
      data = data.split(',')
      this.form.gpsx = data[0]
      this.form.gpsy = data[1]
    })
  },
  methods: {
    getTypeList() {
      let url = '/gtw/EIMS-management/event/queryEventType'
      axios.get(url).then(r => {
        let data = r.data.data || []
        this.typeEmergencyList = this.changeTree(data)
      })
    },
    handleChanges (value) {
      this.$nextTick(() => {
        this.getInfoList()
      })
    },
    departList () {
      let self = this
      let url =
        localStorage.roleName === '超级管理员'
        ? `/community/precinctNew/select?type=2`
        : `/community/precinctNew/select?type=2&precinctId=${localStorage.streetId}`
      axios.get(url).then(function (response) {
        let data = response.data.data.data
        const obj = {}
        data.forEach((item) => {
          obj[item.precinctId] = item
        })
        const parentList = []
        data.forEach((item) => {
          const parent = obj[item.upPrecinctId]
          if (!parent) {
            parentList.push(item)
          }
        })
          // parentList.forEach((item) => {
          //   let arr = []
          //   if (item.children) {
          //     item.children.forEach((r) => {
          //       if (r.children) {
          //         r.children.forEach((s) => {
          //           arr.push(s)
          //         })
          //       }
          //     })
          //   }
          //   delete item.children
          //   item.children = arr
          // })
        self.optionsResource = parentList
      })
    },
    changeTree(data) {
      let arr = []
      if (typeof (data) === 'object') {
        for (let key in data) {
          let obj = {
            label: key,
            value: key,
            children: []
          }
          data[key].forEach(item => {
            let obj2 = {
              label: item,
              value: item
            }
            if (item) {
              obj.children.push(obj2)
            }
          })
          arr.push(obj)
        }
      }
      return arr
    },
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
    handlePlace(type) {
      console.log(this.form)
      this.$root.eventBus.$emit('getGps', {
        emitEventName: 'EssentialInformation',
        oriGpsInfo: this.form.gpsx + ',' + this.form.gpsy
      })
    },
    reset() {
      this.$refs.form.resetFields()
      this.form = {}
    },
    handleOk() {
      const _this = this
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          if (_this.form.typeSelect.length > 1) {
            _this.form.type2 = _this.form.typeSelect[1]
          }
          if (_this.form.typeSelect.length > 0) {
            _this.form.type = _this.form.typeSelect[0]
          }
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
          let url = '/gtw/EIMS-management/event/saveEventInfo'
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
.EmergencyManagementAdd.el-form--inline {
  min-height: 500px;
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
      width: 945px;
    }
  }

  .half-line {

    .el-form-item__content {
      width: 585px;
    }
  }

}
</style>
