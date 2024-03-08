package com.theone.common.binding

import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.theone.common.ext.notNull

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
 * @date 2022-01-21 15:24
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object ViewBindingAdapter {

    @BindingAdapter(value = ["gone"])
    @JvmStatic
    fun viewGone(view: View, gone: Boolean) {
        view.visibility = if (gone) View.GONE else View.VISIBLE
    }

    @BindingAdapter(value = ["goneText"])
    @JvmStatic
    fun viewGoneText(view: View,goneText:String?) {
        view.visibility = if (TextUtils.isEmpty(goneText)) View.GONE else View.VISIBLE
    }

    @BindingAdapter(value = ["invisible"])
    @JvmStatic
    fun viewInvisible(view: View, invisible: Boolean ) {
        view.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
    }

    @BindingAdapter(value = ["invisibleText"])
    @JvmStatic
    fun viewInvisibleText(view: View,invisibleText:String?) {
        view.visibility = if (TextUtils.isEmpty(invisibleText)) View.INVISIBLE else View.VISIBLE
    }

    @BindingAdapter(value = ["checkChangeListener"])
    @JvmStatic
    fun checkChange(checkbox: CheckBox, listener: CompoundButton.OnCheckedChangeListener) {
        checkbox.setOnCheckedChangeListener(listener)
    }

    @BindingAdapter(value = ["showPwd"])
    @JvmStatic
    fun showPwd(view: EditText, boolean: Boolean) {
        if (boolean) {
            view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            view.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        view.setSelection(view.text.length)
    }

}