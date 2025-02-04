<template>
  <div class="doctor-layout">
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
          unique-opened
        >
          <el-menu-item index="/doctor/dashboard">
            <i class="el-icon-s-home"></i>
            <span>工作台</span>
          </el-menu-item>
          <el-menu-item index="/doctor/appointments">
            <i class="el-icon-s-order"></i>
            <span>预约管理</span>
          </el-menu-item>
          <el-submenu index="consultations">
            <template slot="title">
              <i class="el-icon-chat-line-round"></i>
              <span>咨询服务</span>
            </template>
            <el-menu-item index="/doctor/consultations/sessions">
              <span>咨询会话</span>
            </el-menu-item>
            <el-menu-item index="/doctor/consultations/records">
              <span>咨询记录</span>
            </el-menu-item>
            <el-menu-item index="/doctor/consultations/resources">
              <span>咨询资源</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="content">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>内容管理</span>
            </template>
            <el-menu-item index="/doctor/articles">文章管理</el-menu-item>
            <el-menu-item index="/doctor/courses">课程管理</el-menu-item>
            <el-menu-item index="/doctor/tests">心理测试</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="header-right">
            <el-dropdown trigger="click" @command="handleCommand">
              <span class="el-dropdown-link">
                <el-avatar :size="32" :src="avatar"></el-avatar>
                <span class="username">{{ username }}</span>
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
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
  name: 'DoctorLayout',
  computed: {
    ...mapState('user', ['username', 'avatar'])
  },
  methods: {
    ...mapActions('user', ['logout']),
    handleRoute(route) {
      if (route && route !== this.$route.path) {
        this.$router.push(route)
      }
    },
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
        this.handleRoute('/doctor/profile')
      }
    }
  },
  data() {
    return {
      menus: [
        {
          icon: 'el-icon-monitor',
          title: '工作台',
          route: '/doctor/dashboard'
        },
        {
          icon: 'el-icon-date',
          title: '预约管理',
          route: '/doctor/appointments'
        },
        {
          icon: 'el-icon-chat-line-round',
          title: '咨询服务',
          children: [
            {
              title: '咨询会话',
              route: '/doctor/consultations/sessions'
            },
            {
              title: '咨询记录',
              route: '/doctor/consultations/records'
            },
            {
              title: '咨询资源',
              route: '/doctor/consultations/resources'
            }
          ]
        },
        {
          icon: 'el-icon-document',
          title: '内容管理',
          children: [
            {
              title: '文章管理',
              route: '/doctor/articles'
            },
            {
              title: '课程管理',
              route: '/doctor/courses'
            },
            {
              title: '心理测试',
              route: '/doctor/tests'
            }
          ]
        },
        {
          icon: 'el-icon-user',
          title: '个人中心',
          route: '/doctor/profile'
        }
      ]
    }
  }
}
</script>

<style scoped>
.doctor-layout {
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
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
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
  display: flex;
  justify-content: flex-end;
  padding: 0 20px;
}

.header-right {
  display: flex;
  align-items: center;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.username {
  margin: 0 8px;
  font-size: 14px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}

.el-menu-vertical {
  height: calc(100vh - 60px);
}
</style> 