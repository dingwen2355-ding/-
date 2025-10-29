<template>
	<div class="cloud-func" v-show="showSetting">
		<div class="func-title">
			体剖切
			<span @click.stop="handleClose">
				<i class="el-icon-close"></i>
			</span>
		</div>

		<div class="func-warp" v-show="showSetting">
			<template v-for="(item, index) in setting">
				<div :key="index" class="warp-list" v-if="item.type === 'slider'">
					<div class="list-title">{{ item.name }}</div>
					<div class="list">
						<el-slider :min="item.min" :max="item.max" :step="item.step" v-model="item.value"
							@change="changeValue" :show-input="false" :show-input-controls="false"></el-slider>
						<el-input-number controls-position="right" :min="item.min" :max="item.max" :step="item.step"
							v-model="item.value" @change="changeValue"></el-input-number>
					</div>
				</div>
				<div :key="index" class="warp-list" v-if="item.type === 'radio'">
					<div class="list-title">{{ item.name }}</div>
					<el-radio-group v-model="item.value" @change="changeValue">
						<el-radio v-for="(op, i) in item.options" :key="i" :value="op.value" :label="op.value">{{ op.label
						}}</el-radio>
					</el-radio-group>
				</div>
			</template>
			<div class="btn-wrap">
				<div :class="{ bg: toolChose }" @click="handlerMeasuring">测 量</div>
				<div :class="{ bg: isCoorPickup }" @click="handlerCoorPickup">坐 标</div>
			</div>
			<div class="btn-wrap">
				<div @click="handleDownload">保存截图</div>
				<div @click="reLoadFun">重 置</div>
			</div>
		</div>
	</div>
</template>

<script>
import { getCurCameraImage } from "@/utils/loadScreenshot.js";
export default {
	name: "VolumeSection",
	data() {
		return {
			showSetting: false,
			toolChose: false,
			isCoorPickup: false,
			value: 5,
			opacity: 0.5,
			setting: [],
			settingTemp: [],
		};
	},
	watch: {
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.MouseClickPoint) {
					this.setting.some((item) => {
						if (item.type === "slider") {
							this.settingTemp = JSON.parse(JSON.stringify(this.setting));
							this.showSetting = true;
						}
					});
				}
			},
		},
	},
	async mounted() {
		await window.sealAPI._clip.cancel();
		window.sealAPI._clip.start(2);
		this.$message.success("进入体剖切！");

		this.settingLoad();
	},
	methods: {
		settingLoad() {
			let setting = window.sealAPI._clip.getSetting().setting
			if (setting) {
				this.setting = JSON.parse(JSON.stringify(setting))
			}
		},
		handleDownload() {
			const filename = "体剖切_" + new Date().getTime();
			getCurCameraImage(true, filename);
		},
		handlerCoorPickup() {
			this.toolChose = false;
			if (this.isCoorPickup) {
				window.sealAPI._tools.stopMeasurement_biz();
				this.$message({
					type: "info",
					duration: 500,
					message: "关闭测量工具！",
				});
				return (this.isCoorPickup = false);
			}
			let item = {
				id: 44,
				label: "坐标",
				pid: 4,
				name: "坐标",
				src: "cloud-measurement-4",
				mode: 1,
			};
			window.sealAPI._tools.setMeasurement_biz(item.mode);
			this.isCoorPickup = true;
			this.$message.success("点击场景拾取坐标！");
		},
		reLoadFun() {
			this.setting = JSON.parse(JSON.stringify(this.settingTemp));
			this.changeValue();
		},
		handlerMeasuring() {
			this.isCoorPickup = false;
			if (this.toolChose) {
				window.sealAPI._tools.stopMeasurement_biz();
				this.$message({
					type: "info",
					duration: 500,
					message: "关闭测量工具！",
				});
				return (this.toolChose = false);
			}

			this.toolChose = true;
			this.$message.success("启动测量工具！");
			let item = {
				id: 41,
				label: "直线",
				mode: 2,
				name: "直线",
				pid: 4,
				src: "cloud-measurement-1",
			};
			// 设置测量 0 1 2
			window.sealAPI._tools.setMeasurement_biz(item.mode);
		},
		changeValue() {
			var curOa = window.sealAPI._clip.getSetting().curOa;
			if (!curOa.rocationy) {
				for (var i = 0; i < this.setting.length; i++) {
					curOa[this.setting[i].key] = this.setting[i].value;
				}
			} else {
				for (var i = 0; i < this.setting.length; i++) {
					curOa[this.setting[i].key] = curOa.rocationy - this.setting[i].value;
				}
			}
			window.sealAPI._clip.update();
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	async beforeDestroy() {
		await window.sealAPI._clip.cancel();
		window.sealAPI._tools.stopMeasurement_biz();
		this.handleClose();
	},
};
</script>

<style lang="scss" scoped>
@import "~@/views/cloud/page/css/func3.scss";

.warp-list {
	&:not(:first-child) {
		margin-top: 6px;
	}

	div.list {
		margin: 0 !important;
	}

	/deep/.el-radio-group {
		margin-top: 12px;
		margin-bottom: 12px;
	}

	/deep/.el-slider {
		width: 76%;
		margin-right: 20px;
	}
}

.btn-wrap {
	&:first-child {
		margin-top: 24px;
	}

	&:not(:first-child) {
		margin-top: 12px;
	}
}
</style>