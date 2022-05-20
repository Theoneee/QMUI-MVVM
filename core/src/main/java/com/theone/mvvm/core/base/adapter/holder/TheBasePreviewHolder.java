package com.theone.mvvm.core.base.adapter.holder;

import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.utils.BitmapUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.MediaUtils;
import com.theone.common.callback.IImageUrl;
import com.theone.mvvm.core.app.util.glide.GlideEngine;


public class TheBasePreviewHolder extends BaseViewHolder {

    /**
     * 图片
     */
    public final static int ADAPTER_TYPE_IMAGE = 1;
    /**
     * 视频
     */
    public final static int ADAPTER_TYPE_VIDEO = 2;

    /**
     * 音频
     */
    public final static int ADAPTER_TYPE_AUDIO = 3;

    protected final int screenWidth;
    protected final int screenHeight;
    protected final int screenAppInHeight;
    protected IImageUrl media;
    protected final ImageEngine imageEngine;
    public PhotoView coverImageView;


    public static TheBasePreviewHolder generate(ViewGroup parent, int viewType, int resource) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        if (viewType == ADAPTER_TYPE_VIDEO) {
            return new ThePreviewVideoHolder(itemView);
        } else if (viewType == ADAPTER_TYPE_AUDIO) {
            return new ThePreviewAudioHolder(itemView);
        } else {
            return new ThePreviewImageHolder(itemView);
        }
    }

    public TheBasePreviewHolder(@NonNull View itemView) {
        super(itemView);
        imageEngine = GlideEngine.createGlideEngine();
        this.screenWidth = DensityUtil.getRealScreenWidth(itemView.getContext());
        this.screenHeight = DensityUtil.getScreenHeight(itemView.getContext());
        this.screenAppInHeight = DensityUtil.getRealScreenHeight(itemView.getContext());
        findViews(itemView);
    }

    protected void findViews(View itemView) {
        this.coverImageView = itemView.findViewById(com.luck.picture.lib.R.id.preview_image);
    }

    /**
     * bind Data
     *
     * @param media
     * @param position
     */
    public void bindData(IImageUrl media, int position) {
        this.media = media;
        int[] size = getSize(media);
        int[] maxImageSize = BitmapUtils.getMaxImageSize(size[0], size[1]);
        loadImageBitmap(media, maxImageSize[0], maxImageSize[1]);
        setScaleDisplaySize(media);
        setOnClickEventListener();
        setOnLongClickEventListener();
    }

    protected void setOnClickEventListener() {
        coverImageView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (mPreviewEventListener != null) {
                    mPreviewEventListener.onPressed();
                }
            }
        });
    }

    protected void setOnLongClickEventListener() {
        coverImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mPreviewEventListener != null) {
                    mPreviewEventListener.onLongPressDownload(media);
                }
                return false;
            }
        });
    }

    protected void loadImageBitmap(final IImageUrl media, int maxWidth, int maxHeight) {
        imageEngine.loadImageBitmap(coverImageView.getContext(), media.getImageUrl(), maxWidth, maxHeight,
                new OnCallbackListener<Bitmap>() {
                    @Override
                    public void onCall(Bitmap bitmap) {
                        loadBitmapCallback(media, bitmap);
                    }
                });
    }

    protected void loadBitmapCallback(IImageUrl media, Bitmap bitmap) {
        String path = media.getImageUrl();
        if (bitmap == null) {
            mPreviewEventListener.onLoadError();
        } else {
            if (PictureMimeType.isUrlHasWebp(path)
                    || PictureMimeType.isUrlHasGif(path)) {
                if (imageEngine != null) {
                    coverImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageEngine.loadImage(coverImageView.getContext(), path, coverImageView);
                }
            } else {
                setImageViewBitmap(bitmap);
            }
            if (media.getWidth() <= 0) {
                media.setWidth(bitmap.getWidth());
            }
            if (media.getHeight() <= 0) {
                media.setHeight(bitmap.getHeight());
            }
            int width, height;
            ImageView.ScaleType scaleType;
            if (MediaUtils.isLongImage(bitmap.getWidth(), bitmap.getHeight())) {
                scaleType = ImageView.ScaleType.CENTER_CROP;
                width = screenWidth;
                height = screenHeight;
            } else {
                scaleType = ImageView.ScaleType.FIT_CENTER;
                int[] size = getSize(media);
                boolean isHaveSize = bitmap.getWidth() > 0 && bitmap.getHeight() > 0;
                width = isHaveSize ? bitmap.getWidth() : size[0];
                height = isHaveSize ? bitmap.getHeight() : size[1];
            }
            mPreviewEventListener.onLoadComplete(width, height, new OnCallbackListener<Boolean>() {
                @Override
                public void onCall(Boolean isBeginEffect) {
                    coverImageView.setScaleType(isBeginEffect ? ImageView.ScaleType.CENTER_CROP : scaleType);
                }
            });
        }
    }

    protected void setImageViewBitmap(Bitmap bitmap) {
        coverImageView.setImageBitmap(bitmap);
    }

    protected int[] getSize(IImageUrl media) {
        return new int[]{media.getWidth(), media.getHeight()};
    }

    protected void setScaleDisplaySize(IImageUrl media) {
        if ( screenWidth < screenHeight) {
            if (media.getWidth() > 0 && media.getHeight() > 0) {
                float ratio = (float) media.getWidth() / (float) media.getHeight();
                int displayHeight = (int) (screenWidth / ratio);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) coverImageView.getLayoutParams();
                layoutParams.width = screenWidth;
                layoutParams.height = displayHeight > screenHeight ? screenAppInHeight : screenHeight;
                layoutParams.gravity = Gravity.CENTER;
            }
        }
    }

    /**
     * onViewAttachedToWindow
     */
    public void onViewAttachedToWindow() {

    }

    /**
     * onViewDetachedFromWindow
     */
    public void onViewDetachedFromWindow() {

    }

    protected TheBasePreviewHolder.OnPreviewEventListener mPreviewEventListener;

    public void setOnPreviewEventListener(TheBasePreviewHolder.OnPreviewEventListener listener) {
        this.mPreviewEventListener = listener;
    }

    public interface OnPreviewEventListener {

        void onLoadComplete(int width, int height, OnCallbackListener<Boolean> call);

        void onLoadError();

        void onPressed();

        void onPreviewVideoTitle(String videoName);

        void onLongPressDownload(IImageUrl media);
    }

}