package com.theone.common.callback

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
 * @date 2021-04-25 16:05
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
interface IImageUrl {

    /**
     * @return 图片地址
     */
    fun getImageUrl(): String

    /**
     * @return 缩略图
     */
    fun getThumbnail(): String? = null

    /**
     * @return 有些图片需要加refer，没有就不用管
     */
    fun getRefer(): String? = null

    /**
     * @return 是否为视频，如果为视频，视频播放图标会显示
     */
    fun isVideo(): Boolean = false

    /**
     * 图片的宽度
     * @return
     */
    fun getWidth(): Int = 0

    /**
     * 图片的高度
     * @return
     */
    fun getHeight(): Int = 0

}