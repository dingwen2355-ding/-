<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import Home from "./views/Home.vue";
import axios from "axios";
import "@/assets/font/font.css";
import ScaleBox from "vue3-scale-box";
let showMap = ref(true);
function mapComplate(val) {
	showMap.value = val;
}
onMounted(() => {
	let queryData = {};
	let href = window.location.href;
	let query = href.split("?");
	// if (query.length > 1) {
	// 	let data = query[1].split("&");
	// 	data.forEach(e => {
	// 		queryData[e.split("=")[0]] = e.split("=")[1];
	// 	});
	// 	console.log("========1==")
	// 	console.log(window.sessionStorage.getItem("token"))
	// 	if(!window.sessionStorage.getItem("token")) {
	// 		axios.post(IP + "/system/login/tohome/screen?code=" + queryData.code).then(res => {
	// 			if (!res) {

	// 				return;
	// 			} else {
	// 				console.log("--------------2222222222222222-");
	// 				console.log(res.data);
	// 				if(res.data.code == 500) {
	// 					axios.get(IP + "/system/login/authorization/screen").then(res => {
	// 						if (!res) {
	// 							return;
	// 						} else {
	// 							window.open(res.data.data, "_self");
	// 						}
	// 					});
	// 				} 
	// 				window.sessionStorage.setItem("token", res.data.data.access_token);
	// 				window.open("/wmcs/", "_self");
	// 			}
	// 		});
	// 	}
	// } else {
	// 	console.log("调用统一身份认证");
	// 	axios.get(IP + "/system/login/authorization/screen").then(res => {
	// 		if (!res) {
	// 			return;
	// 		} else {
	// 			window.open(res.data.data, "_self");
	// 		}
	// 	});
	// }
});
onBeforeMount(() => {
	if (import.meta.env.MODE == "dev") {
		const oScript = document.createElement("script");
		oScript.type = "text/javascript";
		oScript.src = "//minedata.com.cn/minemapapi/v2.1.0/minemap.js";
		document.head.appendChild(oScript);
		const link1 = document.createElement("link");
		link1.rel = "stylesheet";
		link1.href =
			"//minedata.com.cn/support/static/api/demo/js-api/zh/css/demo.css";
		document.head.appendChild(link1);
		const link2 = document.createElement("link");
		link2.rel = "stylesheet";
		link2.href = "//minedata.com.cn/minemapapi/v2.1.0/minemap.css";
		document.head.appendChild(link2);
	} else {
		const oScript = document.createElement("script");
		oScript.type = "text/javascript";
		oScript.src = "//2.22.131.158:21009/minemapapi/v2.1.0/minemap.js";
		document.head.appendChild(oScript);
		const link1 = document.createElement("link");
		link1.rel = "stylesheet";
		link1.href =
			"//2.22.131.158:21009/support/static/api/demo/js-api/zh/css/demo.css";
		document.head.appendChild(link1);
		const link2 = document.createElement("link");
		link2.rel = "stylesheet";
		link2.href = "//2.22.131.158:21009/minemapapi/v2.1.0/minemap.css";
		document.head.appendChild(link2);
	}

	// axios.get("json/polygon.json").then(res => {
	//   let rows = res.data.RECORDS;
	//   console.log(rows);

	//   axios.get("json/bianjie.json").then(res2 => {
	//     let rows2 = res2.data.features;
	//     console.log(rows2);
	//     rows.forEach(element => {
	//       rows2.forEach(element2 => {
	//         if (element.name.indexOf(element2.properties.name) != -1) {
	//           console.log(element.name);
	//           console.log(JSON.stringify(element2.geometry));
	//         }
	//       });
	//     });
	//   })
	// })
});
</script>

<template>
	<!--
		<ScaleBox
		:width="3840"
		:height="945"
		bgc="transparent"
		:delay="100"
		:isFlat="false"
	>
	</ScaleBox>
	-->
	<Home></Home>
</template>

<style lang="scss">
::-webkit-scrollbar {
	width: 10px;
}

::-webkit-scrollbar-thumb {
	border-radius: 10px;
	background: rgba(0, 0, 0, 0.2);
}

::-webkit-scrollbar-track {
	border-radius: 0;
	background: rgba(0, 0, 0, 0.1);
}

html,
body {
	width: 3840px;
	height: 945px;
	font-family: alibaba85;
	overflow: hidden;
}

#app {
	font-family: alibaba85;
}

.flex-row {
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-around;
}

.flex-col {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: space-around;
}

.gap {
	width: 2px;
	height: 100%;
	background: linear-gradient(to bottom, #ffffff00, #fff, #ffffff00);
}
</style>
