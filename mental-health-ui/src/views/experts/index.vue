 <template>
  <div class="experts-page">
    <div class="page-header">
      <div class="container">
        <h1>专家指导</h1>
        <p>专业的心理咨询团队，助您解决心理困扰</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <el-row :gutter="30">
          <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="expert in experts" :key="expert.id">
            <div class="expert-card">
              <div class="expert-avatar">
                <img :src="expert.avatar" :alt="expert.name">
              </div>
              <h3>{{ expert.name }}</h3>
              <p class="expert-title">{{ expert.title }}</p>
              <p class="expert-specialty">擅长领域：{{ expert.specialty }}</p>
              <div class="expert-stats">
                <div class="stat-item">
                  <span class="stat-number">{{ expert.experience }}年</span>
                  <span class="stat-label">从业经验</span>
                </div>
                <div class="stat-item">
                  <span class="stat-number">{{ expert.cases }}+</span>
                  <span class="stat-label">服务案例</span>
                </div>
              </div>
              <el-button type="primary" @click="bookConsultation(expert)">预约咨询</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 预约弹窗 -->
    <el-dialog
      title="预约咨询"
      :visible.sync="dialogVisible"
      width="600px"
      :before-close="handleClose">
      <div v-if="selectedExpert">
        <div class="appointment-expert-info">
          <div class="expert-avatar-small">
            <img :src="selectedExpert.avatar" :alt="selectedExpert.name">
          </div>
          <div class="expert-info">
            <h3>{{ selectedExpert.name }}</h3>
            <p>{{ selectedExpert.title }} | 擅长：{{ selectedExpert.specialty }}</p>
          </div>
        </div>
        
        <div class="appointment-form">
          <h4>选择预约时间</h4>
          <div class="date-picker-container">
            <div class="date-selector">
              <p>日期</p>
              <el-date-picker
                v-model="appointmentDate"
                type="date"
                placeholder="选择日期"
                :picker-options="dateOptions">
              </el-date-picker>
            </div>
            
            <div class="time-selector">
              <p>时间段</p>
              <el-select v-model="appointmentTime" placeholder="选择时间段">
                <el-option
                  v-for="item in timeSlots"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled">
                </el-option>
              </el-select>
            </div>
          </div>
          
          <div class="appointment-type">
            <h4>咨询方式</h4>
            <el-radio-group v-model="consultationType">
              <el-radio :label="1">在线视频</el-radio>
              <el-radio :label="2">语音通话</el-radio>
              <el-radio :label="3">面对面咨询</el-radio>
            </el-radio-group>
          </div>
          
          <div class="appointment-remark">
            <h4>咨询说明</h4>
            <el-input
              type="textarea"
              :rows="4"
              placeholder="请简单描述您需要咨询的问题"
              v-model="consultationRemark">
            </el-input>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitAppointment">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Experts',
  data() {
    return {
      experts: [
        {
          id: 1,
          name: '张医生',
          title: '资深心理咨询师',
          specialty: '情感关系、职场压力',
          experience: 15,
          cases: 2000,
          avatar: 'https://picsum.photos/400/2012'
        },
        {
          id: 2,
          name: '李教授',
          title: '临床心理学专家',
          specialty: '抑郁症、焦虑障碍',
          experience: 20,
          cases: 3000,
          avatar: 'https://picsum.photos/400/2013'
        },
        {
          id: 3,
          name: '王医生',
          title: '青少年心理咨询师',
          specialty: '青少年心理家庭关系',
          experience: 10,
          cases: 1500,
          avatar: 'https://picsum.photos/400/2014'
        },
        {
          id: 4,
          name: '陈医生',
          title: '婚姻家庭咨询师',
          specialty: '婚姻关系、亲子教育',
          experience: 12,
          cases: 1800,
          avatar: 'https://picsum.photos/400/2017'
        }
      ],
      dialogVisible: false,
      selectedExpert: null,
      appointmentDate: '',
      appointmentTime: '',
      consultationType: 1,
      consultationRemark: '',
      timeSlots: [
        { value: '09:00-10:00', label: '09:00-10:00', disabled: false },
        { value: '10:00-11:00', label: '10:00-11:00', disabled: false },
        { value: '11:00-12:00', label: '11:00-12:00', disabled: false },
        { value: '14:00-15:00', label: '14:00-15:00', disabled: false },
        { value: '15:00-16:00', label: '15:00-16:00', disabled: false },
        { value: '16:00-17:00', label: '16:00-17:00', disabled: false }
      ],
      dateOptions: {
        disabledDate(time) {
          // 禁用过去的日期和30天后的日期
          return time.getTime() < Date.now() - 8.64e7 || time.getTime() > Date.now() + 30 * 8.64e7
        }
      }
    }
  },
  computed: {
    ...mapState('user', ['token'])
  },
  methods: {
    bookConsultation(expert) {
      if (!this.token) {
        this.$confirm('请先登录后再进行预约', '提示', {
          confirmButtonText: '去登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push({
            path: '/login',
            query: { redirect: this.$route.fullPath }
          })
        }).catch(() => {
          // 用户取消操作
        })
      } else {
        this.selectedExpert = expert
        this.dialogVisible = true
      }
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    submitAppointment() {
      if (!this.appointmentDate) {
        this.$message.error('请选择预约日期')
        return
      }
      if (!this.appointmentTime) {
        this.$message.error('请选择预约时间段')
        return
      }

      // 这里是前端模拟，实际项目中会调用API
      setTimeout(() => {
        this.$message.success(`预约${this.selectedExpert.name}咨询成功！我们会尽快与您联系。`)
        this.dialogVisible = false
        this.resetForm()
      }, 500)
    },
    resetForm() {
      this.appointmentDate = ''
      this.appointmentTime = ''
      this.consultationType = 1
      this.consultationRemark = ''
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

.expert-card {
  background: white;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  transition: transform 0.3s ease;
}

.expert-card:hover {
  transform: translateY(-5px);
}

.expert-avatar {
  width: 120px;
  height: 120px;
  margin: 0 auto 20px;
  border-radius: 50%;
  overflow: hidden;
}

.expert-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.expert-title {
  color: var(--primary-color);
  margin: 10px 0;
}

.expert-specialty {
  color: #666;
  margin-bottom: 15px;
}

.expert-stats {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin: 15px 0;
  padding: 15px 0;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
}

.stat-item {
  display: flex;
  flex-direction: column;
}

.stat-number {
  font-size: 1.2rem;
  font-weight: bold;
  color: var(--primary-color);
}

.stat-label {
  font-size: 0.9rem;
  color: #666;
}

.el-button {
  width: 100%;
  margin-top: 15px;
}

/* 预约弹窗样式 */
.appointment-expert-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.expert-avatar-small {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 15px;
}

.expert-avatar-small img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.expert-info h3 {
  margin: 0 0 5px 0;
}

.expert-info p {
  margin: 0;
  color: #666;
}

.appointment-form h4 {
  margin: 20px 0 10px;
  color: #333;
}

.date-picker-container {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.date-selector, .time-selector {
  width: 50%;
}

.appointment-type, .appointment-remark {
  margin-top: 20px;
}

.el-select {
  width: 100%;
}
</style> 