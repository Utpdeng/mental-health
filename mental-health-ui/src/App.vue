<template>
  <div id="app">
    <Navbar v-if="!isAdminRoute && !isDoctorRoute && !isUserRoute" />
    <router-view :class="{ 'has-nav': !isAdminRoute && !isDoctorRoute && !isUserRoute }"/>
    <Footer v-if="!isAdminRoute && !isDoctorRoute && !isUserRoute" />
    <AIChatBot v-if="showChatBot" />
  </div>
</template>

<script>
import Navbar from '@/components/layout/Navbar.vue'
import Footer from '@/components/layout/Footer.vue'
import AIChatBot from '@/components/AIChatBot.vue'

export default {
  name: 'App',
  components: {
    Navbar,
    Footer,
    AIChatBot
  },
  computed: {
    isAdminRoute() {
      return this.$route.path.startsWith('/admin')
    },
    isDoctorRoute() {
      return this.$route.path.startsWith('/doctor')
    },
    isUserRoute() {
      return this.$route.path.startsWith('/user')
    },
    showChatBot() {
      // 在管理端和医生端不显示聊天机器人
      return !this.isAdminRoute && !this.isDoctorRoute
    }
  }
}
</script>

<style>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.has-nav {
  padding-top: 60px;
}
</style>
