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
	private List<String> proset=new ArrayList<String>();//ʡ�ݼ���
	private List<String> citset=new ArrayList<String>();//���м���
	private List<String> colset=new ArrayList<String>();//�ղس��м���
	
	private static DBManager dbManager=null;
	
	public static DBManager getDBManager(Context context){
		if(dbManager==null){
			dbManager=new DBManager(context);
		}
		
		return dbManager;
	}
	
	//�ڲ�dbhelper��
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
	//�����ղس������ݿ�
	public int createCollectionDB(Context context)
	{	
		DbHelper dbHelper = new DbHelper(context,"collectcity");
		System.out.println("create database");

		dbhelper = dbHelper;
		
		return 0;
	}
	//��ȡDbHelper��
	public DbHelper getDbHelper()
	{
		return dbhelper;
	}
	//��ӳ���
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
	//ɾ������
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
	//��ȡ�ղس��м���
	public List<String> getCollection()
	{
		//����ղس��м���
    	colset.clear();
       //�����ݿ� 
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
	//�洢�����������
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
	//��ȡ�ó������һ�θ��µ�����
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
	
	//��ȡ���г��е����֣� �����б�
	public Cursor getAllCity(){
		Cursor cr=db_weather.rawQuery("select * from citys", null);
		return cr;
	}

	//��ȡ���е�Code
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
	
	// ���� ʡ�ݼ���
     
	public List<String> getProSet(){
       //�����ݿ� 
 		Cursor cursor=db_weather.query("provinces", null, null, null, null, null, null);
 		while(cursor.moveToNext()){
 			String pro=cursor.getString(cursor.getColumnIndexOrThrow("name"));
 			proset.add(pro);
 		}
 		cursor.close();
    	return proset;
    }
  
    //���س��м���
    public List<String> getCitSet(int pro_id){
    	//��ճ��м���
    	citset.clear();
       //�����ݿ� 
 		Cursor cursor=db_weather.query("citys", null, "province_id="+pro_id, null, null, null, null);
 		while(cursor.moveToNext()){
 			String city=cursor.getString(cursor.getColumnIndexOrThrow("name"));
 			citset.add(city);
 		}
 		cursor.close();
    	return citset;
    } 
	//���������ݿ�
    public void openWeatherDB() 
	{ 
        File sdFile = Environment.getExternalStorageDirectory();
        File gpsPath = new File(sdFile.getPath()+"/weatherApp/weather.db");
        
        if (!gpsPath.exists())
        { 
            try
            {
	            //����Ŀ¼
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

