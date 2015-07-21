package com.example.weatherapp;


import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.Toast;


//-------个人包-------//
import com.example.controler.*;
import com.example.myview.MainUI;


//-------个人包-------//
public class WeatherActivity extends Activity {
	private MainUI mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mainLayout=new MainUI(this);
        setContentView(mainLayout);
    
        mainLayout.getAddCityButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
		        i.setClass(WeatherActivity.this, ProvinceActivity.class);
		        startActivity(i);
			}
		});
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem aItem) {
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();
    	/////////////////////////////////////////////////
    	List<cityInfo> mlistInfo=mainLayout.getMListInfo();
    	DBManager db=DBManager.getDBManager(WeatherActivity.this);
    	///////////////////////////////////////////////////
		switch(aItem.getItemId()){
		case 0:
			Toast.makeText(WeatherActivity.this, "查看这里的天气",Toast.LENGTH_SHORT).show();
			return true;  
		case 1:
			DBManager.getDBManager(WeatherActivity.this).deleteCity(mainLayout.getInfo().getTitle());			

			Iterator it = db.getCollection().iterator();
			int i = 0;
			String cityName;
			mlistInfo.clear();   
			while(it.hasNext()){
				cityName = it.next().toString();
				cityInfo information = new cityInfo();  
				information.setId(i);  
				information.setTitle(cityName);  
				information.setDetails(cityName+"的天气情况");  
				information.setAvatar(R.drawable.ic_launcher);  
				mlistInfo.add(information); //将新的info对象加入到信息列表中  
				i++;  
			}  
			mainLayout.RefreshAllList();
			Toast.makeText(WeatherActivity.this, "你删除了这个地方",Toast.LENGTH_SHORT).show();
			return true; 
		}
		return false;			
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }
}
