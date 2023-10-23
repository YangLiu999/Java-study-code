package com.yangliu.nettyDemo.handler;

import java.lang.reflect.InvocationTargetException;

/**
 * @author YL
 * @date 2021/12/27
 **/
public interface CDHandler {
    byte[] doHandle(byte[] req) throws NoSuchMethodException, InvocationTargetException,IllegalAccessError;
}
