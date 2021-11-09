package com.example.learningmachineparentsapp.Homepage.Homework;

import java.sql.Time;

public class HomeworkBean {
    String con;
    Time use_time;

    public HomeworkBean(){
        this.use_time = new Time(0);
    }

    public HomeworkBean(String con, Time use_time) {
        this.con = con;
        this.use_time = use_time;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public Time getUse_time() {
        return use_time;
    }

    public void setUse_time(Time use_time) {
        this.use_time = use_time;
    }
}
