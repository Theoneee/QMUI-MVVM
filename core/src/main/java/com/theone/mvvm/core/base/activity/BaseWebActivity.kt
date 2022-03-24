package com.theone.mvvm.core.base.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Handler
import android.os.Message
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUIProgressBar
import com.qmuiteam.qmui.widget.webview.QMUIWebView
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.*
import com.theone.common.widget.TheMarqueeTextView
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.IWeb
import com.theone.mvvm.core.app.ext.addImageListenerJs
import com.theone.mvvm.core.app.ext.parseImagePreviewBeans
import com.theone.mvvm.core.app.ext.setZoomControlGone
import com.theone.mvvm.core.app.ext.startImagePreview
import com.theone.mvvm.core.app.widge.webview.BridgeWebView
import com.theone.mvvm.core.app.widge.webview.BridgeWebViewClient
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*


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
 * @date 2021/3/8 0008
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

abstract class BaseWebActivity<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseCoreActivity<VM, DB>(), QMUIWebView.OnScrollChangeListener {

    private val PROGRESS_PROCESS: Int = 0
    private val PROGRESS_GONE: Int = 1

    private val IMAGE_HANDLER_NAME = "imagelistener"

    protected val mWebView: BridgeWebView by lazy {
        BridgeWebView(this).apply {
            webChromeClient = getWebViewChromeClient()
            webViewClient = this@BaseWebActivity.getWebViewClient(this)
            setZoomControlGone()
        }
    }
    private val mProgressHandler: ProgressHandler by lazy { ProgressHandler() }
    private lateinit var mTitleView: TheMarqueeTextView

    protected val mIWeb: IWeb by getValueNonNull(BundleConstant.DATA)
    private lateinit var mUrl: String
    private var isFirstLoad: Boolean = true

    override fun loadSirRegisterView(): View = getWebContainer()

    private fun needDispatchSafeAreaInset(): Boolean = false

    override fun showTopBar(): Boolean = false

    abstract fun getWebContainer(): QMUIWebViewContainer
    abstract fun getProgressBar(): QMUIProgressBar

    protected open fun getWebViewChromeClient(): WebChromeClient = ExplorerWebViewChromeClient()
    protected open fun getWebViewClient(webView: BridgeWebView): QMUIWebViewClient =
        ExplorerWebViewClient(webView,needDispatchSafeAreaInset())

    override fun initView(root: View) {
        initTopBar()
        initWebView()
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        if (isFirstLoad){
            isFirstLoad = false
            loadUrl()
        }
    }

