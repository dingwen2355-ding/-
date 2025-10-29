<template>
  <div class="distributionPlace">
    <div class="main-container">
      <div class="tools-container">
        <el-form :inline="true" :model="searchForm" ref="form">
          <el-form-item label="避难场所名称:">
            <el-input
              clearable
              v-model="searchForm.name"
              style="width: 175px"
              placeholder="请输入避难场所名称"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="类别:">
            <el-select filterable v-model="searchForm.kind" style="width: 140px;" placeholder="请选择类别" clearable>
              <el-option v-for="item in kindList" :value="item.id" :label="item.name" :key="item.id"></el-option>
            </el-select>
          </el-form-item>-->
          <el-form-item label="地址:">
            <el-input
              clearable
              v-model="searchForm.address"
              style="width: 140px"
              placeholder="请输入地址"
            ></el-input>
          </el-form-item>
          <el-form-item label="面积:">
            <!-- <el-input clearable v-model="searchForm.area" type="number" min="0" placeholder="请输入面积"></el-input> -->
            <el-input clearable v-model="searchForm.area" style="width: 140px" placeholder="请输入面积"></el-input>
          </el-form-item>
          <el-form-item style="float: right">
            <el-button
              icon="el-icon-search"
              type="primary"
              style="margin-left: 10px"
              @click="onSubmit"
            >查询</el-button>
            <el-button icon="el-icon-refresh-left" @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="contentBody">
        <div class="znvmap" id="map-container"></div>
        <div class="map-hover-01">
          <div
            class="list-item"
            :class="mapState === '开放' ? 'active' : ''"
            @click="mapTypeClick('1', 'state')"
          >
            <span class="l-circle green"></span>
            <span class="l-label">开放</span>
            <span class="l-num">{{ openNum }}</span>
          </div>
          <div
            class="list-item"
            :class="mapState === '有人' ? 'active' : ''"
            @click="mapTypeClick('2', 'state')"
          >
            <span class="l-circle yellow"></span>
            <span class="l-label">有人</span>
            <span class="l-num">{{ somebodyNum }}</span>
          </div>
          <div
            class="list-item"
            :class="mapState === '告警' ? 'active' : ''"
            @click="mapTypeClick('3', 'state')"
          >
            <span class="l-circle red"></span>
            <span class="l-label">告警</span>
            <span class="l-num">{{ alarmNum }}</span>
          </div>
          <div
            class="list-item"
            :class="mapState === '未开放' ? 'active' : ''"
            @click="mapTypeClick('4', 'state')"
          >
            <span class="l-circle blue"></span>
            <span class="l-label">未开放</span>
            <span class="l-num">{{ notOpenedNum }}</span>
          </div>
          <div
            class="list-item"
            :class="mapState === '暂不使用' ? 'active' : ''"
            @click="mapTypeClick('5', 'state')"
          >
            <span class="l-circle grey"></span>
            <span class="l-label">暂不使用</span>
            <span class="l-num">{{ notUsed }}</span>
          </div>
        </div>
        <div class="map-hover-02">
          <div
            class="list-item"
            :class="mapType === '室内场所' ? 'active' : ''"
            @click="mapTypeClick('室内场所', 'type')"
          >
            <i class="l-icon el-icon-s-home"></i>
            <span class="l-label">室内场所</span>
          </div>
          <div
            class="list-item"
            :class="mapType === '室外场所' ? 'active' : ''"
            @click="mapTypeClick('室外场所', 'type')"
          >
            <i class="l-icon el-icon-s-promotion"></i>
            <span class="l-label">室外场所</span>
          </div>
        </div>
        <div class="map-hover-03" v-show="ShelterDetailDialogVisible">
          <i class="m-div-close el-icon-close" @click="ShelterDetailDialogVisible = false"></i>
          <div class="m-div-title">{{ infomation.name }}</div>
          <div class="m-div-content">
            <div class="item-list">
              <div class="label">办事处：</div>
              <div class="value">{{ infomation.streetName }}</div>
            </div>
            <div class="item-list">
              <div class="label">所属社区：</div>
              <div class="value">{{ infomation.community }}</div>
            </div>
            <div class="item-list">
              <div class="label">场所地址：</div>
              <div class="value">{{ infomation.address }}</div>
            </div>
            <div class="item-list">
              <div class="label">场所类型：</div>
              <div class="value">{{ infomation.type }}</div>
            </div>
            <div class="item-list">
              <div class="label">总容纳人数：</div>
              <div class="value">{{ infomation.maxGalleryful }}人</div>
            </div>
            <div class="item-list">
              <div class="label">目前容纳人数：</div>
              <div class="value">{{ infomation.alreadyGalleryful }}人</div>
            </div>
            <div class="item-list">
              <div class="label">容纳率：</div>
              <!-- <div
                class="value"
              >{{ ((infomation.alreadyGalleryful / infomation.maxGalleryful) * 100).toFixed(2) }}%</div>-->
              <div class="value">{{alreadyGalleryfulChange(infomation)}}%</div>
            </div>
            <div class="item-list">
              <div class="label">责任人：</div>
              <div class="value">{{ infomation.person }}</div>
            </div>
            <div class="item-list">
              <div class="label">联系电话：</div>
              <div class="value">{{ infomation.phone }}</div>
            </div>
            <div class="item-list">
              <div class="label">当前状态：</div>
              <div class="value" v-if="infomation.state === '1'">
                <span class="v-circle green"></span>开放
              </div>
              <div class="value" v-if="infomation.state === '2'">
                <span class="v-circle yellow"></span>有人
              </div>
              <div class="value" v-if="infomation.state === '3'">
                <span class="v-circle red"></span>告警
              </div>
              <div class="value" v-if="infomation.state === '4'">
                <span class="v-circle blue"></span>未开放
              </div>
              <div class="value" v-if="infomation.state === '5'">
                <span class="v-circle grey"></span>暂不开放
              </div>
            </div>
          </div>
        </div>
        <div class="map-hover-04" v-show="MediaDialogVisible">
          <i class="m-div-close el-icon-close" @click="MediaDialogVisible = false"></i>
          <znv-video :src="src" type="mp4" class="m-div-media"></znv-video>
        </div>
        <div class="map-hover-05">
          <div class="m-tools">
            <i class="m-icon el-icon-aim" @click="goCenter"></i>
          </div>
          <div class="m-tools">
            <i class="m-icon el-icon-zoom-in" @click="zoomIn"></i>
            <span class="m-split"></span>
            <i class="m-icon el-icon-zoom-out" @click="zoomOut"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import ZnvVideo from '@/components/znvVideo/Video'
