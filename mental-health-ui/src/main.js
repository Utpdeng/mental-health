import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import 'animate.css'
import './styles/global.scss'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

// 配置 Font Awesome
library.add(fas)
Vue.component('font-awesome-icon', FontAwesomeIcon)

// 使用 Element UI
Vue.use(ElementUI)

// 注册全局组件
Vue.component('LoadingSpinner', LoadingSpinner)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
