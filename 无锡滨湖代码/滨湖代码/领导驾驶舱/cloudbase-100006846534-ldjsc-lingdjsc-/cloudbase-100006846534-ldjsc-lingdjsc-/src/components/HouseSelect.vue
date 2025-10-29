<template>
  <div class="SelectBox">
    <div class="SelectBody">
        <div class="title">
			<div style="color: white;font-size: 38px;margin: 12px;float: left;">房屋展示</div>
			<span class="bud_close" style="" @click="close">×</span>
        </div>
        <div class="build_sel">
            <div
              v-for="(item,idx) in buildList" :key="idx"
              :class="{active:item.name==buildSel}"
              class="tab"
              @click="changeBuildCode(item.name)">
              {{item.name}}
            </div>
        </div>
        <div class="unit_sel">
			<div
              v-for="(item,idx) in unitList" :key="idx"
              :class="{active:item==unitSel}"
              class="tabUnit"
              @click="changeUnitCode(item)">
              {{item}}
            </div>
		</div>
        <div class="RoomBody" style="position: absolute;top:155px;left:35%;width:49%;height:500px;">
          <el-scrollbar style="height:100%;text-align: center;">
            <div
              :class="{active:item.code==roomSel}"
             class="oneRoom"
             v-for="(item,index) in houseList" :key="index"
             @click="showHouseInfoPlane(item.code,item.name,buildSel)"
             style="display: inline-block;">
             {{item.name}}
            </div>
          </el-scrollbar>
        
        </div>
    </div>

  </div>
</template>

<script>
import axios from "axios"
//import { scrnSys } from "@/api/api"
//import { FormItem } from "element-ui"
//import houseInfoPlane from '@/views/gridGovernance/house/houseInfoPlane.vue';

export default {
    components: {
    },
    data() {
        return {
            data:{
                // RoomClass:'room',
                // isHier:false,
                // isAir:false,
            },
            roomType:1,
            buildList:[],
			buildSel:"",
            unitSel:"",
			roomSel:"",
			unitList:[],
			houseList:[]
        }
    },
    //1自主，2空关，3出租
    methods: {
		close() {
		  this.$emit("closePlane")
		},
		changeBuildCode(buildCode){
          this.buildSel=buildCode;
          this.unitSel="";
          this.roomSel="";
		  var build = this.buildList.find(b=>b.name==buildCode);
		  this.unitList=[];
		  for(var unit of build.units){
			this.unitList.push(unit.name);
		  }
		  this.houseList=[];
        },
        changeUnitCode(unitCode){
		  var build = this.buildList.find(b=>b.name==this.buildSel);
		  var unit = build.units.find(b=>b.name==unitCode);
          this.unitSel=unitCode
          this.roomSel="";
		  this.houseList=[];
		  var houses=unit.houses.sort((a,b)=>Number(a.floor)-Number(b.floor));
		  for(var house of houses){
			this.houseList.push(house);
		  }
        },
        showHouseInfoPlane(roomId,roomName,buildName){
			this.roomSel=roomId;
          this.$emit('openHouseInfoPlane', roomId,roomName,buildName)
          //this.$emit('openHouseInfoPlane', '1513341484366690320')
        },
        updateData(){
            let _this = this
            axios.get("https://2.21.138.89:11511/house/getRoomBaseInfoByBuindingCode", {
                params: {
                  'gmlId':_this.buildId
                }
            })
            .then((res) => {
				this.buildSel="";
				this.unitSel="";
				this.roomSel="";
				this.buildList=[];
				this.unitList=[];
				this.houseList=[];
				for(var data of res.data){
					var build = this.buildList.find(b=>b.name==data.lz);
					if(!build){
						build={name:data.lz,units:[]}
						this.buildList.push(build);
					}
					var unit = build.units.find(b=>b.name==data.uniteShowName);
					if(!unit){
						unit={name:data.uniteShowName,houses:[]}
						build.units.push(unit);
					}
					unit.houses.push({
						floor:data.floor,
						name:data.roomName,
						code:data.roomCode
					})
				}
            })
            .catch((err) => {
                console.log(err)
            })
        }
    },
    props:["buildId"],
    watch:{
      buildId:{
        handler(newVal,oldVal){
          if(newVal!=undefined)this.updateData()
        },
      }
    },
    mounted(){
      //this.updateData()
    },
}
</script>

