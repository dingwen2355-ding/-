<template>
  <div class="SimilarThings module">
    <SmallTitle size="big" />
    <div v-if="list.length === 0 || !precinct" class="untitle">
      <div class="img">
        <img src="../../../../assets/global/暂无事件.png" alt />
      </div>
    </div>
    <div class="content" v-else>
      <div class="dis-panel" v-for="(item, index) in list" :key="index">
        <div class="content-right">
          <div class="c-title">{{ item.event_title }}</div>
          <div class="c-tip">
            <i class="el-icon-time" style="font-size: 50rem; color: #b3e1fb"></i>
            <span class="tip-time">{{ item.happen_time }}</span>
            <span class="icon-place"></span>
            <span class="tip-place" :title="item.address">{{ item.address }}</span>
          </div>
          <div class="c-con">{{ item.desc }}</div>
        </div>
      </div>
    </div>
    <div class="tip1">
      <ul>
        <li
          v-for="(item, index) in typeName"
          :key="index"
          :class="{ active: index === activeIndex }"
          @click="handleClick(index)"
        >{{ item }}</li>
      </ul>
      <!-- <span v-for="item in typeName" :key="item"  @click="changeType">{{ item }}</span> -->
    </div>
    <!-- <div class="tip2" @click="openMore">更多>></div> -->
    <more-info-dialog :dialogVisibleFlag.sync="dialogVisibleFlag" :type="type"></more-info-dialog>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import MoreInfoDialog from '@/components/MoreInfoDialog.vue'
import Axios from '@/utils/request'

export default {
  name: 'SimilarThings',
  components: { SmallTitle, MoreInfoDialog },
  data() {
    return {
      dialogVisibleFlag: false,
      precinct: this.$route.query.eventId,
      type: 2,
      // typeName: {
      //   1: '类似事件',
      //   2: '附近事件'
      // },
      typeName: ['类似事件', '附近事件'],
      list: [],
      activeIndex: 1
    }
  },
  mounted() {
    this.eventInfoListByType()
  },
  activated() {
    this.eventInfoListByType()
  },
  methods: {
    handleClick(index) {
      this.activeIndex = index
      if (this.activeIndex === 0) {
        this.type = 1
      } else {
        this.type = 2
      }
      this.list = []
      this.eventInfoListByType()
    },
    openMore() {
      this.dialogVisibleFlag = true
    },
    changeType() {
      if (this.type === 1) {
        this.type = 2
      } else {
        this.type = 1
      }
      this.eventInfoListByType()
    },
    eventInfoListByType() {
      let url = `/apis/Command/eventInfoListByType?type=${this.type}`
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.list = res.data.data.list
          if (this.type === 1) {
            this.list.push(
              ...[
                {
                  event_title: '污水外溢',
                  desc: '上报水务集团，提前打开收水井，列入重点巡检点位。',
                  happen_time: '2024-06-21 12:00:00',
                  event_type_name: '自然灾害',
                  area: '具区路市委党校',
                  event_id: 75
                },
                {
                  event_title: '污水外溢、树叶堵塞收水井',
                  desc: '联合环卫公司提前清理收水井周边落叶，降雨期间打开收水井，遇污水外溢联系水务集团。落实人员和设备现场备汛。',
                  happen_time: '2024-06-21 12:00:00',
                  event_type_name: '自然灾害',
                  area: '中南西路蠡湖大道两侧',
                  event_id: 75
                },
                {
                  event_title: '出水口封堵',
                  desc: '封堵排口已拆除，现场配备一台2000m³/h移动泵车',
                  happen_time: '2024-06-21 12:00:00',
                  event_type_name: '自然灾害',
                  area: '清晏路蠡湖大道口',
                  event_id: 75
                },
                {
                  event_title: '污水外溢',
                  desc: '提前打开收水了，已联系水务集团现场架设动力站。',
                  happen_time: '2024-06-21 12:00:00',
                  event_type_name: '自然灾害',
                  area: '渔港路蠡湖小镇',
                  event_id: 75
                },
                {
                  event_title: '山水截流不到位',
                  desc: '落实移动泵车一台',
                  happen_time: '2024-06-21 12:00:00',
                  event_type_name: '自然灾害',
                  area: '缘溪道雪浪山景区北门',
                  event_id: 75
                },
                {
                  event_title: '路边停车场雨水排入',
                  desc: '完成地块淌水严重区域筑坝隔离，现场落实人员值守。',
                  happen_time: '2024-06-21 12:00:00',
                  event_type_name: '自然灾害',
                  area: '缘溪道南湖中路路口',
                  event_id: 75
                }
              ]
            )
          }
        }
      })
      // if (sessionStorage.getItem('eventNaber') && this.type === 2) {
      //   this.list = JSON.parse(sessionStorage.getItem('eventNaber')).data
      // } else {
      //   let url = `/apis/Command/eventInfoListByType?type=${this.type}`
      //   Axios.get(url).then((res) => {
      //     if (res.data.code === 200) {
      //       let data = res.data.data.list
      //       this.list = data || []
      //       if (this.type === 2) {
      //         sessionStorage.setItem('eventNaber', JSON.stringify({ data }))
      //       }
      //     }
      //   })
      // }
    }
  }
}
</script>

