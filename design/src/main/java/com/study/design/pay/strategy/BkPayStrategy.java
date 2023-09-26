package com.study.design.pay.strategy;

import com.study.design.pojo.PayBody;

/**
 * @author YL
 * @date 2023/09/21
 **/
public class BkPayStrategy implements PayStrategy {
    @Override
    public Boolean pay(PayBody payBody) {
        return true;
    }
}
