package poi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonVo {
    private String name1;

    private String name2;

    private String name3;

    private String name4;

    private String name5;

    private String name6;

    private String name7;

    private String name8;

    private String name9;

    private String name10;

    private String name11;

    private String name12;

    private String name13;

    private String name14;

    private int age1;

    private int age2;

    private int age3;

    private int age4;

    private int age5;

    private int age6;

    private int age7;

    private int age8;

    private int age9;

    private Date date1;

    private Date date2;

    private Date date3;

    private Date date4;

    private Date date5;

    private Date date6;

    private Date date7;

    private Date date8;

    private Date date9;

    public PersonVo() {

    }

    public PersonVo(int i) throws ParseException {
        this.setName1("序号：" + i);
        this.setName2("1");
        this.setName3("1");
        this.setName4("1");
        this.setName5("1");
        this.setName6("1");
        this.setName7("1");
        this.setName8("1");
        this.setName9("1");
        this.setName10("1");
        this.setName11("1");
        this.setName12("1");
        this.setName13("1");
        this.setName14("1");
        this.setAge1(12);
        this.setAge2(12);
        this.setAge3(12);
        this.setAge4(12);
        this.setAge5(12);
        this.setAge6(12);
        this.setAge7(12);
        this.setAge8(12);
        this.setAge9(12);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2015-01-01 11:11:11");
        this.setDate1(date);
        this.setDate2(date);
        this.setDate3(date);
        this.setDate4(date);
        this.setDate5(date);
        this.setDate6(date);
        this.setDate7(date);
        this.setDate8(date);
        this.setDate9(date);
    }

    /**
     * 设置name1.
     * 
     * @return 返回name1
     */
    public String getName1() {
        return name1;
    }

    /**
     * 获取name1.
     * 
     * @param name1
     *            要设置的name1
     */
    public void setName1(String name1) {
        this.name1 = name1;
    }

    /**
     * 设置name2.
     * 
     * @return 返回name2
     */
    public String getName2() {
        return name2;
    }

    /**
     * 获取name2.
     * 
     * @param name2
     *            要设置的name2
     */
    public void setName2(String name2) {
        this.name2 = name2;
    }

    /**
     * 设置name3.
     * 
     * @return 返回name3
     */
    public String getName3() {
        return name3;
    }

    /**
     * 获取name3.
     * 
     * @param name3
     *            要设置的name3
     */
    public void setName3(String name3) {
        this.name3 = name3;
    }

    /**
     * 设置name4.
     * 
     * @return 返回name4
     */
    public String getName4() {
        return name4;
    }

    /**
     * 获取name4.
     * 
     * @param name4
     *            要设置的name4
     */
    public void setName4(String name4) {
        this.name4 = name4;
    }

    /**
     * 设置name5.
     * 
     * @return 返回name5
     */
    public String getName5() {
        return name5;
    }

    /**
     * 获取name5.
     * 
     * @param name5
     *            要设置的name5
     */
    public void setName5(String name5) {
        this.name5 = name5;
    }

    /**
     * 设置name6.
     * 
     * @return 返回name6
     */
    public String getName6() {
        return name6;
    }

    /**
     * 获取name6.
     * 
     * @param name6
     *            要设置的name6
     */
    public void setName6(String name6) {
        this.name6 = name6;
    }

    /**
     * 设置name7.
     * 
     * @return 返回name7
     */
    public String getName7() {
        return name7;
    }

    /**
     * 获取name7.
     * 
     * @param name7
     *            要设置的name7
     */
    public void setName7(String name7) {
        this.name7 = name7;
    }

    /**
     * 设置name8.
     * 
     * @return 返回name8
     */
    public String getName8() {
        return name8;
    }

    /**
     * 获取name8.
     * 
     * @param name8
     *            要设置的name8
     */
    public void setName8(String name8) {
        this.name8 = name8;
    }

    /**
     * 设置name9.
     * 
     * @return 返回name9
     */
    public String getName9() {
        return name9;
    }

    /**
     * 获取name9.
     * 
     * @param name9
     *            要设置的name9
     */
    public void setName9(String name9) {
        this.name9 = name9;
    }

    public String getName10() {
        return name10;
    }

    public void setName10(String name10) {
        this.name10 = name10;
    }

    public String getName11() {
        return name11;
    }

    public void setName11(String name11) {
        this.name11 = name11;
    }

    public String getName12() {
        return name12;
    }

    public void setName12(String name12) {
        this.name12 = name12;
    }

    public String getName13() {
        return name13;
    }

    public void setName13(String name13) {
        this.name13 = name13;
    }

    public String getName14() {
        return name14;
    }

    public void setName14(String name14) {
        this.name14 = name14;
    }

    /**
     * 设置age1.
     * 
     * @return 返回age1
     */
    public int getAge1() {
        return age1;
    }

    /**
     * 获取age1.
     * 
     * @param age1
     *            要设置的age1
     */
    public void setAge1(int age1) {
        this.age1 = age1;
    }

    /**
     * 设置age2.
     * 
     * @return 返回age2
     */
    public int getAge2() {
        return age2;
    }

    /**
     * 获取age2.
     * 
     * @param age2
     *            要设置的age2
     */
    public void setAge2(int age2) {
        this.age2 = age2;
    }

    /**
     * 设置age3.
     * 
     * @return 返回age3
     */
    public int getAge3() {
        return age3;
    }

    /**
     * 获取age3.
     * 
     * @param age3
     *            要设置的age3
     */
    public void setAge3(int age3) {
        this.age3 = age3;
    }

    /**
     * 设置age4.
     * 
     * @return 返回age4
     */
    public int getAge4() {
        return age4;
    }

    /**
     * 获取age4.
     * 
     * @param age4
     *            要设置的age4
     */
    public void setAge4(int age4) {
        this.age4 = age4;
    }

    /**
     * 设置age5.
     * 
     * @return 返回age5
     */
    public int getAge5() {
        return age5;
    }

    /**
     * 获取age5.
     * 
     * @param age5
     *            要设置的age5
     */
    public void setAge5(int age5) {
        this.age5 = age5;
    }

    /**
     * 设置age6.
     * 
     * @return 返回age6
     */
    public int getAge6() {
        return age6;
    }

    /**
     * 获取age6.
     * 
     * @param age6
     *            要设置的age6
     */
    public void setAge6(int age6) {
        this.age6 = age6;
    }

    /**
     * 设置age7.
     * 
     * @return 返回age7
     */
    public int getAge7() {
        return age7;
    }

    /**
     * 获取age7.
     * 
     * @param age7
     *            要设置的age7
     */
    public void setAge7(int age7) {
        this.age7 = age7;
    }

    /**
     * 设置age8.
     * 
     * @return 返回age8
     */
    public int getAge8() {
        return age8;
    }

    /**
     * 获取age8.
     * 
     * @param age8
     *            要设置的age8
     */
    public void setAge8(int age8) {
        this.age8 = age8;
    }

    /**
     * 设置age9.
     * 
     * @return 返回age9
     */
    public int getAge9() {
        return age9;
    }

    /**
     * 获取age9.
     * 
     * @param age9
     *            要设置的age9
     */
    public void setAge9(int age9) {
        this.age9 = age9;
    }

    /**
     * 设置date1.
     * 
     * @return 返回date1
     */
    public Date getDate1() {
        return date1;
    }

    /**
     * 获取date1.
     * 
     * @param date1
     *            要设置的date1
     */
    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    /**
     * 设置date2.
     * 
     * @return 返回date2
     */
    public Date getDate2() {
        return date2;
    }

    /**
     * 获取date2.
     * 
     * @param date2
     *            要设置的date2
     */
    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    /**
     * 设置date3.
     * 
     * @return 返回date3
     */
    public Date getDate3() {
        return date3;
    }

    /**
     * 获取date3.
     * 
     * @param date3
     *            要设置的date3
     */
    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    /**
     * 设置date4.
     * 
     * @return 返回date4
     */
    public Date getDate4() {
        return date4;
    }

    /**
     * 获取date4.
     * 
     * @param date4
     *            要设置的date4
     */
    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    /**
     * 设置date5.
     * 
     * @return 返回date5
     */
    public Date getDate5() {
        return date5;
    }

    /**
     * 获取date5.
     * 
     * @param date5
     *            要设置的date5
     */
    public void setDate5(Date date5) {
        this.date5 = date5;
    }

    /**
     * 设置date6.
     * 
     * @return 返回date6
     */
    public Date getDate6() {
        return date6;
    }

    /**
     * 获取date6.
     * 
     * @param date6
     *            要设置的date6
     */
    public void setDate6(Date date6) {
        this.date6 = date6;
    }

    /**
     * 设置date7.
     * 
     * @return 返回date7
     */
    public Date getDate7() {
        return date7;
    }

    /**
     * 获取date7.
     * 
     * @param date7
     *            要设置的date7
     */
    public void setDate7(Date date7) {
        this.date7 = date7;
    }

    /**
     * 设置date8.
     * 
     * @return 返回date8
     */
    public Date getDate8() {
        return date8;
    }

    /**
     * 获取date8.
     * 
     * @param date8
     *            要设置的date8
     */
    public void setDate8(Date date8) {
        this.date8 = date8;
    }

    /**
     * 设置date9.
     * 
     * @return 返回date9
     */
    public Date getDate9() {
        return date9;
    }

    /**
     * 获取date9.
     * 
     * @param date9
     *            要设置的date9
     */
    public void setDate9(Date date9) {
        this.date9 = date9;
    }

}
