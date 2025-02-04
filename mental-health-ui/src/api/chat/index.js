import request from '@/utils/request'

// 根据标题和分类查询文章
export function getChatHistory(sessionId) {
  return request({
    url: `/api/chat/history/${sessionId}`,
    method: 'get'
  })    
}