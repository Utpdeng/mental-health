<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="data-card">
          <div class="data-header">
            <i class="el-icon-user"></i>
            <span>用户总数</span>
          </div>
          <div class="data-content">
            <span class="number">{{ statistics.userCount }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="data-header">
            <i class="el-icon-s-custom"></i>
            <span>医生总数</span>
          </div>
          <div class="data-content">
            <span class="number">{{ statistics.doctorCount }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="data-header">
            <i class="el-icon-s-order"></i>
            <span>预约总数</span>
          </div>
          <div class="data-content">
            <span class="number">{{ statistics.appointmentCount }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="data-header">
            <i class="el-icon-message"></i>
            <span>咨询总数</span>
          </div>
          <div class="data-content">
            <span class="number">{{ statistics.consultationCount }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'AdminDashboard',
  data() {
    return {
      statistics: {
        userCount: 1250,
        doctorCount: 48,
        appointmentCount: 3567,
        consultationCount: 2890
      }
    }
  }
}
</script>

<style scoped>
.dashboard {
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