/*
 * 文件名：GsonUtil.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： GsonUtil.java
 * 修改人：zxiaofan
 * 修改时间：2016年11月6日
 * 修改内容：新增
 */
package guava.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author zxiaofan
 */
public class GsonUtil {
    /**
     * GSON.
     */
    private static final Gson GSON;

    private static String format_Default = "yyyy-MM-dd HH:mm:ss";

    public static String format_y_M_d = "yyyy-MM-dd";

    public static String format_y_M_d_Slash = "yyyy/MM/dd HH:mm:ss";

    /**
     * 构造函数.
     * 
     */
    private GsonUtil() {
        throw new RuntimeException("工具类不允许实例化!");
    }

    static {
        GSON = new GsonBuilder().setDateFormat(format_Default).create(); // 避免本地环境不同导致模式字符串不同引发Date错乱
    }

    /**
     * 获取GSON.
     * 
     * @return GSON
     */
    public static Gson getGson() {
        return GSON;
    }

    /**
     * 获取自定义日期格式的GSON.
     * 
     * @return GSON
     */
    public static Gson getGson(String format) {
        return new GsonBuilder().setDateFormat(format).create();
    }

    /**
     * 处理泛型对象.
     * 
     * @param json
     *            json
     * @param raw
     *            原类型
     * @param actual
     *            真实类型
     * @param <T>
     *            t
     * @return 对象
     */
    public static <T> T fromJson(String json, Class raw, Class actual) {
        return GSON.fromJson(json, new SpecialParameterizedType(raw, actual)); // new TypeToken<List<Menu>>(){}.getType()
    }

    /**
     * toJson,避免String再次被转义.
     * 
     * @param obj
     *            obj
     * @return String
     */
    public static String toJson(Object obj) {
        if (obj instanceof String) {
            return obj.toString();
        } else {
            return GSON.toJson(obj);
        }
    }

}

@SuppressWarnings("rawtypes")
class SpecialParameterizedType implements ParameterizedType {

    private Class clz;

    private Class actualType;

    /**
     * 构造函数.
     * 
     * @param clz
     *            clz
     * @param actualType
     *            s
     */
    public SpecialParameterizedType(Class clz, Class actualType) {
        super();
        if (null == clz || null == actualType) {
            throw new RuntimeException("cla or actualType can not null");
        }
        this.clz = clz;
        this.actualType = actualType;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{actualType};
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Type getRawType() {
        return clz;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Type getOwnerType() {
        return null;
    }

}
