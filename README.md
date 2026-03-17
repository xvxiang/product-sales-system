# 商品销售系统 - 快速开始指南

## 项目简介
基于 SpringBoot + Vue + ElementUI 的商品销售管理系统 Demo，实现了商品的增删改查、导入导出、销售趋势图表等功能。

## 技术栈
- **后端**: SpringBoot 4.0.3 + MyBatis + MySQL
- **前端**: Vue 2 + ElementUI + ECharts
- **数据库**: MySQL 8.0+
- **其他**: Redis, Elasticsearch (可选)

## 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+
- Node.js (可选，仅用于前端开发)

## 快速开始

### 1. 数据库初始化

```bash
# 登录 MySQL
mysql -u root -p

# 执行 SQL 脚本
source src/main/resources/schema.sql
```

或者直接运行 `src/main/resources/schema.sql` 文件中的 SQL 语句。

### 2. 配置数据库连接

编辑 `src/main/resources/application.yml` 文件，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/product_db?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&allowPublicKeyRetrieval=true
    username: root
    password: 你的数据库密码
```

### 3. 启动项目

#### 方式一：使用 Maven 命令

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### 方式二：打包后运行

```bash
# 打包
mvn clean package -DskipTests

# 运行
java -jar target/product-sales-system-0.0.1-SNAPSHOT.jar
```

#### 方式三：使用 IDE

直接在 IDE 中运行 `ProductSalesSystemApplication.java` 的 main 方法。

### 4. 访问系统

启动成功后，在浏览器中访问：

```
http://localhost:8080/index.html
```

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

## 测试数据

系统已预置以下测试数据：
- **5 个商品分类**: 电子产品、服装服饰、食品饮料、家居用品、图书文具
- **10 个商品**: iPhone 15 Pro、MacBook Pro、AirPods Pro 等
- **7 个订单**: 包含不同日期的订单数据
- **销售数据**: 可用于查看销售趋势图

## 常见问题

### 1. 数据库连接失败
检查 MySQL 服务是否启动，数据库连接配置是否正确。

### 2. 跨域问题
项目已配置全局跨域，如有问题请检查 `CorsConfig.java`。

### 3. 导入失败
确保 Excel 文件格式正确，第一行为表头，数据从第二行开始。


## 联系方式
如有问题，请提交 Issue 或联系开发者。
