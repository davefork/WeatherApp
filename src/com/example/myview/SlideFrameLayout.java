package com.example.myview;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SlideFrameLayout extends FrameLayout {

	private Scroller mScroller;
	private int leftMenuWidth;
	private Context context;
	
	public SlideFrameLayout(Context context) {
		super(context);
		this.context=context;
		initLayout();
	}

	public SlideFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		initLayout();
		
	}
	public void initLayout(){
		mScroller=new Scroller(context,new DecelerateInterpolator());
	}
	
	public void setMeasure(int leftMenuWidth){
		this.leftMenuWidth=leftMenuWidth;
	}
	
	private boolean isTestComplete=false;
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		getEventType(ev);
		if(isTestComplete){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(!isTestComplete){
			getEventType(ev);
			return false;
		}
		else{
			if(isrightleftEvent){
				switch(ev.getActionMasked()){
				
				case MotionEvent.ACTION_MOVE:
					int curScroll=getScrollX();
				
					int dis_x=(int)(ev.getX()-pt.x);
					System.out.println("currScroll:"+curScroll);
					System.out.println("dis_x:"+dis_x);
					int expectX=-dis_x+curScroll;
					int finalX=0;
					if(expectX<0){
						finalX=Math.max(expectX, -leftMenuWidth);
					}
					else{
						finalX=expectX;
					}
					scrollTo(finalX,0);
					pt.x=(int) ev.getX();
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					int curScrollX=getScrollX();
					if(Math.abs(curScrollX)>leftMenuWidth/2){
						if(curScrollX<0){
							mScroller.startScroll(curScrollX, 0, -leftMenuWidth-curScrollX, 0);
						}
					}
					else{
						mScroller.startScroll(curScrollX, 0, -curScrollX, 0);
					}
					invalidate();
					isTestComplete=false;
					isrightleftEvent=false;
					break;
				}
			}else{
				isTestComplete=false;
				isrightleftEvent=false;
			}
		}
		
		
		return true;
	}
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		super.computeScroll();
		if(!mScroller.computeScrollOffset()){
			return;
		}
		int tempX=mScroller.getCurrX();
		scrollTo(tempX, 0);
	}

	private Point pt=new Point();
	private static final int TEST_DIS=20;
	private boolean isrightleftEvent;
	
	private void getEventType(MotionEvent ev){
		switch(ev.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
			pt.x=(int) ev.getX();
			pt.y=(int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int dX=Math.abs((int)ev.getX()-pt.x);
			int dY=Math.abs((int)ev.getY()-pt.y);
			if(dX>=TEST_DIS&&dX>=dY){
				isrightleftEvent=true;
				isTestComplete=true;
				pt.x=(int) ev.getX();
				pt.y=(int) ev.getY();
			}else if(dY>=TEST_DIS&&dY>dX){
				isrightleftEvent=false;
				isTestComplete=false;
				pt.x=(int) ev.getX();
				pt.y=(int) ev.getY();
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
			
		case MotionEvent.ACTION_CANCEL:
			break;
		}
	}
}
