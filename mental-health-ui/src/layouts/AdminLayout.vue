<template>
  <div class="admin-layout">
    <el-container>
      <el-aside width="200px">
        <div class="logo">
          <i class="fas fa-brain"></i>
          <router-link to="/">心理健康平台</router-link>
        </div>
        <el-menu
          :default-active="$route.path"
          class="el-menu-vertical"
          background-color="#304156"
          text-color="#fff"
          active-text-color="#409EFF"
          router
        >
          <el-menu-item index="/admin/dashboard">
            <i class="el-icon-s-home"></i>
            <span>控制台</span>
          </el-menu-item>
          <el-menu-item index="/admin/users">
            <i class="el-icon-user"></i>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/doctors">
            <i class="el-icon-s-custom"></i>
            <span>医生管理</span>
          </el-menu-item>
          <el-submenu index="content">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>内容管理</span>
            </template>
            <el-menu-item index="/admin/articles">文章管理</el-menu-item>
            <el-menu-item index="/admin/courses">课程管理</el-menu-item>
            <el-menu-item index="/admin/tests">心理测试</el-menu-item>
          </el-submenu>
          <el-menu-item index="/admin/community">
            <i class="el-icon-s-custom"></i>
            <span>社区管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="header-right">
            <el-dropdown trigger="click" @command="handleCommand">
              <span class="el-dropdown-link">
                <el-avatar :size="32" :src="avatar"></el-avatar>
                {{ username }}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'AdminLayout',
  computed: {
    ...mapState('user', ['username', 'avatar'])
  },
  methods: {
    ...mapActions('user', ['logout']),
    async handleCommand(command) {
      if (command === 'logout') {
        try {
          await this.logout()
          this.$router.push('/login')
          this.$message.success('退出登录成功')
        } catch (error) {
          this.$message.error('退出登录失败')
        }
      } else if (command === 'profile') {
        this.$router.push('/admin/profile')
      }
    }
  }
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
}

.el-container {
  height: 100vh;
}

.el-aside {
  background-color: #304156;
  color: #fff;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
}

.logo a {
  color: #fff;
  text-decoration: none;
}

.logo i {
  font-size: 24px;
  margin-right: 10px;
}

.el-menu {
  border-right: none;
}

.el-header {
  background-color: #fff;
  color: #333;
  line-height: 60px;
  border-bottom: 1px solid #e6e6e6;
}

.header-right {
  float: right;
  cursor: pointer;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  color: #333;
}

.el-avatar {
  margin-right: 10px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style> 