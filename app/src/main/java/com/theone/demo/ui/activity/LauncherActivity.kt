package com.theone.demo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.qmuiteam.qmui.arch.QMUILatestVisit
import com.theone.common.ext.delay
import com.theone.demo.R
import com.theone.demo.app.util.CacheUtil
import com.theone.demo.app.widget.TypeTextView
import com.theone.demo.databinding.ActivityLauncherBinding
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.base.activity.BaseCoreActivity
import com.theone.mvvm.core.app.util.RxHttpManager

class LauncherActivity : BaseCoreActivity<BaseViewModel, ActivityLauncherBinding>(),
    TypeTextView.OnTypeViewListener {

    private val mTypes: Array<String> by lazy {
        resources.getStringArray(R.array.nonsenses)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        super.onCreate(savedInstanceState)
    }

    override fun showTopBar(): Boolean = false

    override fun initView(root: View) {
        // 再次安装后请求时要清除，不然会读取到
        if (CacheUtil.isFirst()) {
            RxHttpManager.clearCookieCache()
            CacheUtil.isEnterApp()
        }

        val tips = mTypes[(mTypes.indices).random()]
        getDataBinding().tvType.run {
            if (CacheUtil.isOpenLauncherText()) {
                setOnTypeViewListener(this@LauncherActivity)
                start(tips, 120)
            } else {
                text = tips
                startToMain(2000)
            }
        }
    }

    override fun createObserver() {
    }


    override fun onTypeStart() {

    }

    override fun onTypeOver() {
        startToMain(800)
    }

    private fun startToMain(time: Long) {
        delay(time){
            var intent = QMUILatestVisit.intentOfLatestVisit(this)
            if (intent == null) {
                intent = Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

}
