<template>
  <div class="test-page">
    <div class="page-header">
      <div class="container">
        <h1>{{ testTitle }}</h1>
        <p>请认真阅读每个问题，选择最符合您当前情况的选项</p>
      </div>
    </div>

    <div class="section" v-if="!isFinished">
      <div class="container">
        <div class="test-progress">
          <el-progress :percentage="progressPercentage" :text-inside="true" :stroke-width="18"></el-progress>
          <div class="progress-text">
            <span>题目 {{ currentIndex + 1 }}/{{ questions.length }}</span>
            <span>已用时间: {{ formatTime(usedTime) }}</span>
          </div>
        </div>

        <div class="question-card">
          <div class="question-content">
            <h2>{{ currentQuestion.content }}</h2>
            <el-radio-group v-model="currentAnswer" class="answer-options">
              <el-radio 
                v-for="(option, index) in currentQuestion.options" 
                :key="index" 
                :label="index"
                class="answer-option">
                {{ option.text }}
              </el-radio>
            </el-radio-group>
          </div>

          <div class="question-actions">
            <el-button @click="prevQuestion" :disabled="currentIndex === 0">上一题</el-button>
            <el-button type="primary" @click="nextQuestion" :disabled="currentAnswer === null">
              {{ currentIndex === questions.length - 1 ? '完成测评' : '下一题' }}
            </el-button>
          </div>
        </div>
      </div>
    </div>

    <div class="section" v-else>
      <div class="container">
        <div class="result-card">
          <div class="result-header">
            <i class="fas fa-check-circle"></i>
            <h2>测评完成</h2>
          </div>
          
          <div class="result-content">
            <p>恭喜您完成了{{ testTitle }}！</p>
            <p>您的测评结果将由专业人员分析后呈现。</p>
            
            <div class="result-summary">
              <div class="summary-item">
                <span class="summary-label">完成时间</span>
                <span class="summary-value">{{ formatTime(usedTime) }}</span>
              </div>
              <div class="summary-item">
                <span class="summary-label">题目数量</span>
                <span class="summary-value">{{ questions.length }}</span>
              </div>
            </div>

            <div class="result-note">
              <p>注意：本测评结果仅供参考，不能替代专业医生的诊断。如有任何疑问，请咨询专业心理医生。</p>
            </div>
          </div>
          
          <div class="result-actions">
            <el-button type="primary" @click="goToAssessment">返回测评列表</el-button>
            <el-button type="success" @click="goToExperts">咨询专家</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AssessmentTest',
  data() {
    return {
      testId: null,
      testTitle: '',
      questions: [],
      answers: [],
      currentIndex: 0,
      currentAnswer: null,
      startTime: null,
      usedTime: 0,
      timer: null,
      isFinished: false,
      
      // 测试用题库，实际项目中应该从后端API获取
      questionsBank: {
        1: { // 情绪健康测评
          title: '情绪健康测评',
          questions: [
            {
              content: '在过去的两周里，您是否经常感到情绪低落、沮丧或绝望？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '在过去的两周里，您是否对做事情失去了兴趣或乐趣？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '在过去的两周里，您是否有睡眠问题（难以入睡、半夜醒来或睡眠过多）？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '在过去的两周里，您是否感到疲倦或精力不足？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '在过去的两周里，您是否食欲不振或过度饮食？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '在过去的两周里，您是否对自己感到不满意或认为自己是个失败者，或让自己或家人失望？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '在过去的两周里，您是否难以集中注意力，例如阅读报纸或看电视？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '在过去的两周里，您说话或行动是否变得缓慢以至于被他人注意到？或相反，您是否比平常更加坐立不安或烦躁？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '在过去的两周里，您是否曾经想过自己最好已经死亡或以某种方式伤害自己？',
              options: [
                { text: '完全没有', score: 0 },
                { text: '有几天', score: 1 },
                { text: '一半以上时间', score: 2 },
                { text: '几乎每天', score: 3 }
              ]
            },
            {
              content: '以上这些问题对您的工作、照顾家庭或与他人相处有多大困难？',
              options: [
                { text: '没有困难', score: 0 },
                { text: '有些困难', score: 1 },
                { text: '很大困难', score: 2 },
                { text: '极端困难', score: 3 }
              ]
            }
          ]
        },
        2: { // 压力指数测试
          title: '压力指数测试',
          questions: [
            {
              content: '您经常感到时间不够用吗？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '您经常为工作或生活中的小事烦恼吗？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '您是否经常感到精力不足，无法完成日常任务？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '在面对压力时，您是否容易出现头痛、胃痛等身体不适？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '您经常感到焦虑或紧张吗？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '您是否常常无法入睡或睡眠质量差？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '您经常对未来感到担忧吗？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '您是否经常感到难以应对生活中的变化？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            }
          ]
        },
        3: { // 人格特质分析
          title: '人格特质分析',
          questions: [
            {
              content: '您是否喜欢参加社交活动并结交新朋友？',
              options: [
                { text: '非常不符合', score: 0 },
                { text: '不太符合', score: 1 },
                { text: '有点符合', score: 2 },
                { text: '非常符合', score: 3 }
              ]
            },
            {
              content: '在团队中，您更倾向于担任领导角色还是支持角色？',
              options: [
                { text: '始终是支持角色', score: 0 },
                { text: '通常是支持角色', score: 1 },
                { text: '通常是领导角色', score: 2 },
                { text: '始终是领导角色', score: 3 }
              ]
            },
            {
              content: '您更喜欢按计划行事还是随机应变？',
              options: [
                { text: '一定要按计划行事', score: 0 },
                { text: '偏好按计划行事', score: 1 },
                { text: '偏好随机应变', score: 2 },
                { text: '完全随机应变', score: 3 }
              ]
            },
            {
              content: '您是否经常根据直觉做决定？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '面对困难时，您是否容易感到沮丧和放弃？',
              options: [
                { text: '从不', score: 0 },
                { text: '偶尔', score: 1 },
                { text: '经常', score: 2 },
                { text: '总是', score: 3 }
              ]
            },
            {
              content: '您是否善于处理细节性工作？',
              options: [
                { text: '完全不擅长', score: 0 },
                { text: '不太擅长', score: 1 },
                { text: '比较擅长', score: 2 },
                { text: '非常擅长', score: 3 }
              ]
            },
            {
              content: '您是否喜欢尝试新事物？',
              options: [
                { text: '非常不喜欢', score: 0 },
                { text: '不太喜欢', score: 1 },
                { text: '比较喜欢', score: 2 },
                { text: '非常喜欢', score: 3 }
              ]
            },
            {
              content: '您是否经常关心他人的感受？',
              options: [
                { text: '很少关心', score: 0 },
                { text: '有时关心', score: 1 },
                { text: '经常关心', score: 2 },
                { text: '总是关心', score: 3 }
              ]
            }
          ]
        },
        // 其他测试题目可以继续添加...
      }
    }
  },
  computed: {
    currentQuestion() {
      return this.questions[this.currentIndex] || {}
    },
    progressPercentage() {
      return Math.round((this.currentIndex + 1) / this.questions.length * 100)
    }
  },
  created() {
    this.testId = this.$route.params.id
    this.testTitle = this.$route.query.title || '心理测评'
    this.initTest()
    this.startTimer()
  },
  beforeDestroy() {
    this.stopTimer()
  },
  methods: {
    initTest() {
      // 在实际项目中，这里应该通过API获取测试题目
      // 此处为模拟数据
      const testData = this.questionsBank[this.testId]
      if (testData) {
        this.questions = testData.questions
        this.testTitle = testData.title
        // 初始化答案数组
        this.answers = Array(this.questions.length).fill(null)
      } else {
        this.$message.error('未找到测试题目')
        this.goToAssessment()
      }
    },
    startTimer() {
      this.startTime = Date.now()
      this.timer = setInterval(() => {
        this.usedTime = Math.floor((Date.now() - this.startTime) / 1000)
      }, 1000)
    },
    stopTimer() {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
      }
    },
    formatTime(seconds) {
      const minutes = Math.floor(seconds / 60)
      const remainingSeconds = seconds % 60
      return `${minutes}分${remainingSeconds}秒`
    },
    nextQuestion() {
      // 保存当前答案
      this.answers[this.currentIndex] = this.currentAnswer
      
      if (this.currentIndex < this.questions.length - 1) {
        this.currentIndex++
        this.currentAnswer = this.answers[this.currentIndex]
      } else {
        // 已完成所有题目
        this.finishTest()
      }
    },
    prevQuestion() {
      if (this.currentIndex > 0) {
        // 保存当前答案
        this.answers[this.currentIndex] = this.currentAnswer
        
        this.currentIndex--
        this.currentAnswer = this.answers[this.currentIndex]
      }
    },
    finishTest() {
      this.stopTimer()
      this.isFinished = true
      
      // 这里可以向后端提交测试答案并获取结果
      // 实际项目中，这里应该调用API
      // const result = this.calculateResult()
      // this.submitTestResult(result)
    },
    calculateResult() {
      // 简单计算得分，实际中可能更复杂
      let totalScore = 0
      this.answers.forEach((answer, index) => {
        if (answer !== null && this.questions[index] && this.questions[index].options[answer]) {
          totalScore += this.questions[index].options[answer].score
        }
      })
      return totalScore
    },
    goToAssessment() {
      this.$router.push('/assessment')
    },
    goToExperts() {
      this.$router.push('/experts')
    }
  }
}
</script>

