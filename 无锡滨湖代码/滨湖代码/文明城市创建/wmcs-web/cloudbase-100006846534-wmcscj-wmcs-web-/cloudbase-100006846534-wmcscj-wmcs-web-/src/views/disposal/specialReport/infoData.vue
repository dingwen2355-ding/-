
<template>
  <baseKanbanInfo :title="useType == 'add' ? '上报事件' : '事件详情'">
    <div
      slot="headbtn"
      class="show-flex-r a-c padding-left-20 padding-right-20"
    >
      <div class="show-flex-r a-c">
        <!-- <el-button type="warning" size="mini" @click="goBack()">取消</el-button> -->
      </div>
    </div>
    <div slot="body" v-loading="v_loadig" class="td-form-content-wap">
      <div class="w-100 show-flex-r d-c">
        <div style="padding: 20px; padding-bottom: 40px; width: 1200px">
          <el-steps :active="0" finish-status="success">
            <el-step title="事件提交" icon="el-icon-remove-outline" />
            <el-step title="区分派" icon="el-icon-remove-outline" />
            <el-step title="单位受理" icon="el-icon-remove-outline" />
            <el-step
              title="社区整改"
              v-show="item.handlerDeptType == null || item.handlerDeptType == 2"
              icon="el-icon-remove-outline"
            />
            <el-step
              title="街镇核查"
              v-show="item.handlerDeptType == null || item.handlerDeptType == 2"
              icon="el-icon-remove-outline"
            />
            <el-step title="区办结" icon="el-icon-remove-outline" />
            <el-step title="已办结" icon="el-icon-remove-outline" />
          </el-steps>
        </div>
      </div>
      <el-form
        ref="ruleForm"
        :model="item"
        :rules="rules"
        class="td-form-content-wap"
        size="small"
        label-position="top"
      >
        <el-row class="show-flex-r wrap" :gutter="20">
          <el-col :span="12">
            <el-form-item label="考核周期" prop="cycleId">
              <searchAssessCycle
                ref="assessCycleId"
                noLabel
                useType="form"
                allWidth="100%"
                :disabled="baseDisable"
                @change="
                  v => {
                    item.cycleId = v['assessCycleId'] || '';
                    item.cycle = v['assessCycleName'] || '';
                  }
                "
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="街道/社区" prop="regionCode">
              <searchRegionDept
                ref="regionId"
                noLabel
                useType="form"
                allWidth="100%"
                :disabled="baseDisable"
                @change="changeRegion" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="事发点位" prop="checkPointsId">
              <selectCheckPoints
                ref="checkPointsId"
                noLabel
                allWidth="100%"
                :disabled="baseDisable"
                @change="
                  v => {
                    item.checkPointsId = v['checkPointsId'] || '';
                  }
                "
                @changeNext="changeCheckPoint"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="点位类型" prop="checkPointsTypeId">
              <searchCheckPointsType
                ref="checkPointsTypeId"
                keyName="checkPointsTypeId"
                noLabel
                useType="form"
                allWidth="100%"
                :disabled="baseDisable"
                @change="
                  v => {
                    item.checkPointsTypeId = v['checkPointsTypeId'] || '';
                  }
                "
                @changeNext="changeCheckPointType"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="检查项目" prop="checkItemId">
              <selectCheckItem
                ref="checkItemId"
                noLabel
                allWidth="100%"
                :disabled="baseDisable"
                @change="
                  v => {
                    item.checkItemId = v['checkItemId'] || '';
                    item.eventTitle = v['checkItemName'] || '';
                  }
                "
                @changeNext="changeCheckContent"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="责任单位" prop="deptIds">
              <selectDepts
                ref="deptIds"
                noLabel
                allWidth="100%"
                :disabled="baseDisable"
                filterType="all"
                @change="
                  v => {
                    item.deptIds = v['deptIds'] || '';
                  }
                "
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="扣分" prop="score">
              <div v-if="!baseDisable">
                <el-input v-model="item.score" />
              </div>
              <div v-else class="td-form-val" style="color: red">
                {{ -item.score || "" }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="事件类型" prop="eventType">
              <selectEventType
                ref="eventType"
                keyName="eventType"
                noLabel
                allWidth="100%"
                :disabled="baseDisable"
                filterType="all"
                @change="
                  v => {
                    item.eventTypeOne = v['eventType'][0] || '';
                    item.eventTypeTwo = v['eventType'][1] || '';
                    item.eventType = v['eventType'][2] || '';
                  }
                "
              />
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="经度" prop="lon">
              <div v-if="!baseDisable">
                <el-input v-model="lon" />
              </div>
              <div v-else class="td-form-val">
                {{ lon || "" }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纬度" prop="lat">
              <div v-if="!baseDisable">
                <el-input v-model="lat" />
              </div>
              <div v-else class="td-form-val">
                {{ lat || "" }}
              </div>
            </el-form-item>
          </el-col> -->
          <!-- <el-col :span="24">
              <el-form-item label="事件标题" prop="eventTitle">
                <div v-if="!baseDisable">
                  <el-input v-model="item.eventTitle" />
                </div>
                <div v-else class="td-form-val">
                  {{ item.eventTitle || "" }}
                </div>
              </el-form-item>
            </el-col> -->
          <el-col :span="24">
            <el-form-item label="存在问题" prop="content">
              <div v-if="!baseDisable">
                <el-input v-model="item.content" type="textarea" :rows="3" />
              </div>
              <div v-else class="td-form-val">
                {{ item.content || "" }}
              </div>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="24">
            <el-form-item label="详细地址" prop="address">
              <div v-if="!baseDisable">
                <el-input v-model="item.address" />
              </div>
              <div v-else class="td-form-val">
                {{ item.address || "" }}
              </div>
            </el-form-item>
          </el-col> -->
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
                <div v-if="!baseDisable" class="file-ps-text">
                  * 可上传图片,最多上传9张图片,每张图片不超过10M
                </div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="td-subform-wapper">
        <t-button v-if="useType == 'add'" @click="addNew()">确定</t-button>
        <t-button v-if="useType == 'edit'" @click="editItem()">确定</t-button>
        <t-button ghost class="margin-left-20" @click="goBack()">取消</t-button>
      </div>
    </div>
  </baseKanbanInfo>
</template>
<script>
import { eventReport, detail } from "@/api/workflow/workflow";
export default {
    props: {
        useType: { type: String, default: "" }
    },
    data() {
        return {
            id: 0,
            v_loadig: false,
            formLabelWidth: "130px",
            lon:"",
            lat:"",
            item: {
                // ========================基本信息
                cycleId: "",
                checkPointsTypeId: "",
                checkPointsId: "",
                regionId: '',
                regionCode: '',
                regionName: '',
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
                attchpath: []
            },
            // 用来默认使用责任单位回显的
            deptFrom: {
                id: [],
                name: []
            },
            rules: {
                cycleId: [{required: true, message: "请选择考核周期", trigger: ["blur", "change"]}],
                checkPointsTypeId: [{required: true, message: "请选择点位类型", trigger: ["blur", "change"]}],
                checkPointsId: [{required: true, message: "请选择事发点位", trigger: ["blur", "change"]}],
                content: [{required: true, message: "请输入存在问题", trigger: ["blur", "change"]}]
            }
        };
    },
    computed: {
        baseDisable() {
            // 详情禁用
            return !!(this.useType == "info");
        }
    },
    mounted() {
        if (this.useType != "add") {
            this.id = this.$route.query.id;
            this.getInfo();
        }
    },
    methods: {
        goBack() {
            this.$router.back(-1);
        },
        refushBack() {
            this.$router.push({ path: "./list?_list_refush=true" });
        },
        changeRegion(data) {
            // 切换社区
            this.item.regionCode = data['regionId'] || ''
            // 清空点位类型:触发全自动
            this.$refs.checkPointsTypeId.setValWithChange('')
            this.$refs.checkPointsId.setValWithChange("", "", this.item.regionCode);
        },
        changeCheckPointType(data) {
            // 切换点位类型后做联动
            this.$refs.checkPointsId.setValWithChange("", data?.id || "", this.item.regionCode);
            // 检查项目:选择自动触发
            this.$refs.checkItemId.setValWithChange("", data?.id || "");
            // 检查项目:选择自动触发
            this.$refs.regionId.setValWithChange("", "", "");
        },
        changeCheckPoint(data) {
            // 切换事发点位
            this.item.regionCode = data && data.regionCode || ''
            this.item.regionName = data && data.regionName || ''
            // 根据名称勾选责任部门
            this.deptFrom.name = [this.item.regionName]
            this.$refs.deptIds.setValByAll(this.deptFrom.id, this.deptFrom.name)
            // 带出点位类型:不自动
            this.item.checkPointsTypeId = data.typeId
            this.$refs.checkPointsTypeId.setValWithoutChange(data.typeId)
            // 手动触发类型变更后续
            // 检查项目:选择自动触发
            this.$refs.checkItemId.setValWithChange("", data.typeId || "")
        },
        changeCheckContent(data) {
            // 切换检查项目后做联动
            // 责任单位
            this.deptFrom.id = (data?.deptIds || "").split(",")
            this.$refs.deptIds.setValByAll(this.deptFrom.id, this.deptFrom.name);
            // 事件类型
            this.$refs.eventType.setValWithChange(data?.eventTypeId || "");
            // 扣分
            this.item.score = data?.score || "";
        },
        getInfo() {
            let _this = this;
            this.v_loadig = true;
            // 获取详情接口
            detail(this.id)
                .then(res => {
                let v = res.data.eventReport;
                v.attchpath = v.attchpath.split(',').filter(i => { return i })
                _this.item = v
                if(v.coordinate!="") {
                  _this.lon=v.coordinate.split(",")[0]
                  _this.lat=v.coordinate.split(",")[1]
                }
                _this.$refs.checkPointsTypeId.setValWithChange(v.checkPointsTypeId);
                _this.$refs.checkPointsId.setValWithChange(v.checkPointsId, v.checkPointsTypeId);
                _this.$refs.checkItemId.setValWithChange(v.checkItemId, v.checkPointsTypeId);
                _this.$refs.deptIds.setValWithChange((v.deptIds || "").split(","));
                _this.$refs.assessCycleId.setValWithChange(v.cycleId);
            })
                .finally(e => {
                _this.v_loadig = false;
            });
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = ["content"];
            arr.forEach(k => {
                this.item[k] = this.item[k].trim();
            });
        },
        addNew() {
            let _this = this;
            this.useTrim();
            let upData = JSON.parse(JSON.stringify(this.item))
            console.log(upData)
            upData.attchpath = upData.attchpath.join(',')
            upData.coordinate = this.lon +","+ this.lat;
            this.$refs.ruleForm.validate(valid => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    eventReport(upData)
                        .then(d => {
                        _this.$successMessage("操作成功!");
                        _this.refushBack();
                    })
                        .finally(e => {
                        _this.v_loadig = false;
                    });
                }
                else {
                    return false;
                }
            });
        },
        editItem() {
            let _this = this;
            this.useTrim();
            let upData = JSON.parse(JSON.stringify(this.item))
            upData.attchpath = upData.attchpath.join(',')
            upData.coordinate = this.lon +","+ this.lat;
            this.$refs.ruleForm.validate(valid => {
                if (valid) {
                    if (this.v_loadig)
                        return;
                    this.v_loadig = true;
                    editDevice(upData)
                        .then(d => {
                        _this.$successMessage("操作成功!");
                        // _this.$emit('changeUseType', 'info')
                        _this.goBack();
                    })
                        .finally(e => {
                        _this.v_loadig = false;
                    });
                }
                else {
                    return false;
                }
            });
        }
    }
};
</script>