package com.example.weatherapp;

import java.util.ArrayList;


import com.example.controler.WeatherDataManager;
import com.example.weatherapp.R;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class gallery extends Activity{
	private Gallery mGallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sforcast);
		
		mGallery = (Gallery)findViewById(R.id.gallery1);
		
		try {
			mGallery.setAdapter(new myAdapter(this));
		} catch (IllegalArgumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
//		mGallery.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO 自动生成的方法存根
//				v.setBackgroundColor(getResources().getColor(R.color.click));
//				mGallery.setBackgroundColor(getResources().getColor(R.color.click));
//			}
//		});
		mGallery.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView parent,View v,int position,long id) {
				// TODO 自动生成的方法存根
				//v.setBackgroundColor(Color.BLACK);
				//v.setBackgroundColor(getResources().getColor(R.color.click));
			}
			
		});
	}
}

class myAdapter extends BaseAdapter{
	private Context mContext;  
    private ArrayList<Integer> imgList=new ArrayList<Integer>();  
    private ArrayList<Object> imgSizes=new ArrayList<Object>();
    
    private WeatherDataManager wData=new WeatherDataManager();
    
    public myAdapter(Context c) throws IllegalArgumentException, IllegalAccessException{  
        mContext = c; 
        wData.HttpGetData();
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return 6;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public View getView(int position, View con, ViewGroup arg2) {
		// TODO 自动生成的方法存根
		TextView day,des,dPicDes,nPicDes,windDes,windLevel;
		ImageView dImg,nImg;
		
		if(con==null){
			con=LayoutInflater.from(mContext).inflate(R.layout.subgallery,null);
		}
		
		day=(TextView)con.findViewById(R.id.day);
		des=(TextView) con.findViewById(R.id.des);
		dImg=(ImageView) con.findViewById(R.id.dPic);
		nImg=(ImageView) con.findViewById(R.id.nPic);
		dPicDes=(TextView)con.findViewById(R.id.dPicDes);
		nPicDes=(TextView)con.findViewById(R.id.nPicDes);
		windDes=(TextView)con.findViewById(R.id.windDes);

		//显示星期
		//System.out.println();
		String today=wData.getWeek();
		int dayNum=-1;
		
		if(today.equals("星期一")) {dayNum=1;}
		else if(today.equals("星期二")) {dayNum=2;}
		else if(today.equals("星期三")) {dayNum=3;}
		else if(today.equals("星期四")) {dayNum=4;}
		else if(today.equals("星期五")) {dayNum=5;}
		else if(today.equals("星期六")) {dayNum=6;}
		else if(today.equals("星期日")) {dayNum=0;}

		int thisDayInt=(dayNum+position)%7;
		String thisDay="";
		
		switch(thisDayInt)
		{
			case 1: thisDay="星期一";break;
			case 2: thisDay="星期二";break;
			case 3: thisDay="星期三";break;
			case 4: thisDay="星期四";break;
			case 5: thisDay="星期五";break;
			case 6: thisDay="星期六";break;
			case 0: thisDay="星期日";break;
		}
		
		day.setText(thisDay);
		des.setText(wData.GetWeatherString(position+1));//显示天气描述
		
		//两张图片
		int dWeatherId=Integer.parseInt(wData.getImageId(position+1,0));
		int nWeatherId=Integer.parseInt(wData.getImageId(position+1,1))+100;
		dImg.getDrawable().setLevel(dWeatherId);
		nImg.getDrawable().setLevel(nWeatherId);
		
		//
		dPicDes.setText(wData.getImageTitle(position+1,0));
		nPicDes.setText(wData.getImageTitle(position+1,1));
		//windDes.setText(wData.getWindDescription(position+1));
		
		
		return con;
	}
	
}
