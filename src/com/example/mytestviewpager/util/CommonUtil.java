package com.example.mytestviewpager.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
	private static DecimalFormat df=new DecimalFormat("#.00");
	
	public static String getFileInfo(long file){
		StringBuffer strBuff=new StringBuffer();
		if(file<1024){
			strBuff.append(file);
			strBuff.append("B");
		}else if(file<1024*1024){
			strBuff.append(df.format((double)(file/1024)));
			strBuff.append("K");
		}else if(file<(1024*1024*1024)){
			strBuff.append(df.format((double)(file/(1024*1024))));
			strBuff.append("M");
		}else if(file<1111111111111l){
			strBuff.append(df.format((double)(file/(1024*1024*1024))));
			strBuff.append("G");
		}
		return strBuff.toString();
	}
	
	public static String getStrTime(long fileTime){
		if(fileTime==0){
			return "δ֪";
		}
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String fTime=formatter.format(new Date(fileTime));
		return fTime;
	}
}
