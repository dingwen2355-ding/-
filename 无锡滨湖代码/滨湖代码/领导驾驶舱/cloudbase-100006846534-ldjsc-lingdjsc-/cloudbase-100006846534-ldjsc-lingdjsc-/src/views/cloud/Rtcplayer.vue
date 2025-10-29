<template>
  <div>
    <!-- loading -->
    <!--loading-tmpl v-if="!showPage" ref="loading" class="loadingdiv"></loading-tmpl-->
    <!-- player -->
    <div :id="divId" class="player"></div>
    <!-- 功能模板入口 -->
    <FunctionTmpl v-if="showPage && bizTMPL == 'default'" ref="view" :class="{ showTool: !showTool }" />
    <!-- 业务模板 -->
    <component v-if="showPage && !!bizTMPL && bizTMPL != 'default' && component" :is="component" :ref="bizFolder('ref')"
      :class="{ showTool: !showTool }" />
    <!-- 切换业务模板 -->
    <div v-if="showPage && !!scenesInfo && scenesInfo.needSW && bizTMPL !== 'default'
      " :class="['switch_business_tmpl', { showTool: !showTool }]"
      @click="handleSwitchBusinessTmpl(bizTMPL, 'switch')">
      <img src="@/assets/images/cloud/img/cutover.png" />
    </div>

    <!-- scene service download：实例申请 -->
    <InstanceAppTmpl v-if="showInstance" @handleClickButton="handleClickButton" test="建筑id"/>
	<HouseSelect
        v-show="showModal"
        :buildId="buildInfo.id"
		@closePlane="closeHouseSelectPlane"
        @openHouseInfoPlane="showHouseInfoPlane"
      />
    <houseInfoPlane
        :roomId="roomId"
        :roomName="roomName"
        :buildName1="buildName"
        :show="HouseInfoPlaneVisiable"
        @closePlane="closeHouseInfoPlane"
      />
	<div style="width:100px;height:100px;"></div>

    <el-dialog v-if="showVmModal" :visible.sync="showVmModal" width="1300px"  :modal-append-to-body='true' style="height: 850px !important;overflow: visible;">
      <div slot="title" class="dialog-title">
        <span class="title-text">{{ vmName }}</span>
      </div>
      <VideoCpnt :src="vmSrc" style="margin-top: 30px;"/>
    </el-dialog>
  </div>
</template>

<script>
import SealAirCityAPI from "./SealAirCityAPI";
import StyleStrategy from "./showStyleList";
import api from "./manager/api/index";
import { get, generalPostEvent } from "@/utils/fetch";
import { TileLayerStyle } from "@/utils/Enum";

