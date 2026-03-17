package com.xvxiang.productsalessystem.service;

import com.xvxiang.productsalessystem.entity.Product;
import com.xvxiang.productsalessystem.common.PageResult;

import java.util.List;

/**
 * 产品服务接口
 */
public interface ProductService {

    /**
     * 分页查询产品列表
     */
    PageResult<Product> getPage(Integer pageNum, Integer pageSize, String productCode, String name, Long categoryId, Integer status);

    /**
     * 根据 ID 查询产品
     */
    Product getById(Long id);

    /**
     * 添加产品
     */
    int add(Product product);

    /**
     * 更新产品
     */
    int update(Product product);

    /**
     * 删除产品
     */
    int delete(Long id);

    /**
     * 批量删除产品
     */
    int batchDelete(List<Long> ids);

    /**
     * 批量导入产品
     */
    int batchImport(List<Product> products);

    /**
     * 查询商品销售趋势（近期一周）
     */
    List<Product> getSalesTrend(Long productId);
}
