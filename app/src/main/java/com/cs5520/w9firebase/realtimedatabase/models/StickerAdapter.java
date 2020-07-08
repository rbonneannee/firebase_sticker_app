package com.cs5520.w9firebase.realtimedatabase.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cs5520.w9firebase.R;

import java.util.ArrayList;

public class StickerAdapter extends ArrayAdapter<Sticker> {

    public StickerAdapter(Context context, ArrayList<Sticker> stickerList) {
        super(context, 0, stickerList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.sticker_spinner_row, parent, false
            );
        }

        ImageView imageViewSticker = convertView.findViewById(R.id.image_view_sticker);
        // TextView textViewName = convertView.findViewById(R.id.text_view_name);

        Sticker currentSticker = getItem(position);

        if (currentSticker != null) {
            imageViewSticker.setImageResource(currentSticker.getStickerImageResId());
            // textViewName.setText(currentSticker.getStickerName());
        }
        return convertView;
    }
}
