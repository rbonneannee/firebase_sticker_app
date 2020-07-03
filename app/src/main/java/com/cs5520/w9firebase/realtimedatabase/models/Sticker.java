package com.cs5520.w9firebase.realtimedatabase.models;

import android.graphics.drawable.Icon;

import java.util.Date;

public class Sticker {

    private Icon icon;
    private String stickerName;
    private Date dateSent;

    public Sticker(){
        this.dateSent = new Date();
    }


}
