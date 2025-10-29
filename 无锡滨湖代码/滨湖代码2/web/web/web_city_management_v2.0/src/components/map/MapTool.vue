<template>
  <ul class="MapTool">
    <div id="edit-ctrl-group" class="edit-ctrl-group">
      <div class="edit-btn" :title="isEdit?'禁用编辑':'启用编辑'" @click="onDrawEnableControl">
        <span class="iconfont" :class="!isEdit?'icon-dankuang_beizhu':'icon-close'" />
      </div>
      <div class="edit-btn" title="画多边形" @click="onEditBtnClick('polygon')">
        <span class="iconfont icon-draw-polygon1" />
      </div>
      <div class="edit-btn" title="画矩形" @click="onEditBtnClick('rectangle')">
        <span class="iconfont icon-draw-square" />
      </div>
      <div class="edit-btn" title="画三角形" @click="onEditBtnClick('triangle')">
        <span class="iconfont icon-Triangle" />
      </div>
      <div class="edit-btn" title="画圆" @click="onEditBtnClick('circle')">
        <span class="iconfont icon-draw-circle" />
      </div>
      <div class="edit-btn" title="删除所选" @click="onEditBtnClick('trash')">
        <span class="iconfont icon-tool-delete" />
      </div>
    </div>
  </ul>
</template>

<script>
import ZnvMap from '@/components/map/znvMap'
export default {
  data() {
    return {
      isEdit: false
    }
  },
  methods: {
    onEditBtnClick(type) {
      if (this.isEdit) {
        if (type === 'trash') {
          // ZnvMap.hideAllMarkers()
        }
        ZnvMap.draw(type)
      }
    },
    onDrawEnableControl() {
      this.isEdit = !this.isEdit
      ZnvMap.enableOrDisableDraw(this.isEdit)
    }
  }
}
</script>

<style lang="scss">
.MapTool {
  position: absolute;
  right: 550rem;
  top: 448rem;
  z-index: 99;

  .edit-btn {
    width: 120rem;
    height: 120rem;
    line-height: 120rem;
    font-size: 48rem;
    vertical-align: middle;
    text-align: center;
    cursor: pointer;
    color: RGBA(137, 195, 231, 1);
    background-color: RGBA(0, 71, 182, 1);
    .iconfont {
      font-size: 48rem;
    }
  }

  .edit-btn:hover {
    background-color: #efefef;
  }
}
</style>
