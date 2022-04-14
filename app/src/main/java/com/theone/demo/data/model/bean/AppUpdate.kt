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
class AppUpdate() :IApkUpdate,Parcelable {

    override fun isNewVersion(): Boolean = true

    override fun getAppVersionCode(): Int = 1

    override fun isForceUpdate(): Boolean  = false

    override fun getAppVersionName(): String = "1.0.0"

    override fun getAppApkUrl(): String = ""

    override fun getAppUpdateLog(): String = "更新内容：\n xxxxx"

    override fun getAppApkSize(): Long  = 0

}