package com.theone.common.ext

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theone.common.constant.BundleConstant


fun Fragment.getView(layoutId: Int): View {
    return layoutInflater.inflate(layoutId, null)
}

/**
 * dp值转换为px
 */
fun Fragment.dp2px(dp: Int): Int {
    return requireContext().dp2px(dp)
}

/**
 * px值转换成dp
 */
fun Fragment.px2dp(px: Int): Int {
    return requireContext().px2dp(px)
}

fun Fragment.startActivity(target: Class<*>, finish: Boolean = false) {
    requireActivity().startActivity(target, finish)
}

fun Fragment.startActivity(intent: Intent, finish: Boolean = false) {
    requireActivity().startActivity(intent, finish)
}

fun Fragment.startWebView(url: String) {
    startActivity(Intent().apply {
        action = "android.intent.action.VIEW"
        data = Uri.parse(url)
    })
}

inline fun <reified T : Fragment> newFragment(bundle: Bundle.() -> Unit): T {
    val instance = T::class.java.getDeclaredConstructor().run {
        isAccessible = true
        newInstance()
    }
    return instance.bundle(bundle)
}

inline fun <reified T : Fragment> T.bundle(bundle: Bundle.() -> Unit): T {
    return apply {
        arguments = Bundle().apply {
            bundle.invoke(this)
        }
    }
}