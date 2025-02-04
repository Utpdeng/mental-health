import request from '@/utils/request'

/**
 * 用户登录
 * @param {Object} data
 * @param {string} data.username - 用户名或邮箱
 * @param {string} data.password - 密码
 */
export function login(data) {
  return request({
    url: '/api/users/login',
    method: 'post',
    data
  })
}

/**
 * 用户注册
 * @param {Object} data
 * @param {string} data.username - 用户名
 * @param {string} data.email - 邮箱
 * @param {string} data.password - 密码
 */
export function register(data) {
  return request({
    url: '/api/users/register',
    method: 'post',
    data
  })
}

/**
 * 获取用户信息
 * @param {number} userId - 用户ID
 */
export function getUserInfo(userId) {
  return request({
    url: `/api/users/${userId}`,
    method: 'get'
  })
}

/**
 * 退出登录
 */
export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function getAppointments(params) {
  return request({
    url: '/doctor/appointments',
    method: 'get',
    params
  })
}

export function updateAppointment(id, data) {
  return request({
    url: `/doctor/appointments/${id}`,
    method: 'put',
    data
  })
}

export function getConsultationSessions(params) {
  return request({
    url: '/doctor/consultations/sessions',
    method: 'get',
    params
  })
}

/**
 * 获取用户列表
 * @param {Object} params
 * @param {string} params.role - 角色筛选条件
 * @param {string} params.search - 搜索关键词
 * @param {number} params.page - 当前页码
 * @param {number} params.pageSize - 每页数量
 */
export function getUserList(params) {
  return request({
    url: '/api/users/list',
    method: 'get',
    params
  })
}
