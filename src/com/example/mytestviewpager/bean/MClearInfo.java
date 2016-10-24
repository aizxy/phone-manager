package com.example.mytestviewpager.bean;

import android.graphics.drawable.Drawable;

public class MClearInfo {
	private int id;
	private String softChineseName;
	private String softEnglishName;
	private String apkName;
	private String filePath;
	private Drawable icon;
	private long size;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public MClearInfo(int id, String softChineseName, String softEnglishName,
			String apkName, String filePath) {
		this.apkName = apkName;
		this.filePath = filePath;
		this.id = id;
		this.softChineseName = softChineseName;
		this.softEnglishName = softEnglishName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSoftChineseName() {
		return softChineseName;
	}

	public void setSoftChineseName(String softChineseName) {
		this.softChineseName = softChineseName;
	}

	public String getSoftEnglishName() {
		return softEnglishName;
	}

	public void setSoftEnglishName(String softEnglishName) {
		this.softEnglishName = softEnglishName;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getFilePath() {
		return filePath;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "MClearInfo[id=" + id + ",softChineseName=" + softChineseName
				+ ",softEnglishName=" + softEnglishName + ",apkName=" + apkName
				+ ",filePath=" + filePath+",size="+size+"]";
	}

}
