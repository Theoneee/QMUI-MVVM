package com.theone.demo.app.net

import rxhttp.wrapper.annotation.DefaultDomain


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
 * @date 2021/2/18 0018
 * @describe 请求接口
 * @email 625805189@qq.com
 * @remark
 */
object Url {

    /**
     * 设置为默认域名
     */
    @DefaultDomain
    const val BASE_URL = "https://www.wanandroid.com/"

    /**
     * 登录
     * @POST
     * @username 用户名
     * @password 登录密码
     */
    const val LOGIN = "user/login"

    /**
     * 注册
     * @POST
     * @username   用户名
     * @password   登录密码
     * @repassword 确认密码
     */
    const val REGISTER = "user/register"

    /**
     * 登出
     * @GET
     */
    const val LOGIN_OUT = "user/logout/json"

    /**
     * 获取个人积分
     * @GET
     */
    const val USER_COIN = "lg/coin/userinfo/json"

    /**
     * 首页文章
     * @GET
     * @page 页码
     */
    const val HOME_ARTICLE = "article/list/%d/json"

    /**
     * 首页Banner
     * @GET
     */
    const val HOME_BANNER = "banner/json"

    /**
     * 项目分类
     * @GET
     */
    const val PROJECT_ITEM = "project/tree/json"

    /**
     * 项目内容
     * @GET
     * @page 页码
     * @cid 二级目录的id
     * @remark 起始页  1
     */
    const val PROJECT_DATA = "project/list/%d/json?cid=%d"

    /**
     * 广场
     * @GET
     * @page 页码
     */
    const val PLAZA = "user_article/list/%d/json"

    /**
     * 问答
     * @GET
     * @page 页码
     */
    const val QA = "wenda/list/%d/json"

    /**
     * 体系
     * @GET
     */
    const val TREE = "tree/json"

    /**
     * 体系
     * @GET
     * @page 页码
     * @cid 二级目录的id
     */
    const val TREE_DATA = "article/list/%d/json?cid=%d"

    /**
     * 导航
     * @GET
     */
    const val NAVIGATION = "navi/json"

    /**
     * 微信公众号
     * @GET
     */
    const val WX_GZH = "wxarticle/chapters/json"

    /**
     * 公众号历史数据
     * @GET
     * @id 公众号ID
     * @page 页码
     */
    const val WX_GZH_DATA = "wxarticle/list/%d/%d/json"

    /**
     * 分享的文章
     * @POST
     * @title 分享的标题
     * @link 分享的链接
     */
    const val SHARE_ARTICLE = "lg/user_article/add/json"

    /**
     * 自己分享的文章
     * @GET
     * @page 页码
     * @remark 起始页  1
     */
    const val MY_SHARE_ARTICLE = "user/lg/private_articles/%d/json"

    /**
     * 删除自己分享的文章
     * @POST
     * @文章id
     */
    const val DELETE_SHARE_ARTICLE = "lg/user_article/delete/%d/json"

    /**
     * 积分排行榜
     * @GET
     * @page 页码
     * @remark 起始页  1
     */
    const val INTEGRAL_RANK = "coin/rank/%d/json"

    /**
     * 积分记录
     * @GET
     * @page 页码
     * @remark 起始页  1
     */
    const val INTEGRAL_HISTORY = "lg/coin/list/%d/json"

    /**
     * 积分规则
     */
    const val INTEGRAL_RULES = BASE_URL+"blog/show/2653"

    /**
     * 我收藏的文章
     * @GET
     * @page 页码
     */
    const val MY_COLLECTION_ARTICLES = " lg/collect/list/0/json"

    /**
     * 收藏站内文章
     * @POST
     * @id 文章id
     */
    const val COLLECTION_ARTICLE = "lg/collect/%d/json"

    /**
     * 收藏站外文章
     * @POST
     * @title  标题
     * @author 作者
     * @link   链接
     */
    const val COLLECTION_OUT_SITE_ARTICLE = "lg/collect/add/json"

    /**
     * 取消收藏-文章列表
     * @POST
     * @id  列表中文章的id
     */
    const val UN_LIST_COLLECTION = "lg/uncollect_originId/%d/json"

    /**
     * 取消收藏-我的收藏页面
     * @POST
     * @id  收藏文章的id
     * @originId 收藏之前的那篇文章本身的id； 但是收藏支持主动添加，这种情况下，没有originId则为-1
     */
    const val UN_MY_COLLECTION = "lg/uncollect/2805/json"

    /**
     * 获取搜索热词
     * @GET
     */
    const val HOT_KEYS = "hotkey/json"

    /**
     * 搜索
     * @POST
     * @page 页码
     * @k    搜索关键词(支持多个关键词，用空格隔开)
     */
    const val SEARCH = "article/query/%d/json"

}