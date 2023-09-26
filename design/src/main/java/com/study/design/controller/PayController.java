package com.study.design.controller;

import com.study.design.pay.pojo.PayBody;
import com.study.design.service.PayServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YL
 * @date 2023/09/20
 **/
@RestController
public class PayController {

    @Autowired
    private PayServie payServie;

    @RequestMapping("/pay")
    public Boolean pay(@RequestBody PayBody payBody){
        payServie.pay(payBody);

        return true;
    }
}
