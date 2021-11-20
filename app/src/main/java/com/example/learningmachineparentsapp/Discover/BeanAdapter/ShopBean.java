package com.example.learningmachineparentsapp.Discover.BeanAdapter;

import java.text.DecimalFormat;

public class ShopBean {

    private String iconid;
    private String tit;
    private double money;
    private int people;
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");    // 格式化浮点数位两位小数

    public ShopBean(String iconid, String tit, double money, int people) {
        this.iconid = iconid;
        this.tit = tit;
        this.money = money;
        this.people = people;
    }

    public String getIconid() {
        return iconid;
    }

    public void setIconid(String iconid) {
        this.iconid = iconid;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public double getMoney() {
        return Double.valueOf(decimalFormat.format(money));
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }
}
