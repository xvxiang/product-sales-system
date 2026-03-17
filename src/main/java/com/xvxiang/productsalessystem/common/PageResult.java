package com.xvxiang.productsalessystem.common;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果类
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页数据列表
     */
    private List<T> records;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    public PageResult() {
    }

    public PageResult(Long total, List<T> records, Integer pageNum, Integer pageSize) {
        this.total = total;
        this.records = records;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }
}
