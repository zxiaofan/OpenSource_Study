/*
 * 文件名：PropertyUtils_Study.java
 * 版权：Copyright 2007-2015 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： PropertyUtils_Study.java
 * 修改人：yunhai
 * 修改时间：2015年12月3日
 * 修改内容：新增
 */
package commons.beanutils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import model.BigDestBo;
import model.BigSrcBo;
import model.PersonBo;
import model.Student;

/**
 * PropertyUtils_Study.
 * 
 * PropertyUtils need jar:commons-beanutils、commons-logging.
 * 
 * @author yunhai
 */
public class PropertyUtils_Study {

    @Test
    public void testList() {
        List<Object> list = new ArrayList<Object>();
        list.add(new PersonBo("naPer", 2, new BigDecimal(22)));
        list.add(new Student("naStu", 3));
        // List<Object>中参数类型不统一，则Lambda无法获取属性值(无法consumer.getName())
        list.forEach(consumer -> System.out.println(consumer.toString()));
        List<PersonBo> listPer = new ArrayList<PersonBo>();
        listPer.add(new PersonBo("name", 2, new BigDecimal(22)));
        listPer.forEach(consumer -> System.out.println(consumer.getName()));
    }

    @Test
    public void testCopy() {
        testBasic();
        copyWhenDiffer(); // architectural difference
        EfficiencyCompare(5000); // 5000,Consume time: Direct(3ms) <commom(356ms) <PropertuUtils(3035ms)
    }

