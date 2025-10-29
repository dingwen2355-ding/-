const getDate = dates => {
  var dd = new Date();
  var n = dates || 0;
  dd.setDate(dd.getDate() + n);
  var y = dd.getFullYear();
  var m = dd.getMonth() + 1;
  var d = dd.getDate();
  m = m < 10 ? "0" + m : m;
  d = d < 10 ? "0" + d : d;
  var day = y + "-" + m + "-" + d;
  return day;
};

// getDate(dates)  //dates为数字类型，0代表今日,-1代表昨日，1代表明日，返回yyyy-mm-dd格式字符串，dates不传默认代表今日

const getMonday = (type, dates) => {
  var now = new Date();
  var nowTime = now.getTime();
  var day = now.getDay();
  var longTime = 24 * 60 * 60 * 1000;
  var n = longTime * 7 * (dates || 0);
  if (type == "s") {
    var dd = nowTime - (day - 1) * longTime + n;
  }
  if (type == "e") {
    var dd = nowTime + (7 - day) * longTime + n;
  }
  dd = new Date(dd);
  var y = dd.getFullYear();
  var m = dd.getMonth() + 1;
  var d = dd.getDate();
  m = m < 10 ? "0" + m : m;
  d = d < 10 ? "0" + d : d;
  var day = y + "-" + m + "-" + d;
  return day;
};

// getMonday(type, dates); //type为字符串类型，有两种选择，"s"代表开始,"e"代表结束，dates为数字类型，不传或0代表本周，-1代表上周，1代表下周
// getMonday("s", 1); //得到下周一的yyyy-mm-dd格式日期
// getMonday("e", 1); //得到下周日的yyyy-mm-dd格式日期

const getMonth = (type, months) => {
  var d = new Date();
  var year = d.getFullYear();
  var month = d.getMonth() + 1;
  if (Math.abs(months) > 12) {
    months = months % 12;
  }
  if (months != 0) {
    if (month + months > 12) {
      year++;
      month = (month + months) % 12;
    } else if (month + months < 1) {
      year--;
      month = 12 + month + months;
    } else {
      month = month + months;
    }
  }
  month = month < 10 ? "0" + month : month;
  var date = d.getDate();
  var firstday = year + "-" + month + "-" + "01";
  var lastday = "";
  if (
    month == "01" ||
    month == "03" ||
    month == "05" ||
    month == "07" ||
    month == "08" ||
    month == "10" ||
    month == "12"
  ) {
    lastday = year + "-" + month + "-" + 31;
  } else if (month == "02") {
    if (
      (year % 4 == 0 && year % 100 != 0) ||
      (year % 100 == 0 && year % 400 == 0)
    ) {
      lastday = year + "-" + month + "-" + 29;
    } else {
      lastday = year + "-" + month + "-" + 28;
    }
  } else {
    lastday = year + "-" + month + "-" + 30;
  }
  var day = "";
  if (type == "s") {
    day = firstday;
  } else {
    day = lastday;
  }
  return day;
};

// getMonth(type, months); //type为字符串类型，有两种选择，"s"代表开始,"e"代表结束，months为数字类型，不传或0代表本月，-1代表上月，1代表下月
// getMonth("s", 1); //得到下月第一天的yyyy-mm-dd格式日期
// getMonth("e", 1); //得到下月最后一天的yyyy-mm-dd格式日期

const getYear = (type, dates) => {
  var dd = new Date();
  var n = dates || 0;
  var year = dd.getFullYear() + Number(n);
  if (type == "s") {
    var day = year + "-01-01";
  }
  if (type == "e") {
    var day = year + "-12-31";
  }
  if (!type) {
    var day = year + "-01-01/" + year + "-12-31";
  }
  return day;
};

// getYear(type, dates); //type为字符串类型，有两种选择，"s"代表开始,"e"代表结束，dates为数字类型，不传或0代表今年，-1代表去年，1代表明年
// getYear("s", 1); //得到明年第一天的yyyy-mm-dd格式日期
// getYear("e", 1); //得到明年最后一天的yyyy-mm-dd格式日期◊
export { getDate, getMonday, getMonth, getYear };
