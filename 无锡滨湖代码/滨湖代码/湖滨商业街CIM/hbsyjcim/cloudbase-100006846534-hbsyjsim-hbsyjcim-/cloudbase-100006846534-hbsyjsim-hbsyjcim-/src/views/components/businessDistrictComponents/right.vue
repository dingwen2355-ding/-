<template>
  <div v-if="visible" class="right-bg">
    <IocTitle title="报警等级分布" @change="closedWin()" />
    <WarningChart :chartData="shopList.length" />

    <IocTitle title="设备告警列表" :closeVisible="false" />

    <div class="box-02">
      <ul class="head">
        <li>
          <div>
            <span>设备名称</span>
          </div>
          <div>
            <span>位置</span>
          </div>
          <div>
            <span>等级</span>
          </div>
        </li>
      </ul>
      <ul class="content scroll-container">
        <li
          v-for="(item, i) in shopList"
          :key="i"
          @click="handleClickItem(item, i)"
        >
          <div>
            <span>{{ item.device_code }}</span>
          </div>
          <div>
            <span>{{ item.location_desc }}</span>
          </div>
          <div>
            <span class="type normal">普通</span>
            <!-- <span class="type emergent" v-else>紧急</span> -->
          </div>
        </li>
      </ul>
    </div>
    <el-pagination
      class="page-box"
      layout="total,prev, pager, next"
      @current-change="changePage"
      :total="pageObj.total"
      :page-size="pageObj.size"
      :page-count="pageObj.page"
    >
    </el-pagination>
  </div>
</template>

<script>
import IocTitle from "@/components/title";
import WarningChart from "./rightwainingChart.vue";
import request from "@/utils/request";
import * as CoordTransform from "@/utils/coordtransform";
import eventBus from "@/utils/eventBus";

export default {
  name: "businessDistrictComLeft",
  components: { IocTitle, WarningChart },
  data() {
    return {
      visible: true,
      shopList: [],
      pageObj: {
        total: 0,
        size: 10,
        page: 1,
      },
    };
  },
  created() {
    this.getWarningLit();
  },
  methods: {
    async getWarningLit() {
      await request
        .post("api/binhu/getComponentWarnningList", {
          pageNo: this.pageObj.page,
          pageSize: this.pageObj.size,
        })
        .then(({ code, data }) => {
          if (code !== 200) return;
          this.pageObj.total = data.length;
          this.shopList = data;
        });
    },
    handleClickItem(item, i) {
      console.log(item);
      eventBus.$emit("handleBdcDialog", false);
      // 获取坐标点位 t1 井盖
      if (!item.lat || !item.lng) return;
      this.setMarker("t1", item, i);
    },
    // 坐标转换
    getCoord(name, lng, lat) {
      // const a = CoordTransform.BD09ToGCJ02(lng, lat);
      // const point = CoordTransform.GCJ02ToWGS84(a[0], a[1]);
      let points = CoordTransform.TransformByEPSG(4326, 4549, lng, lat);
      return [...points, 50];
    },
    async setMarker(type, item, i) {
      // 清除marker
      await __g?.marker.clear();
      const e = item;
      let marker = {
        id: "bdc-" + type + i,
        userData: JSON.stringify(e),
        coordinate: this.getCoord(e.id, e.lng * 1, e.lat * 1),
        imagePath: HostConfig.Path + type + ".png",
        // text: `${e.name}`,
        fontSize: 20,
        displayMode: 2,
        range: [1, 80000],
        imageSize: [48, 58],
        anchors: [-24, 58],
        // textBackgroundColor: "RGB(0, 213, 255)",
        // fontColor: "RGB(255, 255, 255)",
      };

      // 添加点位
      await __g?.marker.add(marker);

      // 聚焦点位
      await __g?.marker.focus([marker.id], 500);

      eventBus.$emit("handleBdcDialog", true, {
        UserData: JSON.stringify(item),
        Id: "bdc-t1" + item.id,
      });
    },
    closedWin() {
      this.visible = false;
    },
    // 改变页码
    changePage(val) {
      console.log(val);
      this.getWarningLit();
    },
  },
};
</script>

<style lang="scss" scoped>
.box-02 {
  ul {
    li {
      cursor: pointer;
      height: 42px;
      display: grid;
      grid-template-columns: 120px auto 60px;
      align-items: center;
      font-size: 14px;
      font-weight: 400;
      background-color: rgba(31, 168, 239, 0.2);

      &:nth-child(even) {
        background-color: rgba(31, 168, 239, 0.1);
      }

      div {
        &:nth-child(1) {
          padding-left: 8px;
        }

        &:nth-child(2) {
          padding-left: 8px;
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
        }

        &:nth-child(3) {
          text-align: center;

          .type {
            display: flex;
            padding: 2px 0;
            justify-content: center;
            align-items: center;
            gap: 10px;
            border-radius: 2px;
            font-size: 12px;
            width: 42px;
            margin: 0 auto;
          }

          .normal {
            border: 1px solid #00d5ff;
            background: #00d5ff33;
            color: #00d5ff;
          }

          .emergent {
            border: 1px solid #ffa076;
            background: #ffa07633;
            color: #ffa076;
          }
        }
      }
    }
  }

  ul.head {
    margin-top: 8px;

    li {
      color: rgb(0, 213, 255);
      font-size: 16px;
      font-weight: 400;
      background-color: rgba(29, 80, 120, 0.8);
    }
  }

  ul.content {
    height: 464px;
  }
}
.page-box {
  margin-top: 5px;
  width: 100%;
  display: flex;
  justify-content: flex-end;
  position: relative;

  ::v-deep .el-pagination__total {
    position: absolute;
    left: 0;
    color: #fff;
  }

  ::v-deep .btn-prev {
    background: #606465;
    color: #fff;
    width: 28px;
    min-width: 28px;
    border-radius: 50%;
  }

  ::v-deep .btn-next {
    background: #606465;
    color: #fff;
    width: 28px;
    min-width: 28px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  ::v-deep .el-pager li {
    background: transparent;
    // border-radius: 50%;
    color: #fff;
  }

  ::v-deep .el-pager {
    .number {
      width: 28px;
      min-width: 28px;
      height: 28px;
      border-radius: 50%;
    }

    .active {
      background: #409eff;
    }
  }
}
</style>