<style lang="scss" scoped>
/* 隐藏el-scrollbar的横向滚动条 */
.el-scrollbar {
	.el-scrollbar__wrap {
	  overflow-x: hidden;
	  .el-scrollbar__view {
		height: 100%;
	  }
	}
}

.SelectBox {
    background-image:url('~@/assets/images/housePlane/backGround.png');
    width: 477px;
    height: 576px;
    background-size: 100% 100%;
    position: absolute;
    left: 40%;
    top:200px;
	padding: 30px 51px;
    .title {
        display: flex;
        flex-direction: row;
        justify-content:space-between;
		.bud_close {
		  font-size: 35px;
		  cursor: pointer;
		  float: right;
		  margin-top: 10px;
		  margin-right: 50px;
		  color: #fff;
		}
    }
    .block {
        display: flex;
        flex-direction: row;
    }
    .text {
        font-size: 22px;
        font-family: AlibabaPuHuiTi;
        font-weight: 200;
        color: #E0E1E7;
        line-height: 55px;
        letter-spacing: 1px;
        margin-top: 9px;
        margin-left: 10px;
        display:inline-block
    }
    .color{
      width: 27px;
      height: 18px;
      border-radius: 1px;
      margin-top: 27px;
      margin-left: 10px;
    }
    .zizhu.color{
        background: linear-gradient(-24deg, #308FE6, rgba(255,255,255,0.4400));
    }

    .chuzu.color{
        background: #12F1C4;
    }

    .kongguan.color{
        background: linear-gradient(-24deg, rgba(3,0,0,0.4400), rgba(255,255,255,0.4400));
    }
}
.unit_sel{
	position: absolute;
	left: 34%;
	width: 52%;
	height: 40px;
	padding: 0px 20px;
	.tabUnit{
		width: 53px;
		color: white;
		font-size: 20px;
		margin: 2px 1px;
		cursor: pointer;
		float: left;
		text-align: center;
	}
	.tabUnit.active{
		color: #05caa9;
	}
}
.RoomBody {
    margin: 10px 0px;
    display: flex;
    flex-wrap:wrap;
    justify-content:center;
    flex-direction: row;
    color: #FFF;
    height: 445px;
    overflow-y: auto;
}
.build_sel{
  position: absolute;
  width:25%;
  height:500px;
  text-align: center;
  color: #fff;
  font-size: 20px;
  line-height: 30px;
  .tab{
    width: 130px;
    height: 45px;
    background-size: 100% 100%;
    margin: 5px 2px 1px 10px;
    cursor: pointer;
	line-height: 45px;
	font-size: 25px;
  }
  .tab.active{
    background-image:url('~@/assets/images/housePlane/activeTabGB.png');
  }
}
.oneRoom{
    width: 70px;
    text-align: center;
    margin: 10px;
    height: 25px;
	line-height: 25px;
    cursor: pointer;
	font-size: 20px;
	float: left;
}
.oneRoom.active{
	color: #05caa9;
}
.roomType_1{
    width: 70px;
    text-align: center;
    margin-right: 10px;
    margin-bottom: 10px;
    height: 32px;
    background: rgba(44, 149, 237, 0.45);
    border: 1px solid #296FB3;
    border-radius: 1px;
}
.roomType_3{
    width: 70px;
    text-align: center;
    margin-right: 10px;
    margin-bottom: 10px;
    height: 32px;
    background: rgba(15,239,186,0.4500);
    border: 1px solid rgba(22,244,208,0.8400);
    border-radius: 1px;
}
.roomType_2{
    width: 70px;
    text-align: center;
    margin-right: 10px;
    margin-bottom: 10px;
    height: 32px;
    background: rgba(169,170,172,0.2500);
    border: 1px solid #777777;
    border-radius: 1px;
}

</style>