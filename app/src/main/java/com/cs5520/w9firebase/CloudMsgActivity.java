package com.cs5520.w9firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs5520.w9firebase.realtimedatabase.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

public class CloudMsgActivity extends AppCompatActivity {
    private static final String TAG = CloudMsgActivity.class.getSimpleName();

    private DatabaseReference database;
    private Spinner spinner;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_msg);
        this.database = FirebaseDatabase.getInstance().getReference().child("users");

        this.spinner = (Spinner) findViewById(R.id.spinner);
        this.userList = new ArrayList<User>();

        // TODO: Replace hardcoded users with users in database
        User u0 = new User("Andrea");
        u0.setRegistrationToken("regA");
        User u1 = new User("Bebe");
        u1.setRegistrationToken("regB");
        User u2 = new User("Carmen");
        u2.setRegistrationToken("regC");
        User u3 = new User("Damien");
        u3.setRegistrationToken("regD");
        userList.add(u0);
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);

        // Create an ArrayAdapter using the User array and a default spinner layout
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item, userList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getSelectedItem();
                displayUserData(user);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


 /*
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
*/
    }

    public void getSelectedUser(View v) {
        User user = (User) spinner.getSelectedItem();
        displayUserData(user);
    }

    private void displayUserData(User user) {
        String username = user.getUsername();
        String regToken = user.getRegistrationToken();
        Integer numStickersSent = user.getNumStickersSent();

        String userData = "Username: " + username + "\nToken: " + regToken + "\n# Stickers Sent: "
                + numStickersSent;

        Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }

}
