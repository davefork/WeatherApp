package com.example.weatherapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.Gallery;


//-------���˰�-------//
import com.example.controler.*;

//-------���˰�-------//

public class WeatherActivity extends Activity {
	Gallery g;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sforcast);
        g=(Gallery) findViewById(R.id.gallery1);
        try {
			g.setAdapter(new myAdapter(this));
		} catch (IllegalArgumentException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }
}
