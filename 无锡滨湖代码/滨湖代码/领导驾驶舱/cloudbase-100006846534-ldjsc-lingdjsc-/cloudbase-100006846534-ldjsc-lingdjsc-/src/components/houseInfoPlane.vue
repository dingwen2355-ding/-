<!--
 * @Descripttion: 【网格党支部】
 * @version: 1.1
 * @Author: FZJ
 * @Date: 2022-04-07 14:29:47
 * @LastEditors: FZJ
 * @LastEditTime: 2022-08-09 17:42:58
-->
<template>
  <div class="houseDialog" v-if="show">
    <!-- <el-dialog  class="dialog" :visible="true"
            :append-to-body="true"
            :before-close="close"
            :max-height="550"
            :modal="false"
            :show-close="false"
            > -->
    <div class="head">
      <!--span class="spanL">{{ buildName }}楼</span-->
      <span class="spanC">{{ roomCode }}</span>
      <!-- <span v-if="2" class="spanR">空关房</span>
          <span v-else-if="3" class="spanR">出租房</span>
          <span v-else class="spanR">自助房</span> -->
      <span class="bud_close" style="" @click="close">×</span>
    </div>
    <div class="body">
        <!--div class="bodyHead"><span>人口信息</span></div-->
        <el-scrollbar style="height:100%;overflow-x: hidden;">
			<div class="peopleInfo" v-for="(item, index) in peopleInfo" :key="index">
			  <img class="peoplePhoto" v-if="item.sex=='男'" src="@/assets/images/housePlane/peopleBG2.png" />
			  <img class="peoplePhoto" v-else-if="item.sex=='女'" src="@/assets/images/housePlane/peopleNv.png" />
			  <div class="text">
				<div class="info">
				  <img class="infoSign" src="@/assets/images/housePlane/point.png" />
				  <span>姓名:</span>
				  <span>{{ item.name }}</span>
				</div>
				<div class="info">
				  <img class="infoSign" src="@/assets/images/housePlane/point.png" />
				  <span>性别:</span>
				  <span>{{ item.sex }}</span>
				</div>
				<div class="info">
				  <img class="infoSign" src="@/assets/images/housePlane/point.png" />
				  <span>出生日期:</span>
				  <span>{{ item.birthday }}</span>
				</div>
				<div class="info">
				  <img class="infoSign" src="@/assets/images/housePlane/point.png" />
				  <span>居住地址:</span>
				  <span class="site">{{ item.site }}</span>
				</div>
			  </div>
			  <div class="sign">
				<!-- <img v-if="true" src="@/assets/images/housePlane/liuG.png" /> -->
				<img v-if="item.flow" src="@/assets/images/housePlane/liuB.png" />
				<img v-if="item.release" src="@/assets/images/housePlane/xing.png" />
			  </div>
			</div>
        </el-scrollbar>
      <!--div>
        <div class="bodyHead"><span>历史居住信息</span><img src="" /></div>
        <div class="peopleInfo" v-for="(item, index) in oldPeopleInfo" :key="index">
          <img class="peoplePhoto" src="@/assets/images/housePlane/peopleBG2.png" />
          <div class="text">
            <div class="info">
              <img class="infoSign" src="@/assets/images/housePlane/point.png" />
              <span>姓名:</span>
              <span>{{ item.name }}</span>
            </div>
            <div class="info">
              <img class="infoSign" src="@/assets/images/housePlane/point.png" />
              <span>性别:</span>
              <span>{{ item.sex }}</span>
            </div>
            <div class="info">
              <img class="infoSign" src="@/assets/images/housePlane/point.png" />
              <span>出生日期:</span>
              <span>{{ item.birthday }}</span>
            </div>
            <div class="info">
              <img class="infoSign" src="@/assets/images/housePlane/point.png" />
              <span>户籍地址:</span>
              <span class="site">{{ item.site }}</span>
            </div>
          </div>
          <div class="sign">
            <img v-if="item.flow" src="@/assets/images/housePlane/liuB.png" />
            <img v-if="item.release" src="@/assets/images/housePlane/xing.png" />
          </div>
        </div>
      </div-->
    </div>
    <!-- </el-dialog> -->
  </div>
</template>
<script>
import axios from "axios";
//import { scrnSys } from "@/api/api";

