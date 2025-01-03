<template>
  <div class="home">
    <!-- 轮播图部分 -->
    <div class="hero-section">
      <el-carousel 
        :interval="4000" 
        :height="carouselHeight" 
        :autoplay="true"
        indicator-position="outside"
        arrow="hover">
        <el-carousel-item v-for="item in carouselItems" :key="item.id">
          <div class="carousel-content" 
               :style="{ backgroundImage: `url(${item.image})` }">
            <div class="carousel-overlay">
              <div class="container">
                <h2>{{ item.title }}</h2>
                <p>{{ item.description }}</p>
                <el-button type="primary" size="large" @click="learnMore">了解更多</el-button>
              </div>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 服务特色部分 -->
    <section class="features">
      <div class="container">
        <h2 class="section-title">我们的服务</h2>
        <div class="feature-grid">
          <div class="feature-card" v-for="feature in features" :key="feature.id">
            <div class="feature-icon">
              <font-awesome-icon :icon="feature.icon" />
            </div>
            <h3>{{ feature.title }}</h3>
            <p>{{ feature.description }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 心理知识部分 -->
    <section class="knowledge">
      <div class="container">
        <h2 class="section-title">心理知识</h2>
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" v-for="article in articles" :key="article.id">
            <el-card class="article-card" :body-style="{ padding: '0px' }">
              <img :src="article.image" class="article-image">
              <div class="article-content">
                <h3>{{ article.title }}</h3>
                <p>{{ article.summary }}</p>
                <div class="article-footer">
                  <time>{{ article.date }}</time>
                  <el-button type="text">阅读更多</el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </section>

    <!-- 联系我们部分 -->
    <section class="contact">
      <div class="container">
        <h2 class="section-title">联系我们</h2>
        <el-row :gutter="40">
          <el-col :md="12">
            <div class="contact-info">
              <h3>校园心理健康中心</h3>
              <p><font-awesome-icon icon="map-marker-alt" /> 地址：XX大学心理健康中心</p>
              <p><font-awesome-icon icon="phone" /> 电话：0123-4567890</p>
              <p><font-awesome-icon icon="envelope" /> 邮箱：support@example.com</p>
              <p><font-awesome-icon icon="clock" /> 工作时间：周一至周五 9:00-17:00</p>
            </div>
          </el-col>
          <el-col :md="12">
            <el-form :model="contactForm" label-position="top">
              <el-form-item label="姓名">
                <el-input v-model="contactForm.name"></el-input>
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="contactForm.email"></el-input>
              </el-form-item>
              <el-form-item label="留言">
                <el-input type="textarea" v-model="contactForm.message" :rows="4"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitContact">发送留言</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      carouselHeight: '500px',
      carouselItems: [
        {
          id: 1,
          image: require('@/assets/images/carousel/slide1.jpg'),
          title: '心理健康，从这里开始',
          description: '专业的心理咨询服务，为您的心理健康保驾护航'
        },
        {
          id: 2,
          image: require('@/assets/images/carousel/slide2.jpg'),
          title: '专业团队，贴心服务',
          description: '资深心理咨询师团队，提供专业的心理健康指导'
        },
        {
          id: 3,
          image: require('@/assets/images/carousel/slide3.jpg'),
          title: '共建和谐校园',
          description: '营造积极向上的心理环境，助力健康成长'
        }
      ],
      features: [
        {
          id: 1,
          icon: ['fas', 'comments'],
          title: '在线咨询',
          description: '随时随地进行心理咨询，专业咨询师在线服务'
        },
        {
          id: 2,
          icon: ['fas', 'book-reader'],
          title: '心理课程',
          description: '丰富的心理健康课程，提升心理素质'
        },
        {
          id: 3,
          icon: ['fas', 'users'],
          title: '团体辅导',
          description: '小组互助，共同成长'
        },
        {
          id: 4,
          icon: ['fas', 'clipboard-check'],
          title: '心理测评',
          description: '专业的心理测评工具，了解自己的心理状态'
        }
      ],
      articles: [
        {
          id: 1,
          image: require('@/assets/images/articles/article1.jpg'),
          title: '如何缓解考试焦虑',
          summary: '考试焦虑是很多学生都会遇到的问题，本文将为您介绍几个实用的缓解方法...',
          date: '2024-01-05'
        },
        {
          id: 2,
          image: require('@/assets/images/articles/article2.jpg'),
          title: '提升学习效率的心理技巧',
          summary: '正确的学习方法和心态可以大大提升学习效率，让我们一起来了解这些技巧...',
          date: '2024-01-03'
        },
        {
          id: 3,
          image: require('@/assets/images/articles/article3.jpg'),
          title: '人际关系中的自我调适',
          summary: '在人际交往中遇到困扰？这些方法可以帮助你更好地处理人际关系...',
          date: '2024-01-01'
        }
      ],
      contactForm: {
        name: '',
        email: '',
        message: ''
      }
    }
  },
  methods: {
    learnMore() {
      this.$router.push('/services')
    },
    submitContact() {
      // 处理提交留言的逻辑
      this.$message.success('留言已提交，我们会尽快回复您！')
      this.contactForm = {
        name: '',
        email: '',
        message: ''
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.home {
  .hero-section {
    margin-top: -2rem; // 抵消 app-main 的 padding
    margin-bottom: 4rem;

    .el-carousel {
      .el-carousel__container {
        .el-carousel__item {
          .carousel-content {
            height: 100%;
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;

            .carousel-overlay {
              height: 100%;
              background: rgba($black, 0.4);
              @include flex(row, center, center);
              padding: 2rem;
              text-align: center;
              color: $white;

              .container {
                max-width: 800px;

                h2 {
                  font-size: 3rem;
                  font-weight: 600;
                  margin-bottom: 1.5rem;
                  animation: fadeInDown 1s;
                }

                p {
                  font-size: 1.25rem;
                  margin-bottom: 2rem;
                  animation: fadeInUp 1s;
                }

                .el-button {
                  padding: 12px 30px;
                  font-size: 1.125rem;
                  animation: fadeIn 1.5s;
                }
              }
            }
          }
        }
      }

      .el-carousel__indicators {
        bottom: 20px;

        .el-carousel__button {
          width: 12px;
          height: 12px;
          border-radius: 50%;
          background-color: rgba($white, 0.7);

          &:hover {
            background-color: $white;
          }
        }
      }
    }
  }

  // 添加动画
  @keyframes fadeInDown {
    from {
      opacity: 0;
      transform: translateY(-20px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  @keyframes fadeInUp {
    from {
      opacity: 0;
      transform: translateY(20px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
}

// 响应式调整
@media (max-width: 768px) {
  .home {
    .hero-section {
      .el-carousel {
        .el-carousel__container {
          .carousel-content {
            .carousel-overlay {
              .container {
                h2 {
                  font-size: 2rem;
                }

                p {
                  font-size: 1rem;
                }
              }
            }
          }
        }
      }
    }
  }
}

.section-title {
  text-align: center;
  font-size: 2.5rem;
  color: $gray-800;
  margin-bottom: 3rem;
}

.features {
  padding: 4rem 0;
  background-color: $white;

  .feature-grid {
    display: grid;
    grid-template-columns: repeat(1, 1fr);
    gap: 2rem;
    
    @include respond-to('md') {
      grid-template-columns: repeat(2, 1fr);
    }
    
    @include respond-to('lg') {
      grid-template-columns: repeat(4, 1fr);
    }
  }

  .feature-card {
    text-align: center;
    padding: 2rem;
    background-color: $gray-100;
    border-radius: 0.5rem;
    @include transition;
    
    &:hover {
      transform: translateY(-5px);
      @include box-shadow(2);
    }

    .feature-icon {
      font-size: 3rem;
      color: $primary-color;
      margin-bottom: 1.5rem;
    }

    h3 {
      font-size: 1.5rem;
      margin-bottom: 1rem;
      color: $gray-800;
    }

    p {
      color: $gray-600;
      line-height: 1.6;
    }
  }
}

.knowledge {
  padding: 4rem 0;
  background-color: $gray-100;

  .article-card {
    margin-bottom: 2rem;
    @include transition;

    &:hover {
      transform: translateY(-5px);
      @include box-shadow(2);
    }

    .article-image {
      width: 100%;
      height: 200px;
      object-fit: cover;
    }

    .article-content {
      padding: 1.5rem;

      h3 {
        font-size: 1.25rem;
        margin-bottom: 1rem;
        color: $gray-800;
      }

      p {
        color: $gray-600;
        margin-bottom: 1rem;
      }

      .article-footer {
        @include flex(row, space-between, center);
        color: $gray-500;
      }
    }
  }
}

.contact {
  padding: 4rem 0;
  background-color: $white;

  .contact-info {
    h3 {
      font-size: 1.5rem;
      margin-bottom: 2rem;
      color: $gray-800;
    }

    p {
      margin: 1rem 0;
      color: $gray-600;

      svg {
        margin-right: 1rem;
        color: $primary-color;
      }
    }
  }
}
</style> 