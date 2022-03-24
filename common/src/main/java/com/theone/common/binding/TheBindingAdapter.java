package com.theone.common.ui.binding;//  ┏┓　　　┏┓
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

import android.os.SystemClock;
import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * @author The one
 * @date 2021-05-25 16:40
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TheBindingAdapter {

    /**
     * 这个方法不能用Kotlin写
     */
    @BindingAdapter(value = {"noRepeatClick"}, requireAll = false)
    public static void onClickWithNoRepeatClick(View view, View.OnClickListener clickListener) {
        final Long[] mHits = new Long[2];
        mHits[0] = 0L;
        mHits[1] = 0L;
        view.setOnClickListener(view1 -> {
                System.arraycopy(mHits,1,mHits,0,mHits.length -1);
                mHits[mHits.length -1] = SystemClock.uptimeMillis();
                if(mHits[0] < SystemClock.uptimeMillis() - 500){
                    clickListener.onClick(view1);
                }
        });
    }

}
