<template>
    <div class="user-layout">
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
            <el-menu-item index="/user/appointed">
              <i class="el-icon-s-custom"></i>
              <span>预约记录</span>
            </el-menu-item>
            <el-menu-item index="/user/consultation">
              <i class="el-icon-s-custom"></i>
              <span>咨询会话</span>
            </el-menu-item>
            <el-menu-item index="/user/record">
              <i class="el-icon-s-custom"></i>
              <span>咨询记录</span>
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
          this.$router.push('/user/profile')
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .user-layout {
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