package com.gk.cbl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUnit {
	//时间戳转字符串
	public static String fromTimeStampToString(long time){
		String res;
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    long lt = new Long(time);
	    Date date = new Date(lt);
	    res = simpleDateFormat.format(date);
	    return res;
	}
	//字符串转时间类型
	public static Date StringToDate(String str){
		Date myDate = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			myDate = sdf.parse(str);
		}catch (Exception e){
			e.printStackTrace();
		}
		return myDate;
	}

	//获取当前时间
	public static Date getDate(){
		return new Date();
	}
}	
