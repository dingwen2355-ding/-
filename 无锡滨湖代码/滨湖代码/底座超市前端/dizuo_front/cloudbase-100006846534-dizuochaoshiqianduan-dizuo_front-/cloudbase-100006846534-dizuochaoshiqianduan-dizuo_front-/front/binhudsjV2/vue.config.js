/*
 * @Descripttion: vue.config基础设置
 * @version:
 * @Author: GM
 * @Date: 2021-03-02 11:06:10
 * @LastEditors: GM
 * @LastEditTime: 2021-08-26 16:17:53
 */
const webpack = require("webpack");
const CompressionWebpackPlugin = require("compression-webpack-plugin");
const BundleAnalyzerPlugin = require("webpack-bundle-analyzer")
  .BundleAnalyzerPlugin;
const isProduction = process.env.NODE_ENV === "prod";
const nextWork = process.env.VUE_APP_NEXTWORK === "true";
const path = require("path");
const cdn = {
  externals: {
    vue: "Vue",
    vuex: "Vuex",
    "vue-router": "VueRouter",
    "ant-design-vue": "antd",
    jquery: "$",
    axios: "axios",
    lodash: "_",
    "mapbox-gl": "mapboxgl",
    "@mapbox/mapbox-gl-language": "MapboxLanguage",
    "@turf/turf": "turf",
    proj4: "proj4",
    "vue-photo-preview": "vuePhotoPreview"
  },
  js: [
    "https://cdn.jsdelivr.net/npm/vue-router@3.0.3/dist/vue-router.min.js",
    "https://cdn.jsdelivr.net/npm/vue@2.6.12/dist/vue.min.js",
    "https://cdn.jsdelivr.net/npm/moment@2.24.0/min/moment-with-locales.min.js",
    "https://cdn.jsdelivr.net/npm/ant-design-vue@1.7.5/dist/antd.min.js",
    "https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js",
    "https://cdn.jsdelivr.net/npm/axios@0.21.1/dist/axios.min.js",
    "https://cdn.jsdelivr.net/npm/vuex@3.0.1/dist/vuex.min.js",
    "https://cdn.jsdelivr.net/npm/lodash@4.17.15/lodash.min.js",
    "https://cdn.jsdelivr.net/npm/mapbox-gl@2.1.1/dist/mapbox-gl.min.js",
    "https://cdn.jsdelivr.net/npm/@mapbox/mapbox-gl-language@0.10.1/index.min.js",
    "https://cdn.jsdelivr.net/npm/@turf/turf@5.1.6/turf.min.js",
    "https://cdn.jsdelivr.net/npm/proj4@2.5.0/dist/proj4.js",
    "https://cdn.jsdelivr.net/npm/vue-photo-preview@1.1.3/dist/vue-photo-preview.min.js"
  ],
  css: [
    "https://cdn.jsdelivr.net/npm/ant-design-vue@1.7.5/dist/antd.min.css",
    "https://cdn.jsdelivr.net/npm/vue-photo-preview@1.1.3/dist/skin.css"
  ]
};

module.exports = {
  publicPath: "/binhudsj",
  outputDir: "binhudsj",
  assetsDir: "static",
  productionSourceMap: false,
  runtimeCompiler: true,
  pluginOptions: {
    "style-resources-loader": {
      preProcessor: "less",
      patterns: []
    }
  },
  configureWebpack: config => {
    if (isProduction) {
      config.plugins.push(
        new CompressionWebpackPlugin({
          algorithm: "gzip",
          test: /\.js$|\.html$|\.json$|\.css/,
          threshold: 10240,
          minRatio: 0.8
        })
      );
      config.plugins.push(
        new webpack.ContextReplacementPlugin(
          /moment[/\\]locale$/,
          /zh-cn|zh-hk|en/
        )
      );
      config.optimization = {
        runtimeChunk: "single",
        splitChunks: {
          chunks: "all",
          maxInitialRequests: Infinity,
          minSize: 20000,
          cacheGroups: {
            vendor: {
              test: /[\\/]node_modules[\\/]/,
              name(module) {
                // get the name. E.g. node_modules/packageName/not/this/part.js
                // or node_modules/packageName
                const packageName = module.context.match(
                  /[\\/]node_modules[\\/](.*?)([\\/]|$)/
                )[1];
                // npm package names are URL-safe, but some servers don't like @ symbols
                return `npm.${packageName.replace("@", "")}`;
              }
            }
          }
        }
      };
      // 取消webpack警告的性能提示
      config.performance = {
        hints: "warning",
        //入口起点的最大体积
        maxEntrypointSize: 50000000,
        //生成文件的最大体积
        maxAssetSize: 30000000,
        //只给出 js 文件的性能提示
        assetFilter: function (assetFilename) {
          return assetFilename.endsWith(".js");
        }
      };
      // config.externals = nextWork ? cdn.externals : {};
    };
    config.externals = {
      ...config.externals,
      './cptable': 'var cptable'
    }
  },
  chainWebpack: config => {
    if (process.env.use_analyzer) {
      config
        .plugin("webpack-bundle-analyzer")
        .use(require("webpack-bundle-analyzer").BundleAnalyzerPlugin);
    }
    // if (isProduction && nextWork) {
    config.plugin("html").tap(args => {
      args[0].title = '滨湖区大数据';
      // args[0].cdn = cdn;
      return args;
    });
    // };
    config.plugin('provide').use(webpack.ProvidePlugin, [{
      $: 'jquery',
      jquery: 'jquery',
      jQuery: 'jquery',
      'window.jQuery': 'jquery'
    }])
  },
  css: {
    loaderOptions: {
      less: {
        javascriptEnabled: true
      }
    },
    extract: true, // 是否使用css分离插件 ExtractTextPlugin
    sourceMap: false, // 开启 CSS source maps
    modules: false // 启用 CSS modules for all css / pre-processor files.
  },
  devServer: {
    port: 8070,
    open: true,
    //是否只能本地访问
    disableHostCheck: true,
    host: "0.0.0.0",
    https: false,
    hotOnly: true,
    proxy: {
      "/api": {
        target: 'https://2.21.138.89:11511/', //
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        //ws: true, // proxy websockets
        //pathRewrite方法重写url
        pathRewrite: {
          '^/api': '/api'
          //pathRewrite: {'^/api': '/'} 重写之后url为 http://192.168.1.16:8085/xxxx
          //pathRewrite: {'^/api': '/api'} 重写之后url为 http://192.168.1.16:8085/api/xxxx
        }
      },
      "/dbApi": {
        target: 'https://2.21.138.89:11511/', //
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        //ws: true, // proxy websockets
        //pathRewrite方法重写url
        pathRewrite: {
          '^/dbApi': '/dbApi'
          //pathRewrite: {'^/api': '/'} 重写之后url为 http://192.168.1.16:8085/xxxx
          //pathRewrite: {'^/api': '/api'} 重写之后url为 http://192.168.1.16:8085/api/xxxx
        },
      },
      "/logout": {
        target: 'http://2.21.137.200:8081/', //
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        //ws: true, // proxy websockets
        //pathRewrite方法重写url
        pathRewrite: {
          '^/logout': '/logout'
          //pathRewrite: {'^/api': '/'} 重写之后url为 http://192.168.1.16:8085/xxxx
          //pathRewrite: {'^/api': '/api'} 重写之后url为 http://192.168.1.16:8085/api/xxxx
        }
      }
    }
  },
  lintOnSave: false,
};
