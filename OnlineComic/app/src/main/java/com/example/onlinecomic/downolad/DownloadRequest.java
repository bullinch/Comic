package com.example.onlinecomic.downolad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.onlinecomic.callback.DownloadCallback;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.func.RetryExceptionFunc;
import com.zhouyou.http.request.BaseRequest;
import com.zhouyou.http.transformer.HandleErrTransformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class DownloadRequest extends BaseRequest<DownloadRequest> {

    public DownloadRequest(String url) {
        super(url);
    }

    private String savePath;
    private String saveName;

    /**
     * 下载文件路径<br>
     * 默认在：/storage/emulated/0/Android/data/包名/files/1494647767055<br>
     */
    public DownloadRequest savePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    /**
     * 下载文件名称<br>
     * 默认名字是时间戳生成的<br>
     */
    public DownloadRequest saveName(String saveName) {
        this.saveName = saveName;
        return this;
    }

    @SuppressLint("CheckResult")
    public <T> Disposable execute(final DownloadCallback callBack) {
        return (Disposable) build().generateRequest().compose(new ObservableTransformer<ResponseBody, ResponseBody>() {
            @Override
            public ObservableSource<ResponseBody> apply(@NonNull Observable<ResponseBody> upstream) {
                return upstream;//.observeOn(AndroidSchedulers.mainThread());
            }
        }).compose(new HandleErrTransformer()).retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody body) throws Exception {
                        writeResponseBodyToDisk(savePath, saveName, context, body, callBack);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private String fileSuffix;
    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    private static String PNG_CONTENTTYPE = "image/png";
    private static String JPG_CONTENTTYPE = "image/jpg";
    private boolean writeResponseBodyToDisk(String path, String name, Context context, ResponseBody body, DownloadCallback callBack) {
        if (callBack != null) {
            callBack.onStart();
        }
        if (!TextUtils.isEmpty(name)) {//text/html; charset=utf-8
            String type;
            if (!name.contains(".")) {
                type = body.contentType().toString();
                if (type.equals(APK_CONTENTTYPE)) {
                    fileSuffix = ".apk";
                } else if (type.equals(PNG_CONTENTTYPE)) {
                    fileSuffix = ".png";
                } else if (type.equals(JPG_CONTENTTYPE)) {
                    fileSuffix = ".jpg";
                } else {
                    fileSuffix = "." + body.contentType().subtype();
                }
                name = name + fileSuffix;
            }
        } else {
            name = System.currentTimeMillis() + fileSuffix;
        }
        if (path == null) {
            path = context.getExternalFilesDir(null) + File.separator + name;
        } else {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = path + File.separator + name;
            path = path.replaceAll("//", "/");
        }
        try {
            File futureStudioIconFile = new File(path);
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[1024 * 120];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    if(Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();
                if (callBack != null) {
                    callBack.onComplete(path);
                }
                return true;
            } catch (Exception e) {
                onError(e);
                return false;
            }

            finally {
                if (outputStream != null) {
                    outputStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            onError(e);
            return false;
        }
    }

    @SuppressLint("CheckResult")
    private void onError(Exception e) {
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiManager.downloadFile(url);
    }
}
