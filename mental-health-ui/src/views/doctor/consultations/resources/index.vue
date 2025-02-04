<template>
  <div class="consultation-resources">
    <div class="resources-header">
      <div class="header-title">
        <i class="el-icon-collection"></i>
        <span>咨询资源</span>
      </div>
      <el-input
        v-model="searchQuery"
        placeholder="搜索资源"
        prefix-icon="el-icon-search"
        clearable
        class="search-input"
      ></el-input>
    </div>

    <el-row :gutter="20">
      <!-- 常用量表 -->
      <el-col :span="8">
        <el-card class="resource-card">
          <div slot="header">
            <i class="el-icon-document"></i>
            <span>心理量表</span>
          </div>
          <div class="resource-list">
            <div v-for="scale in filteredScales" :key="scale.id" class="resource-item">
              <div class="item-info">
                <span class="item-title">{{ scale.name }}</span>
                <span class="item-desc">{{ scale.description }}</span>
              </div>
              <el-button type="text" @click="previewResource(scale)">查看</el-button>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 咨询工具 -->
      <el-col :span="8">
        <el-card class="resource-card">
          <div slot="header">
            <i class="el-icon-tools"></i>
            <span>咨询工具</span>
          </div>
          <div class="resource-list">
            <div v-for="tool in filteredTools" :key="tool.id" class="resource-item">
              <div class="item-info">
                <span class="item-title">{{ tool.name }}</span>
                <span class="item-desc">{{ tool.description }}</span>
              </div>
              <el-button type="text" @click="previewResource(tool)">使用</el-button>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 参考资料 -->
      <el-col :span="8">
        <el-card class="resource-card">
          <div slot="header">
            <i class="el-icon-reading"></i>
            <span>参考资料</span>
          </div>
          <div class="resource-list">
            <div v-for="material in filteredMaterials" :key="material.id" class="resource-item">
              <div class="item-info">
                <span class="item-title">{{ material.name }}</span>
                <span class="item-desc">{{ material.description }}</span>
              </div>
              <el-button type="text" @click="previewResource(material)">下载</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 预览对话框 -->
    <el-dialog
      :title="currentResource.name"
      :visible.sync="dialogVisible"
      width="60%"
    >
      <div class="resource-preview">
        <div class="preview-info">
          <p><strong>描述：</strong>{{ currentResource.description }}</p>
          <p><strong>适用范围：</strong>{{ currentResource.scope }}</p>
          <p><strong>使用说明：</strong>{{ currentResource.instruction }}</p>
        </div>
        <div class="preview-content" v-if="currentResource.content">
          {{ currentResource.content }}
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="handleResourceAction">
          {{ getActionText(currentResource.type) }}
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'ConsultationResources',
  data() {
    return {
      searchQuery: '',
      dialogVisible: false,
      currentResource: {},
      scales: [
        {
          id: 1,
          type: 'scale',
          name: 'SCL-90 症状自评量表',
          description: '评估个体的心理健康状况',
          scope: '适用于各类心理问题的初步筛查',
          instruction: '请患者根据最近一周的实际感受进行评分'
        },
        {
          id: 2,
          type: 'scale',
          name: 'SDS 抑郁自评量表',
          description: '评估抑郁症状的严重程度',
          scope: '适用于抑郁症状的评估',
          instruction: '根据过去一周的感受进行评分'
        },
        {
          id: 3,
          type: 'scale',
          name: 'SAS 焦虑自评量表',
          description: '评估焦虑症状的严重程度',
          scope: '适用于焦虑症状的评估',
          instruction: '根据过去一周的感受进行评分'
        }
      ],
      tools: [
        {
          id: 1,
          type: 'tool',
          name: '认知重构工作表',
          description: '帮助识别和改变消极思维模式',
          scope: '适用于认知行为治疗',
          instruction: '记录触发事件、想法和情绪'
        },
        {
          id: 2,
          type: 'tool',
          name: '情绪日记模板',
          description: '记录和追踪每日情绪变化',
          scope: '适用于情绪管理',
          instruction: '每天记录情绪强度和相关事件'
        },
        {
          id: 3,
          type: 'tool',
          name: '放松训练指南',
          description: '指导进行呼吸和肌肉放松练习',
          scope: '适用于压力管理',
          instruction: '按照指南进行练习'
        }
      ],
      materials: [
        {
          id: 1,
          type: 'material',
          name: '心理健康手册',
          description: '常见心理问题的科普资料',
          scope: '适用于心理健康教育',
          instruction: '可作为患者教育材料'
        },
        {
          id: 2,
          type: 'material',
          name: '睡眠改善指南',
          description: '改善睡眠质量的建议和方法',
          scope: '适用于失眠问题',
          instruction: '按照指南逐步改善睡眠'
        },
        {
          id: 3,
          type: 'material',
          name: '压力管理手册',
          description: '压力应对和管理技巧',
          scope: '适用于压力管理',
          instruction: '学习和实践压力管理方法'
        }
      ]
    }
  },
  computed: {
    filteredScales() {
      return this.filterResources(this.scales)
    },
    filteredTools() {
      return this.filterResources(this.tools)
    },
    filteredMaterials() {
      return this.filterResources(this.materials)
    }
  },
  methods: {
    filterResources(resources) {
      if (!this.searchQuery) return resources
      const query = this.searchQuery.toLowerCase()
      return resources.filter(resource => 
        resource.name.toLowerCase().includes(query) ||
        resource.description.toLowerCase().includes(query)
      )
    },
    previewResource(resource) {
      this.currentResource = { ...resource }
      this.dialogVisible = true
    },
    getActionText(type) {
      const actions = {
        scale: '使用量表',
        tool: '使用工具',
        material: '下载资料'
      }
      return actions[type] || '确定'
    },
    handleResourceAction() {
      this.$message.success(`${this.currentResource.name} 已准备就绪`)
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped>
.consultation-resources {
  padding: 20px;
}

.resources-header {
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

.search-input {
  width: 250px;
}

.resource-card {
  margin-bottom: 20px;
  height: calc(100vh - 200px);
  overflow-y: auto;
}

.resource-card :deep(.el-card__header) {
  padding: 15px 20px;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 8px;
}

.resource-list {
  padding: 10px 0;
}

.resource-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.resource-item:last-child {
  border-bottom: none;
}

.item-info {
  flex: 1;
  margin-right: 15px;
}

.item-title {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.item-desc {
  display: block;
  font-size: 13px;
  color: #666;
}

.resource-preview {
  padding: 20px;
}

.preview-info {
  margin-bottom: 20px;
}

.preview-info p {
  margin: 10px 0;
}

.preview-content {
  padding: 15px;
  background: #f5f7fa;
  border-radius: 4px;
}

:deep(.el-card__body) {
  padding: 10px 20px;
}
</style> 