package com.mlt.factoryautotest.tools;

import android.util.Log;

/**
 * Log统一管理类
 * 
 * @author chb
 * 
 */
public class Logset
{

	private Logset()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "FactoryAutoTest";

	// 下面四个是默认tag的函数 ,直接调用方便
	public static void logi(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void logd(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void loge(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void logv(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}
	
	

	// 下面是传入自定义tag的函数,可以传入自定义的TAG
	public static void logi(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void logd(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void loge(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void logv(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}
	
}