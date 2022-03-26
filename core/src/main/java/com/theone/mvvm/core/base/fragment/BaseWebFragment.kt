package com.theone.mvvm.core.base.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ZoomButtonsController
import androidx.databinding.ViewDataBinding
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUIProgressBar
import com.qmuiteam.qmui.widget.webview.QMUIWebView
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer
import com.theone.common.constant.BundleConstant
import com.theone.common.ext.getValueNonNull
import com.theone.common.ext.gone
import com.theone.common.ext.showViews
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.core.R
import com.theone.mvvm.core.base.callback.IWeb
import com.theone.mvvm.core.app.ext.addImageListenerJs
import com.theone.mvvm.core.app.ext.parseImagePreviewBeans
import com.theone.mvvm.core.app.ext.startImagePreviewFragment
import com.theone.mvvm.core.app.widge.webview.BridgeWebView
import com.theone.mvvm.core.app.widge.webview.BridgeWebViewClient
import com.theone.mvvm.ext.qmui.setTitleWitchBackBtn
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.reflect.Field
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

abstract class BaseWebFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseCoreFragment<VM, DB>(), QMUIWebView.OnScrollChangeListener {

    private val PROGRESS_PROCESS: Int = 0
    private val PROGRESS_GONE: Int = 1

    private val IMAGE_HANDLER_NAME = "imagelistener"

    private val mWebView: BridgeWebView by lazy {
        BridgeWebView(mActivity)
    }
    private val mProgressHandler: ProgressHandler by lazy { ProgressHandler() }

    protected val mIWeb: IWeb by getValueNonNull(BundleConstant.DATA)
    private lateinit var mUrl: String

    private fun needDispatchSafeAreaInset(): Boolean = false

    override fun showTopBar(): Boolean = false

    abstract fun getWebContainer(): QMUIWebViewContainer
    abstract fun getProgressBar(): QMUIProgressBar

    override fun initView(root: View) {
        initTopBar()
    }

    protected open fun initTopBar() {
        setTitleWitchBackBtn(mIWeb.getWebTitle())
    }

    protected open fun setTopBarTitle(title: String?) {
       getTopBar()?.setTitle(title)
    }

    override fun onLazyInit() {
        super.onLazyInit()
        initWebView()
        loadUrl()
    }

    private fun initWebView() {
        val needDispatchSafeAreaInset = needDispatchSafeAreaInset()
        getWebContainer().run {
            addWebView(mWebView, needDispatchSafeAreaInset)
            val params = layoutParams as FrameLayout.LayoutParams
            fitsSystemWindows = !needDispatchSafeAreaInset
            params.topMargin = if (needDispatchSafeAreaInset) 0 else QMUIResHelper.getAttrDimen(
                context,
                R.attr.qmui_topbar_height
            )
            layoutParams = params
            setCustomOnScrollChangeListener(this@BaseWebFragment)
        }

        mWebView.run {
            overScrollMode = View.OVER_SCROLL_NEVER
            webChromeClient = ExplorerWebViewChromeClient()
            webViewClient = ExplorerWebViewClient(this,needDispatchSafeAreaInset)
            setZoomControlGone(this)
        }
        handleUrl(mIWeb.getWebUrl())
        registerHandler()
    }

    protected open fun loadUrl() {
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

    private fun setZoomControlGone(webView: WebView) {
        webView.settings.displayZoomControls = false
        val classType: Class<*>
        val field: Field
        try {
            classType = WebView::class.java
            field = classType.getDeclaredField("mZoomButtonsController")
            field.isAccessible = true
            val zoomButtonsController = ZoomButtonsController(
                webView
            )
            zoomButtonsController.zoomControls.visibility = View.GONE
            try {
                field[webView] = zoomButtonsController
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
    }

    inner class ExplorerWebViewChromeClient() : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress > mProgressHandler.mDstProgressIndex) {
                sendProgressMessage(PROGRESS_PROCESS, newProgress, 100)
            }
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (mIWeb.isWebTitleNeedChange())
                setTopBarTitle(title)
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

    protected open fun getImageBridgeHandler():BridgeHandler{
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
                for (i in itemData.indices) {
                    val item = itemData[i]
                    if (item == src) {
                        position = i
                    }
                }
                startImagePreview(itemData, position)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }


    protected open fun startImagePreview(
        itemData: ArrayList<String>,
        position: Int
    ) {
        startImagePreviewFragment(
            images = itemData.parseImagePreviewBeans(),
            position = position
        )
    }

    @SuppressLint("HandlerLeak")
    inner class ProgressHandler : Handler(Looper.getMainLooper()) {

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

    fun getWebView(): WebView = mWebView

}