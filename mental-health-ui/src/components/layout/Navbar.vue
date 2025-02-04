<template>
  <nav class="navbar">
    <div class="container navbar-container">
      <div class="navbar-brand">
        <router-link to="/" class="logo-container">
          <i class="fas fa-brain logo-icon"></i>
          <span class="logo-text">心理健康平台</span>
        </router-link>
      </div>

      <div class="navbar-right">
        <router-link to="/" class="nav-item">首页</router-link>
        <router-link to="/services" class="nav-item">服务项目</router-link>
        <router-link to="/about" class="nav-item">关于我们</router-link>
        <router-link to="/contact" class="nav-item">联系咨询</router-link>
        
        <!-- 未登录显示登录注册按钮 -->
        <template v-if="!isLoggedIn">
          <router-link to="/login" class="nav-item">登录</router-link>
          <router-link to="/register" class="nav-item">注册</router-link>
        </template>
        
        <!-- 已登录显示用户信息 -->
        <template v-else>
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" :src="userAvatar"></el-avatar>
              <span class="username">{{ username }}</span>
              <i class="el-icon-arrow-down"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="profile">
                <i class="el-icon-user"></i> 个人中心
              </el-dropdown-item>
              <el-dropdown-item v-if="isAdmin" command="admin">
                <i class="el-icon-s-tools"></i> 管理后台
              </el-dropdown-item>
              <el-dropdown-item v-if="isDoctor" command="doctor">
                <i class="el-icon-s-tools"></i> 医生后台
              </el-dropdown-item>
              <el-dropdown-item v-if="isUser" command="user">
                <i class="el-icon-s-tools"></i> 咨询会话
              </el-dropdown-item>
              <el-dropdown-item divided command="logout">
                <i class="el-icon-switch-button"></i> 退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </div>
    </div>
  </nav>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'AppNavbar',
  computed: {
    ...mapState('user', ['username', 'role', 'avatar']),
    isLoggedIn() {
      return !!this.$store.state.user.token
    },
    isAdmin() {
      return this.$store.state.user.role === 'admin'
    },
    isDoctor() {
      return this.$store.state.user.role === 'doctor'
    },
    isUser() {
      return this.$store.state.user.role === 'user'
    },
    userAvatar() {
      return this.avatar || require('@/assets/images/default-avatar.png')
    },
  },
  methods: {
    ...mapActions('user', ['logout']),
    async handleCommand(command) {
      switch (command) {
        case 'profile':
          if (this.isAdmin) {
            this.$router.push('/admin/profile')
          } else if (this.isDoctor) {
            this.$router.push('/doctor/profile')
          } else if (this.isUser) {
            this.$router.push('/user/profile')
          }
          break
        case 'admin':
          this.$router.push('/admin/dashboard')
          break
        case 'doctor':
          this.$router.push('/doctor/dashboard')
          break
        case 'user':
          this.$router.push('/user/consultation')
          break
        case 'logout':
          try {
            await this.logout()
            this.$message.success('退出登录成功')
            this.$router.push('/login')
          } catch (error) {
            this.$message.error('退出登录失败')
          }
          break
      }
    }
  }
}
</script>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 70px;
  background: rgba(255, 255, 255, 0.98);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  z-index: 1000;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.navbar-brand {
  display: flex;
  align-items: center;
}

.logo-container {
  display: flex;
  align-items: center;
  text-decoration: none;
  color: var(--primary-color);
}

.logo-icon {
  font-size: 24px;
  margin-right: 10px;
}

.logo-text {
  font-size: 20px;
  font-weight: 600;
}

.navbar-right {
  display: flex;
  align-items: center;
}

.nav-item {
  text-decoration: none;
  color: var(--text-color);
  font-weight: 500;
  transition: color 0.3s ease;
  padding: 0 15px;
}

.nav-item:hover {
  color: var(--primary-color);
}

/* 用户信息样式 */
.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 0 0 15px;
}

.username {
  margin: 0 8px;
  color: var(--text-color);
}

@media (max-width: 768px) {
  .navbar-right {
    display: none;
  }
}
</style>
