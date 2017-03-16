/*
 * 文件名：HelloApi.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： HelloApi.java
 * 修改人：xiaofan
 * 修改时间：2017年3月11日
 * 修改内容：新增
 */
package com.zxiaofan.dubboConsumer.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxiaofan.dubboConsumer.service.IConsumerService;

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
@RequestMapping
@Component
public class HelloApi {
    @Resource(name = "consumerService")
    private IConsumerService consumerService;

    @RequestMapping(value = "/api", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
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
        System.out.println("api_param:" + json); // 入参
        result = consumerService.hi(json); // 业务处理
        return result;
    }
}
