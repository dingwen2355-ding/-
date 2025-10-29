<template>
  <div class="map-filter">
    <div class="searchInput-box">
      <el-input
        placeholder="搜索点位"
        v-model="searchInfo"
        class="input-with-search"
        @keyup.enter.native="searchEnter"
      >
        <el-button slot="append" icon="el-icon-search" style="cursor: pointer" @click="searchEnter"></el-button>
      </el-input>
      <ul class="search-list">
        <li
          v-for="(item, index) in list"
          :key="index"
          @click="gotoPoint(item)"
        >{{ item.opts.title }}</li>
      </ul>
    </div>
    <div class="search-panel">
      <div class="panel-title">图层</div>
      <div class="collapse">
        <el-collapse v-model="activeName" accordion>
          <collapse-item
            v-for="(it, index) in mapIcon"
            :key="index"
            :title="it.name"
            :icon="it.iconfont"
            :name="index"
          >
            <div
              class="type-item"
              v-for="item in it.children"
              :key="item.name"
              :class="{ active1: item.active }"
              @click="checkPointSataus(item)"
            >
              {{ item.name }}
              <ul class="item-list" v-if="item.children && item.children.length > 0">
                <li
                  v-for="(itc, indc) in item.children"
                  :key="indc"
                  @click="checkChildStatus(itc)"
                >{{ itc.title }}</li>
              </ul>
            </div>
          </collapse-item>
        </el-collapse>
      </div>
    </div>
  </div>
</template>

<script>
import mapIcon from '@/components/map/mapIcon'
import ZnvMap from '@/components/map/znvMap'
import { setItemByKey, getLeafItemList } from '@/utils/treeFunc.js'
import collapseItem from '../znvCollapse/collapse-item'

export default {
  components: {
    collapseItem
  },
  data() {
    return {
      searchInfo: '',
      mapIcon: mapIcon,
      activeName: 0,
      list: []
    }
  },
  mounted() { },
  methods: {
    checkPointSataus(item) {
      if (item.children && item.children.length > 0) {
        return
      }
      setItemByKey(item, 'active', !item.active)
      this.getIconList()
      // const categories = [
      //   { name: '易涝点', type: 'floodPoint' },
      //   { name: '河道', type: 'riverRoadPoint' },
      //   { name: '湖泊', type: 'lakePoint' }
      // ]
      // const index = categories.findIndex((type) => type.name === item.name)
      // if (index > -1) {
      //   const cate = categories[index]
      //   if (item.active) {
      //     ZnvMap.showMarkersByType(cate.type)
      //   } else {
      //     ZnvMap.hideMarkersByType(cate.type)
      //   }
      // } else {
      //   this.getIconList()
      // }
    },
    checkChildStatus(item) {
      let data = {
        deviceId: item.id,
        deviceTYpeName: item.title,
        type: item.type,
        url: item.url
      }
      this.$root.Bus.$emit('openVideoWindow', data)
    },
    getIconList() {
      this.iconList = getLeafItemList(this.mapIcon)
      this.filterPoint()
    },
    filterPoint() {
      let nameList = []
      this.iconList.forEach((e) => {
        if (e.active) {
          nameList.push(e.type)
        }
      })
      console.log(nameList)
      this.$store.dispatch('map/setFilterIconList', nameList)
      ZnvMap.showCircleContainPointByKey([
        {
          key: 'type',
          value: nameList
        }
      ])
    },
    gotoPoint(item) {
      ZnvMap.clickEvent(item)
    },
    searchEnter() {
      if (!this.searchInfo) {
        return []
      } else {
        let searchInputList = this.searchInfo.split(' ')
        this.list = ZnvMap.getLayerByTitles(searchInputList)
      }
    }
  }
}
</script>

<style lang="scss">
.map-filter {
  width: 748rem;
  position: absolute;
  right: 700rem;
  top: 448rem;
  z-index: 99;
  .search-panel {
    width: 748rem;
    margin-top: 50rem;
    padding-bottom: 50rem;
    height: auto;
    background-color: RGBA(0, 39, 136, 1);
    border: 4rem solid #0165cb;
    z-index: 99;
    .panel-title {
      width: 100%;
      height: 120rem;
      background-color: RGBA(0, 86, 186, 1);
      border-bottom: 8rem solid RGBA(0, 119, 225, 1);
      padding-left: 50rem;
      display: flex;
      align-items: center;
    }
    .collapse {
      margin-left: 50rem;
      margin-right: 50rem;
      margin-top: 40rem;
      .el-collapse {
        border-top: none;
        border-bottom: none;
      }
      .el-collapse-item {
        border-top: 4rem solid #0084fe;
        border-left: 4rem solid #0084fe;
        border-right: 4rem solid #0084fe;
      }
      .el-collapse-item:last-child {
        border-bottom: 4rem solid #0084fe;
      }
      .el-collapse-item__arrow {
        border-bottom: none;
        overflow: visible;
        margin: 0 20rem 0 0;
      }
      .iconfont {
        font-size: 66rem;
        margin-right: 20rem;
      }
      .type-item {
        cursor: pointer;
        padding-left: 106rem;
        position: relative;
        .item-list {
          position: absolute;
          width: 648rem;
          height: auto;
          background-color: RGBA(0, 44, 141, 1);
          border: 1rem solid #ebeef5;
          left: -648rem;
          top: 0rem;
          cursor: pointer;
          display: none;
          li {
            padding-left: 40rem;
            line-height: 80rem;
            font-size: 48rem;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #73bce0;
            cursor: pointer;
            text-align: left;

            &:hover {
              background: #055898;
            }
          }
        }
      }

      .type-item:hover {
        background-color: RGBA(0, 71, 182, 1);
        .item-list {
          display: inline-block;
        }
      }
      .active1 {
        background-color: RGBA(0, 71, 182, 1);
      }

      .el-collapse-item__header {
        height: 120rem;
        background-color: RGBA(0, 71, 182, 1);
        color: rgba(75, 233, 255, 1);
        border-bottom: 1rem solid RGBA(30, 81, 142, 1);
        font-size: 48rem;
        padding-left: 30rem;
      }
      .el-collapse-item__wrap {
        background-color: RGBA(10, 45, 82, 0.7);
        border-bottom: none;
      }

      .el-collapse-item__content {
        background-color: RGBA(0, 44, 141, 1);
        color: RGBA(137, 195, 231, 1);
        padding: 20rem 0;
        font-size: 48rem;
        // opacity: 0.7;
      }
    }
  }

  .input-with-search {
    border: 1rem solid RGBA(0, 74, 172, 1);

    .el-input__inner {
      background: RGBA(0, 26, 127, 0.8);
      border: none;
      height: 100rem;
      font-size: 48rem;
      color: #fff;
    }

    .el-input-group__append {
      padding: 0 12rem;
      font-size: 70rem;
      border: none;
      background-color: RGBA(0, 26, 127, 1);
      color: RGBA(2, 151, 219, 1);
    }
  }

  .searchInput-box {
    position: relative;
    &:hover {
      .search-list {
        display: block;
        z-index: 1;
      }
    }
  }

  .search-list {
    width: 100%;
    max-height: 1200rem;
    background: rgba(7, 15, 46, 0.8);
    border: 1rem solid RGBA(2, 151, 219, 1);
    position: absolute;
    top: 100rem;
    overflow: auto;
    display: none;

    li {
      line-height: 60rem;
      font-size: 48rem;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #ffffff;
      cursor: pointer;
      text-align: left;
      padding-left: 20rem;
      margin-top: 30rem;

      &:hover {
        background: #070f2e;
      }
    }
  }
}
</style>