<style lang="scss" scoped>
.SimilarThings {
  width: 2840rem;
  // height: 1850rem;
  position: absolute;
  top: 2880rem;
  left: 128rem;
  z-index: 2;

  .untitle {
    width: 100%;
    //height: 100%;
    height: 628rem;
    text-align: center;
    border: 8rem solid #002e7b;

    .img {
      margin-top: 60rem;
    }
  }
  .content {
    padding: 48rem 88rem;
    height: 628rem;
    overflow-y: scroll;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    border-radius: 8rem;
    .dis-panel:first-of-type {
      margin-top: 10rem;
    }
    .dis-panel {
      margin-top: 15rem;
      margin-bottom: 15rem;
      width: 100%;
      min-height: 115rem;
      height: auto;
      display: flex;
      .con-line-4 {
        margin-top: 50rem;
        display: flex;
        align-items: center;
        flex-wrap: nowrap;
        .icon-time {
          width: 52rem;
          height: 52rem;
          display: inline-block;
          margin-top: -5rem;
          color: #77b1e2;
          // background: url('../../../../assets/left/217时间@2x.png') no-repeat;
          // background-size: 100% 100%;
        }
        .icon-place {
          margin-left: 110rem;
          width: 52rem;
          height: 52rem;
          display: inline-block;
          background: url('../../../../assets/left/_定位小@2x.png') no-repeat;
          background-size: 100% 100%;
          cursor: pointer;
        }
        .tip-time {
          margin-left: 3rem;
          font-size: 48rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #ffffff;
          // line-height: 24rem;
          opacity: 0.5;
          white-space: nowrap;
        }
        .tip-place {
          margin-left: 5rem;
          font-size: 48rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #ffffff;
          // line-height: 24rem;
          opacity: 0.5;
          width: 1300rem;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          // cursor: pointer;
        }
      }

      .icon-left {
        margin-top: 6rem;
        width: 30rem;
        height: 30rem;
        background-image: url('../../../../assets/left/告警实心@2x.png');
        background-size: 100% 100%;
      }
      .content-right {
        width: calc(100% - 30rem);
        height: auto;
        .c-title {
          font-size: 64rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          margin-left: 20rem;
          color: rgba(238, 139, 141, 1);
          // line-height: 35rem;
        }
        .c-tip {
          margin: 20rem 0 30rem;
          padding-left: 38rem;
          display: flex;
          align-items: center;
          .icon-time {
            width: 68rem;
            height: 64rem;
            display: inline-block;
            margin-top: -5rem;
            color: #77b1e2;
            // background: url('../../../../assets/left/217时间@2x.png') no-repeat;
            // background-size: 100% 100%;
          }
          .icon-place {
            margin-left: 20rem;
            width: 68rem;
            height: 64rem;
            display: inline-block;
            background: url('../../../../assets/left/_定位小@2x.png') no-repeat;
            background-size: 100% 100%;
            cursor: pointer;
          }
          .tip-time {
            margin-left: 3rem;
            font-size: 64rem;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #ffffff;
            // opacity: 0.5;
            white-space: nowrap;
          }
          .tip-place {
            margin-left: 5rem;
            font-size: 64rem;
            font-family: Source Han Sans CN;
            font-weight: 400;
            color: #ffffff;
            // opacity: 0.5;
            width: 450rem;
            display: inline-block;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            // cursor: pointer;
          }
        }
        .c-con {
          font-size: 56rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #ececec;
          line-height: 84rem;
          padding-left: 30rem;
        }
      }
    }
  }
  .tip1 {
    position: absolute;
    top: 86rem;
    left: 120rem;
    width: 90rem;
    height: 20rem;
    font-size: 18rem;
    font-family: SourceHanSansCN;
    font-weight: 500;
    // color: #42a1e4;
    line-height: 48rem;
    ul {
      display: flex;
      width: 1000rem;
      li {
        font-size: 96rem;
        font-family: 'YouShe';
        font-weight: 500;
        color: #42a1e4;
        line-height: 80rem;
        margin-left: 100rem;
        cursor: pointer;
      }
      li.active {
        font-size: 96rem;
        font-family: 'YouShe';
        font-weight: 500;
        color: #ffffff;
      }
    }
  }
  .tip2 {
    position: absolute;
    top: 0rem;
    right: 0rem;
    width: 70rem;
    height: 18rem;
    font-size: 16rem;
    font-family: SourceHanSansCN;
    font-weight: 400;
    color: #ffd56f;
    line-height: 24rem;
    cursor: pointer;
  }
}
</style>
