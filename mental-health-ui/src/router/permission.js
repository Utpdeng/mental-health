import router from './index'
import store from '../store'
import { Message } from 'element-ui'

// 白名单路由
const whiteList = [
  '/login', 
  '/register', 
  '/', 
  '/about', 
  '/contact', 
  '/services',
  '/experts',
  '/assessment',
  '/education',
  '/community'
]

router.beforeEach((to, from, next) => {
  const token = store.state.user.token
  const role = store.state.user.role

  // 在白名单中，直接通过
  if (whiteList.includes(to.path)) {
    next()
    return
  }

  // 没有token，重定向到登录页
  if (!token) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
    return
  }

  // 有token但没有角色信息，获取用户信息
  if (!role) {
    store.dispatch('user/getUserInfo')
      .then(() => {
        next({ ...to, replace: true })
      })
      .catch(() => {
        store.dispatch('user/logout').then(() => {
          Message.error('获取用户信息失败，请重新登录')
          next({
            path: '/login',
            query: { redirect: to.fullPath }
          })
        })
      })
    return
  }

  // 检查页面是否需要特定角色
  if (to.meta.roles && !to.meta.roles.includes(role)) {
    next('/')
    Message.error('无权访问该页面')
    return
  }

  // 根据角色跳转到不同控制台
  if (to.path === '/login' && role) {
    switch (role) {
      case 'admin':
        next('/admin/dashboard')
        break
      case 'doctor':
        next('/doctor/dashboard') 
        break
      default:
        next('/')
    }
    return
  }

  // 正常导航
  next()
})
