package com.ysenetdigital.yesnetmassage.models;

public class GetReactModel {
    int Position ;

    public GetReactModel(int position) {
        Position = position;
    }

    public GetReactModel() {
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }
}
