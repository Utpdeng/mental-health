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
  admin: ['/admin', '/assessment/test'],  // admin可以访问/admin开头的路由
  doctor: ['/doctor', '/assessment/test'], // doctor可以访问/doctor开头的路由
  user: ['/user', '/assessment/test']     // user可以访问/user开头的路由
}

// 添加动态路由白名单匹配函数
function matchWhiteList(path) {
  // 首先检查完全匹配
  if (whiteList.includes(path)) {
    return true;
  }
  
  // 检查文章详情页路径 /education/article/任意ID
  if (path.match(/^\/education\/article\/[a-zA-Z0-9]+$/)) {
    return true;
  }
  
  return false;
}

router.beforeEach((to, from, next) => {
  const token = store.state.user.token
  const role = store.state.user.role

  // 使用新的白名单匹配函数
  if (matchWhiteList(to.path)) {
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
    next('/no-permission') // 重定向到无权限页面
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
  return rolePermissions[role].some(prefix => {
    // 对于 /assessment/test 路径，需要特殊处理以支持 /assessment/test/1, /assessment/test/2 等
    if (prefix === '/assessment/test' && path.startsWith('/assessment/test/')) {
      return true
    }
    return path.startsWith(prefix)
  })
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
