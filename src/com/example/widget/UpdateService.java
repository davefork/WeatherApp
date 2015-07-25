package com.example.widget;

import java.security.PublicKey;

import com.example.controler.DBManager;
import com.example.controler.WeatherDataManager;
import com.example.weatherapp.R;
import com.example.weatherapp.WeatherActivity;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Time;
import android.widget.RemoteViews;

public class UpdateService extends Service {
	private static final int UPDATE = 0x123;
	private RemoteViews remoteViews;
	private WeatherDataManager wData;
	
	private int[] weatherImg = {R.drawable.d00,R.drawable.d01,R.drawable.d02};

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	private Handler handler = new Handler() {  
		  
        @Override  
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case UPDATE:  
                // 更新天气  
                updateTime();
                updateWeather();  
                break;  
            }  
        }  
    };
    
    // 广播接收者去接收系统每分钟的提示广播，来更新时间  
    private BroadcastReceiver mTimePickerBroadcast = new BroadcastReceiver() {  
  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            updateTime();  
        }  
    };
    
    
    
    @Override
	public void onCreate() {
		// TODO 自动生成的方法存根
		super.onCreate();
		remoteViews=new RemoteViews(getApplication().getPackageName(),R.layout.widget_main);
		updateTime();// 第一次运行时先更新一下时间和天气  
        updateWeather();
        
        // 点击天气图片，进入MainActivity  
        Intent intent=new Intent(getApplicationContext(), WeatherActivity.class);  
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,intent,0);  
        remoteViews.setOnClickPendingIntent(R.id.tvCurrTime, pi);
	}

	private void updateTime() {  
    	Time time = new Time();
        time.setToNow();
        int hour = time.hour;
        int min = time.minute;
        int year = time.year;
        int month = time.month+1;
        int day = time.monthDay;
        String strTime = String.format("%02d:%02d",hour,min);
        String strDay = String.format("%04d-%02d-%02d",year,month,day);
        remoteViews.setTextViewText(R.id.tvCurrTime,strTime);
        remoteViews.setTextViewText(R.id.tvCurrDay,strDay);
        //remoteViews.setImageViewResource(R.id.imgW,R.drawable.w1);
        
        ComponentName componentName = new ComponentName(getApplication(),widgetProvider.class);  
        AppWidgetManager.getInstance(getApplication()).updateAppWidget(componentName, remoteViews);
        
    }
    
    private void updateWeather() {
    	wData=new WeatherDataManager("北京",DBManager.getDBManager(widgetProvider.pubContext));
    	
    	remoteViews.setTextViewText(R.id.tvCity,wData.getCity());
    	remoteViews.setTextViewText(R.id.tvDes,wData.GetWeatherString(1));
    	remoteViews.setTextViewText(R.id.tvTep,wData.getMaxDegreeInDay(1));
    	remoteViews.setImageViewResource(R.id.imgWeather,R.drawable.d04); 
        // 执行更新  
    	ComponentName componentName = new ComponentName(getApplication(),widgetProvider.class);  
        AppWidgetManager.getInstance(getApplication()).updateAppWidget(componentName, remoteViews); 
    }

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO 自动生成的方法存根
		IntentFilter updateIntent = new IntentFilter();  
        updateIntent.addAction("android.intent.action.TIME_TICK");  
        registerReceiver(mTimePickerBroadcast, updateIntent);
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		// 注销系统的这个广播  
        unregisterReceiver(mTimePickerBroadcast);
		super.onDestroy();
	}
    
    
}
