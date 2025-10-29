<template>
  <div v-if="dialogVisible">
    <el-dialog
      :title="isCode === 1 ? '查看已审核预案详情' : '查看未审核预案详情'"
      :visible.sync="dialogVisible"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form
          ref="formLabel"
          :rules="rules"
          :model="form"
          label-width="110px"
          disabled
          style="margin-right: 35px"
        >
          <el-form-item label="名称" prop="name">
            <el-input clearable v-model="form.name" placeholder="请输入预案名称"></el-input>
          </el-form-item>
          <el-form-item label="类型">
            <el-cascader
              style="width: 100%"
              filterable
              v-model="form.emergencyTypeId"
              :options="options"
              placeholder="请选择类型"
              clearable
            ></el-cascader>
          </el-form-item>
          <el-form-item label="分类" prop="kind">
            <el-select
              filterable
              clearable
              v-model="form.eventTypeId"
              placeholder="请选择预案分类"
              style="width: 100%"
            >
              <el-option
                v-for="item in eventTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="级别">
            <el-select
              filterable
              clearable
              v-model="form.levelId"
              placeholder="请选择级别"
              style="width: 100%"
            >
              <el-option
                v-for="item in manageLevelList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布人" prop="createName">
            <el-input clearable v-model="form.createName" placeholder="请输入发布人"></el-input>
          </el-form-item>
          <el-form-item label="发布时间" prop="releaseTime">
            <el-date-picker
              style="width: 100%"
              v-model="form.releaseTime"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="date"
              placeholder="请输入发布时间"
            ></el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Axios from '@/utils/request'
export default {
  name: 'SharePlanViewDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.form = Object.assign(this.form, JSON.parse(JSON.stringify(this.formInfo)))
      // console.log('this.form', this.form)
    }
  },
  data() {
    return {
      manageLevelList: [],
      options: [],
      form: {
        name: '',
        emergencyTypeId: '',
        levelId: '',
        eventTypeId: '',
        releaseTime: '',
        createName: ''
      }
    }
  },
  mounted() {
    this.getManageLevel()
    this.queryPlanType()
    this.queryEventType()
  },
  methods: {
    // 管理级别
    getManageLevel() {
      let url = '/binhuapis/menu/getStorageLevel'
      Axios.get(url).then((r) => {
        this.manageLevelList = r.data.data.data
      })
    },
    // 类型获取
    queryPlanType() {
      let url = '/binhuapis/get-type-tree'
      Axios.get(url).then((r) => {
        // console.log('类型信息：', r.data.data)
        this.options = r.data.data
      })
    },
    // 分类获取
    queryEventType() {
      let url = '/binhuapis/get-event-type'
      Axios.get(url).then((r) => {
        // console.log('分类获取', r.data.data)
        this.eventTypeList = r.data.data
      })
    },
    // 取 消
    handleClose() {
      this.$emit('update:dialogVisible', false)
    }
  }
}
</script>
