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
		//canvas.drawLine(0,0,0,60,p);
		
		p.setColor(Color.BLUE);
		
		
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
			maxY=60+(maxAvg-maxTep[i])*5;
			minY=120+(minAvg-minTep[i])*5;
			//����
			canvas.drawCircle(x,maxY,3,p);
			canvas.drawCircle(x,minY,3,p);
			//дʱ��
			
			//д�¶�
			canvas.drawText(wData.getMaxDegreeInDay(i+1),x,maxY-20,p);
			canvas.drawText(wData.getMinDegreeInDay(i+1),x,minY+20,p);
			//����
			if(i>0)
			{
				canvas.drawLine(x-40,oldMaxY,x,maxY,p);
				canvas.drawLine(x-40,oldMinY,x,minY,p);
			}
		}
	}

}
