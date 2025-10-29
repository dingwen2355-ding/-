<template>
  <div class="map-filter">
    <div class="searchInput-box">
      <el-input placeholder="搜索点位" v-model="searchInfo" class="input-with-search" @keyup.enter.native="searchEnter">
        <el-button slot="append" icon="el-icon-search" style="cursor: pointer" @click="searchEnter"></el-button>
      </el-input>
      <ul class="search-list">
        <li v-for="(item, index) in list" :key="index" @click="gotoPoint(item)">
          {{ item.title }}
        </li>
      </ul>
    </div>
    <div class="collapse">
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item v-for="(it, index) in mapIcon" :key="index" :title="it.name" :name="index">
          <div
            class="type-item"
            v-for="item in it.children"
            :key="item.name"
            :class="{ active1: item.active }"
            @click="checkPointSataus(item)"
          >
            {{ item.name }}
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
import mapIcon from '../../../../components/map/mapIcon'
import ZnvMap from '@/components/map/znvMap-3d'
import { setItemByKey, getLeafItemList } from '@/utils/treeFunc.js'

export default {
  data() {
    return {
      searchInfo: '',
      mapIcon: mapIcon,
      activeName: 0,
      list: []
    }
  },
  mounted() {},
  methods: {
    checkPointSataus(item) {
      if (item.name === '无人机') {
        let data = {
          deviceId: '1',
          deviceTYpeName: '无人机',
          type: 'flv'
        }
        this.$root.Bus.$emit('openVideoWindow', data)
        return
      }
      setItemByKey(item, 'active', !item.active)
      this.getIconList()
    },
    getIconList() {
      this.iconList = getLeafItemList(this.mapIcon)
      this.filterPoint()
    },
    filterPoint() {
      let nameList = []
      this.iconList.forEach(e => {
        if (e.active) {
          nameList.push(e.type)
        }
      })
      console.log(nameList)
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
  width: 800rem;
  position: absolute;
  right: 20rem;
  top: 0;

  .collapse {
    margin-top: 40rem;

    .type-item {
      cursor: pointer;
      padding-left: 40rem;
    }

    .type-item:hover {
      background-color: RGBA(5, 88, 152, 1);
    }

    .active1 {
      background-color: RGBA(5, 88, 152, 1);
    }

    .el-collapse-item__header {
      height: 120rem;
      line-height: 75rem;
      background-color: RGBA(5, 88, 152, 1);
      color: RGBA(126, 186, 224, 1);
      border-bottom: 1rem solid RGBA(30, 81, 142, 1);
      font-size: 48rem;
      padding-left: 30rem;
    }

    .el-collapse-item__wrap {
      background-color: RGBA(10, 45, 82, 0.7);
    }

    .el-collapse-item__content {
      color: RGBA(137, 195, 231, 1);
      padding: 20rem 0;
      font-size: 48rem;
      // opacity: 0.7;
    }
  }

  .input-with-search {
    border: 1rem solid #00ffff;

    .el-input__inner {
      background: rgba(7, 15, 46, 0.8);
      border: none;
      height: 100rem;
      font-size: 48rem;
    }

    .el-input-group__append {
      padding: 0 12rem;
      font-size: 70rem;
      border: none;
      background: #05c5e3;
      color: #fff;
    }
  }

  .searchInput-box{
    position: relative;
    &:hover{
      .search-list{
        display: block;
      }
    }
  }

  .search-list {
    width: 100%;
    max-height: 1200rem;
    background: rgba(7, 15, 46, 0.8);
    border: 1rem solid #00ffff;
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

      &:hover {
        background: #070f2e;
      }
    }
  }
}
</style>
