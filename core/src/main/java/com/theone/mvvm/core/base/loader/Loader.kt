package com.theone.mvvm.core.base.loader

import android.view.View
import com.theone.mvvm.core.base.loader.callback.Callback

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
 * @date 2022-03-27 10:12
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class Loader private constructor(){

    companion object {

        val loader: Loader by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Loader()
        }

        fun getDefault() = loader

        fun beginBuilder() = Builder()

    }

    var builder: Builder? = null
        private set

    fun register(target:View):LoaderView{
       return LoaderView().register(target,builder)
    }

    class Builder {

        private val callbacks = mutableListOf<Class<out Callback>>()
        private var defaultCallback: Class<out Callback>? = null

        fun addCallback(callback: Class<out Callback>):Builder {
            callbacks.add(callback)
            return this
        }

        fun defaultCallback(default: Class<out Callback>?):Builder {
            defaultCallback = default
            return this
        }

        fun getCallbacks() = callbacks

        fun getDefaultCallback() = defaultCallback

        fun commit() {
            getDefault().builder = this
        }

    }

}