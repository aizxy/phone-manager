package com.example.mytestviewpager.bean;

import android.graphics.drawable.Drawable;

public class RunningAppInfo {
	
	private String packageName;
	private Drawable icon;
	private String lableName;
	private long size;
	private boolean isSystem;
	private boolean isClear;
	
	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	public boolean isClear() {
		return isClear;
	}

	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}

	public RunningAppInfo(String packageName,Drawable icon,String lableName,long size) {
		super();
		this.packageName=packageName;
		this.icon=icon;
		this.lableName=lableName;
		this.size=size;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
