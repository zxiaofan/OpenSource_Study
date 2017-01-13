/*
 * 文件名：Splitter_Study.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： Splitter_Study.java
 * 修改人：zxiaofan
 * 修改时间：2017年1月4日
 * 修改内容：新增
 */
package guava.base;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

/**
 * 
 * @author zxiaofan
 */
public class Splitter_Study {
    String str = ",a,,b,";

    String str2 = "a,,2b,cd,,3e";

    /**
     * 如果想拆分器返回List，使用Lists.newArrayList(splitter.split(string))或类似方法。
     * 
     * splitter实例总是不可变，线程安全的，可将其定义为static final常量。
     * 
     * 拆分器工厂
     * 
     * Splitter.on(char)：按单个字符拆分；Splitter.on(String)：按字符串拆分
     * 
     * Splitter.on(CharMatcher)：按字符匹配器拆分；Splitter.on(Pattern) Splitter.onPattern(String)：按正则表达式拆分
     * 
     * Splitter.fixedLength(int)：按固定长度拆分。
     * 
     * 拆分器修饰符
     * 
     * omitEmptyStrings()忽略空字符串；limit(int n)限制拆分出的字符串数量（超出部分将合并到第n部分）；
     * 
     * trimResults()移除结果字符串的前导空白和尾部空白；trimResults(CharMatcher)给定匹配器，移除结果字符串的前导匹配字符和尾部匹配字符
     * 
     */
    @Test
    public void basicTest() {
        System.out.println("str:" + str);
        List<String> arr = Arrays.asList(str.split(","));
        System.out.println(arr.toString()); // [, a, , b]，仅尾部的空字符串被忽略
        Iterable<String> list = Splitter.on(",").trimResults().omitEmptyStrings().split(str);
        System.out.println(list.toString()); // [a, b]
        System.out.println("str2:" + str2);
        list = Splitter.on(",,").split(str2); // 按字符串拆分
        System.out.println(list.toString()); // [a, 2b,cd, 3e]，注意第二个元素为2b,cd
        list = Splitter.on(CharMatcher.javaDigit()).split(str2);
        System.out.println(list.toString()); // [a, 2b,cd, 3e]，注意第二个元素为2b,cd
        arr = Splitter.on(",").limit(3).splitToList(str2);
        System.out.println(arr.toString()); // [a, , 2b,cd,,3e] limit(int)限制
        System.out.println(arr.get(2)); // 2b,cd,,3e 超出部分合并到第3部分
    }

    /**
     * 字符匹配器[CharMatcher].
     * 
     * 修剪[trim]、折叠[collapse]、移除[remove]、保留[retain]
     */
    @Test
    public void matcherTest() {
        System.out.println("str2:" + str2);
        String theDigits = CharMatcher.digit().retainFrom(str2); // 只保留数字字符
        System.out.println(theDigits); // 23
        // 去除两端的空格，并把中间的连续空格替换成单个空格
        System.out.println(CharMatcher.whitespace().trimAndCollapseFrom(" ab  c d e ", ' ')); // (ab c d e)
        // 用*号替换所有数字
        System.out.println(CharMatcher.javaDigit().replaceFrom("123a2c", "*")); // (***a*c)
        // 移除control字符（某一控制功能的字符，在ASCII码中，第0～31号及第127号(共33个)是控制字符或通讯专用字符，如控制符：LF（换行）、CR（回车）、FF（换页）、DEL（删除）、BS（退格)、BEL（振铃）等）
        System.out.println(CharMatcher.javaIsoControl().removeFrom("acd\rs/#@q")); // acds/#@q
        // 枚举匹配字符
        String res = CharMatcher.anyOf("aeiou").removeFrom("acd\rs/#@q");
        System.out.println(res); // s/#@q
    }

    @Test
    public void separatorTest() {
        System.out.println("ee" + System.lineSeparator() + "ff"); // ee、ff分两行输出
        System.out.println("a\rbc"); // bc
        System.out.println("aaa\nbc"); // aaa、bc分两行输出
        System.out.print("Hello World!\ra"); // run：a123o World!；debug:aello World!123
        System.out.println(123); // 并不会单行输出
        System.out.println(System.getProperty("line.separator").equals(System.lineSeparator())); // true，都是\r\n
        // \n 换行,将当前位置移到下一行开头
        // \r 回车,将当前位置移到本行开头,\r后的字符将挨个覆盖本行前面的字符
        // 不同的系统、不同的应用可能做不同的处理。
    }

    /**
     * 大小写格式.HYPHEN连字符,underscore下划线
     * 
     * LOWER_CAMEL:lowerCamel；UPPER_CAMEL:UpperCamel
     * 
     * LOWER_HYPHEN:lower-hyphen【没有UPPER-HYPHEN】
     * 
     * LOWER_UNDERSCORE:lower_underscore；UPPER_UNDERSCORE:UPPER_UNDERSCORE
     * 
     * 驼峰转换，写代码生成器时很有用
     */
    @Test
    public void caseFormat() {
        String str = "helloZxiaofanCom";
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, str)); // hello-zxiaofan-com
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, str)); // HELLO_ZXIAOFAN_COM
    }
}
