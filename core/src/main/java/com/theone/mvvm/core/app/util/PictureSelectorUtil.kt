package com.theone.mvvm.core.app.util

import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.theone.mvvm.core.app.util.glide.GlideEngine

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
 * @date 2021-05-06 10:50
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
object PictureSelectorUtil {

    fun initIcon(fragment: Fragment,listener: OnResultCallbackListener<LocalMedia>){
        initImageSelector(fragment,listener,false,4,PictureConfig.SINGLE,true,1, arrayListOf(),isCut = true)
    }

    fun initImageSelector(
        fragment: Fragment,
        listener: OnResultCallbackListener<LocalMedia>,
        showCamera: Boolean = false,
        spanCount: Int = 4,
        mode: Int = PictureConfig.MULTIPLE,
        singleReturn: Boolean = false,
        maxSelectNum: Int = 9,
        selectList: List<LocalMedia>,
        isCut: Boolean = false,
        cropWidth: Int = 200,
        cropHeight: Int = 200,
        circle: Boolean = false,
        isCompress: Boolean = false,
        compressQuality: Int = 30,
        minCompressSize: Int = 50,
        openClickSound: Boolean = false,
        compressPath: String? = FileDirectoryManager.getCompressPath(),
        outPath: String? = FileDirectoryManager.getCacheChildFilePath("Camera")
    ) {

        initSelector(
            fragment,
            PictureMimeType.ofImage(),
            showCamera,
            spanCount,
            mode,
            singleReturn,
            maxSelectNum,
            selectList,
            isCut,
            cropWidth,
            cropHeight,
            circle,
            isCompress,
            compressQuality,
            minCompressSize,
            openClickSound,
            0,
            0,
            0,
            compressPath,
            outPath,
            listener
        )

    }

    fun initVideoSelector(
        fragment: Fragment,
        listener: OnResultCallbackListener<LocalMedia>,
        showCamera: Boolean = false,
        spanCount: Int = 4,
        mode: Int = PictureConfig.SINGLE,
        singleReturn: Boolean = true,
        maxSelectNum: Int = 1,
        selectList: List<LocalMedia>,
        openClickSound: Boolean = false,
        videoMaxSecond: Int = 1000,
        videoMinSecond: Int = 1,
        recordSecond: Int = 60,
        compressPath: String? = FileDirectoryManager.getCompressPath(),
        outPath: String? = FileDirectoryManager.getCacheChildFilePath("Camera")
    ) {

        initSelector(
            fragment,
            PictureMimeType.ofVideo(),
            showCamera,
            spanCount,
            mode,
            singleReturn,
            maxSelectNum,
            selectList,
            false,
            200,
            200,
            false,
            false,
            50,
            50,
            openClickSound,
            videoMaxSecond,
            videoMinSecond,
            recordSecond,
            compressPath,
            outPath,
            listener
        )

    }


    private fun initSelector(
        fragment: Fragment,
        type: Int,
        showCamera: Boolean,
        spanCount: Int,
        mode: Int,
        singleReturn: Boolean,
        maxSelectNum: Int,
        selectList: List<LocalMedia>,
        isCut: Boolean,
        cropWidth: Int,
        cropHeight: Int,
        circle: Boolean,
        isCompress: Boolean,
        compressQuality: Int,
        minCompressSize: Int,
        openClickSound: Boolean,
        videoMaxSecond: Int,
        videoMinSecond: Int,
        recordSecond: Int,
        compressPath: String?,
        outPath: String?,
        listener: OnResultCallbackListener<LocalMedia>
    ) {
        PictureSelector.create(fragment)
            .openGallery(type)
            .imageEngine(GlideEngine.createGlideEngine())
            .isCamera(showCamera)
            // 每行显示个数 int
            .imageSpanCount(spanCount)
            // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
            .selectionMode(mode)
            .isEnableCrop(isCut)
            .cropImageWideHigh(cropWidth,cropHeight)
            .isSingleDirectReturn(singleReturn)
            .selectionData(selectList)
            .maxSelectNum(maxSelectNum)
            // 是否压缩 true or false
            .isCompress(isCompress)
            .compressQuality(compressQuality)
            .cutOutQuality(compressQuality)
            .withAspectRatio(1, 1)
            .hideBottomControls(true)
            // 是否开启点击声音 true or false
            .isOpenClickSound(openClickSound)
            //压缩图片保存地址
            .compressSavePath(compressPath)
            // 裁剪框是否可拖拽 true or false
            .freeStyleCropEnabled(true)
            .circleDimmedLayer(circle)
            // 小于100kb的图片不压缩
            .minimumCompressSize(minCompressSize)
            .setOutputCameraPath(outPath)
            // 显示多少秒以内的视频or音频也可适用 int
            .videoMaxSecond(videoMaxSecond)
            // 显示多少秒以内的视频or音频也可适用 int
            .videoMinSecond(videoMinSecond)
            //视频秒数录制 默认60s int
            .recordVideoSecond(recordSecond)
            .forResult(listener)
    }

}
