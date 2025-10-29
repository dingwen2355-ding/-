# binhudsjV2

# 快速构建

## Project setup

```
npm install
```

### Compiles and hot-reloads for development

```
npm run serve
```

### Compiles and minifies for production

```
npm build
```

### Lints and fixes files

```
npm lint
```

### Customize configuration

See [Configuration Reference](https://cli.vuejs.org/config/).

# 主要构成依赖

```
1.vue-cli@2.9.6 vue 脚手架
2.postcss-px-to-viewport 转换 css 代码工具
3.webpack-bundle-analyzer 代码组成分析工程
4.axios http 请求封装
5.compression-webpack-plugin 开启 gzip 可以很大程度减少包的大小
6.Lodash 一个一致性、模块化、高性能的 JavaScript 实用工具库
7.babel-polyfill 浏览器兼容
8.ant-design-vue 蚂蚁金服 Ant Design UI 框架
9.vuex 结合 vuex 手写数据本地储存至 sessionStorage
10.less css 预编译配置
11.eslint  规则设置
```

# 使用编辑工具

```
vscode
1.prettier 格式化整理应对eslint
```

# 项目结构

```
│
├─public                // 静态资源目录
|
├─script                // 页面脚本
│
├─node_modules          // 依赖
│
├─src                   // 项目源文件
|  |  App.vue           // 负责构建定义及页面组件归集
│  │  main.js           // 入口文件
│  │
│  ├─api                // 请求接口
|  |  ├─modules         // 不同区划的请求api接口
│  │  ajax.js           // http请求封装
│  │  api.js            // 整合所有api接口
│  │  ipconfig.js       // 不同环境下的域名配置
│  │
│  ├─assets             // 组件静态资源
│  │  ├─css             // 公共样式静态css文件
│  │    └─business      // 项目公共样式组件
│  │  └─images          // 公共图片样式
│  │    └─icons         // 框架公共小图标（不储存项目图标）
│  │
│  ├─components         // 公共文件
│  │  ├─business        // 业务组件
│  │  │ ├─tools         // 业务组件下的工具
│  │  │ └─page          // 业务组件下的组件
│  │  │   └─ditto       // 业务组件下的UI组件
│  │  ├─frame           // 框架的组件
│  │  └─utils           // js工具
│  │
│  ├─plugins            // 第三方插件
│  │
│  ├─router             // 路由管理
|  |  ├─modules         // 不同业务下的路由模块
|  |  ├─index.js        // 整合所有路由模块
│  │
│  ├─store              // 状态管理
|  |  ├─modules         // 不同业务下的状态
|  |  ├─index.js        // 整合所有状态
│  │
│  ├─views              // 页面模块
|  |  ├─layouts         // 框架下的页面布局模块
|  |  ├─page            // 业务页面
│  │
│  │ .env               // 启动环境配置
│  │ .eslintrc.js       // eslint规则配置
│  └  vue.config.js      // vue环境综合配置
```
