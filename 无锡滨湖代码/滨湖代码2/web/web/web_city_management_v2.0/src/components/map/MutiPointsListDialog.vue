<template>
  <el-dialog
    title="点位列表"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1031rem"
    class="Line—Dialog default-dialog-class large-screen"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div class="dd-title-back">
      <div class="dd-title-back-s"></div>
    </div>
    <div class="dd-back-area top-right-area"></div>
    <div class="dd-back-angle top-left-angle"></div>
    <div class="dd-back-angle top-right-angle"></div>
    <div class="dd-back-angle bottom-left-angle"></div>
    <div class="dd-back-angle bottom-right-angle"></div>
    <div class="muti-content">
        <div class="muti-item" v-for="(item,index) in mutiPoints" :key="'mult'+index" @click="doClick(item)">
          <img :src="mapIconMap[item.properties.icon]">{{item.properties.title}}
        </div>
    </div>
  </el-dialog>
</template>

<script>
import ZnvMap from './znvMap'

export default {
  data() {
    return {
      mutiPoints: [],
      dialogVisible: false,
      mapIconMap: {
        定位: '/files/mapIcon/定位.png',
        固定视频: '/files/mapIcon/固定视频_blue_hover.png',
        公司企业: '/files/mapIcon/point_公司企业@2x.png',
        交通设施: '/files/mapIcon/point_交通设施@2x.png',
        旅游景点: '/files/mapIcon/point_旅游景点@2x.png',
        生活服务: '/files/mapIcon/point_生活服务@2x.png',
        休闲娱乐: '/files/mapIcon/point_休闲娱乐@2x.png',
        医疗保健: '/files/mapIcon/point_医疗保健@2x.png',
        单兵: '/files/mapIcon/单兵_blue_hover.png',
        救援队伍: '/files/mapIcon/救援队伍_blue_hover.png',
        避难场所: '/files/mapIcon/避难场所_blue_hover.png',
        应急物资: '/files/mapIcon/应急物资_blue_hover.png',
        专家: '/files/mapIcon/专家_blue_hover.png',
        易涝点: '/files/mapIcon/point_易涝点@2x.png',
        河道: '/files/mapIcon/point_河道@2x.png',
        湖泊: '/files/mapIcon/point_湖泊@2x.png',
        背景: '/files/mapIcon/矩形1.png'
      }
    }
  },
  watch: {
    '$store.getters.mutiPoints': {
      handler(v) {
        this.mutiPoints = v
        this.dialogVisible = true
      }
    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
    },
    doClick(item) {
      let dataInfo = {
        opts: {
          extData: JSON.parse(item.properties.extData),
          icon: item.properties.icon,
          id: item.properties.id,
          offset: JSON.parse(item.properties.offset),
          position: JSON.parse(item.properties.position),
          size: item.properties.size,
          title: item.properties.title
        }
      }
      this.dialogVisible = false
      ZnvMap.clickEvent(dataInfo)
    }
  }
}
</script>

<style lang="scss" scoped>
.Line—Dialog {
  ::v-deep .el-dialog{
    width: 1612rem !important;
    height: 2204rem !important;
  }
  .historyList{
    margin-bottom: 64rem;
  }
  ::v-deep.el-button{
    padding: 12rem 20rem;
  }
  .footer{
    height: 272rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .muti-content{
    height: 1700rem;
    overflow-y: scroll;
    .muti-item{
      height: 100rem;
      font-size: 64rem;
      cursor: pointer;
      display: flex;
      align-items: center;
      img{
        width: 60rem;
        height: 68rem;
        margin-right: 20rem;
      }

      &:hover{
        color: #00f6ff;
      }
    }
  }
}
</style>
