package com.fonxconn.android.workexample.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fonxconn.android.workexample.R;


/**
 * Created by Aaron on 2017/1/9.
 */

public class ImageLoader {
    private ImageLoader() {
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        //加载成功
        Glide.with(context).load(url).centerCrop().into(imageView);
        //加载失败,设置默认图片
        Glide.with(context).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
    }
}
