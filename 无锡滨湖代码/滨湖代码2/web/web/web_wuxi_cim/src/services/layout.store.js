'use strict'
const layoutStore = {}

/* @usage : store state data shcema
						this.$store.state.layout.header.active  */
layoutStore.state = {
	sysMenuData: localStorage.getItem('CIM_sysMenuData') ? JSON.parse(localStorage.getItem('CIM_sysMenuData')) : { nav: [], flat: [] },
	// sysMenuData: { nav: [], flat: [] },
	menuActiveData: { headerTarget: null, target: null, leftNav: [], leftActive: "" },

	// APP: 服务映射
	serviceMapping: sessionStorage.getItem('CIM_SERVICEMAPPING') ? JSON.parse(sessionStorage.getItem('CIM_SERVICEMAPPING')) : {},
	// main.layout: 控制台index
	index: sessionStorage.getItem('CIM_LAYOUT_INDEX') ? JSON.parse(sessionStorage.getItem('CIM_LAYOUT_INDEX')) : {
		login: null, // menuList.js flag为"login"的index集合
		'key-value': {} // menuList.js key：children中的index value：所在的一级菜单index
	},
	// header.layout
	header: sessionStorage.getItem('CIM_LAYOUT_HEADER') ? JSON.parse(sessionStorage.getItem('CIM_LAYOUT_HEADER')) : {
		active: '',
		actMNU: {} // 激活的菜单数据(模型管理cim)
	},
	// left.layout
	left: sessionStorage.getItem('CIM_LAYOUT_LEFT') ? JSON.parse(sessionStorage.getItem('CIM_LAYOUT_LEFT')) : {
		active: '', // 激活菜单index
		menu: [] // 左侧菜单
	}
}

layoutStore.mutations = {
	'SET_sysMenuData'(state, obj) {
		state.sysMenuData = obj || { nav: [], flat: [] }
		localStorage.setItem('CIM_sysMenuData', JSON.stringify(obj))
	},
	'SET_menuActiveData'(state, obj) {
		// console.log('SET_menuActiveData obj', obj);
		state.menuActiveData = { ...state.menuActiveData, ...(obj || {}) }
	},
	'SET_MAPPING'(state, obj) {
		let keys = Object.keys(obj)
		keys.forEach((key) => {
			state.serviceMapping[key] = obj[key]
		})
		sessionStorage.setItem('CIM_SERVICEMAPPING', JSON.stringify(state.serviceMapping))
	},
	'SET_INDEX'(state, obj) {
		let keys = Object.keys(obj)
		keys.forEach((key) => {
			state.index[key] = obj[key]
		})
		sessionStorage.setItem('CIM_LAYOUT_INDEX', JSON.stringify(state.index))
	},
	'SET_HEADER'(state, obj) {
		let keys = Object.keys(obj)
		keys.forEach((key) => {
			state.header[key] = obj[key]
		})
		sessionStorage.setItem('CIM_LAYOUT_HEADER', JSON.stringify(state.header))
	},
	'SET_LEFT'(state, obj) {
		let keys = Object.keys(obj)
		keys.forEach((key) => {
			state.left[key] = obj[key]
		})
		sessionStorage.setItem('CIM_LAYOUT_LEFT', JSON.stringify(state.left))
	},
}

/* this.$store.getters.header_active */
layoutStore.getters = {
	sysMenuData: state => state.sysMenuData,
	menuActiveData: state => state.menuActiveData,
	serviceMapping: state => state.serviceMapping,
	layoutCNX: state => state.index,
	header_active: state => state.header.active,
	header_actMNU: state => state.header.actMNU,
	left_active: state => state.left.active,
	left_menu: state => state.left.menu
}

layoutStore.actions = {
	'SET_sysMenuData_ACTION'({ commit }, obj) {
		return new Promise((resolve, reject) => {
			commit('SET_sysMenuData', obj)
			resolve()
		})
	},
	'SET_menuActiveData__ACTION'({ commit }, obj) {
		return new Promise((resolve, reject) => {
			commit('SET_menuActiveData', obj)
			resolve()
		})
	},
	'SET_MAPPING_ACTION'({ commit }, obj) {
		return new Promise((resolve, reject) => {
			commit('SET_MAPPING', obj)
			resolve()
		})
	},
	'SET_INDEX_ACTION'({ commit }, obj) {
		return new Promise((resolve, reject) => {
			commit('SET_INDEX', obj)
			resolve()
		})
	},
	'SET_HEADER_ACTION'({ commit }, obj) {
		return new Promise((resolve, reject) => {
			commit('SET_HEADER', obj)
			resolve()
		})
	},
	'SET_LEFT_ACTION'({ commit }, obj) {
		return new Promise((resolve, reject) => {
			commit('SET_LEFT', obj)
			resolve()
		})
	}
}

export default layoutStore