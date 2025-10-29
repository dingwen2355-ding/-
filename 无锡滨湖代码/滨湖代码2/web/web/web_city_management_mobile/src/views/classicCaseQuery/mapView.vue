<template>
  <div class="znv-map">
    <div class="znv-map-top">
      <van-search
        style="border-radius: 5rem"
        input-align="center"
        shape="round"
        show-action
        placeholder="搜索地点或地址">
        <template #left>
          <van-field
            readonly
            clickable
            name="picker"
            :value="selectName"
            placeholder="类别选择"
            @click="typePickerVisible = true"/>
          <van-popup
            position="bottom"
            round
            v-model="typePickerVisible">
            <van-picker
              :columns="oprions"
              item-height="60px"
              show-toolbar
              @cancel="typePickerVisible = false"
              @confirm="handleSelect"/>
          </van-popup>
        </template>
        <template #action>
          <van-button type="info" size="mini" @click="">查询</van-button>
        </template>
      </van-search>
      <div class="znv-map-button" @click="goToCenter">
        <van-icon class="icon" name="guide-o" size="50rem"/>
      </div>
    </div>
    <div class="znv-map-bottom">
      <div class="map-container" id="map-container"></div>
    </div>
  </div>
</template>

<script>
import ZnvMap from "@/common/js/znvMap";
import mapIcon from '@/common/mapIcon.js';
import dataHidden from '@/static/mock/hiddenRisk.json'
import dataMaterial from '@/static/mock/materialManagement.json'
import dataPerimeter from '@/static/mock/perimeterAnalysis.json'
import datapersonPower from '@/static/mock/personPower.json'
import axios from "axios";

export default {
  name: "mapView",
  data() {
    return {
      selectName: '周边分析',
      typePickerVisible: false,
      oprions: ['周边分析', '应急物资', '物资管理', '人员力量', '风险隐患'],
      center: [120.30242008, 31.59091392]
    }
  },
  mounted() {
    this.initMap()
  },
  watch: {},
  methods: {
    initMap() {
      ZnvMap.start('map-container', {
        center: {
          lng: this.center[0],
          lat: this.center[1]
        },
        resizeEnable: true,
        zoom: true,
        minZoom: 10,
        maxZoom: 18
      }).then((res) => {
        ZnvMap.centerTo(this.center, 16)
        this.setLocationMarker(this.center)
      })
    },
    handleSelect(e) {
      console.log('saffas', e)
      this.selectName = e
      this.typePickerVisible = false
      switch (this.selectName) {
        case '周边分析':
          this.loadPerimeterAnalysisPoint({isHidden: true})
          break
        case '应急物资':
          // 应急物资仓库
          this.loadEmergencyCoordinatesPoint({isHidden: true})
          break
        case '物资管理':
          this.loadMaterialManagementPoint({isHidden: true})
          break
        case '人员力量':
          this.loadPersonPowerPoint({isHidden: true})
          break
        case '风险隐患':
          this.loadHiddenRiskPoint({isHidden: true})
          break
      }
    },
    goToCenter() {
      ZnvMap.centerTo(this.center, 16)
      this.setLocationMarker(this.center)
    },
    setLocationMarker(pointGps) {
      ZnvMap.removeEventPointLayer()
      ZnvMap.loadEventPointLayer(
        [{
          id: 'currentLocation',
          title: '当前点位',
          icon: require('@/assets/map/location.png'),
          position: {lng: pointGps[0], lat: pointGps[1]},
          size: {width: 32, height: 32},
          offset: {x: -16, y: -16}
        }]
      )
    },
    loadEmergencyCoordinatesPoint(opts) {
      ZnvMap.removeEventPointLayer()
      sessionStorage.setItem('userId', 57)
      sessionStorage.setItem('userName', 'admin')
      axios.get('/binhuapis/resourceStuff/query?isAll=1' + '&userId=' +
        sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")).then((res) => {
        let lngLat = [res.data.data.list[0].gpsx, res.data.data.list[0].gpsy]
        ZnvMap.centerTo(lngLat, 16)
        let pointList = this.EmergencySuppliesPointProcess(res.data.data.list, '应急物资')
        ZnvMap.loadEventPointLayer(pointList, opts)
      })
    },
    loadPerimeterAnalysisPoint(opts) {
      let data = dataPerimeter.data
      let lngLat = [data[0].gpsx, data[0].gpsy]
      ZnvMap.centerTo(lngLat, 16)
      let pointList = this.EmergencySuppliesPointProcess(data, '周边分析')
      ZnvMap.loadEventPointLayer(pointList, opts)
    },
    loadMaterialManagementPoint(opts) {
      let data = dataMaterial.data
      let lngLat = [data[0].gpsx, data[0].gpsy]
      ZnvMap.centerTo(lngLat, 16)
      let pointList = this.EmergencySuppliesPointProcess(data, '物资管理')
      ZnvMap.loadEventPointLayer(pointList, opts)
    },
    loadPersonPowerPoint(opts) {
      let data = datapersonPower.data
      let lngLat = [data[0].gpsx, data[0].gpsy]
      ZnvMap.centerTo(lngLat, 16)
      let pointList = this.EmergencySuppliesPointProcess(data, '人员力量')
      ZnvMap.loadEventPointLayer(pointList, opts)
    },
    loadHiddenRiskPoint(opts) {
      let data = dataHidden.data
      let lngLat = [data[0].gpsx, data[0].gpsy]
      ZnvMap.centerTo(lngLat, 16)
      let pointList = this.EmergencySuppliesPointProcess(data, '风险隐患')
      ZnvMap.loadEventPointLayer(pointList, opts)
    },
    EmergencySuppliesPointProcess(data, opts) {
      ZnvMap.removeEventPointLayer()
      return data.map((e, index) => {
        let temp = {
          id: opts + '_' + e.id,
          title: e.stuffName,
          icon: mapIcon[`${opts}`],
          position: {lng: e.gpsx, lat: e.gpsy},
          size: {width: 30, height: 34},
          offset: {x: -16, y: -36},
          extData: e
        }
        return temp
      })
    }
  }
}
</script>

<style scoped lang="scss">
.znv-map {
  .znv-map-top {
    position: absolute;
    left: 8rem;
    z-index: 20000;
    width: 718rem;
    margin-top: 20rem;

    .znv-map-button {
      position: relative;
      top: 6rem;
      left: 620rem;
      background-color: white;
      width: 79rem;
      height: 70rem;
      border-radius: 5rem;

      .icon {
        margin: 10rem 8rem 8rem 12rem;
      }
    }
  }

  .znv-map-bottom {
    width: 750rem;
    height: 1625rem;

    .map-container {
      width: 100%;
      height: 100%;
    }
  }
}

/deep/ .van-field__label {
  width: 2.2em;
  margin-right: 8rem;
}

/deep/ .van-search__content {
  margin-left: -330rem;
}

</style>
