package org.succlz123.bluetube.support.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import org.succlz123.bluetube.support.utils.LogUtils;
import org.succlz123.bluetube.support.widget.MyOkHttp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by succlz123 on 15/8/5.
 */
public class DownLoadAsyncTask extends AsyncTask<Object, Object, Boolean> {
    private Context mCtx;
    private String mDownLoadUrl;

    public DownLoadAsyncTask(Context ctx, String downLoadUrl) {
        super();
        this.mCtx = ctx;
        this.mDownLoadUrl = downLoadUrl;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Boolean doInBackground(Object... params) {
        InputStream is = MyOkHttp.getInstance().downLoad(mDownLoadUrl);

        FileOutputStream os = null;
        if (is != null) {
            String fileName = "xx.mp4";
            String filePathName = mCtx.getExternalFilesDir("video").getAbsolutePath() + File.separator + fileName;
            try {
                boolean isCreatecaCheFilePath = new File(filePathName).createNewFile();
                if (isCreatecaCheFilePath) {
                    os = new FileOutputStream(filePathName);
                    byte[] bt = new byte[2048];
                    int byteRead;
                    while ((byteRead = is.read(bt)) != -1) {
                        os.write(bt, 0, byteRead);
                    }
                    os.flush();
                } else {
                    LogUtils.e("FilePath", "is not create");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public void onPostExecute(Boolean inited) {

    }
}