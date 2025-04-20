<template>
  <div class="doctor-tests-page">
    <div class="page-header">
      <div class="container">
        <h1>心理测评管理</h1>
        <p>管理您发布的心理测评题目</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="action-bar">
          <el-button type="primary" @click="showCreateDialog">发布新测评</el-button>
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
          <el-table-column prop="description" label="测评描述" min-width="300"></el-table-column>
          <el-table-column prop="questions" label="题目数量" width="100"></el-table-column>
          <el-table-column prop="duration" label="预计时长(分钟)" width="120"></el-table-column>
          <el-table-column prop="createTime" label="发布时间" width="180"></el-table-column>
          <el-table-column label="操作" width="200" fixed="right">
            <template slot-scope="scope">
              <el-button 
                size="mini" 
                type="primary" 
                @click="editTest(scope.row)"
                :disabled="scope.row.status === 'approved'">
                编辑
              </el-button>
              <el-button 
                size="mini" 
                type="danger" 
                @click="deleteTest(scope.row)"
                :disabled="scope.row.status === 'approved'">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 创建/编辑测评对话框 -->
    <el-dialog 
      :title="dialogTitle" 
      :visible.sync="dialogVisible"
      width="70%"
      :close-on-click-modal="false">
      <el-form :model="testForm" :rules="rules" ref="testForm" label-width="100px">
        <el-form-item label="测评标题" prop="title">
          <el-input v-model="testForm.title" placeholder="请输入测评标题"></el-input>
        </el-form-item>
        <el-form-item label="测评描述" prop="description">
          <el-input 
            type="textarea" 
            v-model="testForm.description" 
            :rows="3"
            placeholder="请输入测评描述">
          </el-input>
        </el-form-item>
        <el-form-item label="预计时长" prop="duration">
          <el-input-number 
            v-model="testForm.duration" 
            :min="5" 
            :max="120"
            :step="5">
          </el-input-number>
          <span class="ml-2">分钟</span>
        </el-form-item>
        
        <el-divider>题目设置</el-divider>
        
        <div class="questions-list">
          <div v-for="(question, index) in testForm.questions" :key="index" class="question-item">
            <el-card class="question-card">
              <div class="question-header">
                <span class="question-number">题目 {{ index + 1 }}</span>
                <el-button 
                  type="text" 
                  class="delete-question" 
                  @click="removeQuestion(index)"
                  v-if="testForm.questions.length > 1">
                  删除
                </el-button>
              </div>
              <el-form-item 
                :label="'题目内容'" 
                :prop="'questions.' + index + '.content'"
                :rules="{ required: true, message: '请输入题目内容', trigger: 'blur' }">
                <el-input 
                  type="textarea" 
                  v-model="question.content" 
                  :rows="2"
                  placeholder="请输入题目内容">
                </el-input>
              </el-form-item>
              <el-form-item 
                label="选项设置" 
                :prop="'questions.' + index + '.options'"
                :rules="{ required: true, message: '请设置选项', trigger: 'blur' }">
                <div class="options-list">
                  <div v-for="(option, oIndex) in question.options" :key="oIndex" class="option-item">
                    <el-input 
                      v-model="option.text" 
                      placeholder="请输入选项内容"
                      class="option-input">
                      <template slot="prepend">{{ getOptionLabel(oIndex) }}</template>
                    </el-input>
                    <el-input-number 
                      v-model="option.score" 
                      :min="0" 
                      :max="10"
                      class="option-score">
                    </el-input-number>
                  </div>
                </div>
              </el-form-item>
            </el-card>
          </div>
        </div>
        
        <div class="add-question">
          <el-button type="text" @click="addQuestion">
            <i class="el-icon-plus"></i> 添加题目
          </el-button>
        </div>
      </el-form>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitTest">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getDoctorTests, createTest, updateTest, deleteTest } from '@/api/test'

