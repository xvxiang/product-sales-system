// src/api/product.js
import request from '@/utils/request';

// 1. 分页查询商品列表
export function getProductList(params) {
  return request({
    url: '/api/product/page',
    method: 'get',
    params, // pageNum, pageSize, productCode, name, categoryId, status
  });
}

// 2. 根据 ID 查询单个商品
export function getProductById(id) {
  return request({
    url: `/api/product/${id}`,
    method: 'get',
  });
}

// 3. 添加商品
export function addProduct(data) {
  return request({
    url: '/api/product',
    method: 'post',
    data,
  });
}

// 4. 更新商品
export function updateProduct(data) {
  return request({
    url: '/api/product',
    method: 'put',
    data,
  });
}

// 5. 删除单个商品
export function deleteProduct(id) {
  return request({
    url: `/api/product/${id}`,
    method: 'delete',
  });
}

// 6. 批量删除商品
export function batchDeleteProduct(ids) {
  return request({
    url: '/api/product/batch',
    method: 'delete',
    data: ids, // List<Long>
  });
}

// 7. 查询销售趋势
export function getSalesTrend(productId) {
  return request({
    url: `/api/product/sales-trend/${productId}`,
    method: 'get',
  });
}

// 8. 导入产品数据 (Excel)
export function importProduct(file) {
  const formData = new FormData();
  formData.append('file', file);
  return request({
    url: '/api/product/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
}

// 9. 导出产品数据
export function exportProduct(params) {
  // 导出通常需要 blob 类型处理，这里先按普通请求写，如果需要下载文件需特殊处理
  return request({
    url: '/api/product/export',
    method: 'get',
    params,
    responseType: 'blob', // 重要：告诉 axios 返回的是二进制流
  });
}

// --- 分类管理接口 ---
export function getCategoryList() {
  return request({
    url: '/api/category/list',
    method: 'get',
  });
}

export function getCategoryById(id) {
  return request({
    url: `/api/category/${id}`,
    method: 'get',
  });
}