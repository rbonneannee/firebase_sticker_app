package com.cs5520.w9firebase.realtimedatabase.models;

import android.graphics.drawable.Icon;

import androidx.annotation.NonNull;

import java.util.Date;

public class Sticker {

    private Icon icon;
    private String stickerName;

    public Sticker(){}

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    @NonNull
    @Override
    public String toString() {
        return this.stickerName;
    }
}
