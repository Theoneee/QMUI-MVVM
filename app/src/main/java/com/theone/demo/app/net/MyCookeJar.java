package com.theone.demo.app.net;//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @author The one
 * @date 2022-01-20 16:13
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class MyCookeJar implements ClearableCookieJar {

    private final CookieCache cache;
    private final CookiePersistor persistor;

    public MyCookeJar(Context context) {
        this.cache = new SetCookieCache();
        this.persistor = new SharedPrefsCookiePersistor(context);
        this.cache.addAll(persistor.loadAll());
    }

    @Override
    synchronized public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        Log.e("saveFromResponse", url.toString());
        if(url.toString().contains(Url.LOGIN)){
            cache.addAll(cookies);
            persistor.saveAll(filterPersistentCookies(cookies));
        }
    }

    private static List<Cookie> filterPersistentCookies(List<Cookie> cookies) {
        List<Cookie> persistentCookies = new ArrayList<>();
        for (Cookie cookie : cookies) {
            Log.e("saveFromResponse", "filterPersistentCookies: " + cookie.toString());
            persistentCookies.add(cookie);
        }
        return persistentCookies;
    }

    @Override
    synchronized public List<Cookie> loadForRequest(HttpUrl url) {
        Log.e("loadForRequest", url.toString() );
        List<Cookie> validCookies = new ArrayList<>();
        for (Iterator<Cookie> it = cache.iterator(); it.hasNext(); ) {
            Cookie currentCookie = it.next();
            if (currentCookie.matches(url)) {
                Log.e("loadForRequest", " matches "+currentCookie.toString() );
                validCookies.add(currentCookie);
            }
        }
        return validCookies;
    }

    @Override
    synchronized public void clearSession() {
        cache.clear();
        cache.addAll(persistor.loadAll());
    }

    @Override
    synchronized public void clear() {
        cache.clear();
        persistor.clear();
    }
}
