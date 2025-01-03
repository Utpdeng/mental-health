import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

// 布局组件
const Layout = () => import('@/components/layout/Layout.vue')

// 路由配置
const routes = [
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        meta: { title: '首页' }
      }
    ]
  },
  {
    path: '/services',
    component: Layout,
    children: [
      {
        path: '',
        name: 'Services',
        component: () => import('@/views/Services.vue'),
        meta: { title: '服务项目' }
      }
    ]
  },
  {
    path: '/about',
    component: Layout,
    children: [
      {
        path: '',
        name: 'About',
        component: () => import('@/views/About.vue'),
        meta: { title: '关于我们' }
      }
    ]
  },
  {
    path: '/resources',
    component: Layout,
    children: [
      {
        path: '',
        name: 'Resources',
        component: () => import('@/views/Resources.vue'),
        meta: { title: '联系资源' }
      }
    ]
  },
  {
    path: '/admin',
    component: Layout,
    meta: { requiresAuth: true, role: 'admin' },
    children: [
      {
        path: '',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue'),
        meta: { title: '管理控制台' }
      }
    ]
  },
  {
    path: '/teacher',
    component: Layout,
    meta: { requiresAuth: true, role: 'teacher' },
    children: [
      {
        path: '',
        name: 'TeacherDashboard',
        component: () => import('@/views/teacher/Dashboard.vue'),
        meta: { title: '教师工作台' }
      }
    ]
  },
  {
    path: '/student',
    component: Layout,
    meta: { requiresAuth: true, role: 'student' },
    children: [
      {
        path: '',
        name: 'StudentDashboard',
        component: () => import('@/views/student/Dashboard.vue'),
        meta: { title: '学生中心' }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach(async (to, from, next) => {
  const isLoggedIn = store.getters['permission/isLoggedIn']
  const userRole = store.getters['permission/userRole']

  // 如果需要认证
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isLoggedIn) {
      // 未登录，重定向到登录页
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
      return
    }
    
    if (to.meta.role && to.meta.role !== userRole) {
      // 角色不匹配，重定向到首页
      next({ path: '/' })
      return
    }
  }

  // 已登录用户访问登录页，重定向到首页
  if (isLoggedIn && to.path === '/login') {
    next({ path: '/' })
    return
  }

  next()
})

export default router
