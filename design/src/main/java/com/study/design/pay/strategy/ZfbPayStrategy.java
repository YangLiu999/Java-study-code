package com.study.design.pay.strategy;

import com.study.design.pojo.PayBody;

/**
 * @author YL
 * @date 2023/09/21
 **/
public class ZfbPayStrategy implements PayStrategy {
    @Override
    public Boolean pay(PayBody payBody) {
        return true;
    }
}
