<template>
  <!-- 事件类型选择 -->
  <div class="form-item-td" :style="{ width: allWidth }">
    <div v-if="!noLabel" class="label-title" :style="{ width: diyWidth }">
      <label>{{ labelName }} :</label>
    </div>
    <div class="search-wapper">
      <el-cascader
        v-show="!disabled"
        ref="selectlabel"
        v-model="value"
        :placeholder="placName"
        :options="casData"
        size="small"
        :props="props"
        :disabled="disabled"
        clearable
        filterable
      />
      <div v-show="disabled" class="td-form-val">
        {{ disableShowName }}
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    labelName: { type: String, default: "事件类型" },
    noLabel: { type: Boolean, default: false },
    keyName: { type: String, default: "eventType" },
    placName: { type: String, default: "请选择事件类型" },
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
    }
  },
  data() {
    return {
      api: "getEventTypeList",
      originData: [],
      casData: [], // 级联数据
      value: [],
      props: {
        checkStrictly: true,
        value: "id",
        label: "name",
        children: "children"
      },
      stopEmit: false // 阻止事件传递
    };
  },
  computed: {
    disableShowName() {
      let selectIds = this.value;
      if (this.casData.length == 0) return "暂未设置";
      if (selectIds.length == 0) return "";
      let labelNames = this.$refs.selectlabel.getCheckedNodes()[0];
      if (!labelNames) return "";
      let label = labelNames.label;
      while (labelNames) {
        labelNames = labelNames.parent;
        if (labelNames) label = labelNames.label + "/" + label;
      }
      return label || "暂未设置";
    }
  },
  watch: {
    "$store.state.searchFilter.getEventTypeList"(newVal, oldVal) {
      this.init(JSON.parse(JSON.stringify(newVal)));
    },
    value(val) {
      console.log("查询组件选择值变化了", val);
      if (!this.stopEmit) {
        this.$emit("change", this.getSearchValue());
      } else {
        console.log("这次改变不传递");
      }
    }
  },
  mounted() {
    let casData = this.$store.state.searchFilter[this.api];
    if (casData) {
      // 已经有缓存
      this.init(JSON.parse(JSON.stringify(casData)));
    } else {
      // 没有缓存则注册缓存请求
      this.$store.commit("addSearchFilter", this.api);
    }
  },
  methods: {
    init(val) {
      // 按照过滤模式先做一次过滤
      let oV = JSON.parse(JSON.stringify(val.records));
      let v = oV;
      this.originData = JSON.parse(JSON.stringify(oV));
      this.$del_key(v, "children");
      this.casData = v;
    },
    setValWithChange(v) {
      let _this = this;
      // 用这种方式调用初始化, 传递事件出去,改变外面的值
      if (this.casData.length == 0) {
        setTimeout(() => {
          _this.setValWithChange(v);
        }, 100);
      } else {
        console.log("初始化成功");
        this.value = !v ? [] : this.getDeepVal(v)
      }
    },
    setValWithoutChange(v) {
      let _this = this;
      // 用这种方式调用初始化, 不传递事件出去
      this.stopEmit = true;
      if (this.casData.length == 0) {
        setTimeout(() => {
          _this.setValWithoutChange(v);
        }, 100);
      } else {
        console.log("初始化成功");
        this.value = !v ? [] : this.getDeepVal(v)
        this.$nextTick(() => {
          this.stopEmit = false;
        });
      }
    },
    getDeepVal(v) {
      // 递归函数
      function getId(arr, v) {
        for (let i = 0; i < arr.length; i++) {
          let item = arr[i];
          if (item.id === v) {
            // 找到了,返回数组
            return [item.id];
          } else {
            // 没找到, 找子集
            if (item.children && item.children.length > 0) {
              let childrenids = getId(item.children, v);
              if (childrenids.length > 0) {
                return [item.id, ...childrenids];
              } else {
                continue;
              }
            } else {
              continue;
            }
          }
        }
        // 遍历一圈没找到
        return [];
      }
      // 用最终ID获取层级ID链路
      let v_ids = getId(this.casData, v);
      console.log("递归结果", v_ids);
      return v_ids;
    },
    resetSearchValue() {
      this.value = [];
    },
    getSearchValue() {
      let returnVal = {};
      if (this.value.length == 0) return returnVal;
      returnVal[this.keyName] = this.value;
      return returnVal;
    }
  }
};
</script>
<style scoped lang="scss">
@import "./basestyle.scss";
</style>
