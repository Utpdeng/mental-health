import request from '@/utils/request'

// 根据标题和分类查询文章
export function searchArticles(title, category) {
  return request({
    url: '/api/articles/search',
    method: 'get',
    params: {
      title,
      category
    }
  })    
}

// 获取文章详情
export function getArticle(id) {
  return request({
    url: `/api/articles/${id}`,
    method: 'get'
  })
}

// 删除文章
export function deleteArticle(id) {
  return request({
    url: `/api/articles/${id}`,
    method: 'delete'
  })
}

// 更新文章
export function updateArticle(id, article) {
  return request({
    url: `/api/articles/${id}`,
    method: 'put',
    data: article
  })
}

// 创建文章
export function createArticle(article) {
  return request({
    url: '/api/articles/create',
    method: 'post',
    data: article
  })
}

export function getArticlesWithPagination(pageNo, pageSize) {
    return request({
      url: '/api/articles/page',
      method: 'get',
      params: {
        pageNo,
        pageSize
      }
    })
  }