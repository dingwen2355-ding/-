<template>
  <div class="el-collapse-item"
    :class="{'is-active': isActive, 'is-disabled': disabled }">
    <div
      role="tab"
      :aria-expanded="isActive"
      :aria-controls="`el-collapse-content-${id}`"
      :aria-describedby ="`el-collapse-content-${id}`"
    >
      <div
        class="el-collapse-item__header"
        @click="handleHeaderClick"
        role="button"
        :id="`el-collapse-head-${id}`"
        :tabindex="disabled ? undefined : 0"
        @keyup.space.enter.stop="handleEnterClick"
        :class="{
          'focusing': focusing,
          'is-active': isActive
        }"
        @focus="handleFocus"
        @blur="focusing = false"
      >
        <div class="left-content">
          <i
              class="el-collapse-item__arrow el-icon-caret-right"
              :class="{'is-active': isActive}">
          </i>
          <slot name="title">{{title}}</slot>
        </div>
        <div class="right-content">
          <slot name="header"></slot>
        </div>
      </div>
    </div>
    <el-collapse-transition>
      <div
        class="el-collapse-item__wrap"
        v-show="isActive"
        role="tabpanel"
        :aria-hidden="!isActive"
        :aria-labelledby="`el-collapse-head-${id}`"
        :id="`el-collapse-content-${id}`"
      >
        <div class="el-collapse-item__content">
          <slot name="main"></slot>
        </div>
      </div>
    </el-collapse-transition>
  </div>
</template>
<script>
import ElCollapseTransition from 'element-ui/src/transitions/collapse-transition'
import Emitter from 'element-ui/src/mixins/emitter'
import { generateId } from 'element-ui/src/utils/util'

export default {
  name: 'ElCollapseItem',

  componentName: 'ElCollapseItem',

  mixins: [Emitter],

  components: { ElCollapseTransition },

  data() {
    return {
      contentWrapStyle: {
        height: 'auto',
        display: 'block'
      },
      contentHeight: 0,
      focusing: false,
      isClick: false,
      id: generateId()
    }
  },

  inject: ['collapse'],

  props: {
    title: String,
    name: {
      type: [String, Number],
      default() {
        return this._uid
      }
    },
    disabled: Boolean
  },

  computed: {
    isActive() {
      return this.collapse.activeNames.indexOf(this.name) > -1
    }
  },

  methods: {
    handleFocus() {
      setTimeout(() => {
        if (!this.isClick) {
          this.focusing = true
        } else {
          this.isClick = false
        }
      }, 50)
    },
    handleHeaderClick() {
      if (this.disabled) return
      this.dispatch('ElCollapse', 'item-click', this)
      this.focusing = false
      this.isClick = true
    },
    handleEnterClick() {
      this.dispatch('ElCollapse', 'item-click', this)
    }
  }
}
</script>
<style lang="scss" scoped>
.el-collapse-item{
  background: #FFFFFF;
  border-left: 1px solid #E9EDF7;
  border-right: 1px solid #E9EDF7;
}
.el-icon-caret-right{
  font-size: 15px;
  color: #7F8892;
}
::v-deep .el-collapse-item__header{
  display: flex !important;
  flex-direction: row !important;
  flex-wrap: nowrap !important;
  justify-content: space-between !important;
  background: transparent;
  .left-content{
    .el-collapse-item__arrow{
      margin: 0px;
      margin-right: 14px;
    }
  }
  .right-content{
    display: flex;
    flex-wrap: nowrap;
    align-items: center;
    margin-right: 100px;
  }
}
</style>
