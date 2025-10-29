<template>
  <!-- 事件节点 -->
  <div :class="{ 'form-item-td': !onlyShow }" :style="{ width: allWidth }">
    <div v-if="!noLabel" class="label-title" :style="{ width: diyWidth }">
      <label>{{ labelName }} :</label>
    </div>
    <div class="search-wapper">
      <el-select
        v-show="!disabled"
        v-model="value"
        size="small"
        :placeholder="placName"
        :disabled="disabled"
        clearable
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <div
        v-show="disabled"
        :class="{ 'td-form-val': !onlyShow, 'only-show': onlyShow }"
      >
        {{ disableShowName }}
      </div>
    </div>
  </div>
</template>
<script>
import { edit } from "@/api/workflow/workflow";

export default {
  props: {
    labelName: { type: String, default: "事件节点" },
    noLabel: { type: Boolean, default: false },
    keyName: { type: String, default: "nodeid" },
    placName: { type: String, default: "请选择节点" },
    disabled: {
      type: Boolean,
      default: false
    },
    diyWidth: {
      type: String,
      default: "85px"
    },
    allWidth: {
      type: String,
      default: "260px"
    },
    defaultVal: {
      type: [String, Number],
      default: ""
    },
    onlyShow: {
      type: Boolean,
      default: false
    },
    taskid: {
      type: [String, Number],
      default: ""
    },
    flowinstanceid: {
      type: [String, Number],
      default: ""
    }
  },
  data() {
    return {
      options: [],
      value: this.defaultVal,
      stopEmit: false // 阻止事件传递
    };
  },
  computed: {
    disableShowName() {
      if (this.value === "") return "暂未设置";
      if (this.options.length == 0) return "";
      let item = this.options.find(i => {
        return i.value == this.value;
      });
      return (item && item.label) || "暂未设置";
    }
  },
  watch: {
    value(val) {
      console.log("查询组件选择值变化了", val);
      if (!this.stopEmit) {
        this.$emit("change", this.getSearchValue());
        this.$emit("changeNext", this.getSearchValue());
      }
    }
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      edit({
        taskId: this.taskid,
        flowinstanceid: this.flowinstanceid
      }).then(r => {
        this.options = r.data.routeList.map(item => {
          return { label: item.endnodename, value: item.endnodeid };
        });
      });
    },
    resetSearchValue() {
      this.value = "";
    },
    getSearchValue() {
      let returnVal = {};
      if (this.value !== "") returnVal[this.keyName] = this.value;
      return returnVal;
    }
  }
};
</script>
<style scoped lang="scss">
@import "./basestyle.scss";
</style>
