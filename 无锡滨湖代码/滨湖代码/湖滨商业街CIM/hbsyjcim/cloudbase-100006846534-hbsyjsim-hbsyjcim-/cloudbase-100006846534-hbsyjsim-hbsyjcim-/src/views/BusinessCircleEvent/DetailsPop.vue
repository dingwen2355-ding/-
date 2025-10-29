<template>
  <div class="DetailsPop" v-show="detailsShow == true">
    <div class="DetailsPop_img">
      <div class="flex">
        <div class="title">工单详情</div>

        <div class="close" @click="Close()">
          <img
            class="close_img"
            src="@/assets/icon_btn_close.png"
            alt="close"
          />
        </div>
      </div>

      <div class="Details">
        <!-- 工单信息 -->
        <div class="Tilte_">
          <i></i>
          <span>工单信息</span>
        </div>

        <div class="Details_Text">
          <div class="Flex_">
            <div class="Title">工单地点:</div>
            <div class="Text">{{ Data.location_desc }}</div>
          </div>

          <div class="line"></div>

          <div class="Flex_">
            <div class="Title">上报时间:</div>
            <div class="Text">{{ Data.alter_time }}</div>
          </div>

          <div class="line"></div>

          <div class="Flex_">
            <div class="Title">工单编号:</div>
            <div class="Text">{{ Data.id }}</div>
          </div>

          <div class="line"></div>

          <div class="Flex_">
            <div class="Title">工单类型:</div>
            <div class="Text">{{ Data.alter_type }}</div>
          </div>

          <div class="line"></div>

          <div class="Flex_">
            <div class="Title">工单来源:</div>
            <div class="Text">{{ Data.alter_source }}</div>
          </div>

          <div class="line"></div>

          <div class="Flex_">
            <div class="Title">上报人:</div>
            <div class="Text">{{ Data.device_code }}</div>
          </div>

          <div class="line"></div>

          <div class="Flex_">
            <div class="Title">情况说明:</div>
            <div class="Text">{{ Data.alter_context }}</div>
          </div>

          <div class="line"></div>
        </div>

        <!-- 处理详情 -->
        <div class="Tilte_">
          <i></i>
          <span>处理详情</span>
        </div>

        <div class="Details_Text">
          <div class="Flex_">
            <div class="Title">处理时间:</div>
            <div class="Text">-</div>
          </div>

          <div class="line"></div>

          <div class="Flex_">
            <div class="Title">工单状态:</div>
            <div class="Text" style="color: #00d5ff; font-weight: 600" v-if="Data.status == '待处理'">待处理</div>
            <div class="Text" style="color: #1bc078; font-weight: 600;" v-else>{{ Data.status }}</div>
          </div>

          <div class="line"></div>

          <div class="Flex_">
            <div class="Title">累计用时:</div>
            <div class="Text">
              -
              <!-- <span>未超时</span> -->
              <!-- <span style="color: #F23D49; border: 1px solid #F23D49;">超时</span> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DetailsPop",

  components: {},

  data() {
    return {
      detailsShow: false,
      Data: {},
    };
  },

  methods: {
    Close() {
      console.log("关闭商圈事件弹窗");
      this.detailsShow = false;
    },
  },

  beforeMount() {},

  mounted() {},
};
</script>

<style lang="scss" scoped>
.DetailsPop {
  position: fixed;
  top: 15vh;
  left: 50%;
  transform: translateX(-50%);

  .DetailsPop_img {
    background-image: url("@/assets/BusinessCircleEvent/img_windows_360_people_bg@2x.png");
    background-size: 100% 100%;
    width: 360px;
    height: 642px;

    .flex {
      display: flex;
      justify-content: space-between;
    }

    .title {
      font-size: 15px;
      transform: translate(58px, 17px);
    }

    .close {
      transform: translate(0, 13px);
      margin-right: 10px;
      cursor: pointer;
      width: 22px;
      height: 22px;

      .close_img {
        width: 100%;
        height: 100%;
      }
    }

    .Details {
      overflow-y: auto;
      width: 355px;
      height: 580px;
      margin: 0 auto;
      margin-top: 7%;
      padding: 0 24px;
      box-sizing: border-box;

      .Tilte_ {
        margin-top: 20px;

        i {
          display: inline-block;
          width: 3px;
          height: 13px;
          border-radius: 5px;
          opacity: 1;
          background: #00d5ff;
          margin-right: 8px;
        }

        span {
          color: #00d5ff;
          font-size: 16px;
          font-weight: 700;
        }
      }

      .Details_Text {
        .Flex_ {
          display: flex;
          margin-top: 11px;

          .Title {
            width: 75px;
            color: #bcd1e9;
            font-size: 15px;
            font-weight: 400;
          }

          .Text {
            width: 270px;
            margin-left: 8px;
            color: #ffffff;
            font-size: 16px;
            font-weight: 400;

            span {
              display: inline-block;
              margin-left: 8px;
              padding: 2px 6px;
              border: 1px solid #1bc078;
              border-radius: 5px;
              color: #1bc078;
              font-size: 12px;
              font-weight: 500;
            }
          }
        }

        .line {
          margin-top: 12px;
          width: 100%;
          height: 1px;
          background: linear-gradient(90deg, #00d5ff 0%, #00d5ff00 101.22%);
        }
      }
    }
  }
}
</style>