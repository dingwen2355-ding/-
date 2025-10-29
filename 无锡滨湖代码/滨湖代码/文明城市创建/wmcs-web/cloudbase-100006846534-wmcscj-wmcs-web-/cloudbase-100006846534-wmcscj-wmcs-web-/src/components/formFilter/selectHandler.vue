<template>
  <!-- 处置人 -->
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
        multiple
        collapse-tags
        :multiple-limit="nodeHandleType==1?1:0"
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
import { getUsersByNodeId, getDeptsByNodeId } from "@/api/workflow/workflow";

export default {
  props: {
    labelName: { type: String, default: "处置人" },
    noLabel: { type: Boolean, default: false },
    keyName: { type: String, default: "handlerid" },
    placName: { type: String, default: "请选择处置人" },
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
      type: Array,
      default: () => { return [] }
    },
    onlyShow: {
      type: Boolean,
      default: false
    },
    nodeid: {
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
      nodeHandleType: 1,
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
      }
    }
  },
  mounted() {},
  methods: {
    getData(nodeid) {
      getDeptsByNodeId({
        flowid: "1234",
        nodeid: nodeid,
        flowInstanceId: this.flowinstanceid
      }).then(r => {
        this.nodeHandleType = r.data.nodeHandleType;
        this.options = r.data.value.map(item => {
          return { label: item.name, value: item.id };
        });
      });
    },
    setValWithChange(v, nodeid) {
      // 这种结构不需要等待后台接口返回options
      // 用这种方式调用初始化, 传递事件出去,改变外面的值
      let _this = this;
      _this.value = v; // 刷新列表不影响赋值
      this.getData(nodeid);
    },
    resetSearchValue() {
      this.value = [];
    },
    getSearchValue() {
      let returnVal = {};
      console.log(this.value);
      if (this.value !== "") returnVal[this.keyName] = JSON.parse(JSON.stringify(this.value)) .join(",");
      return returnVal;
    }
  }
};
</script>
<style scoped lang="scss">
@import "./basestyle.scss";
</style>
