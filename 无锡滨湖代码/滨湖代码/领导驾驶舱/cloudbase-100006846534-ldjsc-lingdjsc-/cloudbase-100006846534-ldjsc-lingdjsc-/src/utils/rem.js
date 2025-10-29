/**
 * rem等比适配配置文件
 */
class ResetW {
  BS; // 基准大小（默认16px）
  CW; // 网页可见区域宽

  constructor(baseSize, clientWidth = document.documentElement.clientWidth) {
    this.BS = baseSize;
    this.CW = clientWidth;
  }

  // 设置 rem 函数
  setRem() {
    // 当前页面宽度相对于 1920宽的缩放比例，可根据自己需要修改。
    this.CW = document.documentElement.clientWidth;
    // 设置页面根节点字体大小（“Math.min(scale, 2)” 指最高放大比例为2，可根据实际业务需求调整）
    let size = this.BS * Math.min(this.CW / 1920, 2);
    document.documentElement.style.fontSize = `${this.CW >= 2560 ? 20 : (this.CW <= 768 ? 12 : size)}px`;
  }
}

export default {
  install(Vue) {
    Vue.prototype.$RS = Vue.$RS = new ResetW(16);
    // 初始化
    Vue.$RS.setRem();
    // 改变窗口大小时重新设置 rem
    window.onresize = function () {
      Vue.$RS.setRem();
    }
  }
}