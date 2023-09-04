package com.theone.mvvm.core.app.util

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.theone.common.ext.toBitmap
import com.theone.mvvm.core.app.appContext
import com.theone.mvvm.core.R
import com.theone.common.ext.getAppIcon

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
 * @date 2021-05-08 13:40
 * @describe 通知管理器
 * @email 625805189@qq.com
 * @remark 根据自行需要更改 ID 和 名称
 */
class NotificationManager private constructor() {

    companion object {
        /**
         * 重要等级渠道ID
         */
        const val LEVEL_HIGH_CHANNEL_ID = "high"

        /**
         * 重要等级渠道名称
         */
        const val LEVEL_HIGH_CHANNEL_NAME = "重要消息"

        /**
         * 默认等级渠道ID
         */
        const val LEVEL_DEFAULT_CHANNEL_ID = "default"

        /**
         * 默认等级渠道名称
         */
        const val LEVEL_DEFAULT_CHANNEL_NAME = "默认消息"

        private var INSTANCE: NotificationManager? = null

        fun getInstance(): NotificationManager {
            if (null == INSTANCE) {
                synchronized(NotificationManager::class) {
                    if (null == INSTANCE) {
                        INSTANCE = NotificationManager()
                    }
                }
            }
            return INSTANCE!!
        }

    }

    private val mNotificationManager: android.app.NotificationManager by lazy {
        appContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as android.app.NotificationManager
    }

    /**
     * 注册 8.0 通知栏等级，应用开始时调用
     */
    fun register() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                LEVEL_HIGH_CHANNEL_ID,
                LEVEL_HIGH_CHANNEL_NAME,
                android.app.NotificationManager.IMPORTANCE_HIGH
            )
            createNotificationChannel(
                LEVEL_DEFAULT_CHANNEL_ID,
                LEVEL_DEFAULT_CHANNEL_NAME,
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            )
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        channelId: String,
        channelName: String,
        importance: Int
    ) {
        val channel = NotificationChannel(channelId, channelName, importance)
        mNotificationManager.createNotificationChannel(channel)
    }

    /**
     * 删除通知渠道
     * 会在通知设置界面显示所有被删除的通知渠道数量，会导致不美观，不推荐使用
     * @param id
     */
    fun deleteNotificationChannel(id: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.deleteNotificationChannel(id)
        }
    }

    /**
     * 创建通知
     *
     * @param id           渠道ID
     * @param level        通知等级
     * @param ticker       提示语
     * @param title        标题
     * @param content      内容
     * @param showWhen     是否显示时间
     * @param smallIcon    小ICON
     */
    fun createNotification(
        id: Int,
        ticker: String?,
        title: String?,
        content: String?,
        level: String = LEVEL_DEFAULT_CHANNEL_ID,
        showWhen: Boolean = true,
        smallIcon: Int = R.drawable.service_down
    ): NotificationCompat.Builder {
        val builder =
            NotificationCompat.Builder(
                appContext,
                level
            )
        if (null != ticker) builder.setTicker(ticker)
        if (null != title) builder.setContentTitle(title)
        if (null != content) builder.setContentText(content)
        if (showWhen) builder.setWhen(System.currentTimeMillis())
        builder.setOnlyAlertOnce(true)
        setNotificationIcon(builder, smallIcon)
        mNotificationManager.notify(id, builder.build())
        return builder
    }

    /**
     * 设置通知栏Icon
     * @param builder
     */
    private fun setNotificationIcon(
        builder: NotificationCompat.Builder,
        id: Int
    ) {
        //解决5.0系统通知栏白色Icon的问题
        val appIcon = appContext.getAppIcon()
        var drawableToBitmap: Bitmap? = null
        if (appIcon != null) {
            drawableToBitmap = appIcon.toBitmap()
        }
        if (drawableToBitmap != null) {
            builder.setSmallIcon(id)
            builder.setLargeIcon(drawableToBitmap)
        } else {
            builder.setSmallIcon(appContext.applicationInfo?.icon!!)
        }
    }

    /**
     * 更新
     * @param ID
     * @param builder
     */
    fun notify(id: Int, builder: NotificationCompat.Builder) {
        mNotificationManager.notify(id, builder.build())
    }

}