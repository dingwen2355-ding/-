let plugins = [];
if (process.env.NODE_ENV === "production") {
  // 生成环境，自动清除console日志打印，保留error和warn
  plugins.push([
    "transform-remove-console", {
      exclude: ['error', 'warn']
    }
  ]);
}
module.exports = {
  presets: ["@vue/cli-plugin-babel/preset"]
};
