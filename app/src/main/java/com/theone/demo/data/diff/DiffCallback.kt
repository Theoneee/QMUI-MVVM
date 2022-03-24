package com.theone.demo.data.diff

import androidx.recyclerview.widget.DiffUtil
import com.theone.demo.data.model.bean.*

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
 * @date 2022-01-24 14:24
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object DiffCallback {

    val ARTICLE = object : DiffUtil.ItemCallback<ArticleResponse>(){

        override fun areItemsTheSame(oldItem: ArticleResponse, newItem: ArticleResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ArticleResponse,
            newItem: ArticleResponse
        ): Boolean {
            return oldItem.collect == newItem.collect
        }

    }

    val INTEGRAL_HISTORY = object : DiffUtil.ItemCallback<IntegralHistoryResponse>(){

        override fun areItemsTheSame(oldItem: IntegralHistoryResponse, newItem: IntegralHistoryResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: IntegralHistoryResponse,
            newItem: IntegralHistoryResponse
        ): Boolean {
            return oldItem.coinCount == newItem.coinCount
        }

    }


    val INTEGRAL = object : DiffUtil.ItemCallback<IntegralResponse>(){

        override fun areItemsTheSame(oldItem: IntegralResponse, newItem: IntegralResponse): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(
            oldItem: IntegralResponse,
            newItem: IntegralResponse
        ): Boolean {
            return oldItem.coinCount == newItem.coinCount
        }

    }


    val NAVIGATION = object : DiffUtil.ItemCallback<NavigationResponse>(){

        override fun areItemsTheSame(oldItem: NavigationResponse, newItem: NavigationResponse): Boolean {
            return oldItem.cid == newItem.cid
        }

        override fun areContentsTheSame(
            oldItem: NavigationResponse,
            newItem: NavigationResponse
        ): Boolean {
            return oldItem.articles.size == newItem.articles.size
        }

    }


    val SYSTEM = object : DiffUtil.ItemCallback<SystemResponse>(){

        override fun areItemsTheSame(oldItem: SystemResponse, newItem: SystemResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SystemResponse,
            newItem: SystemResponse
        ): Boolean {
            return oldItem.children.size == newItem.children.size
        }

    }


    val SEARCH = object : DiffUtil.ItemCallback<String>(){

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return true
        }

    }


}