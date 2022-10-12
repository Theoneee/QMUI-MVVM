package com.theone.demo.ui.fragment.sample

import android.text.SpannableString
import android.view.View
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import com.theone.common.ext.*
import com.theone.demo.R
import com.theone.demo.databinding.FragmentSampleStringExtBinding
import com.theone.demo.viewmodel.StringExtViewModel
import com.theone.mvvm.base.fragment.BaseVmDbFragment
import com.theone.mvvm.ext.qmui.addToGroup
import com.theone.mvvm.ext.qmui.createItem
import com.theone.mvvm.ext.qmui.setTitleWithBackBtn

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
 * @date 2021-04-09 09:29
 * @describe StringExt 示例
 * @email 625805189@qq.com
 * @remark
 */
class StringExtFragment : BaseVmDbFragment<StringExtViewModel, FragmentSampleStringExtBinding>() {

    private lateinit var ForegroundColorSpan: QMUICommonListItemView
    private lateinit var BackgroundColorSpan: QMUICommonListItemView
    private lateinit var StrikeThroughSpan: QMUICommonListItemView
    private lateinit var RelativeSizeSpan: QMUICommonListItemView
    private lateinit var UnderlineSpan: QMUICommonListItemView
    private lateinit var BOLD: QMUICommonListItemView
    private lateinit var ITALIC: QMUICommonListItemView
    private lateinit var BOLD_ITALIC: QMUICommonListItemView
    private lateinit var BOLD_ITALIC2: QMUICommonListItemView
    private lateinit var SKIN: QMUICommonListItemView

    override fun QMUITopBarLayout.initTopBar() {
        setTitleWithBackBtn("StringExt", this@StringExtFragment)
    }

    override fun initView(root: View) {
        getDataBinding().groupListView.run {
            ForegroundColorSpan =
                createItem("前景色", SpanType.ForegroundColorSpan)
            BackgroundColorSpan =
                createItem("背景色", SpanType.BackgroundColorSpan)
            StrikeThroughSpan = createItem("删除线", SpanType.StrikeThroughSpan)
            RelativeSizeSpan = createItem("字体大小", SpanType.RelativeSizeSpan)
            UnderlineSpan = createItem("下划线", SpanType.UnderlineSpan)
            BOLD = createItem("粗体", SpanType.BOLD)
            ITALIC = createItem("斜体", SpanType.ITALIC)
            // 使用这个最好后面多加个空格
            BOLD_ITALIC = createItem("粗体加斜体 ", SpanType.BOLD_ITALIC)
            BOLD_ITALIC2 = createItem(
                "组合使用",
                SpanType.BOLD,
                SpanType.ForegroundColorSpan,
                SpanType.UnderlineSpan,
                SpanType.RelativeSizeSpan
            )

            addToGroup(
                ForegroundColorSpan,
                BackgroundColorSpan,
                StrikeThroughSpan,
                RelativeSizeSpan,
                UnderlineSpan,
                BOLD,
                ITALIC,
                BOLD_ITALIC,
                BOLD_ITALIC2
            )
        }
        getViewModel().run {
            subscript.set(getSpannableString("下标", SpanType.SubscriptSpan))
            superscript.set(getSpannableString("上标", SpanType.SuperscriptSpan))
            fruitName.set(
                getImageSpannableString(
                    getDrawable(
                        mActivity,
                        R.drawable.ic_food_status_up
                    ), getString(R.string.long_fruit_name)
                )
            )
            // 参数基本上都是有默认值的，这里全写出来只是为了直观的看见全部可更改的
            price.set(
                getPriceSpannableString(
                    mActivity,
                    5.20,
                    "这份水果只需要 ",
                    proportion = 1.5f,
                    bold = true,
                    decimalPart = false
                )
            )
        }
    }

    private fun QMUIGroupListView.createItem(
        title: String,
        vararg types: SpanType
    ): QMUICommonListItemView = createItem(getSpannableString(title, *types))

    private fun getSpannableString(title: String, vararg types: SpanType): SpannableString {
        var content = "设置$title"
        var target = title
        if (title.contains("标")) {
            target = "①"
            content += target
        }
        return content.getSpannableString(
            target,
            getColor(mActivity, R.color.qmui_config_color_blue),
            proportion = 1.2f,
            types = *types
        )
    }


}