import Axios from 'axios'
import qs from 'qs';
// import md5 from 'js-md5'; 
export default {
  components: {
    LoadingTmpl: () => import("@/components/loading.tmpl"),
    InstanceAppTmpl: () => import("@/components/instance-app.tmpl"),
    FunctionTmpl: () => import("./page/FunctionTmpl"),
    VideoCpnt: () => import("@/components/videoCpnt"),
	HouseSelect: () => import("@/components/HouseSelect"),
	houseInfoPlane: () => import("@/components/houseInfoPlane"),
  },
  data() {
    var randomNum = ("000000" + Math.floor(Math.random() * 999999)).slice(-6);
    return {
      myDefer: undefined,
      divId: "player" + randomNum,
      mediaid: "",
      token: "",
      showPage: false,
      bizTMPL: null, // 业务模板：default、city、community
      component: null, // 业务模板：组件
      scenesInfo: "", // 场景信息,
      // --------------------
      showTool: true,
      activeName: "", // 显示策略
      showInstance: false, // 实例申请
      showModal: false,
      buildInfo: {},
      tableData: [],
      vmMap: {},
      showVmModal: false,
      vmSrc: null,
      vmName: null,
      nNowPage: 1,// 页码
      nTotal: 0,// 总条数
	  HouseInfoPlaneVisiable:false,
	  roomId:null,
	  roomName:"",
	  buildName:"",
      geom:null
    };
  },
  computed: {
    bizFolder() {
      return function (type = "folder") {
        switch (this.bizTMPL) {
          case "city":
          case "community":
            return this.bizTMPL;
          default:
            let _type = type === "folder" ? "page" : "view";
            return _type;
        }
      };
    },
    loader() {
      if (!this.bizTMPL) return null;
      let folder = this.bizFolder();
      if (folder) return () => import(`./${folder}/index.vue`);
    },
    isInnerMapping() {
      return !!this.$store.getters.serviceMapping.innerMapping;
    }
  },

  watch: {
    "$store.getters.buildCoord": {
      indeterminate: true,
      deep: true,
      handler(coor) {
        if (coor.length == 2) {
          let that = this
          console.log('获取到合法的建筑点位坐标', coor)
          get(
            cim_main + 'resource/shp/gridDetailList',
            {
                'tableName': 'shp_info.jianzhushp1689673559194',
                'start': 1,
                'limit': 10,
                'x': coor[0],
                'y': coor[1]
            }
          ).then((res) => {
            console.log('获取到建筑item', res)
            if (res.data.records.length) {
              let itm = res.data.records[0]
			  that.HouseInfoPlaneVisiable=false;
			  that.showModal = true;
              that.buildInfo = {
                id: itm.gml_id,
                name: itm.jzname,
                total: 0,
                coor: coor.toString(),
                addr: itm.jzadress
              }
              that.geom = itm.geom;
            }
          })
        }


      },
    },
    "$store.getters.vMkr": {
      indeterminate: true,
      deep: true,
      async handler(item) {
        let that = this
        if (item && item.id) {
          let info = this.vmMap[item.id]
          if (info) {
            let tk = await this.vmAuth()
            console.log('点击了某个摄像头，信息为:', info, this.$store, '通道id', item.id)
            //TODO gisxk 此处使用获取点击的监控属性信息，进行弹窗播放操作
            Axios.get(
              'https://2.20.28.22:7902/videoService/realmonitor/uri',
              {
                params: {
                  'channelId': item.id,
                  'scheme': 'HLS_HTTPS'
                },
                headers: { //头部参数
                  'X-Subject-Token': tk
                }
              }
            ).then((res) => {
              console.log('直播流地址', res.url)
              that.vmName = info.name
              that.vmSrc = res.url
              that.showVmModal = true
            })
          }
        }
      },
    },
  },
  created() {
    // set mediaid、token
    this.mediaid = this.$route.query.mediaid;
    this.token = this.$route.query.token;
    let isShare = this.$route.query.isShare;
    if (isShare) this.showTool = false;

    if (!!this.token && this.token != this.$store.getters.token) {
      this.$store.dispatch("SET_ACCOUNT_ACTION", {
        token: this.token,
      });
    }
  },
  mounted() {
    let that = this;
	// 初始化
	this.insight = new window.Insight();
	
    window.RTCPlayer = that;
    window.addEventListener("resize", function () {
      if (window.AirCityPlayerACP) window.AirCityPlayerACP.resize();
    });
    that.initGetiid();
  },
  methods: {
	showHouseInfoPlane(_roomId,_roomName,_buildName) {
      this.roomId = _roomId;
      this.roomName = _roomName;
	  this.buildName=_buildName;
      this.HouseInfoPlaneVisiable = true
    },
	closeHouseSelectPlane(){
      this.showModal = false
	},
	closeHouseInfoPlane() {
      this.HouseInfoPlaneVisiable = false
    },
    // 表格弹框页面修改
    fChangTableLayePaginationr(val) {
      this.nNowPage = val;
      Axios.post(
        cim_main + 'shpInfo/professionAnalysis',
        qs.stringify({
          //'tableName':'layer.grid_xls_202307261133224788',
          //'tableName':'layer.grid_xls_202308111659434185',
          'tableName': 'layer.grid_xls_202308152246588145',//新的人房表
          'start': this.nNowPage,
          'limit': 10,
          'geom': this.geom,
        })
      ).then((resp) => {
        console.log('获取到人房item', resp)
        this.nTotal = resp.message.total
        let ls = []
        resp.message.list.some((item, idx) => {
          ls.push({
            name: item.name ? item.name.trim() : '',
            tel: item.phone ? item.phone.trim() : '',
            place: item.standardad ? item.standardad.trim() : ''
          })
        })
        this.buildInfo.total = resp.message.total
        this.tableData = ls
        this.showModal = true
      })
    },
    addVmMarker(item) {
      if (window.origAPI) {
        //let imgUrl = `${window.location.origin}${window.location.pathname}tag-img/vmMkr.png`
        //let imgUrl = require('@/assets/images/cloud/img/cutover.png')
        let imgUrl = 'http://2.21.137.206:11510/image/P3.png'

        let mkrInfo = {
          id: item.id,
          groupId: 'vmMkrGroup',
          coordinate: [...item.coord, 10], //坐标位置
          coordinateType: 1, //默认0是投影坐标系，也可以设置为经纬度空间坐标系值为1
          imagePath: imgUrl, //显示图片路径
          imageSize: [52, 57], //图片宽高[width,height]
          anchors: [-26, 57], //锚点
          range: [10, 10000], //可视范围
          fixedSize: true, //图片固定尺寸，取值范围：false 自适应，近大远小，true 固定尺寸，默认值：false
          text: item.name,
          //   textSize: 20, // 文字大小
          useTextAnimation: false, //打开文字展开动画效果
          textRange: [1, 1000], //文本可视范围[近裁距离, 远裁距离]
          textOffset: [0, 0], // 文本偏移
          textBackgroundColor: [0, 0, 0, 0],
          fontSize: 12, //字体大小
          fontOutlineSize: 1, //字体轮廓线大小
          fontColor: Color.Yellow, //字体颜色
          fontOutlineColor: "#000", //字体轮廓线颜色

          showLine: false, //标注点下方是否显示垂直牵引线
          lineSize: [10, 1], //垂直牵引线宽度和高度[width, height]
          lineColor: [0, 0, 0, 0.8], //垂直牵引线颜色
          lineOffset: [20, 20], //垂直牵引线偏移

          autoHidePopupWindow: true, //失去焦点后是否自动关闭弹出窗口
          autoHeight: false, // 自动判断下方是否有物体
          displayMode: 2, //显示模式
          priority: 0, //避让优先级
          occlusionCull: false, //是否参与遮挡剔除
        };
        window.origAPI.marker.add(mkrInfo)
      }
    },
    addVmMarkers(items,start) {
      if (window.origAPI) {
		start = start||0;
        //let imgUrl = `${window.location.origin}${window.location.pathname}tag-img/vmMkr.png`
        //let imgUrl = require('@/assets/images/cloud/img/cutover.png')
        let imgUrl = 'http://2.21.137.206:11510/image/P3.png'
		let mkrInfos=[];
		let i = 0;
		let that =this;
		for(i;i < 45000&&i+start < items.length;i++){
			let item = items[i+start];
			let mkrInfo = {
			  id: item.id,
			  groupId: 'vmMkrGroup',
			  coordinate: [...item.coord, 10], //坐标位置
			  coordinateType: 1, //默认0是投影坐标系，也可以设置为经纬度空间坐标系值为1
			  imagePath: imgUrl, //显示图片路径
			  imageSize: [50, 52], //图片宽高[width,height]
			  anchors: [-25, 52], //锚点
			  range: [0, 10000], //可视范围
			  fixedSize: true, //图片固定尺寸，取值范围：false 自适应，近大远小，true 固定尺寸，默认值：false
			  text: item.name,
			  //   textSize: 20, // 文字大小
			  useTextAnimation: false, //打开文字展开动画效果
			  textRange: [1, 10000], //文本可视范围[近裁距离, 远裁距离]
			  textOffset: [0, 0], // 文本偏移
			  textBackgroundColor: [0, 0, 0, 0],
			  fontSize: 12, //字体大小
			  fontOutlineSize: 1, //字体轮廓线大小
			  fontColor: Color.Yellow, //字体颜色
			  fontOutlineColor: [0, 0, 0, 0], //字体轮廓线颜色

			  showLine: false, //标注点下方是否显示垂直牵引线
			  lineSize: [10, 1], //垂直牵引线宽度和高度[width, height]
			  lineColor: [0, 0, 0, 0.8], //垂直牵引线颜色
			  lineOffset: [20, 20], //垂直牵引线偏移

			  autoHidePopupWindow: true, //失去焦点后是否自动关闭弹出窗口
			  autoHeight: false, // 自动判断下方是否有物体
			  displayMode: 4, //显示模式
			  priority: 0, //避让优先级
			  occlusionCull: false, //是否参与遮挡剔除
			};
			mkrInfos.push(mkrInfo);
		}
		window.origAPI.marker.add(mkrInfos,function(){
			if(i+start<items.length-1){
				that.addVmMarkers(items,i+start);
			}
		});
      }
    },
    async vmAuth() {

      let that = this
      // let userName = 'bhcssj'; /// 用户名
      // let password = 'bhcssj@123456'; /// 该用户的明文密码
      let userName = 'jiashicang'; /// 用户名
      let password = 'CYZXlj@123456'; /// 该用户的明文密码


      let clientType = 'winpc'




      return new Promise((resolve, reject) => {
        Axios.post(
          'https://2.20.28.22:7902/videoService/accounts/authorize',
          JSON.stringify({
            "userName": userName,
            "clientType": clientType
          }),
          {
            headers: {
              'Content-Type': 'application/json'
            }
          }
        ).catch((err) => {
          let resp = err.response
          if (resp.config.url.indexOf("videoService/accounts/authorize") !== -1) {
            let randomKey = resp.data["randomKey"];
            let realm = resp.data["realm"];
            let encryptType = resp.data["encryptType"];
            /// 一共计算五次MD5
            let signature = that.str2md5(password);
            signature = that.str2md5(userName + signature);
            signature = that.str2md5(signature);
            signature = that.str2md5(userName + ":" + realm + ":" + signature);
            signature = that.str2md5(signature + ":" + randomKey);
            console.log('新签名', signature)



            Axios.post(
              'https://2.20.28.22:7902/videoService/accounts/authorize',
              JSON.stringify({
                "userName": userName,
                "signature": signature,
                "randomKey": randomKey,
                "encryptType": encryptType,
                "clientType": clientType
              }),
              {
                headers: {
                  'Content-Type': 'application/json'
                }
              }
            ).then((res) => {
              console.log('二次鉴权', res)
              this.$store.dispatch('SET_VTK_ACTION', res.token)
              resolve(res.token);
            })
          }
        })

      })
    },
    str2md5(string) {
      function RotateLeft(lValue, iShiftBits) {
        return (lValue << iShiftBits) | (lValue >>> (32 - iShiftBits));
      }

      function AddUnsigned(lX, lY) {
        var lX4, lY4, lX8, lY8, lResult;
        lX8 = (lX & 0x80000000);
        lY8 = (lY & 0x80000000);
        lX4 = (lX & 0x40000000);
        lY4 = (lY & 0x40000000);
        lResult = (lX & 0x3FFFFFFF) + (lY & 0x3FFFFFFF);
        if (lX4 & lY4) {
          return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
        }
        if (lX4 | lY4) {
          if (lResult & 0x40000000) {
            return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
          } else {
            return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
          }
        } else {
          return (lResult ^ lX8 ^ lY8);
        }
      }

      function F(x, y, z) {
        return (x & y) | ((~x) & z);
      }

      function G(x, y, z) {
        return (x & z) | (y & (~z));
      }

      function H(x, y, z) {
        return (x ^ y ^ z);
      }

      function I(x, y, z) {
        return (y ^ (x | (~z)));
      }

      function FF(a, b, c, d, x, s, ac) {
        a = AddUnsigned(a, AddUnsigned(AddUnsigned(F(b, c, d), x), ac));
        return AddUnsigned(RotateLeft(a, s), b);
      };

      function GG(a, b, c, d, x, s, ac) {
        a = AddUnsigned(a, AddUnsigned(AddUnsigned(G(b, c, d), x), ac));
        return AddUnsigned(RotateLeft(a, s), b);
      };

      function HH(a, b, c, d, x, s, ac) {
        a = AddUnsigned(a, AddUnsigned(AddUnsigned(H(b, c, d), x), ac));
        return AddUnsigned(RotateLeft(a, s), b);
      };

      function II(a, b, c, d, x, s, ac) {
        a = AddUnsigned(a, AddUnsigned(AddUnsigned(I(b, c, d), x), ac));
        return AddUnsigned(RotateLeft(a, s), b);
      };

      function ConvertToWordArray(string) {
        var lWordCount;
        var lMessageLength = string.length;
        var lNumberOfWords_temp1 = lMessageLength + 8;
        var lNumberOfWords_temp2 = (lNumberOfWords_temp1 - (lNumberOfWords_temp1 % 64)) / 64;
        var lNumberOfWords = (lNumberOfWords_temp2 + 1) * 16;
        var lWordArray = Array(lNumberOfWords - 1);
        var lBytePosition = 0;
        var lByteCount = 0;
        while (lByteCount < lMessageLength) {
          lWordCount = (lByteCount - (lByteCount % 4)) / 4;
          lBytePosition = (lByteCount % 4) * 8;
          lWordArray[lWordCount] = (lWordArray[lWordCount] | (string.charCodeAt(lByteCount) << lBytePosition));
          lByteCount++;
        }
        lWordCount = (lByteCount - (lByteCount % 4)) / 4;
        lBytePosition = (lByteCount % 4) * 8;
        lWordArray[lWordCount] = lWordArray[lWordCount] | (0x80 << lBytePosition);
        lWordArray[lNumberOfWords - 2] = lMessageLength << 3;
        lWordArray[lNumberOfWords - 1] = lMessageLength >>> 29;
        return lWordArray;
      };

      function WordToHex(lValue) {
        var WordToHexValue = "", WordToHexValue_temp = "", lByte, lCount;
        for (lCount = 0; lCount <= 3; lCount++) {
          lByte = (lValue >>> (lCount * 8)) & 255;
          WordToHexValue_temp = "0" + lByte.toString(16);
          WordToHexValue = WordToHexValue + WordToHexValue_temp.substr(WordToHexValue_temp.length - 2, 2);
        }
        return WordToHexValue;
      };

      function Utf8Encode(string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
          var c = string.charCodeAt(n);
          if (c < 128) {
            utftext += String.fromCharCode(c);
          } else if ((c > 127) && (c < 2048)) {
            utftext += String.fromCharCode((c >> 6) | 192);
            utftext += String.fromCharCode((c & 63) | 128);
          } else {
            utftext += String.fromCharCode((c >> 12) | 224);
            utftext += String.fromCharCode(((c >> 6) & 63) | 128);
            utftext += String.fromCharCode((c & 63) | 128);
          }
        }
        return utftext;
      };

      var x = Array();
      var k, AA, BB, CC, DD, a, b, c, d;
      var S11 = 7, S12 = 12, S13 = 17, S14 = 22;
      var S21 = 5, S22 = 9, S23 = 14, S24 = 20;
      var S31 = 4, S32 = 11, S33 = 16, S34 = 23;
      var S41 = 6, S42 = 10, S43 = 15, S44 = 21;
      string = Utf8Encode(string);
      x = ConvertToWordArray(string);
      a = 0x67452301;
      b = 0xEFCDAB89;
      c = 0x98BADCFE;
      d = 0x10325476;
      for (k = 0; k < x.length; k += 16) {
        AA = a;
        BB = b;
        CC = c;
        DD = d;
        a = FF(a, b, c, d, x[k + 0], S11, 0xD76AA478);
        d = FF(d, a, b, c, x[k + 1], S12, 0xE8C7B756);
        c = FF(c, d, a, b, x[k + 2], S13, 0x242070DB);
        b = FF(b, c, d, a, x[k + 3], S14, 0xC1BDCEEE);
        a = FF(a, b, c, d, x[k + 4], S11, 0xF57C0FAF);
        d = FF(d, a, b, c, x[k + 5], S12, 0x4787C62A);
        c = FF(c, d, a, b, x[k + 6], S13, 0xA8304613);
        b = FF(b, c, d, a, x[k + 7], S14, 0xFD469501);
        a = FF(a, b, c, d, x[k + 8], S11, 0x698098D8);
        d = FF(d, a, b, c, x[k + 9], S12, 0x8B44F7AF);
        c = FF(c, d, a, b, x[k + 10], S13, 0xFFFF5BB1);
        b = FF(b, c, d, a, x[k + 11], S14, 0x895CD7BE);
        a = FF(a, b, c, d, x[k + 12], S11, 0x6B901122);
        d = FF(d, a, b, c, x[k + 13], S12, 0xFD987193);
        c = FF(c, d, a, b, x[k + 14], S13, 0xA679438E);
        b = FF(b, c, d, a, x[k + 15], S14, 0x49B40821);
        a = GG(a, b, c, d, x[k + 1], S21, 0xF61E2562);
        d = GG(d, a, b, c, x[k + 6], S22, 0xC040B340);
        c = GG(c, d, a, b, x[k + 11], S23, 0x265E5A51);
        b = GG(b, c, d, a, x[k + 0], S24, 0xE9B6C7AA);
        a = GG(a, b, c, d, x[k + 5], S21, 0xD62F105D);
        d = GG(d, a, b, c, x[k + 10], S22, 0x2441453);
        c = GG(c, d, a, b, x[k + 15], S23, 0xD8A1E681);
        b = GG(b, c, d, a, x[k + 4], S24, 0xE7D3FBC8);
        a = GG(a, b, c, d, x[k + 9], S21, 0x21E1CDE6);
        d = GG(d, a, b, c, x[k + 14], S22, 0xC33707D6);
        c = GG(c, d, a, b, x[k + 3], S23, 0xF4D50D87);
        b = GG(b, c, d, a, x[k + 8], S24, 0x455A14ED);
        a = GG(a, b, c, d, x[k + 13], S21, 0xA9E3E905);
        d = GG(d, a, b, c, x[k + 2], S22, 0xFCEFA3F8);
        c = GG(c, d, a, b, x[k + 7], S23, 0x676F02D9);
        b = GG(b, c, d, a, x[k + 12], S24, 0x8D2A4C8A);
        a = HH(a, b, c, d, x[k + 5], S31, 0xFFFA3942);
        d = HH(d, a, b, c, x[k + 8], S32, 0x8771F681);
        c = HH(c, d, a, b, x[k + 11], S33, 0x6D9D6122);
        b = HH(b, c, d, a, x[k + 14], S34, 0xFDE5380C);
        a = HH(a, b, c, d, x[k + 1], S31, 0xA4BEEA44);
        d = HH(d, a, b, c, x[k + 4], S32, 0x4BDECFA9);
        c = HH(c, d, a, b, x[k + 7], S33, 0xF6BB4B60);
        b = HH(b, c, d, a, x[k + 10], S34, 0xBEBFBC70);
        a = HH(a, b, c, d, x[k + 13], S31, 0x289B7EC6);
        d = HH(d, a, b, c, x[k + 0], S32, 0xEAA127FA);
        c = HH(c, d, a, b, x[k + 3], S33, 0xD4EF3085);
        b = HH(b, c, d, a, x[k + 6], S34, 0x4881D05);
        a = HH(a, b, c, d, x[k + 9], S31, 0xD9D4D039);
        d = HH(d, a, b, c, x[k + 12], S32, 0xE6DB99E5);
        c = HH(c, d, a, b, x[k + 15], S33, 0x1FA27CF8);
        b = HH(b, c, d, a, x[k + 2], S34, 0xC4AC5665);
        a = II(a, b, c, d, x[k + 0], S41, 0xF4292244);
        d = II(d, a, b, c, x[k + 7], S42, 0x432AFF97);
        c = II(c, d, a, b, x[k + 14], S43, 0xAB9423A7);
        b = II(b, c, d, a, x[k + 5], S44, 0xFC93A039);
        a = II(a, b, c, d, x[k + 12], S41, 0x655B59C3);
        d = II(d, a, b, c, x[k + 3], S42, 0x8F0CCC92);
        c = II(c, d, a, b, x[k + 10], S43, 0xFFEFF47D);
        b = II(b, c, d, a, x[k + 1], S44, 0x85845DD1);
        a = II(a, b, c, d, x[k + 8], S41, 0x6FA87E4F);
        d = II(d, a, b, c, x[k + 15], S42, 0xFE2CE6E0);
        c = II(c, d, a, b, x[k + 6], S43, 0xA3014314);
        b = II(b, c, d, a, x[k + 13], S44, 0x4E0811A1);
        a = II(a, b, c, d, x[k + 4], S41, 0xF7537E82);
        d = II(d, a, b, c, x[k + 11], S42, 0xBD3AF235);
        c = II(c, d, a, b, x[k + 2], S43, 0x2AD7D2BB);
        b = II(b, c, d, a, x[k + 9], S44, 0xEB86D391);
        a = AddUnsigned(a, AA);
        b = AddUnsigned(b, BB);
        c = AddUnsigned(c, CC);
        d = AddUnsigned(d, DD);
      }
      var temp = WordToHex(a) + WordToHex(b) + WordToHex(c) + WordToHex(d);
      return temp.toLowerCase();
    },
    // scene service download
    handleClickButton(val, obj) {
      if (this.showInstance) {
        this.showInstance = false;
        if (val) {
          this.initGetiid();
        }
      }
    },
    //从接口中获取实例链接配置
    async initGetiid() {
      let that = this;
      let res = undefined;
      if (typeof service_proxy_address !== "undefined") {
        // scene service download
        res = await get(`${service_proxy_address}`, false);
      } else {
        let data = {
          token: this.token,
          id: this.mediaid,
        }
        res = await generalPostEvent(`${cim_server}service/media/getconfig`, data);
      }
      if (res.data) {
        let msg = res.data.errorInfo;
        if (!!msg) {
          //that.$refs["loading"].loadFailed(msg);
          // scene service download
          setTimeout(() => {
            if (typeof service_proxy_address !== "undefined" && msg === "没有找到用户绑定的实例信息") {
              let elem = document.getElementById("l_text");
              let _btn = document.createElement("button");
              _btn.setAttribute("type", "button");
              _btn.setAttribute("style", "background-color: transparent;color: #fff;font-size: 16px;border: 1px solid #fff;border-radius: 4px;padding: 8px 24px 10px 24px;margin-top: 14px;cursor: pointer;");
              _btn.innerText = "实例申请";
              _btn.addEventListener("click", function () {
                that.showInstance = true;
              })
              let _br = document.createElement("br");
              elem.appendChild(_br);
              elem.appendChild(_btn);
            }
          }, 1000);
          return;
        }
        let config = res.data;
        let _config = [];
        if (that.isInnerMapping) {
          _config = [
            `${config.proxyInUrl}`,
            config.instanceId,
          ];
        } else {
          _config = [
            `${config.mappingProxyUrl}`,
            config.instanceId,
          ];
        }
		
		
		let tree = JSON.parse(JSON.stringify(config.mediaTree || {}));
		for (let l in tree) {
			let e = [{
				old: "name",
				new: "label"
			}, {
				old: "datas",
				new: "children",
				delO: !0
			}];
			var t = "dirTree" == l ? [] : null;
			this.replaceAttributeName(tree[l], e, t);
			tree.media3dt = t;
		}
		that.$store.dispatch("SET_TREEDATA_ACTION", tree);
		
		
        /*let tree = config.mediaTree;
        // init tree nodes
        if (Object.prototype.toString.call(tree)) {
          for (let key in tree) {
            that.initNodes(tree[key]);
          }
        }
		
        let modelArr = [];
        that.copyData2Childrens(tree.dirTree, true, modelArr);
        that.copyData2Childrens(tree.cimTree);
        that.copyData2Childrens(tree.areaTree);
        tree.media3dt = modelArr;

        // let baseDataArr = [];
        // that.copyData2Childrens(tree.basedataDirTree, true, baseDataArr);
        // // that.baseDataArr = baseDataArr
        that.$store.dispatch("SET_TREEDATA_ACTION", tree);*/
		
		that.initPlayer(..._config);
		
		setTimeout(function(){
			that.showMarker();
		},10000)
		that.insight.change("mappoint", value => {
		  if(Number(value)){
			that.showMarker();
		  }else{
			that.clearMarker();
		  }
		})
		
      } else {
        //that.$refs["loading"].loadFailed();
      }
    },
    // 初始化叶子节点
    initNodes(data = []) {
      for (let i = 0; i < data.length; i++) {
        let item = data[i];
        item.label = item.name;
        if (!!item.children && item.children.length > 0) this.initNodes(item.children);
      }
    },
	replaceAttributeName(e, t=[], i, a="children") {
		switch (Object.prototype.toString.call(e)) {
		case "[object Object]":
			e.hasOwnProperty("name") || (e.name = e.tableAliasname || e.tableName),
			t.forEach((t=>{
				let a = e[t.old];
				if (i)
					if ("datas" == t.old && a)
						i.push(...a);
					else if (isNaN(e.iD) && !["EPT_Folder"].includes(e.type)) {
						let {children: t, ...a} = e;
						i.push(a)
					}
				if (a) {
					let i = e[t.new]
					  , s = Object.prototype.toString.call(i);
					i && "[object Array]" === s && (a = [...a, ...i]),
					e[t.new] = a,
					t.delO && delete e[t.old]
				}
			}
			));
			let s = Object.prototype.toString.call(e[a]);
			"[object Array]" === s && this.replaceAttributeName(e[a], t, i);
			break;
		case "[object Array]":
			e.forEach((e=>{
				this.replaceAttributeName(e, t, i)
			}
			));
			break
		}
	},
    copyData2Childrens(data, copyData, array) {
      let self = this;
      if (Array.isArray(data)) {
        data.forEach(item => {
          if (item.children) {
            self.copyData2Childrens(item.children, copyData, array)
          }
          if (item.datas) {
            if (!item.children) {
              item.children = [];
            }
            if (copyData) {
              array.push(...item.datas)
            }
            item.children = item.children.concat(item.datas)
          }
        })
      } else if (Object.prototype.toString.call(data) === '[object Object]') {
        if (data.children) {
          self.copyData2Childrens(data.children, copyData, array)
        }
        if (data.datas) {
          if (!data.children) {
            data.children = [];
          }
          if (copyData) {
            array.push(...data.datas)
          }
          data.childres = data.children.concat(data.datas)
        }
      }
    },
    clearMarker() {
      window.origAPI.marker.clear();
	},
	showMarker(){
		var that=this;
      //清除原有撒点，重新进行摄像头撒点
      window.origAPI.marker.clear();
      //TODO gisxk 此数据改成接口获取
		Axios.get('./json/HBSYJSP.json').then((res) => {
		//Axios.get('./json/HBSYJSP2.json').then((res) => {
		  let mkrLs = res;
		  let _vmMap = {}
		  let vmIds = []
		  let vmOptions=[];
		  mkrLs.some((item, idx) => {
			if (item.gpsX>1 && item.gpsY>1) {
			  vmOptions.push({ id: item.channelId, name: item.name, coord: [Number(item.gpsX), Number(item.gpsY)] })
			  _vmMap[item.channelId] = item
			  vmIds.push(item.channelId)
			  
			}
		  })
		  that.addVmMarkers(vmOptions);
		  that.vmMap = _vmMap
		  //window.origAPI.marker.focus(vmIds)
		})
	},
    // 初始化player
    async initPlayer(cloudUrl, iid) {
      let that = this;

      // 设置全局变量：封装后   sealAPI
      window.sealAPI = new SealAirCityAPI();
      console.log("==============================sealAPI:", window.sealAPI);

      await window.sealAPI.initPlayer(iid, that.divId, cloudUrl);

      //that.$refs["loading"].loadSuccess();
      window.sealAPI
        .clear()
        .then(() => {
          window.sealAPI._misc.stopPlayVideo(1);
          console.info("清除完成");
        })
        .catch((ee) => {
          console.log(ee);
        });
      setTimeout(async () => {
        //提示连接成功还是有延时websocket没真正连接成功，暂时延时处理
        that.initData();
        window.sealAPI._tileLayer.stopHighlightActor();

        $Bus.$emit("analyse-cancel-layeredG");
        window.sealAPI._settings.setEnableCameraMovingEvent(); //开启相机移动事件
      }, 1000);
		window.origAPI.marker.clear();


      // /// 一共计算五次MD5
      // let signature = that.str2md5(password);
      // signature = that.str2md5(userName+signature);
      // signature = that.str2md5(signature);
      // signature = that.str2md5(userName+":"+realm+":"+signature);
      // signature = that.str2md5(signature+":"+randomKey);
      // console.log('新签名',signature)
      // debugger
    },
    // 切换业务模板
    handleSwitchBusinessTmpl(biz, opr) {
      if (opr === "switch") {
        let defaultBiz = this.scenesInfo.media.attribute.bizTMPL;
        biz = biz === "default" ? defaultBiz : "default";
      }
      this.bizTMPL = biz;
      if (this.bizTMPL != "default") {
        // 动态添加业务模板组件
        this.loader().then(() => {
          this.component = () => this.loader();
        });
      }
    },
    async initData() {
      let that = this;
      // generalPostEvent(`${cim_main}service/scene/build/tree`, {
      //   id: this.mediaid,
      // }).then((res) => {
      //   let arr = res.message
      //     .map((item) => item.child)
      //     .flat()
      //     .filter((item) => item.id)
      //     .map((item) => item.id.replace(/-/g, ""));
      //   window.sealAPI._tileLayer.hide(arr);
      // });
      let resR = await api.getone({
        token: this.token,
        id: this.mediaid,
      })
      if (!!resR && !!resR.state && resR.state.code === 10000) {
        that.showPage = true;

        let _result = resR.body.message;
        let _attr = _result.media.attribute;
        // res.body.message.media3dt.map((i) => {
        //   i.treeid = i.id.replace(/-/g, "");
        // }); // {deving} 原有
        if (_attr) {
          _attr = JSON.parse(decodeURIComponent(_attr));
          that.handleSwitchBusinessTmpl(_attr.bizTMPL);
          window.sealAPI._tileLayer.showStyle = _attr.showStyle;
          that.changeModelStyle(_attr.showStyle, _result.media3dt);

          // setTimeout(() => {
          //   // 初始化保存的视角
          //   if (!_attr.visualAngle) return;

          //   let resC = _attr.visualAngle;
          //   window.sealAPI._camera.set(resC.x, resC.y, resC.z, resC.pitch, resC.yaw);
          // }, 0)
        }
        _result.media.attribute = _attr || "";
        _result.needSW = _attr.bizTMPL !== "default"; // 是否需要切换模板
        that.scenesInfo = _result;
        // that.$store.dispatch("SET_SCENEINFO_ACTION", that.scenesInfo);
      } else that.$message.error("访问失败");
    },
    // 显示风格
    changeModelStyle(showStyle, media3dt) {
      if (showStyle == "theDefault") {
        window.sealAPI._tileLayer.updateBegin();
        media3dt.forEach(item => {
          let options = JSON.parse(item.options);
          let effect = TileLayerStyle.find(o => o.name == options.displayStyle).styleid;
          let colorArr = options.color.split(',');
          let color = [colorArr[0] / 255, colorArr[1] / 255, colorArr[2] / 255, Number(colorArr[3])];
          if (effect == "0") {
            window.sealAPI._tileLayer.setStyle(item.id, effect, color, options.saturation, options.brightness, options.contrast, options.contrastBase)
          } else {
            window.sealAPI._tileLayer.setStyle(item.id, effect, color)
          }
        })
        window.sealAPI._tileLayer.updateEnd();
      } else {
        // 显示风格（style 样式， 0：默认；1：X光；2：纯色；3：水晶体；4：暗黑；5：科幻；6：扩散）
        let styleList = StyleStrategy.showStyleList
          .filter((item) => item.label === showStyle)
          .map((item) => item.children)
          .flat()
          .filter((item) => item);
        styleList.map((item) => {
          media3dt.map((model) => {
            if (item.cimtypes.includes(model.cimtype)) {
              window.sealAPI._tileLayer.setStyle_biz(
                model.id,
                item.style,
                item.rgba
              );
            }
          });
        });
      }
    },
    // 获取图层树
    getTree(obj) {
      api
        .gettree({
          token: this.$route.query.token,
          id: this.$route.query.mediaid,
        })
        .then((res) => {
          let arr = res.body.message.map((item) => item.child).flat();
          // console.log(arr);
          if (this.activeName === "theDefault") {
            arr.forEach((item) => {
              window.sealAPI._tileLayer.setStyle_biz(item.id, 0, [255 / 255, 255 / 255, 255 / 255, 1]);
            });
          } else {
            arr.forEach((item) => {
              window.sealAPI._tileLayer.setStyle_biz(item.id, obj.style, obj.rgba);
            });
          }
        });
    },
    // default->保存
    async saveProject(checkIds) {
      const that = this;
      // 保存当前视角
      let resV = await window.sealAPI._camera.get();
      resV = [resV].map(({ x, y, z, pitch, yaw }) => ({ x, y, z, pitch, yaw }));
      this.scenesInfo.media.attribute.visualAngle = resV[0];
      // 保存选中的模型（Tree）
      this.scenesInfo.media.attribute.checkedIds = checkIds;

      let resS = await api.getupdate({
        token: this.token,
        id: this.mediaid ? this.mediaid : "",
        topic: this.scenesInfo.media.topic ? this.scenesInfo.media.topic : "",
        name: this.scenesInfo.media.name ? this.scenesInfo.media.name : "",
        attribute: JSON.stringify(this.scenesInfo.media.attribute),
      })
      if (!!resS && !!resS.state && resS.state.code === 10000) {
        that.$message.success("保存成功！");
        // setTimeout(() => {
        //   that.$store.dispatch("SET_SCENEINFO_ACTION", that.scenesInfo);
        // }, 200);
        // SaveProject
        window.origAPI.call0(225);
      } else that.$message.error("保存失败！");
    },
  },
  destroyed() {
    console.log("destroyed ==> close");
  },
};
</script>

