package com.example.controler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

public class WeatherDataManager {
	private Map<String,String> weatherInfo;
	private static Map<String,WeatherDataManager> managerInfo=null;
	private static WeatherDataManager wm=null;
	private DBManager dbManager;
	private String uri;
	//天气类相关常量
	public static final int DAY=0;
	public static final int NIGHT=1;
	
	
	public static final int CLOTH_EXPONENT=0;
	public static final int ULTRAVIOLET_EXPONENT=1;
	public static final int WASH_CAR_EXPONENT=2;
	public static final int TRAVEL_EXPONENT=3;
	public static final int COMFORTABLE_EXPONENT=4;
	public static final int PRACTICE_EXPONENT=5;
	public static final int DRY_EXPONENT=6;
	public static final int ALLERGIC_EXPONENT=7;
	
	public static final int NUM_OF_EXPONENT=8;
	//天气类相关常量
	
	public static WeatherDataManager getWeatherDataManager(String cityName,DBManager dbManager){
		if(managerInfo==null){
			managerInfo=new HashMap<String,WeatherDataManager>();
		}
		if(managerInfo.get(cityName)==null){
			managerInfo.put(cityName, new WeatherDataManager(cityName,dbManager));
		}
		
		return managerInfo.get(cityName);
	}
	public WeatherDataManager(String cityName,DBManager dbManager){
		weatherInfo=new HashMap<String,String>();
		
		this.dbManager=dbManager;
		
		dbManager.createCollectionDB(dbManager.getContext());
		String data=dbManager.getLastWeather(cityName);
		if(data==null){
			dbManager.openWeatherDB();	
			uri="http://m.weather.com.cn/atad/"+dbManager.getCityCode(cityName)+".html";
			HttpGetData();
		}
		else{
			parseJSON(data);
			System.out.println(data);
			HttpGetData();
		}
	}
	
	
	//获取数据方法集//
	public String getCity(){								//获取城市名字
		String tmp=weatherInfo.get("city");
		if(tmp==null){
			return "";
		}
		return tmp;
	}
	
	public String getDegreeString(int day){					//获取完整最高温、最低温字符串
		String tmp=weatherInfo.get("temp"+day);
		if(tmp==null){
			return "";
		}
		return tmp;
	}
	
	public String getWeek(){
		String tmp= weatherInfo.get("week");
		if(tmp==null){
			return "";
		}
		return tmp;
	}
	
	public String getMaxDegreeInDay(int day){				//获取最高温字符串
		String []tmp=weatherInfo.get("temp"+day).split("~");
		if(tmp[0]==null){
			return "1";
		}
		return tmp[0];
	}
	public String getMinDegreeInDay(int day){				//获取最低温字符串
		String []tmp=weatherInfo.get("temp"+day).split("~");
		if(tmp[1]==null){
			return "1";
		}
		return tmp[1];
	}
	public String GetWeatherString(int day){				//天气汉字描述
		String tmp= weatherInfo.get("weather"+day);
		if(tmp==null){
			return "";
		}
		return tmp;
	}
	
	public String getImageId(int day,int dayOrNight){	   //获取图片的名字
		if(dayOrNight==DAY){
			String tmp= weatherInfo.get("img"+day);
			if(tmp==null){
				return "1";
			}
			return tmp;
		}
		String tmp= weatherInfo.get("img"+(day+6));
		if(tmp==null){
			return "1";
		}
		return tmp;
	}
	
	public String getImageTitle(int day,int dayOrNight){	//获取每个图片对应的解释信息
		if(dayOrNight==DAY){
			String tmp= weatherInfo.get("img_title"+day);
			if(tmp==null){
				return "";
			}
			return tmp;
		}
		String tmp= weatherInfo.get("img_title"+(day+6));
		if(tmp==null){
			return "";
		}
		return tmp;
	}
	
	public String getWindDescription(int day){				//获取风的描述
		String tmp= weatherInfo.get("wind"+day);
		if(tmp==null){
			return "";
		}
		return tmp;
	}
	public String getWindLevelDescription(int day){			//获取风速等级
		String tmp= weatherInfo.get("fl"+day);
		if(tmp==null){
			return "";
		}
		return tmp;
	}
	
	public String getExponent(int exponentId){				//得到各种指数
		String tmp="";
		switch(exponentId){
		case CLOTH_EXPONENT:
			tmp= weatherInfo.get("index");
		case ULTRAVIOLET_EXPONENT:
			tmp= weatherInfo.get("index_uv");
		case WASH_CAR_EXPONENT:
			tmp= weatherInfo.get("index_xc");
		case TRAVEL_EXPONENT:
			tmp= weatherInfo.get("index_tr");
		case COMFORTABLE_EXPONENT:
			tmp= weatherInfo.get("index_co");
		case PRACTICE_EXPONENT:
			tmp= weatherInfo.get("index_cl");
		case DRY_EXPONENT:
			tmp= weatherInfo.get("index_ls");
		case ALLERGIC_EXPONENT:
			tmp= weatherInfo.get("index_ag");
		}
		if(tmp==null){
			return "";
		}
		return tmp;
	}
	
	public int getNumOfExponent(){
		return NUM_OF_EXPONENT;
	}
	
	//获取数据方法集//
	public WeatherDataManager(){
		weatherInfo=new HashMap<String,String>();
		managerInfo=new HashMap<String,WeatherDataManager>();
	}
	
	public void HttpGetData(){
		new Thread(getRunnable).start();
	}
	
	private Runnable getRunnable = new Runnable(){
	    @Override
	    public void run() {
	    	String rev="";
	    	try {  
		        HttpClient httpclient = new DefaultHttpClient();  
		     
		        HttpGet get = new HttpGet(uri);  

		        //添加http头信息    
		        get.addHeader("Authorization", "hxt ");  
		        get.addHeader("Content-Type", "application/json");  
		        get.addHeader("User-Agent","student");  
		        HttpResponse response;  
		        response = httpclient.execute(get);
		        int code = response.getStatusLine().getStatusCode(); 
		        //检验状态码，如果成功接收数据   
		        if (code == 200) { 
		            rev = EntityUtils.toString(response.getEntity());    
		            
		        }  
		    } catch (Exception e) {    
		    	e.printStackTrace();
		    }
	    	if(rev!=""){
	    		parseJSON(rev);
	    		dbManager.createCollectionDB(dbManager.getContext());
	    		dbManager.setLastWeather(getCity(), rev);
	    	}
	    	else{
	    	//	Toast.makeText(dbManager.getContext(), "您的网络状况不是很好呦~",Toast.LENGTH_SHORT).show();	
	    	}
	    }
	};
	
	public void parseJSON(String rev)
    {
		try {
			String key,value;
			JSONObject jsonObject = new JSONObject(rev);
			JSONObject tmp=new JSONObject(jsonObject.getString("weatherinfo"));
			Iterator itKey=tmp.keys(); 
			weatherInfo= new HashMap<String,String>();
			 while(itKey.hasNext())
		     {
			 	key=itKey.next().toString();
			 	value=tmp.getString(key);
			 	weatherInfo.put(key, value);
	        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
