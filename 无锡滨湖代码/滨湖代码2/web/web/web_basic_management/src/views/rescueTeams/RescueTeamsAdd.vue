<template>
  <div v-if="dialogVisible">
    <el-dialog
      :title="isCode === 0 ? '修改队伍信息' : isCode === 1 ? '查看队伍信息详情' : '录入队伍信息'"
      :visible.sync="dialogVisible"
      width="1450px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item title="● 基本信息" name="1">
            <team-basic-information
              :form.sync="form"
              ref="teamBasicInformation"
              :isCode.sync="isCode"
            />
          </el-collapse-item>
        </el-collapse>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Axios from '@/utils/request'
import TeamBasicInformation from './addInfo/TeamBasicInformation.vue'

export default {
  name: 'RescueTeamsAdd',
  components: {
    TeamBasicInformation
  },
  data() {
    return {
      activeName: '1',
      loading: false,
      form: {
        id: '',
        city: 1, // 所属地市 默认为1 即淄博市
        teamName: '', // 队伍名称
        belongUnit: '', // 管理单位
        region: '', // 所属辖区
        beManagedType: '', // 管理类型
        apType: '', // 专兼类型
        storageLevel: '', // 管理级别
        manager: '', // 联系人
        managerPhone: '', // 联系人电话
        mainPeople: '', // 主要负责人
        dutyPhone: '', // 值班电话
        serviceArea: '', // 服务领域
        memberNumber: '', // 队员人数
        longitude: '', // 经度
        latitude: '', // 纬度
        industry: '', // 所属行业
        specialities: '', // 擅长领域
        helpType: '', // 救援类别
        address: '', // 详细地址
        orgSet: '', // 机构设置简介
        teamInfo: '', // 队伍信息简介
        aptitudeInfo: '', // 参与重大灾害救援及获得资质证书情况
        // 分队信息列表
        // teamGroupInfos: [],
        // 核心人员信息列表
        // resourceTeamMembers: [],
        // 年度训练情况列表
        // annualTrainingInfos: [],
        isUseAble: 'Y' // 是否可用 默认为'Y'即可用
      },
      isCode: 2
    }
  },
  props: ['dialogVisible', 'formInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    formInfo(v) {
      if (v) {
        this.form = Object.assign(this.form, JSON.parse(JSON.stringify(v)))
      }
    }
  },
  mounted() {
    this.$root.eventBus.$on('isCode', (data) => {
      this.isCode = data
    })
  },
  methods: {
    handleClose(done) {
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
      this.form = {
        id: '',
        city: 1, // 所属地市 默认为1 即淄博市
        teamName: '', // 队伍名称
        belongUnit: '', // 管理单位
        region: '', // 所属辖区
        beManagedType: '', // 管理类型
        apType: '', // 专兼类型
        storageLevel: '', // 管理级别
        manager: '', // 联系人
        managerPhone: '', // 联系人电话
        mainPeople: '', // 主要负责人
        dutyPhone: '', // 值班电话
        serviceArea: '', // 服务领域
        memberNumber: '', // 队员人数
        longitude: '', // 经度
        latitude: '', // 纬度
        industry: '', // 所属行业
        specialities: '', // 擅长领域
        helpType: '', // 救援类别
        address: '', // 详细地址
        orgSet: '', // 机构设置简介
        teamInfo: '', // 队伍信息简介
        aptitudeInfo: '', // 参与重大灾害救援及获得资质证书情况
        // 分队信息列表
        // teamGroupInfos: [],
        // 核心人员信息列表
        // resourceTeamMembers: [],
        // 年度训练情况列表
        // annualTrainingInfos: [],
        isUseAble: 'Y', // 是否可用 默认为'Y'即可用
        newFormD: {}
      }
      this.activeName = '1'
    },
    handleOk() {
      this.$refs.teamBasicInformation.$refs['form'].validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      this.newFormD = JSON.parse(JSON.stringify(this.form))
      console.log('this.newFormD', this.newFormD)
      if (this.newFormD.city) {
        this.newFormD.city = 1
      }
      if (this.newFormD.isUseAble) {
        this.newFormD.isUseAble = 'Y'
      }
      if (Array.isArray(this.newFormD.industry)) {
        this.newFormD.industry = this.newFormD.industry.join(',')
      }
      if (Array.isArray(this.newFormD.specialities)) {
        this.newFormD.specialities = this.newFormD.specialities.join(',')
      }
      if (Array.isArray(this.newFormD.helpType)) {
        this.newFormD.helpType = this.newFormD.helpType.join(',')
      }
      console.log('this.newFormD', this.newFormD)
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/teamInfo/updateTeam'
      } else {
        url = '/binhuapis/teamInfo/insertTeam'
      }
      Axios.post(url, this.newFormD).then((r) => {
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

<style lang="scss">
.addressBook-form.el-form--inline {
  .el-select {
    display: block;
  }
  .el-form-item__content {
    width: 300px;
  }

  .el-date-editor {
    width: 300px;
  }

  .whole-line {
    width: 100%;

    .el-form-item__content {
      width: 585px;
    }
  }
}
</style>
<style lang="scss" scoped>
.info_body {
  .information {
    width: 100%;
    height: 48px;
    line-height: 48px;
    font-size: 18px;
    color: rgb(114, 118, 123);
    font-weight: 500;
    border-bottom: 1px solid #ebeef5;
    cursor: pointer;
  }
  .informationOne {
    border-top: 1px solid #ebeef5;
  }
  .inform_base {
    width: 100%;
    padding: 10px;
    border-bottom: 1px solid #ebeef5;
  }
}
</style>
