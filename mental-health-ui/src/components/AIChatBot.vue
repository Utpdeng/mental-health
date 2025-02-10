<template>
  <div class="ai-chatbot">
    <!-- 悬浮按钮 -->
    <div class="chat-button" @click="toggleChat" :class="{ active: isOpen }">
      <i class="fas fa-robot"></i>
    </div>

    <!-- 聊天窗口 -->
    <div class="chat-window" v-show="isOpen">
      <div class="chat-header">
        <div class="header-left">
          <el-avatar :size="30" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
          <span>AI 助手</span>
        </div>
        <i class="el-icon-close" @click="toggleChat"></i>
      </div>
      
      <div class="chat-messages" ref="messageList">
        <div v-for="msg in messages" :key="msg.id" 
          :class="['message-item', msg.fromBot ? 'message-left' : 'message-right']">
          <div class="message-avatar">
            <el-avatar 
              :size="36" 
              :src="msg.fromBot ? 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' : userAvatar"
            ></el-avatar>
          </div>
          <div class="message-content">
            <div class="message-info">
              <span class="sender-name">{{ msg.fromBot ? 'AI 助手' : '我' }}</span>
              <span class="message-time">{{ formatTime(msg.timestamp) }}</span>
            </div>
            <div class="message-bubble">
              {{ msg.content }}
            </div>
          </div>
        </div>
      </div>

      <div class="chat-input">
        <el-input
          v-model="inputMessage"
          placeholder="请输入消息..."
          @keyup.enter.native="sendMessage"
        >
          <el-button slot="append" @click="sendMessage">发送</el-button>
        </el-input>
      </div>
    </div>
  </div>
</template>

<script>
import { chatbot } from '@/api/chat'

export default {
  name: 'AIChatBot',
  data() {
    return {
      isOpen: false,
      inputMessage: '',
      userAvatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      messages: [
        {
          id: 1,
          content: '你好！我是AI助手，有什么可以帮你的吗？',
          fromBot: true,
          timestamp: new Date()
        }
      ],
      loading: false
    }
  },
  methods: {
    toggleChat() {
      this.isOpen = !this.isOpen
      if (this.isOpen) {
        this.$nextTick(() => {
          this.scrollToBottom()
        })
      }
    },
    async sendMessage() {
      if (!this.inputMessage.trim()) {
        return
      }

      // 添加用户消息
      const userMessage = {
        id: Date.now(),
        content: this.inputMessage,
        fromBot: false,
        timestamp: new Date()
      }
      this.messages.push(userMessage)
      this.scrollToBottom()

      const userInput = this.inputMessage
      this.inputMessage = ''
      this.loading = true

      try {
        // 调用后端API
        const response = await chatbot(userInput)
        console.log(response)
        // 添加机器人回复
        const botMessage = {
          id: Date.now() + 1,
          content: response.data,
          fromBot: true,
          timestamp: new Date()
        }
        this.messages.push(botMessage)
        this.scrollToBottom()
      } catch (error) {
        // 发生错误时添加错误提示消息
        const errorMessage = {
          id: Date.now() + 1,
          content: '服务器繁忙, 请稍后再试。',
          fromBot: true,
          timestamp: new Date()
        }
        this.messages.push(errorMessage)
        console.error('AI回复错误:', error)
      } finally {
        this.loading = false
      }
    },
    scrollToBottom() {
      const messageList = this.$refs.messageList
      messageList.scrollTop = messageList.scrollHeight
    },
    formatTime(timestamp) {
      const date = new Date(timestamp)
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      return `${hours}:${minutes}`
    }
  }
}
</script>

<style scoped>
.ai-chatbot {
  position: fixed;
  right: 20px;
  bottom: 20px;
  z-index: 1000;
}

.chat-button {
  width: 60px;
  height: 60px;
  border-radius: 30px;
  background: var(--primary-color);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  transition: transform 0.3s;
}

.chat-button:hover {
  transform: scale(1.1);
}

.chat-button.active {
  transform: scale(0.9);
}

.chat-button i {
  font-size: 24px;
}

.chat-window {
  position: absolute;
  right: 0;
  bottom: 80px;
  width: 350px;
  height: 500px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.15);
  display: flex;
  flex-direction: column;
}

.chat-header {
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: var(--primary-color);
  color: white;
  border-radius: 10px 10px 0 0;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.chat-header i {
  cursor: pointer;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.message-item {
  margin-bottom: 20px;
  display: flex;
  align-items: flex-start;
  gap: 10px;
}

.message-right {
  flex-direction: row-reverse;
}

.message-content {
  max-width: calc(100% - 46px);
}

.message-info {
  margin-bottom: 4px;
  font-size: 12px;
  color: #999;
}

.message-right .message-info {
  text-align: right;
}

.sender-name {
  margin-right: 8px;
  color: #666;
}

.message-bubble {
  padding: 10px 15px;
  border-radius: 4px;
  word-break: break-all;
}

.message-left .message-bubble {
  background: #f4f4f4;
  border-radius: 0 8px 8px 8px;
}

.message-right .message-bubble {
  background: #e6f3ff;
  border-radius: 8px 0 8px 8px;
}

.chat-input {
  padding: 15px;
  border-top: 1px solid #eee;
}

/* 自定义滚动条样式 */
.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: #ddd;
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-track {
  background: #f4f4f4;
}

/* 添加loading样式 */
.message-bubble.loading::after {
  content: '...';
  animation: loading 1s infinite;
}

@keyframes loading {
  0% { content: '.'; }
  33% { content: '..'; }
  66% { content: '...'; }
}
</style> 