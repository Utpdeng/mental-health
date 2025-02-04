<!-- 将之前的 Community.vue 内容移到这里 --> <template>
  <div class="community-page">
    <div class="page-header">
      <div class="container">
        <h1>社区支持</h1>
        <p>在这里，您永远不是一个人</p>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="community-content">
          <p>我们的社区为您提供一个安全、支持和包容的环境，您可以在这里分享您的故事，获得支持和建议。我们相信，通过相互的支持和理解，每个人都可以找到自己的力量和勇气。</p>
          <p>加入我们的社区，您将有机会参与各种活动和讨论，与其他成员分享经验和见解。无论您是想要寻求帮助，还是希望帮助他人，我们都欢迎您的加入。</p>
          <p>请记住，您并不孤单。我们在这里支持您。</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Community'
}
</script>

<style scoped>
.page-header {
  margin-top: 10px;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  padding: 60px 0;
  text-align: center;
}

.community-content {
  background: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  line-height: 1.8;
  color: #666;
}
</style> 