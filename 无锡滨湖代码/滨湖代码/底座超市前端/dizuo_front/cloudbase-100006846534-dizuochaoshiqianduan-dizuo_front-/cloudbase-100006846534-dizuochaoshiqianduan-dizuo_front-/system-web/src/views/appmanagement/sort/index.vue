<template>
  <!-- 应用分类 -->
  <div class="page">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="应用分类" name="app_module">
        <div class="box">
          <div class="box-item" v-for="(item, idx) in dict.type.app_module">
            <div class="box-item-top _size">
              {{ item.raw.dictLabel }}
              <div class="item-top-icon" :class="'bg' + item.raw.dictValue"></div>
            </div>
            <div class="box-item-top" v-if="item.raw.dictLabel != '业务系统'">
              <div class="">创建时间:{{ item.raw.createTime }}</div>
              <!-- <el-button type="primary" size="mini" plain @click="fEdit(item)">编辑</el-button> -->
            </div>
            <div class="box-item-top" v-if="item.raw.dictLabel == '业务系统'">
              <div class="">创建时间:{{ item.raw.createTime }}</div>
              <!-- <el-button type="primary" size="mini" plain @click="fEdit(item)">新增</el-button> -->
            </div>
            <div class="box-item-top">
              <div class="">更新时间:{{ item.raw.updateTime || '--' }}</div>
              <!-- <el-button type="danger" size="mini" plain @click="fDel(item)">删除</el-button> -->
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="应用来源" name="app_source">
        <div class="box">
          <div class="box-item" v-for="(item, idx) in dict.type.app_source">
            <div class="box-item-top _size">
              {{ item.raw.dictLabel }}
              <div class="item-top-icon" :class="'bg' + item.raw.dictValue"></div>
            </div>
            <div class="box-item-top">
              <div class="">创建时间:{{ item.raw.createTime }}</div>
              <!-- <el-button type="primary" size="mini" plain @click="fEdit(item)">编辑</el-button> -->
            </div>
            <div class="box-item-top">
              <div class="">更新时间:{{ item.raw.updateTime || '--' }}</div>
              <!-- <el-button type="danger" size="mini" plain @click="fDel(item)">删除</el-button> -->
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="场景分类" name="app_type">
        <div class="box">
          <div class="box-item" v-for="(item, idx) in dict.type.app_type">
            <div class="box-item-top _size">
              {{ item.raw.dictLabel }}
              <div class="item-top-icon" :class="'bg' + item.raw.dictValue"></div>
            </div>
            <div class="box-item-top">
              <div class="">创建时间:{{ item.raw.createTime }}</div>
              <!-- <el-button type="primary" size="mini" plain @click="fEdit(item)">编辑</el-button> -->
            </div>
            <div class="box-item-top">
              <div class="">更新时间:{{ item.raw.updateTime || '--' }}</div>
              <!-- <el-button type="danger" size="mini" plain @click="fDel(item)">删除</el-button> -->
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-dialog :title="'修改'" :visible.sync="bDialogDetails" width="50%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字典类型">
          <el-input v-model="form.dictType" :disabled="true" />
        </el-form-item>
        <el-form-item label="数据标签" prop="dictLabel">
          <el-input v-model="form.dictLabel" placeholder="请输入数据标签" />
        </el-form-item>
        <el-form-item label="数据键值" prop="dictValue">
          <el-input v-model="form.dictValue" disabled placeholder="请输入数据键值" />
        </el-form-item>
        <el-form-item label="样式属性" prop="cssClass">
          <el-input v-model="form.cssClass" placeholder="请输入样式属性" />
        </el-form-item>
        <el-form-item label="显示排序" prop="dictSort">
          <el-input-number v-model="form.dictSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="回显样式" prop="listClass">
          <el-select v-model="form.listClass">
            <el-option v-for="item in listClassOptions" :key="item.value" :label="item.label + '(' + item.value + ')'"
              :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">{{ dict.label
            }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  dictEdit,// 修改
  dictDel,//删除
} from "@/api/management/management";
import { listData, getData, delData, addData, updateData } from "@/api/system/dict/data";


