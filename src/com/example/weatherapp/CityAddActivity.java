package com.example.weatherapp;

import java.util.List;

import com.example.myview.*;
import com.example.controler.DBManager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CityAddActivity extends Activity{	
	private DBManager db;
	List<String> city_set;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcity);       
        init();
       
    }    
	   private void init(){
		   final ListView mProvinceList = (ListView) findViewById(R.id.city_list);
		   int proId = getIntent().getIntExtra("province_id", 0);
		   
		   db = new DBManager(this.getApplicationContext());
		   db.openWeatherDB();
		   db.createCollectionDB(CityAddActivity.this);
		   city_set = db.getCitSet(proId) ;
		   mProvinceList.setAdapter(new ArrayAdapter<String>(CityAddActivity.this, android.R.layout.simple_list_item_1,city_set ));
			mProvinceList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					db.addCity(city_set.get(position));
					Intent intent = new Intent(CityAddActivity.this, CityManageActivity.class);
					startActivity(intent);
					
				}
	   });
	   
   
    }
}