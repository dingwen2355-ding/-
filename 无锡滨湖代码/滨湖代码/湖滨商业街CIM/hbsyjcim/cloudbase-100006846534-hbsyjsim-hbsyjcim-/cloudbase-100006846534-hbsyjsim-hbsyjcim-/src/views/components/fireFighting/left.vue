<template>
  <div v-if="visible" class="left-bg">
    <IocTitle title="消防设施" @change="closedWin()" />
    <div class="box1">
      <div class="box1-top">
        <div class="top-item">
          <img class="item-img" src="@/assets/fireFighting/icon_xiaofang_data_1.png" alt="">
          <div class="item-text-box">
            <div class="item-tip">消防栓</div>
            <div class="item-text">{{ supplies.xfs }} <span>个</span></div>
          </div>
        </div>
        <div class="top-item">
          <img class="item-img" src="@/assets/fireFighting/icon_xiaofang_data_2.png" alt="">
          <div class="item-text-box">
            <div class="item-tip">人工水源</div>
            <div class="item-text">{{ supplies.rgsy }} <span>处</span></div>

          </div>

        </div>
      </div>
      <div class="box1-center">
        <div class="center-item">
          <img class="center-img" src="@/assets/fireFighting/Frame 85@2x.png" alt="">
          <div class="center-text-box">
            <div class="center-tip">消防救援物资</div>
            <div class="center-text">{{ supplies.xfjywz }} <span>吨</span></div>
          </div>
        </div>
        <div class="center-item">
          <img class="center-img" src="@/assets/fireFighting/Frame 85@2x(1).png" alt="">
          <div class="center-text-box">
            <div class="center-tip">电梯</div>
            <div class="center-text">{{ supplies.dt }} <span>部</span></div>
          </div>
        </div>
        <div class="center-item">
          <img class="center-img" src="@/assets/fireFighting/Frame 85@2x(2).png" alt="">
          <div class="center-text-box">
            <div class="center-tip">排烟阀</div>
            <div class="center-text">{{ supplies.pyf }} <span>个</span></div>
          </div>
        </div>
      </div>
      <div class="box1-center">
        <div class="center-item">
          <img class="center-img" src="@/assets/fireFighting/Frame 85@2x(3).png" alt="">
          <div class="center-text-box">
            <div class="center-tip">排气阀</div>
            <div class="center-text">{{ supplies.pqf }} <span>个</span></div>
          </div>
        </div>
        <div class="center-item">
          <img class="center-img" src="@/assets/fireFighting/Frame 85@2x(4).png" alt="">
          <div class="center-text-box">
            <div class="center-tip">烟感探测器</div>
            <div class="center-text">{{ supplies.ygtcq }} <span>个</span></div>
          </div>
        </div>
        <div class="center-item">
          <img class="center-img" src="@/assets/fireFighting/Frame 85@2x(5).png" alt="">
          <div class="center-text-box">
            <div class="center-tip">温感探测器</div>
            <div class="center-text">{{ supplies.wgtcq }} <span>个</span></div>
          </div>
        </div>
      </div>
      <!-- <div class="box1-item">
        <img class="item-img" src="@/assets/fireFighting/icon_xiaofang_data_3.png" alt="">
        <div class="item-text">{{ supplies.rescue }} <span>吨</span></div>
        <div class="item-tip">消防救援物资</div>
      </div> -->
    </div>
    <IocTitle class="title-top" title="高危物品" :closeVisible="false" />
    <div class="box2">
      <div class="box2-item">
        <img class="box2-item-img" src="@/assets/fireFighting/icon_databox_2col_type@2x.png" alt="">
        <div class="box2-item-text">
          <div class="text-top">危险品</div>
          <div class="text-down">{{ highRisk.risk }}<span>件</span></div>
        </div>
      </div>
      <div class="box2-item">
        <img class="box2-item-img" src="@/assets/fireFighting/icon_databox_2col_type@2x_2.png" alt="">
        <div class="box2-item-text">
          <div class="text-top">易燃易爆品</div>
          <div class="text-down">{{ highRisk.flammable }}<span>件</span></div>
        </div>
      </div>
    </div>
    <IocTitle title="报警类型" :closeVisible="false" />
    <div class="box3">
      <div id="pie-box"></div>
      <dl>
        <dd v-for="(item, index) in alarmType.typeList" :key="index">
          <i></i>
          <span class="dis-name">{{ item.type }}</span>
          <span>{{ item.number }}</span>
        </dd>
      </dl>
    </div>
    <IocTitle title="总报警处理情况" :closeVisible="false" />
    <div class="box4">
      <div id="circle-box"></div>
      <div class="box4-right">
        <div class="box4-right-item">
          <div class="right-item-top">
            <div class="icon"></div>
            <div class="box4-right-item-top">
              报警总数
            </div>
          </div>
          <div class="box4-right-item-down">{{ alarmHandle.total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}
            <span>家</span>
          </div>

        </div>
        <div class="box4-right-item">
          <div class="right-item-top">
            <div class="icon"></div>
            <div class="box4-right-item-top">
              已处理数
            </div>
          </div>
          <div class="box4-right-item-down">{{ alarmHandle.handleNumber }} <span>单</span> </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import IocTitle from '@/components/title'
import * as CoordTransform from '@/utils/coordtransform'
import request from '@/utils/request'
import "echarts-liquidfill";
export default {
  name: 'fireFightingLeft',
  components: {
    IocTitle
  },
  data() {
    return {
      visible: true,
      supplies: {
        xfs: 652,//消防栓
        rgsy: '-',//人工水源
        xfjywz: '-',//消防救援物资
        dt: '-',//电梯
        pyf: '-',//排烟阀
        pqf: '-',//排气阀
        ygtcq: '-',//烟感探测器
        wgtcq: '-',//温感探测器
      },//消防物资
      highRisk: {
        risk: '-',//危险品数量
        flammable: '-',//易燃易爆品数量
      },//高危物品
      alarmType: {
        total: 0,//报警总数
        typeList: [
          {
            type: "水位水压",
            number: 0
          },
          {
            type: "漏电保护",
            number: 0,
          },
          {
            type: "气体报警",
            number: 0,
          },
          {
            type: "消防通道",
            number: 0
          }
        ]
      },//报警类型
      alarmHandle: {
        total: 0,//报警总数
        handleNumber: 0,//报警处理总数
        percentage: 0//报警处理占比
      }
    }
  },
  created() {

  },
  beforeMount() {
    __g.marker.clear()
  },
  mounted() {
    this.getPieChart()
    this.getCircleChart()
    // 地图打点
    this.getData().then(e => {
      let data = e

      let markerArr = []
      // console.log(data);

      for (let i = 0; i < data.length; i++) {
        const e = data[i];
        let img;
        if (e.type == '消防栓') {
          img = require('@/assets/fireFighting/icon_map_point_14.png')
        }
        if (e.type == '餐饮') {
          img = require('@/assets/fireFighting/icon_map_point_14.png')
        }
        if (e.type == '休闲娱乐') {
          img = require('@/assets/fireFighting/icon_map_point_14.png')
        }
        if (e.type == '科技服务') {
          img = require('@/assets/fireFighting/icon_map_point_14.png')
        }
        if (e.type == '其他') {
          img = require('@/assets/fireFighting/icon_map_point_14.png')
        }
        // console.log(img);
        // console.log(e);
        let marker = {
          id: 'fireFighting' + i,
          userData: JSON.stringify({ type: e.type }),
          coordinate: this.getCoord(e.longitude, e.latitude),
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
      __g.marker.focus(['fireFighting0', 'fireFighting1', 'fireFighting2'], 500)
    })
  },
  methods: {
    // 获取地图打点数据
    async getData() {
      let formatData = []
      await request.get("./data/fireFighting.txt").then((res) => {
        let listData = res.split("\r\n")
        for (let i = 0; i < listData.length; i++) {
          let arr = listData[i].split(",")
          if (arr[0] == "") {
            continue;
          }
          let data = {
            longitude: arr[0],
            latitude: arr[1],
            type: arr[2]
          }
          formatData.push(data)
        }
      })
      return formatData
    },
    // 坐标转换
    getCoord(lng, lat) {
      const a = CoordTransform.BD09ToGCJ02(lng, lat)
      const point = CoordTransform.GCJ02ToWGS84(a[0], a[1])
      const points = CoordTransform.TransformByEPSG(4326, 4549, point[0], point[1])
      return points
    },
    //饼图
    async getPieChart() {
      const chartBox = await this.$echarts.init(document.getElementById('pie-box'))
      const option = {
        title: {
          text: this.alarmType.total + '个',
          subtext: '报警总数',
          left: 'center',
          top: '30%',
          textStyle: {
            fontSize: 24,
            fontWeight: 700,
            color: '#00d5ff',
            // color: function (params) {
            //   // 根据不同的标题文字设置颜色
            //   if (params.name === '个') {
            //     return '#fff'; // 直接访问标题的颜色
            //   } else {
            //     return '#00d5ff'; // 其他标题的颜色
            //   }
            // },
          },
          subtextStyle: {
            fontSize: 15,
            color: '#e2efff'
          }
        },
        tooltip: {
          //提示框
          show: true,
          backgroundColor: "rgba(0, 0, 0,.8)",

          textStyle: {
            color: "#fff",
          },
          position: 'right',
        },
        series: [
          {
            name: '报警类型',
            type: 'pie',
            radius: ['70%', '100%'],
            color: ['#2847CC', '#FFBC95', '#FFA075', '#1ADDD4'],
            avoidLabelOverlap: false,
            itemStyle: {
            },
            label: {
              normal: {
                show: false
              },

            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: [...this.alarmType.typeList.map(item => { return { value: item.number, name: item.type } }),],
            emphasis: {
              scale: false, // 禁用点击时的放大效果
            },
          },

        ]
      }
      await chartBox.setOption(option)
    },
    // 水球图
    async getCircleChart() {
      const chartBox = await this.$echarts.init(document.getElementById('circle-box'))
      let option = {
        backgroundColor: "", //背景色
        title: {
          text: `${this.alarmHandle.percentage * 100} %`,
          subtext: "已处理数",
          top: "30%",
          textStyle: {
            fontSize: 20,
            fontFamily: "Microsoft Yahei",
            fontWeight: "normal",
            color: "#00D5FF",
            testAlign: "center",
          },
          subtextStyle: {
            fontSize: 15,
            color: '#fff'
          },
          x: "center",
          y: "48%",
        },
        series: [
          {
            type: "liquidFill", //配置echarts图类型
            radius: "90%",
            center: ["50%", "50%"],
            //  shape: 'roundRect',// 设置水球图类型（矩形[rect]，菱形[diamond]，三角形[triangle]，水滴状[pin],箭头[arrow]...） 默认为圆形
            data: [this.alarmHandle.percentage, this.alarmHandle.percentage],  //设置波浪的值 
            //waveAnimation:false, //静止的波浪
            backgroundStyle: {
              borderWidth: 1,
              color: "transparent",//水球图内部背景色
            },
            outline: {
              borderDistance: 0,
              itemStyle: {
                borderWidth: 4,
                borderColor: "#0db3d9",
              },
            },
            color: [ //波浪颜色
              {
                type: "linear",
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 1,
                    color: "#117f97", //下
                  },
                  {
                    offset: 0,
                    color: "#117f97",
                  },
                ],
                globalCoord: false,
              },
              {
                type: "linear",
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 1,
                    color: "#0fafd0", //下
                  },
                  {
                    offset: 0,
                    color: "#0fafd0",
                  },
                ],
                globalCoord: false,
              },
            ],
            label: {
              normal: {
                formatter: "",
              },
            },
          },

        ],
      };
      await chartBox.setOption(option)

    },
    // 隐藏当前窗口
    closedWin() {
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>
.left-bg {
  .box1 {
    margin-top: 16px;
    margin-bottom: 8px;
    // display: flex;
    // justify-content: space-between;

    .box1-top {
      display: flex;
      justify-content: space-between;

      .top-item {
        width: 210px;
        height: 60px;
        background-image: url('@/assets/fireFighting/img_anfang_data_camrea1(1).png');
        background-size: 100% 100%;
        display: flex;
        align-items: center;

        .item-img {
          width: 40px;
          height: 40px;
          // margin-top: 16px;
          margin-left: 20px;
        }

        .item-text-box {
          margin-left: 10px;

          .item-text {
            // margin-top: 9px;
            // margin-bottom: 8px;
            color: #00d5ff;
            // text-align: center;
            font-family: "DIN";
            font-size: 18px;
            font-style: normal;
            font-weight: 500;
            line-height: normal;

            span {
              color: #bcd1e9;
              text-align: center;
              font-family: "Inter";
              font-size: 15px;
              font-style: normal;
              font-weight: 400;
              line-height: normal;
            }
          }

          .item-tip {
            color: #ffffff;
            text-align: center;
            font-family: "Inter";
            font-size: 16px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
          }
        }


      }
    }

    .box1-center {
      margin-top: 10px;
      display: flex;
      justify-content: space-between;

      .center-item {
        display: flex;
        width: 138px;
        height: 62px;
        background-image: url("@/assets/fireFighting/img_anfang_data_camrea1(1).png");
        background-size: 100% 100%;
        box-sizing: border-box;
        padding-top: 8px;
        padding-left: 8px;

        .center-img {
          width: 20px;
          height: 20px;
        }

        .center-text-box {
          margin-left: 10px;

          .center-tip {
            color: #ffffff;
            text-align: center;
            font-family: "Inter";
            font-size: 16px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
          }

          .center-text {
            color: #00d5ff;
            // text-align: center;
            font-family: "Inter";
            font-size: 22px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;

            span {
              color: #bcd1e9;
              text-align: center;
              font-family: "Inter";
              font-size: 15px;
              font-style: normal;
              font-weight: 400;
              line-height: normal;
            }
          }
        }
      }
    }

  }

  .box2 {
    margin-top: 8px;
    margin-bottom: 12px;
    display: flex;
    justify-content: space-between;

    .box2-item {
      width: 210px;
      height: 64px;
      flex-shrink: 0;
      background: linear-gradient(90deg, #00d5ff1a 0%, #00d5ff1a 100%);
      stroke-width: 1px;
      stroke: #00D5FF;
      border-left: 1px solid #00D5FF;
      display: flex;
      align-items: center;

      .box2-item-img {
        width: 40px;
        height: 40px;
        margin-left: 12px;
      }

      .box2-item-text {
        margin-left: 16px;

        .text-top {
          color: #ffffff;
          text-align: center;
          font-family: "Inter";
          font-size: 16px;
          font-style: normal;
          font-weight: 400;
          line-height: normal;
        }

        .text-down {
          color: #00d5ff;
          font-family: "DIN";
          font-size: 18px;
          font-style: normal;
          font-weight: 500;
          line-height: normal;

          span {
            color: #bcd1e9;
            text-align: center;
            font-family: "Inter";
            font-size: 15px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
            padding-left: 8px;
          }
        }
      }
    }
  }

  .box3 {
    display: flex;
    align-items: center;

    #pie-box {
      width: 150px;
      height: 150px;
      margin-top: 8px;
      margin-left: 29px;
      margin-bottom: 16px;
    }

    dl {
      margin-left: 30px;

      dd {
        display: flex;
        align-items: center;
        margin-bottom: 10px;
        color: #7c979c;

        i {
          width: 8px;
          height: 8px;
          display: inline-block;
          border-radius: 50%;
          margin: 0 8px;
          background-color: #2847CC;
        }

        .dis-name {
          width: 65px;
          color: #e2efff;
          font-family: "PingFang SC";
          font-size: 15px;
          font-style: normal;
          font-weight: 600;
          line-height: 15px;
        }

        span {
          margin-left: 16px;
          color: #e2efff;
          font-family: "PingFang SC";
          font-size: 18px;
          font-style: normal;
          font-weight: 400;
          line-height: 18px;
        }

        &:nth-child(2) {
          i {
            background-color: #FFBC95;
          }
        }

        &:nth-child(3) {
          i {
            background-color: #FFA075;
          }
        }

        &:nth-child(4) {
          i {
            background-color: #1ADDD4;
          }
        }

      }
    }
  }

  .box4 {
    margin-top: 20px;
    margin-left: 17px;
    display: flex;
    align-items: center;

    #circle-box {
      width: 160px;
      height: 160px;
    }

    .box4-right {
      margin-left: 60px;
      height: 152px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      .box4-right-item {

        width: 155px;
        height: 70px;
        flex-shrink: 0;
        background: linear-gradient(0deg, #3d94ff1a 0%, #00d5ff1a 100%);
        stroke-width: 1px;
        stroke: #00d5ffb3;
        display: flex;
        flex-direction: column;

        .right-item-top {
          display: flex;
          align-items: center;
          margin-left: 10px;
          margin-top: 10px;

          .icon {
            width: 5px;
            height: 5px;
            border-radius: 6px;
            background: #00D5FF;
            margin-right: 8px;
          }


          .box4-right-item-top {
            color: #ffffff;
            font-family: "Inter";
            font-size: 16px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
          }
        }


        .box4-right-item-down {
          margin-top: 8px;
          margin-left: 26px;
          color: #00d5ff;
          font-family: "PingFang SC";
          font-size: 20px;
          font-style: normal;
          font-weight: 600;
          line-height: normal;

          span {
            color: #a0bde0;
            font-family: "Inter";
            font-size: 15px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
          }
        }

      }
    }
  }

  .title-top {
    margin-top: 12px;
  }
}
</style>