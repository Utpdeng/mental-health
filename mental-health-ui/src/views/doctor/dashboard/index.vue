<template>
  <div class="doctor-dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="data-card">
          <div class="data-header">
            <i class="el-icon-s-order"></i>
            <span>今日预约</span>
          </div>
          <div class="data-content">
            <span class="number">{{ statistics.todayAppointments }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="data-header">
            <i class="el-icon-chat-line-round"></i>
            <span>待处理咨询</span>
          </div>
          <div class="data-content">
            <span class="number">{{ statistics.pendingConsultations }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="data-header">
            <i class="el-icon-document"></i>
            <span>发布文章</span>
          </div>
          <div class="data-content">
            <span class="number">{{ statistics.articles }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="data-header">
            <i class="el-icon-reading"></i>
            <span>发布课程</span>
          </div>
          <div class="data-content">
            <span class="number">{{ statistics.courses }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'DoctorDashboard',
  data() {
    return {
      statistics: {
        todayAppointments: 5,
        pendingConsultations: 3,
        articles: 12,
        courses: 4
      }
    }
  }
}
</script>

<style scoped>
.doctor-dashboard {
  padding: 20px;
}

.data-card {
  margin-bottom: 20px;
}

.data-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  color: #666;
}

.data-header i {
  font-size: 20px;
  margin-right: 10px;
}

.data-content .number {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}
</style> 