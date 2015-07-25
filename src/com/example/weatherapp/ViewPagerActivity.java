package com.example.weatherapp;

import java.util.ArrayList;
import java.util.List;

import com.example.controler.WeatherDataManager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewPagerActivity extends Activity {

  private List<View> list = new ArrayList<View>();
  private ViewPager mPageVp;
  private WeatherDataManager wData;
  private int maxTep[]=new int[6];
  private int minTep[]=new int[6];
  private TextView tv_title[] = new TextView[6];
  private TextView tv_degree[] = new TextView[6];
  private TextView tv_day[] = new TextView[6];
  private TextView tv_night[] = new TextView[6];
  private ImageView img_day[] = new ImageView[6];
  private ImageView img_night[] = new ImageView[6];
  
  private ImageView mTabLineIv;
  private int currentIndex;
  private int screenWidth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_viewpager);
    String cityName=getIntent().getStringExtra("cityName");
    wData=WeatherDataManager.getWeatherDataManager(cityName,null);

    findById();
    getDate();
    init();
    initTabLineWidth();
  }
  private void getDate(){
	  
	  float maxAvg=0,minAvg=0;//平均温度
	  for(int i=0;i<6;i++){
		String max=wData.getMaxDegreeInDay(i+1);
		String s_day = wData.getImageTitle(i+1, 0);
		String s_night = wData.getImageTitle(i+1, 1);
		String windInfo = wData.getWindDescription(i+1);
		String []tmp=max.split("℃");
		int dWeatherId=Integer.parseInt(wData.getImageId(i+1,0));
		int nWeatherId=Integer.parseInt(wData.getImageId(i+1,1))+100;
		maxTep[i]=Integer.parseInt(tmp[0]);
		maxAvg+=maxTep[i];
		
		String min=wData.getMinDegreeInDay(i+1);
		tmp=min.split("℃");
		minTep[i]=Integer.parseInt(tmp[0]);
		minAvg+=minTep[i];
		tv_degree[i].setText(min+"~"+max+"\r\n \r\n"+windInfo);
		img_day[i].getDrawable().setLevel(dWeatherId);
		img_night[i].getDrawable().setLevel(nWeatherId);
		tv_day[i].setText("白天"+s_day);
		tv_night[i].setText("夜间"+s_night);
		}
		maxAvg=maxAvg/6;
		minAvg=minAvg/6;

		for(int i=0;i<6;i++){

		String today=wData.getWeek();
		int dayNum=-1;
		
		if(today.equals("星期一")) {dayNum=1;}
		else if(today.equals("星期二")) {dayNum=2;}
		else if(today.equals("星期三")) {dayNum=3;}
		else if(today.equals("星期四")) {dayNum=4;}
		else if(today.equals("星期五")) {dayNum=5;}
		else if(today.equals("星期六")) {dayNum=6;}
		else if(today.equals("星期日")) {dayNum=0;}
		
		int thisDayInt=(dayNum+i)%7;
		String thisDay="";

		switch(thisDayInt)
		{
			case 1: thisDay="一";break;
		case 2: thisDay="二";break;
		case 3: thisDay="三";break;
		case 4: thisDay="四";break;
		case 5: thisDay="五";break;
		case 6: thisDay="六";break;
		case 0: thisDay="日";break;
		}
		
		if(i==0){
			tv_title[i].setText("今天");
		}else{
			tv_title[i].setText("周"+thisDay);
			}
		
		}
		
	}
	  private void findById() {
	    tv_title[0] = (TextView) this.findViewById(R.id.id_tv1);
	    tv_title[1] = (TextView) this.findViewById(R.id.id_tv2);
	    tv_title[2] = (TextView) this.findViewById(R.id.id_tv3);
	    tv_title[3] = (TextView) this.findViewById(R.id.id_tv4);
	    tv_title[4] = (TextView) this.findViewById(R.id.id_tv5);
	    tv_title[5] = (TextView) this.findViewById(R.id.id_tv6);
	    for(int i=0;i<6;i++){
		  	list.add(getLayoutInflater().inflate(R.layout.viewpager_tab, null));
		  	tv_degree[i] = (TextView) list.get(i).findViewById(R.id.degree);
		  	tv_day[i] = (TextView) list.get(i).findViewById(R.id.imgtitle_d);
		  	tv_night[i] = (TextView) list.get(i).findViewById(R.id.imgtitle_n);
		  	img_day[i] = (ImageView) list.get(i).findViewById(R.id.img_day);
		  	img_night[i] = (ImageView) list.get(i).findViewById(R.id.img_night);
		  	  
	  	  }
	    mTabLineIv = (ImageView) this.findViewById(R.id.id_tab_line_iv);	
	    mPageVp = (ViewPager) this.findViewById(R.id.id_page_vp);
	  }

  private void init() {
   
      
      mPageVp.setAdapter(new ViewPagerAdapter(list));       
	  mPageVp.setOnPageChangeListener(new OnPageChangeListener() {
	
	  /**
	   * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
	   */
	  @Override
	  public void onPageScrollStateChanged(int state) {
	
	  }
	
	  /**
	   * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
	   * offsetPixels:当前页面偏移的像素位置
	   */
	  @Override
	  public void onPageScrolled(int position, float offset,
	      int offsetPixels) {
	    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
	        .getLayoutParams();

	/**
	 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
	 * 设置mTabLineIv的左边距 滑动场景：
	 * 记6个页面,
	 * 从左到右分别为0,1,2,3,4,5
	 */
	
	if (currentIndex == 0 && position == 0)// 0->1
	{
	  lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
	      * (screenWidth / 6));
	
	} else if (currentIndex == 1 && position == 0) // 1->0
	{
	  lp.leftMargin = (int) (-(1 - offset)
	      * (screenWidth * 1.0 / 6) + currentIndex
	      * (screenWidth / 6));
	
	} else if (currentIndex == 1 && position == 1) // 1->2
	{
	  lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
	      * (screenWidth / 6));
	} else if (currentIndex == 2 && position == 1) // 2->1
	    {
	      lp.leftMargin = (int) (-(1 - offset)
	          * (screenWidth * 1.0 / 6) + currentIndex
	          * (screenWidth / 6));
	 }
	/////////////////////////////////////////////////////////////////
	else if (currentIndex == 2 && position == 2) // 2->3
	{
	  lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
	      * (screenWidth / 6));
	} else if (currentIndex == 3 && position == 2) // 3->2
    {
      lp.leftMargin = (int) (-(1 - offset)
          * (screenWidth * 1.0 / 6) + currentIndex
          * (screenWidth / 6));
    }else if (currentIndex == 3 && position == 3) // 3->4
	{
	  lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
	      * (screenWidth / 6));
	} else if (currentIndex == 4 && position == 3) // 4->3
    {
      lp.leftMargin = (int) (-(1 - offset)
          * (screenWidth * 1.0 / 6) + currentIndex
          * (screenWidth / 6));
    }else if (currentIndex == 5 && position == 5) // 4->5
	{
	  lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
	      * (screenWidth / 6));
	} else if (currentIndex == 5 && position == 4) // 5->4
	{
	  lp.leftMargin = (int) (-(1 - offset)
	      * (screenWidth * 1.0 / 6) + currentIndex
	      * (screenWidth / 6));
	}
	    mTabLineIv.setLayoutParams(lp);
	  }
	
	  @Override
	  public void onPageSelected(int position) {
	    resetTextView();
	    switch (position) {
	    case 0:
	    	tv_title[0].setTextColor(Color.WHITE);
	      break;
	    case 1:
	    	tv_title[1].setTextColor(Color.WHITE);
	      break;
	    case 2:
	    	tv_title[2].setTextColor(Color.WHITE);
	      break;
	    case 3:
	    	tv_title[3].setTextColor(Color.WHITE);
	      break;
	    case 4:
	    	tv_title[4].setTextColor(Color.WHITE);
	      break;
	    case 5:
	    	tv_title[5].setTextColor(Color.WHITE);
	      break;
	    }
	    currentIndex = position;
	  }
	  });

  }

  /**
   * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
   */
  private void initTabLineWidth() {
    DisplayMetrics dpMetrics = new DisplayMetrics();
    getWindow().getWindowManager().getDefaultDisplay()
        .getMetrics(dpMetrics);
    screenWidth = dpMetrics.widthPixels;
    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
        .getLayoutParams();
    lp.width = screenWidth / 6;
    mTabLineIv.setLayoutParams(lp);
  }

  /**
   * 重置颜色
   */
  private void resetTextView() {
    tv_title[0].setTextColor(Color.parseColor("#EEE5DE"));
    tv_title[1].setTextColor(Color.parseColor("#EEE5DE"));
    tv_title[2].setTextColor(Color.parseColor("#EEE5DE"));
    tv_title[3].setTextColor(Color.parseColor("#EEE5DE"));
    tv_title[4].setTextColor(Color.parseColor("#EEE5DE"));
    tv_title[5].setTextColor(Color.parseColor("#EEE5DE"));
  }
  class ViewPagerAdapter extends PagerAdapter {
	  
      private List<View> list = null;

      public ViewPagerAdapter(List<View> list) {
          this.list = list;
      }

      @Override
      public int getCount() {
          return list.size();
      }

      @Override
      public Object instantiateItem(ViewGroup container, int position) {
          container.addView(list.get(position));
          return list.get(position);
      }

      @Override
      public void destroyItem(ViewGroup container, int position, Object object) {
          container.removeView(list.get(position));
      }

      @Override
      public boolean isViewFromObject(View arg0, Object arg1) {
          return arg0 == arg1;
      }

  }
  
}
