<template>
  <div class="teacher-dashboard">
    <div class="container">
      <el-row :gutter="20">
        <el-col :span="24">
          <h1 class="page-title">教师工作台</h1>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :xs="24" :md="16">
          <el-card class="schedule-card">
            <div slot="header">
              <span>今日咨询安排</span>
            </div>
            <el-table :data="schedules" style="width: 100%">
              <el-table-column prop="time" label="时间" width="180"></el-table-column>
              <el-table-column prop="student" label="学生"></el-table-column>
              <el-table-column prop="status" label="状态">
                <template slot-scope="scope">
                  <el-tag :type="scope.row.status === '已确认' ? 'success' : 'warning'">
                    {{ scope.row.status }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                  <el-button type="text" @click="handleView(scope.row)">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>

        <el-col :xs="24" :md="8">
          <el-card class="quick-actions">
            <div slot="header">
              <span>快捷操作</span>
            </div>
            <div class="action-list">
              <el-button type="primary" icon="el-icon-plus" plain>新增咨询</el-button>
              <el-button type="success" icon="el-icon-document" plain>查看报告</el-button>
              <el-button type="warning" icon="el-icon-message" plain>发送通知</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TeacherDashboard',
  data() {
    return {
      schedules: [
        { time: '09:00-10:00', student: '张三', status: '已确认' },
        { time: '10:30-11:30', student: '李四', status: '待确认' },
        { time: '14:00-15:00', student: '王五', status: '已确认' }
      ]
    }
  },
  methods: {
    handleView(row) {
      console.log('查看详情:', row)
    }
  }
}
</script>

<style lang="scss" scoped>
.teacher-dashboard {
  padding: 2rem 0;

  .page-title {
    font-size: 2rem;
    color: $gray-800;
    margin-bottom: 2rem;
  }

  .schedule-card {
    margin-bottom: 1rem;
  }

  .quick-actions {
    margin-bottom: 1rem;

    .action-list {
      @include flex(column, flex-start, stretch);
      gap: 1rem;

      .el-button {
        width: 100%;
      }
    }
  }
}
</style> 