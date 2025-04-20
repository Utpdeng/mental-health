<template>
  <div class="admin-tests-page">
    <div class="page-header">
      <div class="container">
        <h1>心理测评审核</h1>
        <p>审核医生提交的心理测评题目</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="filter-bar">
          <el-form :inline="true" :model="filterForm" class="filter-form">
            <el-form-item label="状态">
              <el-select v-model="filterForm.status" placeholder="请选择状态" clearable>
                <el-option label="待审核" value="pending"></el-option>
                <el-option label="已通过" value="approved"></el-option>
                <el-option label="已拒绝" value="rejected"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="医生">
              <el-input 
                v-model="filterForm.doctor" 
                placeholder="请输入医生姓名"
                clearable>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="resetFilter">重置</el-button>
            </el-form-item>
          </el-form>
        </div>

        <el-table :data="tests" style="width: 100%" v-loading="loading">
          <el-table-column prop="title" label="测评标题" min-width="200">
            <template slot-scope="scope">
              <span>{{ scope.row.title }}</span>
              <el-tag size="mini" :type="getStatusType(scope.row.status)" class="ml-2">
                {{ getStatusText(scope.row.status) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="doctor" label="提交医生" width="120"></el-table-column>
          <el-table-column prop="description" label="测评描述" min-width="300"></el-table-column>
          <el-table-column prop="questions" label="题目数量" width="100"></el-table-column>
          <el-table-column prop="duration" label="预计时长(分钟)" width="120"></el-table-column>
          <el-table-column prop="createTime" label="提交时间" width="180"></el-table-column>
          <el-table-column label="操作" width="200" fixed="right">
            <template slot-scope="scope">
              <el-button 
                size="mini" 
                type="primary" 
                @click="reviewTest(scope.row)"
                v-if="scope.row.status === 'pending'">
                审核
              </el-button>
              <el-button 
                size="mini" 
                type="info" 
                @click="viewTest(scope.row)">
                查看
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="pagination-container">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pagination.currentPage"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pagination.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pagination.total">
          </el-pagination>
        </div>
      </div>
    </div>

    <!-- 审核对话框 -->
    <el-dialog 
      :title="'审核测评 - ' + currentTest.title" 
      :visible.sync="reviewDialogVisible"
      width="70%"
      :close-on-click-modal="false">
      <div class="test-review">
        <div class="test-info">
          <h3>测评信息</h3>
          <p><strong>标题：</strong>{{ currentTest.title }}</p>
          <p><strong>描述：</strong>{{ currentTest.description }}</p>
          <p><strong>预计时长：</strong>{{ currentTest.duration }}分钟</p>
          <p><strong>提交医生：</strong>{{ currentTest.doctor }}</p>
          <p><strong>提交时间：</strong>{{ currentTest.createTime }}</p>
        </div>

        <el-divider>题目列表</el-divider>

        <div class="questions-list">
          <div v-for="(question, index) in currentTest.questions" :key="index" class="question-item">
            <el-card class="question-card">
              <div class="question-header">
                <span class="question-number">题目 {{ index + 1 }}</span>
              </div>
              <div class="question-content">
                <p><strong>题目内容：</strong>{{ question.content }}</p>
                <div class="options-list">
                  <p><strong>选项：</strong></p>
                  <div v-for="(option, oIndex) in question.options" :key="oIndex" class="option-item">
                    <span class="option-label">{{ getOptionLabel(oIndex) }}</span>
                    <span class="option-text">{{ option.text }}</span>
                    <span class="option-score">(分值: {{ option.score }})</span>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
        </div>

        <el-divider>审核意见</el-divider>

        <el-form :model="reviewForm" :rules="reviewRules" ref="reviewForm" label-width="100px">
          <el-form-item label="审核结果" prop="status">
            <el-radio-group v-model="reviewForm.status">
              <el-radio label="approved">通过</el-radio>
              <el-radio label="rejected">拒绝</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="审核意见" prop="comment">
            <el-input 
              type="textarea" 
              v-model="reviewForm.comment" 
              :rows="3"
              placeholder="请输入审核意见">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="reviewDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitReview">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAdminTests, reviewTest } from '@/api/test'

export default {
  name: 'AdminTests',
  data() {
    return {
      loading: false,
      tests: [],
      filterForm: {
        status: '',
        doctor: ''
      },
      pagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      reviewDialogVisible: false,
      currentTest: {
        id: '',
        title: '',
        description: '',
        duration: 0,
        doctor: '',
        createTime: '',
        questions: []
      },
      reviewForm: {
        status: '',
        comment: ''
      },
      reviewRules: {
        status: [
          { required: true, message: '请选择审核结果', trigger: 'change' }
        ],
        comment: [
          { required: true, message: '请输入审核意见', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchTests()
  },
  methods: {
    async fetchTests() {
      this.loading = true
      try {
        const params = {
          ...this.filterForm,
          page: this.pagination.currentPage,
          size: this.pagination.pageSize
        }
        const res = await getAdminTests(params)
        this.tests = res.data.list
        this.pagination.total = res.data.total
      } catch (error) {
        console.error('获取测试列表失败，使用模拟数据:', error)
        // 使用模拟数据
        this.tests = [
          {
            id: '1',
            title: '情绪健康测评',
            description: '全面评估您的情绪状态，了解自己的情绪健康水平',
            doctor: '张医生',
            questions: 10,
            duration: 15,
            status: 'pending',
            createTime: '2023-05-10 14:30:00',
            questions: [
              {
                content: '在过去的一周里，您是否经常感到情绪低落？',
                options: [
                  { text: '从不', score: 0 },
                  { text: '偶尔', score: 1 },
                  { text: '经常', score: 2 },
                  { text: '总是', score: 3 }
                ]
              },
              {
                content: '您是否容易感到焦虑或紧张？',
                options: [
                  { text: '从不', score: 0 },
                  { text: '偶尔', score: 1 },
                  { text: '经常', score: 2 },
                  { text: '总是', score: 3 }
                ]
              }
            ]
          },
          {
            id: '2',
            title: '压力指数测试',
            description: '测量您当前的压力水平，找到压力源',
            doctor: '李医生',
            questions: 8,
            duration: 10,
            status: 'approved',
            createTime: '2023-05-15 09:20:00',
            questions: [
              {
                content: '您是否经常感到工作压力大？',
                options: [
                  { text: '从不', score: 0 },
                  { text: '偶尔', score: 1 },
                  { text: '经常', score: 2 },
                  { text: '总是', score: 3 }
                ]
              }
            ]
          }
        ]
        this.pagination.total = this.tests.length
      } finally {
        this.loading = false
      }
    },
    handleSearch() {
      this.pagination.currentPage = 1
      this.fetchTests()
    },
    resetFilter() {
      this.filterForm = {
        status: '',
        doctor: ''
      }
      this.handleSearch()
    },
    handleSizeChange(val) {
      this.pagination.pageSize = val
      this.fetchTests()
    },
    handleCurrentChange(val) {
      this.pagination.currentPage = val
      this.fetchTests()
    },
    reviewTest(test) {
      this.currentTest = { ...test }
      this.reviewForm = {
        status: '',
        comment: ''
      }
      this.reviewDialogVisible = true
    },
    viewTest(test) {
      this.currentTest = { ...test }
      this.reviewDialogVisible = true
    },
    getOptionLabel(index) {
      return String.fromCharCode(65 + index) + '.'
    },
    getStatusType(status) {
      const types = {
        'pending': 'warning',
        'approved': 'success',
        'rejected': 'danger'
      }
      return types[status] || 'info'
    },
    getStatusText(status) {
      const texts = {
        'pending': '待审核',
        'approved': '已通过',
        'rejected': '已拒绝'
      }
      return texts[status] || '未知'
    },
    async submitReview() {
      try {
        await this.$refs.reviewForm.validate()
        await reviewTest(this.currentTest.id, this.reviewForm)
        
        const index = this.tests.findIndex(t => t.id === this.currentTest.id)
        if (index !== -1) {
          this.tests[index].status = this.reviewForm.status
        }
        
        this.reviewDialogVisible = false
        this.$message.success('审核提交成功')
      } catch (error) {
        if (error !== 'cancel') {
          console.error('提交审核失败:', error)
          this.$message.error('提交审核失败')
        }
      }
    }
  }
}
</script>

<style scoped>
.page-header {
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  padding: 40px 0;
  margin-bottom: 30px;
}

.filter-bar {
  margin-bottom: 20px;
}

.test-review {
  max-height: 60vh;
  overflow-y: auto;
}

.test-info {
  margin-bottom: 20px;
}

.test-info p {
  margin: 5px 0;
}

.questions-list {
  margin: 20px 0;
}

.question-item {
  margin-bottom: 20px;
}

.question-header {
  margin-bottom: 15px;
}

.question-number {
  font-weight: bold;
}

.options-list {
  margin-top: 10px;
}

.option-item {
  display: flex;
  align-items: center;
  margin: 5px 0;
}

.option-label {
  width: 30px;
  font-weight: bold;
}

.option-text {
  flex: 1;
}

.option-score {
  color: #666;
  margin-left: 10px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}

.ml-2 {
  margin-left: 8px;
}
</style>
