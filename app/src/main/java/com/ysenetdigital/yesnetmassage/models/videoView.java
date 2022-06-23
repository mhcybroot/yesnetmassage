package com.ysenetdigital.yesnetmassage.models;

public class videoView {
    String VidoeUrl,userID,videoID;

    public videoView() {
    }

    public videoView(String vidoeUrl, String videoID) {
        VidoeUrl = vidoeUrl;
        this.videoID = videoID;
    }

    public videoView(String vidoeUrl, String userID, String videoID) {
        VidoeUrl = vidoeUrl;
        this.userID = userID;
        this.videoID = videoID;
    }

    public String getVidoeUrl() {
        return VidoeUrl;
    }

    public void setVidoeUrl(String vidoeUrl) {
        VidoeUrl = vidoeUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }
}