import ZnvMap from '@/components/map/znvMap'
import Axios from '@/utils/request'
import mapIcon from '@/components/map/mapIcon'
export default {
  name: 'distributionPlace',
  data() {
    return {
      infomation: {},
      ShelterDetailDialogVisible: false,
      MediaDialogVisible: false,
      src: '',
      kindList: [
        { id: '室内场所', name: '室内场所' },
        { id: '室外场所', name: '室外场所' }
      ],
      searchForm: {
        name: '',
        kind: '',
        address: '',
        area: ''
      },

      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      multipleSelection: [],
      showAdd: false,
      formInfo: {},
      isCode: 0, // 0 编辑 2 新增
      list: [],
      openNum: 0,
      somebodyNum: 0,
      alarmNum: 0,
      notOpenedNum: 0,
      notUsed: 0,
      mapType: '',
      mapState: ''
    }
  },
  watch: {
    '$store.getters.windowInfoDetail': {
      handler(v) {
        console.log(v)
        if (['室内场所', '室外场所', '避难场所'].includes(v.data.type)) {
          this.showWindowInfoDetail(v.data)
        }
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initMap()
    })
    this.src = configs.defaultMediaUrl
  },
  components: {
    ZnvVideo
  },
  methods: {
    alreadyGalleryfulChange(value) {
      if (value.maxGalleryful === '' || value.maxGalleryful === null || value.maxGalleryful === 0 || value.maxGalleryful === '0') {
        return 0
      } else {
        return ((parseInt(value.alreadyGalleryful) / parseInt(value.maxGalleryful)) * 100).toFixed(2)
      }
    },
    goCenter() {
      ZnvMap.gotoPoint([120.284401, 31.527823], 12)
    },
    zoomIn() {
      ZnvMap.zoomIn()
    },
    zoomOut() {
      ZnvMap.zoomOut()
    },
    mapTypeClick(v, t) {
      ZnvMap.hideAllMarkers()
      ZnvMap.showPointByKey([
        {
          key: t,
          value: [v]
        }
      ])
    },
    initMap() {
      // 初始化地图
      ZnvMap.start('map-container', {
        center: {
          lng: 120.284401,
          lat: 31.527823
        },
        resizeEnable: true,
        zoom: 12,
        minZoom: 2
      }).then(() => {
        // 加载点位
        this.loadMapPoints()
      })
    },
    loadMapPoints() {
      this.loadShelterPoint({ isHidden: false })
    },
    // 避难安置
    loadShelterPoint(opts) {
      Axios.get('/binhuapis/siteManage/query').then((res) => {
        let pointList = this.ShelterProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    getMapIcon(e) {
      let iconString = e.type + '_'
      switch (e.state) {
        case '1':
          this.openNum++
          iconString = iconString + '绿'
          break
        case '2':
          this.somebodyNum++
          iconString = iconString + '黄'
          break
        case '3':
          this.alarmNum++
          iconString = iconString + '红'
          break
        case '4':
          this.notOpenedNum++
          iconString = iconString + '蓝'
          break
        case '5':
          this.notUsed++
          iconString = iconString + '灰'
          break
        default:
          break
      }
      return iconString
    },
    ShelterProcess(data) {
      return data.map((e, index) => {
        let iconInfo = mapIcon[this.getMapIcon(e)]
        e.clickType = e.type
        let temp = {
          id: e.type + '_' + e.id,
          icon: iconInfo,
          title: e.name,
          position: { lng: e.gpsx, lat: e.gpsy },
          size: { width: 42, height: 42 },
          offset: { x: -21, y: -21 },
          extData: e
        }
        return temp
      })
    },
    queryDataTable() {
      ZnvMap.hideAllMarkers()
      this.loading = true
      let url = '/binhuapis/siteManage/query?'
      if (this.searchForm.name) {
        url = url + '&name=' + this.searchForm.name
      }
      // if (this.searchForm.kind) {
      //   url = url + '&category=' + this.searchForm.kind
      // }
      if (this.searchForm.address) {
        url = url + '&address=' + this.searchForm.address
      }
      if (this.searchForm.area) {
        url = url + '&acreage=' + this.searchForm.area
      }
      Axios.get(url).then((res) => {
        ZnvMap.showLayerByIds(res.data.data.list.map((e) => e.type + '_' + e.id))
      })
    },
    // 查询
    onSubmit() {
      this.mapState = ''
      this.mapType = ''
      this.queryDataTable()
    },
    // 重置
    resetSearch() {
      this.searchForm = {
        name: '',
        kind: '',
        address: '',
        area: ''
      }
    },
    showWindowInfoDetail(v) {
      this.ShelterDetailDialogVisible = +new Date()
      this.infomation = v
    },
    showMediaDetail(v) {
      this.MediaDialogVisible = +new Date()
    }
  }
}
</script>
<style lang="scss" scoped>
.distributionPlace {
  width: 100%;
  height: 100%;
  .main-container {
    width: 100%;
    height: 100%;
    .tools-container {
      ::v-deep .el-form {
        display: flex;
        flex-wrap: nowrap;
        align-items: center;
        .el-form-item {
          display: flex;
          margin-right: 0px;
          align-items: center;
          .el-form-item__label {
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #7f8892;
            line-height: 30px;
          }
          .el-form-item__content {
            .el-select {
              width: 250px !important;
            }
            .el-input {
              width: 250px !important;
              .el-input__inner {
                width: 250px;
                height: 45px;
                background: #ffffff;
                border: 1px solid #e9edf7;
                border-radius: 8px;
              }
            }
          }
        }
      }
    }
    .contentBody {
      position: relative;
      width: 100%;
      height: calc(100% - 60px);
      .znvmap {
        height: 100%;
        position: relative;
        background: #fff;
      }
      .map-hover-01 {
        position: absolute;
        bottom: 18px;
        left: 12px;
        width: 167px;
        height: auto;
        background: #ffffff;
        border-radius: 16px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .list-item {
          cursor: pointer;
          display: flex;
          flex-wrap: nowrap;
          align-items: center;
          justify-content: space-between;
          padding-top: 10px;
          padding-bottom: 10px;
          padding-left: 20px;
          padding-right: 20px;
          .l-circle {
            width: 13px;
            height: 13px;
            border-radius: 50%;
          }
          .l-circle.green {
            background: #76e147;
          }
          .l-circle.yellow {
            background: #ffd325;
          }
          .l-circle.red {
            background: #fd614c;
          }
          .l-circle.grey {
            background: #afbdc9;
          }
          .l-circle.blue {
            background: #1e80ff;
          }
          .l-label {
            width: 80px;
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #8b99b0;
            margin-left: 11px;
          }
          .l-num {
            font-size: 16px;
            font-family: Arial;
            font-weight: 400;
            color: #616161;
            text-align: right;
          }
        }
        .list-item:first-child {
          margin-top: 16px;
        }
        .list-item:last-child {
          margin-bottom: 16px;
        }
        .active {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
        }
        .list-item:hover {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
          .l-num {
            color: #ffffff;
          }
        }
      }
      .map-hover-02 {
        position: absolute;
        top: 18px;
        left: 16px;
        width: 152px;
        height: auto;
        background: #ffffff;
        border-radius: 16px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .list-item {
          width: 100%;
          padding-left: 21px;
          padding-right: 10px;
          padding-top: 10px;
          padding-bottom: 10px;
          cursor: pointer;
          .l-icon {
            width: 24px;
            height: 20px;
            color: #8b99b0;
          }
          .l-label {
            margin-left: 10px;
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #8b99b0;
          }
        }
        .list-item:first-child {
          margin-top: 16px;
        }
        .list-item:last-child {
          margin-bottom: 16px;
        }
        .active {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
        }
        .list-item:hover {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
        }
      }
      .map-hover-03 {
        position: absolute;
        left: calc(50% - 90px);
        top: 30%;
        transform: translate(-50%, -50%);
        width: 419px;
        height: auto;
        background: #ffffff;
        border-radius: 16px;
        padding-left: 30px;
        padding-top: 31px;
        .m-div-close {
          right: 20px;
          top: 20px;
          position: absolute;
          width: 11px;
          height: 11px;
        }
        .m-div-title {
          font-size: 18px;
          font-family: Microsoft YaHei;
          font-weight: bold;
          color: #7f8892;
        }
        .m-div-content {
          margin-top: 19px;
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          .item-list {
            margin-bottom: 10px;
            display: flex;
            flex-direction: row;
            flex-wrap: nowrap;
            .label {
              width: 120px;
              font-size: 16px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #a1aac2;
              text-align: right;
            }
            .value {
              width: calc(100% - 120px);
              font-size: 16px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #7f8892;
              .v-circle {
                display: inline-block;
                width: 10px;
                height: 10px;
                background: #77c26f;
                border-radius: 50%;
                margin-right: 6px;
              }
              .v-circle.green {
                background: #76e147;
              }
              .v-circle.yellow {
                background: #ffd325;
              }
              .v-circle.red {
                background: #fd614c;
              }
              .v-circle.grey {
                background: #afbdc9;
              }
              .v-circle.blue {
                background: #1e80ff;
              }
            }
          }
          .item-list:last-child {
            margin-bottom: 26px;
          }
        }
      }
      .map-hover-04 {
        position: absolute;
        right: 20px;
        top: 20px;
        width: 444px;
        height: 266px;
        z-index: 99;
        background: #000000;
        .m-div-close {
          right: 20px;
          top: 0px;
          position: absolute;
          width: 11px;
          height: 11px;
          color: #ffffff;
        }
        .m-div-media {
          z-index: 299;
          margin-top: 20px;
          margin-bottom: 20px;
        }
      }
      .map-hover-05 {
        position: absolute;
        right: 19px;
        bottom: 15px;
        .m-tools {
          width: 46px;
          min-height: 46px;
          height: auto;
          background: #ffffff;
          border-radius: 23px;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          margin-bottom: 16px;
          cursor: pointer;
          .m-split {
            width: 26px;
            height: 1px;
            background: #eef0f5;
          }
          .m-icon {
            font-size: 26px;
            color: #616161;
            padding: 10px;
          }
          .m-icon:hover {
            color: rgba(97, 97, 97, 0.7);
          }
        }
      }
    }
  }
}
</style>
