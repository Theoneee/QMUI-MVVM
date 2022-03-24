package com.theone.common.binding

import android.widget.*
import androidx.databinding.BindingAdapter
import com.theone.common.util.DateFormatUtils
import com.theone.common.widget.TheSelectImageView

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
 * @date 2022-01-21 15:17
 * @describe Common内的组件相关的Binding
 * @email 625805189@qq.com
 * @remark
 */
object CommonBindingAdapter {

    /**
     * 格式化时间
     * @param textView TextView
     * @param longDate Long 格式的时间
     * @param type Int? 类型
     */
    @BindingAdapter(value = ["longDate", "type"], requireAll = false)
    @JvmStatic
    fun formatDate(
        textView: TextView,
        longDate: Long,
        type: Int?
    ) {
        textView.text = DateFormatUtils.formatTimeStampString(
            textView.context,
            longDate,
            type?:DateFormatUtils.FORMAT_TYPE_PERSONAL_FOOTPRINT
        )
    }

    @BindingAdapter(value = ["selectChangedListener", "select"], requireAll = false)
    fun setSelectImageListener(
        selectImageView: TheSelectImageView,
        selectChangedListener: TheSelectImageView.OnSelectChangedListener? = null,
        select: Boolean = false
    ) {
        selectImageView.run {
            onSelectChangedListener = selectChangedListener
            toggleSelect(select)
        }
    }


}