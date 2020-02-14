import Element from 'element-ui'

export default {
  confirm: function (title) {
    return new Promise((resolve) => {
      Element.MessageBox.confirm(title, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        resolve(true)
      }).catch(() => {
        resolve(false)
      })
    })
  },
  loading: function () {
    const options = {
      target: arguments[0] || 'document.body',
      body: arguments[1] || false,
      fullscreen: arguments[2] || true,
      lock: arguments[3] || false,
      background: 'rgba(0, 0, 0, 0.5)'
    }
    const loadingInstance = Element.Loading.service(options)
    return loadingInstance
  },
  message: function (type, message) {
    Element.Message({
      showClose: true,
      message: message,
      type: type
    })
  },
  validate: function (e) {
    return new Promise((resolve) => {
      e.validate((valid) => {
        resolve(valid)
      })
    })
  }
}
