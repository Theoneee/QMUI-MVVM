package com.theone.demo.app.util

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.theone.demo.data.model.bean.UserInfo
import com.theone.mvvm.core.app.util.MMKVUtil
import com.theone.mvvm.core.app.util.RxHttpManager
import rxhttp.RxHttpPlugins


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
 * @date 2021/3/11 0011
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object CacheUtil {

    private const val HISTORY: String = "search_history"
    private const val ANIMATION: String = "animation_type"
    private const val LAUNCHER: String = "launcher_mode"
    private const val USER: String = "user"

    fun isLogin(): Boolean = null != getUser()

    fun loginOut() {
        RxHttpManager.clearCookieCache()
        //RxHttpPlugins.getCache().removeAll()
        setUser(null)
    }

    fun setUser(userInfo: UserInfo?) {
        val user = if (null != userInfo) Gson().toJson(userInfo) else ""
        MMKVUtil.putString(USER, user)
    }

    fun getUser(): UserInfo? {
        val userStr = MMKVUtil.getString(USER)
        return if (userStr.isNullOrEmpty())
            null
        else
            Gson().fromJson(userStr, UserInfo::class.java)
    }

    private const val FIRST: String = "app_first"

    fun isFirst(): Boolean = MMKVUtil.getBoolean(FIRST, true)

    fun isEnterApp() {
        MMKVUtil.putBoolean(FIRST, false)
    }

    /**
     * 获取搜索历史缓存数据
     */
    fun getSearchHistoryData(): ArrayList<String> {
        val searchCacheStr = MMKVUtil.getString(HISTORY)
        if (!TextUtils.isEmpty(searchCacheStr)) {
            return Gson().fromJson(
                searchCacheStr
                , object : TypeToken<ArrayList<String>>() {}.type
            )
        }
        return arrayListOf()
    }

    /**
     * 设置搜索历史数据
     */
    fun setSearchHistoryData(searchResponseStr: String) {
        MMKVUtil.putString(HISTORY, searchResponseStr)
    }

    /**
     * 获取列表动画类型
     */
    fun getAnimationType(): Int = MMKVUtil.getInt(ANIMATION, 0)

    /**
     * 设置类表动画类型
     */
    fun setAnimationType(type: Int) {
        MMKVUtil.putInt(ANIMATION, type)
    }

    /**
     * 是否开启启动页文字
     */
    fun isOpenLauncherText(): Boolean = MMKVUtil.getBoolean(LAUNCHER, false)

    /**
     * 设置启动页文字开关
     * @param open Boolean
     */
    fun setLauncherText(open: Boolean) {
        MMKVUtil.putBoolean(LAUNCHER, open)
    }

}