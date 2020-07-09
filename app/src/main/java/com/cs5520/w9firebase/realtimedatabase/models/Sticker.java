package com.cs5520.w9firebase.realtimedatabase.models;

import android.graphics.drawable.Icon;

import androidx.annotation.NonNull;

import java.util.Date;

// TODO update rules so that sticker collection can't be edited
public class Sticker {

    private String stickerName;
    private int stickerImageResId;
    private String imageURL;

    public Sticker(){}

    public Sticker(String stickerName, int stickerImageResId) {
        this.stickerName = stickerName;
        this.stickerImageResId = stickerImageResId;
    }

    public Sticker (String stickerName) {
        this.stickerName = stickerName;
    }

    public Sticker (String stickerName, String imageURL) {
        this.stickerName = stickerName;
        this.imageURL = imageURL;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public int getStickerImageResId() {
        return stickerImageResId;
    }

    public void setStickerImageResId(int stickerImageResId) {
        this.stickerImageResId = stickerImageResId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public String toString() {
        return this.stickerName;
    }
}
