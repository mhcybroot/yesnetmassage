package com.ysenetdigital.yesnetmassage.models;

public class videoView {
    String VidoeUrl,userID,videoID;

    public videoView() {
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
