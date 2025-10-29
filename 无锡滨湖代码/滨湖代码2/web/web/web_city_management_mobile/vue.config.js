'use strict'
const path = require('path')
const webpack = require('webpack')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const port = process.env.port || process.env.npm_config_port || 8080 // dev port

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
    // 此处开启 https
    https: false,
    proxy: {
      '/binhuapis': {
        // 青岛
        target: 'http://172.16.7.11:18090/',
        secure: false,
        pathRewrite: { '^/binhuapis': '' },
        changeOrigin: true
      },
      '/apis': {
        // 青岛
        target: 'http://172.16.7.11:9090/',
        secure: false,
        pathRewrite: { '^/apis': '' },
        changeOrigin: true
      },
      '/auth': {
        target: 'http://172.16.7.11:18091/',
        changeOrigin: true,
        pathRewrite: {
          '^/auth': ''
        }
      },
      '/community': {
        target: 'http://172.16.7.11:18015/',
        secure: false,
        pathRewrite: { '^/community': '' },
        changeOrigin: true
      },
      '/eventAuth': {
        target: 'http://172.16.7.11:8049/',
        changeOrigin: true,
        pathRewrite: {
          '^/eventAuth': ''
        }
      },
      '/zbapis': {
        // 淄博
        target: 'http://172.16.7.11:8070/',
        changeOrigin: true,
        pathRewrite: {
          '^/zbapis': ''
        }
      },
      '/dpapis': {
        // 大鹏
        target: 'http://172.16.7.11:18015/',
        changeOrigin: true,
        pathRewrite: {
          '^/dpapis': ''
        }
      },
      '/zbcommunity': {
        target: 'http://172.16.7.11:8071/',
        changeOrigin: true,
        pathRewrite: {
          '^/zbcommunity': ''
        }
      },
      '/cdapis': {
        // 成都
        target: 'http://172.16.7.11:18089/' /* 互联网测试环境 */,
        // target: 'http://10.134.20.248:8089/', /*  大数据股份政务网环境 */
        // target: 'http://10.45.157.180:8094/', /* 大数据中心政务云环境 */
        secure: false,
        pathRewrite: { '^/cdapis': '' },
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
  }
}
