<template>
  <div class="article-management">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <div class="left-actions">
        <el-button type="primary" icon="el-icon-plus" @click="handleCreate">
          发布文章
        </el-button>
      </div>
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
          v-model="categoryFilter"
          placeholder="文章分类"
          clearable
          @change="handleSearch"
          class="filter-item"
        >
          <el-option
            v-for="item in categories"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
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

    <!-- 文章列表 -->
    <div class="article-list" v-loading="loading">
      <!-- 有数据时显示文章列表 -->
      <template v-if="articles.length">
        <el-card
          v-for="article in articles"
          :key="article.id"
          class="article-card"
          shadow="hover"
        >
          <div class="article-header">
            <h3 class="article-title" @click="viewDetail(article)">
              {{ article.title }}
            </h3>
            <el-tag
              :type="getStatusType(article.review_status)"
              size="small"
            >
              {{ getStatusText(article.review_status) }}
            </el-tag>
          </div>
          <div class="article-preview">
            <div class="article-cover" v-if="article.imageUrls && article.imageUrls.length">
              <img :src="article.imageUrls[0]" :alt="article.title">
            </div>
            <div class="article-content">
              <p class="article-excerpt">{{ getExcerpt(article.content) }}</p>
              <div class="article-meta">
                <span><i class="el-icon-user"></i> {{ article.author }}</span>
                <span><i class="el-icon-folder"></i> {{ article.category }}</span>
                <span><i class="el-icon-time"></i> {{ formatDate(article.createTime) }}</span>
              </div>
            </div>
          </div>
          <div class="article-actions">
            <el-button
              type="text"
              size="small"
              @click="handleEdit(article)"
            >编辑</el-button>
            <el-button
              type="text"
              size="small"
              @click="handleDelete(article)"
            >删除</el-button>
          </div>
        </el-card>
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
    </div>

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

    <!-- 文章编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="handleDialogClose"
    >
      <el-form
        ref="articleForm"
        :model="articleForm"
        :rules="articleRules"
        label-width="80px"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="articleForm.title" placeholder="请输入文章标题" />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="articleForm.category" placeholder="请选择文章分类">
            <el-option
              v-for="item in categories"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="封面图">
          <el-upload
            class="article-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleImageSuccess"
            :before-upload="beforeImageUpload"
          >
            <img v-if="articleForm.imageUrls && articleForm.imageUrls[0]" 
                 :src="articleForm.imageUrls[0]" 
                 class="article-image">
            <i v-else class="el-icon-plus article-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input
            type="textarea"
            v-model="articleForm.content"
            :rows="15"
            placeholder="请输入文章内容"
          />
        </el-form-item>
      </el-form>
      
      <!-- 添加对话框底部按钮 -->
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleDialogClose">取 消</el-button>
        <el-button type="primary" @click="submitForm">发 布</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { 
  searchArticles,
  getArticle,
  createArticle,
  updateArticle,
  deleteArticle
} from '@/api/article'
import { formatDate } from '@/utils/date'

