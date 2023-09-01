package com.theone.demo.data.model.bean

import android.os.Parcel
import android.os.Parcelable
import com.theone.mvvm.core.base.callback.IApkUpdate
import kotlinx.parcelize.Parcelize

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
 * @date 2022-04-14 09:15
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
@Parcelize
class AppUpdate : IApkUpdate, Parcelable {

    override fun isNewVersion(): Boolean = true

    override fun getAppVersionCode(): Int = 1

    override fun isForceUpdate(): Boolean = false

    override fun getAppVersionName(): String = "1.0.0"

    override fun getAppApkUrl(): String =
        "https://cos.pgyer.com/08e403833045055e3bf6ebf9e41d7b85.apk?sign=5ecc4077f22b35569f2e3f3611cea080&sign2=71764402c1ba6dcf30fa1d7533136a7b&t=1693535366&response-content-disposition=attachment%3Bfilename%3D%E9%9B%AA%E9%9B%AA%E9%9B%AA_3.7.1.apk"

    override fun getAppUpdateLog(): String =
        "【新增1】短视频首页增加今日热门\n" +
                "【新增2】短视频首页增加今日热门\n" +
                "【新增3】短视频首页增加今日热门\n" +
                "【新增4】短视频首页增加今日热门\n" +
                "【新增5】短视频首页增加今日热门\n" +
                "【新增6】短视频首页增加今日热门\n" +
                "【新增7】短视频首页增加今日热门\n" +
                "【新增8】短视频首页增加今日热门\n" +
                "【新增9】短视频首页增加今日热门\n"

    override fun getAppApkSize(): Long = 0

}