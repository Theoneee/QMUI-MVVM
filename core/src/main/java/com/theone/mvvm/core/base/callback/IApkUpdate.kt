package com.theone.mvvm.core.base.callback

import android.os.Parcelable

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
 * @date 2021-05-08 11:29
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
interface IApkUpdate:Parcelable {

    fun isNewVersion(): Boolean

    //获取版本号
    fun getAppVersionCode(): Int

    //是否强制更新 0 不强制更新 1
    fun isForceUpdate(): Boolean

    //版本号 描述作用
    fun getAppVersionName(): String?

    //新安装包的下载地址
    fun getAppApkUrl(): String

    //更新日志
    fun getAppUpdateLog(): String?

    //安装包大小 单位字节
    fun getAppApkSize(): Long

}