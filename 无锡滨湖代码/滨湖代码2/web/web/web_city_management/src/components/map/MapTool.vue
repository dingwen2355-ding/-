<template>
  <ul class="MapTool">
    <div id="edit-ctrl-group" class="edit-ctrl-group">
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
    return {}
  },
  methods: {
    onEditBtnClick(type) {
      ZnvMap.draw(type)
    }
  }
}
</script>

<style lang="scss">
.MapTool {
  position: absolute;
  right: 2000rem;
  top: 200rem;

  .edit-btn {
    width: 120rem;
    height: 120rem;
    line-height: 120rem;
    font-size: 48rem;
    vertical-align: middle;
    text-align: center;
    cursor: pointer;
    color: RGBA(137, 195, 231, 1);
    background-color: RGBA(5, 88, 152, 1);
    .iconfont{
      font-size: 48rem;
    }
  }

  .edit-btn:hover {
    background-color: #efefef;
  }
}
</style>
