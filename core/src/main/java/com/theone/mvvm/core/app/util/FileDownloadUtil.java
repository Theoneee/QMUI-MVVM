package com.theone.mvvm.core.app.util;//  ┏┓　　　┏┓
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

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rxhttp.RxHttpPlugins;

/**
 * @author The one
 * @date 2022-08-09 11:14
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class FileDownloadUtil {

    private static FileDownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;
    private final Platform mPlatform;

    public static FileDownloadUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new FileDownloadUtil();
        }
        return downloadUtil;
    }

    private FileDownloadUtil() {
        okHttpClient = RxHttpPlugins.getOkHttpClient();
        mPlatform = Platform.get();
    }


    /**
     * @param url          下载连接
     * @param destFileDir  下载的文件储存目录
     * @param destFileName 下载文件名称，后面记得拼接后缀，否则手机没法识别文件类型
     * @param listener     下载监听
     */
    public void download(final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        //异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 下载失败监听回调
                mPlatform.execute(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                //储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                final File file = new File(dir, destFileName);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) ((sum * 1.0f / total) * 100);
                        //Log.e("UPDATE", "onResponse: sum = "+sum +"  total = "+total+"  progress = "+progress);
                        //下载中更新进度条
                        mPlatform.execute(new Runnable() {
                            @Override
                            public void run() {
                                listener.onDownloading(progress);
                            }
                        });
                    }
                    fos.flush();
                    //下载完成
                    mPlatform.execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadSuccess(file);
                        }
                    });
                } catch (Exception e) {
                    mPlatform.execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed(e);
                        }
                    });
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface OnDownloadListener {

        /**
         * 下载成功之后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载异常信息
         */

        void onDownloadFailed(Exception e);
    }

    static class Platform {
        private static final Platform PLATFORM = findPlatform();

        public static Platform get() {
            return PLATFORM;
        }

        private static Platform findPlatform() {
            try {
                Class.forName("android.os.Build");
                if (Build.VERSION.SDK_INT != 0) {
                    return new Android();
                }
            } catch (ClassNotFoundException ignored) {
            }
            return new Platform();
        }

        public Executor defaultCallbackExecutor() {
            return Executors.newCachedThreadPool();
        }

        public void execute(Runnable runnable) {
            defaultCallbackExecutor().execute(runnable);
        }


        static class Android extends FileDownloadUtil.Platform {
            @Override
            public Executor defaultCallbackExecutor() {
                return new MainThreadExecutor();
            }

            static class MainThreadExecutor implements Executor {
                private final Handler handler = new Handler(Looper.getMainLooper());

                @Override
                public void execute(Runnable r) {
                    handler.post(r);
                }
            }
        }
    }

}
