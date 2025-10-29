<template>
  <div v-if="dialogFormVisible">
    <el-dialog
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
      :title="isCode === 1 ? '预警处置(已处置)' : '预警处置'"
      :visible.sync="dialogFormVisible"
    >
      <el-form class="addForm" ref="form" :model="formInfo" label-width="120px" :disabled="isCode === 1">
        <el-form-item label="预警标题" prop="title" class="whole-line">
          <el-input disabled v-model="formInfo.warningTitle" placeholder="请输入预警标题"></el-input>
        </el-form-item>
        <el-form-item label="预警内容" prop="content" class="whole-line">
          <el-input
            disabled
            v-model="formInfo.warningContent"
            type="textarea"
            :rows="5"
            placeholder="请输入预警内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="防御建议" prop="content" class="whole-line">
          <el-input
            disabled
            v-model="formInfo.defenseAdvice"
            type="textarea"
            :rows="5"
            placeholder="请输入防御建议"
          ></el-input>
        </el-form-item>
        <!--  -->
        <el-form-item label="处置结果" prop="content" class="whole-line">
          <el-radio-group v-model="formInfo.deal">
            <el-radio label="1">有效</el-radio>
            <el-radio label="3">无效</el-radio>
          </el-radio-group>
          <!-- <el-input
            :disabled="formInfo.deal === '1' ? true : false"
            v-model="formInfo.dealContent"
            type="textarea"
            :rows="5"
            placeholder="请输入处置结果"
          ></el-input> -->
        </el-form-item>
        <el-form-item label="处置意见" prop="content" class="whole-line">
          <el-input

            v-model="formInfo.dealContent"
            type="textarea"
            :rows="5"
            placeholder="请输入处置意见"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button v-if="isCode != 1" type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Axios from 'axios'
export default {
  props: ['dialogFormVisible', 'formInfo', 'isCode'],
  data() {
    return {}
  },
  mounted() {
    console.log("formInfo", this.formInfo);
  },
  methods: {
    handleClose() {
      this.$emit('update:dialogFormVisible', false)
    },
    submit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let url = `/gtw/EIMS-management/commandWarning/dealWarning?id=${this.formInfo.id}&content=${this.formInfo.dealContent}
                    &title=${this.formInfo.warningTitle}&userId=${localStorage.getItem('userId')}&deal=${this.formInfo.deal}`
          Axios.get(url).then((r) => {
            if (r.data.code === 200) {
              this.$emit('success')
            } else {
              this.$message(r.data.message)
            }
          })
        }
      })
    }
  }
}
</script>

<style>
</style>
