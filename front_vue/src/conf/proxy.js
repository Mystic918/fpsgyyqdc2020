module.exports = {
  '/survey2020/api': {
    target: 'http://old.gaoyao.gov.cn',
    changeOrigin: true,
    pathRewrite: { '^/survey2020/api': '/survey2020/api' }
  }
}
