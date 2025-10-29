// 刷新token
import { t_http } from '../network/request'
import { api } from '../config'
import Cookies from 'js-cookie'

// 刷新Token
export function refushToken() {
    console.log('刷新Token--')
    let refreshToken = Cookies.get('refreshToken')
    return t_http({
        method: 'get',
        url: api + `/ems/v1/refreshToken`,
        params: { refreshToken }
    })
}
