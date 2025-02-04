import request from '@/utils/request'

// 获取咨询列表
export function getConsultationList(params) {
  return request({
    url: '/api/consultations/list',
    method: 'get',
    params: {
      doctorId: params.doctorId,
      form: params.form
    }
  })
}

// 更新咨询反馈
export function updateConsultation(id, feedback) {
  return request({
    url: '/api/consultations/update',
    method: 'post',
    data: {
      id,
      feedback
    }
  })
}

// 获取咨询详情
export function getConsultationById(id) {
  return request({
    url: `/api/consultations/${id}`,
    method: 'get'
  })
} 