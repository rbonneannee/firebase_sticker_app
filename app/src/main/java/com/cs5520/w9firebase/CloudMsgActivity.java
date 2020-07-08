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
import com.cs5520.w9firebase.realtimedatabase.models.StickerAdapter;
import com.cs5520.w9firebase.realtimedatabase.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CloudMsgActivity extends AppCompatActivity {
    private static final String TAG = CloudMsgActivity.class.getSimpleName();

    private DatabaseReference database;
    private Spinner userSpinner;
    private Spinner stickerSpinner;
    private List<User> userList;
    private ArrayList<Sticker> stickerList;

    private StickerAdapter stickerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_msg);
        
        this.database = FirebaseDatabase.getInstance().getReference();

        this.userSpinner = (Spinner) findViewById(R.id.spinner_user);
        this.stickerSpinner = (Spinner) findViewById(R.id.spinner_sticker);
        this.userList = new ArrayList<User>();
        this.stickerList = new ArrayList<Sticker>();
        
        //This code looks complicated, but most of this is automatically generated methods.
        //Look at Line 61 of RealTimeDatabaseActivity in the Professor's firebase demo
        database.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                userList.add(user);
                Log.d(TAG, "onChildAdded userList =  " + userList.size());
                setUserSpinner();
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
        
        database.child("stickers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Sticker sticker = snapshot.getValue(Sticker.class);

                // Update Sticker object with corresponding drawable
                switch (sticker.getStickerName()) {
                    case "ice cream":
                        sticker.setStickerImageResId(R.drawable.noun_ice_cream);
                        break;
                    case "truck":
                        sticker.setStickerImageResId(R.drawable.noun_truck);
                        break;
                    case "smile":
                        sticker.setStickerImageResId(R.drawable.noun_smile);
                        break;
                    case "turtle":
                        sticker.setStickerImageResId(R.drawable.noun_turtle);
                        break;
                }

                stickerList.add(sticker);
                setStickerSpinner();
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
        Log.d(TAG, "IN setUserSpinner: userList =  " + userList.size());

        // Create an ArrayAdapter using the User array and a default spinner layout
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item, userList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        userSpinner.setAdapter(adapter);

        if (userList.size() == 0) {
            Log.d(TAG, "userList is empty =  " + userList.size());
        }

        for (User user : userList) {
            Log.d(TAG, "here" + user.toString());
        }

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
        this.stickerAdapter = new StickerAdapter(this, this.stickerList);
        this.stickerSpinner.setAdapter(stickerAdapter);
        this.stickerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void originalSetStickerSpinner() {

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
