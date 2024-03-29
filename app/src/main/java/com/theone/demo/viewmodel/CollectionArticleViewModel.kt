package com.theone.demo.viewmodel

import com.theone.demo.data.net.PagerResponse
import com.theone.demo.data.model.bean.ArticleResponse
import com.theone.demo.data.net.Url
import rxhttp.wrapper.cache.CacheMode


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
 * @date 2021/3/4 0004
 * @describe 收藏的文章
 * @email 625805189@qq.com
 * @remark
 */
class CollectionArticleViewModel : ArticleViewModel(Url.MY_COLLECTION_ARTICLES) {

    // 收藏这里把所有的都设置为已收藏
    override fun onSuccess(response: PagerResponse<List<ArticleResponse>>?) {
        response?.datas?.forEach { it ->
            it.collect = true
        }
        super.onSuccess(response)
    }

    override fun getCacheMode(): CacheMode =  CacheMode.ONLY_NETWORK

}