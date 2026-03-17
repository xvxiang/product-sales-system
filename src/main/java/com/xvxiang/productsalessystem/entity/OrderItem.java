package com.xvxiang.productsalessystem.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单详情实体
 */
@Data
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单详情 ID
     */
    private Long id;

    /**
     * 订单 ID
     */
    private Long orderId;

    /**
     * 商品 ID
     */
    private Long productId;

    /**
     * 商品编号
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 小计金额
     */
    private BigDecimal amount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
