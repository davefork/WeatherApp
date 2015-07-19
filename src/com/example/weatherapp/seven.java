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
		// TODO �Զ����ɵĹ��캯�����
		wData.HttpGetData();
		
	}

	public seven(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO �Զ����ɵĹ��캯�����
		wData.HttpGetData();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO �Զ����ɵķ������
		super.onDraw(canvas);
		//�����߿�
		p.setColor(Color.WHITE);
		canvas.drawLine(10,0,10,191,p);
		canvas.drawLine(290,0,290,191,p);
		
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
			x=40+40*i;
			maxY=70+(maxAvg-maxTep[i])*5;
			minY=140+(minAvg-minTep[i])*5;
			
			p.setColor(Color.argb(200,0,144,255));//��������ɫ
			//����
			canvas.drawCircle(x,maxY,3,p);
			canvas.drawCircle(x,minY,3,p);
			
			//д�¶�
			canvas.drawText(wData.getMaxDegreeInDay(i+1),x,maxY-15,p);
			canvas.drawText(wData.getMinDegreeInDay(i+1),x,minY+20,p);
			//����
			if(i>0)
			{
				canvas.drawLine(x-40,oldMaxY,x,maxY,p);
				canvas.drawLine(x-40,oldMinY,x,minY,p);
			}
			
			//дʱ��
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
				canvas.drawText("����",x,20,p);
			}else{
				canvas.drawText(thisDay,x,20,p);
			}
		}
	}

}