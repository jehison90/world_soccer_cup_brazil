package com.psl.mundial;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import dao.SheetsDataSource;
import dto.Sheet;

class ViewPagerAdapter extends PagerAdapter
{

	List <Sheet> sheets;
	
	
  @SuppressWarnings("deprecation")
public ViewPagerAdapter(int screenWidth, int screenHeight, int numberOfItems, int numberOfItemsPerPage, Context context, List<Sheet> sheets, SheetsDataSource dataSource){
	  this.sheets = sheets; 
	  int numberOfPages = 0;
		
		if((numberOfItems % numberOfItemsPerPage) > 0){
			numberOfPages = (numberOfItems / numberOfItemsPerPage) + 1;
		}else {
			numberOfPages = numberOfItems / numberOfItemsPerPage;
		}
		
		int horizontalItems = screenWidth / 55;
		
		int verticalItems = 0;
		
		if((numberOfItemsPerPage % horizontalItems) > 0){
			verticalItems = (numberOfItemsPerPage / horizontalItems) + 1;
		}else {
			verticalItems = (numberOfItemsPerPage / horizontalItems);
		}
		
		Log.d("limites","pages "+numberOfPages+" hor: "+horizontalItems+" vert: "+verticalItems);
		
		for (int i = 0; i < numberOfPages; i++) {
			Log.d("pages", "pagina " + i);
			ScrollView scrollView = new ScrollView(context);
			LinearLayout verticalLayout = new LinearLayout(context);
			LayoutParams vp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			verticalLayout.setLayoutParams(vp);
			verticalLayout.setOrientation(LinearLayout.VERTICAL);
			
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			LayoutParams bp = new LayoutParams(50, 50);
			
			for (int y = 0; y < verticalItems; y++) {
				LinearLayout layout = new LinearLayout(context);
				layout.setLayoutParams(lp);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.setWeightSum(horizontalItems);
				bp = new LayoutParams(55, 55);
				for (int x = 0; x < horizontalItems; x++) {
					
					int itemNumber = (((i) * numberOfItemsPerPage) + ((y*horizontalItems) + (x)));
					
					if(itemNumber < ((i+1) * numberOfItemsPerPage) && itemNumber < numberOfItems){
						GradientDrawable drawable = null;						
						Sheet sheet = sheets.get(itemNumber);
						Button button = new Button(context);
						button.setText(""+ sheet.getId());
						if(sheet.getColor().equals("WHITE")){
							drawable = new GradientDrawable();		
							drawable.setShape(GradientDrawable.OVAL);
							drawable.setStroke(2, context.getResources().getColor(R.color.DarkGray));
							drawable.setColor(context.getResources().getColor(R.color.White));
							button.setBackgroundDrawable(drawable);
							button.setTag("WHITE");
						}else if(sheet.getColor().equals("GREEN")){
							drawable = new GradientDrawable();		
							drawable.setShape(GradientDrawable.OVAL);
							drawable.setStroke(2, context.getResources().getColor(R.color.DarkGray));
							drawable.setColor(context.getResources().getColor(R.color.Green));
							button.setBackgroundDrawable(drawable);
							button.setTag("GREEN");
							Log.d("g", "g");
						}
						
						button.setOnClickListener(new ButtonOnClickListener(dataSource, sheet.getId(), ""+button.getTag(), drawable, context));
						layout.addView(button, bp);
					}					
				}			
				verticalLayout.addView(layout);
			}
			
			scrollView.addView(verticalLayout);
			
			addView(scrollView);
		}
  }

  // This holds all the currently displayable views, in order from left to right.
  private ArrayList<View> views = new ArrayList<View>();

  //-----------------------------------------------------------------------------
  // Used by ViewPager.  "Object" represents the page; tell the ViewPager where the
  // page should be displayed, from left-to-right.  If the page no longer exists,
  // return POSITION_NONE.
  @Override
  public int getItemPosition (Object object)
  {
    int index = views.indexOf (object);
    if (index == -1)
      return POSITION_NONE;
    else
      return index;
  }

  //-----------------------------------------------------------------------------
  // Used by ViewPager.  Called when ViewPager needs a page to display; it is our job
  // to add the page to the container, which is normally the ViewPager itself.  Since
  // all our pages are persistent, we simply retrieve it from our "views" ArrayList.
  @Override
  public Object instantiateItem (ViewGroup container, int position)
  {
    View v = views.get (position);
    container.addView (v);
    return v;
  }

  //-----------------------------------------------------------------------------
  // Used by ViewPager.  Called when ViewPager no longer needs a page to display; it
  // is our job to remove the page from the container, which is normally the
  // ViewPager itself.  Since all our pages are persistent, we do nothing to the
  // contents of our "views" ArrayList.
  @Override
  public void destroyItem (ViewGroup container, int position, Object object)
  {
    container.removeView (views.get (position));
  }

  //-----------------------------------------------------------------------------
  // Used by ViewPager; can be used by app as well.
  // Returns the total number of pages that the ViewPage can display.  This must
  // never be 0.
  @Override
  public int getCount ()
  {
    return views.size();
  }

  //-----------------------------------------------------------------------------
  // Used by ViewPager.
  @Override
  public boolean isViewFromObject (View view, Object object)
  {
    return view == object;
  }

  //-----------------------------------------------------------------------------
  // Add "view" to right end of "views".
  // Returns the position of the new view.
  // The app should call this to add pages; not used by ViewPager.
  public int addView (View v)
  {
    return addView (v, views.size());
  }

  //-----------------------------------------------------------------------------
  // Add "view" at "position" to "views".
  // Returns position of new view.
  // The app should call this to add pages; not used by ViewPager.
  public int addView (View v, int position)
  {
    views.add (position, v);
    return position;
  }

  //-----------------------------------------------------------------------------
  // Removes "view" from "views".
  // Retuns position of removed view.
  // The app should call this to remove pages; not used by ViewPager.
  public int removeView (ViewPager pager, View v)
  {
    return removeView (pager, views.indexOf (v));
  }

  //-----------------------------------------------------------------------------
  // Removes the "view" at "position" from "views".
  // Retuns position of removed view.
  // The app should call this to remove pages; not used by ViewPager.
  public int removeView (ViewPager pager, int position)
  {
    // ViewPager doesn't have a delete method; the closest is to set the adapter
    // again.  When doing so, it deletes all its views.  Then we can delete the view
    // from from the adapter and finally set the adapter to the pager again.  Note
    // that we set the adapter to null before removing the view from "views" - that's
    // because while ViewPager deletes all its views, it will call destroyItem which
    // will in turn cause a null pointer ref.
    pager.setAdapter (null);
    views.remove (position);
    pager.setAdapter (this);

    return position;
  }

  //-----------------------------------------------------------------------------
  // Returns the "view" at "position".
  // The app should call this to retrieve a view; not used by ViewPager.
  public View getView (int position)
  {
    return views.get (position);
  }

}