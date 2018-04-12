package com.czy_yangxudong.photo.imageloader;

/**
 * 描    述：
 * 修订历史：
 */

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.yxd.imagepickers.loader.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.io.File;

public class UILImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        ImageSize size = new ImageSize(width, height);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(Uri.fromFile(new File(path)).toString(), imageView, size);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        ImageSize size = new ImageSize(width, height);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(Uri.fromFile(new File(path)).toString(), imageView, size);
    }

    @Override
    public void clearMemoryCache() {
    }
}
