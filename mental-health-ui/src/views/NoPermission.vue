<template>
  <div class="no-permission-page">
    <div class="container">
      <div class="error-content">
        <i class="el-icon-warning-outline"></i>
        <h1>403</h1>
        <h2>无权限访问</h2>
        <p>抱歉，您没有权限访问该页面</p>
        <div class="action-buttons">
          <el-button type="primary" @click="goHome">返回首页</el-button>
          <el-button @click="goBack">返回上一页</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NoPermission',
  methods: {
    goHome() {
      this.$router.replace('/')
    },
    goBack() {
      this.$router.go(-1)
    }
  }
}
</script>

<style scoped>
.no-permission-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
  padding: 20px;
  text-align: center;
}

.error-content {
  max-width: 500px;
  padding: 40px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.el-icon-warning-outline {
  font-size: 80px;
  color: #E6A23C;
  margin-bottom: 20px;
}

h1 {
  font-size: 72px;
  margin: 0;
  color: #E6A23C;
  line-height: 1;
}

h2 {
  font-size: 28px;
  margin: 10px 0;
  font-weight: 500;
  color: #333;
}

p {
  color: #666;
  margin-bottom: 30px;
  font-size: 16px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
}
</style> 