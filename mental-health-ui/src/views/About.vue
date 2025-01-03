<template>
  <div class="about">
    <div class="container">
      <h1 class="page-title">关于我们</h1>
      
      <el-row :gutter="40">
        <el-col :md="12">
          <div class="about-content">
            <h2>校园心理健康中心</h2>
            <p>我们致力于为广大师生提供专业的心理健康服务，创造积极向上的校园心理环境。通过专业的心理咨询、心理测评、心理课程等多种形式，帮助师生更好地认识自我、发展自我。</p>
            
            <h3>我们的使命</h3>
            <p>促进师生心理健康，营造和谐校园氛围，助力人才培养。</p>
            
            <h3>我们的愿景</h3>
            <p>打造一流的校园心理服务平台，成为学生心理健康的守护者。</p>
            
            <h3>核心价值观</h3>
            <ul>
              <li>专业性：提供高质量的心理健康服务</li>
              <li>关爱性：以人为本，真诚关怀每位师生</li>
              <li>发展性：促进师生的心理健康与个人成长</li>
              <li>创新性：运用现代技术提升服务质量</li>
            </ul>
          </div>
        </el-col>
        
        <el-col :md="12">
          <div class="team-section">
            <h2>我们的团队</h2>
            <div class="team-grid">
              <div v-for="member in teamMembers" :key="member.id" class="team-member">
                <el-avatar :size="80" :src="member.avatar"></el-avatar>
                <h3>{{ member.name }}</h3>
                <p class="title">{{ member.title }}</p>
                <p class="description">{{ member.description }}</p>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'About',
  data() {
    return {
      teamMembers: [
        {
          id: 1,
          name: '张教授',
          title: '中心主任',
          description: '临床心理学博士，从事心理咨询20年',
          avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
        },
        {
          id: 2,
          name: '李医生',
          title: '心理咨询师',
          description: '心理学硕士，注册心理咨询师',
          avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
        },
        {
          id: 3,
          name: '王老师',
          title: '心理咨询师',
          description: '教育心理学硕士，学校心理辅导经验丰富',
          avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
        }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.about {
  .page-title {
    text-align: center;
    font-size: 2.5rem;
    color: $gray-800;
    margin-bottom: 3rem;
  }

  .about-content {
    h2 {
      font-size: 2rem;
      color: $gray-800;
      margin-bottom: 1.5rem;
    }

    h3 {
      font-size: 1.5rem;
      color: $gray-700;
      margin: 2rem 0 1rem;
    }

    p {
      color: $gray-600;
      line-height: 1.8;
      margin-bottom: 1rem;
    }

    ul {
      list-style: none;
      padding: 0;

      li {
        color: $gray-600;
        margin: 0.5rem 0;
        padding-left: 1.5rem;
        position: relative;

        &:before {
          content: "•";
          color: $primary-color;
          position: absolute;
          left: 0;
        }
      }
    }
  }

  .team-section {
    h2 {
      font-size: 2rem;
      color: $gray-800;
      margin-bottom: 2rem;
    }

    .team-grid {
      display: grid;
      grid-template-columns: repeat(1, 1fr);
      gap: 2rem;
      
      @include respond-to('lg') {
        grid-template-columns: repeat(2, 1fr);
      }
    }

    .team-member {
      text-align: center;
      padding: 1.5rem;
      background-color: $gray-100;
      border-radius: 0.5rem;
      @include transition;

      &:hover {
        transform: translateY(-5px);
        @include box-shadow(2);
      }

      h3 {
        font-size: 1.25rem;
        color: $gray-800;
        margin: 1rem 0 0.5rem;
      }

      .title {
        color: $primary-color;
        font-weight: 600;
        margin-bottom: 0.5rem;
      }

      .description {
        color: $gray-600;
      }
    }
  }
}
</style> 