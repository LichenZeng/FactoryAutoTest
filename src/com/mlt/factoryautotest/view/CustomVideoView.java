package com.mlt.factoryautotest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {

	    private int mVideoWidth;
	    private int mVideoHeight;

	    public CustomVideoView(Context context) {
	            super(context);
	    }

	    public CustomVideoView(Context context, AttributeSet attrs) {
	            super(context, attrs);
	    }

	    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
	            super(context, attrs, defStyle);
	    }
  
	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	            // Log.i("@@@@", "onMeasure");

	           //The following code is for video playback length and width is based on the parameters you set to determine
	            int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
	            int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
	            setMeasuredDimension(width, height);
	    }
	

}
