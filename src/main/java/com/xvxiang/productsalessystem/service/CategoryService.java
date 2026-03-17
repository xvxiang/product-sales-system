package com.xvxiang.productsalessystem.service;

import com.xvxiang.productsalessystem.entity.Category;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {

    /**
     * 查询所有分类
     */
    List<Category> getAll();

    /**
     * 根据 ID 查询分类
     */
    Category getById(Long id);
}
