<!-- 将之前的 Assessment.vue 内容移到这里 --> <template>
  <div class="assessment-page">
    <div class="page-header">
      <div class="container">
        <h1>心理测评</h1>
        <p>专业的心理测评工具，帮助您更好地了解自己</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <el-row :gutter="30">
          <el-col :xs="24" :sm="12" :lg="8" v-for="test in tests" :key="test.id">
            <div class="test-card">
              <div class="test-image">
                <img :src="test.image" :alt="test.title">
              </div>
              <div class="test-content">
                <h3>{{ test.title }}</h3>
                <p class="test-description">{{ test.description }}</p>
                <div class="test-info">
                  <span><i class="fas fa-clock"></i> {{ test.duration }}分钟</span>
                  <span><i class="fas fa-question-circle"></i> {{ test.questions }}题</span>
                </div>
                <el-button type="primary" @click="startTest(test)">开始测评</el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Assessment',
  data() {
    return {
      tests: [
        {
          id: 1,
          title: '情绪健康测评',
          description: '全面评估您的情绪状态，了解自己的情绪健康水平',
          duration: 15,
          questions: 50,
          image: 'https://picsum.photos/400/200'
        },
        {
          id: 2,
          title: '压力指数测试',
          description: '测量您当前的压力水平，找到压力源',
          duration: 10,
          questions: 30,
          image: 'https://picsum.photos/400/201'
        },
        {
          id: 3,
          title: '人格特质分析',
          description: '了解您的性格特点，发现自己的优势',
          duration: 20,
          questions: 60,
          image: 'https://picsum.photos/400/202'
        },
        {
          id: 4,
          title: '职业倾向测评',
          description: '发现最适合您的职业方向',
          duration: 25,
          questions: 70,
          image: 'https://picsum.photos/400/203'
        },
        {
          id: 5,
          title: '人际关系测评',
          description: '评估您的社交能力和人际关系状况',
          duration: 15,
          questions: 45,
          image: 'https://picsum.photos/400/204'
        },
        {
          id: 6,
          title: '睡眠质量评估',
          description: '了解您的睡眠状况，改善睡眠质量',
          duration: 10,
          questions: 25,
          image: 'https://picsum.photos/400/205'
        }
      ]
    }
  },
  computed: {
    ...mapState('user', ['token'])
  },
  methods: {
    startTest(test) {
      if (!this.token) {
        this.$confirm('请先登录后再进行心理测评', '提示', {
          confirmButtonText: '去登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push({
            path: '/login',
            query: { redirect: this.$route.fullPath }
          })
        }).catch(() => {
          // 用户取消操作
        })
      } else {
        // 跳转到测评页面
        this.$router.push({
          path: `/assessment/test/${test.id}`,
          query: { title: test.title }
        })
      }
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

.test-card {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  transition: transform 0.3s ease;
}

.test-card:hover {
  transform: translateY(-5px);
}

.test-image {
  height: 200px;
  overflow: hidden;
}

.test-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.test-content {
  padding: 20px;
}

.test-description {
  color: #666;
  margin: 10px 0;
  min-height: 60px;
}

.test-info {
  display: flex;
  justify-content: space-between;
  margin: 15px 0;
  color: #666;
}

.test-info i {
  margin-right: 5px;
  color: var(--primary-color);
}

.el-button {
  width: 100%;
}
</style> 