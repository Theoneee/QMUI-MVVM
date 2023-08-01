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

    enum class Type{
        IMAGE,
        VIDEO,
        AUDIO
    }

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

    fun getFileName():String = ""

    fun mineType():String

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

    /**
     * 设置宽度
     * @param size Int
     */
    fun setWidth(size:Int){

    }
    /**
     * 设置高度
     * @param size Int
     */
    fun setHeight(size:Int){

    }

}