package com.theone.mvvm.core.app.util

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.theone.mvvm.core.data.entity.RxHttpBuilder
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import java.io.File
import java.util.concurrent.TimeUnit

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
 * @date 2021-07-12 09:50
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object RxHttpManager {

    private var mCacheFile: File? = null
    var cookieJar: ClearableCookieJar? = null

    fun init(builder: RxHttpBuilder = RxHttpBuilder()): RxHttpPlugins =
        with(builder) {
            mCacheFile = File(cacheFilePath, cacheFileName)
            val client = getDefaultClient()
            OkHttpUtils.initClient(client)
            RxHttpPlugins.init(client)
                .setCache(mCacheFile, cacheMaxSize, cacheMode, cacheValidTime)
        }


    fun clearCache() {
        mCacheFile?.deleteOnExit()
        cookieJar?.clear()
    }

    private fun RxHttpBuilder.getDefaultClient(): OkHttpClient = OkHttpClient().newBuilder().apply {
        if (isNeedCookie) {
            this@RxHttpManager.cookieJar = cookiejar
            cookieJar(cookiejar)
        }
        connectTimeout(outTime, TimeUnit.SECONDS)
        readTimeout(readTime, TimeUnit.SECONDS)
        writeTimeout(writeTime, TimeUnit.SECONDS)
        //添加信任证书
        sslSocketFactory(sslSocketFactory, trustManager)
        //忽略host验证
        hostnameVerifier { _, _ -> true }
    }.build()

}