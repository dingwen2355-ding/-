'use strict'
import cookieProvider from './cookie.provider.js'
const loginStore = {}

/* @usage : store state data shcema
            this.$store.state.login.account  */
loginStore.state = {
    account: {
        ...(cookieProvider.get('CIM_account_info') ? JSON.parse(cookieProvider.get('CIM_account_info')) : {
            'token': '',
            'uploadtoken': '',
            'jtToken': '',
            'userInfo': {}
        }),
        'btns': (localStorage.getItem('CIM_account_btns') ? JSON.parse(localStorage.getItem('CIM_account_btns')) : []),
        'path': (cookieProvider.get('CIM_account_path') ? JSON.parse(cookieProvider.get('CIM_account_path')) : [])
    }
}

loginStore.mutations = {
    'SET_ACCOUNT'(state, obj) {
        let keys = Object.keys(obj)
        keys.forEach((key) => {
            state.account[key] = obj[key]
            switch (key) {
                case 'btns':
                    localStorage.setItem('CIM_account_btns', JSON.stringify(state.account.btns), 7)
                    break;
                case 'path':
                    cookieProvider.set('CIM_account_path', JSON.stringify(state.account.path), 7)
                    break;
            }
        })

        // 7天免登录
        if (!!obj.token) {
            let { btns, path, ...info } = state.account;
            cookieProvider.set('CIM_account_info', JSON.stringify(info), 7)
        }
        // 清除cookie
        if (!state.account.token) {
            cookieProvider.delete('CIM_account_info')
            localStorage.removeItem('CIM_account_btns')
            cookieProvider.delete('CIM_account_path')
        }
    },
}

loginStore.getters = {
    token: state => state.account.token,
    uploadtoken: state => state.account.uploadtoken,
    freedoToken: state => state.account.freedoToken,
    jtToken: state => state.account.jtToken,
    userInfo: state => state.account.userInfo,
    btns: state => state.account.btns,
    path: state => state.account.path,
}

loginStore.actions = {
    'SET_ACCOUNT_ACTION'({ commit }, obj) {
        return new Promise((resolve, reject) => {
            commit('SET_ACCOUNT', obj)
            resolve()
        })
    }
}

export default loginStore