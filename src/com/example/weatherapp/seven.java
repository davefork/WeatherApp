package com.example.weatherapp;

import com.example.controler.WeatherDataManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class seven extends View{
	private Paint p=new Paint();
	private WeatherDataManager wData;
	private int maxTep[]=new int[6];
	private int minTep[]=new int[6];

	public seven(Context context) {
		super(context);
		// TODO �Զ����ɵĹ��캯�����
		//wData=WeatherDataManager.getWeatherDataManager();
		
	}

	public seven(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO �Զ����ɵĹ��캯�����
		//wData=WeatherDataManager.getWeatherDataManager();
	}
	public void setWeatherDataManager(WeatherDataManager wData){
		this.wData=wData;
	}
	int translate=20;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO �Զ����ɵķ������
		super.onDraw(canvas);
		//�����߿�
		p.setColor(Color.WHITE);
		canvas.drawLine(10,0+translate,10,191+translate,p);
		
		p.setColor(Color.argb(200,0,144,255));
		
		
		//���������鸳ֵ
		float maxAvg=0,minAvg=0;//ƽ���¶�
		for(int i=0;i<6;i++)
		{
			String max=wData.getMaxDegreeInDay(i+1);
			String []tmp=max.split("��");
			maxTep[i]=Integer.parseInt(tmp[0]);
			maxAvg+=maxTep[i];
			
			String min=wData.getMinDegreeInDay(i+1);
			tmp=min.split("��");
			minTep[i]=Integer.parseInt(tmp[0]);
			minAvg+=minTep[i];
		}

		maxAvg=maxAvg/6;
		minAvg=minAvg/6;
		
		float x,maxY=0,minY=0,oldMaxY,oldMinY;
		for(int i=0;i<6;i++)
		{
			//�õ���һ���������
			oldMaxY=maxY;
			oldMinY=minY;
			//�����µĵ������
			x=40+50*i;
			maxY=70+(maxAvg-maxTep[i])*5;
			minY=150+(minAvg-minTep[i])*5;
			
			p.setColor(Color.WHITE);//��������ɫ
			p.setTextSize(12);
			//����
			canvas.drawCircle(x,maxY+translate,3,p);
			canvas.drawCircle(x,minY+translate,3,p);
			
			//д�¶�
			canvas.drawText(wData.getMaxDegreeInDay(i+1),x,maxY-15+translate,p);
			canvas.drawText(wData.getMinDegreeInDay(i+1),x,minY+20+translate,p);
			//����
			if(i>0)
			{
				canvas.drawLine(x-50,oldMaxY+translate,x,maxY+translate,p);
				canvas.drawLine(x-50,oldMinY+translate,x,minY+translate,p);
			}
			
			//дʱ��
			p.setTextSize(16);
			p.setColor(getResources().getColor(R.color.orange));
			
			String today=wData.getWeek();
			int dayNum=-1;
			
			if(today.equals("����һ")) {dayNum=1;}
			else if(today.equals("���ڶ�")) {dayNum=2;}
			else if(today.equals("������")) {dayNum=3;}
			else if(today.equals("������")) {dayNum=4;}
			else if(today.equals("������")) {dayNum=5;}
			else if(today.equals("������")) {dayNum=6;}
			else if(today.equals("������")) {dayNum=0;}

			int thisDayInt=(dayNum+i)%7;
			String thisDay="";
			
			switch(thisDayInt)
			{
				case 1: thisDay="һ";break;
				case 2: thisDay="��";break;
				case 3: thisDay="��";break;
				case 4: thisDay="��";break;
				case 5: thisDay="��";break;
				case 6: thisDay="��";break;
				case 0: thisDay="��";break;
			}
			
			if(i==0){
				canvas.drawText("����",x-15,20+translate,p);
			}else{
				canvas.drawText(thisDay,x-8,20+translate,p);
			}
		}
	}
}
