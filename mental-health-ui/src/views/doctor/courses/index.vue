<template>
  <div class="doctor-courses">
    <div class="page-header">
      <div class="header-title">
        <i class="el-icon-reading"></i>
        <span>课程管理</span>
      </div>
      <div class="header-actions">
        <el-button type="primary" icon="el-icon-plus" @click="handleCreate">
          创建课程
        </el-button>
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
      <el-table-column label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button 
            size="mini" 
            type="danger" 
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 课程表单对话框 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="60%"
    >
      <el-form 
        ref="courseForm" 
        :model="courseForm" 
        :rules="rules" 
        label-width="100px"
      >
        <el-form-item label="课程标题" prop="title">
          <el-input v-model="courseForm.title"></el-input>
        </el-form-item>
        <el-form-item label="课程类型" prop="type">
          <el-select v-model="courseForm.type" placeholder="请选择课程类型">
            <el-option label="心理健康" value="mental_health"></el-option>
            <el-option label="情绪管理" value="emotion_management"></el-option>
            <el-option label="人际关系" value="relationships"></el-option>
            <el-option label="职业发展" value="career"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程简介" prop="description">
          <el-input type="textarea" v-model="courseForm.description" rows="4"></el-input>
        </el-form-item>
        <el-form-item label="课程价格" prop="price">
          <el-input-number v-model="courseForm.price" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="课程封面">
          <el-upload
            class="course-cover-uploader"
            action="#"
            :show-file-list="false"
            :before-upload="beforeCoverUpload"
          >
            <img v-if="courseForm.coverUrl" :src="courseForm.coverUrl" class="course-cover">
            <i v-else class="el-icon-plus course-cover-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="课程资源">
          <el-upload
            class="course-resource-uploader"
            action="#"
            :file-list="courseForm.resources"
            multiple
          >
            <el-button size="small" type="primary">添加资源</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm" :loading="submitting">
          确定
        </el-button>
      </div>
    </el-dialog>

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
  name: 'DoctorCourses',
  data() {
    return {
      loading: false,
      submitting: false,
      dialogVisible: false,
      detailVisible: false,
      dialogTitle: '',
      currentCourse: null,
      courses: [
        {
          id: 1,
          title: '情绪管理入门课程',
          type: '情绪管理',
          description: '帮助您更好地认识和管理自己的情绪...',
          price: 199,
          status: 'published',
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
          type: '人际关系',
          description: '学习建立和维护健康的人际关系...',
          price: 299,
          status: 'pending',
          coverUrl: 'https://picsum.photos/300/201',
          createTime: '2024-01-16 14:30:00',
          resources: [
            { name: '课程大纲.pdf', type: 'pdf' },
            { name: '练习作业.docx', type: 'document' }
          ]
        }
      ],
      courseForm: {
        title: '',
        type: '',
        description: '',
        price: 0,
        coverUrl: '',
        resources: []
      },
      rules: {
        title: [
          { required: true, message: '请输入课程标题', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择课程类型', trigger: 'change' }
        ],
        description: [
          { required: true, message: '请输入课程简介', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请设置课程价格', trigger: 'blur' }
        ]
      }
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
    handleCreate() {
      this.dialogTitle = '创建课程'
      this.courseForm = {
        title: '',
        type: '',
        description: '',
        price: 0,
        coverUrl: '',
        resources: []
      }
      this.dialogVisible = true
    },
    handleEdit(course) {
      this.dialogTitle = '编辑课程'
      this.courseForm = { ...course }
      this.dialogVisible = true
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
    beforeCoverUpload(file) {
      // 模拟上传
      this.courseForm.coverUrl = URL.createObjectURL(file)
      return false
    },
    submitForm() {
      this.$refs.courseForm.validate(valid => {
        if (valid) {
          this.submitting = true
          setTimeout(() => {
            if (this.courseForm.id) {
              const index = this.courses.findIndex(item => item.id === this.courseForm.id)
              this.courses[index] = { ...this.courseForm }
            } else {
              this.courses.push({
                ...this.courseForm,
                id: Date.now(),
                status: 'pending',
                createTime: formatDate(new Date())
              })
            }
            this.dialogVisible = false
            this.submitting = false
            this.$message.success('操作成功')
          }, 1000)
        }
      })
    },
    formatDate
  }
}
</script>

<style scoped>
.doctor-courses {
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

.course-cover-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 300px;
  height: 180px;
}

.course-cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.course-cover-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 300px;
  height: 180px;
  line-height: 180px;
  text-align: center;
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
</style> 