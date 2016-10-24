package com.example.mytestviewpager.bean;

import android.content.pm.PackageInfo;

public class AppInfo {
	
	private PackageInfo packageInfo;
	private boolean isDel;
	public AppInfo(PackageInfo packageInfo,boolean isDel){
		super();
		this.packageInfo=packageInfo;
		this.isDel=isDel;
	}
	public PackageInfo getPackageInfo() {
		return packageInfo;
	}
	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}
	public boolean isDel() {
		return isDel;
	}
	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}
	public AppInfo(PackageInfo packageInfo){
		super();
		this.packageInfo=packageInfo;
	}
}
