/*
 * 文件名：ThriftServerProxy.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ThriftServerProxy.java
 * 修改人：yunhai
 * 修改时间：2016年7月18日
 * 修改内容：新增
 */
package thrift;

import java.lang.reflect.Constructor;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yunhai
 */
public class ThriftServerProxy {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int port;// 端口

    private String serviceInterface;// 实现类接口

    private Object serviceImplObject;// 实现类

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void start() {
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("init");
                    TServerSocket serverTransport = new TServerSocket(getPort());
                    // 实现类处理类class
                    Class Processor = Class.forName(getServiceInterface() + "$Processor");
                    // 接口
                    Class Iface = Class.forName(getServiceInterface() + "$Iface");
                    // 接口构造方法类
                    Constructor con = Processor.getConstructor(Iface);
                    // 实现类处理类
                    TProcessor processor = (TProcessor) con.newInstance(serviceImplObject);
                    TBinaryProtocol.Factory protFactory = new TBinaryProtocol.Factory(true, true);
                    TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
                    args.protocolFactory(protFactory);
                    args.processor(processor);
                    TServer server = new TThreadPoolServer(args);
                    logger.info("Starting server on port " + getPort() + " ...");
                    System.out.println("Starting server on port " + getPort() + " ...");
                    server.serve();
                } catch (TTransportException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Object getServiceImplObject() {
        return serviceImplObject;
    }

    public void setServiceImplObject(Object serviceImplObject) {
        this.serviceImplObject = serviceImplObject;
    }
}
