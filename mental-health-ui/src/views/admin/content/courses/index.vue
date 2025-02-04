<template>
  <div class="course-management">
    <div class="page-header">
      <div class="header-title">
        <i class="el-icon-reading"></i>
        <span>课程管理</span>
      </div>
      <div class="header-actions">
        <el-input
          v-model="searchQuery"
          placeholder="搜索课程"
          prefix-icon="el-icon-search"
          clearable
          class="filter-item"
        />
        <el-select
          v-model="typeFilter"
          placeholder="课程类型"
          clearable
          class="filter-item"
        >
          <el-option label="心理健康" value="心理健康" />
          <el-option label="情绪管理" value="情绪管理" />
          <el-option label="人际关系" value="人际关系" />
          <el-option label="职业发展" value="职业发展" />
        </el-select>
        <el-select
          v-model="statusFilter"
          placeholder="状态"
          clearable
          class="filter-item"
        >
          <el-option label="全部" value="" />
          <el-option label="已发布" value="published" />
          <el-option label="待审核" value="pending" />
          <el-option label="已拒绝" value="rejected" />
        </el-select>
      </div>
    </div>

    <!-- 课程列表 -->
    <el-table :data="courses" v-loading="loading" border style="width: 100%">
      <el-table-column prop="title" label="课程标题" min-width="200">
        <template slot-scope="scope">
          <el-button type="text" @click="viewDetail(scope.row)">
            {{ scope.row.title }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="doctor" label="发布医生" width="120" />
      <el-table-column prop="type" label="课程类型" width="120">
        <template slot-scope="scope">
          <el-tag :type="getTypeTag(scope.row.type)">{{ scope.row.type }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="价格" width="100">
        <template slot-scope="scope">
          ¥{{ scope.row.price }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180">
        <template slot-scope="scope">
          {{ formatDate(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250" fixed="right">
        <template slot-scope="scope">
          <el-button-group v-if="scope.row.status === 'pending'">
            <el-button 
              size="mini" 
              type="success"
              @click="handleApprove(scope.row)"
            >通过</el-button>
            <el-button 
              size="mini" 
              type="warning"
              @click="handleReject(scope.row)"
            >拒绝</el-button>
          </el-button-group>
          <el-button 
            size="mini" 
            type="danger" 
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 课程详情对话框 -->
    <el-dialog
      title="课程详情"
      :visible.sync="detailVisible"
      width="60%"
    >
      <div v-if="currentCourse" class="course-detail">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="课程标题">
            {{ currentCourse.title }}
          </el-descriptions-item>
          <el-descriptions-item label="发布医生">
            {{ currentCourse.doctor }}
          </el-descriptions-item>
          <el-descriptions-item label="课程类型">
            <el-tag :type="getTypeTag(currentCourse.type)">
              {{ currentCourse.type }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="课程简介">
            {{ currentCourse.description }}
          </el-descriptions-item>
          <el-descriptions-item label="课程价格">
            ¥{{ currentCourse.price }}
          </el-descriptions-item>
          <el-descriptions-item label="课程封面">
            <img :src="currentCourse.coverUrl" class="detail-cover">
          </el-descriptions-item>
          <el-descriptions-item label="课程资源">
            <ul class="resource-list">
              <li v-for="(resource, index) in currentCourse.resources" :key="index">
                <i :class="getResourceIcon(resource.type)"></i>
                {{ resource.name }}
                <el-button 
                  type="text" 
                  size="mini"
                  @click="previewResource(resource)"
                >预览</el-button>
              </li>
            </ul>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { formatDate } from '@/utils/date'

export default {
  name: 'CourseManagement',
  data() {
    return {
      loading: false,
      searchQuery: '',
      typeFilter: '',
      statusFilter: '',
      detailVisible: false,
      currentCourse: null,
      courses: [
        {
          id: 1,
          title: '情绪管理入门课程',
          doctor: '张医生',
          type: '情绪管理',
          description: '帮助您更好地认识和管理自己的情绪...',
          price: 199,
          status: 'pending',
          coverUrl: 'https://picsum.photos/300/200',
          createTime: '2024-01-15 10:00:00',
          resources: [
            { name: '课程介绍.pdf', type: 'pdf' },
            { name: '第一课.mp4', type: 'video' }
          ]
        },
        {
          id: 2,
          title: '人际关系提升课程',
          doctor: '李医生',
          type: '人际关系',
          description: '学习建立和维护健康的人际关系...',
          price: 299,
          status: 'published',
          coverUrl: 'https://picsum.photos/300/201',
          createTime: '2024-01-16 14:30:00',
          resources: [
            { name: '课程大纲.pdf', type: 'pdf' },
            { name: '练习作业.docx', type: 'document' }
          ]
        }
      ]
    }
  },
  methods: {
    getTypeTag(type) {
      const tags = {
        mental_health: 'success',
        emotion_management: 'warning',
        relationships: 'info',
        career: 'primary'
      }
      return tags[type] || ''
    },
    getStatusType(status) {
      const types = {
        published: 'success',
        pending: 'warning',
        rejected: 'danger'
      }
      return types[status] || 'info'
    },
    getStatusText(status) {
      const texts = {
        published: '已发布',
        pending: '待审核',
        rejected: '已拒绝'
      }
      return texts[status] || status
    },
    getResourceIcon(type) {
      const icons = {
        pdf: 'el-icon-document',
        video: 'el-icon-video-camera',
        document: 'el-icon-tickets'
      }
      return icons[type] || 'el-icon-document'
    },
    handleApprove(course) {
      this.$confirm('确认通过该课程?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        const index = this.courses.findIndex(item => item.id === course.id)
        this.courses[index].status = 'published'
        this.$message.success('已通过')
      })
    },
    handleReject(course) {
      this.$confirm('确认拒绝该课程?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.courses.findIndex(item => item.id === course.id)
        this.courses[index].status = 'rejected'
        this.$message.success('已拒绝')
      })
    },
    handleDelete(course) {
      this.$confirm('确认删除该课程?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.courses = this.courses.filter(item => item.id !== course.id)
        this.$message.success('删除成功')
      })
    },
    viewDetail(course) {
      this.currentCourse = course
      this.detailVisible = true
    },
    previewResource(resource) {
      this.$message.info(`预览${resource.name}`)
    },
    formatDate
  }
}
</script>

<style scoped>
.course-management {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-title {
  font-size: 18px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.filter-item {
  width: 200px;
}

.detail-cover {
  max-width: 300px;
  max-height: 180px;
  border-radius: 4px;
}

.resource-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.resource-list li {
  display: flex;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}

.resource-list li:last-child {
  border-bottom: none;
}

.resource-list i {
  margin-right: 8px;
  color: #409EFF;
}

.resource-list .el-button {
  margin-left: auto;
}
</style>
