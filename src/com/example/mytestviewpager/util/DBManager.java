package com.example.mytestviewpager.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.mytestviewpager.bean.ClassInfo;
import com.example.mytestviewpager.bean.TableClass;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DBManager {
	private static final String FILE_NAME = "commonnum.db";
	private static final String FILE_PACKAGE = "com.example.mytestviewpager";
	private static final String FILE_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ FILE_PACKAGE+"/"+FILE_NAME;

	public static void readUpdataDB(InputStream path) throws IOException {
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

	public static List<ClassInfo> readClassListTable() {
		List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
		SQLiteDatabase dataBase = SQLiteDatabase.openOrCreateDatabase(
				FILE_PATH, null);
		String sql = "select * from classlist";
		Cursor cursor = dataBase.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				int idx = cursor.getInt(cursor.getColumnIndex("idx"));
				ClassInfo classInfo = new ClassInfo(name, idx);
				classInfoList.add(classInfo);
			} while (cursor.moveToNext());
			cursor.close();
			dataBase.close();
		}

		return classInfoList;
	}
	public static List<TableClass> readTableClass(String tableName){
		List<TableClass> list=new ArrayList<TableClass>();
		SQLiteDatabase dataBase=SQLiteDatabase.openOrCreateDatabase(FILE_PATH, null);
		String sql=" select * from "+ tableName;
		Cursor cursor=dataBase.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			do{
				String name=cursor.getString(cursor.getColumnIndex("name"));
				long number=cursor.getLong(cursor.getColumnIndex("number"));
				TableClass classInfo=new TableClass(name,number);
				list.add(classInfo);
			}while(cursor.moveToNext());
			cursor.close();
			dataBase.close();
		}
		return list;
	}
}
