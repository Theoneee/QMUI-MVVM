package com.theone.demo.ui.bindadapter

import android.text.InputType
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.BindingAdapter
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.kotlin.wrapContent
import com.qmuiteam.qmui.layout.QMUIFrameLayout
import com.qmuiteam.qmui.widget.QMUIFloatLayout
import com.theone.common.ext.dp2px
import com.theone.common.ext.getDrawable
import com.theone.demo.R
import com.theone.demo.app.util.ColorUtil
import com.theone.demo.data.model.bean.ArticleResponse
import com.theone.demo.data.model.bean.ClassifyResponse
import com.theone.demo.data.model.bean.SearchResponse
import com.theone.demo.ui.fragment.category.SystemArticleFragment
import com.theone.demo.ui.fragment.web.WebExplorerFragment


object CustomBindAdapter {

    @BindingAdapter(value = ["treeChildData", "fragment"], requireAll = false)
    @JvmStatic
    fun loadTreeData(
        floatLayout: QMUIFloatLayout,
        classifyResponses: List<ClassifyResponse>,
        fragment: QMUIFragment?
    ) {
        floatLayout.removeAllViews()
        for (c in classifyResponses) {
            createFloatLayoutItem(floatLayout,c.name){
                fragment?.startFragment(SystemArticleFragment.newInstance(c))
            }
        }
    }

    @BindingAdapter(value = ["treeChildData", "fragment"], requireAll = false)
    @JvmStatic
    fun loadArticleTreeData(
        floatLayout: QMUIFloatLayout,
        articles: List<ArticleResponse>,
        fragment: QMUIFragment?
    ) {
        floatLayout.removeAllViews()
        for (article in articles) {
            createFloatLayoutItem(floatLayout,article.title){
                fragment?.startFragment(WebExplorerFragment.newInstance(article))
            }
        }
    }

    fun loadHotSearchData(
        floatLayout: QMUIFloatLayout,
        articles: List<SearchResponse>,
        callback: ((String) -> Unit?)? = null
    ) {
        floatLayout.removeAllViews()
        for (article in articles) {
            createFloatLayoutItem(floatLayout,article.name,callback)
        }
    }

    private fun createFloatLayoutItem(
        floatLayout: QMUIFloatLayout,title:String,callback: ((String) -> Any?)? = null){
        val context = floatLayout.context
        val layoutParams = ViewGroup.LayoutParams(wrapContent, wrapContent)
        val container = QMUIFrameLayout(context)
        val space = context.dp2px(10)
        container.setPadding(0,0,space,space)
        val tag = TextView(context)
        val padding = context.dp2px( 4)
        val padding2 = padding * 2
        tag.run {
            setPadding(padding2, padding, padding2, padding)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            maxLines = 1
            setTextColor(ColorUtil.randomColor())
            text = title
            background = getDrawable(
                context,
                R.drawable.tree_tag_bg
            )
        }
        container.addView(tag,layoutParams)
        callback?.run {
            container.setOnClickListener{
                invoke(title)
            }
        }
        floatLayout.addView(container, layoutParams)
    }

    @BindingAdapter(value = ["checkChange"])
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

    @BindingAdapter(value = ["visible"], requireAll = false)
    @JvmStatic
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

}