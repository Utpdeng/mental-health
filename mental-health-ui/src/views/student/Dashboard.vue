<template>
  <div class="student-dashboard">
    <div class="container">
      <el-row :gutter="20">
        <el-col :span="24">
          <h1 class="page-title">学生中心</h1>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :xs="24" :md="16">
          <el-card class="main-card">
            <div class="welcome-section">
              <h2>欢迎回来，{{ studentName }}</h2>
              <p>今天感觉如何？</p>
              <div class="mood-tracker">
                <el-button 
                  v-for="mood in moods" 
                  :key="mood.value"
                  :type="selectedMood === mood.value ? 'primary' : ''"
                  circle
                  @click="selectMood(mood.value)"
                >
                  {{ mood.icon }}
                </el-button>
              </div>
            </div>

            <el-divider></el-divider>

            <div class="upcoming-sessions">
              <h3>即将到来的咨询</h3>
              <div v-if="nextSession" class="session-card">
                <div class="session-info">
                  <p class="session-time">
                    <font-awesome-icon icon="calendar" />
                    {{ nextSession.date }} {{ nextSession.time }}
                  </p>
                  <p class="session-teacher">
                    <font-awesome-icon icon="user" />
                    咨询师：{{ nextSession.teacher }}
                  </p>
                </div>
                <el-button type="primary" size="small">进入咨询</el-button>
              </div>
              <el-empty v-else description="暂无预约咨询"></el-empty>
            </div>
          </el-card>
        </el-col>

        <el-col :xs="24" :md="8">
          <el-card class="quick-links">
            <div slot="header">
              <span>快捷入口</span>
            </div>
            <div class="links-list">
              <el-button type="text" icon="el-icon-date">预约咨询</el-button>
              <el-button type="text" icon="el-icon-document">心理测评</el-button>
              <el-button type="text" icon="el-icon-chat-line-round">互助社区</el-button>
              <el-button type="text" icon="el-icon-reading">心理课程</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StudentDashboard',
  data() {
    return {
      studentName: '张同学',
      selectedMood: null,
      moods: [
        { value: 'great', icon: '😊' },
        { value: 'good', icon: '🙂' },
        { value: 'okay', icon: '😐' },
        { value: 'bad', icon: '😔' },
        { value: 'terrible', icon: '😢' }
      ],
      nextSession: {
        date: '2024-01-05',
        time: '14:00-15:00',
        teacher: '李老师'
      }
    }
  },
  methods: {
    selectMood(mood) {
      this.selectedMood = mood
    }
  }
}
</script>

<style lang="scss" scoped>
.student-dashboard {
  padding: 2rem 0;

  .page-title {
    font-size: 2rem;
    color: $gray-800;
    margin-bottom: 2rem;
  }

  .main-card {
    margin-bottom: 1rem;

    .welcome-section {
      text-align: center;
      padding: 1rem 0;

      h2 {
        color: $gray-800;
        margin-bottom: 1rem;
      }

      .mood-tracker {
        margin: 1rem 0;
        @include flex(row, center, center);
        gap: 1rem;

        .el-button {
          font-size: 1.5rem;
        }
      }
    }
  }

  .upcoming-sessions {
    h3 {
      margin-bottom: 1rem;
      color: $gray-700;
    }

    .session-card {
      @include flex(row, space-between, center);
      padding: 1rem;
      background-color: $gray-100;
      border-radius: 0.5rem;

      .session-info {
        p {
          margin: 0.5rem 0;
          color: $gray-600;

          svg {
            margin-right: 0.5rem;
          }
        }
      }
    }
  }

  .quick-links {
    .links-list {
      @include flex(column, flex-start, stretch);
      gap: 1rem;

      .el-button {
        text-align: left;
        font-size: 1rem;

        i {
          margin-right: 0.5rem;
        }
      }
    }
  }
}
</style> 