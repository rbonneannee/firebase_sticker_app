package com.cs5520.w9firebase.realtimedatabase.models;

import java.util.ArrayList;

public class User {
    private String username;
    private String registrationToken;
    private Integer numStickersSent;
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

    public ArrayList<Sticker> getStickersReceived() {
        return stickersReceived;
    }

    public void setStickersReceived(ArrayList<Sticker> stickersReceived) {
        this.stickersReceived = stickersReceived;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public Integer getNumStickersSent() {
        return numStickersSent;
    }

    public void setNumStickersSent(Integer numStickersSent) {
        this.numStickersSent = numStickersSent;
    }
}
