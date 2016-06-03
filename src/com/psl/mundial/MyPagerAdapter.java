package com.psl.mundial;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import dto.Sheet;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	@SuppressWarnings("deprecation")
	public MyPagerAdapter(FragmentManager fm, int screenWidth, int screenHeight, int numberOfItems, int numberOfItemsPerPage, Context context, List<Sheet> sheets) {
		super(fm);
		this.fragments = new ArrayList<Fragment>();
		
		int numberOfPages = 0;
		
		if((numberOfItems % numberOfItemsPerPage) > 0){
			numberOfPages = (numberOfItems / numberOfItemsPerPage) + 1;
		}else {
			numberOfPages = numberOfItems / numberOfItemsPerPage;
		}
		
		int horizontalItems = screenWidth / 50;
		
		int verticalItems = 0;
		
		if((numberOfItemsPerPage % horizontalItems) > 0){
			verticalItems = (numberOfItemsPerPage / horizontalItems) + 1;
		}else {
			verticalItems = (numberOfItemsPerPage / horizontalItems);
		}
		
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(Color.TRANSPARENT);
		drawable.setShape(GradientDrawable.OVAL);
		drawable.setStroke(2, context.getResources().getColor(R.color.DarkGray));
		
		for (int i = 0; i < numberOfPages; i++) {
			Log.d("pages", "pagina " + i);
			Fragment page = new Fragment();
			ScrollView scrollView = new ScrollView(context);
			LinearLayout verticalLayout = new LinearLayout(context);
			LayoutParams vp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			verticalLayout.setLayoutParams(vp);
			verticalLayout.setOrientation(LinearLayout.VERTICAL);
			
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			LayoutParams bp = new LayoutParams(0, 55, 1);
			
			for (int x = 0; x < horizontalItems; x++) {
				LinearLayout layout = new LinearLayout(context);
				layout.setLayoutParams(lp);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.setWeightSum(5);
				for (int y = 0; y < verticalItems; y++) {
					Button button = new Button(context);
					button.setText(""+ ((x*5) + (y+1)) );
					button.setBackgroundDrawable(drawable);
					layout.addView(button, bp);
				}			
				verticalLayout.addView(layout);
			}
			
			scrollView.addView(verticalLayout);
			
			fm.beginTransaction().add(scrollView.getId(), page).commit();		
			
			fragments.add(page);
		}
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
}