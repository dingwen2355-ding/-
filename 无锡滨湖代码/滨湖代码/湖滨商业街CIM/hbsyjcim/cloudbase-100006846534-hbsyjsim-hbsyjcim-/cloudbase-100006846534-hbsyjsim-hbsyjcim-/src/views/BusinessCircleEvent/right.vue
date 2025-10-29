<template>
  <div v-if="visible" class="right-bg">
    <div class="TOP">
      <IocTitle title="中心可视化概览" @change="closedWin()" />

      <div>
        <div class="background"><h3>事件高发区域</h3></div>
        <div class="block">
          <div class="rounds_img">
            <div>
              <div class="Left_">
                <div class="title">西广场</div>
                <div class="number">{{ West }}</div>
              </div>

              <div class="Centre_">
                <div class="title">东广场</div>
                <div class="number">{{ East }}</div>
              </div>

              <div class="Right_">
                <div class="title">主街区</div>
                <div class="number">{{ Host }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div>
        <div class="background"><h3>事件处置时长</h3></div>
        <div class="block">
          <div class="content-right">
            <dl class="head">
              <dd>
                <div>类型</div>
                <div>数量</div>
                <div>时限</div>
                <div>平均时长</div>
                <div>环比</div>
              </dd>
            </dl>

            <div v-if="List.length != 0">
              <dl class="dl-content">
                <dd v-for="(item, index) in List" :key="index">
                  <div>{{ item.name || "-" }}</div>
                  <div>{{ item.count || "-" }}</div>
                  <div>{{ item.shc }}小时</div>
                  <div>-</div>
                  <div>{{ item.persent }}%</div>
                </dd>
              </dl>
            </div>

            <div class="none" v-else>暂无数据</div>

          </div>
        </div>
      </div>

      <div>
        <div class="background"><h3>事件高发类型</h3></div>
        <div class="block">
          <div class="Progress">

            <div v-if="Data.length != 0">
              <div class="top" v-for="(item, index) in Data" :key="index">
                <div class="text">{{ item.name }}<span>{{ item.count }}宗</span>
                </div>
                <el-progress :percentage="Number(item.persent)"></el-progress>
              </div>
            </div>

            <div v-else class="none">暂无数据</div>

          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import IocTitle from "@/components/title/index";

import { GetEventDisposal, GetEventGflx } from "@/utils/api/BusinessCircleEvent";

export default {
  name: "BusinessCircleEventRight",
  components: {
    IocTitle,
  },

  data() {
    return {
      visible: true,
      East: '-',
      Host: '-',
      West: '-',
      List: [],
      Data: [],
    };
  },

  methods: {
    closedWin() {
      this.visible = false;
    },

    getEventDisposal() {
      GetEventDisposal({
        pageNo: 1,
        pageSize: 1000,
      }).then((res) => {
        console.log("-事件处置时长-请求到接口了------");
        console.log(res);
        if( res.code == 200 ) {
          console.log(res.data.data.list);
          this.List = res.data.data.list;
        }
      });
    },

    getEventGflx() {
      GetEventGflx({}).then((res) => {
        console.log("-事件高发类型-请求到接口了------");
        console.log(res);
        if( res.code == 200 ) {
          console.log(res.data.data);
          this.Data = res.data.data;
        }
      });
    },

  },

  created() {
    this.getEventDisposal();
    this.getEventGflx();
  },
};
</script>

<style lang="scss" scoped>
.TOP {
  margin-top: 16px;

  .background {
    width: 428px;
    height: 28px;
    line-height: 28px;
    background: linear-gradient(90deg, #00d5ff4d 0%, #00d5ff00 101.22%);
    margin-top: 23px;

    h3 {
      color: #ffffff;
      font-size: 18px;
      font-weight: 400;
      margin-left: 17px;
    }
  }

  .block {
    width: 428px;
    position: relative;

    .rounds_img {
      margin-top: 21px;
      margin-left: 10px;
      background-image: url("@/assets/BusinessCircleEvent/img_data_cycle_bg@2x.png");
      background-size: 100% 100%;
      width: 414px;
      height: 125px;

      .title {
        color: #a0bde0;
        font-size: 16px;
        font-weight: 400;
      }

      .number {
        color: #00d5ff;
        text-align: center;
        font-size: 20px;
        font-weight: 500;
      }

      .Left_ {
        position: absolute;
        top: 42%;
        left: 22%;
      }

      .Centre_ {
        position: absolute;
        top: 35%;
        left: 47%;
      }

      .Right_ {
        position: absolute;
        top: 42%;
        right: 20%;
      }
    }

    .content-right {
      width: 100%;
      dl {
        dd {
          height: 42px;
          display: grid;
          grid-template-columns: 90px 70px 90px 90px 80px;
          align-items: center;
          font-size: 14px;
          font-weight: 400;
          background-color: rgba(31, 168, 239, 0.1);
          &:nth-child(even) {
            background-color: rgba(31, 168, 239, 0.2);
          }
          div {
            &:nth-child(1) {
              margin-left: 10px;
            }
            &:nth-child(2) {
              text-align: center;
            }
            &:nth-child(3) {
              text-align: center;
            }
            &:nth-child(4) {
              text-align: center;
            }
            &:nth-child(5) {
              text-align: center;
            }
          }
        }
      }
      dl.head {
        margin-top: 8px;
        dd {
          color: rgb(0, 213, 255);
          font-size: 16px;
          font-weight: 400;
          background-color: rgba(29, 80, 120, 0.8);
        }
      }
      dl.dl-content {
        height: 252px;
        overflow-y: auto;
      }

      .none {
          height: 252px;
          line-height: 252px;
          font-size: 15px;
          text-align: center;
      }
    }

    .Progress {
      margin-top: 10px;
      height: 150px;
      overflow-y: auto;

      .top{
        margin-bottom: 5px;

        .text {
          color: #ffffff;
          font-size: 13px;
          font-weight: 700;
          margin-bottom: 5px;
          position: relative;

          span {
            display: inline-block;
            width: 60px;
            text-align: right;
            position: absolute;
            right: 70px;
          }
        }
      }

      .none {
        height: 150px;
        line-height: 150px;
        font-size: 15px;
        text-align: center;
      }
    }

  }
}

::v-deep .el-progress-bar__inner {
    background: linear-gradient(90deg, #5071FF 0%, #00D5FF 100%);
}
::v-deep .el-progress-bar__outer {
    border-radius: 0 !important;
    width: 420px !important;
    background-color: #ffffff33 !important;
}
::v-deep .el-progress__text {
    margin-top: -40px !important;
    margin-left: -1px !important;
    font-size: 13px !important;
    color: #A0BDE0 !important;
    width: 43px !important;
    text-align: right !important;
}

</style>
