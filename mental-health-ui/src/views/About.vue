<template>
  <div class="about-page">
    <div class="page-header">
      <div class="container">
        <h1>关于我们</h1>
        <p>专业的心理健康服务团队，您的心灵成长伙伴</p>
      </div>
    </div>

    <div class="section mission-section">
      <div class="container">
        <div class="mission-content">
          <div class="mission-text">
            <h2>我们的使命</h2>
            <p>致力于为每个人提供专业、温暖的心理健康服务，帮助更多人获得心灵的平静与成长。我们相信，每个人都值得拥有健康、快乐的人生。</p>
            <div class="mission-stats">
              <div class="stat-item">
                <div class="stat-number">10000+</div>
                <div class="stat-label">服务案例</div>
              </div>
              <div class="stat-item">
                <div class="stat-number">50+</div>
                <div class="stat-label">专业团队</div>
              </div>
              <div class="stat-item">
                <div class="stat-number">98%</div>
                <div class="stat-label">满意度</div>
              </div>
            </div>
          </div>
          <div class="mission-image">
            <img src="@/assets/about/231.png" alt="我们的使命">
          </div>
        </div>
      </div>
    </div>

    <div class="section team-section">
      <div class="container">
        <h2>核心团队</h2>
        <div class="team-grid">
          <div class="team-member" v-for="(member, index) in teamMembers" :key="index">
            <div class="member-avatar">
              <img :src="member.avatar" :alt="member.name">
            </div>
            <h3>{{ member.name }}</h3>
            <p class="member-title">{{ member.title }}</p>
            <p class="member-description">{{ member.description }}</p>
          </div>
        </div>
      </div>
    </div>

    <div class="section timeline-section">
      <div class="container">
        <h2>发展历程</h2>
        <el-timeline>
          <el-timeline-item
            v-for="(activity, index) in timeline"
            :key="index"
            :timestamp="activity.date"
            placement="top"
          >
            <h3>{{ activity.title }}</h3>
            <p>{{ activity.content }}</p>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AboutPage',
  data() {
    return {
      teamMembers: [
        {
          name: '张医生',
          title: '首席心理咨询师',
          description: '从业20年，擅长个人成长、情感关系咨询',
          avatar: require('@/assets/about/1.jpg')
        },
        {
          name: '李教授',
          title: '心理学专家',
          description: '心理学博士，专注青少年心理健康研究',
          avatar: require('@/assets/about/2.jpg')
        },
        {
          name: '王老师',
          title: '资深心理咨询师',
          description: '擅长压力管理、职场心理调适',
          avatar: require('@/assets/about/3.jpg')
        },
        {
          name: '陈医生',
          title: '心理治疗师',
          description: '专注抑郁焦虑等心理障碍治疗',
          avatar: require('@/assets/about/4.jpg')
        }
      ],
      timeline: [
        {
          date: '2024年',
          title: '成立之初',
          content: '心理健康平台正式成立，开始提供线上心理咨询服务'
        },
        {
          date: '2025年',
          title: '业务扩展',
          content: '预计开设线下咨询中心，推出团体心理辅导服务'
        },
        {
          date: '2025年',
          title: '技术创新',
          content: '引入AI辅助系统，提升服务效率和质量'
        },
        {
          date: '2025年',
          title: '全国布局',
          content: '预计服务网络覆盖全国30个主要城市'
        }
      ]
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

.page-header h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.section {
  padding: 80px 0;
}

.mission-content {
  display: flex;
  align-items: center;
  gap: 60px;
}

.mission-text {
  flex: 1;
}

.mission-text h2 {
  font-size: 2rem;
  margin-bottom: 20px;
  color: var(--text-color);
}

.mission-stats {
  display: flex;
  gap: 40px;
  margin-top: 40px;
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: 2.5rem;
  font-weight: bold;
  color: var(--primary-color);
}

.stat-label {
  margin-top: 10px;
  color: var(--text-color);
}

.mission-image {
  flex: 1;
}

.mission-image img {
  width: 100%;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.team-section {
  background: var(--background-color);
}

.team-section h2 {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 50px;
}

.team-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 30px;
}

.team-member {
  background: white;
  padding: 30px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.team-member:hover {
  transform: translateY(-5px);
}

.member-avatar {
  width: 120px;
  height: 120px;
  margin: 0 auto 20px;
  border-radius: 50%;
  overflow: hidden;
}

.member-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.member-title {
  color: var(--primary-color);
  margin: 10px 0;
}

.member-description {
  color: #666;
}

.timeline-section h2 {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 50px;
}

@media (max-width: 768px) {
  .mission-content {
    flex-direction: column;
  }

  .mission-stats {
    flex-direction: column;
    gap: 20px;
  }

  .team-grid {
    grid-template-columns: 1fr;
  }
}
</style>
