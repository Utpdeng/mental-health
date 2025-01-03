<template>
  <div class="app-layout">
    <header class="app-header" :class="{ 'header-shadow': scrolled }">
      <div class="header-content">
        <div class="logo">
          <Logo />
        </div>
        <nav class="nav-menu">
          <el-menu mode="horizontal" :router="true" :default-active="activeMenu">
            <el-menu-item index="/">首页</el-menu-item>
            <el-menu-item index="/services">服务项目</el-menu-item>
            <el-menu-item index="/about">关于我们</el-menu-item>
            <el-menu-item index="/resources">联系资源</el-menu-item>
            <template v-if="isLoggedIn">
              <el-submenu index="workspace">
                <template slot="title">工作台</template>
                <el-menu-item v-if="isAdmin" index="/admin">管理控制台</el-menu-item>
                <el-menu-item v-if="isTeacher" index="/teacher">教师工作台</el-menu-item>
                <el-menu-item v-if="isStudent" index="/student">学生中心</el-menu-item>
              </el-submenu>
            </template>
          </el-menu>
        </nav>
        <div class="header-right">
          <el-dropdown v-if="isLoggedIn" @command="handleCommand">
            <span class="el-dropdown-link">
              <el-avatar :size="32" :src="userAvatar"></el-avatar>
              <span class="username">{{ username }}</span>
              <i class="el-icon-arrow-down"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="profile">个人信息</el-dropdown-item>
              <el-dropdown-item command="settings">设置</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button 
            v-else 
            type="primary" 
            class="login-btn" 
            @click="login">
            <i class="el-icon-user"></i>
            登录
          </el-button>
        </div>
      </div>
    </header>

    <main class="app-main">
      <transition name="fade" mode="out-in">
        <router-view></router-view>
      </transition>
    </main>

    <footer class="app-footer">
      <div class="footer-content">
        <p>&copy; {{ currentYear }} 校园心理健康平台. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script>
import Logo from '@/components/common/Logo.vue'
import { mapGetters } from 'vuex'

export default {
  name: 'Layout',
  components: {
    Logo
  },
  data() {
    return {
      scrolled: false,
      currentYear: new Date().getFullYear()
    }
  },
  computed: {
    ...mapGetters('permission', [
      'isLoggedIn'
    ]),
    username() {
      return this.$store.state.permission.user?.name || ''
    },
    userAvatar() {
      return this.$store.state.permission.user?.avatar || ''
    },
    isAdmin() {
      return this.$store.state.permission.user?.role === 'admin'
    },
    isTeacher() {
      return this.$store.state.permission.user?.role === 'teacher'
    },
    isStudent() {
      return this.$store.state.permission.user?.role === 'student'
    },
    activeMenu() {
      return this.$route.path
    }
  },
  methods: {
    async handleCommand(command) {
      switch (command) {
        case 'profile':
          await this.$router.push('/profile').catch(() => {})
          break
        case 'settings':
          await this.$router.push('/settings').catch(() => {})
          break
        case 'logout':
          try {
            await this.$store.dispatch('permission/logout')
            if (this.$route.meta.requiresAuth) {
              await this.$router.push('/login').catch(() => {})
            } else if (this.$route.path === '/') {
              this.$forceUpdate()
            } else {
              await this.$router.push('/').catch(() => {})
            }
          } catch (error) {
            console.error('Logout error:', error)
            this.$message.error('退出登录失败，请重试')
          }
          break
      }
    },
    login() {
      this.$router.push('/login').catch(() => {})
    },
    handleScroll() {
      this.scrolled = window.scrollY > 0
    }
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll)
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll)
  }
}
</script>

<style lang="scss" scoped>
.app-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.app-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: $header-height;
  background-color: $white;
  z-index: 1000;
  @include transition;

  &.header-shadow {
    @include box-shadow(1);
  }

  .header-content {
    @include flex(row, space-between, center);
    max-width: $container-max-width;
    height: 100%;
    margin: 0 auto;
    padding: 0 1rem;

    .logo {
      @include flex(row, flex-start, center);
      gap: 1rem;
      min-width: 200px;
    }

    .nav-menu {
      flex: 1;
      display: flex;
      justify-content: flex-end;

      .el-menu {
        border-bottom: none;
        background: transparent;

        &.el-menu--horizontal {
          display: flex;
          justify-content: flex-end;

          > .el-menu-item {
            height: $header-height;
            line-height: $header-height;
            border-bottom: none;
            font-size: 1rem;
            padding: 0 1.5rem;

            &:hover, &:focus {
              color: $primary-color;
            }

            &.is-active {
              color: $primary-color;
              border-bottom: 2px solid $primary-color;
            }
          }
        }
      }
    }

    .header-right {
      margin-left: 2rem;
      min-width: 120px;

      .el-dropdown-link {
        @include flex(row, center, center);
        gap: 0.5rem;
        cursor: pointer;

        .username {
          margin: 0 0.5rem;
        }
      }

      .login-btn {
        padding: 10px 24px;
        font-size: 1rem;
        border-radius: 20px;
        font-weight: 500;
        @include transition;

        i {
          margin-right: 4px;
          font-size: 16px;
        }

        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 4px 12px rgba($primary-color, 0.2);
        }

        &:active {
          transform: translateY(0);
        }
      }
    }
  }
}

.app-main {
  flex: 1;
  margin-top: $header-height;
  padding: 2rem 0;
}

.app-footer {
  background-color: $gray-800;
  color: $gray-300;
  padding: 2rem 0;

  .footer-content {
    max-width: $container-max-width;
    margin: 0 auto;
    padding: 0 1rem;
    text-align: center;
  }
}
</style> 