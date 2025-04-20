<template>
  <div class="admin-community-page">
    <div class="page-header">
      <div class="container">
        <h1>社区管理</h1>
        <p>管理社区内容、用户和举报信息</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <el-tabs v-model="activeTab" type="card">
          <!-- 内容管理 -->
          <el-tab-pane label="内容管理" name="content">
            <div class="filter-bar">
              <el-form :inline="true" :model="contentFilter" class="filter-form">
                <el-form-item label="类型">
                  <el-select v-model="contentFilter.type" placeholder="请选择类型" clearable>
                    <el-option label="帖子" value="post"></el-option>
                    <el-option label="评论" value="comment"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="状态">
                  <el-select v-model="contentFilter.status" placeholder="请选择状态" clearable>
                    <el-option label="正常" value="normal"></el-option>
                    <el-option label="已删除" value="deleted"></el-option>
                    <el-option label="已隐藏" value="hidden"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handleContentSearch">搜索</el-button>
                  <el-button @click="resetContentFilter">重置</el-button>
                </el-form-item>
              </el-form>
            </div>

            <el-table :data="contentList" style="width: 100%" v-loading="contentLoading">
              <el-table-column prop="title" label="标题" min-width="200">
                <template slot-scope="scope">
                  <span>{{ scope.row.title }}</span>
                  <el-tag size="mini" :type="getContentTypeTag(scope.row.type)" class="ml-2">
                    {{ getContentTypeText(scope.row.type) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="author" label="作者" width="120"></el-table-column>
              <el-table-column prop="content" label="内容" min-width="300">
                <template slot-scope="scope">
                  <div class="content-preview">{{ scope.row.content }}</div>
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="发布时间" width="180"></el-table-column>
              <el-table-column prop="status" label="状态" width="100">
                <template slot-scope="scope">
                  <el-tag :type="getContentStatusType(scope.row.status)">
                    {{ getContentStatusText(scope.row.status) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200" fixed="right">
                <template slot-scope="scope">
                  <el-button 
                    size="mini" 
                    type="primary" 
                    @click="viewContent(scope.row)">
                    查看
                  </el-button>
                  <el-button 
                    size="mini" 
                    :type="scope.row.status === 'normal' ? 'danger' : 'success'"
                    @click="toggleContentStatus(scope.row)">
                    {{ scope.row.status === 'normal' ? '隐藏' : '恢复' }}
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <div class="pagination-container">
              <el-pagination
                @size-change="handleContentSizeChange"
                @current-change="handleContentCurrentChange"
                :current-page="contentPagination.currentPage"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="contentPagination.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="contentPagination.total">
              </el-pagination>
            </div>
          </el-tab-pane>

          <!-- 用户管理 -->
          <el-tab-pane label="用户管理" name="users">
            <div class="filter-bar">
              <el-form :inline="true" :model="userFilter" class="filter-form">
                <el-form-item label="角色">
                  <el-select v-model="userFilter.role" placeholder="请选择角色" clearable>
                    <el-option label="患者" value="patient"></el-option>
                    <el-option label="医生" value="doctor"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="状态">
                  <el-select v-model="userFilter.status" placeholder="请选择状态" clearable>
                    <el-option label="正常" value="normal"></el-option>
                    <el-option label="禁言" value="muted"></el-option>
                    <el-option label="封禁" value="banned"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handleUserSearch">搜索</el-button>
                  <el-button @click="resetUserFilter">重置</el-button>
                </el-form-item>
              </el-form>
            </div>

            <el-table :data="userList" style="width: 100%" v-loading="userLoading">
              <el-table-column prop="username" label="用户名" width="150"></el-table-column>
              <el-table-column prop="nickname" label="昵称" width="150"></el-table-column>
              <el-table-column prop="role" label="角色" width="100">
                <template slot-scope="scope">
                  <el-tag :type="getUserRoleType(scope.row.role)">
                    {{ getUserRoleText(scope.row.role) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
              <el-table-column prop="createTime" label="注册时间" width="180"></el-table-column>
              <el-table-column prop="status" label="状态" width="100">
                <template slot-scope="scope">
                  <el-tag :type="getUserStatusType(scope.row.status)">
                    {{ getUserStatusText(scope.row.status) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200" fixed="right">
                <template slot-scope="scope">
                  <el-button 
                    size="mini" 
                    type="primary" 
                    @click="viewUser(scope.row)">
                    详情
                  </el-button>
                  <el-button 
                    size="mini" 
                    :type="scope.row.status === 'normal' ? 'danger' : 'success'"
                    @click="toggleUserStatus(scope.row)">
                    {{ scope.row.status === 'normal' ? '禁言' : '解禁' }}
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <div class="pagination-container">
              <el-pagination
                @size-change="handleUserSizeChange"
                @current-change="handleUserCurrentChange"
                :current-page="userPagination.currentPage"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="userPagination.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="userPagination.total">
              </el-pagination>
            </div>
          </el-tab-pane>

          <!-- 举报管理 -->
          <el-tab-pane label="举报管理" name="reports">
            <div class="filter-bar">
              <el-form :inline="true" :model="reportFilter" class="filter-form">
                <el-form-item label="类型">
                  <el-select v-model="reportFilter.type" placeholder="请选择类型" clearable>
                    <el-option label="帖子" value="post"></el-option>
                    <el-option label="评论" value="comment"></el-option>
                    <el-option label="用户" value="user"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="状态">
                  <el-select v-model="reportFilter.status" placeholder="请选择状态" clearable>
                    <el-option label="待处理" value="pending"></el-option>
                    <el-option label="已处理" value="processed"></el-option>
                    <el-option label="已忽略" value="ignored"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handleReportSearch">搜索</el-button>
                  <el-button @click="resetReportFilter">重置</el-button>
                </el-form-item>
              </el-form>
            </div>

            <el-table :data="reportList" style="width: 100%" v-loading="reportLoading">
              <el-table-column prop="type" label="举报类型" width="120">
                <template slot-scope="scope">
                  <el-tag :type="getReportTypeTag(scope.row.type)">
                    {{ getReportTypeText(scope.row.type) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="target" label="举报对象" min-width="200">
                <template slot-scope="scope">
                  <div class="target-preview">{{ scope.row.target }}</div>
                </template>
              </el-table-column>
              <el-table-column prop="reason" label="举报原因" min-width="200">
                <template slot-scope="scope">
                  <div class="reason-preview">{{ scope.row.reason }}</div>
                </template>
              </el-table-column>
              <el-table-column prop="reporter" label="举报人" width="120"></el-table-column>
              <el-table-column prop="createTime" label="举报时间" width="180"></el-table-column>
              <el-table-column prop="status" label="状态" width="100">
                <template slot-scope="scope">
                  <el-tag :type="getReportStatusType(scope.row.status)">
                    {{ getReportStatusText(scope.row.status) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200" fixed="right">
                <template slot-scope="scope">
                  <el-button 
                    size="mini" 
                    type="primary" 
                    @click="handleReport(scope.row)"
                    v-if="scope.row.status === 'pending'">
                    处理
                  </el-button>
                  <el-button 
                    size="mini" 
                    type="info" 
                    @click="viewReport(scope.row)">
                    查看
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <div class="pagination-container">
              <el-pagination
                @size-change="handleReportSizeChange"
                @current-change="handleReportCurrentChange"
                :current-page="reportPagination.currentPage"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="reportPagination.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="reportPagination.total">
              </el-pagination>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

    <!-- 内容详情对话框 -->
    <el-dialog 
      :title="'内容详情 - ' + currentContent.title" 
      :visible.sync="contentDialogVisible"
      width="70%"
      :close-on-click-modal="false">
      <div class="content-detail">
        <div class="content-header">
          <h3>{{ currentContent.title }}</h3>
          <div class="content-meta">
            <span>作者：{{ currentContent.author }}</span>
            <span>发布时间：{{ currentContent.createTime }}</span>
            <el-tag :type="getContentStatusType(currentContent.status)">
              {{ getContentStatusText(currentContent.status) }}
            </el-tag>
          </div>
        </div>
        <div class="content-body">
          {{ currentContent.content }}
        </div>
      </div>
    </el-dialog>

    <!-- 用户详情对话框 -->
    <el-dialog 
      :title="'用户详情 - ' + currentUser.nickname" 
      :visible.sync="userDialogVisible"
      width="50%"
      :close-on-click-modal="false">
      <div class="user-detail">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="用户名">{{ currentUser.username }}</el-descriptions-item>
          <el-descriptions-item label="昵称">{{ currentUser.nickname }}</el-descriptions-item>
          <el-descriptions-item label="角色">
            <el-tag :type="getUserRoleType(currentUser.role)">
              {{ getUserRoleText(currentUser.role) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="邮箱">{{ currentUser.email }}</el-descriptions-item>
          <el-descriptions-item label="注册时间">{{ currentUser.createTime }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getUserStatusType(currentUser.status)">
              {{ getUserStatusText(currentUser.status) }}
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>

    <!-- 举报处理对话框 -->
    <el-dialog 
      :title="'处理举报 - ' + currentReport.type" 
      :visible.sync="reportDialogVisible"
      width="60%"
      :close-on-click-modal="false">
      <div class="report-detail">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="举报类型">
            <el-tag :type="getReportTypeTag(currentReport.type)">
              {{ getReportTypeText(currentReport.type) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="举报对象">{{ currentReport.target }}</el-descriptions-item>
          <el-descriptions-item label="举报原因">{{ currentReport.reason }}</el-descriptions-item>
          <el-descriptions-item label="举报人">{{ currentReport.reporter }}</el-descriptions-item>
          <el-descriptions-item label="举报时间">{{ currentReport.createTime }}</el-descriptions-item>
        </el-descriptions>

        <el-form :model="reportForm" :rules="reportRules" ref="reportForm" label-width="100px" class="mt-4">
          <el-form-item label="处理结果" prop="action">
            <el-radio-group v-model="reportForm.action">
              <el-radio label="delete">删除内容</el-radio>
              <el-radio label="warn">警告用户</el-radio>
              <el-radio label="ignore">忽略举报</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="处理说明" prop="comment">
            <el-input 
              type="textarea" 
              v-model="reportForm.comment" 
              :rows="3"
              placeholder="请输入处理说明">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="reportDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitReport">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'AdminCommunity',
  data() {
    return {
      activeTab: 'content',
      
      // 内容管理
      contentLoading: false,
      contentList: [],
      contentFilter: {
        type: '',
        status: ''
      },
      contentPagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      contentDialogVisible: false,
      currentContent: {
        title: '',
        author: '',
        content: '',
        createTime: '',
        status: ''
      },

      // 用户管理
      userLoading: false,
      userList: [],
      userFilter: {
        role: '',
        status: ''
      },
      userPagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      userDialogVisible: false,
      currentUser: {
        username: '',
        nickname: '',
        role: '',
        email: '',
        createTime: '',
        status: ''
      },

      // 举报管理
      reportLoading: false,
      reportList: [],
      reportFilter: {
        type: '',
        status: ''
      },
      reportPagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      reportDialogVisible: false,
      currentReport: {
        type: '',
        target: '',
        reason: '',
        reporter: '',
        createTime: '',
        status: ''
      },
      reportForm: {
        action: '',
        comment: ''
      },
      reportRules: {
        action: [
          { required: true, message: '请选择处理结果', trigger: 'change' }
        ],
        comment: [
          { required: true, message: '请输入处理说明', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchContentList()
    this.fetchUserList()
    this.fetchReportList()
  },
  methods: {
    // 内容管理方法
    async fetchContentList() {
      this.contentLoading = true
      try {
        // 模拟数据
        this.contentList = [
          {
            id: '1',
            title: '如何应对焦虑情绪',
            author: '张医生',
            content: '焦虑是一种常见的情绪反应，我们可以通过深呼吸、运动等方式来缓解...',
            type: 'post',
            status: 'normal',
            createTime: '2023-05-10 14:30:00'
          },
          {
            id: '2',
            title: '评论：关于抑郁症的治疗',
            author: '李患者',
            content: '我觉得药物治疗和心理治疗都很重要，不能只依赖其中一种...',
            type: 'comment',
            status: 'hidden',
            createTime: '2023-05-15 09:20:00'
          }
        ]
        this.contentPagination.total = this.contentList.length
      } catch (error) {
        console.error('获取内容列表失败:', error)
      } finally {
        this.contentLoading = false
      }
    },
    handleContentSearch() {
      this.contentPagination.currentPage = 1
      this.fetchContentList()
    },
    resetContentFilter() {
      this.contentFilter = {
        type: '',
        status: ''
      }
      this.handleContentSearch()
    },
    handleContentSizeChange(val) {
      this.contentPagination.pageSize = val
      this.fetchContentList()
    },
    handleContentCurrentChange(val) {
      this.contentPagination.currentPage = val
      this.fetchContentList()
    },
    viewContent(content) {
      this.currentContent = { ...content }
      this.contentDialogVisible = true
    },
    async toggleContentStatus(content) {
      try {
        await this.$confirm(
          `确定要${content.status === 'normal' ? '隐藏' : '恢复'}该内容吗？`,
          '提示',
          { type: 'warning' }
        )
        // 模拟状态切换
        const index = this.contentList.findIndex(item => item.id === content.id)
        if (index !== -1) {
          this.contentList[index].status = content.status === 'normal' ? 'hidden' : 'normal'
        }
        this.$message.success('操作成功')
      } catch (error) {
        if (error !== 'cancel') {
          console.error('操作失败:', error)
          this.$message.error('操作失败')
        }
      }
    },

    // 用户管理方法
    async fetchUserList() {
      this.userLoading = true
      try {
        // 模拟数据
        this.userList = [
          {
            id: '1',
            username: 'zhangsan',
            nickname: '张三',
            role: 'patient',
            email: 'zhangsan@example.com',
            status: 'normal',
            createTime: '2023-04-01 10:00:00'
          },
          {
            id: '2',
            username: 'lisi',
            nickname: '李四',
            role: 'doctor',
            email: 'lisi@example.com',
            status: 'muted',
            createTime: '2023-04-15 14:30:00'
          }
        ]
        this.userPagination.total = this.userList.length
      } catch (error) {
        console.error('获取用户列表失败:', error)
      } finally {
        this.userLoading = false
      }
    },
    handleUserSearch() {
      this.userPagination.currentPage = 1
      this.fetchUserList()
    },
    resetUserFilter() {
      this.userFilter = {
        role: '',
        status: ''
      }
      this.handleUserSearch()
    },
    handleUserSizeChange(val) {
      this.userPagination.pageSize = val
      this.fetchUserList()
    },
    handleUserCurrentChange(val) {
      this.userPagination.currentPage = val
      this.fetchUserList()
    },
    viewUser(user) {
      this.currentUser = { ...user }
      this.userDialogVisible = true
    },
    async toggleUserStatus(user) {
      try {
        await this.$confirm(
          `确定要${user.status === 'normal' ? '禁言' : '解禁'}该用户吗？`,
          '提示',
          { type: 'warning' }
        )
        // 模拟状态切换
        const index = this.userList.findIndex(item => item.id === user.id)
        if (index !== -1) {
          this.userList[index].status = user.status === 'normal' ? 'muted' : 'normal'
        }
        this.$message.success('操作成功')
      } catch (error) {
        if (error !== 'cancel') {
          console.error('操作失败:', error)
          this.$message.error('操作失败')
        }
      }
    },

    // 举报管理方法
    async fetchReportList() {
      this.reportLoading = true
      try {
        // 模拟数据
        this.reportList = [
          {
            id: '1',
            type: 'post',
            target: '如何应对焦虑情绪',
            reason: '内容包含不当言论',
            reporter: '王五',
            status: 'pending',
            createTime: '2023-05-12 16:45:00'
          },
          {
            id: '2',
            type: 'comment',
            target: '关于抑郁症的治疗',
            reason: '人身攻击',
            reporter: '赵六',
            status: 'processed',
            createTime: '2023-05-18 09:30:00'
          }
        ]
        this.reportPagination.total = this.reportList.length
      } catch (error) {
        console.error('获取举报列表失败:', error)
      } finally {
        this.reportLoading = false
      }
    },
    handleReportSearch() {
      this.reportPagination.currentPage = 1
      this.fetchReportList()
    },
    resetReportFilter() {
      this.reportFilter = {
        type: '',
        status: ''
      }
      this.handleReportSearch()
    },
    handleReportSizeChange(val) {
      this.reportPagination.pageSize = val
      this.fetchReportList()
    },
    handleReportCurrentChange(val) {
      this.reportPagination.currentPage = val
      this.fetchReportList()
    },
    handleReport(report) {
      this.currentReport = { ...report }
      this.reportForm = {
        action: '',
        comment: ''
      }
      this.reportDialogVisible = true
    },
    viewReport(report) {
      this.currentReport = { ...report }
      this.reportDialogVisible = true
    },
    async submitReport() {
      try {
        await this.$refs.reportForm.validate()
        // 模拟处理举报
        const index = this.reportList.findIndex(item => item.id === this.currentReport.id)
        if (index !== -1) {
          this.reportList[index].status = 'processed'
        }
        this.reportDialogVisible = false
        this.$message.success('处理成功')
      } catch (error) {
        if (error !== 'cancel') {
          console.error('处理失败:', error)
          this.$message.error('处理失败')
        }
      }
    },

    // 通用方法
    getContentTypeTag(type) {
      return type === 'post' ? 'primary' : 'info'
    },
    getContentTypeText(type) {
      return type === 'post' ? '帖子' : '评论'
    },
    getContentStatusType(status) {
      const types = {
        'normal': 'success',
        'deleted': 'danger',
        'hidden': 'warning'
      }
      return types[status] || 'info'
    },
    getContentStatusText(status) {
      const texts = {
        'normal': '正常',
        'deleted': '已删除',
        'hidden': '已隐藏'
      }
      return texts[status] || '未知'
    },
    getUserRoleType(role) {
      return role === 'doctor' ? 'primary' : 'success'
    },
    getUserRoleText(role) {
      return role === 'doctor' ? '医生' : '患者'
    },
    getUserStatusType(status) {
      const types = {
        'normal': 'success',
        'muted': 'warning',
        'banned': 'danger'
      }
      return types[status] || 'info'
    },
    getUserStatusText(status) {
      const texts = {
        'normal': '正常',
        'muted': '禁言',
        'banned': '封禁'
      }
      return texts[status] || '未知'
    },
    getReportTypeTag(type) {
      const types = {
        'post': 'primary',
        'comment': 'info',
        'user': 'warning'
      }
      return types[type] || 'info'
    },
    getReportTypeText(type) {
      const texts = {
        'post': '帖子',
        'comment': '评论',
        'user': '用户'
      }
      return texts[type] || '未知'
    },
    getReportStatusType(status) {
      const types = {
        'pending': 'warning',
        'processed': 'success',
        'ignored': 'info'
      }
      return types[status] || 'info'
    },
    getReportStatusText(status) {
      const texts = {
        'pending': '待处理',
        'processed': '已处理',
        'ignored': '已忽略'
      }
      return texts[status] || '未知'
    }
  }
}
</script>

<style scoped>
.page-header {
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  padding: 40px 0;
  margin-bottom: 30px;
}

.filter-bar {
  margin-bottom: 20px;
}

.content-preview,
.target-preview,
.reason-preview {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.content-detail {
  padding: 20px;
}

.content-header {
  margin-bottom: 20px;
}

.content-meta {
  margin-top: 10px;
  color: #666;
}

.content-meta span {
  margin-right: 20px;
}

.content-body {
  line-height: 1.6;
  white-space: pre-wrap;
}

.user-detail,
.report-detail {
  padding: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}

.mt-4 {
  margin-top: 16px;
}

.ml-2 {
  margin-left: 8px;
}
</style>
