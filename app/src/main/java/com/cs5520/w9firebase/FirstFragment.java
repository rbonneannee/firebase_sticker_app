package com.cs5520.w9firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cs5520.w9firebase.realtimedatabase.models.Sticker;
import com.cs5520.w9firebase.realtimedatabase.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;

// TODO do some work on other threads (call getToken from another thread, bc it's blocking)
public class FirstFragment extends Fragment {

    private static final String TAG = FirstFragment.class.getSimpleName();

    private DatabaseReference mDatabase;
    private TextInputEditText mUsernameFld;
    private User mUser;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.mUsernameFld = view.findViewById(R.id.inputField_username);

        // Navigation to second fragment
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        // Navigation to cloud msg activity; creates/saves user to DB
        view.findViewById(R.id.button_db_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createUser(view)) {
                    writeUser();
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_cloudMsgActivity);
                }
            }
        });
    }

    /**
     * Validates username and creates User object.
     * @param v a View to display validation messages to user
     */
    private boolean createUser(View v) {
        String username = mUsernameFld.getText().toString();
        if (username.isEmpty()) {
            Snackbar.make(v, "Error: Username field is blank", Snackbar.LENGTH_LONG)
                    .setAction("Error", null).show();
        } else if (username.contains("\n") || username.contains(" ")){
            Snackbar.make(v, "Error: Username cannot have whitespaces or new lines", Snackbar.LENGTH_LONG)
                    .setAction("Error", null).show();
        } else {
            Snackbar.make(v, "Username updated " + username, Snackbar.LENGTH_LONG)
                    .setAction("Confirmation", null).show();
            this.mUser = new User(username);
            return true;
        }
        return false;
    }

    /**
     * Saves user to database.
     */
    private void writeUser() {
        // TODO move to another thread?
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
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

                // Update and save user to database -
                mUser.setRegistrationToken(token);
                mUser.setNumStickersSent(0);

                // users are identified by token
                mDatabase.child("users").child(mUser.getRegistrationToken()).setValue(mUser);
            }
        });
    }
}
