/*
Resources:
https://stuff.mit.edu/afs/sipb/project/android/docs/training/improving-layouts/smooth-scrolling.html#:~:text=The%20key%20to%20a%20smoothly,app%2C%20you%20can%20enable%20StrictMode%20.
https://www.youtube.com/watch?v=SApBLHIpH8A
https://www.stacktips.com/tutorials/android/universal-image-loader-library-in-android
 */

package com.cs5520.w9firebase.realtimedatabase.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cs5520.w9firebase.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class StickerListAdapter extends ArrayAdapter<Sticker> {
    private int resource;
    private int lastPosition = -1;

    static class ViewHolder {
        ImageView icon;
    }



    public StickerListAdapter(Context context, int resource, ArrayList<Sticker> stickerObjList) {
        super(context, resource, stickerObjList);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        setupImageLoader();
        return initView(position, convertView, parent);
    }


    private View initView(int position, View convertView, ViewGroup parent) {
        // Create the sticker
        Sticker currentSticker = getItem(position);

        String imgURL = null;
        if (currentSticker != null) {
            imgURL = currentSticker.getImageURL();
        }

        // View to show animation
        final View result;

        // Viewholder object
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);

            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.linearLayout_imageView_sticker);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        result = convertView;

        Animation animation = AnimationUtils.loadAnimation(super.getContext(),
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        ImageLoader imageLoader = ImageLoader.getInstance();
        int defaultImg = super.getContext().getResources().getIdentifier("@drawable/image_failed", null, super.getContext().getPackageName());
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImg)
                .showImageOnFail(defaultImg)
                .showImageOnLoading(defaultImg).build();

        //download and display image from url
        imageLoader.displayImage(imgURL, holder.icon, options);

        return convertView;
    }

    private void setupImageLoader() {
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                super.getContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
