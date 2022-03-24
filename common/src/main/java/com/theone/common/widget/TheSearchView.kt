package com.theone.common.widget

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.theone.common.R
import com.theone.common.ext.*

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
 * @date 2021-03-29 13:53
 * @describe 自定义一个带搜索图标+背景+删除+键盘点击搜索功能的搜索框
 * @email 625805189@qq.com
 * @remark
 */
class TheSearchView(context: Context, enable: Boolean = true) : RelativeLayout(context),
    TextView.OnEditorActionListener {

    // 是否可以输入
    private var editEnable: Boolean = true
        set(value) {
            field = value
            setEditTextEnable(value)
        }

    var mSearchListener: OnTextChangedListener? = null

    /**
     * 左右间距
     * 上下间距默认为左右间距的2/3
     */
    var mDefaultPadding: Int = dp2px(10)

    /**
     * 搜索的EditText
     */
    val mEditText: EditText by lazy {
        // 光标设置无效，只能这样加载了
        val editText = View.inflate(context, R.layout.common_search_eadittext_layout, null) as EditText
        editText.apply {
            id = R.id.common_search_edittext_id
            background = null
            layoutParams = LayoutParams(matchParent, wrapContent).apply {
                addRule(LEFT_OF, R.id.common_search_delete_id)
                marginEnd = mDefaultPadding
            }
            textSize = 14f
            hint = context.getString(R.string.common_search_hint)
            isSingleLine = true
            val verPadding = mDefaultPadding * 2 / 3
            setPadding(mDefaultPadding, verPadding, mDefaultPadding, verPadding)
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                getDrawable(R.drawable.common_search_icon),
                null,
                null,
                null
            )
            compoundDrawablePadding = mDefaultPadding
//            设置为什么无效？
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                setTextCursorDrawable(getDrawable(R.drawable.common_edit_text_cursor))
//            }
//            通过反射
//            try {
//                this.javaClass.getDeclaredField("mCursorDrawableRes").apply {
//                    isAccessible = true
//                    set(this@apply,R.drawable.common_edit_text_cursor)
//                }
//            }catch(e:Exception){
//                e.printStackTrace()
//            }
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            setOnEditorActionListener(this@TheSearchView)
            afterTextChange {
                val empty = mEditText.isEmpty()
                mClear.visibility = if (empty) View.GONE else View.VISIBLE
                mSearchListener?.run {
                    onSearchViewTextChanged(it, empty)
                }
            }
        }
    }

    /**
     * 清除内容的按钮
     */
    private val mClear: ImageView by lazy {
        AppCompatImageView(context).apply {
            id = R.id.common_search_delete_id
            layoutParams = LayoutParams(wrapContent, wrapContent).apply {
                addRule(ALIGN_PARENT_RIGHT)
                addRule(CENTER_VERTICAL)
                marginEnd = mDefaultPadding
            }
            setImageDrawable(getDrawable(R.drawable.common_title_bar_delete))
            setOnClickListener {
                mEditText.setText("")
            }
            visibility = View.GONE
        }
    }

    init {
        background = getDrawable(R.drawable.common_search_bg_corner)
        editEnable = enable
        addView(mClear)
        addView(mEditText)
        layoutParams = LayoutParams(matchParent, wrapContent)
    }

    /**
     * 设置EditText的状态
     */
    private fun setEditTextEnable(enable: Boolean) {
        mEditText.run {
            isCursorVisible = enable
            isFocusable = enable
            isFocusableInTouchMode = enable
            if (enable) requestFocus()
            else clearFocus()
        }
    }

    /**
     * 键盘按键监听
     */
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        mSearchListener?.let {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                it.onSearchViewClick(mEditText.textString(), mEditText.isEmpty())
            }
        }
        return false
    }

    interface OnTextChangedListener {
        fun onSearchViewTextChanged(content: String, empty: Boolean)
        fun onSearchViewClick(content: String, empty: Boolean)
    }

}