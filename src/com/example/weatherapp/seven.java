package com.example.weatherapp;

import com.example.controler.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class seven extends View{
	private Paint p=new Paint();
	private WeatherDataManager wData=new WeatherDataManager();
	private int maxTep[]=new int[6];
	private int minTep[]=new int[6];

	public seven(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		wData.HttpGetData();
		
	}

	public seven(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		wData.HttpGetData();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
		//画个边框
		p.setColor(Color.WHITE);
		canvas.drawLine(10,0,10,191,p);
		canvas.drawLine(290,0,290,191,p);
		
		p.setColor(Color.argb(200,0,144,255));
		
		
		//给数据数组赋值
		float maxAvg=0,minAvg=0;//平均温度
		for(int i=0;i<6;i++)
		{
			String max=wData.getMaxDegreeInDay(i+1);
			String []tmp=max.split("℃");
			maxTep[i]=Integer.parseInt(tmp[0]);
			maxAvg+=maxTep[i];
			
			String min=wData.getMinDegreeInDay(i+1);
			tmp=min.split("℃");
			minTep[i]=Integer.parseInt(tmp[0]);
			minAvg+=minTep[i];
		}

		maxAvg=maxAvg/6;
		minAvg=minAvg/6;
		
		float x,maxY=0,minY=0,oldMaxY,oldMinY;
		for(int i=0;i<6;i++)
		{
			//得到上一个点的坐标
			oldMaxY=maxY;
			oldMinY=minY;
			//计算新的点的坐标
			x=40+40*i;
			maxY=70+(maxAvg-maxTep[i])*5;
			minY=140+(minAvg-minTep[i])*5;
			
			p.setColor(Color.argb(200,0,144,255));//画笔是蓝色
			//画点
			canvas.drawCircle(x,maxY,3,p);
			canvas.drawCircle(x,minY,3,p);
			
			//写温度
			canvas.drawText(wData.getMaxDegreeInDay(i+1),x,maxY-15,p);
			canvas.drawText(wData.getMinDegreeInDay(i+1),x,minY+20,p);
			//画线
			if(i>0)
			{
				canvas.drawLine(x-40,oldMaxY,x,maxY,p);
				canvas.drawLine(x-40,oldMinY,x,minY,p);
			}
			
			//写时间
			p.setColor(getResources().getColor(R.color.orange));
			
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
				canvas.drawText("今天",x,20,p);
			}else{
				canvas.drawText(thisDay,x,20,p);
			}
		}
	}

}
