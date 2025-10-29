// 搜索条件缓存策略
import Cookies from 'js-cookie'

export const searchFilterStoreMixins = {
    methods: {
        InverSearchFilter() {
            return
        },
        getBasicSelect(meth, paras) {
            let token = Cookies.get('token')
            let searchFilt = {}
            meth({ type: paras }).then((data) => {
                let res = data.data.data
                Object.keys(res).forEach((item) => {
                    data = []
                    data = res[item].map((i) => {
                        return { label: i.name.trim(), value: i.id }
                    })
                    data.unshift({ value: 0, label: '全部' })
                    searchFilt = {}
                    searchFilt[item] = { data, token: token }
                    this.$store.commit('storeSearchFilter', searchFilt)
                })
            })
        }
    }
}
