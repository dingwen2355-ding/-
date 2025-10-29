<template>
  <div class="panel-tab__content">
    <el-table :data="elementPropertyList" size="mini" max-height="240" border fit>
      <el-table-column label="序号" width="50px" type="index" />
      <el-table-column label="属性名" prop="name" min-width="100px" show-overflow-tooltip />
      <el-table-column label="属性值" prop="value" min-width="100px" show-overflow-tooltip />
      <el-table-column label="操作" width="90px">
        <template slot-scope="{ row, $index }">
          <el-button size="mini" type="text" @click="openAttributesForm(row, $index)">编辑</el-button>
          <el-divider direction="vertical" />
          <el-button size="mini" type="text" style="color: #ff4d4f" @click="removeAttributes(row, $index)"
            >移除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="element-drawer__button">
      <el-button size="mini" type="primary" icon="el-icon-plus" @click="openAttributesForm(null, -1)"
        >添加属性</el-button
      >
    </div>
    <el-dialog :visible.sync="propertyFormModelVisible" title="属性配置" width="600px" append-to-body destroy-on-close>
      <el-form :model="propertyForm" label-width="80px" size="mini" ref="attributeFormRef" @submit.native.prevent>
        <el-form-item label="属性名：" prop="name">
          <el-input v-model="propertyForm.name" :disabled="$store.state.bpmn.isPropoters == 'hide'" />
        </el-form-item>
        <el-form-item label="属性值：" prop="value">
          <el-input v-if="$store.state.bpmn.isPropoters == 'show'" v-model="propertyForm.value" clearable />
          <el-select v-else v-model="formKey" @change="updateElementFormKey">
            <el-option
              v-for="i in $store.state.bpmn.formObj"
              :key="i.dataId"
              :value="i.dataId"
              :label="i.formName + '|' + i.formCode"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button size="mini" @click="propertyFormModelVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveAttribute">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
import { MessageBox } from 'element-ui'
export default {
  name: 'ElementProperties',
  props: {
    id: String,
    type: String
  },
  inject: {
    prefix: 'prefix',
    width: 'width'
  },
  data() {
    return {
      protryName: null, //属性名
      formKey: null,
      elementPropertyList: [],
      propertyForm: {},
      editingPropertyIndex: -1,
      propertyFormModelVisible: false,
      url: '/v1/ActivitiDesignController/getDesignList'
    }
  },
  watch: {
    id: {
      immediate: true,
      handler(val) {
        val && val.length && this.resetAttributesList()
      }
    }
  },
  methods: {
    //点击连线的属性名
    updateElementFormKey(e) {
      console.log(e, 'eeee')
      this.$store.state.bpmn.formObj.forEach(i => {
        if (i.dataId == e) {
          this.propertyForm.name = this.protryName = i.formName
        }
      })
    },

    resetAttributesList() {
      this.bpmnElement = window.bpmnInstances.bpmnElement
      this.otherExtensionList = [] // 其他扩展配置
      this.bpmnElementProperties =
        this.bpmnElement.businessObject?.extensionElements?.values?.filter(ex => {
          if (ex.$type !== `${this.prefix}:Properties`) {
            this.otherExtensionList.push(ex)
          }
          return ex.$type === `${this.prefix}:Properties`
        }) ?? []

      // 保存所有的 扩展属性字段
      this.bpmnElementPropertyList = this.bpmnElementProperties.reduce((pre, current) => pre.concat(current.values), [])
      // 复制 显示
      this.elementPropertyList = JSON.parse(JSON.stringify(this.bpmnElementPropertyList ?? []))
    },
    openAttributesForm(attr, index) {
      this.propertyForm.name = null
      this.protryName = null
      this.editingPropertyIndex = index
      this.propertyForm = index === -1 ? {} : JSON.parse(JSON.stringify(attr))
      this.propertyFormModelVisible = true
      this.$nextTick(() => {
        if (this.$refs['attributeFormRef']) this.$refs['attributeFormRef'].clearValidate()
      })
    },
    removeAttributes(attr, index) {
  
      console.log(this.$message.warning, 'cccc')
      MessageBox.confirm('确认移除该属性吗？', '提示', {
        confirmButtonText: '确 认',
        cancelButtonText: '取 消',
        type: 'warning'
      })
        .then(() => {
          this.elementPropertyList.splice(index, 1)
          this.bpmnElementPropertyList.splice(index, 1)
          // 新建一个属性字段的保存列表
          const propertiesObject = window.bpmnInstances.moddle.create(`${this.prefix}:Properties`, {
            values: this.bpmnElementPropertyList
          })
          this.updateElementExtensions(propertiesObject)
          this.resetAttributesList()
        })
        .catch(() => console.info('操作取消'))
    },
    saveAttribute() {
      const { name, value } = this.propertyForm
      console.log(this.bpmnElementPropertyList)
      if (this.editingPropertyIndex !== -1) {
        window.bpmnInstances.modeling.updateModdleProperties(
          this.bpmnElement,
          this.bpmnElementPropertyList[this.editingPropertyIndex],
          {
            name,
            value
          }
        )
      } else {
        // 新建属性字段
        //判断是否为连线的
        if (this.$store.state.bpmn.isPropoters == 'show') {
          var newPropertyObject = window.bpmnInstances.moddle.create(`${this.prefix}:Property`, { name, value })
        } else {
          var newPropertyObject = window.bpmnInstances.moddle.create(`${this.prefix}:Property`, {
            name,
            value: this.formKey
          })
        }
        // 新建一个属性字段的保存列表
        const propertiesObject = window.bpmnInstances.moddle.create(`${this.prefix}:Properties`, {
          values: this.bpmnElementPropertyList.concat([newPropertyObject])
        })
        this.updateElementExtensions(propertiesObject)
      }
      this.propertyFormModelVisible = false
      this.resetAttributesList()
    },
    updateElementExtensions(properties) {
      const extensions = window.bpmnInstances.moddle.create('bpmn:ExtensionElements', {
        values: this.otherExtensionList.concat([properties])
      })
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, {
        extensionElements: extensions
      })
    }
  }
}
</script>

<style scoped>
.el-select--mini {
  width: 100% !important;
}
</style>
