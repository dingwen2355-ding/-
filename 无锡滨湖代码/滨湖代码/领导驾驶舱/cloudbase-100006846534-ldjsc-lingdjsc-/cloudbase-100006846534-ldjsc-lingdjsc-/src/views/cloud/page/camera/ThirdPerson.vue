<template>
	<div class="cloud-func">
		<div @click="handleClose">
			<i class="el-icon-close"></i>
			<p>取消</p>
		</div>
	</div>
</template>

<script>
export default {
	name: "ThirdPerson",
	data() {
		return {};
	},
	mounted() {
		window.sealAPI._settings.setInteractiveMode(1);
	},
	methods: {
		handleClose() {
			if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
		},
	},
	beforeDestroy() {
		window.sealAPI._settings.setInteractiveMode(0);
		this.handleClose();
	},
};
</script>

<style lang="scss" scoped>
@import "~@/views/cloud/page/css/func3.scss";

.cloud-func {
	width: 88px;

	div {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding-bottom: 6px;
		margin: 8px 10px 8px 10px;

		&:hover {
			background: rgba(216, 216, 216, 0.2);
			border-radius: 8px;
		}

		i {
			cursor: pointer;
			font-size: 40px;
			color: #ffffff;

		}

		p {
			font-size: 14px;
			font-weight: 400;
			color: #ffffff;
		}
	}
}
</style>