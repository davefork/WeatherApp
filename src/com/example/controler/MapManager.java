package com.example.controler;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;


public class MapManager {
	private LocationClient mLocationClient;
	private GeofenceClient mGeofenceClient;
	private MyLocationListener mMyLocationListener;
	private String cityName = "";
	private Vibrator mVibrator;
	private Context context;	
	
	public MapManager(Context context) {
		this.context = context;
	}	
	public void buildMap()
	{
		mLocationClient = new LocationClient(context);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(context);				
		mVibrator =(Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);	
		InitLocation();
		mLocationClient.start();
	}
	/**
	 * 实现实位回调监听
	 */
	private class MyLocationListener implements BDLocationListener {
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
				
			cityName = location.getProvince()+location.getDistrict();	
			mLocationClient.stop();
		}
	}

	//返回城市字符串
	public String getCity() {		
		return cityName;
	}
	
	public void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);//设置定位模式
		option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
}
