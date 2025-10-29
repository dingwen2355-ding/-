import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from "path"
import postcsspxtoviewport from "postcss-px-to-viewport"
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  lintOnSave: false,
  css: {
    preprocessorOptions: {
      scss: {
        additionalData:`@import "@/assets/styles/jc-ui-base.scss";`,
      }
    },
    postcss: {
      plugins: [
        postcsspxtoviewport({
          unitToConvert: 'px', // 要转化的单位
          viewportWidth: 3840, // UI设计稿的宽度，一般写 320
          // 下面的不常用，上面的常用
          unitPrecision: 6, // 转换后的精度，即小数点位数
          propList: ['*'], // 指定转换的css属性的单位，*代表全部css属性的单位都进行转换
          viewportUnit: 'vw', // 指定需要转换成的视窗单位，默认vw
          fontViewportUnit: 'vw', // 指定字体需要转换成的视窗单位，默认vw
          selectorBlackList: ['ignore-'], // 指定不转换为视窗单位的类名，
          minPixelValue: 0.25, // 默认值1，小于或等于1px则不进行转换
          mediaQuery: true, // 是否在媒体查询的css代码中也进行转换，默认false
          replace: true, // 是否转换后直接更换属性值
          landscape: false // 是否处理横屏情况
        })
      ]
    }
  },
  base: './',
  // 打包配置
  build: {
    outDir: 'binhu-screen-web', //指定输出路径
    minify: 'terser', // 混淆器，terser构建后文件体积更小
  },
  resolve: {
    // 配置路径别名
    alias: {
      '@': resolve(__dirname, './src'),
    },
  },
  // 本地运行配置，及反向代理配置
  server: {
    port: 10402,
    host: "0.0.0.0",
    // 是否开启 https
    https: false,
    cors: true, // 默认启用并允许任何源
    open: true, // 在服务器启动时自动在浏览器中打开应用程序
    //反向代理配置，注意rewrite写法，开始没看文档在这里踩了坑
    proxy: {
      '/api': {
        target: 'http://gyhl.wellscene.cn:20000/prod-api', //代理接口
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/video': {
        target: 'http://devimages.apple.com', //代理接口
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/video/, ''),
      },
      '/prod-api': {
        target: 'http://2.21.138.78:8080/prod-api', //代理接口
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/prod-api/, ''),
      },
    },
  },
})
