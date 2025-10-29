<template>
	<cloud-tmpl title='垂直' :scrollHeight='42' @close='handleClose'>
		<template v-slot:content>
			<ul class="content-warp">
				<li>
					<span>水平宽度</span> <span>{{ horizontalLength }} m</span>
				</li>
				<li>
					<span>垂直高度</span> <span>{{ verticalHeight }} m</span>
				</li>
			</ul>

			<div class="content-coordinate">
				<p>起点</p>
				<ul class="content-warp">
					<li>
						<span>X</span> <span>{{ startCoordinate[0] }}</span>
					</li>
					<li>
						<span>Y</span> <span>{{ startCoordinate[1] }}</span>
					</li>
					<li>
						<span>Z</span>
						<span>{{ startCoordinate[2] }}</span>
					</li>
				</ul>

				<p>终点</p>
				<ul class="content-warp">
					<li>
						<span>X</span> <span>{{ endCoordinate[0] }}</span>
					</li>
					<li>
						<span>Y</span> <span>{{ endCoordinate[1] }}</span>
					</li>
					<li>
						<span>Z</span> <span>{{ endCoordinate[2] }}</span>
					</li>
				</ul>
			</div>
		</template>
	</cloud-tmpl>
</template>

<script>
export default {
	name: "Vertical",
	data() {
		return {
			horizontalLength: 0,
			verticalHeight: 0,
			startCoordinate: [],
			endCoordinate: [],
		};
	},
	watch: {
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.Type == "VerticalLine") {
					this.horizontalLength = val.HorizontalLength
					this.verticalHeight = val.VerticalHeight
					this.startCoordinate = this.coorRoundFix(val.StartCoordinate)
					this.endCoordinate = this.coorRoundFix(val.EndCoordinate)
				}
			},
		},
	},
	mounted() {
		this.setMeasurement();
	},
	methods: {
		async setMeasurement() {
			await window.sealAPI._tools.setMeasurement(MeasurementMode.Vertical, null);
			window.sealAPI._tools.startMeasurement();
		},
		// 坐标处理
		coorRoundFix(coor) {
			let coorRound = []
			coorRound[0] = Math.round(coor[0] * 100) / 100;
			coorRound[1] = Math.round(coor[1] * 100) / 100;
			coorRound[2] = Math.round(coor[2] * 100) / 100;
			return coorRound
		},
		async cancelMeasurement() {
			await window.sealAPI._tools.stopMeasurement();
		},
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	async beforeDestroy() {
		await this.cancelMeasurement();
		await this.handleClose();
	}
};
</script>

<style lang="scss" scoped>
.content-warp {
	font-size: 16px;
	color: #ffffff;

	li {
		padding: 0 10px;
		line-height: 56px;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
}

.content-coordinate {
	&>p {
		padding: 10px 10px;
	}

	.content-warp {
		li {
			line-height: 30px;
			padding-left: 30px;
		}
	}
}
</style>