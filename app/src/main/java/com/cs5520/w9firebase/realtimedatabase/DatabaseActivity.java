package com.cs5520.w9firebase.realtimedatabase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cs5520.w9firebase.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseActivity extends AppCompatActivity {
  private static final String SERVER_KEY =
      "key=AAAAgmQuzxA:APA91bFW8VCokyXik3ngcV7mKS-UHUWrUqvE08pZOpy1AHszBih88Tvw8t0ciEc0BN3hGk_8810tB-yjx8zQVxhAjjxiYuLZPh91hsfgSarEN5Fn8ikLDfG2k6JZWGDwFg1rlRkwtXtV";

  // Reference to Firebase Realtime Database
  private DatabaseReference mDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_database);

    // initialize elements with findViewById

    mDatabase = FirebaseDatabase.getInstance().getReference();
  }
}
