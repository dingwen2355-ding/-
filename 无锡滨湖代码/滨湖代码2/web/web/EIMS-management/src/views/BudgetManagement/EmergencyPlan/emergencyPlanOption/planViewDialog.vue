<template>
  <div class="planViewDialog" v-if="dialogVisible">
    <el-dialog
      title="查看预案详情信息"
      :visible.sync="dialogVisible"
      width="1055px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="tab">
        <div class="item" v-for="item in tabList" :key="item.id">
          <div class="line" :class="{ isActived: item.id === isActived }"></div>
          <div class="top" :class="{ isActived: item.id === isActived }" @click="handleDetail(item)">
            {{ item.id + 1 }}
          </div>
          <div class="name">{{ item.name }}</div>
        </div>
      </div>
      <div style="height: 100%" v-if="isActived === 0">
        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupData" :key="item.id">
            <el-form :inline="true" :model="item" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="分组" placeholder="请输入分组">
                      <el-input
                        v-model="item.groupName"
                        :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <auto-editor
                        v-model="item.remark"
                        :disabled="true"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                      ></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div style="height: 100%" v-if="isActived === 1">
        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupWarningData" :key="item.id">
            <el-form :inline="true" :model="item" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="分组" placeholder="请输入分组">
                      <el-input
                        v-model="item.groupName"
                        :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <auto-editor
                        :disabled="true"
                        v-model="item.remark"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                      ></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div style="height: 100%" v-if="isActived === 2">
        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
          <div
            style="width: 100%; margin-top: 10px; margin-left: 10px"
            v-for="item in groupResponseData"
            :key="item.id"
          >
            <el-form :inline="true" :model="item" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="分组" placeholder="请输入分组">
                      <el-input
                        v-model="item.groupName"
                        :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <auto-editor
                        :disabled="true"
                        v-model="item.remark"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                      ></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div style="height: 100%" v-if="isActived === 3">
        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupSafeData" :key="item.id">
            <el-form :inline="true" :model="item" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="分组" placeholder="请输入分组">
                      <el-input
                        v-model="item.groupName"
                        :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <auto-editor
                        :disabled="true"
                        v-model="item.remark"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                      ></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div style="height: 100%" v-if="isActived === 4">
        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
          <div
            style="width: 100%; margin-top: 10px; margin-left: 10px"
            v-for="item in groupResponseStopData"
            :key="item.id"
          >
            <el-form :inline="true" :model="item" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="分组" placeholder="请输入分组">
                      <el-input
                        v-model="item.groupName"
                        :style="isCollapse ? 'width: 1065px' : 'width: 920px'"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <auto-editor
                        :disabled="true"
                        v-model="item.remark"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                      ></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div style="height: 70%" v-if="isActived === 5">
        <div class="info_body" style="margin-top: 20px; height: 100%; width: 100%">
          <el-form :model="planRewardsDutyForm" ref="form" :disabled="planId == ''">
            <el-form-item label="描述">
              <auto-editor
                :disabled="true"
                v-model="planRewardsDutyForm.rewardsDuty"
                :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
              ></auto-editor>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div v-if="isActived === 6">
        <div style="text-align: right; height: 47px; width: 100%">
          <div style="height: 565px; overflow: scroll">
            <el-table v-loading="aboutLoading" class="tableInfo" border :data="aboutDocData" style="width: 100%">
              <el-table-column prop="fileName" label="名称"></el-table-column>
              <el-table-column prop="remark" label="备注"></el-table-column>
              <el-table-column prop="createTime" label="上传时间"></el-table-column>
              <el-table-column fixed="right" label="操作" width="180">
                <template slot-scope="scope">
                  <el-button @click="aboutDocView(scope.row)" type="text" size="medium">详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
      </div>
      <add-about-dialog
        :dialogVisible.sync="showPlanAboutAdd"
        :formInfo="formPlanAboutInfo"
        :is-code="isPlanAboutCode"
        :plan-id="planAuditId"
      ></add-about-dialog>
    </el-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
import addAboutDialog from '../../myPlan/components/addAboutDialog.vue'
import AutoEditor from '../../../../components/AutoEditor/AutoEditor.vue'
export default {
  name: 'planViewDialog',
  props: ['dialogVisible', 'planId'],
  components: { addAboutDialog, AutoEditor },
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.planAuditId = this.planId
      this.queryPlanGroupList(this.planAuditId)
    }
  },
  data() {
    return {
      planAuditId: '',
      showPlanAboutAdd: false,
      formPlanAboutInfo: {},
      isPlanAboutCode: 1, // 1 查看
      tabList: [
        {
          id: 0,
          name: '前期准备'
        },
        {
          id: 1,
          name: '监测预警'
        },
        {
          id: 2,
          name: '应急响应'
        },
        {
          id: 3,
          name: '应急保障'
        },
        {
          id: 4,
          name: '应急响应终止'
        },
        {
          id: 5,
          name: '奖励与责任追究'
        },
        {
          id: 6,
          name: '相关资料'
        }
      ],
      isActived: 0,
      isCollapse: false,
      groupData: [],
      groupWarningData: [],
      groupResponseData: [],
      groupSafeData: [],
      groupResponseStopData: [],
      planRewardsDutyForm: { rewardsDuty: '' },
      aboutLoading: false,
      aboutDocData: []
    }
  },
  methods: {
    handleDetail(val) {
      // console.log('handleDetail', val)
      this.isActived = val.id
    },
    handleClose() {
      this.$emit('update:dialogVisible', false)
      this.isActived = 0
      this.$emit('cancel')
    },
    // 获取预案分组以及相关资料list
    queryPlanGroupList(id) {
      let url = '/gtw/EIMS-management/query-group-data?id=' + id
      Axios.get(url).then((res) => {
        // 前期准备
        this.groupData = res.data.data.planGroupBeans
        // 监测预警
        this.groupWarningData = res.data.data.planMonitorGroups
        // 应急响应
        this.groupResponseData = res.data.data.planResponseGroups
        // 应急保障
        this.groupSafeData = res.data.data.planSupportGroups
        // 应急响应终止
        this.groupResponseStopData = res.data.data.planStopGroups
        // 奖励与责任追究
        this.planRewardsDutyForm.rewardsDuty = res.data.data.rewardDuty
        // 相关资料
        this.aboutDocData = res.data.data.planDataBeans
      })
    },
    // 查看资料详情
    aboutDocView(row) {
      // console.log('查看资料详情: ', row)
      this.isPlanAboutCode = 1
      this.formPlanAboutInfo = row
      this.showPlanAboutAdd = true
    }
  }
}
</script>
<style lang="scss">
.planViewDialog {
  .el-dialog__body {
    height: 800px;
  }
  .tab {
    display: flex;
    .item {
      width: 133px;
      text-align: center;
      position: relative;
      padding-top: 40px;
      margin-bottom: 30px;
      .top {
        width: 34px;
        height: 34px;
        background: #839bb2;
        border-radius: 50%;
        margin: auto;
        font-size: 18px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #ffffff;
        text-align: center;
        line-height: 34px;
        margin-bottom: 12px;
        z-index: 9;
        position: absolute;
        top: 0;
        left: calc(50% - 17px);
        cursor: pointer;
      }
      .name {
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #414c5c;
      }
      .line {
        width: 133px;
        height: 2px;
        background: #eff2f5;
        position: absolute;
        top: 16px;
        left: 0;
      }
      .isActived {
        background: #0093ff;
      }
    }
  }
}
</style>
