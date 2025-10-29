import IP from "../ipconfig";
const api = {
  GridPower: IP.server_statistics + "/statistics/grid/member/byType" //网格力量
};
export default api;