export default {
  name: 'ArticleManagement',
  data() {
    return {
      loading: false,
      searchQuery: '',
      categoryFilter: '',
      statusFilter: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      articles: [],
      dialogVisible: false,
      dialogTitle: '',
      defaultImageUrl: 'https://picsum.photos/400/206',
      articleForm: {
        title: '',
        content: '',
        category: '',
        imageUrls: [this.defaultImageUrl],
        author: '',
        review_status: 'pending',
        isDeleted: false
      },
      articleRules: {
        title: [
          { required: true, message: '请输入文章标题', trigger: 'blur' },
          { min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur' }
        ],
        category: [
          { required: true, message: '请选择文章分类', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请输入文章内容', trigger: 'blur' },
          { min: 10, message: '内容不能少于 10 个字符', trigger: 'blur' }
        ]
      },
      categories: [
        { label: '心理健康', value: '心理健康' },
        { label: '情感关系', value: '情感关系' },
        { label: '个人成长', value: '个人成长' },
        { label: '职场压力', value: '职场压力' },
        { label: '家庭教育', value: '家庭教育' }
      ],
      uploadUrl: process.env.VUE_APP_BASE_API + '/api/upload/image'
    }
  },
  created() {
    this.fetchArticles()
  },
  methods: {
    async fetchArticles() {
      this.loading = true
      try {
        const res = await searchArticles(this.searchQuery, this.categoryFilter)
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
    handleCreate() {
      this.dialogTitle = '发布文章'
      this.initForm()
      this.dialogVisible = true
    },
    initForm() {
      this.articleForm = {
        title: '',
        content: '',
        category: '',
        imageUrls: [this.defaultImageUrl],
        author: this.$store.state.user.username,
        review_status: 'pending',
        isDeleted: false
      }
    },
    async handleEdit(article) {
      this.dialogTitle = '编辑文章'
      try {
        const res = await getArticle(article.id)
        this.articleForm = res.data
        this.dialogVisible = true
      } catch (error) {
        this.$message.error('获取文章详情失败')
      }
    },
    handleDelete(article) {
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
      })
    },
    async submitForm() {
      this.$refs.articleForm.validate(async valid => {
        if (valid) {
          try {
            const articleData = {
              ...this.articleForm,
              createTime: new Date(),
              updateTime: new Date()
            }
            
            if (this.articleForm.id) {
              await updateArticle(this.articleForm.id, articleData)
              this.$message.success('更新成功')
            } else {
              await createArticle(articleData)
              this.$message.success('发布成功，等待审核')
            }
            
            this.dialogVisible = false
            this.fetchArticles()
          } catch (error) {
            this.$message.error('操作失败：' + error.message)
          }
        }
      })
    },
    handleDialogClose() {
      this.$confirm('确认关闭？未保存的内容将会丢失', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dialogVisible = false
        this.$refs.articleForm.resetFields()
      })
    },
    handleImageSuccess(res) {
      this.articleForm.imageUrls = [res.data.url]
    },
    beforeImageUpload(file) {
      const isImage = file.type.startsWith('image/')
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isImage) {
        this.$message.error('只能上传图片文件!')
      }
      if (!isLt2M) {
        this.$message.error('图片大小不能超过 2MB!')
      }
      return isImage && isLt2M
    },
    getStatusType(status) {
      const types = {
        published: 'success',
        pending: 'warning',
      }
      return types[status] || 'info'
    },
    getStatusText(status) {
      const texts = {
        published: '已发布',
        pending: '待审核',
      }
      return texts[status] || status
    },
    getExcerpt(content) {
      return content ? content.substring(0, 200) + '...' : ''
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
  justify-content: space-between;
  margin-bottom: 20px;
}

.right-filters {
  display: flex;
  gap: 10px;
}

.filter-item {
  width: 200px;
}

.article-card {
  margin-bottom: 20px;
}

.article-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.article-title {
  margin: 0;
  cursor: pointer;
  color: #303133;
}

.article-title:hover {
  color: var(--primary-color);
}

.article-preview {
  display: flex;
  gap: 20px;
}

.article-cover {
  width: 200px;
  height: 120px;
  overflow: hidden;
  border-radius: 4px;
}

.article-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.article-content {
  flex: 1;
}

.article-excerpt {
  color: #666;
  margin: 0 0 10px;
  line-height: 1.6;
}

.article-meta {
  color: #999;
  font-size: 13px;
  display: flex;
  gap: 20px;
}

.article-meta i {
  margin-right: 5px;
}

.article-actions {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #eee;
  text-align: right;
}

.article-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}

.article-uploader:hover {
  border-color: var(--primary-color);
}

.article-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.article-image {
  width: 178px;
  height: 178px;
  display: block;
  object-fit: cover;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style> 