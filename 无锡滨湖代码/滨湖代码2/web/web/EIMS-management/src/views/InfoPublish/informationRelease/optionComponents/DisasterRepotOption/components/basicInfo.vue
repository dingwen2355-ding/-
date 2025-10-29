<template>
  <div class="basicInfo">
    <el-form :inline="true" ref="form" :rules="rules" class="storage-form" :model="form" label-width="100px">
      <el-form-item label="标题" prop="disName">
        <el-input clearable v-model="form.disName" placeholder="请输入标题" :disabled="isCode == 1"></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="typeId">
        <el-cascader
          :disabled="isCode == 1"
          filterable
          style="width: 100%"
          v-model="form.typeId"
          :options="options"
          placeholder="请选择类型"
          clearable
        ></el-cascader>
      </el-form-item>
      <el-form-item label="报送科室" prop="submitItem">
        <el-button class="searchBtn" type="primary" @click="chooseSendItem()">请点此处选择报送科室</el-button>
      </el-form-item>
      <div>
        <el-form-item label="报送人" prop="repPersonName">
          <el-input clearable v-model="form.repPersonName" :disabled="isCode == 1" placeholder="请输入报送人"></el-input>
        </el-form-item>
        <el-form-item label="报送时间" prop="repTime">
          <el-date-picker
            :disabled="isCode == 1"
            style="width: 100%"
            v-model="form.repTime"
            value-format="yyyy-MM-dd hh:mm:ss"
            type="date"
            placeholder="请选择报送时间"
          ></el-date-picker>
        </el-form-item>
      </div>
      <el-form-item label="内容" prop="remark" class="whole-line">
        <!-- <el-input clearable type="textarea" :rows="8" v-model="form.content" placeholder="请输入内容"></el-input> -->
        <auto-editor
          :disabled="isCode == 1"
          v-model="form.remark"
          :style="isCollapse ? 'width: 940px' : 'width: 800px'"
        ></auto-editor>
      </el-form-item>
    </el-form>
    <!-- 资源管理 -->
    <Source
      v-if="dialogSourceVisible"
      @cancel="dialogSourceVisible = false"
      @choosedKeys="choosedKeys"
      :dialogSourceInfo="dialogSourceInfo"
      :dialogSourceVisible="dialogSourceVisible"
      :isCode="isSourceCode"
      :checkedKeys="checkedKeys"
    />
  </div>
</template>
<script>
import axios from 'axios'
import AutoEditor from '../../../../../../components/AutoEditor/AutoEditor.vue'
import Source from '../../DisasterRepotOption/components/Source.vue'
export default {
  name: 'basicInfo',
  props: ['form', 'isCode'],
  components: { AutoEditor, Source },
  data() {
    return {
      rules: {
        disName: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        typeId: [{ required: true, message: '请选择类型', trigger: 'blur' }],
        submitItem: [{ required: true, message: '请选择报送科室', trigger: 'blur' }],
        // repPersonName: [{ required: true, message: '请输入报送人', trigger: 'blur' }],
        // repTime: [{ required: true, message: '请选择报送时间', trigger: 'blur' }],
        remark: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      },
      options: [],
      isCollapse: false,
      dialogSourceVisible: false,
      dialogSourceInfo: {
        userId: localStorage.getItem('userId')
      },
      isSourceCode: 2,
      checkedKeys: []
    }
  },
  watch: {
    '$store.getters.isCollapse': {
      handler(v) {
        this.isCollapse = v
        if (this.form.repPersonName === '') {
          this.form.repPersonName = localStorage.getItem('user')
        }
      },
      immediate: true
    }
  },
  mounted() {
    this.getTypeList()
    if (this.isCode == 1) {
      this.isSourceCode = 1
    }
    console.log('this.form.submitItem', this.form.submitItem)
    if (this.form.submitItem.length > 0) {
      this.checkedKeys = this.form.submitItem.split(',')
      console.log('this.checkedKeys', this.checkedKeys)
    }
    // console.log('this.form.submitItem', this.form.submitItem)
  },
  methods: {
    // 获取类型
    getTypeList() {
      let url = '/gtw/EIMS-management/get-mes-ype-tree'
      axios.get(url).then((res) => {
        this.options = res.data.data
      })
    },
    // 选择科室
    chooseSendItem() {
      console.log(this.form.submitItem, 'this.form.submitItem');
      console.log(this.checkedKeys, 'this.checkedKeys');
      if (this.checkedKeys.length === 0) {
        this.checkedKeys = this.form.submitItem
      }
      this.dialogSourceVisible = true
    },
    choosedKeys(ids) {
      console.log(ids, 'ids');
      this.form.submitItem = ids
      this.$emit('choosedKey', ids)
    }
  }
}
</script>
<style lang="scss">
.basicInfo {
  height: 590px;
  overflow: auto;
  overflow-x: hidden;
  .storage-form.el-form--inline {
    .el-form-item__content {
      width: calc(100% - 100px);
    }
    .whole-line {
      width: 100%;
      .el-form-item__content {
        width: calc(100% - 130px);
      }
    }
  }
}
</style>
