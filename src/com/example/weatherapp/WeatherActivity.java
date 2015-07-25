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
import com.example.myview.MainUI;
//-------个人包-------//
import com.example.myview.RefreshableView;


//-------个人包-------//
public class WeatherActivity extends Activity {
	private MainUI mainLayout;
	Gallery g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sforcast);
        g=(Gallery) findViewById(R.id.gallery1);
        g.setAdapter(new myAdapter(this));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }
}
