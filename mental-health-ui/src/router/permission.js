import router from './index'
import store from '../store'
import { Message } from 'element-ui'

// 白名单路由 - 不需要登录就能访问
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

// 角色权限映射
const rolePermissions = {
  admin: ['/admin'],  // admin可以访问/admin开头的路由
  doctor: ['/doctor'], // doctor可以访问/doctor开头的路由
  user: ['/user']     // user可以访问/user开头的路由
}

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

  // 检查用户是否有权限访问该路由
  const hasPermission = checkPermission(to.path, role)
  if (!hasPermission) {
    Message.error('无权访问该页面')
    next(getDefaultPath(role)) // 重定向到对应角色的默认页面
    return
  }

  // 正常导航
  next()
})

// 检查权限
function checkPermission(path, role) {
  if (!rolePermissions[role]) {
    return false
  }
  
  // 检查路径是否以该角色允许的前缀开头
  return rolePermissions[role].some(prefix => path.startsWith(prefix))
}

// 获取角色默认页面
function getDefaultPath(role) {
  switch (role) {
    case 'admin':
      return '/admin/dashboard'
    case 'doctor':
      return '/doctor/dashboard'
    case 'user':
      return '/user/profile'
    default:
      return '/'
  }
}
