package com.xvxiang.productsalessystem.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品分类实体
 */
@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类 ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类编码
     */
    private String code;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
