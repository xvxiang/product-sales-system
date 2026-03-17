package com.xvxiang.productsalessystem.controller;

import com.xvxiang.productsalessystem.entity.Product;
import com.xvxiang.productsalessystem.service.ProductService;
import com.xvxiang.productsalessystem.common.Result;
import com.xvxiang.productsalessystem.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 分页查询产品列表
     */
    @GetMapping("/page")
    public Result<PageResult<Product>> getPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status) {
        
        log.info("查询产品列表：pageNum={}, pageSize={}, productCode={}, name={}, categoryId={}, status={}", 
                pageNum, pageSize, productCode, name, categoryId, status);
        
        PageResult<Product> pageResult = productService.getPage(pageNum, pageSize, productCode, name, categoryId, status);
        return Result.success(pageResult);
    }

    /**
     * 根据 ID 查询产品
     */
    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }

    /**
     * 添加产品
     */
    @PostMapping
    public Result<String> add(@RequestBody Product product) {
        try {
            productService.add(product);
            return Result.success("添加成功");
        } catch (Exception e) {
            log.error("添加产品失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新产品
     */
    @PutMapping
    public Result<String> update(@RequestBody Product product) {
        try {
            productService.update(product);
            return Result.success("更新成功");
        } catch (Exception e) {
            log.error("更新产品失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除产品
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("删除产品失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 批量删除产品
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        try {
            productService.batchDelete(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            log.error("批量删除失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询商品销售趋势（近期一周）
     */
    @GetMapping("/sales-trend/{productId}")
    public Result<Map<String, Object>> getSalesTrend(@PathVariable Long productId) {
        try {
            List<Product> trendList = productService.getSalesTrend(productId);
            
            // 将数据转换为前端需要的格式
            Map<String, Object> result = new HashMap<>();
            List<String> dates = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            
            for (Product product : trendList) {
                dates.add(product.getName()); // name 字段存储日期
                values.add(product.getStock()); // stock 字段存储销量
            }
            
            result.put("dates", dates);
            result.put("values", values);
            
            return Result.success(result);
        } catch (Exception e) {
            log.error("查询销售趋势失败", e);
            return Result.error(e.getMessage());
        }
    }
}
