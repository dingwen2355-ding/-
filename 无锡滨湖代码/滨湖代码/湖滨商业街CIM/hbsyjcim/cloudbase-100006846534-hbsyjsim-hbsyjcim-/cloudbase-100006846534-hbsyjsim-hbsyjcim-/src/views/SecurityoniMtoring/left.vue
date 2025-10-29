<template>
  <div v-if="visible" class="left-bg">
    <div class="TOP">
      <IocTitle title="视频监控概览" @change="closedWin()" />
      <div class="all" @click="All()"></div>

      <div class="Flex">
        <div class="ordinary" @click="Monitor(0)">
          <div class="position">
            <div class="title">普通监控</div>
            <div class="number" style="color: #00d5ff">{{ Video }}</div>
          </div>
        </div>

        <div class="eagle" @click="Monitor(1)">
          <div class="position">
            <div class="title">鹰眼监控</div>
            <div class="number" style="color: #3d94ff">{{ Eagle }}</div>
          </div>
        </div>

        <div class="ai" @click="Monitor(2)">
          <div class="position">
            <div class="title">AI监控</div>
            <div class="number" style="color: #1bc078">{{ Ai }}</div>
          </div>
        </div>
      </div>

      <div class="block">
        <h3>视频监控</h3>

        <div>
          <!-- 搜索框 -->
          <div class="search">
            <el-input class="query-inp" placeholder="请输入视频名称" v-model="input" clearable @input="handleInputSearch">
              <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>
          </div>

          <!-- 区域切换 -->
          <div class="Flex">
            <div v-for="(item, index) in RegionData" :key="item.id">
              <div :class="ActiveKey == index ? 'region_a' : 'region'" @click="RegionAClick(item, index)">
                <div class="content">
                  <div class="name">{{ item.name }}</div>
                  <div class="number">
                    {{ item.number }}<span>&nbsp;个</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 视频列表  -->
          <div ref="customList" class="video_block" v-if="MonitorItem == 0 || MonitorItem == 1 || MonitorItem == 2">
            <div v-if="List.length != 0">
              <div class="video_list" v-for="(item, index) in List" :key="item.id">
                <div class="video_img">
                  <div class="flex_">
                    <div>
                      <div class="text">{{ item.channel_name }}</div>
                      <div class="sign">治安</div>
                    </div>

                    <div class="play" @click="VideoShow(item)">
                      <div class="play_text">播放</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="none" v-else>暂无数据</div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  GetVideonum,
  GetAreaVideonum,
  GetSecurityVideo,
} from "@/utils/api/SecurityoniMtoring";

import IocTitle from "@/components/title/index";
import { MarkerTool } from "@/utils/tool/MarkerTool";
import { FenceTool } from "@/utils/tool/FenceTool.js";

export default {
  name: "SecurityoniMtoringLeft",

  components: {
    IocTitle,
  },

  data() {
    return {
      visible: true,
      MonitorItem: 0, //视频监控 切换
      input: "", //搜索的值
      List: [], //列表数据
      ListSearch: [], //搜索用的列表值
      RegionData: [
        { name: "东广场", number: 0, id: 0 },
        { name: "西广场", number: 0, id: 1 },
        { name: "主街区", number: 0, id: 2 },
      ],
      ActiveType: 0, //选中的
      ActiveKey: 0, //选中下标

      Video: 0,
      Ai: 0,
      Eagle: 0,
    };
  },

  methods: {
    //搜索列表
    handleInputSearch(value) {
      // console.log("-------输入框的值：", value);
      if (value == "") {
        this.List = this.ListSearch;
      } else {
        var sList = [];
        for (var i = 0; i < this.ListSearch.length; i++) {
          if (this.ListSearch[i].channel_name.includes(value)) {
            sList.push(this.ListSearch[i]);
          }
        }
        this.List = sList;
      }
    },

    // 隐藏当前窗口
    closedWin() {
      this.visible = false;
    },

    // 全部显示坐标点
    All() {
      console.log("全部显示坐标点");
      const markerTool = new MarkerTool();
      markerTool.Clear();
      markerTool.getPointAllShow();
    },

    //视频监控 切换 0普通 1鹰眼 2AI
    Monitor(item) {
      const markerTool = new MarkerTool();
      markerTool.Clear();
      if (item == 0) {
        markerTool.getPointShow("video", "普通");
      }

      if (item == 1) {
        this.$message({
          message: '暂无数据',
        });
      }

      if (item == 2) {
        markerTool.getPointShow("aivideo", "ai");
      }
      console.log("0普通 1鹰眼 2AI", item);
    },

    // 区域切换
    RegionAClick(item, index) {
      console.log("区域切换", item.id, index);
      this.MonitorItem = item.id;
      if (this.ActiveKey == index) return;
      this.ActiveType = item.id; //选中的
      this.ActiveKey = index; //选中下标
      this.getSecurityVideo(item.name);
      this.scrollToTop();
      this.input = '';
    },

    scrollToTop() { 
      if (this.List.length != 0) {
        this.$refs.customList.scrollTop = 0;
      }
    },

    //视频弹窗
    VideoShow(item) {
      const model = this.findComponentUpward(this, "IocLayout");
      const son = this.findComponentDownward(model, "VideoPop");
      son.otherCloseVideo();
      son.getVideoUrl(item.channel_id);
      son.name = item.channel_name;
      son.videoShow = true;
      console.log("修改子组件属性---成功");
    },

    getVideonum() {
      GetVideonum({}).then((res) => {
        console.log("-视频监控概览-请求到接口了------");
        console.log(res);
        if (res.code == 200) {
          this.Video = res.data[1].count;
          this.Ai = res.data[0].count;
          this.Eagle = res.data[2].count;
        }
      });
    },

    getAreaVideonum() {
      GetAreaVideonum({}).then((res) => {
        console.log("-获取区分视频个数-请求到接口了------");
        console.log(res);
        if (res.code == 200) {
          this.RegionData[0].number = res.data[0].count;
          this.RegionData[2].number = res.data[1].count;
          this.RegionData[1].number = res.data[2].count;
        }
      });
    },

    //视频列表接口 --
    getSecurityVideo(name) {
      var that = this;
      GetSecurityVideo({
        area: name,
      }).then((res) => {
        console.log("-安防监控-获取设备信息-视频列表-请求到接口了------");
        console.log(res);
        if (res.code == 200) {
          that.List = res.data;
          that.ListSearch = res.data;
        }
      });
    },
  },

  created() {
    this.getVideonum();
    this.getAreaVideonum();
    this.getSecurityVideo("东广场");
  },

  beforeMount() {
    const markerTool = new MarkerTool();
    markerTool.Clear();
  },

  mounted() {
    const markerTool = new MarkerTool();
    markerTool.getPointAllShow();

    const fence = new FenceTool();
    fence.Show();
  },

  destroyed() { //销毁
    FenceTool.Clear();
  },
};
</script>

