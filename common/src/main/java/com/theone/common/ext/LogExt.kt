package com.theone.common.ext

import android.util.Log

var TAG = "TheBase"
private var LOG_ENABLE = true

fun LogInit(enable: Boolean, tag: String = "TheBase") {
    LOG_ENABLE = enable
    TAG = tag
}

private enum class LEVEL {
    V, D, I, W, E
}

fun String.logV(tag: String = TAG) =
    log(
        LEVEL.V,
        tag,
        this
    )

fun String.logD(tag: String = TAG) =
    log(
        LEVEL.D,
        tag,
        this
    )

fun String.logI(tag: String = TAG) =
    log(
        LEVEL.I,
        tag,
        this
    )

fun String.logW(tag: String = TAG) =
    log(
        LEVEL.W,
        tag,
        this
    )

fun String.logE(tag: String = TAG) =
    log(
        LEVEL.E,
        tag,
        this
    )

private fun log(level: LEVEL, tag: String, message: String) {
    if (LOG_ENABLE)
        when (level) {
            LEVEL.V -> Log.v(tag, message)
            LEVEL.D -> Log.d(tag, message)
            LEVEL.I -> Log.i(tag, message)
            LEVEL.W -> Log.w(tag, message)
            LEVEL.E -> Log.e(tag, message)
        }
}