package com.book.bean;

import lombok.Data;

import java.util.List;

/**
 * PageBean
 *   封装分页相关属性
 * @author hcj
 * @date 2023-08-09
 */
@Data
public class PageBean<E> {
    /**
     * 总记录数
    */
    private Integer total;
    /**
     * 每页记录数
     */
    private List<E> data;
}
