var webRoot = '/survey2020/api'
var http = {
  url: {
    root: 'http://old.gaoyao.gov.cn/survey2020',
    enterprise: webRoot + '/enterprise',
    enterprisePassword: webRoot + '/enterprise/password',
    person: webRoot + '/person/:eid',
    personList: webRoot +'/enterprise/person'
  }
}

export default {
  http
}