<style lang="scss" scoped>
.TOP {
  margin-top: 16px;
  position: relative;

  .all {
    width: 108px;
    height: 25px;
    position: absolute;
    top: 4px;
    left: 8px;
    cursor: pointer;
  }

  .Flex {
    display: flex;

    .ordinary {
      margin: 8px 8px 0 0;
      background-image: url("@/assets/SecurityoniMtoring/img_camera_jiankong.png");
      background-size: 100% 100%;
      width: 137px;
      height: 64px;
      cursor: pointer;
    }

    .eagle {
      margin: 8px 8px 0 0;
      background-image: url("@/assets/SecurityoniMtoring/img_camera_eagle.png");
      background-size: 100% 100%;
      width: 137px;
      height: 64px;
      cursor: pointer;
    }

    .ai {
      margin-top: 8px;
      background-image: url("@/assets/SecurityoniMtoring/img_camera_AI.png");
      background-size: 100% 100%;
      width: 137px;
      height: 64px;
      cursor: pointer;
    }

    .position {
      margin: 13px 0 0 53px;

      .title {
        color: #ffffff;
        font-size: 15px;
        font-weight: 400;
        cursor: pointer;
      }

      .number {
        font-size: 18px;
        font-weight: 500;
      }
    }
  }

  .block {
    width: 428px;
    height: 28px;
    line-height: 28px;
    background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 101.22%);
    margin: 23px 0 8px 0;

    h3 {
      color: #ffffff;
      font-size: 18px;
      font-weight: 400;
      margin-left: 17px;
    }

    .search {
      margin: 8px 0;
    }

    .region {
      margin: 8px 8px 16px 0;
      background-image: url("@/assets/SecurityoniMtoring/icon_camdata_bg@2x.png");
      background-size: 100% 100%;
      width: 138px;
      height: 60px;
      cursor: pointer;
    }

    .region_a {
      margin: 8px 8px 16px 0;
      background-image: url("@/assets/SecurityoniMtoring/icon_camdata_act_bg@2x.png");
      background-size: 100% 100%;
      width: 138px;
      height: 60px;
      cursor: pointer;
    }

    .content {
      margin-left: 11px;

      .name {
        color: #ffffff;
        font-size: 14px;
        font-weight: 400;
      }

      .number {
        color: #00d5ff;
        font-size: 17px;
        font-weight: 700;

        span {
          color: #a0bde0;
          font-size: 15px;
          font-weight: 400;
        }
      }
    }

    .video_block {
      height: 504px;
      overflow-y: auto;

      .video_list {
        margin-bottom: 8px;

        .video_img {
          background-image: url("@/assets/SecurityoniMtoring/img_camlist_bg@2x.png");
          background-size: 100% 100%;
          width: 420px;
          height: 64px;

          .flex_ {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin: 0 8px 0 18px;
            padding: 2px 0 8px 0;

            .text {
              font-size: 15px;
              margin-bottom: 4px;
            }

            .sign {
              width: 46px;
              height: 22px;
              text-align: center;
              line-height: 22px;
              border-radius: 5px;
              border: 1px solid #00d5ff;
              color: #00d5ff;
              font-size: 15px;
            }

            .play {
              background-image: url("@/assets/SecurityoniMtoring/img_btn_play_bg@2x.png");
              background-size: 100% 100%;
              width: 64px;
              height: 34px;
              text-align: center;
              line-height: 34px;

              .play_text {
                color: #00d5ff;
                font-size: 16px;
                font-weight: 400;
                cursor: pointer;
              }
            }
          }
        }
      }

      .none {
        font-size: 18px;
        text-align: center;
        margin: 46% 0;
      }
    }
  }
}

::v-deep .el-input__icon {
  color: #00d5ff;
  font-size: 20px;
  margin-left: 10px;
}

::v-deep .el-input__inner {
  width: 428px;
  background-color: transparent;
  border: 1px solid #2e70bb;
  color: #fff;
  border-radius: 0;
  font-size: 15px;
  padding-left: 40px;
}
</style>

<style>
.el-message {
  position: fixed;
  left: 50%;
  top: 530px;
  background-color: #095261cd;
  border: 1px solid #00d5ff;
}
.el-message .el-icon-info {
    color: #fff;
}
.el-message--info .el-message__content {
    color: #fff;
}
</style>
