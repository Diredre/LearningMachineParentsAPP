package com.example.learningmachineparentsapp.Homepage.Homework;

import java.sql.Date;
import java.sql.Time;

public class HomeworkBean {
    private String con;
    private Date use_time = new Date(System.currentTimeMillis());
    private int isComplete; //0： 布置未完成 1：布置已完成 2：孩子主动上传的完成
    private Date com_time;

    public HomeworkBean(){
        this.use_time = new Date(0);
    }

    public HomeworkBean(String con, Date use_time) {
        this.con = con;
        this.use_time = use_time;
    }

    public HomeworkBean(String con, Date use_time, int isComplete, Date com_time) {
        this.con = con;
        this.use_time = use_time;
        this.isComplete = isComplete;
        this.com_time = com_time;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public Date getUse_time() {
        return use_time;
    }

    public void setUse_time(Date use_time) {
        this.use_time = use_time;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int complete) {
        isComplete = complete;
    }

    public Date getCom_time() {
        return com_time;
    }

    public void setCom_time(Date com_time) {
        this.com_time = com_time;
    }
}
