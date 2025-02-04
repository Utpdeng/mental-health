<template>
  <div class="contact-page">
    <div class="page-header">
      <div class="container">
        <h1>联系咨询</h1>
        <p>随时为您提供专业的心理健康咨询服务</p>
      </div>
    </div>
    
    <div class="section">
      <div class="container">
        <el-row :gutter="30">
          <el-col :span="12">
            <div class="contact-form">
              <h2>在线预约</h2>
              <el-form :model="form" :rules="rules" ref="form" label-width="80px">
                <el-form-item label="姓名" prop="name">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                  <el-input v-model="form.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                  <el-input v-model="form.email"></el-input>
                </el-form-item>
                <el-form-item label="咨询类型" prop="type">
                  <el-select v-model="form.type" placeholder="请选择咨询类型">
                    <el-option label="个人咨询" value="personal"></el-option>
                    <el-option label="团体咨询" value="group"></el-option>
                    <el-option label="心理测评" value="assessment"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="留言" prop="message">
                  <el-input type="textarea" v-model="form.message" rows="4"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitForm('form')">提交预约</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="contact-info">
              <h2>联系方式</h2>
              <div class="info-item">
                <i class="fas fa-phone"></i>
                <div class="info-content">
                  <h3>咨询热线</h3>
                  <p>000-000-0000</p>
                </div>
              </div>
              <div class="info-item">
                <i class="fas fa-envelope"></i>
                <div class="info-content">
                  <h3>电子邮箱</h3>
                  <p>darrell@mentalhealth.com</p>
                </div>
              </div>
              <div class="info-item">
                <i class="fas fa-map-marker-alt"></i>
                <div class="info-content">
                  <h3>地址</h3>
                  <p>xxx</p>
                </div>
              </div>
              <div class="info-item">
                <i class="fas fa-clock"></i>
                <div class="info-content">
                  <h3>工作时间</h3>
                  <p>周一至周日 9:00-21:00</p>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ContactPage',
  data() {
    return {
      form: {
        name: '',
        phone: '',
        email: '',
        type: '',
        message: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择咨询类型', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$message.success('预约提交成功！我们会尽快与您联系。')
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
  margin-top: 10px;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  padding: 60px 0;
  text-align: center;
}

.contact-form {
  background: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.contact-info {
  background: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.info-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 30px;
}

.info-item i {
  font-size: 24px;
  color: var(--primary-color);
  margin-right: 20px;
  margin-top: 5px;
}

.info-content h3 {
  margin: 0 0 5px;
  color: var(--text-color);
}

.info-content p {
  margin: 0;
  color: #666;
}

h2 {
  margin-bottom: 30px;
  color: var(--text-color);
}

@media (max-width: 768px) {
  .el-row {
    display: flex;
    flex-direction: column;
  }
  
  .contact-form,
  .contact-info {
    margin-bottom: 30px;
  }
}
</style>
