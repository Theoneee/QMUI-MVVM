package com.theone.mvvm.core.app.util

import com.theone.mvvm.core.app.ext.customParseException
import com.theone.mvvm.core.data.entity.RxHttpBuilder
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

    private var mBuilder: RxHttpBuilder? = null
    private var mPlugins: RxHttpPlugins? = null

    fun init(builder: RxHttpBuilder = RxHttpBuilder()) =
        with(builder) {
            mBuilder = builder
            RxHttpPlugins.init(getDefaultClient()).apply {
                mPlugins = this
                setCache(
                    File(cacheFilePath, cacheFileName),
                    cacheMaxSize,
                    cacheMode,
                    cacheValidTime
                )
            }
        }

    fun clearCookieCache() {
        mBuilder?.cookiejar?.clear()
    }

    fun getRxHttpBuilder() = mBuilder
    fun getRxHttpPlugins() = mPlugins

    private fun RxHttpBuilder.getDefaultClient(): OkHttpClient = OkHttpClient().newBuilder().apply {
        cookiejar?.let {
            cookieJar(it)
        }
        connectTimeout(outTime, TimeUnit.SECONDS)
        readTimeout(readTime, TimeUnit.SECONDS)
        writeTimeout(writeTime, TimeUnit.SECONDS)
        //添加信任证书
        sslSocketFactory(sslSocketFactory, trustManager)
        //忽略host验证
        hostnameVerifier { _, _ -> true }
    }.build()

    /**
     * 自定义错误解析
     * @param parse Function1<Throwable, String?>?
     */
    fun initCustomExceptionParse(parse: ((Throwable) -> String?)? = null) {
        customParseException = parse
    }

}