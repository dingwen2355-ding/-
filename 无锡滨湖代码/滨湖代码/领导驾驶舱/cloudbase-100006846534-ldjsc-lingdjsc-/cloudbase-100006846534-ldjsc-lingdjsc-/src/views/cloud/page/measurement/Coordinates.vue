<template>
	<cloud-tmpl title='坐标' :scrollHeight='20' @close='handleClose'>
		<template v-slot:content>
			<ul class="content-warp">
				<li>
					<span>X</span> <span>{{ coordinate[0] }}</span>
				</li>
				<li>
					<span>Y</span> <span>{{ coordinate[1] }}</span>
				</li>
				<li>
					<span>Z</span> <span>{{ coordinate[2] }}</span>
				</li>
			</ul>
		</template>
	</cloud-tmpl>
</template>

<script>
export default {
	name: "Coordinates",
	data() {
		return {
			coordinate: [0, 0, 0],
		};
	},
	watch: {
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.Type == "Coordinate") {
					this.coordinate = this.coorRoundFix(val.Coordinate)
				}
			},
		},
	},
	mounted() {
		this.setMeasurement();
	},
	methods: {
		async setMeasurement() {
			await window.sealAPI._tools.setMeasurement(MeasurementMode.Coordinate, null);
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
</style>