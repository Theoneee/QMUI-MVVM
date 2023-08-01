package com.theone.mvvm.core.app.widge.dialog

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.qmuiteam.qmui.widget.QMUIProgressBar
import com.qmuiteam.qmui.widget.QMUIProgressBar.QMUIProgressBarTextGenerator
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.theone.mvvm.core.R

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
 * @date 2021-04-30 14:06
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class ProgressDialog(context: Context, themeId: Int = R.style.QMUI_Dialog) :
    QMUIDialog(context, themeId), QMUIProgressBarTextGenerator {

    private var mProgressBar: QMUIProgressBar? = null
    private var mTip: AppCompatTextView? = null
    private var mOldPercent :Int = 0

    init {
        setContentView(R.layout.dialog_progress)
        mProgressBar = findViewById(R.id.progressbar)
        mProgressBar?.run {
            maxValue = 100
            qmuiProgressBarTextGenerator = this@ProgressDialog
        }
        mTip = findViewById(R.id.message)
        setCanceledOnTouchOutside(false)
    }

//    private fun createContent(
//    ): View {
//        mProgressBar = QMUIProgressBar(context).apply {
//            val width = dp2px(50)
//            layoutParams = ViewGroup.LayoutParams(width, width)
//            setProgressColor(QMUIResHelper.getAttrColor(context, R.attr.app_skin_primary_color))
//            setStrokeRoundCap(true)
//            setType(QMUIProgressBar.TYPE_CIRCLE)
//        }
//        mTip = AppCompatTextView(context).apply {
//            layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
//                topMargin = QMUIResHelper.getAttrDimen(context, com.qmuiteam.qmui.R.attr.qmui_tip_dialog_text_margin_top)
//            }
//        }
//        return QMUILinearLayout(context).apply {
//            setBackgroundColor(getColor(R.color.white))
//            orientation = LinearLayout.VERTICAL
//            gravity = Gravity.CENTER_HORIZONTAL
//            layoutParams = ViewGroup.LayoutParams(dp2px(190), dp2px(130))
//            addView(mProgressBar)
//            addView(mTip)
//        }
//    }

    fun setProgress(progress: Int, max: Int = 100,animated:Boolean = false) {
        if(progress == mOldPercent) return
        mProgressBar?.let {
            val percent = ((progress/(max*1.0f))*100).toInt()
            it.setProgress(percent,animated)
        }
    }

    fun setMessage(msg: String?) {
        mTip?.run {
            if(text.toString() != msg){
                text = msg
            }
        }

    }

    override fun generateText(progressBar: QMUIProgressBar?, value: Int, maxValue: Int): String  =
        "$value%"

}