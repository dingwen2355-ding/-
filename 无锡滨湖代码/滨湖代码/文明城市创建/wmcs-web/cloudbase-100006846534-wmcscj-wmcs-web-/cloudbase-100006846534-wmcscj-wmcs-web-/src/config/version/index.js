import { ver1_1x } from './1.1.0.x.js'

export const version = {
    name: '版本描述说明',
    // 当前版本
    // version: '0.7.1',
    // 当前已经实装的模块
    module: [],
    // 历史版本描述
    historyDescription: [
        ver1_1x
    ]
}

export function getVersion() {
    let nowVer = version.historyDescription.slice(-1)[0]
    let nowVersion = nowVer.slice(-1)[0].version
    return nowVersion
}
