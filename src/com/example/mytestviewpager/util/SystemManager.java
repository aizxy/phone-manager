package com.example.mytestviewpager.util;

import android.os.Build;

public class SystemManager {
	public static String getPhoneName(){
		return Build.BRAND;
	}
	public static String getSystemPhoneModel(){
		return Build.VERSION.RELEASE;
	}
	public static String getPhoneModel(){
		return Build.MODEL+"Android"+getSystemPhoneModel();
	}
}
