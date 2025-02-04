<template>
  <div class="services-page">
    <div class="page-header">
      <div class="container">
        <h1>服务项目</h1>
        <p>为您提供专业的心理健康服务</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="services-list">
          <div class="service-item" v-for="(service, index) in services" :key="index">
            <div class="service-icon">
              <i :class="service.icon"></i>
            </div>
            <div class="service-content">
              <h3>{{ service.title }}</h3>
              <p>{{ service.description }}</p>
              <ul class="service-features">
                <li v-for="(feature, fIndex) in service.features" :key="fIndex">
                  {{ feature }}
                </li>
              </ul>
              <el-button type="primary" class="service-btn" @click="goToService(service.path)">了解更多</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ServicesPage',
  data() {
    return {
      services: [
        {
          icon: 'fas fa-user-md',
          title: '个人咨询',
          description: '一对一专业心理咨询服务，帮助您解决个人困扰。',
          features: [
            '专业心理咨询师一对一服务',
            '个性化解决方案',
            '全程保密服务',
            '灵活的预约时间'
          ],
          path: '/experts',
        },
        {
          icon: 'fas fa-users',
          title: '团体咨询',
          description: '小组形式的心理辅导，共同分享、共同成长。',
          features: [
            '专业团体辅导',
            '主题式小组活动',
            '互助成长环境',
            '社交技能提升'
          ],
          path: '/experts',
        },
        {
          icon: 'fas fa-clipboard-check',
          title: '心理评估',
          description: '专业的心理测评工具，帮助您更好地了解自己。',
          features: [
            '标准化心理测评',
            '个性化分析报告',
            '专业解读服务',
            '发展建议制定'
          ],
          path: '/assessment',
        },
        {
          icon: 'fas fa-graduation-cap',
          title: '心理课程',
          description: '系统的心理健康教育课程，提升心理素质。',
          features: [
            '系统化课程设计',
            '互动式学习体验',
            '实用技能培训',
            '案例分析讨论'
          ],
          path: '/education',
        }
      ]
    }
  },
  methods: {
    goToService(path) {
      this.$router.push(path)
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

.services-list {
  padding: 40px 0;
}

.service-item {
  display: flex;
  margin-bottom: 40px;
  padding: 30px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.service-item:hover {
  transform: translateY(-5px);
}

.service-icon {
  flex: 0 0 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2.5rem;
  color: var(--primary-color);
}

.service-content {
  flex: 1;
  padding-left: 30px;
}

.service-content h3 {
  color: var(--text-color);
  margin-bottom: 15px;
}

.service-features {
  list-style: none;
  margin: 20px 0;
  padding: 0;
}

.service-features li {
  margin-bottom: 10px;
  padding-left: 25px;
  position: relative;
}

.service-features li::before {
  content: '✓';
  position: absolute;
  left: 0;
  color: var(--primary-color);
}

.service-btn {
  margin-top: 20px;
}

@media (max-width: 768px) {
  .service-item {
    flex-direction: column;
    text-align: center;
  }

  .service-icon {
    margin-bottom: 20px;
  }

  .service-content {
    padding-left: 0;
  }

  .service-features li {
    text-align: left;
  }
}
</style>
