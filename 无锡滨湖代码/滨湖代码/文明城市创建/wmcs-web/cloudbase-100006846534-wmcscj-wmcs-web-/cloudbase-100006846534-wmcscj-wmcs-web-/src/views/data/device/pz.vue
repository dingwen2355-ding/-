<template>
    <!-- 配置 -->
    <el-dialog
      title="智能监控配置"
      :visible.sync="show"
      append-to-body
      :width="rowWidth"
      :before-close="cancel"
    >
      <div class="dia-bd-hei">
        <div class="td-form-content-wap" style="width: 98%;">
          <el-form
            ref="ruleForm"
            :model="item"
            :rules="rules"
            size="small"
            label-position="top"
          >
            <el-row class="show-flex-r wrap" :gutter="20">
              <el-col :span="24">
                <el-form-item label="通道编号" prop="channelId">
                    <div class="td-form-val">
                        {{ item.channelId || '' }}
                    </div>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="通道名称" prop="name">
                    <div class="td-form-val">
                        {{ item.name || '' }}
                    </div>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="所属街道/社区" prop="regionCode">
                  <searchRegionDept
                    ref="parentId"
                    keyName="parentId"
                    noLabel
                    useType="form"
                    allWidth="90%"
                    filterType="all"
                    :disabled="baseDisable"
                    @change="changeRegion" />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="绑定点位" prop="pointsId">
                  <selectCheckPoints
                    ref="checkPointsId"
                    noLabel
                    allWidth="90%"
                    :disabled="baseDisable"
                    @change="
                      v => {
                        item.pointsId = v['checkPointsId'] || '';
                      }
                    "
                  />
                </el-form-item>
              </el-col>
              <!-- <el-col :span="24">
                <el-form-item label="算法类型" prop="alarmType">
                    <dirtAll
                        noLabel
                        keyName="sflx"
                        useType="form"
                        allWidth="90%"
                        :defaultVal="item.alarmType"
                        dictName="work_alarm_type"
                    />
                </el-form-item>
              </el-col> -->
              <el-col :span="24">
                <el-form-item label="需求算法说明" prop="alarmTypeRemark">
                  <div v-if="!baseDisable">
                    <el-input v-model="item.alarmTypeRemark" type="textarea" :rows="3" />
                  </div>
                  <div v-else class="td-form-val">
                    {{ item.alarmTypeRemark || '' }}
                  </div>
                </el-form-item>   
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
        <t-button theme="primary" @click="sure()">确定</t-button>
      </div>
    </el-dialog>
  </template>
  <script>
  export default {
    props: {
      rowWidth: {
        // 整体宽度设置
        type: String,
        default: "700px"
      },
      show: {
        type: Boolean,
        default: false
      },
      detail: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        v_loadig: false,
        item: {}
      };
    },
    computed: {
        baseDisable() {
            // 详情禁用
            return !!(this.useType == "info");
        }
    },
    mounted() {
      this.item = JSON.parse(JSON.stringify(this.detail))
      this.$nextTick(() => {
        this.$refs.parentId.setValWithChange(this.item.regionCode)
      })
    },
    methods: {
      cancel() {
        this.$emit("cancel");
      },
      sure() {
        let _this = this
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            _this.$emit("sure", JSON.parse(JSON.stringify(_this.item)))
          } else {
            return false
          }
        })
      },
      changeRegion(data) {
        this.regionCode = data['parentId'] || ''
        // 清空点位类型:触发全自动
        this.$refs.checkPointsId.setValWithChange("", "", this.regionCode);
        this.item.regionCode = this.regionCode
      }
    }
  };
  </script>
  