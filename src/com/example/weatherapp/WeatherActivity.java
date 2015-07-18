package com.example.weatherapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Menu;
import android.widget.ListView;


//-------个人包-------//
import com.example.controler.*;
//-------个人包-------//
import com.example.myview.RefreshableView;

public class WeatherActivity extends Activity {
	private ListView mainList;
	private RefreshableView refreshView;
	
	private  WeatherDataManager weatherManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }
}
