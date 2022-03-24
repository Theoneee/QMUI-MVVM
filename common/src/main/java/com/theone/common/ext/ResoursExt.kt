package com.theone.common.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

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
 * @date 2021-03-29 14:27
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

fun View.getColor(@ColorRes id:Int):Int{
    return getColor(context,id)
}

fun View.getDrawable(@DrawableRes id:Int):Drawable?{
    return getDrawable(context,id)
}

fun View.getString(@StringRes id:Int):String{
    return context.getString(id)
}

fun getDrawable(context: Context,@DrawableRes id:Int):Drawable?{
    return ContextCompat.getDrawable(context,id)
}

fun getColor(context: Context,@ColorRes id:Int):Int{
    return ContextCompat.getColor(context,id)
}

fun Fragment.getColor(@ColorRes id:Int):Int{
    return getColor(requireContext(),id)
}

fun Fragment.getDrawable(@DrawableRes id:Int):Drawable?{
    return getDrawable(requireContext(),id)
}

fun Fragment.getString(@StringRes id:Int):String{
    return requireContext().getString(id)
}

