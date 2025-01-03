<template>
  <div class="admin-dashboard">
    <div class="container">
      <el-row :gutter="20">
        <el-col :span="24">
          <h1 class="page-title">管理控制台</h1>
        </el-col>
      </el-row>
      
      <el-row :gutter="20" class="stats-cards">
        <el-col :xs="24" :sm="12" :md="6">
          <el-card class="stat-card">
            <div class="stat-content">
              <font-awesome-icon icon="users" class="stat-icon" />
              <div class="stat-info">
                <h3>总用户数</h3>
                <p class="stat-value">1,234</p>
              </div>
            </div>
          </el-card>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6">
          <el-card class="stat-card">
            <div class="stat-content">
              <font-awesome-icon icon="user-md" class="stat-icon" />
              <div class="stat-info">
                <h3>咨询师数量</h3>
                <p class="stat-value">45</p>
              </div>
            </div>
          </el-card>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6">
          <el-card class="stat-card">
            <div class="stat-content">
              <font-awesome-icon icon="calendar-check" class="stat-icon" />
              <div class="stat-info">
                <h3>今日预约</h3>
                <p class="stat-value">28</p>
              </div>
            </div>
          </el-card>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6">
          <el-card class="stat-card">
            <div class="stat-content">
              <font-awesome-icon icon="chart-line" class="stat-icon" />
              <div class="stat-info">
                <h3>本月咨询量</h3>
                <p class="stat-value">342</p>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminDashboard'
}
</script>

<style lang="scss" scoped>
.admin-dashboard {
  padding: 2rem 0;

  .page-title {
    font-size: 2rem;
    color: $gray-800;
    margin-bottom: 2rem;
  }

  .stats-cards {
    margin-bottom: 2rem;
  }

  .stat-card {
    margin-bottom: 1rem;
    @include transition;

    &:hover {
      transform: translateY(-5px);
      @include box-shadow(2);
    }

    .stat-content {
      @include flex(row, flex-start, center);
      gap: 1rem;
      padding: 1rem;

      .stat-icon {
        font-size: 2rem;
        color: $primary-color;
      }

      .stat-info {
        h3 {
          font-size: 0.875rem;
          color: $gray-600;
          margin-bottom: 0.5rem;
        }

        .stat-value {
          font-size: 1.5rem;
          font-weight: 600;
          color: $gray-800;
          margin: 0;
        }
      }
    }
  }
}
</style> 