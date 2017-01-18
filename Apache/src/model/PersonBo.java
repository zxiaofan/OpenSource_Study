/*
 * 文件名：PersonBo.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： PersonBo.java
 * 修改人：yunhai
 * 修改时间：2015年12月3日
 * 修改内容：新增
 */
package model;

import java.math.BigDecimal;

/**
 * @author yunhai
 */
public class PersonBo {
    private String name;

    private int age;

    private BigDecimal money;

    public PersonBo(String name, int age, BigDecimal money) {
        super();
        this.name = name;
        this.age = age;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

}
