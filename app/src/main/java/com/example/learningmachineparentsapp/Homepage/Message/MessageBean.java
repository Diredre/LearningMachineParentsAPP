package com.example.learningmachineparentsapp.Homepage.Message;

public class MessageBean {

    private int id;
    private String content;
    private String fromUser;
    private String imgurl;
    private String desc;
    private String date;
    private String type;
    private static Boolean isRead = false;

    public MessageBean(int id, String content, String fromUser, String imgurl, Boolean isRead) {
        this.id = id;
        this.content = content;
        this.fromUser = fromUser;
        this.imgurl = imgurl;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Boolean getIsRead() {
        return isRead;
    }

    public static void setIsRead(Boolean isRead) {
        MessageBean.isRead = isRead;
    }
}
