package com.cs5520.w9firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs5520.w9firebase.realtimedatabase.models.Sticker;
import com.cs5520.w9firebase.realtimedatabase.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CloudMsgActivity extends AppCompatActivity {
    private static final String TAG = CloudMsgActivity.class.getSimpleName();

    private DatabaseReference database;
    private Spinner userSpinner;
    private Spinner stickerSpinner;
    private List<User> userList;
    private List<Sticker> stickerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_msg);
        this.database = FirebaseDatabase.getInstance().getReference().child("users");

        this.userSpinner = (Spinner) findViewById(R.id.spinner_user);
        this.stickerSpinner = (Spinner) findViewById(R.id.spinner_sticker);
        this.userList = new ArrayList<User>();
        this.stickerList = new ArrayList<Sticker>();

        database = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference userRef = database.getReference("users");

        //This code looks complicated, but most of this is automatically generated methods.
        //Look at Line 61 of RealTimeDatabaseActivity in the Professor's firebase demo
        database.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                userList.add(user);
            }

            //TODO: Find some way to find index. Updating currently not implemented
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                //userList.set()
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userList.remove(user);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled:" + error);
            }
        });

//        User u0 = new User("Andrea");
//        u0.setRegistrationToken("regA");
//        User u1 = new User("Bebe");
//        u1.setRegistrationToken("regB");
//        User u2 = new User("Carmen");
//        u2.setRegistrationToken("regC");
//        User u3 = new User("Damien");
//        u3.setRegistrationToken("regD");
//        userList.add(u0);
//        userList.add(u1);
//        userList.add(u2);
//        userList.add(u3);


        // TODO: Replace hardcoded stickers with stickers in database
        database.child("stickers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sticker sticker = snapshot.getValue(Sticker.class);
                stickerList.add(sticker);
            }

            //TODO: Find some way to find index. Updating currently not implemented
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sticker sticker = snapshot.getValue(Sticker.class);
                //stickerList.set(sticker);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                userList.remove(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled:" + error);
            }
        });

//        Sticker s0 = new Sticker();
//        s0.setStickerName("doll");
//        Sticker s1 = new Sticker();
//        s1.setStickerName("balloon");
//        Sticker s2 = new Sticker();
//        s2.setStickerName("smile");
//        Sticker s3 = new Sticker();
//        s3.setStickerName("rain cloud");
//        stickerList.add(s0);
//        stickerList.add(s1);
//        stickerList.add(s2);
//        stickerList.add(s3);

        //TODO: Set default spinner value. Currently they start blank
        setUserSpinner();
        setStickerSpinner();


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



    // TODO: Low priority - Create one setSpinner method that can handle both User and
    //  Sticker objects
    // FOR USER SPINNER
    private void setUserSpinner() {

        // Create an ArrayAdapter using the User array and a default spinner layout
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item, userList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        userSpinner.setAdapter(adapter);

        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getSelectedItem();
                displayUserData(user);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getSelectedUser(View v) {
        User user = (User) userSpinner.getSelectedItem();
        displayUserData(user);
    }

    private void displayUserData(User user) {
        String username = user.getUsername();
        String regToken = user.getRegistrationToken();
        Integer numStickersSent = user.getNumStickersSent();

        String data = "Username: " + username + "\nToken: " + regToken + "\n# Stickers Sent: "
                + numStickersSent;
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    // FOR STICKER SPINNER
    private void setStickerSpinner() {

        // Create an ArrayAdapter using the User array and a default spinner layout
        ArrayAdapter<Sticker> adapter = new ArrayAdapter<Sticker>(this, android.R.layout.simple_spinner_item, stickerList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        stickerSpinner.setAdapter(adapter);

        stickerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sticker sticker = (Sticker) parent.getSelectedItem();
                displayStickerData(sticker);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void displayStickerData(Sticker sticker) {
        String stickerName = sticker.getStickerName();

        String data = "Sticker: " + stickerName;
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    public void getSelectedSticker(View v) {
        Sticker sticker = (Sticker) stickerSpinner.getSelectedItem();
        displayStickerData(sticker);
    }


}
