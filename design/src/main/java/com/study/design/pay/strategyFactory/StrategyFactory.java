package com.study.design.pay.strategyFactory;

import com.study.design.pay.strategy.PayStrategy;
import com.study.design.pay.strategyEnum.StrategyEnum;

/**
 * 工厂类依靠策略枚举返回策略类
 * 不需要修改工厂类，完全无状态
 * @author YL
 * @date 2023/09/25
 **/
public class StrategyFactory {

    public static PayStrategy getPayStrategy(StrategyEnum strategyEnum){
        PayStrategy payStrategy = null;
        try {
            payStrategy = (PayStrategy) Class.forName(strategyEnum.getValue()).newInstance();
        } catch (Exception e) {
           //异常处理
        }
        return payStrategy;
    }
}
