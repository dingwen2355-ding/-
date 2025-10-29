<template>
  <div class="map-view-container" v-show="mapShow">
    <div class="map-view" :class="{ forbid: forbid }" id="all-map2" @click="clickMap"></div>
    <div class="chooseList" v-show="areaSupport">
      <header class="title">
        <label>已选择项</label>
      </header>
      <div class="content">
        <div class="search-box">
          <el-input placeholder="请输入搜索内容" v-model="chooseText"></el-input>
          <el-button icon="el-icon-search"></el-button>
        </div>
        <div class="list-content">
          <el-checkbox-group v-model="chooseForm.chooseList">
            <el-checkbox
              class="leftItem"
              v-show="item.isShow"
              :checked="item.checked"
              v-for="item in chooseForm.list"
              :key="item.deviceId"
              :label="item.deviceId"
            >
              <!--<div class="leftItem" v-for="item in chooseForm.list" :key="item.deviceId" :label="item">-->
              <el-tooltip class="item" effect="dark" :content="item.deviceName" placement="top">
                <div
                  style="width: 480px; height: 100%; display: flex; justify-content: space-between; align-items: center"
                >
                  <span v-if="item.onofflineState === 0" style="color: #85ce61">在线</span>
                  <span v-if="item.onofflineState === 1" style="color: red">离线</span>
                  <span
                    style="
                      display: inline-block;
                      width: 350px;
                      overflow: hidden;
                      white-space: nowrap;
                      text-overflow: ellipsis;
                    "
                    >{{ item.deviceName }}</span
                  >
                  <!-- <el-button @click="handleView(item)" type="text" size="medium">预览</el-button> -->
                </div>
              </el-tooltip>
              <!--</div>-->
            </el-checkbox>
          </el-checkbox-group>
        </div>
        <div class="bottom-tools">
          <!-- <el-button type="primary" @click="doSure">确认</el-button>
          <el-button @click="doCancel">取消</el-button> -->
        </div>
      </div>
    </div>
    <el-row class="search-row">
      <div class="search-container">
        <el-input v-model="address" placeholder="请输入地址进行搜索" clearable></el-input>
        <el-button type="primary" @click="searchPlace">搜索</el-button>
        <span style="margin-left: 20px">点位信息</span>
        <el-input v-model="pointInfo"></el-input>
        <el-button @click="submitPoint" type="primary" :disabled="forbid">确定</el-button>
        <el-button @click="closeMap" type="primary">关闭</el-button>
        <!--<el-button @click="drawAnything" type="primary" v-show="areaSupport">区域选择</el-button>-->
        <el-dropdown v-show="areaSupport" @command="handleCommand">
          <el-button type="primary">
            区域选择
            <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown" class="el-dropdown-menu-map">
            <el-dropdown-item command="circle">圆形</el-dropdown-item>
            <el-dropdown-item command="rect">方型</el-dropdown-item>
            <el-dropdown-item command="polygon">多边形</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-row>
    <div class="button-group" style="cursor: pointer">
      <!--      <input type="button" class="button" value="中心点选择" id="point" @click.stop="drawPoint"/>-->
      <!--      <input type="button" class="button" value="清空绘制" id="clear" @click.stop="clearDrawOverlays"/>-->
    </div>
    <!-- <VideoDialog :markerInfo="markerInfo" /> -->
  </div>
</template>

<script>
// import VideoDialog from '../view/videoPacketConfiguration/VideoDialog.vue'
import $ from 'jquery'
import ZnvMap from '../components/map/znvMap'
import axios from 'axios'

