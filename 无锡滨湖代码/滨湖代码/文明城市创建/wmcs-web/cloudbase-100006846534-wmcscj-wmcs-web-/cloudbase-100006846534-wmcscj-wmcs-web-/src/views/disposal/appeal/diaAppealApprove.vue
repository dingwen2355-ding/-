<template>
  <!-- 通用审核弹框 -->
  <el-dialog
    :title="title"
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
              <el-form-item label="审核结果">
                <el-radio-group v-model="item.status">
                  <el-radio :label="1">通过</el-radio>
                  <el-radio :label="2">拒绝</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col v-show="item.status == 1" :span="24">
              <el-form-item label="责任单位" :required="item.status == 1" prop="newDeptIds">
                <selectDepts
                  ref="deptIds"
                  noLabel
                  allWidth="100%"
                  :disabled="false"
                  filterType="all"
                  @change="
                    v => {
                      item.newDeptIds = v['deptIds'] || '';
                    }
                  "
                />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="审核意见">
                <el-input v-model="item.reason" type="textarea" :rows="3" />
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
    title: {
      type: String,
      default: "审核"
    },
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
    let getThis = () => { return this }
    return {
      item: {
        status: 1,
        reason: "",
        newDeptIds: ""
      },
      rules: {
        newDeptIds: [
            {
                validator: (rule, value, callback) => {
                    let _this = getThis()
                    if (_this.item.status == 1) {
                        if (_this.item.newDeptIds) return callback()
                        return callback(new Error('请选择部门'))
                    } else {
                        return callback()
                    }
                },
                trigger: ['blur', 'change']
            }
        ]
      }
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.$refs.deptIds.setValWithChange(this.detail.deptIds.split(","));
    });
  },
  methods: {
    cancel() {
      this.$emit("cancel");
    },
    sure() {
        let _this = this
        this.$refs.ruleForm.validate((valid) => {
            if (valid) {
                _this.$emit("sure", JSON.parse(JSON.stringify(this.item)))
            } else {
                return false
            }
        })
    //   this.$emit("sure", JSON.parse(JSON.stringify(this.item)));
      // if (!!this.item.newDeptIds && this.item.newDeptIds != "") {
      //   this.$emit("sure", JSON.parse(JSON.stringify(this.item)));
      // }
    }
  }
};
</script>
