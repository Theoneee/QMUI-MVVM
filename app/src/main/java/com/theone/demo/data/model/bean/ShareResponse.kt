package com.theone.demo.data.model.bean

import com.theone.demo.data.net.PagerResponse
import java.io.Serializable

data class ShareResponse(
    var coinInfo: CoinInfoResponse,
    var shareArticles: PagerResponse<List<ArticleResponse>>
) : Serializable