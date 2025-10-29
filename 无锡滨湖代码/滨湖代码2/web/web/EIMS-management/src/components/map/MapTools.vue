<template>
  <div class="map-tools">
    <ul>
      <!-- <li @click="changeMap()">
        <i class="iconfont icon-weixingditu" title="底图切换"></i>
      </li> -->
      <li @click="zoomIn()">
        <i class="iconfont icon-fangda" title="放大"></i>
      </li>
      <li @click="zoomOut()">
        <i class="iconfont icon-suoxiao" title="缩小"></i>
      </li>
      <!-- <li @click="drawLine()">
        <i class="iconfont icon-huaxian" title="画线"></i>
      </li>
      <li @click="drawCircle()">
        <i class="iconfont icon-huayuan" title="圈选"></i>
      </li>
      <li @click="drawPolygon()">
        <i class="iconfont icon-huaduobianxing" title="框选"></i>
      </li>
      <li @click="clearDraw()">
        <i class="iconfont icon-qingchu" title="清空"></i>
      </li> -->
    </ul>
  </div>
</template>

<script>
import ZnvMap from './znvMap'
export default {
  data () {
    return {
      isNormalMap: true // 是普通底图
    }
  },
  methods: {
    changeMap () {
      this.isNormalMap = !this.isNormalMap
      if (this.isNormalMap) {
        ZnvMap.changeMap('ditu')
      } else {
        ZnvMap.changeMap('yixiangditu')
      }
    },
    zoomIn () {
      ZnvMap.zoomIn()
    },
    zoomOut () {
      ZnvMap.zoomOut()
    },
    drawLine () {
      ZnvMap.drawLine()
    },
    drawCircle () {
      ZnvMap.drawCircle()
    },
    drawPolygon () {
      ZnvMap.drawPolygon()
    },
    clearDraw () {
      ZnvMap.clearDraw()
    }
  }
}
</script>

<style lang="scss">
.map-tools {
  position: absolute;
  left: 60px;
  top: 31px;
  color: #fff;
  text-align: center;
  z-index: 2;
  ul {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    li {
      width: 38px;
      height: 38px;
      background: rgba(7, 15, 46, 0.8);
      border: 1px solid #00ffff;
      line-height: 38px;
      cursor: pointer;
      margin-top: 15px;
      .iconfont {
        font-size: 22px;
        color: #fff;
      }
    }
  }
}
</style>
