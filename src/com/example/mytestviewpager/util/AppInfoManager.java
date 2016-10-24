package com.example.mytestviewpager.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mytestviewpager.bean.AppInfo;
import com.example.mytestviewpager.bean.RunningAppInfo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug;

public class AppInfoManager {

	private Context context;
	private PackageManager packManager;
	private ActivityManager activityManager;

	private List<AppInfo> allPackageInfos = new ArrayList<AppInfo>();
	private List<AppInfo> systemPackageInfos = new ArrayList<AppInfo>();
	private List<AppInfo> usePackageInfos = new ArrayList<AppInfo>();

	@SuppressLint("ServiceCast")
	public AppInfoManager(Context context) {
		this.context = context;
		packManager = context.getPackageManager();
		activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
	}

	private static AppInfoManager manager = null;

	public static AppInfoManager getAppInfoManager(Context context) {
		if (manager == null) {
			manager = new AppInfoManager(context);
		}
		return manager;
	}

	public void KillProcesses(String packageName) {
		activityManager.killBackgroundProcesses(packageName);
	}

	public void KillAllProcesses() {
		List<RunningAppProcessInfo> allProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo runAppInfo : allProcesses) {
			if (runAppInfo.importance > RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				String processName = runAppInfo.processName;
				try {
					ApplicationInfo appInfo = packManager.getApplicationInfo(
							processName, PackageManager.GET_META_DATA
									| PackageManager.GET_SHARED_LIBRARY_FILES
									| PackageManager.GET_SHARED_LIBRARY_FILES
									| PackageManager.GET_UNINSTALLED_PACKAGES);
					if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					} else {
						activityManager.killBackgroundProcesses(processName);
					}
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public final static int SYSTEM_TYPE_FLAG = 1;
	public final static int USER_TYPE_FLAG = 0;

	// public Map<Integer,List<RunningAppInfo>> getRunningAppInfos(){
	// Map<Integer, List<RunningAppInfo>> appInfos=new
	// HashMap<Integer,List<RunningAppInfo>>();
	// List<RunningAppInfo> systemInfos=new ArrayList<RunningAppInfo>();
	// List<RunningAppInfo> userInfos=new ArrayList<RunningAppInfo>();
	// List<RunningAppProcessInfo>
	// allInfos=activityManager.getRunningAppProcesses();
	// for(RunningAppProcessInfo run:allInfos){
	// int pid=run.pid;
	// String packageName=run.processName;
	// int importance=run.importance;
	// if(importance>=RunningAppProcessInfo.IMPORTANCE_SERVICE){
	// Drawable icon;
	// String lableName;
	// long size;
	// Debug.MemoryInfo[] memoryInfos=activityManager.getProcessMemoryInfo(new
	// int[]{pid});
	// size=(memoryInfos[0].getTotalPrivateDirty())*1024;
	// try{
	// icon=packManager.getApplicationIcon(packageName);
	// ApplicationInfo appInfos2 = packManager.getApplicationInfo(
	// packageName, PackageManager.GET_META_DATA
	// |PackageManager.GET_SHARED_LIBRARY_FILES
	// |PackageManager.GET_UNINSTALLED_PACKAGES);
	// lableName=packManager.getApplicationLabel(appInfos2).toString();
	// RunningAppInfo appInfo=new RunningAppInfo(packageName, icon, lableName,
	// size);
	// if((appInfos2.flags&ApplicationInfo.FLAG_SYSTEM)!=0){
	// appInfo.setSystem(true);
	// appInfo.setClear(false);
	// systemInfos.add(appInfo);
	// }else{
	// appInfo.setSystem(false);
	// appInfo.setClear(true);
	// userInfos.add(appInfo);
	// }
	// }catch(NameNotFoundException e){
	// e.printStackTrace();
	// }
	// }
	// }
	// appInfos.put(SYSTEM_TYPE_FLAG, systemInfos);
	// appInfos.put(USER_TYPE_FLAG,userInfos);
	// return appInfos;
	// }

	/** 获取正在运行应用 */
	public Map<Integer, List<RunningAppInfo>> getRuningAppInfos() {
		Map<Integer, List<RunningAppInfo>> runingAppInfos = new HashMap<Integer, List<RunningAppInfo>>();
		List<RunningAppInfo> sysapp = new ArrayList<RunningAppInfo>();
		List<RunningAppInfo> userapp = new ArrayList<RunningAppInfo>();
		// 获取所有正在运行应用
		List<RunningAppProcessInfo> appProcessInfos = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcessInfo : appProcessInfos) {
			String packageName = appProcessInfo.processName; // 正在运行程序进程名
			int pid = appProcessInfo.pid; // 正在运行程序进程ID
			int importance = appProcessInfo.importance; // 正在运行程序进程级别
			// 服务进程（包括）级别以下进程
			if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				Drawable icon; // 所取数据：运行中程序图标
				String lableName; // 所取数据：运行中程序名称
				long size; // 所取数据：运行中程序所占内存
				// 获取每个进程ID(集合)占用的内存大小(集合), pid和MemoryInfo是一一对应的
				// 返回一个或多个进程的内存使用情况
				Debug.MemoryInfo[] memoryInfos = activityManager
						.getProcessMemoryInfo(new int[] { pid });
				size = (memoryInfos[0].getTotalPrivateDirty()) * 1024;// 获取个人
				try {
					icon = packManager.getApplicationIcon(packageName);
					ApplicationInfo applicationInfo = packManager
							.getApplicationInfo(
									packageName,
									PackageManager.GET_META_DATA
											| PackageManager.GET_SHARED_LIBRARY_FILES
											| PackageManager.GET_UNINSTALLED_PACKAGES);
					// 返回此应用程序的标签
					lableName = packManager
							.getApplicationLabel(applicationInfo).toString();
					RunningAppInfo runingAppInfo = new RunningAppInfo(
							packageName, null, lableName, size);
					// 系统进程
					if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
						runingAppInfo.setSystem(true);
						runingAppInfo.setClear(false);
						sysapp.add(runingAppInfo);
					}
					// 用户进程(默认选中)
					else {
						runingAppInfo.setSystem(false);
						runingAppInfo.setClear(true);
						userapp.add(runingAppInfo);
					}
				} catch (NameNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
		runingAppInfos.put(SYSTEM_TYPE_FLAG, sysapp);
		runingAppInfos.put(USER_TYPE_FLAG, userapp);
		return runingAppInfos;
	}

	public List<AppInfo> getAllPackageInfos(boolean isReset) {
		if (isReset) {
			loadAllDataPackageInfo();
		}
		return allPackageInfos;
	}

	public List<AppInfo> getSystemPackageInfo(boolean isReset) {
		if (isReset) {
			loadAllDataPackageInfo();
			systemPackageInfos.clear();
			for (AppInfo pi : allPackageInfos) {
				if ((pi.getPackageInfo().applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					systemPackageInfos.add(pi);
				}
			}
		}
		return systemPackageInfos;
	}

	public List<AppInfo> getUsePackageInfo(boolean isReset) {
		if (isReset) {
			loadAllDataPackageInfo();
			usePackageInfos.clear();
			for (AppInfo pi : allPackageInfos) {
				if ((pi.getPackageInfo().applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				} else {
					usePackageInfos.add(pi);
				}
			}
		}
		return usePackageInfos;
	}

	public void loadAllDataPackageInfo() {
		List<PackageInfo> allInfos = packManager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_UNINSTALLED_PACKAGES);
		allPackageInfos.clear();
		for (PackageInfo pi : allInfos) {
			allPackageInfos.add(new AppInfo(pi));
		}
	}

}