<style lang="scss" scoped>
.showTool {
  display: none !important;
}

.loadingdiv,
.player {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
}

.switch_business_tmpl {
  position: fixed;
  z-index: 100;
  right: 100px;
  top: 26px;
  width: 78px;
  cursor: pointer;

  img {
    max-width: 100%;
  }
}

/deep/.el-dialog {
  background: url('../../assets/images/bg/VideoPop.png') no-repeat center center !important;
  background-size: 100% 100% !important;
  height: 100% !important;
  box-shadow: none !important;
  padding: 10px 20px 0 20px;
}

/deep/ .el-dialog__body {
  padding: 0px 40px 40px !important;
}

/deep/.el-dialog__header {
  margin: 15px 60px 20px
}

/deep/.el-dialog__headerbtn {
  top: 25px !important;
  right: 40px !important;
}

/deep/.el-dialog__headerbtn .el-dialog__close {
  font-size: 30px;
  color: #fff !important;
}

.layer-box-item {
  display: flex;
  align-items: center;
  width: 100%;
  height: 40px;
  font-size: 20px;
  color: #fff;
}

/* .layer-box-item:nth-child(even) {
      background: #f2f2f2;
    } */

.box-item-name {
  width: 150px;
}

.table {
  width: 100%;
  margin: 0 auto 20px;
}

.table-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: center;
  color: #fff;
  font-size: 20px;
  border-bottom: 1px solid #45b1e0;

}

.table-item-name {
  height: 40px;
  line-height: 40px;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.table-item-name {
  width: 100px;
}

.table-item-tel {
  width: 180px;
}

.table-item-code {
  width: calc(100% - 280px);
}



.title-text {
  position: absolute;
  top: 25px;
  color: #fff;
  font-size: 20px;
}

/deep/.el-dialog,
/deep/.el-pager li {
  background: none;
  color: #fff;
}
/deep/ .el-pagination {
	position: absolute;
    bottom: 20px;
    right: 100px;
}
/deep/.el-pagination .btn-next,
/deep/ .el-pagination .btn-prev {
  background: none;
  color: #fff;
}
/deep/.el-pager li.active{
  color: #409EFF !important;
}
</style>