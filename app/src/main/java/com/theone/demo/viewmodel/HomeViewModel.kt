package com.theone.demo.viewmodel

import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.theone.demo.data.model.bean.BannerResponse
import com.theone.demo.app.net.Url
import com.theone.demo.data.repository.ApiRepository


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
 * @date 2021/2/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class HomeViewModel : ArticleViewModel() {

    private val mBanners: UnPeekLiveData<List<BannerResponse>> = UnPeekLiveData()
    fun getBanners(): ProtectedUnPeekLiveData<List<BannerResponse>> = mBanners

    override fun requestServer() {
        request({
            val response = ApiRepository.INSTANCE.getArticles(Url.HOME_ARTICLE,page,getCacheMode())
            if(isFirst || isFresh){
                mBanners.value = ApiRepository.INSTANCE.getBanners(Url.HOME_BANNER,getCacheMode())
            }
            onSuccess(response)
        })
    }

}

