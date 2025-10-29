<template>
	<cloud-tmpl :title='cmptData.name' :scrollHeight='32' @close='handleClose'>
		<template v-slot:content>
			<div class="weather-warp">
				<div class="warp-list">
					<div class="list-title open">
						开启{{ cmptData.name }}
						<el-switch v-model="weartherSetting[cmptData.name].open" active-color="#13ce66"
							inactive-color="#979797" @change="changeOpen">
						</el-switch>
					</div>
				</div>
				<template v-for="(item, index) in weartherSetting[cmptData.name].data">
					<div :key="index" class="warp-list">
						<div class="list-title">{{ item.name }}</div>
						<div class="list">
							<el-slider :min="item.min" :max="item.max" :step="item.step" v-model="item.value"
								@change="changeValue" :show-input="false" :show-input-controls="false"></el-slider>
							<el-input-number v-model="item.value" controls-position="right" :max="item.max" :min="item.min"
								:step="item.step" @change="changeValue"></el-input-number>
						</div>
					</div>
				</template>
			</div>
		</template>
	</cloud-tmpl>
</template>

<script>
export default {
	props: {
		cmptData: {
			type: Object,
			default: {},
		},
	},
	name: "WeatherSetting",
	data() {
		return {
			weartherSetting: {}
		};
	},
	created() {
		this.weartherSetting = window.sealAPI._weather.getPARAM()
	},
	methods: {
		changeOpen(open) {
			this.toSettingWeather(
				open,
				this.cmptData.name,
				this.weartherSetting[this.cmptData.name].data
			);
		},
		changeValue(e) {
			if (this.weartherSetting[this.cmptData.name].open) {
				this.toSettingWeather(
					true,
					this.cmptData.name,
					this.weartherSetting[this.cmptData.name].data
				);
			}
		},
		async toSettingWeather(open, name, data) {
			if (open) await window.sealAPI._weather.setDarkMode_biz(false);
			if (open) {
				if (name == "雨") {
					if (this.weartherSetting['雪'].open) this.weartherSetting['雪'].open = false;
					window.sealAPI._weather.setRainParam(
						data[0].value,
						data[1].value,
						data[2].value
					);
				} else if (name == "雪") {
					if (this.weartherSetting['雨'].open) {
						this.weartherSetting['雨'].open = false;
					}
					window.sealAPI._weather.setSnowParam(
						data[0].value,
						data[1].value,
						data[2].value
					);
				} else if (name == "雾") {
					window.sealAPI._weather.setFogParam(
						data[0].value,
						data[1].value,
						data[2].value
					);
				} else if (name == "云") {
					window.sealAPI._weather.setCloudDensity(
						data[0].value,
					);
				}
			}
			else if (name == "雾") window.sealAPI._weather.setFogParam(0, 0, 0);
			else if (name == "云") window.sealAPI._weather.setCloudDensity(0);
			else window.sealAPI._weather.disableRainSnow();
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		}
	},
	beforeDestroy() {
		this.handleClose();
	}
};
</script>

<style lang="scss" scoped>
.weather-warp {
	.list-title {
		&.open {
			width: 100%;
			padding-bottom: 20px;
			margin-bottom: 12px;
			border-bottom: 1px solid rgba(255, 255, 255, 0.1);

			::v-deep.el-switch {
				margin-left: 6px;
			}
		}
	}

	.warp-list {
		.list {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin: 0 !important;
		}

		&:not(:first-child) {
			margin-top: 6px;
		}
	}

	::v-deep.el-input-number {
		max-width: 80%;

		&.is-controls-right {

			.el-input-number__decrease,
			.el-input-number__increase {
				width: 22px;
				height: 16px;
				top: -2px;
				padding-right: 10px;
				box-sizing: border-box;
				background: transparent;
				border: none;

				i {
					font-size: 20px;

					&.el-icon-arrow-down:before {
						content: "\e790";
					}

					&.el-icon-arrow-up:before {
						content: "\e78f";
					}
				}
			}

			.el-input-number__decrease {
				right: 1px;
				bottom: 2px;
				top: auto;
				left: auto;
			}
		}

		.el-input__inner {
			border-radius: 4px;
			outline: none;
			height: 34px;
			color: #ffffff;
			border: 1px solid #646b6f;
			background: #494d52;
			padding-left: 7px;
			padding-right: 22px;

			&:focus {
				outline: none;
				border-radius: 4px;
				border: 1px solid;
				border-color: #02b2ff;
			}
		}
	}

	::v-deep.el-slider {
		width: 86%;
		margin: 0 20px 0 12px;
	}
}
</style>