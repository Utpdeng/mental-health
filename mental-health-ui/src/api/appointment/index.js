import request from '@/utils/request'

export function getAppointments(userId, doctorId, status) {
  return request({
    url: '/api/appointments/list',
    method: 'get',
    params: {
      userId,
      doctorId,
      status
    }
  })
}

export function getAppointmentDetail(id) {
  return request({
    url: `/api/appointments/${id}`,
    method: 'get'
  })
}

export function createAppointment(data) {
  return request({
    url: '/api/appointments/create',
    method: 'post',
    data
  })
}

export function updateAppointment(id, status) {
  return request({
    url: `/api/appointments/${id}`,
    method: 'put',
    params: {
      status
    }
  })
}
