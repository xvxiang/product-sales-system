package com.xvxiang.productsalessystem.mapper;

import com.xvxiang.productsalessystem.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类 Mapper
 */
@Mapper
public interface CategoryMapper {

    /**
     * 查询所有分类
     */
    List<Category> selectAll();

    /**
     * 根据 ID 查询分类
     */
    Category selectById(@Param("id") Long id);

    /**
     * 根据 ID 集合查询分类
     */
    List<Category> selectByIds(@Param("ids") List<Long> ids);
}
