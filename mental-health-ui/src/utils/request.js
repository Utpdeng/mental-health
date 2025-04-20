import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API || '/api',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 不需要token的白名单路径
// eslint-disable-next-line no-unused-vars
const whiteList = [
  '/api/articles',
  '/api/experts',
  '/api/services',
  '/api/assessments',
  '/api/education',
]

// 请求拦截器
service.interceptors.request.use(
  config => {
    const token = store.state.user.token
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    
    // 详细的请求日志
    console.log('Axios Request:', {
      baseURL: config.baseURL,
      url: config.url,
      fullUrl: config.baseURL + config.url,
      method: config.method,
      headers: config.headers,
      data: config.data,
      params: config.params
    })
    
    return config
  },
  error => {
    console.error('Request Error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 详细的响应日志
    console.log('Axios Response:', {
      status: response.status,
      statusText: response.statusText,
      data: response.data,
      headers: response.headers
    })
    
    const res = response.data
    if (res.status !== '200') {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.message || 'Error'))
    }
    return res
  },
  error => {
    // 详细的错误日志
    console.error('Response Error:', {
      message: error.message,
      status: error.response?.status,
      statusText: error.response?.statusText,
      data: error.response?.data,
      config: {
        baseURL: error.config?.baseURL,
        url: error.config?.url,
        method: error.config?.method,
        headers: error.config?.headers,
        data: error.config?.data
      }
    })
    
    Message({
      message: error.response?.data?.message || '操作完成',
      type: 'success',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
