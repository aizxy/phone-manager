package com.example.mytestviewpager.bean;

import android.graphics.drawable.Drawable;

public class PhoneInfo {
	
	private String title;
	private String content;
	private Drawable icon;
	public PhoneInfo(String title,String content,Drawable icon){
		super();
		this.content=content;
		this.icon=icon;
		this.title=title;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
}
