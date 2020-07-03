package com.cs5520.w9firebase.realtimedatabase.models;

import java.util.ArrayList;

public class User {
    private String username;
    private Integer stickersSent;
    private ArrayList<Sticker> stickersReceived;

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStickersSent() {
        return stickersSent;
    }

    public void setStickersSent(Integer stickersSent) {
        this.stickersSent = stickersSent;
    }

    public ArrayList<Sticker> getStickersReceived() {
        return stickersReceived;
    }

    public void setStickersReceived(ArrayList<Sticker> stickersReceived) {
        this.stickersReceived = stickersReceived;
    }
}
