const path = require('path')
const webpack = require('webpack')
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = ['js', 'css']
// const isProduction = process.env.NODE_ENV === 'production'

module.exports = {
    // publicPath: "./", // hase路由配置
    publicPath: "/", // history路由配置
    assetsDir: "static",
    lintOnSave: false,
    productionSourceMap: false,
    devServer: {
        port: 9001,
        disableHostCheck: true,
        overlay: {
            warnings: false,
            errors: false
        },
        proxy: {
            '/api': {
                // target: 'http://192.168.3.31:9000', // 本地服务器
                // target: 'http://pro.jchgis.cn:9000', // 新测试服务器映射
                // target: 'https://viviyy.51vip.biz', // 测试服务器
                // target: 'http://gyhl.wellscene.cn:20000/prod-api/', // 测试服务器
                target: 'http://localhost:9000/', // 测试服务器
                // target: '', // uat
                // target: 'http://2.21.137.224:8080/prod-api', // 正式服务器
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    },
    css: {
        loaderOptions: {
            scss: {
                // V10版本的键名，坑的一P，V8才改过的
                additionalData: `@import "@/assets/style/variables.scss";`
            }
        }
    },
    configureWebpack: {
        resolve: {
            alias: {
                '@': path.resolve(__dirname, './src'),
                '@i': path.resolve(__dirname, './src/assets')
            }
        },
        plugins: [
            new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
            // 下面是下载的插件的配置
            new CompressionWebpackPlugin({
                algorithm: 'gzip',
                test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
                threshold: 10240,
                minRatio: 0.8
            }),
            new webpack.optimize.LimitChunkCountPlugin({
                maxChunks: 5,
                minChunkSize: 100
            })
        ]
    }
}
