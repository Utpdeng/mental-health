<template>
  <div class="article-detail-page">
    <div class="article-header" v-if="article">
      <div class="container">
        <h1>{{ article.title }}</h1>
        <div class="article-meta">
          <span><i class="el-icon-user"></i> {{ article.author }}</span>
          <span><i class="el-icon-folder"></i> {{ article.category }}</span>
          <span><i class="el-icon-date"></i> {{ article.publishDate }}</span>
        </div>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="article-container" v-loading="loading">
          <template v-if="article">
            <div class="article-image" v-if="article.imageUrls && article.imageUrls.length">
              <img :src="article.imageUrls[0]" :alt="article.title">
            </div>
            <div class="article-content">
              <div v-html="article.content"></div>
              
              <div class="article-actions">
                <el-button type="primary" @click="goBack">返回文章列表</el-button>
                <el-button type="success" icon="el-icon-share">分享</el-button>
              </div>
            </div>
          </template>
          
          <el-empty v-else description="文章不存在或已被删除">
            <el-button type="primary" @click="goBack">返回列表</el-button>
          </el-empty>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getArticle } from '@/api/article'

export default {
  name: 'ArticleDetail',
  data() {
    return {
      articleId: null,
      article: null,
      loading: false,
      // 添加本地文章数据
      mockArticles: {
        "679ca16c804c420b939f9945": {
          id: "679ca16c804c420b939f9945",
          title: '克服焦虑的小技巧',
          content: `
            <h2>什么是焦虑？</h2>
            <p>焦虑是人类面对未知、威胁或压力时的一种正常情绪反应。适度的焦虑有助于我们保持警觉和动力，但当焦虑变得过度或持续时间太长，就可能影响我们的日常生活和身心健康。</p>
            
            <h2>焦虑的常见症状</h2>
            <p>焦虑的表现可能包括：</p>
            <ul>
              <li>心跳加速</li>
              <li>呼吸急促</li>
              <li>肌肉紧张</li>
              <li>难以入睡</li>
              <li>注意力不集中</li>
              <li>过度担忧</li>
              <li>焦躁不安</li>
            </ul>
            
            <h2>五种有效的焦虑缓解技巧</h2>
            
            <h3>1. 深呼吸练习</h3>
            <p>深呼吸是一种简单而有效的放松技巧，可以随时随地进行。当你感到焦虑时，尝试这个简单的4-7-8呼吸法：</p>
            <ol>
              <li>坐在舒适的位置，背部挺直</li>
              <li>通过鼻子吸气4秒钟</li>
              <li>屏住呼吸7秒钟</li>
              <li>通过嘴巴呼气8秒钟，呼气时发出"嘘"的声音</li>
              <li>重复这个过程4-5次</li>
            </ol>
            
            <h3>2. 正念冥想</h3>
            <p>正念冥想帮助我们将注意力集中在当下，不被过去的遗憾或未来的担忧所困扰。尝试每天花5-10分钟进行正念冥想：</p>
            <ol>
              <li>找一个安静的地方，采取舒适的坐姿</li>
              <li>闭上眼睛，专注于你的呼吸</li>
              <li>当思绪wandering时，温和地将注意力带回到呼吸上</li>
              <li>不要评判自己的思绪，只是观察它们</li>
            </ol>
            
            <h3>3. 身体扫描放松法</h3>
            <p>身体扫描帮助我们释放身体各部位的紧张感：</p>
            <ol>
              <li>从脚趾开始，逐渐向上关注身体的每个部位</li>
              <li>注意每个部位的感觉，是否有紧张或不适</li>
              <li>尝试在呼气时释放每个部位的紧张感</li>
              <li>想象温暖、放松的感觉流过你的全身</li>
            </ol>
            
            <h3>4. 限制咖啡因和酒精摄入</h3>
            <p>咖啡因和酒精可能加剧焦虑症状。适量减少这些物质的摄入可能有助于缓解焦虑：</p>
            <ul>
              <li>减少咖啡、茶和能量饮料的摄入</li>
              <li>避免使用酒精作为应对焦虑的方式</li>
              <li>保持充分的水分摄入</li>
            </ul>
            
            <h3>5. 规律的体育锻炼</h3>
            <p>体育锻炼是缓解焦虑的有效方法之一。即使是轻度到中度的体育活动也能释放内啡肽，提升心情：</p>
            <ul>
              <li>每周尝试进行至少150分钟的中等强度活动</li>
              <li>选择你喜欢的活动，如散步、游泳、骑自行车或瑜伽</li>
              <li>保持规律性比一次高强度锻炼更重要</li>
            </ul>
            
            <h2>寻求专业帮助</h2>
            <p>如果你的焦虑持续存在并影响日常生活，请考虑寻求专业心理健康服务。心理咨询师和医生可以提供额外的支持和治疗选择，如认知行为疗法(CBT)、药物治疗或其他适合你特定需求的干预措施。</p>
            
            <h2>结语</h2>
            <p>记住，管理焦虑是一个过程，需要时间和耐心。尝试这些技巧，找到最适合你的方法，并对自己保持耐心和善意。</p>
          `,
          author: '张心理',
          category: '情绪管理',
          imageUrls: ['https://picsum.photos/id/1/800/400'],
          publishDate: '2025-03-10'
        },
        "679ca14e804c420b939f9944": {
          id: "679ca14e804c420b939f9944",
          title: '正念冥想的好处',
          content: `
            <h2>什么是正念冥想？</h2>
            <p>正念冥想是一种源自古老佛教传统的修习方法，它鼓励我们专注于当下，培养对自己思想、情感和感受的觉知，同时不带评判。在现代背景下，正念已经被科学研究证实对心理健康有显著的益处。</p>
            
            <h2>正念冥想的科学依据</h2>
            <p>近几十年来，大量科学研究证实了正念冥想对大脑和身体的积极影响。神经科学研究表明，长期的正念修习可以改变大脑的结构和功能，尤其是负责注意力、情绪调节和自我意识的区域。</p>
            
            <h2>正念冥想的七大好处</h2>
            
            <h3>1. 减轻压力和焦虑</h3>
            <p>正念冥想通过帮助我们改变对压力源的反应方式来减轻压力。研究表明，定期的正念修习可以降低体内皮质醇(压力荷尔蒙)的水平，并减少焦虑症状。当我们学会观察自己的想法和感受而不被其淹没时，我们就能够以更加平静和有效的方式应对生活中的挑战。</p>
            
            <h3>2. 提高专注力和注意力</h3>
            <p>在这个充满干扰的时代，保持专注变得越来越困难。正念修习通过训练大脑将注意力集中在当下的体验上，如呼吸或身体感受，从而增强我们的专注能力。多项研究表明，经常进行正念练习的人在需要持续注意力的任务中表现更好。</p>
            
            <h3>3. 改善情绪健康</h3>
            <p>正念冥想可以帮助我们更好地理解和管理情绪，减少抑郁症状，并增强心理韧性。通过学习观察情绪的生起和消失，而不必立即反应或被情绪所控制，我们可以培养更健康的情绪调节能力。</p>
            
            <h3>4. 增强自我意识</h3>
            <p>正念修习帮助我们深入了解自己的思想、情感和行为模式。这种增强的自我意识使我们能够识别不健康的习惯和反应模式，并作出更明智的选择。当我们对自己有更清晰的认识时，我们与他人的关系也会改善。</p>
            
            <h3>5. 改善身体健康</h3>
            <p>除了心理益处外，正念冥想还与多种身体健康改善相关，包括：</p>
            <ul>
              <li>降低血压</li>
              <li>改善睡眠质量</li>
              <li>减轻慢性疼痛</li>
              <li>增强免疫系统功能</li>
              <li>减少消化问题</li>
            </ul>
            
            <h3>6. 增强情绪韧性</h3>
            <p>正念冥想帮助我们培养面对困难和挑战的能力。通过提高我们对情绪反应的觉知，正念使我们能够在困难情况下保持平衡，并从失败或挫折中更快恢复。</p>
            
            <h3>7. 培养慈悲和同理心</h3>
            <p>许多正念修习包含了慈悲冥想，这种练习旨在培养对自己和他人的善意和同理心。研究表明，这些修习可以增加亲社会行为，改善人际关系，并增强社会连接感。</p>
            
            <h2>如何开始正念冥想</h2>
            <p>对于初学者，可以从以下简单步骤开始：</p>
            <ol>
              <li>找一个安静、舒适的地方坐下</li>
              <li>设定一个计时器（开始可以是5-10分钟）</li>
              <li>采取舒适但挺直的姿势</li>
              <li>将注意力集中在呼吸上</li>
              <li>当注意力wandering时，温和地将其带回呼吸</li>
              <li>练习结束时，花一点时间注意你的感受</li>
            </ol>
            
            <h2>结语</h2>
            <p>正念冥想不是一种即时的解决方案，而是一种需要时间和耐心的修习。最好的方法是每天进行短时间的练习，而不是偶尔进行长时间的冥想。随着时间的推移，你将开始注意到正念给你的生活带来的积极变化。</p>
          `,
          author: '李教授',
          category: '冥想技巧',
          imageUrls: ['https://picsum.photos/id/2/800/400'],
          publishDate: '2025-03-15'
        },
        "679ca12d804c420b939f9943": {
          id: "679ca12d804c420b939f9943",
          title: '心理健康与饮食',
          content: `
            <h2>饮食与心理健康的关系</h2>
            <p>越来越多的研究表明，我们的饮食选择不仅影响身体健康，还直接影响我们的心理健康和情绪状态。大脑是身体能量消耗最多的器官之一，它需要适当的营养来维持最佳功能。</p>
            
            <p>这种关系被称为"肠-脑轴"，指的是肠道和大脑之间的双向通信系统。我们的肠道微生物组对产生影响心情和行为的神经递质（如血清素和多巴胺）起着重要作用。</p>
            
            <h2>影响心理健康的主要营养素</h2>
            
            <h3>1. Omega-3脂肪酸</h3>
            <p>Omega-3脂肪酸对大脑健康至关重要，研究表明它们可以帮助减轻抑郁和焦虑症状。</p>
            <p><strong>食物来源：</strong></p>
            <ul>
              <li>脂肪鱼类（如三文鱼、沙丁鱼和鲭鱼）</li>
              <li>亚麻籽和亚麻籽油</li>
              <li>核桃</li>
              <li>奇亚籽</li>
            </ul>
            
            <h3>2. 复合碳水化合物</h3>
            <p>复合碳水化合物提供稳定的能量来源，有助于保持血糖水平稳定，这对情绪稳定非常重要。</p>
            <p><strong>食物来源：</strong></p>
            <ul>
              <li>全谷物（如燕麦、糙米和全麦面包）</li>
              <li>豆类和扁豆</li>
              <li>水果和蔬菜</li>
            </ul>
            
            <h3>3. 蛋白质</h3>
            <p>蛋白质含有氨基酸，是合成神经递质所必需的。充足的蛋白质摄入有助于保持稳定的心情。</p>
            <p><strong>食物来源：</strong></p>
            <ul>
              <li>瘦肉和家禽</li>
              <li>鱼类</li>
              <li>蛋类</li>
              <li>豆腐和其他大豆制品</li>
              <li>豆类和扁豆</li>
              <li>坚果和种子</li>
            </ul>
            
            <h3>4. 抗氧化剂</h3>
            <p>抗氧化剂保护大脑免受氧化应激的影响，氧化应激与抑郁和焦虑有关。</p>
            <p><strong>食物来源：</strong></p>
            <ul>
              <li>浆果（如蓝莓、草莓和黑莓）</li>
              <li>深色绿叶蔬菜</li>
              <li>深色巧克力（70%以上可可含量）</li>
              <li>坚果和种子</li>
            </ul>
            
            <h3>5. 维生素D</h3>
            <p>维生素D缺乏与抑郁症风险增加有关。虽然阳光是维生素D的主要来源，但某些食物也含有这种重要的营养素。</p>
            <p><strong>食物来源：</strong></p>
            <ul>
              <li>脂肪鱼（如三文鱼和鲭鱼）</li>
              <li>强化食品（如某些牛奶、橙汁和谷物）</li>
              <li>蛋黄</li>
            </ul>
            
            <h3>6. B族维生素</h3>
            <p>B族维生素对能量产生和神经系统功能至关重要。维生素B12和叶酸尤其与抑郁症和认知功能有关。</p>
            <p><strong>食物来源：</strong></p>
            <ul>
              <li>全谷物</li>
              <li>瘦肉和家禽</li>
              <li>鱼类</li>
              <li>蛋类</li>
              <li>豆类</li>
              <li>深色绿叶蔬菜</li>
            </ul>
            
            <h2>促进心理健康的饮食模式</h2>
            
            <h3>地中海饮食</h3>
            <p>地中海饮食富含水果、蔬菜、全谷物、鱼类、橄榄油和坚果，被证明与抑郁风险降低有关。这种饮食模式提供了丰富的抗氧化剂、健康脂肪和纤维，所有这些都对大脑健康有益。</p>
            
            <h3>益生菌食品</h3>
            <p>益生菌食品含有对肠道健康有益的"好"细菌，可能通过肠-脑轴影响心理健康。</p>
            <p><strong>食物来源：</strong></p>
            <ul>
              <li>酸奶（带有活性培养物）</li>
              <li>泡菜</li>
              <li>康普茶</li>
              <li>酸菜</li>
              <li>味噌</li>
            </ul>
            
            <h2>应避免的食物</h2>
            <p>某些食物可能对心理健康产生负面影响：</p>
            <ul>
              <li><strong>高糖食品：</strong>可能导致血糖波动，影响情绪稳定性</li>
              <li><strong>高度加工食品：</strong>通常缺乏重要营养素，可能含有对大脑健康有害的添加剂</li>
              <li><strong>过量酒精：</strong>酒精是一种抑制剂，可能加重抑郁症状并干扰睡眠</li>
              <li><strong>过量咖啡因：</strong>可能加剧焦虑症状并干扰睡眠</li>
            </ul>
            
            <h2>实用建议</h2>
            <ol>
              <li>遵循规律的饮食模式，避免长时间不进食</li>
              <li>保持足够的水分摄入</li>
              <li>选择全食物而非加工食品</li>
              <li>增加水果和蔬菜的摄入</li>
              <li>包括优质蛋白质和健康脂肪</li>
              <li>减少糖和精制碳水化合物的摄入</li>
              <li>考虑将益生菌食品纳入饮食</li>
            </ol>
            
            <h2>结语</h2>
            <p>虽然饮食是心理健康的重要因素，但它只是整体健康的一部分。综合方法包括定期锻炼、充足睡眠、压力管理和必要时寻求专业帮助，对于维持良好的心理健康同样重要。</p>
            
            <p>如果你正在经历严重的心理健康问题，饮食虽然可以支持你的恢复，但不应替代专业的医疗或心理健康治疗。</p>
          `,
          author: '王营养师',
          category: '健康生活',
          imageUrls: ['https://picsum.photos/id/3/800/400'],
          publishDate: '2025-04-20'
        },
        "679ca0f7804c420b939f9942": {
          id: "679ca0f7804c420b939f9942",
          title: '建立健康的人际关系',
          content: `
            <h2>人际关系对心理健康的重要性</h2>
            <p>人类是社会性动物，我们天生就需要与他人建立联系。健康的人际关系是我们心理健康和整体幸福感的基础。研究表明，拥有强大的社交网络和支持系统的人通常更快乐、更健康，并且能够更好地应对生活中的压力和挑战。</p>
            
            <h2>健康人际关系的特征</h2>
            <p>健康的人际关系具有以下几个关键特征：</p>
            
            <h3>1. 相互尊重</h3>
            <p>健康的关系基于相互尊重，这意味着：</p>
            <ul>
              <li>重视对方的想法、感受和需求</li>
              <li>尊重对方的边界和个人空间</li>
              <li>不试图控制或操纵对方</li>
              <li>接受并欣赏彼此的差异</li>
            </ul>
            
            <h3>2. 有效沟通</h3>
            <p>沟通是所有健康关系的基础，有效的沟通包括：</p>
            <ul>
              <li>积极倾听，不仅听对方说了什么，还要理解背后的情感和需求</li>
              <li>清晰、诚实地表达自己的想法和感受</li>
              <li>使用"我"陈述而非指责性的"你"陈述</li>
              <li>保持开放的心态，愿意讨论困难话题</li>
              <li>注意非语言沟通，如面部表情和肢体语言</li>
            </ul>
            
            <h3>3. 信任与诚实</h3>
            <p>信任是健康关系的核心，它建立在：</p>
            <ul>
              <li>言行一致</li>
              <li>信守承诺</li>
              <li>坦诚相待</li>
              <li>对对方的信任和支持</li>
            </ul>
            
            <h3>4. 平衡的相互依赖</h3>
            <p>健康的关系既不是完全独立也不是过度依赖，而是：</p>
            <ul>
              <li>既维持个人身份和兴趣，又享受共同的时间和活动</li>
              <li>能够寻求帮助，也能够独立解决问题</li>
              <li>在关系中感到安全和支持，同时保持个人成长</li>
            </ul>
            
            <h3>5. 有效的冲突解决</h3>
            <p>所有关系都会有冲突，健康的关系能够：</p>
            <ul>
              <li>以建设性的方式处理分歧</li>
              <li>专注于问题而非人身攻击</li>
              <li>寻求双赢的解决方案</li>
              <li>在必要时妥协</li>
              <li>从冲突中学习和成长</li>
            </ul>
            
            <h2>建立和维护健康关系的策略</h2>
            
            <h3>1. 自我认知和个人发展</h3>
            <p>健康关系始于健康的自我：</p>
            <ul>
              <li>了解自己的优点、缺点、需求和价值观</li>
              <li>培养自尊和自我接纳</li>
              <li>学会设定健康的界限</li>
              <li>处理个人的问题和不安全感</li>
            </ul>
            
            <h3>2. 培养沟通技巧</h3>
            <p>有效沟通是可以学习和提高的：</p>
            <ul>
              <li>练习积极倾听，不打断，真正理解对方</li>
              <li>学习表达需求和界限，而不显得咄咄逼人或被动</li>
              <li>定期进行深入、有意义的对话</li>
              <li>学会在冲突中保持冷静和尊重</li>
            </ul>
            
            <h3>3. 建立情感智力</h3>
            <p>情感智力对于健康关系至关重要：</p>
            <ul>
              <li>识别和理解自己的情绪</li>
              <li>发展对他人情绪的同理心</li>
              <li>学习有效管理强烈情绪，如愤怒或失望</li>
              <li>培养情感弹性，从挫折中恢复</li>
            </ul>
            
            <h3>4. 投入时间和努力</h3>
            <p>所有健康的关系都需要持续的投入：</p>
            <ul>
              <li>定期花时间与重要的人相处</li>
              <li>表达感谢和欣赏</li>
              <li>在对方需要时提供支持</li>
              <li>创造共同的积极体验和回忆</li>
              <li>在关系遇到困难时寻求帮助，如伴侣咨询或家庭治疗</li>
            </ul>
            
            <h3>5. 维护多样化的社交网络</h3>
            <p>拥有不同类型的关系有助于满足不同的社交需求：</p>
            <ul>
              <li>培养深厚的亲密关系（伴侣、密友）</li>
              <li>维持家庭关系</li>
              <li>发展社区和工作关系</li>
              <li>参与团体活动，如兴趣小组或志愿服务</li>
            </ul>
            
            <h2>处理有毒关系</h2>
            <p>有时，我们需要识别并处理不健康的关系：</p>
            <ul>
              <li>认识有毒关系的迹象（如控制、操纵、缺乏尊重、持续伤害）</li>
              <li>设定和坚持健康的界限</li>
              <li>在必要时寻求专业帮助</li>
              <li>有时，结束关系可能是最健康的选择</li>
              <li>从不健康的关系中学习，避免未来重复类似模式</li>
            </ul>
            
            <h2>结语</h2>
            <p>建立健康的人际关系需要时间、努力和自我认知，但回报是巨大的。健康的关系不仅提高我们的生活质量，还能为我们提供应对生活挑战的弹性和力量。记住，关系是一个持续的过程，始终有提升和成长的空间。</p>
          `,
          author: '赵心理咨询师',
          category: '人际关系',
          imageUrls: ['https://picsum.photos/id/4/800/400'],
          publishDate: '2025-04-05'
        },
        "679c9b5fa4644d5cc4326d01": {
          id: "679c9b5fa4644d5cc4326d01",
          title: '提升情绪智商',
          content: `
            <h2>什么是情绪智商（EQ）？</h2>
            <p>情绪智商（Emotional Intelligence或EQ）是指个人识别、理解、管理自己情绪以及感知和影响他人情绪的能力。与智商（IQ）相比，情绪智商更多地与个人的社交能力、领导力和幸福感相关。</p>
            
            <p>心理学家Peter Salovey和John Mayer首次提出情绪智商的概念，而Daniel Goleman的著作《情绪智商》则使这一概念广为人知。</p>
            
            <h2>情绪智商的五个核心要素</h2>
            
            <h3>1. 自我意识</h3>
            <p>自我意识是情绪智商的基础，它包括：</p>
            <ul>
              <li>识别自己的情绪和它们对思想、行为的影响</li>
              <li>准确评估自己的优势和局限性</li>
              <li>建立健康的自信和自我价值感</li>
            </ul>
            <p><strong>如何提升：</strong></p>
            <ul>
              <li>定期进行自我反思，可以通过写日记或冥想</li>
              <li>询问并接受他人的反馈</li>
              <li>注意自己的情绪触发因素和身体反应</li>
            </ul>
            
            <h3>2. 自我管理</h3>
            <p>自我管理指的是控制和调节情绪的能力：</p>
            <ul>
              <li>保持冷静和清晰思考，即使在压力下</li>
              <li>管理冲动和负面情绪</li>
              <li>适应变化和克服障碍</li>
              <li>保持积极的展望</li>
            </ul>
            <p><strong>如何提升：</strong></p>
            <ul>
              <li>练习呼吸技巧和正念冥想</li>
              <li>培养健康的应对策略，如体育锻炼或与朋友交谈</li>
              <li>制定明确的目标并追踪进度</li>
              <li>学习重新构建负面想法</li>
            </ul>
            
            <h3>3. 社会意识</h3>
            <p>社会意识涉及理解他人情绪和需求的能力：</p>
            <ul>
              <li>发展同理心，理解他人的感受和观点</li>
              <li>识别团体或组织中的社会动态和权力关系</li>
              <li>认识到他人的需求和关切</li>
            </ul>
            <p><strong>如何提升：</strong></p>
            <ul>
              <li>积极倾听，注意言语和非言语线索</li>
              <li>尝试从他人的角度看待情况</li>
              <li>阅读小说或观看讲述不同人生经历的电影</li>
              <li>与不同背景、文化的人交流</li>
            </ul>
            
            <h3>4. 人际关系管理</h3>
            <p>这是应用情绪智商建立和维护健康关系的能力：</p>
            <ul>
              <li>有效沟通和解决冲突</li>
              <li>影响和鼓舞他人</li>
              <li>促进协作和团队合作</li>
              <li>建立有意义的联系</li>
            </ul>
            <p><strong>如何提升：</strong></p>
            <ul>
              <li>练习清晰、公开的沟通</li>
              <li>学习积极的冲突解决技巧</li>
              <li>寻找合作而非竞争的机会</li>
              <li>展示真诚的关心和兴趣</li>
            </ul>
            
            <h3>5. 动机</h3>
            <p>有些情绪智商模型包括自我激励作为第五个要素：</p>
            <ul>
              <li>设定有挑战性但可实现的目标</li>
              <li>对成功和持续改进保持热情</li>
              <li>坚持面对挫折和障碍</li>
              <li>找到内在动力而非仅依赖外在奖励</li>
            </ul>
            <p><strong>如何提升：</strong></p>
            <ul>
              <li>明确你的价值观和目标</li>
              <li>将大目标分解为可管理的步骤</li>
              <li>庆祝小成功</li>
              <li>找到工作和活动中的意义和目的</li>
            </ul>
            
            <h2>高情商的好处</h2>
            <p>发展情绪智商可以带来许多好处：</p>
            <ul>
              <li><strong>更好的人际关系：</strong> 更有效地沟通和解决冲突</li>
              <li><strong>职业成功：</strong> 研究表明，高情商是职场成功的关键预测因素</li>
              <li><strong>改善心理健康：</strong> 减少压力、焦虑和抑郁</li>
              <li><strong>更好的决策：</strong> 情绪觉知有助于做出更明智的选择</li>
              <li><strong>提高领导能力：</strong> 有效领导者通常拥有高情商</li>
              <li><strong>增强韧性：</strong> 更好地应对挑战和逆境</li>
            </ul>
            
            <h2>提高情绪智商的实践活动</h2>
            
            <h3>日常实践</h3>
            <ol>
              <li><strong>情绪日记：</strong> 每天记录你的情绪、触发因素和反应</li>
              <li><strong>"暂停"技巧：</strong> 在情绪激动时，暂停、呼吸，然后再做出反应</li>
              <li><strong>积极倾听练习：</strong> 有意识地练习专注倾听，不打断，提问澄清</li>
              <li><strong>情绪词汇表扩展：</strong> 学习更多描述情绪的词汇，超越"好"、"坏"、"悲伤"、"高兴"</li>
              <li><strong>感恩练习：</strong> 每天列出你感谢的三件事</li>
            </ol>
            
            <h3>长期发展</h3>
            <ol>
              <li><strong>寻求反馈：</strong> 请可信赖的朋友或同事给你关于你情绪反应的诚实反馈</li>
              <li><strong>阅读情绪智商相关书籍：</strong> 如Goleman的《情绪智商》或Bradberry与Greaves的《情绪智商2.0》</li>
              <li><strong>参加培训课程：</strong> 许多组织和机构提供情绪智商提升课程</li>
              <li><strong>寻求辅导或治疗：</strong> 专业人士可以帮助解决情绪管理的特定挑战</li>
              <li><strong>正念冥想：</strong> 定期练习可以增强情绪觉知和控制</li>
            </ol>
            
            <h2>结语</h2>
            <p>情绪智商不是与生俱来的固定特质，而是可以通过有意识的努力和实践来发展的一组技能。通过提高你识别、理解和管理自己和他人情绪的能力，你可以改善你的人际关系、职业前景和整体幸福感。</p>
            
            <p>记住，提高情绪智商是一个终身的旅程，需要持续的自我反思和实践。即使是小的进步也能带来显著的积极影响。</p>
          `,
          author: '钱博士',
          category: '个人成长',
          imageUrls: ['https://picsum.photos/id/5/800/400'],
          publishDate: '2025-03-12'
        },
        "678f9c090931b935cdb9e6cc": {
          id: "678f9c090931b935cdb9e6cc",
          title: '如何管理压力',
          content: `
            <h2>理解压力</h2>
            <p>压力是我们对身体或心理需求的自然反应。它可以来自工作、人际关系、财务问题或生活中的重大变化。尽管适度的压力有时可以提高我们的警觉性和表现，但长期的慢性压力可能对身体和心理健康产生负面影响。</p>
            
            <p>压力会触发体内的"战斗或逃跑"反应，释放荷尔蒙如肾上腺素和皮质醇。短期内这是有益的，但长期处于高压力状态可能导致多种健康问题，包括高血压、心脏病、消化问题、免疫系统减弱、抑郁和焦虑。</p>
            
            <h2>压力的常见症状</h2>
            
            <h3>身体症状</h3>
            <ul>
              <li>头痛或头晕</li>
              <li>肌肉紧张或疼痛</li>
              <li>胸痛或心跳加速</li>
              <li>疲劳感</li>
              <li>睡眠问题</li>
              <li>消化问题</li>
              <li>免疫系统减弱</li>
            </ul>
            
            <h3>心理和情绪症状</h3>
            <ul>
              <li>焦虑或烦躁</li>
              <li>情绪低落</li>
              <li>注意力不集中</li>
              <li>过度担忧</li>
              <li>缺乏动力或兴趣</li>
              <li>易怒或情绪波动</li>
              <li>感到不知所措</li>
            </ul>
            
            <h3>行为症状</h3>
            <ul>
              <li>饮食习惯改变（过度进食或食欲不振）</li>
              <li>社交退缩</li>
              <li>拖延或逃避责任</li>
              <li>使用酒精、香烟或药物来放松</li>
              <li>神经性习惯（如咬指甲、踱步）</li>
            </ul>
            
            <h2>有效的压力管理策略</h2>
            
            <h3>1. 识别压力源</h3>
            <p>了解什么导致你的压力是管理它的第一步：</p>
            <ul>
              <li>保持压力日记，记录何时感到压力大，以及触发因素</li>
              <li>区分可控和不可控的压力源</li>
              <li>优先处理可以改变的情况</li>
            </ul>
            
            <h3>2. 发展健康的应对机制</h3>
            <p>健康的应对策略帮助减轻压力，而不会造成更多问题：</p>
            
            <h4>身体策略</h4>
            <ul>
              <li><strong>定期锻炼：</strong> 每周至少150分钟的中等强度活动可以减轻压力，释放内啡肽</li>
              <li><strong>充足睡眠：</strong> 大多数成年人需要7-9小时的优质睡眠</li>
              <li><strong>平衡饮食：</strong> 减少咖啡因、糖和酒精，增加水果、蔬菜和全谷物</li>
              <li><strong>深呼吸和放松技巧：</strong> 如腹式呼吸、渐进性肌肉放松或瑜伽</li>
            </ul>
            
            <h4>心理策略</h4>
            <ul>
              <li><strong>正念冥想：</strong> 帮助你专注于当下，而不陷入过去的忧虑或未来的担忧</li>
              <li><strong>认知重构：</strong> 识别并挑战消极思维模式</li>
              <li><strong>设定界限：</strong> 学会说"不"，避免过度承诺</li>
              <li><strong>时间管理：</strong> 设定现实的目标，优先处理重要任务</li>
              <li><strong>培养积极的自我对话：</strong> 用支持性的语言替代自我批评</li>
            </ul>
            
            <h4>社交策略</h4>
            <ul>
              <li><strong>寻求支持：</strong> 与朋友、家人或支持团体分享你的感受</li>
              <li><strong>培养人际关系：</strong> 投入时间维护重要的关系</li>
              <li><strong>在必要时寻求专业帮助：</strong> 心理治疗师可以提供额外的工具和支持</li>
            </ul>
            
            <h4>生活方式策略</h4>
            <ul>
              <li><strong>培养爱好：</strong> 发展带给你快乐和满足感的活动</li>
              <li><strong>与大自然接触：</strong> 在自然环境中度过时间可以减轻压力</li>
              <li><strong>减少媒体摄入：</strong> 限制新闻和社交媒体时间</li>
              <li><strong>志愿服务：</strong> 帮助他人可以转移注意力并增加目标感</li>
              <li><strong>简化生活：</strong> 减少生活中的混乱和复杂性</li>
            </ul>
            
            <h3>3. 工作场所压力管理</h3>
            <p>特别是在工作环境中：</p>
            <ul>
              <li>明确优先级和期望</li>
              <li>学会有效沟通和冲突解决</li>
              <li>定期休息，包括午餐休息和休假</li>
              <li>使用可用的资源，如雇员辅助计划</li>
              <li>维持工作与生活的平衡</li>
            </ul>
            
            <h3>4. 长期心态和习惯</h3>
            <p>培养这些品质可以增强你面对压力的韧性：</p>
            <ul>
              <li><strong>适应性：</strong> 接受变化是生活的一部分</li>
              <li><strong>灵活性：</strong> 愿意调整计划和期望</li>
              <li><strong>乐观态度：</strong> 寻找积极面，即使在困难时期</li>
              <li><strong>感恩：</strong> 定期反思你生活中的积极方面</li>
              <li><strong>接受不完美：</strong> 放弃追求完美，接受"足够好"</li>
            </ul>
            
            <h2>何时寻求专业帮助</h2>
            <p>如果压力开始严重影响你的生活质量、工作表现或关系，考虑寻求专业帮助：</p>
            <ul>
              <li>持续的身体症状未被医疗检查排除其他病因</li>
              <li>压力引起的焦虑或抑郁感</li>
              <li>使用酒精、药物或其他物质来应对</li>
              <li>无法履行日常责任</li>
              <li>感到不知所措或无助</li>
            </ul>
            
            <h2>结语</h2>
            <p>压力管理是一种学习和不断实践的技能。通过理解你的压力源并发展健康的应对策略，你可以减少压力的负面影响，并培养应对生活挑战的韧性。记住，寻求支持不是软弱的表现，而是自我照顾的重要部分。</p>
          `,
          author: '孙医生',
          category: '压力管理',
          imageUrls: ['https://picsum.photos/id/6/800/400'],
          publishDate: '2025-03-18'
        }
      }
    }
  },
  created() {
    // 从URL中解析文章ID，不依赖Vue Router的参数
    const pathParts = window.location.pathname.split('/');
    this.articleId = pathParts[pathParts.length - 1];
    console.log('从URL路径中提取的文章ID:', this.articleId);
    
    if (!this.articleId) {
      console.error('无法从URL中获取文章ID');
      this.$message.error('无法获取文章ID');
      return;
    }
    
    this.fetchArticle();
  },
  methods: {
    async fetchArticle() {
      this.loading = true
      try {
        // 确保有文章ID
        if (!this.articleId) {
          console.error('未提供文章ID')
          this.$message.error('文章ID未提供')
          this.loading = false
          return
        }

        // 尝试从API获取
        // try {
        //   const res = await getArticle(this.articleId)
        //   if (res && res.status === '200' && res.data) {
        //     this.article = res.data
        //     this.loading = false
        //     return
        //   }
        // } catch (apiError) {
        //   console.warn('API获取文章失败，尝试使用本地数据', apiError)
        // }
        
        // API获取失败，尝试从本地获取
        if (this.mockArticles[this.articleId]) {
          this.article = this.mockArticles[this.articleId]
          console.log('使用本地数据展示文章:', this.articleId)
        } else {
          // 找不到指定ID的文章，使用第一篇文章
          const firstArticleId = Object.keys(this.mockArticles)[0]
          this.article = this.mockArticles[firstArticleId]
          console.warn('未找到ID为', this.articleId, '的文章，使用第一篇文章代替')
        }
      } catch (error) {
        console.error('获取文章详情失败:', error)
        this.$message.error('加载文章失败')
      } finally {
        this.loading = false
      }
    },
    goBack() {
      // 使用原生方法返回文章列表页面，完全避开Vue Router
      const baseUrl = window.location.origin;
      window.location.href = `${baseUrl}/education`;
    }
  }
}
</script>

