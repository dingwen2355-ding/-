module.exports = {
  //路径前缀
  publicPath: "/",
  lintOnSave: true,
  productionSourceMap: false,
  chainWebpack: (config) => {
    //忽略的打包文件
    config.externals({
      'vue': 'Vue',
      'vue-router': 'VueRouter',
      'vuex': 'Vuex',
      'axios': 'axios',
      'element-ui': 'ELEMENT',
    });
    const entry = config.entry('app');
    entry.add('babel-polyfill').end();
    entry.add('classlist-polyfill').end();
    entry.add('@/mock').end();
  },
  css: {
    extract: { ignoreOrder: true }
  },
  //开发模式反向代理配置，生产模式请使用Nginx部署并配置反向代理
  devServer: {
    port: 1888,
    proxy: {
      '/api': {
        //本地服务接口地址
        target: 'http://172.20.0.133:8080',
        // target: 'http://172.20.0.89:80',    // 徐
        // target: 'http://172.26.61.170:10018', // 王
        // target: 'http://172.20.0.107:9999', // 王
        // target: 'http://172.20.0.133:8080', // 标
        //测试
        // target: 'http://172.16.1.125:20018',
        // target: 'http://58.215.28.118:58807',

        ws: true,
        pathRewrite: {
          '^/api': '/',
          // '^/api': '/api'
        }
      }
    }
  },
};
