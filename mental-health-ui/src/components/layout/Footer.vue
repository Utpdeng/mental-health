<template>
  <footer class="footer">
    <div class="container">
      <p class="copyright">© {{ currentYear }} 心理健康平台. 保留所有权利。</p>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'AppFooter',
  computed: {
    currentYear() {
      return new Date().getFullYear()
    }
  }
}
</script>

<style scoped>
.footer {
  background: var(--background-color);
  padding: 20px 0;
  text-align: center;
  margin-top: auto;
}

.copyright {
  color: #666;
}
</style>
