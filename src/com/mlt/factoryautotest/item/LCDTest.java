package com.mlt.factoryautotest.item;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import com.mlt.factoryautotest.FactoryAutoTest;
import com.mlt.factoryautotest.R;
import com.mlt.factoryautotest.times.FactoryAutoTime;
import com.mlt.factoryautotest.tools.Logset;


/**
 * LCDTest :The Test for LCD.
 */
public class LCDTest {
	
	private static String TAG = "FactoryAuto_LCDTest";
	public static boolean mIsLcdTestStop = false;  
	
	public static long mLCDStartTime = 0;  
	public static long mLCDOverTime = 0;  
	public static long mLCDTestTime = 0;  
	
	private final static  Handler handler = new Handler();  
	static int lcdtestkey = 1;
	private final static  Runnable lcdtask = new Runnable() {  
        public void run() {  
        	if (!mIsLcdTestStop) {
        		switch (lcdtestkey) {
				case 1:
					mLCDStartTime = FactoryAutoTime.getCurentSecond();
					FactoryAutoTest.mAllTestStartTime = FactoryAutoTime.getCurentSecond();
					FactoryAutoTest.mLcdLinearLayout.setVisibility(View.VISIBLE);
					Logset.logd(TAG, "LCDTest test testing");
					lcdtestkey = 2;
					handler.postDelayed(this,900); 
					break;
				case 2:
					FactoryAutoTest.mLcdLinearLayout.setVisibility(View.INVISIBLE);
					FactoryAutoTest.mainLayout.setBackgroundColor(Color.RED);
					lcdtestkey = 3;
					handler.postDelayed(this, 800);  
					break;
				case 3:
					FactoryAutoTest.mainLayout.setBackgroundColor(Color.GREEN);
					lcdtestkey = 4;
					handler.postDelayed(this, 800);  
					break;
				case 4:
					FactoryAutoTest.mainLayout.setBackgroundColor(Color.BLUE);
					lcdtestkey = 5;
					handler.postDelayed(this, 800);  
					break;
				case 5:
					FactoryAutoTest.mainLayout.setBackgroundColor(Color.BLACK);
					lcdtestkey = 6;
					handler.postDelayed(this, 800);
					break;
				case 6:
					mIsLcdTestStop = true;
					FactoryAutoTest.mainLayout.setBackgroundColor(Color.WHITE);
					FactoryAutoTest.mTest1.setText(R.string.lcd);
					mLCDOverTime = FactoryAutoTime.getCurentSecond();
					mLCDTestTime = mLCDOverTime - mLCDStartTime;
					Logset.logd(TAG, "LCDTest test over");
					Logset.logd(TAG, "LCDTest test time: "+mLCDTestTime);
					FactoryAutoTest.factoryAutoTest.handler.sendEmptyMessage(FactoryAutoTest.MSG_FORNT_CAMERA_TEST);
					break;
				}
			}
        }  
    };  
    
	/** 
	 * Start LCD test.
	*/
	public static void LcdTestCase() {
		lcdtestkey = 1;
		mIsLcdTestStop = false;
		FactoryAutoTest.TextViewColorChange(FactoryAutoTest.mTest8,FactoryAutoTest.mTest1);
		FactoryAutoTest.mTest1.setText(R.string.lcding);
		Logset.logd(TAG, "LCDTest test start");
		handler.post(lcdtask);  
	}
}
	
	
	
	
