package com.mlt.factoryautotest.times;

import android.annotation.SuppressLint;
import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @ClassName: FactoryAutoTime 
* @PackageName:com.mlt.factoryautotest.times
* @Description: Get the time
* @author:   chehongbin
* @date:     2015-8-7 下午3:43:59  
* Copyright (c) 2015 MALATA,All Rights Reserved.
*/
@SuppressLint("SimpleDateFormat")
public class FactoryAutoTime {
	
	/** 
	* @MethodName: getCurentTime 
	* @Functions:Get the current time
	* @return	:String   
	*/
	public static String getCurentTime(){
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(new java.util.Date()); 
		return date;
	}
	
	 /** 
	* @MethodName: getCurentSecond 
	* @Functions:The current time is converted into long type
	* @throws ParseException  
	* @return	:long   
	*/
	public static long getCurentSecond() throws ParseException{
		 String curentTime = getCurentTime();
		 SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = null;
		 long beginTime;
		 try {
			date = sDate.parse(curentTime);
		 } catch (java.text.ParseException e) {
			e.printStackTrace();
		 }
		 beginTime = date.getTime();
		 return beginTime/1000;
	 }
	 
	 public static long getTimeInterval(long startTime,long overTime) {
		 return overTime - startTime;
	 }

}
