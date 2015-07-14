package com.example.controler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DBManager {
	private final int BUFFER_SIZE = 1024; 
	private SQLiteDatabase database;
	private Context context;

	public DBManager(Context context)
	{
		this.context = context; 
	}
	//获取所有城市的名字， 方便列表
	public Cursor getAllCity(){
		Cursor cr=database.rawQuery("select * from weather", null);
		return cr;
	}

	//获取城市的Code
	public int getCityCode(String cityName){
		Cursor cr=database.rawQuery("select * from weather where city='"+cityName+"'", null);
		while(cr.moveToNext()){
			if(cr.getInt(1)>10000000){
				return cr.getInt(1);
			}
		}
		return -1;
	}
	 
	public void openDatabase() 
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
	            InputStream is= am.open("weather.db");

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
	        database = SQLiteDatabase.openOrCreateDatabase(gpsPath, null);   
	    }

	 
	public void close()
	{
		if (database != null)
		{
		this.database.close(); 
		}
	}
}
