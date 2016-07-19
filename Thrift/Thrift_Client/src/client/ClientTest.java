/*
 * 文件名：ClientTest.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： ClientTest.java
 * 修改人：yunhai
 * 修改时间：2016年3月25日
 * 修改内容：新增
 */
package client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;

import thrift.IThriftService;

/**
 * 客户端实现
 * 
 * @author yunhai
 */
public class ClientTest {
    /**
     * 调用Hello服务
     */
    @Test
    public void startClient() {

        try {

            // 设置调用的服务地址为本地，端口为6789
            TTransport transport = new TSocket("localhost", 1234);
            transport.open();

            // 数据传输协议有：二进制协议、压缩协议、JSON格式协议
            // 这里使用的是二进制协议
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // 单接口
            // IThriftService.Client client = new IThriftService.Client(protocol);
            // 多接口调用
            // serviceName需与app-context-service.xml中的processorMap的key=com.zxiaofan.thrift.IThriftService一致
            TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol, "com.zxiaofan.thrift.IThriftService");
            IThriftService.Client client = new IThriftService.Client(mp1);
            // 调用服务器端的服务方法
            System.out.println(client.sayHello("zxiaofan"));
            System.out.println(client.getRandom());

            // 关闭
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
