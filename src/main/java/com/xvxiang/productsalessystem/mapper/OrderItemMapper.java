package com.xvxiang.productsalessystem.mapper;

import com.xvxiang.productsalessystem.dto.SalesTrendDTO;
import com.xvxiang.productsalessystem.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 订单详情 Mapper
 */
@Mapper
public interface OrderItemMapper {

    /**
     * 查询商品近期销售数据（用于折线图）
     */
    List<SalesTrendDTO> selectSalesTrend(@Param("productId") Long productId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    /**
     * 根据商品 ID 查询订单详情
     */
    List<OrderItem> selectByProductId(@Param("productId") Long productId);
}
