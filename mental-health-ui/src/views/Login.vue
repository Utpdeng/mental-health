<template>
  <div class="login-page">
    <div class="login-container">
      <el-card class="login-card">
        <div class="login-header">
          <Logo :showText="false" />
          <h2>登录</h2>
        </div>
        
        <el-form :model="loginForm" :rules="rules" ref="loginForm">
          <el-form-item prop="username">
            <el-input 
              v-model="loginForm.username" 
              prefix-icon="el-icon-user"
              placeholder="用户名">
            </el-input>
          </el-form-item>
          
          <el-form-item prop="password">
            <el-input 
              v-model="loginForm.password" 
              prefix-icon="el-icon-lock"
              type="password"
              placeholder="密码">
            </el-input>
          </el-form-item>
          
          <el-form-item prop="role">
            <el-select v-model="loginForm.role" placeholder="请选择角色" style="width: 100%">
              <el-option label="管理员" value="admin"></el-option>
              <el-option label="教师" value="teacher"></el-option>
              <el-option label="学生" value="student"></el-option>
            </el-select>
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="handleLogin" style="width: 100%">
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
import Logo from '@/components/common/Logo.vue'

export default {
  name: 'Login',
  components: {
    Logo
  },
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        role: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.$store.dispatch('permission/login', {
            name: this.loginForm.username,
            role: this.loginForm.role
          })
          
          const redirectPath = this.$route.query.redirect || this.getDefaultRedirect()
          this.$router.push(redirectPath)
        }
      })
    },
    getDefaultRedirect() {
      const roleRedirectMap = {
        admin: '/admin',
        teacher: '/teacher',
        student: '/student'
      }
      return roleRedirectMap[this.loginForm.role] || '/'
    }
  }
}
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  @include flex(row, center, center);
  background: linear-gradient(135deg, $primary-color, $secondary-color);
  padding: 1rem;
}

.login-container {
  width: 100%;
  max-width: 400px;
}

.login-card {
  .login-header {
    text-align: center;
    margin-bottom: 2rem;

    .logo {
      width: 80px;
      height: 80px;
      margin-bottom: 1rem;
    }

    h2 {
      color: $gray-800;
      font-size: 1.5rem;
    }
  }
}
</style> 