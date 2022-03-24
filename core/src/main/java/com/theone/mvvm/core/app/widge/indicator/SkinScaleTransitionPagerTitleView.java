package com.theone.mvvm.core.app.widge.indicator;//  ┏┓　　　┏┓
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

import android.content.Context;

/**
 * @author The one
 * @date 2022-01-10 13:15
 * @describe 带有缩放效果
 * @email 625805189@qq.com
 * @remark
 */
public class SkinScaleTransitionPagerTitleView extends SkinPagerTitleView {

    /**
     * 建议在 0.75 - 0.9 之间
     */
    private float mScale = 0.85f;
    /**
     * 是否需要对字号进行放大处理
     */
    private boolean isScaleUpTestSize = true;

    public SkinScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    /**
     * 这里需要对字号处理下
     * 如果不处理：
     * 如：字号设置的15sp,被选中的是【正常】的15sp,而其他的是被缩小了[mMinScale]倍
     * 我们想要的是：
     * 普通的字号就是15sp,而被选中的则应该是[15/mMinScale]
     *
     * @param unit
     * @param size
     */
    @Override
    public void setTextSize(int unit, float size) {
        float scale = 1.0f;
        if(isScaleUpTestSize){
            scale = mScale;
        }
        super.setTextSize(unit, size / scale);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);
        setScaleX(mScale + (1.0f - mScale) * enterPercent);
        setScaleY(mScale + (1.0f - mScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);
        setScaleX(1.0f + (mScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mScale - 1.0f) * leavePercent);
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float minScale) {
        mScale = minScale;
    }

    public boolean isScaleUpTestSize() {
        return isScaleUpTestSize;
    }

    public void setScaleUpTestSize(boolean scaleUpTestSize) {
        isScaleUpTestSize = scaleUpTestSize;
    }
}
