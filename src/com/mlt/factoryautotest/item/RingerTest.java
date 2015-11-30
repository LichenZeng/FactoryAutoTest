package com.mlt.factoryautotest.item;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;

import com.mlt.factoryautotest.FactoryAutoTest;
import com.mlt.factoryautotest.R;
import com.mlt.factoryautotest.times.FactoryAutoTime;
import com.mlt.factoryautotest.tools.Logset;

public class RingerTest {

	private static String TAG = "FactoryAuto_RingerTest";
	public static boolean mIsRingerTestStop = false;
	public static MediaPlayer mMediaPlayer;
	private static AudioManager mAudioManager;
	
	public static long mRingStartTime = 0;  
	public static long mRingOverTime = 0; 
	public static long mRingTestTime = 0;  
	
	public static void RingerTestCase(){
		
		FactoryAutoTest.TextViewColorChange(FactoryAutoTest.mTest6,FactoryAutoTest.mTest7);
		FactoryAutoTest.mTest7.setText(R.string.ringering);
		StartRingerTest();
	}
	
	private final static  Handler handler = new Handler();  
	static int ringertestkey = 1;
	private final static  Runnable ringertask = new Runnable() {  
        public void run() {  
        	if (!mIsRingerTestStop) {
        		switch (ringertestkey) {
				case 1:
					playRawMusic();
					mRingStartTime = FactoryAutoTime.getCurentSecond();
					Logset.logd(TAG, "RingerTest  testing");
					ringertestkey = 2;
					handler.postDelayed(this,4000);  
					break;
				case 2:
					if (mMediaPlayer != null) {
						mMediaPlayer.stop();
				    }
					FactoryAutoTest.mTest7.setText(R.string.ringer);
					mRingOverTime = FactoryAutoTime.getCurentSecond();
					mRingTestTime = mRingOverTime - mRingStartTime;
					Logset.logd(TAG, "RingerTest test over");
					Logset.logd(TAG, "RingerTest test time:"+mRingTestTime);
					mIsRingerTestStop = true;
					FactoryAutoTest.factoryAutoTest.handler.sendEmptyMessage(FactoryAutoTest.MSG_VIDEO_TEST);
					break;
				default:
					break;
				}
			}
        }  
    }; 
    
    public static void StartRingerTest() {
    	ringertestkey = 1;
    	mIsRingerTestStop = false;
    	mAudioManager = (AudioManager) FactoryAutoTest.mContext.getSystemService(Context.AUDIO_SERVICE);
        mMediaPlayer = new MediaPlayer();
        Logset.logd(TAG, "RingerTest test start");
		handler.post(ringertask);
	}
    
    
    /** 
	* @MethodName: playmusic 
	* @Description:playing music ,music file at values/raw
	* @return void   
	*/
	public static void playRawMusic() {
		mAudioManager.setMode(AudioManager.MODE_NORMAL); //Turn into Loudspeaker playing mode
		try {
			 try {
				 mMediaPlayer = MediaPlayer.create(FactoryAutoTest.mContext, com.mlt.factoryautotest.R.raw.backroad);
				 mMediaPlayer.setLooping(true); 
				 mMediaPlayer.setVolume(0.9f, 0.9f); // set play music sound size
				 mMediaPlayer.start();
			 } catch (Exception e) {
             	e.printStackTrace();
             }
			 
		}  catch (Exception e) {
         	e.printStackTrace();
        } 
	}
    

}
