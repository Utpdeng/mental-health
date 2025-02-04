<template>
  <div class="doctor-appointments">
    <el-card class="box-card">
      <div slot="header" class="page-header">
        <div class="header-title">
          <i class="el-icon-s-order"></i>
          <span>预约管理</span>
        </div>
        <div class="header-actions">
          <el-date-picker
            v-model="searchDate"
            type="date"
            placeholder="选择日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            @change="handleSearch"
            class="filter-item"
          />
          <el-select 
            v-model="searchStatus" 
            placeholder="预约状态" 
            @change="handleSearch"
            class="filter-item"
          >
            <el-option label="全部" value />
            <el-option label="待处理" value="pending" />
            <el-option label="已通过" value="confirmed" />
            <el-option label="已拒绝" value="cancelled" />
            <el-option label="已完成" value="completed" />
          </el-select>
          <el-button 
            type="primary" 
            icon="el-icon-refresh" 
            @click="fetchAppointments"
          >刷新</el-button>
        </div>
      </div>

      <el-table 
        :data="appointments" 
        v-loading="loading" 
        style="width: 100%"
        border
      >
        <el-table-column prop="id" label="预约ID" width="80" align="center" />
        <el-table-column prop="doctorName" label="医生姓名" align="center" />
        <el-table-column label="预约时间" align="center">
          <template slot-scope="scope">
            <div>{{ formatDate(scope.row.scheduleTime) }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="form" label="咨询类型" align="center">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.form === 'video' ? 'success' : 'primary'"
              size="medium"
            >
              {{ scope.row.form === 'video' ? '视频咨询' : '图文咨询' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" align="center" width="100">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template slot-scope="scope">
            <div class="action-buttons">
              <el-tooltip content="查看详情" placement="top">
                <el-button 
                  circle
                  size="mini" 
                  type="primary" 
                  icon="el-icon-view"
                  @click="handleView(scope.row)"
                ></el-button>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 预约详情对话框 -->
    <el-dialog 
      title="预约详情" 
      :visible.sync="dialogVisible" 
      width="500px"
      custom-class="appointment-dialog"
    >
      <div class="appointment-detail" v-if="currentAppointment">
        <div class="detail-item">
          <label>医生姓名：</label>
          <span>{{ currentAppointment.doctorName }}</span>
        </div>
        <div class="detail-item">
          <label>预约时间：</label>
          <span>{{ formatDate(currentAppointment.scheduleTime) }}</span>
        </div>
        <div class="detail-item">
          <label>咨询类型：</label>
          <span>
            <el-tag :type="getFormType(currentAppointment.form)">
              {{ getFormText(currentAppointment.form) }}
            </el-tag>
          </span>
        </div>
        <div class="detail-item">
          <label>支付状态：</label>
          <span>
            <el-tag :type="getPaymentStatusType(currentAppointment.paymentStatus)">
              {{ getPaymentStatusText(currentAppointment.paymentStatus) }}
            </el-tag>
          </span>
        </div>
        <div class="detail-item">
          <label>咨询状态：</label>
          <span>
            <el-tag :type="getStatusType(currentAppointment.status)">
              {{ getStatusText(currentAppointment.status) }}
            </el-tag>
          </span>
        </div>
        <div class="detail-item description">
          <label>咨询描述：</label>
          <p>{{ currentAppointment.content }}</p>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getAppointments, getAppointmentDetail } from '@/api/appointment'

export default {
  name: 'DoctorAppointments',
  data() {
    return {
      loading: false,
      appointments: [],
      searchDate: '',
      searchStatus: '',
      dialogVisible: false,
      currentAppointment: null,
      pageSize: 10,
      currentPage: 1,
      total: 0
    }
  },
  computed: {
    userId() {
      return this.$store.state.user.userId
    },
  },
  created() {
    this.fetchAppointments()
  },
  methods: {
    async fetchAppointments() {
      this.loading = true
      try {
        const { data } = await getAppointments(this.userId, null, this.searchStatus)
        this.appointments = data
        this.total = data.length
      } catch (error) {
        this.$message.error('获取预约列表失败')
      } finally {
        this.loading = false
      }
    },
    async handleView(row) {
      try {
        const { data } = await getAppointmentDetail(row.id)
        this.currentAppointment = data
        this.dialogVisible = true
      } catch (error) {
        this.$message.error('获取预约详情失败')
      }
    },
    formatDate(dateString) {
      const date = new Date(dateString)
      return date.toLocaleString()
    },
    handleSearch() {
      this.fetchAppointments()
    },
    getFormType(form) {
      const types = {
        video: 'success',
        graphic: 'primary',
        other: 'info'
      }
      return types[form] || 'info'
    },
    getFormText(form) {
      const texts = {
        video: '视频咨询',
        graphic: '图文咨询',
        other: '其他'
      }
      return texts[form] || form
    },
    getPaymentStatusType(status) {
      const types = {
        unpaid: 'warning',
        paid: 'success',
        refunded: 'danger'
      }
      return types[status] || 'info'
    },
    getPaymentStatusText(status) {
      const texts = {
        unpaid: '未支付',
        paid: '已支付',
        refunded: '已退款'
      }
      return texts[status] || status
    },
    getStatusType(status) {
      const types = {
        pending: 'warning',
        confirmed: 'success',
        cancelled: 'danger',
        completed: 'info'
      }
      return types[status] || 'info'
    },
    getStatusText(status) {
      const texts = {
        pending: '待处理',
        confirmed: '已通过',
        cancelled: '已拒绝',
        completed: '已完成'
      }
      return texts[status] || status
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.fetchAppointments()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchAppointments()
    }
  }
}
</script>

<style scoped>
.doctor-appointments {
  padding: 20px;
}

.box-card {
  margin: 0 auto;
  max-width: 1200px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-title {
  display: flex;
  align-items: center;
}

.header-title i {
  margin-right: 8px;
  font-size: 18px;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.filter-item {
  width: 150px;
}

.action-buttons {
  display: flex;
  gap: 5px;
  justify-content: center;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.appointment-detail .detail-item {
  margin-bottom: 15px;
}

.appointment-detail label {
  font-weight: bold;
  margin-right: 10px;
}

.appointment-detail .description p {
  margin-top: 5px;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 4px;
}
</style>
