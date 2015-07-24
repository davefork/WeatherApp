package com.example.adapter;

import java.util.ArrayList;


import com.example.controler.WeatherDataManager;
import com.example.weatherapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class LineChartAdapter extends BaseAdapter{
	private Context mContext;  
    private ArrayList<Integer> imgList=new ArrayList<Integer>();  
    private ArrayList<Object> imgSizes=new ArrayList<Object>();
    
    private WeatherDataManager wData;
    
    public LineChartAdapter(Context c,WeatherDataManager wData){  
        mContext = c;  
        this.wData=wData;
    }

	@Override
	public int getCount() {
		// TODO �Զ����ɵķ������
		return 6;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO �Զ����ɵķ������
		return 0;
	}
	public WeatherDataManager getWeatherDataManager(){
		return wData;
	}

	@Override
	public View getView(int position, View con, ViewGroup arg2) {
		// TODO �Զ����ɵķ������
		TextView day,des,dPicDes,nPicDes;
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

		//��ʾ����
		String today=wData.getWeek();
		int dayNum=-1;
		
		if(today.equals("����һ")) {dayNum=1;}
		else if(today.equals("���ڶ�")) {dayNum=2;}
		else if(today.equals("������")) {dayNum=3;}
		else if(today.equals("������")) {dayNum=4;}
		else if(today.equals("������")) {dayNum=5;}
		else if(today.equals("������")) {dayNum=6;}
		else if(today.equals("������")) {dayNum=0;}

		int thisDayInt=(dayNum+position)%7;
		String thisDay="";
		
		switch(thisDayInt)
		{
			case 1: thisDay="����һ";break;
			case 2: thisDay="���ڶ�";break;
			case 3: thisDay="������";break;
			case 4: thisDay="������";break;
			case 5: thisDay="������";break;
			case 6: thisDay="������";break;
			case 0: thisDay="������";break;
		}
		
		if(position==0){
			day.setText("����");
		}else{
			day.setText(thisDay);
		}
		
		des.setText("------");//�ָ���
		
		//����ͼƬ
		int dWeatherId=Integer.parseInt(wData.getImageId(position+1,0));
		int nWeatherId=Integer.parseInt(wData.getImageId(position+1,1))+100;
		dImg.getDrawable().setLevel(dWeatherId);
		nImg.getDrawable().setLevel(nWeatherId);
		
		//
		dPicDes.setText(wData.getImageTitle(position+1,0));
		nPicDes.setText(wData.getImageTitle(position+1,1));
		
		
		return con;
	}
	
}
