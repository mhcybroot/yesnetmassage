package com.ysenetdigital.yesnetmassage.models;

public class friendModel {

    String userId,friend,request_friend;

    public friendModel(String userId, String friend, String request_friend) {
        this.userId = userId;
        this.friend = friend;
        this.request_friend = request_friend;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getRequest_friend() {
        return request_friend;
    }

    public void setRequest_friend(String request_friend) {
        this.request_friend = request_friend;
    }
}
