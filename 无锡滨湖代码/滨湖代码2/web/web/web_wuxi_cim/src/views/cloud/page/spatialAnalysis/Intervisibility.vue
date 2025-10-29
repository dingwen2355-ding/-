<template></template>

<script>
export default {
	name: "Intervisibility",
	data() {
		return {
			option: {
				height: 1.8, //视点高度（距离场景交互所拾取点的高度）
				visibleColor: Color.Green, //可见区域的颜色，默认值：绿色
				invisibleColor: Color.Red, //不可见区域的颜色，默认值：红色
			},
		};
	},
	watch: {
		"$store.getters.eventSealAPI": {
			indeterminate: true,
			deep: true,
			handler(val) {
				if (val.eventtype == "VisibilityAnalysis") {
					console.log('VisibilityAnalysis.val', val);
				}
			}
		}
	},
	created() {
		window.sealAPI._tools.startVisiblityAnalysis(this.option);
	},
	mounted() {
		this.$message.success("进入通视分析！")
	},
	beforeDestroy() {
		window.sealAPI._tools.stopVisiblityAnalysis();
	},
	methods: {}
};
</script>

<style lang="scss" scoped></style>