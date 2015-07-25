package com.example.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class widgetProvider extends AppWidgetProvider {

	private Intent intent;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		intent = new Intent(context, UpdateService.class);  
        context.startService(intent);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		context.stopService(intent);
		super.onDeleted(context, appWidgetIds);
	}
	
}
