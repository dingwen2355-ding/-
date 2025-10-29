/**
 * Created by whl on 2018/5/25.
 */
let now = new Date()
let nowDay = now.getDate() // 当前日
let nowMonth = now.getMonth() + 1 // 当前月
let fullMonth = nowMonth < 10 ? '0' + nowMonth : nowMonth
let nowYear = now.getYear() // 当前年
let nowDayOfWeek = now.getDay() // 获取当前星期X(0-6,0代表星期天)

// 过去日期
nowYear += (nowYear < 2000) ? 1900 : 0 //
let nowFullYear = now.getFullYear() // 当前全年
let lastMonthDate = new Date()
lastMonthDate.setDate(1)
lastMonthDate.setMonth(lastMonthDate.getMonth() - 1)
// let lastYear = lastMonthDate.getYear()
let lastMonth = lastMonthDate.getMonth()
let setFormat = function (x) {
  if (x < 10) {
    x = '0' + x
  }
  return x
}

let znvTime = {
  /**
   * 获取上个月的值
   * */
  getMonth: function () {
    return nowMonth
  },

  // 获取近一个月时间
  getPreMonthAndYearAndDay: function () {
    if (nowMonth === 1) {
      return [
        (nowFullYear - 1) + '-12-' + (nowDay < 10 ? '0' + nowDay : nowDay),
        nowFullYear + '-' + (nowMonth < 10 ? '0' + nowMonth : nowMonth) + '-' + (nowDay < 10 ? '0' + nowDay : nowDay)
      ]
    } else {
      return [
        nowFullYear + '-' + ((nowMonth - 1) < 10 ? '0' + (nowMonth - 1) : (nowMonth - 1)) + '-' + (nowDay < 10 ? '0' + nowDay : nowDay),
        nowFullYear + '-' + (nowMonth < 10 ? '0' + nowMonth : nowMonth) + '-' + (nowDay < 10 ? '0' + nowDay : nowDay)
      ]
    }
  },

  /**
   * 获取上个月和当前月往前推11个月份
   * return {currentMonth:201807,arr:[{value:201808},{value:201807}]}
   * */
  getPreMonthAndYear: function () {
    if (nowMonth === 1) {
      return { month: 12, year: nowYear - 1 }
    } else {
      return { month: (nowMonth - 1) < 10 ? '0' + (nowMonth - 1) : (nowMonth - 1), year: nowYear }
    }
  },
  getYearMonth: function () {
    let oo = {}
    let fullMonth = nowMonth < 10 ? '0' + nowMonth : nowMonth
    oo.currentMonth = nowFullYear + '-' + fullMonth
    let arr = []
    for (let i = -1; i <= 10; i++) {
      let temp = nowMonth - i
      let temp2 = nowFullYear
      if (temp <= 0) {
        temp = 12 + temp
        temp2 = temp2 - 1
      }
      let mon = {}
      mon.value = temp2 + '-' + (temp < 10 ? ('0' + temp) : '' + temp)
      arr.push(mon)
    }
    oo.fullMonth = arr
    return oo
  },
  getTimeByYearMonth: function (yearMonth) {
    let res = []
    let sTime = ''
    let eTime = ''
    let arr = yearMonth.split('-')
    if (arr.length === 2) {
      if (arr[1] === '1') {
        sTime = arr[0] + '-01-01 00:00:00'
        eTime = arr[0] + '-01-31 23:59:59'
      } else if (arr[1] === '12') {
        sTime = arr[0] + '-12-01 00:00:00'
        eTime = arr[0] + '-12-31 23:59:59'
      } else {
        let nextMonth = Number(arr[1]) + 1
        let ss = nextMonth < 10 ? '0' + nextMonth : '' + nextMonth
        sTime = yearMonth + '-01 00:00:00'
        eTime = arr[0] + '-' + ss + '-01 00:00:00'
      }
    }
    res.push(sTime)
    res.push(eTime)
    return res
  },
  // 获得本周的开始日期
  getCurrentWeekFirstDay: function () {
    let WeekFirstDay = new Date(now - (nowDayOfWeek - 1) * 86400000)
    let month = Number(WeekFirstDay.getMonth()) + 1
    if (month >= 1 && month <= 9) {
      month = '0' + month
    }
    return WeekFirstDay.getFullYear() + '-' + month + '-' + WeekFirstDay.getDate()
  },
  // 获得本月的开始日期
  getCurrentMonthFirstDay: function () {
    return nowFullYear + '-' + fullMonth + '-01'
  },
  // 获得本季度的开始月份
  getQuarterStartMonth: function () {
    let quarterStartMonth = 0
    if (nowMonth < 12) {
      quarterStartMonth = 9
    }
    if (nowMonth < 9) {
      quarterStartMonth = 6
    }
    if (nowMonth < 6) {
      quarterStartMonth = 3
    }
    if (nowMonth < 3) {
      quarterStartMonth = 0
    }
    return quarterStartMonth
  },
  // 获得本季度的开始日期
  getCurrentQuarterFirstDay: function () {
    let quarterStartDate = new Date(nowFullYear, this.getQuarterStartMonth(), 1)
    return this.formatDate(quarterStartDate)
  },
  // 获得上周的开始日期
  getLastWeekStartDate: function () {
    let weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 7)
    return this.formatDate(weekStartDate)
  },
  // 获得上周的结束日期
  getLastWeekEndDate: function () {
    let weekEndDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek - 1)
    return this.formatDate(weekEndDate)
  },
  // 获得上月开始时间
  getLastMonthStartDate: function () {
    let lastMonthStartDate = new Date(nowYear, lastMonth, 1)
    return this.formatDate(lastMonthStartDate)
  },
  // 获得上月结束时间
  getLastMonthEndDate: function () {
    let lastMonthEndDate = new Date(nowYear, lastMonth, this.getMonthDays(lastMonth))
    return this.formatDate(lastMonthEndDate)
  },
  // 格式化日期：yyyy-MM-dd
  formatDate: function (date) {
    let myyear = date.getFullYear()
    let mymonth = date.getMonth() + 1
    let myweekday = date.getDate()
    if (mymonth < 10) {
      mymonth = '0' + mymonth
    }
    if (myweekday < 10) {
      myweekday = '0' + myweekday
    }
    return (myyear + '-' + mymonth + '-' + myweekday)
  },
  // 获得某月的天数
  getMonthDays: function (myMonth) {
    let monthStartDate = new Date(nowYear, myMonth, 1)
    let monthEndDate = new Date(nowYear, myMonth + 1, 1)
    let days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24)
    return days
  },
  // ---------------------------------------------------
  // 日期格式化
  // 格式 YYYY/yyyy/YY/yy 表示年份
  // MM/M 月份
  // W/w 星期
  // dd/DD/d/D 日期
  // hh/HH/h/H 小时
  // mm/m 分钟
  // ss/SS/s/S 秒
  // ---------------------------------------------------
  getCurrentTimeByFormat: function (formatStr) {
    let now = new Date()
    let nowDay = now.getDate() // 当前日
    let nowMonth = now.getMonth() // 当前月
    let nowYear = now.getYear() // 当前年
    let nowFullYear = now.getFullYear() // 当前全年
    let str = formatStr
    let Week = ['日', '一', '二', '三', '四', '五', '六']

    str = str.replace(/yyyy|YYYY/, nowFullYear)
    str = str.replace(/yy|YY/, (nowYear % 100) > 9 ? (nowYear % 100).toString() : '0' + (nowYear % 100))

    str = str.replace(/MM/, (nowMonth + 1) > 9 ? (nowMonth + 1).toString() : '0' + (nowMonth + 1))
    str = str.replace(/M/g, nowMonth + 1)

    str = str.replace(/w|W/g, '星期' + Week[nowDayOfWeek])
    str = str.replace(/z|Z/g, '周' + Week[nowDayOfWeek])

    str = str.replace(/dd|DD/, nowDay > 9 ? nowDay.toString() : '0' + nowDay)
    str = str.replace(/d|D/g, nowDay)

    str = str.replace(/hh|HH/, now.getHours() > 9 ? now.getHours().toString() : '0' + now.getHours())
    str = str.replace(/h|H/g, now.getHours())
    str = str.replace(/mm/, now.getMinutes() > 9 ? now.getMinutes().toString() : '0' + now.getMinutes())
    str = str.replace(/m/g, now.getMinutes())

    str = str.replace(/ss|SS/, now.getSeconds() > 9 ? now.getSeconds().toString() : '0' + now.getSeconds())
    str = str.replace(/s|S/g, now.getSeconds())

    return str
  },
  getCurrentDay: function () {
    let res = []
    let sTime = this.getCurrentTimeByFormat('YYYY-MM-DD')
    sTime = sTime + ' 00:00:00'
    let eTime = this.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getCurrentWeek: function () {
    let res = []
    let sTime = this.getCurrentWeekFirstDay() + ' 00:00:00'
    let eTime = this.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getCurrentMonth: function () {
    let res = []
    let sTime = this.getCurrentMonthFirstDay() + ' 00:00:00'
    let eTime = this.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getCurrentQuarter: function () {
    let res = []
    let sTime = this.getCurrentQuarterFirstDay() + ' 00:00:00'
    let eTime = this.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getCurrentYear: function () {
    let res = []
    let sTime = nowFullYear + '-01-01 00:00:00'
    let eTime = this.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getPreDay: function () {
    let day1 = new Date()
    day1.setTime(day1.getDate() - 1)
    let res = []
    let sTime = this.formatDate(day1) + ' 00:00:00'
    let eTime = this.formatDate(day1) + ' 23:59:59'
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getPreWeek: function () {
    let res = []
    let sTime = this.getLastWeekStartDate() + ' 00:00:00'
    let eTime = this.getLastWeekEndDate() + ' 23:59:59'
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getPreMonth: function () {
    let res = []
    let sTime = this.getLastMonthStartDate() + ' 00:00:00'
    let eTime = this.getLastMonthEndDate() + ' 23:59:59'
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getPreQuarter: function () {
    let res = []
    let temp = this.getQuarterStartMonth() + 1
    let sTime = ''
    let eTime = ''
    if (temp === 1) {
      sTime = (nowFullYear - 1) + '-10-01 00:00:00'
      eTime = (nowFullYear - 1) + '-12-31 23:59:59'
    } else if (temp === 4) {
      sTime = nowFullYear + '-01-01 00:00:00'
      eTime = nowFullYear + '-03-31 23:59:59'
    } else if (temp === 7) {
      sTime = nowFullYear + '-04-01 00:00:00'
      eTime = nowFullYear + '-06-30 23:59:59'
    } else if (temp === 10) {
      sTime = nowFullYear + '-07-01 00:00:00'
      eTime = nowFullYear + '-09-30 23:59:59'
    }
    res.push(sTime)
    res.push(eTime)
    return res
  },
  getPreYear: function () {
    let res = []
    let sTime = nowFullYear - 1 + '-01-01 00:00:00'
    let eTime = nowFullYear - 1 + '-12-31 00:00:00'
    res.push(sTime)
    res.push(eTime)
    return res
  },
  formatByTime: function (value) {
    let secondTime = parseInt(value) // 秒
    let minuteTime = 0 // 分
    let hourTime = 0 // 小时
    let dayTime = 0 // 天
    if (secondTime > 60) { // 如果秒数大于60，将秒数转换成整数
      // 获取分钟，除以60取整数，得到整数分钟
      minuteTime = parseInt(secondTime / 60)
      // 获取秒数，秒数取佘，得到整数秒数
      secondTime = parseInt(secondTime % 60)
      // 如果分钟大于60，将分钟转换成小时
      if (minuteTime > 60) {
        // 获取小时，获取分钟除以60，得到整数小时
        hourTime = parseInt(minuteTime / 60)
        // 获取小时后取佘的分，获取分钟除以60取佘的分
        minuteTime = parseInt(minuteTime % 60)
        if (hourTime > 24) {
          // 获取天数，获取小时除以24，得到整数天
          dayTime = parseInt(hourTime / 24)
          // 获取天后取佘的小时，获取小时除以24取佘的小时
          hourTime = parseInt(hourTime % 24)
        }
      }
    }
    let result = ''
    if (dayTime <= 0) {
      result = '' + parseInt(secondTime) + '秒'
    }

    if (minuteTime > 0) {
      result = '' + parseInt(minuteTime) + '分' + result
    }
    if (hourTime > 0) {
      result = '' + parseInt(hourTime) + '时' + result
    }
    if (dayTime > 0) {
      result = dayTime + '天' + result
    }
    return result
  },
  formatBySecond: function (second) {
    let secondTime = parseInt(second) // 秒
    let minuteTime = 0 // 分
    let hourTime = 0 // 小时
    if (secondTime >= 60) { // 如果秒数大于60，将秒数转换成整数
      // 获取分钟，除以60取整数，得到整数分钟
      minuteTime = parseInt(secondTime / 60)
      // 获取秒数，秒数取佘，得到整数秒数
      secondTime = parseInt(secondTime % 60)
      // 如果分钟大于60，将分钟转换成小时
      if (minuteTime >= 60) {
        // 获取小时，获取分钟除以60，得到整数小时
        hourTime = parseInt(minuteTime / 60)
        // 获取小时后取佘的分，获取分钟除以60取佘的分
        minuteTime = parseInt(minuteTime % 60)
      }
    }
    let result = ''
    if (hourTime > 0) {
      result = setFormat(hourTime) + ':' + setFormat(minuteTime) + ':' + setFormat(secondTime)
    } else {
      result = setFormat(minuteTime) + ':' + setFormat(secondTime)
    }
    return result
  }
}
export default znvTime
