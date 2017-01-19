/*
 * 文件名：BeanUtilsExtends.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： BeanUtilsExtends.java
 * 修改人：zxiaofan
 * 修改时间：2017年1月17日
 * 修改内容：新增
 */
package commons.beanutils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

/**
 * BeanUtils扩展工具类，自定义转换器
 * 
 * @author zxiaofan
 */
public class BeanUtilsExtends extends BeanUtils {
    static {
        // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        // ConvertUtils.register(new SqlDateConverter(), java.sql.Date.class);
        // ConvertUtils.register(new SqlTimestampConverter(), java.sql.Timestamp.class);
        // 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空（1.9版本原生已支持）
        // ConvertUtils.register(new DateConverter(), java.util.Date.class);
        //
        // 自定义String转换器
        ConvertUtils.register(new Converter_String(), java.lang.Integer.class); // 【register不支持Object或Number这种大类型】
    }

    public static void copyProperties(Object target, Object source) throws IllegalAccessException, InvocationTargetException {
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    }
}

/**
 * 自定义转换.
 * 
 * 每个转换器只支持一种类型，so instanceof 派不上用场
 *
 */
class Converter_String implements Converter {

    public Object convert(Class arg0, Object arg1) {
        if (null == arg1) {
            return null;
        }
        // if (arg1 instanceof Integer) { // Integer
        System.out.println(arg1 + "_Integer");
        // } else if (arg1.getClass().getTypeName().equals("java.lang.Integer")) { // int
        // System.out.println(arg1 + "_int");
        // }
        return arg1;
    }
}