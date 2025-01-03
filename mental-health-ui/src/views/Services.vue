<template>
  <div class="services">
    <div class="container">
      <h1 class="page-title">服务项目</h1>
      
      <el-row :gutter="30">
        <el-col :xs="24" :sm="12" :md="8" v-for="service in services" :key="service.id">
          <el-card class="service-card" :body-style="{ padding: '0px' }">
            <div class="service-image">
              <font-awesome-icon :icon="service.icon" />
            </div>
            <div class="service-content">
              <h3>{{ service.title }}</h3>
              <p>{{ service.description }}</p>
              <el-button type="primary" plain>了解更多</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Services',
  data() {
    return {
      services: [
        {
          id: 1,
          icon: ['fas', 'user-md'],
          title: '个人咨询',
          description: '一对一专业心理咨询服务，帮助您解决个人困扰，提升心理健康水平。'
        },
        {
          id: 2,
          icon: ['fas', 'users'],
          title: '团体辅导',
          description: '小组形式的心理辅导活动，促进交流与成长，提升社交能力。'
        },
        {
          id: 3,
          icon: ['fas', 'brain'],
          title: '心理测评',
          description: '专业的心理测评工具，帮助了解自己的心理状态和性格特点。'
        },
        {
          id: 4,
          icon: ['fas', 'chalkboard-teacher'],
          title: '心理课程',
          description: '系统的心理健康教育课程，提供心理知识和自我成长指导。'
        },
        {
          id: 5,
          icon: ['fas', 'hands-helping'],
          title: '危机干预',
          description: '为遇到严重心理困扰的学生提供及时的危机干预服务。'
        },
        {
          id: 6,
          icon: ['fas', 'comments'],
          title: '在线咨询',
          description: '便捷的线上咨询服务，随时随地获取心理支持。'
        }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.services {
  .page-title {
    text-align: center;
    font-size: 2.5rem;
    color: $gray-800;
    margin-bottom: 3rem;
  }

  .service-card {
    margin-bottom: 2rem;
    @include transition;
    
    &:hover {
      transform: translateY(-5px);
      @include box-shadow(2);
    }

    .service-image {
      height: 150px;
      @include flex(row, center, center);
      background: linear-gradient(135deg, $primary-color, $secondary-color);
      color: $white;
      font-size: 4rem;
    }

    .service-content {
      padding: 1.5rem;
      text-align: center;

      h3 {
        font-size: 1.5rem;
        color: $gray-800;
        margin-bottom: 1rem;
      }

      p {
        color: $gray-600;
        margin-bottom: 1.5rem;
        min-height: 4.5rem;
      }
    }
  }
}
</style> 