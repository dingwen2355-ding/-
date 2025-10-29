/*
 * @Descripttion: 导入导出任务模块接口
 * @version:
 * @Author: GM
 * @Date: 2021-05-13 11:41:18
 * @LastEditors: GM
 * @LastEditTime: 2021-07-23 11:23:05
 */
import IP from "../ipconfig";
const api = {
  importTask: {
    LIST: IP.server_data_center + "/dc/excel/task/list", //获取导入任务列表
    INFO: IP.server_data_center + "/dc/excel/task/getInfoById", //查看导入详情
    DELETE: IP.server_data_center + "/dc/excel/task/delete", //删除导入任务
    TEMPLATE: IP.server_data_center + "/dc/excel/task/template", //下载导入模板
    EXPORTERROR: IP.server_fileServe + "/sys/file/downloadFileByKey" //导出任务的错误行
  }
};
export default api;
