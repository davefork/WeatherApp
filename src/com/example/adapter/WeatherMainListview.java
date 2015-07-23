package com.example.adapter;

import com.example.controler.WeatherDataManager;
import com.example.myview.LineGridView;
import com.example.weatherapp.R;
import com.example.weatherapp.WeatherActivity;
import com.example.weatherapp.seven;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.AbsListView.LayoutParams;
import android.widget.Gallery;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WeatherMainListview extends BaseAdapter{
	private  WeatherDataManager weatherManager;
	
	private static class ExponenteHolder{
		LineGridView exponenteGird;
	}
	private static class LineChartHolder{
		Gallery gallery;
		seven lineChart;
		TextView lineChartTitle;
	}
	private static class WeatherInfoHolder{
		LineGridView twoDaysGird;
		TextView weatherDesc;
		TextView windLevel;
		TextView degree;
	}
	
	
	private LayoutInflater mInflater;
     
    public WeatherMainListview(Context context,WeatherDataManager wm){
        this.mInflater = LayoutInflater.from(context);
        weatherManager=wm;
    }
    
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-gene	rated method stub
        return 0;
    }
    
    @Override
    public int getViewTypeCount() {
    	// TODO Auto-generated method stub
    	return 3;
    }
    
    @Override
    public int getItemViewType(int position) {
    	// TODO Auto-generated method stub
    	switch(position){
    	case 0:
    		return 0;
    	case 1:
    		return 1;
    	case 2:
    		return 2;
    	}
    	return 0;
    }

	@Override
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ExponenteHolder expHolder;
		LineChartHolder lineChartHolder;
		WeatherInfoHolder weatherInfoHolder;
		switch(position){
		case 0:
			if (convertView == null) {
				weatherInfoHolder=new WeatherInfoHolder();
				//RelativeLayout vg=new RelativeLayout(mInflater.getContext());
				//vg.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,  (int)(WeatherActivity.height*0.8)));
				//***********注意类型一定要写对！！！！LayoutParams的！！！！！*******************//
				convertView =mInflater.inflate(R.layout.activity_weather_main,null);
				LayoutParams titleParams=new LayoutParams(MarginLayoutParams.MATCH_PARENT, (int)(WeatherActivity.height*0.9));
				
				convertView.setLayoutParams(titleParams);
				//FrameLayout layout=(FrameLayout) convertView.findViewById(R.id.weather_info_layout);
				//convertView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,  (int)(WeatherActivity.height*0.8)));
				//LayoutParams params=new LayoutParams(MarginLayoutParams.MATCH_PARENT, (int)(WeatherActivity.height*0.2));	
				//layout.setLayoutParams(params);
				
				weatherInfoHolder.twoDaysGird=(LineGridView) convertView.findViewById(R.id.two_day_info);
				weatherInfoHolder.twoDaysGird.setAdapter(new TwoDaysInfoAdapter(mInflater.getContext(),weatherManager));
				weatherInfoHolder.degree=(TextView) convertView.findViewById(R.id.degree);
				weatherInfoHolder.weatherDesc=(TextView)convertView.findViewById(R.id.weather_desc);
				weatherInfoHolder.windLevel=(TextView)convertView.findViewById(R.id.wind_level);
				convertView.setTag(weatherInfoHolder);
	        }else {
	        	weatherInfoHolder=(WeatherInfoHolder) convertView.getTag();
	        	
	        }
			weatherInfoHolder.degree.setText(weatherManager.getDegreeString(position+1));
        	weatherInfoHolder.weatherDesc.setText(weatherManager.GetWeatherString(position+1));
        	weatherInfoHolder.windLevel.setText(weatherManager.getWindLevelDescription(position+1));
			break;
		case 1:
			if (convertView == null) {
				lineChartHolder=new LineChartHolder();
				convertView =mInflater.inflate(R.layout.sforcast, null);
				lineChartHolder.gallery=(Gallery) convertView.findViewById(R.id.gallery1);
				lineChartHolder.lineChart=(seven)convertView.findViewById(R.id.lineChart);
				//lineChartHolder.lineChartTitle=(TextView)convertView.findViewById(R.id.lineChartTitle);
				lineChartHolder.gallery.setAdapter(new LineChartAdapter(mInflater.getContext(),weatherManager));
				convertView.setTag(lineChartHolder);
				
	        }else {
	        	lineChartHolder=(LineChartHolder) convertView.getTag();
	        
	        }

			break;
		case 2:
			if (convertView == null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				expHolder=new ExponenteHolder();
				convertView =mInflater.inflate(R.layout.exponent_grid, null);
				expHolder.exponenteGird=(LineGridView) convertView.findViewById(R.id.exp_grid);
				expHolder.exponenteGird.setAdapter(new ExponenteAdapter(mInflater.getContext(),weatherManager));
				convertView.setTag(expHolder);
				
	        }else {
	        	expHolder=(ExponenteHolder) convertView.getTag();
	        
	        }	
			
			break;
		}
		
		
		return convertView;
	}    
}
