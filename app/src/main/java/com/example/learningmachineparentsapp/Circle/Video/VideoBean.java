package com.example.learningmachineparentsapp.Circle.Video;

public class VideoBean {

    private String videoUrl, imageUrl, auther, con;
    private int likenum = 0;
    private int comment_num = 0;
    private int type = 0;   // 运动类=1， 文化类=2， 历史类=3
    private boolean isLike = false;

    public VideoBean(String videoUrl, String imageUrl, String auther, String con) {
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.auther = auther;
        this.con = con;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
