package com.cs5520.w9firebase.realtimedatabase.models;

public class Message {

    private String senderToken;
    private String receiverToken;
    private String body;

    public Message() {
    }

    public String getSenderToken() {
        return senderToken;
    }

    public String getReceiverToken() {
        return receiverToken;
    }

    public String getBody() {
        return body;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public void setReceiverToken(String receiverToken) {
        this.receiverToken = receiverToken;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
