package com.theone.common.binding

import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.widget.*
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import com.theone.common.R
import com.theone.common.ext.YYYY_MM_DD_HH_MM_SS
import com.theone.common.ext.formatLong
import com.theone.common.ext.getPriceSpannableString
import com.theone.common.util.DateFormatUtils
import com.theone.common.widget.TheSelectImageView
import com.theone.common.widget.edittext.filter.SpaceFilter
import com.theone.common.widget.edittext.filter.StringFilter

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
            type ?: DateFormatUtils.FORMAT_TYPE_PERSONAL_FOOTPRINT
        )
    }


    /**
     * 格式化时间
     * @param textView TextView
     * @param longDate Long 格式的时间
     * @param type Int? 类型
     */
    @BindingAdapter(value = ["date", "type"], requireAll = false)
    @JvmStatic
    fun formatDate(
        textView: TextView,
        date: String?,
        type: Int?
    ) {
        if (!TextUtils.isEmpty(date)) {
            date!!.formatLong(YYYY_MM_DD_HH_MM_SS)?.let {
                textView.text = DateFormatUtils.formatTimeStampString(
                    textView.context,
                    it,
                    type ?: DateFormatUtils.FORMAT_TYPE_PERSONAL_FOOTPRINT
                )
            }
        }
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

    /**
     * 添加过滤器
     * @param editText EditText
     * @param inputDigits String 允许的字符串
     * @param inputSpace Boolean 是否允许输入空格
     */
    @BindingAdapter(value = ["inputDigits", "inputSpace"], requireAll = false)
    @JvmStatic
    fun editTextFilter(
        editText: EditText,
        inputDigits: String?,
        inputSpace: Boolean?
    ) {
        val filters = mutableListOf<InputFilter>()
        inputDigits?.let {
            filters.add(StringFilter(it))
        }
        if (inputSpace == true) {
            filters.add(SpaceFilter())
        }
        if (filters.isEmpty()) {
            return
        }
        filters.addAll(editText.filters)
        editText.filters = filters.toTypedArray()
    }

    /**
     * 设置价格
     * @param textView TextView
     * @param price Double 价格
     * @param priceLeftText String 价格左边的文字
     * @param priceRightText String 价格右边的文字
     * @param priceColor Int 价格颜色
     * @param priceProportion Float 字体放大比例
     * @param priceBold Boolean  是否需要加粗
     * @param priceDecimalPart Boolean 小数部分是否需要放大
     */
    @BindingAdapter(
        value = ["price", "priceColor", "priceLeftText", "priceRightText", "priceProportion", "priceBold", "priceDecimalPart"],
        requireAll = false
    )
    @JvmStatic
    fun price(
        textView: TextView,
        price: Double,
        @ColorRes priceColor: Int = R.color.common_price_color,
        priceLeftText: String = "",
        priceRightText: String = "",
        priceProportion: Float = 1.5f,
        priceBold: Boolean = false,
        priceDecimalPart: Boolean = false
    ) {
        textView.text = getPriceSpannableString(
            textView.context,
            price,
            color = priceColor,
            left = priceLeftText,
            right = priceRightText,
            proportion = priceProportion,
            bold = priceBold,
            decimalPart = priceDecimalPart
        )
    }

}