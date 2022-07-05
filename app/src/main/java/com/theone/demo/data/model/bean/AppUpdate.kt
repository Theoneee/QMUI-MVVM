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
        "http://theone.0851zy.com/2022/04/13/97096d9c4094306a804f847f7c89d751.apk"

    override fun getAppUpdateLog(): String =
        "【新增1】短视频首页增加今日热门\n" +
                "【新增2】短视频首页增加今日热门\n" +
                "【新增3】短视频首页增加今日热门\n" +
                "【新增4】短视频首页增加今日热门\n" +
                "【新增5】短视频首页增加今日热门\n" +
                "【新增6】短视频首页增加今日热门\n" +
                "【新增7】短视频首页增加今日热门\n" +
                "【新增8】短视频首页增加今日热门\n" +
                "【新增9】短视频首页增加今日热门\n" +
                "【新增10】短视频首页增加今日热门\n"

    override fun getAppApkSize(): Long = 0

}