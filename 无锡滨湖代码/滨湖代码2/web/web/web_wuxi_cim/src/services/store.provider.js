import Vue from "vue";
import Vuex from "vuex";
// import createPersistedState from 'vuex-persistedstate'
import LoginStore from './login.store.js'
import LayoutStore from './layout.store.js'
import AirCityStore from './airCity.store.js'
import ScenesInfoStore from './scenesInfo.store.js'

Vue.use(Vuex)

export default new Vuex.Store({
	/* plugins: [createPersistedState(
		// 存到session
		{ storage: window.sessionStorage }
	)], */
	modules: {
		login: LoginStore,
		layout: LayoutStore,
		airCity: AirCityStore,
		scenesInfo: ScenesInfoStore,
	}
})