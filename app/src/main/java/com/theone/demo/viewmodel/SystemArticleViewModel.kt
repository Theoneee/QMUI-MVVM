package com.theone.demo.viewmodel

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
 * @date 2021/3/4 0004
 * @describe 体系文章
 * @email 625805189@qq.com
 * @remark
 */
class SystemArticleViewModel:ArticleViewModel() {

    var mId :Int = 0

    override fun requestServer() {
       request({
            onSuccess(ApiRepository.INSTANCE.getTreeData(page,mId,getCacheMode()))
        })
    }

}