package com.ysenetdigital.yesnetmassage.models;

public class additional_pic {
    String picUrl;
String picId;
String userID;

    public additional_pic() {
    }


    public additional_pic(String picUrl, String picId) {
        this.picUrl = picUrl;
        this.picId = picId;
    }

    public additional_pic(String picUrl, String picId, String userID) {
        this.picUrl = picUrl;
        this.picId = picId;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public additional_pic(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }


    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
