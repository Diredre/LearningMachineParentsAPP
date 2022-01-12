package com.example.learningmachineparentsapp.Homepage.Homework;

import android.util.Log;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class HomeworkBean {
    private String con;
    private String use_time;
    private int isComplete; //0： 布置未完成 1：布置已完成 2：孩子主动上传的完成
    private String com_time;
    private List<ImageBean> piclist = new ArrayList<>();


    public HomeworkBean(String con, String use_time) {
        this.con = con;
        this.use_time = use_time;
    }

    public HomeworkBean(String con, String use_time, int isComplete, String com_time, List<ImageBean> piclist) {
        this.con = con;
        this.use_time = use_time;
        this.isComplete = isComplete;
        this.com_time = com_time;
        this.piclist = piclist;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public String getCom_time() {
        return com_time;
    }

    public void setCom_time(String com_time) {
        this.com_time = com_time;
    }

    public List<ImageBean> getPiclist() {
        return piclist;
    }

    public void setPiclist(List<ImageBean> piclist) {
        this.piclist = piclist;
    }
}
