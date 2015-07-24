package com.example.controler;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DBManager {
	private final int BUFFER_SIZE = 1024; 
	private SQLiteDatabase db_weather;
	private Context context;
	private DbHelper dbhelper;
	private List<String> proset=new ArrayList<String>();//省份集合
	private List<String> citset=new ArrayList<String>();//城市集合
	private List<String> colset=new ArrayList<String>();//收藏城市集合
	
	private static DBManager dbManager=null;
	
	public static DBManager getDBManager(Context context){
		if(dbManager==null){
			dbManager=new DBManager(context);
		}
		
		return dbManager;
	}
	
	//内部dbhelper类
	private class DbHelper extends SQLiteOpenHelper
	{

		public DbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}
		public DbHelper(Context context, String name) {
			super(context, name, null, 1);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onCreate(SQLiteDatabase db_collection) {
			// TODO Auto-generated method stub
			db_collection.execSQL("create table collection (name varchar primary key,weather varchar);");
			System.out.println("create collection");
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}		
	}
	//创建收藏城市数据库
	public int createCollectionDB(Context context)
	{	
		DbHelper dbHelper = new DbHelper(context,"collectcity");
		System.out.println("create database");

		dbhelper = dbHelper;
		
		return 0;
	}
	//获取DbHelper类
	public DbHelper getDbHelper()
	{
		return dbhelper;
	}
	//添加城市
	public int addCity(String cityName)
	{
		SQLiteDatabase db_collection;
		db_collection = getDbHelper().getReadableDatabase();
		ContentValues  values = new ContentValues();
		values.put("name",cityName);
		db_collection.insert("collection", null, values);
		System.out.println("add city");
		dbhelper.close();
		db_collection.close();
		close();
		return 0;
	}
	//删除城市
	public int deleteCity(String cityName)
	{
		SQLiteDatabase db_collection;
		db_collection = getDbHelper().getReadableDatabase();
		String[] args = {cityName};
		db_collection.delete("collection", "name=?", args);
		System.out.println("delete city");
		dbhelper.close();
		db_collection.close();
		close();
        return 0;
	}
	//获取收藏城市集合
	public List<String> getCollection()
	{
		//清空收藏城市集合
    	colset.clear();
       //打开数据库 
    	SQLiteDatabase db_collection;
		db_collection = getDbHelper().getReadableDatabase();
 		Cursor cursor=db_collection.query("collection", null, null, null, null, null, null);
 		while(cursor.moveToNext()){
 			String city=cursor.getString(cursor.getColumnIndexOrThrow("name"));
 			colset.add(city);
 		}
 		dbhelper.close();
 		cursor.close();
		return colset;		
	}
	//存储最近更新天气
	public int setLastWeather(String city,String weather)
	{
		SQLiteDatabase db_collection;
		db_collection = getDbHelper().getReadableDatabase();
		db_collection.execSQL("update collection set weather =' "+weather+"'where name = '"+
		city+"';");
		dbhelper.close();
		db_collection.close();
		return 0;
	}
	//获取该城市最近一次更新的天气
	public String getLastWeather(String city)
	{
		String weather = "";
		SQLiteDatabase db_collection;
		db_collection = getDbHelper().getReadableDatabase();
		db_collection.execSQL("");
		dbhelper.close();
		db_collection.close();
		return weather;
	}
	public DBManager(Context context)
	{
		this.context = context; 
	}
	
	//获取所有城市的名字， 方便列表
	public Cursor getAllCity(){
		Cursor cr=db_weather.rawQuery("select * from citys", null);
		return cr;
	}

	//获取城市的Code
	public int getCityCode(String cityName){
		Cursor cr=db_weather.rawQuery("select * from citys where name='"+cityName+"'", null);
		while(cr.moveToNext()){
			if(cr.getInt(3)>10000000){
				close();
				return cr.getInt(3);
			}
		}
		close();
		return -1;
		
	}
	
	// 返回 省份集合
     
	public List<String> getProSet(){
       //打开数据库 
 		Cursor cursor=db_weather.query("provinces", null, null, null, null, null, null);
 		while(cursor.moveToNext()){
 			String pro=cursor.getString(cursor.getColumnIndexOrThrow("name"));
 			proset.add(pro);
 		}
 		cursor.close();
    	return proset;
    }
  
    //返回城市集合
    public List<String> getCitSet(int pro_id){
    	//清空城市集合
    	citset.clear();
       //打开数据库 
 		Cursor cursor=db_weather.query("citys", null, "province_id="+pro_id, null, null, null, null);
 		while(cursor.moveToNext()){
 			String city=cursor.getString(cursor.getColumnIndexOrThrow("name"));
 			citset.add(city);
 		}
 		cursor.close();
    	return citset;
    } 
	//打开天气数据库
    public void openWeatherDB() 
	{ 
        File sdFile = Environment.getExternalStorageDirectory();
        File gpsPath = new File(sdFile.getPath()+"/weatherApp/weather.db");
        
        if (!gpsPath.exists())
        { 
            try
            {
	            //创建目录
	            File pmsPaht = new File(sdFile.getPath()+"/weatherApp");
	            Log.i("weatherApp", "weatherApp: "+pmsPaht.getPath());
	            pmsPaht.mkdirs();
	            
	            AssetManager am = this.context.getAssets(); 
	            InputStream is= am.open("db_weather.db");

				FileOutputStream fos = new FileOutputStream(gpsPath);
				 
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
	            while ((count = is.read(buffer)) > 0) 
	            {
                   fos.write(buffer, 0, count);
	            }
				 	fos.flush();
				 
				 	fos.close();
					is.close();
				 	am.close();
				}
				catch (IOException e)
				{  
					e.printStackTrace();
				} 
	        }
        db_weather = SQLiteDatabase.openOrCreateDatabase(gpsPath, null);   
	    }
	public void close()
	{
		if (db_weather != null)
		{
		this.db_weather.close(); 
		}
	}
	
}

