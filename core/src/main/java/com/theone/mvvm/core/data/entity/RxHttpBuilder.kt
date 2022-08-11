package com.theone.mvvm.core.data.entity

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.theone.mvvm.base.appContext
import com.theone.mvvm.core.app.util.FileDirectoryManager
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.ssl.HttpsUtils
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

//  ┏┓　　　┏┓
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
/**
 * @author The one
 * @date 2021-07-12 09:52
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
data class RxHttpBuilder(
    var sslSocketFactory: SSLSocketFactory = HttpsUtils.getSslSocketFactory().sSLSocketFactory,
    var trustManager: X509TrustManager = HttpsUtils.getSslSocketFactory().trustManager,

    var isNeedCookie: Boolean = false,
    var cookiejar: PersistentCookieJar? =
        if (isNeedCookie) PersistentCookieJar(
            SetCookieCache(), SharedPrefsCookiePersistor(
                appContext
            )
        ) else null,
    var cookieFileName: String = "RxHttpCookie",
    var cookieFilePath: String = appContext.cacheDir.absolutePath,

    var cacheFileName: String = "RxHttCache",
    var cacheFilePath: String = cookieFilePath,
    var cacheMaxSize: Long = 1024 * 1024 * 10,
    var cacheMode: CacheMode = CacheMode.ONLY_NETWORK,
    var cacheValidTime: Long = Long.MAX_VALUE,

    var outTime: Long = 10,
    var readTime: Long = 10,
    var writeTime: Long = 10
)