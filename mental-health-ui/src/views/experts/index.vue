<!-- 将之前的 Experts.vue 内容移到这里 --> <template>
  <div class="experts-page">
    <div class="page-header">
      <div class="container">
        <h1>专家指导</h1>
        <p>专业的心理咨询团队，助您解决心理困扰</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <el-row :gutter="30">
          <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="expert in experts" :key="expert.id">
            <div class="expert-card">
              <div class="expert-avatar">
                <img :src="expert.avatar" :alt="expert.name">
              </div>
              <h3>{{ expert.name }}</h3>
              <p class="expert-title">{{ expert.title }}</p>
              <p class="expert-specialty">擅长领域：{{ expert.specialty }}</p>
              <div class="expert-stats">
                <div class="stat-item">
                  <span class="stat-number">{{ expert.experience }}年</span>
                  <span class="stat-label">从业经验</span>
                </div>
                <div class="stat-item">
                  <span class="stat-number">{{ expert.cases }}+</span>
                  <span class="stat-label">服务案例</span>
                </div>
              </div>
              <el-button type="primary" @click="bookConsultation(expert)">预约咨询</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Experts',
  data() {
    return {
      experts: [
        {
          id: 1,
          name: '张医生',
          title: '资深心理咨询师',
          specialty: '情感关系、职场压力',
          experience: 15,
          cases: 2000,
          avatar: 'https://picsum.photos/400/2012'
        },
        {
          id: 2,
          name: '李教授',
          title: '临床心理学专家',
          specialty: '抑郁症、焦虑障碍',
          experience: 20,
          cases: 3000,
          avatar: 'https://picsum.photos/400/2013'
        },
        {
          id: 3,
          name: '王医生',
          title: '青少年心理咨询师',
          specialty: '青少年心理家庭关系',
          experience: 10,
          cases: 1500,
          avatar: 'https://picsum.photos/400/2014'
        },
        {
          id: 4,
          name: '陈医生',
          title: '婚姻家庭咨询师',
          specialty: '婚姻关系、亲子教育',
          experience: 12,
          cases: 1800,
          avatar: 'https://picsum.photos/400/2017'
        }
      ]
    }
  },
  methods: {
    bookConsultation(expert) {
      this.$message.success(`预约${expert.name}咨询成功！我们会尽快与您联系。`)
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

.expert-card {
  background: white;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  transition: transform 0.3s ease;
}

.expert-card:hover {
  transform: translateY(-5px);
}

.expert-avatar {
  width: 120px;
  height: 120px;
  margin: 0 auto 20px;
  border-radius: 50%;
  overflow: hidden;
}

.expert-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.expert-title {
  color: var(--primary-color);
  margin: 10px 0;
}

.expert-specialty {
  color: #666;
  margin-bottom: 15px;
}

.expert-stats {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin: 15px 0;
  padding: 15px 0;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
}

.stat-item {
  display: flex;
  flex-direction: column;
}

.stat-number {
  font-size: 1.2rem;
  font-weight: bold;
  color: var(--primary-color);
}

.stat-label {
  font-size: 0.9rem;
  color: #666;
}

.el-button {
  width: 100%;
  margin-top: 15px;
}
</style> 