package com.study.design.service;

import com.study.design.pay.facade.StrategyFacade;
import com.study.design.pay.strategyContext.PayContext;
import com.study.design.component.PayHandler;
import com.study.design.pay.strategyEnum.StrategyEnum;
import com.study.design.pay.strategyFactory.StrategyFactory;
import com.study.design.pay.pojo.PayBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YL
 * @date 2023/09/20
 **/
@Service
public class PayServie {
    @Autowired
    private PayHandler payHandler;

    public Boolean pay(PayBody payBody) {

        Boolean flag = false;
       /* if (payBody.getType() == 0){
            //zfb
            //flag = payHandler.zfbPay(payBody);
            //flag = new PayContext(new ZfbPayStrategy()).execute(payBody);
            flag = new PayContext(StrategyFactory.getPayStrategy(StrategyEnum.ZfbPayStrategy))
                    .execute(payBody);
        }else if (payBody.getType() == 1){
            //wc
            //flag = payHandler.wcPay(payBody);
            //flag = new PayContext(new WcPayStrategy()).execute(payBody);
            flag = new PayContext(StrategyFactory.getPayStrategy(StrategyEnum.WcPayStrategy))
                    .execute(payBody);
        }else if (payBody.getType() == 2){
            //bk
            //flag = payHandler.bkPay(payBody);
            //flag = new PayContext(new BkPayStrategy()).execute(payBody);
            flag = new PayContext(StrategyFactory.getPayStrategy(StrategyEnum.BkPayStrategy))
                    .execute(payBody);
        }else {
            throw new UnsupportedOperationException("unknown type");
        }*/
        //门面类优化修改
        flag = StrategyFacade.pay(payBody);
        if (flag){
            saveToDb(payBody);
        }
        return flag;
    }

    private void saveToDb(PayBody payBody) {

    }
}
