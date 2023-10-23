package com.yangliu.nettyDemo.dispatcher;

import com.yangliu.nettyDemo.handler.CDData;

/**
 * @author YL
 * @date 2021/12/22
 **/
public interface Dispatcher {
    void init() throws Exception;
    CDData doDispatcher(CDData req);
}
