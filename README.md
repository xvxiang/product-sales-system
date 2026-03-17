# 商品销售系统

## 项目简介
基于 SpringBoot + Vue + ElementUI 的商品销售管理系统 Demo，实现了商品的增删改查、导入导出、销售趋势图表等功能。

## 技术栈
- **后端**: SpringBoot 4.0.3 + MyBatis + MySQL
- **前端**: Vue 2 + ElementUI + ECharts
- **数据库**: MySQL 8.0+
- **其他**: Redis, Elasticsearch (可选)

## 功能说明

### 1. 商品列表展示
- 分页显示商品信息
- 支持按商品编号、商品名称、分类筛选
- 表格列：商品编号、名称、分类、价格、库存、单位、品牌、状态等

### 2. 工具栏功能
- **新增**: 添加新商品
- **编辑**: 修改商品信息
- **删除**: 删除单个商品
- **批量删除**: 可多选多条数据进行批量删除
- **搜索**: 根据商品编号模糊查询
- **导入**: 批量导入 Excel 数据
- **导出**: 支持导出全部、导出勾选的、导出当前页

### 3. 销售趋势图
点击商品编号可弹出折线图，展示该商品近期一周的销售数量趋势。

### 4. 数据导入
- 支持 Excel 文件导入 (.xlsx, .xls)
- 自动验证商品编号是否重复

### 5. 数据导出
- **导出全部**: 导出所有商品数据
- **导出勾选的**: 导出选中的商品数据
- **导出当前页**: 导出当前页面的商品数据

## API 接口

### 商品管理
- `GET /api/product/page` - 分页查询商品列表
- `GET /api/product/{id}` - 根据 ID 查询商品
- `POST /api/product` - 添加商品
- `PUT /api/product` - 更新商品
- `DELETE /api/product/{id}` - 删除商品
- `DELETE /api/product/batch` - 批量删除商品
- `POST /api/product/import` - 导入商品数据
- `GET /api/product/export` - 导出商品数据
- `GET /api/product/sales-trend/{productId}` - 查询销售趋势

### 分类管理
- `GET /api/category/list` - 查询所有分类


