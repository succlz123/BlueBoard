package org.succlz123.bluetube.ui.fragment.acfun.left;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadManager;
import com.github.snowdream.android.app.DownloadTask;

import org.succlz123.bluetube.MyApplication;
import org.succlz123.bluetube.R;
import org.succlz123.bluetube.bean.acfun.AcContentVideo;
import org.succlz123.bluetube.bean.acfun.AcGetH5ByVid;
import org.succlz123.bluetube.support.config.RetrofitConfig;
import org.succlz123.bluetube.support.helper.acfun.AcApi;
import org.succlz123.bluetube.support.helper.acfun.AcString;
import org.succlz123.bluetube.support.utils.GlobalUtils;
import org.succlz123.bluetube.support.utils.LogUtils;
import org.succlz123.bluetube.ui.fragment.BaseFragment;

import java.io.File;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by fashi on 2015/8/20.
 */
public class DownLoadFragment extends BaseFragment {

	public static DownLoadFragment newInstance(String videoId, String danmakuId, String sourceId, String sourceType) {
		DownLoadFragment fragment = new DownLoadFragment();
		Bundle bundle = new Bundle();
		bundle.putString(AcString.VIDEO_ID, videoId);
		bundle.putString(AcString.DANMAKU_ID, danmakuId);
		bundle.putString(AcString.SOURCE_ID, sourceId);
		bundle.putString(AcString.SOURCE_TYPE, sourceType);
		fragment.setArguments(bundle);

		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ac_fragment_content_info, container, false);
		ButterKnife.bind(this, view);

		String videoId = getArguments().getString(AcString.VIDEO_ID);
		String danmakuId = getArguments().getString(AcString.DANMAKU_ID);
		String sourceId = getArguments().getString(AcString.SOURCE_ID);
		String sourceType = getArguments().getString(AcString.SOURCE_TYPE);

//		new DownLoadAsyncTask(getActivity(), path).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		String fileName = "ac" + videoId + ".mp4";
		final String filePathName = getActivity().getExternalFilesDir("video").getAbsolutePath() + File.separator + fileName;

		final DownloadManager downloadManager = new DownloadManager(getActivity());

		if (TextUtils.equals(sourceType, "letv")) {
			RetrofitConfig.getAcContentLeTvVideo().onContentResult(
					AcApi.getAcContentVideoUrl(sourceType), new Callback<AcContentVideo>() {

						@Override
						public void success(AcContentVideo acContentVideo, Response response) {
							String base64Url = acContentVideo.getData().getVideo_list().getVideo_4().getMain_url();
							String path = GlobalUtils.decodeByBase64(base64Url);

							DownloadTask task = new DownloadTask(getActivity());
							task.setUrl(path);
							task.setPath(filePathName);

							downloadManager.add(task, listener); //Add the task
							downloadManager.start(task, listener); //Start the task
							downloadManager.stop(task, listener); //Stop the task if you exit your APP.
						}

						@Override
						public void failure(RetrofitError error) {

						}
					});
			RetrofitConfig.getAcContentDanMu().onContentResult(
					AcApi.getAcContentDanMuUrl(), danmakuId, new Callback<Response>() {
						@Override
						public void success(Response response, Response response2) {

						}

						@Override
						public void failure(RetrofitError error) {

						}
					});
		} else {
			RetrofitConfig.getAcContentHtml5Video().onContentResult(
					AcApi.getAcContentHtml5VideoUrl("2440995"), new Callback<AcGetH5ByVid>() {

						@Override
						public void success(AcGetH5ByVid acGetH5ByVid, Response response) {
							if (acGetH5ByVid.isSuccess() && acGetH5ByVid.getResult().getC20().getFiles().size() != 0) {
								String path = acGetH5ByVid.getResult().getC20().getFiles().get(0).getUrl();

							} else {
								if (getActivity() != null && getActivity().isDestroyed()) {
									GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "不支持的视频源");
								}
							}
						}

						@Override
						public void failure(RetrofitError error) {
//							if (getActivity() != null && getActivity().isDestroyed()) {
//								GlobalUtils.showToastShort(MyApplication.getsInstance().getApplicationContext(), "网络异常");
//							}
						}
					});
		}

		return view;
	}

	@Override
	protected void lazyLoad() {

	}

	private DownloadListener listener = new DownloadListener<Integer, DownloadTask>() {
		/**
		 * The download task has been added to the sqlite.
		 * <p/>
		 * operation of UI allowed.
		 *
		 * @param downloadTask the download task which has been added to the sqlite.
		 */
		@Override
		public void onAdd(DownloadTask downloadTask) {
			super.onAdd(downloadTask);
			LogUtils.i("onAdd()");
			LogUtils.i("" + downloadTask);
		}

		/**
		 * The download task has been delete from the sqlite
		 * <p/>
		 * operation of UI allowed.
		 *
		 * @param downloadTask the download task which has been deleted to the sqlite.
		 */
		@Override
		public void onDelete(DownloadTask downloadTask) {
			super.onDelete(downloadTask);
			LogUtils.i("onDelete()");
		}

		/**
		 * The download task is stop
		 * <p/>
		 * operation of UI allowed.
		 *
		 * @param downloadTask the download task which has been stopped.
		 */
		@Override
		public void onStop(DownloadTask downloadTask) {
			super.onStop(downloadTask);
			LogUtils.i("onStop()");
		}

		/**
		 * Runs on the UI thread before doInBackground(Params...).
		 */
		@Override
		public void onStart() {
			super.onStart();
			LogUtils.i("onStart()");
		}

		/**
		 * Runs on the UI thread after publishProgress(Progress...) is invoked. The
		 * specified values are the values passed to publishProgress(Progress...).
		 *
		 * @param values The values indicating progress.
		 */
		@Override
		public void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
//			((DownloadTaskAdapter) getListAdapter()).notifyDataSetChanged();
			LogUtils.i("onProgressUpdate");
		}

		/**
		 * Runs on the UI thread after doInBackground(Params...). The specified
		 * result is the value returned by doInBackground(Params...). This method
		 * won't be invoked if the task was cancelled.
		 *
		 * @param downloadTask The result of the operation computed by
		 *                     doInBackground(Params...).
		 */
		@Override
		public void onSuccess(DownloadTask downloadTask) {
			super.onSuccess(downloadTask);
			LogUtils.i("onSuccess()");
		}

		/**
		 * Applications should preferably override onCancelled(Object). This method
		 * is invoked by the default implementation of onCancelled(Object). Runs on
		 * the UI thread after cancel(boolean) is invoked and
		 * doInBackground(Object[]) has finished.
		 */
		@Override
		public void onCancelled() {
			super.onCancelled();
			LogUtils.i("onCancelled()");
		}

		@Override
		public void onError(Throwable thr) {
			super.onError(thr);
			LogUtils.i("onError()");
		}

		/**
		 * Runs on the UI thread after doInBackground(Params...) when the task is
		 * finished or cancelled.
		 */
		@Override
		public void onFinish() {
			super.onFinish();
			LogUtils.i("onFinish()");
		}
	};


}
