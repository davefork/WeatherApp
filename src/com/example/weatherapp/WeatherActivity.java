package com.example.weatherapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Menu;
import android.widget.Gallery;
import android.widget.ListView;


//-------���˰�-------//
import com.example.controler.*;
import com.example.myview.MainUI;
//-------���˰�-------//
import com.example.myview.RefreshableView;


//-------���˰�-------//
public class WeatherActivity extends Activity {
	private MainUI mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mainLayout=new MainUI(this);
        setContentView(mainLayout);
    
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }
}
