<template>
	<cloud-tmpl title='投影面积' :scrollHeight='12' @close='handleClose'>
		<template v-slot:content>
			<ul class="content-warp">
				<li>
					<span>面积</span>
					<span>{{ area }} ㎡</span>
				</li>
				<li>
					<span>周长</span>
					<span>{{ perimeter }} m</span>
				</li>
			</ul>
		</template>
	</cloud-tmpl>
</template>

<script>
export default {
	name: "MultiPointArea",
	data() {
		return {
			area: 0,
			perimeter: 0,
		};
	},
	watch: {
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.Type == "Area") {
					this.area = val.Area
					this.perimeter = val.Perimeter
				}
			},
		},
	},
	mounted() {
		this.setMeasurement();
	},
	methods: {
		async setMeasurement() {
			await window.sealAPI._tools.setMeasurement(MeasurementMode.MultiPoint, null);
			window.sealAPI._tools.startMeasurement();
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