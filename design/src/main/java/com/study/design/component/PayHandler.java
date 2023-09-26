package com.study.design.component;

import com.study.design.pojo.PayBody;
import org.springframework.stereotype.Component;

/**
 * @author YL
 * @date 2023/09/20
 **/
@Component
public class PayHandler {


    public Boolean zfbPay(PayBody payBody) {
        return true;
    }

    public Boolean wcPay(PayBody payBody) {
        return true;
    }

    public Boolean bkPay(PayBody payBody) {
        return true;
    }
}
