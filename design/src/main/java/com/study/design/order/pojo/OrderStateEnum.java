package com.study.design.order.pojo;

/**
 * @author YL
 * @date 2023/09/07
 * 订单状态枚举
 **/
public enum OrderStateEnum {

    ORDER_STATE_WAIT_PAY,//待支付
    ORDER_STATE_WAIT_SEND,//待发货
    ORDER_STATE_WAIT_RECEIVE,//待收货
    ORDER_STATE_FINISH;//已完成

}