export default {
  name: 'MapCollectionPoint',
  data() {
    return {
      MGeocoder: null,
      pointInfo: '',
      address: '',
      idKey: 'initPoint',
      pns: [],
      chooseForm: {
        chooseList: [],
        list: []
      },
      massMarks: null,
      chooseText: ''
      // markerInfo: {
      //   deviceName: '',
      //   deviceId: '',
      //   dialogVisible: false
      // }
    }
  },
  // components: { VideoDialog },
  props: ['oriGpsInfo', 'forbid', 'mapShow', 'areaSupport'],
  mounted() {
    this.$nextTick(() => {
      let translates = $('#app').css('transform').substring(7).split(',')
      $('#all-map2').css('transform', `scale(${1 / translates[0]}, ${1 / translates[3]})`)
      this.$nextTick(() => {
        this.initMap()
      })
    })
    // if (this.pns.length === 0) {
    //   this.initPoints()
    // }
    // this.$root.eventBus.$on('upDataVideoDialog', (data) => {
    //   this.markerInfo = {
    //     deviceName: '',
    //     deviceId: '',
    //     dialogVisible: false
    //   }
    // })
  },
  destroyed() {
    ZnvMap.destroy()
  },
  methods: {
    // handleView(row) {
    //   // console.log(row)
    //   this.dialogTableVisible = true
    //   this.markerInfo = {
    //     deviceName: row.deviceName,
    //     deviceId: row.deviceId,
    //     dialogVisible: true
    //   }
    //   // this.cameraIndexCodes = row.deviceId
    // },
    handleCommand(cammand) {
      this.drawAnything(cammand)
    },
    initPoints() {
      const url = '/gtw/EIMS-management/device/getDeviceInfo?page=1&pageSize=280000'
      axios({
        method: 'get',
        url: url
      }).then((r) => {
        if (r.data.code === 200) {
          this.pns = r.data.data.list
        } else {
          this.$message({
            message: r.data.message,
            customClass: 'el-message-map'
          })
        }
      })
    },
    initMap() {
      // 初始化地图
      ZnvMap.start('all-map2', {
        resizeEnable: true,
        center: [118.054413, 36.81243], // 淄博市
        zoom: 16,
        mapStyle: 'amap://styles/light'
      }).then(() => {})
      // ZnvMap.mapComplete(async () => {
      //     this.MGeocoder = ZnvMap.initGeocoder({
      //         city: '', // 城市，默认：“全国”
      //         batch: true
      //     })
      // })
    },
    // 点击节点画出读取的中心点位
    initMarker(gpsX, gpsY, id) {
      let obj = {
        gpsX: parseFloat(gpsX),
        gpsY: parseFloat(gpsY),
        pointId: id
      }
      ZnvMap.initMarker(obj, () => {})
    },
    // 隐藏所有的点位
    hideAllLayer() {
      ZnvMap.hideAllLayer()
    },
    clickMap() {
      // this.drawPoint()
    },
    submitPoint() {
      this.$emit('getPoint', this.pointInfo)
      setTimeout(() => {
        this.pointInfo = ''
      }, 1000)
      ZnvMap.clearDrawOverlays()
    },
    closeMap() {
      const _this = this
      if (this.massMarks) {
        this.massMarks.clear()
        this.chooseForm.list = []
        this.chooseForm.chooseList = []
      }
      ZnvMap.clearMap()
      ZnvMap.clearDrawOverlays()
      this.$nextTick(function () {
        _this.$emit('closeMap')
      })
    },
    // 隐藏所有的轮廓
    // hidePolygon() {
    //     ZnvMap.hidePolygon()
    // },
    // 显示用户设置的显示轮廓，用来对比
    // showSavePolygon() {
    //     ZnvMap.showSavePolygon()
    // },
    // 隐藏固定id的轮廓
    // hideOnePolygon(id) {
    //     ZnvMap.hideOnePolygon(id)
    // },
    // 显示固定id的轮廓
    // showOnePolygon(id) {
    //     ZnvMap.showOnePolygon(id)
    // },
    // 画节点的轮廓
    // regionalContour(id, pathString, fillColor, outlineColor) {
    //     // 适配惠南多个轮廓
    //     let pathStringList = pathString.split('#')
    //     if (fillColor === '') {
    //         fillColor = '#00AEFF'
    //     }
    //     pathStringList.forEach(e => {
    //         ZnvMap.regionalContour(id, e, {
    //             strokeColor: outlineColor,
    //             fillColor: fillColor,
    //             fillOpacity: 0.6,
    //             strokeOpacity: 1,
    //             strokeWeight: 2
    //         })
    //     })
    // },
    // // 编辑后画区域
    // drawPolygon() {
    //     // ZnvMap.clearDrawOverlays()
    //     ZnvMap.drawPolygon(data => {
    //         this.$emit('drawFinish', {
    //             data: data,
    //             type: 'Polygon'
    //         })
    //     })
    // },
    // 编辑后画点
    drawPoint() {
      ZnvMap.clearDrawOverlays()
      this.hideAllLayer()
      this.$nextTick(() => {
        ZnvMap.drawPoint((data) => {
          // this.$emit('drawFinish', {
          //     data: data,
          //     type: 'Point'
          // })
          console.log(data)
          this.pointInfo = data.lat.toString() + ',' + data.lng.toString()
        })
      })
    },
    getPoint(v) {
      this.pointInfo = v
    },
    setPlace(address, callback) {
      // let self = this
      const url = `/gtw/EIMS-management/gps/queryGpsByAddress?address=${address}`
      axios({
        method: 'get',
        url: url
      }).then((r) => {
        if (r.data.code === 200) {
          callback(r.data.data)
        } else {
          this.$message({
            type: 'warning',
            message: '未寻找到相关位置信息',
            customClass: 'el-message-map'
          })
          return false
        }
      })
    },
    searchPlace() {
      let self = this
      if (this.address !== '') {
        this.setPlace(this.address, function (data) {
          if (data) {
            let lng = data.gpsx
            let lat = data.gpsy
            let point = ZnvMap.zbTransform(
              {
                gpsX: lng,
                gpsY: lat
              },
              4
            )
            self.centerPoint({
              gpsX: point[0],
              gpsY: point[1]
            })
            ZnvMap.hideAllLayer()
            self.initMarker(point[0], point[1], new Date().getTime())
            self.pointInfo = point[0].toString() + ',' + point[1].toString()
          }
        })
      }
    },
    clearDrawOverlays() {
      ZnvMap.clearDrawOverlays()
    },
    clearOverlays() {
      ZnvMap.clearDrawOverlays()
    },
    // 设置地图中心点
    centerPoint(data) {
      ZnvMap.gotoMapPoint({
        lng: data.gpsX,
        lat: data.gpsY,
        zoom: 14
      })
    },
    drawAnything(type) {
      const _this = this
      if (_this.massMarks) {
        _this.massMarks.clear()
        ZnvMap.clearDrawOverlays()
        _this.chooseForm.chooseList = []
      }
      switch (type) {
        case 'circle':
          ZnvMap.selectCircle(this.pns, function (rePns) {
            let pTemp = rePns.map((t) => {
              t.lnglat = [t.lng, t.lat]
              t.name = t.deviceName
              t.style = 2
              return t
            })
            let p = new Promise((resolve, reject) => {
              _this.massMarks = ZnvMap.MassMarks(pTemp)
              resolve(pTemp)
            })
            p.then((areaDevices) => {
              // _this.$root.eventBus.$emit("getGpsArea", areaDevices);
              _this.chooseForm.list = areaDevices.map((t) => {
                t.checked = true
                t.isShow = true
                return t
              })
            })
          })
          break
        case 'rect':
          ZnvMap.selectRect(this.pns, function (rePns) {
            let pTemp = rePns.map((t) => {
              t.lnglat = [t.lng, t.lat]
              t.name = t.deviceName
              t.style = 2
              return t
            })
            let p = new Promise((resolve, reject) => {
              _this.massMarks = ZnvMap.MassMarks(pTemp)
              resolve(pTemp)
            })
            p.then((areaDevices) => {
              // _this.$root.eventBus.$emit("getGpsArea", areaDevices);
              _this.chooseForm.list = areaDevices.map((t) => {
                t.checked = true
                t.isShow = true
                return t
              })
            })
          })
          break
        case 'polygon':
          ZnvMap.selectPolygon(this.pns, function (rePns) {
            let pTemp = rePns.map((t) => {
              t.lnglat = [t.lng, t.lat]
              t.name = t.deviceName
              t.style = 2
              return t
            })
            let p = new Promise((resolve, reject) => {
              _this.massMarks = ZnvMap.MassMarks(pTemp)
              resolve(pTemp)
            })
            p.then((areaDevices) => {
              // _this.$root.eventBus.$emit("getGpsArea", areaDevices);
              _this.chooseForm.list = areaDevices.map((t) => {
                t.checked = true
                t.isShow = true
                return t
              })
            })
          })
          break
      }
    }
    // doSure() {
    //   const _this = this
    //   this.$root.eventBus.$emit(
    //     'getGpsArea',
    //     this.chooseForm.list.filter((t) => _this.chooseForm.chooseList.includes(t.deviceId))
    //   )
    // },
    // doCancel() {
    //   if (this.massMarks) {
    //     this.massMarks.clear()
    //     ZnvMap.clearDrawOverlays()
    //     ZnvMap.clearMap()
    //     this.chooseForm.chooseList = []
    //   }
    //   this.chooseForm.list = []
    // }
  },
  watch: {
    chooseText(nVal, oVal) {
      this.chooseForm.list = this.chooseForm.list.map((t) => {
        if (t.deviceName.indexOf(nVal) > -1) {
          t.isShow = true
        } else {
          t.isShow = false
        }
        return t
      })
    },
    oriGpsInfo(oldV, newV) {
      if (this.oriGpsInfo && this.oriGpsInfo !== '') {
        let arr = this.oriGpsInfo.split(',')
        this.centerPoint({
          gpsX: arr[0],
          gpsY: arr[1]
        })
        this.pointInfo = arr[0] + ',' + arr[1]
        this.initMarker(arr[0], arr[1], new Date().getTime())
      }
    },
    // mapShow(v) {
    //   console.log(this.mapShow)
    //   if (v) {
    //     if (!this.forbid) {
    //       // this.drawPoint()
    //     } else {
    //     }
    //   }
    // },
    '$store.getters.mapClickGps'(v) {
      console.log(v)
      if (!this.forbid) {
        ZnvMap.clearMap()
        let data = v.split(',')
        this.getPoint(v)
        this.initMarker(data[0], data[1], new Date().getTime())
      }
    },
    mapShow(val) {
      if (val) {
        if (this.massMarks) {
          this.massMarks.clear()
          this.chooseForm.list = []
        }
        ZnvMap.clearDrawOverlays()
      }
    }
  }
}
</script>

