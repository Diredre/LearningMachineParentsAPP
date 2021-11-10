package com.example.learningmachineparentsapp.Circle.Video;

public class VideoCommentBean {

    private int id;
    private String nickName, createDate, content, num;

    public VideoCommentBean(int id, String nickName, String createDate, String content, String num) {
        this.id = id;
        this.nickName = nickName;
        this.createDate = createDate;
        this.content = content;
        this.num = num;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setNum(String num){
        this.num = num;
    }

    public String getNum(){
        return num;
    }
}
