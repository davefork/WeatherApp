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
                // ��������  
                updateTime();  
                updateWeather();  
                break;  
            }  
        }  
    };
    
    // �㲥������ȥ����ϵͳÿ���ӵ���ʾ�㲥��������ʱ��  
    private BroadcastReceiver mTimePickerBroadcast = new BroadcastReceiver() {  
  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            updateTime();  
        }  
    };
    
    
    
    @Override
	public void onCreate() {
		// TODO �Զ����ɵķ������
		super.onCreate();
		remoteViews=new RemoteViews(getApplication().getPackageName(),R.layout.widget_main);
		updateTime();// ��һ������ʱ�ȸ���һ��ʱ�������  
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
        // System.out.println("��ǰ������" + w.getWeather() + ":" + w.getTemp_c()  
        // + ":" + w.getIcon());  
//        remoteViews.setTextViewText(R.id.condition, MyWeather.weather1);  
//        remoteViews.setTextViewText(R.id.tem, (MyWeather.temp1));  
        // ����ͼƬ������ȡ����ͼƬ��Դ  
        // remoteViews.setImageViewResource(  
        // R.id.weather,  
        // getApplicationContext().getResources().getIdentifier(  
        // w.getIcon(), "drawable", "com.way.apptest"));  
//        if (MyWeather.img1 != null || !"".equals(MyWeather.img1))   
//            remoteViews.setImageViewResource(R.id.weather,  
//                    weatherImg[Integer.parseInt(MyWeather.img1)]);  
//        // ִ�и���  
//        ComponentName componentName = new ComponentName(  
//                getApplicationContext(), App.class);  
//        AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(  
//                componentName, remoteViews);  
    }

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO �Զ����ɵķ������
		IntentFilter updateIntent = new IntentFilter();  
        updateIntent.addAction("android.intent.action.TIME_TICK");  
        registerReceiver(mTimePickerBroadcast, updateIntent);
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// TODO �Զ����ɵķ������
		// ע��ϵͳ������㲥  
        unregisterReceiver(mTimePickerBroadcast);
		super.onDestroy();
	}
    
    
}
