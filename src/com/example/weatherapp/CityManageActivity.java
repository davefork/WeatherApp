package com.example.weatherapp;

import com.example.content.TitleBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.controler.DBManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CityManageActivity extends Activity{
	private ListView listView;  //����һ��ListView����  
	private List<info> mlistInfo = new ArrayList<info>();  //����һ��list����̬�洢Ҫ��ʾ����Ϣ  
	private DBManager db;
	private info getObject;
	private TitleBar tb ;
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		db.close();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citycollection);
		listView = (ListView) findViewById(R.id.listView);
		db = new DBManager(this.getApplicationContext());
		db.openWeatherDB();
		db.createCollectionDB(CityManageActivity.this);
		tb = (TitleBar) findViewById(R.id.title_bar);
		tb.setTvTitle("�ղصĳ���");
		setInfo();
		tb.getBtn_left().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});
		tb.getBtn_right().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent i = new Intent();
			        i.setClass(CityManageActivity.this, ProvinceActivity.class);
			        startActivity(i);
			}			
		});
		listView.setAdapter(new ListViewAdapter(mlistInfo));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				getObject = mlistInfo.get(position);//ͨ��position��ȡ������Ķ��� 
				int infoId = getObject.getId();//��ȡ��Ϣid  
				//Toast��ʾ����  
				Toast.makeText(CityManageActivity.this, "��ϢID:"+infoId,Toast.LENGTH_SHORT).show();				
			}			
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				getObject = mlistInfo.get(position);
				return false;
			}
		});
		//�����˵���ʾ
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu conMenu, View view,
					ContextMenuInfo info) {
				// TODO Auto-generated method stub
				conMenu.setHeaderTitle("�������");
				conMenu.add(0, 0, 0, "��Ҫ�����������");
				conMenu.add(0, 1, 1, "��Ҫɾ����");
			}
		});		
	    }
	//�����˵�������
	public boolean onContextItemSelected(MenuItem aItem)
	{
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();
		switch(aItem.getItemId()){
		case 0:
			Toast.makeText(CityManageActivity.this, "�鿴���������",Toast.LENGTH_SHORT).show();
			return true;  
		case 1:
			db.deleteCity(getObject.getTitle());			
			setInfo();
			listView.setAdapter(new ListViewAdapter(mlistInfo));
			Toast.makeText(CityManageActivity.this, "��ɾ��������ط�",Toast.LENGTH_SHORT).show();
			return true; 
		}
		return false;			
	}
	
	public class ListViewAdapter extends BaseAdapter
	{
		View[] itemViews;
		
		public ListViewAdapter(List<info>mlistInfo)
		{
			itemViews = new View[mlistInfo.size()];
			for(int i=0;i<mlistInfo.size();i++){
				info getInfo = (info)mlistInfo.get(i);
				itemViews[i]=makeItemView(
						getInfo.getTitle(),getInfo.getDetails(),getInfo.getAvatar());
			}
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemViews.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			 return itemViews[position];    
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null)    
				 return itemViews[position];    
			return convertView;  
		}	
		
		private View makeItemView(String strTitle, String strText, int resId){
			LayoutInflater inflater = (LayoutInflater) CityManageActivity.this    
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
					// ʹ��View�Ķ���itemView��R.layout.item����  
					View itemView = inflater.inflate(R.layout.item_collection, null);  
					// ͨ��findViewById()����ʵ��R.layout.item�ڸ����  
					TextView title = (TextView) itemView.findViewById(R.id.title);    
					title.setText(strTitle);    //������Ӧ��ֵ  
					TextView text = (TextView) itemView.findViewById(R.id.info);    
					text.setText(strText);    
					ImageView image = (ImageView) itemView.findViewById(R.id.img);    
					image.setImageResource(resId);  

			return itemView;
		}
	}
	public void setInfo(){  
		Iterator it = db.getCollection().iterator();
		int i = 0;
		String cityName;
		mlistInfo.clear();   
		while(it.hasNext()){
		cityName = it.next().toString();
		info information = new info();  
		information.setId(i);  
		information.setTitle(cityName);  
		information.setDetails(cityName+"���������");  
		information.setAvatar(R.drawable.ic_launcher);  
		mlistInfo.add(information); //���µ�info������뵽��Ϣ�б���  
		i++;  
		}  
	}
	public class info {  
	    private int id; //��ϢID  
	    private String title;   //��Ϣ����  
	    private String details; //��ϸ��Ϣ  
	    private int avatar; //ͼƬID  			     			   		  
	    //��ϢID������  
	    public void setId(int id) {    
		        this.id = id;  
		    }  
		    public int getId() {    
		        return id;  
		    }  
		    //����  
		    public void setTitle(String title) {    
		        this.title = title;  
		    }  
		   public String getTitle() {  
		        return title;    
		    }  
		      
		    //��ϸ��Ϣ  
		    public void setDetails(String info) {    
		        this.details = info;  
		   }  
		    public String getDetails() {    
		        return details;    
		    }  
		      
		    //ͼƬ  
		    public void setAvatar(int avatar) {    
		        this.avatar = avatar;  
		    }  
		public int getAvatar() {    
		     return avatar;  
		}  
	} 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.weather, menu);
	        return true;
	    }
	}
