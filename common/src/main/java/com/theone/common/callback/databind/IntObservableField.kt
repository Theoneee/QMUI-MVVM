package com.theone.common.callback.databind

import androidx.databinding.ObservableField

/**
 * 自定义的Int类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class IntObservableField(value: Int = 0) : ObservableField<Int>(value) {

    override fun get(): Int {
        return super.get()!!
    }

    override fun set(value: Int?) {
        super.set(value?:0)
    }

}