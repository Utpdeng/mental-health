<template>
  <div class="consultation-records">
    <div class="records-header">
      <div class="header-title">
        <i class="el-icon-document"></i>
        <span>咨询记录</span>
      </div>
      <div class="header-actions">
        <el-button 
          type="primary" 
          icon="el-icon-refresh" 
          size="small"
          @click="fetchRecords"
        >刷新</el-button>
      </div>
    </div>

    <el-table 
      :data="records" 
      v-loading="loading"
      style="width: 100%"
    >
      <el-table-column label="患者信息" width="200">
        <template slot-scope="scope">
          <div class="patient-info">
            <el-avatar :size="40" :src="scope.row.avatarUrl"></el-avatar>
            <div class="patient-detail">
              <span class="patient-name">{{ scope.row.username }}</span>
              <el-tag size="mini" type="info">{{ scope.row.gender === 'male' ? '男' : '女' }}</el-tag>
            </div>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="咨询方式" width="120">
        <template slot-scope="scope">
          <el-tag :type="scope.row.form === 'video' ? 'success' : 'primary'">
            {{ scope.row.form === 'video' ? '视频咨询' : '图文咨询' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="time" label="时长" width="100">
        <template slot-scope="scope">
          {{ scope.row.time }}分钟
        </template>
      </el-table-column>

      <el-table-column label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="咨询时间" width="180">
        <template slot-scope="scope">
          {{ formatDateTime(scope.row.createdAt) }}
        </template>
      </el-table-column>

      <el-table-column prop="feedback" label="反馈意见">
        <template slot-scope="scope">
          <el-input
            v-if="scope.row.isEditing"
            v-model="scope.row.editingFeedback"
            type="textarea"
            :rows="2"
            placeholder="请输入反馈意见"
          ></el-input>
          <span v-else>{{ scope.row.feedback || '-' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="150" fixed="right">
        <template slot-scope="scope">
          <div class="action-buttons">
            <template v-if="scope.row.isEditing">
              <el-button 
                type="success" 
                size="mini" 
                @click="saveFeedback(scope.row)"
              >保存</el-button>
              <el-button 
                size="mini" 
                @click="cancelEdit(scope.row)"
              >取消</el-button>
            </template>
            <template v-else>
              <el-button 
                type="primary" 
                size="mini" 
                @click="viewDetail(scope.row)"
              >详情</el-button>
              <el-button 
                type="warning" 
                size="mini" 
                @click="editFeedback(scope.row)"
              >编辑</el-button>
            </template>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- 详情对话框 -->
    <el-dialog
      title="咨询详情"
      :visible.sync="dialogVisible"
      width="600px"
    >
      <div v-loading="detailLoading">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="患者姓名">{{ currentDetail.username }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ currentDetail.gender === 'male' ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item label="咨询方式">{{ currentDetail.form === 'video' ? '视频咨询' : '图文咨询' }}</el-descriptions-item>
          <el-descriptions-item label="咨询时长">{{ currentDetail.time }}分钟</el-descriptions-item>
          <el-descriptions-item label="开始时间">{{ formatDateTime(currentDetail.createdAt) }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ formatDateTime(currentDetail.updatedAt) }}</el-descriptions-item>
          <el-descriptions-item label="反馈意见" :span="2">{{ currentDetail.feedback || '-' }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getConsultationList, updateConsultation, getConsultationById } from '@/api/consultation'

export default {
  name: 'ConsultationRecords',
  data() {
    return {
      loading: false,
      records: [],
      dialogVisible: false,
      detailLoading: false,
      currentDetail: {}
    }
  },
  computed: {
    doctorId() {
      return this.$store.state.user.userId
    }
  },
  created() {
    this.fetchRecords()
  },
  methods: {
    formatDateTime(datetime) {
      if (!datetime) return '-'
      const date = new Date(datetime)
      return date.toLocaleString()
    },
    getStatusType(status) {
      const types = {
        confirmed: 'success',
        completed: 'info'
      }
      return types[status] || 'info'
    },
    getStatusText(status) {
      const texts = {
        confirmed: '进行中',
        completed: '已完成'
      }
      return texts[status] || status
    },
    async fetchRecords() {
      this.loading = true
      try {
        const res = await getConsultationList({ doctorId: this.doctorId })
        if (res.status === '200') {
          this.records = res.data.map(record => ({
            ...record,
            isEditing: false,
            editingFeedback: record.feedback
          }))
        }
      } catch (error) {
        this.$message.error('获取记录失败')
      } finally {
        this.loading = false
      }
    },
    async viewDetail(record) {
      this.dialogVisible = true
      this.detailLoading = true
      try {
        console.log('获取详情，ID:', record.id)
        const res = await getConsultationById(record.id)
        console.log('详情响应:', res)
        if (res.status === '200') {
          this.currentDetail = res.data
        }
      } catch (error) {
        console.error('获取详情失败:', error)
        this.$message.error('获取详情失败')
      } finally {
        this.detailLoading = false
      }
    },
    editFeedback(record) {
      record.isEditing = true
      record.editingFeedback = record.feedback
    },
    cancelEdit(record) {
      record.isEditing = false
      record.editingFeedback = record.feedback
    },
    async saveFeedback(record) {
      try {
        const res = await updateConsultation(record.id, record.editingFeedback)
        if (res.status === '200') {
          record.feedback = record.editingFeedback
          record.isEditing = false
          this.$message.success('更新成功')
        }
      } catch (error) {
        this.$message.error('更新失败')
      }
    }
  }
}
</script>

<style scoped>
.consultation-records {
  padding: 20px;
}

.records-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-title {
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 8px;
}

.header-title i {
  color: var(--primary-color);
}

.patient-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.patient-detail {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.patient-name {
  font-weight: bold;
}

.action-buttons {
  display: flex;
  gap: 5px;
}

:deep(.el-table) {
  margin-top: 20px;
}

:deep(.el-descriptions) {
  padding: 20px;
}
</style> 