export default {
  data() {
    return {
      buildName: "",
      roomCode: "",

      peopleInfo: [
        {
          name: "刘家兴",
          photo: "",
          sex: "男",
          birthday: "1976-03-03",
          site: "山东省济宁市XX镇淮北路138号"
        }
      ],
      oldPeopleInfo: [
        {
          name: "刘家兴",
          photo: "",
          sex: "男",
          birthday: "1976-03-03",
          site: "山东省济宁市XX镇淮北路138号"
        }
      ]
    };
  },
  props: ["dialogVisible", "roomId", "roomName", "buildName1", "show"],
  watch: {
    roomId: {
      handler(newVal, oldVal) {
        if (newVal != undefined) this.updateData();
      }
    },
	roomName: {
      handler(newVal, oldVal) {
		this.roomCode=this.roomName;
      }
    },
	buildName1: {
      handler(newVal, oldVal) {
		this.buildName=this.buildName1;
      }
    }
  },
  components: {},
  methods: {
    // currentPageChange(val) {
    //   this.currentPage = val
    //   this.updateData()
    // },
    updateData() {
	debugger
		let _this = this;
		axios.get("https://2.21.138.89:11511/house/getresidentInfoByRoomCode", {
			params: {
			  'roomCode':this.roomId
			}
		})
		.then((res) => {
			_this.peopleInfo =[];
			_this.oldPeopleInfo =[];
			for(var item of res.data){
				if(item.history!='0'){
					if(!_this.oldPeopleInfo.find(p=>p.sfzjhm==item.sfzjhm)){
						_this.oldPeopleInfo.push({
							name: item.name,
							photo: "",
							sex: item.types == "1" ? "男" : "女",
							birthday: item.csrq,
							sfzjhm: item.sfzjhm,
							site: item.live_place_address
						});
					}
				}else{
					if(!_this.peopleInfo.find(p=>p.sfzjhm==item.sfzjhm)){
						_this.peopleInfo.push({
							name: item.name,
							photo: "",
							sex: item.types == "1" ? "男" : "女",
							birthday: item.csrq,
							sfzjhm: item.sfzjhm,
							site: item.live_place_address
						});
					}
				}
			}
		});
    },
    close() {
      this.$emit("closePlane")
    }
  },
  mounted() {
    //console.log("传入的房屋id", this.roomId);
    //this.updateData();
  }
};
</script>
<style lang="scss" scoped>
/*定制el_dialog列表弹窗样式*/
.el-scrollbar__bar.is-horizontal{
}
.houseDialog {
  //background: transparent;
  background-image: url("~@/assets/images/housePlane/backGround2.png");
  // padding: 0px;
  z-index: 3000;
  width: 472px;
  padding: 26px 75px 0px 40px;
  height: 570px;
  background-size: 100% 100%;
  position: absolute;
  left: 50%;
  top:200px;

  .head {
    font-size: 22px;
    font-family: AlibabaPuHuiTi;
    font-weight: 400;
    color: #89a1b1;
    line-height: 22px;
    letter-spacing: 1px;
    margin-top: 18px;
    width:450px;
    .spanL {
      margin-left: 10px;
    }
    .spanC {
      margin-left: 10px;
      color: #f2ffff;
    }
    .spanR {
      float: right;
      margin-right: 25px;
      width: 63px;
      height: 19px;
      font-size: 20px;
      font-weight: 400;
      color: #15ffd5;
    }
    .bud_close {
      font-size: 35px;
      cursor: pointer;
      float: right;
      color: #fff;
    }
  }
  .body {
    overflow-y: auto;
    height: 440px;
    margin-top: 14px;
    margin-left: 15px;
    //background: aliceblue;
    font-size: 18px;
    font-family: AlibabaPuHuiTi;
    font-weight: 400;
    color: #b8c6d3;
    line-height: 24px;
    letter-spacing: 1px;

    img {
      display: inline-block;
    }
    .bodyHead {
      width: 398px;
      height: 31px;
      background-image: url("~@/assets/images/housePlane/headBG.png");
      background-size: 100% 100%;
      margin-top: 15px;

      span {
        width: 70px;
        height: 17px;
        font-weight: 500;
        font-style: italic;
        color: #e9eef3;
        line-height: 32px;
        margin-left: 15px;
      }
    }
    .peopleInfo {
      display: flex;
      margin-top: 15px;
      border-bottom: 1px solid #089cd7;
      padding-bottom: 10px;
      position: relative;
      .peoplePhoto {
        height: 99px;
        width: 90px;
        background-size: 100% 100%;
      }
      .text {
        width: 350px;
        margin-left: 15px;
        .info {
          .infoSign {
            width: 25px;
            height: 25px;
            background-size: 100% 100%;
          }
          span {
            margin-left: 5px;
          }
          .site {
          }
        }
      }
      .sign {
        position: absolute;
        margin-left: 335px;
      }
    }
  }
}
</style>
