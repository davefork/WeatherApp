package com.example.weatherapp;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;


//-------个人包-------//
import com.example.controler.*;
import com.example.myview.RefreshableView;
import com.example.myview.RefreshableView.PullToRefreshListener;
import com.example.adapter.WeatherMainListview;
//-------个人包-------//

public class WeatherActivity extends Activity {
	private ListView mainList;
	private RefreshableView refreshView;
	
	private  WeatherDataManager weatherManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        
        weatherManager=new WeatherDataManager();
        weatherManager.HttpGetData();
       //----------------------------------------------------------------- 
        mainList=(ListView) findViewById(R.id.mainList);
        mainList.setAdapter(new WeatherMainListview(this,weatherManager));
      //----------------------------------------------------------------- 
        refreshView=(RefreshableView) findViewById(R.id.refreshView);
        refreshView.setOnRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				refreshView.finishRefreshing();
			}
		}, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }
}
