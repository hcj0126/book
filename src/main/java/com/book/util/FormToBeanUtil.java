package com.book.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * FormToBeanUtil
 *  前端表单数据封装到对象
 * @author hcj
 * @date 2023-08-08
 */
public class FormToBeanUtil {

    public static <T> T formToBean(Map<String, String[]> map,Class<T> clazz){
        // 创建对象
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.populate(t,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
