package com.theone.common.widget

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable

/**
 * https://github.com/whataa/noDrawable
 */
class ProxyDrawable : StateListDrawable() {
    var originDrawable: Drawable? = null
        private set

    override fun addState(stateSet: IntArray?, drawable: Drawable) {
        if (stateSet != null && stateSet.size == 1 && stateSet[0] == 0) {
            originDrawable = drawable
        }
        super.addState(stateSet, drawable)
    }

}