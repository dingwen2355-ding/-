<template>
	<div class="cloud-func">
		<div class="func-title">
			日照分析
			<span @click.stop="handleClose">
				<i class="el-icon-close"></i>
			</span>
		</div>

		<div class="func-warp" v-show="!showOptions">
			<p class="prompt">
				选择分析区域，图形闭合结束！
			</p>
			<div class="btn-wrap">
				<div @click="changeOpen()">重新开始</div>
			</div>
		</div>

		<div class="func-warp" v-show="showOptions">
			<div class="warp-list legend">
				<span>日照颜色</span>
				<img src="@/assets/images/cloud/page/analysis-sunlight.legend.png" />
			</div>
			<div class="warp-list">
				<div class="list-title">分析日期</div>
				<div class="list analy-data-box">
					<el-date-picker v-model="analyData" type="date" placeholder="选择日期">
					</el-date-picker>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">时间区间</div>
				<div class="list analy-data-box">
					<el-time-select placeholder="起始时间" v-model="option.startTime" :picker-options="{
							start: '00:00',
							step: '00:30',
							end: '23:00',
						}">
					</el-time-select>
					<el-time-select placeholder="结束时间" v-model="option.endTime" :picker-options="{
							start: '00:30',
							step: '00:30',
							end: '23:30',
							minTime: option.startTime,
						}">
					</el-time-select>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">采样距离（米）</div>
				<div class="list">
					<el-slider :min="0" :max="100" :step="5" v-model="option.spacing" @change="changeValue"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number controls-position="right" :min="0" :max="100" :step="1" v-model="option.spacing"
						@change="changeValue"></el-input-number>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">底面高度（米）</div>
				<div class="list">
					<el-slider :min="-20" :max="200" :step="5" v-model="option.undersideHeight" @change="changeValue"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number controls-position="right" :min="-20" :max="200" :step="1"
						v-model="option.undersideHeight" @change="changeValue"></el-input-number>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">高度（米）</div>
				<div class="list">
					<el-slider :min="-10" :max="500" :step="5" v-model="option.height" @change="changeValue"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number controls-position="right" :min="-10" :max="500" :step="1" v-model="option.height"
						@change="changeValue"></el-input-number>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">绘制半径（米）</div>
				<div class="list">
					<el-slider :min="0" :max="50" :step="1" v-model="option.sphereRadius" @change="changeValue"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number controls-position="right" :min="0" :max="50" :step="1" v-model="option.sphereRadius"
						@change="changeValue"></el-input-number>
				</div>
			</div>

			<div class="btn-wrap btn">
				<div @click="changeOpen()">重新开始</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: "Sunshine",
	data() {
		return {
			showOptions: false,
			analyData: new Date(),
			option: {
				year: 2022, //年，取值范围：[1970~至今年份]，默认值：当前日期
				month: 1, //月，取值范围：[1~12]，默认值：当前日期
				day: 12, //日，取值范围：[1~31]，默认值：当前日期
				startTime: "08:00",
				endTime: "16:00",
				spacing: 8, //间距，取值范围：[任意负数~任意正数]，默认值：-1米
				undersideHeight: 0, //底面高度，取值范围：[任意负数~任意正数]，默认值：0米
				height: 100, //高度，取值范围：[0~任意正数]，默认值：5000米
				sphereRadius: 2, //日照半径，取值范围：[0~任意正数]，默认值：200米
			},
		};
	},
	watch: {
		analyData: {
			immediate: true,
			deep: true,
			handler(value) {
				let val = null;
				try {
					val = new Date(value);
				} catch (err) {
					console.error("日期转换出错", err);
					val = new Date();
				}
				let dateInfo = {
					year: val.getFullYear(),
					month: val.getMonth() + 1,
					day: val.getDate(),
				};
				this.option = { ...this.option, ...dateInfo };
			},
		},
		option: {
			deep: true,
			immediate: true,
			handler(val) {
				let { startTime, endTime } = val;
				if (startTime.replace(":", "") - endTime.replace(":", "") >= 0)
					this.option = { ...val, endTime: "23:30" };
			},
		},
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.eventtype == "Sunshine") {
					this.showOptions = true;
				}
			},
		},
	},
	mounted() {
		this.$nextTick(() => {
			window.sealAPI._tools.startSunshineAnalysis(this.option);
			this.$message.success("选择分析区域，图形闭合结束！");
		});
	},
	methods: {
		async changeOpen() {
			this.showOptions = false;
			await window.sealAPI._tools.stopSunshineAnalysis();
			await window.sealAPI._tools.startSunshineAnalysis(this.option);
		},
		changeValue(e) {
			window.sealAPI._tools.startSunshineAnalysis(this.option);
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	beforeDestroy() {
		window.sealAPI._tools.stopSunshineAnalysis();
		this.handleClose();
	},
};
</script>

<style lang="scss" scoped>
@import "~@/views/cloud/page/css/func3.scss";

.prompt {
	margin: 20px 0;
	text-align: center;
}

.list {
	margin: 0 !important;
}

.legend {
	display: flex;
	align-items: center;
	margin-bottom: 12px;

	img {
		margin-left: 20px;
	}
}

/deep/.analy-data-box {
	margin-top: 10px !important;
	margin-bottom: 12px !important;

	.el-date-editor {
		&:nth-child(2) {
			margin-left: 20px;
		}
	}
}

/deep/.el-slider {
	width: 86%;
	margin-right: 20px;
}

.btn-wrap {
	&.btn {
		margin-top: 24px;

	}
}
</style>