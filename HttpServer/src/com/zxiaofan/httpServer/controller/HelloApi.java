/*
 * 文件名：HelloApi.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： HelloApi.java
 * 修改人：xiaofan
 * 修改时间：2017年3月11日
 * 修改内容：新增
 */
package com.zxiaofan.httpServer.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 对外开放的Http接口.
 * 
 * 必须引入aopalliance-1.0.jar，否则服务启动虽不报错，但请求http服务可能404
 * 
 * produces="application/json;charset=utf-8":客户端只接收json且编码为utf-8
 * 
 * @author xiaofan
 */
@Controller
@RequestMapping(value = "/api")
@Component
public class HelloApi {
    @RequestMapping(value = "/boy", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String boy(HttpServletRequest request, HttpServletResponse response) {
        String json = null;
        String result = null;
        StringBuffer buffer = new StringBuffer();
        String readLine = "";
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = request.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((readLine = bufferedReader.readLine()) != null) {
                // RequestMethod.POST模式时，readLine有值
                buffer.append(readLine);
            }
            json = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("boy_param:" + json); // 入参
        result = "hello_boy"; // 业务处理
        return result;
    }

    @RequestMapping(value = "/girl", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String girl(HttpServletRequest request, HttpServletResponse response) {
        String json = null;
        String result = null;
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            buffer.append(request.getRequestURL());
            Map<String, String[]> params = request.getParameterMap();
            for (String key : params.keySet()) { // RequestMethod.GET模式只能获取url地址后面的参数
                String[] values = params.get(key);
                if (values.length > 0) {
                    buffer.append("?");
                }
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    buffer.append(key + "=" + value + "&");
                }
            }
            // 通常直接取Map(params)里的值更方便
            json = buffer.toString();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != inputStreamReader) {
                    inputStreamReader.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("girl_param:" + json); // 入参
        result = "hello_girl"; // 业务处理
        return result;
    }
}
