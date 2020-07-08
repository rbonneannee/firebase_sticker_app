package com.cs5520.w9firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cs5520.w9firebase.realtimedatabase.models.Sticker;
import com.cs5520.w9firebase.realtimedatabase.models.StickerListAdapter;
import com.cs5520.w9firebase.realtimedatabase.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {

    private static final String TAG = InboxActivity.class.getSimpleName();

    private String currentUserToken;
    private DatabaseReference databaseReference;

    //TODO: remove stickerNameList
    // Array of stickers as strings and drawables
    private ArrayList<String> stickerNameList;

    private ArrayList<R.drawable> stickerPNGList;
    private ArrayList<Sticker> stickerObjList;

    // TODO: string adapter
    // Adapter to manage the items to display
    ArrayAdapter<String> adapter;
    StickerListAdapter stickerListAdapter;

    // Space to display items
    private ListView stickersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        this.currentUserToken = getIntent().getStringExtra("userToken");
        Log.d(TAG, "userToken = " + this.currentUserToken);

        this.databaseReference = FirebaseDatabase.getInstance().getReference();

        // TODO: remove string name list
        this.stickerNameList = new ArrayList<String>();

        this.stickerObjList = new ArrayList<Sticker>();

        stickerReceivedListener();
    }

    private void stickerReceivedListener() {
        this.databaseReference.child("users").child(currentUserToken).child("stickersReceived")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        String stickerName = snapshot.getValue(String.class);
                        stickerNameList.add(stickerName);
                        Log.d(TAG, "Sticker name: " + stickerName);
                        Log.d(TAG, "StickerList ct: " + stickerNameList.size());

                        stickerObjList.add(createSticker(stickerName));
                        displayStickers();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private Sticker createSticker(String stickerName) {
        Sticker sticker = new Sticker(stickerName);
        switch (stickerName) {
            case "ice cream":
                sticker.setImageURL("drawable://" + R.drawable.noun_ice_cream);
                break;
            case "truck":
                sticker.setImageURL("drawable://" + R.drawable.noun_truck);
                break;
            case "turtle":
                sticker.setImageURL("drawable://" + R.drawable.noun_turtle);
                break;
            case "smile":
                sticker.setImageURL("drawable://" + R.drawable.noun_smile);
                break;
        }
        return sticker;
    }

    private void displayStickers() {
        this.stickersListView = findViewById(R.id.listView_stickersReceived);

        // TODO: remove
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                this.stickerNameList);
        // KEEP
        this.stickerListAdapter = new StickerListAdapter(this, R.layout.adapter_image_listview, stickerObjList);
        this.stickersListView.setAdapter(stickerListAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}