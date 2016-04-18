package org.succlz123.blueboard.view.adapter.recyclerview.download;

import org.succlz123.blueboard.MyApplication;
import org.succlz123.blueboard.R;
import org.succlz123.blueboard.base.BaseRvViewHolder;
import org.succlz123.blueboard.model.utils.common.OkUtils;
import org.succlz123.blueboard.model.utils.common.ViewUtils;
import org.succlz123.blueboard.service.DownloadService;
import org.succlz123.okdownload.OkDownloadEnqueueListener;
import org.succlz123.okdownload.OkDownloadError;
import org.succlz123.okdownload.OkDownloadRequest;
import org.succlz123.okdownload.OkDownloadStatus;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by succlz123 on 15/9/1.
 */
public class DownLoadRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private WeakReference<Activity> mActivity;
    private OkDownloadEnqueueListener mListener;
    private ArrayList<OkDownloadRequest> mDownLoadList = new ArrayList<>();
    private DownloadService mDownloadService;

    private DecimalFormat mDecimalFormat = new DecimalFormat("0.0");

    public DownLoadRvAdapter(Activity activity) {
        super();
        this.mActivity = new WeakReference<>(activity);
    }

    public void setDownloadService(DownloadService downloadService) {
        mDownloadService = downloadService;
        notifyDataSetChanged();
    }

    public ArrayList<OkDownloadRequest> getDownLoadList() {
        return mDownLoadList;
    }

    public void setDownLoadList(ArrayList<OkDownloadRequest> downLoadList) {
        if (downLoadList == null) {
            return;
        }
        mDownLoadList = downLoadList;
        notifyDataSetChanged();
    }

    public void cleanListener() {
        if (mDownloadService.getListeners() != null) {
            mDownloadService.clearListenerMap();
        }
        mListener = null;
    }

    public class DlVH extends BaseRvViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvFileSize;
        private ProgressBar pbDownload;
        private Button btnDownload;
        private Button btnCancel;

        public DlVH(View itemView) {
            super(itemView);
            tvTitle = f(itemView, R.id.tv_download_title);
            tvDescription = f(itemView, R.id.tv_download_description);
            tvFileSize = f(itemView, R.id.tv_download_file_size);
            pbDownload = f(itemView, R.id.pb_download);
            btnDownload = f(itemView, R.id.btn_download);
            btnCancel = f(itemView, R.id.btn_cancel);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View downloadView = inflater.inflate(R.layout.download, parent, false);

        return new DlVH(downloadView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DlVH) {
            if (mDownLoadList.size() <= 0) {
                return;
            }

            final OkDownloadRequest request = mDownLoadList.get(position);

            if (request == null) {
                return;
            }

            String title = request.getTitle();
            final String sign = request.getSign();
            String description = request.getDescription();
            long fileSize = request.getFileSize();
            String filePath = request.getFilePath();
            long cacheSize = 0;
            String cacheSizeString = "0m";
            if (!TextUtils.isEmpty(filePath)) {
                cacheSize = new File(filePath).length();
                cacheSizeString = mDecimalFormat.format((float) cacheSize / 1024 / 1024) + "m";
            }

            ((DlVH) holder).tvTitle.setText(title);
            ((DlVH) holder).tvDescription.setText(description);
            ((DlVH) holder).pbDownload.setMax(100);
            ((DlVH) holder).tvFileSize.setText(cacheSizeString);

            if (request.getStatus() == OkDownloadStatus.FINISH | cacheSize == fileSize) {
                ((DlVH) holder).pbDownload.setProgress(100);
                ((DlVH) holder).btnDownload.setText("完成");
                ((DlVH) holder).btnDownload.setEnabled(false);
                ((DlVH) holder).btnDownload.setBackgroundResource(R.color.md_green_400);
            } else {
                if (cacheSize != 0 && fileSize != 0) {
                    ((DlVH) holder).pbDownload.setProgress((int) (cacheSize * 100 / fileSize));
                }
                ((DlVH) holder).btnDownload.setEnabled(true);

                ((DlVH) holder).btnDownload.setText("下载");

//                if (request.getStatus() == OkDownloadStatus.PAUSE) {
//                    ((DlVH) holder).btnDownload.setText("下载");
//                } else {
//                    ((DlVH) holder).btnDownload.setText("暂停");
//                }
            }

            if (mDownloadService == null) {
                return;
            }

            mListener = new DownloadListener(this, mDecimalFormat,
                    ((DlVH) holder).pbDownload,
                    ((DlVH) holder).tvFileSize,
                    ((DlVH) holder).btnDownload);
            mDownloadService.addListener(sign, mListener);

            ((DlVH) holder).btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDownloadService.toggleDownload(sign, request);
                    ((DlVH) holder).btnDownload.setEnabled(false);
                }
            });

            ((DlVH) holder).btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mActivity.get() == null) {
                        return;
                    }
                    ViewUtils.showAlertDialog(mActivity.get(),
                            "这是标题",
                            "是否删除任务?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDownloadService.cancelDownload(sign, mListener);
                                }
                            });
                }
            });
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return mDownLoadList.size();
    }

    public static class MyDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int marginRight = OkUtils.dp2px(parent.getContext(), 7);
            if (position == 1 | position == 3) {
                outRect.set(0, 0, marginRight, 0);
            }
        }
    }

    private static class DownloadListener implements OkDownloadEnqueueListener {
        private WeakReference<DownLoadRvAdapter> weakReferenceAdapter;
        private WeakReference<ProgressBar> weakReferencePbRate;
        private WeakReference<TextView> weakReferenceTvRate;
        private WeakReference<Button> weakReferenceBtnDownload;
        private WeakReference<DecimalFormat> weakReferenceDecimalFormat;

        public DownloadListener(DownLoadRvAdapter weakReferenceAdapter, DecimalFormat weakReferenceDecimalFormat, ProgressBar weakReferencePbRate, TextView weakReferenceTvRate, Button weakReferenceBtnDownload) {
            super();
            this.weakReferenceAdapter = new WeakReference<>(weakReferenceAdapter);
            this.weakReferenceDecimalFormat = new WeakReference<>(weakReferenceDecimalFormat);
            this.weakReferencePbRate = new WeakReference<>(weakReferencePbRate);
            this.weakReferenceTvRate = new WeakReference<>(weakReferenceTvRate);
            this.weakReferenceBtnDownload = new WeakReference<>(weakReferenceBtnDownload);
        }

        @Override
        public void onStart(int id) {
            final Button btnDownload = weakReferenceBtnDownload.get();
            if (btnDownload == null) {
                return;
            }

            MyApplication.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnDownload.setText("暂停");
                    btnDownload.setEnabled(true);
                }
            });
        }

        @Override
        public void onProgress(int progress, final long cacheSize, final long totalSize) {
            final ProgressBar pbRate = weakReferencePbRate.get();
            final Button btnDownload = weakReferenceBtnDownload.get();
            final DecimalFormat decimalFormat = weakReferenceDecimalFormat.get();
            final TextView tvRate = weakReferenceTvRate.get();

            if (pbRate != null) {
                pbRate.setProgress(progress);
            }

            if (tvRate == null || decimalFormat == null || btnDownload == null) {
                return;
            }

            MyApplication.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnDownload.setText("暂停");
                    tvRate.setText(decimalFormat.format((float) cacheSize / 1024 / 1024) + "m");
                }
            });
        }

        @Override
        public void onRestart() {
            final Button btnDownload = weakReferenceBtnDownload.get();
            if (btnDownload == null) {
                return;
            }

            MyApplication.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnDownload.setEnabled(true);
                    btnDownload.setText("暂停");
                }
            });
        }

        @Override
        public void onPause() {
            final Button btnDownload = weakReferenceBtnDownload.get();
            if (btnDownload == null) {
                return;
            }

            MyApplication.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnDownload.setText("下载");
                    btnDownload.setEnabled(true);
                }
            });
        }

        @Override
        public void onCancel(String sign) {
            final DownLoadRvAdapter adapter = weakReferenceAdapter.get();
            final Button btnDownload = weakReferenceBtnDownload.get();
            if (adapter == null | btnDownload == null) {
                return;
            }

            adapter.setDownLoadList(MyApplication.getInstance().quaryAllDonwload());
            btnDownload.setEnabled(true);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFinish(int id) {
            final DownLoadRvAdapter adapter = weakReferenceAdapter.get();
            final Button btnDownload = weakReferenceBtnDownload.get();
            if (adapter == null | btnDownload == null) {
                return;
            }

            MyApplication.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnDownload.setEnabled(true);
                    adapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onError(final OkDownloadError error) {
            final Button btnDownload = weakReferenceBtnDownload.get();
            if (btnDownload == null) {
                return;
            }

            MyApplication.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnDownload.setEnabled(true);
                    btnDownload.setText("失败");
                    btnDownload.setBackgroundResource(R.color.md_red_400);
                    OkUtils.showToastShort(error.getMessage());
                }
            });
        }
    }
}
