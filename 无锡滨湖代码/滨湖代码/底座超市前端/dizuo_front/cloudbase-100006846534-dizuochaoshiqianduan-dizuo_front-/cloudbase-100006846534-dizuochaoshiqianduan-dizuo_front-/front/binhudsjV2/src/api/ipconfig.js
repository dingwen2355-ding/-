// 开发环境，因为要走代理，所以都是/开头。对应vue.config.js 除非你要加个新服务，否则这里不用改。
const devIP = {
  server_statistics: "/statistics",
  server_filePreview: "/filePreview",
  server_fileServe: "/fileServe",
  server_system: "/system",
  server_ocn: "https://2.21.138.89:11511/",

};
// 除了开发环境都走这里，读.env文件,这边的地址就对应各自env文件填写的地址
const otherIP = {
  server_statistics: process.env.VUE_APP_BASE_URL_STATISTICS,
  server_filePreview: process.env.VUE_APP_BASE_URL_FILEPREVIEW,
  server_fileServe: process.env.VUE_APP_BASE_URL_FILESERVE,
  server_ocn: "https://2.21.138.89:11511/",
  server_system: process.env.VUE_APP_BASE_URL_SYSTEM
};
let IP = {};
if (process.env.NODE_ENV === "dev") {
  IP = devIP;
} else {
  IP = otherIP;
}
export default IP;
