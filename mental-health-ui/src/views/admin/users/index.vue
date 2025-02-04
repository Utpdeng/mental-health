<template>
  <div class="users-management">
    <el-card>
      <div slot="header" class="header">
        <span>用户管理</span>
        <div class="search-box">
          <el-input
            v-model="searchQuery"
            placeholder="搜索用户名/邮箱"
            style="width: 200px; margin-right: 10px"
            clearable
            @clear="handleSearch"
            @keyup.enter.native="handleSearch"
          >
            <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
          </el-input>
          <el-select v-model="roleFilter" placeholder="角色" style="width: 120px" @change="handleSearch">
            <el-option label="全部" value=""></el-option>
            <el-option label="管理员" value="admin"></el-option>
            <el-option label="医生" value="doctor"></el-option>
            <el-option label="用户" value="user"></el-option>
          </el-select>
        </div>
      </div>

      <el-table 
        :data="filteredUsers" 
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="userId" label="ID" width="80"></el-table-column>
        <el-table-column label="头像" width="80">
          <template slot-scope="scope">
            <el-avatar :src="scope.row.avatarUrl || require('@/assets/images/default-avatar.png')"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column label="昵称">
          <template slot-scope="scope">
            {{ scope.row.nickname || scope.row.username }}
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="role" label="角色">
          <template slot-scope="scope">
            <el-tag :type="getRoleType(scope.row.role)">
              {{ getRoleText(scope.row.role) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="注册时间">
          <template slot-scope="scope">
            {{ new Date(scope.row.createdAt).toLocaleDateString() }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 'active' ? 'success' : 'danger'">
              {{ scope.row.status === 'active' ? '活跃' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="gender" label="性别">
          <template slot-scope="scope">
            {{ scope.row.gender === 'male' ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="location" label="地区"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button 
              size="mini" 
              type="danger" 
              @click="handleDelete(scope.row)"
              :disabled="scope.row.role === 'admin'"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>

      <!-- 编辑用户对话框 -->
      <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px">
        <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="editForm.username"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="editForm.email"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select v-model="editForm.role" style="width: 100%">
              <el-option label="管理员" value="admin"></el-option>
              <el-option label="医生" value="doctor"></el-option>
              <el-option label="用户" value="user"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm" :loading="submitLoading">确 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { getUserList } from '@/api/user'

export default {
  name: 'UserManagement',
  data() {
    return {
      loading: false,
      searchQuery: '',
      roleFilter: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      users: [],
      dialogVisible: false,
      dialogTitle: '',
      editForm: {
        id: null,
        username: '',
        email: '',
        role: ''
      },
      submitLoading: false,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'change' }
        ]
      }
    }
  },
  computed: {
    filteredUsers() {
      const start = (this.currentPage - 1) * this.pageSize
      const end = start + this.pageSize
      return this.users.slice(start, end)
    }
  },

  watch: {
    currentPage: 'fetchUsers',
    pageSize: 'fetchUsers',
    searchQuery: 'fetchUsers',
    roleFilter: 'fetchUsers'
  },

  created() {
    this.fetchUsers()
  },
  methods: {
    getRoleType(role) {
      const types = {
        admin: 'danger',
        doctor: 'warning',
        user: 'primary'
      }
      return types[role] || 'info'
    },
    getRoleText(role) {
      const texts = {
        admin: '管理员',
        doctor: '医生',
        user: '用户'
      }
      return texts[role] || role
    },
    async fetchUsers() {
      this.loading = true
      try {
        const params = {
          ...(this.roleFilter && { role: this.roleFilter }),
          ...(this.searchQuery && { search: this.searchQuery })
        }
        const res = await getUserList(params)
        console.log('用户列表接口返回:', res)
        if (res && res.status === '200') {
          this.users = Array.isArray(res.data) ? res.data : []
          this.total = res.data?.length || 0
        } else {
          this.users = []
          this.total = 0
          console.warn('API返回数据格式异常:', res)
          this.$message.error('获取用户列表失败，数据格式异常')
        }
      } catch (error) {
        console.error(error)
        this.$message.error('获取用户列表失败')
      } finally {
        this.loading = false
      }
    },

    handleSearch() {
      this.currentPage = 1
      this.fetchUsers()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleEdit(row) {
      this.dialogTitle = '编辑用户'
      this.editForm = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该用户?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 这里应该调用删除API
        this.users = this.users.filter(user => user.id !== row.id)
        this.$message.success('删除成功')
      }).catch(() => {})
    },
    submitForm() {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          this.submitLoading = true
          try {
            // 这里应该调用更新API
            const index = this.users.findIndex(user => user.id === this.editForm.id)
            if (index !== -1) {
              this.users[index] = { ...this.users[index], ...this.editForm }
            }
            this.$message.success('更新成功')
            this.dialogVisible = false
          } catch (error) {
            this.$message.error('更新失败')
          } finally {
            this.submitLoading = false
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-box {
  display: flex;
  align-items: center;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style>
