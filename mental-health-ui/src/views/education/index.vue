<!-- 将之前的 Education.vue 内容移到这里 --> <template>
  <div class="education-page">
    <div class="page-header">
      <div class="container">
        <h1>心理健康教育</h1>
        <p>丰富的心理健康知识，助您成长</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <!-- 文章列表 -->
        <el-row :gutter="30" v-loading="loading">
          <!-- 有数据时显示文章列表 -->
          <template v-if="articles.length">
            <el-col :xs="24" :sm="12" :lg="8" v-for="article in articles" :key="article.id">
              <div class="article-card">
                <div class="article-image">
                  <img :src="article.imageUrls[0]" :alt="article.title">
                </div>
                <div class="article-content">
                  <h3>{{ article.title }}</h3>
                  <p class="article-description">{{ article.content }}</p>
                  <div class="article-meta">
                    <span><i class="el-icon-user"></i> {{ article.author }}</span>
                    <span><i class="el-icon-folder"></i> {{ article.category }}</span>
                  </div>
                  <el-button type="text" @click="readArticle(article)">阅读更多</el-button>
                </div>
              </div>
            </el-col>
          </template>
          
          <!-- 无数据时显示空状态 -->
          <el-empty
            v-else
            class="empty-state"
            description="暂无文章"
          >
            <template #image>
              <i class="el-icon-document"></i>
            </template>
          </el-empty>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { getArticlesWithPagination } from '@/api/article'

export default {
  name: 'Education',
  data() {
    return {
      articles: [],
      loading: false,
      // 添加模拟数据以备API调用失败时使用
      mockArticles: [
        {
          id: "678f9c090931b935cdb9e6cc",
          title: '克服焦虑的小技巧',
          content: '焦虑是现代生活中常见的心理问题，本文介绍了几种简单有效的方法来缓解日常焦虑...',
          author: '张心理',
          category: '情绪管理',
          imageUrls: ['https://picsum.photos/id/1/800/400'],
          review_status: 'published',
          publishDate: '2023-05-10'
        },
        {
          id: "679c9b5fa4644d5cc4326d01",
          title: '正念冥想的好处',
          content: '正念冥想是一种古老而有效的减压方法，研究表明它对改善心理健康有显著效果...',
          author: '李教授',
          category: '冥想技巧',
          imageUrls: ['https://picsum.photos/id/2/800/400'],
          review_status: 'published',
          publishDate: '2023-06-15'
        },
        {
          id: "679ca0f7804c420b939f9942",
          title: '心理健康与饮食',
          content: '你的饮食选择如何影响你的心理健康？本文探讨了营养与情绪之间的密切关系...',
          author: '王营养师',
          category: '健康生活',
          imageUrls: ['https://picsum.photos/id/3/800/400'],
          review_status: 'published',
          publishDate: '2023-07-20'
        },
        {
          id: "679ca12d804c420b939f9943",
          title: '建立健康的人际关系',
          content: '良好的人际关系对心理健康至关重要，本文分享如何建立和维护健康的人际关系...',
          author: '赵心理咨询师',
          category: '人际关系',
          imageUrls: ['https://picsum.photos/id/4/800/400'],
          review_status: 'published',
          publishDate: '2023-08-05'
        },
        {
          id: "679ca14e804c420b939f9944",
          title: '提升情绪智商',
          content: '情绪智商是成功的关键因素之一，了解如何识别、理解和管理自己的情绪...',
          author: '钱博士',
          category: '个人成长',
          imageUrls: ['https://picsum.photos/id/5/800/400'],
          review_status: 'published',
          publishDate: '2023-09-12'
        },
        {
          id: "679ca16c804c420b939f9945",
          title: '如何管理压力',
          content: '在现代快节奏的生活中，压力管理是保持心理健康的重要技能，本文提供实用的压力管理策略...',
          author: '孙医生',
          category: '压力管理',
          imageUrls: ['https://picsum.photos/id/6/800/400'],
          review_status: 'published',
          publishDate: '2023-10-18'
        }
      ]
    }
  },
  created() {
    this.fetchArticles()
  },
  methods: {
    async fetchArticles() {
      this.loading = true
      try {
        const res = await getArticlesWithPagination()
        if (res && res.status === '200') {
          // 只显示已发布的文章
          this.articles = res.data.filter(article => 
            article.review_status === 'published'
          )
        } else {
          // 使用模拟数据
          this.articles = this.mockArticles
        }
      } catch (error) {
        console.error('获取文章列表失败:', error)
        // API调用失败时使用模拟数据
        this.articles = this.mockArticles
        this.$message.info('使用本地数据展示')
      } finally {
        this.loading = false
      }
    },
    readArticle(article) {
      // 使用原生DOM API创建一个临时链接并点击它，避免任何Vue Router调用
      if (article && article.id) {
        // 构建完整URL（包含基础路径）
        const baseUrl = window.location.origin;
        const url = `${baseUrl}/education/article/${article.id}`;
        
        // 使用<a>标签方法，避免使用Vue Router
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('target', '_self');
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        
        // 防止事件继续传播
        return false;
      } else {
        this.$message.error('文章信息不完整');
      }
    }
  }
}
</script>

<style scoped>
.page-header {
  margin-top: 10px;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  padding: 60px 0;
  text-align: center;
}

.article-card {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  transition: transform 0.3s ease;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.article-card:hover {
  transform: translateY(-5px);
}

.article-image {
  height: 200px;
  overflow: hidden;
}

.article-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.article-content {
  padding: 20px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.article-description {
  color: #666;
  margin: 10px 0;
  flex: 1;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.article-meta {
  color: #999;
  font-size: 13px;
  margin-bottom: 15px;
  display: flex;
  gap: 15px;
}

.article-meta i {
  margin-right: 5px;
}

.el-button {
  width: 100%;
  text-align: left;
  padding: 0;
  color: var(--primary-color);
}

.el-button:hover {
  text-decoration: underline;
}

.empty-state {
  margin: 40px 0;
  padding: 40px 0;
  background: #fff;
  border-radius: 4px;
  width: 100%;
}

.empty-state i {
  font-size: 60px;
  color: #909399;
}
</style> 