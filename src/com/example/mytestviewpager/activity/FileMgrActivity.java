package com.example.mytestviewpager.activity;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.util.CommonUtil;
import com.example.mytestviewpager.util.FileManager;
import com.example.mytestviewpager.util.FileManager.SearchFileListener;
import com.example.mytestviewpager.util.FileTypeUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FileMgrActivity extends BaseActivity {
	private FileManager fileManager;
	private Thread thread;

	private ImageView iv_file_all, iv_file_doc, iv_file_video, iv_file_music,
			iv_file_photo, iv_file_apps, iv_file_compress;
	private TextView tv_file_size, tv_all_size, tv_doc_size, tv_video_size,
			tv_music_size, tv_photo_size, tv_compress_size, tv_apps_size;
	private ProgressBar pb_file_apps, pb_file_compress, pb_file_photo,
			pb_file_music, pb_file_video, pb_file_doc, pb_file_all;
	private RelativeLayout rl_file_all, rl_file_doc, rl_file_video,
			rl_file_music, rl_file_photo, rl_file_compress, rl_file_apps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_mgr);
		initActionBar("文件管理", R.drawable.btn_homeasup_default, -1,
				onClickListener);
		initUI();
		asyncTaskLoadData();
	}

	private void asyncTaskLoadData() {
		fileManager = FileManager.getInatance();
		fileManager.setSearchFileListener(onSearchFileListener);
		thread = new Thread() {
			public void run() {
				fileManager.searchSDCardFile();
			};
		};
		thread.start();
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.iv_left:
				startActivity(HomeActivity.class);
				finish();
				break;
			case R.id.rl_file_all:
			case R.id.rl_file_apps:
			case R.id.rl_file_compress:
			case R.id.rl_file_doc:
			case R.id.rl_file_music:
			case R.id.rl_file_photo:
			case R.id.rl_file_video:
				Intent intent = new Intent(FileMgrActivity.this,
						FileMgrShowActivity.class);
				intent.putExtra("id", id);
				startActivityForResult(intent, 1);
				break;
			}

		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			tv_file_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_all_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_apps_size.setText(CommonUtil.getFileInfo(fileManager
					.getApkFileSize()));
			tv_music_size.setText(CommonUtil.getFileInfo(fileManager
					.getAudioFileSize()));
			tv_photo_size.setText(CommonUtil.getFileInfo(fileManager
					.getImageFileSize()));
			tv_doc_size.setText(CommonUtil.getFileInfo(fileManager
					.getTxtFileSize()));
			tv_video_size.setText(CommonUtil.getFileInfo(fileManager
					.getVideoFileSize()));
			tv_compress_size.setText(CommonUtil.getFileInfo(fileManager
					.getZipFileSize()));
		}
	};

	private void initUI() {
		tv_file_size = (TextView) findViewById(R.id.tv_file_size);
		tv_all_size = (TextView) findViewById(R.id.tv_all_size);
		tv_doc_size = (TextView) findViewById(R.id.tv_doc_size);
		tv_video_size = (TextView) findViewById(R.id.tv_video_size);
		tv_music_size = (TextView) findViewById(R.id.tv_music_size);
		tv_photo_size = (TextView) findViewById(R.id.tv_photo_size);
		tv_compress_size = (TextView) findViewById(R.id.tv_compress_size);
		tv_apps_size = (TextView) findViewById(R.id.tv_apps_size);
		pb_file_apps = (ProgressBar) findViewById(R.id.pb_file_apps);
		pb_file_compress = (ProgressBar) findViewById(R.id.pb_file_compress);
		pb_file_photo = (ProgressBar) findViewById(R.id.pb_file_photo);
		pb_file_music = (ProgressBar) findViewById(R.id.pb_file_music);
		pb_file_video = (ProgressBar) findViewById(R.id.pb_file_video);
		pb_file_doc = (ProgressBar) findViewById(R.id.pb_file_doc);
		pb_file_all = (ProgressBar) findViewById(R.id.pb_file_all);
		iv_file_all = (ImageView) findViewById(R.id.iv_file_all);
		iv_file_doc = (ImageView) findViewById(R.id.iv_file_doc);
		iv_file_video = (ImageView) findViewById(R.id.iv_file_video);
		iv_file_music = (ImageView) findViewById(R.id.iv_file_music);
		iv_file_photo = (ImageView) findViewById(R.id.iv_file_photo);
		iv_file_apps = (ImageView) findViewById(R.id.iv_file_apps);
		iv_file_compress = (ImageView) findViewById(R.id.iv_file_compress);
		rl_file_all = (RelativeLayout) findViewById(R.id.rl_file_all);
		rl_file_doc = (RelativeLayout) findViewById(R.id.rl_file_doc);
		rl_file_video = (RelativeLayout) findViewById(R.id.rl_file_video);
		rl_file_music = (RelativeLayout) findViewById(R.id.rl_file_music);
		rl_file_photo = (RelativeLayout) findViewById(R.id.rl_file_photo);
		rl_file_compress = (RelativeLayout) findViewById(R.id.rl_file_compress);
		rl_file_apps = (RelativeLayout) findViewById(R.id.rl_file_apps);
		rl_file_all.setOnClickListener(onClickListener);
		rl_file_doc.setOnClickListener(onClickListener);
		rl_file_video.setOnClickListener(onClickListener);
		rl_file_music.setOnClickListener(onClickListener);
		rl_file_photo.setOnClickListener(onClickListener);
		rl_file_compress.setOnClickListener(onClickListener);
		rl_file_apps.setOnClickListener(onClickListener);

	}

	private FileManager.SearchFileListener onSearchFileListener = new SearchFileListener() {

		@Override
		public void searching(String typeName) {

			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = typeName;
			handler.sendMessage(msg);
		}

		@Override
		public void end(boolean isExceptionEnd) {
			handler.sendEmptyMessage(2);
		}
	};

	protected void onDestroy() {
		fileManager.setStopSearch(true);
		super.onDestroy();
	}

	protected void myHandlerMessage(Message msg) {
		switch (msg.what) {
		case 1:
			String typeName = (String) msg.obj;
			tv_file_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_all_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			if (typeName.equals(FileTypeUtil.TYPE_APK)) {
				tv_apps_size.setText(CommonUtil.getFileInfo(fileManager
						.getApkFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_ANY)) {
				tv_all_size.setText(CommonUtil.getFileInfo(fileManager
						.getAnyFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_AUDIO)) {
				tv_music_size.setText(CommonUtil.getFileInfo(fileManager
						.getAudioFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_IMAGE)) {
				tv_photo_size.setText(CommonUtil.getFileInfo(fileManager
						.getImageFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_TXT)) {
				tv_doc_size.setText(CommonUtil.getFileInfo(fileManager
						.getTxtFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_VIDEO)) {
				tv_video_size.setText(CommonUtil.getFileInfo(fileManager
						.getVideoFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_ZIP)) {
				tv_compress_size.setText(CommonUtil.getFileInfo(fileManager
						.getZipFileSize()));
			}
			break;

		case 2:
			tv_file_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_all_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_doc_size.setText(CommonUtil.getFileInfo(fileManager
					.getTxtFileSize()));
			tv_video_size.setText(CommonUtil.getFileInfo(fileManager
					.getVideoFileSize()));
			tv_music_size.setText(CommonUtil.getFileInfo(fileManager
					.getAudioFileSize()));
			tv_photo_size.setText(CommonUtil.getFileInfo(fileManager
					.getImageFileSize()));
			tv_compress_size.setText(CommonUtil.getFileInfo(fileManager
					.getZipFileSize()));
			tv_apps_size.setText(CommonUtil.getFileInfo(fileManager
					.getApkFileSize()));
			iv_file_all.setVisibility(View.VISIBLE);
			iv_file_apps.setVisibility(View.VISIBLE);
			iv_file_compress.setVisibility(View.VISIBLE);
			iv_file_doc.setVisibility(View.VISIBLE);
			iv_file_music.setVisibility(View.VISIBLE);
			iv_file_photo.setVisibility(View.VISIBLE);
			iv_file_video.setVisibility(View.VISIBLE);

			pb_file_all.setVisibility(View.INVISIBLE);
			pb_file_apps.setVisibility(View.INVISIBLE);
			pb_file_compress.setVisibility(View.INVISIBLE);
			pb_file_photo.setVisibility(View.INVISIBLE);
			pb_file_music.setVisibility(View.INVISIBLE);
			pb_file_video.setVisibility(View.INVISIBLE);
			pb_file_doc.setVisibility(View.INVISIBLE);
			break;
		}
	}
}
