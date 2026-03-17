package com.xvxiang.productsalessystem.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售趋势数据传输对象
 */
@Data
public class SalesTrendDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 销售数量
     */
    private Integer quantity;

    /**
     * 订单日期
     */
    private LocalDate orderDate;
}
