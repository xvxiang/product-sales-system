package com.xvxiang.productsalessystem.mapper;

import com.xvxiang.productsalessystem.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品 Mapper
 */
@Mapper
public interface ProductMapper {

    /**
     * 分页查询产品列表
     */
    List<Product> selectPage(@Param("offset") Integer offset, 
                             @Param("limit") Integer limit,
                             @Param("productCode") String productCode,
                             @Param("name") String name,
                             @Param("categoryId") Long categoryId,
                             @Param("status") Integer status);

    /**
     * 查询总数
     */
    Long selectCount(@Param("productCode") String productCode,
                     @Param("name") String name,
                     @Param("categoryId") Long categoryId,
                     @Param("status") Integer status);

    /**
     * 根据 ID 查询产品
     */
    Product selectById(@Param("id") Long id);

    /**
     * 根据编号查询产品
     */
    Product selectByProductCode(@Param("productCode") String productCode);

    /**
     * 插入产品
     */
    int insert(Product product);

    /**
     * 更新产品
     */
    int update(Product product);

    /**
     * 批量插入产品
     */
    int batchInsert(@Param("list") List<Product> list);

    /**
     * 删除产品
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除产品
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
