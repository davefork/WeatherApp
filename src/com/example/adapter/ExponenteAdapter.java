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

public class ExponenteAdapter extends BaseAdapter{ 
	private WeatherDataManager weatherManager;
	
    //上下文对象 
	private static class ViewHolder {  
	    public ImageView img;  
	    public TextView text;  
	}  
    private Context context; 
   
    public ExponenteAdapter(Context context,WeatherDataManager wm){ 
        this.context = context; 
        weatherManager=wm;
    } 
    public int getCount() { 
        return weatherManager.getNumOfExponent();
    } 

    public Object getItem(int item) { 
        return item; 
    } 

    public long getItemId(int id) { 
        return id; 
    } 
     
    //创建View方法 
    public View getView(int position, View convertView, ViewGroup parent) { 
    
        ViewHolder vh=null;
        if (convertView == null) { 
        	convertView = LayoutInflater.from(context).inflate(R.layout.exponente, null);
        	vh=new ViewHolder();
        	
        	vh.img=(ImageView)convertView.findViewById(R.id.img);
            vh.text=(TextView) convertView.findViewById(R.id.text);
            
            convertView.setTag(vh);
        }  
        else{
        	vh=(ViewHolder)convertView.getTag();
        }
        vh.text.setText(weatherManager.getExponent(position));
        vh.img.setImageResource(context.getResources().getIdentifier("e"+position, "drawable", "com.example.weatherapp"));
        return convertView; 
    } 
}


