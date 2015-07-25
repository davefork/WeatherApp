package com.example.myview;
	
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.ImageProcess.ImageProcess;
import com.example.adapter.ListViewAdapter;
import com.example.adapter.WeatherMainListview;
import com.example.background.ShinyDayView;
import com.example.controler.DBManager;
import com.example.controler.WeatherDataManager;
import com.example.controler.cityInfo;
import com.example.myview.RefreshableView.PullToRefreshListener;
import com.example.weatherapp.R;
import com.example.weatherapp.WeatherActivity;

public class MainUI extends RelativeLayout{
	private Context context;
	private FrameLayout leftMenu;
	private SlideFrameLayout middlePart;
	private ViewFlipper flipper;
	private LinearLayout pagesLayout;
	private DBManager dbManager;
	private List<String> colset;
	//private Scroller mScroller;
	ListView middleMainList;
	private ImageProcess imageProcess;
	View layoutTitle;
	private ImageView pagesView[];
	View weatherViews[];
	private RefreshableView[] refreshableViews;
	//---------------初始化界面的变量！！！------------------//

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
	public void setPagesImg(){
		int curPage=middlePart.getCurPage();
		for(int i=0;i<pagesView.length;i++){
			pagesView[i].setImageResource(R.drawable.grey_point);
		}
		pagesView[curPage].setImageResource(R.drawable.white_point);
		TextView cityNameTitle=(TextView) layoutTitle.findViewById(R.id.cityName);
		cityNameTitle.setText(colset.get(curPage));
	}

	public void setFlipperAndPageView(){
		dbManager.createCollectionDB(context);
		colset=dbManager.getCollection();
		middlePart.setData(flipper,colset.size(),this);
		
		View[] tmpWeatherViews=new View[colset.size()];
		ImageView[] tmpPagesView=new ImageView[colset.size()];
		RefreshableView []tmpRefreshableViews=new RefreshableView[colset.size()];
		
		LayoutParams pagesParams=new LayoutParams(17, 17);
		flipper.removeAllViews();
		pagesLayout.removeAllViews();
		for(int i = 0; i < colset.size(); i++)  
        {  
			String tmp=colset.get(i);  
			tmpWeatherViews[i]=LayoutInflater.from(context).inflate(R.layout.activity_weather,null);
			flipper.addView(tmpWeatherViews[i]);
			middleMainList=(ListView) tmpWeatherViews[i].findViewById(R.id.mainList);
			tmpRefreshableViews[i]=(RefreshableView) tmpWeatherViews[i].findViewById(R.id.refreshView);
			middleMainList.setAdapter(new WeatherMainListview(context,WeatherDataManager.getWeatherDataManager(tmp, dbManager)));
			tmpPagesView[i]=new ImageView(context);
			tmpPagesView[i].setLayoutParams(pagesParams);
			tmpPagesView[i].setPadding(5, 0, 5, 0);
			pagesLayout.addView(tmpPagesView[i]);
			tmpRefreshableViews[i].setOnRefreshListener(new RrefreshListener(i) {
				@Override
				public void onRefresh() {
					// TODO Auto-generated method stub
					System.out.println("index:"+index);
					refreshableViews[index].finishRefreshing();
				}
			}, i);
        }
		setPagesImg();
	
		weatherViews=tmpWeatherViews;
		pagesView=tmpPagesView;
		refreshableViews=tmpRefreshableViews;
		
	}

