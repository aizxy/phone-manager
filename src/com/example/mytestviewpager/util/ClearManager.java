package com.example.mytestviewpager.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.mytestviewpager.R;
import com.example.mytestviewpager.bean.ClassInfo;
import com.example.mytestviewpager.bean.MClearInfo;

import android.R.menu;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class ClearManager {
	private static final String FILE_NAME = "clearpath.db";
	private static final String FILE_PACKAGE = "com.example.mytestviewpager";
	private static final String FILE_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ FILE_PACKAGE+"/"+FILE_NAME;
	
	public static ArrayList<MClearInfo> softFileList;
	
	public static List<MClearInfo> getPhoneSoftDetail(Context context){
		if(softFileList==null){
			softFileList=readClassListTable();
			System.out.println("9999999999999"+softFileList);
		}
		ArrayList<MClearInfo> currentPhoneAppDetail=new ArrayList<MClearInfo>();
		for(MClearInfo cb:softFileList){
			System.out.println("8888888"+cb.getFilePath());
			File file=new File(cb.getFilePath());
			Drawable icon;
			if(file.exists()){
				try{
					icon=context.getPackageManager().getApplicationIcon(cb.getApkName());
				}catch(NameNotFoundException e){
					icon=context.getResources().getDrawable(R.drawable.ic_launcher);
				}
				cb.setIcon(icon);
				currentPhoneAppDetail.add(cb);
			}
		}
		System.out.println("66666666666666666"+currentPhoneAppDetail);
		return currentPhoneAppDetail;
	}

	public static void readClearDB(InputStream path) throws IOException {
		File toFile = new File(FILE_PATH);
		if (!toFile.exists()) {
			BufferedInputStream bis = new BufferedInputStream(path);
			FileOutputStream fis = new FileOutputStream(toFile);
			BufferedOutputStream bos = new BufferedOutputStream(fis);
			int length = 0;
			byte[] b = new byte[5 * 1024];
			while ((length = bis.read(b)) != -1) {
				bos.write(b, 0, length);
			}
			bos.flush();
			bos.close();
			bis.close();
		}
	}
	
	public static ArrayList<MClearInfo> readClassListTable() {
		ArrayList<MClearInfo> classInfoList = new ArrayList<MClearInfo>();
		SQLiteDatabase dataBase = SQLiteDatabase.openOrCreateDatabase(
				FILE_PATH, null);
		String sql = "select * from softdetail ";
		Cursor cursor = dataBase.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				String softChineseName = cursor.getString(cursor.getColumnIndex("softChinesename"));
				String softEnglishName = cursor.getString(cursor.getColumnIndex("softEnglishname"));
				String apkName = cursor.getString(cursor.getColumnIndex("apkname"));
				String filePath = cursor.getString(cursor.getColumnIndex("filepath"));
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+filePath;
				MClearInfo mClearInfo = new MClearInfo(id, softChineseName, softEnglishName, apkName, filePath);
				classInfoList.add(mClearInfo);
			} while (cursor.moveToNext());
			cursor.close();
			dataBase.close();
		}

		return classInfoList;
	}
}
