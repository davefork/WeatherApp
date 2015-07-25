package com.example.weatherapp;

import java.util.List;

import com.example.controler.DBManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LauncherActivity extends Activity {
	private Handler mHandler;
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher_layout);
	    mHandler=new Handler(){
	    	public void handleMessage (Message msg) {
                switch(msg.what) {
                case 1:
                   Intent i=new Intent(LauncherActivity.this,WeatherActivity.class);
                   startActivity(i);           
                }
            }
	    };
	 
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DBManager dbManager=DBManager.getDBManager(LauncherActivity.this);
				dbManager.createCollectionDB(LauncherActivity.this);
				List<String>colcity=dbManager.getCollection();
				if(colcity.size()==0){
					dbManager.createCollectionDB(LauncherActivity.this);
					dbManager.addCity("±±¾©");
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(1);
			}
		}).start();
	}
}