	private void setMiddlePart(){
		layoutTitle=LayoutInflater.from(context).inflate(R.layout.activity_weather_title,null);
		View layoutMiddle=LayoutInflater.from(context).inflate(R.layout.flipper_layout, null);
		View layoutBg=LayoutInflater.from(context).inflate(R.layout.background, null);
		
		
		//之后需要改掉的地方！！！！！！！！！！！！！！！
		LayoutParams titleParams=new LayoutParams(MarginLayoutParams.MATCH_PARENT, (int)(WeatherActivity.height*0.2));	
		layoutTitle.setLayoutParams(titleParams);
		ImageView leftMenuBtn=(ImageView) layoutTitle.findViewById(R.id.left_menu);
		
		leftMenuBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				middlePart.changeLeftMenuState();
			}
		});
		
		ShinyDayView shinyBg=new ShinyDayView(context);
		LayoutParams bgParams=new LayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT);	
		shinyBg.setLayoutParams(bgParams);
		FrameLayout bgFrame=(FrameLayout) layoutBg.findViewById(R.id.bgContainer);
		bgFrame.addView(shinyBg);
		middlePart.addView(layoutBg);
		
		middlePart.addView(layoutMiddle);
		middlePart.addView(layoutTitle);
		
		dbManager.createCollectionDB(context);
		colset=dbManager.getCollection();
		
		
		flipper=(ViewFlipper) layoutMiddle.findViewById(R.id.flipper);
		pagesLayout=(LinearLayout)layoutTitle.findViewById(R.id.page_layout);
		middlePart.setData(flipper,colset.size(),this);
		
		weatherViews=new View[colset.size()];
		pagesView=new ImageView[colset.size()];
		refreshableViews=new RefreshableView[colset.size()];
		
		
		LayoutParams pagesParams=new LayoutParams(17, 17);
		
		for(int i = 0; i < colset.size(); i++)  
        {  
			String tmp=colset.get(i);  
			weatherViews[i]=LayoutInflater.from(context).inflate(R.layout.activity_weather,null);
			flipper.addView(weatherViews[i]);
			middleMainList=(ListView) weatherViews[i].findViewById(R.id.mainList);	
			middleMainList.setAdapter(new WeatherMainListview(context,WeatherDataManager.getWeatherDataManager(tmp, dbManager)));
			pagesView[i]=new ImageView(context);
			pagesView[i].setLayoutParams(pagesParams);
			pagesView[i].setPadding(5, 0, 5, 0);
			pagesLayout.addView(pagesView[i]);
			refreshableViews[i]=(RefreshableView) weatherViews[i].findViewById(R.id.refreshView);
			refreshableViews[i].setOnRefreshListener(new RrefreshListener(i) {
				@Override
				public void onRefresh() {
					// TODO Auto-generated method stub
					System.out.println("index:"+index);
					refreshableViews[index].finishRefreshing();
				}
			}, i);
			//dbManager.createCollectionDB(context);
			//System.out.println(dbManager.getLastWeather(colset.get(i)));
        }
		
		setPagesImg();
//		for(int i=0;i<weatherViews.length;i++){
//			
//			
//			System.out.println(weatherViews.length);
//	//		middleMainList.setOnScrollListener(new OnScrollListener() {
//	//			
//	//			@Override
//	//			public void onScrollStateChanged(AbsListView absList, int state) {
//	//				// TODO Auto-generated method stub
//	//				  
//	//			        if(absList.getLastVisiblePosition()==absList.getCount()-1&&isBkDark==false){
//	//			        	isBkDark=true;
//	//			        	ImageView iv=(ImageView)middlePart.findViewById(R.id.blur);
//	//			        	System.out.println("Visible");
//	//			        	iv.setVisibility(VISIBLE);
//	//		
//	//			        	
//	//			        	
//	//			        }else if(absList.getLastVisiblePosition()!=absList.getCount()-1&&isBkDark==true){
//	//			        	isBkDark=false;
//	//			        	ImageView iv=(ImageView)middlePart.findViewById(R.id.blur);
//	//			        	System.out.println("GONE");
//	//			        	iv.setVisibility(GONE);
//	//			        }         
//	//			        
//	//			}
//	//			
//	//			@Override
//	//			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//	//				// TODO Auto-generated method stub
//	//				
//	//			}
//	//		});
//			
//			
//		}
//		refreshableView = (RefreshableView)weatherViews[0].findViewById(R.id.refreshView);  
//		refreshableView.setOnRefreshListener(new PullToRefreshListener() {  
//            @Override  
//            public void onRefresh() {   
//             
//                	System.out.println("111");
//                
//     
//                refreshableView.finishRefreshing();  
//            }  
//        }, 0);  
//		refreshableView = (RefreshableView)weatherViews[1].findViewById(R.id.refreshView);  
//		refreshableView.setOnRefreshListener(new PullToRefreshListener() {  
//            @Override  
//            public void onRefresh() {   
//             
//                	System.out.println("222");
//                
//     
//                refreshableView.finishRefreshing();  
//            }  
//        }, 0);  
	}
	

	
	
