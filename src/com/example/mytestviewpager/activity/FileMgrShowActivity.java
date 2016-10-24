package com.example.mytestviewpager.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.R.id;
import com.example.mytestviewpager.R.layout;
import com.example.mytestviewpager.R.menu;
import com.example.mytestviewpager.adapter.FileAdapter;
import com.example.mytestviewpager.base.BaseActivity;
import com.example.mytestviewpager.bean.FileInfo;
import com.example.mytestviewpager.util.CommonUtil;
import com.example.mytestviewpager.util.FileManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FileMgrShowActivity extends BaseActivity {

	private int id;
	private String title;
	
	private TextView tv_fileshow_num, tv_fileshow_size;
	private ListView lv_fileshow_list;
	private Button btn_fileshow_clear;

	private long fileSize;
	private long fileNum;
	
	private List<FileInfo> fileInfo;
	private FileManager fileManager;
	private FileAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_mgr_show);
		id = getIntent().getIntExtra("id", R.id.rl_file_all);
		initInfo(id);
		initActionBar(title, R.drawable.btn_homeasup_default, -1,
				onClickListener);
		initUI();
		adapter = new FileAdapter(this);
		lv_fileshow_list.setAdapter(adapter);
		adapter.setDataToAdapter(fileInfo);
		adapter.notifyDataSetChanged();
	}

	private void initUI() {
		tv_fileshow_num = (TextView) findViewById(R.id.tv_fileshow_num);
		tv_fileshow_size = (TextView) findViewById(R.id.tv_fileshow_size);
		lv_fileshow_list = (ListView) findViewById(R.id.lv_fileshow_list);
		btn_fileshow_clear = (Button) findViewById(R.id.btn_fileshow_clear);
		btn_fileshow_clear.setOnClickListener(onClickListener);
		tv_fileshow_num.setText(fileNum + "个");
		tv_fileshow_size.setText(CommonUtil.getFileInfo(fileSize));
		fileManager=FileManager.getInatance();
	}

	private void initInfo(int id) {
		switch (id) {
		case R.id.rl_file_all:
			fileInfo = FileManager.getInatance().getAnyFileList();
			fileSize = FileManager.getInatance().getAnyFileSize();
			title = "全部文件";
			break;
		case R.id.rl_file_apps:
			fileInfo = FileManager.getInatance().getApkFileList();
			fileSize = FileManager.getInatance().getApkFileSize();
			title = "程序包";
			break;
		case R.id.rl_file_compress:
			fileInfo = FileManager.getInatance().getZipFileList();
			fileSize = FileManager.getInatance().getZipFileSize();
			title = "压缩包";
			break;
		case R.id.rl_file_doc:
			fileInfo = FileManager.getInatance().getTxtFileList();
			fileSize = FileManager.getInatance().getTxtFileSize();
			title = "文档";
			break;

		case R.id.rl_file_music:
			fileInfo = FileManager.getInatance().getAudioFileList();
			fileSize = FileManager.getInatance().getAudioFileSize();
			title = "音频";
			break;

		case R.id.rl_file_photo:
			fileInfo = FileManager.getInatance().getImageFileList();
			fileSize = FileManager.getInatance().getImageFileSize();
			title = "图片";
			break;

		case R.id.rl_file_video:
			fileInfo = FileManager.getInatance().getVideoFileList();
			fileSize = FileManager.getInatance().getVideoFileSize();
			title = "视频";
			break;
		}
		fileNum = fileInfo.size();

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_left:
				setResult(1);
				finish();
				break;
			case R.id.btn_fileshow_clear:
				deleteFile();
				break;
			}

		}
	};

	private void deleteFile() {
		List<FileInfo> deleteFileInfo = new ArrayList<FileInfo>();
		for (int i = 0; i < adapter.getDataList().size(); i++) {
			FileInfo fileInfo = adapter.getDataList().get(i);
			if (fileInfo.isSelect()) {
				deleteFileInfo.add(fileInfo);
			}
		}

		for (int i = 0; i < deleteFileInfo.size(); i++) {
			FileInfo delFileInfo = deleteFileInfo.get(i);
			File file = delFileInfo.getFile();
			long size = file.length();
			if (file.delete()) {
				adapter.getDataList().remove(delFileInfo);
				FileManager.getInatance().getAnyFileList().remove(delFileInfo);
				FileManager.getInatance().setAnyFileSize(
						FileManager.getInatance().getAnyFileSize() - size);
				FileManager.getInatance().getApkFileList().remove(delFileInfo);
				FileManager.getInatance().setApkFileSize(
						FileManager.getInatance().getApkFileSize() - size);
				FileManager.getInatance().getZipFileList().remove(delFileInfo);
				FileManager.getInatance().setZipFileSize(
						FileManager.getInatance().getZipFileSize() - size);
				FileManager.getInatance().getTxtFileList().remove(delFileInfo);
				FileManager.getInatance().setTxtFileSize(
						FileManager.getInatance().getTxtFileSize() - size);
				FileManager.getInatance().getAudioFileList()
						.remove(delFileInfo);
				FileManager.getInatance().setAudioFileSize(
						FileManager.getInatance().getAudioFileSize() - size);
				FileManager.getInatance().getImageFileList()
						.remove(delFileInfo);
				FileManager.getInatance().setImageFileSize(
						FileManager.getInatance().getImageFileSize() - size);
				FileManager.getInatance().getVideoFileList()
						.remove(delFileInfo);
				FileManager.getInatance().setVideoFileSize(
						FileManager.getInatance().getVideoFileSize() - size);
				switch (id) {
				case R.id.rl_file_all:
//					FileManager.getInatance().getAnyFileList()
//							.remove(delFileInfo);
//					FileManager.getInatance().setAnyFileSize(
//							FileManager.getInatance().getAnyFileSize() - size);
					fileSize = FileManager.getInatance().getAnyFileSize();
					break;
				case R.id.rl_file_apps:
//					FileManager.getInatance().getApkFileList()
//							.remove(delFileInfo);
//					FileManager.getInatance().setApkFileSize(
//							FileManager.getInatance().getApkFileSize() - size);
					fileSize = FileManager.getInatance().getApkFileSize();
					break;
				case R.id.rl_file_compress:
//					FileManager.getInatance().getZipFileList()
//							.remove(delFileInfo);
//					FileManager.getInatance().setZipFileSize(
//							FileManager.getInatance().getZipFileSize() - size);
					fileSize = FileManager.getInatance().getZipFileSize();
					break;
				case R.id.rl_file_doc:
//					FileManager.getInatance().getTxtFileList()
//							.remove(delFileInfo);
//					FileManager.getInatance().setTxtFileSize(
//							FileManager.getInatance().getTxtFileSize() - size);
					fileSize = FileManager.getInatance().getTxtFileSize();
					break;
				case R.id.rl_file_music:
//					FileManager.getInatance().getAudioFileList()
//							.remove(delFileInfo);
//					FileManager.getInatance()
//							.setAudioFileSize(
//									FileManager.getInatance()
//											.getAudioFileSize() - size);
					fileSize = FileManager.getInatance().getAudioFileSize();
					break;
				case R.id.rl_file_photo:
//					FileManager.getInatance().getImageFileList()
//							.remove(delFileInfo);
//					FileManager.getInatance()
//							.setImageFileSize(
//									FileManager.getInatance()
//											.getImageFileSize() - size);
					fileSize = FileManager.getInatance().getImageFileSize();
					break;
				case R.id.rl_file_video:
//					FileManager.getInatance().getVideoFileList()
//							.remove(delFileInfo);
//					FileManager.getInatance()
//							.setVideoFileSize(
//									FileManager.getInatance()
//											.getVideoFileSize() - size);
					fileSize = FileManager.getInatance().getVideoFileSize();
					break;
				}
			}
		}
		adapter.notifyDataSetChanged();
		fileNum = adapter.getDataList().size();
		tv_fileshow_num.setText(fileNum + "个");
		tv_fileshow_size.setText(CommonUtil.getFileInfo(fileSize));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		fileManager.setStopSearch(true);
	}

}
