// src/utils/request.js
import axios from 'axios';
import { ElMessage } from 'element-plus';

// 创建 axios 实例
const service = axios.create({
  baseURL: 'http://localhost:8080', // ⚠️ 注意：这里改成你后端的实际地址，如果是 8080 就不用动
  timeout: 5000, // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    // 如果有 token，可以在这里添加
    // const token = localStorage.getItem('token');
    // if (token) config.headers['Authorization'] = token;
    return config;
  },
  (error) => {
    console.error('Request error:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (response) => {
    // 假设后端返回格式是 { code: 200, data: ..., msg: "success" }
    // 根据你的后端实际返回结构调整这里的逻辑
    const res = response.data;
    
    // 如果后端直接返回数据列表（没有 code 字段），直接返回 res
    // 如果后端有统一封装，通常判断 code === 200 或 code === 0
    if (res.code !== 200 && res.code !== 0 && res.code !== undefined) {
      ElMessage.error(res.msg || '请求失败');
      return Promise.reject(new Error(res.msg || 'Error'));
    }
    return res;
  },
  (error) => {
    console.error('Response error:', error);
    ElMessage.error(error.message || '网络错误');
    return Promise.reject(error);
  }
);

export default service;