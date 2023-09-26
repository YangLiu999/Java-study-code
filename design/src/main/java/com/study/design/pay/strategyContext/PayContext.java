package com.study.design.pay.strategyContext;

import com.study.design.pay.strategy.PayStrategy;
import com.study.design.pay.pojo.PayBody;

/**
 * @author YL
 * @date 2023/09/25
 **/
public class PayContext {

    private PayStrategy payStrategy;

    public PayContext(PayStrategy payStrategy){
        this.payStrategy = payStrategy;
    }

    public Boolean execute(PayBody payBody){
        return payStrategy.pay(payBody);
    }

}
