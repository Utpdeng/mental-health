<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <div class="profile-header">
        <el-avatar :size="100" :src="userInfo.avatarUrl"></el-avatar>
        <h2>{{ userInfo.nickname || userInfo.username }}</h2>
      </div>

      <el-descriptions title="个人信息" :column="1" border>
        <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ userInfo.email }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ userInfo.role }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ userInfo.gender === 'male' ? '男' : '女' }}</el-descriptions-item>
        <el-descriptions-item label="生日">{{ userInfo.birthdate }}</el-descriptions-item>
        <el-descriptions-item label="地区">{{ userInfo.location }}</el-descriptions-item>
        <el-descriptions-item label="兴趣爱好">{{ userInfo.preferences }}</el-descriptions-item>
        <el-descriptions-item label="注册时间">{{ userInfo.createdAt }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script>
import { getUserInfo } from '@/api/user'

export default {
  name: 'DoctorProfile',
  data() {
    return {
      userInfo: {}
    }
  },
  async created() {
    const userId = this.$store.state.user.userId
    try {
      const res = await getUserInfo(userId)
      this.userInfo = res.data
      console.log(this.userInfo.avatarUrl)
    } catch (error) {
      this.$message.error('获取用户信息失败')
    }
  }
}
</script>

<style scoped>
.profile-container {
  padding: 20px;
}

.profile-card {
  max-width: 800px;
  margin: 0 auto;
}

.profile-header {
  text-align: center;
  margin-bottom: 20px;
}

.profile-header h2 {
  margin-top: 10px;
}
</style>
