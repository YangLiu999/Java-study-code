package com.study.design.pay.facade;

import com.study.design.pay.pojo.PayBody;
import com.study.design.pay.strategy.PayStrategy;
import com.study.design.pay.strategyContext.PayContext;
import com.study.design.pay.strategyEnum.StrategyEnum;
import com.study.design.pay.strategyFactory.StrategyFactory;

/**
 * 只暴露门面，对于策略、策略枚举、工厂全部隐藏
 * 门面就是超强封装
 * @author YL
 * @date 2023/09/26
 **/
public class StrategyFacade {

    //定义map提前初始化StrategyEnum映射关系

    public static Boolean pay(PayBody payBody){
        //获取策略枚举
        StrategyEnum strategyEnum = getStrategyEnum(payBody.getType());
        //获取策略对象
        PayStrategy payStrategy = StrategyFactory.getPayStrategy(strategyEnum);
        //生成策略上下文
        PayContext payContext = new PayContext(payStrategy);
        //进行扣款
        return payContext.execute(payBody);
    }

    private static StrategyEnum getStrategyEnum(int type) {
        switch (type){
            case 0:
                return StrategyEnum.ZfbPayStrategy;
            case 1:
                return StrategyEnum.WcPayStrategy;
            case 2:
                return StrategyEnum.BkPayStrategy;
            default:
                return null;
        }
    }
}
