package com.theone.demo.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 积分
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class IntegralResponse(
        var coinCount: Int = 0,//积分
        var level: Int = 0,//等级
        var rank: Int = 0,// 排名
        var userId: Int = 0,
        var username: String?="") : Parcelable