export default {
  name: '',
  dicts: ['app_module', 'sys_normal_disable', 'app_source', 'app_type'],
  components: {},
  props: [''],
  data() {
    return {
      activeName: 'app_module',
      bDialogDetails: false,
      form: {},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // dictLabel: [
        //   { required: true, message: "数据标签不能为空", trigger: "blur" }
        // ],
        // dictValue: [
        //   { required: true, message: "数据键值不能为空", trigger: "blur" }
        // ],
        // dictSort: [
        //   { required: true, message: "数据顺序不能为空", trigger: "blur" }
        // ]
      },
      // 数据标签回显样式
      listClassOptions: [
        {
          value: "default",
          label: "默认"
        },
        {
          value: "primary",
          label: "主要"
        },
        {
          value: "success",
          label: "成功"
        },
        {
          value: "info",
          label: "信息"
        },
        {
          value: "warning",
          label: "警告"
        },
        {
          value: "danger",
          label: "危险"
        }
      ],
    }
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() {
    console.log(this.dict.type.app_module)
  },
  beforeDestroy() {

  },
  methods: {
    // 修改接口
    fEdit(data) {
      this.form = data.raw;
      this.bDialogDetails = true;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dictCode != undefined) {
            dictEdit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.bDialogDetails = false;
            });
          } else {
            addData(this.form).then(response => {
              // this.$store.dispatch('dict/removeDict', this.queryParams.dictType);
              this.$modal.msgSuccess("新增成功");
              this.bDialogDetails = false;
            });
          }
        }
      });
    },
    // 取消按钮
    cancel() {
      this.bDialogDetails = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        dictCode: undefined,
        dictLabel: undefined,
        dictValue: undefined,
        cssClass: undefined,
        listClass: 'default',
        dictSort: 0,
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
    },
    // 删除
    fDel(data) {
      this.$confirm('您确定要删除吗, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        dictDel({ id: data.raw.dictCode }).then(res => {
          this.$modal.msgSuccess("删除成功");
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    handleClick() {

    },
  }
}
</script>
<style lang='scss' scoped>
.page {
  box-sizing: border-box;
  padding: 20px;
  width: 100%;
  height: calc(100vh - 84px);
}

.box {
  display: flex;
  align-items: center;
  // justify-content: space-around;
  flex-wrap: wrap;
  width: 100%;
  // height: 200px;
}

.box-item {
  width: 31%;
  height: 200px;
  margin: 0 1% 20px;
  padding: 20px;
  box-sizing: border-box;
  background: #d8d8d8;
}

.box-item-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 10px;
}

._size {
  color: #000;
  font-size: 30px;
  font-weight: bold;
}

.item-top-icon {
  width: 60px;
  height: 60px;
  // border-radius: 50%;  
  overflow: hidden;
  background: orange;
}

.bg1669301825683091458 {
  background: url('../../../assets/icon/fenlei/moudle/gjzc.png') no-repeat center / 60px 60px;
}

.bg1669301899511230465 {
  background: url('../../../assets/icon/fenlei/moudle/zjfw.png') no-repeat center / 60px 60px;
}

.bg1669301968486559746 {
  background: url('../../../assets/icon/fenlei/moudle/ptnl.png') no-repeat center / 60px 60px;
}
.bg35 {
  background: url('../../../assets/icon/fenlei/moudle/gjzc.png') no-repeat center / 60px 60px;
}


.bg3 {
  background: url('../../../assets/icon/fenlei/source/dsfqy.png') no-repeat center / 60px 60px;
}

.bg2 {
  background: url('../../../assets/icon/fenlei/source/other.png') no-repeat center / 60px 60px;
}

.bg1 {
  background: url('../../../assets/icon/fenlei/source/cyzx.png') no-repeat center / 60px 60px;
}

.bg001 {
  background: url('../../../assets/icon/fenlei/type/szzw.png') no-repeat center / 60px 60px;
}

.bg002 {
  background: url('../../../assets/icon/fenlei/type/szsh.png') no-repeat center / 60px 60px;
}

.bg003 {
  background: url('../../../assets/icon/fenlei/type/szzl.png') no-repeat center / 60px 60px;
}

.bg004 {
  background: url('../../../assets/icon/fenlei/type/szsh.png') no-repeat center / 60px 60px;
}

.bg005 {
  background: url('../../../assets/icon/fenlei/type/szjjian.png') no-repeat center / 60px 60px;
}

.bg006 {
  background: url('../../../assets/icon/fenlei/type/szjji.png') no-repeat center / 60px 60px;
}
</style>