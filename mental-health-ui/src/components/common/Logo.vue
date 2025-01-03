<template>
  <div class="logo-wrapper">
    <svg class="logo" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
      <circle cx="50" cy="50" r="45" fill="currentColor" opacity="0.2"/>
      <path d="M50 20c-16.569 0-30 13.431-30 30 0 16.569 13.431 30 30 30 16.569 0 30-13.431 30-30C80 33.431 66.569 20 50 20zm0 5c13.807 0 25 11.193 25 25S63.807 75 50 75 25 63.807 25 50s11.193-25 25-25z" fill="currentColor"/>
      <path d="M50 35c-8.284 0-15 6.716-15 15 0 8.284 6.716 15 15 15 8.284 0 15-6.716 15-15 0-8.284-6.716-15-15-15z" fill="currentColor"/>
    </svg>
    <span class="logo-text" v-if="showText">校园心理健康平台</span>
  </div>
</template>

<script>
export default {
  name: 'Logo',
  props: {
    showText: {
      type: Boolean,
      default: true
    }
  }
}
</script>

<style lang="scss" scoped>
.logo-wrapper {
  @include flex(row, flex-start, center);
  gap: 1rem;
  color: $primary-color;

  .logo {
    width: 40px;
    height: 40px;
  }

  .logo-text {
    font-size: 1.25rem;
    font-weight: 600;
  }
}
</style> 