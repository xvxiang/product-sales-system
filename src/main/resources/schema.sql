-- 创建数据库
CREATE DATABASE IF NOT EXISTS product_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE product_db;

-- 1. 商品分类表（只分一级）
DROP TABLE IF EXISTS category;
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类 ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    code VARCHAR(50) UNIQUE COMMENT '分类编码',
    description VARCHAR(500) COMMENT '分类描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 2. 产品表
DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商品 ID',
    product_code VARCHAR(50) UNIQUE NOT NULL COMMENT '商品编号',
    name VARCHAR(200) NOT NULL COMMENT '商品名称',
    category_id BIGINT COMMENT '分类 ID',
    price DECIMAL(10,2) DEFAULT 0.00 COMMENT '价格',
    cost DECIMAL(10,2) DEFAULT 0.00 COMMENT '成本',
    stock INT DEFAULT 0 COMMENT '库存',
    unit VARCHAR(20) DEFAULT '件' COMMENT '单位',
    brand VARCHAR(100) COMMENT '品牌',
    model VARCHAR(100) COMMENT '型号',
    specification VARCHAR(500) COMMENT '规格',
    description TEXT COMMENT '商品描述',
    status TINYINT DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    image_url VARCHAR(500) COMMENT '图片 URL',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_category_id (category_id),
    INDEX idx_product_code (product_code),
    INDEX idx_name (name),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品表';

-- 3. 订单表
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单 ID',
    order_no VARCHAR(50) UNIQUE NOT NULL COMMENT '订单编号',
    customer_name VARCHAR(100) COMMENT '客户名称',
    customer_phone VARCHAR(20) COMMENT '客户电话',
    total_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '订单总金额',
    status TINYINT DEFAULT 0 COMMENT '订单状态：0-待支付，1-已支付，2-已发货，3-已完成，4-已取消',
    order_date DATE COMMENT '订单日期',
    payment_time DATETIME COMMENT '支付时间',
    delivery_time DATETIME COMMENT '发货时间',
    complete_time DATETIME COMMENT '完成时间',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_order_no (order_no),
    INDEX idx_customer_name (customer_name),
    INDEX idx_order_date (order_date),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 4. 订单详情表
DROP TABLE IF EXISTS order_item;
CREATE TABLE order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单详情 ID',
    order_id BIGINT NOT NULL COMMENT '订单 ID',
    product_id BIGINT NOT NULL COMMENT '商品 ID',
    product_code VARCHAR(50) NOT NULL COMMENT '商品编号',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    price DECIMAL(10,2) NOT NULL COMMENT '单价',
    quantity INT NOT NULL DEFAULT 1 COMMENT '数量',
    amount DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id),
    INDEX idx_product_code (product_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

-- 插入测试数据
-- 分类数据
INSERT INTO category (name, code, description, sort_order) VALUES
('电子产品', 'ELECTRONICS', '各类电子产品', 1),
('服装服饰', 'CLOTHING', '服装鞋帽', 2),
('食品饮料', 'FOOD', '食品饮料', 3),
('家居用品', 'HOME', '家居生活用品', 4),
('图书文具', 'BOOKS', '图书文化用品', 5);

-- 产品数据
INSERT INTO product (product_code, name, category_id, price, cost, stock, unit, brand, model, specification, status) VALUES
('P001', 'iPhone 15 Pro', 1, 8999.00, 7000.00, 100, '台', 'Apple', 'A2848', '256GB 钛金属', 1),
('P002', 'MacBook Pro 14', 1, 12999.00, 10000.00, 50, '台', 'Apple', 'M3', '16+512GB', 1),
('P003', 'AirPods Pro 2', 1, 1899.00, 1200.00, 200, '个', 'Apple', 'USB-C', '主动降噪', 1),
('P004', '男士休闲衬衫', 2, 299.00, 150.00, 500, '件', '优衣库', 'XL', '蓝色 纯棉', 1),
('P005', '运动鞋', 2, 599.00, 300.00, 300, '双', 'Nike', 'AIR', '白色 42 码', 1),
('P006', '有机牛奶', 3, 68.00, 40.00, 1000, '箱', '蒙牛', '特仑苏', '250ml*12', 1),
('P007', '坚果礼盒', 3, 198.00, 120.00, 800, '盒', '三只松鼠', '豪华装', '1500g', 1),
('P008', '四件套', 4, 399.00, 200.00, 400, '套', '无印良品', '纯棉', '1.8m 床', 1),
('P009', '台灯', 4, 159.00, 80.00, 600, '个', '飞利浦', 'LED', '护眼调光', 1),
('P010', 'Java 编程思想', 5, 108.00, 60.00, 1000, '本', '机械工业出版社', '第 4 版', '精装', 1);

-- 订单数据
INSERT INTO orders (order_no, customer_name, customer_phone, total_amount, status, order_date) VALUES
('ORD20260310001', '张三', '13800138001', 10898.00, 3, '2026-03-10'),
('ORD20260311002', '李四', '13800138002', 6597.00, 3, '2026-03-11'),
('ORD20260312003', '王五', '13800138003', 2297.00, 2, '2026-03-12'),
('ORD20260313004', '赵六', '13800138004', 567.00, 1, '2026-03-13'),
('ORD20260314005', '孙七', '13800138005', 1899.00, 3, '2026-03-14'),
('ORD20260315006', '周八', '13800138006', 8999.00, 3, '2026-03-15'),
('ORD20260316007', '吴九', '13800138007', 366.00, 3, '2026-03-16');

-- 订单详情数据
INSERT INTO order_item (order_id, product_id, product_code, product_name, price, quantity, amount) VALUES
(1, 1, 'P001', 'iPhone 15 Pro', 8999.00, 1, 8999.00),
(1, 3, 'P003', 'AirPods Pro 2', 1899.00, 1, 1899.00),
(2, 2, 'P002', 'MacBook Pro 14', 12999.00, 1, 12999.00),
(2, 6, 'P006', '有机牛奶', 68.00, 10, 680.00),
(3, 4, 'P004', '男士休闲衬衫', 299.00, 2, 598.00),
(3, 5, 'P005', '运动鞋', 599.00, 1, 599.00),
(3, 9, 'P009', '台灯', 159.00, 1, 159.00),
(3, 10, 'P010', 'Java 编程思想', 108.00, 1, 108.00),
(4, 6, 'P006', '有机牛奶', 68.00, 5, 340.00),
(4, 7, 'P007', '坚果礼盒', 198.00, 1, 198.00),
(5, 3, 'P003', 'AirPods Pro 2', 1899.00, 1, 1899.00),
(6, 1, 'P001', 'iPhone 15 Pro', 8999.00, 1, 8999.00),
(7, 6, 'P006', '有机牛奶', 68.00, 3, 204.00),
(7, 7, 'P007', '坚果礼盒', 198.00, 1, 198.00);
