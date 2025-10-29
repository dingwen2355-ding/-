const path = require('path')
const CompressionPlugin = require('compression-webpack-plugin')

function resolve(dir) {
  return path.join(__dirname, dir)
}

// vue.config.js
module.exports = {
  /*
    Vue-cli3:
    Crashed when using Webpack `import()` #2463
    https://github.com/vuejs/vue-cli/issues/2463
   */
  // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
  productionSourceMap: false,
  // 运行时编译模板
  runtimeCompiler: true,
  // 打包app时放开该配置
  // publicPath:'./',
  configureWebpack: (config) => {
    // 生产环境取消 console.log
    if (process.env.NODE_ENV === 'production') {
      config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true
    }
  },
  chainWebpack: (config) => {
    config.resolve.alias
      .set('@$', resolve('src'))
      .set('@api', resolve('src/api'))
      .set('@assets', resolve('src/assets'))
      .set('@comp', resolve('src/components'))
      .set('@views', resolve('src/views'))
      .set('@layout', resolve('src/layout'))
      .set('@static', resolve('src/static'))
      .set('@mobile', resolve('src/modules/mobile'))

    // 生产环境，开启js\css压缩
    if (process.env.NODE_ENV === 'production') {
      config.plugin('compressionPlugin').use(
        new CompressionPlugin({
          test: /\.(js|css|less)$/, // 匹配文件名
          threshold: 10240, // 对超过10k的数据压缩
          deleteOriginalAssets: false // 不删除源文件
        })
      )
    }

    // 配置 webpack 识别 markdown 为普通的文件
    config.module.rule('markdown').test(/\.md$/).use().loader('file-loader').end()
  },

  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          /* less 变量覆盖，用于自定义 ant design 主题 */
          'primary-color': '#1890FF',
          'link-color': '#1890FF',
          /* 'primary-color': '#172d5a',
          'link-color': '#172d5a', */
          'border-radius-base': '4px'
        },
        javascriptEnabled: true
      }
    }
  },

  devServer: {
    port: 3000,
    proxy: {
      /* '/api': {
        target: 'https://mock.ihx.me/mock/5baf3052f7da7e07e04a5116/antd-pro', //mock API接口系统
        ws: false,
        changeOrigin: true,
        pathRewrite: {
          '/api': ''  //默认所有请求都加了api前缀，需要去掉
        }
      }, */
      '/api': {
        target: 'http://localhost:8080', // 请求本地 grandlynn-boot后台项目
        // target: 'http://10.10.10.35:8080',
        //  target: 'http://192.168.137.246:8080',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '/api': '/grandlynn-boot'
        }
      }
    }
  },

  lintOnSave: undefined,
  pages: {
    index: {
      // page 的入口
      entry: 'src/main.js',
      // 当使用 title 选项时，
      // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
      title: '滨湖智慧社区人口平台',
      DOMIAN_URL: '/api'
    }
  }
}
