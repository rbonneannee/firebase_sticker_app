package com.cs5520.w9firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cs5520.w9firebase.realtimedatabase.models.Message;
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
    private User currentUser;
    private String currentUserToken;
    private TextView username;
    private TextView stickersSent;

    private StickerAdapter stickerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_msg);

        this.database = FirebaseDatabase.getInstance().getReference();
        this.currentUserToken = getIntent().getStringExtra("userToken");
        this.userSpinner = (Spinner) findViewById(R.id.spinner_user);
        this.stickerSpinner = (Spinner) findViewById(R.id.spinner_sticker);
        this.userList = new ArrayList<User>();
        this.stickerList = new ArrayList<Sticker>();
        stickersSent = (TextView) findViewById(R.id.stickers_sent);


        // Send message
        findViewById(R.id.button_testMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                Sticker message = (Sticker) stickerSpinner.getSelectedItem();
                User recipient = (User) userSpinner.getSelectedItem();
                msg.setBody(message.getStickerName());
                msg.setSenderToken(currentUserToken);
                msg.setReceiverToken(recipient.getRegistrationToken());
                database.child("messages").push().setValue(msg);

                //TODO increment stickers sent by 1
                currentUser.incrementStickersSent();
                database.child("users").child(currentUserToken).setValue(currentUser);

                confirmSent();
            }
        });

        // TODO use ArrayAdapter.notifyDataSetChanged() instead of creating new instances
        // TODO for readability, make a separate class implementing ChildEventListener
        // Listeners for changes in "users" branch of database
        database.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getRegistrationToken().equals(currentUserToken)) {
                    Toast.makeText(CloudMsgActivity.this, user.getNumStickersSent().toString(), Toast.LENGTH_LONG);
                    stickersSent.setText(String.valueOf(user.getNumStickersSent()));
                    currentUser = user;
                }
                userList.add(user);
                Log.d(TAG, "onChildAdded userList =  " + userList.size());
                setUserSpinner();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getRegistrationToken().equals(currentUserToken)) {
                    stickersSent.setText(String.valueOf(user.getNumStickersSent()));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userList.remove(user);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // No action
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled:" + error);
            }
        });

        // Listeners for changes in "stickers" branch of database
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

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // No action
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // No action
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
    }

    private void confirmSent() {
        String confirm = "You sent a " + getSelectedSticker().getStickerName() + " sticker to " +
                getSelectedUser().getUsername() + "!";
        Toast.makeText(this, confirm, Toast.LENGTH_LONG).show();
    }

    // TODO make a higher-order function to lift up what's common between the set spinner methods
    private void setUserSpinner() {
        Log.d(TAG, "IN setUserSpinner: userList =  " + userList.size());

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
                // No action
            }
        });
    }

    public User getSelectedUser() {
        return (User) userSpinner.getSelectedItem();
    }

    private void displayUserData(User user) {
        String username = user.getUsername();
        String regToken = user.getRegistrationToken();
        Integer numStickersSent = user.getNumStickersSent();

        String data = "Username: " + username + "\nToken: " + regToken + "\n# Stickers Sent: "
                + numStickersSent;
        Log.d(TAG, data);
    }

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
                // No action
            }
        });
    }

    private void displayStickerData(Sticker sticker) {
        String stickerName = sticker.getStickerName();
        String data = "Sticker: " + stickerName;
        Log.d(TAG, data);
    }

    public Sticker getSelectedSticker() {
        return (Sticker) stickerSpinner.getSelectedItem();
    }
}
