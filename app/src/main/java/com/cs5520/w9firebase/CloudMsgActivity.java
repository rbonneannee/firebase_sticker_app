package com.cs5520.w9firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class CloudMsgActivity extends AppCompatActivity {
    private static final String TAG = CloudMsgActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_msg);

        // Process to get application's unique Firebase instance token
        Button button_getToken = (Button) findViewById(R.id.button_getToken);
        button_getToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if(!task.isSuccessful()) {
                                    Log.w(TAG, "getInstanceID failed", task.getException());
                                    return;
                                }

                                // Gets and displays unique app instance token
                                String token = task.getResult().getToken();
                                String msg = "InstanceID Token: " + token;
                                Log.d(TAG, msg);
                                Toast.makeText(CloudMsgActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



    }

}
