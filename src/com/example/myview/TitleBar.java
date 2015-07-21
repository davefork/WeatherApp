package com.example.myview;


import com.example.weatherapp.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleBar extends LinearLayout {
 
    private ImageView btn_right;
    private TextView tv_title;
 
    public TitleBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
 
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
 
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.title_bar, this);   
        btn_right = (ImageView) findViewById(R.id.add);
        tv_title = (TextView) findViewById(R.id.textView1);        
        btn_right.setClickable(true);
 
    }
    
 
 
    public void setBtnRightImage(int res)
    {
    	if (btn_right != null) {
    		btn_right.setImageResource(res);
        }
    }
  
    public void setTvTitle(String resId) {
        if (tv_title != null) {
            tv_title.setText(resId);
        }
    }
 
   
 
    public ImageView getBtn_right() {
        return btn_right;
    }
 
    public TextView getTv_title() {
        return tv_title;
    }
 
}
