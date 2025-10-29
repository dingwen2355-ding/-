<template>
  <section class="editPlan" style="display: flex; flex-direction: row">
    <div class="left">
      <el-row class="header">
        <el-col :span="44">
          <el-form :inline="true" :model="searchform" ref="form">
            <el-form-item label="名称">
              <el-input v-model="searchform.planName" placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-plus" @click="addPlan">新增</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <div style="height: 720px; overflow: scroll">
        <el-table v-loading="loading" class="tableInfo" border :data="planTableData" style="width: 100%">
          <el-table-column show-overflow-tooltip prop="name" label="预案名称">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" size="medium">{{ scope.row.name }}</el-button>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="auditOpinion" label="审核意见"></el-table-column>
          <el-table-column fixed="right" label="操作" width="180">
            <template slot-scope="scope">
              <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
              <el-button @click="handleRelease(scope.row)" type="text" size="medium">发布</el-button>
              <!-- <el-button
                @click="handleRelease(scope.row)"
                type="text"
                size="medium"
                v-if="scope.row.status == '未发布' || scope.row.status == '退回' || scope.row.status == '3'"
                >发布</el-button
              >-->
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
          :page-size="pageSize"
          :pager-count="1"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <div class="right">
      <!-- <emergency-plan-tags showName="true" :planName="planName" :showRightContent="showRightContent"> -->
      <div class="right_title">{{ planName }}</div>
      <div class="tab">
        <div class="item" v-for="item in tabList" :key="item.id">
          <div class="line" :class="{ isActived: item.id === isActived }"></div>
          <div class="top" :class="{ isActived: item.id === isActived }" @click="handleDetail(item)">{{ item.id + 1 }}</div>
          <div class="name">{{ item.name }}</div>
        </div>
      </div>
      <div slot="prepare" style="height: 100%" v-if="isActived === 0">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button type="success" @click="saveGroups()" style="margin-right: 10px">保存</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="addGroupItem()" style="margin-bottom: 10px">添加分组</el-button>
        </div>
        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupData" :key="item.id">
            <el-form :inline="true" :model="item" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="分组" placeholder="请输入分组">
                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>
                      <i class="el-icon-delete" style="color: red; font-size: 20px" @click="delGroup(item.id, item)"></i>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <!--<el-input
                        type="textarea"
                        :rows="6"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                        v-model="item.remark"
                      ></el-input>-->
                      <auto-editor
                        v-model="item.remark"
                        :disabled="false"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                      ></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
                <!-- <el-form-item style="float: right; margin-right: 20px">
                  <el-button type="danger" plain @click="delGroup(item.id, item)">删除</el-button>
                </el-form-item>-->
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div slot="monitoringWarning" style="height: 100%" v-if="isActived === 1">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button type="success" @click="saveWarningGroups()" style="margin-right: 10px">保存</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="addWarningGroupItem()" style="margin-bottom: 10px">添加分组</el-button>
        </div>
        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupWarningData" :key="item.id">
            <el-form :inline="true" :model="item" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="分组" placeholder="请输入分组">
                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>
                      <i
                        class="el-icon-delete"
                        style="color: red; font-size: 20px"
                        @click="delWarningGroup(item.id, item)"
                      ></i>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <!--<el-input
                        type="textarea"
                        :rows="6"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                        v-model="item.remark"
                      ></el-input>-->
                      <auto-editor
                        :disabled="false"
                        v-model="item.remark"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                      ></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
                <!-- <el-form-item style="float: right; margin-right: 20px">
                  <el-button type="danger" plain @click="delWarningGroup(item.id, item)">删除</el-button>
                </el-form-item>-->
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div slot="emergencyResponse" style="height: 100%" v-if="isActived === 2">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button type="success" @click="saveResponseGroups()" style="margin-right: 10px">保存</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="addResponseGroupItem()" style="margin-bottom: 10px">添加分组</el-button>
        </div>
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
                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>
                      <i
                        class="el-icon-delete"
                        style="color: red; font-size: 20px"
                        @click="delResponseGroup(item.id, item)"
                      ></i>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <!--<el-input
                        type="textarea"
                        :rows="6"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                        v-model="item.remark"
                      ></el-input>-->
                      <auto-editor
                        :disabled="false"
                        v-model="item.remark"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                      ></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
                <!-- <el-form-item style="float: right; margin-right: 20px">
                  <el-button type="danger" plain @click="delResponseGroup(item.id, item)">删除</el-button>
                </el-form-item>-->
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div slot="emergencySafe" style="height: 100%" v-if="isActived === 3">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button type="success" @click="saveSafeGroups()" style="margin-right: 10px">保存</el-button>
          <el-button type="primary" icon="el-icon-plus" @click="addSafeGroupItem()" style="margin-bottom: 10px">添加分组</el-button>
        </div>
        <div style="height: calc(100% - 235px); width: 100%; overflow-x: hidden">
          <div style="width: 100%; margin-top: 10px; margin-left: 10px" v-for="item in groupSafeData" :key="item.id">
            <el-form :inline="true" :model="item" ref="form">
              <el-row class="header">
                <el-row>
                  <el-col>
                    <el-form-item label="分组" placeholder="请输入分组">
                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>
                      <i
                        class="el-icon-delete"
                        style="color: red; font-size: 20px"
                        @click="delSafeGroup(item.id, item)"
                      ></i>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <!--<el-input
                        type="textarea"
                        :rows="6"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                        v-model="item.remark"
                      ></el-input>-->
                      <auto-editor v-model="item.remark" :style="isCollapse ? 'width: 1085px' : 'width: 940px'"></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
                <!-- <el-form-item style="float: right; margin-right: 20px">
                  <el-button type="danger" plain @click="delSafeGroup(item.id, item)">删除</el-button>
                </el-form-item>-->
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div slot="stopEmergencyResponse" style="height: 100%" v-if="isActived === 4">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button type="success" @click="saveResponseStopGroups()" style="margin-right: 10px">保存</el-button>
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="addResponseStopGroupItem()"
            style="margin-bottom: 10px"
          >添加分组</el-button>
        </div>
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
                      <el-input v-model="item.groupName" :style="isCollapse ? 'width: 1065px' : 'width: 920px'"></el-input>
                      <i
                        class="el-icon-delete"
                        style="color: red; font-size: 20px"
                        @click="delResponseStopGroup(item.id, item)"
                      ></i>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="描述" placeholder="请输入描述信息">
                      <!--<el-input
                        type="textarea"
                        :rows="6"
                        :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
                        v-model="item.remark"
                      ></el-input>-->
                      <auto-editor v-model="item.remark" :style="isCollapse ? 'width: 1085px' : 'width: 940px'"></auto-editor>
                    </el-form-item>
                  </el-col>
                </el-row>
                <!-- <el-form-item style="float: right; margin-right: 20px">
                  <el-button type="danger" plain @click="delResponseStopGroup(item.id, item)">删除</el-button>
                </el-form-item>-->
              </el-row>
            </el-form>
          </div>
        </div>
      </div>
      <div slot="rewardsDuty" style="height: 70%" v-if="isActived === 5">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button type="success" @click="saveRewardsDuty()">保存</el-button>
        </div>
        <div class="info_body" style="margin-top: 20px; height: 100%; width: 100%">
          <el-form :model="planRewardsDutyForm" ref="form" :disabled="planId == ''">
            <el-form-item label="描述">
              <auto-editor
                :disabled="false"
                v-model="planRewardsDutyForm.rewardsDuty"
                :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
              ></auto-editor>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div slot="about" v-if="isActived === 6">
        <div style="text-align: right; height: 47px; width: 100%">
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="addAboutDoc()"
            style="margin-top: 20px; margin-right: 20px; margin-bottom: 10px"
          >新增</el-button>

          <div style="height: 565px; overflow: scroll">
            <el-table v-loading="aboutLoading" class="tableInfo" border :data="aboutDocData" style="width: 100%">
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
      <!-- </emergency-plan-tags> -->
    </div>
    <add-plan-dialog
      :dialogVisible.sync="showPlanAdd"
      @success="handlePlanSuccess"
      :formInfo="formPlanInfo"
      :is-code="isPlanCode"
      :addShow="true"
    ></add-plan-dialog>
    <add-about-dialog
      :dialogVisible.sync="showPlanAboutAdd"
      @success="handlePlanAboutSuccess"
      :formInfo="formPlanAboutInfo"
      :is-code="isPlanAboutCode"
      :plan-id="planId"
    ></add-about-dialog>
  </section>
