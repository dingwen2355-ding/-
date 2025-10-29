// module.exports = {
//   root: true,
//   env: {
//     node: true,
//     'jquery': true
//   },
//   'extends': [
//     'plugin:vue/essential',
//     '@vue/standard'
//   ],
//   rules: {
//     'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
//     'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
//     'space-before-function-paren': 'off',
//     'prefer-const': 'off'
//   },
//   globals: {
//     'configs': true
//   },
//   parserOptions: {
//     parser: 'babel-eslint'
//   }
// }

module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint'
  },
  env: {
    browser: true,
  },
  extends: [
    'plugin:vue/essential',
    'standard',
    // 'plugin:prettier/recommended'
  ],
  // required to lint *.vue files
  plugins: [
    'vue',
  ],
  // add your custom rules here
  rules: {
    // allow async-await
    "prefer-const": "off",
    'generator-star-spacing': 'off',
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'space-before-function-paren': 'off',
    'eqeqeq': 'off',
    'quotes': 'off',
    'semi': 'off',
    'indent': 'off',
    'no-unused-vars': 'warn',
    'no-implicit-globals': 'off'
  },
  globals: {
    'configs': true,
    sysConfig: true,
  }
}

