package com.radish.framelibrary.upload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class LGImgCompressorService extends Service {
    public static final String IS_COMPRESS = "isCompress";
    protected static final String TAG = "GImgCompressorService";

    protected ArrayList<LGImgCompressor.CompressResult> compressResults = new ArrayList<>();
    protected boolean isCompress;
    protected int startId;

    public LGImgCompressorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate...");
        executorService = Executors.newFixedThreadPool(10);
        Intent intent = new Intent(UploadConstanse.ACTION_COMPRESS_BROADCAST);
        intent.setPackage(getPackageName());
        intent.putExtra(UploadConstanse.KEY_COMPRESS_FLAG, UploadConstanse.FLAG_BEGAIIN);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy...");
        Intent intent = new Intent(UploadConstanse.ACTION_COMPRESS_BROADCAST);
        intent.setPackage(getPackageName());
        intent.putExtra(UploadConstanse.KEY_COMPRESS_FLAG, UploadConstanse.FLAG_END);
        intent.putParcelableArrayListExtra(UploadConstanse.KEY_COMPRESS_RESULT, compressResults);
        sendBroadcast(intent);
        compressResults.clear();
        executorService.shutdownNow();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        doCompressImages(intent, startId);
        return Service.START_NOT_STICKY;
    }

    private int taskNumber;
    private ExecutorService executorService;
    private final Object lock = new Object();

    private void doCompressImages(final Intent intent, final int taskId) {
        isCompress = intent.getBooleanExtra(IS_COMPRESS, false);
        final ArrayList<CompressServiceParam> paramArrayList = intent.getParcelableArrayListExtra(UploadConstanse.COMPRESS_PARAM);
        if (compressResults == null)
            compressResults = new ArrayList<>();
        compressResults.clear();
        if (isCompress) {
            synchronized (lock) {
                taskNumber += paramArrayList.size();
            }
            //如果paramArrayList过大,为了避免"The application may be doing too much work on its main thread"的问题,将任务的创建和执行统一放在后台线程中执行
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < paramArrayList.size(); ++i) {
                        executorService.execute(new CompressTask(paramArrayList.get(i), taskId));
                    }
                }
            }).start();
        } else {
            //直接上传
            for (int i = 0; i < paramArrayList.size(); i++) {
                CompressServiceParam param = paramArrayList.get(i);
                String srcImageUri = param.getSrcImageUri();
                LGImgCompressor.CompressResult compressResult = new LGImgCompressor.CompressResult();
                compressResult.setSrcPath(srcImageUri);
                compressResult.setOutPath(srcImageUri);
                compressResults.add(compressResult);
            }
            upload();
        }
    }

    private class CompressTask implements Runnable {
        private CompressServiceParam param;
        private int taskId;

        private CompressTask(CompressServiceParam compressServiceParam, int taskId) {
            this.param = compressServiceParam;
            this.taskId = taskId;
        }

        @Override
        public void run() {
            Log.d(TAG, taskId + " do compress begain..." + Thread.currentThread().getId());
            int outwidth = param.getOutWidth();
            int outHieight = param.getOutHeight();
            int maxFileSize = param.getMaxFileSize();
            String srcImageUri = param.getSrcImageUri();
            LGImgCompressor.CompressResult compressResult = new LGImgCompressor.CompressResult();
            String outPutPath = null;
            try {
                outPutPath = LGImgCompressor.getInstance(LGImgCompressorService.this).compressImage(
                        srcImageUri, outwidth, outHieight, maxFileSize);
            } catch (Exception e) {
            }
            compressResult.setSrcPath(srcImageUri);
            compressResult.setOutPath(outPutPath);
            if (outPutPath == null) {
                compressResult.setStatus(LGImgCompressor.CompressResult.RESULT_ERROR);
            }
            Log.d(TAG, taskId + " do compress end..." + Thread.currentThread().getId());
            synchronized (lock) {
                compressResults.add(compressResult);
                taskNumber--;
                if (taskNumber <= 0) {
//                    stopSelf(taskId);
                    // 开始上传
                    upload();
                }
            }
        }
    }

    protected abstract void upload();

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

}
