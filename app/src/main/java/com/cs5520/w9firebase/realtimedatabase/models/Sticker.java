package com.cs5520.w9firebase.realtimedatabase.models;

import android.graphics.drawable.Icon;

import androidx.annotation.NonNull;

import java.util.Date;

public class Sticker {

    private String stickerName;
    private int stickerImageResId;

    public Sticker(){}

    public Sticker(String stickerName, int stickerImageResId) {
        this.stickerName = stickerName;
        this.stickerImageResId = stickerImageResId;
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

    @NonNull
    @Override
    public String toString() {
        return this.stickerName;
    }
}
