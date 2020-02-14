import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/enterprise',
      name: 'Enterprise',
      component: () => import('@/views/enterprise/index')
    },
    {
      path: '/enterprise/form',
      name: 'EnterpriseForm',
      component: () => import('@/views/enterprise/form')
    },
    {
      path: '/enterprise/menu',
      name: 'EnterpriseMenu',
      component: () => import('@/views/enterprise/menu')
    },
    {
      path: '/enterprise/qrcode',
      name: 'EnterpriseQrcode',
      component: () => import('@/views/enterprise/qrcode')
    },
    {
      path: '/enterprise/password',
      name: 'EnterprisePassword',
      component: () => import('@/views/enterprise/password')
    },{
      path: '/enterprise/person',
      name: 'EnterprisePerson',
      component: () => import('@/views/enterprise/person')
    },
    {
      path: '/person/:id',
      name: 'Person',
      component: () => import('@/views/person/index')
    }
  ]
})
