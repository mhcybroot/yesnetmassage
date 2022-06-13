package com.ysenetdigital.yesnetmassage.models;

public class userModel {
    String email, friend, name, phone, userName,
            post, varification,uId,
            request_friend, senderID, reciverID, userID, pic, token,CounsellingGroupStatus;
    long lastMassageTime,counsellingGroupStopTime;


    public userModel() {
    }


    public userModel(String email, String friend, String name, String phone, String userName, String post, String varification, String request_friend, String senderID, String reciverID, String userID, String pic, String token, long lastMassageTime,long counsellingGroupStopTime,String counsellingGroupStatus,String uId) {
        this.email = email;
        this.uId = uId;
        this.friend = friend;
        this.name = name;
        this.phone = phone;
        this.userName = userName;
        this.post = post;
        this.varification = varification;
        this.request_friend = request_friend;
        this.senderID = senderID;
        this.reciverID = reciverID;
        this.userID = userID;
        this.pic = pic;
        this.token = token;
        this.lastMassageTime = lastMassageTime;
        this.counsellingGroupStopTime = counsellingGroupStopTime;
        this.CounsellingGroupStatus = counsellingGroupStatus;
    }

    public userModel(String email, String  name, String userName, String senderID, String reciverID, String userID) {
        this.email = email;
        this.userName = userName;
        this.name = name;
        this.senderID = senderID;
        this.reciverID = reciverID;
        this.userID = userID;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getVarification() {
        return varification;
    }

    public void setVarification(String varification) {
        this.varification = varification;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public long getLastMassageTime() {
        return lastMassageTime;
    }

    public void setLastMassageTime(long lastMassageTime) {
        this.lastMassageTime = lastMassageTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRequest_friend() {
        return request_friend;
    }

    public void setRequest_friend(String request_friend) {
        this.request_friend = request_friend;
    }

    public userModel(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReciverID() {
        return reciverID;
    }

    public String getCounsellingGroupStatus() {
        return CounsellingGroupStatus;
    }

    public void setCounsellingGroupStatus(String counsellingGroupStatus) {
        CounsellingGroupStatus = counsellingGroupStatus;
    }

    public long getCounsellingGroupStopTime() {
        return counsellingGroupStopTime;
    }

    public void setCounsellingGroupStopTime(long counsellingGroupStopTime) {
        this.counsellingGroupStopTime = counsellingGroupStopTime;
    }

    public void setReciverID(String reciverID) {
        this.reciverID = reciverID;
    }
}
