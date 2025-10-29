<template>
  <div v-if="visible" class="right-bg">
    <IocTitle title="应急避难场所" @change="closedWin()" />
    <div class="box">
      <div class="item" v-for="(item, i) in list" :key="i" @click="showPoint(i)">
        <div class="item-title">{{ item.name }}</div>
        <div class="item-content">
          <div class="content-item">
            <div class="top">场所类型</div>
            <div class="down">{{ item.type }}</div>
          </div>
          <div class="content-item">
            <div class="top">场所面积</div>
            <div class="down">{{ item.area }} m²</div>
          </div>
          <div class="content-item">
            <div class="top">人口容量</div>
            <div class="down">{{ item.size }} 人</div>
          </div>
        </div>
        <div class="line"></div>
        <div class="item-footer">
          <img class="icon" src="@/assets/fireFighting/img_tablelist_adds_iocn1.png" alt="">
          <div class="address">{{ item.address }}</div>
        </div>
      </div>
    </div>
    <div class="box2">
      <!-- <img src="@/assets/fireFighting/Frame_moni.png" alt="" class="box2-img"> -->
      <div class="box2-text">
        模拟演练
      </div>
    </div>
  </div>
</template>

<script>
import IocTitle from '@/components/title'
import * as CoordTransform from '@/utils/coordtransform'

export default {
  name: 'fireFightingRight',
  components: {
    IocTitle
  },
  data() {
    return {
      visible: true,
      list: [
        {
          name: "美湖文化街管理办公室西北",
          type: "小区",
          area: 1704,
          size: 173,
          address: "滨湖区望山路与景丽路交叉口东南120米",
          // lng: 120.281862,
          // lat: 31.537265,
          // zbx: 'baidu',
          lng: 120.274189,
          lat: 31.532092,
          zbx: 'gaode',
        },
        {
          name: "波尔麦时尚酒店西北",
          type: "小区",
          area: 1899,
          size: 190,
          address: "滨湖区景丽东苑185-187",
          // lng: 120.281318,
          // lat: 31.539356,
          // zbx: 'baidu'
          lng: 120.273067,
          lat: 31.534798,
          zbx: 'gaode'
        },
        {
          name: "应急掩蔽场所(湖滨路)",
          type: "小区",
          area: 1430,
          size: 140,
          address: "滨湖区湖滨路与太湖西大道交叉口西北100米",
          lng: 120.282997,
          lat: 31.542638,
          zbx: 'gaode'
        },
      ],//应急避难场所
    }
  },
  methods: {
    showPoint(index) {
      __g.marker.clear()
      let markerArr = []
      for (let i = 0; i < this.list.length; i++) {
        const e = this.list[i];
        let img = require('@/assets/fireFighting/icon_binan_point.png')
        let marker = {
          id: 'refuge' + i,
          // userData: JSON.stringify({ type: e.type }),
          coordinate: this.getCoord(e.lng, e.lat, e.zbx),
          imagePath: img,
          // text: `${e.name}(${e.type})`,
          fontSize: 20,
          displayMode: 2,
          range: [1, 80000],
          imageSize: [48, 48]
        }
        markerArr.push(marker)
      }
      // console.log(markerArr);
      // 添加点位
      __g.marker.add(markerArr)
      // 聚焦点位
      __g.marker.focus(['refuge' + index], 500)
    },
    // 坐标转换
    getCoord(lng, lat, zbx) {
      let points;
      if (zbx == "baidu") {
        const a = CoordTransform.BD09ToGCJ02(lng, lat)
        const point = CoordTransform.GCJ02ToWGS84(a[0], a[1])
        points = CoordTransform.TransformByEPSG(4326, 4549, point[0], point[1])
      }
      else {
        // 高德
        const point = CoordTransform.GCJ02ToWGS84(lng, lat)
        points = CoordTransform.TransformByEPSG(4326, 4549, point[0], point[1])
      }

      return points
    },
    closedWin() {
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>
.right-bg {
  .box {
    margin-top: 20px;
    height: 665px;
    box-sizing: border-box;
    overflow-y: auto;

    .item {
      width: 428px;
      height: 180px;
      box-sizing: border-box;
      background: url('@/assets/fireFighting/img_bulldinglist_bg.png');
      background-size: 100% 100%;
      margin-bottom: 30px;
      padding-top: 25px;

      .item-title {
        padding-left: 20px;
        border-left: 5px solid #00d5ff;
        color: #00d5ff;
        font-family: "Source Sans 3";
        font-size: 20px;
        font-style: normal;
        font-weight: 600;
        line-height: normal;
      }

      .item-content {
        margin-top: 17px;
        display: flex;

        // justify-content: space-between;
        .content-item {
          width: 142.67px;
          height: 40px;
          display: flex;
          flex-direction: column;
          align-items: center;

          .top {
            color: #ffffff;
            font-family: "Inter";
            font-size: 16px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
          }

          .down {
            color: #00d5ff;
            font-family: "Source Sans 3";
            font-size: 18px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
          }
        }
      }

      .line {
        margin-top: 22px;
        width: 396px;
        height: 1px;
        background: linear-gradient(270deg, #00D5FF 0%, #00d5ff00 100%);
      }

      .item-footer {
        display: flex;
        align-items: center;
        margin-top: 10px;
        margin-left: 20px;

        .icon {
          width: 10.92px;
          height: 12.25px;
        }

        .address {
          margin-left: 8px;
          color: #ffffff;
          font-family: "Inter";
          font-size: 16px;
          font-style: normal;
          font-weight: 400;
          line-height: normal;
        }
      }
    }
  }

  .box2 {
    // margin-top: 25px;
    width: 428px;
    height: 70px;
    background-image: url("@/assets/fireFighting/img_moni_bg@2x.png");
    background-size: 100% 100%;
    box-sizing: border-box;
    padding-top: 22px;
    padding-left: 74px;

    .box2-text {
      color: #00d5ff;
      font-family: "Source Sans 3";
      font-size: 18px;
      font-style: normal;
      font-weight: 600;
      line-height: normal;
    }

  }
}
</style>