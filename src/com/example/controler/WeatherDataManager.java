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
	//��������س���
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
	//��������س���
	
	//��ȡ���ݷ�����//
	public String getCity(){								//��ȡ��������
		return weatherInfo.get("city");
	}
	
	public String getDegreeString(int day){					//��ȡ��������¡�������ַ���
		return weatherInfo.get("temp"+day);
	}
	
	public String getMaxDegreeInDay(int day){				//��ȡ������ַ���
		String []tmp=weatherInfo.get("temp"+day).split("~");
		return tmp[0];
	}
	public String getMinDegreeInDay(int day){				//��ȡ������ַ���
		String []tmp=weatherInfo.get("temp"+day).split("~");
		return tmp[1];
	}
	public String GetWeatherString(int day){				//������������
		return weatherInfo.get("weather"+day);
	}
	public String getImageId(int day,int dayOrNight){	   //��ȡͼƬ������
		if(dayOrNight==DAY){
			return weatherInfo.get("img"+day);
		}
		return weatherInfo.get("img"+(day+6));
	}
	public String getImageTitle(int day,int dayOrNight){	//��ȡÿ��ͼƬ��Ӧ�Ľ�����Ϣ
		if(dayOrNight==DAY){
			return weatherInfo.get("img_title"+day);
		}
		return weatherInfo.get("img_title"+(day+6));
	}
	public String getWindDescription(int day){				//��ȡ�������
		return weatherInfo.get("wind"+day);
	}
	public String getWindLevelDescription(int day){			//��ȡ���ٵȼ�
		return weatherInfo.get("fl"+day);
	}
	
	public String getExponent(int exponentId){				//�õ�����ָ��
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
	
	//��ȡ���ݷ�����//
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

		        //���httpͷ��Ϣ    
		        get.addHeader("Authorization", "hxt ");  
		        get.addHeader("Content-Type", "application/json");  
		        get.addHeader("User-Agent","student");  
		        HttpResponse response;  
		        response = httpclient.execute(get);
		        int code = response.getStatusLine().getStatusCode(); 
		        //����״̬�룬����ɹ���������   
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
