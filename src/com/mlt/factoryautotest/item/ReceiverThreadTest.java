package com.mlt.factoryautotest.item;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Handler;
import android.widget.TextView;

import com.mlt.factoryautotest.FactoryAutoTest;
import com.mlt.factoryautotest.R;
import com.mlt.factoryautotest.times.FactoryAutoTime;
import com.mlt.factoryautotest.tools.Logset;

public class ReceiverThreadTest {
	private static String TAG = "FactoryAuto_ReceiverTest";
	static final int frequency = 44100;
	@SuppressWarnings("deprecation")
	static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	static int recBufSize;
	static int playBufSize;
	public static AudioRecord audioRecord;
	public static AudioTrack audioTrack;
	static AudioManager audioManager;

	private static int max; //ringtone max voice
	private static int current;// ringtone current voice
	private static int syscurrent;// ringtone current voice
	private static int sysMax;// ringtone current voice
	
	public static boolean mIsReceiverThreadTestStop = false;
	private static TextView mReceiverTextView;
	
	
	public static void ReceiverTestCase(){
		if (CameraTest.FindBackCameraExist()) {
			FactoryAutoTest.TextViewColorChange(FactoryAutoTest.mTest3,FactoryAutoTest.mTest4);
		}else {
			FactoryAutoTest.mTest4.setTextColor(Color.BLUE);
		}
		StartReceiverTest();
	}
	
	
	private final static  Handler handler = new Handler();  
	static int receiverthreadtestkey = 1;
	private final static  Runnable receiverthreadtask = new Runnable() {  
        public void run() {  
        	if (!mIsReceiverThreadTestStop) {
        		switch (receiverthreadtestkey) {
				case 1:
					handler.postDelayed(this,5000);  
					ReceiverLoopTest.mReceiverStartTime = FactoryAutoTime.getCurentSecond();
					new RecordPlayThread().start();// Open a thread-record edge play
					mReceiverTextView.setText(R.string.receivering);
			        Logset.logd(TAG, "ReceiverTest testing");
					receiverthreadtestkey = 2;
					break;
				case 2:
					mIsReceiverThreadTestStop = true;
					StopReceiver();
					ReceiverLoopTest.mReceiverOverTime = FactoryAutoTime.getCurentSecond();
					ReceiverLoopTest.mReceiverTestTime = ReceiverLoopTest.mReceiverOverTime - ReceiverLoopTest.mReceiverStartTime;
					Logset.logd(TAG, "ReceiverTest test over");
					mReceiverTextView.setText(R.string.str_receiver);
					FactoryAutoTest.factoryAutoTest.handler.sendEmptyMessage(FactoryAutoTest.MSG_RECORD_TEST);
					break;
				default:
					break;
				}
			}
        }  
    };  
	
	public static void StartReceiverTest() {
		mReceiverTextView = FactoryAutoTest.mTest4;
		receiverthreadtestkey = 1;
		mIsReceiverThreadTestStop = false;
		InitReceiver();
    	handler.post(receiverthreadtask);
	}
	
	
	public static void InitReceiver() {
		recBufSize = AudioRecord.getMinBufferSize(frequency,
				channelConfiguration, audioEncoding);

		playBufSize=AudioTrack.getMinBufferSize(frequency,
				channelConfiguration, audioEncoding);
		
		audioManager = (AudioManager) FactoryAutoTest.mContext.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setSpeakerphoneOn(false);   
		audioManager.setMode(AudioManager.MODE_IN_CALL);// The pattern playback mode to tune into the handset
		
		max = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
		current = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
		sysMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
		syscurrent = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
		
		
		//Set to maximum volume
		audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, max,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, sysMax,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		audioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_RAISE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		
		// -----------------------------------------
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,channelConfiguration, audioEncoding, recBufSize);

		audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, frequency,channelConfiguration, audioEncoding,playBufSize, AudioTrack.MODE_STREAM);
		//------------------------------------------
		audioTrack.setStereoVolume(1f, 1f);//Set the current volume
	}
	
	public static void StopReceiver() {
		audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, current,
				AudioManager.USE_DEFAULT_STREAM_TYPE);
		audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, syscurrent,
				AudioManager.USE_DEFAULT_STREAM_TYPE);
		audioManager.setMode(AudioManager.MODE_NORMAL);
		if (audioTrack != null) {
			audioTrack.stop();
			audioRecord.stop();
		}
	}
	
	static class  RecordPlayThread extends Thread {
		public void run() {
			if (!mIsReceiverThreadTestStop) {
				try {
					byte[] buffer = new byte[recBufSize];
					audioRecord.startRecording();//Start Record
					Thread.sleep(400);
					audioTrack.play();//Start play
					
					while (!mIsReceiverThreadTestStop) {
						//MIC stored data from the buffer
						int bufferReadResult = audioRecord.read(buffer, 0,recBufSize);
						byte[] tmpBuf = new byte[bufferReadResult];
						System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
						//Write data that is broadcast
						audioTrack.write(tmpBuf, 0, tmpBuf.length);
					}
					audioTrack.stop();
					audioRecord.stop();
				} catch (Throwable t) {
				}
			}
		}
	};

}
