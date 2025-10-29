<template>
  <k-dialog :dialog-title="dialogTitle" :dialog-visible="dialogVisible" @closeDialog="closeDialog">
    <template>
      <div class="AddEventDialog">
        <div class="form-container">
          <el-form ref="form" :model="form" label-width="260px">
            <el-form-item label="事件类型">
              <el-select
                v-model="form.eventTypeName"
                placeholder="请选择"
                filterable
                allow-create
                @change="unitNameChange(form.eventTypeName)"
              >
                <el-option
                  v-for="item in unitOptions"
                  :key="item.id"
                  :label="item.typeName"
                  :value="item.typeName"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="事件标题">
              <el-input v-model="form.eventTitle"></el-input>
            </el-form-item>
            <el-form-item label="发生时间">
              <el-date-picker
                type="datetime"
                placeholder="选择日期"
                v-model="form.happenTime"
                style="width: 100%;"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="发生地址">
              <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item label="详细描述">
              <el-input v-model="form.desc"></el-input>
            </el-form-item>
            <el-form-item label="所属区划">
              <el-select
                v-model="form.area"
                placeholder="请选择"
                filterable
                allow-create
                @change="unitIdChange(form.area)"
              >
                <el-option
                  v-for="item in areaOptions"
                  :key="item.areaCode"
                  :label="item.name"
                  :value="item.name"
                ></el-option>
              </el-select>
            </el-form-item>
            <!-- <el-form-item label="所在经度">
              <el-input v-model="form.gpsx"></el-input>
            </el-form-item>
            <el-form-item label="所在纬度">
              <el-input v-model="form.gpsy"></el-input>
            </el-form-item> -->
            <el-form-item>
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
      dialogTitle: '新增事件名称',
      form: {
        eventType: '',
        eventTitle: '',
        happenTime: '',
        address: '',
        desc: '',
        areaCode: '',
        eventTypeName: '',
        gpsx: '',
        gpsy: ''
      },
      unitOptions: [],
      areaOptions: [],
      eventId: '',
      list: []
    }
  },
  mounted() {
    this.$root.eventBus.$on('showAddNameDialog', data => {
      this.dialogVisible = true
      this.list = []
      this.form = {
        eventType: '',
        eventTitle: '',
        happenTime: '',
        address: '',
        desc: '',
        areaCode: '',
        eventTypeName: '',
        gpsx: '',
        gpsy: ''
      }
      this.eventId = data
      this.queryUnit()
      this.queryArea()
    })
  },
  methods: {
    unitNameChange(e) {
      this.unitOptions.forEach(item => {
        if (e === item.typeName) {
          this.form.eventType = item.id
        }
      })
    },
    unitIdChange(e) {
      this.areaOptions.forEach(item => {
        if (e === item.name) {
          this.form.areaCode = item.areaCode
        }
      })
    },
    closeDialog: function(v) {
      this.dialogVisible = false
      this.$root.eventBus.$emit('closeAddEventDialog', v)
    },
    async onSubmit() {
      if (!this.form.eventType) {
        this.$message('请选择事件类型')
        return
      }
      if (!this.form.eventTitle) {
        this.$message('请输入事件标题')
        return
      }
      if (!this.form.happenTime) {
        this.$message('请选择发生时间')
        return
      }
      if (!this.form.address) {
        this.$message('请输入发生地址')
        return
      }
      if (!this.form.desc) {
        this.$message('请输入详细描述')
        return
      }
      if (!this.form.areaCode) {
        this.$message('请输入所属区域')
        return
      }
      // if (!this.form.gpsx) {
      //   this.$message('请输入所在经度')
      //   return
      // }
      // if (!this.form.gpsy) {
      //   this.$message('请输入所在纬度')
      //   return
      // }
      let obj = this.form
      try {
        // await addEventInfo(obj)
        await axios.post('/apis/manage/addEvent', obj).then(r => {
          if (r.data.code !== 200) {
            this.$message.error(r.data.message)
          } else {
            // console.log('submit!')
            this.closeDialog(true)
            this.$message({
              message: '新增成功！',
              type: 'success'
            })
            this.list = []
            this.form = {
              arriveTime: '',
              count: 0,
              resourceId: '',
              resourceName: '',
              unitId: '',
              unitName: '',
              type: '辆'
            }
          }
        })
      } catch (error) {
        console.log(error)
        this.$message({
          message: '新增失败！',
          type: 'error'
        })
      }
    },
    queryArea() {
      const url = '/apis/communication/areaInfo?level=1'
      axios.get(url).then(r => {
        this.areaOptions = r.data.data
      })
    },
    queryUnit() {
      const url = '/apis/manage/queryEventTypeList'
      axios.get(url).then(r => {
        this.unitOptions = r.data.data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.AddEventDialog {
  position: relative;
  width: 100%;
  height: 100%;

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