//------------------何明的监狱------------------------//
	private List<cityInfo> mlistInfo = new ArrayList<cityInfo>();  //声明一个list，动态存储要显示的信息  
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
		tb.setTvTitle("收藏的城市");
		
		Iterator it = db.getCollection().iterator();
		int i = 0;
		String cityName = "";
		String tureCityName="";		    	
		mlistInfo.clear();   
		while(it.hasNext()){
			cityName = it.next().toString();			
	    	String a[] = cityName.split(".");
	    	if(a.length ==1)
	        	tureCityName = a[1];	    	
	    	else
	    		tureCityName = cityName;
			cityInfo information = new cityInfo();  
			information.setId(i);  
			information.setTitle(tureCityName);  														
			mlistInfo.add(information); //将新的info对象加入到信息列表中 					
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
				String cityName = "";
				String tureCityName="";		    	
				mlistInfo.clear();   
				while(it.hasNext()){
					cityName = it.next().toString();
					String a[] = cityName.split(".");
			    	if(a.length ==1)
			        	tureCityName = a[1];	    	
			    	else
			    		tureCityName = cityName;
					cityInfo information = new cityInfo();  
					information.setId(i);  
					information.setTitle(tureCityName);  														
					mlistInfo.add(information); //将新的info对象加入到信息列表中 					
					i++;  
				}
				getObject = mlistInfo.get(position);//通过position获取所点击的对象 
				int infoId = getObject.getId();//获取信息id  
				//Toast显示测试  
				Toast.makeText(context, "信息ID:"+infoId,Toast.LENGTH_SHORT).show();				
			}			
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Iterator it = db.getCollection().iterator();
				int i = 0;
				String cityName = "";
				String tureCityName="";		    	
				mlistInfo.clear();   
				while(it.hasNext()){
					cityName = it.next().toString();
					String a[] = cityName.split(".");
			    	if(a.length ==1)
			        	tureCityName = a[1];	    	
			    	else
			    		tureCityName = cityName;
					cityInfo information = new cityInfo();  
					information.setId(i);  
					information.setTitle(tureCityName);  														
					mlistInfo.add(information); //将新的info对象加入到信息列表中 					
					i++;  
				}
				getObject = mlistInfo.get(position);
				return false;
			}
		});
		//长按菜单显示
		lv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu conMenu, View view,
					ContextMenuInfo info) {
				// TODO Auto-generated method stub
				conMenu.setHeaderTitle("你想干嘛");
				conMenu.add(0, 0, 0, "我要看这里的天气");
				conMenu.add(0, 1, 1, "我要删了它");
				}
		});
		db.close();
	}
	//------------------何明的监狱------------------------//
	//---------------初始化界面的各个部分！！！------------------//
	
	//为了创建左右菜单，所以需要context上下文
	private void initView(Context context){
		this.context=context;
		dbManager=DBManager.getDBManager(context);
		leftMenu=new FrameLayout(context);
		middlePart=new SlideFrameLayout(context);
		imageProcess=new ImageProcess();
		//leftMenu.setBackgroundColor(Color.RED);
		//middlePart.setBackgroundColor(Color.GREEN);
		addView(leftMenu);
		addView(middlePart);
		setMiddlePart();
		setLeftMenu();
		
		//mScroller=new Scroller(context,new DecelerateInterpolator());
	}
	
	//测量屏幕，  此函数的参数是当前屏幕的宽度和高度
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		middlePart.measure(widthMeasureSpec, heightMeasureSpec);
		
		int realWidth=MeasureSpec.getSize(widthMeasureSpec);
		int tempWidthMeasure=MeasureSpec.makeMeasureSpec((int)(realWidth*0.85f), MeasureSpec.EXACTLY);
		middlePart.setMeasure((int)(realWidth*0.85f));
		leftMenu.measure(tempWidthMeasure, heightMeasureSpec);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		middlePart.layout(l, t, r, b);
		leftMenu.layout(l, t, r, b);
