package com.theone.demo.domain.work

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.theone.demo.data.net.Url
import com.theone.demo.data.repository.ApiRepository
import okhttp3.internal.wait
import rxhttp.awaitResult
import rxhttp.wrapper.cahce.CacheMode

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
 * @date 2022-06-14 08:06
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class LoginSignWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    companion object {

        const val TAG = "com.theone.demo.domain.work.LoginSignWorker"

        const val ACCOUNT = "worker_account"
        const val PASSWORD = "worker_password"

    }

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        return try {
            val account = inputData.getString(ACCOUNT)
            val password = inputData.getString(PASSWORD)
            if (account.isNullOrEmpty() || password.isNullOrEmpty()) {
                Result.Failure()
            } else {
                ApiRepository.INSTANCE.loginOrRegister(account, password, false).await()
                ApiRepository.INSTANCE.getArticles(Url.HOME_ARTICLE,1, CacheMode.NETWORK_SUCCESS_WRITE_CACHE)
                Result.success()
            }
        } catch (e: Exception) {
            Result.Failure()
        }
    }

}