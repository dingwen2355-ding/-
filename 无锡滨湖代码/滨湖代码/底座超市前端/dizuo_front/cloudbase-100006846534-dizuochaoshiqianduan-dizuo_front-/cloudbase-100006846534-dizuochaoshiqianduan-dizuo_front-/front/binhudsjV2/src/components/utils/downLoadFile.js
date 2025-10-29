/*
 * @Descripttion:下载文件
 * @version:
 * @Author: GM
 * @Date: 2021-05-21 14:11:09
 * @LastEditors: GM
 * @LastEditTime: 2021-09-13 18:05:54
 */
import message from "ant-design-vue/lib/message";
import axios from "axios";
export const dldFile = function(a, cd) {
  axios
    .get(a, {
      responseType: "blob"
    })
    .then(res => {
      if (!res) {
        message.error("下载模板文件失败");
        cd && cd();
        return false;
      } else {
        const blob = new Blob([res.data], {
          type:
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        const downloadElement = document.createElement("a");
        const href = window.URL.createObjectURL(blob);
        let contentDisposition = res.headers["content-disposition"];
        let patt = new RegExp("filename=([^;]+\\.[^\\.;]+);*");
        let result = patt.exec(contentDisposition);
        let filename = decodeURI(result[1]);
        downloadElement.style.display = "none";
        downloadElement.href = href;
        downloadElement.download = filename; //下载后文件名
        document.body.appendChild(downloadElement);
        downloadElement.click(); //点击下载+
        document.body.removeChild(downloadElement); //下载完成移除元素
        window.URL.revokeObjectURL(href); //释放掉blob对象
        cd && cd();
      }
    });
};
