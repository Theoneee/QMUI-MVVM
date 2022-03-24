package com.theone.common.widget.datePicker;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

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
 * @date 2019/7/25 0025
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class Builder extends BaseBuilder {
    private int textColor = 0xFF333333;
    private int selectedTextColor = 0xFF59B29C;
    private boolean loop;
    private float maxTextSize;
    private float minTextSize;
    private int maxTextAlpha = 0xFF;
    private int minTextAlpha = 0x78;
    private float textSpaceRatio = 1.6f;

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(@ColorInt int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public float getMaxTextSize() {
        return maxTextSize;
    }

    /**
     *
     * @param context context
     * @param unit unit
     * @param value value
     * @see TypedValue#applyDimension(int, float, DisplayMetrics)
     */
    public void setMaxTextSize(@NonNull Context context, int unit, float value) {
        this.maxTextSize = TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());
    }

    public float getMinTextSize() {
        return minTextSize;
    }

    /**
     *
     * @param context context
     * @param unit unit
     * @param value value
     * @see TypedValue#applyDimension(int, float, DisplayMetrics)
     */
    public void setMinTextSize(@NonNull Context context, int unit, float value) {
        this.minTextSize = TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());;
    }

    public int getMaxTextAlpha() {
        return maxTextAlpha;
    }

    public void setMaxTextAlpha(@IntRange(from = 0, to = 0xFF) int maxTextAlpha) {
        this.maxTextAlpha = maxTextAlpha;
    }

    public int getMinTextAlpha() {
        return minTextAlpha;
    }

    public void setMinTextAlpha(@IntRange(from = 0, to = 0xFF) int minTextAlpha) {
        this.minTextAlpha = minTextAlpha;
    }

    public float getTextSpaceRatio() {
        return textSpaceRatio;
    }

    public void setTextSpaceRatio(@FloatRange(from = 0) float textSpaceRatio) {
        this.textSpaceRatio = textSpaceRatio;
    }
}