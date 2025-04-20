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
            <div class="score-section">
              <div class="score-card">
                <div class="score-value">{{ calculateResult() }}</div>
                <div class="score-label">总分</div>
              </div>
              <div class="score-description">
                <h3>分数解读</h3>
                <p>{{ getScoreInterpretation() }}</p>
              </div>
            </div>

            <div class="dimension-analysis">
              <h3>维度分析</h3>
              <div class="dimension-list">
                <div v-for="(dimension, index) in getDimensionScores()" :key="index" class="dimension-item">
                  <div class="dimension-header">
                    <span class="dimension-name">{{ dimension.name }}</span>
                    <span class="dimension-score">{{ dimension.score }}分</span>
                  </div>
                  <el-progress 
                    :percentage="dimension.percentage" 
                    :status="getDimensionStatus(dimension.percentage)"
                    :format="dimensionFormat">
                  </el-progress>
                  <p class="dimension-desc">{{ dimension.description }}</p>
                </div>
              </div>
            </div>

            <div class="suggestions">
              <h3>个性化建议</h3>
              <div class="suggestion-list">
                <div v-for="(suggestion, index) in getSuggestions()" :key="index" class="suggestion-item">
                  <i class="el-icon-arrow-right"></i>
                  <span>{{ suggestion }}</span>
                </div>
              </div>
            </div>

            <div class="next-steps">
              <h3>后续行动建议</h3>
              <div class="step-list">
                <div v-for="(step, index) in getNextSteps()" :key="index" class="step-item">
                  <div class="step-number">{{ index + 1 }}</div>
                  <div class="step-content">
                    <h4>{{ step.title }}</h4>
                    <p>{{ step.description }}</p>
                  </div>
                </div>
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
    getScoreInterpretation() {
      const score = this.calculateResult()
      if (score <= 10) {
        return '您的情绪状态良好，继续保持健康的生活方式。'
      } else if (score <= 20) {
        return '您存在轻微的情绪困扰，建议关注情绪变化，适当调整生活方式。'
      } else if (score <= 30) {
        return '您的情绪状态需要关注，建议采取积极措施改善情绪，必要时寻求专业帮助。'
      } else {
        return '您的情绪状态需要重视，建议尽快寻求专业心理医生的帮助。'
      }
    },
    getDimensionScores() {
      const totalScore = this.calculateResult()
      return [
        {
          name: '情绪稳定性',
          score: Math.floor(totalScore * 0.4),
          percentage: Math.floor((totalScore * 0.4) / 12 * 100),
          description: '评估您情绪的稳定程度和调节能力'
        },
        {
          name: '压力水平',
          score: Math.floor(totalScore * 0.3),
          percentage: Math.floor((totalScore * 0.3) / 9 * 100),
          description: '反映您当前的压力状态和应对能力'
        },
        {
          name: '心理健康',
          score: Math.floor(totalScore * 0.3),
          percentage: Math.floor((totalScore * 0.3) / 9 * 100),
          description: '评估您的整体心理健康状况'
        }
      ]
    },
    getDimensionStatus(percentage) {
      if (percentage <= 30) {
        return 'success'
      } else if (percentage <= 60) {
        return 'warning'
      } else {
        return 'exception'
      }
    },
    dimensionFormat(percentage) {
      return `${percentage}%`
    },
    getSuggestions() {
      const score = this.calculateResult()
      if (score <= 10) {
        return [
          '继续保持健康的生活方式',
          '定期进行情绪自检',
          '培养积极的兴趣爱好',
          '保持规律的作息时间',
          '与家人朋友保持良好沟通'
        ]
      } else if (score <= 20) {
        return [
          '每天进行10-15分钟的深呼吸练习',
          '保持规律的作息时间，确保充足的睡眠',
          '每周进行3-4次有氧运动，每次30分钟',
          '培养积极的兴趣爱好，转移注意力',
          '与家人朋友保持良好沟通，分享感受'
        ]
      } else if (score <= 30) {
        return [
          '学习并实践放松技巧，如冥想、深呼吸等',
          '建立健康的生活作息，保证充足睡眠',
          '适当减少工作压力，学会说"不"',
          '寻求专业心理咨询师的帮助',
          '参加心理健康相关的课程或讲座'
        ]
      } else {
        return [
          '立即预约专业心理医生进行咨询',
          '在医生指导下进行必要的治疗',
          '建立健康的生活作息，保证充足睡眠',
          '避免独处，保持与家人朋友的联系',
          '学习并实践放松技巧，如冥想、深呼吸等'
        ]
      }
    },
    getNextSteps() {
      const score = this.calculateResult()
      if (score <= 10) {
        return [
          {
            title: '保持健康习惯',
            description: '继续维持现有的健康生活方式，保持良好的作息和饮食习惯'
          },
          {
            title: '定期情绪自检',
            description: '每周进行一次情绪状态评估，及时发现并处理情绪问题'
          },
          {
            title: '培养兴趣爱好',
            description: '发展新的兴趣爱好，丰富生活内容，提升生活质量'
          }
        ]
      } else if (score <= 20) {
        return [
          {
            title: '建立健康作息',
            description: '制定合理的作息时间表，确保每天7-8小时的睡眠时间'
          },
          {
            title: '开始运动计划',
            description: '选择一项喜欢的运动，每周坚持3-4次，每次30分钟'
          },
          {
            title: '学习放松技巧',
            description: '通过冥想、深呼吸等方式，学习有效的放松方法'
          }
        ]
      } else if (score <= 30) {
        return [
          {
            title: '寻求专业帮助',
            description: '预约心理咨询师，进行专业的心理评估和指导'
          },
          {
            title: '调整生活方式',
            description: '重新规划工作和生活，适当减少压力源'
          },
          {
            title: '建立支持系统',
            description: '与家人朋友沟通，建立情感支持网络'
          }
        ]
      } else {
        return [
          {
            title: '立即就医',
            description: '尽快预约专业心理医生，进行全面的评估和治疗'
          },
          {
            title: '建立安全计划',
            description: '与家人一起制定安全计划，确保在需要时能及时获得帮助'
          },
          {
            title: '配合治疗',
            description: '严格按照医生的建议进行治疗，定期复诊'
          }
        ]
      }
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

.score-section {
  display: flex;
  align-items: center;
  gap: 30px;
  margin-bottom: 40px;
  padding: 30px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.score-card {
  text-align: center;
  padding: 20px;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  border-radius: 8px;
  min-width: 120px;
}

.score-value {
  font-size: 36px;
  font-weight: bold;
  margin-bottom: 5px;
}

.score-label {
  font-size: 14px;
}

.score-description {
  flex: 1;
}

.score-description h3 {
  margin-bottom: 10px;
  color: #333;
}

.dimension-analysis {
  margin-bottom: 40px;
  padding: 30px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.dimension-analysis h3 {
  margin-bottom: 20px;
  color: #333;
}

.dimension-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.dimension-item {
  padding: 15px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

.dimension-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.dimension-name {
  font-weight: bold;
  color: #333;
}

.dimension-score {
  color: #409eff;
}

.dimension-desc {
  margin-top: 10px;
  color: #666;
  font-size: 14px;
}

.suggestions {
  margin-bottom: 40px;
  padding: 30px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.suggestions h3 {
  margin-bottom: 20px;
  color: #333;
}

.suggestion-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.suggestion-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 4px;
}

.suggestion-item i {
  color: #409eff;
}

.next-steps {
  margin-bottom: 40px;
  padding: 30px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.next-steps h3 {
  margin-bottom: 20px;
  color: #333;
}

.step-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.step-item {
  display: flex;
  gap: 20px;
  padding: 20px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

.step-number {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  border-radius: 50%;
  font-weight: bold;
}

.step-content h4 {
  margin-bottom: 10px;
  color: #333;
}

.step-content p {
  color: #666;
  line-height: 1.6;
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