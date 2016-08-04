/*
 * 文件名：TServerEventHandler.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： TServerEventHandler.java
 * 修改人：zxiaofan
 * 修改时间：2016年8月4日
 * 修改内容：新增
 */
package com.zxiaofan.thrift;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * TServerEventHandler
 * 
 * @author zxiaofan
 */
public class Handler implements TServerEventHandler {

    /**
     * {@inheritDoc}.
     */
    @Override
    public ServerContext createContext(TProtocol arg0, TProtocol arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void preServe() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void processContext(ServerContext paramServerContext, TTransport in, TTransport out) {
        try {
            TSocket socket = (TSocket) in;
            // 读取前4位，定义了object的大小
            byte[] b = new byte[4];
            socket.read(b, 0, 4); // 读取socket的4个Byte到b中
            int len = byte2int(b);
            byte[] objArr = new byte[len];
            socket.read(objArr, 0, len);
            socket.read(b, 0, 4);
            len = byte2int(b);
            objArr = new byte[len];
            socket.read(objArr, 0, len);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
