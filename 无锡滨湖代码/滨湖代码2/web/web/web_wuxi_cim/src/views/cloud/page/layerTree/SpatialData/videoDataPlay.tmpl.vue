<template>
	<cloud-tmpl title="播放" :top="100" :scrollHeight="25" @close='handleClose'>
		<template v-slot:content>
			<div class="content-box inq-tmpl-body">
				<div class="video-container">
					<LivePlayer :videoUrl="videoURL" fluent autoplay loop live aspect='16:9' :controls="false"
						:hide-big-play-button="true" />
				</div>
				<div class="inf-box">
					<div class="left">
						<h3> {{ videoTitle }} </h3>
						<p><b>位置：</b>{{ videoAddress }}</p>
					</div>
					<el-button type="text" icon="el-icon-location-information" @click="positionEvent"></el-button>
				</div>
			</div>
		</template>
	</cloud-tmpl>
</template>

<script>
import LivePlayer from '@liveqing/liveplayer'
// import { mapGetters } from "vuex";
// import { post } from "@/utils/fetch";
export default {
	name: "videoDataPlay.tmpl",
	components: { LivePlayer },
	props: ["row", 'gridBindFieldInfo'],
	data() {
		return {
			videoTitle: "",
			videoAddress: "",
			videoURL: "",
		};
	},
	// computed: {
	// 	...mapGetters(["token"])
	// },
	created() {
		if (this.row) {
			// this.videoURL = this.row[this.gridBindFieldInfo.specialApiUrl];
			this.videoTitle = this.row[this.gridBindFieldInfo.featureName];
			this.videoAddress = this.gridBindFieldInfo.specialAddress ? this.row[this.gridBindFieldInfo.specialAddress] : ""
		}
	},
	mounted() {
		this.videoURL = this.row[this.gridBindFieldInfo.specialApiUrl];

		// if (this.row.devicecode) this.getVideoURL(this.row.devicecode);
		// else this.$message.warning("监控设别识别码缺失！");
	},
	methods: {
		/* 播放地址解析 */
		// getVideoURL(devicecode) {
		// 	let formData = new FormData();
		// 	formData.append(
		// 		"authUrl",
		// 		`${oneMachineOneGear}/auth/login/v1/outsideLogin`
		// 	);
		// 	formData.append("userName", "ddlogindh");
		// 	formData.append("userPassword", "laoshan2021@dh");
		// 	formData.append(
		// 		"videoUrl",
		// 		`${oneMachineOneGear}/omog/towerVideo/v1/getVideoRealtimeUrl`
		// 	);
		// 	formData.append(
		// 		"channelUrl",
		// 		`${oneMachineOneGear}/omog/towerVideo/v1/queryDeviceInfoList`
		// 	);
		// 	formData.append("deviceCode", devicecode);
		// 	formData.append("streamType", "1");
		// 	formData.append("protocolType", "4");
		// 	formData.append("netType", "1");

		// 	post(`${cim_main}omog/getvideo/info?token=${this.token}`, formData)
		// 		.then((res) => {
		// 			if (res.code === 200) this.videoURL = res.data.streamUrl;
		// 			else this.$message.error("获取播放地址失败");
		// 		})
		// 		.catch((e) => {
		// 			this.$message.error("获取播放地址出错");
		// 			console.error(e);
		// 		});
		// },
		/* 定位 */
		positionEvent() {
			this.$emit('positionEvent', this.row)
		},
		handleClose() {
			this.$emit('closeEvent')
		}
	}
};
</script>

<style lang="scss" scoped>
.inq-tmpl-body {

	.video-container {
		width: 100%;
		max-height: 200px;
		background-color: #000;
		margin-bottom: 10px;
	}

	.inf-box {
		display: flex;
		align-items: flex-start;
		justify-content: space-between;

		.left {
			width: calc(100% - 50px);

			h3 {
				margin: 0;
				font-size: 16px;
				color: #fff;
			}

			p {
				font-size: 14px;
				color: #ccc;
				margin-top: 5px;
			}
		}

		.el-button {
			color: #fff;
			font-size: 20px;
		}
	}
}
</style>