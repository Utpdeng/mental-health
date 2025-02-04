<template>
  <div class="app-container">
    <el-card>
      <div class="filter-container" style="display: flex; justify-content: flex-end;">
        <el-input
          v-model="searchQuery"
          placeholder="搜索医生"
          style="width: 200px; margin-right: 10px;"
          class="filter-item"
          @keyup.enter.native="handleSearch"
        />
        <el-select
          v-model="statusFilter"
          placeholder="状态"
          clearable
          class="filter-item"
          style="width: 130px; margin-right: 10px;"
          @change="handleSearch"
        >
          <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-button
          class="filter-item"
          type="primary"
          icon="el-icon-search"
          @click="handleSearch"
        >
          搜索
        </el-button>
        <el-button
          class="filter-item"
          type="success"
          icon="el-icon-plus"
          @click="handleAdd"
        >
          添加医生
        </el-button>
      </div>

      <div style="display: flex; flex-direction: column;">
        <el-table
          v-loading="loading"
          :data="doctors"
          border
          fit
          highlight-current-row
          style="width: 100%;"
        >
          <el-table-column label="姓名" prop="name" width="220" />
          <el-table-column label="职称" prop="title" width="250" />
          <el-table-column label="擅长领域" prop="specialty" width="350" />
          <el-table-column label="状态" width="200">
            <template slot-scope="{row}">
              <el-tag :type="row.status === 'active' ? 'success' : row.status === 'resigned' ? 'danger' : 'warning'">
                {{ row.status === 'active' ? '在职' : row.status === 'resigned' ? '离职' : '休假' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
            <template slot-scope="{row}">
              <el-button
                type="primary"
                size="mini"
                @click="handleEdit(row)"
              >
                编辑
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(row)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <div style="display: flex; justify-content: flex-end;">
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="currentPage"
            :limit.sync="pageSize"
            @pagination="fetchDoctors"
          />
        </div>
      </div>
    </el-card>

    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <el-form
        ref="editForm"
        :model="editForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editForm.name" />
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-input v-model="editForm.title" />
        </el-form-item>
        <el-form-item label="专业" prop="specialty">
          <el-input v-model="editForm.specialty" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="editForm.status" placeholder="请选择">
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="简介" prop="introduction">
          <el-input
            v-model="editForm.introduction"
            type="textarea"
            :rows="4"
          />
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="editForm.avatar" :src="editForm.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getPsychologistsList, getPsychologistDetail, updatePsychologist } from '@/api/doctor'
import Pagination from '@/components/Pagination'

export default {
  name: 'DoctorManagement',
  components: {
    Pagination
  },
  data() {
    return {
      loading: false,
      searchQuery: '',
      statusFilter: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      doctors: [],
      dialogVisible: false,
      dialogTitle: '',
      editForm: {
        id: null,
        name: '',
        title: '',
        specialty: '',
        status: 'active',
        introduction: '',
        avatar: ''
      },
      submitLoading: false,
      rules: {
        // 保持原有验证规则不变
      },
      statusOptions: [
        { value: 'active', label: '在职' },
        { value: 'resigned', label: '离职' },
        { value: 'leave', label: '休假' }
      ]
    }
  },
  computed: {
    // 保持原有computed属性不变
  },
  created() {
    this.fetchDoctors()
  },
  methods: {
    async fetchDoctors() {
      this.loading = true
      try {
        const params = {
          search: this.searchQuery,
          employment_status: this.statusFilter || null,
          page: this.currentPage,
          pageSize: this.pageSize
        }
        const res = await getPsychologistsList(params)
        console.log('返回的列表是：' ,res);
        
        if (res.status == 200) {
          this.doctors = res.data.map(d => ({
            psychologistId: d.psychologistId,
            name: d.nickname,
            title: d.title,
            specialty: d.specialty,
            status: d.employmentStatus,
            introduction: d.introduction,
            avatar: d.avatarUrl
          }))
          this.total = this.doctors.length
          console.log('doctors: ',this.doctors);
        }
      } catch (error) {
        console.error('获取医生列表失败:', error)
        this.$message.error('获取医生列表失败')
      } finally {
        this.loading = false
      }
    },
    
    async handleEdit(row) {
      this.dialogTitle = '编辑医生信息'
      try {
        const res = await getPsychologistDetail(row.psychologistId)
        if (res.status == 200) {
          this.editForm = {
            psychologistId: res.data.psychologistId,
            name: res.data.nickname,
            title: res.data.title,
            specialty: res.data.specialty,
            status: res.data.status,
            introduction: res.data.introduction,
            avatar: res.data.avatar
          }
          this.dialogVisible = true
        }
      } catch (error) {
        console.error('获取医生详情失败:', error)
        this.$message.error('获取医生详情失败')
      }
    },

    async submitForm() {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          this.submitLoading = true
          try {
            const data = {
              psychologistId: this.editForm.psychologistId,
              name: this.editForm.name,
              title: this.editForm.title,
              specialty: this.editForm.specialty,
              introduction: this.editForm.introduction,
              status: this.editForm.status,
              avatar: this.editForm.avatar
            }
            
            const res = await updatePsychologist(data)
            if (res.status === 200) {
              this.$message.success('操作成功')
              this.dialogVisible = false
              this.fetchDoctors() // 刷新列表
            }
          } catch (error) {
            console.error('操作失败:', error)
            this.$message.error('操作失败')
          } finally {
            this.submitLoading = false
          }
        }
      })
    },

    // 保持其他方法不变
    handleSearch() {
      this.currentPage = 1
      this.fetchDoctors()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.fetchDoctors()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchDoctors()
    },
    handleAdd() {
      this.dialogTitle = '添加医生'
      this.editForm = {
        psychologistId: null,
        name: '',
        title: '',
        specialty: '',
        status: 'active',
        introduction: '',
        avatar: ''
      }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该医生?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.doctors = this.doctors.filter(doctor => doctor.psychologistId !== row.psychologistId)
        this.$message.success('删除成功')
      }).catch(() => {})
    },
    handleAvatarSuccess(res, file) {
      this.editForm.avatar = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>

<style scoped>
/* 保持原有样式不变 */
</style>
