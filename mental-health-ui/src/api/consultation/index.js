import request from '@/utils/request'

export function getConsultationList(doctorId, userId, appointmentId, form) {
  return request({
    url: '/api/consultations/list',
    method: 'get',
    params: {
      doctorId,
      userId,
      appointmentId,
      form
    }
  })
}

export function getConsultationById(id) {
  return request({
    url: `/api/consultations/${id}`,
    method: 'get'
  })
}

export function createAppointment(data) {
  return request({
    url: '/api/consultations/create',
    method: 'post',
    data
  })
}

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
