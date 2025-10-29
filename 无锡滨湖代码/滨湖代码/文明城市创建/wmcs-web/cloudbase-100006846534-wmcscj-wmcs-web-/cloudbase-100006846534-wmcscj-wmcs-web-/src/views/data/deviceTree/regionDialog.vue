<template>
    <!-- 通用审核弹框 -->
    <el-dialog
      title="设置视频组织所属街道/社区"
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
            size="small"
            label-position="top"
          >
            <el-row class="show-flex-r wrap" :gutter="20">
                <el-col :span="24">
                    <el-form-item label="所属街道/社区" prop="regionCode">
                        <searchRegionDept
                            ref="parentId"
                            keyName="parentId"
                            noLabel
                            useType="form"
                            allWidth="100%"
                            filterType="all"
                            @change="(v) => { item.regionCode = v['parentId'] || '' }"
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
      }
    },
    data() {
      return {
        item: {
            regionCode: "",
            regionAllName: ""
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