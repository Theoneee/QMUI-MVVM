package com.theone.mvvm.core.app.util

import android.app.Application
import com.tencent.mmkv.MMKV


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
 * @date 2021/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object MMKVUtil {

    private var mMMKV:MMKV?=null

    fun init(application:Application,id: String? = "mmkv"){
        MMKV.initialize(application.filesDir.absolutePath+"/$id")
        mMMKV =  MMKV.mmkvWithID(id)
    }

    private fun getMMKV():MMKV {
      if(null == mMMKV){
          TODO("MMKVUtil需要在Application里调用init方法初始化")
      }
      return mMMKV!!
    }

    fun putBoolean(key: String, value: Boolean) {
        getMMKV().encode(key, value)
    }

    fun getBoolean(key: String, default: Boolean): Boolean =
        getMMKV().decodeBool(key, default)

    fun getBoolean(key: String): Boolean =
        getBoolean(key, false)

    fun putString(key: String, value: String?) {
        getMMKV().encode(key, value)
    }

    fun getString(key: String, default: String?): String? =
        getMMKV().decodeString(key, default)

    fun getString(key: String): String? =
        getString(key, null)

    fun putInt(key: String, value: Int) {
        getMMKV().encode(key, value)
    }

    fun getInt(key: String, default: Int): Int =
        getMMKV().decodeInt(key, default)

    fun clear(){
        getMMKV().clear()
    }

    fun clearAll(){
        getMMKV().clearAll()
    }

    fun clearMemoryCache(){
        getMMKV().clearMemoryCache()
    }
}

