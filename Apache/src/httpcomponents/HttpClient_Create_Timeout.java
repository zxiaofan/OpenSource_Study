/*
 * 文件名：HttpClient_Create_Timeout.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： HttpClient_Create_Timeout.java
 * 修改人：zxiaofan
 * 修改时间：2017年2月22日
 * 修改内容：新增
 */
package httpcomponents;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import junit.framework.TestCase;

/**
 * 
 * @author zxiaofan
 */
public class HttpClient_Create_Timeout extends TestCase {
    // 如果是extends TestCase，单元测试类必须以test(小写)开头
    /**
     * 4.5版本.
     * 
     */
    public void testInitHttpClient_45() {
        System.out.println("HttpClient 4.5版本初始化");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://zxiaofan.com");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet); // import httpcore.jar
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != response) {
            System.out.println("StatusLine:" + response.getStatusLine());// 得到请求结果
            HttpEntity entity = response.getEntity();// 得到请求回来的数据
            String message = null;
            try {
                message = EntityUtils.toString(entity, "utf-8");
                System.out.println(message.substring(0, 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 3.x版本.
     * 
     */
    public void initHttpClient_3x() {
        System.out.println("HttpClient 3.x版本初始化");
        // HttpClient httpClient = new DefaultHttpClient();
        // HttpClient httpClient = new HttpClient();
        // httpClient.setConnectionTimeout(30000);
        // httpClient.setTimeout(30000);
        // HttpClient httpClient = new HttpClient();
        // httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
    }

    /**
     * 4.x版本.
     * 
     */
    public void initHttpClient_4x() {
        System.out.println("HttpClient 4.x版本初始化");
        // HttpClient httpClient = new DefaultHttpClient();
        System.out.println("超时设置方案4.3版本后已过时");
        // httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);// 连接时间
        // httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);// 数据传输时间
    }

    /**
     * 4.3版本.
     * 
     * 4.3版本若不设置超时，一旦服务器没有响应，等待时间会很久很久
     */
    public void initHttpClient_43() {
        System.out.println("HttpClient 4.3版本初始化");
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        System.out.println("超时设置方案4.3版本后已过时");
        // HttpGet httpGet = new HttpGet("zxiaofan.com");// HTTP Get请求(POST雷同)
        // RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
        // httpGet.setConfig(requestConfig);
        // httpClient.execute(httpGet);// 执行请求
    }
}
