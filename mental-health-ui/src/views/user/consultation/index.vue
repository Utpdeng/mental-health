<template>
  <div class="consultation-sessions">
    <!-- 当没有显示聊天界面时才显示会话列表 -->
    <template v-if="!$route.query.sessionId">
      <div class="sessions-header">
        <div class="header-title">
          <i class="el-icon-chat-dot-round"></i>
          <span>咨询会话</span>
        </div>
        <div class="header-actions">
          <el-radio-group v-model="sessionType" size="small">
            <el-radio-button label="all">全部</el-radio-button>
            <el-radio-button label="video">视频咨询</el-radio-button>
            <el-radio-button label="graphic">图文咨询</el-radio-button>
          </el-radio-group>
          <el-button 
            type="primary" 
            icon="el-icon-refresh" 
            size="small"
            @click="fetchSessions"
          >刷新</el-button>
        </div>
      </div>

      <el-row :gutter="20" class="session-cards" v-loading="loading">
        <el-col :span="8" v-for="session in sessions" :key="session.id">
          <el-card 
            shadow="hover" 
            class="session-card"
            :class="{ 'session-card-active': session.status === 'confirmed' }"
          >
            <div class="session-info">
              <div class="patient-basic">
                <el-avatar :size="50" :src="session.doctorAvatar"></el-avatar>
                <div class="patient-name">
                  <div class="patient-tags">
                    <el-tag size="mini">{{ session.doctorName }}</el-tag>
                  </div>
                </div>
                <el-badge 
                  :value="session.unreadCount" 
                  :hidden="!session.unreadCount"
                  class="unread-badge"
                >
                  <el-tag 
                    :type="session.type === 'video' ? 'success' : 'primary'"
                    size="small"
                  >
                    {{ session.form === 'video' ? '视频' : '图文' }}
                  </el-tag>
                </el-badge>
              </div>
              
              <div class="session-detail">
                <div class="detail-item">
                  <label>开始时间：</label>
                  <p>{{ formatDateTime(session.createdAt) }}</p>
                </div>
                <div class="detail-item">
                  <label>时长：</label>
                  <p>{{ session.time }}分钟</p>
                </div>
              </div>

              <div class="session-status">
                <el-tag 
                  :type="getStatusType(session.status)"
                  size="small"
                >
                  {{ getStatusText(session.status) }}
                </el-tag>
                <span class="last-time">{{ formatTime(session.updatedAt) }}</span>
              </div>

              <div class="last-message" v-if="session.feedback">
                {{ session.feedback }}
              </div>
            </div>

            <div class="session-actions">
              <el-button 
                type="primary" 
                size="small"
                @click="enterChat(session)"
              >进入咨询</el-button>
              <el-button 
                type="graphic" 
                size="small"
                @click="viewRecord(session)"
              >查看记录</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </template>

    <!-- 聊天界面 -->
    <router-view></router-view>
  </div>
</template>

<script>
import { getConsultationList } from '@/api/consultation'

export default {
  name: 'ConsultationSessions',
  data() {
    return {
      sessionType: 'all',
      loading: false,
      sessions: [],
      total: 0
    }
  },
  computed: {
    userId() {
      return this.$store.state.user.userId
    }
  },
  watch: {
    sessionType: {
      handler(newType) {
        let form = null
        if (newType === 'video') {
          form = 'video'
        } else if (newType === 'graphic') {
          form = 'graphic'
        }
        // all 的情况下 form 保持为 null
        
        console.log('选择的类型:', newType, '请求的form:', form)
        this.fetchSessions(null, this.userId, null, form)
      },
      immediate: true
    }
  },
  created() {
    this.fetchSessions(null, this.userId, null, null)
  },
  methods: {
    formatDateTime(datetime) {
      const date = new Date(datetime)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      return `${year}-${month}-${day} ${hours}:${minutes}`
    },
    formatTime(datetime) {
      const date = new Date(datetime)
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      return `${hours}:${minutes}`
    },
    getStatusType(status) {
      const types = {
        confirmed: 'success',
      }
      return types[status] || 'info'
    },
    getStatusText(status) {
      const texts = {
        confirmed: '进行中',
        completed: '已结束'
      }
      return texts[status] || status
    },
    async fetchSessions(doctorId = null, userId, appointmentId, form) {
      console.log('医生id:', doctorId, '用户id:', userId, '预约id:', appointmentId, 'form:', form)
      this.loading = true
      try {
        const params = {
          userId: this.userId,
          form: form
        }
        console.log(params);
        const res = await getConsultationList(params)
        if (res.status === '200') {
          this.sessions = res.data
        }
      } catch (error) {
        console.error('获取咨询会话失败:', error)
        this.$message.error('获取咨询会话失败')
      } finally {
        this.loading = false
      }
    },
    enterChat(session) {
      console.log('跳转');
      
      this.$router.push({
        path: '/user/consultation/chat',
        query: { sessionId: session.id, session: session }
      })
    },
    viewRecord(session) {
      // 实现查看记录的逻辑
      console.log('查看记录:', session.id)
      this.$router.push('/user/record')
    }
  }
}
</script>

<style scoped>
.consultation-sessions {
  min-height: 400px;
}

.sessions-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-title {
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 8px;
}

.header-title i {
  color: var(--primary-color);
  font-size: 20px;
}

.header-actions {
  display: flex;
  gap: 15px;
}

.session-cards {
  margin: 0 -10px;
}

.session-card {
  margin-bottom: 20px;
  transition: all 0.3s;
}

.session-card:hover {
  transform: translateY(-2px);
}

.session-card-active {
  border: 1px solid var(--primary-color);
}

.session-info {
  position: relative;
}

.patient-basic {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.patient-name {
  margin-left: 15px;
  flex: 1;
}

.patient-name h4 {
  margin: 0 0 5px;
}

.patient-tags {
  display: flex;
  gap: 5px;
}

.unread-badge {
  margin-left: auto;
}

.session-detail {
  margin: 15px 0;
  font-size: 13px;
}

.detail-item {
  margin-bottom: 8px;
  display: flex;
}

.detail-item label {
  color: #666;
  width: 80px;
}

.detail-item p {
  margin: 0;
  flex: 1;
  color: #333;
}

.session-status {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.last-time {
  font-size: 12px;
  color: #999;
}

.last-message {
  font-size: 13px;
  color: #666;
  margin: 10px 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.session-actions {
  margin-top: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid #eee;
  padding-top: 15px;
}
</style>
