package com.example.mytestviewpager.bean;

public class ClassInfo {
	String name;
	int idx;
	public ClassInfo(String name,int idx) {
		this.idx=idx;
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}

}
