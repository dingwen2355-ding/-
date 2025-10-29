<template>
    <!-- 通用审核弹框 -->
    <el-dialog
      title="工单派发"
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
                <el-form-item label="责任单位" prop="newDeptIds">
                  <selectDepts
                    ref="deptIds"
                    noLabel
                    allWidth="100%"
                    :disabled="false"
                    filterType="all"
                    :deepNum="2"
                    @change="
                      v => {
                        item.newDeptIds = v['deptIds'] || '';
                      }
                    "
                  />
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
        item: {
          newDeptIds: ""
        },
        rules: {
          newDeptIds: [
            {
              required: true,
              message: "请选择责任单位",
              trigger: ["blur", "change"]
            }
          ]
        }
      };
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
      }
    }
  };
  </script>
  