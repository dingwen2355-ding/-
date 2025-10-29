<template>
  <section class="planDigitalProcess" style="display: flex; flex-direction: row; overflow: hidden">
    <div class="left">
      <el-row class="header" style="float: right; margin-bottom: 10px">
        <el-col :span="44">
          <el-button type="primary" @click="addPlan">新增</el-button>
        </el-col>
      </el-row>
      <div style="width: 100%; height: calc(100% - 120px); overflow: scroll">
        <el-table v-loading="loading" class="tableInfo" border :data="planTableData" style="width: 100%">
          <el-table-column prop="name" label="预案名称">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="medium">{{ scope.row.name }}</el-button>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="180">
            <template slot-scope="scope">
              <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
              <el-button @click="handleDelete(scope.row)" type="text" size="medium">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="left-page">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="page"
          :pager-count="1"
          :page-size="pageSize"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <div class="right">
      <!-- <emergency-plan-tags :planName="planName" showName="true" :showRightContent="showRightContent"> -->
      <div class="right_title">{{ planName }}</div>
      <div class="tab">
        <div class="item" v-for="item in tabList" :key="item.id">
          <div class="line" :class="{ isActived: item.id === isActived }"></div>
          <div class="top" :class="{ isActived: item.id === isActived }" @click="handleDetail(item)">{{ item.id + 1 }}</div>
          <div class="name">{{ item.name }}</div>
        </div>
      </div>
      <div class="tab_content">
        <!-- 前期准备 -->
        <div slot="prepare" style="height: 755px; width: 100%; overflow: hidden" v-if="isActived === 0">
          <div style="text-align: right; height: 47px; width: 100%">
            <el-button type="primary" @click="savePrepare()">保存</el-button>
          </div>
          <div class="info_body" style="height: calc(100% - 50px); width: 100%; overflow-x: hidden">
            <el-form :model="planNumPreForm" ref="form">
              <el-collapse v-model="activePreName" accordion>
                <el-collapse-item title="● 指挥体系" name="1">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入指挥体系信息"
                      v-model="planNumPreForm.commandSystem"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item :title="title" name="2">
                  <el-form-item style="width: 100%">
                    <div style="display: flex">
                      <div class="leftGroup boxShadow3">
                        <div style="text-align: right; height: 47px">
                          <el-button type="primary" @click="addGroupInfo()">新增</el-button>
                        </div>
                        <el-tree
                          ref="docTree"
                          :data="nodesData"
                          node-key="id"
                          default-expand-all
                          :props="defaultProps"
                          :expand-on-click-node="false"
                          highlight-current
                          @node-click="handleNodeClick"
                          style="text-align: left"
                        >
                          <span
                            class="custom-tree-node"
                            style="width: 100%"
                            slot-scope="{ node, data }"
                            @mouseenter="mouseenter(data)"
                            @mouseleave="mouseleave(data)"
                          >
                            <span>{{ node.label }}</span>
                            <el-button
                              v-show="data.id === showId"
                              size="mini"
                              type="text"
                              icon="el-icon-edit"
                              style="margin-left: 20px"
                              @click="() => editGroupInfo(node, data)"
                            ></el-button>
                            <el-button
                              v-show="data.id === showId"
                              size="mini"
                              type="text"
                              icon="el-icon-delete"
                              style="margin-left: 10px"
                              @click="() => deleGroupInfo(node, data)"
                            ></el-button>
                          </span>
                        </el-tree>
                      </div>
                      <div class="rightGroup" v-show="groupMemberShow">
                        <div style="text-align: right; height: 75px; width: 100%">
                          <el-button
                            type="primary"
                            @click="addGroupMember()"
                            style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
                          >新增</el-button>
                          <el-button
                            type="primary"
                            @click="deleteGroupMember()"
                            style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
                          >删除</el-button>
                        </div>
                        <div style="height: 300px; overflow: scroll; width: 100%">
                          <el-table
                            v-loading="loading"
                            class="tableInfo"
                            border
                            :data="groupMemberData"
                            @selection-change="handleMemberSelectionChange"
                            style="width: 100%"
                          >
                            <el-table-column type="selection" width="55"></el-table-column>
                            <el-table-column prop="personName" label="姓名"></el-table-column>
                            <el-table-column prop="post" label="职务"></el-table-column>
                            <el-table-column prop="phoneNumber" label="手机"></el-table-column>
                            <el-table-column prop="office" label="办公"></el-table-column>
                            <el-table-column prop="duty" label="职责"></el-table-column>
                            <el-table-column prop="contactPerson" label="联络员"></el-table-column>
                            <el-table-column prop="contactDuty" label="联络员职务"></el-table-column>
                            <el-table-column prop="contactOfficePhone" label="联络员办公电话"></el-table-column>
                            <el-table-column prop="contactCellPhone" label="联络员手机电话"></el-table-column>
                          </el-table>
                        </div>
                      </div>
                    </div>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 部门职责" name="3">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入部门职责信息"
                      v-model="planNumPreForm.departmentalResponsibilities"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 关联队伍" name="4">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入关联队伍信息"
                      v-model="planNumPreForm.associatedTeams"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 关联安置场所" name="5">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入关联安置场所信息"
                      v-model="planNumPreForm.associatedResettlementSites"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 关联物资" name="6">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入关联物资信息"
                      v-model="planNumPreForm.relatedMaterials"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 专家" name="7">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入专家信息"
                      v-model="planNumPreForm.expert"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 关联系统" name="8">
                  <el-form-item style="width: 100%">
                    <div style="text-align: right; height: 75px; width: 100%">
                      <el-button
                        type="primary"
                        @click="addReleSystem()"
                        style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
                      >新增</el-button>
                      <el-button
                        type="primary"
                        @click="deleteReleSystem()"
                        style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
                      >删除</el-button>
                    </div>
                    <div style="height: 370px; overflow: scroll; width: 100%">
                      <el-table
                        v-loading="loading"
                        class="tableInfo"
                        border
                        :data="planNumPreForm.planNumSystemBeans"
                        @selection-change="handleSelectionChange"
                        style="width: 100%"
                      >
                        <el-table-column type="selection" width="55"></el-table-column>
                        <el-table-column prop="systemUnit" label="管理单位"></el-table-column>
                        <el-table-column prop="systemName" label="系统名称"></el-table-column>
                        <el-table-column prop="systemEnvironment" label="网络环境"></el-table-column>
                        <el-table-column prop="systemUrl" label="网址"></el-table-column>
                      </el-table>
                    </div>
                  </el-form-item>
                </el-collapse-item>
              </el-collapse>
            </el-form>
          </div>
        </div>
        <!-- 监测预警 -->
        <div slot="monitoringWarning" style="height: 755px; width: 100%; overflow: hidden" v-if="isActived === 1">
          <div style="text-align: right; height: 47px; width: 100%">
            <el-button type="primary" @click="saveGroups()" style="margin-right: 10px">保存</el-button>
            <el-button
              type="primary"
              icon="el-icon-circle-plus"
              @click="addGroupItem()"
              style="margin-bottom: 10px"
            >添加分组</el-button>
          </div>
          <div style="height: calc(100% - 50px); width: 100%; overflow-x: hidden">
            <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupData" :key="item.id">
              <el-form :model="item" ref="form">
                <el-row class="header">
                  <el-row>
                    <el-col>
                      <el-form-item label="分组">
                        <el-input v-model="item.monitorGroup" placeholder="请输入分组" style="width: 810px"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col>
                      <el-form-item label="类型">
                        <el-cascader
                          filterable
                          style="width: 810px"
                          v-model="item.monitorTypeId"
                          :options="options"
                          :props="props"
                          placeholder="请选择类型"
                          clearable
                          @change="change"
                        ></el-cascader>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col>
                      <el-form-item label="描述">
                        <el-input
                          type="textarea"
                          placeholder="请输入描述信息"
                          :rows="6"
                          style="width: 810px"
                          v-model="item.remark"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-form-item style="float: right; margin-right: 20px">
                    <el-button type="danger" plain @click="delGroup(item.id, item)">删除</el-button>
                  </el-form-item>
                </el-row>
              </el-form>
            </div>
          </div>
        </div>
        <!-- 应急响应 -->
        <div slot="emergencyResponse" style="height: 755px; width: 100%; overflow: hidden" v-if="isActived === 2">
          <div style="text-align: right; height: 47px; width: 100%; margin-top: 20px">
            <el-button type="primary" @click="saveResponse()">保存</el-button>
            <el-button
              type="primary"
              icon="el-icon-circle-plus"
              @click="addResponseItem()"
              :disabled="responseAddBotton"
              style="margin-bottom: 10px"
            >添加应急响应</el-button>
          </div>
          <div style="height: calc(100% - 50px); width: 100%; overflow-x: hidden">
            <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in responseData" :key="item.id">
              <el-form :model="item" ref="form">
                <el-row class="header">
                  <el-form-item label="级别" prop="levelId">
                    <el-select
                      filterable
                      clearable
                      style="width: 811px; margin-left: 25px"
                      v-model="item.levelId"
                      placeholder="请选择级别"
                    >
                      <el-option
                        v-for="item in emergencyResponseList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="启动条件">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入启动条件"
                      v-model="item.startCondition"
                      style="width: 810px"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="响应行动">
                    <el-input
                      type="textarea"
                      placeholder="请输入响应行动"
                      :rows="9"
                      v-model="item.responseAction"
                      style="width: 810px"
                    ></el-input>
                  </el-form-item>
                </el-row>
              </el-form>
            </div>
          </div>
        </div>
        <!-- 应急保障 -->
        <div slot="emergencySafe" style="height: 755px; width: 100%; overflow: hidden" v-if="isActived === 3">
          <div style="text-align: right; height: 47px; width: 100%">
            <el-button type="primary" @click="saveSafe()">保存</el-button>
          </div>
          <div class="info_body" style="height: calc(100% - 50px); width: 100%; overflow-x: hidden">
            <el-form :model="safeForm" ref="form">
              <el-collapse v-model="activeSafeName" accordion>
                <el-collapse-item title="● 通信保障" name="1">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入通信保障信息"
                      v-model="safeForm.communication"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 应急队伍保障" name="2">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      placeholder="请输入应急队伍保障信息"
                      :rows="9"
                      v-model="safeForm.emergencyTeamSupport"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 应急物资保障" name="3">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      placeholder="请输入应急物资保障信息"
                      :rows="9"
                      v-model="safeForm.emergencySupplies"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 人员转移保障" name="4">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入人员转移保障信息"
                      v-model="safeForm.personTransfer"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 供电保障" name="5">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      placeholder="请输入供电保障信息"
                      :rows="9"
                      v-model="safeForm.powerSupply"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 能源保障" name="6">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      :rows="9"
                      placeholder="请输入能源保障信息"
                      v-model="safeForm.energySecurity"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 交通运输保障" name="7">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      placeholder="请输入交通运输保障信息"
                      :rows="9"
                      v-model="safeForm.transportation"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 医疗保障" name="8">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      placeholder="请输入医疗保障信息"
                      :rows="9"
                      v-model="safeForm.medicalInsurance"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 治安保障" name="9">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      placeholder="请输入治安保障信息"
                      :rows="9"
                      v-model="safeForm.security"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
                <el-collapse-item title="● 资金保障" name="10">
                  <el-form-item label="描述">
                    <el-input
                      type="textarea"
                      placeholder="请输入资金保障信息"
                      :rows="9"
                      v-model="safeForm.financialSecurity"
                      style="width: 840px"
                    ></el-input>
                  </el-form-item>
                </el-collapse-item>
              </el-collapse>
            </el-form>
          </div>
        </div>
        <!-- 应急响应终止 -->
        <div slot="stopEmergencyResponse" style="height: 100%" v-if="isActived === 4">
          <div style="text-align: right; height: 47px; width: 100%">
            <el-button type="primary" @click="saveStopResponse()">保存</el-button>
          </div>
          <div style="text-align: right; height: 47px; width: 100%; margin-top: 20px">
            <el-form :model="stopResponseForm" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="终止条件">
                      <el-input
                        type="textarea"
                        :rows="9"
                        placeholder="请输入终止条件"
                        v-model="stopResponseForm.terminationCondition"
                        style="width: 840px"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="终止程序">
                      <el-input
                        type="textarea"
                        :rows="9"
                        placeholder="请输入终止程序"
                        v-model="stopResponseForm.terminateProgram"
                        style="width: 840px"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-row>
            </el-form>
          </div>
        </div>
        <!-- 奖励与责任追究 -->
        <div slot="rewardsDuty" style="height: 100%" v-if="isActived === 5">
          <div style="text-align: right; height: 47px; width: 100%">
            <el-button type="primary" @click="saveRewardsDuty()">保存</el-button>
          </div>
          <div class="info_body" style="margin-top: 20px; height: 100%; width: 100%">
            <el-form :model="planRewardsDutyForm" ref="form">
              <el-form-item label="描述">
                <el-input
                  type="textarea"
                  :rows="25"
                  placeholder="请输入奖励与责任追究信息"
                  v-model="planRewardsDutyForm.rewardsDuty"
                  style="width: 840px"
                ></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <!-- 相关资料 -->
        <div slot="about" style="height: 755px; width: 100%; overflow: hidden" v-if="isActived === 6">
          <div style="text-align: right; height: 47px; width: 100%">
            <el-button
              type="primary"
              @click="addAboutDoc()"
              style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
            >新增</el-button>
            <div style="height: 690px; overflow: scroll">
              <el-table v-loading="loading" class="tableInfo" border :data="aboutDocData" style="width: 100%">
                <el-table-column prop="fileName" label="名称"></el-table-column>
                <el-table-column prop="remark" label="备注"></el-table-column>
                <el-table-column prop="createTime" label="上传时间"></el-table-column>
                <el-table-column fixed="right" label="操作" width="180">
                  <template slot-scope="scope">
                    <el-button @click="aboutDocView(scope.row)" type="text" size="medium">详情</el-button>
                    <el-button @click="deleteAboutDoc(scope.row)" type="text" size="medium">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </div>
      </div>

      <!-- </emergency-plan-tags> -->
    </div>
    <add-plan-dialog
      :dialogVisible.sync="showPlanAdd"
      @success="handlePlanSuccess"
      :formInfo="formPlanInfo"
      :is-code="isPlanCode"
    ></add-plan-dialog>
    <add-about-dialog
      :dialogVisible.sync="showPlanAboutAdd"
      @success="handlePlanAboutSuccess"
      :formInfo="formPlanAboutInfo"
      :is-code="isPlanAboutCode"
    ></add-about-dialog>
    <add-system-dialog
      :dialogVisible.sync="showSystemAdd"
      @success="handleSystemSuccess"
      :formInfo="formSystemInfo"
      :planId="planId"
      :planPreId="planPreId"
    ></add-system-dialog>
    <add-work-group-dialog
      :dialogVisible.sync="showPlanGroupAdd"
      @success="handlePlanGroupSuccess"
      :is-code="isPlanGroupCode"
      :formInfo="formPlanGroupForm"
      :planId="planId"
    ></add-work-group-dialog>
    <add-work-group-member-dialog
      :dialogVisible.sync="showGroupMemberAdd"
      @success="handlePlanGroupMemberSuccess"
      :formInfo="formPlanGroupMemberForm"
      :planId="planId"
      :planGroupId="planGroupId"
    ></add-work-group-member-dialog>
  </section>
