import { createRouter, createWebHistory } from 'vue-router'
import ProductList from '../views/ProductList.vue' // 稍后我们会创建这个文件

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'product',
      component: ProductList
    }
  ]
})

export default router
