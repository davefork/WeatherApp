package com.example.weatherapp;

import com.example.controler.DBManager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ProvinceActivity extends Activity{	
	private DBManager db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcity);              
        init();
       
    }    
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	finish();
    }
	   private void init(){
		   final ListView mProvinceList = (ListView) findViewById(R.id.city_list);
		   db = new DBManager(this.getApplicationContext());
		   db.openWeatherDB();
		   mProvinceList.setAdapter(new ArrayAdapter<String>(ProvinceActivity.this, android.R.layout.simple_list_item_1, db.getProSet()));
			mProvinceList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(ProvinceActivity.this, CityAddActivity.class);
					intent.putExtra("province_id", position);
					startActivity(intent);
					
				}
	   });
			db.close();
	   
   
    }
}