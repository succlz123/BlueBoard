/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.vov.vitamio.demo;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewDemo extends Activity {

	/**
	 * TODO: Set the path variable to a streaming video URL or a local media file
	 * path.
	 */
	private String path = "http://g3.letv.cn/vod/v2/MTc0LzExLzM5L2JjbG91ZC8xMjM5NjYvdmVyXzAwXzIyLTMyNDM1NjY5MS1hdmMtODc3OTU0LWFhYy0xMjgwMDEtMzYwMjAxNS00NTg1NzQ4MTAtMGJkY2JhMmRiM2RhYTZmMjJhZWZjYWM4ZTQ5MjJhMDktMTQzODUzNjkzMzIzNy5tcDQ=?b=1018&mmsid=33776301&tm=1438678973&key=718b90316c239b159d43eab31a5452e3&platid=2&splatid=206&playid=0&tss=no&vtype=22&cvid=1184904436514&payff=0&pip=db7caa75e198ede6a7d957f186e4b28f&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un";
	private VideoView mVideoView;
	private EditText mEditText;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.videoview);
		mEditText = (EditText) findViewById(R.id.url);
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		if (path == "") {
			// Tell the user to provide a media file URL/path.
			Toast.makeText(VideoViewDemo.this, "Please edit VideoViewDemo Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
			return;
		} else {
			/*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
			mVideoView.setVideoPath(path);
			mVideoView.setMediaController(new MediaController(this));
			mVideoView.requestFocus();

			mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					// optional need Vitamio 4.0
					mediaPlayer.setPlaybackSpeed(1.0f);
				}
			});
		}

	}
	
	public void startPlay(View view) {
	    String url = mEditText.getText().toString();
	    path = url;
	    if (!TextUtils.isEmpty(url)) {
	        mVideoView.setVideoPath(url);
	    }
    }
	
	public void openVideo(View View) {
	  mVideoView.setVideoPath(path);
	}
	
}
