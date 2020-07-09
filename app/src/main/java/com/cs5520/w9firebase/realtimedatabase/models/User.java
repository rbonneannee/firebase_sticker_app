package com.cs5520.w9firebase.realtimedatabase.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String registrationToken;
    private Integer numStickersSent;
    private HashMap<String, String> stickersReceived;

    public User() {
        stickersReceived = new HashMap<>();
    }

    public User(String username) {
        this();
        this.username = username;
        this.numStickersSent = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<String, String> getStickersReceived() {
        return stickersReceived;
    }

    public void setStickersReceived(HashMap<String, String> stickersReceived) {
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

    public void incrementStickersSent() {
        this.numStickersSent++;
    }

    @NonNull
    @Override
    public String toString() {
        return this.username;
    }
}
