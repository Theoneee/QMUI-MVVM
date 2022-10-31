package com.theone.demo.data.repository

import com.theone.demo.app.ext.getCacheMode
import com.theone.demo.data.net.PagerResponse
import com.theone.demo.data.net.Url
import com.theone.demo.data.model.bean.*
import rxhttp.wrapper.cache.CacheMode
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toAwaitResponse

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

    fun loginOrRegister(
        account: String,
        password: String,
        repassword: String? = null
    ) = RxHttp.postForm(if (repassword.isNullOrEmpty()) Url.REGISTER else Url.LOGIN)
        .add("username", account)
        .add("password", password)
        .add("repassword",repassword)
        .toAwaitResponse<UserInfo>()

    /**
     * 登出
     * @return String?
     */
    fun loginOut() = RxHttp.get(Url.LOGIN_OUT)
        .toAwaitResponse<String>()

    /**
     * 获取自己分享的文章
     * @param page Int
     * @param cacheMode CacheMode
     * @return ShareResponse?
     */
    fun getMyShareArticle(page: Int, cacheMode: CacheMode) =
        RxHttp.get(Url.MY_SHARE_ARTICLE, page)
            .setCacheMode(cacheMode)
            .toAwaitResponse<ShareResponse>()

    /**
     *
     * @param url String
     * @param page Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<ArticleResponse>>?
     */
    fun getArticles(
        url: String?,
        page: Int,
        cacheMode: CacheMode
    ) = RxHttp.get(url, page)
        .setCacheMode(cacheMode)
        .toAwaitResponse<PagerResponse<List<ArticleResponse>>>()

    /**
     * 获取轮播图
     * @param url String
     * @param cacheMode CacheMode
     * @return PagerResponse<List<BannerResponse>>?
     */
    fun getBanners(
        url: String,
        cacheMode: CacheMode
    ) = RxHttp.get(url)
        .setCacheMode(cacheMode)
        .toAwaitResponse<List<BannerResponse>>()

    /**
     * 获取搜索热词
     * @param cacheMode CacheMode
     * @return List<SearchResponse>?
     */
    fun getHotKeys(cacheMode: CacheMode) = RxHttp.get(Url.HOT_KEYS)
        .setCacheMode(cacheMode)
        .toAwaitResponse<List<SearchResponse>>()

    /**
     * 获取积分记录
     * @param page Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<IntegralHistoryResponse>>?
     */
    fun getIntegralHistory(
        page: Int,
        cacheMode: CacheMode
    ) = RxHttp.get(Url.INTEGRAL_HISTORY, page)
        .setCacheMode(cacheMode)
        .setCacheValidTime(23 * 60 * 60 * 1000L) // 23个小时
        .toAwaitResponse<PagerResponse<List<IntegralHistoryResponse>>>()

    /**
     * 获取积分排行
     * @param page Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<IntegralResponse>>?
     */
    fun getIntegralRank(
        page: Int,
        cacheMode: CacheMode
    ) = RxHttp.get(Url.INTEGRAL_RANK, page)
        .setCacheMode(cacheMode)
        .toAwaitResponse<PagerResponse<List<IntegralResponse>>>()

    /**
     * 获取个人积分
     * @param cacheMode CacheMode
     * @return IntegralResponse?
     */
    fun getUserCoin(cacheMode: CacheMode) = RxHttp.get(Url.USER_COIN)
        .setCacheMode(cacheMode)
        .setCacheValidTime(Long.MAX_VALUE)
        .toAwaitResponse<IntegralResponse>()

    /**
     * 获取导航
     * @param cacheMode CacheMode
     * @return PagerResponse<List<NavigationResponse>>
     */
     fun getNavigation(cacheMode: CacheMode) = RxHttp.get(Url.NAVIGATION)
            .setCacheMode(cacheMode)
            .toAwaitResponse<List<NavigationResponse>>()

    /**
     * 获取项目内容
     * @param page Int
     * @param id Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<ArticleResponse>>?
     */
    fun getProjectList(
        page: Int,
        id: Int,
        cacheMode: CacheMode
    )= RxHttp.get(Url.PROJECT_DATA, page, id)
            .setCacheMode(cacheMode)
            .toAwaitResponse<PagerResponse<List<ArticleResponse>>>()

    /**
     * 获取项目分类
     * @return List<ClassifyResponse>?
     */
    fun getProjectItems() = RxHttp.get(Url.PROJECT_ITEM)
        .setCacheMode(getCacheMode(true))
        .toAwaitResponse<List<ClassifyResponse>>()

    /**
     * 获取体系
     * @param cacheMode CacheMode
     * @return PagerResponse<List<SystemResponse>>
     */
    fun getTree(cacheMode: CacheMode)= RxHttp.get(Url.TREE)
            .setCacheMode(cacheMode)
            .toAwaitResponse<List<SystemResponse>>()


    /**
     * 获取体系数据
     * @param page Int
     * @param id Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<ArticleResponse>>?
     */
    fun getTreeData(
        page: Int,
        id: Int,
        cacheMode: CacheMode
    )= RxHttp.get(Url.TREE_DATA, page, id)
            .setCacheMode(cacheMode)
            .toAwaitResponse<PagerResponse<List<ArticleResponse>>>()

    /**
     * 获取微信公众号
     * @param cacheMode CacheMode
     * @return List<ClassifyResponse>?
     */
    fun getWxGzh(cacheMode: CacheMode) = RxHttp.get(Url.WX_GZH)
        .setCacheMode(cacheMode)
        .toAwaitResponse<List<ClassifyResponse>>()

    /**
     * 获取微信公众号内容
     * @param page Int
     * @param id Int
     * @param cacheMode CacheMode
     * @return PagerResponse<List<ArticleResponse>>?
     */
    fun getWxGzhItems(
        page: Int,
        id: Int,
        cacheMode: CacheMode
    )= RxHttp.get(Url.WX_GZH_DATA, id, page)
            .setCacheMode(cacheMode)
            .toAwaitResponse<PagerResponse<List<ArticleResponse>>>()

    /**
     * 搜索
     * @param page Int
     * @param key String
     * @param cacheMode CacheMode
     */
    fun search(
        page: Int,
        key: String?,
        cacheMode: CacheMode
    )= RxHttp.postForm(Url.SEARCH, page)
            .add("k", key)
            .setCacheMode(cacheMode)
            .toAwaitResponse<PagerResponse<List<ArticleResponse>>>()

    /**
     * 分享文章
     * @param title String
     * @param link String
     * @return String?
     */
    fun shareArticle(title: String, link: String) = RxHttp.postForm(Url.SHARE_ARTICLE)
        .add("title", title)
        .add("link", link)
        .toAwaitResponse<String>()

    /**
     * 收藏文章
     * @param articleId Int
     * @param isCollect Boolean
     * @return CollectBus
     */
    fun collectionArticle(articleId: Int, isCollect: Boolean)=
        RxHttp.postForm(if (isCollect) Url.UN_LIST_COLLECTION else Url.COLLECTION_ARTICLE, articleId)
            .toAwaitResponse<String>()


}