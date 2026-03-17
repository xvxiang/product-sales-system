package com.xvxiang.productsalessystem.service.impl;

import com.xvxiang.productsalessystem.entity.Category;
import com.xvxiang.productsalessystem.mapper.CategoryMapper;
import com.xvxiang.productsalessystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }
}
