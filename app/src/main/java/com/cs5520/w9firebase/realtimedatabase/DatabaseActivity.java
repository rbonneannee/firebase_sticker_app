package com.cs5520.w9firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseActivity extends AppCompatActivity {

    private static final String TAG = DatabaseActivity.class.getSimpleName();

    // Reference to Firebase Realtime Database
    private DatabaseReference mDatabase;
    private TextView username1;
    private TextView username2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // initialize elements with findViewById

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}
