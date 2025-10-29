<template>
  <div class="panel-tab__content">
    <el-form size="mini" label-width="90px" @submit.native.prevent>
      <el-form-item label="ID">
        <el-input v-model="elementBaseInfo.id" :disabled="idEditDisabled || elementBaseInfo.$type === 'bpmn:Process'"
          clearable @change="updateBaseInfo('id')" />
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="elementBaseInfo.name" clearable @change="updateBaseInfo('name')" />
      </el-form-item>
      <el-form-item label="事件类型" v-if="elementBaseInfo.$type === 'bpmn:BoundaryEvent'">
        <el-select v-model="timeEvent" placeholder="请选择" @change="addbusinessObject">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="时间" v-if="elementBaseInfo.$type === 'bpmn:BoundaryEvent'">
        <el-input v-model="timeInput" clearable @change="timeChange" />
      </el-form-item>
      <!--流程的基础属性-->
      <template v-if="elementBaseInfo.$type === 'bpmn:Process'">
        <el-form-item label="版本标签">
          <el-input v-model="elementBaseInfo.versionTag" clearable @change="updateBaseInfo('versionTag')" />
        </el-form-item>
        <el-form-item label="可执行">
          <el-switch v-model="elementBaseInfo.isExecutable" active-text="是" inactive-text="否"
            @change="updateBaseInfo('isExecutable')" />
        </el-form-item>
      </template>
    </el-form>
  </div>
</template>
<script>
export default {
  name: 'ElementBaseInfo',
  props: {
    businessObject: Object,
    type: String,
    idEditDisabled: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      bpmnElement: null,
      timeEvent: null,
      timeId: null,
      timeInput: null,

      options: [
        {
          value: 'timeDate',
          label: 'timeDate'
        },
        {
          value: 'timeDuration',
          label: 'timeDuration'
        },
        {
          value: 'timeCycle',
          label: 'timeCycle'
        }
      ],
      elementBaseInfo: {}
    }
  },
  created() {
    console.log(this.$store.state, 'vuex')
    this.$store.state['bpmn'] = {}
    this.$store.state['bpmn']['isPropoters'] = true
  },
  watch: {
    businessObject: {
      immediate: false,
      handler: function (val) {
        if (val) {
          this.$nextTick(() => this.resetBaseInfo())
        }
      }
    }
  },
  methods: {
    updateDocumentation(timeId, timeTypes) {
      //拿到当前的元素
      var _modeler = this.$store.state.bpmn.modeler
      console.log(timeId, timeTypes, this.timeInput, 1111)
      const elementRegistry = _modeler.get('elementRegistry')
      this.bpmnElement = elementRegistry.get(timeId)
      // //创建一个Documentation元素


      let eventDefinition = this.bpmnElement.businessObject.eventDefinitions[0]
      const newEventDefinition = window.bpmnInstances.moddle.create('bpmn:TimerEventDefinition', {
        id: eventDefinition.id
      })
      const expression = window.bpmnInstances.bpmnFactory.create("bpmn:Expression", {
        body: this.timeInput
      });
      expression.$parent = newEventDefinition;

      newEventDefinition[timeTypes] = expression;

      this.bpmnElement.businessObject.eventDefinitions = [newEventDefinition]

      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, {
      })

    },

    //时间输入框改变时促发
    timeChange(e) {
      this.updateDocumentation(this.timeId, this.timeEvent)
    },
    addbusinessObject(e) {
      console.log(e, 'eeeeeee')
      this.updateDocumentation(this.timeId, e)
    },
    resetBaseInfo() {
      this.bpmnElement = window?.bpmnInstances?.bpmnElement
      this.elementBaseInfo = JSON.parse(JSON.stringify(this.bpmnElement.businessObject))
      console.log(this.elementBaseInfo, 'elementBaseInfo1')
      //
      this.$store.state.bpmn.elementBusinessObject = JSON.parse(JSON.stringify(this.elementBaseInfo))
      this.timeId = null
      this.timeEvent = null
      this.timeInput = null
      //这里判断是不是边界事件   是的话赋值id
      if (this.elementBaseInfo.eventDefinitions) {
        // this.timeId = this.elementBaseInfo.eventDefinitions[0].id
        this.timeId = this.elementBaseInfo.id
        if (this.elementBaseInfo.eventDefinitions[0].timeDate) {
          this.timeEvent = 'timeDate'
        }
        if (this.elementBaseInfo.eventDefinitions[0].timeDuration) {
          this.timeEvent = 'timeDuration'
        }
        if (this.elementBaseInfo.eventDefinitions[0].timeCycle) {
          this.timeEvent = 'timeCycle'
        }
        this.timeInput = this.elementBaseInfo.eventDefinitions[0][this.timeEvent].body
      }
      if (this.elementBaseInfo.$type == 'bpmn:SequenceFlow') {
        this.$store.state.bpmn.isPropoters = 'hide'
      } else {
        this.$store.state.bpmn.isPropoters = 'show'
      }
    },
    updateBaseInfo(key) {
      const attrObj = Object.create(null)
      attrObj[key] = this.elementBaseInfo[key]
      if (key === 'id') {
        window.bpmnInstances.modeling.updateProperties(this.bpmnElement, {
          id: this.elementBaseInfo[key],
          di: { id: `${this.elementBaseInfo[key]}_di` }
        })
      } else {
        window.bpmnInstances.modeling.updateProperties(this.bpmnElement, attrObj)
      }
    }
  },
  beforeDestroy() {
    this.bpmnElement = null
  }
}
</script>
