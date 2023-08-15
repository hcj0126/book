package com.book.bean;

import lombok.Data;

import java.util.List;

/**
 * TreeMenu
 *
 * @author hcj
 * @date 2023-08-11
 */
@Data
public class TreeMenu {
    /**
     * 节点编号
    */
    private String id;
    /**
     * 节点文本
     */
    private String text;
    /**
     * 是否折叠
     */
    private boolean expanded;
    /**
     * 当前节点的子节点
     */
    private List<TreeMenu> children;
}
