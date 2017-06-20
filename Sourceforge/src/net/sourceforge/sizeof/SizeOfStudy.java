/*
 * 文件名：SizeOfStudy.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： SizeOfStudy.java
 * 修改人：zxiaofan
 * 修改时间：2017年6月20日
 * 修改内容：新增
 */
package net.sourceforge.sizeof;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 利用SizeOf计算java对象的大小.
 * 
 * 运行前添加VM参数：-javaagent:./lib/SizeOf.jar【添加步骤run-Run configurations...-Arguments-VM Arguments】
 * 
 * https://sourceforge.net/projects/sizeof/?source=typ_redirect
 * 
 * byte:1字节 boolean:1字节(待定) short:2字节 char:2字节 int:4字节 float:4字节 long:8字节 double:8字节 java.lang.Object:8字节 ref object：对象的引用4字节 return address：4字节
 * 
 * @author zxiaofan
 */
public class SizeOfStudy {
    @Test
    public void basicTest() {
        int num = 9000;
        Map<String, String> map = new HashMap<>(num);
        for (int i = 0; i < num; i++) {
            map.put("key" + i, "value" + i);
        }
        printSize(map);
        printSizeH(map);
        String filePath = "D:\\json.txt";
        String fileCon = null;
        try {
            fileCon = readFileToString(filePath, "utf-8"); // 实际大小85.3K
        } catch (IOException e) {
            e.printStackTrace();
        }
        printSize(fileCon);
        printSizeH(fileCon);
    }

    /**
     * 读取本地文件为字符串.
     * 
     * @param fileName
     *            文件名
     * @param encode
     *            字符编码
     * @return String
     * @throws IOException
     *             IOException
     */
    public String readFileToString(String fileName, String encode) throws IOException {
        java.io.InputStream input = null;
        StringWriter output = null;
        InputStreamReader in = null;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                if (file.isDirectory())
                    throw new IOException("File '" + file + "' exists but is a directory");
                if (!file.canRead())
                    throw new IOException("File '" + file + "' cannot be read");
                else
                    input = new FileInputStream(file);
            } else {
                throw new FileNotFoundException("File '" + file + "' does not exist");
            }
            output = new StringWriter();
            if (encode == null) {
                in = new InputStreamReader(input);
            } else {
                in = new InputStreamReader(input, encode);
            }
            char buffer[] = new char[4096];
            for (int n = 0; -1 != (n = in.read(buffer));) {
                output.write(buffer, 0, n);
            }
            return output.toString();
        } finally {
            close(output, in, input);
        }
    }

    /**
     * 输出对象大小.
     * 
     * @param obj
     *            obj
     */
    private void printSize(Object obj) {
        System.out.println(getSize(obj));
    }

    /**
     * 获取对象大小.
     * 
     * @param obj
     *            obj
     * @return size
     */
    private long getSize(Object obj) {
        return SizeOf.deepSizeOf(obj);
    }

    /**
     * 批量关闭文件流.
     * 
     * @param closeables
     *            文件流集合
     */
    private void close(AutoCloseable... closeables) {
        if (null != closeables && closeables.length > 0) {
            for (AutoCloseable autoCloseable : closeables) {
                if (null != autoCloseable) {
                    try {
                        autoCloseable.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 友好展示对象大小.
     * 
     * @param obj
     *            obj
     */
    private void printSizeH(Object obj) {
        long deepSizeOf = getSize(obj);
        if (deepSizeOf < 1024) {
            System.out.println(deepSizeOf);
        } else {
            StringBuilder builder = new StringBuilder();
            long tempB = deepSizeOf % 1024;
            long sizeK = (deepSizeOf - tempB) / 1024 % 1024;

            long sizeM = (deepSizeOf - tempB) / 1024 / 1024 % 1024;
            long sizeG = 0;
            sizeG = (deepSizeOf - tempB) / 1024 / 1024 / 1024 % 1024;
            if (sizeG >= 1) {
                builder.append(sizeG).append("G_");
            }
            if (sizeM >= 1) {
                builder.append(sizeM).append("M_");
            }
            builder.append(sizeK).append("K_").append(tempB).append("B");
            System.out.println(builder.toString());
        }
    }
}
