import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// 1. 引入 Element Plus 和样式
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 引入图标 (Element Plus 图标需要单独安装，这里为了简单先用文字或自带图标，若需图标可后续安装 @element-plus/icons-vue)

const app = createApp(App)

// 2. 使用插件
app.use(router)
app.use(ElementPlus)

app.mount('#app')
