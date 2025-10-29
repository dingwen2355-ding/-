<template>
  <div class="map-view">
    <div class="znvmap" id="map-container"></div>
  </div>
</template>

<script>
import $ from "jquery";
import ZnvMap from '@/components/map/znvMap'
import mapIcon from './mapIcon'
import enterpriseRiskMapIcon from '../../view/enterpriseRisk/mapIcon'
// import moment from 'moment'
import Axios from "axios";
export default {
  data () {
    return {
      infomation: {}
    }
  },
  components: {
  },
  mounted () {
    this.initMap()
  },
  watch: {
    '$store.getters.resizeValue': {
      handler (v) {
        this.mapResize(v)
        this.resizeValue = v
      },
      immediate: true
    },
    '$store.getters.windowInfoDetail': {
      handler (v) {
        this.showWindowInfoDetail(v.data)
      }
    }
  },
  methods: {
    initMap () {
      // 初始化地图
      ZnvMap.start('map-container', {
        resizeEnable: true,
        zoom: 12,
        minZoom: 8
      }).then(() => {
        this.mapResize(this.resizeValue)
        // 设置地图中心点和轮廓
        this.centerRegionalContour()
        // 加载点位
        this.loadMapPoints()
      })
    },
    mapResize (v) {
      if (v) {
        $('#map-container')
          .css('transform', 'scale(' + 1 / v[0] + ',' + 1 / v[1] + ')')
          .css(
            'transformOrigin',
            document.body.clientWidth > 3000 ? '1770px' : '500px'
          )
      }
    },
    centerRegionalContour () {
      Axios.get('/static/mock/getPrecinctList.json').then(res => {
        const data = res.data.data[0]
        this.centerPoint(data)
        this.regionalContour(data.regionXY)
      })
    },
    // 设置地图中心点
    centerPoint (data) {
      setTimeout(() => {
        ZnvMap.gotoMapPoint({
          lng: data.gpsX,
          lat: data.gpsY,
          zoom: data.zoom || 10
        })
      }, 1000)
    },
    // 设置区域轮廓
    regionalContour (pathString) {
      Axios.get('/static/mock/district.json').then(res => {
        res.data.features.forEach(e => {
          ZnvMap.regionalContour(
            e.properties.name,
            e.geometry.coordinates[0][0],
            {
              fillColor: '#00CEDC',
              strokeColor: '#00CEDC',
              strokeWeight: 4,
              fillOpacity: 0.2,
              showLabel: true,
              center: e.properties.center,
              label: e.properties.name
            }
          )
        })
      })
    },
    loadMapPoints () {
      if (this.$route.name === 'enterpriseRisk') {
        // 企业风险
        this.loadEnterpriseRiskPoint({ isHidden: false })
      }
    },
    // 企业风险
    loadEnterpriseRiskPoint (opts) {
      Axios.get('/gtw/EIMS-management/industryRisk/queryIndustryInfo').then(res => {
        let pointList = this.EnterpriseRiskPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    EnterpriseRiskPointProcess (data) {
      let risk = {
        '蓝色': {
          class: 'blue',
          label: '低风险'
        },
        '黄色': {
          class: 'yellow',
          label: '一般风险'
        },
        '橙色': {
          class: 'orange',
          label: '较大风险'
        },
        '红色': {
          class: 'red',
          label: '重大风险'
        }
      }
      return data.map((e, index) => {
        let iconInfo = mapIcon['企业风险_' + e.riskLevel]
        e.type = '企业风险'
        e.subType = risk[e.riskLevel].label
        e.class = risk[e.riskLevel].class
        e.precinctName = e.areaName
        let temp = {
          id: '企业风险_' + e.id,
          icon: iconInfo,
          title: e.industryName,
          position: { lng: e.gpsx, lat: e.gpsy },
          size: { width: 16, height: 16 },
          offset: { x: -8, y: -8 },
          extData: e
        }
        enterpriseRiskMapIcon[0].subFilter.forEach(e2 => {
          if (e2.name === e.subType) {
            e2.list.push(temp.id)
          }
        })
        return temp
      })
    },
    showWindowInfoDetail(v) {}
  },
  beforeDestroy() {
    ZnvMap.destroy()
  }
}
</script>

<style lang="scss">
.map-view {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  top: 0;
  overflow: hidden;
  flex-grow: 1;
  .znvmap {
    height: 100%;
    position: relative;
    #popwin-blue, #popwin-yellow, #popwin-orange, #popwin-red {
      background: rgba(255, 255, 255, 0.9) !important;
      transform: translate(-50%, -110%);
      .box{
        display: flex;
        flex-direction: column;
        height: 100%;
      }
      .list{
        padding-top: 12px;
        display: flex;
        flex-direction: column;
        flex-grow: 1;
        justify-content: space-between;
        .item{
          .key{
            color: #8B93A0;
          }
          .value{
            color: #202D40;
          }
        }
      }
    }
    #popwin-blue{
      border: 1px solid #0093FF!important;
      border-radius: 10px ;
      .title{
        color: #0093FF;
      }
    }
    #popwin-yellow{
      border: 1px solid #D3A501!important;
      border-radius: 10px ;
      .title{
        color: #D3A501;
      }
    }
    #popwin-orange{
      border: 1px solid #FF6E0B!important;
      border-radius: 10px ;
      .title{
        color: #FF6E0B;
      }
    }
    #popwin-red{
      border: 1px solid #EE2221!important;
      border-radius: 10px ;
      .title{
        color: #EE2221;
      }
    }
  }
  .twinkle-animation {
    animation: twinkle 1s infinite;
  }
  @keyframes twinkle {
    0% {
      transform: scale(0.7);
      opacity: 0;
    }
    50% {
      transform: scale(1);
      opacity: 1;
    }
    100% {
      transform: scale(0.7);
      opacity: 0;
    }
  }
}
</style>
