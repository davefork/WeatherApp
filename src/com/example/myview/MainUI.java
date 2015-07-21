package com.example.myview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.adapter.ListViewAdapter;
import com.example.adapter.WeatherMainListview;
import com.example.controler.DBManager;
import com.example.controler.WeatherDataManager;
import com.example.controler.cityInfo;
import com.example.myview.RefreshableView.PullToRefreshListener;
import com.example.weatherapp.R;
import com.example.weatherapp.WeatherActivity;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainUI extends RelativeLayout{
	private Context context;
	private FrameLayout leftMenu;
	private FrameLayout middlePart;
	private Scroller mScroller;
	
	//---------------��ʼ������ı���������------------------//
	private RefreshableView refreshableView;
	//---------------��ʼ������ı���������------------------//
	
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

	//---------------��ʼ������ĸ������֣�����------------------//
	private void setMiddlePart(){
		View layoutTitle=LayoutInflater.from(context).inflate(R.layout.activity_weather_title,null);
		View layoutMiddle=LayoutInflater.from(context).inflate(R.layout.activity_weather, null);
		View layoutBg=LayoutInflater.from(context).inflate(R.layout.background, null);
		//֮����Ҫ�ĵ��ĵط�������������������������������
		LayoutParams titleParams=new LayoutParams(MarginLayoutParams.MATCH_PARENT, (int)(WeatherActivity.height*0.2));	
		layoutTitle.setLayoutParams(titleParams);
		
		
		middlePart.addView(layoutBg);
		middlePart.addView(layoutTitle);
		middlePart.addView(layoutMiddle);
		ListView lv=(ListView) layoutMiddle.findViewById(R.id.mainList);
	
		lv.setAdapter(new WeatherMainListview(context,WeatherDataManager.getWeatherDataManager()));
		
		
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
	

	
	

//------------------�����ļ���------------------------//
	private List<cityInfo> mlistInfo = new ArrayList<cityInfo>();  //����һ��list����̬�洢Ҫ��ʾ����Ϣ  
	private DBManager db;
	private cityInfo getObject;
	private TTBar tb ;

///////////////////////////////////////////////////////////////////
	public void RefreshAllList(List<cityInfo> mlistInfo){
		ListView lv=(ListView)(leftMenu.findViewById(R.id.list_collection));
		lv.setAdapter(new ListViewAdapter(mlistInfo,context));
	}
///////////////////////////////////////////////////////////////////
	public ImageView getAddCityButton(){
		return tb.getBtn_right();
	}
	public cityInfo getInfo(){
		return getObject;
	}
	public List<cityInfo> getMListInfo(){
		return mlistInfo;
	}
	
	private void setLeftMenu(){
		View layoutLeft=LayoutInflater.from(context).inflate(R.layout.activity_citycollection, null);
		leftMenu.addView(layoutLeft);
		ListView lv=(ListView) layoutLeft.findViewById(R.id.list_collection);
		
		db =DBManager.getDBManager(context);
		db.openWeatherDB();
		db.createCollectionDB(context);
		tb = (TTBar) layoutLeft.findViewById(R.id.title_bar);
		tb.setTvTitle("�ղصĳ���");
		
		Iterator it = db.getCollection().iterator();
		int i = 0;
		String cityName="";
		mlistInfo.clear();   
		while(it.hasNext()){
			cityName = it.next().toString();
			cityInfo information = new cityInfo();  
			information.setId(i);  
			information.setTitle(cityName);  
			information.setDetails(cityName+"���������");  
			information.setAvatar(R.drawable.ic_launcher);  
			mlistInfo.add(information); //���µ�info������뵽��Ϣ�б���  
			i++;  
		}
		lv.setAdapter(new ListViewAdapter(mlistInfo,context));
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Iterator it = db.getCollection().iterator();
				int i = 0;
				String cityName="";
				mlistInfo.clear();   
				while(it.hasNext()){
					cityName = it.next().toString();
					cityInfo information = new cityInfo();  
					information.setId(i);  
					information.setTitle(cityName);  
					information.setDetails(cityName+"���������");  
					information.setAvatar(R.drawable.ic_launcher);  
					mlistInfo.add(information); //���µ�info������뵽��Ϣ�б���  
					i++;  
				}
				getObject = mlistInfo.get(position);//ͨ��position��ȡ������Ķ��� 
				int infoId = getObject.getId();//��ȡ��Ϣid  
				//Toast��ʾ����  
				Toast.makeText(context, "��ϢID:"+infoId,Toast.LENGTH_SHORT).show();				
			}			
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Iterator it = db.getCollection().iterator();
				int i = 0;
				String cityName="";
				mlistInfo.clear();   
				while(it.hasNext()){
					cityName = it.next().toString();
					cityInfo information = new cityInfo();  
					information.setId(i);  
					information.setTitle(cityName);  
					information.setDetails(cityName+"���������");  
					information.setAvatar(R.drawable.ic_launcher);  
					mlistInfo.add(information); //���µ�info������뵽��Ϣ�б���  
					i++;  
				}
				getObject = mlistInfo.get(position);
				return false;
			}
		});
		//�����˵���ʾ
		lv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu conMenu, View view,
					ContextMenuInfo info) {
				// TODO Auto-generated method stub
				conMenu.setHeaderTitle("�������");
				conMenu.add(0, 0, 0, "��Ҫ�����������");
				conMenu.add(0, 1, 1, "��Ҫɾ����");
				}
		});	
	}
	//------------------�����ļ���------------------------//
	//---------------��ʼ������ĸ������֣�����------------------//
	
	//Ϊ�˴������Ҳ˵���������Ҫcontext������
	private void initView(Context context){
		this.context=context;
		leftMenu=new FrameLayout(context);
		middlePart=new FrameLayout(context);
		
		//leftMenu.setBackgroundColor(Color.RED);
		//middlePart.setBackgroundColor(Color.GREEN);
		addView(middlePart);
		addView(leftMenu);
		setMiddlePart();
		setLeftMenu();
		
		mScroller=new Scroller(context,new DecelerateInterpolator());
	}
	
	//������Ļ��  �˺����Ĳ����ǵ�ǰ��Ļ�Ŀ�Ⱥ͸߶�
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		middlePart.measure(widthMeasureSpec, heightMeasureSpec);
		int realWidth=MeasureSpec.getSize(widthMeasureSpec);
		int tempWidthMeasure=MeasureSpec.makeMeasureSpec((int)(realWidth*0.85f), MeasureSpec.EXACTLY); 
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
