package com.theone.mvvm.core.base.adapter.holder;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.theone.common.callback.IImageUrl;
import com.theone.mvvm.core.R;
import com.theone.mvvm.core.app.widge.ProgressWheel;


public class ThePreviewImageHolder extends TheBasePreviewHolder {

    private ProgressWheel progressBar;

    public ThePreviewImageHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews(View itemView) {
        super.findViews(itemView);
        this.progressBar = itemView.findViewById(R.id.progressWheel);
    }

    @Override
    public void bindData(IImageUrl media, int position) {
        if(getPosition() == position){
            progressBar.spin();
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
        super.bindData(media, position);
    }

    @Override
    protected void loadBitmapCallback(IImageUrl media, Bitmap bitmap) {
        if(progressBar.isSpinning()){
            progressBar.stopSpinning();
            progressBar.setVisibility(View.GONE);
        }
        super.loadBitmapCallback(media, bitmap);
    }
}
