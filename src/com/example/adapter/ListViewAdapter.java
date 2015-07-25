package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.controler.cityInfo;
import com.example.weatherapp.R;

public class ListViewAdapter extends BaseAdapter
{
	View[] itemViews;
	Context context;

	public ListViewAdapter(List<cityInfo>mlistInfo,Context context)
	{
		this.context = context;
		itemViews=new View[mlistInfo.size()]; 
		for(int i=0;i<mlistInfo.size();i++){
			cityInfo getInfo = (cityInfo)mlistInfo.get(i);
			itemViews[i]=makeItemView(
					getInfo.getTitle(),getInfo.getDetails(),getInfo.getAvatar());
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemViews.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		 return itemViews[position];    
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null)    
			 return itemViews[position];    
		return convertView;  
	}	
	
	private View makeItemView(String strTitle, String strText, int resId ){
		LayoutInflater inflater = (LayoutInflater) context   
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
				// 使用View的对象itemView与R.layout.item关联  
				View itemView = inflater.inflate(R.layout.item_collection, null);  
				// 通过findViewById()方法实例R.layout.item内各组件  
				TextView title = (TextView) itemView.findViewById(R.id.title);    
				title.setText(strTitle);    //填入相应的值  
				TextView text = (TextView) itemView.findViewById(R.id.info);    
				text.setText(strText);    
				ImageView image = (ImageView) itemView.findViewById(R.id.img);    
				image.setImageResource(resId);  

		return itemView;
	}
}