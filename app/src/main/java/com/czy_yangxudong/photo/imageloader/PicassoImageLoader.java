package com.czy_yangxudong.photo.imageloader;

/**
 * 描    述：
 * 修订历史：
 */

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.yxd.imagepickers.loader.ImageLoader;
import com.yxd.imagepickers.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)//
                .load(Uri.fromFile(new File(path)))//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)//
                .load(Uri.fromFile(new File(path)))//
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);
    }

    @Override
    public void displayInternetImage(Activity activity, String path, ImageView imageView, int width, int height) {

    }

    @Override
    public void clearMemoryCache() {
    }
}
