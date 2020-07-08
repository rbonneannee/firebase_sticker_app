package com.cs5520.w9firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InboxActivity extends AppCompatActivity {

    private String currentUserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentUserToken = getIntent().getStringExtra("userToken");
        setContentView(R.layout.activity_inbox);
    }
}