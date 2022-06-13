package com.ysenetdigital.yesnetmassage.models;

public class massageModels {
    String uId;

    String Reciever;

    String massageID;
    String image_url;
    String massage;
    int feeling;
    Long timestamp;
    boolean isseen;

    public massageModels(String uId, String massage, Long timestamp) {
        this.uId = uId;
        this.massage = massage;
        this.timestamp = timestamp;
    }

    public massageModels(String uId, String massage, String reciever, String image_url, boolean isseen) {
        this.uId = uId;
        this.massage = massage;
        Reciever = reciever;
        this.image_url = image_url;
        this.isseen = isseen;
    }

    public massageModels(String uId, String massage, String reciever, boolean isseen) {
        this.uId = uId;
        this.massage = massage;
        Reciever = reciever;
        this.isseen = isseen;
    }

    public massageModels(String uId, String massage, boolean isseen) {
        this.uId = uId;
        this.massage = massage;
        this.isseen = isseen;
    }

    public massageModels(String uId, String image_url, String massage, int feeling, boolean isseen) {
        this.uId = uId;

        this.image_url = image_url;
        this.massage = massage;
        this.feeling = feeling;

        this.isseen = isseen;
    }

    public massageModels(String uId, String massage, String image_url) {
        this.uId = uId;
        this.massage = massage;
        this.image_url = image_url;
    }

    public massageModels(String uId, String massage) {
        this.uId = uId;
        this.massage = massage;
    }

    public massageModels(String massage, Long timestamp) {
        this.massage = massage;
        this.timestamp = timestamp;
    }

    public massageModels() {
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public String getReciever() {
        return Reciever;
    }

    public void setReciever(String reciever) {
        Reciever = reciever;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMassageID() {
        return massageID;
    }

    public void setMassageID(String massageID) {
        this.massageID = massageID;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
