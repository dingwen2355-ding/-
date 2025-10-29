<template>
  <div class="myProcessViewDialog" v-if="dialogVisible">
    <el-dialog
      title="查看我的流程"
      :visible.sync="dialogVisible"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <el-form
        :inline="true"
        ref="formLabel"
        :model="form"
        style="margin-right: 35px"
        label-width="110px"
        :disabled="true"
      >
        <el-form-item label="标题">
          <el-input clearable v-model="form.mesName" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-cascader
            filterable
            style="width: 100%"
            v-model="form.typeId"
            :options="options"
            placeholder="请选择类型"
            clearable
          ></el-cascader>
        </el-form-item>
        <el-form-item label="发布方式">
          <el-select filterable clearable v-model="form.relId" placeholder="请选择发布方式" style="width: 100%">
            <el-option v-for="item in releaseWayList" :key="item.id" :label="item.relName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布对象">
          <!-- 融合通信==> 通讯录    站内信=> 系统用户 -->
          <el-button class="searchBtn" type="primary" v-show="form.relObjectName == '站内信'" @click="sysUserObj()"
            >系统用户</el-button
          >
          <el-button
            class="searchBtn"
            type="primary"
            v-show="form.relObjectName == '融合通讯'"
            @click="groupPeopleObj()"
            >通讯录</el-button
          >
        </el-form-item>
        <el-form-item label="发布人">
          <el-input clearable v-model="form.relPerson" placeholder="请输入发布人"></el-input>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-date-picker
            style="width: 100%"
            v-model="form.relTime"
            value-format="yyyy-MM-dd hh:mm:ss"
            type="date"
            placeholder="请输入发布时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="内容" class="whole-line">
          <el-input clearable type="textarea" :rows="8" v-model="form.remark" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-input clearable v-model="form.process" placeholder="请输入状态"></el-input>
        </el-form-item>
        <el-form-item label="处理时间">
          <el-date-picker
            style="width: 100%"
            v-model="form.processTime"
            value-format="yyyy-MM-dd hh:mm:ss"
            type="date"
            placeholder="请输入处理时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="处理意见" class="whole-line">
          <el-input
            clearable
            type="textarea"
            :rows="8"
            v-model="form.auditOpinion"
            placeholder="请输入处理意见"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
      </div>
    </el-dialog>
    <address-book-dialog :dialogVisible.sync="showAddressBook"></address-book-dialog>
    <system-user-dialog :dialogVisible.sync="showSystemUser"></system-user-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
import addressBookDialog from '../optionComponents/MyInfoOption/addressBookDialog.vue'
import systemUserDialog from '../optionComponents/MyInfoOption/SystemUserDialog.vue'
export default {
  name: 'myProcessViewDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  components: { addressBookDialog, systemUserDialog },
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
    },
    formInfo(v) {
      if (v) {
        this.form = Object.assign(this.form, JSON.parse(JSON.stringify(v)))
        // console.log('this.form', this.form)
      }
    }
  },
  data() {
    return {
      options: [], // 类型
      releaseWayList: [], // 发布方式
      form: {
        mesName: '',
        typeId: '',
        relId: '',
        relObjectName: '',
        relPerson: '',
        relTime: '',
        remark: '',
        process: '',
        processTime: '',
        auditOpinion: ''
      },
      showAddressBook: false,
      showSystemUser: false
    }
  },
  mounted() {
    this.getTypeList()
    this.getReleaseWayList()
  },
  methods: {
    // 获取类型
    getTypeList() {
      let url = '/gtw/EIMS-management/get-mes-ype-tree'
      Axios.get(url).then((res) => {
        this.options = res.data.data
      })
    },
    // 获取发布方式
    getReleaseWayList() {
      let url = '/gtw/EIMS-management/get-mes-rel'
      Axios.get(url).then((res) => {
        this.releaseWayList = res.data.data.list
      })
    },
    // 系统用户
    sysUserObj() {
      this.showSystemUser = true
    },
    // 通讯录
    groupPeopleObj() {
      this.showAddressBook = true
    },
    // 取 消
    handleClose() {
      this.$emit('update:dialogVisible', false)
    }
  }
}
</script>
<style lang="scss">
.myProcessViewDialog {
  .whole-line {
    width: 100%;
    .el-form-item__content {
      width: calc(100% - 150px);
    }
  }
}
</style>
