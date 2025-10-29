<template>
    <baseKanbanInfo title="事件延期">
      <div slot="body" v-loading="v_loadig" class="td-form-content-all" style="max-width: 1500px">
        <div class="w-100 show-flex-r d-c">
          <div style="padding: 20px; padding-bottom: 40px; width: 1200px">
            <el-steps :active="(item.status || 10) / 10 - 1" finish-status="success">
              <el-step title="事件提交" icon="el-icon-remove-outline" />
              <el-step title="区分派" icon="el-icon-remove-outline" />
              <el-step title="单位受理" icon="el-icon-remove-outline" />
              <el-step title="社区整改" v-show="item.handlerDeptType == null || item.handlerDeptType == 2"
                icon="el-icon-remove-outline" />
              <el-step title="街镇核查" v-show="item.handlerDeptType == null || item.handlerDeptType == 2"
                icon="el-icon-remove-outline" />
              <el-step title="区办结" icon="el-icon-remove-outline" />
              <el-step title="已办结" icon="el-icon-remove-outline" />
            </el-steps>
          </div>
        </div>
        <changeButton class="margin-left-20 margin-bottom-10" :lists="[{ label: '基础信息', value: 1 }]" size="big" noradis
          @BtnClick="(i, item) => {
            tabVal = item.value;
          }
            " />
        <el-form ref="ruleForm" :model="item" :rules="rules" size="small" label-position="top">
          <div class="w-100 show-flex-r padding-left-20">
            <div class="flex-1">
              <div
                style="padding: 20px;padding-left:40px;border: 1px solid #EBEEF5;box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);">
                <!-- 基础信息 -->
                <el-row v-show="tabVal == 1" class="show-flex-r wrap" :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="处置时限">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ item.timelimit }}</div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="考核周期">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ item.cycle }}</div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-col>
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
                    <el-form-item label="事件类型">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ item.eventTypeName }}</div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="是否转12345">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">
                            {{ item.citizenFlag == 0 ? "否" : "是" }}
                          </div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="24">
                    <el-form-item label="受理单位">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ (item.handlerDeptType && item.handlerDeptType == 1) ? '职能部门受理' :
                            '区划部门受理' }}</div>
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
                    <el-form-item label="责任单位">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ item.deptNames }}</div>
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
                    <el-form-item label="处置时限">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ item.timelimit }}</div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="24">
                    <el-form-item label="详细地址">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ item.address }}</div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
              <div v-if="useHandle && (item.extensionStatus == 1 || item.extensionStatus == 2)" style="border-top: 1px solid #ececec; margin: 20px 0"></div>
              <div v-if="useHandle && (item.extensionStatus == 1 || item.extensionStatus == 2)" 
                style="padding: 20px;padding-left:40px;border: 1px solid #EBEEF5;box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);">
                <el-row v-show="tabVal == 1" class="show-flex-r wrap" :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="延期时间">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ item.extension.extensionDate }}</div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="延期原因">
                      <div class="search-item-td" style="width: 100%">
                        <div class="search-wapper">
                          <div class="td-form-val">{{ item.extension.reason }}</div>
                        </div>
                      </div>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
              <div v-if="useHandle && item.extensionStatus != 1 && item.extensionStatus != 2" style="border-top: 1px solid #ececec; margin: 20px 0"></div>
              <div v-if="useHandle && item.extensionStatus != 1 && item.extensionStatus != 2" style="
                  padding: 20px;
                  padding-left: 40px;
                  border: 1px solid #ebeef5;
                  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                ">
                <div class="td-form-in-title">延期申请</div>
                <el-row class="show-flex-r wrap" :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="延期时间" prop="extensionDate">
                      <div class="zr-100-el-wapper">
                        <el-date-picker v-model="form.extensionDate" size="small" type="datetime"
                          value-format="yyyy-MM-dd hh:mm:ss" placeholder="选择延期时间" />
                      </div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="24">
                    <el-form-item label="延期原因" prop="reason">
                      <div>
                        <el-input v-model="form.reason" type="textarea" :rows="3" />
                      </div>
                    </el-form-item>
                  </el-col>
                </el-row>
                <div class="td-subform-wapper" style="margin-top: 20px">
                  <t-button @click="addNew()">确定</t-button>
                </div>
              </div>
            </div>
            <div style="width: 500px;">
              <div style="width: 100%;padding: 0 20px;">
                <el-timeline :reverse="false">
                  <el-timeline-item v-for="(activity, index) in activities" :key="index" placement="top"
                    :timestamp="activity.handletime" color="#0bbd87">
                    <el-card>
                      <h4>{{ activity.nodename }}</h4>
  
                      <p style="margin: 20px 0;" v-show="activity.handleDept">处理部门：{{ activity.handleDept || '无' }}</p>
                      <p style="margin: 20px 0;">提交内容：{{ activity.handleopinion || '-' }}</p>
                      <p v-if="activity.attchpathList != null && activity.attchpathList.length > 0" style="margin: 20px 0;">
                        附件：</p>
                      <div class="w-100">
                        <baseUpFile ref="attchpathList" :limit="3" :srclist="activity.attchpathList" :showAdd="false"
                          :showDel="false" onlyImg onlyUrl />
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
  import { detail, submit, extensionSave } from "@/api/workflow/workflow";
  export default {
    props: {
      useType: { type: String, default: "" },
      useHandle: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        id: 0,
        v_loadig: false,
        statusStep: 2,
        tabVal: 1,
        item: {
          // ========================基本信息
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
          // 分拨处置
          flowinstanceid: "",
          nodeid: "",
          status: "",
          handlerDeptType: "",
          extensionStatus: "",
          extension: {},
        },
        form: {
          taskId: "",
          extensionDate: "",
          reason: ""
        },
        eventReport: {},
        rules: {
          nodeid: [
            { required: true, message: "请选择节点", trigger: ["blur", "change"] }
          ]
          // handlerid: [{ required: true, message: '请选择处理人', trigger: ['blur', 'change'] }]
        },
        activities: [],
        dialogImageUrl: "",
        dialogVisible: false
      };
    },
    computed: {},
    created() {
      this.form.taskId = this.$route.query.taskid;
      this.item.flowinstanceid = this.$route.query.flowinstanceid;
      this.id = this.$route.query.id;
      this.flowinstanceid = this.$route.query.flowinstanceid;
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
        detail(this.id, this.flowinstanceid)
          .then(res => {
            _this.item = res.data.eventReport;
            _this.item.extension = res.data.extension;
            _this.activities = res.data.nodeHistoryList;
            _this.activities.forEach(item => {
              item.files = !!item.attchpath ? item.attchpath.split(",") : [];
            });
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
        let upData = JSON.parse(JSON.stringify(this.form));
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
      }
    }
  };
  </script>
  