package com.example.adapter;

import com.example.controler.WeatherDataManager;
import com.example.myview.LineGridView;
import com.example.weatherapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeatherMainListview extends BaseAdapter{
	private  WeatherDataManager weatherManager;
	
	private static class ExponenteHolder{
		LineGridView exponenteGird;
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
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ExponenteHolder expHolder;
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
				expHolder=new ExponenteHolder();
				convertView =mInflater.inflate(R.layout.exponent_grid, null);
				expHolder.exponenteGird=(LineGridView) convertView.findViewById(R.id.exp_grid);
				
				expHolder.exponenteGird.setAdapter(new ExponenteAdapter(mInflater.getContext(),weatherManager));
				
				convertView.setTag(expHolder);
				
	        }else {
	        	expHolder=(ExponenteHolder) convertView.getTag();
	        }
			break;
		case 2:
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
		}
		
		
		return convertView;
	}    
}
