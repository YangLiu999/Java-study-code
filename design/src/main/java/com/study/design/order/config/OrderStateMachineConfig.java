package com.study.design.order.config;

import com.study.design.order.pojo.Order;
import com.study.design.order.pojo.OrderStateChangeAction;
import com.study.design.order.pojo.OrderStateType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

/**
 * @author YL
 * @date 2023/09/07
 *
 * 状态机：初始化状态；配置状态之间的转化关系；一些持久化工作
 **/
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStateType, OrderStateChangeAction> {
    /**
     * 初始化状态
     * @param states
     * @throws Exception
     */
    public void configure(StateMachineStateConfigurer<OrderStateType, OrderStateChangeAction> states) throws Exception {
            states.withStates().initial(OrderStateType.ORDER_STATE_WAIT_PAY)
                    .states(EnumSet.allOf(OrderStateType.class));
        }

    /**
     * 配置状态之间的转化
     * @param transitions
     * @throws Exception
     */
    public void configure(StateMachineTransitionConfigurer<OrderStateType, OrderStateChangeAction> transitions) throws Exception {
            transitions.withExternal().source(OrderStateType.ORDER_STATE_WAIT_PAY)
                    .target(OrderStateType.ORDER_STATE_WAIT_SEND)
                    .event(OrderStateChangeAction.PAY_ORDER)
                    .and()
                    .withExternal().source(OrderStateType.ORDER_STATE_WAIT_SEND)
                    .target(OrderStateType.ORDER_STATE_WAIT_RECEIVE)
                    .event(OrderStateChangeAction.SEND_ORDER)
                    .and()
                    .withExternal().source(OrderStateType.ORDER_STATE_WAIT_RECEIVE)
                    .target(OrderStateType.ORDER_STATE_FINISH)
                    .event(OrderStateChangeAction.RECEIVE_ORDER);
        }

    //配置持久化
    @Bean
    public DefaultStateMachinePersister machinePersister(){
        return new DefaultStateMachinePersister<>(new StateMachinePersist<Object,Object, Order>(){

            public void write(StateMachineContext<Object, Object> stateMachineContext, Order order) throws Exception {
                //任何方式的持久化操作 redis

            }

            public StateMachineContext<Object, Object> read(Order order) throws Exception {
                return new DefaultStateMachineContext(order.getOrderState(),null,null,null);
            }
        });
    }
}
