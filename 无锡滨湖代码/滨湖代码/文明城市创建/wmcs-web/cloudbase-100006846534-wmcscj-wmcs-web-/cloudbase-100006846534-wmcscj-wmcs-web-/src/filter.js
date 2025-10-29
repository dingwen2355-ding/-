let moment = require('moment')
moment.locale('zh-cn', { week: { dow: 1, doy: 4 }})

let arrJoinPoint = (arr) => {
    if (!arr) return ''
    return arr.join('、')
}

// 若无数据显示-
let noShowBType = (val) => {
    return val || '-'
}
// 与上面一致, 命名与其他项目一致,方便写
let showNone = (val) => {
    return val || '-'
}

// 若无数据显示'未知'
let noShowWType = (val) => {
    return val || '未知'
}

// 若无数据显示'暂无'
let noShowWzw = (val) => {
    return val || '暂无'
}

// 金额转换成万
let num2wanWithUnit = (val) => {
    if (!val) return '0'
    // 时间格式-,不能用moment 因为小数符合时间戳格式
    if (('' + val).indexOf('-') > -1) return val
    let absVal = Math.abs(val)
    if (absVal < 10000) {
        if (String(val).indexOf('.') == -1) return val * 1
        return (val * 1).toFixed(2) // + '元'
    } else if (absVal >= 10000 && absVal < 100000000) {
        return ((~~(val / 100)) / 100).toFixed(2) + '万'
    } else {
        return ((~~(val / 1000000)) / 100).toFixed(2) + '亿'
    }
}

// 小数转百分比
let num2rat = (val) => {
    if (!val) return '-'
    if (('' + val).indexOf('-') > -1) return val
    return ((val - 0) * 100).toFixed(2) + '%';
}

// 小数转百分比(不加%)
let numrat = (val) => {
    return (val * 100).toFixed(2);
}

// 绝对值
let valAbs = (val) => {
    return Math.abs(val)
}

// 纯粹在数字后加%
let num2ratNoChange = (val) => {
    return String(val) + '%'
}

// 文字截取
let textSlice = (v) => {
    return v.substr(0, 4)
}

// 秒转倒计时
let second2countdown = (val) => {
    if (!val) return '-'
    // 如果不是数字,则原样返回
    if (isNaN(parseInt(val))) {
        return val
    }
    // 先判断正负
    let returnstr = parseInt(val) >= 0 ? '' : '-'
    let num = Math.abs(val)
    let arr = [
        { num: 1 * 60 * 60 * 24, unit: '天' },
        { num: 1 * 60 * 60, unit: '小时' },
        { num: 1 * 60, unit: '分' }
    ]
    arr.forEach(item => {
        let one = item.num
        let oneNum = ~~(num / one)
        if (oneNum > 0) {
            returnstr += (oneNum + item.unit)
            num -= (oneNum * one)
        }
    })
    return returnstr
}

// 时间戳转时间(秒)
let timestrip2formatsec = (val) => {
    if (!val) return '-'
    // 如果不是数字,则原样返回
    if (isNaN(parseInt(val))) {
        return val
    }
    return moment(val * 1000).format('YYYY-MM-DD HH:mm:ss')
}

// 时间戳转时间(填)
let timestrip2formatday = (val) => {
    if (!val) return '-'
    // 如果不是数字,则原样返回
    if (isNaN(parseInt(val))) {
        return val
    }
    return moment(val * 1000).format('YYYY-MM-DD')
}

// 数仓的日期格式转: 09-25(星期五)
let dayWeek = (val) => {
    let s1 = moment(val).format('MM-DD')
    let s2 = moment(val).day()
    s2 == 1 && (s2 = '一')
    s2 == 2 && (s2 = '二')
    s2 == 3 && (s2 = '三')
    s2 == 4 && (s2 = '四')
    s2 == 5 && (s2 = '五')
    s2 == 6 && (s2 = '六')
    s2 == 0 && (s2 = '日')
    return s1 + ` (星期${s2})`
}

// 数仓的日期格式转: 3月34日
let scdayDay = (val) => {
    let s1 = moment(val).format('MM月DD日')
    return s1
}

// ----------------------------------------------业务转换

export {
    // 工具性质类转换
    arrJoinPoint,
    noShowBType,
    showNone,
    noShowWType,
    noShowWzw,
    num2wanWithUnit,
    num2rat,
    numrat,
    valAbs,
    num2ratNoChange,
    textSlice,
    second2countdown,
    timestrip2formatsec,
    timestrip2formatday,
    dayWeek,
    scdayDay
    // 业务性质类转换
}
