package com.psl.mundial;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import dao.SheetsDataSource;
import dto.Sheet;

public class ButtonOnClickListener implements OnClickListener {

	private SheetsDataSource dataSource;
	private Sheet sheet;
	private GradientDrawable drawable;
	private Context context;
	
	public ButtonOnClickListener(SheetsDataSource dataSource, int sheetId, String sheetColor, GradientDrawable drawable, Context context){
		this.sheet = new Sheet(sheetId, sheetColor);
		this.dataSource = dataSource;
		this.drawable = drawable;
		this.context = context;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Log.d("tag", ""+view.getTag());
		if (view.getTag().equals("WHITE")){
			drawable.setColor(context.getResources().getColor(R.color.Green));
			manageItems(sheet.getId(), "GREEN");
			view.setBackgroundDrawable(drawable);
			view.setTag("GREEN");
		} else if (view.getTag().equals("GREEN")){
			drawable.setColor(context.getResources().getColor(R.color.White));
			manageItems(sheet.getId(), "WHITE");
			view.setBackgroundDrawable(drawable);
			view.setTag("WHITE");
		}		
	}

	public void manageItems(int sheetId, String color){
		  dataSource.updateSheet(sheetId, color);
	  }
	
}
