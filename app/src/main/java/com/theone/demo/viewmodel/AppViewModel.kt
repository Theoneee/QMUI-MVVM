package com.theone.demo.viewmodel

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.theone.demo.app.util.CacheUtil
import com.theone.demo.data.model.bean.CollectBus
import com.theone.demo.data.model.bean.UserInfo
import com.theone.mvvm.base.viewmodel.BaseViewModel

class AppViewModel: BaseViewModel() {

    //App的账户信息
    var userInfo = UnPeekLiveData.Builder<UserInfo>().setAllowNullValue(true).create()

    //全局收藏，在任意一个地方收藏或取消收藏，监听该值的界面都会收到消息
    val collectEvent = UnPeekLiveData<CollectBus>()

    //App 列表动画
    var appAnimation = UnPeekLiveData<Int>()

    // 分享文章
    var shareArticle = UnPeekLiveData<Boolean>()

    init {
        userInfo.value = CacheUtil.getUser()
        //初始化列表动画
        appAnimation.value = CacheUtil.getAnimationType()
    }

}