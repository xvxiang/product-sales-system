package com.xvxiang.productsalessystem.service.impl;

import com.xvxiang.productsalessystem.dto.SalesTrendDTO;
import com.xvxiang.productsalessystem.entity.OrderItem;
import com.xvxiang.productsalessystem.entity.Product;
import com.xvxiang.productsalessystem.mapper.OrderItemMapper;
import com.xvxiang.productsalessystem.mapper.ProductMapper;
import com.xvxiang.productsalessystem.service.ProductService;
import com.xvxiang.productsalessystem.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品服务实现类
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public PageResult<Product> getPage(Integer pageNum, Integer pageSize, String productCode, String name, Long categoryId, Integer status) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        int offset = (pageNum - 1) * pageSize;
        List<Product> products = productMapper.selectPage(offset, pageSize, productCode, name, categoryId, status);
        Long total = productMapper.selectCount(productCode, name, categoryId, status);

        return new PageResult<>(total, products, pageNum, pageSize);
    }

    @Override
    public Product getById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(Product product) {
        // 检查商品编号是否已存在
        Product existProduct = productMapper.selectByProductCode(product.getProductCode());
        if (existProduct != null) {
            throw new RuntimeException("商品编号已存在");
        }
        return productMapper.insert(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Product product) {
        return productMapper.update(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return productMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new RuntimeException("请选择要删除的数据");
        }
        return productMapper.deleteByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchImport(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new RuntimeException("导入数据不能为空");
        }

        // 检查商品编号是否重复
        for (Product product : products) {
            Product existProduct = productMapper.selectByProductCode(product.getProductCode());
            if (existProduct != null) {
                throw new RuntimeException("商品编号 " + product.getProductCode() + " 已存在");
            }
        }

        return productMapper.batchInsert(products);
    }

    @Override
    public List<Product> getSalesTrend(Long productId) {
        // 查询近期一周的销售数据
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        List<SalesTrendDTO> salesData = orderItemMapper.selectSalesTrend(productId, startDate, endDate);

        // 将销售数据转换为 Product 对象（利用 name 字段存储日期，stock 字段存储销量）
        List<Product> trendList = new ArrayList<>();
        for (SalesTrendDTO dto : salesData) {
            Product product = new Product();
            product.setId(dto.getProductId());
            product.setName(dto.getOrderDate().toString()); // 用 name 存储日期
            product.setStock(dto.getQuantity()); // 用 stock 存储销量
            trendList.add(product);
        }

        return trendList;
    }
}
