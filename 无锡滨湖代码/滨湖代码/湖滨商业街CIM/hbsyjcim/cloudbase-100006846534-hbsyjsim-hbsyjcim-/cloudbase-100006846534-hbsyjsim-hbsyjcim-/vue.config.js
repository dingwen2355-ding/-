const { defineConfig } = require("@vue/cli-service");

const path = require("path");

function resolve(dir) {
  return path.join(__dirname, "./", dir);
}

module.exports = defineConfig({
  transpileDependencies: true,
  chainWebpack: (config) => {
    config.resolve.alias.set("@", resolve("src"));
  },
  css: {
    loaderOptions: {
      scss: {
        additionalData: '@import "@/styles/global.scss";',
      },
    },
  },
  devServer: {
    open: true,
    proxy: {
      // 如果请求地址以/api打头,就出触发代理机制
      // http://localhost:9588/api/login -> http://线上的地址/api/login
      "/api": {
        target: "http://2.21.138.71:8081", // 我们要代理的真实接口地址
        changeOrigin: true, //开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样服务端和服务端进行数据的交互就不会有跨域问题
        pathRewrite: {
          "^/api": "",
        }, //通过pathRewrite重写地址，将前缀/api转为/
      },
    },
    client: {
      overlay: false,
    },
  },
  lintOnSave: false,
});
