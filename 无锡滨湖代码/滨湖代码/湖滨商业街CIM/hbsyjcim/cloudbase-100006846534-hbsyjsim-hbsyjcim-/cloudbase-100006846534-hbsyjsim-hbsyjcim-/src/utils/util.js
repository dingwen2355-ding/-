import request from "./request";
/**
 * maxNum:最大请求数
 * @param {string[]} urls 待请求的url数组
 * @param {number} maxNum 最大并发数
 */
export function concurRequest(urls, maxNum) {
  return new Promise((resolve) => {
    if (urls.length === 0) {
      resolve([]);
    }
    let index = 0; // 下一次请求对应的url地址下标
    let count = 0; // 请求完成的数量
    const result = [];
    async function innerRequest() {
      const i = index;
      const url = urls[index];
      index++;
      try {
        const resp = await request.post(url);
        result[i] = resp;
      } catch (error) {
        result[i] = error;
      } finally {
        count++;
        if (count === urls.length) {
          resolve(result);
        }
        if (index < urls.length) {
          innerRequest();
        }
      }
    }
    for (let i = 0; i < Math.min(urls.length, maxNum); i++) {
      innerRequest();
    }
  });
}

export function getCurrentTime() {
  var date = new Date();

  // 年月日
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();

  // 时分秒
  var hour = date.getHours();
  var minute = date.getMinutes();
  var second = date.getSeconds();

  // 实时显示

  month = month < 10 ? "0" + month : month;
  day = day < 10 ? "0" + day : day;
  hour = hour < 10 ? "0" + hour : hour;
  minute = minute < 10 ? "0" + minute : minute;
  second = second < 10 ? "0" + second : second;
  return `${year}年${month}月${day}日 ${hour}:${minute}:${second}`;
}
