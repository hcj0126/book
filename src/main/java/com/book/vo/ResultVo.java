package com.book.vo;

import lombok.Data;

/**
 * ResultVo
 *   后台服务器给前端页面返回的结果集对象
 * @author hcj
 * @date 2023-08-08
 */
@Data
public class ResultVo<T> {
    // true表示成功  false表示失败
    private boolean ok;
    // 提示信息
    private String msg;
    private T t;
}
