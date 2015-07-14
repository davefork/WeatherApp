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
	
	public WeatherDataManager(){
		weatherInfo=new HashMap<String,String>();
	}
	
	public void HttpGetData() {  
	    try {  
	    	System.out.println("in");
	        HttpClient httpclient = new DefaultHttpClient();  
	        System.out.println("in");
	        String uri = "http://m.weather.com.cn/atad/101190101.html";   
	        HttpGet get = new HttpGet(uri);  
	        System.out.println("in");
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
