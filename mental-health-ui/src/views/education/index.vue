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
      loading: false
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
        if (res.status === '200') {
          // 只显示已发布的文章
          this.articles = res.data.filter(article => 
            article.review_status === 'published'
          )
        }
      } catch (error) {
        this.$message.error('获取文章列表失败')
      } finally {
        this.loading = false
      }
    },
    readArticle(article) {
      this.$message.info(`即将阅读文章：${article.title}`)
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