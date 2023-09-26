package com.study.design.order.pojo;

/**
 * @author YL
 * @date 2023/09/07
 *
 **/
public class Order {

    private Integer orderId;

    private OrderStateType orderState;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderStateType getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStateType orderState) {
        this.orderState = orderState;
    }
}