<style lang="scss">
.map-view-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 4999;
  overflow: hidden;

  .map-view {
    width: 100%;
    height: 100%;
    cursor: crosshair;

    &.forbid {
      cursor: default;
    }
  }

  .search-row {
    position: absolute;
    top: 20px;
    left: 20px;
    width: 100%;
    height: 80px;
    z-index: 10;

    .search-container {
      .el-input {
        width: 225px;
      }
    }

    .inblock {
      display: inline-block;
    }
  }

  .button-group {
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 12px;
    padding: 10px;
    z-index: 99999;

    .button {
      height: 28px;
      line-height: 28px;
      background-color: #0d9bf2;
      color: #fff;
      border: 0;
      outline: none;
      padding-left: 5px;
      padding-right: 5px;
      border-radius: 3px;
      margin-bottom: 4px;
      cursor: pointer;
    }
  }

  .chooseList {
    position: fixed;
    z-index: 3000;
    width: 600px;
    height: 100%;
    background-color: white;
    right: 0;
    top: 0;

    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    box-shadow: 0px 8px 10px -5px rgba(0, 0, 0, 0.2), 0px 16px 24px 2px rgba(0, 0, 0, 0.14),
      0 6px 30px 5px rgba(0, 0, 0, 0.12);
    overflow: hidden;
    outline: 0;

    padding-left: 30px;
    padding-right: 30px;
    .title {
      height: 80px;
      display: flex;
      flex-direction: row;
      align-items: center;
      label {
        font-size: 20px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #4e5a6e;
      }
    }
    .content {
      height: calc(100% - 80px);
      .search-box {
        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
        .el-input {
          .el-input__inner {
            height: 100%;
            border-radius: 0px;
            border-right: none;
          }
        }
        .el-button {
          border-radius: 0px;
        }
      }
      .list-content {
        height: calc(100% - 140px);
        overflow-x: hidden;
        overflow-y: scroll;

        display: flex;
        flex-direction: column;

        .leftItem {
          padding-left: 10px;
          width: 100%;
          height: 42px;
          margin-top: 10px;
          line-height: 42px;

          .tip {
            width: 100px;
            height: 30px;
            border: 1px solid #09f;
            position: relative;
            background-color: #fff;
            font-size: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
          }

          .tip:before,
          .tip:after {
            content: '';
            display: block;
            border-width: 6px;
            position: absolute;
            top: -17px;
            left: 10px;
            height: 5px;
            border-style: dashed dashed solid;
            border-color: transparent transparent #0099ff;
          }

          .tip:after {
            top: -16px;
            border-color: transparent transparent #fff;
          }
        }
        .leftItem:hover {
          background: #e4f4ff;
        }
        .top {
          text-align: center;
          .item {
            margin: 4px;
          }
        }
      }
      .bottom-tools {
        position: absolute;
        bottom: 0px;
        width: calc(100% - 60px);
        display: flex;
        flex-direction: row-reverse;
        margin-bottom: 20px;

        .el-button {
          margin-left: 10px;
        }
      }
    }
  }
}
.el-message {
  z-index: 9999 !important;
}
.el-message-map {
  z-index: 5034 !important;
}
.el-dropdown-menu-map {
  z-index: 5033 !important;
}
</style>
