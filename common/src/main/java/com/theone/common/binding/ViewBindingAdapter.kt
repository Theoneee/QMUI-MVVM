package com.theone.common.binding

import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.databinding.BindingAdapter

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

    @BindingAdapter(value = ["gone"], requireAll = false)
    @JvmStatic
    @Deprecated(message = "",ReplaceWith("使用goneText替代"))
    fun viewGone(view: View, text: String?) {
        view.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
    }

    @BindingAdapter(value = ["gone","goneText"], requireAll = false)
    @JvmStatic
    fun viewGone(view: View, gone: Boolean?,goneText:String?) {
        view.visibility = if (gone == true || TextUtils.isEmpty(goneText)) View.GONE else View.VISIBLE
    }

    @BindingAdapter(value = ["invisible"], requireAll = false)
    @JvmStatic
    @Deprecated(message = "", ReplaceWith("使用invisibleText替代"))
    fun viewInvisible(view: View, text: String?) {
        view.visibility = if (TextUtils.isEmpty(text)) View.INVISIBLE else View.VISIBLE
    }

    @BindingAdapter(value = ["invisible","invisibleText"], requireAll = false)
    @JvmStatic
    fun viewInvisible(view: View, invisible: Boolean = false,invisibleText:String?=null) {
        view.visibility = if (invisible || TextUtils.isEmpty(invisibleText)) View.INVISIBLE else View.VISIBLE
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