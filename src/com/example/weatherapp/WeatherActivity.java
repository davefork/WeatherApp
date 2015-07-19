package com.example.weatherapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Menu;
import android.widget.Gallery;
import android.widget.ListView;


//-------个人包-------//
import com.example.controler.*;
//-------个人包-------//
import com.example.myview.RefreshableView;


//-------个人包-------//
public class WeatherActivity extends Activity {
	private ListView mainList;
	//private RefreshableView refreshView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sforcast);
    
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }
}
