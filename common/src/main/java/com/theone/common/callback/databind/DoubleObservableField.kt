package com.theone.common.callback.databind

import androidx.databinding.ObservableField

/**
 * 自定义的Int类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class DoubleObservableField(value: Double = 0.0) : ObservableField<Double>(value) {

    override fun get(): Double {
        return super.get()!!
    }

    override fun set(value: Double?) {
        super.set(value?:0.0)
    }

}