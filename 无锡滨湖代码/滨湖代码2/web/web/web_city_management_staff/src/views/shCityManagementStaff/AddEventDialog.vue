<template>
  <k-dialog :dialog-title="dialogTitle" :dialog-visible="dialogVisible" @closeDialog="closeDialog">
    <template>
      <div class="AddEventDialog">
        <div class="form-container">
          <el-form ref="form" :model="form" label-width="260px">
            <el-form-item label="事件状态">
              <el-select v-model="form.eventStatus" placeholder="请选择" @change="changeeventStatus">
                <el-option v-for="item in eventStatusOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="上报单位">
              <el-input v-model="form.unitName"></el-input>
            </el-form-item>
            <el-form-item label="上报时间">
              <el-date-picker type="datetime" placeholder="选择日期" v-model="form.recordTime" style="width: 100%;"
                value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="进展描述">
              <el-input type="textarea" v-model="form.dealContent"></el-input>
            </el-form-item>
            <!-- <el-form-item label="照片">

            </el-form-item>
            <el-form-item label="视频">

            </el-form-item> -->
            <el-form-item label="状态">
              <el-select v-model="form.status" placeholder="请选择" @change="changeStatus">
                <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
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
import { addEventInfo } from '@/api/events-modules.js'
export default {
  name: 'AddEventDialog',
  components: { KDialog },
  data () {
    return {
      dialogVisible: false,
      dialogTitle: '新增事件动态',
      form: {
        status: '',
        eventStatus: '',
        unitName: '',
        recordTime: '',
        dealContent: ''
      },
      eventStatusOptions: [
        {
          value: '1',
          label: '事件上报'
        },
        {
          value: '2',
          label: '事件续报'
        },
        {
          value: '3',
          label: '事件终报'
        }
      ],
      statusOptions: [
        {
          value: '0',
          label: '显示'
        },
        {
          value: '1',
          label: '隐藏'
        }
      ],
      eventId: ''
    }
  },
  mounted () {
    this.$root.eventBus.$on('showAddEventDialog', (data) => {
      this.dialogVisible = true
      this.eventId = data
      // console.log(data)
    })
  },
  methods: {
    changeeventStatus (val) {
      console.log(this.form.eventStatus)
    },
    changeStatus (val) {
      console.log(this.form.status)
    },
    closeDialog: function (v) {
      this.dialogVisible = false
      this.$root.eventBus.$emit('closeAddEventDialog', v)
    },
    async onSubmit () {
      if (!this.eventId) return
      if (!this.form.unitName) {
        this.$message('请输入上报单位')
        return
      }
      if (!this.form.status) {
        this.$message('请选择状态')
        return
      }
      if (!this.form.recordTime) {
        this.$message('请选择上报时间')
        return
      }
      if (!this.form.eventStatus) {
        this.$message('请选择事件状态')
        return
      }
      if (!this.form.dealContent) {
        this.$message('请输入进展描述')
        return
      }
      let obj = {
        eventId: this.eventId,
        ...this.form
      }
      try {
        await addEventInfo(obj)
        // console.log('submit!')
        this.$root.eventBus.$emit('refreshIframe', {})
        this.$root.eventBus.$emit('addNewData', obj)
        this.closeDialog(true)
        this.$message({
          message: '新增成功！',
          type: 'success'
        })
        this.form = {
          status: '',
          eventStatus: '',
          unitName: '',
          recordTime: '',
          dealContent: ''
        }
      } catch (error) {
        console.log(error)
        this.$message({
          message: '新增成功！',
          type: 'error'
        })
      }
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
}
</style>
