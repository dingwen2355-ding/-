<template>
	<div class="cloud-func" v-show="showPanle">
		<div class="func-title">
			可视域
			<span @click="handleClose">
				<i class="el-icon-close"></i>
			</span>
		</div>
		<div class="func-warp">
			<el-scrollbar>
				<template v-for="(item, index) in setting">
					<template v-if="item.type === 'input'">
						<div :key="index" class="warp-list">
							<p>{{ item.name }}</p>
							<el-input @change="changeValueName" v-model="item.value" placeholder="请输入"></el-input>
						</div>
					</template>
					<template v-else-if="item.type === 'select'">
						<div :key="index" class="warp-list">
							<p>{{ item.name }}</p>
							<el-select v-model="item.value" @change="changeValue" placeholder="请选择样式">
								<el-option v-for="(op, i) in item.options" :key="i" :label="op.label" :value="op.value">
								</el-option>
							</el-select>
						</div>
					</template>
					<template v-else-if="item.type === 'slider'">
						<div :key="index" class="warp-list">
							<p>{{ item.name }}</p>
							<div class="list">
								<el-slider :min="item.min" :max="item.max" :step="item.step" v-model="item.value"
									@change="changeValue" :show-input="false" :show-input-controls="false"></el-slider>
								<el-input-number v-model="item.value" @change="changeValue" controls-position="right"
									:min="item.min" :max="item.max" :step="item.step"></el-input-number>
							</div>
						</div>
					</template>
					<template v-else-if="item.type === 'array'">
						<div :key="index" class="warp-list">
							<p>{{ item.name }}</p>
							<div class="list number">
								<el-input-number v-for="(val, j) in item.value" :key="j" v-model="item.value[j]"
									@change="changeValue" controls-position="right" :min="item.min" :max="item.max"
									:step="item.step"></el-input-number>
							</div>
						</div>
					</template>
					<template v-else-if="item.type === 'sliderrange'">
						<div :key="index" class="warp-list">
							<p>{{ item.name }}下限</p>
							<div class="list">
								<el-slider :min="item.min" :max="item.value[1]" :step="item.step" v-model="item.value[0]"
									@change="changeValue" :show-input="false" :show-input-controls="false"></el-slider>
								<el-input-number v-model="item.value[0]" @change="changeValue" controls-position="right"
									:min="item.min" :max="item.value[1]" :step="item.step"></el-input-number>
							</div>
						</div>
						<div :key="index" class="warp-list">
							<p>{{ item.name }}上限</p>
							<div class="list">
								<el-slider :min="item.value[0]" :max="item.max" :step="item.step" v-model="item.value[1]"
									@change="changeValue" :show-input="false" :show-input-controls="false"></el-slider>
								<el-input-number v-model="item.value[1]" @change="changeValue" controls-position="right"
									:min="item.min" :max="item.max" :step="item.step"></el-input-number>
							</div>
						</div>
					</template>
					<template v-else-if="item.type === 'color'">
						<div :key="index" class="warp-list">
							<span>{{ item.name }}</span>
							<div class="list color">
								<div>
									<svg-icon icon-class="cloud-color" class-name="icon"></svg-icon>
									<el-color-picker v-model="item.value" @change="changeValue"></el-color-picker>
								</div>
								<el-input v-model="item.value" placeholder="输入，例如:#FF0000" @change="changeValue"></el-input>
							</div>
						</div>
						<div :key="index" class="warp-list" v-if="item.opacity !== undefined">
							<p>透明度</p>
							<div class="list">
								<el-slider :min="0" :max="1" :step="0.1" v-model="item.opacity" @change="changeValue"
									:show-input="false" :show-input-controls="false"></el-slider>
								<el-input-number v-model="item.opacity" @change="changeValue" controls-position="right"
									:min="0" :max="1" :step="0.1"></el-input-number>
							</div>
						</div>
					</template>
					<template v-else-if="item.type === 'radio'">
						<div :key="index" class="warp-list">
							<span>{{ item.name }}</span>
							<el-radio-group v-model="item.value" @change="changeValue">
								<el-radio v-for="(op, i) in item.options" :key="i" :value="op.value" :label="op.value">{{
									op.label
								}}</el-radio>
							</el-radio-group>
						</div>
					</template>
				</template>
			</el-scrollbar>
			<div class="btn-wrap">
				<div @click="reset()">重置</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: "Viewshed",
	data() {
		return {
			showPanle: false,
			value: 5,
			opacity: 0.5,
			setting: undefined,
			//可视域默认参数
			_originSetting: undefined,
		};
	},
	watch: {
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.eventtype == "ViewShed") this.showPanle = true
			}
		}
	},
	mounted() {
		window.sealAPI._viewshed.start();
		this.$message.success("进入可视域分析！");

		this.settingLoad();
	},
	methods: {
		settingLoad() {
			let setting = window.sealAPI._viewshed.getSetting().setting;
			if (setting) {
				this.setting = JSON.parse(JSON.stringify(setting))
				this._originSetting = JSON.parse(JSON.stringify(setting))
			}
		},
		reset() {
			this.setting = JSON.parse(JSON.stringify(this._originSetting));
			this.changeValue();
		},
		changeValue() {
			var curOa = window.sealAPI._viewshed.getSetting().curOa;
			if (curOa) {
				for (var i = 0; i < this.setting.length; i++) {
					if (this.setting[i].type === "color") {
						var r = parseInt(this.setting[i].value.slice(1, 3), 16) / 255;
						var g = parseInt(this.setting[i].value.slice(3, 5), 16) / 255;
						var b = parseInt(this.setting[i].value.slice(5, 7), 16) / 255;
						curOa[this.setting[i].key] = [r, g, b, this.setting[i].opacity];
					} else {
						curOa[this.setting[i].key] = this.setting[i].value;
					}
				}
				window.sealAPI._viewshed.update();
			}
		},
		changeValueName(value) {
			var curOa = window.sealAPI._viewshed.getSetting().curOa;
			curOa["name"] = value;
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	beforeDestroy() {
		window.sealAPI._viewshed.cancel();
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

	.list {
		margin: 0 !important;
	}

	/deep/.el-radio-group {
		display: block;
		margin-top: 12px;
		margin-bottom: 12px;
	}

	/deep/.number {
		margin-top: 12px !important;
		margin-bottom: 12px !important;

		&>div:first-child {
			margin-right: 20px;
		}
	}

	/deep/.color {
		justify-content: left !important;
		margin-top: 12px !important;
		margin-bottom: 12px !important;

		&>div:first-child {
			display: flex;
			margin-right: 20px;

			.el-color-picker {
				width: 32px;
				height: 26px;
			}

			.el-color-picker__trigger {
				border: none;
				padding: 0;
				height: 26px;
			}

			.el-color-picker__color {
				border: none;
			}
		}

		.el-input {
			width: 120px;
		}
	}

	/deep/.el-slider {
		width: 86%;
		margin: 0 20px 0 12px;
	}
}
</style>