<template>
  <div class="chat-container">
    <!-- 聊天主界面 -->
    <div class="chat-main">
      <!-- 聊天头部信息 -->
      <div class="chat-header">
        <span class="doctor-name">{{ session.doctorName }}</span>
        <el-tag size="small" effect="plain">{{ session.form === 'video' ? '视频咨询' : '图文咨询' }}</el-tag>
      </div>

      <!-- 消息列表区域 -->
      <div class="message-list" ref="messageList">
        <div v-for="msg in messages" :key="msg.id" 
          :class="['message-item', msg.fromId === userId ? 'message-right' : 'message-left']">
          <div class="avatar">
            <el-avatar :size="40" :src="msg.fromId === userId ? session.avatarUrl : session.doctorAvatar"></el-avatar>
          </div>
          <div class="message-content">
            <div class="message-time">{{ formatTime(msg.createTime) }}</div>
            <div class="message-bubble">
              <div class="text-content">{{ msg.content }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 输入区域 -->
      <div class="input-area">
        <div class="toolbar">
          <el-tooltip content="表情" placement="top">
            <i class="el-icon-sunny" @click="showEmoji = !showEmoji"></i>
          </el-tooltip>
          <el-tooltip content="图片" placement="top">
            <i class="el-icon-picture-outline"></i>
          </el-tooltip>
          <el-tooltip content="文件" placement="top">
            <i class="el-icon-document"></i>
          </el-tooltip>
        </div>

        <!-- 表情面板 -->
        <div v-show="showEmoji" class="emoji-panel">
          <span 
            v-for="emoji in emojis" 
            :key="emoji"
            class="emoji-item"
            @click="insertEmoji(emoji)"
          >{{ emoji }}</span>
        </div>

        <div class="input-box">
          <el-input
            v-model="inputMessage"
            type="textarea"
            :rows="3"
            placeholder="请输入消息..."
            @keyup.enter.native="sendMessage"
          ></el-input>
          <el-button type="primary" @click="sendMessage">发送</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getChatHistory } from '@/api/chat'
import websocket from '@/websocket';
export default {
  name: 'UserChat',
  data() {
    return {
      session: this.$route.query.session,
      messages: [],
      inputMessage: '',
      userId: this.$store.state.user.userId,
      ws: null,
      showEmoji: false,
      emojis: ['😊', '😂', '😍', '🤔', '😅', '😭', '😉', '😘', '😡', '😱', '🤮', '😴']
    }
  },
  async created() {
    // 先获取历史消息
    try {
      const res = await getChatHistory(this.session.id)
      if (res.status === '200') {
        this.messages = res.data.map(msg => ({
          id: msg.id,
          type: 'text',
          content: msg.content,
          fromId: msg.sender,
          createTime: msg.timestamp
        }))
        this.$nextTick(() => {
          this.scrollToBottom()
        })
      }
    } catch (error) {
      console.error('获取历史消息失败:', error)
      this.$message.error('获取历史消息失败')
    }

    // 初始化WebSocket连接
    this.initWebSocket()
  },
  beforeDestroy() {
    if (this.ws) {
      this.ws.close()
    }
  },
  methods: {
    initWebSocket() {
      const wsUrl = `ws://${websocket.websocketUrl}/websocket/chat/${this.$store.state.user.userId}/${this.session.id}`
      this.ws = new WebSocket(wsUrl)
      
      this.ws.onopen = () => {
        console.log('WebSocket连接成功')
      }

      this.ws.onmessage = (event) => {
        const message = event.data
        console.log('收到消息:', message)
        
        // 根据消息格式判断类型并处理
        if (message.startsWith('[私聊]')) {
          // 处理接收到的私聊消息
          this.handlePrivateMessage(message)
        } else if (message.startsWith('[私聊发送给')) {
          // 处理私聊发送回显
          this.handlePrivateSentMessage(message) 
        } else if (message.startsWith('系统:')) {
          // 处理系统消息
          this.handleSystemMessage(message)
        } else {
          // 处理普通群聊消息
          this.handlePublicMessage(message)
        }

        this.$nextTick(() => {
          this.scrollToBottom()
        })
      }

      this.ws.onclose = () => {
        console.log('WebSocket连接关闭')
      }

      this.ws.onerror = (error) => {
        console.error('WebSocket错误:', error)
        this.$message.error('聊天连接发生错误')
      }
    },
    sendMessage() {
      if (!this.inputMessage.trim()) return

      const message = {
        content: this.inputMessage,
        receiver: this.session.doctorId,
      }

      this.ws.send(JSON.stringify(message))
      
      this.inputMessage = ''
      this.$nextTick(() => {
        this.scrollToBottom()
      })
    },
    scrollToBottom() {
      const messageList = this.$refs.messageList
      messageList.scrollTop = messageList.scrollHeight
    },
    formatTime(timestamp) {
      return new Date(timestamp).toLocaleTimeString()
    },
    handlePrivateMessage(message) {
      // 处理格式为 "[私聊]sender: content" 的消息
      const match = message.match(/\[私聊\](.*?): (.*)/)
      if (match) {
        const [, sender, content] = match
        this.messages.push({
          id: Date.now(),
          content: content,
          fromId: this.session.doctorId, // 来自医生的消息
          createTime: new Date().toISOString()
        })
      }
    },
    handlePrivateSentMessage(message) {
      // 处理格式为 "[私聊发送给receiver]: content" 的消息
      const match = message.match(/\[私聊发送给(.*?)\]: (.*)/)
      if (match) {
        const [, receiver, content] = match
        this.messages.push({
          id: Date.now(),
          content: content,
          fromId: this.$store.state.user.userId, // 自己发送的消息
          createTime: new Date().toISOString()
        })
      }
    },
    insertEmoji(emoji) {
      this.inputMessage += emoji
      this.showEmoji = false
    }
  }
}
</script>

<style scoped>
.chat-container {
  height: 100%;
  background: #f4f4f4;
  padding: 20px;
}

.chat-main {
  max-width: 800px;
  margin: 0 auto;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  height: calc(100vh - 180px);
}

.chat-header {
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
  display: flex;
  align-items: center;
  gap: 10px;
}

.doctor-name {
  font-size: 16px;
  font-weight: bold;
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.message-item {
  display: flex;
  margin-bottom: 20px;
}

.message-left {
  flex-direction: row;
}

.message-right {
  flex-direction: row-reverse;
}

.avatar {
  margin: 0 10px;
}

.message-content {
  max-width: 60%;
}

.message-time {
  font-size: 12px;
  color: #999;
  margin-bottom: 5px;
}

.message-bubble {
  padding: 10px 15px;
  border-radius: 4px;
  word-break: break-all;
}

.message-left .message-bubble {
  background: #f4f4f4;
}

.message-right .message-bubble {
  background: #e6f3ff;
}

.input-area {
  position: relative;
}

.toolbar {
  padding: 10px;
  border-bottom: 1px solid #eee;
  display: flex;
  gap: 15px;
}

.toolbar i {
  font-size: 20px;
  color: #666;
  cursor: pointer;
}

.toolbar i:hover {
  color: var(--primary-color);
}

.emoji-panel {
  position: absolute;
  bottom: 100%;
  left: 0;
  background: white;
  border: 1px solid #eee;
  border-radius: 4px;
  padding: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.emoji-item {
  cursor: pointer;
  padding: 5px;
  font-size: 20px;
  transition: transform 0.2s;
}

.emoji-item:hover {
  transform: scale(1.2);
}

.input-box {
  display: flex;
  gap: 10px;
  padding: 10px;
}
</style>
  