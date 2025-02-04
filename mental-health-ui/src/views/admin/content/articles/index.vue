<template>
  <div class="article-management">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <div class="right-filters">
        <el-input
          v-model="searchQuery"
          placeholder="搜索文章标题"
          prefix-icon="el-icon-search"
          clearable
          @clear="handleSearch"
          @keyup.enter.native="handleSearch"
          class="filter-item"
        />
        <el-select
          v-model="statusFilter"
          placeholder="状态"
          clearable
          @change="handleSearch"
          class="filter-item"
        >
          <el-option label="全部" value="" />
          <el-option label="已发布" value="published" />
          <el-option label="待审核" value="pending" />
        </el-select>
      </div>
    </div>

    <!-- 文章表格 -->
    <el-table
      v-loading="loading"
      :data="articles"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="title"
        label="标题"
        min-width="200"
      >
        <template slot-scope="scope">
          <el-button type="text" @click="viewDetail(scope.row)">
            {{ scope.row.title }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="author"
        label="发布者"
        width="120"
      />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="180"
      >
        <template slot-scope="scope">
          {{ formatDate(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="更新时间"
        width="180"
      >
        <template slot-scope="scope">
          {{ formatDate(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="review_status"
        label="状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.review_status)">
            {{ getStatusText(scope.row.review_status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="220"
        fixed="right"
      >
        <template slot-scope="scope">
          <div class="operation-buttons">
            <el-button
              v-if="scope.row.review_status === 'pending'"
              type="success"
              size="mini"
              @click="handleApprove(scope.row)"
            >通过</el-button>
            <el-button
              v-if="scope.row.review_status === 'pending'"
              type="warning"
              size="mini"
              @click="handleReject(scope.row)"
            >拒绝</el-button>
            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>

    <!-- 文章详情对话框 -->
    <el-dialog
      title="文章详情"
      :visible.sync="dialogVisible"
      width="60%"
      class="article-dialog"
    >
      <div v-if="currentArticle" class="article-detail">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="标题">
            {{ currentArticle.title }}
          </el-descriptions-item>
          <el-descriptions-item label="作者">
            {{ currentArticle.author }}
          </el-descriptions-item>
          <el-descriptions-item label="分类">
            {{ currentArticle.category }}
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">
            {{ formatDate(currentArticle.createTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="更新时间">
            {{ formatDate(currentArticle.updateTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(currentArticle.review_status)">
              {{ getStatusText(currentArticle.review_status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="封面">
            <div class="article-cover" v-if="currentArticle.imageUrls && currentArticle.imageUrls.length">
              <img :src="currentArticle.imageUrls[0]" :alt="currentArticle.title">
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="内容">
            <div class="article-content">{{ currentArticle.content }}</div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div v-else class="loading-placeholder">
        <el-empty description="加载中..."></el-empty>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { 
  searchArticles,
  getArticle,
  updateArticle,
  deleteArticle
} from '@/api/article'
import { formatDate } from '@/utils/date'

export default {
  name: 'ArticleManagement',
  data() {
    return {
      loading: false,
      articles: [],
      searchQuery: '',
      statusFilter: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      currentArticle: null
    }
  },
  created() {
    this.fetchArticles()
  },
  methods: {
    async fetchArticles() {
      this.loading = true
      try {
        const res = await searchArticles(this.searchQuery, '')
        if (res.status === '200') {
          let filteredArticles = res.data
          if (this.statusFilter) {
            filteredArticles = res.data.filter(article => 
              article.review_status === this.statusFilter
            )
          }
          this.articles = filteredArticles
          this.total = filteredArticles.length
        }
      } catch (error) {
        this.$message.error('获取文章列表失败')
      } finally {
        this.loading = false
      }
    },

    async viewDetail(article) {
      try {
        const res = await getArticle(article.id)
        this.currentArticle = res.data
        this.dialogVisible = true
      } catch (error) {
        this.$message.error('获取文章详情失败')
      }
    },

    async handleApprove(article) {
      try {
        await updateArticle(article.id, {
          ...article,
          review_status: 'published'
        })
        this.$message.success('审核通过')
        this.fetchArticles()
      } catch (error) {
        this.$message.error('操作失败')
      }
    },

    async handleReject(article) {
      try {
        await updateArticle(article.id, {
          ...article,
          review_status: 'rejected'
        })
        this.$message.success('已拒绝')
        this.fetchArticles()
      } catch (error) {
        this.$message.error('操作失败')
      }
    },

    async handleDelete(article) {
      this.$confirm('确认删除该文章?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await deleteArticle(article.id)
          this.$message.success('删除成功')
          this.fetchArticles()
        } catch (error) {
          this.$message.error('删除失败')
        }
      }).catch(() => {})
    },

    handleSearch() {
      this.currentPage = 1
      this.fetchArticles()
    },

    handleSizeChange(val) {
      this.pageSize = val
      this.fetchArticles()
    },

    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchArticles()
    },

    getStatusType(status) {
      const types = {
        published: 'success',
        pending: 'warning',
        rejected: 'danger'
      }
      return types[status] || 'info'
    },

    getStatusText(status) {
      const texts = {
        published: '已发布',
        pending: '待审核',
        rejected: '已拒绝'
      }
      return texts[status] || status
    },

    formatDate
  }
}
</script>

<style scoped>
.article-management {
  padding: 20px;
}

.operation-bar {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.right-filters {
  display: flex;
  gap: 10px;
}

.filter-item {
  width: 200px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}

.article-detail {
  padding: 20px;
}

.article-detail h2 {
  margin: 0 0 20px;
  color: #303133;
}

.article-info {
  color: #909399;
  margin-bottom: 20px;
}

.article-info span {
  margin-right: 20px;
}

.article-info i {
  margin-right: 5px;
}

.article-content {
  line-height: 1.8;
  color: #606266;
}

.article-dialog {
  /* Add any necessary styles for the dialog */
}

.article-cover {
  width: 100%;
  max-width: 300px;
  height: 180px;
  margin: 10px 0;
  border-radius: 4px;
  overflow: hidden;
}

.article-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.loading-placeholder {
  padding: 40px 0;
  text-align: center;
}
</style>
