package com.cs5520.w9firebase.realtimedatabase.models;

import android.graphics.drawable.Icon;

import java.util.Date;

public class Sticker {

    private Icon icon;
    private String stickerName;
    private User sender;
    private Date dateSent;

    public Sticker(){
        this.dateSent = new Date();
    }

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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}
