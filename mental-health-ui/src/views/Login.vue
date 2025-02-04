<template>
  <div class="login-page">
    <div class="page-header">
      <div class="container">
        <h1>登录</h1>
        <p>欢迎回来，请登录您的账户</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="login-form">
          <el-form 
            :model="form" 
            :rules="rules" 
            ref="form" 
            label-width="80px"
            @submit.native.prevent
          >
            <el-form-item label="账号" prop="username">
              <el-input 
                v-model="form.username" 
                placeholder="请输入用户名或邮箱"
              ></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input 
                type="password" 
                v-model="form.password" 
                placeholder="请输入密码"
                show-password
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button 
                type="primary" 
                :loading="loading" 
                @click="submitForm('form')"
                style="width: 100%"
              >登录</el-button>
            </el-form-item>
          </el-form>
          <div class="form-footer">
            <p class="register-link">还没有账户？<router-link to="/register">立即注册</router-link></p>
            <el-button type="text">忘记密码？</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import { login } from '@/api/user' // 导入 login 接口

export default {
  name: 'LoginPage',
  data() {
    return {
      loading: false,
      form: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名或邮箱', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    ...mapActions('user', ['getUserInfo']),
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          this.loading = true
          try {
            // 直接调用登录接口
            const response = await login(this.form)
            
            // 保存登录信息到 vuex
            await this.$store.commit('user/SET_TOKEN', response.data.token)
            await this.$store.commit('user/SET_USER_ID', response.data.userId)
            
            // 存储到 localStorage
            localStorage.setItem('token', response.data.token)
            localStorage.setItem('userId', response.data.userId)
            
            // 获取用户信息
            await this.getUserInfo()
            
            this.$message.success('登录成功')
            
            const role = this.$store.state.user.role
            console.log('Redirecting based on role:', role)
            
            switch (role) {
              case 'admin':
                this.$router.push('/admin')
                break
              case 'doctor':
                this.$router.push('/doctor/dashboard')
                break
              default:
                this.$router.push('/')
            }
          } catch (error) {
            this.$message.error(error.message || '登录失败')
          } finally {
            this.loading = false
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.page-header {
  margin-top: 70px;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  padding: 60px 0;
  text-align: center;
}

.login-form {
  background: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  margin: 0 auto;
}

.form-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.register-link {
  margin: 0;
}

.register-link a,
.el-button--text {
  color: var(--primary-color);
  text-decoration: none;
}

.register-link a:hover,
.el-button--text:hover {
  text-decoration: underline;
}
</style>
