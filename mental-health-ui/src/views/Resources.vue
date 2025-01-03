<template>
  <div class="resources">
    <div class="container">
      <h1 class="page-title">联系资源</h1>
      
      <el-row :gutter="40">
        <el-col :md="16">
          <div class="contact-section">
            <h2>联系方式</h2>
            <div class="contact-info">
              <div class="info-item">
                <font-awesome-icon icon="map-marker-alt" />
                <div class="info-content">
                  <h3>地址</h3>
                  <p>XX大学心理健康中心（XX楼X层）</p>
                </div>
              </div>
              
              <div class="info-item">
                <font-awesome-icon icon="phone" />
                <div class="info-content">
                  <h3>咨询预约</h3>
                  <p>电话：0123-4567890</p>
                  <p>心理咨询热线：400-123-4567（24小时）</p>
                </div>
              </div>
              
              <div class="info-item">
                <font-awesome-icon icon="clock" />
                <div class="info-content">
                  <h3>工作时间</h3>
                  <p>周一至周五：9:00-17:00</p>
                  <p>周六：9:00-12:00（预约咨询）</p>
                </div>
              </div>
            </div>
            
            <div class="map-container">
              <h2>位置导航</h2>
              <div class="map-placeholder">
                <!-- 这里可以嵌入地图组件 -->
                <p>地图加载中...</p>
              </div>
            </div>
          </div>
        </el-col>
        
        <el-col :md="8">
          <div class="resources-section">
            <h2>常用资源</h2>
            <div class="resource-list">
              <a v-for="resource in resources" 
                 :key="resource.id" 
                 :href="resource.link" 
                 class="resource-item"
                 target="_blank">
                <font-awesome-icon :icon="resource.icon" />
                <span>{{ resource.title }}</span>
              </a>
            </div>
            
            <div class="emergency-contact">
              <h3>紧急联系方式</h3>
              <div class="emergency-numbers">
                <p><strong>校园安全中心：</strong>0123-4567891</p>
                <p><strong>校医院急诊：</strong>0123-4567892</p>
                <p><strong>心理危机干预：</strong>0123-4567893</p>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Resources',
  data() {
    return {
      resources: [
        {
          id: 1,
          icon: ['fas', 'book'],
          title: '心理健康知识库',
          link: '#'
        },
        {
          id: 2,
          icon: ['fas', 'file-alt'],
          title: '心理测评量表下载',
          link: '#'
        },
        {
          id: 3,
          icon: ['fas', 'video'],
          title: '心理健康教育视频',
          link: '#'
        },
        {
          id: 4,
          icon: ['fas', 'calendar-alt'],
          title: '活动日历',
          link: '#'
        }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.resources {
  .page-title {
    text-align: center;
    font-size: 2.5rem;
    color: $gray-800;
    margin-bottom: 3rem;
  }

  h2 {
    font-size: 1.75rem;
    color: $gray-800;
    margin-bottom: 1.5rem;
  }

  .contact-section {
    .contact-info {
      margin-bottom: 3rem;

      .info-item {
        @include flex(row, flex-start, flex-start);
        gap: 1.5rem;
        margin-bottom: 2rem;
        padding: 1.5rem;
        background-color: $gray-100;
        border-radius: 0.5rem;
        @include transition;

        &:hover {
          transform: translateY(-5px);
          @include box-shadow(2);
        }

        svg {
          font-size: 2rem;
          color: $primary-color;
        }

        .info-content {
          h3 {
            font-size: 1.25rem;
            color: $gray-800;
            margin-bottom: 0.5rem;
          }

          p {
            color: $gray-600;
            margin: 0.25rem 0;
          }
        }
      }
    }

    .map-container {
      .map-placeholder {
        height: 300px;
        background-color: $gray-100;
        border-radius: 0.5rem;
        @include flex(row, center, center);
        color: $gray-600;
      }
    }
  }

  .resources-section {
    .resource-list {
      margin-bottom: 3rem;

      .resource-item {
        display: block;
        padding: 1rem;
        margin-bottom: 1rem;
        background-color: $gray-100;
        border-radius: 0.5rem;
        color: $gray-700;
        text-decoration: none;
        @include transition;

        &:hover {
          background-color: $primary-color;
          color: $white;
          transform: translateX(5px);
        }

        svg {
          margin-right: 1rem;
        }
      }
    }

    .emergency-contact {
      background-color: $danger-color-light;
      padding: 1.5rem;
      border-radius: 0.5rem;

      h3 {
        color: $danger-color;
        font-size: 1.25rem;
        margin-bottom: 1rem;
      }

      .emergency-numbers {
        p {
          margin: 0.5rem 0;
          color: $gray-700;

          strong {
            color: $danger-color;
          }
        }
      }
    }
  }
}
</style> 