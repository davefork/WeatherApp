package com.example.myview;

import com.example.adapter.WeatherMainListview;
import com.example.controler.WeatherDataManager;
import com.example.myview.RefreshableView.PullToRefreshListener;
import com.example.weatherapp.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class MainUI extends RelativeLayout{
	private Context context;
	private FrameLayout leftMenu;
	private FrameLayout middlePart;
	private Scroller mScroller;
	
	//---------------初始化界面的变量！！！------------------//
	private WeatherDataManager wm;
	private RefreshableView refreshableView;
	//---------------初始化界面的变量！！！------------------//
	
	public MainUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		// TODO Auto-generated constructor stubcontext
	}
	

	public MainUI(Context context) {
		super(context);
		initView(context);
		// TODO Auto-generated constructor stub
	}
	
	//---------------初始化界面的各个部分！！！------------------//
	private void setMiddlePart(){
		View layoutMiddle=LayoutInflater.from(context).inflate(R.layout.activity_weather, null);
		middlePart.addView(layoutMiddle);
		ListView lv=(ListView) layoutMiddle.findViewById(R.id.mainList);
		wm=new WeatherDataManager();
		lv.setAdapter(new WeatherMainListview(context,wm));
		
		
		refreshableView = (RefreshableView) findViewById(R.id.refreshView);  
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {  
            @Override  
            public void onRefresh() {  
                try {  
                    Thread.sleep(3000);  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
                refreshableView.finishRefreshing();  
            }  
        }, 0);  
		 
	}
	
	//---------------初始化界面的各个部分！！！------------------//
	
	//为了创建左右菜单，所以需要context上下文
	private void initView(Context context){
		this.context=context;
		leftMenu=new FrameLayout(context);
		middlePart=new FrameLayout(context);
		
		//leftMenu.setBackgroundColor(Color.RED);
		//middlePart.setBackgroundColor(Color.GREEN);
		addView(middlePart);
		setMiddlePart();
		addView(leftMenu);
		
		mScroller=new Scroller(context,new DecelerateInterpolator());
	}
	
	//测量屏幕，  此函数的参数是当前屏幕的宽度和高度
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		middlePart.measure(widthMeasureSpec, heightMeasureSpec);
		int realWidth=MeasureSpec.getSize(widthMeasureSpec);
		int tempWidthMeasure=MeasureSpec.makeMeasureSpec((int)(realWidth*0.8f), MeasureSpec.EXACTLY); 
		leftMenu.measure(tempWidthMeasure, heightMeasureSpec);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		middlePart.layout(l, t, r, b);
		leftMenu.layout(l-leftMenu.getMeasuredWidth(), t, r, b);
		
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
			return true;
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
						finalX=Math.max(expectX, -leftMenu.getMeasuredWidth());
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
					if(Math.abs(curScrollX)>leftMenu.getMeasuredWidth()/2){
						if(curScrollX<0){
							mScroller.startScroll(curScrollX, 0, -leftMenu.getMeasuredWidth()-curScrollX, 0);
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
		
		
		return super.onTouchEvent(ev);
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
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		// TODO Auto-generated method stub
//		if(!isTestComplete){
//			getEventType(ev);
//			return true;
//		}
//		else{
//			if(isrightleftEvent){
//				switch(ev.getActionMasked()){
//				
//				case MotionEvent.ACTION_MOVE:
//					int curScroll=getScrollX();
//					int dis_x=(int)(ev.getX()-pt.x);
//					System.out.println("currScroll:"+curScroll);
//					System.out.println("dis_x:"+dis_x);
//					int expectX=-dis_x+curScroll;
//					int finalX=0;
//					if(expectX<0){
//						finalX=Math.max(expectX, -leftMenu.getMeasuredWidth());
//					}
//					else{
//						finalX=expectX;
//					}
//					scrollTo(finalX,0);
//					pt.x=(int) ev.getX();
//					break;
//				case MotionEvent.ACTION_UP:
//				case MotionEvent.ACTION_CANCEL:
//					
//					isTestComplete=false;
//					isrightleftEvent=false;
//					break;
//				}
//			}else{
//				isTestComplete=false;
//				isrightleftEvent=false;
//			}
//		}
//		
//		
//		return super.dispatchTouchEvent(ev);
//	}
	
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
