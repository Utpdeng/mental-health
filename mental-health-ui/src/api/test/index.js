import request from '@/utils/request'

// 获取测试列表（医生端）
export function getDoctorTests(params) {
  return request({
    url: '/doctor/tests',
    method: 'get',
    params
  })
}

// 创建测试
export function createTest(data) {
  return request({
    url: '/doctor/tests',
    method: 'post',
    data
  })
}

// 更新测试
export function updateTest(id, data) {
  return request({
    url: `/doctor/tests/${id}`,
    method: 'put',
    data
  })
}

// 删除测试
export function deleteTest(id) {
  return request({
    url: `/doctor/tests/${id}`,
    method: 'delete'
  })
}

// 获取测试详情
export function getTestDetail(id) {
  return request({
    url: `/doctor/tests/${id}`,
    method: 'get'
  })
}

// 获取测试列表（管理员端）
export function getAdminTests(params) {
  return request({
    url: '/admin/tests',
    method: 'get',
    params
  })
}

// 审核测试
export function reviewTest(id, data) {
  return request({
    url: `/admin/tests/${id}/review`,
    method: 'post',
    data
  })
} 