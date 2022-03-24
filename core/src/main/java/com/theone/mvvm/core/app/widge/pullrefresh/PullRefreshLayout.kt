package com.theone.mvvm.core.app.widge.pullrefresh

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout

/**
 * @author cginechen
 * @date 2016-12-11
 */
class PullRefreshLayout : QMUIPullRefreshLayout {
    @IntDef(
        STYLE_QMUI,
        STYLE_WW,
        STYLE_FLYME
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class PullRefreshStyle {}

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    override fun createRefreshView(): View {
        return when (Style) {
            STYLE_WW -> {
                WWLoadingView(context)
            }
            STYLE_FLYME -> {
                FlymeLoadingView(context)
            }
            else -> {
                super.createRefreshView()
            }
        }
    }

    override fun calculateTargetOffset(
        target: Int,
        targetInitOffset: Int,
        targetRefreshOffset: Int,
        enableOverPull: Boolean
    ): Int {
        return super.calculateTargetOffset(target, targetInitOffset, 400, false)
    }

    companion object {
        const val STYLE_QMUI = 0
        const val STYLE_WW = 1
        const val STYLE_FLYME = 2
        var Style = STYLE_FLYME
        fun initStyle(@PullRefreshStyle style: Int) {
            Style = style
        }
    }
}