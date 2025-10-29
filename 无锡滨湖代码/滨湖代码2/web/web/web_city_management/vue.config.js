'use strict'
const path = require('path')
const webpack = require('webpack')

function resolve (dir) {
  return path.join(__dirname, dir)
}

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
        target: 'http://172.16.7.11:18089/',
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
        target: 'http://172.16.7.11:18090/',
        changeOrigin: true,
        pathRewrite: {
          '^/community': ''
        }
      },
      '/spi': {
        target: 'http://172.16.7.11:18091/',
        changeOrigin: true,
        pathRewrite: {
          '^/spi': ''
        }
      },
      '/files': {
        target: 'http://172.16.7.11:28082/'
      }
    },
    // 此处开启 https
    https: false
  },
  configureWebpack: {
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
  }
}
