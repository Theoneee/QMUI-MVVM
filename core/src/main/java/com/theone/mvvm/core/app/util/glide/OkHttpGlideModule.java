package com.theone.mvvm.core.app.util.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;


// 注意这个注解一定要加上，HttpGlideModule是自定义的名字
@GlideModule
public final class OkHttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // 注意这里用我们刚才现有的Client实例传入即可
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getOkHttpClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    /**
     * 添加拦截器
     */
    private  OkHttpClient getOkHttpClient() {
      return   RxHttpPlugins.newOkClientBuilder().addInterceptor(new GlideProgressInterceptor()).build();
    }
}
