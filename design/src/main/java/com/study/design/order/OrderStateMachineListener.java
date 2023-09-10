package com.study.design.order;

import com.study.design.order.pojo.Order;
import com.study.design.order.pojo.OrderStateChangeAction;
import com.study.design.order.pojo.OrderStateEnum;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import javax.naming.Name;

/**
 * @author YL
 * @date 2023/09/08
 * 状态机状态变化监听器:监听到action后进行状态变更
 **/
@Component("OrderStateMachineListener")
@WithStateMachine(name = "orderStateMachine")
public class OrderStateMachineListener {

    @OnTransition(source = "ORDER_STATE_WAIT_PAY",target = "ORDER_STATE_WAIT_SEND")
    public Boolean payToSend(Message<OrderStateChangeAction> message){
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderState(OrderStateEnum.ORDER_STATE_WAIT_SEND);
        return true;
    }

    @OnTransition(source = "ORDER_STATE_WAIT_SEND",target = "ORDER_STATE_WAIT_RECEIVE")
    public Boolean sendToReceive(Message<OrderStateChangeAction> message){
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderState(OrderStateEnum.ORDER_STATE_WAIT_RECEIVE);
        return true;
    }

    @OnTransition(source = "ORDER_STATE_WAIT_RECEIVE",target = "ORDER_STATE_FINISH")
    public Boolean receiveToFinish(Message<OrderStateChangeAction> message){
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderState(OrderStateEnum.ORDER_STATE_FINISH);
        return true;
    }

}
