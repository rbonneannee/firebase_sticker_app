package com.cs5520.w9firebase.realtimedatabase.models;

import com.google.firebase.database.IgnoreExtraProperties;

public class Message {
    public String username; // TODO foreign key to users table
    public String content; // TODO character limit maybe

    public Message() {
    }

    public Message(String username, String content) {
        this.username = username;
        this.content = content;
    }



}
