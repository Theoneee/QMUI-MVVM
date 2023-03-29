package com.theone.common.callback.databind

import androidx.databinding.ObservableField

/**
 * 自定义的CharSequence类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class CharSequenceObservableField(value: CharSequence = "") : ObservableField<CharSequence>(value) {

    override fun get(): CharSequence {
        return super.get()!!
    }

    override fun set(value: CharSequence?) {
        super.set(value?:"")
    }

}