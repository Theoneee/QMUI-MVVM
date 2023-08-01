package com.theone.demo.viewmodel

import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.theone.demo.data.model.bean.ArticleResponse
import com.theone.demo.data.net.PagerResponse
import com.theone.demo.app.util.CacheUtil
import com.theone.demo.data.model.bean.CollectBus
import com.theone.demo.data.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import rxhttp.wrapper.coroutines.Await

abstract class ArticleViewModel(val url: String? = null) : BasePagerViewModel<ArticleResponse>() {

    //收藏文章
    private val collectionError: UnPeekLiveData<String> = UnPeekLiveData()

    fun getCollectionError(): ProtectedUnPeekLiveData<String> = collectionError

    override fun requestServer() {
        requestArticles(ApiRepository.INSTANCE.getArticles(url,page,getCacheMode()))
    }

    protected fun requestArticles( block: Await<PagerResponse<List<ArticleResponse>>>){
        request({
            onSuccess(block.await())
        })
    }

    override fun onSuccess(response: PagerResponse<List<ArticleResponse>>?) {
        // 第一次是从缓存里获取，这里要拿用户里收藏的判断一下
        response?.datas?.run {
            if (isFirst) {
                val user = CacheUtil.getUser()
                user?.collectIds?.forEach { id ->
                    for (index in this.indices) {
                        if(this[index].id == id.toInt()){
                            this[index].collect = true
                            break
                        }
                    }
                }
            }
        }
        super.onSuccess(response)
    }

    fun collection(article: ArticleResponse, event: AppViewModel) {
        request({
            ApiRepository.INSTANCE.collectionArticle(article.getArticleId(),article.collect).await()
            // 操作过后应该更新本地的用户信息里的收藏
            CacheUtil.setUser(
                CacheUtil.getUser()?.apply {
                    if (!article.collect)
                        collectIds.add(article.getArticleId().toString())
                    else
                        collectIds.remove(article.getArticleId().toString())
                })
            event.collectEvent.value = CollectBus(article.getArticleId(), !article.collect)
        }, null, errorLiveData = collectionError)
    }

}