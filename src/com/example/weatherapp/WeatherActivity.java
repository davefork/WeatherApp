package com.example.weatherapp;


import java.util.ArrayList;
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


//-------���˰�-------//
import com.example.controler.*;
import com.example.myview.MainUI;


//-------���˰�-------//
public class WeatherActivity extends Activity {
	private MainUI mainLayout;
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub		
		super.onStart();
    	List<cityInfo> mlistInfo= new ArrayList<cityInfo>();
    	DBManager db= new DBManager(WeatherActivity.this);
    	db.createCollectionDB(this.getApplicationContext());
		Iterator it = db.getCollection().iterator();
		int i = 0;
		String cityName="";
		mlistInfo.clear();   
		while(it.hasNext()){
			cityName = it.next().toString();
			cityInfo information = new cityInfo();  
			information.setId(i);  
			information.setTitle(cityName);  
			information.setDetails(cityName+"���������");  
			information.setAvatar(R.drawable.ic_launcher);  
			mlistInfo.add(information); //���µ�info������뵽��Ϣ�б���  
			i++;  
		}  
		mainLayout.RefreshAllList(mlistInfo);
	}
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
    	List<cityInfo> mlistInfo= new ArrayList<cityInfo>();
    	DBManager db=new DBManager(WeatherActivity.this);
    	db.createCollectionDB(this.getApplicationContext());
    	String cityName = mainLayout.getInfo().getTitle();
    	String a[] = cityName.split(".");
    	String tureCityName = a[1];
    	int cityId = 0;
    	///////////////////////////////////////////////////
		switch(aItem.getItemId()){
		case 0:
			
			cityId = db.getCityCode(tureCityName);
			db.setLastWeather(cityName, "��������");
			//��ȡ������Ϣ
			Toast.makeText(WeatherActivity.this, db.getLastWeather(cityName),Toast.LENGTH_SHORT).show();
			return true;  
		case 1:
			db.deleteCity(mainLayout.getInfo().getTitle());		
			Iterator it = db.getCollection().iterator();
			int i = 0;			
			mlistInfo.clear();   
			while(it.hasNext()){
				cityName = it.next().toString();
				cityInfo information = new cityInfo();  
				information.setId(i);  
				information.setTitle(cityName);  
				information.setDetails(cityName+"���������");  
				information.setAvatar(R.drawable.ic_launcher);  
				mlistInfo.add(information); //���µ�info������뵽��Ϣ�б���  
				i++;
			}  
			mainLayout.RefreshAllList(mlistInfo);
			Toast.makeText(WeatherActivity.this, "��ɾ��������ط�",Toast.LENGTH_SHORT).show();
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
