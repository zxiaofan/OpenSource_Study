/*
 * 文件名：ThriftServerProxy.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： ThriftServerProxy.java
 * 修改人：yunhai
 * 修改时间：2016年7月18日
 * 修改内容：新增
 */
package com.zxiaofan.thrift;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author yunhai
 */
public class ThriftServiceCenter implements Runnable, InitializingBean, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @端口号
     */
    private int port;

    /**
     * @thrift 服务中心
     */
    private TServer server;

    /**
     * @thrift 服务中心
     */
    private Handler handler;

    /**
     * @线程集合
     */
    private Map<String, Object> processorMap = new HashMap<String, Object>();

    /**
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     *            port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return processorMap
     */
    public Map<String, Object> getProcessorMap() {
        return processorMap;
    }

    /**
     * @param processorMap
     *            processorMap
     */
    public void setProcessorMap(Map<String, Object> processorMap) {
        this.processorMap = processorMap;
    }

    /**
     * @程序启动代码
     */
    public void startServer() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void run() {
        TServerTransport t;
        this.isPortValidate();
        try {
            t = new TServerSocket(port);
            TMultiplexedProcessor processor = new TMultiplexedProcessor(); // TMultiplexedProcessor实现多接口
            Set<String> set = processorMap.keySet();
            // 将用户配置的接口加入到发布列表
            for (String interfaceName : set) {
                Object o = processorMap.get(interfaceName);
                // com.better517na.balAccDataService.service.Hello$Processor
                Class<?> proClass = Class.forName(interfaceName + "$Processor");
                Class<?> ifaceClass = Class.forName(interfaceName + "$Iface");
                TProcessor pro = (TProcessor) proClass.getConstructor(ifaceClass).newInstance(o);
                processor.registerProcessor(interfaceName, pro);
            }
            server = new TThreadPoolServer(new TThreadPoolServer.Args(t).processor(processor));
            server.setServerEventHandler(new Handler()); // 不用gs路由必须注释此行
            logger.info("the server is started and is listening at " + port + "...");
            System.out.println("the server is started and is listening at " + port + "...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @验证端口是否可用
     */
    private void isPortValidate() {
        try {
            ServerSocket so = new ServerSocket(port);
            so.close();
        } catch (IOException e1) {
            RuntimeException e = new RuntimeException("thrift服务中心没有正常启动，请检查端口占用情况！");
            throw e;
        }
    }

    @Override
    public void destroy() throws Exception {
        server.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startServer();
    }

    /**
     * @return server
     */
    public TServer getServer() {
        return server;
    }

    /**
     * @param server
     *            server
     */
    public void setServer(TServer server) {
        this.server = server;
    }

    /**
     * @byte2int
     * @param res
     *            res
     * @return int
     */
    public static int byte2int(byte[] res) {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    /**
     * @arrToObject
     * @param b
     *            b
     * @return Map
     * @throws Exception
     *             Exception
     */
    private static Map<String, Object> arrToObject(byte[] b) throws Exception {
        if (null == b) {
            return null;
        }
        ByteArrayInputStream bo = new ByteArrayInputStream(b);
        ObjectInputStream oo = new ObjectInputStream(bo);
        Map<String, Object> obj = (Map<String, Object>) oo.readObject();
        bo.close();
        oo.close();
        return obj;
    }
}