    private void testBasic() {
        Student stuSrc = new Student("A", 10);
        Student stuDest = new Student();
        try {
            PropertyUtils.copyProperties(stuDest, stuSrc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(stuDest.getName());
    }

    /**
     * copy when architectural difference.
     * 
     */
    private void copyWhenDiffer() {
        PersonBo personBo = new PersonBo("B", 12, new BigDecimal(30));
        Student student = new Student();
        try {
            PropertyUtils.copyProperties(student, personBo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(student.getName() + "-" + student.getAge() + "-" + student.getGrade());
    }

    private void EfficiencyCompare(int n) {
        List<BigSrcBo> listSrcBos = CreatListSrcBos(n);
        List<BigDestBo> listDestBos = new ArrayList<BigDestBo>();
        copyDifferBigUtils(listSrcBos, n); // 5000->TimePropertyUtils=3108 ms
        Long timeStart = System.currentTimeMillis();
        for (BigSrcBo bigSrcBo : listSrcBos) {
            try {
                BigDestBo destBo = new BigDestBo();
                copyDifferBigCommon(destBo, bigSrcBo); // 5000->TimecopyDifferBigCommon=370ms
                listDestBos.add(destBo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(listDestBos.get(100).getPassenger_name());
        System.out.println("time-Common:" + (System.currentTimeMillis() - timeStart) + "ms");
        copyDifferBigCommonDirect(listSrcBos, n); // 5000->TimecopyDifferBigCommonbug=3 ms
    }

    /**
     * PropertyUtils efficiency test.
     * 
     */
    private void copyDifferBigUtils(List<BigSrcBo> listSrcBos, int n) {
        List<BigDestBo> listDestBos = new ArrayList<BigDestBo>();
        Long timeStart = System.currentTimeMillis();
        for (BigSrcBo bigSrcBo : listSrcBos) {
            BigDestBo destBo = new BigDestBo();
            try {
                PropertyUtils.copyProperties(destBo, bigSrcBo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listDestBos.add(destBo);
        }
        System.out.println(listDestBos.get(100).getPassenger_name());
        System.out.println("time-PropertyUtils:" + (System.currentTimeMillis() - timeStart) + "ms");
    }

    /**
     * Reflect.
     * 
     * list中字段类型可能不同，为保证通用性，暂未将公共字段提出，为优化效率，可针对相同参数的list重构代码。
     * 
     * 针对实际开发，可封装代码使其支持list嵌套 .
     * 
     * http://www.cnblogs.com/137913828S2/archive/2012/07/10/2584774.html.
     * 
     * http://blog.csdn.net/z69183787/article/details/16967821.
     * 
     * @param listSrcBos
     * @param n
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void copyDifferBigCommon(Object objectDest, Object objectSrc) throws Exception {
        final Field[] fieldSrc = objectSrc.getClass().getDeclaredFields();
        final Field[] fieldDest = objectDest.getClass().getDeclaredFields();// 获取实体类的所有属性，返回Field数组
        Map<String, Object> mapSrc = new HashMap<String, Object>();
        for (Field field : fieldSrc) {
            mapSrc.put(field.getName(), field.getType().toString());
        }
        String name = "";
        String type = "";
        for (Field field : fieldDest) {
            name = field.getName(); // 获取属性的名字
            type = field.getGenericType().toString(); // 获取属性的类型
            if (mapSrc.containsKey(name) && mapSrc.get(name).equals(type)) {
                field.setAccessible(true);
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), objectDest.getClass()); // get属性描述器
                String fieldName = field.getName();
                fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method mGet = objectSrc.getClass().getMethod("get" + fieldName);
                Object value = mGet.invoke(objectSrc); // 调用getter方法获取属性值
                Method wM = pd.getWriteMethod();// 获得写方法
                if (type.equals("int") || type.equals("class java.lang.Integer")) {
                    wM.invoke(objectDest, (Integer) value);
                } else if (type.equals("short")) {
                    wM.invoke(objectDest, (Short) value);
                } else if (type.equals("double")) {
                    wM.invoke(objectDest, (Double) value);
                } else if (type.equals("boolean")) {
                    wM.invoke(objectDest, (Boolean) value);
                } else if (type.equals("class java.util.Date")) {
                    wM.invoke(objectDest, (Date) value);
                } else { // String
                    wM.invoke(objectDest, (String) value);
                }
            }
        }
    }

    /**
     * copy when know the field to be copied,the efficiency of direct-copy is great.
     * 
     * @param listSrcBos
     * @param n
     */
    private void copyDifferBigCommonDirect(List<BigSrcBo> listSrcBos, int n) {
        List<BigDestBo> listDestBos = new ArrayList<BigDestBo>();
        Long timeStart = System.currentTimeMillis();
        for (BigSrcBo srcBo : listSrcBos) {
            BigDestBo destBo = new BigDestBo();
            destBo.setBirth_date(srcBo.getBirth_date());
            destBo.setDoc_id(srcBo.getDoc_id());
            destBo.setDoc_type(srcBo.getDoc_type());
            destBo.setPassenger_name(srcBo.getPassenger_name());
            destBo.setPassenger_type(srcBo.getPassenger_type());
            destBo.setTicket_no(srcBo.getTicket_no());
            listDestBos.add(destBo);
        }
        System.out.println(listDestBos.get(100).getPassenger_name());
        System.out.println("time-Commonbug:" + (System.currentTimeMillis() - timeStart) + "ms");
    }

    /**
     * 构造数据.
     * 
     * @param n
     *            数量
     * @return List<BigSrcBo>
     */
    public List<BigSrcBo> CreatListSrcBos(int n) {
        List<BigSrcBo> lSrcBos = new ArrayList<BigSrcBo>(100);
        for (int i = 0; i < n; i++) {
            BigSrcBo bo = new BigSrcBo();
            bo.setActual_pay(1);
            bo.setAirline_code("a");
            bo.setAlipay_trade_no("b");
            bo.setArr_airport("s");
            bo.setArr_time("s");
            bo.setBirth_date(new Date());
            bo.setBuild_price(3);
            bo.setCabin("c");
            bo.setDep_airport("d");
            bo.setDep_date("d");
            bo.setDep_time("d");
            bo.setDoc_id("d");
            bo.setDoc_type(2);
            bo.setFlight_no("s");
            bo.setGmt_create("w");
            bo.setIs_quality_order(true);
            bo.setIs_voucher_order(false);
            bo.setOil_price(3);
            bo.setOrder_id(123L);
            bo.setPassenger_name("name0");
            bo.setPassenger_type(123);
            bo.setPay_time(new Date());
            bo.setPurchaser("pur");
            bo.setReservationcode("21");
            bo.setSale_price(4);
            bo.setSegment_type("s");
            bo.setSeller_taobao_nick("se");
            bo.setSession_key("se");
            bo.setSpeedy_order(false);
            bo.setStatus("sta");
            bo.setTicket_no("no");
            bo.setTotal_price(5);
            lSrcBos.add(bo);
        }
        lSrcBos.get(100).setPassenger_name("name100");
        return lSrcBos;
    }
}
