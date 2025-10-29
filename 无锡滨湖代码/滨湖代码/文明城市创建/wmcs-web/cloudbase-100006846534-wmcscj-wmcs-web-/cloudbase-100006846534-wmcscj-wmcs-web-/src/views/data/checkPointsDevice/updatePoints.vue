<template>
  <el-dialog
    title="修改"
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
              <el-form-item label="所属组织" prop="orgName">
                  <div class="td-form-val">
                      {{ item.orgName || '' }}
                  </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="通道编号" prop="channelId">
                  <div class="td-form-val">
                      {{ item.channelId || '' }}
                  </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="通道名称" prop="channelName">
                  <div class="td-form-val">
                      {{ item.channelName || '' }}
                  </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="经度" prop="gpsX">
                  <div class="td-form-val">
                      {{ item.gpsX || '' }}
                  </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="纬度" prop="gpsY">
                  <div class="td-form-val">
                      {{ item.gpsY || '' }}
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
            <el-col :span="24">
              <el-form-item label="需求算法说明" prop="remark">
                  <div v-if="!baseDisable">
                    <el-input v-model="item.remark" type="textarea" :rows="3" />
                  </div>
                  <div v-else class="td-form-val">
                    {{ item.remark || '' }}
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
      item: {},
      rules: {
        selectRegionCode: [
          {
            required: true,
            message: "请选择街道/社区",
            trigger: ["blur", "change"]
          }
        ]
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
    this.item = JSON.parse(JSON.stringify(this.detail))
    console.log("====设备详情")
    console.log(this.item)
    this.$nextTick(() => {
      this.$refs.parentId.setValWithChange(this.item.regionCode)
      this.$nextTick(() => {
        this.$refs.checkPointsId.setValWithChange(this.item?.pointsId || "");
      })
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
