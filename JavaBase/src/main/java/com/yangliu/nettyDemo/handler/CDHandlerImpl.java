package com.yangliu.nettyDemo.handler;

import com.yangliu.nettyDemo.dispatcher.Dispatcher;
import com.yangliu.nettyDemo.tool.CDUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author YL
 * @date 2021/12/27
 **/
public class CDHandlerImpl implements CDHandler{

    @Autowired
    private Dispatcher dispatcher;

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public byte[] doHandle(byte[] req) throws NoSuchMethodException, InvocationTargetException, IllegalAccessError {
        try {
            String sreq = new String(req,"UTF-8");
            CDData cd = CDUtil.fromXml(sreq);
            CDData resp = dispatcher.doDispatcher(cd);
            return CDUtil.toXml(resp).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
