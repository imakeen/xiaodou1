package com.radish.framelibrary.upload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * author : SuMeng
 * e-mail : 986335838@qq.com
 * time   : 2019/05/13
 * desc   : xxxx 描述
 */
public class CompressingReciver extends BroadcastReceiver {

    private long serviceStartTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        int flag = intent.getIntExtra(UploadConstanse.KEY_COMPRESS_FLAG, -1);
        Log.e("radish","CompressingReciver====="+flag);
        if (flag == UploadConstanse.FLAG_BEGAIIN) {
            // 开始，通知Activity
//            Activity activity = ActivityCollector.getActivity(ServiceCompressActivity.class);
//            if (activity != null && activity instanceof ServiceCompressActivity){
//                ((ServiceCompressActivity) activity).updateInfo("开始压缩及上传。。。");
//            }
            serviceStartTime = System.currentTimeMillis();
            return;
        }

        if (flag == UploadConstanse.FLAG_END) {
            ArrayList<LGImgCompressor.CompressResult> compressResults =
                    (ArrayList<LGImgCompressor.CompressResult>) intent.getSerializableExtra(UploadConstanse.KEY_COMPRESS_RESULT);
            // 完成，通知Activity
//            Activity activity = ActivityCollector.getActivity(ServiceCompressActivity.class);
//            if (activity != null && activity instanceof ServiceCompressActivity){
//                ((ServiceCompressActivity) activity).updateInfo("已完成："+compressResults.size());
//            }
        }
    }
}

