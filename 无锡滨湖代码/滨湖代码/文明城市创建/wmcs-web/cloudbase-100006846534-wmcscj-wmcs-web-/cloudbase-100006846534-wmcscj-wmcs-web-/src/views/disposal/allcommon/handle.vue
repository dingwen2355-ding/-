<template>
    <baseKanbanInfo :title="showTitle">
      <div slot="body" v-loading="v_loadig" class="td-form-content-all" style="max-width: 90%;">
          <div class="w-100 show-flex-r d-c">
              <div style="padding: 20px;padding-bottom:40px;width: 1200px;">
                  <el-steps :active="((item.status || 10) / 10) - 1" finish-status="success">
                      <el-step title="事件提交" icon="el-icon-remove-outline" />
                      <el-step title="区分派" icon="el-icon-remove-outline" />
                      <el-step title="单位受理" icon="el-icon-remove-outline" />
                      <el-step title="社区整改" v-show="item.handlerDeptType == null || item.handlerDeptType == 2" icon="el-icon-remove-outline" />
                      <el-step title="区核查" icon="el-icon-remove-outline" />
                      <el-step title="已办结" icon="el-icon-remove-outline" />
                  </el-steps>
              </div>
          </div>
          <changeButton
              class="margin-left-20 margin-bottom-10"
              :lists="tabArr"
              size="big"
              noradis
              @BtnClick="(i, item) => { tabVal = item.value }"
          />
          <el-form ref="ruleForm" :model="item" :rules="rules" size="small" label-position="top">
              <div class="w-100 show-flex-r padding-left-20">
                  <div class="flex-1 w-0">
                      <div v-show="tabVal == 1" style="padding: 20px;padding-left:40px;border: 1px solid #EBEEF5;box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);">
                          <!-- 基础信息 -->
                          <el-row class="show-flex-r wrap" :gutter="20">
                              <el-col :span="12">
                                  <el-form-item label="事件编号">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.code }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="问题来源">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.eventSource | fEventSource }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="点位类型">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.checkPointsType }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="事发点位">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.checkPoints }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="24">
                                  <el-form-item label="检查项目">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.checkItem }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="事件大类">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.eventTypeOneName }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="事件小类">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.eventTypeTwoName }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="事件类型">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.eventTypeName }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="所属板块">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.regionName }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="责任单位">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.handlerDeptName }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="12">
                                  <el-form-item label="扣分">
                                  <div class="search-item-td" style="width: 100%; ">
                                      <div class="search-wapper">
                                      <div class="td-form-val" style="color:red;"> {{ item.score }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <el-col :span="24">
                                  <el-form-item label="存在问题">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.content }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col>
                              <!-- <el-col :span="24">
                                  <el-form-item label="详细地址">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">{{ item.address }}</div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col> -->
                              <!-- <el-col :span="12">
                                  <el-form-item label="是否转12345">
                                  <div class="search-item-td" style="width: 100%">
                                      <div class="search-wapper">
                                      <div class="td-form-val">
                                          {{ item.citizenFlag == 0 ? "否" : "是" }}
                                      </div>
                                      </div>
                                  </div>
                                  </el-form-item>
                              </el-col> -->
                          </el-row>
                      </div>
                      <div v-if="tabVal == 3"
                          style="
                              padding: 20px;
                              padding-left: 40px;
                              border: 1px solid #ebeef5;
                              box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                          "
                      >
                          <!-- 事件延期列表 -->
                          <el-table stripe :data="item.extension">
                            <el-table-column label="延期时间" prop="extensionDate" width="170" />
                            <el-table-column label="延期原因" prop="reason" min-width="120" />
                            <el-table-column label="申请时间" prop="applyDate" width="170" />
                            <el-table-column label="申请单位" prop="applyDeptName" width="120" />
                            <el-table-column label="状态" prop="status" width="80">
                                <template slot-scope="scope">
                                    <div>
                                        <el-tag v-if="scope.row.status == 0" type="info" size="small">待审核</el-tag>
                                        <el-tag v-if="scope.row.status == 1" type="success" size="small">已通过</el-tag>
                                        <el-tag v-if="scope.row.status == 2" type="danger" size="small">不通过</el-tag>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="审批时间" prop="examineTime" width="170" />
                            <el-table-column label="审批意见" prop="opinion" min-width="120" />
                            <el-table-column label="操作" width="80" fixed="right">
                                <template slot-scope="scope">
                                    <div class="show-flex-r a-c">
                                        <div v-if="scope.row.status == 0 && pow('ExtensionExamine')" class="zr-link-p unline m-5" @click="toExamine(scope.row)">审批</div>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                          <!-- 延期审核 -->
                            <diaApprove
                                v-if="diaShowApprove"
                                :show="diaShowApprove"
                                @cancel="diaShowApprove = false"
                                @sure="approveItem"
                            />
                      </div>
                      <div v-if="tabVal == 4"
                          style="
                              padding: 20px;
                              padding-left: 40px;
                              border: 1px solid #ebeef5;
                              box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                          "
                      >
                          <!-- <div class="td-form-in-title">申诉列表</div> -->
                          <el-table stripe :data="item.appeal">
                            <el-table-column label="申诉时间" prop="appealTime" width="170" />
                            <el-table-column label="申诉单位" prop="applyDeptName" width="120" />
                            <el-table-column label="申诉原因" prop="reason" min-width="120" />
                            <el-table-column label="原责任单位" prop="oldDeptName" width="120" />
                            <el-table-column label="状态" prop="status" width="80">
                                <template slot-scope="scope">
                                    <div>
                                        <el-tag v-if="scope.row.status == 0" type="info" size="small">待审核</el-tag>
                                        <el-tag v-if="scope.row.status == 1" type="success" size="small">已通过</el-tag>
                                        <el-tag v-if="scope.row.status == 2" type="danger" size="small">不通过</el-tag>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="审批时间" prop="examineTime" width="170" />
                            <el-table-column label="审批意见" prop="opinion" min-width="120" />
                            <el-table-column label="新责任单位" prop="newDeptName" width="120" />
                            <el-table-column label="操作" width="80" fixed="right">
                                <template slot-scope="scope">
                                    <div class="show-flex-r a-c">
                                        <div v-if="scope.row.status == 0 && pow('ExtensionExamine')" class="zr-link-p unline m-5" @click="toShensu(scope.row)">审批</div>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                          <!-- 申诉审核 -->
                            <diaAppealApprove
                                v-if="diaShowShensu"
                                :show="diaShowShensu"
                                :detail="nowItem"
                                @cancel="diaShowShensu = false"
                                @sure="approveShensu"
                            />
                      </div>
                  </div>
                  <div style="width: 500px;height:60vh;overflow: auto;">
                      <div style="width: 100%;padding: 0 20px;">
                          <!-- 模拟组件做个新头 -->
                          <div class="zr-moni-timeline">
                              <div class="left-line">
                                  <div class="box"></div>
                                  <div class="line"></div>
                              </div>
                              <div class="right-wapper">
                                  <div class="in-wapper">
                                      <p style="margin: 10px 0 20px 0;" >事件状态： {{ item.status | fStatus }}</p>
                                      <p style="margin: 10px 0 20px 0;" >当前节点：  {{ item.nodename}}</p>    
                                      <p style="margin: 10px 0 20px 0;" v-show="item.handleDeptName">下一步处置部门： {{ item.handleDeptName}}</p>
                                      <p style="margin: 10px 0 20px 0;" >巡查员信息：  {{ item.reportPerson}} </p>    
                                      <p style="margin: 10px 0 20px 0;" >访问员信息：  / </p> 
                                  </div>
                              </div>
                          </div>
                          <el-timeline :reverse="false">
                              <el-timeline-item v-for="(activity, index) in activities" :key="index" placement="top"
                                  :timestamp="activity.handletime" color="#0bbd87">
                                  <el-card>
                                  <h4>{{ activity.nodename }}</h4>
                                  
                                  <p style="margin: 20px 0;" v-show="activity.handleDept">处理部门：{{ activity.handleDept || '无'}}</p>
                                  <p style="margin: 20px 0;">提交内容：{{ activity.handleopinion || '-' }}</p>
                                  <p v-if="activity.attchpathList != null && activity.attchpathList.length > 0" style="margin: 20px 0;">附件：</p>
                                  <div class="w-100">
                                      <baseUpFile
                                          ref="attchpathList"
                                          :limit="3"
                                          :srclist="activity.attchpathList"
                                          :showAdd="false" 
                                          :showDel="false"
                                          onlyImg
                                          onlyUrl
                                      />
                                  </div>
                                  </el-card>
                              </el-timeline-item>
                          </el-timeline>
                      </div>
                  </div>
              </div>
          </el-form>
      </div>
    </baseKanbanInfo>
  </template>
  <script>
  import { detail, extensionApprove, appealApprove } from "@/api/workflow/workflow";
  import InfoData from "../assessCycle/infoData.vue";
  import diaAppealApprove from '../appeal/diaAppealApprove.vue'
  const moment = require('moment')
  export default {
    filters: {
      fStatus: function(v) {
          if(v == 20) return '待分派'
          if(v == 30) return '待受理'
          if(v == 40) return '待处置'
          if(v == 50) return '待核查'
          if(v == 60) return '已办结'
          return ''
      },
      fEventSource: function(v) {
          if(v == 1) return '日常巡查'
          if(v == 2) return 'AI告警'
          return ''
      }
    },
    components: { diaAppealApprove },
    props: {
      useType: { type: String, default: "" },
      useHandle: {
          type: Boolean,
          default: false
      },
      handleType: {
          // yq: 延期 ss:申诉
          type: String,
          default: ''
      },
      optTag: {
          type: String,
          default: '',
      }
    },
    data() {
      let getThis = () => { return this }
      return {
        id: 0,
        v_loadig: false,
        statusStep: 2,
        tabVal: 1,
        taskid: '',
        scope: '',
        rules: {},
        item: {
          // ========================基本信息
          cycleId:"",
          checkPointsTypeId: "",
          checkPointsId: "",
          checkItemId: "",
          deptIds: "",
          deptNames: "",
          eventType: "",
          eventTypeOne: "",
          eventTypeTwo: "",
          score: 0,
          eventTitle: "",
          content: "",
          address: "",
          flowid: "1234",
          routeid: "1",
          startnodeid: "Start",
          endnodeid: "Node1",
          coordinate: "",
          // 分拨处置
          flowinstanceid: '',
          nodeid: '',
          taskid: '',
          handlerid: '',
          handleopinion: '',
          status: '',
          handlerDeptType: '',
          attchpath: [],
          extensionStatus: "",
          extension: {},
          messageFlag: 0, // 站内信
          smsFlag: 0, // 短信
          // 延期
          extension: {},
          extensionDate: "",
          extensionExamineTime: "",
          extensionOpinion: "",
          reason: "",
          appealExamineTime: "",
          appealOpinion: "",
          // 申诉
          appeal: {},
          applyReason: "",
        },
        eventReport: {},
        activities: [],
        dialogImageUrl: "",
        dialogVisible: false,
        yqInfo_flag: false, // 延期详情
        ssInfo_flag: false,   //申诉详情
        nowItem: null,
        // =======延期
        diaShowApprove: false,
        // 申诉
        diaShowShensu: false,
      };
    },
    computed: {
      yq_pickerOptions() {
          // 延期时间范围选择
          let _this = this
          // let v1 = ''
          return {
              disabledDate(t) {
                  let d = moment(t).format('YYYY-MM-DD')
                  let cztime = moment(_this.item.timelimit || moment()).format('YYYY-MM-DD')
                  return d < cztime
              }
          }
      },
      showTitle() {
          let title = '事件详情'
          if (this.handleType == 'yq') title = '事件延期'
          if (this.handleType == 'ss') title = '事件申诉'
          return title
      },
      baseDisable() {
          // 详情禁用 true：详情
          return false
      },
      tabArr() {
          console.log(this.item)
          let arr = [{ label: '基础信息', value: 1 }]
          if (this.item.id) {
              if (this.handleType == 'yq' || this.handleType == 'all') {
                  arr.push({ label: '事件延期', value: 3 })
              }
              if (this.handleType == 'ss' || this.handleType == 'all') {
                  arr.push({ label: '事件申诉', value: 4 })
              }
              return arr
          } else {
              return arr
          }
      },
    },
    created() {
      this.taskid = this.$route.query.taskid;
      this.item.flowinstanceid = this.$route.query.flowinstanceid;
      this.id = this.$route.query.id;
      this.flowinstanceid = this.$route.query.flowinstanceid;
      this.scope = this.$route.query.scope;
      this.getInfo();
    },
    methods: {
        // 延期审核
        toExamine(item) {
            this.nowItem = item
            this.diaShowApprove = true
        },
        approveItem(result) {
            let _this = this
            this.diaShowApprove = false
            extensionApprove({
                "id": _this.nowItem.id,
                "status": result.status,
                "opinion": result.reason
            }).then(d => {
                _this.$successMessage("操作成功!")
                _this.getInfo()
            })
        },
        // 申诉
        toShensu(item) {
            this.nowItem = item
            this.diaShowShensu = true
        },
        approveShensu(result) {
            let _this = this
            this.diaShowShensu = false
            appealApprove({
                "id": _this.nowItem.id,
                "status": result.status,
                "newDeptIds": result.newDeptIds,
                "opinion": result.reason
            }).then(d => {
                _this.$successMessage("操作成功!")
                _this.getInfo()
            })
        },
      changeNode(item) {
        this.$refs.handlerid.setValWithChange("", item?.nodeid || "");
      },
      goBack() {
        this.$router.back(-1);
      },
      refushBack() {
        this.$router.push({ path: "./list?_list_refush=true" });
      },
      getInfo() {
        let _this = this;
        this.v_loadig = true;
        // 获取详情接口
        detail(this.id, this.flowinstanceid, this.scope, this.taskid)
          .then(res => {
              let inData = res.data.eventReport
              if (res.data.appeal) {
                  let p = res.data.appeal
                  console.log("============")
                  console.log(p)
                  _this.ssInfo_flag = true
                  inData.applyReason = p.reason
                  inData.appealExamineTime = p.examineTime
                  inData.appealOpinion = p.opinion
              }
              _this.item = inData
              _this.activities = res.data.nodeHistoryList;
              _this.item.attchpath = []
          })
          .finally(e => {
            _this.v_loadig = false;
          });
      },
      handlePreview(url) {
        this.dialogImageUrl = url;
        this.dialogVisible = true;
      }
      
    }
  };
  </script>
  <style lang="scss" scoped>
  .zr-moni-timeline{
      width: 100%;
      display: flex;
      .left-line{
          width: 28px;
          position: relative;
          .box{
              width: 12px;
              height: 12px;
              border-radius: 50%;
              background: #0052d9;
              // background: #0bbd87;
              margin-left: -1px;
          }
          .line{
              position: absolute;
              top: 12px;
              left: 4px;
              bottom:0;
              border-left: 2px solid #e4e7ed;
          }
      }
      .right-wapper{
          flex:1;
          width: 0;
          .in-wapper{
              width: 100%;
              background: #ffffff;
              padding: 20px;
              margin-bottom: 20px;
              box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
              border-radius: 3px;
              border: 1px solid #EBEEF5;
              color: #303133;
              font-size: 14px;
          }
      }
  }
  </style>
  