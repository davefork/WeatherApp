package com.example.adapter;

import com.example.controler.WeatherDataManager;
import com.example.myview.LineGridView;
import com.example.weatherapp.R;
import com.example.weatherapp.seven;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
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
		switch(position){
		case 0:
			if (convertView == null) {
				expHolder=new ExponenteHolder();
				convertView =mInflater.inflate(R.layout.exponent_grid, null);
				expHolder.exponenteGird=(LineGridView) convertView.findViewById(R.id.exp_grid);
				expHolder.exponenteGird.setAdapter(new ExponenteAdapter(mInflater.getContext(),weatherManager));
				convertView.setTag(expHolder);
				
	        }else {
	        	expHolder=(ExponenteHolder) convertView.getTag();
	        }
			break;
		case 1:
			if (convertView == null) {
				lineChartHolder=new LineChartHolder();
				System.out.println("1");
				convertView =mInflater.inflate(R.layout.sforcast, null);
				System.out.println("2");
				lineChartHolder.gallery=(Gallery) convertView.findViewById(R.id.gallery1);
				lineChartHolder.lineChart=(seven)convertView.findViewById(R.id.lineChart);
				//lineChartHolder.lineChartTitle=(TextView)convertView.findViewById(R.id.lineChartTitle);
				System.out.println("3");
				lineChartHolder.gallery.setAdapter(new LineChartAdapter(mInflater.getContext(),weatherManager));
				System.out.println("4");
				convertView.setTag(lineChartHolder);
				
	        }else {
	        	lineChartHolder=(LineChartHolder) convertView.getTag();
	        }
//			if (convertView == null) {
//				expHolder=new ExponenteHolder();
//				convertView =mInflater.inflate(R.layout.exponent_grid, null);
//				expHolder.exponenteGird=(LineGridView) convertView.findViewById(R.id.exp_grid);
//				expHolder.exponenteGird.setAdapter(new ExponenteAdapter(mInflater.getContext(),weatherManager));
//				convertView.setTag(expHolder);
//				
//	        }else {
//	        	expHolder=(ExponenteHolder) convertView.getTag();
//	        }
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
