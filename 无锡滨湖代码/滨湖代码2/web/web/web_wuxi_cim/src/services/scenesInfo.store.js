'use strict'
const scenesInfoStore = {}
// {deving: 暂时未启用}
/* @usage : store state data shcema
						this.$store.state.login.account  */
scenesInfoStore.state = {
	treeData: {},
}

scenesInfoStore.getters = {
	treeData: state => state.treeData,
}

scenesInfoStore.mutations = {
	'SET_TREEDATA'(state, obj) {
		state.treeData = obj
	},
}

scenesInfoStore.actions = {
	'SET_TREEDATA_ACTION'({ commit }, obj) {
		return new Promise((resolve, reject) => {
			commit('SET_TREEDATA', obj)
			resolve()
		})
	},
}

export default scenesInfoStore