package com.theone.demo.data.repository

import com.theone.demo.app.ext.getCacheMode
import com.theone.demo.data.net.PagerResponse
import com.theone.demo.data.net.Url
import com.theone.demo.data.model.bean.*
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

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
 * @date 2021-09-14 10:45
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class ApiRepository private constructor() {

    private object Holder {
        val INSTANCE = ApiRepository()
    }

    companion object {
        val INSTANCE = Holder.INSTANCE
    }

    suspend fun loginOrRegister(
        account: String,
        password: String,
        repassword: String,
        isRegister: Boolean
    ): UserInfo {
        return RxHttp.postForm(if (isRegister) Url.REGISTER else Url.LOGIN)
            .add("username", account)
            .add("password", password)
            .add("repassword", repassword, isRegister)
            .toResponse<UserInfo>()
            .await()
    }

    /**
     * 登出
     * @return String?
     */
    fun loginOut(): Await<String> {
        return RxHttp.get(Url.LOGIN_OUT)
            .toResponse<String>()
    }

    /**
     * 获取自己分享的文章
     * @param page Int
     * @param cacheMode CacheMode
     * @return ShareResponse?
     */
    suspend fun getMyShareArticle(page: Int, cacheMode: CacheMode): ShareResponse {
        return RxHttp.get(Url.MY_SHARE_ARTICLE, page)
            .setCacheMode(cacheMode)
            .toResponse<ShareResponse>()
            .await()
    }

    /**
     *
     * @param url String
     * @param page Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<ArticleResponse>>?
     */
    suspend fun getArticles(
        url: String?,
        page: Int,
        cacheMode: CacheMode
    ): PagerResponse<List<ArticleResponse>> {
        return RxHttp.get(url, page)
            .setCacheMode(cacheMode)
            .toResponse<PagerResponse<List<ArticleResponse>>>()
            .await()
    }

    /**
     * 获取轮播图
     * @param url String
     * @param cacheMode CacheMode
     * @return PagerResponse<List<BannerResponse>>?
     */
    suspend fun getBanners(
        url: String,
        cacheMode: CacheMode
    ): List<BannerResponse> {
        return RxHttp.get(url)
            .setCacheMode(cacheMode)
            .toResponse<List<BannerResponse>>()
            .await()
    }

    /**
     * 获取搜索热词
     * @param cacheMode CacheMode
     * @return List<SearchResponse>?
     */
    suspend fun getHotKeys(cacheMode: CacheMode): List<SearchResponse> {
        return RxHttp.get(Url.HOT_KEYS)
            .setCacheMode(cacheMode)
            .toResponse<List<SearchResponse>>()
            .await()
    }

    /**
     * 获取积分记录
     * @param page Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<IntegralHistoryResponse>>?
     */
    suspend fun getIntegralHistory(
        page: Int,
        cacheMode: CacheMode
    ): PagerResponse<List<IntegralHistoryResponse>> {
        return RxHttp.get(Url.INTEGRAL_HISTORY, page)
            .setCacheMode(cacheMode)
            .setCacheValidTime(23 * 60 * 60 * 1000L) // 23个小时
            .toResponse<PagerResponse<List<IntegralHistoryResponse>>>()
            .await()
    }

    /**
     * 获取积分排行
     * @param page Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<IntegralResponse>>?
     */
    suspend fun getIntegralRank(
        page: Int,
        cacheMode: CacheMode
    ): PagerResponse<List<IntegralResponse>> {
        return RxHttp.get(Url.INTEGRAL_RANK, page)
            .setCacheMode(cacheMode)
            .toResponse<PagerResponse<List<IntegralResponse>>>()
            .await()
    }

    /**
     * 获取个人积分
     * @param cacheMode CacheMode
     * @return IntegralResponse?
     */
    fun getUserCoin(cacheMode: CacheMode): Await<IntegralResponse> {
        return RxHttp.get(Url.USER_COIN)
            .setCacheMode(cacheMode)
            .setCacheValidTime(-1)
            .toResponse<IntegralResponse>()
    }

    /**
     * 获取导航
     * @param cacheMode CacheMode
     * @return PagerResponse<List<NavigationResponse>>
     */
    suspend fun getNavigation(cacheMode: CacheMode): PagerResponse<List<NavigationResponse>> {
        val response = RxHttp.get(Url.NAVIGATION)
            .setCacheMode(cacheMode)
            .toResponse<List<NavigationResponse>>()
            .await()
        return PagerResponse<List<NavigationResponse>>().apply {
            datas = response
            curPage = 1
            pageCount = 1
        }
    }

    /**
     * 获取项目内容
     * @param page Int
     * @param id Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<ArticleResponse>>?
     */
    suspend fun getProjectList(
        page: Int,
        id: Int,
        cacheMode: CacheMode
    ): PagerResponse<List<ArticleResponse>> {
        return RxHttp.get(Url.PROJECT_DATA, page, id)
            .setCacheMode(cacheMode)
            .toResponse<PagerResponse<List<ArticleResponse>>>()
            .await()
    }

    /**
     * 获取项目分类
     * @return List<ClassifyResponse>?
     */
    fun getProjectItems() = RxHttp.get(Url.PROJECT_ITEM)
            .setCacheMode(getCacheMode(true))
            .toResponse<List<ClassifyResponse>>()

    /**
     * 获取体系
     * @param cacheMode CacheMode
     * @return PagerResponse<List<SystemResponse>>
     */
    suspend fun getTree(cacheMode: CacheMode): PagerResponse<List<SystemResponse>> {
        val response = RxHttp.get(Url.TREE)
            .setCacheMode(cacheMode)
            .toResponse<List<SystemResponse>>()
            .await()
        return PagerResponse<List<SystemResponse>>().apply {
            datas = response
            curPage = 1
            pageCount = 1
        }
    }

    /**
     * 获取体系数据
     * @param page Int
     * @param id Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<ArticleResponse>>?
     */
    suspend fun getTreeData(
        page: Int,
        id: Int,
        cacheMode: CacheMode
    ): PagerResponse<List<ArticleResponse>> {
        return RxHttp.get(Url.TREE_DATA, page, id)
            .setCacheMode(cacheMode)
            .toResponse<PagerResponse<List<ArticleResponse>>>()
            .await()
    }

    /**
     * 获取微信公众号
     * @param cacheMode CacheMode
     * @return List<ClassifyResponse>?
     */
    fun getWxGzh(cacheMode: CacheMode) = RxHttp.get(Url.WX_GZH)
        .setCacheMode(cacheMode)
        .toResponse<List<ClassifyResponse>>()

    /**
     * 获取微信公众号内容
     * @param page Int
     * @param id Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<ArticleResponse>>?
     */
    suspend fun getWxGzhItems(
        page: Int,
        id: Int,
        cacheMode: CacheMode
    ): PagerResponse<List<ArticleResponse>> {
        return RxHttp.get(Url.WX_GZH_DATA, id, page)
            .setCacheMode(cacheMode)
            .toResponse<PagerResponse<List<ArticleResponse>>>()
            .await()
    }

    /**
     * 搜索
     * @param page Int
     * @param key String
     * @param cacheMode CacheMode
     */
    suspend fun search(
        page: Int,
        key: String?,
        cacheMode: CacheMode
    ): PagerResponse<List<ArticleResponse>> {
        return RxHttp.postForm(Url.SEARCH, page)
            .add("k", key)
            .setCacheMode(cacheMode)
            .toResponse<PagerResponse<List<ArticleResponse>>>()
            .await()
    }

    /**
     * 分享文章
     * @param title String
     * @param link String
     * @return String?
     */
    fun shareArticle(title: String, link: String) = RxHttp.postForm(Url.SHARE_ARTICLE)
        .add("title", title)
        .add("link", link)
        .toResponse<String>()

    /**
     * 收藏文章
     * @param articleId Int
     * @param isCollect Boolean
     * @return CollectBus
     */
    suspend fun collectionArticle(articleId: Int, isCollect: Boolean): CollectBus {
        val url = if (isCollect) Url.UN_LIST_COLLECTION else Url.COLLECTION_ARTICLE
        RxHttp.postForm(url, articleId)
            .toResponse<String>()
            .await()
        return CollectBus(articleId, !isCollect)
    }

}