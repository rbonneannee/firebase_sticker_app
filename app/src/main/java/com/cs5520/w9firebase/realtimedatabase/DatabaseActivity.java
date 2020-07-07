package com.cs5520.w9firebase.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cs5520.w9firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class DatabaseActivity extends AppCompatActivity {
    private static final String SERVER_KEY = "key=AAAAgmQuzxA:APA91bFW8VCokyXik3ngcV7mKS-UHUWrUqvE08pZOpy1AHszBih88Tvw8t0ciEc0BN3hGk_8810tB-yjx8zQVxhAjjxiYuLZPh91hsfgSarEN5Fn8ikLDfG2k6JZWGDwFg1rlRkwtXtV";

    private static final String TAG = DatabaseActivity.class.getSimpleName();

    // Reference to Firebase Realtime Database
    private DatabaseReference mDatabaseRootRef = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
    }
}
