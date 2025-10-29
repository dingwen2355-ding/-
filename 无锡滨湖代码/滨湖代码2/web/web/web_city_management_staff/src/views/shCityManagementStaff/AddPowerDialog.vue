<template>
  <k-dialog :dialog-title="dialogTitle" :dialog-visible="dialogVisible" @closeDialog="closeDialog">
    <template>
      <div class="AddEventDialog">
        <div class="form-container">
          <el-form ref="form" :model="form" label-width="260px">
            <el-form-item label="力量部门">
              <el-select v-model="form.unitName" placeholder="请选择" filterable  allow-create @change="unitNameChange">
                <el-option v-for="item in unitOptions" :key="item.id" :label="item.unitName" :value="item.unitName">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="力量类型">
              <el-select v-model="form.resourceName" placeholder="请选择" filterable allow-create @change="resourceNameChange">
                <el-option v-for="item in resourceOptions" :key="item.resourceName" :label="item.resourceName" :value="item.resourceName">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="数量">
              <el-input v-model="form.count"></el-input>
            </el-form-item>
            <el-form-item label="上报时间">
              <el-date-picker type="datetime" placeholder="选择日期" v-model="form.arriveTime" style="width: 100%;"
                              value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="类型单位">
              <el-input v-model="form.type"></el-input>
            </el-form-item>
            <el-form-item label="已添加">
              <ul v-if="list.length!==0">
                <li class="list" v-for="(v,i) in list" :key="i">
                  <span>{{v.resourceName}}</span>
                  <i class="el-icon-delete" @click="deleteData(i)"></i>
                </li>
              </ul>
              <div v-else class="list">无</div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addData">添加</el-button>
              <el-button type="primary" @click="onSubmit">确定</el-button>
              <el-button @click="dialogVisible = false">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </template>
  </k-dialog>
</template>

<script>
import KDialog from '../../components/KDialog'
// import { addEventInfo } from '@/api/events-modules.js'
import axios from '@/utils/request'

export default {
  name: 'AddEventDialog',
  components: { KDialog },
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '新增到场力量',
      form: {
        'arriveTime': '',
        'count': 0,
        'resourceId': '',
        'resourceName': '',
        'unitId': '',
        'unitName': '',
        type: '辆'
      },
      resourceOptions: [],
      unitOptions: [],
      eventId: '',
      list: []
    }
  },
  mounted() {
    this.$root.eventBus.$on('showAddPowerDialog', (data) => {
      this.dialogVisible = true
      this.list = []
      this.eventId = data
      this.queryUnit()
      this.queryResource()
    })
  },
  methods: {
    unitNameChange() {
      this.form.unitId = +new Date()
      this.unitOptions.forEach(item => {
        if (item.unitName === this.form.unitName) {
          this.form.unitId = item.id
        }
      })
    },
    resourceNameChange() {
      this.form.resourceId = ''
      this.resourceOptions.forEach(item => {
        if (item.resourceName === this.form.resourceName) {
          this.form.resourceId = item.id
        }
      })
    },
    closeDialog: function (v) {
      this.dialogVisible = false
      this.$root.eventBus.$emit('closeAddEventDialog', v)
    },
    onSubmit() {
      if (!this.eventId) {
        this.$message.warning('请先选择事件')
        return
      }
      if (this.list.length === 0) {
        this.$message.warning('请先添加信息')
        return
      }
      this.list.forEach(v => {
        v.eventId = this.eventId
      })
      let obj = this.list
      this.$root.eventBus.$emit('addNewData', obj)
      this.closeDialog(true)
      // try {
      //   // await addEventInfo(obj)
      //   await axios.post('/apis/insertArrivedResource', obj).then(r => {
      //     if (r.data.code !== 200) {
      //       this.$message.error(r.data.message)
      //     } else {
      //       // console.log('submit!')
      //       this.$root.eventBus.$emit('refreshIframe', {})
      //       this.$root.eventBus.$emit('addNewData', {})
      //       this.closeDialog(true)
      //       this.$message({
      //         message: '新增成功！',
      //         type: 'success'
      //       })
      //       this.list = []
      //       this.form = {
      //         'arriveTime': '',
      //         'count': 0,
      //         'resourceId': '',
      //         'resourceName': '',
      //         'unitId': '',
      //         'unitName': '',
      //         type: '辆'
      //       }
      //     }
      //   })
      // } catch (error) {
      //   console.log(error)
      //   this.$message({
      //     message: '新增失败！',
      //     type: 'error'
      //   })
      // }
    },
    queryUnit() {
      const url = '/apis/queryUnitList'
      axios.get(url).then(r => {
        this.unitOptions = r.data.data
      })
    },
    queryResource() {
      const url = '/apis/queryResourceTypeList'
      axios.get(url).then(r => {
        this.resourceOptions = r.data.data
      })
    },
    addData() {
      for (let key in this.form) {
        if (!this.form[key] && key !== 'resourceId') {
          this.$message.warning('请把信息填写完整')
          return
        }
      }
      let tmp = { ...this.form }
      // if (isNaN(tmp.resourceId)) {
      //   tmp.resourceName = tmp.resourceId
      //   tmp.resourceId = ''
      // } else {
      //   tmp.resourceName = this.resourceOptions.find((v) => {
      //     return v.id === Number(this.form.resourceId)
      //   }).resourceName
      // }
      this.list.push(tmp)
    },
    deleteData(i) {
      this.list.splice(i, 1)
    }
  }
}
</script>

<style lang="scss" scoped>
  .AddEventDialog {
    position: relative;
    width: 100%;
    height: 100%;
    .el-button {
  font-size: 40px;
  line-height: 80px;
  padding: 0 40px;
}

    .form-container {
      padding: 80px;
    }

    .list {
      line-height: 80px;
      font-size: 40px;
      float: left;
    }
  }
</style>
