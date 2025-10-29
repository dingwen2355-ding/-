<template>
  <div class="panel-tab__content">
    <div class="element-property input-property">
      <div class="element-property__label">元素文档：</div>
      <div class="element-property__value">
        <el-input
          type="textarea"
          v-model="documentation"
          size="mini"
          resize="vertical"
          :autosize="{ minRows: 2, maxRows: 4 }"
          @input="updateDocumentation"
          @blur="updateDocumentation"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ElementOtherConfig',
  props: {
    id: String
  },
  data() {
    return {
      documentation: ''
    }
  },
  watch: {
    id: {
      immediate: true,
      handler: function(id) {
        if (id && id.length) {
          this.$nextTick(() => {
            const documentations = window.bpmnInstances.bpmnElement.businessObject?.documentation
            this.documentation = documentations && documentations.length ? documentations[0].text : ''
          })
        } else {
          this.documentation = ''
        }
      }
    }
  },
  methods: {
    updateDocumentation() {
      //拿到当前的元素
      (this.bpmnElement && this.bpmnElement.id === this.id) ||
        (this.bpmnElement = window.bpmnInstances.elementRegistry.get(this.id))
        console.log(this.bpmnElement,'bpmnElementbpmnElement2');

        //创建一个Documentation元素
      const documentation = window.bpmnInstances.bpmnFactory.create('bpmn:Documentation', { text: this.documentation })

      //更新元素的属性
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, {
        documentation: [documentation],
        aaa: 'aaaa'
      })
      // console.log(window.bpmnInstances.bpmnFactory.__proto__.create, 'this.bpmnElement')
      // bar.apply(fn)
      // console.log(window.bpmnInstances.bpmnFactory._model.getType)
      console.log(window.bpmnInstances.elementFactory,111111)
    }
  },
  beforeDestroy() {
    this.bpmnElement = null
  }
}
</script>
