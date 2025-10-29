<template>
  <!-- 责任单位 -->
  <div :class="{ 'form-item-td': !onlyShow }" :style="{ width: allWidth }">
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
        :props="casProps"
        :disabled="disabled"
        clearable
        collapse-tags
      >
      </el-cascader>
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
import { getDeptList } from "@/api/search";

export default {
  props: {
    labelName: { type: String, default: "责任单位" },
    noLabel: { type: Boolean, default: false },
    keyName: { type: String, default: "deptIds" },
    placName: { type: String, default: "请选择责任单位" },
    disabled: {
      type: Boolean,
      default: true
    },
    diyWidth: {
      type: String,
      default: "85px"
    },
    allWidth: {
      type: String,
      default: "260px"
    },
    onlyShow: {
      type: Boolean,
      default: false
    },
    deepNum: {
        // 展示层级深度
        type: Number,
        default: 100
    }
  },
  data() {
    return {
      options: [],
      casData: [],
      value: [],
      casProps: {
        checkStrictly: true,
        value: "id",
        multiple: true,
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
      let labelNames = this.$refs.selectlabel.getCheckedNodes();
      let labels = [];
      labelNames.forEach(item => {
        labels.push(item.label);
      });
      return labels.join(",");
    }
  },
  watch: {
    value(val) {
      if (!this.stopEmit) {
        this.$emit("change", this.getSearchValue());
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
        let _this = this
      getDeptList({}).then(res => {
        // 按照过滤模式先做一次过滤
        let oV = JSON.parse(JSON.stringify(res.data.records))
        _this.$del_key(oV, "children")
        // 按照层级深度展示
        function writeDeepNum(arr, num) {
            // 层级深度打标签
            arr.forEach(i => {
                if (i.children && i.children.length > 0 && num < _this.deepNum) {
                    writeDeepNum(i.children, num + 1)
                } else {
                    delete i.children
                }
            })
        }
        writeDeepNum(oV, 1)
        _this.casData = oV;
      });
    },
    setValWithChange(v_origin) {
        // 默认多选,传进来的是数组
        let v = v_origin.filter(a => { return a }).map(i => { return [i] })
        let _this = this;
        // 用这种方式调用初始化, 传递事件出去,改变外面的值
        if (this.casData.length == 0) {
            setTimeout(() => {
            _this.setValWithChange(v_origin);
            }, 100);
        } else {
            console.log("初始化成功");
            let varr = v.map(i => { return _this.getDeepVal(i[0]) })
            _this.value = varr
            // this.value = !v ? [] : v
        }
    },
    setValWithoutChange(v_origin) {
        let v = v_origin.filter(a => { return a }).map(i => { return [i] })
      let _this = this;
      // 用这种方式调用初始化, 不传递事件出去
      this.stopEmit = true;
      if (this.casData.length == 0) {
        setTimeout(() => {
          _this.setValWithoutChange(v_origin);
        }, 100);
      } else {
        console.log("初始化成功");
        let varr = v.map(i => { return _this.getDeepVal(i[0]) })
        _this.value = varr
        // this.value = !v ? [] : v.map(i => { return this.getDeepVal(i) })
        // this.value = !v ? [] : v 
        this.$nextTick(() => {
          this.stopEmit = false;
        });
      }
    },
    // setValByName(namearr) {
    //     let _this = this
    //     let arr = []
    //     namearr.forEach(i => {
    //         if (!i) return
    //         let id = _this.getDeepValByName(i)
    //         arr.push(id)
    //     })
    //     if (add) {
    //         let nowArr = JSON.parse(JSON.stringify(this.value))
    //         let nowLastId = nowArr.map(i => { return i[i.length - 1] })
    //         arr.forEach(j => {
    //             if (nowLastId.indexOf(j[j.length - 1]) < 0 ) {
    //                 nowArr.push(j)
    //             }
    //         })
    //         this.value = nowArr
    //     } else {
    //         this.value = arr
    //     }
    // },
    setValByAll(ids_origin, namearr) {
        let ids = ids_origin.map(i => { return [i] })
        // 默认多选,传进来的是数组
        let _this = this;
        // 用这种方式调用初始化, 传递事件出去,改变外面的值
        if (this.casData.length == 0) {
            setTimeout(() => {
            _this.setValByAll(v)
            }, 100);
        } else {
            console.log("初始化成功");
            let arr = []
            namearr.forEach(i => {
                if (!i) return
                let id = _this.getDeepValByName(i)
                arr.push(id)
            })
            let nowArr = JSON.parse(JSON.stringify(ids))
            let nowLastId = nowArr.map(i => { return i[i.length - 1] })
            arr.forEach(j => {
                if (nowLastId.indexOf(j[j.length - 1]) < 0 ) {
                    nowArr.push(j)
                }
            })
            this.value = nowArr
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
    getDeepValByName(v) {
      // 递归函数
      function getId(arr, v) {
        for (let i = 0; i < arr.length; i++) {
          let item = arr[i];
          if (item.name === v) {
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
      if (this.value.length > 0) {
        // 这里不能和其他组件一样类似disabled一样取值,原因是value是先给的值,但是selectlabel此时还在更新数据,所以树结构里没有这个值
        let rv = this.value.map(i => { return i[i.length - 1] })
        returnVal[this.keyName] = rv.join(",")
      }
      return returnVal;
    }
  }
};
</script>
<style scoped lang="scss">
@import "./basestyle.scss";
</style>
