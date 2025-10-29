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
                <div class="flex-1">
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
                            <el-col :span="24" v-if="item.eventSource != 2">
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
                                    <div class="td-form-val">{{ item.handlerDeptName || item.deptNames }}</div>
                                    </div>
                                </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12" v-if="item.eventSource != 2">
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
                        <!-- <div class="td-form-in-title">延期申请</div> -->
                        <el-row class="show-flex-r wrap" :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="延期时间" prop="extensionDate">
                                <div class="zr-100-el-wapper">
                                    <el-date-picker v-model="item.extensionDate" size="small" type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择延期时间"
                                        :disabled="yqInfo_flag"
                                        :picker-options="yq_pickerOptions"
                                    />
                                </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="处置时限">
                                    <div class="zr-100-el-wapper">
                                        {{ item.timelimit }}
                                    </div>
                                </el-form-item>
                            </el-col>
                             <el-col :span="24">
                                <el-form-item label="延期原因" prop="reason">
                                <div>
                                    <el-input v-model="item.reason" type="textarea" :rows="3" :disabled="yqInfo_flag" />
                                </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12" v-show="yqInfo_flag">
                                <el-form-item label="审核时间" prop="extensionExamineTime">
                                    <div class="zr-100-el-wapper">
                                        <el-date-picker v-model="item.extensionExamineTime" size="small" type="datetime"
                                        value-format="yyyy-MM-dd hh:mm:ss" placeholder="选择审核时间"
                                        :disabled="yqInfo_flag"
                                        />
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24" v-show="yqInfo_flag">
                                <el-form-item label="审核意见" prop="extensionOpinion">
                                    <div>
                                        <el-input v-model="item.extensionOpinion" type="textarea" :rows="3" :disabled="yqInfo_flag" />
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <div class="td-subform-wapper" style="margin-top: 20px">
                        <t-button v-show="!yqInfo_flag" @click="addYQ()">确定</t-button>
                        </div>
                    </div>
                    <!-- <div v-if="useHandle" style="border-top:1px solid #ececec;margin:20px 0;"></div> -->
                    <div v-if="tabVal == 2" style="padding: 20px;padding-left:40px;border: 1px solid #EBEEF5;box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);">
                        <!-- <div class="td-form-in-title">单位受理</div> -->
                        <el-row class="show-flex-r wrap" :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="节点" prop="nodeid">
                                    <selectNode
                                        ref="nodeid"
                                        :taskid="taskid"
                                        :flowinstanceid="item.flowinstanceid"
                                        noLabel
                                        useType="form"
                                        allWidth="100%"
                                        @change="v => { item.nodeid = v['nodeid'] || ''; }"
                                        @changeNext="changeNode"
                                    />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="处理人" prop="handlerid">
                                    <selectHandler
                                        ref="handlerid"
                                        noLabel
                                        :flowinstanceid="item.flowinstanceid"
                                        useType="form"
                                        allWidth="100%"
                                        @change="v => {item.handleDeptid = v['handlerid'] || '';}"
                                    />
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="通知方式" prop="handleopinion">
                                    <div>
                                        <el-checkbox v-model="item.messageFlag" :true-label="1" :false-label="0">站内信</el-checkbox>
                                        <el-checkbox v-model="item.smsFlag" :true-label="1" :false-label="0">短信</el-checkbox>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="反馈意见" prop="handleopinion">
                                    <div>
                                        <el-input v-model="item.handleopinion" type="textarea" :rows="3" />
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="附件">
                                    <div class="w-100">
                                        <baseUpFile
                                            ref="attchpath"
                                            :limit="9"
                                            :srclist="item.attchpath"
                                            :showAdd="!baseDisable"
                                            :showDel="!baseDisable"
                                            onlyImg
                                            onlyUrl
                                            tableName="notable"
                                            fieldName="nofield"
                                        />
                                    <div v-if="!baseDisable" class="file-ps-text">* 可上传图片,最多上传9张图片,每张图片不超过10M</div>
                                </div>
                            </el-form-item>
                        </el-col>
                        </el-row>
                        <div class="td-subform-wapper" style="margin-top:5px;">
                            <t-button @click="addNew()">确定</t-button>
                        </div>
                    </div>

                    <div v-if="tabVal == 4"
                        style="
                            padding: 20px;
                            padding-left: 40px;
                            border: 1px solid #ebeef5;
                            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                        "
                    >
                        <!-- <div class="td-form-in-title">申诉</div> -->
                        <el-row class="show-flex-r wrap" :gutter="20">
                             <el-col :span="24">
                                <el-form-item label="申诉原因" prop="applyReason">
                                <div>
                                    <el-input v-model="item.applyReason" type="textarea" :rows="3" :disabled="ssInfo_flag" />
                                </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12" v-show="yqInfo_flag">
                                <el-form-item label="审核时间" prop="appealExamineTime">
                                    <div class="zr-100-el-wapper">
                                        <el-date-picker v-model="item.appealExamineTime" size="small" type="datetime"
                                        value-format="yyyy-MM-dd hh:mm:ss" placeholder="选择审核时间"
                                        :disabled="yqInfo_flag"
                                        />
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24" v-show="yqInfo_flag">
                                <el-form-item label="审核意见" prop="appealOpinion">
                                    <div>
                                        <el-input v-model="item.appealOpinion" type="textarea" :rows="3" :disabled="yqInfo_flag" />
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <div class="td-subform-wapper" style="margin-top: 20px">
                        <t-button v-show="!ssInfo_flag" @click="addSS()">确定</t-button>
                        </div>
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
                                    <p style="margin: 10px 0 20px 0;" >访问员信息：  /</p> 
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
import { detail, submit, extensionSave, apealSave, appealSave } from "@/api/workflow/workflow";
import InfoData from "../assessCycle/infoData.vue";
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
    },
    // scope: {
    //     type: String,
    //     default: ''
    // },
    // taskid: {
    //     type: String,
    //     default: ''
    // }
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
      rules: {
            nodeid: [{ required: true, message: '请选择节点', trigger: ['blur', 'change'] }],
            // 延期必填
            extensionDate: [
                { required: true, message: '请填写延期时间', trigger: ['blur', 'change'] },
                {
                    validator: (rule, value, callback) => {
                        let _this = getThis()
                        // 首先是必填
                        if (!_this.item.extensionDate) return callback(new Error('请选择延期时间!'))
                        if (_this.item.extensionDate <= _this.item.timelimit) return callback(new Error('延期时间不能小于处置时限!'))
                        callback()
                    },
                    trigger: ['blur', 'change']
                }
            ],
            // 申诉
            applyReason: [{ required: true, message: '请填写申诉原因', trigger: ['blur', 'change'] }]
        },
      activities: [],
      dialogImageUrl: "",
      dialogVisible: false,
      yqInfo_flag: false, // 延期详情
      ssInfo_flag: false,   //申诉详情
    };
  },
  computed: {
    yq_pickerOptions() {
        // 延期时间范围选择
        let _this = this
        // let v1 = ''
        return {
            // onPick(v) {
            //     v1 = v.maxDate ? '' : moment(v.minDate)
            // },
            disabledDate(t) {
                let d = moment(t).format('YYYY-MM-DD')
                let cztime = moment(_this.item.timelimit || moment()).format('YYYY-MM-DD')
                return d < cztime
                // if (v1) {
                //     let minD = moment(v1).add(0 - _this.over, 'days').format('YYYY-MM-DD')
                //     let maxD = moment(v1).add(_this.over, 'days').format('YYYY-MM-DD')
                //     let v_t = moment(t).format('YYYY-MM-DD')
                //     return v_t < minD || v_t > maxD
                // } else {
                //     return false
                // }
            }
        }
    },
    showTitle() {
        let title = '事件详情'
        if (this.useHandle && this.handleType == 'sl') title = '事件受理'
        if (this.useHandle && this.handleType == 'yq') title = '事件延期'
        if (this.useHandle && this.handleType == 'ss') title = '事件申诉'
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
            if (this.handleType == 'sl') {
                arr.push({ label: '事件受理', value: 2 })
            }
            if (this.item.extensionFlag || this.handleType == 'yq') {
                arr.push({ label: '事件延期', value: 3 })
            }
            if (this.item.extensionFlag || this.handleType == 'ss') {
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
            if (inData.extension) {
                let v = inData.extension[0]
                res.data.eventReport.extension = v
                inData.extensionDate = v.extensionDate
                inData.reason = v.reason
                inData.extensionExamineTime = v.examineTime
                inData.extensionOpinion = v.opinion
                _this.yqInfo_flag = true
            }
            if (inData.appeal && inData.appeal.size > 0) {
                let p = inData.appeal[0]
                res.data.eventReport.appeal = p
                inData.applyReason = p.reason
                inData.appealExamineTime = p.examineTime
                inData.appealOpinion = p.opinion
                _this.ssInfo_flag = true
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
    },
    addNew() {
      let _this = this;
    //   this.useTrim();
        
      let upData = JSON.parse(JSON.stringify(this.item));
      console.log(upData)
      upData.attchpath = upData.attchpath.join(',')
      upData.optTag = _this.optTag;
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          if (this.v_loadig) return;
          this.v_loadig = true;
          submit(upData)
            .then(d => {
              _this.$successMessage("操作成功!");
              _this.refushBack();
            })
            .finally(e => {
              _this.v_loadig = false;
            });
        } else {
          return false;
        }
      });
    },
    addYQ() {
        let _this = this;
        let upData = JSON.parse(JSON.stringify({
            taskId: this.taskid,
            extensionDate: this.item.extensionDate,
            reason: this.item.reason
        }))
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            if (this.v_loadig) return;
            this.v_loadig = true;
            extensionSave(upData)
              .then(d => {
                _this.$successMessage("操作成功!");
                _this.refushBack();
              })
              .finally(e => {
                _this.v_loadig = false;
              });
          } else {
            return false;
          }
        });
    },
    addSS() {
        let _this = this;
        let upData = JSON.parse(JSON.stringify({
            taskId: this.taskid,
            applyReason: this.item.applyReason
        }))
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            if (this.v_loadig) return;
            this.v_loadig = true;
            appealSave(upData)
              .then(d => {
                _this.$successMessage("操作成功!");
                _this.refushBack();
              })
              .finally(e => {
                _this.v_loadig = false;
              });
          } else {
            return false;
          }
        });
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
