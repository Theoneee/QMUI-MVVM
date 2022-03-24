package com.theone.mvvm.core.app.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;


/**
 * @author The one
 * @date 2018/8/7 0007
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GlideUtil {

    public static void load(Context context, Object url, ImageView imageView) {
        load(context, url, imageView, getDefaultOptions());
    }

    public static void load(Context context, Object url, ImageView imageView, RequestOptions options) {
        load(context, url, imageView, options, null);
    }


    public static void loadImageAsBitmap(Context context, Object path) {
        loadImageAsBitmap(context, path, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

            }
        });
    }

    public static void loadImageAsBitmap(Context context, Object path, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).
                asBitmap().
                load(path).
                into(simpleTarget);
    }

    public static void loadImageWithProgress(Context context, Object url, ImageView imageView, IGlideProgressListener listener) {
        load(context, url, imageView, getDefaultOptions(), listener);
    }

    public static void loadImageWithProgress(Context context, Object url, ImageView imageView, RequestOptions options, IGlideProgressListener listener) {
        load(context, url, imageView, options, listener);
    }


    public static void load(Context context, final Object url, ImageView imageView, RequestOptions options, IGlideProgressListener listener) {
        if (null == url) return;
        if (null == context) return;
        if (null == imageView) return;
        if (null != listener) {
            GlideProgressInterceptor.addListener(url, listener);
        }

        Glide.with(context)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())// 渐入渐出效果
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (null != listener) {
                            listener.onProgress(0,false);
                            GlideProgressInterceptor.removeListener(url);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (null != listener) {
                            listener.onProgress(0,true);
                            GlideProgressInterceptor.removeListener(url);
                        }
                        return false;
                    }
                })
                .apply(options)
                .into(imageView);
    }

    public static RequestOptions getDefaultOptions() {
        return new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }
}
