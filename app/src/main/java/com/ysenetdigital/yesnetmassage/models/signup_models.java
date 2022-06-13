package com.ysenetdigital.yesnetmassage.models;

public class signup_models {

    String profilepic,username,userID,memberId,email,password,lastmassage,post,verification,friend,request_friend,block,token,phoneNumber,accept,timeofmassage;
    long lastmassagetime;
    int total_reply=0;
Integer totalRefer=0;
  public signup_models() {

    }

    public signup_models(String userID, String friend, String request_friend) {
        this.userID = userID;
        this.friend = friend;
        this.request_friend = request_friend;
    }

    public signup_models(String username, String userID, String memberId, String email, String password, String post) {
        this.username = username;
        this.userID = userID;
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.post = post;
    }

    public signup_models(String username, String userID, String memberId, String email, String password, String post, String verification, String phoneNumber) {
        this.username = username;
        this.userID = userID;
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.post = post;
        this.verification = verification;
        this.phoneNumber = phoneNumber;
    }


    public signup_models(String profilepic, String username, String userID, String memberId, String email, String password, String lastmassage, String post, String verification) {
        this.profilepic = profilepic;
        this.username = username;
        this.userID = userID;
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.lastmassage = lastmassage;
        this.post = post;
        this.verification = verification;
    }

    public Integer getTotalRefer() {
        return totalRefer;
    }

    public void setTotalRefer(Integer totalRefer) {
        this.totalRefer = totalRefer;
    }

    public String getTimeofmassage() {
        return timeofmassage;
    }

    public void setTimeofmassage(String timeofmassage) {
        this.timeofmassage = timeofmassage;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTotal_reply() {
        return total_reply;
    }

    public void setTotal_reply(int total_reply) {
        this.total_reply = total_reply;
    }

    public String getToken() {
        return token;
    }

    public long getLastmassagetime() {
        return lastmassagetime;
    }

    public void setLastmassagetime(long lastmassagetime) {
        this.lastmassagetime = lastmassagetime;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastmassage() {
        return lastmassage;
    }

    public void setLastmassage(String lastmassage) {
        this.lastmassage = lastmassage;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
