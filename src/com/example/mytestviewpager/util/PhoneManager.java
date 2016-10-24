package com.example.mytestviewpager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class PhoneManager {

	private Context context;
	private static PhoneManager manager;

	private WifiManager wifiManager;
	private ConnectivityManager connectivityManager;
	private TelephonyManager telephoneManager;

	private PhoneManager(Context context) {
		this.context = context;
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		telephoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public static PhoneManager getPhoneManager(Context context){
		if(manager==null){
			manager=new PhoneManager(context);
		}
		return manager;
	}
	
	/** 设备Wifi名称 */
	public String getPhoneWifiName() {
		WifiInfo info = wifiManager.getConnectionInfo();
		return info.getSSID() + "";
	}

	/** 设备Wifi的IP */
//	public String getPhoneWifiIP() {
//		WifiInfo info = wifiManager.getConnectionInfo();
//		long ip = info.getIpAddress();
//		return longToIP(ip);
//	}

	/** 设备Wifi的速度 */
	public String getPhoneWifiSpeed() {
		WifiInfo info = wifiManager.getConnectionInfo();
		return info.getLinkSpeed() + "";
	}

	/** 设备Wifi的MAC */
	public String getPhoneWifiMac() {
		WifiInfo info = wifiManager.getConnectionInfo();
		return info.getMacAddress() + "";
	}



/** 设备品牌(moto?) */
	public String getPhoneName1() {
		return Build.BRAND;
	}

/** 设备系统版本号 (4.1.2?) */
	public String getPhoneSystemVersion() {
		return Build.VERSION.RELEASE;
	}

/** 设备CPU名称 */
	public String getPhoneCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



/** 设备CPU数量 */
	public int getPhoneCpuNumber() {
		class CpuFilter implements FileFilter {
			public boolean accept(File pathname) {
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}
		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new CpuFilter());
			return files.length;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}


/**
	 * 获取手机分辨率
	 */
	public String getResolution() {
		String resolution = "";
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		resolution = metrics.widthPixels + "*" + metrics.heightPixels;
		return resolution;
	}

/**
	 * 获取照片最大分辨率
	 */
	public String getMaxPhotoSize() {
		String maxSize = "";
		Camera camera = Camera.open();
		Camera.Parameters parameters = camera.getParameters();
		List<Size> sizes = parameters.getSupportedPictureSizes();
		Size size = null;
		for (Size s : sizes) {
			if (size == null) {
				size = s;
			} else if (size.height * s.width < s.height * s.width) {
				size = s;
			}
		}
		maxSize = size.width + "*" + size.height;
		camera.release();
		return maxSize;
	}

/** 设备系统基带版本 */
	public String getPhoneSystemBasebandVersion() {
		return Build.RADIO;
	}


/**
	 * 判断当前手机是否有ROOT权限
	 * 
	 * @return
	 */
	public boolean isRoot() {
		boolean bool = false;

		try {
			if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
				bool = false;
			} else {
				bool = true;
			}
		} catch (Exception e) {

		}
		return bool;
	}
}
