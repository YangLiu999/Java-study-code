package com.study.design.pojo;

/**
 * @author YL
 * @date 2023/09/20
 **/
public class PayBody {

    private int type;

    private String account;

    private int amount;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
