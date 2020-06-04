package com.xinzu.xiaodou.util;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;


public class FileUtils {

    public static void writeFileSDcard(ResponseBody responseBody, File mFile, DownloadListener downloadListener) {
//        downloadListener.onStart();
//        对于文件的操作，需要放到当前方法中

        long currentLength = 0;
        OutputStream os = null;
        InputStream is = responseBody.byteStream();
        long totalLength = responseBody.contentLength();
        try {
            os = new FileOutputStream(mFile);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
//                LogUtils.e("当前长度--: " + currentLength);
                int progress = (int) (100 * currentLength / totalLength);
//                LogUtils.e("当前进度--: " + progress);
                downloadListener.downLoadprogress(progress);
                if (progress == 100) {
                    downloadListener.downLoadSucces();
                }
            }
        } catch (FileNotFoundException e) {
            downloadListener.downLoadError("未找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            downloadListener.downLoadError("IO错误！");
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
