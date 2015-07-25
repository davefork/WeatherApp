package com.example.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.weatherapp.R;

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
        
	}

	private void updateTime() {  
    	Time time = new Time();
        time.setToNow();
        int hour = time.hour;
        int min = time.minute;
        int second = time.second;
        int year = time.year;
        int month = time.month+1;
        int day = time.monthDay;
        String strTime = String.format("%02d:%02d:%02d %04d-%02d-%02d", hour, min, second,year,month,day);
        System.out.println(strTime);
        remoteViews.setTextViewText(R.id.tvCurrTime,strTime);
        
        ComponentName componentName = new ComponentName(getApplication(),widgetProvider.class);  
        AppWidgetManager.getInstance(getApplication()).updateAppWidget(componentName, remoteViews);
        
    }
    
    private void updateWeather() {  
        // Weather w = new GetWeather().googleWeather();  
        // if (w != null) {  
        // System.out.println("当前天气：" + w.getWeather() + ":" + w.getTemp_c()  
        // + ":" + w.getIcon());  
//        remoteViews.setTextViewText(R.id.condition, MyWeather.weather1);  
//        remoteViews.setTextViewText(R.id.tem, (MyWeather.temp1));  
        // 根据图片名，获取天气图片资源  
        // remoteViews.setImageViewResource(  
        // R.id.weather,  
        // getApplicationContext().getResources().getIdentifier(  
        // w.getIcon(), "drawable", "com.way.apptest"));  
//        if (MyWeather.img1 != null || !"".equals(MyWeather.img1))   
//            remoteViews.setImageViewResource(R.id.weather,  
//                    weatherImg[Integer.parseInt(MyWeather.img1)]);  
//        // 执行更新  
//        ComponentName componentName = new ComponentName(  
//                getApplicationContext(), App.class);  
//        AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(  
//                componentName, remoteViews);  
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
