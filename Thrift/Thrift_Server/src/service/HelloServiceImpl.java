/*
 * 文件名：HelloServiceImpl.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： HelloServiceImpl.java
 * 修改人：yunhai
 * 修改时间：2016年3月25日
 * 修改内容：新增
 */
package service;

import thrift.ThriftHelloWorld.Iface;

/**
 * 服务端实现类
 * 
 * @author yunhai
 */
public class HelloServiceImpl implements Iface {
    public String sayHello(String username) {
        return "hello " + username;
    }

    public String getRandom() {
        return "random";
    }
}
