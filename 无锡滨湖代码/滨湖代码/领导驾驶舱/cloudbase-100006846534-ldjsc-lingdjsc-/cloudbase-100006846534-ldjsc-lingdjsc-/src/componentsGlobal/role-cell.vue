<template>
	<!-- 计划中 -->
	<span class="role-cell" v-if="isShow">
		<slot></slot>
	</span>
</template>

<script>
import { mapGetters } from "vuex";

export default {
	name: "RoleCell",
	props: ['code'],
	computed: {
		...mapGetters(["menuActiveData"]),
		isShow() {
			let { target } = this.menuActiveData
			if (!target) return false
			let btnCodes = (target.childrenList || []).filter(row => row.menuType == 3).map(row => row.code)
			console.log('isShow this.code btnCodes', target.name, this.code, btnCodes, btnCodes.includes(this.code));
			return btnCodes.includes(this.code)
		}
	},

}
</script>

<style lang="scss" scoped>
.role-cell {
	display: inline-block;
}
</style>