<style scoped>
.page-header {
  margin-top: 10px;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  padding: 40px 0;
  text-align: center;
}

.test-progress {
  margin-bottom: 20px;
}

.progress-text {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  color: #666;
}

.question-card {
  background: white;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.question-content {
  margin-bottom: 30px;
}

.question-content h2 {
  margin-bottom: 20px;
  color: #333;
}

.answer-options {
  display: flex;
  flex-direction: column;
}

.answer-option {
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.answer-option:hover {
  background-color: #f9f9f9;
}

.question-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.result-card {
  background: white;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.result-header {
  margin-bottom: 30px;
}

.result-header i {
  font-size: 60px;
  color: #67c23a;
  margin-bottom: 20px;
}

.result-content {
  margin-bottom: 30px;
}

.result-summary {
  display: flex;
  justify-content: center;
  gap: 40px;
  margin: 30px 0;
}

.summary-item {
  display: flex;
  flex-direction: column;
}

.summary-label {
  font-size: 14px;
  color: #666;
}

.summary-value {
  font-size: 24px;
  font-weight: bold;
  color: var(--primary-color);
}

.result-note {
  font-size: 14px;
  color: #666;
  margin-top: 20px;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 5px;
}

.result-actions {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

/* 响应式样式 */
@media (max-width: 768px) {
  .question-card {
    padding: 20px;
  }
  
  .result-summary {
    flex-direction: column;
    gap: 15px;
  }
}
</style> 