<template>
	<div class="cloud-func">
		<div class="func-title">
			土方平衡
			<span @click.stop="handleClose">
				<i class="el-icon-close"></i>
			</span>
		</div>
		<div class="func-warp" v-show="!earthworkOpen">
			<p class="prompt">
				选择分析区域，图形闭合结束！
			</p>
		</div>
		<div class="func-warp" v-show="earthworkOpen">
			<div class="warp-list legend">
				<div><span>填方线</span><label class="fillline"></label></div>
				<div><span>挖方线</span><label class="cutLine"></label></div>
			</div>
			<div class="warp-list volumes">
				<div>
					<span>填方体积:</span><label class="volume">{{ fillVolume }} m³</label>
				</div>
				<div>
					<span>挖方体积:</span><label class="volume">{{ cutVolume }} m³</label>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">高度（米）</div>
				<div class="list">
					<el-slider :min="-100" :max="500" :step="10" v-model="earthwork.height" @change="changeValue2"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number v-model="earthwork.height" controls-position="right" :min="-200" :max="500" :step="1"
						@change="changeValue2"></el-input-number>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">网格大小</div>
				<div class="list">
					<el-slider :min="1" :max="30" :step="1" v-model="earthwork.gridSize" @change="changeValue2"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number v-model="earthwork.gridSize" controls-position="right" :min="1" :max="30" :step="1"
						@change="changeValue2"></el-input-number>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">线段宽度（米）</div>
				<div class="list">
					<el-slider :min="1" :max="30" :step="1" v-model="earthwork.lineThickness" @change="changeValue2"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number v-model="earthwork.lineThickness" controls-position="right" :min="1" :max="30"
						:step="1" @change="changeValue2"></el-input-number>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">点大小</div>
				<div class="list">
					<el-slider :min="1" :max="30" :step="1" v-model="earthwork.pointSize" @change="changeValue2"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number v-model="earthwork.pointSize" controls-position="right" :min="1" :max="30" :step="1"
						@change="changeValue2"></el-input-number>
				</div>
			</div>
			<div class="warp-list">
				<div class="list-title">网格线宽度（米）</div>
				<div class="list">
					<el-slider :min="1" :max="30" :step="1" v-model="earthwork.gridLineThickness" @change="changeValue2"
						:show-input="false" :show-input-controls="false"></el-slider>
					<el-input-number v-model="earthwork.gridLineThickness" controls-position="right" :min="1" :max="30"
						:step="1" @change="changeValue2"></el-input-number>
				</div>
			</div>
			<div class="btn-wrap" v-show="earthworkOpen">
				<div @click="changeOpen()">重新开始</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: "CutFill",
	data() {
		return {
			earthworkOpen: false,
			cutVolume: "--",
			fillVolume: "--",
			earthwork: {
				height: 100, //高度 米
				gridSize: 5, //网格大小
				lineThickness: 2, //线段宽度
				pointSize: 5, //点的大小
				gridLineThickness: 2, //网格线的宽度
				// 颜色
				cutLineColor: Color.Red, //挖方线的颜色
				fillLineColor: Color.Green, //填方线的颜色
				pointColor: Color.Blue, //点的颜色
				gridColor: Color.Yellow, //网格线的颜色
			},
		};
	},
	watch: {
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.eventtype == "cutFillAnalysis") {
					let { fillVolume, cutVolume } = val;
					if (fillVolume && cutVolume) {
						this.fillVolume = fillVolume;
						this.cutVolume = cutVolume;
						this.earthworkOpen = true;
					}
				}
			}
		},
	},
	mounted() {
		window.sealAPI._tools.startCutFillAnalysis(this.earthwork);
	},
	methods: {
		async changeOpen() {
			await window.sealAPI._tools.stopCutFillAnalysis();
			await window.sealAPI._tools.startCutFillAnalysis(this.earthwork);
			this.earthworkOpen = false;
		},
		async changeValue2(e) {
			this.$nextTick(() => {
				window.sealAPI._tools.startCutFillAnalysis(this.earthwork);
			});
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	beforeDestroy() {
		window.sealAPI._tools.stopCutFillAnalysis();
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

.warp-list {
	&:not(:first-child) {
		margin-top: 6px;
	}

	&.legend,
	&.volumes {
		div:not(:first-child) {
			margin-top: 10px;
		}

		span {
			margin-right: 6px;
		}
	}

	&.legend div {
		display: flex;
		align-items: center;

		label {
			display: inline-block;
			margin-left: 10px;
			width: 16px;
			height: 16px;
			border-radius: 50%;

			&.fillline {
				background: green;
			}

			&.cutLine {
				background: red;
			}
		}
	}

	&.volumes {
		margin: 10px 0 16px 0;
	}

	.list {
		margin: 0 !important;
	}

	/deep/.el-slider {
		width: 82%;
		margin-right: 20px;
	}
}

.btn-wrap {
	margin-top: 24px;
}
</style>