<style scoped>
.article-header {
  padding: 60px 0;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  text-align: center;
}

.article-header h1 {
  margin-bottom: 20px;
  font-size: 2.5rem;
}

.article-meta {
  display: flex;
  justify-content: center;
  gap: 20px;
  font-size: 14px;
}

.article-meta i {
  margin-right: 5px;
}

.article-container {
  background: white;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin: 30px 0;
}

.article-image {
  margin: -30px -30px 30px -30px;
  height: 400px;
  overflow: hidden;
}

.article-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.article-content {
  line-height: 1.8;
  color: #333;
}

.article-content h2 {
  margin: 30px 0 15px;
  color: var(--primary-color);
  font-size: 1.8rem;
}

.article-content h3 {
  margin: 25px 0 15px;
  color: #444;
  font-size: 1.4rem;
}

.article-content p {
  margin-bottom: 15px;
}

.article-content ul, .article-content ol {
  margin: 15px 0;
  padding-left: 20px;
}

.article-content li {
  margin-bottom: 8px;
}

.article-content strong {
  font-weight: 600;
}

.article-actions {
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid #eee;
  display: flex;
  justify-content: space-between;
}

@media (max-width: 768px) {
  .article-header h1 {
    font-size: 1.8rem;
  }
  
  .article-meta {
    flex-direction: column;
    gap: 10px;
  }
  
  .article-container {
    padding: 20px;
  }
  
  .article-image {
    margin: -20px -20px 20px -20px;
    height: 250px;
  }
}
</style> 