</template>
<script>
import Axios from 'axios'
// import emergencyPlanTags from '../../components/emergencyPlanTags.vue'
import znvTime from '../../../utils/znvTime'
import AddAboutDialog from './addInfo/addAboutDialog.vue'
import AddPlanDialog from './addInfo/addPlanDialog.vue'
import AddSystemDialog from './SystemOption/addSystemDialog.vue'
import AddWorkGroupDialog from './workGroupOption/addWorkGroupDialog.vue'
import AddWorkGroupMemberDialog from './workGroupOption/addWorkGroupMemberDialog.vue'
export default {
  name: 'planDigitalProcess',
  components: {
    // emergencyPlanTags,
    AddPlanDialog,
    AddAboutDialog,
    AddSystemDialog,
    AddWorkGroupDialog,
    AddWorkGroupMemberDialog
  },
  data() {
    return {
      planId: '',
      planName: '',
      planTableData: [],
      page: 1,
      pageSize: 10,
      total: 0,
      loading: false,
      // 新增/编辑预案
      showPlanAdd: false,
      formPlanInfo: {
        id: '',
        name: '', // 名字
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        fileUrls: [] // 上传文档
      },
      isPlanCode: 0, // 0 编辑 2 新增
      showRightContent: false,
      // 前期准备
      planPreId: '',
      activePreName: '1',
      planNumPreForm: {
        id: '',
        planNumId: '',
        commandSystem: '',
        departmentalResponsibilities: '',
        associatedTeams: '',
        associatedResettlementSites: '',
        relatedMaterials: '',
        expert: '',
        groupNum: '',
        personNum: '',
        remark: '',
        planNumGroupBeans: [],
        planNumSystemBeans: []
      },
      // 工作组
      planGroupId: '',
      title: '',
      groupNum: 0,
      memberNum: 0,
      nodesData: [],
      defaultProps: {
        id: 'id',
        label: 'groupName'
      },
      showId: '',
      showPlanGroupAdd: false,
      formPlanGroupForm: {
        planNumId: '',
        groupName: ''
      },
      isPlanGroupCode: 0, // 0 新增 1修改
      // 工作组成员
      groupMemberShow: false,
      groupMemberData: [],
      groupMemberId: [],
      showGroupMemberAdd: false,
      formPlanGroupMemberForm: {
        planNumId: '',
        personName: '',
        post: '',
        phoneNumber: '',
        office: '',
        duty: '',
        contactCellPhone: '', // 联络员手机电话
        contactDuty: '', // 联络员职务
        contactOfficePhone: '', // 联络员办公电话
        contactPerson: '' // 联络员
      },
      // 关联系统
      systemId: [],
      showSystemAdd: false,
      formSystemInfo: {
        planNumId: '',
        systemName: '',
        systemUnit: '',
        sysyemEnvironment: '',
        sysyemUrl: ''
      },
      // 监测预警
      groupData: [], // 分组表
      options: [], // 类型 数据
      props: {},
      // 应急保障
      activeSafeName: '1',
      safeForm: {
        id: '',
        planNumId: '', // 预案id
        communication: '', // 通信保障
        emergencyTeamSupport: '', // 应急队伍保障
        emergencySupplies: '', // 应急物资保障
        personTransfer: '', // 人员转移保障
        powerSupply: '', // 供电保障
        energySecurity: '', // 能源保障
        transportation: '', // 交通运输保障
        medicalInsurance: '', // 医疗保障
        security: '', // 治安保障
        financialSecurity: '' // 资金保障
      },
      // 应急响应
      emergencyResponseList: [],
      responseData: [],
      responseAddBotton: false,
      responseForm: {
        id: '',
        levelId: '',
        startCondition: '',
        responseAction: ''
      },
      // 应急响应终止
      stopResponseForm: {
        id: '',
        terminationCondition: '',
        terminateProgram: ''
      },
      // 奖励与责任追究
      planRewardsDutyForm: { rewardsDuty: '' },
      // 相关资料
      aboutDocData: [],
      showPlanAboutAdd: false,
      formPlanAboutInfo: {
        id: '',
        planNumId: this.planId,
        fileName: '', // 名字
        remark: '', // 备注
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrl: '' // 文档路径
      },
      isPlanAboutCode: 1, // 1 查看 2新增
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
      isActived: 0
    }
  },
  mounted() {
    this.queryPlanTable()
  },
  methods: {
    handleDetail(val) {
      this.isActived = val.id
    },
    // 分页
    handleCurrentChange(val) {
      // console.log('当前页', val)
      this.page = val
      this.queryPlanTable()
    },
    handleSizeChange(val) {
      // console.log('一页展示', val, '条数据')
      this.pageSize = val
      this.queryPlanTable()
    },
    queryPlanTable() {
      this.loading = true
      let url = '/gtw/EIMS-management/query-plan-num'
      let params = {
        pageNum: this.page,
        pageSize: this.pageSize
      }
      // console.log('params', params)
      Axios.post(url, params).then((res) => {
        this.planTableData = res.data.data.list
        this.total = res.data.data.total
        this.loading = false
      })
    },
    // 重置预案信息
    resetPlan() {
      this.formPlanInfo = {
        id: '',
        name: '', // 名字
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        fileUrls: [] // 上传文档
      }
    },
    // 新增
    addPlan() {
      this.isPlanCode = 2
      this.resetPlan()
      this.showPlanAdd = true
    },
    // 新增预案/编辑预案成功后操作
    handlePlanSuccess() {
      this.page = 1
      this.queryPlanTable()
      this.resetAll()
    },
    // 点击表格某行后事件
    handleClick(row) {
      // console.log(row)
      this.showRightContent = true
      this.planId = row.id
      this.planName = row.name
      this.queryPlanType() // 获取类型数据
      this.getEmergencyResponse() // 获取应急响应级别
      this.groupMemberShow = false
      this.queryPlanGroupList(row.id) // 获取分组表
    },
    // 编辑
    handleEdit(row) {
      // console.log('编辑row: ', row)
      this.isPlanCode = 0
      this.showPlanAdd = true
      if (row.emergencyTypeId && !Array.isArray(row.emergencyTypeId)) {
        row.emergencyTypeId = row.emergencyTypeId.split(',')
      }
      this.formPlanInfo = row
    },
    // 删除
    handleDelete(row) {
      // console.log('删除预案row: ', row)
      let params = []
      params.push(row.id)
      this.$confirm('此操作将删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/gtw/EIMS-management/del-plan-num'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.page = 1
              this.queryPlanTable()
              this.showRightContent = false
              this.resetAll()
            } else {
              this.$message.error('删除失败')
            }
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
      })
    },
    resetAll() {
      this.showRightContent = false
      this.planName = ''
      // 前期准备
      this.planNumPreForm = {
        id: '',
        planNumId: '',
        commandSystem: '',
        departmentalResponsibilities: '',
        associatedTeams: '',
        associatedResettlementSites: '',
        relatedMaterials: '',
        expert: '',
        groupNum: '',
        personNum: '',
        remark: '',
        planNumGroupBeans: [],
        planNumSystemBeans: []
      }
      this.planPreId = []
      // 监测预警
      this.groupData = []
      // 应急响应
      this.responseData = []
      // 应急响应终止
      this.stopResponseForm = { id: '', terminationCondition: '', terminateProgram: '' }
      // 应急保障
      this.safeForm = {
        id: '',
        planNumId: '', // 预案id
        communication: '', // 通信保障
        emergencyTeamSupport: '', // 应急队伍保障
        emergencySupplies: '', // 应急物资保障
        personTransfer: '', // 人员转移保障
        powerSupply: '', // 供电保障
        energySecurity: '', // 能源保障
        transportation: '', // 交通运输保障
        medicalInsurance: '', // 医疗保障
        security: '', // 治安保障
        financialSecurity: '' // 资金保障
      }
      // 相关资料
      this.aboutDocData = []
    },
    // 获取预案右侧信息list
    queryPlanGroupList(id) {
      let url = '/gtw/EIMS-management/query-plan-num-data?id=' + id
      Axios.get(url).then((res) => {
        // 前期准备
        this.planNumPreForm = res.data.data.planNumPreBean || {
          id: '',
          planNumId: '',
          commandSystem: '',
          departmentalResponsibilities: '',
          associatedTeams: '',
          associatedResettlementSites: '',
          relatedMaterials: '',
          expert: '',
          groupNum: '',
          personNum: '',
          remark: '',
          planNumGroupBeans: [],
          planNumSystemBeans: []
        }
        if (res.data.data.planNumPreBean) {
          this.planPreId = res.data.data.planNumPreBean.id // 前期准备id
          if (res.data.data.planNumPreBean.groupNum !== null || res.data.data.planNumPreBean.personNum !== null) {
            this.groupNum = res.data.data.planNumPreBean.groupNum
            this.memberNum = res.data.data.planNumPreBean.personNum
          } else {
            this.memberNum = 0
            this.groupNum = 0
          }

          // console.log('this.groupNum ', this.groupNum, 'this.memberNum', this.memberNum)
          this.title = '● 工作组 ' + '(共 ' + this.groupNum + ' 组, ' + this.memberNum + ' 人)'
          // console.log('this.title', this.title)
          if (res.data.data.planNumPreBean.planNumGroupBeans) {
            this.nodesData = res.data.data.planNumPreBean.planNumGroupBeans // 工作组list获取
          }
        }
        // 监测预警
        for (let item of res.data.data.planNumMonitorBeans) {
          if (item.monitorTypeId && !Array.isArray(item.monitorTypeId)) {
            item.monitorTypeId = item.monitorTypeId.split(',')
          }
        }
        this.groupData = res.data.data.planNumMonitorBeans
        // 应急响应
        this.responseData = res.data.data.planNumEmergencyResponseBeans
        // 应急响应终止
        this.stopResponseForm = res.data.data.planNumEmergencyStopBean || {
          id: '',
          terminationCondition: '',
          terminateProgram: ''
        }
        // 应急保障
        this.safeForm = res.data.data.planNumEmergencySupportBean || {
          id: '',
          planNumId: '', // 预案id
          communication: '', // 通信保障
          emergencyTeamSupport: '', // 应急队伍保障
          emergencySupplies: '', // 应急物资保障
          personTransfer: '', // 人员转移保障
          powerSupply: '', // 供电保障
          energySecurity: '', // 能源保障
          transportation: '', // 交通运输保障
          medicalInsurance: '', // 医疗保障
          security: '', // 治安保障
          financialSecurity: '' // 资金保障
        }
        // 奖励与责任追究
        this.planRewardsDutyForm.rewardsDuty = res.data.data.rewardDuty
        // 相关资料
        this.aboutDocData = res.data.data.planNumDataBeans
      })
    },
    // 前期准备相关操作
    // 工作组相关操作
    mouseenter(data) {
      // console.log('鼠标移到此节点', data)
      this.showId = data.id
      // console.log('鼠标移到此节点', data)
    },
    mouseleave(data) {
      // console.log('鼠标移出此节点', data)
      this.showId = -1
      // console.log('鼠标移到此节点', data)
    },
    // 新增工作组
    addGroupInfo() {
      this.isPlanGroupCode = 0
      this.showPlanGroupAdd = true
      this.formPlanGroupForm = {
        planNumId: '',
        groupName: ''
      }
    },
    // 新增、编辑后操作
    handlePlanGroupSuccess(id) {
      this.queryPlanGroupList(id)
      this.groupMemberShow = false
    },
    // 修改工作组
    editGroupInfo(node, data) {
      // console.log('修改工作组', node, data)
      this.isPlanGroupCode = 1
      this.showPlanGroupAdd = true
      this.formPlanGroupForm = {
        id: data.id,
        planNumId: data.planNumId,
        groupName: data.groupName
      }
    },
    // 删除工作组
    deleGroupInfo(node, data) {
      // console.log('删除工作组', node, data)
      this.$confirm('此操作将删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/del-plan-group?id=' + data.id
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.queryPlanTable()
              this.queryPlanGroupList(data.planNumId)
              this.groupMemberData = []
              this.groupMemberShow = false
            } else {
              this.$message.error('删除失败')
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handleNodeClick(data) {
      // console.log('点击的节点：', data)
      this.planGroupId = data.id
      this.groupMemberShow = true
      this.queryGroupMemberData(data.id)
    },
    queryGroupMemberData(id) {
      let url = '/gtw/EIMS-management/get-plan-group-person?id=' + id
      Axios.get(url).then((res) => {
        this.groupMemberData = res.data.data
      })
    },
    // 新增工作组成员
    addGroupMember() {
      this.showGroupMemberAdd = true
      this.formPlanGroupMemberForm = {
        planNumId: '',
        personName: '',
        post: '',
        phoneNumber: '',
        office: '',
        duty: '',
        contactCellPhone: '', // 联络员手机电话
        contactDuty: '', // 联络员职务
        contactOfficePhone: '', // 联络员办公电话
        contactPerson: '' // 联络员
      }
    },
    // 新增后操作
    handlePlanGroupMemberSuccess(id) {
      this.queryGroupMemberData(id)
    },
    handleMemberSelectionChange(val) {
      // console.log('选择成员信息: ', val)
      for (let item of val) {
        // console.log('选择成员信息item:', item)
        this.groupMemberId.push(item.id)
      }
      this.groupMemberId = Array.from(new Set(this.groupMemberId))
      // console.log('选择成员信息id列表: ', this.groupMemberId)
    },
    // 删除工作组成员
    deleteGroupMember() {
      // console.log('删除工作组成员: ')
      let url = '/gtw/EIMS-management/del-plan-group-person'
      Axios.post(url, this.groupMemberId).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('删除成功')
          this.queryGroupMemberData(this.planGroupId)
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    // 前期准备 --> 关联系统相关操作
    handleSelectionChange(val) {
      // console.log('选择关联系统信息: ', val)
      for (let item of val) {
        // console.log('选择关联系统item:', item)
        this.systemId.push(item.id)
      }
      this.systemId = Array.from(new Set(this.systemId))
      // console.log('选择关联系统id列表: ', this.systemId)
    },
    // 新增关联系统
    addReleSystem() {
      this.showSystemAdd = true
      this.formSystemInfo = {
        planNumId: '',
        systemName: '',
        systemUnit: '',
        sysyemEnvironment: '',
        sysyemUrl: ''
      }
    },
    // 新增关联系统后操作
    handleSystemSuccess(id) {
      this.queryPlanGroupList(id)
    },
    // 删除关联系统
    deleteReleSystem() {
      // console.log('删除关联系统: ')
      let params = { id: this.planPreId, planNumId: this.planId, delPlanNumSystemIds: [] }
      params.delPlanNumSystemIds = this.systemId
      // console.log('删除关联系统参数:', params)
      let url = '/gtw/EIMS-management/update-plan-num-pre'
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('删除成功')
          this.queryPlanTable()
          this.queryPlanGroupList(this.planId)
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    // 保存前期准备
    savePrepare() {
      let params = {
        planNumId: this.planId, // 预案id
        commandSystem: this.planNumPreForm.commandSystem,
        departmentalResponsibilities: this.planNumPreForm.departmentalResponsibilities,
        associatedTeams: this.planNumPreForm.associatedTeams,
        associatedResettlementSites: this.planNumPreForm.associatedResettlementSites,
        relatedMaterials: this.planNumPreForm.relatedMaterials,
        expert: this.planNumPreForm.expert,
        // savePlanNumGroupBeans: this.planNumPreForm.planNumGroupBeans,
        planNumSystemBeans: this.planNumPreForm.planNumSystemBeans
      }
      // console.log('保存前期准备参数: ', params)
      let url = ''
      if (this.planNumPreForm && this.planNumPreForm.id) {
        url = '/gtw/EIMS-management/update-plan-num-pre'
        params.id = this.planNumPreForm.id
      } else {
        url = '/gtw/EIMS-management/add-plan-num-pre'
      }
      Axios.post(url, params).then((res) => {
        if (res.data.code == 200) {
          if (this.planNumPreForm && this.planNumPreForm.id) {
            this.$message.success('修改成功')
            this.queryPlanGroupList(this.planId)
          } else {
            this.$message.success('新增成功')
            this.queryPlanGroupList(this.planId)
          }
        } else {
          if (this.planNumPreForm && this.planNumPreForm.id) {
            this.$message.success('修改失败')
          } else {
            this.$message.success('新增失败')
          }
        }
      })
    },
    // 选中类型
    change(value) {
      // console.log(value)
    },
    // 类型获取
    queryPlanType() {
      let url = '/gtw/EIMS-management/get-monitor-type'
      Axios.get(url).then((r) => {
        // console.log('类型信息：', r.data.data)
        this.options = r.data.data
      })
    },
    // 保存分组
    saveGroups() {
      let params = { addPlanNumMonitorBeans: [], updatePlanNumMonitorBeans: [] }
      for (let item of this.groupData) {
        if (Array.isArray(item.monitorTypeId)) {
          item.monitorTypeId = item.monitorTypeId.join(',')
        }
        if (item.id == undefined) {
          // console.log('保存分组:', item)
          item.planNumId = this.planId
          params.addPlanNumMonitorBeans.push(item)
        } else {
          // console.log('保存分组:', item)
          item.planNumId = this.planId
          params.updatePlanNumMonitorBeans.push(item)
        }
      }
      // console.log('保存分组参数:', params)
      let url = '/gtw/EIMS-management/save-plan-num-monitor'
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('保存成功')
          this.page = 1
          this.queryPlanGroupList(this.planId)
        } else {
          this.$message.error('保存失败')
        }
      })
    },
    // 添加分组
    addGroupItem() {
      this.groupData.push({
        monitorGroup: '',
        monitorTypeId: '',
        remark: ''
      })
    },
    // 删除分组
    delGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (item.id != undefined) {
        let params = { delIds: [] }
        params.delIds.push(index.toString())
        // console.log('删除分组参数:', params)
        let url = '/gtw/EIMS-management/save-plan-num-monitor'
        Axios.post(url, params).then((res) => {
          if (res.data.code === 200) {
            this.$message.success('删除成功')
            this.queryPlanGroupList(this.planId)
          } else {
            this.$message.error('删除失败')
          }
        })
      } else {
        this.groupData.pop()
        // console.log('this.groupData', this.groupData)
      }
    },
    // 获取应急响应级别
    getEmergencyResponse() {
      let url = '/gtw/EIMS-management/get-emergency-response'
      Axios.get(url).then((r) => {
        // console.log('级别信息：', r.data.data)
        this.emergencyResponseList = r.data.data
      })
    },
    // 保存应急响应
    saveResponse() {
      for (let item of this.responseData) {
        let url = ''
        let params = {
          planNumId: this.planId,
          levelId: '',
          startCondition: '',
          responseAction: ''
        }
        if (item && item.id) {
          // console.log('保存应急响应item', item)
          url = '/gtw/EIMS-management/update-plan-num-emergency-response'
          params.id = item.id
          params.levelId = item.levelId
          params.startCondition = item.startCondition
          params.responseAction = item.responseAction
          // console.log('保存应急响应url', url)
          // console.log('保存应急响应params', params)
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code == 200) {
                this.$message.success('修改成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.success('修改失败')
              }
            })
            .catch(() => {
              this.$message.error('修改失败')
            })
        } else {
          // console.log('保存应急响应item', item)
          url = '/gtw/EIMS-management/add-plan-num-emergency-response'
          params.levelId = item.levelId
          params.startCondition = item.startCondition
          params.responseAction = item.responseAction
          // console.log('保存应急响应url', url)
          // console.log('保存应急响应params', params)
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code == 200) {
                this.$message.success('新增成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.success('新增失败')
              }
            })
            .catch(() => {
              this.$message.error('新增失败')
            })
        }
      }
    },
    // 新增应急响应
    addResponseItem() {
      if (this.responseData.length < 4) {
        this.responseAddBotton = false
        this.responseData.push({
          levelId: '',
          startCondition: '',
          responseAction: ''
        })
      } else {
        this.responseAddBotton = true
      }
    },
    // 保存应急保障
    saveSafe() {
      let params = {
        planNumId: this.planId, // 预案id
        communication: this.safeForm.communication, // 通信保障
        emergencyTeamSupport: this.safeForm.emergencyTeamSupport, // 应急队伍保障
        emergencySupplies: this.safeForm.emergencySupplies, // 应急物资保障
        personTransfer: this.safeForm.personTransfer, // 人员转移保障
        powerSupply: this.safeForm.powerSupply, // 供电保障
        energySecurity: this.safeForm.energySecurity, // 能源保障
        transportation: this.safeForm.transportation, // 交通运输保障
        medicalInsurance: this.safeForm.medicalInsurance, // 医疗保障
        security: this.safeForm.security, // 治安保障
        financialSecurity: this.safeForm.financialSecurity // 资金保障
      }
      let url = ''
      if (this.safeForm && this.safeForm.id) {
        url = '/gtw/EIMS-management/update-plan-num-emergency-support'
        params.id = this.safeForm.id
      } else {
        url = '/gtw/EIMS-management/add-plan-num-emergency-support'
      }
      Axios.post(url, params).then((res) => {
        if (res.data.code == 200) {
          if (this.safeForm && this.safeForm.id) {
            this.$message.success('修改成功')
            this.queryPlanGroupList(this.planId)
          } else {
            this.$message.success('新增成功')
            this.queryPlanGroupList(this.planId)
          }
        } else {
          if (this.safeForm && this.safeForm.id) {
            this.$message.success('修改失败')
          } else {
            this.$message.success('新增失败')
          }
        }
      })
    },
    // 保存应急响应终止
    saveStopResponse() {
      let params = {
        planNumId: this.planId,
        terminationCondition: this.stopResponseForm.terminationCondition,
        terminateProgram: this.stopResponseForm.terminateProgram
      }
      let url = ''
      if (this.stopResponseForm && this.stopResponseForm.id) {
        url = '/gtw/EIMS-management/update-plan-num-emergency-stop'
        params.id = this.stopResponseForm.id
      } else {
        url = '/gtw/EIMS-management/add-plan-num-emergency-stop'
      }
      Axios.post(url, params).then((res) => {
        if (res.data.code == 200) {
          if (this.stopResponseForm && this.stopResponseForm.id) {
            this.$message.success('修改成功')
            this.queryPlanGroupList(this.planId)
          } else {
            this.$message.success('新增成功')
            this.queryPlanGroupList(this.planId)
          }
        } else {
          if (this.stopResponseForm && this.stopResponseForm.id) {
            this.$message.success('修改失败')
          } else {
            this.$message.success('新增失败')
          }
        }
      })
    },
    // 保存奖励与责任追究
    saveRewardsDuty() {
      let params = {
        id: this.planId,
        rewardDuty: this.planRewardsDutyForm.rewardsDuty
      }
      let url = '/gtw/EIMS-management/update-plan-num-reward-duty'
      Axios.post(url, params).then((res) => {
        if (res.data.code == 200) {
          this.$message.success('保存成功')
          this.queryPlanGroupList(this.planId)
        } else {
          this.$message.error('保存失败')
        }
      })
    },
    // 重置相关资料
    resetAboutDoc() {
      this.formPlanAboutInfo = {
        planNumId: this.planId,
        fileName: '', // 名字
        remark: '', // 备注
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrl: '' // 文档路径
      }
    },
    // 新增相关资料
    addAboutDoc() {
      this.isPlanAboutCode = 2
      this.resetAboutDoc()
      this.showPlanAboutAdd = true
    },
    // 新增后的操作
    handlePlanAboutSuccess(id) {
      // console.log('新增后的操作id,', id)
      this.queryPlanGroupList(id)
    },
    // 查看资料详情
    aboutDocView(row) {
      // console.log('查看资料详情: ', row)
      this.isPlanAboutCode = 1
      this.formPlanAboutInfo = row
      this.showPlanAboutAdd = true
    },
    // 删除资料详情
    deleteAboutDoc(row) {
      // console.log('删除相关资料: ', row)
      let params = { delIds: [] }
      params.delIds.push(row.id)
      // console.log('删除分组参数:', params)
      let url = '/gtw/EIMS-management/save-plan-num-data'
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('删除成功')
          this.queryPlanTable()
          this.queryPlanGroupList(this.planId)
          // this.planId = row.id
        } else {
          this.$message.error('删除失败')
        }
      })
    }
  }
}
</script>
<style lang="scss">
.planDigitalProcess {
  background: #eff2f5 !important;
  padding: 0 !important;
  .left {
    width: 32%;
    height: 950px;
    background: #ffffff;
    padding: 15px;
  }
  .left-page {
    .el-pagination {
      text-align: left;
    }
    .el-pagination__total {
      margin-right: 0px;
    }
    .el-pagination__jump {
      margin-left: 0px;
    }
  }
  .right {
    width: 68%;
    height: 950px;
    background: #ffffff;
    padding: 15px;
    margin-left: 20px;
    .right_title {
      font-size: 18px;
      font-family: Microsoft YaHei;
      font-weight: bold;
      color: #4e5a6e;
      line-height: 80px;
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
    .tab_content {
      // height: 760px;
      // overflow: auto;
    }
  }
  .boxShadow3 {
    padding: 6px;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
  }
  .leftGroup {
    width: 30%;
    margin-right: 10px;
    margin-top: 10px;
    margin-left: 10px;
  }
  .rightGroup {
    width: 70%;
    margin-right: 10px;
    margin-top: 10px;
    margin-left: 10px;
  }

  // /deep/ .el-form-item__content {
  .el-form-item__content {
    width: 100%;
  }
}
</style>
