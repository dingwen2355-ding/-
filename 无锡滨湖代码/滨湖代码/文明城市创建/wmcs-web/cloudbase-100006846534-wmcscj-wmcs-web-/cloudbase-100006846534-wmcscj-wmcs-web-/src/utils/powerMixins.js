import { me } from '@/api/user'
// 验证权限
const powerMixins = {
    computed: {
        // 验证当前按钮码是否具有权限: 默认就是false
        pow() {
            let codeList = this.$store.state.accessCodeList
            return (code) => {
                // 未定code名称时保留代码的默认显示code
                if (code === 'zr-unknow') return true
                if (codeList.length == 0) return false
                return (codeList.indexOf(code) > -1)
            }
        }
    },
    methods: {
        getMyUserData() {
            // 获取个人登录信息：包含班组：user.employee.teamIds
            let _this = this
            return new Promise((res, rej) => {
                if (_this.$store.state.userInfo && _this.$store.state.userInfo.data) {
                    res(_this.$store.state.userInfo.data)
                } else {
                    me().then((data) => {
                        res(data.data.loginInfo)
                    })
                }
            })
        },
        getMyUserDataOnlyWait() {
            // 获取个人登录信息：和上面的区别是, 后端接口不支持短时间获取两次, 这个接口会等待store的值
            let _this = this
            return new Promise((res, rej) => {
                if (_this.$store.state.userInfo && _this.$store.state.userInfo.data) {
                    res(_this.$store.state.userInfo.data)
                } else {
                    setTimeout(() => {
                        _this.getMyUserDataOnlyWait().then((data) => {
                            res(data)
                        })
                    }, 200)
                }
            })
        }
    }
}
export default powerMixins
