package com.study.design.pay.strategy;

import com.study.design.pojo.PayBody;
import org.springframework.stereotype.Repository;

/**
 * @author YL
 * @date 2023/09/21
 **/

public interface PayStrategy {

    Boolean pay(PayBody payBody);

}
