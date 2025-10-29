/* 服务器时间格式转换 */
export const serverTimeFormatting = (date) => {
  // return new Date(date).toLocaleString().replaceAll("/", "-");

  let dateJSON = new Date(date).toJSON();
  let parseDate = new Date(+new Date(dateJSON) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
  return parseDate;
}
