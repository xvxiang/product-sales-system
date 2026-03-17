package com.xvxiang.productsalessystem.controller;

import com.alibaba.excel.EasyExcel;
import com.xvxiang.productsalessystem.entity.Product;
import com.xvxiang.productsalessystem.service.ProductService;
import com.xvxiang.productsalessystem.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 导入导出控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/product")
public class ImportExportController {

    @Autowired
    private ProductService productService;

    /**
     * 导入产品数据
     */
    @PostMapping("/import")
    public Result<String> importProducts(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return Result.error("请选择文件");
            }

            // 使用 EasyExcel 读取 Excel 数据
            List<Product> products = EasyExcel.read(file.getInputStream())
                    .head(Product.class)
                    .sheet()
                    .doReadSync();

            if (products == null || products.isEmpty()) {
                return Result.error("Excel 文件中没有数据");
            }

            // 调用服务批量导入
            productService.batchImport(products);
            
            return Result.success("导入成功，共导入 " + products.size() + " 条数据");
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 导出产品数据
     */
    @GetMapping("/export")
    public void exportProducts(
            @RequestParam(defaultValue = "all") String type,
            @RequestParam(required = false) String ids,
            HttpServletResponse response) throws IOException {
        
        try {
            List<Product> dataList;

            if ("all".equals(type)) {
                // 导出全部 - 这里简化处理，实际应该查询所有数据
                dataList = productService.getPage(1, 10000, null, null, null, null).getRecords();
            } else if ("selected".equals(type) && ids != null && !ids.isEmpty()) {
                // 导出勾选的
                List<Long> idList = java.util.Arrays.stream(ids.split(","))
                        .map(Long::parseLong)
                        .collect(java.util.stream.Collectors.toList());
                dataList = new java.util.ArrayList<>();
                for (Long id : idList) {
                    Product product = productService.getById(id);
                    if (product != null) {
                        dataList.add(product);
                    }
                }
            } else {
                // 导出当前页 - 简化处理，导出第一页
                dataList = productService.getPage(1, 10, null, null, null, null).getRecords();
            }

            if (dataList == null || dataList.isEmpty()) {
                throw new RuntimeException("没有可导出的数据");
            }

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("商品数据_" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            // 使用 EasyExcel 写入 Excel
            EasyExcel.write(response.getOutputStream(), Product.class)
                    .sheet("商品列表")
                    .doWrite(dataList);
            
            // 刷新输出流
            response.flushBuffer();

        } catch (Exception e) {
            log.error("导出失败", e);
            throw new IOException("导出失败：" + e.getMessage());
        }
    }
}
