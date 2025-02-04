<template>
  <div class="register-page">
    <div class="page-header">
      <div class="container">
        <h1>注册</h1>
        <p>创建一个新账户，开启您的心理健康之旅</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="register-form">
          <el-form 
            :model="form" 
            :rules="rules" 
            ref="form" 
            label-width="80px"
            @submit.native.prevent
          >
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="form.password" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input type="password" v-model="form.confirmPassword" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('form')" :loading="loading">注册</el-button>
            </el-form-item>
          </el-form>
          <p class="login-link">已有账户？<router-link to="/login">立即登录</router-link></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { register } from '@/api/user'

export default {
  name: 'RegisterPage',
  data() {
    // 密码验证规则
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.form.confirmPassword !== '') {
          this.$refs.form.validateField('confirmPassword')
        }
        callback()
      }
    }
    // 确认密码验证规则
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }

    return {
      loading: false,
      form: {
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' },
          { min: 6, message: '密码长度至少为 6 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          this.loading = true
          try {
            const response = await register({
              username: this.form.username,
              email: this.form.email,
              password: this.form.password
            })
            this.$message.success('注册成功！')
            // 注册成功后跳转到登录页
            this.$router.push('/login')
          } catch (error) {
            this.$message.error(error.message || '注册失败，请重试')
          } finally {
            this.loading = false
          }
        } else {
          return false
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

.register-form {
  background: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  margin: 0 auto;
}

.login-link {
  text-align: center;
  margin-top: 20px;
}

.login-link a {
  color: var(--primary-color);
  text-decoration: none;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>