    protected open fun initTopBar() {
        getTopBar()?.run {
            addLeftBackImageButton().setOnClickListener {
                finish()
            }
            // QMUI的Title用的是QMUIQQFaceView，无法使用跑马灯效果，这里重新设置一个
            setTitle(mIWeb.getWebTitle()?.toHtml().toString())
//            mTitleView = TheMarqueeTextView(context).apply {
//                layoutParams = RelativeLayout.LayoutParams(matchParent, wrapContent).apply {
//                    addRule(RelativeLayout.RIGHT_OF, R.id.qmui_topbar_item_left_back)
//                    gravity = Gravity.CENTER
//                    marginEnd = dp2px(20)
//                }
//                marqueeRepeatLimit = Int.MAX_VALUE
//                isFocusable = true
//                textSize = 17f
//                ellipsize = TextUtils.TruncateAt.MARQUEE
//                isSingleLine = true
//                setHorizontallyScrolling(true)
//                isFocusableInTouchMode = true
//                mIWeb.getWebTitle()?.let {
//                    text = it
//                }
//            }
//            setCenterView(mTitleView)
        }
    }

    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        getWebContainer().run {
            addWebView(mWebView, needDispatchSafeAreaInset())
            val params = layoutParams as FrameLayout.LayoutParams
            fitsSystemWindows = !needDispatchSafeAreaInset()
            params.topMargin = if (needDispatchSafeAreaInset()) 0 else QMUIResHelper.getAttrDimen(
                context,
                R.attr.qmui_topbar_height
            )
            layoutParams = params
            setCustomOnScrollChangeListener(this@BaseWebActivity)
        }
        registerHandler()
        handleUrl(mIWeb.getWebUrl())
    }

    protected open fun loadUrl() {
        if (mIWeb.getWebUrl().startsWith("http"))
            mWebView.loadUrl(mIWeb.getWebUrl())
    }

    private fun handleUrl(url: String) {
        mUrl = if (mIWeb.isWebUrlNeedDecode()) {
            try {
                URLDecoder.decode(url, "utf-8")
            } catch (e: UnsupportedEncodingException) {
                url
            }
        } else {
            url
        }
    }

    protected open fun registerHandler() {
        mWebView.registerHandler(IMAGE_HANDLER_NAME, getImageBridgeHandler())
    }

    override fun onScrollChange(
        webView: WebView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {

    }

    private fun sendProgressMessage(
        progressType: Int,
        newProgress: Int,
        duration: Int
    ) {
        val msg = Message()
        msg.what = progressType
        msg.arg1 = newProgress
        msg.arg2 = duration
        mProgressHandler.sendMessage(msg)
    }

    inner class ExplorerWebViewChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress > mProgressHandler.mDstProgressIndex) {
                sendProgressMessage(PROGRESS_PROCESS, newProgress, 100)
            }
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (mIWeb.isWebTitleNeedChange())
                mTitleView.text = title
        }

        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
            callback?.onCustomViewHidden()
        }

    }

    inner class ExplorerWebViewClient(webView: BridgeWebView, needDispatchSafeAreaInset: Boolean) :
        BridgeWebViewClient(webView,needDispatchSafeAreaInset, true) {
        override fun onPageStarted(
            view: WebView,
            url: String,
            favicon: Bitmap?
        ) {
            super.onPageStarted(view, url, favicon)
            if (mProgressHandler.mDstProgressIndex == 0) {
                sendProgressMessage(
                    PROGRESS_PROCESS,
                    30,
                    500
                )
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let {
                if (it.startsWith("http:") || it.startsWith("https:")) {
                    view?.loadUrl(it)
                    return false
                }
            }
            return true
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            handler?.proceed()
        }

        override fun onPageFinished(
            view: WebView,
            url: String
        ) {
            super.onPageFinished(view, url)
            view.addImageListenerJs(IMAGE_HANDLER_NAME)
            sendProgressMessage(
                PROGRESS_GONE,
                100,
                0
            )
        }
    }

    protected open fun getImageBridgeHandler(): BridgeHandler {
        return BridgeHandler { data, function ->
            var jsonObject: JSONObject? = null
            val itemData: ArrayList<String>
            var position = 0
            try {
                jsonObject = JSONObject(data)
                val src = jsonObject.getString("position")
                val personObject = jsonObject.getString("data")
                itemData = Gson().fromJson(
                    personObject,
                    object :
                        TypeToken<List<String>>() {}.type
                )
                for ((i, item) in itemData.withIndex()) {
                    if (item == src) {
                        position = i
                    }
                }
                startPhotoWatchActivity(itemData, position)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }


    protected open fun startPhotoWatchActivity(
        itemData: ArrayList<String>,
        position: Int
    ) {
        startImagePreview(
            this,
            images = itemData.parseImagePreviewBeans(),
            position = position
        )
    }

    @SuppressLint("HandlerLeak")
    inner class ProgressHandler : Handler() {

        var mDstProgressIndex: Int = 0
        var mDuration: Int = 0
        var mAnimator: ObjectAnimator? = null

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PROGRESS_PROCESS -> {
                    mDstProgressIndex = msg.arg1
                    mDuration = msg.arg2
                    showViews(getProgressBar())
                    if (null != mAnimator && mAnimator?.isRunning!!) {
                        mAnimator?.cancel()
                    }
                    mAnimator =
                        ObjectAnimator.ofInt(getProgressBar(), "progress", mDstProgressIndex)
                    mAnimator?.run {
                        duration = mDuration.toLong()
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                if (getProgressBar().progress == 100) {
                                    sendEmptyMessageDelayed(
                                        PROGRESS_GONE,
                                        500
                                    )
                                }
                            }
                        })
                        start()
                    }
                }
                PROGRESS_GONE -> {
                    mDstProgressIndex = 0
                    mDuration = 0
                    getProgressBar().progress = 0
                    getProgressBar().gone()
                    if (mAnimator != null && mAnimator!!.isRunning) {
                        mAnimator!!.cancel()
                    }
                    mAnimator = ObjectAnimator.ofInt(getProgressBar(), "progress", 0)
                    mAnimator?.run {
                        duration = 0
                        removeAllListeners()
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        getWebContainer().destroy()
    }

}