export default {
  name: 'DoctorTests',
  data() {
    return {
      loading: false,
      tests: [],
      dialogVisible: false,
      dialogTitle: '发布新测评',
      testForm: {
        id: null,
        title: '',
        description: '',
        duration: 30,
        questions: [
          {
            content: '',
            options: [
              { text: '', score: 0 },
              { text: '', score: 1 },
              { text: '', score: 2 },
              { text: '', score: 3 }
            ]
          }
        ]
      },
      rules: {
        title: [
          { required: true, message: '请输入测评标题', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入测评描述', trigger: 'blur' },
          { min: 10, max: 500, message: '长度在 10 到 500 个字符', trigger: 'blur' }
        ],
        duration: [
          { required: true, message: '请设置预计时长', trigger: 'blur' }
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
        const res = await getDoctorTests()
        this.tests = res.data
      } catch (error) {
        console.error('获取测试列表失败，使用模拟数据:', error)
        // 使用模拟数据
        this.tests = [
          {
            id: '1',
            title: '情绪健康测评',
            description: '全面评估您的情绪状态，了解自己的情绪健康水平',
            questions: 10,
            duration: 15,
            status: 'approved',
            createTime: '2025-04-10 14:30:00'
          },
          {
            id: '2',
            title: '压力指数测试',
            description: '测量您当前的压力水平，找到压力源',
            questions: 8,
            duration: 10,
            status: 'pending',
            createTime: '2025-04-15 09:20:00'
          },
          {
            id: '3',
            title: '人格特质分析',
            description: '了解您的性格特点，发现自己的优势',
            questions: 12,
            duration: 20,
            status: 'rejected',
            createTime: '2025-04-20 16:45:00'
          }
        ]
      } finally {
        this.loading = false
      }
    },
    showCreateDialog() {
      this.dialogTitle = '发布新测评'
      this.testForm = {
        id: null,
        title: '',
        description: '',
        duration: 30,
        questions: [
          {
            content: '',
            options: [
              { text: '', score: 0 },
              { text: '', score: 1 },
              { text: '', score: 2 },
              { text: '', score: 3 }
            ]
          }
        ]
      }
      this.dialogVisible = true
    },
    editTest(test) {
      this.dialogTitle = '编辑测评'
      this.testForm = {
        ...test,
        questions: test.questions || [
          {
            content: '',
            options: [
              { text: '', score: 0 },
              { text: '', score: 1 },
              { text: '', score: 2 },
              { text: '', score: 3 }
            ]
          }
        ]
      }
      this.dialogVisible = true
    },
    async deleteTest(test) {
      try {
        await this.$confirm('确定要删除该测评吗？', '提示', {
          type: 'warning'
        })
        await deleteTest(test.id)
        this.tests = this.tests.filter(t => t.id !== test.id)
        this.$message.success('删除成功')
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除测试失败:', error)
          this.$message.error('操作完成')
        }
      }
    },
    addQuestion() {
      this.testForm.questions.push({
        content: '',
        options: [
          { text: '', score: 0 },
          { text: '', score: 1 },
          { text: '', score: 2 },
          { text: '', score: 3 }
        ]
      })
    },
    removeQuestion(index) {
      this.testForm.questions.splice(index, 1)
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
    async submitTest() {
      try {
        await this.$refs.testForm.validate()
        
        const testData = {
          title: this.testForm.title,
          description: this.testForm.description,
          duration: this.testForm.duration,
          questions: this.testForm.questions
        }
        
        if (this.testForm.id) {
          await updateTest(this.testForm.id, testData)
          const index = this.tests.findIndex(t => t.id === this.testForm.id)
          if (index !== -1) {
            this.tests.splice(index, 1, {
              ...this.tests[index],
              ...testData,
              questions: testData.questions.length
            })
          }
        } else {
          const res = await createTest(testData)
          this.tests.unshift({
            ...res.data,
            questions: testData.questions.length
          })
        }
        
        this.dialogVisible = false
        this.$message.success('提交成功，等待管理员审核')
      } catch (error) {
        if (error !== 'cancel') {
          console.error('提交测试失败:', error)
          this.$message.error('操作完成')
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

.action-bar {
  margin-bottom: 20px;
}

.questions-list {
  margin-bottom: 20px;
}

.question-item {
  margin-bottom: 20px;
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.question-number {
  font-weight: bold;
}

.delete-question {
  color: #F56C6C;
}

.options-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.option-item {
  display: flex;
  gap: 10px;
}

.option-input {
  flex: 1;
}

.option-score {
  width: 100px;
}

.add-question {
  text-align: center;
  margin: 20px 0;
}

.ml-2 {
  margin-left: 8px;
}
</style> 