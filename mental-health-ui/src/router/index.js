import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'

Vue.use(VueRouter)

// 重写路由的push方法
const routerPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(error => error)
}

// 重写路由的replace方法
const routerReplace = VueRouter.prototype.replace
VueRouter.prototype.replace = function replace(location) {
  return routerReplace.call(this, location).catch(error => error)
}

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/services',
    name: 'Services',
    component: () => import('@/views/Services.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('@/views/About.vue')
  },
  {
    path: '/contact',
    name: 'Contact',
    component: () => import('@/views/Contact.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/experts',
    name: 'Experts',
    component: () => import('@/views/experts/index.vue')
  },
  {
    path: '/assessment',
    name: 'Assessment',
    component: () => import('@/views/assessment/index.vue')
  },
  {
    path: '/education',
    name: 'Education',
    component: () => import('@/views/education/index.vue')
  },
  {
    path: '/community',
    name: 'Community',
    component: () => import('@/views/community/index.vue')
  },
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    meta: { roles: ['admin'] },
    children: [
      {
        path: '',
        redirect: 'dashboard'
      },
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/dashboard/index.vue'),
        meta: { title: '控制台' }
      },
      {
        path: 'users',
        name: 'UserManagement',
        component: () => import('@/views/admin/users/index.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'doctors',
        name: 'DoctorManagement',
        component: () => import('@/views/admin/doctors/index.vue'),
        meta: { title: '医生管理' }
      },
      {
        path: 'articles',
        name: 'DoctorArticles',
        component: () => import('@/views/admin/content/articles/index.vue'),
        meta: { title: '文章管理' }
      },
      {
        path: 'courses',
        name: 'DoctorCourses',
        component: () => import('@/views/admin/content/courses/index.vue'),
        meta: { title: '课程管理' }
      },
      {
        path: 'tests',
        name: 'DoctorTests',
        component: () => import('@/views/admin/content/tests/index.vue'),
        meta: { title: '心理测试' }
      },
      {
        path: 'community',
        name: 'DoctorCommunity',
        component: () => import('@/views/admin/community/index.vue'),
        meta: { title: '社区管理' }
      },
      {
        path: 'profile',
        name: 'AdminProfile',
        component: () => import('@/views/admin/profile/index.vue'),
        meta: { title: '个人中心' }
      } 
    ]
  },
  {
    path: '/doctor',
    component: () => import('@/layouts/DoctorLayout.vue'),
    meta: { roles: ['doctor'] },
    children: [
      {
        path: '',
        redirect: '/doctor/dashboard'
      },
      {
        path: 'dashboard',
        name: 'DoctorDashboard',
        component: () => import('@/views/doctor/dashboard/index.vue'),
        meta: { title: '工作台' }
      },
      {
        path: 'appointments',
        name: 'DoctorAppointments',
        component: () => import('@/views/doctor/appointments/index.vue'),
        meta: { title: '预约管理' }
      },
      {
        path: 'consultations',
        component: {
          render: h => h('router-view')
        },
        children: [
          {
            path: 'sessions',
            name: 'DoctorConsultationSessions',
            component: () => import('@/views/doctor/consultations/sessions/index.vue'),
            children: [
              {
                path: 'chat',
                name: 'DoctorConsultationChat',
                component: () => import('@/views/doctor/consultations/chat/index.vue'),
                meta: { title: '会话' }
              }
            ]
          },
          {
            path: 'records',
            name: 'DoctorConsultationRecords',
            component: () => import('@/views/doctor/consultations/records/index.vue')
          },
          {
            path: 'resources',
            name: 'DoctorConsultationResources',
            component: () => import('@/views/doctor/consultations/resources/index.vue')
          }
        ]
      },
      {
        path: 'articles',
        name: 'DoctorArticles',
        component: () => import('@/views/doctor/articles/index.vue'),
        meta: { title: '文章管理' }
      },
      {
        path: 'courses',
        name: 'DoctorCourses',
        component: () => import('@/views/doctor/courses/index.vue'),
        meta: { title: '课程管理' }
      },
      {
        path: 'tests',
        name: 'DoctorTests',
        component: () => import('@/views/doctor/tests/index.vue'),
        meta: { title: '心理测试' }
      },
      {
        path: 'profile',
        name: 'DoctorProfile',
        component: () => import('@/views/doctor/profile/index.vue'),
        meta: { title: '个人中心' }
      }
    ]
  },
  {
    path: '/user',
    component: () => import('@/layouts/UserLayout.vue'),
    meta: { roles: ['user'] },
    children: [
      {
        path: 'profile',
        name: 'UserProfile',
        component: () => import('@/views/user/profile/index.vue'),
        meta: { title: '个人中心' }
      },
      {
        path: 'appointed',
        name: 'UserAppointed',
        component: () => import('@/views/user/appointed/index.vue'),
        meta: { title: '预约记录' }
      },
      {
        path: 'consultation',
        name: 'UserConsultation',
        component: () => import('@/views/user/consultation/index.vue'),
        meta: { title: '咨询会话' },
        children: [
          {
            path: 'chat',
            name: 'UserConsultationChat',
            component: () => import('@/views/user/chat/index.vue'),
            meta: { title: '会话' }
          }
        ]
      },
      {
        path: 'record',
        name: 'UserRecord',
        component: () => import('@/views/user/record/index.vue'),
        meta: { title: '咨询记录' }
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
})

export default router
