package com.xvxiang.productsalessystem.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 产品实体
 */
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品 ID
     */
    @ExcelProperty("商品 ID")
    private Long id;

    /**
     * 商品编号
     */
    @ExcelProperty("商品编号")
    private String productCode;

    /**
     * 商品名称
     */
    @ExcelProperty("商品名称")
    private String name;

    /**
     * 分类 ID
     */
    @ExcelProperty("分类 ID")
    private Long categoryId;

    /**
     * 分类名称（关联查询）
     */
    @ExcelProperty("商品分类")
    private String categoryName;

    /**
     * 价格
     */
    @ExcelProperty("价格")
    private BigDecimal price;

    /**
     * 成本
     */
    @ExcelProperty("成本")
    private BigDecimal cost;

    /**
     * 库存
     */
    @ExcelProperty("库存")
    private Integer stock;

    /**
     * 单位
     */
    @ExcelProperty("单位")
    private String unit;

    /**
     * 品牌
     */
    @ExcelProperty("品牌")
    private String brand;

    /**
     * 型号
     */
    @ExcelProperty("型号")
    private String model;

    /**
     * 规格
     */
    @ExcelProperty("规格")
    private String specification;

    /**
     * 商品描述
     */
    @ExcelProperty("商品描述")
    private String description;

    /**
     * 状态：0-下架，1-上架
     */
    @ExcelProperty("状态")
    private Integer status;

    /**
     * 图片 URL
     */
    @ExcelProperty("图片 URL")
    private String imageUrl;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
