package com.theone.mvvm.core.base.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.config.PictureMimeType;
import com.theone.common.callback.IImageUrl;
import com.theone.mvvm.core.R;
import com.theone.mvvm.core.base.adapter.holder.TheBasePreviewHolder;
import com.theone.mvvm.core.base.adapter.holder.ThePreviewAudioHolder;
import com.theone.mvvm.core.base.adapter.holder.ThePreviewImageHolder;
import com.theone.mvvm.core.base.adapter.holder.ThePreviewVideoHolder;

import java.util.LinkedHashMap;

/**
 * @author The one
 * @date 2021/5/20 0023
 * @describe
 * @email 625805189@qq.com
 * @remark
 */
public class ThePicturePreviewAdapter<T extends IImageUrl> extends BaseQuickAdapter<T, TheBasePreviewHolder>  {

    private final TheBasePreviewHolder.OnPreviewEventListener onPreviewEventListener;
    private final LinkedHashMap<Integer, TheBasePreviewHolder> mHolderCache = new LinkedHashMap<>();
    private ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_CENTER;

    public ThePicturePreviewAdapter(TheBasePreviewHolder.OnPreviewEventListener onPreviewEventListener) {
        super(0);
        this.onPreviewEventListener = onPreviewEventListener;
    }

    public TheBasePreviewHolder getCurrentHolder(int position) {
        return mHolderCache.get(position);
    }

    @NonNull
    @Override
    public TheBasePreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BasePreviewHolder.ADAPTER_TYPE_VIDEO) {
            return TheBasePreviewHolder.generate(parent, viewType, R.layout.ps_preview_video);
        } else if (viewType == BasePreviewHolder.ADAPTER_TYPE_AUDIO) {
            return TheBasePreviewHolder.generate(parent, viewType, R.layout.ps_preview_audio);
        } else {
            return TheBasePreviewHolder.generate(parent, viewType, R.layout.item_preview_image);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TheBasePreviewHolder holder, int position) {
        if (null != onPreviewEventListener) {
            holder.setOnPreviewEventListener(onPreviewEventListener);
        }
        holder.clearCache();
        IImageUrl media = getData().get(position);
        mHolderCache.put(position, holder);
        holder.bindData(media, position);
        holder.setImageViewScaleType(scaleType);
    }

    @Override
    public int getItemViewType(int position) {
        String mineType = getData().get(position).mineType();
        if (PictureMimeType.isHasVideo(mineType)) {
            return BasePreviewHolder.ADAPTER_TYPE_VIDEO;
        } else if (PictureMimeType.isHasAudio(mineType)) {
            return BasePreviewHolder.ADAPTER_TYPE_AUDIO;
        } else {
            return BasePreviewHolder.ADAPTER_TYPE_IMAGE;
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull TheBasePreviewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull TheBasePreviewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }

    /**
     * 设置播放按钮状态
     *
     * @param position
     */
    public void setVideoPlayButtonUI(int position) {
        TheBasePreviewHolder currentHolder = getCurrentHolder(position);
        if (currentHolder instanceof ThePreviewVideoHolder) {
            ThePreviewVideoHolder videoHolder = (ThePreviewVideoHolder) currentHolder;
            if (videoHolder.ivPlayButton.getVisibility() == View.GONE) {
                videoHolder.ivPlayButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setImageViewScaleType(ImageView.ScaleType scaleType){
        this.scaleType = scaleType;
    }


    /**
     * 释放当前视频相关
     */
    public void destroy() {
        for (Integer key : mHolderCache.keySet()) {
            TheBasePreviewHolder holder = mHolderCache.get(key);
            if (holder instanceof ThePreviewVideoHolder) {
                ThePreviewVideoHolder videoHolder = (ThePreviewVideoHolder) holder;
                videoHolder.releaseVideo();
            } else if (holder instanceof ThePreviewAudioHolder) {
                ThePreviewAudioHolder audioHolder = (ThePreviewAudioHolder) holder;
                audioHolder.releaseAudio();
            }
        }
    }

    @Override
    protected void convert(@NonNull TheBasePreviewHolder theBasePreviewHolder, T t) {

    }

}
