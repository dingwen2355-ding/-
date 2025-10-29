'use strict'
const path = require('path')
const webpack = require('webpack')

function resolve (dir) {
  return path.join(__dirname, dir)
}

const port = process.env.port || process.env.npm_config_port || 8560 // dev port

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
      '/gtw/EIMS-management': {
        target: 'http://172.16.7.11:8560/',
        secure: false,
        pathRewrite: { '^gtw/EIMS-management': '' },
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
  css: {
    loaderOptions: {
      sass: {
        sassOptions: {
          outputStyle: 'expanded'
        }
      }
    }
  }
}