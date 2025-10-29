'use strict'
const path = require('path')
const webpack = require('webpack')

function resolve (dir) {
  return path.join(__dirname, dir)
}

const port = process.env.port || process.env.npm_config_port || 8084 // dev port

module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  lintOnSave: process.env.NODE_ENV === 'development',
  productionSourceMap: true,
  devServer: {
    port: port,
    open: false,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      '/apis': {
        target: 'http://172.16.7.11:18088/', /* 互联网测试环境 */
        // target: 'http://10.134.20.248:8089/', /*  大数据股份政务网环境 */
        // target: 'http://10.45.157.180:8094/', /* 大数据中心政务云环境 */
        secure: false,
        pathRewrite: { '^/apis': '' },
        changeOrigin: true
      },
      '/mainApis': {
        target: 'http://10.45.157.180:8094/', /* 大数据中心政务云环境 */
        // target: 'http://180.167.126.155:8004/', /* 互联网测试环境 */
        secure: false,
        pathRewrite: { '^/mainApis': '' },
        changeOrigin: true
      },
      '/websocketApis': {
        // target: 'http://180.167.126.155:8001/', /* 互联网测试环境 */
        // target: 'http://10.134.20.248:8083/', /*  大数据股份政务网环境 */
        target: 'http://10.45.157.180:8094/', /* 大数据中心政务云环境 */
        secure: false,
        pathRewrite: { '^/websocketApis': '' },
        changeOrigin: true
      },
      '/smi': {
        target: 'http://map.smi.sh.cegn.cn/',
        secure: false,
        pathRewrite: { '^/smi': '' },
        changeOrigin: true
      }
    }
  },
  configureWebpack: {
    resolve: {
      alias: {
        '@': resolve('src')
      }
    },
    plugins: [
      new webpack.ProvidePlugin({
        jQuery: 'jquery',
        $: 'jquery',
        'windows.jQuery': 'jquery'
      })
    ]
  },
  chainWebpack (config) {
    config.plugins.delete('preload') // TODO: need test
    config.plugins.delete('prefetch') // TODO: need test
    // set preserveWhitespace
    config.module
      .rule('vue')
      .use('vue-loader')
      .loader('vue-loader')
      .tap(options => {
        options.compilerOptions.preserveWhitespace = true
        return options
      })
      .end()

    config
      // https://webpack.js.org/configuration/devtool/#development
      .when(process.env.NODE_ENV === 'development',
        config => config.devtool('cheap-source-map')
      )

    config
      .when(process.env.NODE_ENV !== 'development',
        config => {
          config
            .plugin('ScriptExtHtmlWebpackPlugin')
            .after('html')
            .use('script-ext-html-webpack-plugin', [{
              // `runtime` must same as runtimeChunk name. default is `runtime`
              inline: /runtime\..*\.js$/
            }])
            .end()
          config
            .optimization.splitChunks({
              chunks: 'all',
              cacheGroups: {
                libs: {
                  name: 'chunk-libs',
                  test: /[\\/]node_modules[\\/]/,
                  priority: 10,
                  chunks: 'initial' // only package third parties that are initially dependent
                },
                elementUI: {
                  name: 'chunk-elementUI', // split elementUI into a single package
                  priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
                  test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
                },
                commons: {
                  name: 'chunk-commons',
                  test: resolve('src/components'), // can customize your rules
                  minChunks: 3, //  minimum common number
                  priority: 5,
                  reuseExistingChunk: true
                }
              }
            })
          config.optimization.runtimeChunk('single')
        }
      )
  },
  css: {
    loaderOptions: {
      // 给 sass-loader 传递选项
      sass: {
        // @/ 是 src/ 的别名
        prependData: `
        @import "@/styles/mixin.scss";
        @import "@/styles/variables.scss";
        `
      }
    }
  }
}