</template>
<script>
// import emergencyPlanTags from '../../components/emergencyPlanTags.vue'
import AddAboutDialog from '../components/addAboutDialog.vue'
import AddPlanDialog from '../components/addPlanDialog.vue'
import znvTime from '../../../../utils/znvTime'
import Axios from 'axios'
import AutoEditor from '../../../../components/AutoEditor/AutoEditor.vue'
export default {
  components: {
    // emergencyPlanTags,
    AddPlanDialog,
    AddAboutDialog,
    AutoEditor
  },
  name: 'editPlan',
  data() {
    return {
      EditValue: '',
      planId: '',
      planName: '',
      searchform: {
        planName: null
      },
      loading: false,
      planTableData: [],
      page: 1,
      pageSize: 10,
      total: 0,
      showRightContent: false,
      groupData: [],
      groupWarningData: [],
      groupResponseData: [],
      groupSafeData: [],
      groupResponseStopData: [],
      planRewardsDutyForm: { rewardsDuty: '' },
      aboutLoading: false,
      aboutDocData: [],
      // status: 1, // 标志 我的预案
      showPlanAdd: false,
      formPlanInfo: {
        id: '',
        name: '', // 名字
        levelId: '', // 级别
        levelName: '',
        eventTypeId: '', // 类型
        eventTypeName: '',
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        status: '0', // 状态
        shared: '0', // 是否共享
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrls: [] // 上传文档
      },
      isPlanCode: 0, // 0 编辑 2 新增
      showPlanAboutAdd: false,
      formPlanAboutInfo: {
        id: '',
        planId: this.planId,
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
      isActived: 0,
      isCollapse: false
    }
  },
  watch: {
    '$store.getters.isCollapse': {
      handler(v) {
        this.isCollapse = v
      },
      immediate: true
    }
  },
  mounted() {
    this.queryMyPlanList()
  },
  methods: {
    handleDetail(val) {
      this.isActived = val.id
    },
    handleCurrentChange(val) {
      // console.log('当前页', val)
      this.page = val
      this.queryMyPlanList()
    },
    handleSizeChange(val) {
      // console.log('一页展示', val, '条数据')
      this.pageSize = val
      this.queryMyPlanList()
    },
    // 获取预案list
    queryMyPlanList() {
      this.loading = true
      let url = '/gtw/EIMS-management/query-plan'
      let params = {
        pageNum: this.page,
        pageSize: this.pageSize,
        status: ['0', '3']
      }
      if (this.searchform.planName) {
        params.name = this.searchform.planName
      }
      // console.log('params', params)
      Axios.post(url, params).then((res) => {
        this.planTableData = res.data.data.list
        this.total = res.data.data.total
        this.loading = false
      })
    },
    // 查询预案
    onSubmit() {
      this.page = 1
      this.queryMyPlanList()
    },
    resetPlan() {
      this.formPlanInfo = {
        id: '',
        name: '', // 名字
        levelId: '', // 级别
        levelName: '',
        eventTypeId: '', // 类型
        eventTypeName: '',
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        status: '0', // 状态
        shared: '0', // 是否共享
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrls: [] // 上传文档
      }
    },
    // 新增预案
    addPlan() {
      this.isPlanCode = 2
      this.showPlanAdd = true
      this.resetPlan()
    },
    // 点击表格名称后事件
    handleClick(row) {
      // console.log('点击表格名称后事件', row)
      this.showRightContent = true
      this.planId = row.id
      this.planName = row.name
      this.queryPlanGroupList(row.id)
    },
    // 编辑预案
    handleEdit(row) {
      // console.log('编辑预案row: ', row)
      this.showPlanAdd = true
      this.isPlanCode = 0
      if (row.emergencyTypeId && !Array.isArray(row.emergencyTypeId)) {
        row.emergencyTypeId = row.emergencyTypeId.split(',')
      }
      if (row.levelId) {
        row.levelId = Number(row.levelId)
      }
      if (row.status == '未发布') {
        row.status = '0'
      } else if (row.status == '待审核') {
        row.status = '1'
      } else if (row.status == '通过') {
        row.status = '2'
      } else if (row.status == '退回') {
        row.status = '3'
      }
      this.formPlanInfo = row
      // console.log('编辑预案row: ', row)
    },
    // 新增预案/编辑预案成功后操作
    handlePlanSuccess() {
      this.page = 1
      this.queryMyPlanList()
      this.showRightContent = false
    },
    // 发布预案
    handleRelease(row) {
      // console.log('发布预案row: ', row)
      let url = '/gtw/EIMS-management/release-plan'
      let params = {
        id: row.id,
        releaseTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS')
      }
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('发布成功')
          this.page = 1
          this.queryMyPlanList()
        } else {
          this.$message.error('发布失败')
        }
      })
    },
    // 删除预案
    handleDelete(row) {
      // console.log('删除预案row: ', row)
      let params = []
      params.push(row.id)
      this.$confirm('此操作将删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/gtw/EIMS-management/del-plan'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.page = 1
              this.searchform = { name: null }
              this.queryMyPlanList()
            } else {
              this.$message.error(res.data.message)
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
    resetList() {
      this.showRightContent = false
      this.groupData = []
      this.aboutDocData = []
      this.groupWarningData = []
      this.groupResponseData = []
      this.groupSafeData = []
      this.groupResponseStopData = []
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
    // 前期准备
    // 保存分组
    saveGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupData) {
          if (item.id == undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/gtw/EIMS-management/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 添加分组
    addGroupItem() {
      if (this.planId) {
        this.groupData.push({
          groupName: '',
          remark: '',
          groupType: '1'
        })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 删除分组
    delGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id != undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/gtw/EIMS-management/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupData.pop()
          // console.log('this.groupData', this.groupData)
        }
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 监测预警
    saveWarningGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupWarningData) {
          if (item.id == undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/gtw/EIMS-management/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    addWarningGroupItem() {
      if (this.planId) {
        this.groupWarningData.push({
          groupName: '',
          remark: '',
          groupType: '2'
        })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    delWarningGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id != undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/gtw/EIMS-management/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupWarningData.pop()
          // console.log('this.groupWarningData', this.groupWarningData)
        }
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 应急响应
    saveResponseGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupResponseData) {
          if (item.id == undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/gtw/EIMS-management/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    addResponseGroupItem() {
      if (this.planId) {
        this.groupResponseData.push({
          groupName: '',
          remark: '',
          groupType: '3'
        })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    delResponseGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id != undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/gtw/EIMS-management/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupResponseData.pop()
          // console.log('this.groupResponseData', this.groupResponseData)
        }
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 应急保障
    saveSafeGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupSafeData) {
          if (item.id == undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/gtw/EIMS-management/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    addSafeGroupItem() {
      if (this.planId) {
        this.groupSafeData.push({
          groupName: '',
          remark: '',
          groupType: '4'
        })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    delSafeGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id != undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/gtw/EIMS-management/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupSafeData.pop()
          // console.log('this.groupSafeData', this.groupSafeData)
        }
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 应急响应终止
    saveResponseStopGroups() {
      if (this.planId) {
        let params = { addPlanGroupBeans: [], updatePlanGroupBeans: [] }
        for (let item of this.groupResponseStopData) {
          if (item.id == undefined) {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.addPlanGroupBeans.push(item)
          } else {
            // console.log('保存分组:', item)
            item.planId = this.planId
            params.updatePlanGroupBeans.push(item)
          }
        }
        // console.log('保存分组参数:', params)
        let url = '/gtw/EIMS-management/save-plan-group'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    addResponseStopGroupItem() {
      if (this.planId) {
        this.groupResponseStopData.push({
          groupName: '',
          remark: '',
          groupType: '5'
        })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    delResponseStopGroup(index, item) {
      // console.log('index', index)
      // console.log('item', item)
      if (this.planId) {
        if (item.id != undefined) {
          let params = { delIds: [] }
          params.delIds.push(index.toString())
          // console.log('删除分组参数:', params)
          let url = '/gtw/EIMS-management/save-plan-group'
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('删除成功')
                this.queryPlanGroupList(this.planId)
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        } else {
          this.groupResponseStopData.pop()
          // console.log('this.groupResponseStopData', this.groupResponseStopData)
        }
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 保存奖励与责任追究
    saveRewardsDuty() {
      if (this.planId) {
        let params = {
          id: this.planId,
          rewardDuty: this.planRewardsDutyForm.rewardsDuty
        }
        let url = '/gtw/EIMS-management/update-plan-reward-duty'
        Axios.post(url, params)
          .then((res) => {
            if (res.data.code == 200) {
              this.$message.success('保存成功')
              this.queryPlanGroupList(this.planId)
            } else {
              this.$message.error('保存失败')
            }
          })
          .catch(() => {
            this.$message.error('保存失败')
          })
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 相关资料
    resetAboutDoc() {
      this.formPlanAboutInfo = {
        planId: this.planId,
        fileName: '', // 名字
        remark: '', // 备注
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrl: '' // 文档路径
      }
    },
    // 新增相关资料
    addAboutDoc() {
      if (this.planId) {
        this.isPlanAboutCode = 2
        this.resetAboutDoc()
        this.showPlanAboutAdd = true
      } else {
        this.$message.warning('请选择左侧预案后再进行操作')
      }
    },
    // 新增后的操作
    handlePlanAboutSuccess(id) {
      this.queryMyPlanList()
      this.queryPlanGroupList(id)
      // this.planId = id
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
      let url = '/gtw/EIMS-management/save-plan-data'
      Axios.post(url, params)
        .then((res) => {
          if (res.data.code === 200) {
            this.$message.success('删除成功')
            this.queryMyPlanList()
            this.queryPlanGroupList(this.planId)
            // this.planId = row.id
          } else {
            this.$message.error('删除失败')
          }
        })
        .catch(() => {
          this.$message.error('删除失败')
        })
    }
  }
}
</script>
<style lang="scss">
.editPlan {
  .left {
    // width: 30%;
    // height: 100%;
    // margin-right: 10px;
    // margin-top: 10px;
    // margin-left: 10px;
    width: 32%;
    height: 870px;
    background: #ffffff;
    padding: 15px;
  }
  .left-page {
    .el-pagination__total {
      margin-right: 0px;
    }
    .el-pagination__jump {
      margin-left: 0px;
    }
  }
  .right {
    // width: 70%;
    // height: 870px;
    // margin-right: 10px;
    // margin-top: 10px;
    // margin-left: 10px;
    width: 68%;
    height: 870px;
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
  }
}
</style>
