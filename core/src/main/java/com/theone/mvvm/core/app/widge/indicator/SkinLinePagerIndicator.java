package com.theone.mvvm.core.app.widge.indicator;

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

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;

import com.qmuiteam.qmui.skin.IQMUISkinHandlerView;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.theone.mvvm.core.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;


/**
 * @author The one
 * @date 2020/7/14 0014
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SkinLinePagerIndicator extends LinePagerIndicator implements IQMUISkinHandlerView {

    public SkinLinePagerIndicator(Context context) {
        super(context);
        init();
    }

    private void init(){
        int primaryColor = QMUISkinHelper.getSkinColor(this, R.attr.app_skin_tab_indicator_select_color);
        setColors(primaryColor);
    }

    @Override
    public void handle(@NonNull QMUISkinManager manager, int skinIndex, @NonNull Resources.Theme theme, @Nullable SimpleArrayMap<String, Integer> attrs) {
        init();
        invalidate();
    }
}
