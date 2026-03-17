package com.xvxiang.productsalessystem.controller;

import com.xvxiang.productsalessystem.entity.Category;
import com.xvxiang.productsalessystem.service.CategoryService;
import com.xvxiang.productsalessystem.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类
     */
    @GetMapping("/list")
    public Result<List<Category>> getAll() {
        List<Category> categories = categoryService.getAll();
        return Result.success(categories);
    }

    /**
     * 根据 ID 查询分类
     */
    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }
}
