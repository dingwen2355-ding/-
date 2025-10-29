/*
 * @Descripttion:eslint规则配置文件
 * @version:
 * @Author: GM
 * @Date: 2021-03-02 17:42:07
 * @LastEditors: GM
 * @LastEditTime: 2021-03-04 23:19:25
 */
module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: ["plugin:vue/essential", "@vue/prettier"],
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-unused-vars": 0,
    "no-console": "off"
  },
  parserOptions: {
    parser: "babel-eslint"
  }
};
