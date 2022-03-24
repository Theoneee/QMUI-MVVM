package com.theone.demo.app.widget;//  ┏┓　　　┏┓
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
 * @date 2021-03-31 16:51
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.theone.demo.R;

/**
 * 模拟打字机效果
 *
 * */
public class TypeTextView extends AppCompatTextView {
    private Context mContext = null;
    private MediaPlayer mMediaPlayer = null;
    private String mShowTextString = null;
    private Timer mTypeTimer = null;
    private OnTypeViewListener mOnTypeViewListener = null;
    private static final int TYPE_TIME_DELAY = 100;
    private int mTypeTimeDelay = TYPE_TIME_DELAY; // 打字间隔

    public TypeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTypeTextView( context );
    }

    public TypeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeTextView( context );
    }

    public TypeTextView(Context context) {
        super(context);
        initTypeTextView( context );
    }

    public void setOnTypeViewListener( OnTypeViewListener onTypeViewListener ){
        mOnTypeViewListener = onTypeViewListener;
    }

    public void start( final String textString ){
        start( textString, TYPE_TIME_DELAY );
    }

    public void start( final String textString, final int typeTimeDelay ){
        if( TextUtils.isEmpty( textString ) || typeTimeDelay < 0 ){
            return;
        }
        post( new Runnable( ) {
            @Override
            public void run() {
                mShowTextString = textString;
                mTypeTimeDelay = typeTimeDelay;
                setText( "" );
                startTypeTimer( );
                if( null != mOnTypeViewListener ){
                    mOnTypeViewListener.onTypeStart( );
                }
            }
        });
    }

    public void stop( ){
        stopTypeTimer( );
        stopAudio();
    }

    private void initTypeTextView( Context context ){
        mContext = context;
    }

    private void startTypeTimer( ){
        stopTypeTimer( );
        mTypeTimer = new Timer( );
        mTypeTimer.schedule( new TypeTimerTask(), mTypeTimeDelay );
    }

    private void stopTypeTimer( ){
        if( null != mTypeTimer ){
            mTypeTimer.cancel( );
            mTypeTimer = null;
        }
    }

    private void startAudioPlayer() {
        stopAudio();
        playAudio( R.raw.type_in );
    }

    private void playAudio( int audioResId ){
        try{
            stopAudio( );
            mMediaPlayer = MediaPlayer.create( mContext, audioResId );
            mMediaPlayer.start( );
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private void stopAudio( ){
        if( mMediaPlayer != null && mMediaPlayer.isPlaying( ) ){
            mMediaPlayer.stop( );
            mMediaPlayer.release( );
            mMediaPlayer = null;
        }
    }

    class TypeTimerTask extends TimerTask{
        @Override
        public void run() {
            post(new Runnable( ) {
                @Override
                public void run() {
                    if( getText( ).toString( ).length( ) < mShowTextString.length( ) ){
                        setText( mShowTextString.substring(0, getText( ).toString( ).length( ) + 1 ) );
//                        startAudioPlayer();
                        startTypeTimer( );
                    }else{
                        stopTypeTimer( );
                        if( null != mOnTypeViewListener ){
                            mOnTypeViewListener.onTypeOver( );
                        }
                    }
                }
            });
        }
    }

    public interface OnTypeViewListener{
        public void onTypeStart( );
        public void onTypeOver( );
    }
}
