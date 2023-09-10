package com.study.design.order.pojo;

/**
 * @author YL
 * @date 2023/09/07
 *
 **/
public class Order {

    private Integer orderId;

    private OrderStateEnum orderState;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderStateEnum getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStateEnum orderState) {
        this.orderState = orderState;
    }
}
