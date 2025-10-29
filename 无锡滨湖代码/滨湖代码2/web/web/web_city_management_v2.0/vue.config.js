'use strict'
const path = require('path')
const webpack = require('webpack')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const SpeedMeasurePlugin = require('speed-measure-webpack-plugin')

const smp = new SpeedMeasurePlugin()

const port = process.env.port || process.env.npm_config_port || 9530 // dev port
console.log(process.argv)
process.env.VUE_APP_BUILD_ENV = process.argv[process.argv.length - 1]
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
        target: 'http://2.21.137.156:8088/',
        secure: false,
        pathRewrite: { '^/apis': '' },
        changeOrigin: true
      },
      '/testmap': {
        target: 'https://apis.map.qq.com/',
        secure: false,
        pathRewrite: { '^/apis': '' },
        changeOrigin: true
      },
      '/auth': {
        target: 'http://172.16.7.11:8096/',
        changeOrigin: true,
        pathRewrite: {
          '^/auth': ''
        }
      },
      '/community': {
        target: 'http://2.21.137.156:8089/',
        changeOrigin: true,
        pathRewrite: {
          '^/community': ''
        }
      },
      '/spi': {
        target: 'http://2.21.137.156:8093/',
        changeOrigin: true,
        pathRewrite: {
          '^/spi': ''
        }
      },
      '/files': {
        target: 'http://2.21.137.156:8080/',
        changeOrigin: true
      },
      '/flood': {
        target: 'http://172.16.7.11:18089/',
        secure: false,
        pathRewrite: { '^/flood': '' },
        changeOrigin: true
      },
      '/txmap': {
        target: 'http://2.22.51.26:30000/',
        secure: false,
        pathRewrite: { '^/txmap': '' },
        changeOrigin: true
      }
    },
    // 此处开启 https
    https: false
  },
  configureWebpack: smp.wrap({
    devtool: 'eval-source-map',
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
  })
}
