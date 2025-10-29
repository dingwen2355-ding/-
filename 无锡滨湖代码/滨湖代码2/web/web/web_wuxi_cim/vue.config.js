const webpack = require("webpack");
const path = require("path");
const CopyWebpackPlugin = require("copy-webpack-plugin");
// 引入等比适配插件
const px2rem = require("postcss-px2rem");

// 配置基本大小
const postcss = px2rem({
  // 基准大小 baseSize，需要和rem.js中相同
  remUnit: 16,
});
function resolve(dir) {
  return path.join(__dirname, dir);
}
const version = new Date().getTime();
module.exports = {
  // eslint检查代码
  publicPath: "./",
  lintOnSave: false,
  // 静态资源 (js、css、img、fonts)
  assetsDir: "static",
  // 默认情况下 babel-loader 忽略其中的所有文件 node_modules
  transpileDependencies: [],
  devServer: {
    watchOptions: {
      ignored: [/node_modules/],
      poll: 1200,
    },
    open: true,
    host: "0.0.0.0",
    disableHostCheck: true,
    // port: 8080,
    https: false,
    hotOnly: false,
    hot: true,
    // proxy: '',
    proxy: {
      //配置跨域
      // '/api': {
      //   target: 'http://cim-platform.g-bim.cn/cim-dev-backstage-server/',  //这里后台的地址模拟的;应该填写你们真实的后台接口
      //   changOrigin: true,  //允许跨域
      //   pathRewrite: {
      //     '^/api': ''
      //   }
      // },
      "/apis": {
        target: "http://2.21.137.156:8088/",
        secure: false,
        pathRewrite: { "^/apis": "" },
        changeOrigin: true,
      },
      "/community": {
        target: "http://2.21.137.156:8092/",
        changeOrigin: true,
        pathRewrite: {
          "^/community": "",
        },
      },
    },
    before: (app) => {},
  },
  chainWebpack: (config) => {
    //修改文件引入自定义路径
    // config.resolve.alias.set('@', resolve('src'));
    // 图片打包压缩，使用了 --- image-webpack-loader --- 插件对图片进行压缩
    config.plugins.delete("prefetch");
    config.resolve.alias
      .set("assets", resolve("src/assets"))
      .set("@", resolve("src"));
    // config.output.filename(`[name].js?[hash].${version}`).end()
    // set svg-sprite-loader
    config.module
      .rule("svg")
      .exclude.add(resolve("src/icons"))
      .end();
    config.module
      .rule("icons")
      .test(/\.svg$/)
      .include.add(resolve("src/icons"))
      .end()
      .use("svg-sprite-loader")
      .loader("svg-sprite-loader")
      .options({
        symbolId: "icon-[name]",
      })
      .end();
    /* config.module
      .rule('images')
      .use('image-webpack-loader')
      .loader('image-webpack-loader')
      .options({ bypassOnDebug: true })
      .end() */
  },
  productionSourceMap: process.env.NODE_ENV === "development",
  // pluginOptions: {},
  configureWebpack: {
    devtool: process.env.NODE_ENV === "development" ? "source-map" : undefined,
    devServer: {
      headers: { "Access-Control-Allow-Origin": "*" },
    },
    plugins: [
      new CopyWebpackPlugin([
        {
          from:
            "node_modules/@liveqing/liveplayer/dist/component/crossdomain.xml",
        },
        {
          from:
            "node_modules/@liveqing/liveplayer/dist/component/liveplayer-lib.min.js",
          to: "js/",
        },
        {
          from:
            "node_modules/@liveqing/liveplayer/dist/component/liveplayer.swf",
        },
      ]),
    ],
    output: {
      // 输出重构  打包编译后的 文件名称  【模块名称.版本号.时间戳】
      filename: `static/js/[name].[hash].${version}.js`,
      chunkFilename: `static/js/[id].[hash].${version}.js`,
    },
  },
  css: {
    loaderOptions: {
      postcss: {
        plugins: [postcss],
      },
    },
  },
  /* module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /(node_modules|bower_components)/,
        use: {
          loader: "babel-loader?cacheDirectory=true",
          options: {
            presets: ["@babel/preset-env", "@babel/preset-react"],
          },
        },
      },
    ],
  } */
};
