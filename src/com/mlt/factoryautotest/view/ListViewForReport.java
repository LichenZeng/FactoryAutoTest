package com.mlt.factoryautotest.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mlt.factoryautotest.FactoryAutoTest;
import com.mlt.factoryautotest.R;
import com.mlt.factoryautotest.item.CameraTest;
import com.mlt.factoryautotest.item.LCDTest;
import com.mlt.factoryautotest.item.ReceiverLoopTest;
import com.mlt.factoryautotest.item.RecordPlayTest;
import com.mlt.factoryautotest.item.RingerTest;
import com.mlt.factoryautotest.item.VibratorTest;
import com.mlt.factoryautotest.item.VideoTest;

public class ListViewForReport {
	
	
	/** Called when the activity is first created. */
    private static ListView listview;
    private static MyAdapter adapter;
    static List<Map<String,Object>> listMaps;
    static int count = 0;
    
    private static  long  LCDTestTime;
    private static  long  FrontCameraTestTime;
    private static  long  BackCamerTestTime; 
    private static  long  ReceiverTestTime;
    private static  long  RecordTestTime;
    private static  long  VibratorTestTime;
    private static  long  RingTestTIme;
    private static  long  VideoTestTime;
    private static  long  AllTestTime;
    private static String TestResult;
    private static int TestResultColor;
	
	/** 
	* @MethodName: ViewReport 
	* @Functions:Display test results.
	* @return	:void   
	*/
	public static void ViewReport() {
		//chb modify for VFOZBAUBQ-57 2015-9-9
		if (FactoryAutoTest.mCurrentTestTimes >= 1) {
			getTestTime();
            Map<String, Object> map = new HashMap<String, Object>();
           
            /**get the  time for test item*/
            map.put("test_time"+(adapter.mItemList.size()), AllTestTime);
            map.put("test_lcd"+(adapter.mItemList.size()), LCDTestTime);
            map.put("test_front"+(adapter.mItemList.size()), FrontCameraTestTime);
            map.put("test_back"+(adapter.mItemList.size()), BackCamerTestTime);
            map.put("test_receiver"+(adapter.mItemList.size()), ReceiverTestTime);
            map.put("test_record"+(adapter.mItemList.size()), RecordTestTime);
            map.put("test_vibrator"+(adapter.mItemList.size()), VibratorTestTime);
            map.put("test_ring"+(adapter.mItemList.size()), RingTestTIme);
            map.put("test_video"+(adapter.mItemList.size()), VideoTestTime);
            map.put("test_result"+(adapter.mItemList.size()), TestResult);
            map.put("test_result_color"+(adapter.mItemList.size()), TestResultColor);
            
            listMaps.add(map);
            adapter.mItemList = listMaps;
            adapter.notifyDataSetChanged();
		}
	}
	
	/** 
	* @MethodName: getTestTime 
	* @Functions:Get each time a test to obtain
	* @return	:void   
	*/
	public static void getTestTime() {
		AllTestTime = FactoryAutoTest.mALLTestTime;
		LCDTestTime = LCDTest.mLCDTestTime;
		FrontCameraTestTime = CameraTest.mFrontCameraTestTime;
		BackCamerTestTime = CameraTest.mBackCameraTestTime;
		ReceiverTestTime = ReceiverLoopTest.mReceiverTestTime;
		RecordTestTime = RecordPlayTest.mRecordTestTime;
		VibratorTestTime = VibratorTest.mVibratorTestTime;
		RingTestTIme = RingerTest.mRingTestTime;
		VideoTestTime = VideoTest.mVideoTestTime;
		TestResult =  FactoryAutoTest.mTestResult;
		TestResultColor = FactoryAutoTest.mTestResultColor;
	}
	
	/** 
	* @MethodName: InitListView 
	* @Functions:Init the ListView Adapter
	* @return	:void   
	*/
	public static void InitListView() {
		listview = FactoryAutoTest.mReportListView;
	
        listMaps = new ArrayList<Map<String,Object>>();

        adapter = new MyAdapter(FactoryAutoTest.mContext, listMaps, R.layout.report, 
        		 new String[]{ "tv","report_timea"}, 
                new int[]{ R.id.report_times,R.id.report_timea});
        
        listview.setAdapter(adapter);
	}
	
    private static class MyAdapter extends SimpleAdapter{
        private List<Map<String, Object>> mItemList;
        @SuppressWarnings("unchecked")
		public MyAdapter(Context context, List<? extends Map<String, Object>> data,int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            mItemList = (List<Map<String, Object>>) data;
            if(data == null){
                count = 0;
            }else{
                count = data.size();
            }
        }
        public int getCount() {
            return mItemList.size();
        }

        public Object getItem(int pos) {
            return pos;
        }

        public long getItemId(int pos) {
            return pos;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	
            Map<String, Object> map = mItemList.get(position);
            /**get Test Item time*/
            Long testTime  =  (Long) map.get("test_time"+position);
            Long testLCD  =  (Long) map.get("test_lcd"+position);
            Long testFront  =  (Long) map.get("test_front"+position);
            Long testBack  =  (Long) map.get("test_back"+position);
            Long testReceiver  =  (Long) map.get("test_receiver"+position);
            Long testRecord  =  (Long) map.get("test_record"+position);
            Long testVibrator  =  (Long) map.get("test_vibrator"+position);
            Long testRing  =  (Long) map.get("test_ring"+position);
            Long testVideo  =  (Long) map.get("test_video"+position);
            /**Test Result and color*/
            String testResult  =  (String) map.get("test_result"+position);
            int testResultColor  =  (int) map.get("test_result_color"+position);
            
            View view = super.getView(position, convertView, parent);
            
            //set the textview ID
            TextView tvTestItem = (TextView)view.findViewById(R.id.report_times);
            TextView tvTestLCD = (TextView)view.findViewById(R.id.report_time1);
            TextView tvTestFront = (TextView)view.findViewById(R.id.report_time2);
            TextView tvTestBack = (TextView)view.findViewById(R.id.report_time3);
            TextView tvTestReceiver = (TextView)view.findViewById(R.id.report_time4);
            TextView tvTestRecord = (TextView)view.findViewById(R.id.report_time5);
            TextView tvTestVibrator = (TextView)view.findViewById(R.id.report_time6);
            TextView tvTestRing = (TextView)view.findViewById(R.id.report_time7);
            TextView tvTestVideo = (TextView)view.findViewById(R.id.report_time8);
            TextView tvAllTestTime = (TextView)view.findViewById(R.id.report_timea);
            TextView tvTestResult = (TextView)view.findViewById(R.id.report_result);
            
            int i = position +1;
            //set the test item time
            tvTestItem.setText(R.string.test);tvTestItem.append(i+":");
            tvTestLCD.setText(testLCD+"S");
			tvTestFront.setText(testFront+"S");
			tvTestBack.setText(testBack+"S");
			tvTestReceiver.setText(testReceiver+"S");
			tvTestRecord.setText(testRecord+"S");
			tvTestVibrator.setText(testVibrator+"S");
			tvTestRing.setText(testRing+"S");
			tvTestVideo.setText(testVideo+"S");
			tvAllTestTime.setText(testTime+"S");

			//view the test result  
			tvTestResult.setText(testResult+"");tvTestResult.setTextColor(testResultColor);

			return view;
        }
    }

}
