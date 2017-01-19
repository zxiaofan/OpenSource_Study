/*
 * 文件名：BeanUtils.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： BeanUtils.java
 * 修改人：zxiaofan
 * 修改时间：2017年1月17日
 * 修改内容：新增
 */
package commons.beanutils;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import model.BigDestBo;

/**
 * 需引入commons-logging包
 * 
 * @author zxiaofan
 */
public class BeanUtils_Study {
    @Test
    public void basicTest() {
        BigDestBo destBo1 = new BigDestBo();
        destBo1.setDoc_id("idsrc");
        destBo1.setDoc_type(1);
        BigDestBo destBo2 = new BigDestBo();
        try {
            BeanUtils.copyProperties(destBo2, destBo1);
        } catch (Exception e) { // BeanUtils【1.9版本前】不允许Date值为null,// No value specified for 'Date'
            e.printStackTrace();
        }
        print(destBo2);
    }

    /**
     * ConvertUtils.register注册转换器.
     * 
     */
    @Test
    public void converterTest() {
        BigDestBo destBo1 = new BigDestBo();
        destBo1.setDoc_id("idsrc");
        destBo1.setDoc_type(1);
        destBo1.setPassenger_type(2);
        BigDestBo destBo2 = new BigDestBo();
        try {
            BeanUtilsExtends.copyProperties(destBo2, destBo1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        print(destBo2);
    }

    /**
     * 对指定字段赋指定值.
     * 
     */
    @Test
    public void otherTest() {
        BigDestBo destBo2 = new BigDestBo();
        try {
            BeanUtils.copyProperty(destBo2, "doc_id", "id_new"); // 目标bean，目标字段，目标value
        } catch (Exception e) {
            e.printStackTrace();
        }
        print(destBo2); // &doc_id=id_new&doc_type=0
    }

    /**
     * 打印非空属性.
     * 
     * @param param
     */
    private void print(Object param) {
        try {
            System.out.println(util.BeanUtils.joinProperty(param));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
