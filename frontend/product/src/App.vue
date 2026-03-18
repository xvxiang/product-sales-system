<!-- src/App.vue -->
<template>
  <!-- router-view 是路由出口，你的 ProductList 页面会渲染在这里 -->
  <router-view />
</template>

<script setup>
// 这里不需要任何代码
</script>

<style>
/* 全局样式可以在这里写，或者保留默认的 reset 样式 */
body {
  margin: 0;
  padding: 0;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
}
</style>