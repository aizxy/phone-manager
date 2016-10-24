package com.example.mytestviewpager.bean;

import java.io.File;

public class FileInfo {
	private File file;
	private boolean isSelect;
	private String icon;
	private String fileType;
	
	public FileInfo(File file,String icon,String fileType){
		this.file=file;
		this.icon=icon;
		this.fileType=fileType;
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
