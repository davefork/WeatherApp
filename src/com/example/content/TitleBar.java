package com.example.content;


import com.example.weatherapp.R;
import com.example.weatherapp.R.id;
import com.example.weatherapp.R.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleBar extends LinearLayout {
 
    private ImageView btn_left;
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
        btn_left = (ImageView) findViewById(R.id.back);
        btn_right = (ImageView) findViewById(R.id.add);
        tv_title = (TextView) findViewById(R.id.textView1);
        btn_left.setClickable(true);
        btn_right.setClickable(true);
 
    }
    
    public void setBtnleftImage(int res)
    {
    	if (btn_left != null) {
            btn_left.setImageResource(res);
        }
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
 
    public ImageView getBtn_left() {
        return btn_left;
    }
 
    public ImageView getBtn_right() {
        return btn_right;
    }
 
    public TextView getTv_title() {
        return tv_title;
    }
 
}
