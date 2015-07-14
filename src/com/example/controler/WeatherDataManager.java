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

public class WeatherDataManager {
	private Map<String,String> weatherInfo;
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
	//天气类相关常量
	
	//获取数据方法集//
	public String getCity(){								//获取城市名字
		return weatherInfo.get("city");
	}
	
	public String getDegreeString(int day){					//获取完整最高温、最低温字符串
		return weatherInfo.get("temp"+day);
	}
	
	public String getMaxDegreeInDay(int day){				//获取最高温字符串
		String []tmp=weatherInfo.get("temp"+day).split("~");
		return tmp[0];
	}
	public String getMinDegreeInDay(int day){				//获取最低温字符串
		String []tmp=weatherInfo.get("temp"+day).split("~");
		return tmp[1];
	}
	public String GetWeatherString(int day){				//天气汉字描述
		return weatherInfo.get("weather"+day);
	}
	public String getImageId(int day,int dayOrNight){	   //获取图片的名字
		if(dayOrNight==DAY){
			return weatherInfo.get("img"+day);
		}
		return weatherInfo.get("img"+(day+6));
	}
	public String getImageTitle(int day,int dayOrNight){	//获取每个图片对应的解释信息
		if(dayOrNight==DAY){
			return weatherInfo.get("img_title"+day);
		}
		return weatherInfo.get("img_title"+(day+6));
	}
	public String getWindDescription(int day){				//获取风的描述
		return weatherInfo.get("wind"+day);
	}
	public String getWindLevelDescription(int day){			//获取风速等级
		return weatherInfo.get("fl"+day);
	}
	
	public String getExponent(int exponentId){				//得到各种指数
		switch(exponentId){
		case CLOTH_EXPONENT:
			return weatherInfo.get("index");
		case ULTRAVIOLET_EXPONENT:
			return weatherInfo.get("index_uv");
		case WASH_CAR_EXPONENT:
			return weatherInfo.get("index_xc");
		case TRAVEL_EXPONENT:
			return weatherInfo.get("index_tr");
		case COMFORTABLE_EXPONENT:
			return weatherInfo.get("index_co");
		case PRACTICE_EXPONENT:
			return weatherInfo.get("index_cl");
		case DRY_EXPONENT:
			return weatherInfo.get("index_ls");
		case ALLERGIC_EXPONENT:
			return weatherInfo.get("index_ag");
		}
		return null;
	}
	
	//获取数据方法集//
	public WeatherDataManager(){
		weatherInfo=new HashMap<String,String>();
	}
	
	public void HttpGetData(){
		new Thread(getRunnable).start();
	}
	
	private Runnable getRunnable = new Runnable(){
	    @Override
	    public void run() {
	    	try {  
		        HttpClient httpclient = new DefaultHttpClient();  
		        String uri = "http://m.weather.com.cn/atad/101190101.html";   
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
		            String rev = EntityUtils.toString(response.getEntity());    
		            parseJSON(rev);
		        }  
		    } catch (Exception e) {    
		    	e.printStackTrace();
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
			 while( itKey.hasNext())
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
