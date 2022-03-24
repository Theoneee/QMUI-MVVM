package com.theone.demo.ui.fragment.base

import android.view.View
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.theone.demo.R
import com.theone.demo.app.util.CacheUtil
import com.theone.demo.app.ext.checkLogin
import com.theone.demo.data.model.bean.ArticleResponse
import com.theone.demo.ui.adapter.ArticleAdapter
import com.theone.demo.ui.fragment.collection.CollectionArticleFragment
import com.theone.demo.ui.fragment.web.WebExplorerFragment
import com.theone.demo.viewmodel.ArticleViewModel
import com.theone.mvvm.ext.qmui.showFailTipsDialog


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
 * @date 2021/3/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
abstract class BaseArticleFragment<VM : ArticleViewModel> :
    BasePagerDbFragment<ArticleResponse, VM>(),
    OnItemChildClickListener {

    override fun getViewModelIndex(): Int = 0

    override fun createAdapter(): ArticleAdapter = ArticleAdapter()

    override fun initAdapter() {
        super.initAdapter()
        mAdapter.addChildClickViewIds(R.id.collection)
        mAdapter.setOnItemChildClickListener(this)
    }

    override fun createObserver() {
        super.createObserver()
        val isCollection = this is CollectionArticleFragment
        mAppVm.run {
            // 监听用户登录、登出时，改变收藏
            userInfo.observe(this@BaseArticleFragment){
                if (it != null) {
                    it.collectIds.forEach { id ->
                        // 以用户信息里的为准，请求的数据可能是缓存里的，没有更新
                        for (item in mAdapter.data) {
                            if (id.toInt() == item.id) {
                                item.collect = true
                                break
                            }
                        }
                    }
                } else {
                    for (item in mAdapter.data) {
                        item.collect = false
                    }
                }
                mAdapter.notifyDataSetChanged()
            }
            collectEvent.observe(this@BaseArticleFragment){
                for (index in mAdapter.data.indices) {
                    val articleId = mAdapter.data[index].getArticleId()
                    if (articleId == it.id) {
                        mAdapter.data[index].collect = it.collect
                        if (isCollection) {
                            mAdapter.data.removeAt(index)
                            mAdapter.notifyItemRemoved(index)
                        } else {
                            mAdapter.notifyItemChanged(index + mAdapter.headerLayoutCount)
                        }
                        // 操作过后应该更新本地的用户信息里的收藏
                        CacheUtil.setUser(
                            mAppVm.userInfo.value?.apply {
                            if (it.collect)
                                collectIds.add(articleId.toString())
                            else
                                collectIds.remove(articleId.toString())
                        })
                        break
                    }
                }
            }

        }

        getViewModel().getCollectionError().observe(this, Observer {
            showFailTipsDialog(it)
        })
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val article = adapter.getItem(position) as ArticleResponse
        startFragment(WebExplorerFragment.newInstance(article))
    }

    override fun onItemChildClick(
        adapter: BaseQuickAdapter<*, *>, view: View, position: Int
    ) {
        checkLogin {
            val article = adapter.getItem(position) as ArticleResponse
            getViewModel().collection(article, mAppVm)
        }
    }

}