//		
//		ImageView iv=(ImageView) middlePart.findViewById(R.id.blur);
//		iv.setVisibility(GONE);
//		iv.setDrawingCacheEnabled(true);
//		imageProcess.blurBitmap(iv.getDrawingCache(),iv,50);
//		iv.setDrawingCacheEnabled(false);
//		
	}
	
//	private boolean isTestComplete=false;
//	
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		// TODO Auto-generated method stub
//		getEventType(ev);
//		if(isTestComplete){
//			return true;
//		}
//		else{
//			return false;
//		}
//	}
//	
//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
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
//				
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
//					int curScrollX=getScrollX();
//					if(Math.abs(curScrollX)>leftMenu.getMeasuredWidth()/2){
//						if(curScrollX<0){
//							mScroller.startScroll(curScrollX, 0, -leftMenu.getMeasuredWidth()-curScrollX, 0);
//						}
//					}
//					else{
//						mScroller.startScroll(curScrollX, 0, -curScrollX, 0);
//					}
//					invalidate();
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
//		return super.onTouchEvent(ev);
//	}
//	@Override
//	public void computeScroll() {
//		// TODO Auto-generated method stub
//		super.computeScroll();
//		if(!mScroller.computeScrollOffset()){
//			return;
//		}
//		int tempX=mScroller.getCurrX();
//		scrollTo(tempX, 0);
//	}
////	@Override
////	public boolean dispatchTouchEvent(MotionEvent ev) {
////		// TODO Auto-generated method stub
////		if(!isTestComplete){
////			getEventType(ev);
////			return true;
////		}
////		else{
////			if(isrightleftEvent){
////				switch(ev.getActionMasked()){
////				
////				case MotionEvent.ACTION_MOVE:
////					int curScroll=getScrollX();
////					int dis_x=(int)(ev.getX()-pt.x);
////					System.out.println("currScroll:"+curScroll);
////					System.out.println("dis_x:"+dis_x);
////					int expectX=-dis_x+curScroll;
////					int finalX=0;
////					if(expectX<0){
////						finalX=Math.max(expectX, -leftMenu.getMeasuredWidth());
////					}
////					else{
////						finalX=expectX;
////					}
////					scrollTo(finalX,0);
////					pt.x=(int) ev.getX();
////					break;
////				case MotionEvent.ACTION_UP:
////				case MotionEvent.ACTION_CANCEL:
////					
////					isTestComplete=false;
////					isrightleftEvent=false;
////					break;
////				}
////			}else{
////				isTestComplete=false;
////				isrightleftEvent=false;
////			}
////		}
////		
////		
////		return super.dispatchTouchEvent(ev);
////	}
//	
//	private Point pt=new Point();
//	private static final int TEST_DIS=20;
//	private boolean isrightleftEvent;
//	
//	private void getEventType(MotionEvent ev){
//		switch(ev.getActionMasked()){
//		case MotionEvent.ACTION_DOWN:
//			pt.x=(int) ev.getX();
//			pt.y=(int) ev.getY();
//			break;
//		case MotionEvent.ACTION_MOVE:
//			int dX=Math.abs((int)ev.getX()-pt.x);
//			int dY=Math.abs((int)ev.getY()-pt.y);
//			if(dX>=TEST_DIS&&dX>=dY){
//				isrightleftEvent=true;
//				isTestComplete=true;
//				pt.x=(int) ev.getX();
//				pt.y=(int) ev.getY();
//			}else if(dY>=TEST_DIS&&dY>dX){
//				isrightleftEvent=false;
//				isTestComplete=false;
//				pt.x=(int) ev.getX();
//				pt.y=(int) ev.getY();
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			break;
//			
//		case MotionEvent.ACTION_CANCEL:
//			break;
//		}
//	}
	
}

class RrefreshListener implements PullToRefreshListener{
	int index;
	public RrefreshListener(int id){
		index=id;
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	
}