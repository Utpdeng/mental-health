<template>
  <div class="home">
    <div class="carousel-section">
      <el-carousel height="600px">
        <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
          <div class="carousel-content" :style="{ backgroundImage: `url(${item.image})` }">
            <div class="carousel-text">
              <h2>{{ item.title }}</h2>
              <p>{{ item.description }}</p>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="services-section section">
      <div class="container">
        <h2 class="section-title">我们的服务</h2>
        <div class="services-grid">
          <div 
            class="service-card" 
            v-for="(service, index) in services" 
            :key="index"
            @click="goToService(service.path)"
          >
            <i :class="service.icon"></i>
            <h3>{{ service.title }}</h3>
            <p>{{ service.description }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      carouselItems: [
        {
          image: require('@/assets/images/slide1.jpg'),
          title: '专业的心理咨询服务',
          description: '让我们一起携手，开启心灵成长之旅'
        },
        {
          image: require('@/assets/images/slide2.jpg'),
          title: '科学的心理评估',
          description: '帮助您更好地了解自己'
        },
        {
          image: require('@/assets/images/slide3.jpg'),
          title: '温暖的社区支持',
          description: '在这里，您永远不是一个人'
        }
      ],
      services: [
        {
          icon: 'fas fa-user-md',
          title: '专家指导',
          description: '经验丰富的心理咨询师为您提供专业指导',
          path: '/experts'
        },
        {
          icon: 'fas fa-clipboard-check',
          title: '心理测评',
          description: '科学的心理评估工具，帮助您了解自己',
          path: '/assessment'
        },
        {
          icon: 'fas fa-graduation-cap',
          title: '心理健康教育',
          description: '丰富的心理健康知识，助您成长',
          path: '/education'
        },
        {
          icon: 'fas fa-users',
          title: '社区支持',
          description: '温暖的社区环境，互助共成长',
          path: '/community'
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
.carousel-section {
  margin-top: 10px;
}

.carousel-content {
  height: 100%;
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
}

.carousel-text {
  text-align: center;
  color: white;
  background: rgba(0, 0, 0, 0.5);
  padding: 30px;
  border-radius: 10px;
  max-width: 600px;
}

.carousel-text h2 {
  font-size: 2.5rem;
  margin-bottom: 20px;
}

.section-title {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 50px;
  color: var(--text-color);
}

.services-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 30px;
}

.service-card {
  background: white;
  padding: 30px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
  cursor: pointer;
}

.service-card:hover {
  transform: translateY(-5px);
}

.service-card i {
  font-size: 3rem;
  color: var(--primary-color);
  margin-bottom: 20px;
}

.service-card h3 {
  margin-bottom: 15px;
  color: var(--text-color);
}

.service-card p {
  color: #666;
}

@media (max-width: 768px) {
  .carousel-section {
    margin-top: 60px;
  }
  
  .carousel-text h2 {
    font-size: 1.8rem;
  }
  
  .services-grid {
    grid-template-columns: 1fr;
  }
}
</style>
