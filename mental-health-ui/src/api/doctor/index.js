import request from '@/utils/request'

export function getPsychologistsList(params) {
  return request({
    url: '/api/psychologists/list',
    method: 'get',
    params
  })
}

export function getPsychologistDetail(id) {
  return request({
    url: `/api/psychologists/${id}`,
    method: 'get'
  })
}

export function updatePsychologist(data) {
  return request({
    url: '/api/psychologists/updatePsychologist',
    method: 'post',
    data
  })
}
