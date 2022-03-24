package com.theone.mvvm.core.app.widge.pullrefresh

import android.content.Context
import android.os.Vibrator
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import com.qmuiteam.qmui.util.QMUIColorHelper
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout.IRefreshView
import com.theone.common.ext.*
import com.theone.mvvm.core.R
import com.theone.mvvm.core.app.widge.ProgressWheel

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
 * @date 2019/8/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class FlymeLoadingView(context: Context) : RelativeLayout(context), IRefreshView {

    private var mProgressWheel: ProgressWheel? = null
    private var mTips: AppCompatTextView? = null
    private var mTextColor = 0
    private var vibrator: Vibrator? = null
    private var isPrepare = false

    private val mFreshTips: String? by lazy {
        getString(R.string.pull_fresh_flyme_fresh)
    }
    private val mPrepareTips: String? by lazy {
        getString(R.string.pull_fresh_flyme_prepare)
    }
    private val mPullingTips: String? by lazy {
        getString(R.string.pull_fresh_flyme_pull)
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.pull_refresh_layout, this, true)
        mProgressWheel = view.findViewById(R.id.progressWheel)
        mTips = view.findViewById(R.id.tv_tips)
        mTextColor = getColor(R.color.pull_fresh_flyme_tips_color)
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }


    override fun stop() {
        mTips?.text = mPullingTips
    }

    override fun doRefresh() {
        mTips?.text = mFreshTips
        mProgressWheel?.spin()
    }

    override fun onPull(i: Int, i1: Int, i2: Int) {
        val percent = i * 1.0f / i1
        if (percent >= 1) {
            if (!isPrepare && null != vibrator && vibrator!!.hasVibrator()) {
                vibrator!!.vibrate(1)
            }
            isPrepare = true
            mTips?.text = mPrepareTips
        } else {
            isPrepare = false
            mTips?.text = mPullingTips
            mProgressWheel?.progress = percent
            //文字透明度
            mTips?.setTextColor(QMUIColorHelper.setColorAlpha(mTextColor, percent))
        }
    }

}