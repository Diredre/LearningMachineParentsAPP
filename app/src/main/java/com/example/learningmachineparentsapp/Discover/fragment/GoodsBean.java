package com.example.learningmachineparentsapp.Discover.fragment;

import java.text.DecimalFormat;

public class GoodsBean {

    private String goodsid;
    private String iconid;
    private String name;
    private double money = 66;
    private int people;
    private int num = 0;
    private boolean isCart = false;
    private boolean isCheck = false;
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");    // 格式化浮点数位两位小数

    public GoodsBean(String goodsid, String iconid, String name, double money, int people) {
        this.goodsid = goodsid;
        this.iconid = iconid;
        this.name = name;
        this.money = money;
        this.people = people;
    }

    public GoodsBean(String goodsid, String iconid, String name, double money, int num, boolean isCart) {
        this.goodsid = goodsid;
        this.iconid = iconid;
        this.name = name;
        this.money = money;
        this.num = num;
        this.isCart = isCart;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public boolean isCart() {
        return isCart;
    }

    public void setCart(boolean cart) {
        isCart = cart;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getIconid() {
        return iconid;
    }

    public void setIconid(String iconid) {
        this.iconid = iconid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }
}
