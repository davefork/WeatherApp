package com.example.adapter;

import com.example.controler.WeatherDataManager;
import com.example.weatherapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TwoDaysInfoAdapter extends BaseAdapter {
	private WeatherDataManager weatherManager;
	private Context context; 
	
	private static class ViewHolder {  
	    public ImageView weatherImg;  
	    public TextView day,degreeRange,grid_weather_desc;  
	}  
	
	public TwoDaysInfoAdapter(Context context,WeatherDataManager wm){ 
        this.context = context; 
        weatherManager=wm;
    } 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Object getItem(int item) {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		
		 if (convertView == null) { 
	        	convertView = LayoutInflater.from(context).inflate(R.layout.two_days_info, null);
	        	vh=new ViewHolder();
	        	
	        	vh.day=(TextView) convertView.findViewById(R.id.day);
	        	vh.degreeRange=(TextView) convertView.findViewById(R.id.degreeRange);
	        	vh.grid_weather_desc=(TextView) convertView.findViewById(R.id.grid_weather_desc);
	        	vh.weatherImg=(ImageView) convertView.findViewById(R.id.weatherImg);
	            
	            convertView.setTag(vh);
        }  
        else{
        	vh=(ViewHolder)convertView.getTag();
        }
		 
		if(position==0){
			vh.day.setText("今天");
		}else{
			vh.day.setText("明天");
		}
		vh.degreeRange.setText(weatherManager.getDegreeString(position+1));
	    vh.grid_weather_desc.setText(weatherManager.getImageTitle(position+1, WeatherDataManager.DAY));
	    
	    int dWeatherId=Integer.parseInt(weatherManager.getImageId(position+1,WeatherDataManager.DAY));
		vh.weatherImg.getDrawable().setLevel(dWeatherId);
		
        return convertView; 
	}

}
