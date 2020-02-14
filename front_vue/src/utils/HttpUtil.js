import Axios from 'Axios'
import PageUtil from './PageUtil'
import Config from '../conf'

export default {
  get: function () {
    let url = arguments[0] || ''
    let params = arguments[1] || {}
    let headers = arguments[2] || {}
    return new Promise((resolve) => {
      Axios({
        method: 'get',
        url: url,
        params: params,
        headers: headers
      }).then(resp => {
        if (resp.data.code === 400) {
          PageUtil.message('warning', resp.data.msg)
        }
        if (resp.data.code === 500) {
          PageUtil.message('error', '内部错误')
        }
        resolve(resp.data)
      }, () => {
        PageUtil.message('error', '系统错误')
        resolve({code: 500})
      })
    })
  },
  post: function () {
    let url = arguments[0] || ''
    let params = arguments[1] || {}
    let data = arguments[2] || ''
    let headers = arguments[3] || {}
    return new Promise((resolve) => {
      Axios({
        method: 'post',
        url: url,
        params: params,
        data: data,
        headers: headers
      }).then(resp => {
        if (resp.data.code === 400) {
          PageUtil.message('warning', resp.data.msg)
        }
        if (resp.data.code === 500) {
          PageUtil.message('error', '内部错误')
        }
        resolve(resp.data)
      }, () => {
        PageUtil.message('error', '系统错误')
        resolve({code: 500})
      })
    })
  },
  url: Config.http.url
}
