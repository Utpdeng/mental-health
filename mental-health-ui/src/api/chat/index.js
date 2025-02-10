import request from '@/utils/request'

// 查询聊天记录
export function getChatHistory(sessionId) {
  return request({
    url: `/api/chat/history/${sessionId}`,
    method: 'get'
  })    
}

export function chatbot(req) {
  return request({
    url: '/api/chat/chatbot',
    method: 'get',
    params:{req}
  })
}