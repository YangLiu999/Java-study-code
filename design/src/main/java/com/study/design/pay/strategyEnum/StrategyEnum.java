package com.study.design.pay.strategyEnum;

/**
 * 策略枚举类
 * @author YL
 * @date 2023/09/25
 **/
public enum StrategyEnum {
    ZfbPayStrategy("com.study.design.pay.strategy.ZfbPayStrategy"),
    WcPayStrategy("com.study.design.pay.strategy.WcPayStrategy"),
    BkPayStrategy("com.study.design.pay.strategy.BkPayStrategy");
    String value;

    StrategyEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
